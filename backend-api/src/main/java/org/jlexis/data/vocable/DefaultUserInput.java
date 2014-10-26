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
package org.jlexis.data.vocable;


import org.jlexis.data.vocable.standarduserinput.AbstractStandardUserInput;
import org.jlexis.data.vocable.verification.VocableVerificationData;
import org.jlexis.roklib.HTMLTextFormatter;
import org.jlexis.roklib.TextFormatter;

public class DefaultUserInput extends AbstractStandardUserInput {
    private static final String INPUT_ID = DefaultUserInput.class.getCanonicalName();
    public static final String TERM_KEY = INPUT_ID + ".TERM";

    public DefaultUserInput() {
        super(INPUT_ID);
    }

    @Override
    protected String[] getUserInputIdentifiersImpl() {
        return new String[]{TERM_KEY};
    }

    @Override
    public String getHTMLVersion() {
        TextFormatter formatter = new TextFormatter(new HTMLTextFormatter());
        formatter.appendBold(getUserData(TERM_KEY).getUserEnteredTerm());
        getStandardUserInputDataHandler().getHTMLVersion(formatter, "");
        return formatter.getFormattedText().toString();
    }

    @Override
    public String getShortVersion() {
        return getUserData(TERM_KEY).getUserEnteredTerm();
    }

    @Override
    protected boolean isEmptyImpl() {
        return getUserData(TERM_KEY).isEmpty();
    }

    @Override
    protected AbstractUserInput createNewUserInputObject() {
        return new DefaultUserInput();
    }

    @Override
    public VocableVerificationData getQuizVerificationData() {
        VocableVerificationData result = new VocableVerificationData();
        result.addMandatoryTerm(getUserData(TERM_KEY));
        return result;
    }

    @Override
    public void init() {
        // nothing to do
    }
}
