/*
 * $Id: DBO_AbstractUserInput.java 202 2009-12-15 19:33:40Z roland $
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
package org.jlexis.data.vocable.userinput;


import org.jlexis.data.vocable.RegisteredVocableDataKey;
import org.jlexis.data.vocable.terms.RegularTerm;

import java.util.HashMap;
import java.util.Map;

@Deprecated
public class DBO_AbstractUserInput {
    private long mId;
    private Map<RegisteredVocableDataKey, RegularTerm> mData;
    private String mInputType;

    protected DBO_AbstractUserInput() {
        mData = new HashMap<RegisteredVocableDataKey, RegularTerm>();
    }

    public void copy(DBO_AbstractUserInput other) {
        setData(other.mData);
        setInputType(other.mInputType);
    }

    public Map<RegisteredVocableDataKey, RegularTerm> getData() {
        return mData;
    }

    public void setData(Map<RegisteredVocableDataKey, RegularTerm> data) {
        if (data == null)
            throw new NullPointerException("Data is null.");
        // remove empty values
        mData.putAll(data);
        for (RegisteredVocableDataKey key : data.keySet()) {
            if (data.get(key).isEmpty()) {
                mData.remove(key);
            }
        }
    }

    public String getInputType() {
        return mInputType;
    }

    public void setInputType(String inputType) {
        if (inputType == null)
            throw new NullPointerException("Input type is null.");

        mInputType = inputType;
    }

    @Override
    public String toString() {
        return String.format("{id: %d; inputType: %s}", mId, mInputType);
    }

    @SuppressWarnings("unused")
    private long getId() {
        return mId;
    }

    @SuppressWarnings("unused")
    private void setId(long mid) {
        mId = mid;
    }
}
