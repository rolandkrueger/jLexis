/*
 * $Id: WordStemTermTest.java 204 2009-12-17 15:20:16Z roland $
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
import static junit.framework.Assert.assertTrue;

public class WordStemTermTest extends AbstractTermDataTest {
    @Override
    public AbstractTermData getTestObject() {
        return new WordStemTerm();
    }

    @Override
    @Test
    public void testGetResolvedTerm() {
        for (String data : mUserTestStrings) {
            mTestObj.setUserEnteredString(data);
            assertEquals(data, mTestObj.getStringWithWordStemResolved());
        }
        for (String data : mNormalizedTestStrings) {
            mTestObj.setUserEnteredString(data);
            assertEquals(data, mTestObj.getStringWithWordStemResolved());
        }
    }

    @Override
    @Test
    public void testGetWordStem() {
        String[] words = new String[]{"abcd", "xxxx|yyy", "(aa) <zzzz>ooo"};
        String[] wordStems = new String[]{"abcd", "xxxx", "zzzz"};

        for (int i = 0; i < words.length; ++i) {
            mTestObj.setUserEnteredString(words[i]);
            assertEquals(wordStems[i], mTestObj.getWordStemString());
        }
    }

    @Override
    @Test
    public void testIsWordStem() {
        assertTrue(mTestObj.isWordStem());
    }

    @Override
    @Test
    public void testGetVerificationData() {
        String testData = "test|term; 1, 2, 3;";
        mTestObj.setUserEnteredString(testData);
        VocableVerificationData verificationData = VocableVerificationData.create().withoutAbbreviationVariants().addMandatoryTerm(mTestObj).build();
        assertEquals(2, verificationData.getNumberOfMandatoryValues());
        assertEquals(4, verificationData.getAllValues().size());
        Set<String> set1 = new HashSet<String>();
        set1.add("testterm");
        Set<String> set2 = new HashSet<String>();
        set2.add("1");
        set2.add("2");
        set2.add("3");
        VocableVerificationData comparisonObject = null;
        try {
            comparisonObject = VocableVerificationData.create()
                    .withoutAbbreviationVariants()
                    .addMandatoryValueWithOptions(set1)
                    .addMandatoryValueWithOptions(set2).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(comparisonObject, verificationData);

        testData = "this is <the> testdata";
        mTestObj.setUserEnteredString(testData);
        verificationData = VocableVerificationData.create().withoutAbbreviationVariants().addMandatoryTerm(mTestObj).build();
        assertEquals(1, verificationData.getNumberOfMandatoryValues());
        set1.clear();
        set1.add("this is the testdata");
        comparisonObject = VocableVerificationData.create()
                .withoutAbbreviationVariants()
                .addMandatoryValueWithOptions(set1).build();
        assertEquals(comparisonObject, verificationData);
    }
}