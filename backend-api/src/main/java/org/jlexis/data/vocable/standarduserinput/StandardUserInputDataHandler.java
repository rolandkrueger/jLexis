/*
 * StandardUserInput.java
 * Created on 06.12.2009
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
import org.jlexis.roklib.TextFormatter;

/**
 * @author Roland Krueger
 * @version $Id: $
 */
public class StandardUserInputDataHandler extends AbstractStandardUserInputDataHandler {
    private final static String COMMENT_KEY = ".COMMENT";
    private final static String EXAMPLE_KEY = ".EXAMPLE";
    private final static String PHONETICS_KEY = ".PHONETICS";
    private final static String PRONUNCIATION_KEY = ".PRONUNCIATION";

    private String mCommentKey;
    private String mExampleKey;
    private String mPhoneticsKey;
    private String mPronunciationKey;

    public StandardUserInputDataHandler(AbstractUserInput parent, String userInputIdentifierExtension) {
        super(parent, userInputIdentifierExtension);
        mCommentKey = getUniqueIdentifier(COMMENT_KEY);
        mExampleKey = getUniqueIdentifier(EXAMPLE_KEY);
        mPhoneticsKey = getUniqueIdentifier(PHONETICS_KEY);
        mPronunciationKey = getUniqueIdentifier(PRONUNCIATION_KEY);
    }

    public StandardUserInputDataHandler(AbstractUserInput parent) {
        this(parent, null);
    }

    public String getCommentFieldKey() {
        return mCommentKey;
    }

    public String getExampleFieldKey() {
        return mExampleKey;
    }

    public String getPhoneticsFieldKey() {
        return mPhoneticsKey;
    }

    public String getPronunciationFieldKey() {
        return mPronunciationKey;
    }

    @Override
    public final String[] getUserInputIdentifiers() {
        return new String[]{mCommentKey, mExampleKey, mPhoneticsKey, mPronunciationKey};
    }

    @Override
    public final boolean isEmpty() {
        return getParent().getUserInput(mCommentKey).isEmpty() &&
                getParent().getUserInput(mExampleKey).isEmpty() &&
                getParent().getUserInput(mPhoneticsKey).isEmpty() &&
                getParent().getUserInput(mPronunciationKey).isEmpty();
    }

    public final String getComment() {
        return getParent().getUserInput(mCommentKey).getUserEnteredString();
    }

    public final void setComment(String comment) {
        getParent().addUserInput(mCommentKey, comment);
    }

    public final String getExample() {
        return getParent().getUserInput(mExampleKey).getUserEnteredString();
    }

    public final void setExample(String example) {
        getParent().addUserInput(mExampleKey, example);
    }

    public final String getPhonetics() {
        return getParent().getUserInput(mPhoneticsKey).getUserEnteredString();
    }

    public final void setPhonetics(String phonetics) {
        getParent().addUserInput(mPhoneticsKey, phonetics);
    }

    public final String getPronunciation() {
        return getParent().getUserInput(mPronunciationKey).getUserEnteredString();
    }

    public final void setPronunciation(String pronunciation) {
        getParent().addUserInput(mPronunciationKey, pronunciation);
    }

    public final boolean isExampleDefined() {
        return getParent().isDataDefinedFor(mExampleKey);
    }

    public final boolean isCommentDefined() {
        return getParent().isDataDefinedFor(mCommentKey);
    }

    public final boolean isPhoneticsDefined() {
        return getParent().isDataDefinedFor(mPhoneticsKey);
    }

    public final boolean isPronunciationDefined() {
        return getParent().isDataDefinedFor(mPronunciationKey);
    }

    @Override
    public boolean isAnyTextInputDefined() {
        return isExampleDefined() || isCommentDefined() || isPhoneticsDefined();
    }

    @Override
    public void getHTMLVersion(TextFormatter formatter, String addOn) {
        if (addOn == null) addOn = "";
        if (isCommentDefined()) {
            formatter.append("<HR size=\"1\"/>");
            // TODO: I18N
//      formatter.appendBold ("Kommentar").appendBold (addOn.equals ("") ? "" : " ");
            formatter.appendBold("Comment").appendBold(addOn.equals("") ? "" : " ");
            formatter.appendBold(addOn).appendBold(": ");
            formatter.append(getComment());
        }
        if (isExampleDefined()) {
            if (isCommentDefined()) formatter.append("<BR/>");
            // TODO: I18N
//      formatter.appendBold ("Beispiel").appendBold (addOn.equals ("") ? "" : " ");
            formatter.appendBold("Example").appendBold(addOn.equals("") ? "" : " ");
            formatter.appendBold(addOn).appendBold(": ");
            formatter.append(getExample());
        }
    }
}
