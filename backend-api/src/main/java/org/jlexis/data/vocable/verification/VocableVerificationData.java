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
import static org.jlexis.data.vocable.verification.ResolveParentheses.resolveParenthesesForList;

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
    protected boolean allIsOptional = false;
    protected List<VocableVerificationData> alternatives;
    protected VocableVerificationData optionalValues;
    protected String additionalQuestionText;

    public VocableVerificationData() {
        data = new HashSet<>();
        alternatives = new LinkedList<>();
    }

    public VocableVerificationData(AbstractTermData termData) {
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
        allIsOptional = other.allIsOptional;

        data.addAll(other.data);
        addOptionalValues(other.getOptionalValues());

        // copy the alternatives
        for (VocableVerificationData data : other.alternatives) {
            alternatives.add(new VocableVerificationData(data));
        }
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
            set.addAll(resolveParenthesesForList(set));
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
        if (optionalValues != null) {
            optionalValues.resolveAllParentheses();
        }

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

    protected void addOptionalValues(VocableVerificationData optionalValues) {
        if (this.optionalValues == null) {
            this.optionalValues = new VocableVerificationData();
            this.optionalValues.makeAllOptional();
        }

        this.optionalValues.addOptions(optionalValues.getAllTokens());
    }

    public void addOptionalTerm(AbstractTermData term) {
        addOptionalValues(new VocableVerificationData().tokenizeAndAddString(term.getResolvedAndPurgedTerm()));
    }

    private void removeOptionalValue(String value) {
        assert optionalValues.allIsOptional;

        if (!optionalValues.data.isEmpty()) {
            optionalValues.data.iterator().next().remove(value);
        }
    }

    private VocableVerificationData getOptionalValues() {
        if (optionalValues == null) {
            return new VocableVerificationData();
        }
        return optionalValues;
    }

    protected void addAlternative(VocableVerificationData alternative) {
        if (alternative != null) {
            alternatives.add(alternative);
        }
    }

    protected void addAlternativeTerm(AbstractTermData alternativeTermData) {
        VocableVerificationData alternative = new VocableVerificationData();
        alternative.tokenizeAndAddString(alternativeTermData.getResolvedAndPurgedTerm());
        addAlternative(alternative);
    }

    public void addMandatoryTerm(AbstractTermData mandatoryTermData) {
        tokenizeAndAddString(mandatoryTermData.getResolvedAndPurgedTerm());
    }

    protected void makeAllOptional() {
        Set<String> allTokens = getAllTokensInternal();
        data.clear();
        if (!allTokens.isEmpty()) {
            data.add(allTokens);
        }
        allIsOptional = true;
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

    protected void addOption(String option) {
        if (!allIsOptional) {
            throw new IllegalStateException("This verification data is not all optional. Set this state with " +
                    "makeAllOptional() first.");
        }
        if (option == null) {
            throw new NullPointerException("Argument is null.");
        }

        Set<String> set;
        if (!data.isEmpty()) {
            set = data.iterator().next();
        } else {
            set = new WhitespaceAndSuffixTolerantSet();
            data.add(set);
        }

        set.addAll(resolveParentheses(option));
    }

    protected void addOptions(Collection<String> options) {
        Objects.requireNonNull(options, "options collection to be added is null");
        options.forEach(this::addOption);
    }

    protected void addMandatoryValue(String mandatoryValue) {
        throwErrorIfConfiguredAsAllOptional();
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

    protected void addMandatoryValueWithOptions(String mandatoryValue, String[] options) {
        addMandatoryValueWithOptions(mandatoryValue, Arrays.asList(options));
    }

    public void addMandatoryValueWithOptions(Collection<String> options) {
        throwErrorIfConfiguredAsAllOptional();

        Set<String> newSet = new WhitespaceAndSuffixTolerantSet();
        newSet.addAll(resolveParenthesesForList(options));
        if (!newSet.isEmpty()) {
            data.add(newSet);
        }
    }

    protected void addMandatoryValueWithOptions(String mandatoryValue, Collection<String> options) {
        throwErrorIfConfiguredAsAllOptional();
        if (mandatoryValue == null || options == null) {
            throw new NullPointerException("One of the arguments is null.");
        }

        Set<String> set = getAlternativesForMandatoryValue(mandatoryValue);
        if (set == null) {
            Set<String> newSet = new WhitespaceAndSuffixTolerantSet();
            newSet.addAll(resolveParenthesesForList(options));
            newSet.addAll(resolveParentheses(mandatoryValue));

            if (!newSet.isEmpty()) {
                data.add(newSet);
            }
        } else {
            set.addAll(resolveParenthesesForList(options));
        }
    }

    private void throwErrorIfConfiguredAsAllOptional() {
        if (allIsOptional) {
            throw new IllegalStateException("Cannot add mandatory values. This verification data is all optional. " +
                    " Use addOption() instead.");
        }
    }

    public VocableVerificationData tokenizeAndAddString(String valueToAdd) {
        for (String mandatoryComponent : splitStringOmitEmptyAndTrim(Strings.nullToEmpty(valueToAdd), MANDATORY_COMPONENT_SPLIT_CHAR)) {
            addMandatoryValueWithOptions(createListOfOptionalComponents(mandatoryComponent));
        }
        return this;
    }

    private Set<String> createListOfOptionalComponents(String mandatoryComponent) {
        return new WhitespaceAndSuffixTolerantSet(resolveParenthesesForList(splitStringOmitEmptyAndTrim(mandatoryComponent, OPTIONAL_COMPONENT_SPLIT_CHAR)));
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

        for (Set<String> set : data) {
            result.addAll(set);
        }

        if (optionalValues != null) {
            result.addAll(optionalValues.getAllTokens());
        }
        for (VocableVerificationData data : alternatives) {
            result.addAll(data.getAllTokens());
        }

        return result;
    }

    protected Set<String> getAlternativesForMandatoryValue(String mandatoryValue) {
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
        Matcher m = sPattern1.matcher(replacedAbbreviation);
        if (m.matches()) regex = "\\b%s";
        m = sPattern2.matcher(replacedAbbreviation);
        if (m.matches()) regex = "%s\\b";
        m = sPattern3.matcher(replacedAbbreviation);
        if (m.matches()) regex = "%s";

        Pattern pattern = Pattern.compile(String.format(regex,
                StringUtils.escapeRegexSpecialChars(replacedAbbreviation).replaceAll("\\s+", "\\\\s+")));
        m = pattern.matcher(input);

        StringBuilder buf = new StringBuilder(input);
        whileLoop:
        while (m.find()) {
            for (IntRange range : excludedRanges) {
                if (range.containsInteger(m.start())) continue whileLoop;
            }
            IntRange range = new IntRange(m.start(), m.start() + masterAbbreviation.length() - 1);
            excludedRanges.add(range);

            buf.replace(m.start(), m.end(), masterAbbreviation);
            m = pattern.matcher(buf);
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

    public final static class UnmodifiableVocableVerificationData extends VocableVerificationData {

        public UnmodifiableVocableVerificationData(VocableVerificationData data) {
            this.data = Objects.requireNonNull(data.data);
            additionalQuestionText = data.additionalQuestionText;
            allIsOptional = data.allIsOptional;
            alternatives = data.alternatives;
            optionalValues = data.optionalValues;
        }

        @Override
        public void setAdditionalQuestionText(String additonalQuestionText) {
            throw new UnsupportedOperationException("This object cannot be modified.");
        }

        @Override
        public void addMandatoryTerm(AbstractTermData mandatoryTermData) {
            throw new UnsupportedOperationException("This object cannot be modified.");
        }

        @Override
        public void addAlternative(VocableVerificationData alternative) {
            // TODO Auto-generated method stub
            super.addAlternative(alternative);
        }

        @Override
        public void addAlternativeTerm(AbstractTermData alternativeTermData) {
            throw new UnsupportedOperationException("This object cannot be modified.");
        }

        @Override
        public void addMandatoryValue(String mandatoryValue) {
            throw new UnsupportedOperationException("This object cannot be modified.");
        }

        @Override
        public void addMandatoryValueWithOptions(Collection<String> options) {
            throw new UnsupportedOperationException("This object cannot be modified.");
        }

        @Override
        public void addMandatoryValueWithOptions(String mandatoryValue, Collection<String> options) {
            throw new UnsupportedOperationException("This object cannot be modified.");
        }

        @Override
        public void addMandatoryValueWithOptions(String mandatoryValue, String[] options) {
            throw new UnsupportedOperationException("This object cannot be modified.");
        }

        @Override
        public void addOption(String option) {
            throw new UnsupportedOperationException("This object cannot be modified.");
        }

        @Override
        public void addOptionalValues(VocableVerificationData optionalValues) {
            throw new UnsupportedOperationException("This object cannot be modified.");
        }

        @Override
        public void addOptions(Collection<String> options) {
            throw new UnsupportedOperationException("This object cannot be modified.");
        }

        @Override
        public void makeAllOptional() {
            throw new UnsupportedOperationException("This object cannot be modified.");
        }

        @Override
        public void addOptionalTerm(AbstractTermData term) {
            throw new UnsupportedOperationException("This object cannot be modified.");
        }

        @Override
        public VocableVerificationData tokenizeAndAddString(String value) {
            throw new UnsupportedOperationException("This object cannot be modified.");
        }
    }
}