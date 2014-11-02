/*
 * Created on 30.09.2009
 * 
 * Copyright 2007-2009 Roland Krueger (www.rolandkrueger.info)
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
package org.jlexis.data.vocable.verification;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static junit.framework.Assert.*;

/**
 * @author Roland Krueger
 */
public class WhitespaceAndSuffixTolerantSetTest {
    private WhitespaceAndSuffixTolerantSet tolerantSet;

    @Before
    public void setUp() {
        tolerantSet = new WhitespaceAndSuffixTolerantSet();
    }

    @Test
    public void testSuffixTolerance() {
        char[] chars = new char[]{'#', '+', '[', '*', '.', ']', '?'};
        tolerantSet = new WhitespaceAndSuffixTolerantSet(chars);
        tolerantSet.addAll(new ArrayList<String>(Arrays.asList(new String[]{
                "test", "data", "string?"
        })));

        assertTrue(tolerantSet.contains("test"));
        assertTrue(tolerantSet.contains("data#"));
        assertTrue(tolerantSet.contains("test]"));
        assertTrue(tolerantSet.contains("test?"));

        assertFalse(tolerantSet.contains("*test"));
        assertFalse(tolerantSet.contains("test'"));
        assertFalse(tolerantSet.contains("test-"));
    }

    @Test
    public void testContains() {
        tolerantSet.addAll(new ArrayList<String>(Arrays.asList(new String[]{
                "xxx.yyy", "wo? hier !", "string?"
        })));
        assertTrue(tolerantSet.contains("xxx.yyy"));
        assertTrue(tolerantSet.contains("xxx .  yyy"));
        assertTrue(tolerantSet.contains("xxx\t.\tyyy"));
        assertTrue(tolerantSet.contains("wo   ?hier!"));
        assertTrue(tolerantSet.contains("wo?hier!"));
        assertTrue(tolerantSet.contains("wo\t?   hier  ! \t"));
        assertTrue(tolerantSet.contains("string        ?            "));
    }

    @Test
    public void testRemove() {
        tolerantSet.addAll(new ArrayList<String>(Arrays.asList(new String[]{
                "xxx.yyy", "wo? hier !", "string?", "test"
        })));

        assertEquals(4, tolerantSet.size());
        tolerantSet.remove("test     ");
        tolerantSet.remove("xxx .   \t yyy ");
        tolerantSet.remove("     wo ?hier!  \t");
        tolerantSet.remove("string  ?");
        assertTrue(tolerantSet.isEmpty());
    }

    @Test
    public void testIterator() {
        List<String> list = new ArrayList<String>(Arrays.asList(new String[]{
                "_test_", "da.ta", "string?"
        }));
        tolerantSet.addAll(list);
        Set<String> compare = new HashSet<String>();
        for (String s : tolerantSet) {
            compare.add(s);
        }
        assertTrue(compare.containsAll(list));
        assertTrue(list.containsAll(compare));
    }

    @Test
    public void testAdd() {
        tolerantSet.add("test ? data string.");
        assertTrue(tolerantSet.contains("test ? data string."));
    }

    @Test
    public void testAddAll() {
        List<String> setToAdd = new ArrayList<String>(Arrays.asList(new String[]{
                "test", "data", "string?"
        }));
        tolerantSet.addAll(setToAdd);
        assertTrue(tolerantSet.contains("data"));
        assertTrue(tolerantSet.contains("string?"));
        assertTrue(tolerantSet.contains("test"));
    }

    @Test
    public void testClear() {
        tolerantSet.addAll(new ArrayList<String>(Arrays.asList(new String[]{
                "test", "data", "string?"
        })));
        assertEquals(3, tolerantSet.size());
        tolerantSet.clear();
        assertEquals(0, tolerantSet.size());
    }
}
