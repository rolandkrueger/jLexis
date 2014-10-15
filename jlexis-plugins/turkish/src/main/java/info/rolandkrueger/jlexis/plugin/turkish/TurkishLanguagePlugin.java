/*
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
package info.rolandkrueger.jlexis.plugin.turkish;

import info.rolandkrueger.jlexis.data.vocable.AbstractWordType;
import info.rolandkrueger.jlexis.managers.JLexisResourceManager;
import info.rolandkrueger.jlexis.plugin.LanguagePlugin;
import info.rolandkrueger.jlexis.quiz.data.AbstractQuizType;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.swing.Icon;

public class TurkishLanguagePlugin extends LanguagePlugin
{
  private String mFlagIdentifier;
  
  public TurkishLanguagePlugin ()
  {
    try
    {
      mFlagIdentifier = JLexisResourceManager.getInstance ().registerIconResource (
          TurkishLanguagePlugin.class, "icons/tr.png");
    } catch (IOException e)
    {
      // TODO: error handling
      e.printStackTrace();
    }
  }
  
  @Override
  public String getLanguageName ()
  {
    // TODO: I18N
//    return "T\u00FCrkisch";
    return "Turkish";
  }

  @Override
  public Icon getIcon ()
  {
    return JLexisResourceManager.getInstance ().getIcon (mFlagIdentifier);
  }

  @Override
  protected AbstractWordType getCorrespondingWordTypeForImpl (AbstractWordType wordType)
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public AbstractWordType getDefaultWordType ()
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<AbstractQuizType> getQuizTypes ()
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Set<String>> getAbbreviationAlternatives ()
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isDefaultPlugin ()
  {
    return false;
  }
}
