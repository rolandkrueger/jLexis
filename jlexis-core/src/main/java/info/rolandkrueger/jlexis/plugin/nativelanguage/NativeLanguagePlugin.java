/*
 * $Id: NativeLanguagePlugin.java 204 2009-12-17 15:20:16Z roland $
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

import info.rolandkrueger.jlexis.data.vocable.AbstractWordType;
import info.rolandkrueger.jlexis.managers.JLexisResourceManager;
import info.rolandkrueger.jlexis.managers.JLexisResourceManager.ResourcesEnums;
import info.rolandkrueger.jlexis.plugin.LanguagePlugin;
import info.rolandkrueger.jlexis.quiz.data.AbstractQuizType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.Icon;

public class NativeLanguagePlugin extends LanguagePlugin
{
  private static List<Set<String>> sAbbreviationAlternatives;
  
  static 
  {
    // TODO: externalize in resource file
    sAbbreviationAlternatives = new ArrayList<Set<String>> ();
    Set<String> set = new HashSet<String> ();
    set.addAll (Arrays.asList (new String[] {"etwas", "etw.", "etw"}));
    sAbbreviationAlternatives.add (set);
    set = new HashSet<String> ();
    set.addAll (Arrays.asList (new String[] {"jemanden", "jdn.", "jdn"}));
    sAbbreviationAlternatives.add (set);
    set = new HashSet<String> ();
    set.addAll (Arrays.asList (new String[] {"jemandem", "jdm.", "jdm"}));
    sAbbreviationAlternatives.add (set);
    set = new HashSet<String> ();
    set.addAll (Arrays.asList (new String[] {"sich", "s.", "s"}));
    sAbbreviationAlternatives.add (set);
    set = new HashSet<String> ();
    set.addAll (Arrays.asList (new String[] {"und", "u.", "+"}));
    sAbbreviationAlternatives.add (set);
  }
  
  private AbstractWordType mWordType;
  
  public NativeLanguagePlugin ()
  {
    setIdentifier ("NativeLanguagePlugin");
    setVersionNumber (1);
    setVersionString ("1");
    mWordType = new NativeLanguageWordType ();
    registerWordType (mWordType);
  }
  
  @Override
  protected AbstractWordType getCorrespondingWordTypeForImpl (AbstractWordType wordType)
  {
    return mWordType;
  }

  @Override
  public AbstractWordType getDefaultWordType ()
  {
    return mWordType;
  }

  @Override
  public Icon getIcon ()
  {
    return JLexisResourceManager.getInstance ().getIcon (ResourcesEnums.EMPTY);
  }

  @Override
  public String getLanguageName ()
  {
    // TODO: I18N
//    return "Muttersprache";
    return "Mother tongue";
  }

  @Override
  public boolean isDefaultPlugin ()
  {
    return false;
  }

  @Override
  public List<AbstractQuizType> getQuizTypes ()
  {
    return Collections.emptyList ();
  }

  @Override
  public List<Set<String>> getAbbreviationAlternatives ()
  {
    // TODO: externalize this in a translation resource file
    return Collections.emptyList ();
  }
}
