package com.moon.util;

import java.util.HashMap;
import java.util.Map;

import static com.moon.util.TypeUtil.cast;

/**
 * @author benshaoye
 */
@Deprecated
public class PropertiesGroup extends HashMap<String, Object> {

    private final PropertiesGroup all;
    private final PropertiesGroup parent;

    public PropertiesGroup(Map<String, ?> source) {
        this.parent = null;
        final PropertiesGroup top = all = this;
        source.forEach((key, value) -> {
            PropertiesGroup group = top;
            String[] keys = key.split("\\.");
            for (int i = 0, len = keys.length - 1; ; i++) {
                key = keys[i];
                if (i < len) {
                    group = findGroup(top, group, key);
                } else {
                    group.put(key, value);
                    break;
                }
            }
        });
    }

    private PropertiesGroup(PropertiesGroup all, PropertiesGroup parent) {
        this.parent = parent;
        this.all = all;
    }

    private PropertiesGroup findGroup(PropertiesGroup root, PropertiesGroup parent, String namespace) {
        PropertiesGroup group = (PropertiesGroup) parent.get(namespace);
        if (group == null) {
            parent.put(namespace, group = new PropertiesGroup(root, parent));
        }
        return group;
    }

    public boolean isRoot() {
        return all == this;
    }

    public PropertiesGroup getAll() {
        return all;
    }

    public PropertiesGroup getParent() {
        return parent;
    }

    public PropertiesGroup getSubGroup(String namespace) {
        return (PropertiesGroup) get(namespace);
    }

    public int getInt(String key) {
        return cast().toIntValue(get(key));
    }

    public long getLong(String key) {
        return cast().toLongValue(get(key));
    }

    public double getDouble(String key) {
        return cast().toDoubleValue(get(key));
    }

    public boolean getBoolean(String key) {
        return cast().toBooleanValue(get(key));
    }

    public String getString(String key) {
        return String.valueOf(get(key));
    }
}
