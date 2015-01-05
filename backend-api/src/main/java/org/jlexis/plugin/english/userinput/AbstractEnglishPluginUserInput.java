/*
 * Created on 06.12.2009
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

import org.jlexis.data.vocable.userinput.AbstractUserInput;
import org.jlexis.data.vocable.userinput.DefaultUserInput;
import org.jlexis.data.vocable.userinput.UserInput;
import org.jlexis.data.vocable.userinput.standard.StandardUserInputDecorator;

public abstract class AbstractEnglishPluginUserInput extends AbstractUserInput {
    private static final long serialVersionUID = 548147957600211389L;
    private StandardUserInputDecorator mStandardInputBE;
    private StandardUserInputDecorator mStandardInputAE;

    protected AbstractEnglishPluginUserInput(String inputType) {
        super(inputType);
        mStandardInputBE = new StandardUserInputDecorator(this, "BE");
        mStandardInputAE = new StandardUserInputDecorator(this, "AE");
    }

    protected abstract boolean isEmptyImpl();

    @Override
    public boolean isEmpty() {
        return mStandardInputBE.isEmpty() &&
                mStandardInputAE.isEmpty() &&
                isEmptyImpl();
    }

    protected StandardUserInputDecorator getStandardInputBE() {
        return mStandardInputBE;
    }

    protected StandardUserInputDecorator getStandardInputAE() {
        return mStandardInputAE;
    }

    public UserInput getBritishStandardValues() {
        StandardUserInputDecorator result = new StandardUserInputDecorator(new DefaultUserInput());
        result.setComment(mStandardInputBE.getComment());
        result.setExample(mStandardInputBE.getExample());
        result.setPhonetics(mStandardInputBE.getPhonetics());
        result.setPronunciation(mStandardInputBE.getPronunciation());
        return result;
    }

    public void setBritishStandardValues(StandardUserInputDecorator defaultBritishEnglish) {
        mStandardInputBE.setComment(defaultBritishEnglish.getComment());
        mStandardInputBE.setExample(defaultBritishEnglish.getExample());
        mStandardInputBE.setPhonetics(defaultBritishEnglish.getPhonetics());
        mStandardInputBE.setPronunciation(defaultBritishEnglish.getPronunciation());
    }

    public StandardUserInputDecorator getAmericanStandardValues() {
        StandardUserInputDecorator result = new StandardUserInputDecorator(new DefaultUserInput());
        result.setComment(mStandardInputAE.getComment());
        result.setExample(mStandardInputAE.getExample());
        result.setPhonetics(mStandardInputAE.getPhonetics());
        result.setPronunciation(mStandardInputAE.getPronunciation());
        return result;
    }

    public void setAmericanStandardValues(StandardUserInputDecorator defaultAmericanEnglish) {
        mStandardInputAE.setComment(defaultAmericanEnglish.getComment());
        mStandardInputAE.setExample(defaultAmericanEnglish.getExample());
        mStandardInputAE.setPhonetics(defaultAmericanEnglish.getPhonetics());
        mStandardInputAE.setPronunciation(defaultAmericanEnglish.getPronunciation());
    }

    public String getComment() {
        return mStandardInputBE.getComment();
    }

    public String getExample() {
        return mStandardInputBE.getExample();
    }
}
