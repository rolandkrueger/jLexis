/*
 * Created on 04.04.2009
 * 
 * Copyright 2007-2009 Roland Krueger (www.rolandkrueger.info)
 * 
 * This file is part of jLexis.
 *
 * jLexis is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * jLexis is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with jLexis; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package org.jlexis.data.vocable.verification;


import com.google.common.base.MoreObjects;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import org.apache.commons.lang.math.IntRange;
import org.jlexis.data.languages.Language;
import org.jlexis.data.vocable.terms.AbstractTermData;
import org.jlexis.data.vocable.terms.InflectedTerm;
import org.jlexis.data.vocable.terms.RegularTerm;
import org.jlexis.util.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.jlexis.data.vocable.verification.ResolveParentheses.resolveParentheses;
import static org.jlexis.data.vocable.verification.ResolveParentheses.resolveParenthesesForCollection;

/**
 * @author Roland Krueger
 */
public class VocableVerificationData {
    public static final char MANDATORY_COMPONENT_SPLIT_CHAR = ';';
    public static final char OPTIONAL_COMPONENT_SPLIT_CHAR = ',';
    private static Pattern sPattern1 = Pattern.compile("\\w+(\\w*\\W*)*\\w*\\W");
    private static Pattern sPattern2 = Pattern.compile("\\W\\w*(\\w*\\W*)*\\w+");
    private static Pattern sPattern3 = Pattern.compile("\\W(\\w*\\W*)*\\w*\\W");

    protected Set<Set<String>> data;
    protected List<VocableVerificationData> alternatives;
    protected WhitespaceAndSuffixTolerantSet optionalValues;
    protected String additionalQuestionText;

    private VocableVerificationData() {
        data = new HashSet<>();
        alternatives = new LinkedList<>();
        optionalValues = new WhitespaceAndSuffixTolerantSet();
    }

    private VocableVerificationData(AbstractTermData termData) {
        this();
        VocableVerificationData tmp = new VocableVerificationData();

        tmp.tokenizeAndAddString(termData.getNormalizedTerm());
        for (Set<String> mandatorySetWithNormalizedTerms : tmp.getMandatoryValuesWithOptions()) {
            Set<String> mandatorySet = new HashSet<>();
            for (String value : mandatorySetWithNormalizedTerms) {
                // For every token which the user has entered, add its resolved form to the
                // verification data, so that the resolved term is also a valid answer
                AbstractTermData term;
                if (termData.isInflected()) {
                    term = new InflectedTerm(termData.getWordStemObject());
                } else {
                    term = new RegularTerm();
                }
                term.setNormalizedTerm(value);
                mandatorySet.add(term.getPurgedTerm());

                RegularTerm regularTerm = new RegularTerm();
                regularTerm.setUserEnteredTerm(term.getResolvedTerm());
                mandatorySet.add(regularTerm.getPurgedTerm());
            }
            addMandatoryValueWithOptions(mandatorySet);
        }
    }

    /**
     * Copy constructor.
     *
     * @param other {@link VocableVerificationData} object to be copied
     */
    private VocableVerificationData(VocableVerificationData other) {
        this();

        data.addAll(other.data);
        optionalValues.addAll(other.optionalValues);

        // copy the alternatives
        for (VocableVerificationData data : other.alternatives) {
            alternatives.add(new VocableVerificationData(data));
        }
    }

    public static Builder create() {
        return new Builder();
    }

    public static DataWithMandatoryTermsBuilder createFromTerms() {
        return new DataWithMandatoryTermsBuilder();
    }

    @Deprecated
    public String getAdditionalQuestionText() {
        return additionalQuestionText;
    }

    @Deprecated
    public void setAdditionalQuestionText(String additionalQuestionText) {
        this.additionalQuestionText = additionalQuestionText;
    }

    private void resolveAllParentheses() {
        for (Set<String> set : data) {
            set.addAll(resolveParenthesesForCollection(set));
        }
    }

    public VocableVerificationResult verify(VocableVerificationData comparisonValue) {
        return verify(comparisonValue, null, false);
    }

    public VocableVerificationResult verify(VocableVerificationData comparisonValue, Language forLanguage) {
        return verify(comparisonValue, forLanguage, true);
    }

    public VocableVerificationResult verify(VocableVerificationData comparisonValue, Language forLanguage,
                                            boolean normalizeAbbreviations) {
        if (normalizeAbbreviations) {
            if (forLanguage == null) {
                throw new NullPointerException("Cannot normalize abbreviations: Language object is null.");
            }
            normalizeAbbreviations(forLanguage);
            comparisonValue.normalizeAbbreviations(forLanguage);
        }
        Set<String> inputSet = new WhitespaceAndSuffixTolerantSet(comparisonValue.getAllTokens());

        resolveAllParentheses();
        optionalValues.addAll(resolveParenthesesForCollection(optionalValues));

        for (VocableVerificationData alternative : alternatives) {
            VocableVerificationResult alternativeResult = alternative.verify(comparisonValue, forLanguage, normalizeAbbreviations);
            if (alternativeResult.getResult() == VocableVerificationResultEnum.CORRECT) {
                return alternativeResult;
            }
        }

        for (VocableVerificationData data : comparisonValue.getAlternatives()) {
            inputSet.addAll(data.getAllTokens());
        }

        // Create a copy of this object. The data of this copy will be removed as needed.
        VocableVerificationData compareData = new VocableVerificationData(this);

        for (String value : new HashSet<>(inputSet)) {
            Set<String> thisMandatory = getAlternativesForMandatoryValue(value);
            if (thisMandatory != null) {
                // if one of the mandatory answers is contained in the comparison verification data object
                // remove the respective set of alternative values for this mandatory answer from the
                // result object
                compareData.data.remove(thisMandatory);
                inputSet.removeAll(thisMandatory);
            } else if (compareData.getOptionalValues().contains(value)) {
                compareData.removeOptionalValue(value);
                inputSet.remove(value);
            }
        }

        if (compareData.isEmpty()) {
            if (inputSet.isEmpty()) {
                return new VocableVerificationResult(VocableVerificationResultEnum.CORRECT);
            } else {
                VocableVerificationResult result = new VocableVerificationResult(VocableVerificationResultEnum.TOO_MANY_VALUES);
                result.addRedundantValues(inputSet);
                return result;
            }
        } else {
            VocableVerificationResult result = new VocableVerificationResult(VocableVerificationResultEnum.NOT_ENOUGH_VALUES);
            result.addMissingValues(compareData.getAllTokens());
            return result;
        }
    }

    public List<VocableVerificationData> getAlternatives() {
        return Collections.unmodifiableList(alternatives);
    }

    private void addOptionalTerm(AbstractTermData term) {
        optionalValues.addAll(
                resolveParenthesesForCollection(
                        VocableVerificationData.createFromTerms()
                                .tokenizeAndAddString(term.getResolvedAndPurgedTerm())
                                .build()
                                .getAllTokens()));
    }

    private void removeOptionalValue(String value) {
        optionalValues.remove(value);
    }

    private Set<String> getOptionalValues() {
        return optionalValues;
    }

    private void addAlternative(VocableVerificationData alternative) {
        alternatives.add(Objects.requireNonNull(alternative, "cannot add null object as alternative"));
    }

    private void addMandatoryTerm(AbstractTermData mandatoryTermData) {
        tokenizeAndAddString(mandatoryTermData.getResolvedAndPurgedTerm());
    }

    protected boolean isEmpty() {
        if (data.isEmpty()) {
            return true;
        }

        for (Set<String> set : data) {
            if (!set.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private void addMandatoryValue(String mandatoryValue) {
        Objects.requireNonNull(mandatoryValue, "given mandatory value is null");

        if (contains(mandatoryValue)) {
            return;
        }

        Set<String> set = new WhitespaceAndSuffixTolerantSet();
        set.addAll(resolveParentheses(mandatoryValue));

        if (!set.isEmpty()) {
            data.add(set);
        }
    }

    private void addMandatoryValueWithOptions(Collection<String> options) {
        Set<String> newSet = new WhitespaceAndSuffixTolerantSet();
        newSet.addAll(resolveParenthesesForCollection(options));
        if (!newSet.isEmpty()) {
            data.add(newSet);
        }
    }

    private VocableVerificationData tokenizeAndAddString(String valueToAdd) {
        for (String mandatoryComponent : splitStringOmitEmptyAndTrim(Strings.nullToEmpty(valueToAdd), MANDATORY_COMPONENT_SPLIT_CHAR)) {
            addMandatoryValueWithOptions(createListOfOptionalComponents(mandatoryComponent));
        }
        return this;
    }

    private Set<String> createListOfOptionalComponents(String mandatoryComponent) {
        return new WhitespaceAndSuffixTolerantSet(resolveParenthesesForCollection(splitStringOmitEmptyAndTrim(mandatoryComponent, OPTIONAL_COMPONENT_SPLIT_CHAR)));
    }

    private List<String> splitStringOmitEmptyAndTrim(String value, char separator) {
        return Splitter.on(separator)
                .omitEmptyStrings()
                .trimResults()
                .splitToList(value);
    }

    private boolean contains(String token) {
        for (Set<String> set : data) {
            if (set.contains(token)) {
                return true;
            }
        }
        return false;
    }

    public Set<String> getAllTokens() {
        return Collections.unmodifiableSet(getAllTokensInternal());
    }

    private Set<String> getAllTokensInternal() {
        Set<String> result = new WhitespaceAndSuffixTolerantSet();

        data.forEach(result::addAll);

        if (optionalValues != null) {
            result.addAll(optionalValues);
        }
        for (VocableVerificationData data : alternatives) {
            result.addAll(data.getAllTokens());
        }

        return result;
    }

    private Set<String> getAlternativesForMandatoryValue(String mandatoryValue) {
        for (Set<String> set : data) {
            if (set.contains(mandatoryValue)) {
                return set;
            }
        }
        return null;
    }

    public Set<Set<String>> getMandatoryValuesWithOptions() {
        return Collections.unmodifiableSet(data);
    }

    protected void normalizeAbbreviations(Language forLanguage) {
        if (!forLanguage.getLanguagePlugin().isPresent()) {
            return;
        }
        List<Set<String>> abbreviationAlternatives =
                forLanguage.getLanguagePlugin().get().getAbbreviationAlternatives();

        String abbreviation;
        for (Set<String> set : data) {
            Set<String> copySet = new HashSet<>(set);
            for (String userAnswer : copySet) {
                Set<IntRange> excludedRanges = new HashSet<>();
                set.remove(userAnswer);
                for (Set<String> abbreviationSet : abbreviationAlternatives) {
                    Set<String> copyAbbreviationSet = new HashSet<>(abbreviationSet);
                    if (abbreviationSet.isEmpty()) {
                        continue;
                    }
                    String abbreviationMaster = abbreviationSet.iterator().next();


                    while (!copyAbbreviationSet.isEmpty()) {
                        abbreviation = "";
                        for (String s : copyAbbreviationSet) {
                            if (s.length() > abbreviation.length()) {
                                abbreviation = s;
                            }
                        }
                        copyAbbreviationSet.remove(abbreviation);
                        userAnswer = replaceAbbreviation(excludedRanges, userAnswer, abbreviation, abbreviationMaster);
                    }
                }
                set.add(userAnswer);
            }
        }
    }

    private String replaceAbbreviation(Set<IntRange> excludedRanges, String input,
                                       String replacedAbbreviation, String masterAbbreviation) {
        assert excludedRanges != null;
        String regex = "\\b%s\\b";
        Matcher matcher = sPattern1.matcher(replacedAbbreviation);
        if (matcher.matches()) {
            regex = "\\b%s";
        }
        matcher = sPattern2.matcher(replacedAbbreviation);
        if (matcher.matches()) {
            regex = "%s\\b";
        }
        matcher = sPattern3.matcher(replacedAbbreviation);
        if (matcher.matches()) {
            regex = "%s";
        }

        Pattern pattern = Pattern.compile(String.format(regex,
                StringUtils.escapeRegexSpecialChars(replacedAbbreviation).replaceAll("\\s+", "\\\\s+")));
        matcher = pattern.matcher(input);

        StringBuilder buf = new StringBuilder(input);
        whileLoop:
        while (matcher.find()) {
            for (IntRange range : excludedRanges) {
                if (range.containsInteger(matcher.start())) {
                    continue whileLoop;
                }
            }
            IntRange range = new IntRange(matcher.start(), matcher.start() + masterAbbreviation.length() - 1);
            excludedRanges.add(range);

            buf.replace(matcher.start(), matcher.end(), masterAbbreviation);
            matcher = pattern.matcher(buf);
        }

        return buf.toString();
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof VocableVerificationData))
            return false;

        VocableVerificationData other = (VocableVerificationData) obj;
        return data.equals(other.data);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass())
                .add("data", data)
                .toString();
    }

    public final static class Builder {
        public FinishedBuilder fromExisting(VocableVerificationData data) {
            return new FinishedBuilder(new VocableVerificationData(data));
        }

        public FinishedBuilder fromTermData(AbstractTermData termData) {
            return new FinishedBuilder(new VocableVerificationData(termData));
        }
    }

    public final static class DataWithMandatoryTermsBuilder {
        private VocableVerificationData data;

        private DataWithMandatoryTermsBuilder() {
            this.data = new VocableVerificationData();
        }

        public DataWithMandatoryTermsBuilder addMandatoryTerm(AbstractTermData mandatoryTermData) {
            data.addMandatoryTerm(mandatoryTermData);
            return this;
        }

        public DataWithMandatoryTermsBuilder addMandatoryValueWithOptions(Collection<String> options) {
            data.addMandatoryValueWithOptions(options);
            return this;
        }

        public DataWithMandatoryTermsBuilder tokenizeAndAddString(String valueToAdd) {
            data.tokenizeAndAddString(valueToAdd);
            return this;
        }

        public DataWithMandatoryTermsBuilder addOptionalTerm(AbstractTermData term) {
            data.addOptionalTerm(term);
            return this;
        }

        public DataWithMandatoryTermsBuilder addAlternative(VocableVerificationData alternative) {
            data.addAlternative(alternative);
            return this;
        }

        public VocableVerificationData build() {
            return data;
        }
    }

    public final static class FinishedBuilder {
        private VocableVerificationData data;

        private FinishedBuilder(VocableVerificationData data) {
            this.data = data;
        }

        public VocableVerificationData build() {
            return data;
        }

    }
}