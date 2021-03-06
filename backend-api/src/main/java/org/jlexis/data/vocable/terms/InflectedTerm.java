/*
 * $Id: InflectedTerm.java 113 2009-05-20 18:19:32Z roland $
 * Created on 08.03.2009
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

import java.util.regex.Matcher;

import static java.util.Objects.requireNonNull;

public class InflectedTerm extends AbstractTerm {
    private AbstractTerm wordStem;

    public InflectedTerm(WordStemTerm stem) {
        wordStem = requireNonNull(stem, "word stem object is null.");
    }

    @Override
    public String getStringWithWordStemResolved() {
        String result = replace(getEncodedString(),
                WORD_STEM_PLACEHOLDER_ENCODED_PATTERN,
                Matcher.quoteReplacement(wordStem.getWordStemString()));
        result = removeMarkerStrings(result);
        return result;
    }

    @Override
    public String getWordStemString() {
        throw new UnsupportedOperationException("This is not a word stem.");
    }

    @Override
    public AbstractTerm getWordStemTerm() {
        return wordStem;
    }

    @Override
    public boolean isWordStem() {
        return false;
    }

    @Override
    public boolean isInflected() {
        return true;
    }
}
