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

import org.jlexis.util.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO: documentation
 * @author Roland Krueger
 */
public class WhitespaceAndSuffixTolerantSet implements Set<String> {
    private Map<String, Pattern> data;
    private String suffixToleranceChars;

    public WhitespaceAndSuffixTolerantSet() {
        data = new HashMap<String, Pattern>();
        suffixToleranceChars = "";
    }

    public WhitespaceAndSuffixTolerantSet(Collection<String> c) {
        this();
        addAll(c);
    }

    public WhitespaceAndSuffixTolerantSet(Collection<String> c, char[] suffixToleranceChars) {
        this(suffixToleranceChars);
        addAll(c);
    }

    public WhitespaceAndSuffixTolerantSet(char[] suffixToleranceChars) {
        this();

        StringBuilder buf = new StringBuilder();
        buf.append("[");
        for (char c : suffixToleranceChars) {
            if (c == '[') buf.append("\\[");
            else if (c == ']') buf.append("\\]");
            else buf.append(c);
        }
        buf.append("]?");

        this.suffixToleranceChars = buf.toString();
    }

    @Override
    public boolean add(String value) {
        value = value.trim();
        value = value.replaceAll("\\s+", " ");
        String original = value;
        if (value.equals("")) return false;
        value = value.replaceAll("\\s*(\\W)\\s*", "\\\\s*$1\\\\s*");
        value = StringUtils.escapeRegexSpecialChars(value);
        value = String.format("%s%s", value.replace("\\s\\*", "\\s*"), suffixToleranceChars);

        data.put(original, Pattern.compile(value));
        return true;
    }

    private String getKeyForValue(String value) {
        value = value.trim();
        for (String s : data.keySet()) {
            Matcher matcher = data.get(s).matcher(value);
            if (matcher.matches()) return s;
        }
        return null;
    }

    @Override
    public boolean addAll(Collection<? extends String> c) {
        for (String s : c) {
            add(s);
        }
        return true;
    }

    @Override
    public void clear() {
        data.clear();
    }

    @Override
    public boolean contains(Object o) {
        return getKeyForValue(o.toString()) != null;
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
        return data.keySet().iterator();
    }

    @Override
    public boolean remove(Object o) {
        String key = getKeyForValue(o.toString());
        if (key == null) return false;
        data.remove(key);
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean result = true;
        for (Object o : c) {
            result &= remove(o);
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
}
