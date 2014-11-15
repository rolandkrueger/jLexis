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

class MandatoryValuesWithAlternativesTest extends GroovyTestCase {

    MandatoryValuesWithAlternatives values

    void setUp() {
        values = new MandatoryValuesWithAlternatives()
    }

    void test_copy() {
        values.addMandatoryValueWithAlternatives(["a", "b", "c"])
        MandatoryValuesWithAlternatives copy = MandatoryValuesWithAlternatives.copy(values)
        assertEquals(values, copy)
    }

    void test_addMandatoryValueWithAlternatives() {
        values.addMandatoryValueWithAlternatives(["a", "b", "c"])
        assertThat(values.getSize(), is(1))
        assertThat(values.getAlternativesForValue("c"), hasSize(3))
    }

    void test_addMandatoryValueWithAlternatives_with_null_and_empty_collection() {
        values.addMandatoryValueWithAlternatives(null)
        assertTrue(values.isEmpty())
        values.addMandatoryValueWithAlternatives([])
        assertTrue(values.isEmpty())
    }

    void test_getAllValues() {
        values.addMandatoryValueWithAlternatives(["a", "b", "c"])
        values.addMandatoryValueWithAlternatives(["1", "2", "3"])

        WhitespaceAndSuffixTolerantSet allValues = new WhitespaceAndSuffixTolerantSet(["a", "b", "c", "1", "2", "3"])
        assertEquals(allValues.size(), values.getAllValues().size())
    }

    void test_getAllValues_returns_unmodifiable_set() {
        values.addMandatoryValueWithAlternatives(["a", "b", "c"])
        Set<String> allValues = values.getAllValues()

        shouldFail(UnsupportedOperationException) {
            allValues.add "d"
        }
    }

    void test_getAlternativesForValue() {
        values.addMandatoryValueWithAlternatives(["a", "b", "c"])
        Set<String> aAlternatives = values.getAlternativesForValue("a")
        Set<String> bAlternatives = values.getAlternativesForValue("b")
        Set<String> cAlternatives = values.getAlternativesForValue("c")

        assertThat(aAlternatives, hasSize(3))
        assertThat(bAlternatives, hasSize(3))
        assertThat(cAlternatives, hasSize(3))
    }

    void test_getAlternativesForValue_for_null_and_empty_value() {
        assertThat(values.getAlternativesForValue(null), is(IsEmptyCollection.empty()))
        assertThat(values.getAlternativesForValue("  "), is(IsEmptyCollection.empty()))
    }

    void test_getAlternativesForValue_returns_unmodifiable_set() {
        values.addMandatoryValueWithAlternatives(["a", "b", "c"])
        Set<String> alternatives = values.getAlternativesForValue "a"

        shouldFail(UnsupportedOperationException) {
            alternatives.add "d"
        }
    }

    void test_removeAlternativesForValue() {
        values.addMandatoryValueWithAlternatives(["a", "b", "c"])
        assertThat(values.getSize(), is(1))
        assertFalse(values.removeAlternativesForValue("not contained"))
        assertThat(values.getSize(), is(1))

        assertTrue(values.removeAlternativesForValue("c"))
        assertThat(values.isEmpty(), is(true))
    }

    void test_isEmpty() {
        assertThat(values.isEmpty(), is(true))
        values.addMandatoryValueWithAlternatives(["a", "b", "c"])
        assertThat(values.isEmpty(), is(false))
        values.removeAlternativesForValue("b")
        assertThat(values.isEmpty(), is(true))
    }

    void test_getSize() {
        assertThat(values.getSize(), is(0))
        values.addMandatoryValueWithAlternatives(["a", "b", "c"])
        assertThat(values.getSize(), is(1))
        values.addMandatoryValueWithAlternatives(["1", "2", "3"])
        assertThat(values.getSize(), is(2))
    }

    void test_resolveAllParenthesizedContent() {
        values.addMandatoryValueWithAlternatives(["Lehrer(in)"])
        values.resolveAllParenthesizedContent()

        assertThat(values.getAlternativesForValue("Lehrer"), is(not(IsEmptyCollection.empty())))
        assertThat(values.getAlternativesForValue("Lehrerin").size(), is(3))
    }
}
