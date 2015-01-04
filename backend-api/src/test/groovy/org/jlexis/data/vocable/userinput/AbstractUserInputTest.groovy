/*
 * Copyright 2007-2015 Roland Krueger (www.rolandkrueger.info)
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

package org.jlexis.data.vocable.userinput
import org.jlexis.data.vocable.RegisteredVocableDataKey
import org.jlexis.tests.JLexisTestBase

abstract class AbstractUserInputTest extends JLexisTestBase {

    UserInput userInput

    abstract UserInput createUserInput()

    @Override
    void setUp() {
        userInput = createUserInput()
    }

    void test_addUserInput() {
        RegisteredVocableDataKey key = new RegisteredVocableDataKey("key")
        assertTrue("user input should be empty", userInput.isEmpty())
        userInput.addUserInput(key, "user input")
        assertFalse("user input should not be empty", userInput.isEmpty())
        def retrievedInput = userInput.getUserInput(key)
        assertEquals("user input", retrievedInput.userEnteredString)
    }

    void test_adding_empty_or_null_input_removes_term() {
        RegisteredVocableDataKey key = new RegisteredVocableDataKey("key")
        userInput.addUserInput(key, "user input")
        assertTrue(userInput.isInputDefinedFor(key))
        userInput.addUserInput(key, "")
        assertFalse(userInput.isInputDefinedFor(key))
        assertTrue(userInput.isEmpty())

        userInput.addUserInput(key, "user input")
        assertTrue(userInput.isInputDefinedFor(key))
        userInput.addUserInput(key, null)
        assertFalse(userInput.isInputDefinedFor(key))
        assertTrue(userInput.isEmpty())
    }

    void test_is_empty_for_user_input_with_registered_inflected_term() {
        def wordStemKey = new RegisteredVocableDataKey("wordStem")
        def inflectedTermKey = new RegisteredVocableDataKey("inflected")
        userInput.registerKeyForWordStem(wordStemKey)
        userInput.registerKeyForInflectedTerm(wordStemKey, inflectedTermKey)
        assertTrue(userInput.isEmpty())

        userInput.addUserInput(wordStemKey, "")
        userInput.addUserInput(inflectedTermKey, null)
        assertTrue(userInput.isEmpty())

        userInput.addUserInput(wordStemKey, "work|ing")
        userInput.addUserInput(inflectedTermKey, "--s")
        assertFalse(userInput.isEmpty())
    }

    void test_new_user_input_is_empty() {
        assertTrue(userInput.isEmpty())
    }

    void test_user_input_with_regular_term_is_not_empty() {
        userInput.addUserInput(new RegisteredVocableDataKey("key"), "input")
        assertFalse(userInput.isEmpty())
        userInput.addUserInput(new RegisteredVocableDataKey("key"), "")
        assertTrue(userInput.isEmpty())
        assertFalse(userInput.isInputDefinedFor(new RegisteredVocableDataKey("key")))
    }

    void test_using_inflected_terms() {
        def wordStemKey = new RegisteredVocableDataKey("wordStem")
        def inflectedTermKey = new RegisteredVocableDataKey("inflected")
        userInput.registerKeyForWordStem(wordStemKey)
        userInput.registerKeyForInflectedTerm(wordStemKey, inflectedTermKey)

        userInput.addUserInput(wordStemKey, "work|ing")
        userInput.addUserInput(inflectedTermKey, "--s")
        assertTrue(userInput.isInputDefinedFor(wordStemKey))
        assertTrue(userInput.isInputDefinedFor(inflectedTermKey))

        assertEquals("working", userInput.getUserInput(wordStemKey).cleanedString)
        assertEquals("works", userInput.getUserInput(inflectedTermKey).cleanedStringWithWordStemResolved)
    }
}
