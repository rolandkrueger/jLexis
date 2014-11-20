/*
 * $Id: AbstractTermDataTest.java 204 2009-12-17 15:20:16Z roland $
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

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public abstract class AbstractTermTest {
    protected AbstractTerm term;

    protected String[] encodedTestStrings = new String[]{
            "${-}xxx xxx${-} -x-",
            "abc${|}def",
            "xx${-}xxx ${<}abc${>}xx${$}",
            "${-} ${<} ${>} ${|}"};

    protected String[] userTestStrings = new String[]{
            "--xxx xxx-- -x-",
            "abc|def",
            "xx--xxx <abc>xx$",
            "${-} ${<} ${>} ${|}"};

    public abstract AbstractTerm createTerm();

    @Deprecated
    public abstract void testGetVerificationData();

    public abstract void testIsWordStem();

    public abstract void testGetResolvedTerm();

    public abstract void testGetWordStem();

    @Before
    public void setUp() {
        term = createTerm();
    }

    @Test
    public void test_getEncodedString() {
        for (String data : encodedTestStrings) {
            term.setEncodedString(data);
            assertEquals(data, term.getEncodedString());
        }
    }

    @Test
    public void test_getUserEnteredString() {
        for (String data : userTestStrings) {
            term.setUserEnteredString(data);
            assertEquals(data, term.getUserEnteredString());
        }
    }

    @Test
    public void test_getter_return_empty_string_for_new_term() {
        assertThat(term.getEncodedString(), is(""));
        assertThat(term.getUserEnteredString(), is(""));
        assertThat(term.getCleanedString(), is(""));
        assertThat(term.getCleanedStringWithWordStemResolved(), is(""));
        assertThat(term.getStringWithWordStemResolved(), is(""));
    }

    @Test
    public void test_setEncodedString_is_properly_decoded() {
        term.setEncodedString("${$} ${-} ${<} ${|} ${>}");
        assertThat(term.getUserEnteredString(), is("$ -- < | >"));
        assertThat(term.getEncodedString(), is("${$} ${-} ${<} ${|} ${>}"));
    }

    @Test
    public void test_special_characters_are_encoded() {
        term.setUserEnteredString("$ -- < | >");
        assertThat(term.getUserEnteredString(), is("$ -- < | >"));
        assertThat(term.getEncodedString(), is("${$} ${-} ${<} ${|} ${>}"));
    }

    @Test
    public void test_cached_strings_are_properly_cleared_in_the_setters() {
        term.setUserEnteredString("abc");
        term.setEncodedString("123");
        assertThat(term.getEncodedString(), is("123"));
        assertThat(term.getUserEnteredString(), is("123"));

        term = createTerm();
        term.setEncodedString("abc");
        term.setUserEnteredString("123");
        assertThat(term.getEncodedString(), is("123"));
        assertThat(term.getUserEnteredString(), is("123"));
    }

    @Test
    public void test_getCleanedString() {
        String[] purged = new String[]{
                "-xxx xxx- -x-",
                "abcdef",
                "xx-xxx abcxx$",
                "-   "};
        for (int i = 0; i < purged.length; ++i) {
            term.setEncodedString(encodedTestStrings[i]);
            assertEquals(purged[i], term.getCleanedString());
        }
    }

    @Test
    public void test_setUserEnteredString_null() {
        term.setUserEnteredString(null);
        assertThat(term.getUserEnteredString(), is(""));
    }

    @Test
    public void test_setEncodedString_null() {
        term.setEncodedString(null);
        assertThat(term.getEncodedString(), is(""));
    }

    @Test
    public void test_isEmpty() {
        assertThat(term.isEmpty(), is(true));
        term.setEncodedString("");
        assertThat(term.isEmpty(), is(true));

        term.setUserEnteredString("");
        assertThat(term.isEmpty(), is(true));

        term.setUserEnteredString("xxx");
        assertThat(term.isEmpty(), is(false));
    }
}
