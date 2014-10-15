/*
 * Created on 29.05.2009.
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

import info.rolandkrueger.jlexis.data.units.LearningUnit;

import java.util.Collections;
import java.util.List;

/**
 * @author Roland Krueger
 * @version $Id: GeneralQuizConfiguration.java 134 2009-07-06 19:33:01Z roland $
 */
public class GeneralQuizConfiguration
{
  public enum QuestionOrderEnum             {CORRECT_ORDER, RANDOM};
  public enum QuestionRepetitionEnum        {FIXED, ONCE, ENDLESS};
  public enum IncorrectAnswerRepetitionEnum {FIXED, ONCE, NONE};
  public enum WhenToRepeatIncorrectAnswer   {IMMEDIATELY, LATER};
  public enum ShowCorrectAnswer             {ALWAYS, ONLY_WHEN_INCORRECT};
  public enum QuestionStrictness            {ALL, SOME};
  
  private QuestionOrderEnum mOrder;
  private int mRepeatQuestionsCount;
  private QuestionRepetitionEnum mQuestionRepetition;
  private IncorrectAnswerRepetitionEnum mIncorrectAnswerRepetition;
  private int mRepeatIncorrectAnswerCount;
  private WhenToRepeatIncorrectAnswer mWhenToRepeat;
  private boolean mDoMixinUnits;
  private int mMixinPercentage;
  private boolean mMixinAllUnits;
  private List<LearningUnit> mMixinUnits;
  private ShowCorrectAnswer mShowCorrectAnswer;
  private boolean mShowComments;
  private boolean mShowExample;
  private QuestionStrictness mQuestionStrictness;
  
  public GeneralQuizConfiguration ()
  {
    mOrder = QuestionOrderEnum.CORRECT_ORDER;
    mRepeatQuestionsCount = 1;
    mQuestionRepetition = QuestionRepetitionEnum.FIXED;
    mIncorrectAnswerRepetition = IncorrectAnswerRepetitionEnum.FIXED;
    mRepeatIncorrectAnswerCount = 1;
    mWhenToRepeat = WhenToRepeatIncorrectAnswer.IMMEDIATELY;
    mDoMixinUnits = false;
    mMixinPercentage = 25;    
    mMixinAllUnits = true;
    mShowCorrectAnswer = ShowCorrectAnswer.ONLY_WHEN_INCORRECT;  
    mShowComments = true;
    mShowExample = true;
    mQuestionStrictness = QuestionStrictness.ALL;
  }
  
  public QuestionOrderEnum getOrder ()
  {
    return mOrder;
  }

  public void setOrder (QuestionOrderEnum order)
  {
    mOrder = order;
  }

  public int getRepeatQuestionsCount ()
  {
    return mRepeatQuestionsCount;
  }

  public void setRepeatQuestionsCount (int repeatQuestionsCount)
  {
    mRepeatQuestionsCount = repeatQuestionsCount;
  }

  public QuestionRepetitionEnum getQuestionRepetition ()
  {
    return mQuestionRepetition;
  }

  public void setQuestionRepetition (QuestionRepetitionEnum questionRepetition)
  {
    mQuestionRepetition = questionRepetition;
    if (questionRepetition == QuestionRepetitionEnum.ONCE)
      setRepeatQuestionsCount (1);
  }

  public IncorrectAnswerRepetitionEnum getIncorrectAnswerRepetition ()
  {
    return mIncorrectAnswerRepetition;
  }

  public void setIncorrectAnswerRepetition (IncorrectAnswerRepetitionEnum incorrectAnswerRepetition)
  {
    mIncorrectAnswerRepetition = incorrectAnswerRepetition;
    if (incorrectAnswerRepetition == IncorrectAnswerRepetitionEnum.ONCE)
      setRepeatIncorrectAnswerCount (1);
  }

  public int getRepeatIncorrectAnswerCount ()
  {
    return mRepeatIncorrectAnswerCount;
  }

  public void setRepeatIncorrectAnswerCount (int repeatIncorrectAnswerCount)
  {
    mRepeatIncorrectAnswerCount = repeatIncorrectAnswerCount;
  }

  public WhenToRepeatIncorrectAnswer getWhenToRepeat ()
  {
    return mWhenToRepeat;
  }

  public void setWhenToRepeat (WhenToRepeatIncorrectAnswer whenToRepeat)
  {
    mWhenToRepeat = whenToRepeat;
  }

  public boolean isDoMixinUnits ()
  {
    return mDoMixinUnits;
  }

  public void setDoMixinUnits (boolean doMixinUnits)
  {
    mDoMixinUnits = doMixinUnits;
  }

  public int getMixinPercentage ()
  {
    return mMixinPercentage;
  }

  public void setMixinPercentage (int mixinPercentage)
  {
    mMixinPercentage = mixinPercentage;
  }

  public boolean isMixinAllUnits ()
  {
    return mMixinAllUnits;
  }

  public void setMixinAllUnits (boolean mixinAllUnits)
  {
    mMixinAllUnits = mixinAllUnits;
  }

  public List<LearningUnit> getMixinUnits ()
  {
    if ( ! isDoMixinUnits () || mMixinUnits == null) 
      return Collections.emptyList ();
    return mMixinUnits;
  }

  public void setMixinUnits (List<LearningUnit> mixinUnits)
  {
    mMixinUnits = mixinUnits;
  }

  public ShowCorrectAnswer getShowCorrectAnswer ()
  {
    return mShowCorrectAnswer;
  }

  public void setShowCorrectAnswer (ShowCorrectAnswer showCorrectAnswer)
  {
    mShowCorrectAnswer = showCorrectAnswer;
  }

  public boolean isShowComments ()
  {
    return mShowComments;
  }

  public void setShowComments (boolean showComments)
  {
    mShowComments = showComments;
  }

  public boolean isShowExample ()
  {
    return mShowExample;
  }

  public void setShowExample (boolean showExample)
  {
    mShowExample = showExample;
  }

  public QuestionStrictness getQuestionStrictness ()
  {
    return mQuestionStrictness;
  }

  public void setQuestionStrictness (QuestionStrictness questionStrictness)
  {
    mQuestionStrictness = questionStrictness;
  }
}
