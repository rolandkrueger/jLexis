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

package org.jlexis.data.vocable.terms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class WordStemTermParameterizedTest {

    private final WordStemTerm wordStemTerm;
    private final String input;
    private final String expected;

    public WordStemTermParameterizedTest(String input, String expected) {
        this.input = input;
        this.expected = expected;
        wordStemTerm = new WordStemTerm();
    }

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new String[][]{
                {"abcd", "abcd"},
                {"xxxx|yyy", "xxxx"},
                {"(aa) <zzzz>ooo", "zzzz"},
        });
    }

    @Test
    public void testGetWordStemString() {
        wordStemTerm.setUserEnteredString(input);
        assertThat(wordStemTerm.getWordStemString(), is(expected));
    }
}