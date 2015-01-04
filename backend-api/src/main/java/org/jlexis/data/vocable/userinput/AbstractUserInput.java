/*
 * AbstractUserInput.java
 * Created on 03.04.2007
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
package org.jlexis.data.vocable.userinput;

import com.google.common.base.Strings;
import org.jlexis.data.vocable.RegisteredVocableDataKey;
import org.jlexis.data.vocable.terms.AbstractTerm;
import org.jlexis.data.vocable.terms.EmptyTerm;
import org.jlexis.data.vocable.terms.InflectedTerm;
import org.jlexis.data.vocable.terms.RegularTerm;
import org.jlexis.data.vocable.terms.UnmodifiableTerm;
import org.jlexis.data.vocable.terms.WordStemTerm;
import org.jlexis.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.*;

/**
 * This class generically represents a user's input for one vocable in one specific language. For instance, when the
 * user enters the English translation for a vocable, the corresponding {@link AbstractUserInput} object for this
 * English part would contain this English translation, an example phrase, a comment and some more optional data.
 * <p/>
 * The data itself is held in {@link java.util.Map} instances. The value types of these maps are instances of {@link
 * org.jlexis.data.vocable.terms.AbstractTerm}. By this, a maximum flexibility is achieved since the particular pieces
 * of data belonging to this {@link AbstractUserInput} can be referenced by arbitrary keys. These keys are represented
 * by instances of class {@link org.jlexis.data.vocable.RegisteredVocableDataKey}. Subclasses of {@link
 * AbstractUserInput} can freely choose which data to store in this internal data structure. They are themselves
 * responsible for correctly managing this data.
 * <p/>
 * As defined by the interface {@link org.jlexis.data.vocable.userinput.UserInput}, it is possible to store inflected
 * terms and their corresponding word stems in this user input implementation. The keys used for word stems and
 * inflected terms first have to be registered with {@link #registerKeyForWordStem(org.jlexis.data.vocable.RegisteredVocableDataKey)}
 * and {@link #registerKeyForInflectedTerm(org.jlexis.data.vocable.RegisteredVocableDataKey, org.jlexis.data.vocable.RegisteredVocableDataKey)},
 * respectively. Only after registering these keys, data can be safely added for their corresponding terms with {@link
 * #addUserInput(org.jlexis.data.vocable.RegisteredVocableDataKey, String)}.
 *
 * @author Roland Krueger
 * @see org.jlexis.data.vocable.terms.AbstractTerm
 * @see org.jlexis.data.vocable.RegisteredVocableDataKey
 */
public abstract class AbstractUserInput implements UserInput {

    /**
     * The data of this {@link AbstractUserInput}. This object assembles all the fragments that forms the language
     * specific part of a {@link org.jlexis.data.vocable.Vocable}, such as the vocabulary data of one language itself,
     * comments, examples and extra data.
     */
    private Map<RegisteredVocableDataKey, AbstractTerm> regularTerms;
    private Map<RegisteredVocableDataKey, WordStemTerm> wordStems;
    private Map<RegisteredVocableDataKey, InflectedTerm> inflectedTerms;

    private String userInputIdentifier;

    private AbstractUserInput() {
        regularTerms = new HashMap<>(8);
        wordStems = new HashMap<>(1);
        inflectedTerms = new HashMap<>(5);
    }

    /**
     * Create a new user input object with a specific unique identifier. This identifier is used to compare two
     * instances of a concrete user input class and to decide whether they correspond with each other.
     *
     * @param userInputIdentifier
     *         unique identifier for this user input. This may be a fully qualified class name for example.
     * @see org.jlexis.data.vocable.userinput.UserInput#correspondsTo(UserInput)
     */
    protected AbstractUserInput(String userInputIdentifier) {
        this();
        if (Strings.isNullOrEmpty(userInputIdentifier)) {
            throw new IllegalArgumentException("empty user input identifier string not accepted");
        }

        this.userInputIdentifier = userInputIdentifier;
    }

    public void addUserInput(RegisteredVocableDataKey key, String input) {
        if (StringUtils.isStringNullOrEmpty(input)) {
            return;
        }

        createNewRegularTermIfInputNotAvailable(key, getTermForKey(key))
                .setUserEnteredString(input);
    }

    /**
     * Creates a new {@link org.jlexis.data.vocable.terms.RegularTerm} and adds it to the regular terms map if the given
     * term object is null.
     *
     * @param key
     *         key with which the newly created term will be added to the regular terms map if necessary
     * @param term
     *         term object which may be null
     * @return either the given term if that was non-null or a new {@link org.jlexis.data.vocable.terms.RegularTerm}
     */
    private AbstractTerm createNewRegularTermIfInputNotAvailable(RegisteredVocableDataKey key, AbstractTerm term) {
        if (term == null) {
            term = new RegularTerm();
            regularTerms.put(key, term);
        }
        return term;
    }

    /**
     * Determine the right term object for the given key by searching the term in the three term maps for inflected
     * terms, word stem terms, and regular terms.
     *
     * @return the corresponding term object for the given key or <code>null</code> if no term has been registered for
     * this key
     */
    private AbstractTerm getTermForKey(RegisteredVocableDataKey key) {
        if (inflectedTerms.containsKey(key)) {
            return inflectedTerms.get(key);
        }

        if (wordStems.containsKey(key)) {
            return wordStems.get(key);
        }

        return regularTerms.get(key);
    }

    public boolean correspondsTo(UserInput other) {
        return userInputIdentifier.equals(other.getUserInputIdentifier());
    }

    public AbstractTerm getUserInput(RegisteredVocableDataKey key) {
        AbstractTerm term = getTermForKey(key);

        if (term != null) {
            return new UnmodifiableTerm(term);
        } else {
            return EmptyTerm.instance();
        }
    }

    public boolean isInputDefinedFor(RegisteredVocableDataKey key) {
        return ! getUserInput(key).isEmpty();
    }

    public String getUserInputIdentifier() {
        return userInputIdentifier;
    }

    /**
     * Replace the data from this user input with the data from the given user input.
     *
     * @param userInput
     *         user input object that has to correspond to this user input as specified by method {@link
     *         org.jlexis.data.vocable.userinput.UserInput#correspondsTo(UserInput)}
     * @throws java.lang.IllegalArgumentException
     *         if the given user input does not correspond to this user input as specified by method {@link
     *         org.jlexis.data.vocable.userinput.UserInput#correspondsTo(UserInput)}
     */
    public void replace(AbstractUserInput userInput) {
        checkArgument(correspondsTo(userInput), "cannot replace: this user input type does not correspond to the " +
                "given user input");

        regularTerms.clear();
        regularTerms.putAll(userInput.regularTerms);
        wordStems.clear();
        wordStems.putAll(userInput.wordStems);
        inflectedTerms.clear();
        inflectedTerms.putAll(userInput.inflectedTerms);
    }

    @Override
    public void registerKeyForWordStem(RegisteredVocableDataKey key) {
        checkArgument(! wordStems.containsKey(key), String.format("The key %s has already been" +
                " configured as word stem.", key));

        wordStems.put(key, new WordStemTerm());
    }

    @Override
    public void registerKeyForInflectedTerm(RegisteredVocableDataKey governingWordStemKey, RegisteredVocableDataKey inflectedTermKey) {
        checkArgument(wordStems.containsKey(governingWordStemKey), String.format("The key %s has not yet been configured as word stem.", governingWordStemKey));
        checkArgument(! inflectedTerms.containsKey(inflectedTermKey), String.format("The key %s has already been configured as an inflected term.", inflectedTermKey));

        inflectedTerms.put(inflectedTermKey, new InflectedTerm(wordStems.get(governingWordStemKey)));
    }
}
