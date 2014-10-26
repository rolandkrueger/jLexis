/*
 * Created on 01.12.2009
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
package org.jlexis.plugin.english.userinput;

import org.jlexis.data.vocable.AbstractUserInput;
import org.jlexis.data.vocable.terms.TermDataInterface;
import org.jlexis.data.vocable.verification.VocableVerificationData;
import org.jlexis.roklib.HTMLTextFormatter;
import org.jlexis.roklib.TextFormatter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EnglishVerbUserInput extends AbstractEnglishPluginUserInput {
    private static final String INPUT_ID = EnglishVerbUserInput.class.getCanonicalName();
    private static final String INFINITIVE_BE_KEY = INPUT_ID + ".INFINITIVE_BE";
    private static final String INFINITIVE_AE_KEY = INPUT_ID + ".INFINITIVE_AE";
    private static final String PAST_TENSE_BE_KEY = INPUT_ID + ".PAST_TENSE_BE";
    private static final String PAST_TENSE_AE_KEY = INPUT_ID + ".PAST_TENSE_AE";
    private static final String PAST_PARTICIPLE_BE_KEY = INPUT_ID + ".PAST_PARTICIPLE_BE";
    private static final String PAST_PARTICIPLE_AE_KEY = INPUT_ID + ".PAST_PARTICIPLE_AE";
    private static final String IRREGULAR_BE_KEY = INPUT_ID + ".IRREGULAR_BE";
    private static final String IRREGULAR_AE_KEY = INPUT_ID + ".IRREGULAR_AE";

    public EnglishVerbUserInput() {
        super(INPUT_ID);
    }

    @Override
    protected String[] getUserInputIdentifiersImpl() {
        List<String> userInputIDs = new ArrayList<>(18);
        userInputIDs.add(INFINITIVE_BE_KEY);
        userInputIDs.add(INFINITIVE_AE_KEY);
        userInputIDs.add(PAST_TENSE_BE_KEY);
        userInputIDs.add(PAST_TENSE_AE_KEY);
        userInputIDs.add(PAST_PARTICIPLE_BE_KEY);
        userInputIDs.add(PAST_PARTICIPLE_AE_KEY);
        userInputIDs.add(IRREGULAR_BE_KEY);
        userInputIDs.add(IRREGULAR_AE_KEY);

        return userInputIDs.toArray(new String[userInputIDs.size()]);
    }

    @Override
    protected boolean isEmptyImpl() {
        return getUserData(INFINITIVE_BE_KEY).isEmpty() &&
                getUserData(INFINITIVE_AE_KEY).isEmpty() &&
                getUserData(PAST_TENSE_BE_KEY).isEmpty() &&
                getUserData(PAST_TENSE_AE_KEY).isEmpty() &&
                getUserData(PAST_PARTICIPLE_BE_KEY).isEmpty() &&
                getUserData(PAST_PARTICIPLE_AE_KEY).isEmpty();
    }

    @Override
    protected AbstractUserInput createNewUserInputObject() {
        return new EnglishVerbUserInput();
    }

    @Override
    public String getHTMLVersion() {
        TextFormatter formatter = new TextFormatter(new HTMLTextFormatter());
        List<String> beValues = new LinkedList<>();
        if (isDataDefinedFor(INFINITIVE_BE_KEY))
            beValues.add(getPurgedUserData(INFINITIVE_BE_KEY));
        if (isIrregularBE() && isDataDefinedFor(PAST_TENSE_BE_KEY))
            beValues.add(getResolvedAndPurgedUserData(PAST_TENSE_BE_KEY));
        if (isIrregularBE() && isDataDefinedFor(PAST_PARTICIPLE_BE_KEY))
            beValues.add(getResolvedAndPurgedUserData(PAST_PARTICIPLE_BE_KEY));

        if (!beValues.isEmpty()) {
            for (String value : beValues) {
                formatter.appendBold(value).append(", ");
            }
            formatter.setLength(formatter.getLength() - 2);
            if (getStandardInputBE().isPhoneticsDefined())
                formatter.append(" [").append(getStandardInputBE().getPhonetics()).append("]");
            formatter.appendItalic(" verb");
        }

        List<String> aeValues = new LinkedList<>();
        if (isDataDefinedFor(INFINITIVE_AE_KEY))
            aeValues.add(getPurgedUserData(INFINITIVE_AE_KEY));
        if (isIrregularAE() && isDataDefinedFor(PAST_TENSE_AE_KEY))
            aeValues.add(getResolvedAndPurgedUserData(PAST_TENSE_AE_KEY));
        if (isIrregularAE() && isDataDefinedFor(PAST_PARTICIPLE_AE_KEY))
            aeValues.add(getResolvedAndPurgedUserData(PAST_PARTICIPLE_AE_KEY));

        if (!beValues.isEmpty() && !aeValues.isEmpty())
            formatter.appendItalic(" (BrE)").append(" / ");

        if (!aeValues.isEmpty()) {
            for (String value : aeValues) {
                formatter.appendBold(value).append(", ");
            }
            formatter.setLength(formatter.getLength() - 2);
            if (getStandardInputAE().isPhoneticsDefined())
                formatter.append(" [").append(getStandardInputAE().getPhonetics()).append("]");
            if (beValues.isEmpty()) formatter.appendItalic(" verb");
            formatter.appendItalic(" (AmE)");
        }

        if (getStandardInputBE().isAnyTextInputDefined()) {
            getStandardInputBE().getHTMLVersion(formatter, "(BrE)");
        }
        if (getStandardInputAE().isAnyTextInputDefined()) {
            getStandardInputAE().getHTMLVersion(formatter, "(AmE)");
        }

        return formatter.getFormattedText().toString();
    }

    @Override
    public VocableVerificationData getQuizVerificationData() {
        VocableVerificationData result = new VocableVerificationData();
        // TODO: i18n
        String additionalQuestionText = "Bitte auch die Zeitform%s %s%s angeben. ";
        List<TermDataInterface> beValues = new LinkedList<>();
        if (isDataDefinedFor(INFINITIVE_BE_KEY))
            beValues.add(getUserData(INFINITIVE_BE_KEY));
        if (isIrregularBE() && isDataDefinedFor(PAST_TENSE_BE_KEY))
            beValues.add(getUserData(PAST_TENSE_BE_KEY));
        if (isIrregularBE() && isDataDefinedFor(PAST_PARTICIPLE_BE_KEY))
            beValues.add(getUserData(PAST_PARTICIPLE_BE_KEY));

        List<TermDataInterface> aeValues = new LinkedList<>();
        if (isDataDefinedFor(INFINITIVE_AE_KEY))
            aeValues.add(getUserData(INFINITIVE_AE_KEY));
        if (isIrregularAE() && isDataDefinedFor(PAST_TENSE_AE_KEY))
            aeValues.add(getUserData(PAST_TENSE_AE_KEY));
        if (isIrregularAE() && isDataDefinedFor(PAST_PARTICIPLE_AE_KEY))
            aeValues.add(getUserData(PAST_PARTICIPLE_AE_KEY));

        if (!beValues.isEmpty()) {
            if (isIrregularBE()) {
                if (isDataDefinedFor(PAST_TENSE_BE_KEY) && isDataDefinedFor(PAST_PARTICIPLE_BE_KEY)) {
                    // TODO: i18n
                    additionalQuestionText = String.format(additionalQuestionText, "en", "<I>Past Tense</I>",
                            " und <I>Past Participle</I>");
                } else if (isDataDefinedFor(PAST_TENSE_BE_KEY)) {
                    // TODO: i18n
                    additionalQuestionText = String.format(additionalQuestionText, "", "<I>Past Tense</I>", "");
                } else if (isDataDefinedFor(PAST_PARTICIPLE_BE_KEY)) {
                    // TODO: i18n
                    additionalQuestionText = String.format(additionalQuestionText, "", "<I>Past Participle</I>", "");
                }
            } else additionalQuestionText = "";
            result.addMandatoryTerm(beValues.get(0));
            for (int i = 1; i < beValues.size(); ++i) {
                if (isIrregularBE())
                    result.addMandatoryTerm(beValues.get(i));
                else
                    result.addOptionalTerm(beValues.get(i));
            }

            // TODO: i18n
            additionalQuestionText = String.format("%sGefragt ist <i>britisches</i> Englisch.", additionalQuestionText);
        }

        // if there is only data in American English make the first piece of data mandatory
        if (beValues.isEmpty() && !aeValues.isEmpty()) {
            if (isIrregularAE()) {
                if (isDataDefinedFor(PAST_TENSE_AE_KEY) && isDataDefinedFor(PAST_PARTICIPLE_AE_KEY)) {
                    // TODO: i18n
                    additionalQuestionText = String.format(additionalQuestionText, "en", "<I>Past Tense</I>",
                            " und <I>Past Participle</I>");
                } else if (isDataDefinedFor(PAST_TENSE_AE_KEY)) {
                    // TODO: i18n
                    additionalQuestionText = String.format(additionalQuestionText, "", "<I>Past Tense</I>", "");
                } else if (isDataDefinedFor(PAST_PARTICIPLE_AE_KEY)) {
                    // TODO: i18n
                    additionalQuestionText = String.format(additionalQuestionText, "", "<I>Past Participle</I>", "");
                }
            } else additionalQuestionText = "";
            result.addMandatoryTerm(aeValues.get(0));
            additionalQuestionText = String.format("%sGefragt ist <i>amerikanisches</i> Englisch.", additionalQuestionText);
        } else if (!beValues.isEmpty() && !aeValues.isEmpty()) {
            // if there is both British and American English data defined
            result.addOptionalTerm(aeValues.get(0));
        }

        for (int i = 1; i < aeValues.size(); ++i) {
            if (isIrregularAE() && beValues.isEmpty()) {
                result.addMandatoryTerm(aeValues.get(i));
            } else {
                result.addOptionalTerm(aeValues.get(i));
            }
        }

        result.setAdditonalQuestionText(additionalQuestionText);
        return result;
    }

    @Override
    public String getShortVersion() {
        StringBuilder buf = new StringBuilder();
        List<String> beValues = new LinkedList<>();
        if (isDataDefinedFor(INFINITIVE_BE_KEY))
            beValues.add(getPurgedUserData(INFINITIVE_BE_KEY));
        if (isIrregularBE() && isDataDefinedFor(PAST_TENSE_BE_KEY))
            beValues.add(getResolvedAndPurgedUserData(PAST_TENSE_BE_KEY));
        if (isIrregularBE() && isDataDefinedFor(PAST_PARTICIPLE_BE_KEY))
            beValues.add(getResolvedAndPurgedUserData(PAST_PARTICIPLE_BE_KEY));

        if (!beValues.isEmpty()) {
            for (String value : beValues) {
                buf.append(value).append(", ");
            }
            buf.setLength(buf.length() - 2);
        }

        List<String> aeValues = new LinkedList<>();
        if (isDataDefinedFor(INFINITIVE_AE_KEY))
            aeValues.add(getPurgedUserData(INFINITIVE_AE_KEY));
        if (isIrregularAE() && isDataDefinedFor(PAST_TENSE_AE_KEY))
            aeValues.add(getResolvedAndPurgedUserData(PAST_TENSE_AE_KEY));
        if (isIrregularAE() && isDataDefinedFor(PAST_PARTICIPLE_AE_KEY))
            aeValues.add(getResolvedAndPurgedUserData(PAST_PARTICIPLE_AE_KEY));

        if (!beValues.isEmpty() && !aeValues.isEmpty())
            buf.append(" (BrE) / ");

        if (!aeValues.isEmpty()) {
            for (String value : aeValues) {
                buf.append(value).append(", ");
            }
            buf.setLength(buf.length() - 2);
            buf.append(" (AmE)");
        }

        return buf.toString();
    }

    public String getInfinitiveBE() {
        return getUserEnteredTerm(INFINITIVE_BE_KEY);
    }

    public void setInfinitiveBE(String infinitive) {
        addUserData(INFINITIVE_BE_KEY, infinitive);
    }

    public String getInfinitiveAE() {
        return getUserEnteredTerm(INFINITIVE_AE_KEY);
    }

    public void setInfinitiveAE(String infinitive) {
        addUserData(INFINITIVE_AE_KEY, infinitive);
    }

    public String getPastTenseBE() {
        return getUserEnteredTerm(PAST_TENSE_BE_KEY);
    }

    public void setPastTenseBE(String pastTense) {
        addUserData(PAST_TENSE_BE_KEY, pastTense);
    }

    public String getPastTenseAE() {
        return getUserEnteredTerm(PAST_TENSE_AE_KEY);
    }

    public void setPastTenseAE(String pastTense) {
        addUserData(PAST_TENSE_AE_KEY, pastTense);
    }

    public String getPastParticipleBE() {
        return getUserEnteredTerm(PAST_PARTICIPLE_BE_KEY);
    }

    public void setPastParticipleBE(String pastParticiple) {
        addUserData(PAST_PARTICIPLE_BE_KEY, pastParticiple);
    }

    public String getPastParticipleAE() {
        return getUserEnteredTerm(PAST_PARTICIPLE_AE_KEY);
    }

    public void setPastParticipleAE(String pastParticiple) {
        addUserData(PAST_PARTICIPLE_AE_KEY, pastParticiple);
    }

    public boolean isIrregularBE() {
        return getUserEnteredTerm(IRREGULAR_BE_KEY).equals("1");
    }

    public void setIrregularBE(boolean irregular) {
        addUserData(IRREGULAR_BE_KEY, irregular ? "1" : "0");
    }

    public boolean isIrregularAE() {
        return getUserEnteredTerm(IRREGULAR_AE_KEY).equals("1");
    }

    public void setIrregularAE(boolean irregular) {
        addUserData(IRREGULAR_AE_KEY, irregular ? "1" : "0");
    }

    @Override
    public void init() {
        setWordStem(INFINITIVE_BE_KEY);
        addWordStemChild(INFINITIVE_BE_KEY, PAST_TENSE_BE_KEY);
        addWordStemChild(INFINITIVE_BE_KEY, PAST_PARTICIPLE_BE_KEY);

        setWordStem(INFINITIVE_AE_KEY);
        addWordStemChild(INFINITIVE_AE_KEY, PAST_TENSE_AE_KEY);
        addWordStemChild(INFINITIVE_AE_KEY, PAST_PARTICIPLE_AE_KEY);
    }
}
