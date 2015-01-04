/*
 * Created on 15.05.2009
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
package org.jlexis.quiz.generalquiztypes;


import org.jlexis.data.languages.Language;
import org.jlexis.data.units.UnmodifiableLearningUnit;
import org.jlexis.data.vocable.Vocable;
import org.jlexis.data.vocable.verification.VocableVerificationData;
import org.jlexis.quiz.data.AbstractQuizQuestion;
import org.jlexis.quiz.data.AbstractQuizType;
import org.jlexis.quiz.data.AbstractQuizTypeConfiguration;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GeneralQuizType extends AbstractQuizType {
    private String description;
//  private GeneralQuizConfigPanel mConfigPanel;

    public GeneralQuizType() {
        //        TODO: fix me
//    mConfigPanel = new GeneralQuizConfigPanel ();
    }

    @Override
    public JPanel getConfigurationPanel() {
        //        TODO: fix me
//    return mConfigPanel;
        return null;
    }

    @Override
    public String getName() {
        // TODO: I18N
//    return "Alles abfragen";
        return "Test everything";
    }

    @Override
    public AbstractQuizTypeConfiguration getQuizTypeConfiguration() {
        return new GeneralQuizTypeConfiguration(this);
    }

    private class GeneralQuizTypeConfiguration extends AbstractQuizTypeConfiguration {
        private GeneralQuizMode mQuizMode;

        public GeneralQuizTypeConfiguration(AbstractQuizType quizType) {
            super(quizType);
//        TODO: fix me
//      mQuizMode = mConfigPanel.getSelectedQuizMode ();
        }

        @Override
        public AbstractQuizTypeConfiguration getCopy() {
            return new GeneralQuizTypeConfiguration(getQuizType());
        }

        @Override
        protected List<AbstractQuizQuestion> createQuizQuestionsForImpl(List<UnmodifiableLearningUnit> units,
                                                                        Language forLanguage) {
            List<AbstractQuizQuestion> result = new ArrayList<AbstractQuizQuestion>();

            for (UnmodifiableLearningUnit unit : units) {
                List<Language> langs = unit.getLanguages();

                if (mQuizMode == GeneralQuizMode.MIXED) {
                    for (Vocable vocable : unit) {
                        Language language = unit.getRandomLanguage();
                        result.add(new GeneralQuizQuestion(vocable, language, unit));
                    }
                } else if (mQuizMode == GeneralQuizMode.FOREIGN_LANGUAGE_TO_NATIVE) {
                    for (Vocable vocable : unit) {
                        result.add(new GeneralQuizQuestion(vocable, unit.getNativeLanguage(), unit));
                    }
                } else {
                    for (Language language : langs) {
                        for (Vocable vocable : unit) {
                            result.add(new GeneralQuizQuestion(vocable, language, unit));
                        }
                    }
                }
            }

            return result;
        }

        @Override
        public String getDescription() {
            // TODO: I18N
            if (description == null) {
                StringBuilder buf = new StringBuilder();
//        buf.append ("Abfragerichtung: ");
                buf.append("Test order: ");
                buf.append(mQuizMode.toString());
                description = buf.toString();
            }
            return description;
        }

        public class GeneralQuizQuestion extends AbstractQuizQuestion {
            private Language mSourceLanguage;

            public GeneralQuizQuestion(Vocable forVocable, Language forLanguage, UnmodifiableLearningUnit forUnit) {
                super(forVocable, forLanguage);
                setQuizAnswerType(QuizAnswerType.TEXT);
                VocableVerificationData vocableVerificationData = forVocable.getVariantInput(forLanguage).getQuizVerificationData();
                String additionalQuestionText = vocableVerificationData.getAdditionalQuestionText();
                // TODO: I18N
//        String questionText = String.format ("\u00DCbersetze nach %s! ", forLanguage.getName ().toLowerCase ());
                String questionText = String.format("Translate into %s! ", forLanguage.getName());
                if (additionalQuestionText != null) {
                    questionText += additionalQuestionText;
                }
                setQuestionText(questionText);

                Language sourceLanguage = null;
                if (mQuizMode == GeneralQuizMode.MIXED) {
                    sourceLanguage = forUnit.getRandomLanguageExcept(forLanguage);
                } else if (mQuizMode == GeneralQuizMode.NATIVE_TO_FOREIGN_LANGUAGE) {
                    sourceLanguage = forUnit.getNativeLanguage();
                } else {
                    // TODO: the following must be selectable by the user: if there is more than one
                    // languages used in the learning unit, not only the first available language should
                    // be taken as the source language
                    sourceLanguage = forUnit.getLanguages().get(0);
                }
                setExpectedAnswer(vocableVerificationData);
                // FIXME: signature of provideShortDisplayText changed
//                setTextToTranslate(forVocable.getVariantInput(sourceLanguage).provideShortDisplayText());
                setSourceLanguage(sourceLanguage);
            }

            @Override
            protected void setSourceLanguageImpl(Language sourceLanguage) {
                mSourceLanguage = sourceLanguage;
            }

            @Override
            public Language getSourceLanguage() {
                return mSourceLanguage;
            }

            @Override
            protected AnswerCorrectness checkUserAnswerImpl() {
                return AnswerCorrectness.IMPLEMENTATION_NOT_NEEDED;
            }

            @Override
            protected String getGivenAnswerImpl() {
                return null;
            }
        }
    }
}
