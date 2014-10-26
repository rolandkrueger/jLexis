package org.jlexis.plugin.english.wordtypes;/*
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


import org.jlexis.data.vocable.AbstractWordType;
import org.jlexis.data.vocable.WordTypeEnum;
import org.jlexis.plugin.english.EnglishLanguagePlugin;
import org.jlexis.plugin.english.inputpanels.EnglishVerbInputPanel;
import org.jlexis.plugin.english.userinput.EnglishVerbUserInput;

public class EnglishVerbWordType extends AbstractWordType {
    private EnglishLanguagePlugin mPlugin;

    public EnglishVerbWordType(EnglishLanguagePlugin plugin) {
        //TODO I18N
        super("Verb", "EnglishVerb", new EnglishVerbUserInput());
        mPlugin = plugin;
    }

    @Override
    public AbstractVocableInputPanel getInputPanel() {
        EnglishVerbInputPanel inputPanel = new EnglishVerbInputPanel(this);
        inputPanel.setPlugin(mPlugin);
        return inputPanel;
    }

    @Override
    public WordTypeEnum getWordTypeEnum() {
        return WordTypeEnum.VERB;
    }
}
