/*
 * Created on 26.09.2009
 * 
 * Copyright 2007-2009 Roland Krueger (www.rolandkrueger.info)
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
package org.jlexis.data.vocable.verification;


import org.jlexis.data.vocable.AbstractWordClass;
import org.jlexis.plugin.LanguagePlugin;
import org.jlexis.plugin.PluginIdentifier;
import org.jlexis.quiz.data.AbstractQuizType;

import java.util.*;

/**
 * @author Roland Krueger
 * @version $Id: LanguagePluginTestImplementation.java 138 2009-10-16 06:45:04Z roland $
 */
public class LanguagePluginTestImplementation extends LanguagePlugin {
    private List<Set<String>> abbreviationAlternatives;
    private String[] alternatives = {"il y a", "iya", "i.y.a.", ".i.y.a."};

    public LanguagePluginTestImplementation() {
        super(new PluginIdentifier("test", "1.0"));
        abbreviationAlternatives = new ArrayList<Set<String>>();
        Set<String> set = new HashSet<String>();
        set.addAll(Arrays.asList(alternatives));
        abbreviationAlternatives.add(set);
    }

    @Override
    public List<Set<String>> getAbbreviationAlternatives() {
        return abbreviationAlternatives;
    }

    @Override
    protected AbstractWordClass getCorrespondingWordTypeForImpl(AbstractWordClass wordType) {
        return null;
    }

    @Override
    public AbstractWordClass getDefaultWordType() {
        return null;
    }

    @Override
    public String getLanguageNameKey() {
        return "";
    }

    @Override
    public String getIconId() {
        return null;
    }

    @Override
    public List<AbstractQuizType> getQuizTypes() {
        return null;
    }

    @Override
    public boolean isDefaultPlugin() {
        return false;
    }
}
