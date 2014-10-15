/*
 * Created on 17.03.2009
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
package info.rolandkrueger.jlexis.plugin.swedish.quiztypes;

import info.rolandkrueger.jlexis.data.languages.Language;
import info.rolandkrueger.jlexis.data.units.UnmodifiableLearningUnit;
import info.rolandkrueger.jlexis.data.vocable.AbstractWordType;
import info.rolandkrueger.jlexis.data.vocable.Vocable;
import info.rolandkrueger.jlexis.data.vocable.WordTypeEnum;
import info.rolandkrueger.jlexis.plugin.swedish.quiztypes.configpanels.AnswerInputTypeEnum;
import info.rolandkrueger.jlexis.plugin.swedish.quiztypes.configpanels.DisplayTypeEnum;
import info.rolandkrueger.jlexis.plugin.swedish.quiztypes.configpanels.NounGenderQuizConfigPanel;
import info.rolandkrueger.jlexis.plugin.swedish.userinput.SwedishNounUserInput;
import info.rolandkrueger.jlexis.quiz.data.AbstractQuizQuestion;
import info.rolandkrueger.jlexis.quiz.data.AbstractQuizType;
import info.rolandkrueger.jlexis.quiz.data.AbstractQuizTypeConfiguration;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

/**
 * @author Roland Krueger
 * @version $Id: NounGenderQuiz.java 187 2009-11-20 22:02:04Z roland $
 */
public class NounGenderQuiz extends AbstractQuizType
{
  private NounGenderQuizConfigPanel mConfigPanel;
  private static List<String> sOptions;
  
  static
  {
    sOptions = new ArrayList<String> ();
    // TODO: I18N
//    sOptions.add ("Utrum");
//    sOptions.add ("Neutrum");
    sOptions.add ("Common");
    sOptions.add ("Neuter");
  }
  
  public NounGenderQuiz ()
  {
    mConfigPanel = new NounGenderQuizConfigPanel ();
  }
  
  @Override
  public JPanel getConfigurationPanel ()
  {
    return mConfigPanel;
  }

  @Override
  public String getName ()
  {
    //TODO: I18N
//    return "Substantive: Geschlecht";
    return "Nouns: Gender";
  }

  @Override
  public AbstractQuizTypeConfiguration getQuizTypeConfiguration ()
  {
    return new NounSexQuizConfiguration (this);
  }
  
  public class NounSexQuizConfiguration extends AbstractQuizTypeConfiguration
  {
    private AnswerInputTypeEnum mAnswerInputType;
    private DisplayTypeEnum     mDisplayType;
    private String              mDescription;
    
    protected NounSexQuizConfiguration (AbstractQuizType type)
    {
      super (type);
      mAnswerInputType = mConfigPanel.getAnswerInputType ();
      mDisplayType     = mConfigPanel.getDisplayType ();
    }
    
    @Override
    public AbstractQuizTypeConfiguration getCopy ()
    {
      return new NounSexQuizConfiguration (getQuizType ());
    }

    @Override
    protected List<AbstractQuizQuestion> createQuizQuestionsForImpl (List<UnmodifiableLearningUnit> units, Language forLanguage)
    {
      List<AbstractQuizQuestion> result = new ArrayList<AbstractQuizQuestion> ();
      
      for (UnmodifiableLearningUnit unit : units)
      {
        for (Vocable vocable : unit)
        {
          AbstractWordType wordType = vocable.getVariantWordType (forLanguage);
          if (wordType.getWordTypeEnum () != WordTypeEnum.NOUN) continue;
          
          
          AbstractQuizQuestion question = new NounSexQuizQuestion (vocable, forLanguage, unit);
          result.add (question);
        }
      }
      
      return result;
    }

    @Override
    public String getDescription ()
    {
      // TODO: I18N
      if (mDescription == null)
      {
        StringBuilder buf = new StringBuilder ();
//        buf.append ("Eingabe: ");
        buf.append ("Input: ");
        buf.append (mAnswerInputType.toString ());
//        buf.append (", Anzeige: ");
        buf.append (", Display: ");
        buf.append (mDisplayType.toString ());
        mDescription = buf.toString ();
      }
      return mDescription;
    }

    public class NounSexQuizQuestion extends AbstractQuizQuestion 
    {
      private Language mSourceLanguage;
      
      // TODO: I18N
      public NounSexQuizQuestion (Vocable forVocable, Language forLanguage, UnmodifiableLearningUnit forUnit)
      {
        super (forVocable, forLanguage);
        
        if (mAnswerInputType == AnswerInputTypeEnum.MULTIPLE_CHOICE)
        {
          setMultipleChoiceOptions (sOptions);
//          setQuestionText ("Wie ist das Geschlecht des folgenden Wortes?");
          setQuestionText ("What is the gender of the following term?");
          setQuizAnswerType (QuizAnswerType.MULTIPLE_CHOICE_SINGLE_SELECTION);
        }
        
        if (mAnswerInputType == AnswerInputTypeEnum.DEFINITE_TEXTUAL)
        {
//          setQuestionText   ("Wie ist die bestimmte Form Singular fuer das folgende Wort?");
          setQuestionText   ("What is the definite singular of the following term?");
          setExpectedAnswer (forVocable.getVariantInput (forLanguage).getUserData (
              SwedishNounUserInput.DEFINITE_SINGULAR_KEY));
          setQuizAnswerType (QuizAnswerType.TEXT);
        }
        
        if (mDisplayType == DisplayTypeEnum.SWEDISH)
        {
          setTextToTranslate (forVocable.getVariantInput (forLanguage).getUserData (
              SwedishNounUserInput.INDEFINITE_SINGULAR_KEY).getPurgedTerm ());
        }
        
        if (mDisplayType == DisplayTypeEnum.NATIVE)
        {
          setTextToTranslate (forVocable.getVariantInput (forUnit.getNativeLanguage ()).getShortVersion ());
          setSourceLanguage (forUnit.getNativeLanguage ());
        }
        
        if (mDisplayType == DisplayTypeEnum.MIXED)
        {
          Language language = forUnit.getRandomLanguage ();
          if (language.equals (forLanguage))
          {
            setTextToTranslate (forVocable.getVariantInput (forLanguage).getUserData (
                SwedishNounUserInput.INDEFINITE_SINGULAR_KEY).getPurgedTerm ());
          } else
          {
            setTextToTranslate (forVocable.getVariantInput (language).getShortVersion ());
            setSourceLanguage (language);
          }
        }
        
        if (mDisplayType == DisplayTypeEnum.ALL)
        {
          Language language = forUnit.getRandomLanguageExcept (forLanguage);
          setTextToTranslate (forVocable.getVariantInput (language).getShortVersion ());
        }
      }
      
      @Override
      protected void setSourceLanguageImpl (Language sourceLanguage)
      {
        mSourceLanguage = sourceLanguage;
      }
      
      @Override
      public Language getSourceLanguage ()
      {
        return mSourceLanguage;
      }
      
      @Override
      protected AnswerCorrectness checkUserAnswerImpl ()
      {
        if (mAnswerInputType == AnswerInputTypeEnum.MULTIPLE_CHOICE)
        {
          List<Integer> selection = getSelectedOptionIndices ();
          String nounSex = getVocable ().getVariantInput (getQueriedLanguage ()).getUserData (
              SwedishNounUserInput.UTRUM_NEUTRUM_KEY).getUserEnteredTerm ();
          if (nounSex.equals (SwedishNounUserInput.Gender.UTRUM.toString ()) && selection.get (0).equals (0))
            return AnswerCorrectness.CORRECT;
          if (nounSex.equals (SwedishNounUserInput.Gender.NEUTRUM.toString ()) && selection.get (0).equals (1))
            return AnswerCorrectness.CORRECT;
          return AnswerCorrectness.INCORRECT;
        }
        
        return AnswerCorrectness.INCORRECT;
      }

      @Override
      protected String getGivenAnswerImpl ()
      {
        if (mAnswerInputType == AnswerInputTypeEnum.MULTIPLE_CHOICE)
        {
          List<Integer> selection = getSelectedOptionIndices ();
          if (selection.get (0).equals (0)) return "Utrum";
          return "Neutrum";
        }
      
        return null;
      }
    }
  }
}
