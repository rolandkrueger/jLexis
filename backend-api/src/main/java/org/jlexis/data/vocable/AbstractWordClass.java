/*
 * Created on 29.01.2007
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
package org.jlexis.data.vocable;

import org.jlexis.data.vocable.userinput.UserInput;
import org.jlexis.util.StringUtils;

import java.util.Objects;

import static com.google.common.base.Preconditions.*;

public abstract class AbstractWordClass {
    private String nameI18nKey;
    private String identifier;
    private UserInput userInputPrototype;

    /**
     * Create a new word class object.
     *
     * @param nameI18nKey
     *         key for translating the name of this word class
     * @param identifier
     *         technical ID to be used as primary key in the database
     * @param userInputPrototype
     *         a user input object used to spawn new instances of the concrete user input class on demand
     */
    protected AbstractWordClass(String nameI18nKey, String identifier, UserInput userInputPrototype) {
        this.nameI18nKey = checkNotNull(nameI18nKey);
        this.userInputPrototype = checkNotNull(userInputPrototype);

        checkArgument(! StringUtils.isNullOrEmptyWithTrim(identifier), "Identifier must not be null or the empty string.");
        this.identifier = identifier;
    }

    /**
     * Provides the concrete word class enum value that this object represents.
     */
    public abstract WordClassEnum getWordTypeEnum();

    /**
     * Factory method for creating the user input object that corresponds to this word class.
     *
     * @return a new user input object that corresponds to this word class.
     */
    public final UserInput createNewUserInputObject() {
        UserInput result = userInputPrototype.createUserInputObject();
        result.init();
        return result;
    }

    /**
     * Provides the i18n key for the translation of this word class's name. This will later be resolved to a text shown
     * to the user on the UI (e.g. "Adjective" or "Noun").
     */
    public final String getNameI18nKey() {
        return nameI18nKey;
    }

    /**
     * Provides the identifier for this word class. This is a technical key which is used to find the correct class for
     * a {@link org.jlexis.data.vocable.AbstractWordClass} for data loaded from the database. This could be for example
     * "EnglishAdjective" or "EnglishNoun".
     */
    public final String getIdentifier() {
        return identifier;
    }

    @Override
    public int hashCode() {
        return identifier.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null &&
                (obj == this
                        || obj instanceof AbstractWordClass && Objects.equals(identifier, ((AbstractWordClass) obj).identifier));
    }
}
