/*
 * $Id: InflectedTermTest.java 204 2009-12-17 15:20:16Z roland $
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

import org.jlexis.data.vocable.verification.VocableVerificationData;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

public class InflectedTermTest extends AbstractTermDataTest {
    private WordStemTerm mStem;

    @Override
    public AbstractTermData getTestObject() {
        mStem = new WordStemTerm();
        return new InflectedTerm(mStem);
    }

    @Override
    @Test
    public void testGetResolvedTerm() {
        String[] words = new String[]{"abcd", "xxxx|yyy", "(aa) <zzzz>ooo"};
        String[] inflected = new String[]{"--xyz", "--ooo $", "vvv--vvv"};
        String[] resolved = new String[]{"abcdxyz", "xxxxooo $", "vvvzzzzvvv"};

        for (int i = 0; i < words.length; ++i) {
            mStem.setUserEnteredTerm(words[i]);
            mTestObj.setUserEnteredTerm(inflected[i]);
            assertEquals(resolved[i], mTestObj.getResolvedTerm());
        }
    }

    @Override
    @Test(expected = UnsupportedOperationException.class)
    public void testGetWordStem() {
        mTestObj.getWordStem();
    }

    @Override
    @Test
    public void testIsWordStem() {
        assertFalse(mTestObj.isWordStem());
    }

    @Override
    @Test
    public void testGetVerificationData() {
        mStem.setUserEnteredTerm("test|term");
        mTestObj.setUserEnteredTerm("--value; --vocable, word");
        VocableVerificationData verificationData = mTestObj.getVerificationData();
        assertEquals(2, verificationData.getMandatoryValuesWithOptions().size());
        assertEquals(5, verificationData.getAllTokens().size());
        Set<String> set1 = new HashSet<String>();
        set1.add("testvalue");
        set1.add("-value");
        Set<String> set2 = new HashSet<String>();
        set2.add("testvocable");
        set2.add("word");
        set2.add("-vocable");
        VocableVerificationData comparisonObject = new VocableVerificationData();
        comparisonObject.addMandatoryValueWithOptions(set1);
        comparisonObject.addMandatoryValueWithOptions(set2);
        assertEquals(comparisonObject, verificationData);
    }
}
