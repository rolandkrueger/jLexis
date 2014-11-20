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

import static junit.framework.Assert.assertEquals;

@RunWith(Parameterized.class)
public class InflectedTermParameterizedTest {
    private WordStemTerm wordStem;
    private InflectedTerm inflectedTerm;

    private String word;
    private String inflected;
    private String expected;

    public InflectedTermParameterizedTest(String word, String inflected, String expected) {
        this.word = word;
        this.inflected = inflected;
        this.expected = expected;
        wordStem = new WordStemTerm();
        inflectedTerm = new InflectedTerm(wordStem);
    }

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new String[][]{
                {"abcd", "--xyz", "abcdxyz"},
                {"xxxx|yyy", "--ooo $", "xxxxooo $"},
                {"(aa) <zzzz>ooo", "vvv--vvv", "vvvzzzzvvv"},
        });
    }

    @Test
    public void testGetResolvedTerm() {
        wordStem.setUserEnteredString(word);
        inflectedTerm.setUserEnteredString(inflected);
        assertEquals(expected, inflectedTerm.getStringWithWordStemResolved());
    }
}