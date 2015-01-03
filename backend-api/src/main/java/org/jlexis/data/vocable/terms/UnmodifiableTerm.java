/*
 * Copyright 2007-2015 Roland Krueger (www.rolandkrueger.info)
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with jLexis; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package org.jlexis.data.vocable.terms;

import java.util.Objects;

public final class UnmodifiableTerm extends AbstractTerm {
    private AbstractTerm data;

    public UnmodifiableTerm(AbstractTerm data) {
        this.data = Objects.requireNonNull(data);
    }

    public final String getEncodedString() {
        return data.getEncodedString();
    }

    public final void setEncodedString(String normalizedTerm) {
        throw new UnsupportedOperationException("This object cannot be modified.");
    }

    public final String getCleanedString() {
        return data.getCleanedString();
    }

    public final String getStringWithWordStemResolved() {
        return data.getStringWithWordStemResolved();
    }

    public final String getUserEnteredString() {
        return data.getUserEnteredString();
    }

    public final void setUserEnteredString(String string) {
        throw new UnsupportedOperationException("This object cannot be modified.");
    }

    public final String getWordStemString() {
        return data.getWordStemString();
    }

    public final boolean isEmpty() {
        return data.isEmpty();
    }

    public final boolean isWordStem() {
        return data.isWordStem();
    }

    @Override
    public boolean isInflected() {
        return data.isInflected();
    }

    @Override
    public AbstractTerm getWordStemTerm() {
        return data.getWordStemTerm();
    }

    @Override
    public final String toString() {
        return data.toString();
    }
}
