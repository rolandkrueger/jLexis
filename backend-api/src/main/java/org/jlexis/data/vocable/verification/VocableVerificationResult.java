/*
 * Created on 19.05.2009
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

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Roland Krueger
 * @version $Id: VocableVerificationResult.java 111 2009-05-19 19:11:31Z roland $
 */
public class VocableVerificationResult {
    private VocableVerificationResultEnum mResultType;
    private Set<String> mRedundantValues;
    private Set<String> mMissingValues;

    public VocableVerificationResult() {
        mResultType = VocableVerificationResultEnum.UNDEFINED;
    }

    public boolean isCorrect() {
        return mResultType == VocableVerificationResultEnum.CORRECT;
    }

    public void addRedundantValues(Collection<String> redundantValues) {
        if (redundantValues == null)
            redundantValues = Collections.emptyList();
        if (mRedundantValues == null)
            mRedundantValues = new HashSet<String>();
        mRedundantValues.addAll(redundantValues);
    }

    public void addMissingValues(Collection<String> missingValues) {
        if (missingValues == null)
            missingValues = Collections.emptyList();
        if (mMissingValues == null)
            mMissingValues = new HashSet<String>();
        mMissingValues.addAll(missingValues);
    }

    public VocableVerificationResultEnum getResult() {
        return mResultType;
    }

    public void setResult(VocableVerificationResultEnum result) {
        mResultType = result;
    }

    public Set<String> getMissingValues() {
        if (mMissingValues == null)
            return Collections.emptySet();

        return mMissingValues;
    }

    public Set<String> getRedundantValues() {
        if (mRedundantValues == null)
            return Collections.emptySet();

        return mRedundantValues;
    }
}
