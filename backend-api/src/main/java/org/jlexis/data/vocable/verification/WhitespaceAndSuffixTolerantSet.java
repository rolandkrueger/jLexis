/*
 * Created on 30.09.2009
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

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO: documentation
 *
 * @author Roland Krueger
 */
public class WhitespaceAndSuffixTolerantSet implements Set<String> {
    /**
     * Internal data store that maps the normalized user input on the original user input. User data is normalized by
     * removing leading and trailing whitespace from the string and replacing all whitespace characters with a single
     * blank character.
     */
    private Map<String, String> data;
    private Pattern removeSuffixPattern;
    private StringNormalizer normalizer;

    public WhitespaceAndSuffixTolerantSet() {
        data = new HashMap<String, String>();
        normalizer = new StringNormalizer();
        removeSuffixPattern = Pattern.compile("\\s*(.*)\\s*");
    }

    public WhitespaceAndSuffixTolerantSet(Collection<String> c, char... suffixToleranceChars) {
        this(suffixToleranceChars);
        addAll(c);
    }

    public WhitespaceAndSuffixTolerantSet(char... suffixToleranceChars) {
        this();

        StringBuilder buf = new StringBuilder();
        buf.append("\\s*(.*?)\\s*[");
        for (char c : suffixToleranceChars) {
            if (c == '[') buf.append("\\[");
            else if (c == ']') buf.append("\\]");
            else buf.append(c);
        }
        buf.append("]?");

        this.removeSuffixPattern = Pattern.compile(buf.toString());
    }

    @Override
    public boolean add(String value) {
        data.put(normalizer.normalize(value), value);
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends String> c) {
        boolean result = false;
        for (String s : c) {
            result |= add(s);
        }
        return result;
    }

    @Override
    public void clear() {
        data.clear();
    }

    @Override
    public boolean contains(Object object) {
        if (object == null || !(object instanceof String)) {
            return false;
        }

        String normalizedValue = normalizer.normalize((String) object);
        if (data.containsKey(normalizedValue)) {
            return true;
        }

        Matcher matcher = removeSuffixPattern.matcher(normalizedValue);
        return matcher.matches() && data.containsKey(matcher.group(1));
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        boolean result = true;
        for (Object o : c) {
            result &= contains(o);
        }
        return result;
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public Iterator<String> iterator() {
        return data.values().iterator();
    }

    @Override
    public boolean remove(Object o) {
        if (o == null || !(o instanceof String)) {
            return false;
        }
        return data.remove(normalizer.normalize((String) o)) != null;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean result = false;
        for (Object o : c) {
            result |= remove(o);
        }
        return result;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public Object[] toArray() {
        return data.keySet().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return data.keySet().toArray(a);
    }

    @Override
    public String toString() {
        return data.keySet().toString();
    }

    @Override
    public boolean equals(Object o) {
        return data.keySet().equals(o);
    }

    @Override
    public int hashCode() {
        return data.keySet().hashCode();
    }

    public static class StringNormalizer {
        /**
         * Normalizes a String by performing the following operations on it:
         * <ul>
         * <li>remove all leading and trailing whitespace</li>
         * <li>replace all whitespaces with a single blank</li>
         * <li>remove all whitespace before and after punctuation characters</li>
         * </ul>
         * @param value input value, must not be null
         * @return normalized value
         */
        public String normalize(String value) {
            return value.trim().replaceAll("\\s+", " ").replaceAll("\\s*(\\W+?)\\s*", "$1");
        }
    }
}
