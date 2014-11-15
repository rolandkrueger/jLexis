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

package org.jlexis.data.vocable.verification
import org.hamcrest.collection.IsEmptyCollection

import static org.hamcrest.CoreMatchers.is
import static org.hamcrest.Matchers.hasSize
import static org.hamcrest.Matchers.not
import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertThat

class MandatoryValuesWithVariantsTest extends GroovyTestCase {

    MandatoryValuesWithVariants values

    void setUp() {
        values = new MandatoryValuesWithVariants()
    }

    void test_copy() {
        values.addMandatoryValueWithVariants(["a", "b", "c"])
        MandatoryValuesWithVariants copy = MandatoryValuesWithVariants.copy(values)
        assertEquals(values, copy)
    }

    void test_addMandatoryValueWithVariants() {
        values.addMandatoryValueWithVariants(["a", "b", "c"])
        assertThat(values.getSize(), is(1))
        assertThat(values.getVariantsForValue("c"), hasSize(3))
    }

    void test_addMandatoryValueWithVariants_with_null_and_empty_collection() {
        values.addMandatoryValueWithVariants(null)
        assertTrue(values.isEmpty())
        values.addMandatoryValueWithVariants([])
        assertTrue(values.isEmpty())
    }

    void test_getAllValues() {
        values.addMandatoryValueWithVariants(["a", "b", "c"])
        values.addMandatoryValueWithVariants(["1", "2", "3"])

        WhitespaceAndSuffixTolerantSet allValues = new WhitespaceAndSuffixTolerantSet(["a", "b", "c", "1", "2", "3"])
        assertEquals(allValues.size(), values.getAllValues().size())
    }

    void test_getAllValues_returns_unmodifiable_set() {
        values.addMandatoryValueWithVariants(["a", "b", "c"])
        Set<String> allValues = values.getAllValues()

        shouldFail(UnsupportedOperationException) {
            allValues.add "d"
        }
    }

    void test_getVariantsForValue() {
        values.addMandatoryValueWithVariants(["a", "b", "c"])
        Set<String> aVariants = values.getVariantsForValue("a")
        Set<String> bVariants = values.getVariantsForValue("b")
        Set<String> cVariants = values.getVariantsForValue("c")

        assertThat(aVariants, hasSize(3))
        assertThat(bVariants, hasSize(3))
        assertThat(cVariants, hasSize(3))
    }

    void test_getVariantsForValue_for_null_and_empty_value() {
        assertThat(values.getVariantsForValue(null), is(IsEmptyCollection.empty()))
        assertThat(values.getVariantsForValue("  "), is(IsEmptyCollection.empty()))
    }

    void test_getVariantsForValue_returns_unmodifiable_set() {
        values.addMandatoryValueWithVariants(["a", "b", "c"])
        Set<String> variants = values.getVariantsForValue "a"

        shouldFail(UnsupportedOperationException) {
            variants.add "d"
        }
    }

    void test_removeVariantsForValue() {
        values.addMandatoryValueWithVariants(["a", "b", "c"])
        assertThat(values.getSize(), is(1))
        assertFalse(values.removeVariantsForValue("not contained"))
        assertThat(values.getSize(), is(1))

        assertTrue(values.removeVariantsForValue("c"))
        assertThat(values.isEmpty(), is(true))
    }

    void test_isEmpty() {
        assertThat(values.isEmpty(), is(true))
        values.addMandatoryValueWithVariants(["a", "b", "c"])
        assertThat(values.isEmpty(), is(false))
        values.removeVariantsForValue("b")
        assertThat(values.isEmpty(), is(true))
    }

    void test_getSize() {
        assertThat(values.getSize(), is(0))
        values.addMandatoryValueWithVariants(["a", "b", "c"])
        assertThat(values.getSize(), is(1))
        values.addMandatoryValueWithVariants(["1", "2", "3"])
        assertThat(values.getSize(), is(2))
    }

    void test_resolveAllParenthesizedContent() {
        values.addMandatoryValueWithVariants(["Lehrer(in)"])
        values.resolveAllParenthesizedContent()

        assertThat(values.getVariantsForValue("Lehrer"), is(not(IsEmptyCollection.empty())))
        assertThat(values.getVariantsForValue("Lehrerin").size(), is(3))
    }
}
