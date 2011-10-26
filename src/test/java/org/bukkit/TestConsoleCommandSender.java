package org.bukkit;

import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;

public class TestConsoleCommandSender extends ConsoleCommandSender {
    protected TestConsoleCommandSender(Server server) {
        super(server);
    }

    @Override
    public void sendMessage(String message) {
        getServer().getLogger().info(message);
    }
}
