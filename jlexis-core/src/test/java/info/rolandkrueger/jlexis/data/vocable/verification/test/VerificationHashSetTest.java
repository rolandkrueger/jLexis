/*
 * Created on 30.09.2009
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
import info.rolandkrueger.jlexis.data.vocable.verification.VerificationHashSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Roland Krueger
 * @version $Id: VerificationHashSetTest.java 204 2009-12-17 15:20:16Z roland $
 */
public class VerificationHashSetTest
{
  private VerificationHashSet mTestObj;
  
  @Before
  public void setUp ()
  {
    mTestObj = new VerificationHashSet ();
  }
  
  @Test
  public void testSuffixTolerance ()
  {
    char[] chars = new char[] {'#', '+', '[', '*', '.', ']', '?'};
    mTestObj = new VerificationHashSet (chars);
    mTestObj.addAll (new ArrayList<String> (Arrays.asList (new String[] {
        "test", "data", "string?"
    })));
    
    assertTrue (mTestObj.contains ("test"));
    assertTrue (mTestObj.contains ("data#"));
    assertTrue (mTestObj.contains ("test]"));
    assertTrue (mTestObj.contains ("test?"));

    assertFalse (mTestObj.contains ("*test"));
    assertFalse (mTestObj.contains ("test'"));
    assertFalse (mTestObj.contains ("test-"));
  }
  
  @Test
  public void testContains ()
  {
    mTestObj.addAll (new ArrayList<String> (Arrays.asList (new String[] {
        "xxx.yyy", "wo? hier !", "string?"
    })));
    assertTrue (mTestObj.contains ("xxx.yyy"));
    assertTrue (mTestObj.contains ("xxx .  yyy"));
    assertTrue (mTestObj.contains ("xxx\t.\tyyy"));
    assertTrue (mTestObj.contains ("wo   ?hier!"));
    assertTrue (mTestObj.contains ("wo?hier!"));
    assertTrue (mTestObj.contains ("wo\t?   hier  ! \t"));
    assertTrue (mTestObj.contains ("string        ?            "));
  }
  
  @Test
  public void testRemove ()
  {
    mTestObj.addAll (new ArrayList<String> (Arrays.asList (new String[] {
        "xxx.yyy", "wo? hier !", "string?", "test"
    })));
    
    assertEquals (4, mTestObj.size ());
    mTestObj.remove ("test     ");
    mTestObj.remove ("xxx .   \t yyy ");
    mTestObj.remove ("     wo ?hier!  \t");
    mTestObj.remove ("string  ?");    
    assertTrue (mTestObj.isEmpty ());
  }
  
  @Test
  public void testIterator ()
  {
    List<String> list = new ArrayList<String> (Arrays.asList (new String[] {
        "_test_", "da.ta", "string?"
    })); 
    mTestObj.addAll (list);
    Set<String> compare = new HashSet<String> ();
    for (String s : mTestObj)
    {
      compare.add (s);
    }
    assertTrue (compare.containsAll (list));
    assertTrue (list.containsAll (compare));    
  }
  
  @Test
  public void testAdd ()
  {
    mTestObj.add ("test ? data string.");
    assertTrue (mTestObj.contains ("test ? data string."));
  }
  
  @Test
  public void testAddAll ()
  {
    List<String> setToAdd = new ArrayList<String> (Arrays.asList (new String[] {
        "test", "data", "string?"
    }));
    mTestObj.addAll (setToAdd);
    assertTrue (mTestObj.contains ("data"));
    assertTrue (mTestObj.contains ("string?"));
    assertTrue (mTestObj.contains ("test"));
  }
  
  @Test
  public void testClear ()
  {
    mTestObj.addAll (new ArrayList<String> (Arrays.asList (new String[] {
        "test", "data", "string?"
    })));
    assertEquals (3, mTestObj.size ());
    mTestObj.clear ();
    assertEquals (0, mTestObj.size ());
  }
}
