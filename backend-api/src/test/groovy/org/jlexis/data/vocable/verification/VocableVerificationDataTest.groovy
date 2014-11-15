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
/**
 * Created by rkrueger on 15.11.2014.
 */
class VocableVerificationDataTest extends JLexisTestBase {

    VocableVerificationData.ChooseAbbreviationVariantsBuilder builder;
    SetOfAbbreviationVariants abbreviationVariants

    void setUp() {
        builder = VocableVerificationData.create();
        abbreviationVariants = new SetOfAbbreviationVariants()
        abbreviationVariants.addAbbreviation("something", "sth.", "sth")
        abbreviationVariants.addAbbreviation("someone", "so.")
    }

    void test_tokenizeAndAddString_with_empty_values() {
        builder.withoutAbbreviationVariants()
            .tokenizeAndAddString("")
            .tokenizeAndAddString(null);
        assertEquals(0, builder.withoutAbbreviationVariants().build().getAllValues().size());
    }

    void test_abbreviation_variants_are_harmonized() {
        VocableVerificationData data = builder
                .withAbbreviationVariants(abbreviationVariants)
                .addMandatoryValueWithOptions(["give sth.   to so."])
                .build()
        assertEquals(new WhitespaceAndSuffixTolerantSet(["give something to someone"]), data.getAllValues())
    }
}
