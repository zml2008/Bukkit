
package org.bukkit.entity;

import java.net.InetSocketAddress;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.permission.PermissionGroup;
import org.bukkit.permission.Permissions;

/**
 * Represents a player, connected or not
 *
 */
public interface Player extends HumanEntity, CommandSender, Permissions {
    /**
     * Checks if this player is currently online
     *
     * @return true if they are online
     */
    public boolean isOnline();

    /**
     * Gets the "friendly" name to display of this player. This may include color.
     *
     * Note that this name will not be displayed in game, only in chat and places
     * defined by plugins
     *
     * @return String containing a color formatted name to display for this player
     */
    public String getDisplayName();

    /**
     * Sets the "friendly" name to display of this player. This may include color.
     *
     * Note that this name will not be displayed in game, only in chat and places
     * defined by plugins
     *
     * @return String containing a color formatted name to display for this player
     */
    public void setDisplayName(String name);

    /**
     * Set the target of the player's compass.
     *
     * @param loc
     */
    public void setCompassTarget(Location loc);

    /**
     * Get the previously set compass target.
     *
     * @return location of the target
     */
    public Location getCompassTarget();

    /**
     * Gets the socket address of this player
     * @return the player's address
     */
    public InetSocketAddress getAddress();
    
    /**
     * Sends this sender a message raw
     *
     * @param message Message to be displayed
     */
    public void sendRawMessage(String message);

    /**
     * Kicks player with custom kick message.
     *
     * @return
     */
    public void kickPlayer(String message);

    /**
     * Says a message (or runs a command).
     *
     * @param msg message to print
     */
    public void chat(String msg);

    /**
     * Makes the player perform the given command
     *
     * @param command Command to perform
     * @return true if the command was successful, otherwise false
     */
    public boolean performCommand(String command);

    /**
     * Returns if the player is in sneak mode
     * @return true if player is in sneak mode
     */
    public boolean isSneaking();

    /**
     * Sets the sneak mode the player
     * @param sneak true if player should appear sneaking
     */
    public void setSneaking(boolean sneak);

    /**
     * Saves the players current location, health, inventory, motion, and other information into the username.dat file, in the world/player folder
     */
    public void saveData();

    /**
     * Loads the players current location, health, inventory, motion, and other information from the username.dat file, in the world/player folder
     *
     * Note: This will overwrite the players current inventory, health, motion, etc, with the state from the saved dat file.
     */
    public void loadData();

    /**
     * Forces an update of the player's entire inventory.
     *
     * @return
     *
     * @deprecated This method should not be relied upon as it is a temporary work-around for a larger, more complicated issue.
     */
    public void updateInventory();

    /**
     * Retrieve the list of all groups associated with this player.
     * 
     * These groups determine the permissions of the player. Groups that occur later
     * in the list take precedence over earlier ones.
     * 
     * Every Player is also a member of their own group, whose permissions override every
     * other group they are a member of. This is known as the override group.
     * 
     * This list is mutable, and should be used to add or remove profiles to the group.
     * It is imperative that you synchronize on this list if you iterate over it.
     * 
     * @return A thread safe list of PermissionGroups that apply to this player.
     */
    List<PermissionGroup> getGroups();

    /**
     * Players inherit their rank from the groups they are part of. The group with the highest
     * rank determines the rank of this player.
     * 
     * This rank can be used in inter-player permissions. For example,players may be allowed 
     * to teleport to any other player whose rank is lower than theirs.
     * 
     * @return the rank of this player
     */
    public int getRank();
}
