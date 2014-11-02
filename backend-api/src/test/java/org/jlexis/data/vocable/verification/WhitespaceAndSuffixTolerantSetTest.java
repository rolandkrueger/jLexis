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

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.IsCollectionContaining.hasItem;

/**
 * @author Roland Krueger
 */
public class WhitespaceAndSuffixTolerantSetTest {
    private WhitespaceAndSuffixTolerantSet tolerantSet;

    @Before
    public void setUp() {
        tolerantSet = new WhitespaceAndSuffixTolerantSet('#');
    }

    @Test
    public void test_constructor_with_collection_argument() {
        tolerantSet = new WhitespaceAndSuffixTolerantSet(Arrays.asList("test", "value"), '#', '+');
        assertThat(tolerantSet, hasSize(2));
        assertSetContainsAll("test#", "value+");
    }

    @Test
    public void test_when_no_suffix_defined_then_set_doesnt_tolerate_suffix_chars() {
        tolerantSet = new WhitespaceAndSuffixTolerantSet();
        tolerantSet.add("test value");
        // an additional suffix '#' is not tolerated since no tolerated suffix characters have been provided
        assertThat("set incorrectly tolerates redundant suffix character although no such character was configured",
                tolerantSet.contains("test value#"), is(false));
    }

    @Test
    public void test_only_single_character_suffixes_are_tolerated() {
        tolerantSet.add("test value");
        assertThat("set incorrectly tolerates multi-character suffixes",
                tolerantSet.contains("test value##"), is(false));
    }

    @Test
    public void test_redundant_suffix_is_tolerated_with_additional_whitespace() {
        tolerantSet.add("test value");
        assertThat("set doesn't tolerate redundant suffix with additional whitespace",
                tolerantSet.contains("test  value \t  #"), is(true));
    }

    @Test
    public void test_suffix_characters_in_user_data_is_respected() {
        tolerantSet = new WhitespaceAndSuffixTolerantSet('?');
        tolerantSet.add("is it real?");
        assertThat("set doesn't respect suffix characters in user data",
                tolerantSet.contains("is it real?  ?"), is(true));
        assertThat("set doesn't respect suffix characters in user data",
                tolerantSet.contains("is it real?"), is(true));
    }

    @Test
    public void test_original_input_is_kept() {
        String testValue = "  test \t  value with \n    a lot of \t\t whitespace ";
        tolerantSet.add(testValue);
        assertThat("set doesn't ignore whitespace",
                tolerantSet.contains("test value with a lot of whitespace"), is(true));
        assertThat("set doesn't keep original input", tolerantSet, hasItem(equalTo(testValue)));
    }

    @Test
    public void test_suffix_characters_are_tolerated() {
        tolerantSet = new WhitespaceAndSuffixTolerantSet('#', '+', '[', '*', '.', ']', '?');
        tolerantSet.addAll(Arrays.asList(
                "test", "data", "string?"
        ));

        assertSetContainsAll("test", "data#", "test]", "test?");
    }

    @Test
    public void test_suffix_characters_are_not_tolerated_as_prefix() {
        tolerantSet.add("test value");
        assertSetDoesNotContainAny("#test value");
    }

    @Test
    public void test_set_does_not_tolerate_suffix_characters_which_were_not_configured() {
        tolerantSet.add("test value");
        assertSetDoesNotContainAny("test value+");
    }

    @Test
    public void test_contains_is_whitespace_tolerant() {
        tolerantSet.addAll(new ArrayList<String>(Arrays.asList(new String[]{
                "xxx.yyy", "wo? hier !", "string?"
        })));

        assertSetContainsAll("xxx.yyy",
                "xxx .  yyy",
                "xxx\t.\tyyy",
                "wo   ?hier!",
                "wo?hier!",
                "wo\t?   hier  ! \t",
                "string        ?            ");
    }

    @Test
    public void test_remove() {
        tolerantSet.addAll(Arrays.asList("xxx.yyy", "wo? hier !", "string?", "test"));

        assertThat(tolerantSet, hasSize(4));
        assertThat(tolerantSet.remove("test#"), is(false));
        assertThat(tolerantSet.remove("not in set"), is(false));

        assertThat(tolerantSet.remove("test     "), is(true));
        assertThat(tolerantSet.remove("xxx .   \t yyy "), is(true));
        assertThat(tolerantSet.remove("     wo ?hier!  \t"), is(true));
        assertThat(tolerantSet.remove("string  ?"), is(true));
        assertThat(tolerantSet, is(empty()));

        assertThat(tolerantSet.remove(null), is(false));
        assertThat(tolerantSet.remove(new Object()), is(false));
    }

    @Test
    public void test_removeAll() {
        tolerantSet.addAll(Arrays.asList("test value", "test"));
        assertThat(tolerantSet.removeAll(Arrays.asList("not", "in", "set")), is(false));

        assertThat(tolerantSet.removeAll(Arrays.asList("  test  value  ", "test ")), is(true));
        assertThat(tolerantSet, is(empty()));
    }

    @Test
    public void test_iterator() {
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
    public void test_add() {
        tolerantSet.add("test ? data string.");
        assertSetContainsAll("test ? data string.");
    }

    @Test
    public void test_addAll() {
        List<String> setToAdd = new ArrayList<String>(Arrays.asList(new String[]{
                "test", "data", "string?"
        }));
        tolerantSet.addAll(setToAdd);

        assertSetContainsAll("data", "string?", "test");
    }

    @Test
    public void test_clear() {
        tolerantSet.addAll(new ArrayList<String>(Arrays.asList(new String[]{
                "test", "data", "string?"
        })));
        assertThat(tolerantSet, hasSize(3));
        tolerantSet.clear();
        assertThat(tolerantSet, is(empty()));
    }

    @Test
    public void test_contains_only_works_for_non_null_strings() {
        tolerantSet.add("test value");

        assertThat(tolerantSet.contains(null), is(false));
        assertThat(tolerantSet.contains(new Object()), is(false));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_retainAll_not_supported() {
        tolerantSet.retainAll(Collections.emptyList());
    }

    @Test
    public void test_contains_all() {
        tolerantSet.add("test value");
        assertThat(tolerantSet.containsAll(Arrays.asList("test value", "   test   value   ", "test value#")), is(true));
    }

    private void assertSetContainsAll(String... values) {
        for (String value : values) {
            assertThat("set doesn't contain value '" + value + "'",
                    tolerantSet.contains(value), is(true));
        }
    }

    private void assertSetDoesNotContainAny(String... values) {
        for (String value : values) {
            assertThat("set does incorrectly contain value " + value,
                    tolerantSet.contains(value), is(false));
        }
    }
}
