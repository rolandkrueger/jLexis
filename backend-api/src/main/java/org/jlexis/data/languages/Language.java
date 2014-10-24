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
import org.jlexis.plugin.PluginIdentifier;

import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * This class represents a single language that can be learned by the user. When
 * creating a new {@link org.jlexis.data.units.LearningUnit} the user typically selects one or more foreign languages she
 * wants to learn with this unit. Each of these languages are represented by a {@link Language}
 * object.
 * </p>
 * <p>
 * A {@link Language} is defined by the name of the language and a reference to the
 * {@link LanguagePlugin} from which the language was created. The language name is usually predefined by a language plugin, i.e.
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
     * The name of the language. Is usually defined by the {@link LanguagePlugin}, except for default
     * plugins. When a default language plugin is chosen for a language the user has to provide the name of the language.
     */
    private String languageName;

    /**
     * The identifier of the corresponding {@link LanguagePlugin} as provided by
     * {@link LanguagePlugin#getIdentifier()}. This value is set after loading the {@link Language}
     * object from the database and will be used to find a matching {@link LanguagePlugin} object from
     * the set of available {@link LanguagePlugin}s. Note that this set can differ from the set that
     * was available when saving the {@link Language} object to the database. This may be the case
     * when plugins are deleted or a newer version of a plugin is installed.
     */
    private PluginIdentifier sourcePlugin;

    private long id;

    /**
     * Creates a new {@link Language} object for the given {@link LanguagePlugin}. The name for the new
     * {@link Language} object is defined by the plugin.
     *
     * @param sourcePluginIdentifier identifier for the plugin that provides this {@link Language}.
     */
    public Language(PluginIdentifier sourcePluginIdentifier, String languageName) {
        Objects.requireNonNull(sourcePluginIdentifier);

        this.sourcePlugin = sourcePluginIdentifier;
        this.languageName = languageName;
    }

    public String getName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = Objects.requireNonNull(languageName);
    }

    @Deprecated
    public Optional<LanguagePlugin> getLanguagePlugin() {
        // TODO delete me
        throw new UnsupportedOperationException();
    }

    public PluginIdentifier getPluginIdentifier() {
        return sourcePlugin;
    }

    @Override
    public String toString() {
        return com.google.common.base.Objects.toStringHelper(this).add("name", languageName).toString();
    }

    @Override
    public int hashCode() {
        return languageName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj instanceof Language) {
            Language other = (Language) obj;
            if (other.getName().equals(getName())) return true;
        }

        return false;
    }

    @SuppressWarnings("unused")
    private long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    private void setId(long id) {
        this.id = id;
    }
}
