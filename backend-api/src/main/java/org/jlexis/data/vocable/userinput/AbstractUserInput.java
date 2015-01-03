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
 * This class generically represents a user's input for one vocable in one specific language. If, for example, the user
 * entered the English translation for a vocable, the corresponding {@link AbstractUserInput} object would contain that
 * translation, an example phrase, a comment and some more optional data.
 * <p/>
 * The data itself is held in a {@link java.util.Map}. By this, a maximum flexibility is achieved since the particular
 * pieces of data belonging to this {@link AbstractUserInput} can be referenced by arbitrary keys. Subclasses of {@link
 * AbstractUserInput} can freely choose which data to store in this internal data structure. They are themselves
 * responsible for correctly managing this data.
 * <p/>
 *
 * @author Roland Krueger
 */
public abstract class AbstractUserInput implements UserInput {

    /**
     * The data of this {@link AbstractUserInput}. This object assembles all the fragments that forms the language
     * specific part of a {@link org.jlexis.data.vocable.Vocable}, such as the vocabulary data of one language itself, comments, examples and
     * extra data.
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

    protected AbstractUserInput(String userInputIdentifier) {
        this();
        if (Strings.isNullOrEmpty(userInputIdentifier)) {
            throw new IllegalArgumentException("empty user input identifier string not accepted");
        }

        this.userInputIdentifier = userInputIdentifier;
    }

    public abstract AbstractUserInput createUserInputObject();

    public void addUserInput(RegisteredVocableDataKey identifier, String input) {
        if (StringUtils.isStringNullOrEmpty(input)) {
            return;
        }

        createNewRegularTermIfInputNotAvailable(identifier, getTermForKey(identifier))
                .setUserEnteredString(input);
    }

    private AbstractTerm createNewRegularTermIfInputNotAvailable(RegisteredVocableDataKey identifier, AbstractTerm term) {
        if (term == null) {
            term = new RegularTerm();
            regularTerms.put(identifier, term);
        }
        return term;
    }

    private AbstractTerm getTermForKey(RegisteredVocableDataKey identifier) {
        if (inflectedTerms.containsKey(identifier)) {
            return inflectedTerms.get(identifier);
        }

        if (wordStems.containsKey(identifier)) {
            return wordStems.get(identifier);
        }

        return regularTerms.get(identifier);
    }

    public boolean correspondsTo(UserInput other) {
        return userInputIdentifier.equals(other.getUserInputIdentifier());
    }

    public AbstractTerm getUserInput(RegisteredVocableDataKey identifier) {
        AbstractTerm term = getTermForKey(identifier);

        if (term != null) {
            return new UnmodifiableTerm(term);
        } else {
            return EmptyTerm.instance();
        }
    }

    public boolean isInputDefinedFor(RegisteredVocableDataKey identifier) {
        return ! getUserInput(identifier).isEmpty();
    }

    public String getUserInputIdentifier() {
        return userInputIdentifier;
    }

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

    public void setWordStem(RegisteredVocableDataKey identifier) {
        checkArgument(! wordStems.containsKey(identifier), String.format("The key %s has already been" +
                " configured as word stem.", identifier));

        wordStems.put(identifier, new WordStemTerm());
    }

    public void addWordStemChild(RegisteredVocableDataKey governingWordStemKey, RegisteredVocableDataKey inflectedTermKey) {
        checkArgument(wordStems.containsKey(governingWordStemKey), String.format("The key %s has not yet been configured as word stem.", governingWordStemKey));
        checkArgument(! inflectedTerms.containsKey(inflectedTermKey), String.format("The key %s has already been configured as an inflected term.", inflectedTermKey));

        inflectedTerms.put(inflectedTermKey, new InflectedTerm(wordStems.get(governingWordStemKey)));
    }
}
