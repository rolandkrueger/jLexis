/*
 * Created on 12.11.2014
 *
 * Copyright 2014 Roland Krueger (www.rolandkrueger.info)
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
 */

package org.jlexis.data.vocable.verification

import static org.jlexis.data.vocable.verification.ResolveParentheses.*

class ResolveParenthesesTest extends GroovyTestCase {

    void test_with_single_pair_of_parentheses() {
        assertEquals(["Lehrerin", "Lehrer", "Lehrer(in)"] as Set, resolveParentheses("Lehrer(in)"))
    }

    void test_with_two_pairs_of_parentheses() {
        assertEquals(["(un)gesund", "(un)gesund(es)", "(un)gesundes", "gesund", "gesund(es)", "gesundes", "ungesund", "ungesund(es)", "ungesundes"] as Set,
                resolveParentheses("(un)gesund(es)"))
    }

    void test_resolve_collection_of_values() {
        assertEquals(["(un)gesund", "(un)gesund(es)", "(un)gesundes", "gesund", "gesund(es)", "gesundes", "ungesund", "ungesund(es)", "ungesundes",
                      "Lehrerin", "Lehrer", "Lehrer(in)"] as Set,
                resolveParenthesesForCollection(["(un)gesund(es)", "Lehrer(in)"]))
    }

    void test_too_many_parentheses_will_return_input() {
        assertEquals(["(a)(b)(c)(d)(e)"] as Set, resolveParentheses("(a)(b)(c)(d)(e)"))
    }

    void test_resolve_unbalanced_parentheses() {
        assertEquals(["(a (b (c"] as Set, resolveParentheses("(a (b (c"))
        assertEquals([")a )b )c"] as Set, resolveParentheses(")a )b )c"))
        assertEquals(["(a)(b c", "(b c", "a(b c"] as Set, resolveParentheses("(a)(b c"))
    }

    void test_resolve_many_parentheses() {
        assertEquals(80, resolveParentheses("(a)(b)(c)(d)").size())
    }

    void test_resolve_empty_parentheses() {
        assertEquals(["test", "test ()"] as Set, resolveParentheses("test ()"))
    }

    void test_with_no_parentheses() {
        assertEquals(["language"] as Set, resolveParentheses("language"))
    }

    void test_nested_parentheses() {
        assertEquals(["x(y(z))", "xy", "x(y)", "x(yz)", "x", "xyz"] as Set, resolveParentheses("x(y(z))"))
    }

    void test_with_null_input() {
        assertEquals([] as Set, resolveParentheses(null))
    }
}
