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
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized)
class NormalizeAbbreviationAlternativesTest extends JLexisTestBase {

    String fullForm
    String[]  alternatives
    String expectedOutput
    String input

    @Parameterized.Parameters
    static data() {
        return [
                ["\t    abbreviation    ", ["abbr.", "abbrev.", "abbrv.", "abbr"], "abbreviation abbreviation abbreviation abbreviation", "abbreviation abbr. abbrev. abbrv."] as Object[],
                ["abbreviation", ["abbr.", "abbrev.", "abbrv.", "abbr"], "abbreviation abbreviation abbreviation abbreviation", "abbreviation abbr. abbrev. abbrv."] as Object[],
                ["abbreviation", ["abbr.", "abbrev.", "abbrv.", "abbr"], "xxxabbr.abbreviation", "xxxabbr.   abbr."] as Object[],
                ["abbreviation", ["abbr.", "abbrev.", "abbrv.", "abbr"], "abbreviation abbreviation", "abbr.abbr"] as Object[],
                ["abbreviation", ["abbr.", "abbrev.", "abbrv.", "abbr"], "abbreviation xxx(abbreviation)abbreviation", "abbr.xxx(abbr.)abbr"] as Object[],
                ["abbreviation", ["abbr.", "abbrev.", "abbrv.", "abbr"], "abbreviation", "abbreviation"] as Object[],
                ["abbreviation", ["abbr.", "abbr"], "abbreviation", "abbr."] as Object[],
                ["abbreviation", [/*"abbr",*/ "abbr."], "(abbreviation)", "(abbr.)"] as Object[],
                ["abbreviation", ["abbr.", "abbrev.", "abbrv.", "abbr"], "abbreviation", "abbrev."] as Object[],
                ["abbreviation", ["abbr.", "abbrev.", "abbrv.", "abbr"], "abbreviation", "abbrv."] as Object[],
                ["abbreviation", ["abbr.", "abbrev.", "abbrv.", "abbr"], "abbreviation", "abbr"] as Object[],

                ["you will", ["you'll"], "you will", "you ' ll"] as Object[],
                ["you will", ["you'll"], "you will", "you'll"] as Object[],
                ["you will", ["you'll"], "you will", "you    ' ll"] as Object[],
                ["you will", ["you'll"], "youth willing", "youth willing"] as Object[],

                ["someone", ["so."], "", ""] as Object[],
                ["someone", ["s{}][|+(^*)o."], "xxx", "xxx"] as Object[],
        ]
    }

    NormalizeAbbreviationAlternativesTest(String fullForm, def alternatives, String expectedOutput, String input) {
        this.fullForm = fullForm
        this.alternatives = alternatives
        this.expectedOutput = expectedOutput
        this.input = input
    }

    @Test
    void test_normalize() {
        AbbreviationAlternatives abbreviationAlternatives = new AbbreviationAlternatives(fullForm)
        abbreviationAlternatives.setAlternatives(alternatives)

        assertEquals("incorrect output for input '" + input + "'", expectedOutput, abbreviationAlternatives.normalize(input))
    }
}
