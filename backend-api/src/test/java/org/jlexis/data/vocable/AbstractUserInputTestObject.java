/*
 * AbstractUserInputTestObject.java
 * Created on 14.11.2009
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
package org.jlexis.data.vocable;


import org.jlexis.data.vocable.verification.VocableVerificationData;

/**
 * @author Roland Krueger
 */
public class AbstractUserInputTestObject extends AbstractUserInput {
    public int getCommentCalled = 0;
    public int getExampleCalled = 0;
    public int getShortVersionCalled = 0;
    public int getHTMLVersionCalled = 0;

    public AbstractUserInputTestObject() {
        super("TEST");
    }

    public void reset() {
        getCommentCalled = 0;
        getExampleCalled = 0;
        getShortVersionCalled = 0;
        getHTMLVersionCalled = 0;
    }

    @Override
    protected AbstractUserInput createNewUserInputObject() {
        return new AbstractUserInputTestObject();
    }

    @Override
    public String getComment() {
        getCommentCalled++;
        return "comment";
    }

    @Override
    public String getExample() {
        getExampleCalled++;
        return "example";
    }

    @Override
    public String getHTMLVersion() {
        getHTMLVersionCalled++;
        return "HTML";
    }

    @Override
    public VocableVerificationData getQuizVerificationData() {
        return null;
    }

    @Override
    public String getShortVersion() {
        getShortVersionCalled++;
        return "short version";
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void init() {
    }
}
