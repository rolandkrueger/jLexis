/*
 * AbstractUserInputTest.java
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
package info.rolandkrueger.jlexis.data.vocable.test;

import static junit.framework.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Roland Krueger
 * @version $Id: $
 */
public class AbstractUserInputTest
{
  private AbstractUserInputTestObject mTestObj;
  
  @Before
  public void setUp ()
  {
    mTestObj = new AbstractUserInputTestObject ();
  }
  
  @Test
  public void testGetExample ()
  {
    assertEquals ("example", mTestObj.getExample ());
    assertEquals (1, mTestObj.getExampleCalled);
    assertEquals ("example", mTestObj.getExample ());
    assertEquals (1, mTestObj.getExampleCalled);
    
    // Change some data. Cache gets cleared.
    mTestObj.replace (mTestObj);
    assertEquals ("example", mTestObj.getExample ());
    assertEquals (2, mTestObj.getExampleCalled);
  }
  
  @Test
  public void testGetComment ()
  {
    assertEquals ("comment", mTestObj.getComment ());
    assertEquals (1, mTestObj.getCommentCalled);
    assertEquals ("comment", mTestObj.getComment ());
    assertEquals (1, mTestObj.getCommentCalled);
    
    // Change some data. Cache gets cleared.
    mTestObj.replace (mTestObj);
    assertEquals ("comment", mTestObj.getComment ());
    assertEquals (2, mTestObj.getCommentCalled);
  }
  
  @Test
  public void testGetHTMLVersion ()
  {
    assertEquals ("HTML", mTestObj.getHTMLVersion ());
    assertEquals (1, mTestObj.getHTMLVersionCalled);
    assertEquals ("HTML", mTestObj.getHTMLVersion ());
    assertEquals (1, mTestObj.getHTMLVersionCalled);
    
    // Change some data. Cache gets cleared.
    mTestObj.replace (mTestObj);
    assertEquals ("HTML", mTestObj.getHTMLVersion ());
    assertEquals (2, mTestObj.getHTMLVersionCalled);
  }
  
  @Test
  public void testGetShortVersion ()
  {
    assertEquals ("short version", mTestObj.getShortVersion ());
    assertEquals (1, mTestObj.getShortVersionCalled);
    assertEquals ("short version", mTestObj.getShortVersion ());
    assertEquals (1, mTestObj.getShortVersionCalled);
    
    // Change some data. Cache gets cleared.
    mTestObj.replace (mTestObj);
    assertEquals ("short version", mTestObj.getShortVersion ());
    assertEquals (2, mTestObj.getShortVersionCalled);
  }
}