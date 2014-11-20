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

import static com.google.common.base.Strings.nullToEmpty;
import static java.util.Objects.requireNonNull;

public abstract class AbstractTerm {
    protected final static String WORD_STEM_MARKER_ENCODED = "${|}";
    protected final static String WORD_STEM_BEGIN_MARKER_ENCODED = "${<}";
    protected final static String WORD_STEM_END_MARKER_ENCODED = "${>}";

    protected final static String WORD_STEM_MARKER_ENCODED_QUOTED = Matcher.quoteReplacement("${|}");
    protected final static String WORD_STEM_BEGIN_MARKER_ENCODED_QUOTED = Matcher.quoteReplacement("${<}");
    protected final static String WORD_STEM_END_MARKER_ENCODED_QUOTED = Matcher.quoteReplacement("${>}");
    protected final static String WORD_STEM_PLACEHOLDER_ENCODED_QUOTED = Matcher.quoteReplacement("${-}");
    protected final static String DOLLAR_SIGN_ENCODED_QUOTED = Matcher.quoteReplacement("${$}");

    protected final static String WORD_STEM_MARKER = Matcher.quoteReplacement("|");
    protected final static String BEGIN_WORD_STEM_MARKER = Matcher.quoteReplacement("<");
    protected final static String END_WORD_STEM_MARKER = Matcher.quoteReplacement(">");
    protected final static String WORD_STEM_PLACEHOLDER = Matcher.quoteReplacement("--");
    protected final static String DOLLAR_SIGN = Matcher.quoteReplacement("$");
    protected final static String HYPHEN_SIGN = Matcher.quoteReplacement("-");

    protected final static Pattern WORD_STEM_MARKER_ENCODED_PATTERN = Pattern.compile("${|}", Pattern.LITERAL);
    protected final static Pattern WORD_STEM_BEGIN_MARKER_ENCODED_PATTERN = Pattern.compile("${<}", Pattern.LITERAL);
    protected final static Pattern WORD_STEM_END_MARKER_ENCODED_PATTERN = Pattern.compile("${>}", Pattern.LITERAL);
    protected final static Pattern WORD_STEM_PLACEHOLDER_ENCODED_PATTERN = Pattern.compile("${-}", Pattern.LITERAL);
    protected final static Pattern DOLLAR_SIGN_ENCODED_PATTERN = Pattern.compile("${$}", Pattern.LITERAL);

    protected final static Pattern WORD_STEM_MARKER_PATTERN = Pattern.compile("|", Pattern.LITERAL);
    protected final static Pattern BEGIN_WORD_STEM_MARKER_PATTERN = Pattern.compile("<", Pattern.LITERAL);
    protected final static Pattern END_WORD_STEM_MARKER_PATTERN = Pattern.compile(">", Pattern.LITERAL);
    protected final static Pattern WORD_STEM_PLACEHOLDER_PATTERN = Pattern.compile("--", Pattern.LITERAL);
    protected final static Pattern DOLLAR_SIGN_PATTERN = Pattern.compile("$", Pattern.LITERAL);

    private String encodedString = "";
    private String userEnteredString;
    private String cleanedString;
    private String cleanedStringWithWordStemResolved;

    public String getEncodedString() {
        return encodedString;
    }

    public void setEncodedString(String encodedString) {
        clearCachedStrings();
        setEncodedStringIntern(encodedString);
    }

    public String getUserEnteredString() {
        if (userEnteredString == null) {
            String result = replace(encodedString, WORD_STEM_MARKER_ENCODED_PATTERN, WORD_STEM_MARKER);
            result = replace(result, WORD_STEM_BEGIN_MARKER_ENCODED_PATTERN, BEGIN_WORD_STEM_MARKER);
            result = replace(result, WORD_STEM_END_MARKER_ENCODED_PATTERN, END_WORD_STEM_MARKER);
            result = replace(result, WORD_STEM_PLACEHOLDER_ENCODED_PATTERN, WORD_STEM_PLACEHOLDER);
            userEnteredString = replace(result, DOLLAR_SIGN_ENCODED_PATTERN, DOLLAR_SIGN);
        }
        return userEnteredString;
    }

    public void setUserEnteredString(String string) {
        userEnteredString = nullToEmpty(string).trim();
        String result = replace(userEnteredString, DOLLAR_SIGN_PATTERN, DOLLAR_SIGN_ENCODED_QUOTED);
        result = replace(result, WORD_STEM_MARKER_PATTERN, WORD_STEM_MARKER_ENCODED_QUOTED);
        result = replace(result, BEGIN_WORD_STEM_MARKER_PATTERN, WORD_STEM_BEGIN_MARKER_ENCODED_QUOTED);
        result = replace(result, END_WORD_STEM_MARKER_PATTERN, WORD_STEM_END_MARKER_ENCODED_QUOTED);
        result = replace(result, WORD_STEM_PLACEHOLDER_PATTERN, WORD_STEM_PLACEHOLDER_ENCODED_QUOTED);

        setEncodedStringIntern(result);
    }

    private void setEncodedStringIntern(String encodedString) {
        this.encodedString = nullToEmpty(encodedString).trim();
    }

    protected String removeMarkerStrings(String string) {
        String result = replace(string, WORD_STEM_PLACEHOLDER_PATTERN, "");
        result = clean(result);

        return result;
    }

    public String getCleanedString() {
        if (cleanedString == null) {
            cleanedString = clean(encodedString);
        }
        return cleanedString;
    }

    protected String clean(String string) {
        String result = replace(string, WORD_STEM_MARKER_ENCODED_PATTERN, "");
        result = replace(result, WORD_STEM_BEGIN_MARKER_ENCODED_PATTERN, "");
        result = replace(result, WORD_STEM_END_MARKER_ENCODED_PATTERN, "");
        result = replace(result, WORD_STEM_PLACEHOLDER_ENCODED_PATTERN, HYPHEN_SIGN);
        result = replace(result, DOLLAR_SIGN_ENCODED_PATTERN, DOLLAR_SIGN);
        return result;
    }

    public boolean isEmpty() {
        return "".equals(getEncodedString());
    }

    public final  String getCleanedStringWithWordStemResolved() {
        if (cleanedStringWithWordStemResolved == null) {
            cleanedStringWithWordStemResolved = clean(getStringWithWordStemResolved());
        }
        return cleanedStringWithWordStemResolved;
    }

    private void clearCachedStrings() {
        encodedString = "";
        userEnteredString = null;
        cleanedString = null;
        cleanedStringWithWordStemResolved = null;
    }

    public abstract boolean isWordStem();

    public abstract boolean isInflected();

    public abstract AbstractTerm getWordStemTerm();

    public abstract String getWordStemString();

    public abstract String getStringWithWordStemResolved();

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("user entered value", getUserEnteredString())
                .toString();
    }

    protected String replace(String value, Pattern pattern, String replacement) {
        return pattern.matcher(value).replaceAll(replacement);
    }
}
