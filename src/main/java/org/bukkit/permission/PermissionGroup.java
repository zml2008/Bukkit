package org.bukkit.permission;

import java.util.List;

import org.bukkit.entity.Player;

public interface PermissionGroup extends Permissions, Rank {

    public String getName();

    public void setName(final String name);

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
     * @return A list of PermissionProfiles that apply to this group.
     */
    public List<PermissionProfile> getProfiles();

    /**
     * The list of all Players that belong to this group.
     * 
     * Adding a Player to this list adds that Player to the group, and has the same effect as adding this
     * group to that Players groups list.
     * 
     * It is imperative that you synchronize on this list if you iterate over it.
     * 
     * @return A list of all players who belong to this group.
     */
    public List<Player> getPlayers();
}
