/*
 * StandardUserInput.java
 * Created on 06.12.2009
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
package info.rolandkrueger.jlexis.data.vocable.standarduserinput;

import info.rolandkrueger.jlexis.data.vocable.AbstractUserInput;
import info.rolandkrueger.roklib.util.formatter.TextFormatter;

/**
 * @author Roland Krueger
 * @version $Id: $
 */
public class StandardUserInputDataHandler extends AbstractStandardUserInputDataHandler
{
  private final static String COMMENT_KEY       = ".COMMENT";
  private final static String EXAMPLE_KEY       = ".EXAMPLE";
  private final static String PHONETICS_KEY     = ".PHONETICS";
  private final static String PRONUNCIATION_KEY = ".PRONUNCIATION";
  
  private String mCommentKey;
  private String mExampleKey;
  private String mPhoneticsKey;
  private String mPronunciationKey;  
  
  public StandardUserInputDataHandler (AbstractUserInput parent, String userInputIdentifierExtension)
  {
    super (parent, userInputIdentifierExtension);
    mCommentKey       = getUniqueIdentifier (COMMENT_KEY);
    mExampleKey       = getUniqueIdentifier (EXAMPLE_KEY);
    mPhoneticsKey     = getUniqueIdentifier (PHONETICS_KEY);
    mPronunciationKey = getUniqueIdentifier (PRONUNCIATION_KEY);
  }
  
  public StandardUserInputDataHandler (AbstractUserInput parent)
  {
    this (parent, null);
  }

  public String getCommentFieldKey ()
  {
    return mCommentKey;
  }
  
  public String getExampleFieldKey ()
  {
    return mExampleKey;
  }
  
  public String getPhoneticsFieldKey ()
  {
    return mPhoneticsKey;
  }
  
  public String getPronunciationFieldKey ()
  {
    return mPronunciationKey;
  }
  
  @Override
  public final String[] getUserInputIdentifiers ()
  {
    return new String[] {mCommentKey, mExampleKey, mPhoneticsKey, mPronunciationKey};
  }
  
  @Override
  public final boolean isEmpty ()
  {
    return getParent ().getUserData (mCommentKey).isEmpty () &&
           getParent ().getUserData (mExampleKey).isEmpty () &&
           getParent ().getUserData (mPhoneticsKey).isEmpty () &&
           getParent ().getUserData (mPronunciationKey).isEmpty ();
  }
  
  public final String getComment ()
  {
    return getParent ().getUserData (mCommentKey).getUserEnteredTerm ();
  }
  
  public final String getExample ()
  {
    return getParent ().getUserData (mExampleKey).getUserEnteredTerm ();
  }
  
  public final String getPhonetics ()
  {
    return getParent ().getUserData (mPhoneticsKey).getUserEnteredTerm ();
  }
  
  public final String getPronunciation ()
  {
    return getParent ().getUserData (mPronunciationKey).getUserEnteredTerm ();
  }
  
  public final void setComment (String comment)
  {
    getParent ().addUserData (mCommentKey, comment);
  }
  
  public final void setExample (String example)
  {
    getParent ().addUserData (mExampleKey, example);
  }
  
  public final void setPhonetics (String phonetics)
  {
    getParent ().addUserData (mPhoneticsKey, phonetics);
  }
  
  public final void setPronunciation (String pronunciation)
  {
    getParent ().addUserData (mPronunciationKey, pronunciation);
  }
  
  public final boolean isExampleDefined ()
  {
    return getParent ().isDataDefinedFor (mExampleKey);
  }
  
  public final boolean isCommentDefined ()
  {
    return getParent ().isDataDefinedFor (mCommentKey);
  }
  
  public final boolean isPhoneticsDefined ()
  {
    return getParent ().isDataDefinedFor (mPhoneticsKey);
  }
  
  public final boolean isPronunciationDefined ()
  {
    return getParent ().isDataDefinedFor (mPronunciationKey);
  }

  @Override
  public boolean isAnyTextInputDefined ()
  {
    return isExampleDefined () || isCommentDefined () || isPhoneticsDefined ();
  }
  
  @Override
  public void getHTMLVersion (TextFormatter formatter, String addOn)
  {
    if (addOn == null) addOn = "";
    if (isCommentDefined ())
    {
      formatter.append ("<HR size=\"1\"/>");
      // TODO: I18N
//      formatter.appendBold ("Kommentar").appendBold (addOn.equals ("") ? "" : " ");
      formatter.appendBold ("Comment").appendBold (addOn.equals ("") ? "" : " ");
      formatter.appendBold (addOn).appendBold (": ");
      formatter.append (getComment ());
    }
    if (isExampleDefined ())
    {      
      if (isCommentDefined ()) formatter.append ("<BR/>");
      // TODO: I18N
//      formatter.appendBold ("Beispiel").appendBold (addOn.equals ("") ? "" : " ");
      formatter.appendBold ("Example").appendBold (addOn.equals ("") ? "" : " ");
      formatter.appendBold (addOn).appendBold (": ");
      formatter.append (getExample ());
    }
  }
}
