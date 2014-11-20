/*
 * $Id: RegularTermTest.java 204 2009-12-17 15:20:16Z roland $
 * Created on 09.03.2009
 * 
 * Copyright 2007 Roland Krueger (www.rolandkrueger.info)
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
package org.jlexis.data.vocable.terms;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RegularTermTest extends AbstractTermTest {
    @Override
    @Test
    public void testIsWordStem() {
        assertFalse(term.isWordStem());
    }

    @Override
    public AbstractTerm createTerm() {
        return new RegularTerm();
    }

    @Test
    public void testGetResolvedTerm() {
        term.setUserEnteredString("--xxx");
        assertThat(term.getStringWithWordStemResolved(), is("--xxx"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_getWordStemTerm_not_supported() {
        term.getWordStemTerm();
    }

    @Test
    public void testGetWordStemString() {
        for (String data : userTestStrings) {
            term.setUserEnteredString(data);
            assertEquals(data, term.getWordStemString());
        }
        for (String data : encodedTestStrings) {
            term.setUserEnteredString(data);
            assertEquals(data, term.getWordStemString());
        }
    }
}