/*
 * Copyright 2007-2014 Roland Krueger (www.rolandkrueger.info)
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

package org.jlexis.data.vocable.verification;

import com.google.common.base.Strings;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.jlexis.data.vocable.verification.ResolveParentheses.resolveParenthesesForCollection;

public class MandatoryValuesWithVariants {

    private Set<WhitespaceAndSuffixTolerantSet> data;

    public MandatoryValuesWithVariants() {
        data = new HashSet<>();
    }

    public static MandatoryValuesWithVariants copy(MandatoryValuesWithVariants values) {
        MandatoryValuesWithVariants copy = new MandatoryValuesWithVariants();

        for (WhitespaceAndSuffixTolerantSet set : values.data) {
            copy.addMandatoryValueWithVariants(set);
        }

        return copy;
    }

    public void addMandatoryValueWithVariants(Collection<String> values) {
        data.add(new WhitespaceAndSuffixTolerantSet(values));
    }

    public Set<String> getAllValues() {
        Set<String> result = new WhitespaceAndSuffixTolerantSet();
        data.forEach(result::addAll);
        return result;
    }

    public Set<String> getVariantsForValue(String value) {
        return Collections.unmodifiableSet(getVariantsForValueInternal(value));
    }

    private Set<String> getVariantsForValueInternal(String value) {
        if (Strings.nullToEmpty(value).trim().isEmpty()) {
            return Collections.emptySet();
        }

        for (Set<String> set : data) {
            if (set.contains(value)) {
                return set;
            }
        }
        return Collections.emptySet();
    }

    public boolean removeVariantsForValue(String value) {
        return data.remove(getVariantsForValueInternal(value));
    }

    public boolean isEmpty() {
        if (data.isEmpty()) {
            return true;
        }

        for (Set<String> set : data) {
            if (!set.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public int getSize() {
        return data.size();
    }

    public void resolveAllParenthesizedContent() {
        for (Set<String> set : data) {
            set.addAll(resolveParenthesesForCollection(set));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MandatoryValuesWithVariants that = (MandatoryValuesWithVariants) o;

        if (!data.equals(that.data)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }
}
