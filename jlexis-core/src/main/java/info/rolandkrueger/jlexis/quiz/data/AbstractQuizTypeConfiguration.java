/*
 * Created on 29.03.2009
 * 
 * Copyright 2007-2009 Roland Krueger (www.rolandkrueger.info)
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
package info.rolandkrueger.jlexis.quiz.data;

import info.rolandkrueger.jlexis.data.languages.Language;
import info.rolandkrueger.jlexis.data.units.UnmodifiableLearningUnit;
import info.rolandkrueger.roklib.util.data.ManagedData;
import info.rolandkrueger.roklib.util.helper.CheckForNull;

import java.util.Collections;
import java.util.List;

/**
 * @author Roland Krueger
 * @version $Id: AbstractQuizTypeConfiguration.java 197 2009-12-14 07:27:08Z roland $
 */
public abstract class AbstractQuizTypeConfiguration
{
  private ManagedData<Language>   mLanguage;
  private List<AbstractQuizQuestion> mQuizQuestions;
  private AbstractQuizType           mQuizType;
  
  protected AbstractQuizTypeConfiguration (AbstractQuizType type)
  {
    CheckForNull.check (type);
    mLanguage = new ManagedData<Language> ();
    mQuizType = type;
  }
  
  protected abstract List<AbstractQuizQuestion> createQuizQuestionsForImpl (List<UnmodifiableLearningUnit> units, Language forLanguage);
  public abstract String getDescription ();
  public abstract AbstractQuizTypeConfiguration getCopy ();
  
  public final AbstractQuizType getQuizType ()
  {
    return mQuizType;
  }
  
  public final void createQuizQuestionsFor (List<UnmodifiableLearningUnit> units, Language forLanguage)
  {
    mQuizQuestions = null;
    mQuizQuestions = createQuizQuestionsForImpl (units, forLanguage);
  }
  
  public final void setCorrespondingLanguage (Language lang)
  {
    if (lang == null) return;
    mLanguage.setValue (lang);
  }
  
  public final List<AbstractQuizQuestion> getQuizQuestions ()
  {
    if (mQuizQuestions == null) return Collections.emptyList ();
    return mQuizQuestions;
  }
  
  public final ManagedData<Language> getCorrespondingLanguage ()
  {
    return mLanguage;
  }
}
