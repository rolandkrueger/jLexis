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

import java.util.*;

/**
 * @author Roland Krueger
 */
public class VocableVerificationResult {
    private VocableVerificationResultEnum result;
    private Set<String> redundantValues;
    private Set<String> missingValues;

    public VocableVerificationResult(VocableVerificationResultEnum result) {
        this.result = Objects.requireNonNull(result);
    }

    public boolean isCorrect() {
        return result == VocableVerificationResultEnum.CORRECT;
    }

    public void addRedundantValues(Collection<String> redundantValues) {
        if (redundantValues == null)
            redundantValues = Collections.emptyList();
        if (this.redundantValues == null)
            this.redundantValues = new HashSet<String>();
        this.redundantValues.addAll(redundantValues);
    }

    public void addMissingValues(Collection<String> missingValues) {
        if (missingValues == null)
            missingValues = Collections.emptyList();
        if (this.missingValues == null)
            this.missingValues = new HashSet<String>();
        this.missingValues.addAll(missingValues);
    }

    public VocableVerificationResultEnum getResult() {
        return result;
    }

    public Set<String> getMissingValues() {
        if (missingValues == null)
            return Collections.emptySet();

        return missingValues;
    }

    public Set<String> getRedundantValues() {
        if (redundantValues == null)
            return Collections.emptySet();

        return redundantValues;
    }
}
