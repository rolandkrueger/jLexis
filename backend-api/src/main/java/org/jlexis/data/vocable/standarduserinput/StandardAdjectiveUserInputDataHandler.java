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
package org.jlexis.data.vocable.standarduserinput;

import org.jlexis.data.vocable.AbstractUserInput;
import org.jlexis.data.vocable.terms.TermDataInterface;
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

    private String mPositiveKey;
    private String mComparativeKey;
    private String mSuperlativeKey;
    private String mIsNotComparableKey;
    private String mIsIrregularKey;

    public StandardAdjectiveUserInputDataHandler(AbstractUserInput parent, String userInputIdentifierExtension) {
        super(parent, userInputIdentifierExtension);
        mPositiveKey = getUniqueIdentifier(POSITIVE_KEY);
        mComparativeKey = getUniqueIdentifier(COMPARATIVE_KEY);
        mSuperlativeKey = getUniqueIdentifier(SUPERLATIVE_KEY);
        mIsNotComparableKey = getUniqueIdentifier(IS_NOT_COMPARABLE_KEY);
        mIsIrregularKey = getUniqueIdentifier(IS_IRREGULAR_KEY);
    }

    public StandardAdjectiveUserInputDataHandler(AbstractUserInput parent) {
        this(parent, null);
    }

    public List<TermDataInterface> getTermData() {
        AbstractUserInput parent = getParent();
        List<TermDataInterface> result = new LinkedList<TermDataInterface>();
        if (parent.isDataDefinedFor(mPositiveKey))
            result.add(parent.getUserData(mPositiveKey));
        if (parent.isDataDefinedFor(mComparativeKey))
            result.add(parent.getUserData(mComparativeKey));
        if (parent.isDataDefinedFor(mSuperlativeKey))
            result.add(parent.getUserData(mSuperlativeKey));

        return result;
    }

    public String getPositiveAdjectiveFormKey() {
        return mPositiveKey;
    }

    public String getComparativeAdjectiveFormKey() {
        return mComparativeKey;
    }

    public String getSuperlativeAdjectiveFormKey() {
        return mSuperlativeKey;
    }

    public void initWordStemFields() {
        AbstractUserInput parent = getParent();
        parent.setWordStem(mPositiveKey);
        parent.addWordStemChild(mPositiveKey, mComparativeKey);
        parent.addWordStemChild(mPositiveKey, mSuperlativeKey);
    }

    @Override
    public final String[] getUserInputIdentifiers() {
        return new String[]{mPositiveKey, mComparativeKey, mSuperlativeKey, mIsNotComparableKey, mIsIrregularKey};
    }

    @Override
    public final boolean isEmpty() {
        AbstractUserInput parent = getParent();
        return parent.getUserData(mPositiveKey).isEmpty() &&
                parent.getUserData(mComparativeKey).isEmpty() &&
                parent.getUserData(mSuperlativeKey).isEmpty();
    }

    public String getPositive() {
        return getParent().getUserEnteredTerm(mPositiveKey);
    }

    public void setPositive(String positive) {
        getParent().addUserData(mPositiveKey, positive);
    }

    public String getPositiveResolvedAndPurged() {
        return getParent().getResolvedAndPurgedUserData(mPositiveKey);
    }

    public String getComparative() {
        return getParent().getUserEnteredTerm(mComparativeKey);
    }

    public void setComparative(String comparative) {
        getParent().addUserData(mComparativeKey, comparative);
    }

    public String getComparativeResolvedAndPurged() {
        return getParent().getResolvedAndPurgedUserData(mComparativeKey);
    }

    public String getSuperlative() {
        return getParent().getUserEnteredTerm(mSuperlativeKey);
    }

    public void setSuperlative(String superlative) {
        getParent().addUserData(mSuperlativeKey, superlative);
    }

    public String getSuperlativeResolvedAndPurged() {
        return getParent().getResolvedAndPurgedUserData(mSuperlativeKey);
    }

    public boolean isNotComparable() {
        return getParent().getUserEnteredTerm(mIsNotComparableKey).equals("1");
    }

    public void setNotComparable(boolean isNotComparable) {
        getParent().addUserData(mIsNotComparableKey, isNotComparable ? "1" : "0");
    }

    public boolean isIrregular() {
        return getParent().getUserEnteredTerm(mIsIrregularKey).equals("1");
    }

    public void setIrregular(boolean isIrregular) {
        getParent().addUserData(mIsIrregularKey, isIrregular ? "1" : "0");
    }

    public boolean isPositiveDataDefined() {
        return getParent().isDataDefinedFor(mPositiveKey);
    }

    public boolean isComparativeDataDefined() {
        return getParent().isDataDefinedFor(mComparativeKey);
    }

    public boolean isSuperlativeDataDefined() {
        return getParent().isDataDefinedFor(mSuperlativeKey);
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
