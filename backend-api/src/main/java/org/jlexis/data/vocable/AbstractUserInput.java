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

import org.jlexis.data.vocable.terms.*;
import org.jlexis.data.vocable.verification.VocableVerificationData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * <p>
 * This class generically represents a user's input for one vocable in one specific language. If,
 * for example, the user entered the English translation for a vocable, the corresponding
 * {@link AbstractUserInput} object would contain that translation, an example phrase, a comment and
 * some more optional data.
 * </p>
 * <p>
 * The data itself is held in a {@link java.util.Map}. By this, a maximum flexibility is achieved since the
 * particular pieces of data belonging to this {@link AbstractUserInput} can be referenced by
 * arbitrary keys. Subclasses of {@link AbstractUserInput} can freely choose which data to store in
 * this internal data structure.
 * </p>
 * TODO: describe data caching feature
 *
 * @author Roland Krueger
 */
public abstract class AbstractUserInput implements UserInputInterface {
    private enum DataType {COMMENT, EXAMPLE, SHORT_VERSION, HTML_VERSION}

    ;

    private static Map<String, RegisteredVocableDataKey> sRegisteredKeys;
    private static boolean sIsSynchronizedWithDatabase;

    /**
     * Object that represents this {@link AbstractUserInput} in the database.
     */
    private DBO_AbstractUserInput mDatabaseObject;

    /**
     * The data of this {@link AbstractUserInput}. This object assembles all the fragments
     * that forms the language specific part of a {@link Vocable}, such as
     * the vocabulary data of one language itself, comments, examples and extra data.
     */
    private Map<RegisteredVocableDataKey, AbstractTermData> mData;
    private String mInputType;
    private Map<String, WordStemTerm> mWordStems;
    private Map<String, InflectedTerm> mInflectedTerms;

    /**
     * Cache that holds the formatted texts for the data of this {@link AbstractUserInput}. This data
     * consists of the short version as obtained by {@link AbstractUserInput#getShortVersion()}, the
     * HTML-version as obtained by {@link AbstractUserInput#getHTMLVersion()} etc. The cache is cleared
     * each time that the data of this object is changed.
     */
    private Map<DataType, String> mDataCache;

    static {
        sRegisteredKeys = new HashMap<String, RegisteredVocableDataKey>();
        sIsSynchronizedWithDatabase = false;
    }

    private AbstractUserInput() {
        mData = new HashMap<RegisteredVocableDataKey, AbstractTermData>(8);
        mWordStems = new HashMap<String, WordStemTerm>(1);
        mInflectedTerms = new HashMap<String, InflectedTerm>(5);
    }

    protected AbstractUserInput(String inputType) {
        this();
//    CheckForNull.check (inputType);
        if (inputType.trim().equals(""))
            throw new IllegalArgumentException("Empty input type string not accepted.");

        mInputType = inputType;
    }

    public DBO_AbstractUserInput getDatabaseObject() {
        if (mDatabaseObject == null)
            mDatabaseObject = new DBO_AbstractUserInput();

        Map<RegisteredVocableDataKey, RegularTerm> preparedData = new HashMap<RegisteredVocableDataKey, RegularTerm>();
        for (RegisteredVocableDataKey key : mData.keySet()) {
            preparedData.put(key, new RegularTerm(mData.get(key).getNormalizedTerm()));
        }

        mDatabaseObject.setData(preparedData);
        mDatabaseObject.setInputType(mInputType);

        return mDatabaseObject;
    }

    protected void setDatabaseObject(DBO_AbstractUserInput databaseObj) {
        if (databaseObj == null)
            throw new NullPointerException("Argument is null.");

        clearCache(); // clear cache when data changes
        mData.clear();
        Map<RegisteredVocableDataKey, RegularTerm> dboMap = databaseObj.getData();
        for (RegisteredVocableDataKey key : dboMap.keySet()) {
            AbstractTermData term = getRegisteredTermTypeForKey(key.getKey());
            term.setNormalizedTerm(dboMap.get(key).getNormalizedTerm());
            mData.put(key, term);
        }

        mInputType = databaseObj.getInputType();
    }

    @Override
    public final String getShortVersion() {
        return getDataFromCache(DataType.SHORT_VERSION);
    }

    @Override
    public final String getHTMLVersion() {
        return getDataFromCache(DataType.HTML_VERSION);
    }

    @Override
    public final String getComment() {
        return getDataFromCache(DataType.COMMENT);
    }

    @Override
    public final String getExample() {
        return getDataFromCache(DataType.EXAMPLE);
    }

    protected abstract String getShortVersionImpl();

    protected abstract String getHTMLVersionImpl();

    protected abstract String getCommentImpl();

    protected abstract String getExampleImpl();

    @Override
    public abstract boolean isEmpty();

    @Override
    public abstract void init();

    protected abstract AbstractUserInput createNewUserInputObject();

    @Deprecated
    public void appendXMLData(Document document, Element root) {
//    for (RegisteredVocableDataKey key : mData.keySet ())
//    {
//      String userData = getUserData (key.getKey ());
//      if (userData == null || userData.equals ("")) continue;
//      Element particle = document.createElement ("particle");
//      particle.setAttribute ("id", key.getKey ());
//      
//      particle.appendChild (document.createCDATASection (userData));
//      
//      root.appendChild (particle);
//    }
    }

    public void addUserData(String identifier, String data) {
        if (!isKeyRegistered(identifier))
            throw new IllegalStateException(String.format("Given identifier '%s' has not been provided " +
                    "by AbstractUserInput.getUserInputIdentifiers().", identifier));
        if (!sIsSynchronizedWithDatabase)
            throw new IllegalStateException("Registered keys have not yet been synchronized with database. " +
                    "Call " + AbstractUserInput.class.getName() + ".synchronizeRegisteredKeysWithDatabase() first.");
        if (data == null)
            throw new NullPointerException("User data object is null.");

        clearCache(); // clear the cache when data changes
        AbstractTermData term = getRegisteredTermTypeForKey(identifier);
        term.setUserEnteredTerm(data);
        mData.put(sRegisteredKeys.get(identifier), term);
    }

    private AbstractTermData getRegisteredTermTypeForKey(String identifier) {
        AbstractTermData term = mInflectedTerms.get(identifier);
        if (term != null) return term;

        term = mWordStems.get(identifier);
        if (term != null) return term;

        term = new RegularTerm();
        return term;
    }

    private boolean isKeyRegistered(String identifier) {
        return sRegisteredKeys.containsKey(identifier);
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

            sRegisteredKeys.put(identifier, new RegisteredVocableDataKey(identifier));
        }
    }

    protected abstract String[] getUserInputIdentifiers();

    public boolean isTypeCorrect(UserInputInterface other) {
        return mInputType.equals(other.getUserInputIdentifier());
    }

    public TermDataInterface getUserData(String identifier) {
        if (!isKeyRegistered(identifier))
            throw new IllegalStateException(String.format("Given identifier '%s' has not been provided " +
                    "by AbstractUserInput.getUserInputIdentifiers().", identifier));

        AbstractTermData result = mData.get(new RegisteredVocableDataKey(identifier));
        if (result == null) result = new RegularTerm();

        return result;
    }

    public boolean isDataDefinedFor(String identifier) {
        RegisteredVocableDataKey key = new RegisteredVocableDataKey(identifier);
        return mData.containsKey(key) && !mData.get(key).isEmpty();
    }

    public String getUserInputIdentifier() {
        return mInputType;
    }

    public void replace(AbstractUserInput userInput) {
        clearCache(); // clear the cache when data changes
        mData.clear();
        for (RegisteredVocableDataKey key : userInput.mData.keySet()) {
            mData.put(key, userInput.mData.get(key));
        }
    }

    public void setWordStem(String registeredKey) {
        if (!isKeyRegistered(registeredKey))
            throw new IllegalArgumentException(String.format("Given argument %s has not been provided " +
                    "by AbstractUserInput.getUserInputIdentifiers()", registeredKey));

        if (mWordStems.containsKey(registeredKey))
            throw new IllegalArgumentException(String.format("The key %s has already been configured as word stem.", registeredKey));

        mWordStems.put(registeredKey, new WordStemTerm());
    }

    public void addWordStemChild(String governingWordStemKey, String inflectedTermKey) {
        if (!isKeyRegistered(governingWordStemKey))
            throw new IllegalArgumentException("The word stem key has not been registered with AbstractUserInput.registerIdentifier()");
        if (!isKeyRegistered(inflectedTermKey))
            throw new IllegalArgumentException("The child key has not been registered with AbstractUserInput.registerIdentifier()");
        if (!mWordStems.containsKey(governingWordStemKey))
            throw new IllegalArgumentException(String.format("The key %s has not yet been configured as word stem.", governingWordStemKey));
        if (mInflectedTerms.containsKey(inflectedTermKey))
            throw new IllegalArgumentException(String.format("The key %s has already been configured as an inflected term.", inflectedTermKey));
        mInflectedTerms.put(inflectedTermKey, new InflectedTerm(mWordStems.get(governingWordStemKey)));
    }

    /**
     * Loads the IDs of the {@link RegisteredVocableDataKey}s from the database if the corresponding
     * key is availabe. If the key isn't available in the database yet, store the key into the database.
     */
    @SuppressWarnings("unchecked")
    public static void synchronizeRegisteredKeysWithDatabase() {
//      TODO: refactor
//    List<RegisteredVocableDataKey> registeredKeys =
//      new ArrayList<RegisteredVocableDataKey>(sRegisteredKeys.values ());
//    Session session = JLexisPersistenceManager.getInstance ().getSession ();
//    Query qry = session.createQuery ("FROM RegisteredVocableDataKey");
//    List<RegisteredVocableDataKey> keys = qry.list ();
//
//    for (RegisteredVocableDataKey key : keys)
//    {
//      registeredKeys.remove (key);
//      sRegisteredKeys.put (key.getKey (), key);
//    }
//
//    // persist all new keys not available in the database
//    for (RegisteredVocableDataKey key : registeredKeys)
//    {
//      JLexisPersistenceManager.getInstance ().saveObject (key);
//    }
//    sIsSynchronizedWithDatabase = true;
    }

    private void clearCache() {
        if (mDataCache == null) return;
        mDataCache.clear();
        mDataCache = null;
    }

    private String getDataFromCache(DataType type) {
        if (mDataCache == null) {
            mDataCache = new Hashtable<DataType, String>(4);
        }
        if (!mDataCache.containsKey(type)) {
            String data = null;
            switch (type) {
                case COMMENT:
                    data = getCommentImpl();
                    break;
                case EXAMPLE:
                    data = getExampleImpl();
                    break;
                case HTML_VERSION:
                    data = getHTMLVersionImpl();
                    break;
                case SHORT_VERSION:
                    data = getShortVersionImpl();
            }
            if (data == null) data = "";
            mDataCache.put(type, data);
            return data;
        }

        return mDataCache.get(type);
    }

    @Override
    public abstract VocableVerificationData getQuizVerificationData();

    public String getPurgedUserData(String identifier) {
        return getUserData(identifier).getPurgedTerm();
    }

    public String getUserEnteredTerm(String identifier) {
        return getUserData(identifier).getUserEnteredTerm();
    }

    public String getResolvedUserData(String identifier) {
        return getUserData(identifier).getResolvedTerm();
    }

    public String getResolvedAndPurgedUserData(String identifier) {
        return getUserData(identifier).getResolvedAndPurgedTerm();
    }
}
