package org.jlexis.plugin

import org.jlexis.tests.JLexisTestBase

class PluginIdentifierTest extends JLexisTestBase {

    void testConstructor() {
        requireParametersNonNull(["key", "version"]) { key, version ->
            new PluginIdentifier(key, version)
        }
    }

    void testGetIdentifier() {
        PluginIdentifier identifier = new PluginIdentifier("key", "version")
        assertEquals(identifier.getIdentifier(), "key-version")
    }
}
