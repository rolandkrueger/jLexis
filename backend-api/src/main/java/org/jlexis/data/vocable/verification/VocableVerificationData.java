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


import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import org.jlexis.data.vocable.terms.AbstractTerm;

import java.util.*;

import static org.jlexis.data.vocable.verification.ResolveParentheses.resolveParenthesesForCollection;

/**
 * @author Roland Krueger
 */
public class VocableVerificationData {
    public static final char MANDATORY_COMPONENT_SPLIT_CHAR = ';';
    public static final char OPTIONAL_COMPONENT_SPLIT_CHAR = ',';

    private MandatoryValuesWithAlternatives mandatoryValues;
    private WhitespaceAndSuffixTolerantSet optionalValues;
    private List<VocableVerificationData> alternatives;
    private SetOfAbbreviationVariants abbreviationVariants;

    @Deprecated
    private String additionalQuestionText;

    private VocableVerificationData() {
        mandatoryValues = new MandatoryValuesWithAlternatives();
        alternatives = new LinkedList<>();
        optionalValues = new WhitespaceAndSuffixTolerantSet();
    }

    private VocableVerificationData(AbstractTerm termData) {
        this();
        addMandatoryTerm(termData);
    }

    /**
     * Copy constructor.
     *
     * @param other {@link VocableVerificationData} object to be copied
     */
    private VocableVerificationData(VocableVerificationData other) {
        this();

        mandatoryValues = MandatoryValuesWithAlternatives.copy(other.mandatoryValues);
        optionalValues.addAll(other.optionalValues);

        // copy the alternatives
        for (VocableVerificationData data : other.alternatives) {
            alternatives.add(new VocableVerificationData(data));
        }
    }

    public static ChooseAbbreviationVariantsBuilder create() {
        return new ChooseAbbreviationVariantsBuilder();
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
        mandatoryValues.resolveAllParenthesizedContent();
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
                markSetOfMandatoryVariantsAsFoundInBothObjects(inputSet, compareData, thisMandatory, value);
            } else if (compareData.getOptionalValues().contains(value)) {
                markOptionalValueAsFoundInBothObjects(inputSet, compareData, value);
            }
        }

        VocableComparisonResult result = new VocableComparisonResult();
        if (compareData.isEmpty()) {
            result.addRedundantValues(inputSet);
        } else {
            result.addMissingValues(compareData.getAllValues());
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
    private void markSetOfMandatoryVariantsAsFoundInBothObjects(Set<String> inputSet, VocableVerificationData compareData, Set<String> thisMandatory, String value) {
        compareData.mandatoryValues.removeAlternativesForValue(value);
        inputSet.removeAll(thisMandatory);
    }

    private Set<String> buildInputSetForComparison(VocableVerificationData comparisonValue) {
        Set<String> inputSet = new WhitespaceAndSuffixTolerantSet(comparisonValue.getAllValues());
        for (VocableVerificationData data : comparisonValue.getAlternatives()) {
            inputSet.addAll(data.getAllValues());
        }
        return inputSet;
    }

    public List<VocableVerificationData> getAlternatives() {
        return Collections.unmodifiableList(alternatives);
    }

    private void addOptionalValue(String value) {
        if ("".equals(Strings.nullToEmpty(value).trim())) {
            return;
        }
        optionalValues.addAll(
                VocableVerificationData.create()
                        .withAbbreviationVariants(abbreviationVariants)
                        .tokenizeAndAddString(value)
                        .build()
                        .getAllValues());
    }

    private void addOptionalTerm(AbstractTerm term) {
        addOptionalValue(term.getCleanedStringWithWordStemResolved());
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

    private void addMandatoryTerm(AbstractTerm mandatoryTermData) {
        tokenizeAndAddString(mandatoryTermData.getCleanedStringWithWordStemResolved());
    }

    protected boolean isEmpty() {
        return mandatoryValues.isEmpty();
    }

    private void addMandatoryValueWithOptions(Collection<String> options) {
        Collection<String> optionsToAdd = options;
        if (abbreviationVariants != null) {
            optionsToAdd = abbreviationVariants.harmonizeAll(options);
        }
        mandatoryValues.addMandatoryValueWithAlternatives(optionsToAdd);
    }

    private VocableVerificationData tokenizeAndAddString(String valueToAdd) {
        String value = Strings.nullToEmpty(valueToAdd);
        if (abbreviationVariants != null) {
            value = abbreviationVariants.harmonizeAll(value);
        }

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

    public Set<String> getAllValues() {
        return Collections.unmodifiableSet(getAllValuesInternal());
    }

    private Set<String> getAllValuesInternal() {
        Set<String> result = new WhitespaceAndSuffixTolerantSet();

        result.addAll(mandatoryValues.getAllValues());

        result.addAll(optionalValues);
        for (VocableVerificationData data : alternatives) {
            result.addAll(data.getAllValues());
        }

        return result;
    }

    private Set<String> getAlternativesForMandatoryValue(String value) {
        return mandatoryValues.getAlternativesForValue(value);
    }

    public int getNumberOfMandatoryValues() {
        return mandatoryValues.getSize();
    }

    public void setAbbreviationVariants(SetOfAbbreviationVariants abbreviationVariants) {
        this.abbreviationVariants = abbreviationVariants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VocableVerificationData that = (VocableVerificationData) o;

        return mandatoryValues.equals(that.mandatoryValues);
    }

    @Override
    public int hashCode() {
        return mandatoryValues.hashCode();
    }

    public final static class ChooseAbbreviationVariantsBuilder {
        public DataWithMandatoryTermsBuilder withoutAbbreviationVariants() {
            return new DataWithMandatoryTermsBuilder();
        }

        public DataWithMandatoryTermsBuilder withAbbreviationVariants(SetOfAbbreviationVariants abbreviationVariants) {
            return new DataWithMandatoryTermsBuilder(abbreviationVariants);
        }
    }

    public final static class DataWithMandatoryTermsBuilder {
        private VocableVerificationData data;

        private DataWithMandatoryTermsBuilder() {
            this.data = new VocableVerificationData();
        }

        private DataWithMandatoryTermsBuilder(SetOfAbbreviationVariants abbreviationVariants) {
            this();
            data.setAbbreviationVariants(abbreviationVariants);
        }

        public DataWithMandatoryTermsBuilder addMandatoryTerm(AbstractTerm mandatoryTermData) {
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

        public DataWithMandatoryTermsBuilder addOptionalTerm(AbstractTerm term) {
            data.addOptionalTerm(term);
            return this;
        }

        public DataWithMandatoryTermsBuilder addOptionalValue(String value) {
            data.addOptionalValue(value);
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
}