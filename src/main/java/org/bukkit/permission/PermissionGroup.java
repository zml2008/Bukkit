package org.bukkit.permission;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.bukkit.Server;

public class PermissionGroup implements Permissions {
    private String name;
    private final Server server;
    private PermissionProfile overrideProfile;
    private List<PermissionProfile> profiles;

    public PermissionGroup(final Server server, final String name) {
        this.server = server;
        this.name = name;

        this.profiles = Collections.synchronizedList(new LinkedList<PermissionProfile>());
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setPermission(final String key, final Object value) {
        // a group has its own profile, that takes precedence
        // over any other profiles that it has
        PermissionProfile profile = getOverrideProfile();
        profile.setPermission(key, value);
    }

    public void unsetPermission(String key) {
        PermissionProfile profile = getOverrideProfile();
        profile.unsetPermission(key);
    }

    public <T> T getPermission(final String key) {
        if (overrideProfile.isPermissionSet(key)) {
            return overrideProfile.getPermission(key);
        }

        T result = null;
        for (PermissionProfile profile : profiles) {
            if (profile.isPermissionSet(key)) {
                result = profile.getPermission(key);
            }
        }

        return result;
    }

    public boolean isPermissionSet(final String key) {
        if (overrideProfile.isPermissionSet(key)) {
            return true;
        }
        for (PermissionProfile profile : profiles) {
            if (profile.isPermissionSet(key)) {
                return true;
            }
        }

        return false;
    }

    /**
     * The default permission profile for a group.
     *
     * This profile overrides any other profiles settings that may be defined.
     *
     * @return this group's default permission profile
     * 
     * TODO get the group profile from disk if it exists
     */
    private PermissionProfile getOverrideProfile() {
        if (overrideProfile == null) {
            overrideProfile = new PermissionProfile(server, name);
        }
        return overrideProfile;
    }

    /**
     * Retrieve the list of all profiles associated with this group.
     * 
     * These profiles determine the permissions of the group. Profiles that occur later
     * in the list take precedence over earlier ones.
     * 
     * Every group also has its own profile, whose permissions override every
     * profile in this list. This is known as the override profile.
     * 
     * This list is mutable, and should be used to add or remove profiles to the group.
     * It is imperative that you synchronize on this list if you iterate over it.
     * 
     * @return A thread safe list of PermissionProfiles that apply to this group.
     */
    public List<PermissionProfile> getProfiles(){
        return profiles;
    }

    /**
     * Add a profile to this group, at the given level.
     * 
     * Profiles with higher levels will override a lower profiles permissions.
     * If two profiles have the same level, the one whose name is 'lower' will
     * be overridden.
     * 
     * @param profile The PermissionProfile to add to this group.
     * @param level Higher levelled profiles override lower levelled ones.
     *//*
    public void addPermissionProfile(PermissionProfile profile, int level) {
        if (profile == null) {
            return;
        }
        ProfileLevel profileLevel = new ProfileLevel(profile, level);
        profiles.add(profileLevel);
    }*/
/*
    class ProfileLevel implements Comparable<ProfileLevel>{
        private final PermissionProfile profile;
        private int level;

        public ProfileLevel(PermissionProfile profile, int level) {
            this.profile = profile;
            this.level = level;
        }

        PermissionProfile getProfile(){
            return profile;
        }

        public int compareTo(ProfileLevel o) {
            if (this.equals(o)) {
                return 0;
            }
            if (this.level == o.level) {
                return this.profile.getName().compareTo(o.profile.getName());
            } else {
                return this.level - o.level;
            }
        }
    }
    */
    /*
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

    public static PermissionProfile loadProfile(final Server server,
            final String name, final Map<String, Object> map) throws InvalidPermissionProfileException {
        PermissionProfile result = new PermissionProfile(server, name);
        Set<String> keys = map.keySet();

        for (String key : keys) {
            result.loadNode(key, map.get(key));
        }

        return result;
    }

    public static PermissionProfile[] loadProfiles(final Server server,
            final Map<String, Object> map) throws InvalidPermissionProfileException {
        List<PermissionProfile> result = new ArrayList<PermissionProfile>();
        Set<String> keys = map.keySet();

        for (String key : keys) {
            try {
                result.add(loadProfile(server, key, (Map<String, Object>) map.get(key)));
            } catch (ClassCastException ex) {
                throw new InvalidPermissionProfileException("Attempted to load profile " + key, ex);
            }
        }

        return result.toArray(new PermissionProfile[0]);
    }

    public static PermissionProfile[] loadProfiles(final Server server, final InputStream stream)
            throws InvalidPermissionProfileException {
        return loadProfiles(server, (Map<String, Object>)yaml.load(stream));
    }

    public static PermissionProfile[] loadProfiles(final Server server, final Reader reader)
            throws InvalidPermissionProfileException {
        return loadProfiles(server, (Map<String, Object>)yaml.load(reader));
    }
    */
}
