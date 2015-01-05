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
import org.jlexis.data.vocable.userinput.UserInput;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.*;

/**
 * Data object representing exactly one vocable. This class is simply a container holding a map of {@link VocableData}
 * objects. Each {@link VocableData} represents the language specific part of the vocable.
 *
 * @author Roland Krueger
 * @see VocableData
 */
public class Vocable implements Serializable {
    private static final long serialVersionUID = - 5117218170042020375L;

    private Map<Language, VocableData> data;
    private long id;

    /**
     * Copy constructor. Used by {@link org.jlexis.data.vocable.UnmodifiableVocable}.
     */
    protected Vocable(Vocable other) {
        this.data = other.data;
    }

    public Vocable() {
        data = new HashMap<>(2);
    }

    /**
     * Adds one language specific part of a vocable to this object. Such a fragment consists of three parts. The first
     * argument provides the {@link Language} that was used by the user to add the language specific data to the
     * vocable. The second argument defines the word type (such as noun, verb, etc.) of the current vocable. The third
     * argument is the data itself.
     *
     * @param forLanguage
     *         the {@link Language
     * @param wordClass
     *         the current word type (verb, noun, etc.)
     * @param userInput
     *         the user input
     */
    public void addVariant(Language forLanguage, AbstractWordClass wordClass, UserInput userInput) {
        removeDataFor(forLanguage);
        data.put(forLanguage, new VocableData(forLanguage, wordClass, userInput));
    }

    public UserInput getVariantInput(Language forLanguage) {
        checkArgument(data.containsKey(forLanguage), String.format("Language %s is not defined for this Vocable object.",
                forLanguage));

        return data.get(forLanguage).getUserInput();
    }

    public AbstractWordClass getVariantWordClass(Language forLanguage) {
        checkArgument(data.containsKey(forLanguage), String.format("Language %s is not defined for this Vocable object.",
                forLanguage.getName()));

        return data.get(forLanguage).getWordClass();
    }

    private VocableData removeDataFor(Language language) {
        return data.remove(language);
    }

    /**
     * Determines whether the user data contained in this {@link Vocable} object is empty, i. e. whether no data
     * whatsoever is held by the {@link VocableData} elements. This method uses a pure technical definition of
     * "empty", i.e. a vocable is empty if all of its {@link org.jlexis.data.vocable.VocableData} elements are empty.
     * These are empty in turn if their user input is empty. A user input is empty if all of its term data is empty
     * (i.e. the term data elements are either not present or contain an empty string).
     *
     * @return <code>true</code> if this vocable doesn't contain any user-provided data
     * @see #isAnyTextInputDefined()
     */
    public boolean isEmpty() {
        for (VocableData dataElement : data.values()) {
            if (! dataElement.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public boolean isVariantDefined(Language forLanguage) {
        return data.containsKey(forLanguage);
    }

    /**
     * Determines if there is any textual input defined in this vocable that has been provided by the user. This
     * information is needed in addition to {@link #isEmpty()} to determine whether or not a vocable object can be
     * seen as not defined as per the user. There may still be data contained in the vocable data, e.g. default
     * values or non-textual input such as boolean values (encoded as strings) or enumeration values. If a vocable is
     * determined as not defined by this method, it could be safely removed without losing any user-provided data.
     *
     * @see org.jlexis.data.vocable.userinput.UserInput#isAnyTextInputDefined()
     */
    public boolean isAnyTextInputDefined() {
        for (VocableData dataElement : data.values()) {
            if (! dataElement.getUserInput().isAnyTextInputDefined()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a list of all languages that are used for this vocable object.
     */
    public Collection<Language> getLanguages() {
        return Collections.unmodifiableCollection(data.keySet());
    }

    protected long getId() {
        return id;
    }

    protected void setId(long id) {
        this.id = id;
    }
}
