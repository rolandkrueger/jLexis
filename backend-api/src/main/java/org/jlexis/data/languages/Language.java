/*
 * $Id: Language.java 197 2009-12-14 07:27:08Z roland $
 * Created on 19.11.2008
 * 
 * Copyright 2007 Roland Krueger (www.rolandkrueger.info)
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
package org.jlexis.data.languages;

import org.jlexis.plugin.LanguagePlugin;

import java.util.Optional;

/**
 * <p>
 * A {@link Language} object represents a single language that can be learned by the user. When
 * creating a new {@link org.jlexis.data.units.LearningUnit} the user typically selects one or more foreign languages she
 * wants to learn with this unit. Each of these languages are represented by a {@link Language}
 * object.
 * </p>
 * <p>
 * A {@link Language} object is defined by the name of the language and a correponding
 * {@link LanguagePlugin} object. The language name is usually predefined by a language plugin, i.e.
 * if the user selects a language specific {@link LanguagePlugin}, she simultaneously selects the
 * corresponding {@link Language} object.
 * <p>
 * This is different with default language plugins. Such plugins don't have a corresponding
 * {@link Language} object. This is because a default language plugin can handle every language. If
 * the user selects a default language plugin for her new {@link org.jlexis.data.units.LearningUnit}, she is asked to
 * provide the name of the language she wants to handle with this plugin. With this more languages
 * can be learned than language plugins are available. Each {@link org.jlexis.data.units.LearningUnit} can manage an own set
 * of {@link Language} objects which are independent of other {@link org.jlexis.data.units.LearningUnit}s.
 * </p>
 *
 * @author Roland Krueger
 */
public class Language {
    /**
     * The corresponding {@link LanguagePlugin} for this {@link Language} object. This object remains
     * <code>null</code> directly after loading the {@link Language} object from the database.
     */
    private Optional<LanguagePlugin> mPlugin;
    /**
     * The name of the language. Is usually defined by the {@link LanguagePlugin}, except for default
     * plugins.
     */
    private Optional<String> mLanguageName;
    /**
     * The identifier of the corresponding {@link LanguagePlugin} as provided by
     * {@link LanguagePlugin#getIdentifier()}. This value is set after loading the {@link Language}
     * object from the database and will be used to find a matching {@link LanguagePlugin} object from
     * the set of available {@link LanguagePlugin}s. Not that this set can differ from the set that
     * was available when saving the {@link Language} object to the database. This may be the case
     * when plugins are deleted or a newer version of a plugin is installed.
     */
    private Optional<String> mPluginIdentifier;
    /**
     * The version of the corresponding {@link LanguagePlugin} as provided by
     * {@link LanguagePlugin#getVersionNumber()}. The same information applies for the plugin version as
     * described under {@link Language#mPluginIdentifier}.
     */
    private Optional<Integer> mPluginVersion;

    private long mID;

    /**
     * Default constructor.
     */
    protected Language() {
        mPlugin = Optional.empty();
        mLanguageName = Optional.empty();
        mPluginIdentifier = Optional.empty();
        mPluginVersion = Optional.empty();
    }

    /**
     * Creates a new {@link Language} object for the given {@link LanguagePlugin}. The name for the new
     * {@link Language} object is defined by the plugin.
     *
     * @param pluginForThisLanguage the plugin that corresponds to this {@link Language}.
     */
    protected Language(LanguagePlugin pluginForThisLanguage) {
        this();
        if (pluginForThisLanguage == null) throw new NullPointerException("LanguagePlugin object is null!");

        setLanguagePlugin(pluginForThisLanguage);
        setLanguageName(pluginForThisLanguage.getLanguageName());
    }

    public String getLanguageName() {
        return mLanguageName.get();
    }

    public void setLanguageName(String languageName) {
        if (languageName == null)
            throw new IllegalArgumentException("Language name is null.");
        mLanguageName = Optional.of(languageName);
    }

    public void setLanguagePlugin(LanguagePlugin plugin) {
        mPlugin = Optional.of(plugin);
        setPluginIdentifier(plugin.getIdentifier());
        setPluginVersion(plugin.getVersionNumber());
    }

    public Optional<LanguagePlugin> getLanguagePlugin() {
        if (!mPlugin.isPresent()) {
//        TODO: fix me
            // a plugin instance has not yet been set for this language. Try to fetch one.
//      LanguagePlugin result = JLexisMain.getInstance ().getPluginManager ().getPluginFor (this);
//      if (result != null)
//        mPlugin.setValue (result);
//      else
//        throw new RuntimeException("Unable to find a matching plugin for language " + toString ());
        }
        return mPlugin;
    }

    public String getPluginIdentifier() {
        return mPluginIdentifier.get();
    }

    public void setPluginIdentifier(String pluginIdentifier) {
        mPluginIdentifier = Optional.of(pluginIdentifier);
    }

    public Integer getPluginVersion() {
        return mPluginVersion.get();
    }

    public void setPluginVersion(int pluginVersion) {
        mPluginVersion = Optional.of(pluginVersion);
    }

    @Override
    public String toString() {
        if (!mLanguageName.isPresent()) return "?";
        return mLanguageName.toString();
    }

    @Override
    public int hashCode() {
        return getLanguageName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj instanceof Language) {
            Language other = (Language) obj;
            if (other.getLanguageName().equals(getLanguageName())) return true;
        }

        return false;
    }

    @SuppressWarnings("unused")
    private long getId() {
        return mID;
    }

    @SuppressWarnings("unused")
    private void setId(long id) {
        mID = id;
    }
}
