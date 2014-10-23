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

import java.util.Optional;

/**
 * This class represents the language specific part of a {@link Vocable} object. An object of type
 * {@link VocableData} is bound to exactly one {@link org.jlexis.plugin.LanguagePlugin}. It contains all data for one
 * language (represented by the {@link Language}) in one {@link Vocable}.
 *
 * @author Roland Krueger
 */
public class VocableData {
    private AbstractWordType mWordType;
    private AbstractUserInput mUserInput;
    private Language mForLanguage;

    private Optional<String> mWordTypeIdentifier;
    private DBO_AbstractUserInput mUserInputFromDatabase;
    private long mId;

    /**
     * Private default constructor for Hibernate support.
     */
    private VocableData() {
        mWordTypeIdentifier = Optional.empty();
    }

    /**
     * Constructs a {@link VocableData} object from user entered data.
     *
     * @param forPlugin
     * @param wordType
     * @param data      the data entered by the user
     */
    public VocableData(Language forLanguage, AbstractWordType wordType, AbstractUserInput data) {
        this();
//        CheckForNull.check(forLanguage, wordType, data);

        mForLanguage = forLanguage;
        mWordType = wordType;
        mUserInput = data;
        setWordTypeIdentifier(mWordType.getIdentifier());
    }

    /**
     * Provides the {@link Language} object for this {@link VocableData}.
     *
     * @return the {@link Language} for this {@link VocableData}
     */
    public Language getLanguage() {
        if (mForLanguage == null)
            throw new IllegalStateException("Language object not available.");

        return mForLanguage;
    }

    public void setLanguage(Language language) {
        mForLanguage = language;
    }

    public AbstractUserInput getUserInput() {
        if (mUserInput == null && mUserInputFromDatabase == null)
            throw new IllegalStateException("Unable to provide data: both data objects are null.");

        if (mUserInput == null) {
            // TODO: ensure that the used word type identifier is never unknown for the plugin
            AbstractWordType wordType = mForLanguage.getLanguagePlugin().get().getWordTypeFor(mWordTypeIdentifier.get());
            assert wordType != null;
            mUserInput = wordType.createNewUserInputObject();
            mUserInput.setDatabaseObject(mUserInputFromDatabase);
        }

        return mUserInput;
    }

    @SuppressWarnings("unused")
    private void setDatabaseObject(DBO_AbstractUserInput userInputFromDatabase) {
        mUserInputFromDatabase = userInputFromDatabase;
    }

    @SuppressWarnings("unused")
    private DBO_AbstractUserInput getDatabaseObject() {
        if (getUserInput() == null) {
            throw new IllegalStateException("AbstractUserInput object not available.");
        }

        DBO_AbstractUserInput dbo = getUserInput().getDatabaseObject();
        if (mUserInputFromDatabase != null) {
            mUserInputFromDatabase.copy(dbo);
        } else {
            mUserInputFromDatabase = dbo;
        }
        return mUserInputFromDatabase;
    }

    public boolean isEmpty() {
        return mUserInput.isEmpty();
    }

    public void replaceInput(VocableData otherData) {
        if (!getWordType().getClass().equals(otherData.getWordType().getClass()))
            throw new IllegalArgumentException("Replacement vocable data doesn't have the same word type.");

        getUserInput().replace(otherData.getUserInput());
    }

    public AbstractWordType getWordType() {
        if (mWordType == null) {
            if (mWordTypeIdentifier.isPresent()) {
                mWordType = getLanguage().getLanguagePlugin().get()
                        .getWordTypeFor(mWordTypeIdentifier.get());
                if (mWordType == null) {
                    throw new IllegalStateException("Unable to obtain AbstractWordType object.");
                }
            } else {
                throw new IllegalStateException("AbstractWordType object not available.");
            }
        }

        return mWordType;
    }

    public String getWordTypeIdentifier() {
        return getWordType().getIdentifier();
    }

    public void setWordTypeIdentifier(String wordTypeIdentifier) {
        if (wordTypeIdentifier == null)
            throw new IllegalArgumentException("Argument is null.");

        mWordTypeIdentifier = Optional.of(wordTypeIdentifier);
    }

    @SuppressWarnings("unused")
    private long getId() {
        return mId;
    }

    @SuppressWarnings("unused")
    private void setId(long id) {
        mId = id;
    }
}