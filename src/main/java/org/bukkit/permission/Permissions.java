/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bukkit.permission;

/**
 * Represents a structure which may contain permissions, such as a profile or a user
 */
public interface Permissions {
    <T> T getPermission(final String key);

    boolean isPermissionSet(final String key);

    void setPermission(final String key, final Object value);

    void unsetPermission(final String key);
}
