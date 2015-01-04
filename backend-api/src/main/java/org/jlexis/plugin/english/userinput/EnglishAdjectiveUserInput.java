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

import org.jlexis.data.vocable.RegisteredVocableDataKey;
import org.jlexis.data.vocable.terms.AbstractTerm;
import org.jlexis.data.vocable.userinput.AbstractUserInput;
import org.jlexis.data.vocable.userinput.standard.StandardAdjectiveUserInputDecorator;
import org.jlexis.data.vocable.verification.VocableVerificationData;
import org.jlexis.data.vocable.verification.VocableVerificationData.DataWithMandatoryTermsBuilder;
import org.jlexis.roklib.TextFormatter;

import java.util.LinkedList;
import java.util.List;

public class EnglishAdjectiveUserInput extends AbstractEnglishPluginUserInput {
    private final static String INPUT_ID = EnglishAdjectiveUserInput.class.getCanonicalName();
    private final static RegisteredVocableDataKey ADJECTIVE_USAGE_BE = new RegisteredVocableDataKey(INPUT_ID + "" +
            ".ADJECTIVE_USAGE_BE");
    private final static RegisteredVocableDataKey ADJECTIVE_USAGE_AE = new RegisteredVocableDataKey(INPUT_ID + "" +
            ".ADJECTIVE_USAGE_AE");
    private static final long serialVersionUID = - 7091084877733059407L;
    private StandardAdjectiveUserInputDecorator adjectiveStandardInputBE;
    private StandardAdjectiveUserInputDecorator adjectiveStandardInputAE;

    public EnglishAdjectiveUserInput() {
        super(INPUT_ID);
        adjectiveStandardInputBE = new StandardAdjectiveUserInputDecorator(this, "BE");
        adjectiveStandardInputAE = new StandardAdjectiveUserInputDecorator(this, "AE");
    }

    @Override
    protected boolean isEmptyImpl() {
        return adjectiveStandardInputBE.isEmpty() &&
                adjectiveStandardInputAE.isEmpty();
    }

    @Override
    public AbstractUserInput createUserInputObject() {
        return new EnglishAdjectiveUserInput();
    }

    @Override
    public void provideFullDisplayText(TextFormatter formatter) {
        formatter.appendBold(adjectiveStandardInputBE.getPositiveResolvedAndPurged());
        if (adjectiveStandardInputBE.isComparativeDataDefined())
            formatter.append(", ").appendItalic("comp. ").appendBold(adjectiveStandardInputBE.getComparativeResolvedAndPurged());
        if (adjectiveStandardInputBE.isSuperlativeDataDefined())
            formatter.append(", ").appendItalic("superl. ").appendBold(adjectiveStandardInputBE.getSuperlativeResolvedAndPurged());
        if (getStandardInputBE().isPhoneticsDefined())
            formatter.append(" [").append(getStandardInputBE().getPhonetics()).append("]");
        if (adjectiveStandardInputBE.isAnyTextInputDefined()) formatter.appendItalic(" adj.");
        if (getAdjectiveUsageBE() != AdjectiveUsage.UNSPECIFIED)
            formatter.append(" [").appendItalic(getAdjectiveUsageBE().getDisplayString()).append("] ");
        if (adjectiveStandardInputAE.isAnyTextInputDefined()) {
            if (adjectiveStandardInputBE.isAnyTextInputDefined())
                formatter.appendItalic(" (BrE)").append(" / ");
            formatter.appendBold(adjectiveStandardInputAE.getPositiveResolvedAndPurged());
            if (adjectiveStandardInputAE.isComparativeDataDefined())
                formatter.append(", ").appendItalic("comp. ").appendBold(adjectiveStandardInputAE.getComparativeResolvedAndPurged());
            if (adjectiveStandardInputAE.isSuperlativeDataDefined())
                formatter.append(", ").appendItalic("superl. ").appendBold(adjectiveStandardInputAE.getSuperlative());
            if (getStandardInputAE().isPhoneticsDefined())
                formatter.append(" [").append(getStandardInputAE().getPhonetics()).append("]");
            if (! adjectiveStandardInputBE.isAnyTextInputDefined())
                formatter.appendItalic(" adj.");
            if (getAdjectiveUsageAE() != AdjectiveUsage.UNSPECIFIED)
                formatter.append(" [").appendItalic(getAdjectiveUsageAE().getDisplayString()).append("] ");
            formatter.appendItalic(" (AmE)");
        }

        if (getStandardInputBE().isAnyTextInputDefined()) {
            getStandardInputBE().provideFullDisplayText(formatter);//, "(BrE)");
        }
        if (getStandardInputAE().isAnyTextInputDefined()) {
            getStandardInputAE().provideFullDisplayText(formatter);//, "(AmE)");
        }

        // FIXME: result for old signature
//        return formatter.getFormattedText().toString();
    }

    @Override
    public boolean isAnyTextInputDefined() {
        return getStandardInputBE().isAnyTextInputDefined() || getStandardInputAE().isAnyTextInputDefined() ||
                adjectiveStandardInputBE.isAnyTextInputDefined() || adjectiveStandardInputAE.isAnyTextInputDefined();
    }

    @Override
    public VocableVerificationData getQuizVerificationData() {
        final DataWithMandatoryTermsBuilder builder = VocableVerificationData.create().withoutAbbreviationVariants();
        // TODO: i18n
        String additionalQuestionText = "Bitte auch %s%s eingeben. ";
        List<AbstractTerm> beValues = new LinkedList<>();
        beValues.addAll(adjectiveStandardInputBE.getTermData());
        List<AbstractTerm> aeValues = new LinkedList<>();
        aeValues.addAll(adjectiveStandardInputAE.getTermData());

        if (!beValues.isEmpty()) {
            builder.addMandatoryTerm(beValues.get(0));
            if (isIrregularBE()) {
                if (adjectiveStandardInputBE.isComparativeDataDefined() &&
                        adjectiveStandardInputBE.isSuperlativeDataDefined()) {
                    // TODO: i18n
                    additionalQuestionText = String.format(additionalQuestionText, "Komparativ", " und Superlativ");
                } else if (adjectiveStandardInputBE.isComparativeDataDefined()) {
                    // TODO: i18n
                    additionalQuestionText = String.format(additionalQuestionText, "den Komparativ", "");
                } else if (adjectiveStandardInputBE.isSuperlativeDataDefined()) {
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
                if (adjectiveStandardInputAE.isComparativeDataDefined() &&
                        adjectiveStandardInputAE.isSuperlativeDataDefined()) {
                    // TODO: i18n
                    additionalQuestionText = String.format(additionalQuestionText, "Komparativ", " und Superlativ");
                } else if (adjectiveStandardInputAE.isComparativeDataDefined()) {
                    // TODO: i18n
                    additionalQuestionText = String.format(additionalQuestionText, "den Komparativ", "");
                } else if (adjectiveStandardInputAE.isSuperlativeDataDefined()) {
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

        final VocableVerificationData result = builder.build();
        result.setAdditionalQuestionText(additionalQuestionText);
        return result;
    }

    @Override
    public void provideShortDisplayText(TextFormatter formatter) {
        StringBuilder buf = new StringBuilder();
        buf.append(adjectiveStandardInputBE.getPositiveResolvedAndPurged());
        if (adjectiveStandardInputBE.isComparativeDataDefined())
            buf.append(", ").append(adjectiveStandardInputBE.getComparativeResolvedAndPurged());
        if (adjectiveStandardInputBE.isSuperlativeDataDefined())
            buf.append(", ").append(adjectiveStandardInputBE.getSuperlativeResolvedAndPurged());
        if (adjectiveStandardInputAE.isAnyTextInputDefined()) {
            if (adjectiveStandardInputBE.isAnyTextInputDefined())
                buf.append(" (BrE) / ");
            if (adjectiveStandardInputAE.isPositiveDataDefined())
                buf.append(adjectiveStandardInputAE.getPositiveResolvedAndPurged());
            if (adjectiveStandardInputAE.isComparativeDataDefined())
                buf.append(", ").append(adjectiveStandardInputAE.getComparativeResolvedAndPurged());
            if (adjectiveStandardInputAE.isSuperlativeDataDefined())
                buf.append(", ").append(adjectiveStandardInputAE.getSuperlativeResolvedAndPurged());
            buf.append(" (AmE)");
        }

        // FIXME: result for old signature
//        return buf.toString();
    }

    public String getPositiveBE() {
        return adjectiveStandardInputBE.getPositive();
    }

    public void setPositiveBE(String positive) {
        adjectiveStandardInputBE.setPositive(positive);
    }

    public String getPositiveAE() {
        return adjectiveStandardInputAE.getPositive();
    }

    public void setPositiveAE(String positive) {
        adjectiveStandardInputAE.setPositive(positive);
    }

    public String getComparativeBE() {
        return adjectiveStandardInputBE.getComparative();
    }

    public void setComparativeBE(String comparative) {
        adjectiveStandardInputBE.setComparative(comparative);
    }

    public String getComparativeAE() {
        return adjectiveStandardInputAE.getComparative();
    }

    public void setComparativeAE(String comparative) {
        adjectiveStandardInputAE.setComparative(comparative);
    }

    public String getSuperlativeBE() {
        return adjectiveStandardInputBE.getSuperlative();
    }

    public void setSuperlativeBE(String superlative) {
        adjectiveStandardInputBE.setSuperlative(superlative);
    }

    public String getSuperlativeAE() {
        return adjectiveStandardInputAE.getSuperlative();
    }

    public void setSuperlativeAE(String superlative) {
        adjectiveStandardInputAE.setSuperlative(superlative);
    }

    public boolean isIrregularBE() {
        return adjectiveStandardInputBE.isIrregular();
    }

    public void setIrregularBE(boolean irregular) {
        adjectiveStandardInputBE.setIrregular(irregular);
    }

    public boolean isIrregularAE() {
        return adjectiveStandardInputAE.isIrregular();
    }

    public void setIrregularAE(boolean irregular) {
        adjectiveStandardInputAE.setIrregular(irregular);
    }

    public boolean isNotComparableBE() {
        return adjectiveStandardInputBE.isNotComparable();
    }

    public void setNotComparableBE(boolean notComparable) {
        adjectiveStandardInputBE.setNotComparable(notComparable);
    }

    public boolean isNotComparableAE() {
        return adjectiveStandardInputAE.isNotComparable();
    }

    public void setNotComparableAE(boolean notComparable) {
        adjectiveStandardInputAE.setNotComparable(notComparable);
    }

    public AdjectiveUsage getAdjectiveUsageBE() {
        AdjectiveUsage result;
        try {
            result = AdjectiveUsage.valueOf(getUserInput(ADJECTIVE_USAGE_BE).getUserEnteredString());
        } catch (IllegalArgumentException nfExc) {
            return AdjectiveUsage.UNSPECIFIED;
        }
        return result;
    }

    public void setAdjectiveUsageBE(AdjectiveUsage usage) {
        addUserInput(ADJECTIVE_USAGE_BE, usage.toString());
    }

    public AdjectiveUsage getAdjectiveUsageAE() {
        AdjectiveUsage result;
        try {
            result = AdjectiveUsage.valueOf(getUserInput(ADJECTIVE_USAGE_AE).getUserEnteredString());
        } catch (IllegalArgumentException nfExc) {
            return AdjectiveUsage.UNSPECIFIED;
        }
        return result;
    }

    public void setAdjectiveUsageAE(AdjectiveUsage usage) {
        addUserInput(ADJECTIVE_USAGE_AE, usage.toString());
    }

    @Override
    public void init() {
        setAdjectiveUsageBE(AdjectiveUsage.UNSPECIFIED);
        setAdjectiveUsageAE(AdjectiveUsage.UNSPECIFIED);
        adjectiveStandardInputBE.init();
        adjectiveStandardInputAE.init();
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
