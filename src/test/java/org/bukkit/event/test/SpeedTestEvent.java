package org.bukkit.event.test;

import org.bukkit.event.Event;
// import org.bukkit.plugin.HandlerList;

public class SpeedTestEvent extends Event {
    // private static final HandlerList handlers = new HandlerList();

    public SpeedTestEvent() {
        super("SpeedTestEvent");
    }

    /* @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    } */
}
