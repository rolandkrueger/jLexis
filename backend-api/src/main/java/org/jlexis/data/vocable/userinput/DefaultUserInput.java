/*
 * DefaultUserInput.java
 * Created on 03.04.2007
 * 
 * Copyright 2007 Roland Krueger (www.rolandkrueger.info)
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
package org.jlexis.data.vocable.userinput;


import org.jlexis.data.vocable.RegisteredVocableDataKey;
import org.jlexis.data.vocable.verification.VocableVerificationData;
import org.jlexis.roklib.TextFormatter;

/**
 * User input class that manages a single, simple term. This is typically used for entering the translation of a
 * vocable into the user's native language.
 */
public class DefaultUserInput extends AbstractUserInput {
    private static final String INPUT_ID = DefaultUserInput.class.getCanonicalName();
    public static final RegisteredVocableDataKey TERM_KEY = new RegisteredVocableDataKey(INPUT_ID + ".TERM");

    public DefaultUserInput() {
        super(INPUT_ID);
    }

    @Override
    public void provideFullDisplayText(TextFormatter formatter) {
        formatter.appendBold(getUserInput(TERM_KEY).getUserEnteredString());
        // FIXME: result for old signature
//        return formatter.getFormattedText().toString();
    }

    @Override
    public void provideShortDisplayText(TextFormatter formatter) {
        // FIXME: result for old signature
//        return getUserInput(TERM_KEY).getUserEnteredString();
    }

    @Override
    public boolean isEmpty() {
        return getUserInput(TERM_KEY).isEmpty();
    }

    @Override
    public AbstractUserInput createUserInputObject() {
        return new DefaultUserInput();
    }

    @Override
    public VocableVerificationData getQuizVerificationData() {
        return VocableVerificationData.create()
                .withoutAbbreviationVariants()
                .addMandatoryTerm(getUserInput(TERM_KEY))
                .build();
    }

    @Override
    public void init() {
        // nothing to do
    }
}
