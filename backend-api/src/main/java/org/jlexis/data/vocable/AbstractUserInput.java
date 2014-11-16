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
import org.jlexis.data.vocable.terms.*;
import org.jlexis.data.vocable.verification.VocableVerificationData;

import java.util.HashMap;
import java.util.Map;

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

    private static Map<String, RegisteredVocableDataKey> REGISTERED_KEYS;
    private static boolean IS_SYNCHRONIZED_WITH_DATABASE;

    /**
     * Object that represents this {@link AbstractUserInput} in the database.
     */
    private DBO_AbstractUserInput mDatabaseObject;

    /**
     * The data of this {@link AbstractUserInput}. This object assembles all the fragments that forms the language
     * specific part of a {@link Vocable}, such as the vocabulary data of one language itself, comments, examples and
     * extra data.
     */
    private Map<RegisteredVocableDataKey, AbstractTerm> data;
    private String inputType;
    private Map<String, WordStemTerm> wordStems;
    private Map<String, InflectedTerm> inflectedTerms;

    static {
        REGISTERED_KEYS = new HashMap<>();
        IS_SYNCHRONIZED_WITH_DATABASE = false;
    }

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

    /**
     * Loads the IDs of the {@link RegisteredVocableDataKey}s from the database if the corresponding key is availabe. If
     * the key isn't available in the database yet, store the key into the database.
     */
    @SuppressWarnings("unchecked")
    public static void synchronizeRegisteredKeysWithDatabase() {
//      TODO: refactor
//    List<RegisteredVocableDataKey> registeredKeys =
//      new ArrayList<RegisteredVocableDataKey>(REGISTERED_KEYS.values ());
//    Session session = JLexisPersistenceManager.getInstance ().getSession ();
//    Query qry = session.createQuery ("FROM RegisteredVocableDataKey");
//    List<RegisteredVocableDataKey> keys = qry.list ();
//
//    for (RegisteredVocableDataKey key : keys)
//    {
//      registeredKeys.remove (key);
//      REGISTERED_KEYS.put (key.getKey (), key);
//    }
//
//    // persist all new keys not available in the database
//    for (RegisteredVocableDataKey key : registeredKeys)
//    {
//      JLexisPersistenceManager.getInstance ().saveObject (key);
//    }
//    IS_SYNCHRONIZED_WITH_DATABASE = true;
    }

    @Deprecated
    public DBO_AbstractUserInput getDatabaseObject() {
        if (mDatabaseObject == null)
            mDatabaseObject = new DBO_AbstractUserInput();

        Map<RegisteredVocableDataKey, RegularTerm> preparedData = new HashMap<RegisteredVocableDataKey, RegularTerm>();
        for (RegisteredVocableDataKey key : data.keySet()) {
            preparedData.put(key, new RegularTerm(data.get(key).getEncodedString()));
        }

        mDatabaseObject.setData(preparedData);
        mDatabaseObject.setInputType(inputType);

        return mDatabaseObject;
    }

    @Deprecated
    protected void setDatabaseObject(DBO_AbstractUserInput databaseObj) {
        if (databaseObj == null)
            throw new NullPointerException("Argument is null.");

        data.clear();
        Map<RegisteredVocableDataKey, RegularTerm> dboMap = databaseObj.getData();
        for (RegisteredVocableDataKey key : dboMap.keySet()) {
            AbstractTerm term = getRegisteredTermTypeForKey(key.getKey());
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

    public void addUserData(String identifier, String data) {
        if (!isKeyRegistered(identifier))
            throw new IllegalStateException(String.format("Given identifier '%s' has not been provided " +
                    "by AbstractUserInput.getUserInputIdentifiers().", identifier));
        if (!IS_SYNCHRONIZED_WITH_DATABASE)
            throw new IllegalStateException("Registered keys have not yet been synchronized with database. " +
                    "Call " + AbstractUserInput.class.getName() + ".synchronizeRegisteredKeysWithDatabase() first.");
        if (data == null)
            throw new NullPointerException("User data object is null.");

        AbstractTerm term = getRegisteredTermTypeForKey(identifier);
        term.setUserEnteredString(data);
        this.data.put(REGISTERED_KEYS.get(identifier), term);
    }

    private AbstractTerm getRegisteredTermTypeForKey(String identifier) {
        AbstractTerm term = inflectedTerms.get(identifier);
        if (term != null) return term;

        term = wordStems.get(identifier);
        if (term != null) return term;

        term = new RegularTerm();
        return term;
    }

    private boolean isKeyRegistered(String identifier) {
        return REGISTERED_KEYS.containsKey(identifier);
    }

    public void registerUserInputIdentifiers() {
        String[] identifiers = getUserInputIdentifiers();
        if (identifiers == null) return;

        for (String identifier : identifiers) {
            if (isKeyRegistered(identifier)) {
                // TODO: handle this situation
                System.out.println(String.format("WARNING: User input identifier %s is already registered.", identifier));
//        throw new IllegalStateException (String.format ("User input identifier %s is already registered.", identifier));
                continue;
            }

            REGISTERED_KEYS.put(identifier, new RegisteredVocableDataKey(identifier));
        }
    }

    protected abstract String[] getUserInputIdentifiers();

    public boolean isTypeCorrect(UserInput other) {
        return inputType.equals(other.getUserInputIdentifier());
    }

    public AbstractTerm getUserData(String identifier) {
        if (!isKeyRegistered(identifier))
            throw new IllegalStateException(String.format("Given identifier '%s' has not been provided " +
                    "by AbstractUserInput.getUserInputIdentifiers().", identifier));

        AbstractTerm result = data.get(new RegisteredVocableDataKey(identifier));
        if (result == null) result = new RegularTerm();

        return result;
    }

    public boolean isDataDefinedFor(String identifier) {
        RegisteredVocableDataKey key = new RegisteredVocableDataKey(identifier);
        return data.containsKey(key) && !data.get(key).isEmpty();
    }

    public String getUserInputIdentifier() {
        return inputType;
    }

    public void replace(AbstractUserInput userInput) {
        data.clear();
        for (RegisteredVocableDataKey key : userInput.data.keySet()) {
            data.put(key, userInput.data.get(key));
        }
    }

    public void setWordStem(String registeredKey) {
        if (!isKeyRegistered(registeredKey))
            throw new IllegalArgumentException(String.format("Given argument %s has not been provided " +
                    "by AbstractUserInput.getUserInputIdentifiers()", registeredKey));

        if (wordStems.containsKey(registeredKey))
            throw new IllegalArgumentException(String.format("The key %s has already been configured as word stem.", registeredKey));

        wordStems.put(registeredKey, new WordStemTerm());
    }

    public void addWordStemChild(String governingWordStemKey, String inflectedTermKey) {
        if (!isKeyRegistered(governingWordStemKey))
            throw new IllegalArgumentException("The word stem key has not been registered with AbstractUserInput.registerIdentifier()");
        if (!isKeyRegistered(inflectedTermKey))
            throw new IllegalArgumentException("The child key has not been registered with AbstractUserInput.registerIdentifier()");
        if (!wordStems.containsKey(governingWordStemKey))
            throw new IllegalArgumentException(String.format("The key %s has not yet been configured as word stem.", governingWordStemKey));
        if (inflectedTerms.containsKey(inflectedTermKey))
            throw new IllegalArgumentException(String.format("The key %s has already been configured as an inflected term.", inflectedTermKey));
        inflectedTerms.put(inflectedTermKey, new InflectedTerm(wordStems.get(governingWordStemKey)));
    }

    @Override
    public abstract VocableVerificationData getQuizVerificationData();

    public String getPurgedUserData(String identifier) {
        return getUserData(identifier).getCleanedString();
    }

    public String getUserEnteredTerm(String identifier) {
        return getUserData(identifier).getUserEnteredString();
    }

    public String getResolvedUserData(String identifier) {
        return getUserData(identifier).getStringWithWordStemResolved();
    }

    public String getResolvedAndPurgedUserData(String identifier) {
        return getUserData(identifier).getCleanedStringWithWordStemResolved();
    }
}
