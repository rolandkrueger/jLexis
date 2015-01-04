/*
 * $Id: VocableData.java 197 2009-12-14 07:27:08Z roland $
 * Created on 06.01.2009
 * 
 * Copyright 2007 Roland Krueger (www.rolandkrueger.info)
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
package org.jlexis.data.vocable;


import org.jlexis.data.languages.Language;
import org.jlexis.data.vocable.userinput.DBO_AbstractUserInput;
import org.jlexis.data.vocable.userinput.UserInput;

import java.util.Optional;

import static com.google.common.base.Preconditions.*;

/**
 * This class represents the language specific part of a {@link Vocable} object. An object of type {@link VocableData}
 * is bound to exactly one {@link org.jlexis.data.languages.Language}. It contains all data for one language
 * (represented by the {@link Language} object) in one {@link Vocable}.
 *
 * @author Roland Krueger
 */
public class VocableData {
    /**
     * The language of the given user input.
     */
    private Language forLanguage;
    /**
     * The concrete input the user has given for this language.
     */
    private UserInput userInput;
    /**
     * The word type that the user input represents. This defines verbs, nouns, adjectives, etc.
     */
    private AbstractWordClass wordType;

    private Optional<String> wordTypeIdentifier;
    private long id;

    private VocableData() {
        wordTypeIdentifier = Optional.empty();
    }

    /**
     * Constructs a {@link VocableData} object from user entered data.
     *
     * @param userInput
     *         the data entered by the user
     */
    public VocableData(Language forLanguage, AbstractWordClass wordType, UserInput userInput) {
        this();
        this.forLanguage = checkNotNull(forLanguage);
        this.wordType = checkNotNull(wordType);
        this.userInput = checkNotNull(userInput);
        setWordTypeIdentifier(this.wordType.getIdentifier());
    }

    /**
     * @return the {@link Language} object for this {@link VocableData}
     */
    public Language getLanguage() {
        return forLanguage;
    }

    public UserInput getUserInput() {
//        TODO: refactor
//        if (userInput == null && mUserInputFromDatabase == null)
//            throw new IllegalStateException("Unable to provide data: both data objects are null.");
//
//        if (userInput == null) {
//            // TODO: ensure that the used word type identifier is never unknown for the plugin
//            AbstractWordType wordType = forLanguage.getLanguagePlugin().get().getWordTypeFor(wordTypeIdentifier.get());
//            assert wordType != null;
//            userInput = wordType.createUserInputObject();
//            userInput.setDatabaseObject(mUserInputFromDatabase);
//        }

        return userInput;
    }

    @SuppressWarnings("unused")
    private DBO_AbstractUserInput getDatabaseObject() {
//        TODO: refactor
//        if (getUserInput() == null) {
//            throw new IllegalStateException("AbstractUserInput object not available.");
//        }
//
//        DBO_AbstractUserInput dbo = getUserInput().getDatabaseObject();
//        if (mUserInputFromDatabase != null) {
//            mUserInputFromDatabase.copy(dbo);
//        } else {
//            mUserInputFromDatabase = dbo;
//        }
//        return mUserInputFromDatabase;
        return null;
    }

    public boolean isEmpty() {
        return userInput.isEmpty();
    }

    public AbstractWordClass getWordType() {
        if (wordType == null) {
            if (wordTypeIdentifier.isPresent()) {
                wordType = getLanguage().getLanguagePlugin().get()
                        .getWordTypeFor(wordTypeIdentifier.get());
                if (wordType == null) {
                    throw new IllegalStateException("Unable to obtain AbstractWordType object.");
                }
            } else {
                throw new IllegalStateException("AbstractWordType object not available.");
            }
        }

        return wordType;
    }

    public String getWordTypeIdentifier() {
        return getWordType().getIdentifier();
    }

    public void setWordTypeIdentifier(String wordTypeIdentifier) {
        if (wordTypeIdentifier == null)
            throw new IllegalArgumentException("Argument is null.");

        this.wordTypeIdentifier = Optional.of(wordTypeIdentifier);
    }

    @SuppressWarnings("unused")
    private long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    private void setId(long id) {
        this.id = id;
    }
}
