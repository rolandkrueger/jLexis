/*
 * Vocable.java
 * Created on 15.04.2007
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


import org.jlexis.data.languages.Language;
import org.jlexis.data.vocable.userinput.AbstractUserInput;
import org.jlexis.data.vocable.userinput.UserInput;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;

/**
 * Data object representing exactly one vocable. This class is simply a container holding a list of {@link VocableData}
 * objects. Each {@link VocableData} represents the language specific part of the vocable.
 *
 * @author Roland Krueger
 * @see VocableData
 */
public class Vocable implements Serializable {
    private static final long serialVersionUID = -5117218170042020375L;

    protected Map<Language, VocableData> mData;
    private long mId;

    public Vocable() {
        mData = new Hashtable<Language, VocableData>();
    }

    /**
     * Adds one language specific part of a vocable to this object. Such a fragment consists of three parts. The first
     * argument provides the {@link Language} that was used by the user to add the language specific data to the
     * vocable. The second argument defines the word type (such as noun, verb, etc.) of the current vocable. The third
     * argument is the data itself.
     *
     * @param forLanguage the {@link Language
     * @param wordType    the current word type (verb, noun, etc.)
     * @param data        the user input
     */
    public void addVariant(Language forLanguage, AbstractWordType wordType, AbstractUserInput data) {
        removeDataFor(forLanguage);
        mData.put(forLanguage, new VocableData(forLanguage, wordType, data));
    }

    public UserInput getVariantInput(Language forLanguage) {
        if (!mData.containsKey(forLanguage)) {
            throw new IllegalArgumentException(String.format("Language %s is not defined for this Vocable object.",
                    forLanguage));
        }

        AbstractUserInput result = mData.get(forLanguage).getUserInput();
        return result;
    }

    public AbstractWordType getVariantWordType(Language forLanguage) {
        if (!mData.containsKey(forLanguage))
            throw new IllegalArgumentException(String.format("Language %s is not defined for this Vocable object.",
                    forLanguage.getName()));

        return mData.get(forLanguage).getWordType();
    }

    private VocableData removeDataFor(Language plugin) {
        return mData.remove(plugin);
    }

    /**
     * Removes all data previously added to this {@link Vocable} and replaces it with the data of the provided {@link
     * Vocable} object.
     *
     * @param voc a {@link Vocable} object the data of which is to be copied into this object
     */
    public void replace(Vocable voc) {
        for (Language lang : mData.keySet()) {
            VocableData otherData = voc.mData.get(lang);
            if (otherData == null)
                throw new IllegalArgumentException("Unable to replace vocable data. Given vocable object does not match.");
            VocableData thisData = mData.get(lang);
            thisData.replaceInput(otherData);
        }

//    mData.clear ();
//    mData.putAll (voc.mData);
    }

    /**
     * Determines whether the user data contained in this {@link Vocable} object is empty, i. e. whether no data
     * whatsoever is held by the {@link VocableData} elements.
     *
     * @return <code>true</code> if this vocable doesn't contain any user-provided data
     */
    public boolean isEmpty() {
        for (VocableData dataElement : mData.values()) {
            if (!dataElement.isEmpty()) return false;
        }
        return true;
    }

    protected Map<Language, VocableData> getVocableData() {
        return mData;
    }

    /**
     * Setter method for the vocable data. This method is used by Hibernate when loading a {@link Vocable} object from
     * the database.
     *
     * @param data the vocable data
     */
    @SuppressWarnings("unused")
    private void setVocableData(Map<Language, VocableData> data) {
        // TODO: check if the following will result in problems with Hibernate, due to copying the map
        // instead of assigning it.
        mData.putAll(data);
    }

    public Collection<Language> getLanguages() {
        return Collections.unmodifiableCollection(mData.keySet());
    }

    protected long getId() {
        return mId;
    }

    protected void setId(long id) {
        mId = id;
    }
}
