/*
 * EnglishLanguagePlugin.java
 * Created on 16.10.2009
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
package org.jlexis.plugin.english;

import org.jlexis.data.vocable.AbstractWordClass;
import org.jlexis.data.vocable.WordClassEnum;
import org.jlexis.plugin.LanguagePlugin;
import org.jlexis.plugin.PluginIdentifier;
import org.jlexis.quiz.data.AbstractQuizType;

import java.util.*;

/**
 * @author Roland Krueger
 * @version $Id: EnglishLanguagePlugin.java 193 2009-12-07 19:44:07Z roland $
 */
public class EnglishLanguagePlugin extends LanguagePlugin {
    private static final String PLUGIN_KEY = "org.jlexis.plugin.english.plugin";
    private static final String PLUGIN_VERSION = "1.0";

    private static List<Set<String>> ABBREVIATION_ALTERNATIVES;

    private String mFlagGreatBritainIdentifier;
    private String mFlagUSAIdentifier;
    private AbstractWordClass defaultWordType;
    private AbstractWordClass noun;
    private AbstractWordClass adjective;
    private AbstractWordClass verb;

    static {
        ABBREVIATION_ALTERNATIVES = new ArrayList<>();
        addAbbreviationAlternativeSet("something", "sth.", "sth");
        addAbbreviationAlternativeSet("somebody", "sb", "sb.", "sbd", "sbd.");
        addAbbreviationAlternativeSet("someone", "so", "so.");
        addAbbreviationAlternativeSet("have not", "haven't");
        addAbbreviationAlternativeSet("had not", "hadn't");
        addAbbreviationAlternativeSet("are not", "aren't");
        addAbbreviationAlternativeSet("is not", "isn't");
        addAbbreviationAlternativeSet("do not", "don't");
        addAbbreviationAlternativeSet("does not", "doesn't");
        addAbbreviationAlternativeSet("has not", "hasn't");
        addAbbreviationAlternativeSet("cannot", "can't");
        addAbbreviationAlternativeSet("could not", "couldn't");
        addAbbreviationAlternativeSet("will not", "won't");
        addAbbreviationAlternativeSet("would not", "wouldn't");
        addAbbreviationAlternativeSet("should not", "shouldn't");
        addAbbreviationAlternativeSet("it is", "it's");
        addAbbreviationAlternativeSet("was not", "wasn't");
        addAbbreviationAlternativeSet("were not", "weren't");
        addAbbreviationAlternativeSet("I will", "I'll");
        addAbbreviationAlternativeSet("let us", "let's");
        addAbbreviationAlternativeSet("we will", "we'll");
        addAbbreviationAlternativeSet("you will", "you'll");
        addAbbreviationAlternativeSet("they will", "they'll");
        addAbbreviationAlternativeSet("you are", "you're");
        addAbbreviationAlternativeSet("I am", "I'm");
    }

    public EnglishLanguagePlugin() {
        super(new PluginIdentifier(PLUGIN_KEY, PLUGIN_VERSION));
//        try {
//            mFlagGreatBritainIdentifier = JLexisResourceManager.getInstance().registerIconResource(
//                    EnglishLanguagePlugin.class, "icons/gb.png");
//            mFlagUSAIdentifier = JLexisResourceManager.getInstance().registerIconResource(
//                    EnglishLanguagePlugin.class, "icons/us.png");
//        } catch (IOException e) {
//            // TODO: error handling
//            e.printStackTrace();
//        }
//        noun = new EnglishNounWordType(this);
//        adjective = new EnglishAdjectiveWordType(this);
//        verb = new EnglishVerbWordType(this);
//        defaultWordType = new EnglishDefaultWordType();
        registerWordType(defaultWordType);
        registerWordType(noun);
        registerWordType(adjective);
        registerWordType(verb);
    }

    private static void addAbbreviationAlternativeSet(String... alternatives) {
        ABBREVIATION_ALTERNATIVES.add(new HashSet<>(Arrays.asList(alternatives)));
    }

    @Override
    public List<Set<String>> getAbbreviationAlternatives() {
        return ABBREVIATION_ALTERNATIVES;
    }

    @Override
    protected AbstractWordClass getCorrespondingWordTypeForImpl(AbstractWordClass wordType) {
        if (wordType.getWordTypeEnum() == WordClassEnum.NOUN) return noun;
        if (wordType.getWordTypeEnum() == WordClassEnum.ADJECTIVE) return adjective;
        if (wordType.getWordTypeEnum() == WordClassEnum.VERB) return verb;
        return defaultWordType;
    }

    @Override
    public AbstractWordClass getDefaultWordType() {
        return defaultWordType;
    }

    @Override
    public String getIconId() {
        // TODO fix me
        return "mFlagGreatBritainIdentifier";
    }

//    public Icon getUSAFlagIcon() {
//        return JLexisResourceManager.getInstance().getIcon(mFlagUSAIdentifier);
//    }

    @Override
    public String getLanguageNameKey() {
        // TODO: i18n
        return "English";
    }

    @Override
    public List<AbstractQuizType> getQuizTypes() {
        return Collections.emptyList();
    }

    @Override
    public boolean isDefaultPlugin() {
        return false;
    }
}
