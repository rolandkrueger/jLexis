/*
 * AbstractStandardUserInput
 * Created on 13.11.2009
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
package org.jlexis.data.vocable.userinput.standard;


import org.jlexis.data.vocable.userinput.AbstractUserInput;

/**
 * @author Roland Krueger
 */
public abstract class AbstractStandardUserInput extends AbstractUserInput {
    private StandardUserInputDataHandler mStandardUserInputDataHandler;

    protected AbstractStandardUserInput(String inputType) {
        super(inputType);
        mStandardUserInputDataHandler = new StandardUserInputDataHandler(this);
    }

    public StandardUserInputDataHandler getStandardUserInputDataHandler() {
        return mStandardUserInputDataHandler;
    }

    public final String getComment() {
        return mStandardUserInputDataHandler.getComment();
    }

    public final void setComment(String comment) {
        mStandardUserInputDataHandler.setComment(comment);
    }

    public final String getExample() {
        return mStandardUserInputDataHandler.getExample();
    }

    public final void setExample(String example) {
        mStandardUserInputDataHandler.setExample(example);
    }

    public final String getPhonetics() {
        return mStandardUserInputDataHandler.getPhonetics();
    }

    public final void setPhonetics(String phonetics) {
        mStandardUserInputDataHandler.setPhonetics(phonetics);
    }

    public String getPhoneticsString() {
        if (!isPhoneticsDefined()) return "";
        return "[" + getPhonetics() + "]";
    }

    public final String getPronunciation() {
        return mStandardUserInputDataHandler.getPronunciation();
    }

    public final void setPronunciation(String pronunciation) {
        mStandardUserInputDataHandler.setPronunciation(pronunciation);
    }

    public final boolean isExampleDefined() {
        return mStandardUserInputDataHandler.isExampleDefined();
    }

    public final boolean isCommentDefined() {
        return mStandardUserInputDataHandler.isCommentDefined();
    }

    public final boolean isPhoneticsDefined() {
        return mStandardUserInputDataHandler.isPhoneticsDefined();
    }

    public final boolean isPronunciationDefined() {
        return mStandardUserInputDataHandler.isPronunciationDefined();
    }

//    @Override
//    protected final String[] getUserInputIdentifiers() {
//        List<String> result = new ArrayList<String>(5);
//        result.addAll(Arrays.asList(mStandardUserInputDataHandler.getUserInputIdentifiers()));
//        result.addAll(Arrays.asList(getUserInputIdentifiersImpl()));
//        return result.toArray(new String[]{});
//    }

    @Override
    public final boolean isEmpty() {
        return mStandardUserInputDataHandler.isEmpty() && isEmptyImpl();
    }

    protected abstract boolean isEmptyImpl();

//    protected abstract String[] getUserInputIdentifiersImpl();
}
