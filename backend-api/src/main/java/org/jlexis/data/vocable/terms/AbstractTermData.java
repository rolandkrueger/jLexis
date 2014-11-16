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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.requireNonNull;

public abstract class AbstractTermData {
    protected final static String WORD_STEM_MARKER_ENCODED = Matcher.quoteReplacement("${|}");
    protected final static String WORD_STEM_BEGIN_MARKER_ENCODED = Matcher.quoteReplacement("${<}");
    protected final static String WORD_STEM_END_MARKER_ENCODED = Matcher.quoteReplacement("${>}");
    protected final static String WORD_STEM_PLACEHOLDER_ENCODED = Matcher.quoteReplacement("${-}");
    protected final static String DOLLAR_SIGN_ENCODED = Matcher.quoteReplacement("${$}");

    private final static String WORD_STEM_MARKER = Matcher.quoteReplacement("|");
    private final static String BEGIN_WORD_STEM_MARKER = Matcher.quoteReplacement("<");
    private final static String END_WORD_STEM_MARKER = Matcher.quoteReplacement(">");
    private final static String WORD_STEM_PLACEHOLDER = Matcher.quoteReplacement("--");
    private final static String DOLLAR_SIGN = Matcher.quoteReplacement("$");
    private final static String HYPHEN_SIGN = Matcher.quoteReplacement("-");

    private final static Pattern WORD_STEM_MARKER_ENCODED_PATTERN = Pattern.compile("${|}", Pattern.LITERAL);
    private final static Pattern WORD_STEM_BEGIN_MARKER_ENCODED_PATTERN = Pattern.compile("${<}", Pattern.LITERAL);
    private final static Pattern WORD_STEM_END_MARKER_ENCODED_PATTERN = Pattern.compile("${>}", Pattern.LITERAL);
    private final static Pattern WORD_STEM_PLACEHOLDER_ENCODED_PATTERN = Pattern.compile("${-}", Pattern.LITERAL);
    private final static Pattern DOLLAR_SIGN_ENCODED_PATTERN = Pattern.compile("${$}", Pattern.LITERAL);

    private final static Pattern WORD_STEM_MARKER_PATTERN = Pattern.compile("|", Pattern.LITERAL);
    private final static Pattern BEGIN_WORD_STEM_MARKER_PATTERN = Pattern.compile("<", Pattern.LITERAL);
    private final static Pattern END_WORD_STEM_MARKER_PATTERN = Pattern.compile(">", Pattern.LITERAL);
    private final static Pattern WORD_STEM_PLACEHOLDER_PATTERN = Pattern.compile("--", Pattern.LITERAL);
    private final static Pattern DOLLAR_SIGN_PATTERN = Pattern.compile("$", Pattern.LITERAL);

    protected String encodedTerm = "";

    public String getEncodedTerm() {
        return encodedTerm;
    }

    public void setEncodedTerm(String encodedTerm) {
        this.encodedTerm = requireNonNull(encodedTerm).trim();
    }

    public String getUserEnteredTerm() {
        String result = replace(encodedTerm, WORD_STEM_MARKER_ENCODED_PATTERN, WORD_STEM_MARKER);
        result = replace(result, WORD_STEM_BEGIN_MARKER_ENCODED_PATTERN, BEGIN_WORD_STEM_MARKER);
        result = replace(result, WORD_STEM_END_MARKER_ENCODED_PATTERN, END_WORD_STEM_MARKER);
        result = replace(result, WORD_STEM_PLACEHOLDER_ENCODED_PATTERN, WORD_STEM_PLACEHOLDER);
        return replace(result, DOLLAR_SIGN_ENCODED_PATTERN, DOLLAR_SIGN);
    }

    public void setUserEnteredTerm(String term) {
        String result = replace(requireNonNull(term), DOLLAR_SIGN_PATTERN, DOLLAR_SIGN_ENCODED);
        result = replace(result, WORD_STEM_MARKER_PATTERN, WORD_STEM_MARKER_ENCODED);
        result = replace(result, BEGIN_WORD_STEM_MARKER_PATTERN, WORD_STEM_BEGIN_MARKER_ENCODED);
        result = replace(result, END_WORD_STEM_MARKER_PATTERN, WORD_STEM_END_MARKER_ENCODED);
        result = replace(result, WORD_STEM_PLACEHOLDER_PATTERN, WORD_STEM_PLACEHOLDER_ENCODED);

        setEncodedTerm(result);
    }

    protected String removeMarkerStrings(String string) {

        String result = replace(string, WORD_STEM_PLACEHOLDER_PATTERN, "");
        result = purge(result);

        return result;
    }

    public String getPurgedTerm() {
        return purge(encodedTerm);
    }

    protected String purge(String string) {
        String result = replace(string, WORD_STEM_MARKER_ENCODED_PATTERN, "");
        result = replace(result, WORD_STEM_BEGIN_MARKER_ENCODED_PATTERN, "");
        result = replace(result, WORD_STEM_END_MARKER_ENCODED_PATTERN, "");
        result = replace(result, WORD_STEM_PLACEHOLDER_ENCODED_PATTERN, HYPHEN_SIGN);
        result = replace(result, DOLLAR_SIGN_ENCODED_PATTERN, DOLLAR_SIGN);
        return result;
    }

    private String replace(String value, Pattern pattern, String replacement) {
        return pattern.matcher(value).replaceAll(replacement);
    }

    public boolean isEmpty() {
        return "".equals(getEncodedTerm());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("user entered term", getUserEnteredTerm())
                .toString();
    }

    public abstract boolean isWordStem();

    public abstract boolean isInflected();

    public abstract AbstractTermData getWordStemTerm();

    public abstract String getResolvedAndPurgedTerm();

    public abstract String getWordStem();

    public abstract String getResolvedTerm();
}
