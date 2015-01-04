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
import org.jlexis.data.vocable.userinput.UserInput;

import java.io.Serializable;

import static com.google.common.base.Preconditions.*;

/**
 * This class represents the language specific part of a {@link Vocable} object. An object of type {@link VocableData}
 * is bound to exactly one {@link org.jlexis.data.languages.Language}. It contains all data for one language
 * (represented by the {@link Language} object) in one {@link Vocable}.
 *
 * @author Roland Krueger
 */
public class VocableData implements Serializable {
    private static final long serialVersionUID = - 2500879511063950497L;
    /**
     * The language of the given user input.
     */
    private Language forLanguage;
    /**
     * The concrete input the user has given for this language.
     */
    private UserInput userInput;
    /**
     * The word class that the user input represents. This defines verbs, nouns, adjectives, etc.
     */
    private AbstractWordClass wordClass;

    private long id;

    /**
     * Creates a new and empty VocableData object.
     */
    public VocableData(Language forLanguage, AbstractWordClass wordClass) {
        this(forLanguage, wordClass, checkNotNull(wordClass).createNewUserInputObject());
    }

    /**
     * Constructs a VocableData object from user entered data.
     *
     * @param userInput
     *         the data entered by the user
     */
    public VocableData(Language forLanguage, AbstractWordClass wordClass, UserInput userInput) {
        this.forLanguage = checkNotNull(forLanguage);
        this.wordClass = checkNotNull(wordClass);
        this.userInput = checkNotNull(userInput);
    }

    /**
     * @return the {@link Language} object for this {@link VocableData}
     */
    public Language getLanguage() {
        return forLanguage;
    }

    public UserInput getUserInput() {
        return userInput;
    }

    public AbstractWordClass getWordClass() {
        return wordClass;
    }

    /**
     * A VocableData object is considered empty if its user input object is empty.
     */
    public boolean isEmpty() {
        return userInput.isEmpty();
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
