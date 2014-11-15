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

class SetOfAbbreviationVariantsTest extends GroovyTestCase {

    public static final String ABBREVIATION = "abbreviation"

    SetOfAbbreviationVariants set

    @Override
    void setUp() {
        set = new SetOfAbbreviationVariants()
    }

    void test_addAbbreviation_more_variants_null_is_ignored() {
        set.addAbbreviation(ABBREVIATION, "abbr.", null)
        assertEquals(["abbr."] as Set, set.getVariantsFor(ABBREVIATION))
    }

    void test_addAbbreviation_first_variant_null__or_empty_is_rejected() {
        shouldFail(NullPointerException) {
            set.addAbbreviation(ABBREVIATION, null)
        }
        shouldFail(IllegalStateException) {
            set.addAbbreviation(ABBREVIATION, "")
        }
        shouldFail(IllegalStateException) {
            set.addAbbreviation(ABBREVIATION, "   ")
        }
    }

    void test_addAbbreviation_fullForm_null__or_empty_is_rejected() {
        shouldFail(NullPointerException) {
            set.addAbbreviation(null, "abbr.")
        }
        shouldFail(IllegalStateException) {
            set.addAbbreviation("", "abbr.")
        }
        shouldFail(IllegalStateException) {
            set.addAbbreviation("   ", "abbr.")
        }
    }

    void test_getVariantsFor() {
        addTestAbbreviations()
        assertEquals(["abbr.", "abbrev.", "abbr"] as Set, set.getVariantsFor(ABBREVIATION))
    }

    void test_addAbbreviation() {
        addTestAbbreviations()
        set.addAbbreviation("something", "sth.", "sth", "someth.")

        assertEquals(["abbr.", "abbrev.", "abbr"] as Set, set.getVariantsFor(ABBREVIATION))
        assertEquals(["sth.", "sth", "someth."] as Set, set.getVariantsFor("something"))
        assertEquals(2, set.getSize())
    }

    void test_getVariantsFor_unknown_abbreviation() {
        addTestAbbreviations()
        assertEquals([] as Set, set.getVariantsFor("something"))
    }

    void test_add_same_abbreviation_several_times() {
        addTestAbbreviations()
        addTestAbbreviations()
        assertEquals(1, set.getSize())
    }

    void test_harmonizeAll_with_input_null() {
        shouldFail(NullPointerException) {
            set.harmonizeAll((String) null)
        }
    }

    void test_harmonizeAll() {
        addTestAbbreviations()
        set.addAbbreviation("something", "sth", "sth.", "someth.")

        assertEquals("", set.harmonizeAll(""))
        assertEquals(ABBREVIATION, set.harmonizeAll("abbr"))
        assertEquals("something", set.harmonizeAll("someth."))
        assertEquals("abbreviation something", set.harmonizeAll("abbrev.   sth."))
    }

    void test_harmonizeAll_for_empty_collection_returns_empty_collection() {
        assertTrue(set.harmonizeAll(Collections.emptyList()).isEmpty())
    }

    void test_harmonizeAll_for_null_or_empty_strings_returns_empty_collection() {
        assertTrue(set.harmonizeAll(["", "   ", null]).isEmpty())
    }

    void test_harmonizeAll_for_collection() {
        addTestAbbreviations()
        set.addAbbreviation("something", "sth", "sth.", "someth.")

        Collection<String> result = set.harmonizeAll(["abbr", "sth"])
        assertEquals([ABBREVIATION, "something"], result)
    }

    private addTestAbbreviations() {
        set.addAbbreviation(ABBREVIATION, "abbr.", "abbrev.", "abbr")
    }
}
