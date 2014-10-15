/*
 * $Id: VocableDetailedViewModel.java 204 2009-12-17 15:20:16Z roland $
 * Created on 06.03.2009
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
package info.rolandkrueger.jlexis.data.guidata;

import info.rolandkrueger.jlexis.data.languages.Language;
import info.rolandkrueger.jlexis.data.vocable.UserInputInterface;
import info.rolandkrueger.jlexis.data.vocable.Vocable;

public class VocableDetailedViewModel
{
  private Vocable mVocable;
  private String  mDetailedVocableText;
  
  public VocableDetailedViewModel ()
  {
  }
  
  public void update ()
  {
    setVocable (mVocable);
  }
  
  public void setVocable (Vocable vocable)
  {
    if (vocable == null)
      throw new NullPointerException ("Vocable is null.");
    mVocable = vocable;
    mDetailedVocableText = null;
  }
  
  public String getDetailedText ()
  {
    if (mVocable == null) return "";
    
    if (mDetailedVocableText == null)
    {
      StringBuilder b = new StringBuilder ();
      b.append ("<HTML><CENTER>");
      b.append ("<TABLE width=\"66%\" align=\"center\" style=\"border-style: solid; border-width=1px\" border=\"1\">\n");
      
      b.append ("<TR>");
      for (Language language : mVocable.getLanguages ())
      {
        b.append ("<TH bgcolor=\"#3096F9\" width=\"50%\">").append (language).append ("</TH>");
      }
      b.append ("</TR>\n");
      
      b.append ("<TR>");
      for (Language language : mVocable.getLanguages ())
      {
        UserInputInterface userInput = mVocable.getVariantInput (language);
        if (userInput == null) continue;
        String detailedInputString = userInput.getHTMLVersion ();
        if (detailedInputString == null) detailedInputString = "";
        b.append ("<TD valign=\"top\">").append (detailedInputString).append ("</TD>\n");
      }      
      b.append ("</TR>");
      
      b.append ("</TABLE>");
      b.append ("</CENTER></HTML>");
      
      mDetailedVocableText = b.toString ();
    }
    
    return mDetailedVocableText;
  }

  public void clear ()
  {
    mVocable             = null;
    mDetailedVocableText = null;
  }
}
