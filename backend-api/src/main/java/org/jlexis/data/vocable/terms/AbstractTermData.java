/*
 * $Id: AbstractTermData.java 204 2009-12-17 15:20:16Z roland $
 * Created on 07.03.2009
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
package org.jlexis.data.vocable.terms;

import com.google.common.base.MoreObjects;
import org.jlexis.managers.ConfigurationManager;

import java.util.Objects;

public abstract class AbstractTermData {
    protected final static String WORD_STEM_MARKER = "${|}";
    protected final static String WORD_STEM_BEGIN_MARKER = "${<}";
    protected final static String WORD_STEM_END_MARKER = "${>}";
    protected final static String WORD_STEM_PLACEHOLDER = "${-}";
    protected final static String SPECIAL_CHAR_PLACEHOLDER = "${$}";

    protected String normalizedTerm = "";

    public String getNormalizedTerm() {
        return normalizedTerm;
    }

    public void setNormalizedTerm(String normalizedTerm) {
        this.normalizedTerm = Objects.requireNonNull(normalizedTerm).trim();
    }

    public String getUserEnteredTerm() {
        ConfigurationManager config = ConfigurationManager.getInstance();
        String result = normalizedTerm.replace(WORD_STEM_MARKER, config.getWordStemMarker());
        result = result.replace(WORD_STEM_BEGIN_MARKER, config.getWordStemBeginMarker());
        result = result.replace(WORD_STEM_END_MARKER, config.getWordStemEndMarker());
        result = result.replace(WORD_STEM_PLACEHOLDER, config.getWordStemPlaceholder());
        result = result.replace(SPECIAL_CHAR_PLACEHOLDER, "$");
        return result;
    }

    public void setUserEnteredTerm(String term) {
        Objects.requireNonNull(term);

        ConfigurationManager config = ConfigurationManager.getInstance();
        String result = term.replace("$", SPECIAL_CHAR_PLACEHOLDER);
        result = result.replace(config.getWordStemMarker(), WORD_STEM_MARKER);
        result = result.replace(config.getWordStemBeginMarker(), WORD_STEM_BEGIN_MARKER);
        result = result.replace(config.getWordStemEndMarker(), WORD_STEM_END_MARKER);
        result = result.replace(config.getWordStemPlaceholder(), WORD_STEM_PLACEHOLDER);

        setNormalizedTerm(result);
    }

    protected String removeMarkerStrings(String string) {
        String result = string.replace(WORD_STEM_MARKER, "");
        result = result.replace(WORD_STEM_BEGIN_MARKER, "");
        result = result.replace(WORD_STEM_END_MARKER, "");
        result = result.replace(WORD_STEM_PLACEHOLDER, "");
        result = result.replace(SPECIAL_CHAR_PLACEHOLDER, "$");

        return result;
    }

    public String getPurgedTerm() {
        return purge(normalizedTerm);
    }

    protected String purge(String string) {
        String result = string.replace(WORD_STEM_MARKER, "");
        result = result.replace(WORD_STEM_BEGIN_MARKER, "");
        result = result.replace(WORD_STEM_END_MARKER, "");
        result = result.replace(WORD_STEM_PLACEHOLDER, "-");
        result = result.replace(SPECIAL_CHAR_PLACEHOLDER, "$");

        return result;
    }

    public boolean isEmpty() {
        return "".equals(getNormalizedTerm());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("user entered term", getUserEnteredTerm())
                .toString();
    }

    public abstract boolean isWordStem();

    public abstract boolean isInflected();

    public abstract AbstractTermData getWordStemObject();

    public abstract String getResolvedAndPurgedTerm();

    public abstract String getWordStem();

    public abstract String getResolvedTerm();
}
