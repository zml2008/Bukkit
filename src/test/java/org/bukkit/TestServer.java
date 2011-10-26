package org.bukkit;

import com.avaje.ebean.config.ServerConfig;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.Recipe;
import org.bukkit.map.MapView;
import org.bukkit.permissions.Permissible;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.SimpleServicesManager;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.File;
import java.util.*;
import java.util.logging.Logger;

import static org.mockito.Mockito.*;

public class TestServer implements Server {
    private static final Logger logger = Logger.getLogger("TestBukkit");
    private boolean whitelist, allowNether, onlineMode, allowFlight;
    private final Map<String, List<Player>> onlinePlayers = new HashMap<String, List<Player>>();
    private List<World> worlds = new ArrayList<World>();
    private List<String> serverOps = new ArrayList<String>(),
            whitelisted = new ArrayList<String>(),
            banned = new ArrayList<String>(),
            ipBanned = new ArrayList<String>();

    private Map<Short, MapView> mapViews = new HashMap<Short, MapView>();
    private SimpleCommandMap commandMap = new SimpleCommandMap(this);
    private PluginManager pluginManager = new SimplePluginManager(this, commandMap);
    private ServicesManager servicesManager = new SimpleServicesManager();
    private int spawnRadius = 20,viewDistance = 10, maxPlayers = 20;
    private BukkitScheduler scheduler = mock(BukkitScheduler.class);
    private GameMode gameMode = GameMode.SURVIVAL;
    private ConsoleCommandSender consoleSender = new TestConsoleCommandSender(this);
    private File pluginsDir = new File("plugins"), updateDir = new File(pluginsDir, "updates");

    private static TestServer instance = new TestServer();

    public static TestServer getInstance() {
        return instance;
    }

    private TestServer() {
        Bukkit.setServer(this);
    }

    public String getName() {
        return "TestBukkit";
    }

    public String getVersion() {
        return "AlphaAlphaAlpha0.1";
    }

    public Player[] getOnlinePlayers() {
        List<Player> players = new ArrayList<Player>();
        for (List<Player> world : onlinePlayers.values()) {
            players.addAll(world);
        }
        return players.toArray(new Player[players.size()]);
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getPort() {
        return 25565;
    }

    public int getViewDistance() {
        return viewDistance;
    }

    public String getIp() {
        return null; //TODO
    }

    public String getServerName() {
        return "Testing Server for Bukkit";
    }

    public String getServerId() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean getAllowNether() {
        return allowNether;
    }

    public void setAllowNether(boolean value) {
        this.allowNether = value;
    }

    public boolean hasWhitelist() {
        return whitelist;
    }

    public void setWhitelist(boolean value) {
        this.whitelist = value;
    }

    public Set<OfflinePlayer> getWhitelistedPlayers() {
        Set<OfflinePlayer> players = new HashSet<OfflinePlayer>();
        for (String name : whitelisted) {
            players.add(getOfflinePlayer(name));
        }
        return players;
    }

    public void reloadWhitelist() {}

    public int broadcastMessage(String message) {
        return broadcast(message, BROADCAST_CHANNEL_USERS);
    }

    public String getUpdateFolder() {
        return updateDir.getAbsolutePath();
    }

    public File getUpdateFolderFile() {
        return updateDir;
    }

    public Player getPlayer(String name) {
        return getPlayerExact(name);
    }

    public Player getPlayerExact(String name) {
        for (List<Player> players : onlinePlayers.values()) {
            for (Player player : players) {
                if (player.getName().equalsIgnoreCase(name)) {
                    return player;
                }
            }
        }
        return null;
    }

    public List<Player> matchPlayer(String name) {
        List<Player> returned = new ArrayList<Player>();
        for (List<Player> players : onlinePlayers.values()) {
            for (Player player : players) {
                if (player.getName().startsWith(name)) {
                    returned.add(player);
                }
            }
        }
        return returned;
    }

    public PluginManager getPluginManager() {
        return pluginManager;
    }

    public BukkitScheduler getScheduler() {
        return scheduler;
    }

    public ServicesManager getServicesManager() {
        return servicesManager;
    }

    public List<World> getWorlds() {
        return worlds;
    }

    public World createWorld(String name, World.Environment environment) {
        return createWorld(WorldCreator.name(name).environment(environment));
    }

    public World createWorld(String name, World.Environment environment, long seed) {
        return createWorld(WorldCreator.name(name).environment(environment).seed(seed));
    }

    public World createWorld(String name, World.Environment environment, ChunkGenerator generator) {
        return createWorld(WorldCreator.name(name).environment(environment).generator(generator));
    }

    public World createWorld(String name, World.Environment environment, long seed, ChunkGenerator generator) {
        return createWorld(WorldCreator.name(name).environment(environment).seed(seed).generator(generator));
    }

    public World createWorld(WorldCreator creator) {
        if (getWorld(creator.name()) != null) {
            return getWorld(creator.name());
        }
        World world = mock(World.class);
        when(world.getName()).thenReturn(creator.name());
        when(world.getEnvironment()).thenReturn(creator.environment());
        when(world.getSeed()).thenReturn(creator.seed());
        when(world.getGenerator()).thenReturn(creator.generator());
        worlds.add(world);
        return world;
    }

    public boolean unloadWorld(String name, boolean save) {
        World world = getWorld(name);
        if (world == null) return false;
        return unloadWorld(world, save);
    }

    public boolean unloadWorld(World world, boolean save) {
        if (save) world.save();
        return worlds.remove(world);
    }

    public World getWorld(String name) {
        for (World world : worlds) {
            if (world.getName().equals(name)) {
                return world;
            }
        }
        return null;
    }

    public World getWorld(UUID uid) {
        for (World world : worlds) {
            if (uid.equals(world.getUID())) {
                return world;
            }
        }
        return null;
    }

    public MapView getMap(short id) {
        return mapViews.get(id);
    }

    public MapView createMap(World world) {
        MapView map = mock(MapView.class);
        when(map.getWorld()).thenReturn(world);
        return map;
    }

    public void reload() {
       logger.info("Server reloaded");
    }

    public Logger getLogger() {
        return logger;
    }

    public PluginCommand getPluginCommand(String name) {
        Command command = commandMap.getCommand(name);
        if (command instanceof PluginCommand) {
            return (PluginCommand)command;
        } else {
            return null;
        }
    }

    public void savePlayers() {
        for (Player player : getOnlinePlayers()) {
            player.saveData();
        }
    }

    public boolean dispatchCommand(CommandSender sender, String commandLine) throws CommandException {
        return commandMap.dispatch(sender, commandLine);
    }

    public void configureDbConfig(ServerConfig config) {
        com.avaje.ebean.config.DataSourceConfig ds = new com.avaje.ebean.config.DataSourceConfig();
        ds.setDriver("org.sqlite.JDBC");
        ds.setUrl("jdbc:sqlite:{DIR}{NAME}.db");
        ds.setUsername("aperture");
        ds.setPassword("science");
        ds.setIsolationLevel(com.avaje.ebeaninternal.server.lib.sql.TransactionIsolation.getLevel("SERIALIZABLE"));

        if (ds.getDriver().contains("sqlite")) {
            config.setDatabasePlatform(new com.avaje.ebean.config.dbplatform.SQLitePlatform());
            config.getDatabasePlatform().getDbDdlSyntax().setIdentity("");
        }

        config.setDataSourceConfig(ds);
    }

    public boolean addRecipe(Recipe recipe) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Map<String, String[]> getCommandAliases() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getSpawnRadius() {
        return spawnRadius;
    }

    public void setSpawnRadius(int value) {
        spawnRadius = value;
    }

    public boolean getOnlineMode() {
        return onlineMode;
    }

    public boolean getAllowFlight() {
        return allowFlight;
    }

    public void shutdown() {
    }

    public int broadcast(String message, String permission) {
        int targets = 0;
        for (Permissible perm : getPluginManager().getPermissionSubscriptions(permission)) {
            if (perm instanceof CommandSender) {
                ((CommandSender) perm).sendMessage(message);
            }
        }
        return targets;
    }

    public OfflinePlayer getOfflinePlayer(final String name) {
        OfflinePlayer player = getPlayer(name);
        if (player == null) {
            player = new OfflinePlayer() {
                
                public boolean isOnline() {
                    return false;
                }

                public String getName() {
                    return name;
                }

                public boolean isBanned() {
                    return banned.contains(name);
                }

                public void setBanned(boolean value) {
                    if (value) {
                        banned.add(name);
                    } else {
                        banned.remove(name);
                    }
                }

                public boolean isWhitelisted() {
                    return whitelisted.contains(name);
                }

                public void setWhitelisted(boolean value) {
                    if (value) {
                        whitelisted.add(name);
                    } else {
                        whitelisted.remove(name);
                    }
                }

                public Player getPlayer() {
                    return TestServer.this.getPlayer(name);
                }

                public Map<String, Object> serialize() {
                    return null;  //To change body of implemented methods use File | Settings | File Templates.
                }

                public boolean isOp() {
                    return serverOps.contains(name);
                }

                public void setOp(boolean value) {
                    if (value) {
                        serverOps.add(name);
                    } else {
                        serverOps.remove(name);
                    }
                }
            };
        }
        return player;
    }

    public Set<String> getIPBans() {
        Set<String> ret = new HashSet<String>();
        for (String name : ipBanned) {
            ret.add(name);
        }
        return ret;
    }

    public void banIP(String address) {
        ipBanned.add(address);
    }

    public void unbanIP(String address) {
        ipBanned.remove(address);
    }

    public Set<OfflinePlayer> getBannedPlayers() {
        Set<OfflinePlayer> players = new HashSet<OfflinePlayer>();
        for (String name : banned) {
            players.add(getOfflinePlayer(name));
        }
        return players;
    }

    public Set<OfflinePlayer> getOperators() {
        Set<OfflinePlayer> players = new HashSet<OfflinePlayer>();
        for (String name : serverOps) {
            players.add(getOfflinePlayer(name));
        }
        return players;
    }

    public GameMode getDefaultGameMode() {
        return gameMode;
    }

    public void setDefaultGameMode(GameMode mode) {
        this.gameMode = mode;
    }

    public ConsoleCommandSender getConsoleSender() {
        return consoleSender;
    }
}
