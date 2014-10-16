/*
 * LanguagePlugin.java
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
package org.jlexis.plugin;

import org.jlexis.data.DefaultWordType;
import org.jlexis.data.vocable.AbstractWordType;
import org.jlexis.quiz.data.AbstractQuizType;

import java.util.*;

public abstract class LanguagePlugin {
    private Map<String, AbstractWordType> wordTypes;
    protected Locale locale;
    private String pluginName;
    private String versionString;
    private int versionNumber;
    private String description;
    private String identifier;
    private String providerURL;
    private Integer hashCode;

    public LanguagePlugin() {
        providerURL = "";
    }

    public abstract String getLanguageName();

    public abstract String getIconId();

    protected abstract AbstractWordType getCorrespondingWordTypeForImpl(AbstractWordType wordType);

    public abstract AbstractWordType getDefaultWordType();

    public abstract List<AbstractQuizType> getQuizTypes();

    public abstract List<Set<String>> getAbbreviationAlternatives();

    /**
     * A default language plugin can be used for every language. It has no language specific features
     * and is thus less flexible when it comes to handling the characteristics of a language. Since a
     * default language plugin is independent of a specific language, no language name can be assigned
     * to the plugin. Therefore, if the default plugin is used by the user, the desired language name
     * is queried from her by jLexis.
     *
     * @return <code>true</code> if this plugin is a default plugin
     */
    public abstract boolean isDefaultPlugin();

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    protected void registerWordType(AbstractWordType wordType) {
        // lazy instantiation of the word types list
        if (wordTypes == null) {
            wordTypes = new HashMap<String, AbstractWordType>();
        }
        AbstractWordType testIfAlreadyRegistered = wordTypes.get(wordType.getIdentifier());
        if (testIfAlreadyRegistered != null)
            throw new IllegalArgumentException("This word type has already been registered.");

        wordTypes.put(wordType.getIdentifier(), wordType);
    }

    public void registerUserInputIdentifiers() {
        if (wordTypes == null) return;
        for (AbstractWordType wordType : wordTypes.values()) {
            wordType.registerUserInputIdentifiers();
        }
    }

    public Collection<AbstractWordType> getWordTypes() {
        // if no word types have been registered for this plugin by a subclass, add at least
        // the default word type
        if (wordTypes == null || wordTypes.size() == 0) {
            wordTypes = new HashMap<String, AbstractWordType>();
            // TODO: I18N
            AbstractWordType defaultWordType = new DefaultWordType("allg. Wort");
            wordTypes.put(defaultWordType.getIdentifier(), defaultWordType);
        }
        return wordTypes.values();
    }

    public AbstractWordType getWordTypeFor(String identifier) {
        return wordTypes.get(identifier);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPluginName() {
        return pluginName;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    public String getVersionString() {
        if (versionString == null || versionString.equals(""))
            return String.valueOf(versionNumber);
        return versionString;
    }

    public void setVersionString(String version) {
        versionString = version;
    }

    public String toString() {
        return getLanguageName();
    }

    public AbstractWordType getCorrespondingWordTypeFor(AbstractWordType wordType) {
        AbstractWordType result = getCorrespondingWordTypeForImpl(wordType);
        if (result == null) {
            return getDefaultWordType();
        }
        return result;
    }

    public String getIdentifier() {
        if (identifier == null)
            throw new IllegalStateException("Identifier for LanguagePlugin hasn't been set yet.");
        return identifier;
    }

    public void setIdentifier(String identifier) {
        hashCode = null;
        this.identifier = identifier;
    }

    public int getVersionNumber() {
        hashCode = null;
        return versionNumber;
    }

    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    /**
     * Returns the URL of the plugin's provider. This is the person or organization that maintains and
     * releases the language plugin.
     *
     * @return The URL of the plugins originator.
     */
    public String getProviderURL() {
        return providerURL;
    }

    /**
     * Sets the URL of the plugin's provider.
     *
     * @param providerURL a URL string
     */
    public void setProviderURL(String providerURL) {
        if (providerURL == null) throw new NullPointerException("Provider URL is null.");
        this.providerURL = providerURL;
    }

    @Override
    public final int hashCode() {
        if (hashCode == null) {
            hashCode = new String(getIdentifier() + getVersionNumber()).hashCode();
        }
        return hashCode;
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof LanguagePlugin))
            return false;
        LanguagePlugin other = (LanguagePlugin) obj;
        if (other.identifier.equals(identifier) && other.versionNumber == versionNumber)
            return true;

        return false;
    }
}
