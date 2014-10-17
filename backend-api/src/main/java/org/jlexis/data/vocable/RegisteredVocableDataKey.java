/*
 * $Id: RegisteredVocableDataKey.java 78 2009-03-07 22:54:49Z roland $
 * Created on 20.12.2008
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
package org.jlexis.data.vocable;

public class RegisteredVocableDataKey implements Comparable<RegisteredVocableDataKey> {
    private Long mId;
    private String mKey;

    public RegisteredVocableDataKey() {
        mKey = "";
    }

    public RegisteredVocableDataKey(String key) {
        mKey = key;
    }

    @SuppressWarnings("unused")
    private Long getId() {
        return mId;
    }

    @SuppressWarnings("unused")
    private void setId(Long id) {
        mId = id;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }

    @Override
    public int hashCode() {
        return mKey.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if (obj instanceof RegisteredVocableDataKey) {
            RegisteredVocableDataKey other = (RegisteredVocableDataKey) obj;
            return mKey.equals(other.mKey);
        }
        return mKey.equals(obj);
    }

    @Override
    public int compareTo(RegisteredVocableDataKey o) {
        return mKey.compareTo(o.mKey);
    }
}
