package org.jlexis.plugin

import com.google.common.testing.EqualsTester
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

    void testEquals() {
        EqualsTester tester = new EqualsTester()
                .addEqualityGroup(new PluginIdentifier("key", "version"), new PluginIdentifier("key", "version"))
                .testEquals()
    }
}
