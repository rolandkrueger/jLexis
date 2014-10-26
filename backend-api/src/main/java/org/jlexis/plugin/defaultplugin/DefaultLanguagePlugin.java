/*
 * DefaultLanguagePlugin.java
 * Created on 31.01.2007
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
package org.jlexis.plugin.defaultplugin;

import org.jlexis.data.vocable.AbstractWordType;
import org.jlexis.plugin.LanguagePlugin;
import org.jlexis.plugin.PluginIdentifier;
import org.jlexis.quiz.data.AbstractQuizType;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class DefaultLanguagePlugin extends LanguagePlugin {

    public DefaultLanguagePlugin() {
        super(new PluginIdentifier("org.jlexis.defaultplugin", "1.0"));
    }

    @Override
    public AbstractWordType getDefaultWordType() {
        assert getWordTypes().size() > 0;
        // return the word type that is added to the set of registered word types by default when no
        // other word types have explicitly been registered
        return getWordTypes().iterator().next();
    }

    @Override
    public String getLanguageNameKey() {
        // TODO: i18n
        return "Default";
    }

    @Override
    public String getIconId() {
        // TODO fix me
        return "EMPTY";
    }

    @Override
    protected AbstractWordType getCorrespondingWordTypeForImpl(AbstractWordType wordType) {
        return getDefaultWordType();
    }

    @Override
    public boolean isDefaultPlugin() {
        return true;
    }

    @Override
    public List<AbstractQuizType> getQuizTypes() {
        return Collections.emptyList();
    }

    @Override
    public List<Set<String>> getAbbreviationAlternatives() {
        return Collections.emptyList();
    }
}
