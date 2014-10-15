/*
 * SwedishNounUserInput.java
 * Created on 15.04.2007
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
import info.rolandkrueger.jlexis.data.vocable.verification.VocableVerificationData;

public class SwedishNounUserInput extends AbstractStandardUserInput
{
  private static final String INPUT_ID                = SwedishNounUserInput.class.getCanonicalName ();
  public  static final String INDEFINITE_SINGULAR_KEY = INPUT_ID + ".INDEFINITE_SINGULAR";
  public  static final String DEFINITE_SINGULAR_KEY   = INPUT_ID + ".DEFINITE_SINGULAR";
  public  static final String DEFINITE_PLURAL_KEY     = INPUT_ID + ".DEFINITE_PLURAL";
  public  static final String INDEFINITE_PLURAL_KEY   = INPUT_ID + ".INDEFINITE_PLURAL";
  public  static final String UTRUM_NEUTRUM_KEY       = INPUT_ID + ".UTRUM_NEUTRUM";
  public  static final String GROUP_KEY               = INPUT_ID + ".GROUP";
  public  static final String IRREGULAR_NOUN_KEY      = INPUT_ID + ".IRREGULAR";

  public enum Gender {UTRUM, NEUTRUM, UNKNOWN}
  public enum Group  {GROUP1, GROUP2, GROUP3, GROUP4, GROUP5, UNKNOWN}
  
  @Override
  protected String[] getUserInputIdentifiersImpl ()
  {
    return new String[] {INDEFINITE_SINGULAR_KEY, DEFINITE_SINGULAR_KEY, DEFINITE_PLURAL_KEY, 
        INDEFINITE_PLURAL_KEY, UTRUM_NEUTRUM_KEY, GROUP_KEY, IRREGULAR_NOUN_KEY};
  }
  
  public SwedishNounUserInput ()
  {
    super (INPUT_ID);
  }
  
  @Override
  public void init ()
  {
    setWordStem (INDEFINITE_SINGULAR_KEY);
    addWordStemChild (INDEFINITE_SINGULAR_KEY, DEFINITE_SINGULAR_KEY);
    addWordStemChild (INDEFINITE_SINGULAR_KEY, INDEFINITE_PLURAL_KEY);
    addWordStemChild (INDEFINITE_SINGULAR_KEY, DEFINITE_PLURAL_KEY);    
  }

  public void setGender (Gender sex)
  {
    addUserData (UTRUM_NEUTRUM_KEY, sex.toString ());
  }
  
  public Gender getGender ()
  {
    if ( ! isDataDefinedFor (UTRUM_NEUTRUM_KEY)) return Gender.UNKNOWN;
    
    Gender gender;
    try
    {
      gender = Gender.valueOf (getUserData (UTRUM_NEUTRUM_KEY).getUserEnteredTerm ());
    } catch (IllegalArgumentException iaExc)
    {
      return Gender.UNKNOWN;
    }
    return gender;
  }
  
  public Group getGroup ()
  {
    if ( ! isDataDefinedFor (GROUP_KEY)) return Group.UNKNOWN;
    
    Group group;
    try
    {
      group = Group.valueOf (getUserData (GROUP_KEY).getUserEnteredTerm ());
    } catch (IllegalArgumentException iaExc)
    {
      return Group.UNKNOWN;  
    } 
    return group;
  }
  
  public void setNounGroup (Group group)
  {
    addUserData (GROUP_KEY, group.toString ());
  }
  
  public void setIrregular (boolean irregular)
  {
    addUserData (IRREGULAR_NOUN_KEY, String.valueOf (irregular));
  }
  
  public boolean getIrregular ()
  {
    boolean result;
    try
    {
      result = Boolean.valueOf (getUserData (IRREGULAR_NOUN_KEY).getUserEnteredTerm ());      
    } catch (Exception e)
    {
      return false;
    }
    return result;
  }
  
  @Override
  protected String getHTMLVersionImpl ()
  {
    StringBuilder b = new StringBuilder ();
    b.append ("<b>");
    if (isDataDefinedFor (INDEFINITE_SINGULAR_KEY))
    {
      b.append (getUserData (INDEFINITE_SINGULAR_KEY).getPurgedTerm ());
    }
    if (isDataDefinedFor (DEFINITE_SINGULAR_KEY))
    {
      b.append (", ");
      b.append (getUserData (DEFINITE_SINGULAR_KEY).getResolvedTerm ());
    }
    if (isDataDefinedFor (INDEFINITE_PLURAL_KEY))
    {
      b.append (", ");
      b.append (getUserData (INDEFINITE_PLURAL_KEY).getResolvedTerm ());
    }
    if (isDataDefinedFor (DEFINITE_PLURAL_KEY))
    {
      b.append (", ");
      b.append (getUserData (DEFINITE_PLURAL_KEY).getResolvedTerm ());
    }
    
    b.append ("</b>").append (getPhoneticsString ());
    if (isExampleDefined ())
    {
      b.append ("<br/><hr size=\"1\"/>");
      // TODO: I18N
//      b.append ("Beispiel:<br/>").append (getExample ());
      b.append ("Example:<br/>").append (getExample ());
    }
    
    return b.toString ();
  }
  
  @Override
  protected String getShortVersionImpl ()
  {
    String indefiniteSingular = getUserData (INDEFINITE_SINGULAR_KEY).getPurgedTerm ();
    if (indefiniteSingular.equals ("")) return "";
    
    String definiteSingular = getUserData (DEFINITE_SINGULAR_KEY).getPurgedTerm ();    
    String indefinitePlural = getUserData (INDEFINITE_PLURAL_KEY).getPurgedTerm ();
    
    if (definiteSingular.equals ("") && indefinitePlural.equals ("")) return indefiniteSingular;
    
    if (definiteSingular.equals ("")) definiteSingular = "-";
    if (indefinitePlural.equals ("")) indefinitePlural = "-";
    
    return String.format ("%s, %s, %s", indefiniteSingular, definiteSingular, indefinitePlural);
  }
  
  @Override
  protected boolean isEmptyImpl ()
  {
    return getUserData (INDEFINITE_SINGULAR_KEY).isEmpty () &&
           getUserData (INDEFINITE_PLURAL_KEY).isEmpty () &&
           getUserData (DEFINITE_SINGULAR_KEY).isEmpty () &&
           getUserData (DEFINITE_PLURAL_KEY).isEmpty ();
  }

  @Override
  protected AbstractUserInput createNewUserInputObject ()
  {
    return new SwedishNounUserInput ();
  }

  @Override
  public VocableVerificationData getQuizVerificationData ()
  {
    VocableVerificationData result = new VocableVerificationData ();
    result.addMandatoryTerm   (getUserData (INDEFINITE_SINGULAR_KEY));
    result.addAlternativeTerm (getUserData (DEFINITE_SINGULAR_KEY));
    result.addOptionalTerm    (getUserData (DEFINITE_PLURAL_KEY));
    result.addOptionalTerm    (getUserData (INDEFINITE_PLURAL_KEY));
    return result;
  }
}
