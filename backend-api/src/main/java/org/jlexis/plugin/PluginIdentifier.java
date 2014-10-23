package org.jlexis.plugin;

import java.util.Objects;

public class PluginIdentifier {
    private String key;
    private String version;
    private String identifier;

    public PluginIdentifier(String key, String version) {
        this.key = Objects.requireNonNull(key);
        this.version = Objects.requireNonNull(version);
        this.identifier = key + "-" + version;
    }

    public String getVersion() {
        return version;
    }

    public String getKey() {
        return key;
    }

    public String getIdentifier() {
        return identifier;
    }
}
