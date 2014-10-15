/*
 * $Id: NativeLanguageUserInput.java 177 2009-11-16 18:48:11Z roland $
 * Created on 07.03.2009
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
package info.rolandkrueger.jlexis.plugin.nativelanguage;

import info.rolandkrueger.jlexis.data.vocable.AbstractUserInput;
import info.rolandkrueger.jlexis.data.vocable.verification.VocableVerificationData;

public class NativeLanguageUserInput extends AbstractUserInput
{
  private static final String INPUT_ID    = NativeLanguageUserInput.class.getCanonicalName ();
  public  static final String TERM_KEY    = INPUT_ID + ".TERM";
  public  static final String EXAMPLE_KEY = INPUT_ID + ".EXAMPLE";
  public  static final String COMMENT_KEY = INPUT_ID + ".COMMENT";
  
  @Override
  protected String[] getUserInputIdentifiers ()
  {
    return new String[] {TERM_KEY, EXAMPLE_KEY, COMMENT_KEY};
  }
  
  protected NativeLanguageUserInput ()
  {
    super (INPUT_ID);
  }
  
  @Override
  public void init () {}

  @Override
  protected AbstractUserInput createNewUserInputObject ()
  {
    return new NativeLanguageUserInput ();
  }

  @Override
  protected String getHTMLVersionImpl ()
  {
    //TODO: I18N
    StringBuilder b = new StringBuilder ();
    b.append ("<B>").append (getUserData (TERM_KEY)).append ("</B>");
    if (isDataDefinedFor (COMMENT_KEY))
    {
//      b.append ("<BR/><HR size=\"1\"/>").append ("<I>Kommentar:</I><BR/>");
      b.append ("<BR/><HR size=\"1\"/>").append ("<I>Comment:</I><BR/>");
      b.append (getUserData (COMMENT_KEY));
      b.append ("<BR/>");
    }
    if (isDataDefinedFor (EXAMPLE_KEY))
    {
//      b.append ("<BR/><HR size=\"1\"/>").append ("<I>Beispiel:</I><BR/>");
      b.append ("<BR/><HR size=\"1\"/>").append ("<I>Example:</I><BR/>");
      b.append (getUserData (EXAMPLE_KEY));
    }
    return b.toString ();
  }

  @Override
  protected String getShortVersionImpl ()
  {
    return getUserData (TERM_KEY).getUserEnteredTerm ();
  }

  @Override
  public boolean isEmpty ()
  {
    return getUserData (TERM_KEY).isEmpty () &&
           getUserData (EXAMPLE_KEY).isEmpty () &&
           getUserData (COMMENT_KEY).isEmpty ();
  }

  @Override
  public VocableVerificationData getQuizVerificationData ()
  {
    VocableVerificationData result = new VocableVerificationData ();
    result.addMandatoryTerm (getUserData (TERM_KEY));
    return result;
  }

  @Override
  protected String getCommentImpl ()
  {
    return "";
  }

  @Override
  protected String getExampleImpl ()
  {
    return "";
  }
}
