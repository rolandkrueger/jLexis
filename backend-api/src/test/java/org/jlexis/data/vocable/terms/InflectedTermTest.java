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

public class InflectedTermTest extends AbstractTermTest {
    private WordStemTerm mStem;

    @Override
    public AbstractTerm getTestObject() {
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
            mStem.setUserEnteredString(words[i]);
            mTestObj.setUserEnteredString(inflected[i]);
            assertEquals(resolved[i], mTestObj.getStringWithWordStemResolved());
        }
    }

    @Override
    @Test(expected = UnsupportedOperationException.class)
    public void testGetWordStem() {
        mTestObj.getWordStemString();
    }

    @Override
    @Test
    public void testIsWordStem() {
        assertFalse(mTestObj.isWordStem());
    }

    @Override
    @Test
    public void testGetVerificationData() {
        mStem.setUserEnteredString("test|term");
        mTestObj.setUserEnteredString("--value; --vocable, word");
        VocableVerificationData verificationData = VocableVerificationData.create().withoutAbbreviationVariants().addMandatoryTerm(mTestObj).build();
        assertEquals(2, verificationData.getNumberOfMandatoryValues());
        assertEquals(5, verificationData.getAllValues().size());
        Set<String> set1 = new HashSet<String>();
        set1.add("testvalue");
        set1.add("-value");
        Set<String> set2 = new HashSet<String>();
        set2.add("testvocable");
        set2.add("word");
        set2.add("-vocable");
        VocableVerificationData comparisonObject = VocableVerificationData.create()
                .withoutAbbreviationVariants()
                .addMandatoryValueWithOptions(set1)
                .addMandatoryValueWithOptions(set2).build();
        assertEquals(comparisonObject, verificationData);
    }
}
