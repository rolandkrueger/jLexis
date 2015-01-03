/*
 * StandardAdjectiveUserInputDataHandler.java
 * Created on 20.11.2009
 * 
 * Copyright Roland Krueger (www.rolandkrueger.info)
 * 
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
package org.jlexis.data.vocable.userinput.standard;

import org.jlexis.data.vocable.userinput.AbstractUserInput;
import org.jlexis.data.vocable.RegisteredVocableDataKey;
import org.jlexis.data.vocable.terms.AbstractTerm;
import org.jlexis.roklib.TextFormatter;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Roland Krueger
 * @version $Id: $
 */
public class StandardAdjectiveUserInputDataHandler extends AbstractStandardUserInputDataHandler {
    private final static String POSITIVE_KEY = ".ADJECTIVE_POSITIVE";
    private final static String COMPARATIVE_KEY = ".ADJECTIVE_COMPARATIVE";
    private final static String SUPERLATIVE_KEY = ".ADJECTIVE_SUPERLATIVE";
    private final static String IS_NOT_COMPARABLE_KEY = ".ADJECTIVE_IS_NOT_COMPARABLE";
    private final static String IS_IRREGULAR_KEY = ".ADJECTIVE_IS_IRREGULAR";

    private RegisteredVocableDataKey positiveKey;
    private RegisteredVocableDataKey comparativeKey;
    private RegisteredVocableDataKey superlativeKey;
    private RegisteredVocableDataKey isNotComparableKey;
    private RegisteredVocableDataKey isIrregularKey;

    public StandardAdjectiveUserInputDataHandler(AbstractUserInput parent, String userInputIdentifierExtension) {
        super(parent, userInputIdentifierExtension);
        positiveKey = getUniqueIdentifier(POSITIVE_KEY);
        comparativeKey = getUniqueIdentifier(COMPARATIVE_KEY);
        superlativeKey = getUniqueIdentifier(SUPERLATIVE_KEY);
        isNotComparableKey = getUniqueIdentifier(IS_NOT_COMPARABLE_KEY);
        isIrregularKey = getUniqueIdentifier(IS_IRREGULAR_KEY);
    }

    public StandardAdjectiveUserInputDataHandler(AbstractUserInput parent) {
        this(parent, null);
    }

    public List<AbstractTerm> getTermData() {
        AbstractUserInput parent = getParent();
        List<AbstractTerm> result = new LinkedList<>();
        if (parent.isInputDefinedFor(positiveKey))
            result.add(parent.getUserInput(positiveKey));
        if (parent.isInputDefinedFor(comparativeKey))
            result.add(parent.getUserInput(comparativeKey));
        if (parent.isInputDefinedFor(superlativeKey))
            result.add(parent.getUserInput(superlativeKey));

        return result;
    }

    public RegisteredVocableDataKey getPositiveAdjectiveFormKey() {
        return positiveKey;
    }

    public RegisteredVocableDataKey getComparativeAdjectiveFormKey() {
        return comparativeKey;
    }

    public RegisteredVocableDataKey getSuperlativeAdjectiveFormKey() {
        return superlativeKey;
    }

    public void initWordStemFields() {
        AbstractUserInput parent = getParent();
        parent.setWordStem(positiveKey);
        parent.addWordStemChild(positiveKey, comparativeKey);
        parent.addWordStemChild(positiveKey, superlativeKey);
    }

    @Override
    public final RegisteredVocableDataKey[] getUserInputIdentifiers() {
        return new RegisteredVocableDataKey[]{positiveKey, comparativeKey, superlativeKey, isNotComparableKey, isIrregularKey};
    }

    @Override
    public final boolean isEmpty() {
        AbstractUserInput parent = getParent();
        return parent.getUserInput(positiveKey).isEmpty() &&
                parent.getUserInput(comparativeKey).isEmpty() &&
                parent.getUserInput(superlativeKey).isEmpty();
    }

    public String getPositive() {
        return getParent().getUserInput(positiveKey).getUserEnteredString();
    }

    public void setPositive(String positive) {
        getParent().addUserInput(positiveKey, positive);
    }

    public String getPositiveResolvedAndPurged() {
        return getParent().getUserInput(positiveKey).getCleanedStringWithWordStemResolved();
    }

    public String getComparative() {
        return getParent().getUserInput(comparativeKey).getUserEnteredString();
    }

    public void setComparative(String comparative) {
        getParent().addUserInput(comparativeKey, comparative);
    }

    public String getComparativeResolvedAndPurged() {
        return getParent().getUserInput(comparativeKey).getCleanedStringWithWordStemResolved();
    }

    public String getSuperlative() {
        return getParent().getUserInput(superlativeKey).getUserEnteredString();
    }

    public void setSuperlative(String superlative) {
        getParent().addUserInput(superlativeKey, superlative);
    }

    public String getSuperlativeResolvedAndPurged() {
        return getParent().getUserInput(superlativeKey).getCleanedStringWithWordStemResolved();
    }

    public boolean isNotComparable() {
        return getParent().getUserInput(isNotComparableKey).equals("1");
    }

    public void setNotComparable(boolean isNotComparable) {
        getParent().addUserInput(isNotComparableKey, isNotComparable ? "1" : "0");
    }

    public boolean isIrregular() {
        return getParent().getUserInput(isIrregularKey).equals("1");
    }

    public void setIrregular(boolean isIrregular) {
        getParent().addUserInput(isIrregularKey, isIrregular ? "1" : "0");
    }

    public boolean isPositiveDataDefined() {
        return getParent().isInputDefinedFor(positiveKey);
    }

    public boolean isComparativeDataDefined() {
        return getParent().isInputDefinedFor(comparativeKey);
    }

    public boolean isSuperlativeDataDefined() {
        return getParent().isInputDefinedFor(superlativeKey);
    }

    @Override
    public boolean isAnyTextInputDefined() {
        return isPositiveDataDefined() || isComparativeDataDefined() || isSuperlativeDataDefined();
    }

    public void getHTMLVersion(TextFormatter formatter, String addOn) {
        // TODO implement
        throw new RuntimeException("not yet implemented");
    }
}
