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

import org.jlexis.tests.JLexisTestBase
import static org.hamcrest.CoreMatchers.*

import static org.hamcrest.MatcherAssert.assertThat

class AbbreviationAlternativesTest extends JLexisTestBase {

    AbbreviationAlternatives abbreviationAlternatives

    @Override
    void setUp() {
        abbreviationAlternatives = new AbbreviationAlternatives("abbreviation")
    }

    void testConstructor() {
        requireParametersNonNull(["abbreviation"]) { abbreviation ->
            new AbbreviationAlternatives(abbreviation[0])
        }
    }

    void test_setAlternatives_null() {
        abbreviationAlternatives.setAlternatives(null)
        assertEquals([] as Set, abbreviationAlternatives.getAlternatives())
    }

    void test_setAlternatives_empty_array_yields_empty_set() {
        abbreviationAlternatives.setAlternatives()
        assertEquals([] as Set, abbreviationAlternatives.getAlternatives())
    }

    void test_setAlternatives() {
        abbreviationAlternatives.setAlternatives("abbr.", "abbr.", "abbrev.")
        assertEquals(["abbrev.", "abbr."] as Set, abbreviationAlternatives.getAlternatives())
    }

    void test_setAlternatives_resets_previously_set_values() {
        abbreviationAlternatives.setAlternatives("old", "gammal", "alt")
        abbreviationAlternatives.setAlternatives("new", "ny", "neu")
        assertEquals(["new", "ny", "neu"] as Set, abbreviationAlternatives.getAlternatives())
    }

    void testGetFullForm() {
        assertThat new AbbreviationAlternatives("abbreviation").getFullForm(), is("abbreviation")
    }

    void test_addAlternatives_WithNull() {
        shouldFail(NullPointerException) {
            abbreviationAlternatives.setAlternatives(null, null)
        }
    }

}
