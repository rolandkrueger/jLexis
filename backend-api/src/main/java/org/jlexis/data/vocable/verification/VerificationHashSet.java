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
 * TODO: update documentation
 * <p/>
 * Subclass of {@link java.util.HashSet} which will not accept the empty string as input value. If the
 * empty string is tried to be added, method {@link VerificationHashSet#add(String)} simply returns
 * false and skips adding this value.
 *
 * @author Roland Krueger
 * @version $Id: VerificationHashSet.java 136 2009-10-02 18:20:53Z roland $
 */
public class VerificationHashSet implements Set<String> {
    private Map<String, Pattern> mData;
    private String mSuffixToleranceChars;

    public VerificationHashSet() {
        mData = new HashMap<String, Pattern>();
        mSuffixToleranceChars = "";
    }

    public VerificationHashSet(Collection<String> c) {
        this();
        addAll(c);
    }

    public VerificationHashSet(Collection<String> c, char[] suffixToleranceChars) {
        this(suffixToleranceChars);
        addAll(c);
    }

    public VerificationHashSet(char[] suffixToleranceChars) {
        this();

        StringBuilder buf = new StringBuilder();
        buf.append("[");
        for (char c : suffixToleranceChars) {
            if (c == '[') buf.append("\\[");
            else if (c == ']') buf.append("\\]");
            else buf.append(c);
        }
        buf.append("]?");

        mSuffixToleranceChars = buf.toString();
    }

    @Override
    public boolean add(String value) {
        value = value.trim();
        value = value.replaceAll("\\s+", " ");
        String original = value;
        if (value.equals("")) return false;
        value = value.replaceAll("\\s*(\\W)\\s*", "\\\\s*$1\\\\s*");
        value = StringUtils.escapeRegexSpecialChars(value);
        value = String.format("%s%s", value.replace("\\s\\*", "\\s*"), mSuffixToleranceChars);

        mData.put(original, Pattern.compile(value));
        return true;
    }

    private String getKeyForValue(String value) {
        value = value.trim();
        for (String s : mData.keySet()) {
            Matcher matcher = mData.get(s).matcher(value);
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
        mData.clear();
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
        return mData.isEmpty();
    }

    @Override
    public Iterator<String> iterator() {
        return mData.keySet().iterator();
    }

    @Override
    public boolean remove(Object o) {
        String key = getKeyForValue(o.toString());
        if (key == null) return false;
        mData.remove(key);
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
        return mData.size();
    }

    @Override
    public Object[] toArray() {
        return mData.keySet().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return mData.keySet().toArray(a);
    }

    @Override
    public String toString() {
        return mData.keySet().toString();
    }

    @Override
    public boolean equals(Object o) {
        return mData.keySet().equals(o);
    }

    @Override
    public int hashCode() {
        return mData.keySet().hashCode();
    }
}
