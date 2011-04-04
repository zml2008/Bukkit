package org.bukkit.permission;

public interface Rank {

    /**
     * By default every group has rank 0. The rank does not affect the order in which group
     * permissions are applied to members of that group. Rather, since players are typically
     * grouped together, it allows for a heirachial system where inter-player permissions can
     * be set depending on who has superior (or inferior) rank.
     * 
     * For example, you may allow players to teleport to any other player whose max rank is lower
     * than theirs.
     * 
     * @return the rank of this group
     */
    public abstract int getRank();

    /**
     * @param rank the new rank of this group
     * 
     * @see getRank()
     */
    public abstract void setRank(int rank);

}
