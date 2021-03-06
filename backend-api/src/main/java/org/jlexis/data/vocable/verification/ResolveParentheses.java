/*
 * Created on 12.11.2014
 *
 * Copyright 2014 Roland Krueger (www.rolandkrueger.info)
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
 */

package org.jlexis.data.vocable.verification;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Collections.emptySet;
import static org.jlexis.util.StringUtils.isNullOrEmptyWithTrim;

/**
 * Utility class for resolving parenthesized content contained in a String. Resolving parenthesized content in this
 * context means to transform an input String into a set of alternative values which each constitute a valid
 * representation of the input String under the premise that content in parentheses are optional.
 * <p/>
 * For example, if an input String <em>(to) learn"</em> is fed to function {@link #resolveParentheses(String)} this
 * value will be resolved into the following set of values: <em>(to) learn</em>, <em>to learn</em>, and <em>learn</em>.
 * <p/>
 * This feature is needed when verifying a given user answer to a vocabulary quiz question. If a quiz question asks for
 * a term that contains parenthesized content, the user can validly answer with all alternatives contained in the set of
 * resolved terms. I.e., if a quiz question asks for <em>(to) learn</em>, the user can choose to answer with <em>(to)
 * learn</em>, <em>to learn</em>, or <em>learn</em> with all of these alternatives being correct answers.
 * <p/>
 * Note that the set of valid alternatives disproportionately grows with the number of parenthesized terms in the input
 * data. Therefore, only a maximum number of four pairs of parentheses are supported by this class. If more
 * parenthesized content is contained in the input String a singleton set is returned by the resolve function containing
 * the input String itself.
 */
public final class ResolveParentheses {

    private static final String PARENTHESIZED_CONTENT_GROUP = "ParenthesizedContent";
    private static final String CONTENT_BEFORE_PARENTHESES_GROUP = "ContentBeforeParentheses";
    private static final String CONTENT_AFTER_PARENTHESES_GROUP = "ContentAfterParentheses";
    private static Pattern PATTERN = Pattern.compile(
            "(?<ContentBeforeParentheses>.*?)" +
                    "\\((?<ParenthesizedContent>[^()]*)\\)" +
                    "(?<ContentAfterParentheses>.*?)");

    private ResolveParentheses() {
    }

    /**
     * Resolves all parenthesized content in the given input String and returns a set of valid alternatives each
     * representing the input String.
     * <p/>
     * For example, the input <em>Lehrer(in)</em> will be resolved into the set <em>Lehrer(in)</em>, <em>Lehrer</em>,
     * and <em>Lehrerin</em>, each constituting a valid representation of the input String.
     * <p/>
     * For all values returned in the resulting set, leading and trailing whitespace is removed.
     *
     * @param value the input String to be resolved. If <code>null</code> or empty, an empty set is returned.
     * @return a set containing all valid alternatives for the given value. If the value contains more than four pairs
     * of parentheses, a singleton set is returned containing only the input String itself.
     */
    public static Set<String> resolveParentheses(String value) {
        if (isNullOrEmptyWithTrim(value)) {
            return emptySet();
        }

        if (!value.contains("(")) {
            return Collections.singleton(value);
        }

        return new ResolveParentheses().resolve(value, new HashSet<>());
    }

    /**
     * Resolves all Strings contained in the input list and returns a set containing the resolved values for all of
     * these input Strings.
     *
     * @param values list of values to resolve
     * @return a set containing all valid alternatives for all Strings contained in the input list or an empty set if
     * the given collection is <code>null</code> or empty.
     */
    public static Set<String> resolveParenthesesForCollection(Collection<String> values) {
        if (values == null || values.isEmpty()) {
            return Collections.emptySet();
        }

        if (values.size() == 1) {
            return resolveParentheses(values.iterator().next());
        }

        HashSet<String> result = new HashSet<>();
        for (String value : values) {
            result.addAll(resolveParentheses(value));
        }
        return result;
    }

    private Set<String> resolve(String value, Set<String> set) {
        if (isNullOrEmptyWithTrim(value)) {
            return emptySet();
        }

        set.add(value.trim());
        String processedPrefix = "";
        int groupCount = 0;

        Matcher matcher = PATTERN.matcher(value);
        while (matcher.matches()) {
            if (groupCount > 3) {
                return Collections.singleton(value);
            }
            set.addAll(resolve(processedPrefix + matcher.group(CONTENT_BEFORE_PARENTHESES_GROUP) + matcher.group(CONTENT_AFTER_PARENTHESES_GROUP), set));
            set.addAll(resolve(processedPrefix + matcher.group(CONTENT_BEFORE_PARENTHESES_GROUP) + matcher.group(PARENTHESIZED_CONTENT_GROUP) + matcher.group(CONTENT_AFTER_PARENTHESES_GROUP), set));
            processedPrefix = value.substring(0, indexAfterClosingParentheses(matcher));
            matcher.region(indexAfterClosingParentheses(matcher), value.length());
            groupCount++;
        }

        return set;
    }

    private int indexAfterClosingParentheses(Matcher matcher) {
        return matcher.end(PARENTHESIZED_CONTENT_GROUP) + 1;
    }
}
