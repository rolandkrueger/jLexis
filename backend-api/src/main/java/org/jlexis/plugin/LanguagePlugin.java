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

import com.google.common.base.MoreObjects;
import org.jlexis.data.DefaultWordType;
import org.jlexis.data.vocable.AbstractWordType;
import org.jlexis.quiz.data.AbstractQuizType;

import java.util.*;

public abstract class LanguagePlugin {
    protected Locale locale;
    private Map<String, AbstractWordType> wordTypes;
    private String name;
    private String description;
    private PluginIdentifier identifier;

    public LanguagePlugin(PluginIdentifier identifier) {
        this.identifier = Objects.requireNonNull(identifier);
        wordTypes = new HashMap<>();
    }

    /**
     * @return i18n key for the translation of the plugin's target language
     */
    public abstract String getLanguageNameKey();

    public abstract String getIconId();

    protected abstract AbstractWordType getCorrespondingWordTypeForImpl(AbstractWordType wordType);

    public abstract AbstractWordType getDefaultWordType();

    public abstract List<AbstractQuizType> getQuizTypes();

    public abstract List<Set<String>> getAbbreviationAlternatives();

    /**
     * A default language plugin can be used for every language. It has no language specific features and is thus less
     * flexible when it comes to handling the characteristics of a language. Since a default language plugin is
     * independent of a specific language, no language name can be assigned to the plugin. Therefore, if the default
     * plugin is used by the user, the desired language name is queried from her by jLexis.
     *
     * @return <code>true</code> if this plugin is a default plugin
     */
    public abstract boolean isDefaultPlugin();

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    protected void registerWordType(AbstractWordType wordType) {
        AbstractWordType testIfAlreadyRegistered = wordTypes.get(wordType.getIdentifier());
        if (testIfAlreadyRegistered != null)
            throw new IllegalArgumentException("This word type has already been registered.");

        wordTypes.put(wordType.getIdentifier(), wordType);
    }

    public void registerUserInputIdentifiers() {
        for (AbstractWordType wordType : wordTypes.values()) {
            wordType.registerUserInputIdentifiers();
        }
    }

    public Collection<AbstractWordType> getWordTypes() {
        if (wordTypes.isEmpty()) {
            // TODO: I18N
            return Collections.singleton(new DefaultWordType("allg. Wort"));
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

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public AbstractWordType getCorrespondingWordTypeFor(AbstractWordType wordType) {
        AbstractWordType result = getCorrespondingWordTypeForImpl(wordType);
        if (result == null) {
            return getDefaultWordType();
        }
        return result;
    }

    public PluginIdentifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(PluginIdentifier identifier) {
        this.identifier = identifier;
    }

    @Override
    public final int hashCode() {
        return identifier.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LanguagePlugin that = (LanguagePlugin) o;
        return Objects.equals(identifier, that.identifier);
    }

    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", getName())
                .add("id", identifier)
                .omitNullValues().toString();
    }
}
