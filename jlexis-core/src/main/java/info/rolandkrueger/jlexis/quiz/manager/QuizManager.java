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
package info.rolandkrueger.jlexis.quiz.manager;

import info.rolandkrueger.jlexis.JLexisMain;
import info.rolandkrueger.jlexis.data.languages.Language;
import info.rolandkrueger.jlexis.data.units.LearningUnit;
import info.rolandkrueger.jlexis.data.units.UnmodifiableLearningUnit;
import info.rolandkrueger.jlexis.data.vocable.Vocable;
import info.rolandkrueger.jlexis.gui.internalframes.QuizFrame;
import info.rolandkrueger.jlexis.managers.learningunitmanager.LearningUnitManager;
import info.rolandkrueger.jlexis.misc.JLexisUtils;
import info.rolandkrueger.jlexis.quiz.data.AbstractQuizQuestion;
import info.rolandkrueger.jlexis.quiz.data.AbstractQuizTypeConfiguration;
import info.rolandkrueger.jlexis.quiz.data.GeneralQuizConfiguration;
import info.rolandkrueger.jlexis.quiz.data.AbstractQuizQuestion.AnswerCorrectness;
import info.rolandkrueger.jlexis.quiz.data.GeneralQuizConfiguration.IncorrectAnswerRepetitionEnum;
import info.rolandkrueger.jlexis.quiz.data.GeneralQuizConfiguration.QuestionOrderEnum;
import info.rolandkrueger.jlexis.quiz.data.GeneralQuizConfiguration.QuestionRepetitionEnum;
import info.rolandkrueger.jlexis.quiz.data.GeneralQuizConfiguration.ShowCorrectAnswer;
import info.rolandkrueger.jlexis.quiz.data.GeneralQuizConfiguration.WhenToRepeatIncorrectAnswer;
import info.rolandkrueger.jlexis.util.TernaryLogicValues;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.swing.JOptionPane;

/**
 * @author Roland Krueger
 * @version $Id: QuizManager.java 197 2009-12-14 07:27:08Z roland $
 */
public class QuizManager implements CreateQuizCallbackInterface, ActionListener
{
  private List<AbstractQuizTypeConfiguration> mSelectedQuizTypeConfigurations;
  private Set<LearningUnit>                   mLearningUnits;
  private QuizFrame                           mQuizFrame;
  private List<AbstractQuizQuestion>          mQuizQuestions;
  private AbstractQuizQuestion                mCurrentQuestion;
  private GeneralQuizConfiguration            mConfiguration;
  private TernaryLogicValues                  mLastAnswerCorrect = TernaryLogicValues.UNKNOWN;
  private Vocable                             mLastQuestionSolution;
  
  private int mNextQuestionIndex;
  private int mQuestionCount;
  private int mCorrectAnswers;
  private int mCorrectAnswersOnFirstTrial;
  private String mLastGivenAnswer;
  
  public QuizManager ()
  {
    mSelectedQuizTypeConfigurations = new ArrayList<AbstractQuizTypeConfiguration> ();
    mLearningUnits     = new HashSet<LearningUnit> ();
  }

  @Override
  public void addLearningUnits (List<LearningUnit> units)
  {
    mLearningUnits.addAll (units);
    updateQuizTypeConfigurations (mLearningUnits, mSelectedQuizTypeConfigurations);
  }

  @Override
  public void removeLearningUnits (List<LearningUnit> units)
  {
    mLearningUnits.removeAll (units);
    updateQuizTypeConfigurations (mLearningUnits, mSelectedQuizTypeConfigurations);
  }

  @Override
  public List<AbstractQuizTypeConfiguration> getQuizConfigurations ()
  {
    return mSelectedQuizTypeConfigurations;
  }

  @Override
  public void addQuizTypeConfiguration (AbstractQuizTypeConfiguration quizTypeConfiguration, Language forLanguage)
  {
    if (quizTypeConfiguration == null)
      throw new NullPointerException ("The quiz type configuration object is null.");
    
    quizTypeConfiguration.setCorrespondingLanguage (forLanguage);
    mSelectedQuizTypeConfigurations.add (quizTypeConfiguration);
    updateQuizTypeConfigurations (mLearningUnits, mSelectedQuizTypeConfigurations);
  }

  private void updateQuizTypeConfigurations (Collection<LearningUnit> learningUnits, 
      Collection<AbstractQuizTypeConfiguration> selectedQuizTypeConfigurations)
  {
    for (AbstractQuizTypeConfiguration quizTypeConfiguration : selectedQuizTypeConfigurations)
    {
      Language forLanguage = quizTypeConfiguration.getCorrespondingLanguage ().canRead () ? 
          quizTypeConfiguration.getCorrespondingLanguage ().getValue () : null;
          
      List<UnmodifiableLearningUnit> relevantUnits = new ArrayList<UnmodifiableLearningUnit> ();

      for (LearningUnit unit : learningUnits)
      {
        if (forLanguage == null || unit.getLanguages ().contains (forLanguage))
          relevantUnits.add (new UnmodifiableLearningUnit (unit));
      }
      quizTypeConfiguration.createQuizQuestionsFor (relevantUnits, forLanguage);    
    }    
  }
  
  @Override
  public void startQuiz (GeneralQuizConfiguration configuration)
  {
    mConfiguration = configuration;
    mNextQuestionIndex = 0;
    mQuestionCount = 0;
    mCorrectAnswers = 0;
    mCorrectAnswersOnFirstTrial = 0;
    mQuizQuestions = new ArrayList<AbstractQuizQuestion> ();
    for (AbstractQuizTypeConfiguration config : mSelectedQuizTypeConfigurations)
    {
      mQuizQuestions.addAll (config.getQuizQuestions ()); 
    }
    
    // randomize the question list, if the questions are to be posed in random order
    if (mConfiguration.getOrder () == QuestionOrderEnum.RANDOM)
    {
      Collections.shuffle (mQuizQuestions, new Random (System.currentTimeMillis ()));
    }
    
    // if configured, mix in a number of randomly selected quiz questions from other learning units
    if (mConfiguration.isDoMixinUnits ())
    {
      mixinQuestionsFromOtherUnits ();
    }
    
    mQuizFrame = new QuizFrame ();
    mQuizFrame.setOkButtonActionListener (this);
    mQuizFrame.addCancelButtonActionListener (new ActionListener () {
      @Override
      public void actionPerformed (ActionEvent e)
      {
        // TODO: I18N
        int answer = JOptionPane.showConfirmDialog (mQuizFrame, "Wollen Sie die Abfragerunde wirklich beenden?", 
            "Abbrechen", JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE);
        if (answer == JOptionPane.NO_OPTION) return;
        endQuiz ();
      }});
    mQuizFrame.addFinishButtonActionListener (new ActionListener () {
      @Override
      public void actionPerformed (ActionEvent e)
      {
        endQuiz ();
      }});
    JLexisMain.getInstance ().getMainWindow ().addFrameToDesktop (mQuizFrame);
    poseNextQuestion ();
    mQuizFrame.setVisible (true);
  }
  
  private void mixinQuestionsFromOtherUnits ()
  {
    Set<LearningUnit> units = new HashSet<LearningUnit> ();
    if (mConfiguration.isMixinAllUnits ())
    {
      units.addAll (LearningUnitManager.getInstance ().getAllManagedLearningUnits ());
      units.removeAll (mLearningUnits);
    } else
      units.addAll (mConfiguration.getMixinUnits ());
    
    List<AbstractQuizTypeConfiguration> quizConfigs = new ArrayList<AbstractQuizTypeConfiguration> ();
    for (AbstractQuizTypeConfiguration config : mSelectedQuizTypeConfigurations)
    {
      quizConfigs.add (config.getCopy ());
    }
    
    updateQuizTypeConfigurations (units, quizConfigs);
    List<AbstractQuizQuestion> questions = new ArrayList<AbstractQuizQuestion> ();
    for (AbstractQuizTypeConfiguration config : quizConfigs)
    {
      questions.addAll (config.getQuizQuestions ());
    }
    
    int mixinCount = Math.round (((float) mQuizQuestions.size () * 
        ((float) mConfiguration.getMixinPercentage () / 100.0f)));
    
    while (mixinCount > 0 && questions.size () > 0)
    {
      AbstractQuizQuestion question = questions.remove (JLexisUtils.getRandomNumber (questions.size ()));
      mQuizQuestions.add (JLexisUtils.getRandomNumber (mQuizQuestions.size () + 1), question);
      mixinCount--;
    }
  }
  
  private void endQuiz ()
  {
    mQuizFrame.dispose ();
    QuizStatisticsDialog statisticsDialog = new QuizStatisticsDialog (null);
    statisticsDialog.setValues (mQuestionCount, mCorrectAnswers, mCorrectAnswersOnFirstTrial,
        mQuestionCount - mCorrectAnswers);
    statisticsDialog.setVisible (true);
  }

  private void poseNextQuestion ()
  {
    assert mQuizQuestions != null;
    assert mQuizFrame != null;
    
    mQuizFrame.setCorrectAnswersPercentage (mQuestionCount, mCorrectAnswers);

    while (mCurrentQuestion == null)
    {
      if (mQuizQuestions.isEmpty ())
      {
        // There are no more questions to pose. In this case only the result of the
        // previous question is displayed. After having read this the user can close the
        // quiz frame.
        mQuizFrame.setFinalQuestionsResult (mLastQuestionSolution, mLastAnswerCorrect, 
            mLastGivenAnswer, mConfiguration);
        return;
      }

      if (mConfiguration.getOrder () == QuestionOrderEnum.RANDOM)
      {
        Random rand = new Random (System.currentTimeMillis ());
        mNextQuestionIndex = rand.nextInt (mQuizQuestions.size ());
      }
      mCurrentQuestion = mQuizQuestions.get (mNextQuestionIndex);

      if (mConfiguration.getQuestionRepetition () != QuestionRepetitionEnum.ENDLESS &&
          mCurrentQuestion.getNumberOfTimesQuestionWasPosed () == mConfiguration.getRepeatQuestionsCount ())
      {
        removeQuestion (mCurrentQuestion);
        mCurrentQuestion = null;
      }
      mNextQuestionIndex++;
      if ( ! mQuizQuestions.isEmpty ())
        mNextQuestionIndex %= mQuizQuestions.size ();
    }
    mCurrentQuestion.increaseQuestionPosed ();
    mQuizFrame.setQuestion (mCurrentQuestion, mLastQuestionSolution, mLastAnswerCorrect, 
        mLastGivenAnswer, mConfiguration);
  }
  
  /**
   * Called when the Ok button is pressed in the quiz frame.
   */
  @Override
  public void actionPerformed (ActionEvent e)
  {
    assert mCurrentQuestion != null;
    checkAnswer ();
  }
  
  private void removeQuestion (AbstractQuizQuestion question)
  {
    int index = mQuizQuestions.indexOf (question);
    if (index < 0) return;
    if (index < mNextQuestionIndex)
      mNextQuestionIndex--;
    mQuizQuestions.remove (question);
  }
  
  private void checkAnswer ()
  {
    mQuestionCount++;
    
    AnswerCorrectness answer = mCurrentQuestion.checkUserAnswer ();
    mLastGivenAnswer = mCurrentQuestion.getGivenAnswer ();
    mLastQuestionSolution = null;
    
    if (mConfiguration.getShowCorrectAnswer () == ShowCorrectAnswer.ALWAYS)
    {
      mLastQuestionSolution = mCurrentQuestion.getVocable ();
    } 
      
    if (answer == AnswerCorrectness.CORRECT)
    {
      if (mCurrentQuestion.getIncorrectlyAnsweredCount () == 0)
        mCorrectAnswersOnFirstTrial++;
      mCorrectAnswers++;
      mLastAnswerCorrect = TernaryLogicValues.TRUE;

      mCurrentQuestion = null;
    } else if (answer == AnswerCorrectness.INCORRECT)
    {
      if (mConfiguration.getShowCorrectAnswer () == ShowCorrectAnswer.ONLY_WHEN_INCORRECT)
      {
        mLastQuestionSolution = mCurrentQuestion.getVocable ();
      }
      mLastAnswerCorrect = TernaryLogicValues.FALSE;
      
      if (mConfiguration.getIncorrectAnswerRepetition () != IncorrectAnswerRepetitionEnum.NONE &&
          mCurrentQuestion.getIncorrectlyAnsweredCount () <= mConfiguration.getRepeatIncorrectAnswerCount ()) 
      {
        mCurrentQuestion.increaseIncorrectAnswers ();
        mCurrentQuestion.decreaseQuestionPosed ();
      }
      
      if (mCurrentQuestion.getIncorrectlyAnsweredCount () > mConfiguration.getRepeatIncorrectAnswerCount () ||
          mConfiguration.getIncorrectAnswerRepetition () == IncorrectAnswerRepetitionEnum.NONE)
      {
        removeQuestion (mCurrentQuestion);
        mCurrentQuestion = null;
      } else if ( mConfiguration.getWhenToRepeat () == WhenToRepeatIncorrectAnswer.LATER)
      {
        removeQuestion (mCurrentQuestion);
        Random rand = new Random (System.currentTimeMillis ());
        mQuizQuestions.add (rand.nextInt (mQuizQuestions.size () + 1), mCurrentQuestion);
        
        mCurrentQuestion = null;
      } else
      {
        mLastQuestionSolution = null;
      }
    }
    
    poseNextQuestion ();
  }
}
