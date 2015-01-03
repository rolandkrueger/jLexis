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
import org.jlexis.data.vocable.RegisteredVocableDataKey;
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

    private RegisteredVocableDataKey commentKey;
    private RegisteredVocableDataKey exampleKey;
    private RegisteredVocableDataKey phoneticsKey;
    private RegisteredVocableDataKey pronunciationKey;

    public StandardUserInputDataHandler(AbstractUserInput parent, String userInputIdentifierExtension) {
        super(parent, userInputIdentifierExtension);
        commentKey = getUniqueIdentifier(COMMENT_KEY);
        exampleKey = getUniqueIdentifier(EXAMPLE_KEY);
        phoneticsKey = getUniqueIdentifier(PHONETICS_KEY);
        pronunciationKey = getUniqueIdentifier(PRONUNCIATION_KEY);
    }

    public StandardUserInputDataHandler(AbstractUserInput parent) {
        this(parent, null);
    }

    public RegisteredVocableDataKey getCommentFieldKey() {
        return commentKey;
    }

    public RegisteredVocableDataKey getExampleFieldKey() {
        return exampleKey;
    }

    public RegisteredVocableDataKey getPhoneticsFieldKey() {
        return phoneticsKey;
    }

    public RegisteredVocableDataKey getPronunciationFieldKey() {
        return pronunciationKey;
    }

    @Override
    public final RegisteredVocableDataKey[] getUserInputIdentifiers() {
        return new RegisteredVocableDataKey[]{commentKey, exampleKey, phoneticsKey, pronunciationKey};
    }

    @Override
    public final boolean isEmpty() {
        return getParent().getUserInput(commentKey).isEmpty() &&
                getParent().getUserInput(exampleKey).isEmpty() &&
                getParent().getUserInput(phoneticsKey).isEmpty() &&
                getParent().getUserInput(pronunciationKey).isEmpty();
    }

    public final String getComment() {
        return getParent().getUserInput(commentKey).getUserEnteredString();
    }

    public final void setComment(String comment) {
        getParent().addUserInput(commentKey, comment);
    }

    public final String getExample() {
        return getParent().getUserInput(exampleKey).getUserEnteredString();
    }

    public final void setExample(String example) {
        getParent().addUserInput(exampleKey, example);
    }

    public final String getPhonetics() {
        return getParent().getUserInput(phoneticsKey).getUserEnteredString();
    }

    public final void setPhonetics(String phonetics) {
        getParent().addUserInput(phoneticsKey, phonetics);
    }

    public final String getPronunciation() {
        return getParent().getUserInput(pronunciationKey).getUserEnteredString();
    }

    public final void setPronunciation(String pronunciation) {
        getParent().addUserInput(pronunciationKey, pronunciation);
    }

    public final boolean isExampleDefined() {
        return getParent().isInputDefinedFor(exampleKey);
    }

    public final boolean isCommentDefined() {
        return getParent().isInputDefinedFor(commentKey);
    }

    public final boolean isPhoneticsDefined() {
        return getParent().isInputDefinedFor(phoneticsKey);
    }

    public final boolean isPronunciationDefined() {
        return getParent().isInputDefinedFor(pronunciationKey);
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
