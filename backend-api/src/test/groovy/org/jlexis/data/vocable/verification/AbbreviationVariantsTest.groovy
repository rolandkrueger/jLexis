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

class AbbreviationVariantsTest extends JLexisTestBase {

    AbbreviationVariants abbreviationVariants

    @Override
    void setUp() {
        abbreviationVariants = new AbbreviationVariants("abbreviation")
    }

    void testConstructor() {
        requireParametersNonNull(["abbreviation"]) { abbreviation ->
            new AbbreviationVariants(abbreviation[0])
        }
    }

    void test_setVariants() {
        abbreviationVariants.setVariants("abbr.", "abbr.", "abbrev.")
        assertEquals(["abbrev.", "abbr."] as Set, abbreviationVariants.getVariants())
    }

    void test_setVariants_resets_previously_set_values() {
        abbreviationVariants.setVariants("old", "gammal", "alt")
        abbreviationVariants.setVariants("new", "ny", "neu")
        assertEquals(["new", "ny", "neu"] as Set, abbreviationVariants.getVariants())
    }

    void testGetFullForm() {
        assertThat new AbbreviationVariants("abbreviation").getFullForm(), is("abbreviation")
    }

    void test_setVariants_With_null_or_empty() {
        shouldFail(NullPointerException) {
            abbreviationVariants.setVariants(null, null)
        }
        shouldFail(NullPointerException) {
            abbreviationVariants.setVariants(null)
        }
    }
}
