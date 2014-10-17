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
import org.jlexis.data.vocable.terms.TermDataInterface;
import org.jlexis.data.vocable.verification.VocableVerificationData;
import org.jlexis.data.vocable.verification.VocableVerificationResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Roland Krueger
 * @version $Id: AbstractQuizQuestion.java 136 2009-10-02 18:20:53Z roland $
 */
public abstract class AbstractQuizQuestion {
    public enum QuizAnswerType {
        TEXT,
        MULTIPLE_CHOICE_SINGLE_SELECTION,
        MULTIPLE_CHOICE_MULTIPLE_SELECTION,
        PROVIDED
    }

    ;

    public enum AnswerCorrectness {
        CORRECT,
        INCORRECT,
        CORRECT_BUT_WAS_NOT_ASKED,
        IMPLEMENTATION_NOT_NEEDED
    }

    private static AnswerPanelHandle sTextualAnswerPanelHandle;
    private static Map<Integer, AnswerPanelHandle> sMultipleChoiceAnswerPanelHandles;

    private Vocable mVocable;
    private QuizAnswerType mType = QuizAnswerType.TEXT;
    private List<String> mOptionLabels;
    private boolean mUseStandardQuestion = false;
    private String mQuestionText;
    private String mTextToTranslate;
    private Language mQueriedLanguage;
    private VocableVerificationData mExpectedAnswer;
    private int mIncorrectlyAnsweredCount;
    private int mNumberOfTimesQuestionWasPosed;
    private AnswerPanelHandle mAnswerPanelHandle;

    static {
        // register the standard answer panels provided by this class
        sTextualAnswerPanelHandle = AnswerPanelManager.getInstance().registerAnswerPanel(new TextualAnswerPanel());
        sMultipleChoiceAnswerPanelHandles = new HashMap<Integer, AnswerPanelHandle>();
    }

    public AbstractQuizQuestion(Vocable forVocable, Language forLanguage) {
        if (forVocable == null || forLanguage == null)
            throw new NullPointerException("One of the arguments is null.");

        mVocable = forVocable;
        mQueriedLanguage = forLanguage;
        mIncorrectlyAnsweredCount = 0;
        mNumberOfTimesQuestionWasPosed = 0;
    }

    protected abstract void setSourceLanguageImpl(Language sourceLanguage);

    public void setSourceLanguage(Language sourceLanguage) {
        if (sourceLanguage == null)
            throw new NullPointerException("Source language is null.");
        setSourceLanguageImpl(sourceLanguage);
    }

    public abstract Language getSourceLanguage();

    protected abstract AnswerCorrectness checkUserAnswerImpl();

    public int getIncorrectlyAnsweredCount() {
        return mIncorrectlyAnsweredCount;
    }

    public int getNumberOfTimesQuestionWasPosed() {
        return mNumberOfTimesQuestionWasPosed;
    }

    public void increaseIncorrectAnswers() {
        mIncorrectlyAnsweredCount++;
    }

    public void decreaseQuestionPosed() {
        mNumberOfTimesQuestionWasPosed--;
    }

    public void increaseQuestionPosed() {
        mNumberOfTimesQuestionWasPosed++;
    }

    public AnswerCorrectness checkUserAnswer() {
        if (mType == QuizAnswerType.TEXT && mExpectedAnswer != null) {
            VocableVerificationData givenAnswer = new VocableVerificationData();
            givenAnswer.tokenizeAndAddString(((TextualAnswerPanel) AnswerPanelManager.getInstance().
                    getAnswerPanelFor(sTextualAnswerPanelHandle)).getAnswerText());
            VocableVerificationResult result = mExpectedAnswer.verify(givenAnswer, mQueriedLanguage);

            if (result.isCorrect())
                return AnswerCorrectness.CORRECT;
            return AnswerCorrectness.INCORRECT;
        } else
            return checkUserAnswerImpl();
    }

    public String getGivenAnswer() {
        if (mType == QuizAnswerType.TEXT && mExpectedAnswer != null) {
            return ((TextualAnswerPanel) AnswerPanelManager.getInstance().
                    getAnswerPanelFor(sTextualAnswerPanelHandle)).getAnswerText();
        } else return getGivenAnswerImpl();
    }

    protected abstract String getGivenAnswerImpl();

    public Vocable getVocable() {
        return mVocable;
    }

    public String getQuestionText() {
        return mQuestionText;
    }

    public String getTextToTranslate() {
        return mTextToTranslate;
    }

    public boolean isStandardQuestionUsed() {
        return mUseStandardQuestion;
    }

    public Language getQueriedLanguage() {
        return mQueriedLanguage;
    }

    public void setQuestionText(String text) {
        if (text == null)
            throw new NullPointerException("Question text is null.");

        mQuestionText = text;
    }

    public void useStandardQuestion() {
        mUseStandardQuestion = true;
    }

    public void setTextToTranslate(String textToTranslate) {
        if (textToTranslate == null)
            throw new NullPointerException("Text to translate is null.");

        mTextToTranslate = textToTranslate;
    }

    public void setQuizAnswerType(QuizAnswerType type) {
        if ((type == QuizAnswerType.MULTIPLE_CHOICE_MULTIPLE_SELECTION ||
                type == QuizAnswerType.MULTIPLE_CHOICE_SINGLE_SELECTION) &&
                mOptionLabels == null)
            throw new IllegalStateException("No option labels have been set with setMultipleChoiceOptions() yet.");

        if (type != QuizAnswerType.MULTIPLE_CHOICE_MULTIPLE_SELECTION &&
                type != QuizAnswerType.MULTIPLE_CHOICE_SINGLE_SELECTION)
            mOptionLabels = null;

        mType = type;
    }

    public void setExpectedAnswer(VocableVerificationData expectedAnswer) {
        if (mType != QuizAnswerType.TEXT)
            throw new IllegalStateException("This quiz question is not of type TEXT. Configure it as being of " +
                    "type text with setQuizAnswerType() first.");
        if (expectedAnswer == null)
            throw new NullPointerException("Expected answer object is null.");

        mExpectedAnswer = expectedAnswer;
    }

    public void setExpectedAnswer(TermDataInterface expectedAnswer) {
        if (expectedAnswer == null)
            throw new NullPointerException("Expected answer is null.");
        VocableVerificationData data = new VocableVerificationData();
        data.tokenizeAndAddString(expectedAnswer.getNormalizedTerm());
        setExpectedAnswer(data);
    }

    public void setMultipleChoiceOptions(List<String> optionLabels) {
        if (optionLabels == null)
            throw new NullPointerException("Option label list is null.");

        if (mType == null)
            mType = QuizAnswerType.MULTIPLE_CHOICE_SINGLE_SELECTION;

        mAnswerPanelHandle = sMultipleChoiceAnswerPanelHandles.get(optionLabels.size());
        if (mAnswerPanelHandle == null) {
            MultipleChoiceAnswerPanel panel = new MultipleChoiceAnswerPanel(optionLabels.size());
            mAnswerPanelHandle = AnswerPanelManager.getInstance().registerAnswerPanel(panel);
            sMultipleChoiceAnswerPanelHandles.put(optionLabels.size(), mAnswerPanelHandle);
        }

        mOptionLabels = optionLabels;
    }

    public List<Integer> getSelectedOptionIndices() {
        if (mType != QuizAnswerType.MULTIPLE_CHOICE_MULTIPLE_SELECTION &&
                mType != QuizAnswerType.MULTIPLE_CHOICE_SINGLE_SELECTION)
            throw new IllegalStateException("This quiz question is not a multiple choice question.");
        if (mAnswerPanelHandle == null)
            throw new IllegalStateException("This quiz question hasn't been posed yet.");

        return ((MultipleChoiceAnswerPanel) AnswerPanelManager.getInstance().
                getAnswerPanelFor(mAnswerPanelHandle)).getSelectedIndices();
    }

    public String getAnswerText() {
        if (mType != QuizAnswerType.TEXT)
            throw new IllegalStateException("This quiz question is not of type TEXT.");
        if (mAnswerPanelHandle == null)
            throw new IllegalStateException("This quiz question hasn't been posed yet.");

        return ((TextualAnswerPanel) AnswerPanelManager.getInstance().
                getAnswerPanelFor(sTextualAnswerPanelHandle)).getAnswerText();
    }

    public void setAnswerPanelHandle(AnswerPanelHandle answerPanelHandle) {
        if (answerPanelHandle == null)
            throw new NullPointerException("Panel is null.");

        if (mType != QuizAnswerType.PROVIDED)
            throw new IllegalStateException("Quiz answer type is not set to PROVIDED.");

        mAnswerPanelHandle = answerPanelHandle;
    }

    public AnswerPanelHandle getAnswerPanelHandle() {
        if (mAnswerPanelHandle == null) {
            if (mType == QuizAnswerType.TEXT) {
                mAnswerPanelHandle = sTextualAnswerPanelHandle;
            } else {
                throw new IllegalStateException("No answer panel handle has been provided for this quiz question.");
            }
        }
        if (mType == QuizAnswerType.MULTIPLE_CHOICE_MULTIPLE_SELECTION ||
                mType == QuizAnswerType.MULTIPLE_CHOICE_SINGLE_SELECTION) {
            ((MultipleChoiceAnswerPanel) AnswerPanelManager.getInstance().
                    getAnswerPanelFor(mAnswerPanelHandle)).setChoices(mOptionLabels, mType);
        }
        return mAnswerPanelHandle;
    }

    public AbstractAnswerPanel getAnswerPanel() {
        return AnswerPanelManager.getInstance().getAnswerPanelFor(getAnswerPanelHandle());
    }
}
