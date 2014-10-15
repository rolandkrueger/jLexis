/*
 * EnglishLanguagePlugin.java
 * Created on 16.10.2009
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
package info.rolandkrueger.jlexis.plugin.english;

import info.rolandkrueger.jlexis.data.vocable.AbstractWordType;
import info.rolandkrueger.jlexis.data.vocable.WordTypeEnum;
import info.rolandkrueger.jlexis.managers.JLexisResourceManager;
import info.rolandkrueger.jlexis.plugin.LanguagePlugin;
import info.rolandkrueger.jlexis.plugin.english.wordtypes.EnglishAdjectiveWordType;
import info.rolandkrueger.jlexis.plugin.english.wordtypes.EnglishDefaultWordType;
import info.rolandkrueger.jlexis.plugin.english.wordtypes.EnglishNounWordType;
import info.rolandkrueger.jlexis.plugin.english.wordtypes.EnglishVerbWordType;
import info.rolandkrueger.jlexis.quiz.data.AbstractQuizType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.Icon;

/**
 * @author Roland Krueger
 * @version $Id: EnglishLanguagePlugin.java 193 2009-12-07 19:44:07Z roland $
 */
public class EnglishLanguagePlugin extends LanguagePlugin
{
  private static List<Set<String>> sAbbreviationAlternatives;
  
  private String mFlagGreatBritainIdentifier; 
  private String mFlagUSAIdentifier; 
  private AbstractWordType mDefault;
  private AbstractWordType mNoun;
  private AbstractWordType mAdjective;
  private AbstractWordType mVerb;
  
  static 
  {
    sAbbreviationAlternatives = new ArrayList<Set<String>> ();
    Set<String> set = new HashSet<String> ();
    set.addAll (Arrays.asList (new String[] {"something", "sth.", "sth"}));
    sAbbreviationAlternatives.add (new HashSet<String> (set));
    set.clear ();
    set.addAll (Arrays.asList (new String[] {"somebody", "sb", "sb.", "sbd", "sbd."}));
    sAbbreviationAlternatives.add (new HashSet<String> (set));
    set.clear ();
    set.addAll (Arrays.asList (new String[] {"someone", "so", "so."}));
    sAbbreviationAlternatives.add (new HashSet<String> (set));
    set.clear ();
    set.addAll (Arrays.asList (new String[] {"have not", "haven't"}));
    sAbbreviationAlternatives.add (new HashSet<String> (set));
    set.clear ();
    set.addAll (Arrays.asList (new String[] {"had not", "hadn't"}));
    sAbbreviationAlternatives.add (new HashSet<String> (set));
    set.clear ();
    set.addAll (Arrays.asList (new String[] {"are not", "aren't"}));
    sAbbreviationAlternatives.add (new HashSet<String> (set));
    set.clear ();
    set.addAll (Arrays.asList (new String[] {"is not", "isn't"}));
    sAbbreviationAlternatives.add (new HashSet<String> (set));
    set.clear ();
    set.addAll (Arrays.asList (new String[] {"do not", "don't"}));
    sAbbreviationAlternatives.add (new HashSet<String> (set));
    set.clear ();
    set.addAll (Arrays.asList (new String[] {"does not", "doesn't"}));
    sAbbreviationAlternatives.add (new HashSet<String> (set));
    set.clear ();
    set.addAll (Arrays.asList (new String[] {"has not", "hasn't"}));
    sAbbreviationAlternatives.add (new HashSet<String> (set));
    set.clear ();
    set.addAll (Arrays.asList (new String[] {"cannot", "can't"}));
    sAbbreviationAlternatives.add (new HashSet<String> (set));
    set.clear ();
    set.addAll (Arrays.asList (new String[] {"could not", "couldn't"}));
    sAbbreviationAlternatives.add (new HashSet<String> (set));
    set.clear ();
    set.addAll (Arrays.asList (new String[] {"will not", "won't"}));
    sAbbreviationAlternatives.add (new HashSet<String> (set));
    set.clear ();
    set.addAll (Arrays.asList (new String[] {"would not", "wouldn't"}));
    sAbbreviationAlternatives.add (new HashSet<String> (set));
    set.clear ();
    set.addAll (Arrays.asList (new String[] {"should not", "shouldn't"}));
    sAbbreviationAlternatives.add (new HashSet<String> (set));
    set.clear ();
    set.addAll (Arrays.asList (new String[] {"it is", "it's"}));
    sAbbreviationAlternatives.add (new HashSet<String> (set));
    set.clear ();
    set.addAll (Arrays.asList (new String[] {"was not", "wasn't"}));
    sAbbreviationAlternatives.add (new HashSet<String> (set));
    set.clear ();
    set.addAll (Arrays.asList (new String[] {"were not", "weren't"}));
    sAbbreviationAlternatives.add (new HashSet<String> (set));
    set.clear ();
    set.addAll (Arrays.asList (new String[] {"I will", "I'll"}));
    sAbbreviationAlternatives.add (new HashSet<String> (set));
    set.clear ();
    set.addAll (Arrays.asList (new String[] {"let us", "let's"}));
    sAbbreviationAlternatives.add (new HashSet<String> (set));
    set.clear ();
    set.addAll (Arrays.asList (new String[] {"we will", "we'll"}));
    sAbbreviationAlternatives.add (new HashSet<String> (set));
    set.clear ();
    set.addAll (Arrays.asList (new String[] {"you will", "you'll"}));
    sAbbreviationAlternatives.add (new HashSet<String> (set));
    set.clear ();
    set.addAll (Arrays.asList (new String[] {"they will", "they'll"}));
    sAbbreviationAlternatives.add (new HashSet<String> (set));
    set.clear ();
    set.addAll (Arrays.asList (new String[] {"you are", "you're"}));
    sAbbreviationAlternatives.add (new HashSet<String> (set));
    set.clear ();
    set.addAll (Arrays.asList (new String[] {"I am", "I'm"}));
    sAbbreviationAlternatives.add (new HashSet<String> (set));
  }
  
  public EnglishLanguagePlugin ()
  {
    try
    {
      mFlagGreatBritainIdentifier = JLexisResourceManager.getInstance ().registerIconResource (
          EnglishLanguagePlugin.class, "icons/gb.png");
      mFlagUSAIdentifier = JLexisResourceManager.getInstance ().registerIconResource (
          EnglishLanguagePlugin.class, "icons/us.png");
    } catch (IOException e)
    {
      // TODO: error handling
      e.printStackTrace();
    }
    mNoun      = new EnglishNounWordType      (this);
    mAdjective = new EnglishAdjectiveWordType (this);
    mVerb      = new EnglishVerbWordType      (this);
    mDefault   = new EnglishDefaultWordType   ();
    registerWordType (mDefault);
    registerWordType (mNoun);
    registerWordType (mAdjective);
    registerWordType (mVerb);
  }
  
  @Override
  public List<Set<String>> getAbbreviationAlternatives ()
  {
    return sAbbreviationAlternatives;
  }

  @Override
  protected AbstractWordType getCorrespondingWordTypeForImpl (AbstractWordType wordType)
  {
    if (wordType.getWordTypeEnum () == WordTypeEnum.NOUN) return mNoun;
    if (wordType.getWordTypeEnum () == WordTypeEnum.ADJECTIVE) return mAdjective;
    if (wordType.getWordTypeEnum () == WordTypeEnum.VERB) return mVerb;
    return mDefault;
  }

  @Override
  public AbstractWordType getDefaultWordType ()
  {
    return mDefault;
  }

  @Override
  public Icon getIcon ()
  {
    return JLexisResourceManager.getInstance ().getIcon (mFlagGreatBritainIdentifier);
  }
  
  public Icon getUSAFlagIcon ()
  {
    return JLexisResourceManager.getInstance ().getIcon (mFlagUSAIdentifier);
  }

  @Override
  public String getLanguageName ()
  {
    // TODO: i18n
//    return "Englisch";
    return "English";
  }

  @Override
  public List<AbstractQuizType> getQuizTypes ()
  {
    return Collections.emptyList ();
  }

  @Override
  public boolean isDefaultPlugin ()
  {
    return false;
  }
}
