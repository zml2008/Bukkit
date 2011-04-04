
package org.bukkit.permission;

import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

/**
 * Represents a profile of permissions
 */
public class PermissionProfile implements Permissions {
    private final String name;
    private final Map<String, Object> permissionValues = new HashMap<String, Object>();
    private static final Yaml yaml = new Yaml(new SafeConstructor());

    public PermissionProfile(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPermission(final String key, final Object value) {
        PermissionDescriptionNode desc = Bukkit.getServer().getPluginManager().getPermissionPath(key);

        if (desc.isValid(value)) {
            permissionValues.put(key, value);
        } else {
            throw new IllegalArgumentException("Cannot set " + key + " to " + value);
        }
    }

    public void unsetPermission(String key) {
        permissionValues.remove(key);
    }

    public <T> T getPermission(final String key) {
        T result = (T)permissionValues.get(key);

        if (result == null) {
            PermissionDescriptionNode desc = Bukkit.getServer().getPluginManager().getPermissionPath(key);

            result = (T)desc.getDefault();
        }

        return result;
    }

    public boolean isPermissionSet(final String key) {
        return permissionValues.containsKey(key);
    }

    private void loadNode(final String path, Object node) throws InvalidPermissionProfileException {
        if (node instanceof Map<?, ?>) {
            try {
                Map<String, Object> map = (Map<String, Object>)node;
                Set<String> keys = map.keySet();

                for (String key : keys) {
                    loadNode(path + "." + key, map.get(key));
                }
            } catch (ClassCastException ex) {
                throw new InvalidPermissionProfileException(path + " is not a valid map in " + name, ex);
            } catch (IllegalArgumentException ex) {
                throw new InvalidPermissionProfileException(ex);
            }
        } else {
            setPermission(path, node);
        }
    }

    public static PermissionProfile loadProfile(final String name,
            final Map<String, Object> map) throws InvalidPermissionProfileException {
        PermissionProfile result = new PermissionProfile(name);
        Set<String> keys = map.keySet();

        for (String key : keys) {
            result.loadNode(key, map.get(key));
        }

        return result;
    }

    public static PermissionProfile[] loadProfiles(final Map<String, Object> map) throws InvalidPermissionProfileException {
        List<PermissionProfile> result = new ArrayList<PermissionProfile>();
        Set<String> keys = map.keySet();

        for (String key : keys) {
            try {
                result.add(loadProfile(key, (Map<String, Object>) map.get(key)));
            } catch (ClassCastException ex) {
                throw new InvalidPermissionProfileException("Attempted to load profile " + key, ex);
            }
        }

        return result.toArray(new PermissionProfile[0]);
    }

    public static PermissionProfile[] loadProfiles(final InputStream stream)
            throws InvalidPermissionProfileException {
        return loadProfiles((Map<String, Object>)yaml.load(stream));
    }

    public static PermissionProfile[] loadProfiles(final Reader reader)
            throws InvalidPermissionProfileException {
        return loadProfiles((Map<String, Object>)yaml.load(reader));
    }
}
