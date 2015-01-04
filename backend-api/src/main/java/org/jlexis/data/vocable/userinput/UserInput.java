/*
 * Created on 24.03.2009 Copyright 2007-2009 Roland Krueger (www.rolandkrueger.info) This file is
 * part of jLexis. jLexis is free software; you can redistribute it and/or modify it under the terms
 * of the GNU General Public License as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version. jLexis is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should
 * have received a copy of the GNU General Public License along with jLexis; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.jlexis.data.vocable.userinput;

import org.jlexis.data.vocable.RegisteredVocableDataKey;
import org.jlexis.data.vocable.terms.AbstractTerm;
import org.jlexis.data.vocable.verification.VocableVerificationData;
import org.jlexis.roklib.TextFormatter;

/**
 * Interface for defining data classes that store and manage user entered vocabulary data. A user input will receive the
 * vocabulary data for exactly one language in one particular vocable.
 *
 * @author Roland Krueger
 */
public interface UserInput {

    /**
     * Initialize the user input object.
     */
    void init();

    /**
     * Provides a String containing the short version vor this {@link AbstractUserInput}. This is usually the most
     * important information held by this object. Less important information like example clauses or comments will
     * usually not be contained in this short version. The information returned by this method will be used in places,
     * where a space-saving display of a vocable is needed, such as in vocabulary overview tables.
     *
     * @return a short summary of this {@link AbstractUserInput}
     */
    String getShortVersion();

    /**
     * Provides a piece of HTML-formatted text containing the information of this {@link AbstractUserInput}. This text
     * will then be used to render the data of this object as an HTML document.
     *
     * @return a HTML-version of this {@link AbstractUserInput}'s data
     */
    String getHTMLVersion(TextFormatter formatter);

    /**
     * Returns <code>true</code> if there is no user data available from this {@link AbstractUserInput} or in other
     * words if this object can be considered empty. An empty user input will not be saved into the list of entered
     * vocables. It is possible that a {@link AbstractUserInput} can be considered empty if there are in fact some data
     * fields that have been provided. This decision is left to subclasses descending from {@link AbstractUserInput}.
     *
     * @return <code>true</code> if the data of this object has not been set
     */
    boolean isEmpty();

    /**
     * <p> Adds a piece of user entered data. This might be the translation of a word, the grammatical gender of a noun,
     * an example clause or a comment. Each of these pieces of data are uniquely identified by a key which will be used
     * as the key into the {@link java.util.Map} data structure holding a {@link AbstractUserInput}'s data. If the data
     * is the empty string, it's addition is skipped to prevent the database from being flooded with empty values. </p>
     *
     * @param key
     *         the key for the piece of user entered data
     * @param input
     *         the data itself
     */
    void addUserInput(RegisteredVocableDataKey key, String input);

    /**
     * Factory method that creates a new instance of the class that implements interface {@link
     * org.jlexis.data.vocable.userinput.UserInput}.
     *
     * @return a new user input object of the type of the class that implements interface {@link
     * org.jlexis.data.vocable.userinput.UserInput}.
     */
    UserInput createUserInputObject();

    boolean correspondsTo(UserInput other);

    AbstractTerm getUserInput(RegisteredVocableDataKey key);

    boolean isInputDefinedFor(RegisteredVocableDataKey key);

    String getUserInputIdentifier();

    VocableVerificationData getQuizVerificationData();

    void registerKeyForWordStem(RegisteredVocableDataKey key);

    void registerKeyForInflectedTerm(RegisteredVocableDataKey governingWordStemKey, RegisteredVocableDataKey inflectedTermKey);
}