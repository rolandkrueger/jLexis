/*
 * Created on 20.11.2009
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
import org.jlexis.data.vocable.standarduserinput.StandardAdjectiveUserInputDataHandler;
import org.jlexis.data.vocable.terms.AbstractTermData;
import org.jlexis.data.vocable.verification.VocableVerificationData;
import org.jlexis.data.vocable.verification.VocableVerificationData.DataWithMandatoryTermsBuilder;
import org.jlexis.roklib.HTMLTextFormatter;
import org.jlexis.roklib.TextFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class EnglishAdjectiveUserInput extends AbstractEnglishPluginUserInput {
    private final static String INPUT_ID = EnglishAdjectiveUserInput.class.getCanonicalName();
    private final static String ADJECTIVE_USAGE_BE = INPUT_ID + ".ADJECTIVE_USAGE_BE";
    private final static String ADJECTIVE_USAGE_AE = INPUT_ID + ".ADJECTIVE_USAGE_AE";
    private StandardAdjectiveUserInputDataHandler mAdjectiveStandardInputBE;
    private StandardAdjectiveUserInputDataHandler mAdjectiveStandardInputAE;

    public EnglishAdjectiveUserInput() {
        super(INPUT_ID);
        mAdjectiveStandardInputBE = new StandardAdjectiveUserInputDataHandler(this, "BE");
        mAdjectiveStandardInputAE = new StandardAdjectiveUserInputDataHandler(this, "AE");
    }

    @Override
    protected String[] getUserInputIdentifiersImpl() {
        List<String> userInputIDs = new ArrayList<>(16);
        userInputIDs.addAll(Arrays.asList(mAdjectiveStandardInputBE.getUserInputIdentifiers()));
        userInputIDs.addAll(Arrays.asList(mAdjectiveStandardInputAE.getUserInputIdentifiers()));
        userInputIDs.add(ADJECTIVE_USAGE_BE);
        userInputIDs.add(ADJECTIVE_USAGE_AE);
        return userInputIDs.toArray(new String[userInputIDs.size()]);
    }

    @Override
    protected boolean isEmptyImpl() {
        return mAdjectiveStandardInputBE.isEmpty() &&
                mAdjectiveStandardInputAE.isEmpty();
    }

    @Override
    protected AbstractUserInput createNewUserInputObject() {
        return new EnglishAdjectiveUserInput();
    }

    @Override
    public String getHTMLVersion() {
        TextFormatter formatter = new TextFormatter(new HTMLTextFormatter());
        formatter.appendBold(mAdjectiveStandardInputBE.getPositiveResolvedAndPurged());
        if (mAdjectiveStandardInputBE.isComparativeDataDefined())
            formatter.append(", ").appendItalic("comp. ").appendBold(mAdjectiveStandardInputBE.getComparativeResolvedAndPurged());
        if (mAdjectiveStandardInputBE.isSuperlativeDataDefined())
            formatter.append(", ").appendItalic("superl. ").appendBold(mAdjectiveStandardInputBE.getSuperlativeResolvedAndPurged());
        if (getStandardInputBE().isPhoneticsDefined())
            formatter.append(" [").append(getStandardInputBE().getPhonetics()).append("]");
        if (mAdjectiveStandardInputBE.isAnyTextInputDefined()) formatter.appendItalic(" adj.");
        if (getAdjectiveUsageBE() != AdjectiveUsage.UNSPECIFIED)
            formatter.append(" [").appendItalic(getAdjectiveUsageBE().getDisplayString()).append("] ");
        if (mAdjectiveStandardInputAE.isAnyTextInputDefined()) {
            if (mAdjectiveStandardInputBE.isAnyTextInputDefined())
                formatter.appendItalic(" (BrE)").append(" / ");
            formatter.appendBold(mAdjectiveStandardInputAE.getPositiveResolvedAndPurged());
            if (mAdjectiveStandardInputAE.isComparativeDataDefined())
                formatter.append(", ").appendItalic("comp. ").appendBold(mAdjectiveStandardInputAE.getComparativeResolvedAndPurged());
            if (mAdjectiveStandardInputAE.isSuperlativeDataDefined())
                formatter.append(", ").appendItalic("superl. ").appendBold(mAdjectiveStandardInputAE.getSuperlative());
            if (getStandardInputAE().isPhoneticsDefined())
                formatter.append(" [").append(getStandardInputAE().getPhonetics()).append("]");
            if (!mAdjectiveStandardInputBE.isAnyTextInputDefined())
                formatter.appendItalic(" adj.");
            if (getAdjectiveUsageAE() != AdjectiveUsage.UNSPECIFIED)
                formatter.append(" [").appendItalic(getAdjectiveUsageAE().getDisplayString()).append("] ");
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
        final DataWithMandatoryTermsBuilder builder = VocableVerificationData.createFromTerms();
        // TODO: i18n
        String additionalQuestionText = "Bitte auch %s%s eingeben. ";
        List<AbstractTermData> beValues = new LinkedList<>();
        beValues.addAll(mAdjectiveStandardInputBE.getTermData());
        List<AbstractTermData> aeValues = new LinkedList<>();
        aeValues.addAll(mAdjectiveStandardInputAE.getTermData());

        if (!beValues.isEmpty()) {
            builder.addMandatoryTerm(beValues.get(0));
            if (isIrregularBE()) {
                if (mAdjectiveStandardInputBE.isComparativeDataDefined() &&
                        mAdjectiveStandardInputBE.isSuperlativeDataDefined()) {
                    // TODO: i18n
                    additionalQuestionText = String.format(additionalQuestionText, "Komparativ", " und Superlativ");
                } else if (mAdjectiveStandardInputBE.isComparativeDataDefined()) {
                    // TODO: i18n
                    additionalQuestionText = String.format(additionalQuestionText, "den Komparativ", "");
                } else if (mAdjectiveStandardInputBE.isSuperlativeDataDefined()) {
                    // TODO: i18n
                    additionalQuestionText = String.format(additionalQuestionText, "den Superlativ", "");
                } else additionalQuestionText = "";
            }
            for (int i = 1; i < beValues.size(); ++i) {
                if (isIrregularBE()) {
                    builder.addMandatoryTerm(beValues.get(i));
                } else {
                    builder.addOptionalTerm(beValues.get(i));
                    additionalQuestionText = "";
                }
            }
            additionalQuestionText = String.format("%sGefragt ist <i>britisches</i> Englisch.", additionalQuestionText);
        }

        // if there is only data in American English make the first piece of data mandatory
        if (beValues.isEmpty() && !aeValues.isEmpty()) {
            builder.addMandatoryTerm(aeValues.get(0));
            if (isIrregularAE()) {
                if (mAdjectiveStandardInputAE.isComparativeDataDefined() &&
                        mAdjectiveStandardInputAE.isSuperlativeDataDefined()) {
                    // TODO: i18n
                    additionalQuestionText = String.format(additionalQuestionText, "Komparativ", " und Superlativ");
                } else if (mAdjectiveStandardInputAE.isComparativeDataDefined()) {
                    // TODO: i18n
                    additionalQuestionText = String.format(additionalQuestionText, "den Komparativ", "");
                } else if (mAdjectiveStandardInputAE.isSuperlativeDataDefined()) {
                    // TODO: i18n
                    additionalQuestionText = String.format(additionalQuestionText, "den Superlativ", "");
                }
            } else additionalQuestionText = "";
            // TODO: i18n
            additionalQuestionText = String.format("%sGefragt ist <i>amerikanisches</i> Englisch.", additionalQuestionText);
        } else if (!beValues.isEmpty() && !aeValues.isEmpty()) {
            // if there is both British and American English data defined
            builder.addOptionalTerm(aeValues.get(0));
        }

        for (int i = 1; i < aeValues.size(); ++i) {
            if (isIrregularAE() && beValues.isEmpty()) {
                builder.addMandatoryTerm(aeValues.get(i));
            } else {
                builder.addOptionalTerm(aeValues.get(i));
            }
        }

        final VocableVerificationData result = builder.finish().build();
        result.setAdditionalQuestionText(additionalQuestionText);
        return result;
    }

    @Override
    public String getShortVersion() {
        StringBuilder buf = new StringBuilder();
        buf.append(mAdjectiveStandardInputBE.getPositiveResolvedAndPurged());
        if (mAdjectiveStandardInputBE.isComparativeDataDefined())
            buf.append(", ").append(mAdjectiveStandardInputBE.getComparativeResolvedAndPurged());
        if (mAdjectiveStandardInputBE.isSuperlativeDataDefined())
            buf.append(", ").append(mAdjectiveStandardInputBE.getSuperlativeResolvedAndPurged());
        if (mAdjectiveStandardInputAE.isAnyTextInputDefined()) {
            if (mAdjectiveStandardInputBE.isAnyTextInputDefined())
                buf.append(" (BrE) / ");
            if (mAdjectiveStandardInputAE.isPositiveDataDefined())
                buf.append(mAdjectiveStandardInputAE.getPositiveResolvedAndPurged());
            if (mAdjectiveStandardInputAE.isComparativeDataDefined())
                buf.append(", ").append(mAdjectiveStandardInputAE.getComparativeResolvedAndPurged());
            if (mAdjectiveStandardInputAE.isSuperlativeDataDefined())
                buf.append(", ").append(mAdjectiveStandardInputAE.getSuperlativeResolvedAndPurged());
            buf.append(" (AmE)");
        }
        return buf.toString();
    }

    public String getPositiveBE() {
        return mAdjectiveStandardInputBE.getPositive();
    }

    public void setPositiveBE(String positive) {
        mAdjectiveStandardInputBE.setPositive(positive);
    }

    public String getPositiveAE() {
        return mAdjectiveStandardInputAE.getPositive();
    }

    public void setPositiveAE(String positive) {
        mAdjectiveStandardInputAE.setPositive(positive);
    }

    public String getComparativeBE() {
        return mAdjectiveStandardInputBE.getComparative();
    }

    public void setComparativeBE(String comparative) {
        mAdjectiveStandardInputBE.setComparative(comparative);
    }

    public String getComparativeAE() {
        return mAdjectiveStandardInputAE.getComparative();
    }

    public void setComparativeAE(String comparative) {
        mAdjectiveStandardInputAE.setComparative(comparative);
    }

    public String getSuperlativeBE() {
        return mAdjectiveStandardInputBE.getSuperlative();
    }

    public void setSuperlativeBE(String superlative) {
        mAdjectiveStandardInputBE.setSuperlative(superlative);
    }

    public String getSuperlativeAE() {
        return mAdjectiveStandardInputAE.getSuperlative();
    }

    public void setSuperlativeAE(String superlative) {
        mAdjectiveStandardInputAE.setSuperlative(superlative);
    }

    public boolean isIrregularBE() {
        return mAdjectiveStandardInputBE.isIrregular();
    }

    public void setIrregularBE(boolean irregular) {
        mAdjectiveStandardInputBE.setIrregular(irregular);
    }

    public boolean isIrregularAE() {
        return mAdjectiveStandardInputAE.isIrregular();
    }

    public void setIrregularAE(boolean irregular) {
        mAdjectiveStandardInputAE.setIrregular(irregular);
    }

    public boolean isNotComparableBE() {
        return mAdjectiveStandardInputBE.isNotComparable();
    }

    public void setNotComparableBE(boolean notComparable) {
        mAdjectiveStandardInputBE.setNotComparable(notComparable);
    }

    public boolean isNotComparableAE() {
        return mAdjectiveStandardInputAE.isNotComparable();
    }

    public void setNotComparableAE(boolean notComparable) {
        mAdjectiveStandardInputAE.setNotComparable(notComparable);
    }

    public AdjectiveUsage getAdjectiveUsageBE() {
        AdjectiveUsage result;
        try {
            result = AdjectiveUsage.valueOf(getUserEnteredTerm(ADJECTIVE_USAGE_BE));
        } catch (IllegalArgumentException nfExc) {
            return AdjectiveUsage.UNSPECIFIED;
        }
        return result;
    }

    public void setAdjectiveUsageBE(AdjectiveUsage usage) {
        addUserData(ADJECTIVE_USAGE_BE, usage.toString());
    }

    public AdjectiveUsage getAdjectiveUsageAE() {
        AdjectiveUsage result;
        try {
            result = AdjectiveUsage.valueOf(getUserEnteredTerm(ADJECTIVE_USAGE_AE));
        } catch (IllegalArgumentException nfExc) {
            return AdjectiveUsage.UNSPECIFIED;
        }
        return result;
    }

    public void setAdjectiveUsageAE(AdjectiveUsage usage) {
        addUserData(ADJECTIVE_USAGE_AE, usage.toString());
    }

    @Override
    public void init() {
        setAdjectiveUsageBE(AdjectiveUsage.UNSPECIFIED);
        setAdjectiveUsageAE(AdjectiveUsage.UNSPECIFIED);
        mAdjectiveStandardInputBE.initWordStemFields();
        mAdjectiveStandardInputAE.initWordStemFields();
    }

    public enum AdjectiveUsage {
        UNSPECIFIED, ONLY_BEFORE_NOUN, USUALLY_BEFORE_NOUN, NOT_BEFORE_NOUN,
        NOT_USUALLY_BEFORE_NOUN, AFTER_NOUN;

        public String getDisplayString() {
            switch (this) {
                case AFTER_NOUN:
                    return "after noun";
                case NOT_BEFORE_NOUN:
                    return "not before noun";
                case NOT_USUALLY_BEFORE_NOUN:
                    return "not usually before noun";
                case ONLY_BEFORE_NOUN:
                    return "only before noun";
                case USUALLY_BEFORE_NOUN:
                    return "usually before noun";
                default:
                    return "unspecified";
            }
        }
    }
}
