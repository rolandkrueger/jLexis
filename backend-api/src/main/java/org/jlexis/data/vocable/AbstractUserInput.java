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
package org.jlexis.data.vocable;

import com.google.common.base.Strings;
import org.jlexis.data.vocable.terms.AbstractTerm;
import org.jlexis.data.vocable.terms.InflectedTerm;
import org.jlexis.data.vocable.terms.RegularTerm;
import org.jlexis.data.vocable.terms.WordStemTerm;
import org.jlexis.data.vocable.verification.VocableVerificationData;
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
 * TODO: describe data caching feature
 *
 * @author Roland Krueger
 */
public abstract class AbstractUserInput implements UserInput {

    /**
     * The data of this {@link AbstractUserInput}. This object assembles all the fragments that forms the language
     * specific part of a {@link Vocable}, such as the vocabulary data of one language itself, comments, examples and
     * extra data.
     */
    private Map<RegisteredVocableDataKey, AbstractTerm> data;
    private String inputType;
    private Map<RegisteredVocableDataKey, WordStemTerm> wordStems;
    private Map<RegisteredVocableDataKey, InflectedTerm> inflectedTerms;

    private AbstractUserInput() {
        data = new HashMap<>(8);
        wordStems = new HashMap<>(1);
        inflectedTerms = new HashMap<>(5);
    }

    protected AbstractUserInput(String inputType) {
        this();
        if (Strings.isNullOrEmpty(inputType)) {
            throw new IllegalArgumentException("empty input type string not accepted");
        }

        this.inputType = inputType;
    }

    @Deprecated
    protected void setDatabaseObject(DBO_AbstractUserInput databaseObj) {

        data.clear();
        Map<RegisteredVocableDataKey, RegularTerm> dboMap = databaseObj.getData();
        for (RegisteredVocableDataKey key : dboMap.keySet()) {
            AbstractTerm term = getRegisteredTermTypeForKey(key);
            term.setEncodedString(dboMap.get(key).getEncodedString());
            data.put(key, term);
        }

        inputType = databaseObj.getInputType();
    }

    @Override
    public abstract boolean isEmpty();

    @Override
    public abstract void init();

    protected abstract AbstractUserInput createNewUserInputObject();

    public void addUserInput(RegisteredVocableDataKey identifier, String input) {
        if (StringUtils.isStringNullOrEmpty(input)) {
            return;
        }

        getRegisteredTermTypeForKey(identifier)
                .setUserEnteredString(input);
    }

    private AbstractTerm getRegisteredTermTypeForKey(RegisteredVocableDataKey identifier) {
        if (inflectedTerms.containsKey(identifier)) {
            return inflectedTerms.get(identifier);
        }

        if (wordStems.containsKey(identifier)) {
            return wordStems.get(identifier);
        }

        AbstractTerm term = new RegularTerm();
        data.put(identifier, term);
        return term;
    }

    public boolean isTypeCorrect(UserInput other) {
        return inputType.equals(other.getUserInputIdentifier());
    }

    public AbstractTerm getUserInput(RegisteredVocableDataKey identifier) {
        AbstractTerm result = data.get(identifier);
        if (result == null) result = new RegularTerm();

        return result;
    }

    public boolean isDataDefinedFor(RegisteredVocableDataKey identifier) {
        return data.containsKey(identifier) && !data.get(identifier).isEmpty();
    }

    public String getUserInputIdentifier() {
        return inputType;
    }

    public void replace(AbstractUserInput userInput) {
        data.clear();
        data.putAll(userInput.data);
    }

    public void setWordStem(RegisteredVocableDataKey registeredKey) {
        checkArgument(! wordStems.containsKey(registeredKey), String.format("The key %s has already been" +
                " configured as word stem.", registeredKey));

        wordStems.put(registeredKey, new WordStemTerm());
    }

    public void addWordStemChild(RegisteredVocableDataKey governingWordStemKey, RegisteredVocableDataKey inflectedTermKey) {
        checkArgument(wordStems.containsKey(governingWordStemKey), String.format("The key %s has not yet been configured as word stem.", governingWordStemKey));
        checkArgument(! inflectedTerms.containsKey(inflectedTermKey), String.format("The key %s has already been configured as an inflected term.", inflectedTermKey));

        inflectedTerms.put(inflectedTermKey, new InflectedTerm(wordStems.get(governingWordStemKey)));
    }

    @Override
    public abstract VocableVerificationData getQuizVerificationData();

    public String getPurgedUserData(RegisteredVocableDataKey identifier) {
        return getUserInput(identifier).getCleanedString();
    }

    public String getUserEnteredTerm(RegisteredVocableDataKey identifier) {
        return getUserInput(identifier).getUserEnteredString();
    }

    public String getResolvedUserData(RegisteredVocableDataKey identifier) {
        return getUserInput(identifier).getStringWithWordStemResolved();
    }

    public String getResolvedAndPurgedUserData(RegisteredVocableDataKey identifier) {
        return getUserInput(identifier).getCleanedStringWithWordStemResolved();
    }
}
