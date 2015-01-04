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
     * Provides a String containing the short version for this {@link UserInput}. This is usually the most important
     * information held by this object. Less important information like example clauses or comments will usually not be
     * contained in this short version. The information returned by this method will be used in places, where a
     * space-saving display of a vocable is needed, such as in vocabulary overview tables.
     *
     * @param formatter
     *         a formatter to add the short display text of this {@link UserInput}
     */
    void provideShortDisplayText(TextFormatter formatter);

    /**
     * Provides a piece of HTML-formatted text containing the information of this {@link UserInput}. This text will then
     * be used to render the data of this object as an HTML document.
     *
     * @return a HTML-version of this {@link UserInput}'s data
     */
    void provideFullDisplayText(TextFormatter formatter);

    /**
     * Returns <code>true</code> if there is no user data available from this {@link UserInput} or in other words if
     * this object can be considered empty. An empty user input will not be saved into the list of entered vocables. It
     * is possible that a {@link UserInput} can be considered empty if there are in fact some data fields that have been
     * provided. For example, a user input with only some default values set can be considered empty. This decision is
     * left to subclasses descending from {@link UserInput}.
     * <p/>
     * This method can be used to determine, whether this user input object needs to be persisted.
     *
     * @return <code>true</code> if the data of this object has not been set
     */
    boolean isEmpty();

    /**
     * Returns <code>true</code> if at least one textual value has been defined. User input objects may store values
     * other than textual input, e.g. boolean values or enumeration values. If only such non-textual values are stored
     * in this user input object, this method returns <code>false</code>.
     * <p/>
     * This method by be used to determine, whether the data of this user input object needs to be displayed to the
     * user.
     *
     * @return <code>true</code> if at least one textual value has been defined.
     */
    boolean isAnyTextInputDefined();

    /**
     * <p> Adds a piece of user entered data. This might be the translation of a word, the grammatical gender of a noun,
     * an example clause or a comment. Each of these pieces of data are uniquely identified by a key which will be used
     * as the key into the {@link java.util.Map} data structure holding a {@link UserInput}'s data. If the data is the
     * empty string, it's addition is skipped to prevent the database from being flooded with empty values. </p>
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