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

import org.jlexis.data.vocable.RegisteredVocableDataKey;
import org.jlexis.data.vocable.terms.AbstractTerm;
import org.jlexis.data.vocable.userinput.AbstractUserInput;
import org.jlexis.data.vocable.userinput.UserInput;
import org.jlexis.roklib.TextFormatter;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Roland Krueger
 */
public class StandardAdjectiveUserInputDecorator extends AbstractUserInputDecorator {
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

    public StandardAdjectiveUserInputDecorator(UserInput delegate, String userInputIdentifierExtension) {
        super(delegate, userInputIdentifierExtension);
        positiveKey = getUniqueIdentifier(POSITIVE_KEY);
        comparativeKey = getUniqueIdentifier(COMPARATIVE_KEY);
        superlativeKey = getUniqueIdentifier(SUPERLATIVE_KEY);
        isNotComparableKey = getUniqueIdentifier(IS_NOT_COMPARABLE_KEY);
        isIrregularKey = getUniqueIdentifier(IS_IRREGULAR_KEY);
    }

    public StandardAdjectiveUserInputDecorator(AbstractUserInput parent) {
        this(parent, null);
    }

    public List<AbstractTerm> getTermData() {
        UserInput parent = getDelegate();
        List<AbstractTerm> result = new LinkedList<>();
        if (parent.isInputDefinedFor(positiveKey)) {
            result.add(parent.getUserInput(positiveKey));
        }
        if (parent.isInputDefinedFor(comparativeKey)) {
            result.add(parent.getUserInput(comparativeKey));
        }
        if (parent.isInputDefinedFor(superlativeKey)) {
            result.add(parent.getUserInput(superlativeKey));
        }

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

    @Override
    public void init() {
        getDelegate().init();
        registerKeyForWordStem(positiveKey);
        registerKeyForInflectedTerm(positiveKey, comparativeKey);
        registerKeyForInflectedTerm(positiveKey, superlativeKey);
    }

    @Override
    public void provideShortDisplayText(TextFormatter formatter) {
        // TODO
    }

    @Override
    public void provideFullDisplayText(TextFormatter formatter) {
        // TODO
    }

    @Override
    public final boolean isEmpty() {
        return getDelegate().isEmpty() &&
                getUserInput(positiveKey).isEmpty() &&
                getUserInput(comparativeKey).isEmpty() &&
                getUserInput(superlativeKey).isEmpty();
    }

    @Override
    public boolean isAnyTextInputDefined() {
        return getDelegate().isAnyTextInputDefined() ||
                isPositiveDataDefined() ||
                isComparativeDataDefined() ||
                isSuperlativeDataDefined();
    }

    public String getPositive() {
        return getUserInput(positiveKey).getUserEnteredString();
    }

    public void setPositive(String positive) {
        getDelegate().addUserInput(positiveKey, positive);
    }

    public String getPositiveResolvedAndPurged() {
        return getUserInput(positiveKey).getCleanedStringWithWordStemResolved();
    }

    public String getComparative() {
        return getUserInput(comparativeKey).getUserEnteredString();
    }

    public void setComparative(String comparative) {
        getDelegate().addUserInput(comparativeKey, comparative);
    }

    public String getComparativeResolvedAndPurged() {
        return getUserInput(comparativeKey).getCleanedStringWithWordStemResolved();
    }

    public String getSuperlative() {
        return getUserInput(superlativeKey).getUserEnteredString();
    }

    public void setSuperlative(String superlative) {
        getDelegate().addUserInput(superlativeKey, superlative);
    }

    public String getSuperlativeResolvedAndPurged() {
        return getUserInput(superlativeKey).getCleanedStringWithWordStemResolved();
    }

    public boolean isNotComparable() {
        return getUserInput(isNotComparableKey).equals("1");
    }

    public void setNotComparable(boolean isNotComparable) {
        getDelegate().addUserInput(isNotComparableKey, isNotComparable ? "1" : "0");
    }

    public boolean isIrregular() {
        return getUserInput(isIrregularKey).equals("1");
    }

    public void setIrregular(boolean isIrregular) {
        getDelegate().addUserInput(isIrregularKey, isIrregular ? "1" : "0");
    }

    public boolean isPositiveDataDefined() {
        return getDelegate().isInputDefinedFor(positiveKey);
    }

    public boolean isComparativeDataDefined() {
        return getDelegate().isInputDefinedFor(comparativeKey);
    }

    public boolean isSuperlativeDataDefined() {
        return getDelegate().isInputDefinedFor(superlativeKey);
    }
}
