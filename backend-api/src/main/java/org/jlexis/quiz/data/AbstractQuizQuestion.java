/*
 * Created on 10.03.2009
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
package org.jlexis.quiz.data;

import org.jlexis.data.languages.Language;
import org.jlexis.data.vocable.Vocable;
import org.jlexis.data.vocable.terms.AbstractTermData;
import org.jlexis.data.vocable.verification.VocableVerificationData;

import java.util.List;
import java.util.Objects;

public abstract class AbstractQuizQuestion {
    private Vocable vocable;
    private QuizAnswerType type = QuizAnswerType.TEXT;

    //        TODO: fix me
//    private static AnswerPanelHandle sTextualAnswerPanelHandle;
//    private static Map<Integer, AnswerPanelHandle> sMultipleChoiceAnswerPanelHandles;
    private List<String> optionLabels;
    private boolean useStandardQuestion = false;
    private String questionText;
    private String textToTranslate;
    private Language queriedLanguage;
    private VocableVerificationData expectedAnswer;
    private int incorrectlyAnsweredCount;
    private int numberOfTimesQuestionWasPosed;
    static {
        // register the standard answer panels provided by this class
//        TODO: fix me
//        sTextualAnswerPanelHandle = AnswerPanelManager.getInstance().registerAnswerPanel(new TextualAnswerPanel());
//        sMultipleChoiceAnswerPanelHandles = new HashMap<Integer, AnswerPanelHandle>();
    }
    public AbstractQuizQuestion(Vocable forVocable, Language forLanguage) {
        vocable = Objects.requireNonNull(forVocable);
        queriedLanguage = Objects.requireNonNull(forLanguage);
        incorrectlyAnsweredCount = 0;
        numberOfTimesQuestionWasPosed = 0;
    }
    //        TODO: fix me
//    private AnswerPanelHandle mAnswerPanelHandle;

    protected abstract void setSourceLanguageImpl(Language sourceLanguage);

    public abstract Language getSourceLanguage();

    public void setSourceLanguage(Language sourceLanguage) {
        Objects.requireNonNull(sourceLanguage, "Source language is null.");
        setSourceLanguageImpl(sourceLanguage);
    }

    protected abstract AnswerCorrectness checkUserAnswerImpl();

    public int getIncorrectlyAnsweredCount() {
        return incorrectlyAnsweredCount;
    }

    public int getNumberOfTimesQuestionWasPosed() {
        return numberOfTimesQuestionWasPosed;
    }

    public void increaseIncorrectAnswers() {
        incorrectlyAnsweredCount++;
    }

    public void decreaseQuestionPosed() {
        numberOfTimesQuestionWasPosed--;
    }

    public void increaseQuestionPosed() {
        numberOfTimesQuestionWasPosed++;
    }

    public AnswerCorrectness checkUserAnswer() {
        if (type == QuizAnswerType.TEXT && expectedAnswer != null) {
            VocableVerificationData givenAnswer = VocableVerificationData.create().withoutAbbreviationVariants().build();
            //        TODO: fix me
//            givenAnswer.tokenizeAndAddString(((TextualAnswerPanel) AnswerPanelManager.getInstance().
//                    getAnswerPanelFor(sTextualAnswerPanelHandle)).getAnswerText());
//            VocableVerificationResult result = expectedAnswer.verify(givenAnswer, queriedLanguage);

//            if (result.isCorrect())
//                return AnswerCorrectness.CORRECT;
            return AnswerCorrectness.INCORRECT;
        } else
            return checkUserAnswerImpl();
    }

    public String getGivenAnswer() {
        //        TODO: fix me
//        if (type == QuizAnswerType.TEXT && expectedAnswer != null) {
//            return ((TextualAnswerPanel) AnswerPanelManager.getInstance().
//                    getAnswerPanelFor(sTextualAnswerPanelHandle)).getAnswerText();
//        } else return getGivenAnswerImpl();
        return null;
    }

    protected abstract String getGivenAnswerImpl();

    public Vocable getVocable() {
        return vocable;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String text) {
        Objects.requireNonNull(text, "Question text is null.");
        questionText = text;
    }

    public String getTextToTranslate() {
        return textToTranslate;
    }

    public void setTextToTranslate(String textToTranslate) {
        Objects.requireNonNull(textToTranslate, "Text to translate is null.");
        this.textToTranslate = textToTranslate;
    }

    public boolean isStandardQuestionUsed() {
        return useStandardQuestion;
    }

    public Language getQueriedLanguage() {
        return queriedLanguage;
    }

    public void useStandardQuestion() {
        useStandardQuestion = true;
    }

    public void setQuizAnswerType(QuizAnswerType type) {
        if ((type == QuizAnswerType.MULTIPLE_CHOICE_MULTIPLE_SELECTION ||
                type == QuizAnswerType.MULTIPLE_CHOICE_SINGLE_SELECTION) &&
                optionLabels == null) {
            throw new IllegalStateException("No option labels have been set with setMultipleChoiceOptions() yet.");
        }

        if (type != QuizAnswerType.MULTIPLE_CHOICE_MULTIPLE_SELECTION &&
                type != QuizAnswerType.MULTIPLE_CHOICE_SINGLE_SELECTION) {
            optionLabels = null;
        }

        this.type = type;
    }

    public void setExpectedAnswer(VocableVerificationData expectedAnswer) {
        if (type != QuizAnswerType.TEXT) {
            throw new IllegalStateException("This quiz question is not of type TEXT. Configure it as being of " +
                    "type text with setQuizAnswerType() first.");
        }
        Objects.requireNonNull(expectedAnswer, "Expected answer object is null.");

        this.expectedAnswer = expectedAnswer;
    }

    public void setExpectedAnswer(AbstractTermData expectedAnswer) {
        Objects.requireNonNull(expectedAnswer, "Expected answer is null.");
        VocableVerificationData data = VocableVerificationData.create()
                .withoutAbbreviationVariants()
                .tokenizeAndAddString(expectedAnswer.getEncodedTerm())
                .build();
        setExpectedAnswer(data);
    }

    public void setMultipleChoiceOptions(List<String> optionLabels) {
        Objects.requireNonNull(optionLabels, "Option label list is null.");

        if (type == null) {
            type = QuizAnswerType.MULTIPLE_CHOICE_SINGLE_SELECTION;
        }

        //        TODO: fix me
//        mAnswerPanelHandle = sMultipleChoiceAnswerPanelHandles.get(optionLabels.size());
//        if (mAnswerPanelHandle == null) {
//            MultipleChoiceAnswerPanel panel = new MultipleChoiceAnswerPanel(optionLabels.size());
//            mAnswerPanelHandle = AnswerPanelManager.getInstance().registerAnswerPanel(panel);
//            sMultipleChoiceAnswerPanelHandles.put(optionLabels.size(), mAnswerPanelHandle);
//        }

        this.optionLabels = optionLabels;
    }

    public List<Integer> getSelectedOptionIndices() {
        if (type != QuizAnswerType.MULTIPLE_CHOICE_MULTIPLE_SELECTION &&
                type != QuizAnswerType.MULTIPLE_CHOICE_SINGLE_SELECTION) {
            throw new IllegalStateException("This quiz question is not a multiple choice question.");
        }
        //        TODO: fix me
//        if (mAnswerPanelHandle == null)
//            throw new IllegalStateException("This quiz question hasn't been posed yet.");
//
//        return ((MultipleChoiceAnswerPanel) AnswerPanelManager.getInstance().
//                getAnswerPanelFor(mAnswerPanelHandle)).getSelectedIndices();
        return null;
    }

    public String getAnswerText() {
        if (type != QuizAnswerType.TEXT) {
            throw new IllegalStateException("This quiz question is not of type TEXT.");
        }
        //        TODO: fix me
//        if (mAnswerPanelHandle == null)
//            throw new IllegalStateException("This quiz question hasn't been posed yet.");
//
//        return ((TextualAnswerPanel) AnswerPanelManager.getInstance().
//                getAnswerPanelFor(sTextualAnswerPanelHandle)).getAnswerText();
        return null;
    }

    public enum QuizAnswerType {
        TEXT,
        MULTIPLE_CHOICE_SINGLE_SELECTION,
        MULTIPLE_CHOICE_MULTIPLE_SELECTION,
        PROVIDED
    }

    public enum AnswerCorrectness {
        CORRECT,
        INCORRECT,
        CORRECT_BUT_WAS_NOT_ASKED,
        IMPLEMENTATION_NOT_NEEDED
    }

    //        TODO: fix me
//    public void setAnswerPanelHandle(AnswerPanelHandle answerPanelHandle) {
//        if (answerPanelHandle == null)
//            throw new NullPointerException("Panel is null.");
//
//        if (type != QuizAnswerType.PROVIDED)
//            throw new IllegalStateException("Quiz answer type is not set to PROVIDED.");
//
//        mAnswerPanelHandle = answerPanelHandle;
//    }

    //        TODO: fix me
//    public AnswerPanelHandle getAnswerPanelHandle() {
//        if (mAnswerPanelHandle == null) {
//            if (type == QuizAnswerType.TEXT) {
//                mAnswerPanelHandle = sTextualAnswerPanelHandle;
//            } else {
//                throw new IllegalStateException("No answer panel handle has been provided for this quiz question.");
//            }
//        }
//        if (type == QuizAnswerType.MULTIPLE_CHOICE_MULTIPLE_SELECTION ||
//                type == QuizAnswerType.MULTIPLE_CHOICE_SINGLE_SELECTION) {
//            ((MultipleChoiceAnswerPanel) AnswerPanelManager.getInstance().
//                    getAnswerPanelFor(mAnswerPanelHandle)).setChoices(optionLabels, type);
//        }
//        return mAnswerPanelHandle;
//    }

    //        TODO: fix me
//    public AbstractAnswerPanel getAnswerPanel() {
//        return AnswerPanelManager.getInstance().getAnswerPanelFor(getAnswerPanelHandle());
//    }
}
