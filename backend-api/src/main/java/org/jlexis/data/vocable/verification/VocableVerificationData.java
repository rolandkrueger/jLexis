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
import org.jlexis.data.vocable.terms.AbstractTermData;
import org.jlexis.data.vocable.terms.InflectedTerm;
import org.jlexis.data.vocable.terms.RegularTerm;

import java.util.*;

import static org.jlexis.data.vocable.verification.ResolveParentheses.resolveParenthesesForCollection;

/**
 * @author Roland Krueger
 */
public class VocableVerificationData {
    public static final char MANDATORY_COMPONENT_SPLIT_CHAR = ';';
    public static final char OPTIONAL_COMPONENT_SPLIT_CHAR = ',';

    private Set<Set<String>> data;
    private List<VocableVerificationData> alternatives;
    private WhitespaceAndSuffixTolerantSet optionalValues;
    private String additionalQuestionText;
    private SetOfAbbreviationVariants abbreviationVariants;

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

    /**
     * Resolves all parenthesized content for all mandatory and optional components of this object. Resolving
     * parentheses is performed according to the contract of class {@link org.jlexis.data.vocable.verification.ResolveParentheses}.
     * <p/>
     * For example, if the value <em>Lehrer(in)</em> has been set as a mandatory term for this data object, the result
     * of calling this method will be that the parentheses of this term will be resolved into the three values
     * <em>Lehrer(in)</em>, <em>Lehrer</em>, and <em>Lehrerin</em>. By that, providing at least one of these options as
     * an answer during a vocabulary quiz will be accepted as a correct solution.
     * <p/>
     * This operation is idempotent, i.e. it can be called more than once with its result remaining the same. It is
     * typically invoked exactly once for a {@link org.jlexis.data.vocable.verification.VocableVerificationData} object
     * during the process of verifying a given answer to a vocabulary quiz question.
     */
    private void resolveAllParenthesizedContent() {
        for (Set<String> set : data) {
            set.addAll(resolveParenthesesForCollection(set));
        }
        optionalValues.addAll(resolveParenthesesForCollection(optionalValues));
    }

    public VocableComparisonResult compareWith(VocableVerificationData comparisonValue) {
        final Set<String> inputSet = buildInputSetForComparison(comparisonValue);
        resolveAllParenthesizedContent();

        // Create a copy of this object. The data of this copy will be removed as needed.
        VocableVerificationData compareData = new VocableVerificationData(this);

        for (String value : new HashSet<>(inputSet)) {
            Set<String> thisMandatory = getAlternativesForMandatoryValue(value);
            if (!thisMandatory.isEmpty()) {
                markSetOfMandatoryVariantsAsFoundInBothObjects(inputSet, compareData, thisMandatory);
            } else if (compareData.getOptionalValues().contains(value)) {
                markOptionalValueAsFoundInBothObjects(inputSet, compareData, value);
            }
        }

        VocableComparisonResult result = new VocableComparisonResult();
        if (compareData.isEmpty()) {
            result.addRedundantValues(inputSet);
        } else {
            result.addMissingValues(compareData.getAllTokens());
        }
        return result;
    }

    private void markOptionalValueAsFoundInBothObjects(Set<String> inputSet, VocableVerificationData compareData, String value) {
        compareData.removeOptionalValue(value);
        inputSet.remove(value);
    }

    /**
     * If one of the mandatory answers is contained in the comparison verification data object remove the respective set
     * of alternative values for this mandatory answer from the result object.
     */
    private void markSetOfMandatoryVariantsAsFoundInBothObjects(Set<String> inputSet, VocableVerificationData compareData, Set<String> thisMandatory) {
        compareData.data.remove(thisMandatory);
        inputSet.removeAll(thisMandatory);
    }

    private Set<String> buildInputSetForComparison(VocableVerificationData comparisonValue) {
        Set<String> inputSet = new WhitespaceAndSuffixTolerantSet(comparisonValue.getAllTokens());
        for (VocableVerificationData data : comparisonValue.getAlternatives()) {
            inputSet.addAll(data.getAllTokens());
        }
        return inputSet;
    }

    public List<VocableVerificationData> getAlternatives() {
        return Collections.unmodifiableList(alternatives);
    }

    private void addOptionalTerm(AbstractTermData term) {
        optionalValues.addAll(
                VocableVerificationData.createFromTerms()
                        .tokenizeAndAddString(term.getResolvedAndPurgedTerm())
                        .finish().build()
                        .getAllTokens());
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

    private void addMandatoryValueWithOptions(Collection<String> options) {
        Set<String> newSet = new WhitespaceAndSuffixTolerantSet();
        newSet.addAll(options);
        if (!newSet.isEmpty()) {
            data.add(newSet);
        }
    }

    private VocableVerificationData tokenizeAndAddString(String valueToAdd) {
        String value = Strings.nullToEmpty(valueToAdd);
        value = abbreviationVariants.harmonizeAll(value);

        for (String mandatoryComponent : splitStringOmitEmptyAndTrim(value, MANDATORY_COMPONENT_SPLIT_CHAR)) {
            addMandatoryValueWithOptions(createListOfOptionalComponents(mandatoryComponent));
        }
        return this;
    }

    private Set<String> createListOfOptionalComponents(String mandatoryComponent) {
        return new WhitespaceAndSuffixTolerantSet(splitStringOmitEmptyAndTrim(mandatoryComponent, OPTIONAL_COMPONENT_SPLIT_CHAR));
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
        return Collections.emptySet();
    }

    public Set<Set<String>> getMandatoryValuesWithOptions() {
        return Collections.unmodifiableSet(data);
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

    public void setAbbreviationVariants(SetOfAbbreviationVariants abbreviationVariants) {
        this.abbreviationVariants = abbreviationVariants;
    }

    public final static class Builder {
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

        public FinishedBuilder finish() {
            return new FinishedBuilder(data);
        }
    }

    public final static class FinishedBuilder {
        private VocableVerificationData data;

        private FinishedBuilder(VocableVerificationData data) {
            this.data = data;
        }

        public FinishedBuilder withAbbreviationVariants(SetOfAbbreviationVariants variants) {
            data.setAbbreviationVariants(variants);
            return this;
        }

        public VocableVerificationData build() {
            return data;
        }
    }
}