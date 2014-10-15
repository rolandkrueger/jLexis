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
package info.rolandkrueger.jlexis.data.vocable.verification.test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import info.rolandkrueger.jlexis.data.vocable.terms.RegularTerm;
import info.rolandkrueger.jlexis.data.vocable.verification.VocableVerificationData;
import info.rolandkrueger.jlexis.data.vocable.verification.VocableVerificationResult;
import info.rolandkrueger.jlexis.data.vocable.verification.VocableVerificationResultEnum;
import info.rolandkrueger.jlexis.managers.ConfigurationManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Roland Krueger
 * @version $Id: VocableVerificationDataTest.java 204 2009-12-17 15:20:16Z roland $
 */
public class VocableVerificationDataTest
{
  private VocableVerificationData mTestObj;
  
  @Before
  public void setUp ()
  {
    mTestObj = new VocableVerificationData ();
    ConfigurationManager.getInstance ().setMandatoryTokenSplitChar (";");
    ConfigurationManager.getInstance ().setOptionalTokenSplitChar (",");
  }
  
  @Test
  public void testVerify1 ()
  {
    verify ("value1;;value2, opt1,; opt2,;A, B", "value2, opt1,; opt2,;value1,;A, B", true);
  }
  
  @Test
  public void testVerify2 ()
  {
    verify ("value1; value2, opt1, opt2; value3, A, B", "opt1, value1, opt2, A", true); 
  }
  
  @Test
  public void testVerify3 ()
  {
    verify ("value1; value2, opt1, opt2; value3, A, B", "opt1, value1, opt2", false); 
  }
  
  @Test
  public void testVerify4 ()
  {
    verify ("value1; value2, opt1, opt2; value3, A, B", "", false); 
  }
  
  @Test
  public void testVerify5 ()
  {
    verify ("value1; value2, opt1, opt2; value3, A, B", "XXX, opt1, value1, opt2, A", false); 
  }
  
  @Test
  public void testVerify_WhitespacesAreNormalized ()
  {
    verify ("value containing   whitespaces; or \t even  tabs", "value \t containing whitespaces, or even \n tabs", true);
  }
  
  private void verify (String data, String input, boolean matchExpected)
  {
    mTestObj.tokenizeAndAddString (data);
    VocableVerificationData comparison = new VocableVerificationData ();
    comparison.tokenizeAndAddString (input);
    VocableVerificationResult result = mTestObj.verify (comparison);
    if (matchExpected)
      assertEquals (VocableVerificationResultEnum.CORRECT, result.getResult ());
    else
      assertFalse (result.getResult () == VocableVerificationResultEnum.CORRECT);
  }
  
  private void verify (VocableVerificationData data, String input, boolean matchExpected)
  {
    VocableVerificationData comparison = new VocableVerificationData ();
    comparison.tokenizeAndAddString (input);
    VocableVerificationResult result = data.verify (comparison);
    if (matchExpected)
      assertEquals (VocableVerificationResultEnum.CORRECT, result.getResult ());
    else
      assertFalse (result.getResult () == VocableVerificationResultEnum.CORRECT);
  }
  
  @Test
  public void testTokenizeAndAddString_WithEmptyValues ()
  {
    mTestObj.tokenizeAndAddString ("");
    mTestObj.tokenizeAndAddString (null);
    assertEquals (0, mTestObj.getAllTokens ().size ());
  }
  
  @Test
  public void testTokenizeAndAddString ()
  {
    mTestObj.tokenizeAndAddString ("value1;;value2, opt1,,opt2,");
    mTestObj.tokenizeAndAddString ("A, B");
    
    assertEquals (6, mTestObj.getAllTokens ().size ());
    assertEquals (3, mTestObj.getMandatoryValuesWithOptions ().size ());
    
    HashSet<String> set1 = new HashSet<String> (Arrays.asList ("value1")); 
    HashSet<String> set2 = new HashSet<String> (Arrays.asList ("value2", "opt2", "opt1")); 
    HashSet<String> set3 = new HashSet<String> (Arrays.asList ("A", "B")); 
    HashSet<Set<String>> set = new HashSet<Set<String>> ();
    set.add (set1);
    set.add (set2);
    set.add (set3);
    assertEquals (set, mTestObj.getMandatoryValuesWithOptions ());
    verify (mTestObj, "value1, opt1, A", true);
    verify (mTestObj, "value1, opt1", false);
    verify (mTestObj, "opt1, B", false);
  }
  
  @Test
  public void testAddOption ()
  {
    mTestObj.makeAllOptional ();
    mTestObj.addOption ("option1");
    mTestObj.addOption ("option2");
    mTestObj.addOption ("option1");
    
    assertEquals (2, mTestObj.getAllTokens ().size ());
    assertEquals (new HashSet<String> (Arrays.asList ("option2", "option1")), mTestObj.getAllTokens ());
  }
  
  @Test
  public void testAddAlternativeTerm ()
  {
    mTestObj.tokenizeAndAddString ("XXX, YYY; AAA, BBB");
    mTestObj.addAlternativeTerm (new RegularTerm ("A, B; 1, 2"));
    
    assertEquals (2, mTestObj.getMandatoryValuesWithOptions ().size ());
    assertEquals (1, mTestObj.getAlternatives ().size ());
    assertEquals (8, mTestObj.getAllTokens ().size ());
    verify (mTestObj, "B, 1", true);
    verify (mTestObj, "A, 2, 1", true);
    verify (mTestObj, "A, 2,B, 1", true);
    verify (mTestObj, "XXX; AAA", true);
    verify (mTestObj, "BBB;XXX; AAA", true);
    verify (mTestObj, "BBB;XXX; AAA, YYY", true);
    verify (mTestObj, "XXX, B", false);
    verify (mTestObj, "AAA, 2, 1", false);
  }
  
  @Test
  public void testAddOptionalValues  ()
  {
    VocableVerificationData optional = new VocableVerificationData ();
    optional.tokenizeAndAddString ("XXX, YYY, ZZZ");
    mTestObj.tokenizeAndAddString ("A, B; 1, 2");
    mTestObj.addOptionalValues (optional);
        
    assertEquals (2, mTestObj.getMandatoryValuesWithOptions ().size ());
    assertEquals (7, mTestObj.getAllTokens ().size ());
    verify (mTestObj, "XXX, A, 1", true);
    verify (mTestObj, "XXX,ZZZ", false);
    verify (mTestObj, "XXX, A", false);
    verify (mTestObj, "B, 1", true);
    verify (mTestObj, "A, B", false);
    verify (mTestObj, "2, 1", false);
  }
  
  @Test
  public void testAddOptions ()
  {
    mTestObj.makeAllOptional ();
    mTestObj.addOptions (Arrays.asList ("A", "B", "C"));
    mTestObj.addOptions (Arrays.asList ("1", "B", "3"));
    
    assertEquals (5, mTestObj.getAllTokens ().size ());
    assertEquals (new HashSet<String> (Arrays.asList ("1", "3", "C", "B", "A")), mTestObj.getAllTokens ());
  }
  
  @Test
  public void testMakeAllOptional ()
  {
    mTestObj.addMandatoryValueWithOptions ("value1", Arrays.asList ("A", "B"));
    mTestObj.makeAllOptional ();
    mTestObj.addOption ("1");
    mTestObj.addOption ("2");
    
    assertEquals (5, mTestObj.getAllTokens ().size ());
    assertEquals (new HashSet<String> (Arrays.asList ("1", "2", "value1", "B", "A")), mTestObj.getAllTokens ());
  }
  
  @Test
  public void testGetAlternativesForMandatoryValue ()
  {
    mTestObj.addMandatoryValue ("value1");
    mTestObj.addMandatoryValueWithOptions (Arrays.asList ("value2", "opt1", "opt2"));
    mTestObj.addMandatoryValueWithOptions ("value1", Arrays.asList ("A", "B"));
    
    assertEquals (new HashSet<String> (Arrays.asList ("value1", "A", "B")), 
        mTestObj.getAlternativesForMandatoryValue ("B"));
    assertEquals (new HashSet<String> (Arrays.asList ("value2", "opt1", "opt2")), 
        mTestObj.getAlternativesForMandatoryValue ("opt1"));
  }
  
  @Test
  public void testIsEmpty ()
  {
    assertTrue (mTestObj.isEmpty ());
    mTestObj.addMandatoryValue ("test");
    assertFalse (mTestObj.isEmpty ());
  }
  
  @Test
  public void testAddMandatoryValueWithOptions ()
  {
    mTestObj.addMandatoryValueWithOptions ("value1", new String[] {"opt1", "opt2", 
                                                                   "opt1" /* repeated value */ });
    mTestObj.addMandatoryValueWithOptions (Arrays.asList ("value2", "optA"));
    mTestObj.addMandatoryValueWithOptions ("value2", Arrays.asList ("optB"));
    
    assertEquals (2, mTestObj.getMandatoryValuesWithOptions ().size ());
    assertEquals (6, mTestObj.getAllTokens ().size ());
    Iterator<Set<String>> it = mTestObj.getMandatoryValuesWithOptions ().iterator ();
    assertEquals (new HashSet<String> (Arrays.asList ("value1", "opt1", "opt2")), it.next ());
    assertEquals (new HashSet<String> (Arrays.asList ("value2", "optB", "optA")), it.next ());
  }
  
  @Test
  public void test_skipEmptStrings ()
  {
    mTestObj.addMandatoryValue ("");
    assertTrue (mTestObj.isEmpty ());
  }
  
  @Test (expected=NullPointerException.class)
  public void testAddMandatoryValue_Fail2 ()
  {
    mTestObj.addMandatoryValue (null);
  }
  
  @Test (expected=IllegalStateException.class)
  public void testAddMandatoryValue_Fail ()
  {
    mTestObj.makeAllOptional ();
    mTestObj.addMandatoryValue ("value");
  }

  @Test
  public void testAddMandatoryValue ()
  {
    mTestObj.addMandatoryValue ("value");
    mTestObj.addMandatoryValue ("string");
    mTestObj.addMandatoryValue ("value");
    
    assertEquals (2, mTestObj.getMandatoryValuesWithOptions ().size ());
    assertEquals (2, mTestObj.getAllTokens ().size ());
    for (Set<String> set : mTestObj.getMandatoryValuesWithOptions ())
    {
      assertEquals (1, set.size ());      
    }
  }
  
  @Test (expected=IllegalStateException.class)
  public void testAddMandatoryValueWithOptions_Fail ()
  {
    mTestObj.makeAllOptional ();
    mTestObj.addMandatoryValueWithOptions ("value1", new String[] {"opt1", "opt2"});
  }
  
  @Test (expected=IllegalStateException.class)
  public void testAddMandatoryValueWithOptions_Fail2 ()
  {
    mTestObj.makeAllOptional ();
    mTestObj.addMandatoryValueWithOptions (Arrays.asList ("value2", "optB", "optA"));
  }
  
  @Test (expected=NullPointerException.class)
  public void testAddMandatoryValueWithOptions_Fail3 ()
  {
    mTestObj.addMandatoryValueWithOptions (null);
  }
  
  @Test (expected=NullPointerException.class)
  public void testAddMandatoryValueWithOptions_Fail4 ()
  {
    Collection<String> c = null;
    mTestObj.addMandatoryValueWithOptions ("value", c);
  }
  
  @Test (expected=IllegalStateException.class)
  public void testAddMandatoryValueWithOptions_Fail5 ()
  {
    mTestObj.makeAllOptional ();
    Collection<String> c = new ArrayList<String> ();
    mTestObj.addMandatoryValueWithOptions ("value", c);
  }
  
  @Test (expected=IllegalStateException.class)
  public void testAddOption_Fail ()
  {
    mTestObj.addOption ("option");
  }
 
  @Test (expected=NullPointerException.class)
  public void testAddOption_Fail2 ()
  {
    mTestObj.makeAllOptional ();
    mTestObj.addOption (null);
  }
  
  @Test (expected=NullPointerException.class)
  public void testAddOptions_Fail ()
  {
    mTestObj.makeAllOptional ();
    mTestObj.addOptions (null);
  }
  
  @Test (expected=IllegalStateException.class)
  public void testAddOptions_Fail2 ()
  {
    mTestObj.addOptions (Arrays.asList ("value2", "optB", "optA"));
  }
  
  @Test 
  public void testNormalizeAbbreviations ()
  {
    mTestObj.tokenizeAndAddString ("zzz il   y  \t a zzz; xxx iya xxx, yyy i.y.a. yyy, xxxiyaxxx; ooo .i.y.a. ooo");
    mTestObj.normalizeAbbreviations (new LanguageTestImplementation ());
    
    VocableVerificationData compareObj = new VocableVerificationData ();
    compareObj.tokenizeAndAddString ("zzz  .i.y.a. zzz; xxx i.y.a. xxx, yyy   iya yyy, xxxiyaxxx; ooo il \t y  a  ooo");
    compareObj.normalizeAbbreviations (new LanguageTestImplementation ());
    
    VocableVerificationResult result = mTestObj.verify (compareObj);
    assertEquals (VocableVerificationResultEnum.CORRECT, result.getResult ());
  }
  
  @Test
  public void testSetContainsIgnoreSpecificWhitespaces ()
  {
    verify ("was?; wo? hier !", "was ?, wo  ? hier!", true); 
  }
  
  @Test
  public void testResolveParentheses ()
  {
    mTestObj = new VocableVerificationData ();
    verify ("a(b) c (d (e)) f", "ab c d (e) f", true);
    mTestObj = new VocableVerificationData ();
    verify ("a(b) c (d (e)) f", "a(b) c (d ) f", true);
    mTestObj = new VocableVerificationData ();
    verify ("Lehrer(in)", "Lehrerin", true);
    mTestObj = new VocableVerificationData ();
    verify ("Lehrer(in)", "Lehrer(in)", true);
    mTestObj = new VocableVerificationData ();
    verify ("Lehrer(in)", "Lehrer, Lehrerin, Lehrer(in)", true);
    mTestObj = new VocableVerificationData ();
    verify ("a(b) c (d (e)) f", "ab c f", true);
    mTestObj = new VocableVerificationData ();
    verify ("a(b) c (d (e)) f", "a c f", true);
    mTestObj = new VocableVerificationData ();
    verify ("a(b) c (d (e)) f", "a c d e f", true);
    mTestObj = new VocableVerificationData ();
    verify ("a(b) c (d (e)) f", "a c d (e) f", true);
  }
}
