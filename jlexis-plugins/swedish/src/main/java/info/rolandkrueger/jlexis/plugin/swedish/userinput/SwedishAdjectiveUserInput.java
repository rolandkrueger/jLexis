/*
 * Created on 20.11.2009.
 * 
 * Copyright 2007 Roland Krueger (www.rolandkrueger.info)
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
package info.rolandkrueger.jlexis.plugin.swedish.userinput;

import info.rolandkrueger.jlexis.data.vocable.AbstractUserInput;
import info.rolandkrueger.jlexis.data.vocable.standarduserinput.AbstractStandardUserInput;
import info.rolandkrueger.jlexis.data.vocable.standarduserinput.StandardAdjectiveUserInputDataHandler;
import info.rolandkrueger.jlexis.data.vocable.verification.VocableVerificationData;
import info.rolandkrueger.roklib.util.formatter.HTMLTextFormatter;
import info.rolandkrueger.roklib.util.formatter.TextFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SwedishAdjectiveUserInput extends AbstractStandardUserInput
{
  private static final String INPUT_ID    = SwedishAdjectiveUserInput.class.getCanonicalName ();
  private static final String NEUTRUM_KEY = INPUT_ID + ".NEUTRUM";
  private static final String PLURAL_KEY  = INPUT_ID + ".PLURAL";  
  
  private StandardAdjectiveUserInputDataHandler mStandardAdjectiveInput;
  
  public SwedishAdjectiveUserInput ()
  {
    super (INPUT_ID);
    mStandardAdjectiveInput = new StandardAdjectiveUserInputDataHandler (this);
  }
  
  public StandardAdjectiveUserInputDataHandler getStandardAdjectiveUserInput ()
  {
    return mStandardAdjectiveInput;
  }
  
  public void setNeutrumTerm (String neutrum)
  {
    addUserData (NEUTRUM_KEY, neutrum);
  }
  
  public void setPluralTerm (String plural)
  {
    addUserData (PLURAL_KEY, plural);
  }
  
  public String getUserEnteredNeutrumTerm ()
  {
    return getUserEnteredTerm (NEUTRUM_KEY);
  }
  
  public String getUserEnteredPluralTerm ()
  {
    return getUserEnteredTerm (PLURAL_KEY);
  }
  
  public String getResolvedAndPurgedNeutrumTerm ()
  {
    return getResolvedAndPurgedUserData (NEUTRUM_KEY);
  }
  
  public String getResolvedAndPurgedPluralTerm ()
  {
    return getResolvedAndPurgedUserData (PLURAL_KEY);
  }

  @Override
  protected String[] getUserInputIdentifiersImpl ()
  {
    List<String> userInputIDs = new ArrayList<String> (7);
    userInputIDs.addAll (Arrays.asList (mStandardAdjectiveInput.getUserInputIdentifiers ()));
    userInputIDs.add (NEUTRUM_KEY);
    userInputIDs.add (PLURAL_KEY);
    return userInputIDs.toArray (new String[] {});
  }

  @Override
  protected boolean isEmptyImpl ()
  {
    return mStandardAdjectiveInput.isEmpty () &&
           getUserData (NEUTRUM_KEY).isEmpty () &&
           getUserData (PLURAL_KEY).isEmpty ();
  }

  @Override
  protected AbstractUserInput createNewUserInputObject ()
  {
    return new SwedishAdjectiveUserInput ();
  }

  @Override
  protected String getHTMLVersionImpl ()
  {
    TextFormatter formatter = new TextFormatter (new HTMLTextFormatter ());
    formatter.appendBold (mStandardAdjectiveInput.getPositiveResolvedAndPurged ());
    formatter.appendItalic (" adj.");
    if (isDataDefinedFor (NEUTRUM_KEY))
    {
      formatter.append (" (").appendBold (getUserData (NEUTRUM_KEY).getResolvedAndPurgedTerm ());
    } 
    if (isDataDefinedFor (PLURAL_KEY))
    {
      if (isDataDefinedFor (NEUTRUM_KEY)) formatter.append (", ");
      else formatter.append ("(");
      formatter.appendBold (getUserData (PLURAL_KEY).getResolvedAndPurgedTerm ()).append (")");
    }
    if (mStandardAdjectiveInput.isComparativeDataDefined ())
      formatter.append (", ").appendItalic (" komp. ").appendBold (mStandardAdjectiveInput.getComparativeResolvedAndPurged ());
    if (mStandardAdjectiveInput.isSuperlativeDataDefined ())
      formatter.append (", ").appendItalic (" superl. ").appendBold (mStandardAdjectiveInput.getSuperlativeResolvedAndPurged ());     
    return formatter.getFormattedText ().toString ();
  }

  @Override
  protected String getShortVersionImpl ()
  {
    StringBuilder buf = new StringBuilder ();
    buf.append (mStandardAdjectiveInput.getPositiveResolvedAndPurged ());
    if (mStandardAdjectiveInput.isComparativeDataDefined ())
      buf.append (", ").append (mStandardAdjectiveInput.getComparativeResolvedAndPurged ());
    if (mStandardAdjectiveInput.isSuperlativeDataDefined ())
      buf.append (", ").append (mStandardAdjectiveInput.getSuperlativeResolvedAndPurged ());
    return buf.toString ();
  }
  
  @Override
  public VocableVerificationData getQuizVerificationData ()
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void init ()
  {
    mStandardAdjectiveInput.initWordStemFields ();
    addWordStemChild (mStandardAdjectiveInput.getPositiveAdjectiveFormKey (), NEUTRUM_KEY);
    addWordStemChild (mStandardAdjectiveInput.getPositiveAdjectiveFormKey (), PLURAL_KEY);
  }
}
