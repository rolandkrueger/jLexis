/*
 * Created on 18.05.2009
 * 
 * Copyright 2007-2009 Roland Krueger (www.rolandkrueger.info)
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
package org.jlexis.data.vocable.verification;

import org.jlexis.data.vocable.verification.VocableVerificationData.DataWithMandatoryTermsBuilder;
import org.jlexis.managers.ConfigurationManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * @author Roland Krueger
 */
public class VocableVerificationDataTest {
    private DataWithMandatoryTermsBuilder builder;

    @Before
    public void setUp() {
        builder = VocableVerificationData.createFromTerms();
        ConfigurationManager.getInstance().setMandatoryTokenSplitChar(";");
        ConfigurationManager.getInstance().setOptionalTokenSplitChar(",");
    }

//    @Test
//    public void testVerify1() {
//        verify("value1;;value2, opt1,; opt2,;A, B", "value2, opt1,; opt2,;value1,;A, B", true);
//    }
//
//    @Test
//    public void testVerify2() {
//        verify("value1; value2, opt1, opt2; value3, A, B", "opt1, value1, opt2, A", true);
//    }
//
//    @Test
//    public void testVerify3() {
//        verify("value1; value2, opt1, opt2; value3, A, B", "opt1, value1, opt2", false);
//    }
//
//    @Test
//    public void testVerify4() {
//        verify("value1; value2, opt1, opt2; value3, A, B", "", false);
//    }
//
//    @Test
//    public void testVerify5() {
//        verify("value1; value2, opt1, opt2; value3, A, B", "XXX, opt1, value1, opt2, A", false);
//    }
//
//    @Test
//    public void testVerify_WhitespacesAreNormalized() {
//        verify("value containing   whitespaces; or \t even  tabs", "value \t containing whitespaces, or even \n tabs", true);
//    }

//    private void verify(String data, String input, boolean matchExpected) {
//        builder.tokenizeAndAddString(data);
//        VocableVerificationData comparison = VocableVerificationData.createFromTerms().tokenizeAndAddString(input).build();
//        VocableVerificationResult result = builder.build().verify(comparison);
//        if (matchExpected)
//            assertEquals(VocableVerificationResultEnum.CORRECT, result.getResult());
//        else
//            assertFalse(result.getResult() == VocableVerificationResultEnum.CORRECT);
//    }
//
//    private void verify(VocableVerificationData data, String input, boolean matchExpected) {
//        VocableVerificationData comparison = VocableVerificationData
//                .createFromTerms()
//                .tokenizeAndAddString(input).build();
//        VocableVerificationResult result = data.verify(comparison);
//        if (matchExpected)
//            assertEquals(VocableVerificationResultEnum.CORRECT, result.getResult());
//        else
//            assertFalse(result.getResult() == VocableVerificationResultEnum.CORRECT);
//    }

    @Test
    public void testTokenizeAndAddString_WithEmptyValues() {
        builder.tokenizeAndAddString("");
        builder.tokenizeAndAddString(null);
        assertEquals(0, builder.build().getAllTokens().size());
    }

//    @Test
//    public void testTokenizeAndAddString() {
//        builder.tokenizeAndAddString("value1;;value2, opt1,,opt2,");
//        builder.tokenizeAndAddString("A, B");
//
//        VocableVerificationData data = builder.build();
//        assertEquals(6, data.getAllTokens().size());
//        assertEquals(3, data.getMandatoryValuesWithOptions().size());
//
//        Set<String> set1 = new HashSet<String>(Arrays.asList("value1"));
//        Set<String> set2 = new HashSet<String>(Arrays.asList("value2", "opt2", "opt1"));
//        Set<String> set3 = new HashSet<String>(Arrays.asList("A", "B"));
//        Set<Set<String>> set = new HashSet<Set<String>>();
//        set.add(set1);
//        set.add(set2);
//        set.add(set3);
//        assertThat(data.getMandatoryValuesWithOptions(), is(set));
//        verify(data, "value1, opt1, A", true);
//        verify(data, "value1, opt1", false);
//        verify(data, "opt1, B", false);
//    }

//    @Test
//    public void testAddAlternative() {
//        builder.tokenizeAndAddString("XXX, YYY; AAA, BBB");
//        builder.addAlternative(VocableVerificationData.create().fromTermData(new RegularTerm("A, B; 1, 2")).build());
//
//        VocableVerificationData data = builder.build();
//
//        assertEquals(2, data.getMandatoryValuesWithOptions().size());
//        assertEquals(1, data.getAlternatives().size());
//        assertEquals(8, data.getAllTokens().size());
//        verify(data, "B, 1", true);
//        verify(data, "A, 2, 1", true);
//        verify(data, "A, 2,B, 1", true);
//        verify(data, "XXX; AAA", true);
//        verify(data, "BBB;XXX; AAA", true);
//        verify(data, "BBB;XXX; AAA, YYY", true);
//        verify(data, "XXX, B", false);
//        verify(data, "AAA, 2, 1", false);
//    }

//    @Test
//    public void testAddOptionalValues() {
//        VocableVerificationData optional = VocableVerificationData.createFromTerms()
//                .tokenizeAndAddString("XXX, YYY, ZZZ").build();
//        builder.tokenizeAndAddString("A, B; 1, 2");
//        builder.addOptionalValues(optional);
//
//        VocableVerificationData data = builder.build();
//        assertEquals(2, data.getMandatoryValuesWithOptions().size());
//        assertEquals(7, data.getAllTokens().size());
//        verify(data, "XXX, A, 1", true);
//        verify(data, "XXX,ZZZ", false);
//        verify(data, "XXX, A", false);
//        verify(data, "B, 1", true);
//        verify(data, "A, B", false);
//        verify(data, "2, 1", false);
//    }

//    @Test
//    public void testGetAlternativesForMandatoryValue() {
//        builder.addMandatoryValue("value1");
//        builder.addMandatoryValueWithOptions(Arrays.asList("value2", "opt1", "opt2"));
//        builder.addMandatoryValueWithOptions("value1", Arrays.asList("A", "B"));
//
//        assertEquals(new HashSet<String>(Arrays.asList("value1", "A", "B")),
//                builder.getAlternativesForMandatoryValue("B"));
//        assertEquals(new HashSet<String>(Arrays.asList("value2", "opt1", "opt2")),
//                builder.getAlternativesForMandatoryValue("opt1"));
//    }
//
//    @Test
//    public void testAddMandatoryValueWithOptions() {
//        builder.addMandatoryValueWithOptions("value1", new String[]{"opt1", "opt2",
//                "opt1" /* repeated value */});
//        builder.addMandatoryValueWithOptions(Arrays.asList("value2", "optA"));
//        builder.addMandatoryValueWithOptions("value2", Arrays.asList("optB"));
//
//        assertEquals(2, builder.getMandatoryValuesWithOptions().size());
//        assertEquals(6, builder.getAllTokens().size());
//        Iterator<Set<String>> it = builder.getMandatoryValuesWithOptions().iterator();
//        assertEquals(new HashSet<String>(Arrays.asList("value1", "opt1", "opt2")), it.next());
//        assertEquals(new HashSet<String>(Arrays.asList("value2", "optB", "optA")), it.next());
//    }
//
//    @Test
//    public void test_skipEmptyStrings() {
//        builder.addMandatoryValue("");
//        assertTrue(builder.isEmpty());
//    }
//
//    @Test(expected = NullPointerException.class)
//    public void testAddMandatoryValue_Fail2() {
//        builder.addMandatoryValue(null);
//    }
//
//    @Test(expected = IllegalStateException.class)
//    public void testAddMandatoryValue_Fail() {
//        builder.makeAllOptional();
//        builder.addMandatoryValue("value");
//    }
//
//    @Test
//    public void testAddMandatoryValue() {
//        builder.addMandatoryValue("value");
//        builder.addMandatoryValue("string");
//        builder.addMandatoryValue("value");
//
//        assertEquals(2, builder.getMandatoryValuesWithOptions().size());
//        assertEquals(2, builder.getAllTokens().size());
//        for (Set<String> set : builder.getMandatoryValuesWithOptions()) {
//            assertEquals(1, set.size());
//        }
//    }

//    @Test
//    public void testNormalizeAbbreviations() {
//        builder.tokenizeAndAddString("zzz il   y  \t a zzz; xxx iya xxx, yyy i.y.a. yyy, xxxiyaxxx; ooo .i.y.a. ooo");
//        builder.normalizeAbbreviations(new Language(new PluginIdentifier("english-plugin", "1.0"), "English"));
//
//        VocableVerificationData compareObj = new VocableVerificationData();
//        compareObj.tokenizeAndAddString("zzz  .i.y.a. zzz; xxx i.y.a. xxx, yyy   iya yyy, xxxiyaxxx; ooo il \t y  a  ooo");
//        compareObj.normalizeAbbreviations(new Language(new PluginIdentifier("english-plugin", "1.0"), "English"));
//
//        VocableVerificationResult result = builder.verify(compareObj);
//        assertEquals(VocableVerificationResultEnum.CORRECT, result.getResult());
//    }
//    @Test
//    public void testSetContainsIgnoreSpecificWhitespaces() {
//        verify("was?; wo? hier !", "was ?, wo  ? hier!", true);
//    }

}
