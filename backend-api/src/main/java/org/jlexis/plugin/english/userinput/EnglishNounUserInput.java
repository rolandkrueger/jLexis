/*
 * Created on 26.10.2009.
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
import org.jlexis.data.vocable.terms.AbstractTerm;
import org.jlexis.data.vocable.verification.VocableVerificationData;
import org.jlexis.data.vocable.verification.VocableVerificationData.DataWithMandatoryTermsBuilder;
import org.jlexis.roklib.HTMLTextFormatter;
import org.jlexis.roklib.TextFormatter;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Roland Krueger
 * @version $Id: EnglishNounUserInput.java 198 2009-12-14 11:06:39Z roland $
 */
public class EnglishNounUserInput extends AbstractEnglishPluginUserInput {
    private static final String INPUT_ID = EnglishNounUserInput.class.getCanonicalName();
    public static final String NOUN_SINGULAR_TERM_KEY_BE = INPUT_ID + ".SINGULAR_TERM_BE";
    public static final String NOUN_PLURAL_TERM_KEY_BE = INPUT_ID + ".PLURAL_TERM_BE";
    public static final String COUNTABILITY_KEY_BE = INPUT_ID + ".COUNTABILITY_KEY_BE";
    public static final String SINGULAR_PLURAL_TYPE_KEY_BE = INPUT_ID + ".SINGULAR_PLURAL_TYPE_KEY_BE";
    public static final String IRREGULAR_PLURAL_KEY_BE = INPUT_ID + ".IRREGULAR_PLURAL_BE";
    public static final String IRREGULAR_PLURAL_PHONETICS_KEY_BE = INPUT_ID + ".IRREGULAR_PLURAL_PHONETICS_BE";

    public static final String NOUN_SINGULAR_TERM_KEY_AE = INPUT_ID + ".SINGULAR_TERM_AE";
    public static final String NOUN_PLURAL_TERM_KEY_AE = INPUT_ID + ".PLURAL_TERM_AE";
    public static final String COUNTABILITY_KEY_AE = INPUT_ID + ".COUNTABILITY_KEY_AE";
    public static final String SINGULAR_PLURAL_TYPE_KEY_AE = INPUT_ID + ".SINGULAR_PLURAL_TYPE_KEY_AE";
    public static final String IRREGULAR_PLURAL_KEY_AE = INPUT_ID + ".IRREGULAR_PLURAL_AE";
    public static final String IRREGULAR_PLURAL_PHONETICS_KEY_AE = INPUT_ID + ".IRREGULAR_PLURAL_PHONETICS_AE";

    public EnglishNounUserInput() {
        super(INPUT_ID);
    }

    @Override
    public void init() {
        setPluralIrregularBE(false);
        setPluralIrregularAE(false);
        setCountabilityBE(Countability.UNSPECIFIED);
        setCountabilityAE(Countability.UNSPECIFIED);
        setSingularPluralTypeBE(SingularPluralForm.UNSPECIFIED);
        setSingularPluralTypeAE(SingularPluralForm.UNSPECIFIED);
    }

    ;

    @Override
    protected String[] getUserInputIdentifiersImpl() {
        return new String[]{NOUN_SINGULAR_TERM_KEY_BE, NOUN_PLURAL_TERM_KEY_BE,
                COUNTABILITY_KEY_BE, SINGULAR_PLURAL_TYPE_KEY_BE, IRREGULAR_PLURAL_KEY_BE, IRREGULAR_PLURAL_PHONETICS_KEY_BE,
                NOUN_SINGULAR_TERM_KEY_AE, NOUN_PLURAL_TERM_KEY_AE, COUNTABILITY_KEY_AE,
                SINGULAR_PLURAL_TYPE_KEY_AE, IRREGULAR_PLURAL_KEY_AE, IRREGULAR_PLURAL_PHONETICS_KEY_AE};
    }

    ;

    @Override
    protected AbstractUserInput createNewUserInputObject() {
        return new EnglishNounUserInput();
    }

    @Override
    public String getHTMLVersion() {
        TextFormatter formatter = new TextFormatter(new HTMLTextFormatter());
        if (isDataDefinedFor(NOUN_SINGULAR_TERM_KEY_BE)) {
            formatter.appendBold(getPurgedUserData(NOUN_SINGULAR_TERM_KEY_BE));
            if (getStandardInputBE().isPhoneticsDefined())
                formatter.append(" [").append(getStandardInputBE().getPhonetics()).append("]");
            formatter.appendItalic(" noun");
            if (getCountabilityBE() != Countability.UNSPECIFIED || getSingularPluralTypeBE() != SingularPluralForm.UNSPECIFIED)
                formatter.append(" [");
            if (getCountabilityBE() != Countability.UNSPECIFIED) {
                formatter.append(getCountabilityBE() == Countability.COUNTABLE ? "C" : "U");
            }
            if (getSingularPluralTypeBE() != SingularPluralForm.UNSPECIFIED) {
                if (getCountabilityBE() != Countability.UNSPECIFIED) formatter.append(", ");
                formatter.appendItalic(getSingularPluralTypeBE() == SingularPluralForm.PLURAL ? "pl." : "sing.");
            }
            if (getCountabilityBE() != Countability.UNSPECIFIED || getSingularPluralTypeBE() != SingularPluralForm.UNSPECIFIED)
                formatter.append("]");
        }
        if (isDataDefinedFor(NOUN_PLURAL_TERM_KEY_BE) && isPluralIrregularBE()) {
            if (isDataDefinedFor(NOUN_SINGULAR_TERM_KEY_BE)) formatter.append(" ");
            formatter.append("(").appendItalic("pl. ").appendBold(getPurgedUserData(NOUN_PLURAL_TERM_KEY_BE));
            if (isDataDefinedFor(IRREGULAR_PLURAL_PHONETICS_KEY_BE)) {
                formatter.append(" [").append(getPluralPhoneticsBE()).append("]");
            }
            formatter.append(")");
        }
        if (isDataDefinedFor(NOUN_SINGULAR_TERM_KEY_AE) &&
                isDataDefinedFor(NOUN_SINGULAR_TERM_KEY_BE)) {
            formatter.appendItalic(" (BrE)").append(" / ");
        }

        if (isDataDefinedFor(NOUN_SINGULAR_TERM_KEY_AE)) {
            formatter.appendBold(getPurgedUserData(NOUN_SINGULAR_TERM_KEY_AE));
            if (getAmericanStandardValues().isPhoneticsDefined())
                formatter.append(" [").append(getStandardInputAE().getPhonetics()).append("]");
            if (!isDataDefinedFor(NOUN_SINGULAR_TERM_KEY_BE)) {
                formatter.appendItalic(" noun");
            }
            if (getCountabilityAE() != Countability.UNSPECIFIED || getSingularPluralTypeAE() != SingularPluralForm.UNSPECIFIED)
                formatter.append(" [");
            if (getCountabilityAE() != Countability.UNSPECIFIED) {
                formatter.append(getCountabilityAE() == Countability.COUNTABLE ? "C" : "U");
            }
            if (getSingularPluralTypeAE() != SingularPluralForm.UNSPECIFIED) {
                if (getCountabilityAE() != Countability.UNSPECIFIED) formatter.append(", ");
                formatter.appendItalic(getSingularPluralTypeAE() == SingularPluralForm.PLURAL ? "pl." : "sing.");
            }
            if (getCountabilityAE() != Countability.UNSPECIFIED || getSingularPluralTypeAE() != SingularPluralForm.UNSPECIFIED)
                formatter.append("]");
            if (isDataDefinedFor(NOUN_PLURAL_TERM_KEY_AE) && isPluralIrregularAE()) {
                if (isDataDefinedFor(NOUN_SINGULAR_TERM_KEY_AE)) formatter.append(" ");
                formatter.append("(").appendItalic("pl. ").appendBold(getPurgedUserData(NOUN_PLURAL_TERM_KEY_AE));
                if (isDataDefinedFor(IRREGULAR_PLURAL_PHONETICS_KEY_AE)) {
                    formatter.append(" [").append(getPluralPhoneticsAE()).append("]");
                }
                formatter.append(")");
            }
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
    public String getShortVersion() {
        StringBuilder buf = new StringBuilder();
        buf.append(getPurgedUserData(NOUN_SINGULAR_TERM_KEY_BE));
        if (isDataDefinedFor(NOUN_PLURAL_TERM_KEY_BE) && isPluralIrregularBE()) {
            if (isDataDefinedFor(NOUN_SINGULAR_TERM_KEY_BE)) buf.append(", ");
            buf.append(getPurgedUserData(NOUN_PLURAL_TERM_KEY_BE));
        }
        if (isDataDefinedFor(NOUN_SINGULAR_TERM_KEY_AE) &&
                isDataDefinedFor(NOUN_SINGULAR_TERM_KEY_BE))
            buf.append(" (BrE) / ");

        if (isDataDefinedFor(NOUN_SINGULAR_TERM_KEY_AE)) {
            buf.append(getPurgedUserData(NOUN_SINGULAR_TERM_KEY_AE));
            if (isDataDefinedFor(NOUN_PLURAL_TERM_KEY_AE) && isPluralIrregularAE()) {
                if (isDataDefinedFor(NOUN_SINGULAR_TERM_KEY_AE)) buf.append(", ");
                buf.append(getPurgedUserData(NOUN_PLURAL_TERM_KEY_AE));
            }
            buf.append(" (AmE)");
        }

        return buf.toString();
    }

    public boolean isPluralIrregularAE() {
        if (!isDataDefinedFor(IRREGULAR_PLURAL_KEY_AE)) return false;
        boolean result = false;
        try {
            result = IrregularPlural.valueOf(
                    getUserData(IRREGULAR_PLURAL_KEY_AE).getUserEnteredString()).equals(IrregularPlural.IRREGULAR);
        } catch (IllegalArgumentException iaExc) {
            return false;
        }

        return result;
    }

    public void setPluralIrregularAE(boolean irregular) {
        addUserInput(IRREGULAR_PLURAL_KEY_AE, irregular ? IrregularPlural.IRREGULAR.toString()
                : IrregularPlural.REGULAR.toString());
    }

    public boolean isPluralIrregularBE() {
        if (!isDataDefinedFor(IRREGULAR_PLURAL_KEY_BE)) return false;
        boolean result = false;
        try {
            result = IrregularPlural.valueOf(
                    getUserData(IRREGULAR_PLURAL_KEY_BE).getUserEnteredString()).equals(IrregularPlural.IRREGULAR);
        } catch (IllegalArgumentException iaExc) {
            return false;
        }

        return result;
    }

    public void setPluralIrregularBE(boolean irregular) {
        addUserInput(IRREGULAR_PLURAL_KEY_BE, irregular ? IrregularPlural.IRREGULAR.toString()
                : IrregularPlural.REGULAR.toString());
    }

    public String getPluralPhoneticsBE() {
        return getUserEnteredTerm(IRREGULAR_PLURAL_PHONETICS_KEY_BE);
    }

    public void setPluralPhoneticsBE(String phonetics) {
        addUserInput(IRREGULAR_PLURAL_PHONETICS_KEY_BE, phonetics);
    }

    public String getPluralPhoneticsAE() {
        return getUserEnteredTerm(IRREGULAR_PLURAL_PHONETICS_KEY_AE);
    }

    public void setPluralPhoneticsAE(String phonetics) {
        addUserInput(IRREGULAR_PLURAL_PHONETICS_KEY_AE, phonetics);
    }

    public String getSingularBE() {
        return getUserEnteredTerm(NOUN_SINGULAR_TERM_KEY_BE);
    }

    public void setSingularBE(String noun) {
        addUserInput(NOUN_SINGULAR_TERM_KEY_BE, noun);
    }

    public String getSingularAE() {
        return getUserEnteredTerm(NOUN_SINGULAR_TERM_KEY_AE);
    }

    public void setSingularAE(String noun) {
        addUserInput(NOUN_SINGULAR_TERM_KEY_AE, noun);
    }

    public String getPluralBE() {
        return getUserEnteredTerm(NOUN_PLURAL_TERM_KEY_BE);
    }

    public void setPluralBE(String noun) {
        addUserInput(NOUN_PLURAL_TERM_KEY_BE, noun);
    }

    public String getPluralAE() {
        return getUserEnteredTerm(NOUN_PLURAL_TERM_KEY_AE);
    }

    public void setPluralAE(String noun) {
        addUserInput(NOUN_PLURAL_TERM_KEY_AE, noun);
    }

    public Countability getCountabilityBE() {
        Countability result;
        try {
            result = Countability.valueOf(getUserEnteredTerm(COUNTABILITY_KEY_BE));
        } catch (IllegalArgumentException nfExc) {
            return Countability.UNSPECIFIED;
        }
        return result;
    }

    public void setCountabilityBE(Countability countability) {
        addUserInput(COUNTABILITY_KEY_BE, countability.toString());
    }

    public Countability getCountabilityAE() {
        Countability result;
        try {
            result = Countability.valueOf(getUserEnteredTerm(COUNTABILITY_KEY_AE));
        } catch (IllegalArgumentException nfExc) {
            return Countability.UNSPECIFIED;
        }
        return result;
    }

    public void setCountabilityAE(Countability countability) {
        addUserInput(COUNTABILITY_KEY_AE, countability.toString());
    }

    public SingularPluralForm getSingularPluralTypeBE() {
        SingularPluralForm result;
        try {
            result = SingularPluralForm.valueOf(getUserEnteredTerm(SINGULAR_PLURAL_TYPE_KEY_BE));
        } catch (IllegalArgumentException nfExc) {
            return SingularPluralForm.UNSPECIFIED;
        }
        return result;
    }

    public void setSingularPluralTypeBE(SingularPluralForm singularPluralType) {
        addUserInput(SINGULAR_PLURAL_TYPE_KEY_BE, singularPluralType.toString());
    }

    public SingularPluralForm getSingularPluralTypeAE() {
        SingularPluralForm result;
        try {
            result = SingularPluralForm.valueOf(getUserEnteredTerm(SINGULAR_PLURAL_TYPE_KEY_AE));
        } catch (IllegalArgumentException nfExc) {
            return SingularPluralForm.UNSPECIFIED;
        }
        return result;
    }

    public void setSingularPluralTypeAE(SingularPluralForm singularPluralType) {
        addUserInput(SINGULAR_PLURAL_TYPE_KEY_AE, singularPluralType.toString());
    }

    @Override
    protected boolean isEmptyImpl() {
        return getUserData(NOUN_SINGULAR_TERM_KEY_BE).isEmpty() &&
                getUserData(NOUN_PLURAL_TERM_KEY_BE).isEmpty() &&
                getUserData(NOUN_SINGULAR_TERM_KEY_AE).isEmpty() &&
                getUserData(NOUN_PLURAL_TERM_KEY_AE).isEmpty() &&
                getUserData(IRREGULAR_PLURAL_PHONETICS_KEY_BE).isEmpty() &&
                getUserData(IRREGULAR_PLURAL_PHONETICS_KEY_AE).isEmpty();
    }

    @Override
    public VocableVerificationData getQuizVerificationData() {
        final DataWithMandatoryTermsBuilder builder = VocableVerificationData.create().withoutAbbreviationVariants();
        // TODO: I18N
//    String additionalQuestionText = "Bitte auch die Pluralform eingeben. ";
        String additionalQuestionText = "Please also provide the plural form. ";
        List<AbstractTerm> beValues = new LinkedList<>();
        if (isDataDefinedFor(NOUN_SINGULAR_TERM_KEY_BE))
            beValues.add(getUserData(NOUN_SINGULAR_TERM_KEY_BE));
        if (isDataDefinedFor(NOUN_PLURAL_TERM_KEY_BE))
            beValues.add(getUserData(NOUN_PLURAL_TERM_KEY_BE));
        List<AbstractTerm> aeValues = new LinkedList<>();
        if (isDataDefinedFor(NOUN_SINGULAR_TERM_KEY_AE))
            aeValues.add(getUserData(NOUN_SINGULAR_TERM_KEY_AE));
        if (isDataDefinedFor(NOUN_PLURAL_TERM_KEY_AE))
            aeValues.add(getUserData(NOUN_PLURAL_TERM_KEY_AE));

        if (!beValues.isEmpty()) {
            if (!isDataDefinedFor(NOUN_PLURAL_TERM_KEY_BE)) additionalQuestionText = "";
            builder.addMandatoryTerm(beValues.get(0));
            for (int i = 1; i < beValues.size(); ++i) {
                if (isPluralIrregularBE()) {
                    builder.addMandatoryTerm(beValues.get(i));
                } else {
                    additionalQuestionText = "";
                    builder.addOptionalTerm(beValues.get(i));
                }
            }
            // TODO: I18N
//      additionalQuestionText = String.format ("%sGefragt ist <i>britisches</i> Englisch.", additionalQuestionText);
            additionalQuestionText = String.format("%s<i>British</i> English is expected.", additionalQuestionText);
        }

        // if there is only data in American English make the first piece of data mandatory
        if (beValues.isEmpty() && !aeValues.isEmpty()) {
            builder.addMandatoryTerm(aeValues.get(0));
            if (!isPluralIrregularAE() || !isDataDefinedFor(NOUN_PLURAL_TERM_KEY_AE)) {
                additionalQuestionText = "";
            }
//      additionalQuestionText = String.format ("%sGefragt ist <i>amerikanisches</i> Englisch.", additionalQuestionText);
            additionalQuestionText = String.format("%s<i>American</i> English is expected.", additionalQuestionText);
        } else if (!beValues.isEmpty() && !aeValues.isEmpty()) {
            // if there is both British and American English data defined
            builder.addOptionalTerm(aeValues.get(0));
        }

        for (int i = 1; i < aeValues.size(); ++i) {
            if (isPluralIrregularAE() && beValues.isEmpty()) {
                builder.addMandatoryTerm(aeValues.get(i));
            } else {
                // make the remaining values for American English optional
                builder.addOptionalTerm(aeValues.get(i));
            }
        }

        final VocableVerificationData result = builder.build();
        result.setAdditionalQuestionText(additionalQuestionText);
        return result;
    }

    private enum IrregularPlural {REGULAR, IRREGULAR}

    public enum Countability {UNSPECIFIED, COUNTABLE, UNCOUNTABLE}

    public enum SingularPluralForm {UNSPECIFIED, SINGULAR, PLURAL}
}
