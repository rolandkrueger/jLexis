package org.jlexis.plugin;

import com.google.common.base.MoreObjects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PluginIdentifier that = (PluginIdentifier) o;

        return Objects.equals(identifier, that.identifier);
    }

    @Override
    public int hashCode() {
        return identifier.hashCode();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("key", getKey())
                .add("version", getVersion())
                .toString();
    }
}
