/*
 * SwedishLanguagePlugin.java
 * Created on 25.03.2007
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
package info.rolandkrueger.jlexis.plugin.swedish;

import info.rolandkrueger.jlexis.data.vocable.AbstractWordType;
import info.rolandkrueger.jlexis.data.vocable.WordTypeEnum;
import info.rolandkrueger.jlexis.managers.JLexisResourceManager;
import info.rolandkrueger.jlexis.managers.JLexisResourceManager.ResourcesEnums;
import info.rolandkrueger.jlexis.plugin.LanguagePlugin;
import info.rolandkrueger.jlexis.plugin.swedish.quiztypes.NounGenderQuiz;
import info.rolandkrueger.jlexis.plugin.swedish.quiztypes.NounPluralQuiz;
import info.rolandkrueger.jlexis.plugin.swedish.wordtypes.SwedishAdjectiveWordType;
import info.rolandkrueger.jlexis.plugin.swedish.wordtypes.SwedishDefaultWordType;
import info.rolandkrueger.jlexis.plugin.swedish.wordtypes.SwedishNounWordType;
import info.rolandkrueger.jlexis.quiz.data.AbstractQuizType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.Icon;

public class SwedishLanguagePlugin extends LanguagePlugin
{
  private static List<Set<String>> sAbbreviationAlternatives;
  
  private AbstractWordType       mNoun;
  private AbstractWordType       mDefault;
  private AbstractWordType       mAdjective;
  private List<AbstractQuizType> mQuizTypes;
  
  static 
  {
    sAbbreviationAlternatives = new ArrayList<Set<String>> ();
    Set<String> set = new HashSet<String> ();
    set.addAll (Arrays.asList (new String[] {"n\u00E5gon", "ngn.", "ngn"}));
    sAbbreviationAlternatives.add (set);
    set = new HashSet<String> ();
    set.addAll (Arrays.asList (new String[] {"n\u00E5got", "ngt.", "ngt"}));
    sAbbreviationAlternatives.add (set);
    set = new HashSet<String> ();
    set.addAll (Arrays.asList (new String[] {"n\u00E5gra", "nga.", "nga"}));
    sAbbreviationAlternatives.add (set);
    set = new HashSet<String> ();
    set.addAll (Arrays.asList (new String[] {"n\u00E5gons", "ngns.", "ngns"}));
    sAbbreviationAlternatives.add (set);
  }
  
  public SwedishLanguagePlugin ()
  {
    mQuizTypes = new ArrayList<AbstractQuizType> ();
    mQuizTypes.add (new NounPluralQuiz ());
    mQuizTypes.add (new NounGenderQuiz    ());
    mDefault   = new SwedishDefaultWordType ();
    mNoun      = new SwedishNounWordType    ();
    mAdjective = new SwedishAdjectiveWordType ();
    registerWordType (mNoun);
    registerWordType (mAdjective);
    registerWordType (mDefault);
  }

  @Override
  public List<Set<String>> getAbbreviationAlternatives ()
  {
    return sAbbreviationAlternatives;
  }
  
  @Override
  public AbstractWordType getDefaultWordType ()
  {    
    return mDefault;
  }

  @Override
  public Icon getIcon ()
  {
    return JLexisResourceManager.getInstance ().getIcon (ResourcesEnums.SWEDISH_FLAG);
  }

  @Override
  protected AbstractWordType getCorrespondingWordTypeForImpl (AbstractWordType wordType)
  {
    if (wordType.getWordTypeEnum () == WordTypeEnum.NOUN)    return mNoun;
    if (wordType.getWordTypeEnum () == WordTypeEnum.ADJECTIVE) return mAdjective;
    return mDefault;
  }

  @Override
  public String getLanguageName ()
  {
    // TODO: i18n
//    return "Schwedisch";
    return "Swedish";
  }

  @Override
  public boolean isDefaultPlugin ()
  {
    return false;
  }
  
  @Override
  public List<AbstractQuizType> getQuizTypes ()
  {
    return mQuizTypes;
  }
}
