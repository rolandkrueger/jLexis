/*
 * $Id: AbstractTermDataTest.java 204 2009-12-17 15:20:16Z roland $
 * Created on 09.03.2009
 * 
 * Copyright 2007 Roland Krueger (www.rolandkrueger.info)
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
package org.jlexis.data.vocable.terms;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.*;

public abstract class AbstractTermDataTest {
    protected AbstractTermData mTestObj;

    protected String[] mNormalizedTestStrings = new String[]{
            "${-}xxx xxx${-} -x-",
            "abc${|}def",
            "xx${-}xxx ${<}abc${>}xx${$}",
            "${-} ${<} ${>} ${|}"};

    protected String[] mUserTestStrings = new String[]{
            "--xxx xxx-- -x-",
            "abc|def",
            "xx--xxx <abc>xx$",
            "${-} ${<} ${>} ${|}"};

    public abstract AbstractTermData getTestObject();

    public abstract void testGetVerificationData();

    public abstract void testIsWordStem();

    public abstract void testGetResolvedTerm();

    public abstract void testGetWordStem();

    @Before
    public void setUp() {
        mTestObj = getTestObject();
    }

    @Test
    public void testGetNormalizedTerm() {
        for (String data : mNormalizedTestStrings) {
            mTestObj.setNormalizedTerm(data);
            assertEquals(data, mTestObj.getNormalizedTerm());
        }
    }

    @Test
    public void testGetUserEnteredTerm() {
        for (String data : mUserTestStrings) {
            mTestObj.setUserEnteredTerm(data);
            assertEquals(data, mTestObj.getUserEnteredTerm());
        }
    }

    @Test
    public void testGetPurgedTerm() {
        String[] purged = new String[]{
                "-xxx xxx- -x-",
                "abcdef",
                "xx-xxx abcxx$",
                "-   "};
        for (int i = 0; i < purged.length; ++i) {
            mTestObj.setNormalizedTerm(mNormalizedTestStrings[i]);
            assertEquals(purged[i], mTestObj.getPurgedTerm());
        }
    }

    @Test(expected = NullPointerException.class)
    public void testSetUserEnteredTermFail() {
        mTestObj.setUserEnteredTerm(null);
    }

    @Test(expected = NullPointerException.class)
    public void testSetNormalizedTermFail() {
        mTestObj.setNormalizedTerm(null);
    }

    @Test
    public void testIsEmpty() {
        assertTrue(mTestObj.isEmpty());
        mTestObj.setNormalizedTerm("");
        assertTrue(mTestObj.isEmpty());
        mTestObj.setUserEnteredTerm("");
        assertTrue(mTestObj.isEmpty());

        mTestObj.setUserEnteredTerm("xxx");
        assertFalse(mTestObj.isEmpty());
    }
}
