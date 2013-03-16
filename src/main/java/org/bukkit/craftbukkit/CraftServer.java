package org.bukkit.craftbukkit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.Warning.WarningState;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.conversations.Conversable;
import org.bukkit.craftbukkit.help.SimpleHelpMap;
import org.bukkit.craftbukkit.inventory.CraftFurnaceRecipe;
import org.bukkit.craftbukkit.inventory.CraftInventoryCustom;
import org.bukkit.craftbukkit.inventory.CraftItemFactory;
import org.bukkit.craftbukkit.inventory.CraftRecipe;
import org.bukkit.craftbukkit.inventory.CraftShapedRecipe;
import org.bukkit.craftbukkit.inventory.CraftShapelessRecipe;
import org.bukkit.craftbukkit.inventory.RecipeIterator;
import org.bukkit.craftbukkit.map.CraftMapView;
import org.bukkit.craftbukkit.metadata.EntityMetadataStore;
import org.bukkit.craftbukkit.metadata.PlayerMetadataStore;
import org.bukkit.craftbukkit.metadata.WorldMetadataStore;
import org.bukkit.craftbukkit.potion.CraftPotionBrewer;
import org.bukkit.craftbukkit.scheduler.CraftScheduler;
import org.bukkit.craftbukkit.updater.AutoUpdater;
import org.bukkit.craftbukkit.updater.BukkitDLUpdaterService;
import org.bukkit.craftbukkit.util.DatFileFilter;
import org.bukkit.craftbukkit.util.Versioning;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.help.HelpMap;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLoadOrder;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.SimpleServicesManager;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.plugin.messaging.StandardMessenger;
import org.bukkit.scheduler.BukkitWorker;
import org.bukkit.util.StringUtil;
import org.bukkit.util.permissions.DefaultPermissions;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.error.MarkedYAMLException;

import org.apache.commons.lang.Validate;
import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.config.dbplatform.SQLitePlatform;
import com.avaje.ebeaninternal.server.lib.sql.TransactionIsolation;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.MapMaker;

import jline.console.ConsoleReader;

public final class CraftServer implements Server {
    private final String serverName = "CraftBukkit";
    private final String serverVersion;
    private final String bukkitVersion = Versioning.getBukkitVersion();
    private final ServicesManager servicesManager = new SimpleServicesManager();
    private final CraftScheduler scheduler = new CraftScheduler();
    private final SimpleCommandMap commandMap = new SimpleCommandMap(this);
    private final SimpleHelpMap helpMap = new SimpleHelpMap(this);
    private final StandardMessenger messenger = new StandardMessenger();
    private final PluginManager pluginManager = new SimplePluginManager(this, commandMap);
    protected final net.minecraft.server.MinecraftServer console;
    protected final net.minecraft.server.dedicated.DedicatedPlayerList playerList;
    private final Map<String, World> worlds = new LinkedHashMap<String, World>();
    private YamlConfiguration configuration;
    private final Yaml yaml = new Yaml(new SafeConstructor());
    private final Map<String, OfflinePlayer> offlinePlayers = new MapMaker().softValues().makeMap();
    private final AutoUpdater updater;
    private final EntityMetadataStore entityMetadata = new EntityMetadataStore();
    private final PlayerMetadataStore playerMetadata = new PlayerMetadataStore();
    private final WorldMetadataStore worldMetadata = new WorldMetadataStore();
    private int monsterSpawn = -1;
    private int animalSpawn = -1;
    private int waterAnimalSpawn = -1;
    private int ambientSpawn = -1;
    public int chunkGCPeriod = -1;
    public int chunkGCLoadThresh = 0;
    private File container;
    private WarningState warningState = WarningState.DEFAULT;
    private final BooleanWrapper online = new BooleanWrapper();

    private final class BooleanWrapper {
        private boolean value = true;
    }

    static {
        ConfigurationSerialization.registerClass(CraftOfflinePlayer.class);
        CraftItemFactory.instance();
    }

    public CraftServer(net.minecraft.server.MinecraftServer console, net.minecraft.server.management.ServerConfigurationManager playerList) {
        this.console = console;
        this.playerList = (net.minecraft.server.dedicated.DedicatedPlayerList) playerList;
        this.serverVersion = CraftServer.class.getPackage().getImplementationVersion();
        online.value = console.getPropertyManager().func_73670_a("online-mode", true);

        Bukkit.setServer(this);

        // Register all the Enchantments and PotionTypes now so we can stop new registration immediately after
        net.minecraft.enchantment.Enchantment.field_77338_j.getClass();
        org.bukkit.enchantments.Enchantment.stopAcceptingRegistrations();

        Potion.setPotionBrewer(new CraftPotionBrewer());
        net.minecraft.potion.Potion.field_76440_q.getClass();
        PotionEffectType.stopAcceptingRegistrations();
        // Ugly hack :(

        if (!Main.useConsole) {
            getLogger().info("Console input is disabled due to --noconsole command argument");
        }

        configuration = YamlConfiguration.loadConfiguration(getConfigFile());
        configuration.options().copyDefaults(true);
        configuration.setDefaults(YamlConfiguration.loadConfiguration(getClass().getClassLoader().getResourceAsStream("configurations/bukkit.yml")));
        saveConfig();
        ((SimplePluginManager) pluginManager).useTimings(configuration.getBoolean("settings.plugin-profiling"));
        monsterSpawn = configuration.getInt("spawn-limits.monsters");
        animalSpawn = configuration.getInt("spawn-limits.animals");
        waterAnimalSpawn = configuration.getInt("spawn-limits.water-animals");
        ambientSpawn = configuration.getInt("spawn-limits.ambient");
        console.autosavePeriod = configuration.getInt("ticks-per.autosave");
        warningState = WarningState.value(configuration.getString("settings.deprecated-verbose"));
        chunkGCPeriod = configuration.getInt("chunk-gc.period-in-ticks");
        chunkGCLoadThresh = configuration.getInt("chunk-gc.load-threshold");

        updater = new AutoUpdater(new BukkitDLUpdaterService(configuration.getString("auto-updater.host")), getLogger(), configuration.getString("auto-updater.preferred-channel"));
        updater.setEnabled(configuration.getBoolean("auto-updater.enabled"));
        updater.setSuggestChannels(configuration.getBoolean("auto-updater.suggest-channels"));
        updater.getOnBroken().addAll(configuration.getStringList("auto-updater.on-broken"));
        updater.getOnUpdate().addAll(configuration.getStringList("auto-updater.on-update"));
        updater.check(serverVersion);

        loadPlugins();
        enablePlugins(PluginLoadOrder.STARTUP);
    }

    private File getConfigFile() {
        return (File) console.options.valueOf("bukkit-settings");
    }

    private void saveConfig() {
        try {
            configuration.save(getConfigFile());
        } catch (IOException ex) {
            Logger.getLogger(CraftServer.class.getName()).log(Level.SEVERE, "Could not save " + getConfigFile(), ex);
        }
    }

    public void loadPlugins() {
        pluginManager.registerInterface(JavaPluginLoader.class);

        File pluginFolder = (File) console.options.valueOf("plugins");

        if (pluginFolder.exists()) {
            Plugin[] plugins = pluginManager.loadPlugins(pluginFolder);
            for (Plugin plugin : plugins) {
                try {
                    String message = String.format("Loading %s", plugin.getDescription().getFullName());
                    plugin.getLogger().info(message);
                    plugin.onLoad();
                } catch (Throwable ex) {
                    Logger.getLogger(CraftServer.class.getName()).log(Level.SEVERE, ex.getMessage() + " initializing " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
                }
            }
        } else {
            pluginFolder.mkdir();
        }
    }

    public void enablePlugins(PluginLoadOrder type) {
        if (type == PluginLoadOrder.STARTUP) {
            helpMap.clear();
            helpMap.initializeGeneralTopics();
        }

        Plugin[] plugins = pluginManager.getPlugins();

        for (Plugin plugin : plugins) {
            if ((!plugin.isEnabled()) && (plugin.getDescription().getLoad() == type)) {
                loadPlugin(plugin);
            }
        }

        if (type == PluginLoadOrder.POSTWORLD) {
            commandMap.registerServerAliases();
            loadCustomPermissions();
            DefaultPermissions.registerCorePermissions();
            helpMap.initializeCommands();
        }
    }

    public void disablePlugins() {
        pluginManager.disablePlugins();
    }

    private void loadPlugin(Plugin plugin) {
        try {
            pluginManager.enablePlugin(plugin);

            List<Permission> perms = plugin.getDescription().getPermissions();

            for (Permission perm : perms) {
                try {
                    pluginManager.addPermission(perm);
                } catch (IllegalArgumentException ex) {
                    getLogger().log(Level.WARNING, "Plugin " + plugin.getDescription().getFullName() + " tried to register permission '" + perm.getName() + "' but it's already registered", ex);
                }
            }
        } catch (Throwable ex) {
            Logger.getLogger(CraftServer.class.getName()).log(Level.SEVERE, ex.getMessage() + " loading " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
        }
    }

    public String getName() {
        return serverName;
    }

    public String getVersion() {
        return serverVersion + " (MC: " + console.func_71249_w() + ")";
    }

    public String getBukkitVersion() {
        return bukkitVersion;
    }

    @SuppressWarnings("unchecked")
    public Player[] getOnlinePlayers() {
        List<net.minecraft.entity.player.EntityPlayerMP> online = playerList.field_72404_b;
        Player[] players = new Player[online.size()];

        for (int i = 0; i < players.length; i++) {
            players[i] = online.get(i).field_71135_a.getPlayer();
        }

        return players;
    }

    public Player getPlayer(final String name) {
        Player[] players = getOnlinePlayers();

        Player found = null;
        String lowerName = name.toLowerCase();
        int delta = Integer.MAX_VALUE;
        for (Player player : players) {
            if (player.getName().toLowerCase().startsWith(lowerName)) {
                int curDelta = player.getName().length() - lowerName.length();
                if (curDelta < delta) {
                    found = player;
                    delta = curDelta;
                }
                if (curDelta == 0) break;
            }
        }
        return found;
    }

    public Player getPlayerExact(String name) {
        String lname = name.toLowerCase();

        for (Player player : getOnlinePlayers()) {
            if (player.getName().equalsIgnoreCase(lname)) {
                return player;
            }
        }

        return null;
    }

    public int broadcastMessage(String message) {
        return broadcast(message, BROADCAST_CHANNEL_USERS);
    }

    public Player getPlayer(final net.minecraft.entity.player.EntityPlayerMP entity) {
        return entity.field_71135_a.getPlayer();
    }

    public List<Player> matchPlayer(String partialName) {
        List<Player> matchedPlayers = new ArrayList<Player>();

        for (Player iterPlayer : this.getOnlinePlayers()) {
            String iterPlayerName = iterPlayer.getName();

            if (partialName.equalsIgnoreCase(iterPlayerName)) {
                // Exact match
                matchedPlayers.clear();
                matchedPlayers.add(iterPlayer);
                break;
            }
            if (iterPlayerName.toLowerCase().contains(partialName.toLowerCase())) {
                // Partial match
                matchedPlayers.add(iterPlayer);
            }
        }

        return matchedPlayers;
    }

    public int getMaxPlayers() {
        return playerList.func_72352_l();
    }

    // NOTE: These are dependent on the corrisponding call in MinecraftServer
    // so if that changes this will need to as well
    public int getPort() {
        return this.getConfigInt("server-port", 25565);
    }

    public int getViewDistance() {
        return this.getConfigInt("view-distance", 10);
    }

    public String getIp() {
        return this.getConfigString("server-ip", "");
    }

    public String getServerName() {
        return this.getConfigString("server-name", "Unknown Server");
    }

    public String getServerId() {
        return this.getConfigString("server-id", "unnamed");
    }

    public String getWorldType() {
        return this.getConfigString("level-type", "DEFAULT");
    }

    public boolean getGenerateStructures() {
        return this.getConfigBoolean("generate-structures", true);
    }

    public boolean getAllowEnd() {
        return this.configuration.getBoolean("settings.allow-end");
    }

    public boolean getAllowNether() {
        return this.getConfigBoolean("allow-nether", true);
    }

    public boolean getWarnOnOverload() {
        return this.configuration.getBoolean("settings.warn-on-overload");
    }

    public boolean getQueryPlugins() {
        return this.configuration.getBoolean("settings.query-plugins");
    }

    public boolean hasWhitelist() {
        return this.getConfigBoolean("white-list", false);
    }

    // NOTE: Temporary calls through to server.properies until its replaced
    private String getConfigString(String variable, String defaultValue) {
        return this.console.getPropertyManager().func_73671_a(variable, defaultValue);
    }

    private int getConfigInt(String variable, int defaultValue) {
        return this.console.getPropertyManager().func_73669_a(variable, defaultValue);
    }

    private boolean getConfigBoolean(String variable, boolean defaultValue) {
        return this.console.getPropertyManager().func_73670_a(variable, defaultValue);
    }

    // End Temporary calls

    public String getUpdateFolder() {
        return this.configuration.getString("settings.update-folder", "update");
    }

    public File getUpdateFolderFile() {
        return new File((File) console.options.valueOf("plugins"), this.configuration.getString("settings.update-folder", "update"));
    }

    public int getPingPacketLimit() {
        return this.configuration.getInt("settings.ping-packet-limit", 100);
    }

    public long getConnectionThrottle() {
        return this.configuration.getInt("settings.connection-throttle");
    }

    public int getTicksPerAnimalSpawns() {
        return this.configuration.getInt("ticks-per.animal-spawns");
    }

    public int getTicksPerMonsterSpawns() {
        return this.configuration.getInt("ticks-per.monster-spawns");
    }

    public PluginManager getPluginManager() {
        return pluginManager;
    }

    public CraftScheduler getScheduler() {
        return scheduler;
    }

    public ServicesManager getServicesManager() {
        return servicesManager;
    }

    public List<World> getWorlds() {
        return new ArrayList<World>(worlds.values());
    }

    public net.minecraft.server.dedicated.DedicatedPlayerList getHandle() {
        return playerList;
    }

    // NOTE: Should only be called from DedicatedServer.ah()
    public boolean dispatchServerCommand(CommandSender sender, net.minecraft.command.ServerCommand serverCommand) {
        if (sender instanceof Conversable) {
            Conversable conversable = (Conversable)sender;

            if (conversable.isConversing()) {
                conversable.acceptConversationInput(serverCommand.field_73702_a);
                return true;
            }
        }
        try {
            return dispatchCommand(sender, serverCommand.field_73702_a);
        } catch (Exception ex) {
            getLogger().log(Level.WARNING, "Unexpected exception while parsing console command \"" + serverCommand.field_73702_a + '"', ex);
            return false;
        }
    }

    public boolean dispatchCommand(CommandSender sender, String commandLine) {
        if (commandMap.dispatch(sender, commandLine)) {
            return true;
        }

        sender.sendMessage("Unknown command. Type \"help\" for help.");

        return false;
    }

    public void reload() {
        configuration = YamlConfiguration.loadConfiguration(getConfigFile());
        net.minecraft.server.dedicated.PropertyManager config = new net.minecraft.server.dedicated.PropertyManager(console.options, console.func_98033_al());

        ((net.minecraft.server.dedicated.DedicatedServer) console).field_71340_o = config;

        boolean animals = config.func_73670_a("spawn-animals", console.func_71268_U());
        boolean monsters = config.func_73670_a("spawn-monsters", console.worlds.get(0).field_73013_u > 0);
        int difficulty = config.func_73669_a("difficulty", console.worlds.get(0).field_73013_u);

        online.value = config.func_73670_a("online-mode", console.func_71266_T());
        console.func_71251_e(config.func_73670_a("spawn-animals", console.func_71268_U()));
        console.func_71188_g(config.func_73670_a("pvp", console.func_71219_W()));
        console.func_71245_h(config.func_73670_a("allow-flight", console.func_71231_X()));
        console.func_71205_p(config.func_73671_a("motd", console.func_71273_Y()));
        monsterSpawn = configuration.getInt("spawn-limits.monsters");
        animalSpawn = configuration.getInt("spawn-limits.animals");
        waterAnimalSpawn = configuration.getInt("spawn-limits.water-animals");
        ambientSpawn = configuration.getInt("spawn-limits.ambient");
        warningState = WarningState.value(configuration.getString("settings.deprecated-verbose"));
        console.autosavePeriod = configuration.getInt("ticks-per.autosave");
        chunkGCPeriod = configuration.getInt("chunk-gc.period-in-ticks");
        chunkGCLoadThresh = configuration.getInt("chunk-gc.load-threshold");

        playerList.func_72363_f().func_73707_e();
        playerList.func_72390_e().func_73707_e();

        for (net.minecraft.world.WorldServer world : console.worlds) {
            world.field_73013_u = difficulty;
            world.func_72891_a(monsters, animals);
            if (this.getTicksPerAnimalSpawns() < 0) {
                world.ticksPerAnimalSpawns = 400;
            } else {
                world.ticksPerAnimalSpawns = this.getTicksPerAnimalSpawns();
            }

            if (this.getTicksPerMonsterSpawns() < 0) {
                world.ticksPerMonsterSpawns = 1;
            } else {
                world.ticksPerMonsterSpawns = this.getTicksPerMonsterSpawns();
            }
        }

        pluginManager.clearPlugins();
        commandMap.clearCommands();
        resetRecipes();

        int pollCount = 0;

        // Wait for at most 2.5 seconds for plugins to close their threads
        while (pollCount < 50 && getScheduler().getActiveWorkers().size() > 0) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {}
            pollCount++;
        }

        List<BukkitWorker> overdueWorkers = getScheduler().getActiveWorkers();
        for (BukkitWorker worker : overdueWorkers) {
            Plugin plugin = worker.getOwner();
            String author = "<NoAuthorGiven>";
            if (plugin.getDescription().getAuthors().size() > 0) {
                author = plugin.getDescription().getAuthors().get(0);
            }
            getLogger().log(Level.SEVERE, String.format(
                "Nag author: '%s' of '%s' about the following: %s",
                author,
                plugin.getDescription().getName(),
                "This plugin is not properly shutting down its async tasks when it is being reloaded.  This may cause conflicts with the newly loaded version of the plugin"
            ));
        }
        loadPlugins();
        enablePlugins(PluginLoadOrder.STARTUP);
        enablePlugins(PluginLoadOrder.POSTWORLD);
    }

    @SuppressWarnings({ "unchecked", "finally" })
    private void loadCustomPermissions() {
        File file = new File(configuration.getString("settings.permissions-file"));
        FileInputStream stream;

        try {
            stream = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            try {
                file.createNewFile();
            } finally {
                return;
            }
        }

        Map<String, Map<String, Object>> perms;

        try {
            perms = (Map<String, Map<String, Object>>) yaml.load(stream);
        } catch (MarkedYAMLException ex) {
            getLogger().log(Level.WARNING, "Server permissions file " + file + " is not valid YAML: " + ex.toString());
            return;
        } catch (Throwable ex) {
            getLogger().log(Level.WARNING, "Server permissions file " + file + " is not valid YAML.", ex);
            return;
        } finally {
            try {
                stream.close();
            } catch (IOException ex) {}
        }

        if (perms == null) {
            getLogger().log(Level.INFO, "Server permissions file " + file + " is empty, ignoring it");
            return;
        }

        List<Permission> permsList = Permission.loadPermissions(perms, "Permission node '%s' in " + file + " is invalid", Permission.DEFAULT_PERMISSION);

        for (Permission perm : permsList) {
            try {
                pluginManager.addPermission(perm);
            } catch (IllegalArgumentException ex) {
                getLogger().log(Level.SEVERE, "Permission in " + file + " was already defined", ex);
            }
        }
    }

    @Override
    public String toString() {
        return "CraftServer{" + "serverName=" + serverName + ",serverVersion=" + serverVersion + ",minecraftVersion=" + console.func_71249_w() + '}';
    }

    public World createWorld(String name, World.Environment environment) {
        return WorldCreator.name(name).environment(environment).createWorld();
    }

    public World createWorld(String name, World.Environment environment, long seed) {
        return WorldCreator.name(name).environment(environment).seed(seed).createWorld();
    }

    public World createWorld(String name, Environment environment, ChunkGenerator generator) {
        return WorldCreator.name(name).environment(environment).generator(generator).createWorld();
    }

    public World createWorld(String name, Environment environment, long seed, ChunkGenerator generator) {
        return WorldCreator.name(name).environment(environment).seed(seed).generator(generator).createWorld();
    }

    public World createWorld(WorldCreator creator) {
        if (creator == null) {
            throw new IllegalArgumentException("Creator may not be null");
        }

        String name = creator.name();
        ChunkGenerator generator = creator.generator();
        File folder = new File(getWorldContainer(), name);
        World world = getWorld(name);
        net.minecraft.world.WorldType type = net.minecraft.world.WorldType.func_77130_a(creator.type().getName());
        boolean generateStructures = creator.generateStructures();

        if (world != null) {
            return world;
        }

        if ((folder.exists()) && (!folder.isDirectory())) {
            throw new IllegalArgumentException("File exists with the name '" + name + "' and isn't a folder");
        }

        if (generator == null) {
            generator = getGenerator(name);
        }

        net.minecraft.world.storage.ISaveFormat converter = new net.minecraft.world.chunk.storage.AnvilSaveConverter(getWorldContainer());
        if (converter.func_75801_b(name)) {
            getLogger().info("Converting world '" + name + "'");
            converter.func_75805_a(name, new net.minecraft.server.ConvertingProgressUpdate(console));
        }

        int dimension = CraftWorld.CUSTOM_DIMENSION_OFFSET + console.worlds.size();
        boolean used = false;
        do {
            for (net.minecraft.world.WorldServer server : console.worlds) {
                used = server.dimension == dimension;
                if (used) {
                    dimension++;
                    break;
                }
            }
        } while(used);
        boolean hardcore = false;

        net.minecraft.world.WorldServer internal = new net.minecraft.world.WorldServer(console, new net.minecraft.world.chunk.storage.AnvilSaveHandler(getWorldContainer(), name, true), name, dimension, new net.minecraft.world.WorldSettings(creator.seed(), net.minecraft.world.EnumGameType.func_77146_a(getDefaultGameMode().getValue()), generateStructures, hardcore, type), console.field_71304_b, console.func_98033_al(), creator.environment(), generator);

        if (!(worlds.containsKey(name.toLowerCase()))) {
            return null;
        }

        internal.field_72988_C = console.worlds.get(0).field_72988_C;

        internal.field_73062_L = new net.minecraft.entity.EntityTracker(internal); // CraftBukkit
        internal.func_72954_a((net.minecraft.world.IWorldAccess) new net.minecraft.world.WorldManager(console, internal));
        internal.field_73013_u = 1;
        internal.func_72891_a(true, true);
        console.worlds.add(internal);

        if (generator != null) {
            internal.getWorld().getPopulators().addAll(generator.getDefaultPopulators(internal.getWorld()));
        }

        pluginManager.callEvent(new WorldInitEvent(internal.getWorld()));
        System.out.print("Preparing start region for level " + (console.worlds.size() - 1) + " (Seed: " + internal.func_72905_C() + ")");

        if (internal.getWorld().getKeepSpawnInMemory()) {
            short short1 = 196;
            long i = System.currentTimeMillis();
            for (int j = -short1; j <= short1; j += 16) {
                for (int k = -short1; k <= short1; k += 16) {
                    long l = System.currentTimeMillis();

                    if (l < i) {
                        i = l;
                    }

                    if (l > i + 1000L) {
                        int i1 = (short1 * 2 + 1) * (short1 * 2 + 1);
                        int j1 = (j + short1) * (short1 * 2 + 1) + k + 1;

                        System.out.println("Preparing spawn area for " + name + ", " + (j1 * 100 / i1) + "%");
                        i = l;
                    }

                    net.minecraft.util.ChunkCoordinates chunkcoordinates = internal.func_72861_E();
                    internal.field_73059_b.func_73158_c(chunkcoordinates.field_71574_a + j >> 4, chunkcoordinates.field_71573_c + k >> 4);
                }
            }
        }
        pluginManager.callEvent(new WorldLoadEvent(internal.getWorld()));
        return internal.getWorld();
    }

    public boolean unloadWorld(String name, boolean save) {
        return unloadWorld(getWorld(name), save);
    }

    public boolean unloadWorld(World world, boolean save) {
        if (world == null) {
            return false;
        }

        net.minecraft.world.WorldServer handle = ((CraftWorld) world).getHandle();

        if (!(console.worlds.contains(handle))) {
            return false;
        }

        if (!(handle.dimension > 1)) {
            return false;
        }

        if (handle.field_73010_i.size() > 0) {
            return false;
        }

        WorldUnloadEvent e = new WorldUnloadEvent(handle.getWorld());
        pluginManager.callEvent(e);

        if (e.isCancelled()) {
            return false;
        }

        if (save) {
            try {
                handle.func_73044_a(true, (net.minecraft.util.IProgressUpdate) null);
                handle.func_73041_k();
                WorldSaveEvent event = new WorldSaveEvent(handle.getWorld());
                getPluginManager().callEvent(event);
            } catch (net.minecraft.world.MinecraftException ex) {
                getLogger().log(Level.SEVERE, null, ex);
            }
        }

        worlds.remove(world.getName().toLowerCase());
        console.worlds.remove(console.worlds.indexOf(handle));

        return true;
    }

    public net.minecraft.server.MinecraftServer getServer() {
        return console;
    }

    public World getWorld(String name) {
        return worlds.get(name.toLowerCase());
    }

    public World getWorld(UUID uid) {
        for (World world : worlds.values()) {
            if (world.getUID().equals(uid)) {
                return world;
            }
        }
        return null;
    }

    public void addWorld(World world) {
        // Check if a World already exists with the UID.
        if (getWorld(world.getUID()) != null) {
            System.out.println("World " + world.getName() + " is a duplicate of another world and has been prevented from loading. Please delete the uid.dat file from " + world.getName() + "'s world directory if you want to be able to load the duplicate world.");
            return;
        }
        worlds.put(world.getName().toLowerCase(), world);
    }

    public Logger getLogger() {
        return console.func_98033_al().func_98076_a();
    }

    public ConsoleReader getReader() {
        return console.reader;
    }

    public PluginCommand getPluginCommand(String name) {
        Command command = commandMap.getCommand(name);

        if (command instanceof PluginCommand) {
            return (PluginCommand) command;
        } else {
            return null;
        }
    }

    public void savePlayers() {
        playerList.func_72389_g();
    }

    public void configureDbConfig(ServerConfig config) {
        DataSourceConfig ds = new DataSourceConfig();
        ds.setDriver(configuration.getString("database.driver"));
        ds.setUrl(configuration.getString("database.url"));
        ds.setUsername(configuration.getString("database.username"));
        ds.setPassword(configuration.getString("database.password"));
        ds.setIsolationLevel(TransactionIsolation.getLevel(configuration.getString("database.isolation")));

        if (ds.getDriver().contains("sqlite")) {
            config.setDatabasePlatform(new SQLitePlatform());
            config.getDatabasePlatform().getDbDdlSyntax().setIdentity("");
        }

        config.setDataSourceConfig(ds);
    }

    public boolean addRecipe(Recipe recipe) {
        CraftRecipe toAdd;
        if (recipe instanceof CraftRecipe) {
            toAdd = (CraftRecipe) recipe;
        } else {
            if (recipe instanceof ShapedRecipe) {
                toAdd = CraftShapedRecipe.fromBukkitRecipe((ShapedRecipe) recipe);
            } else if (recipe instanceof ShapelessRecipe) {
                toAdd = CraftShapelessRecipe.fromBukkitRecipe((ShapelessRecipe) recipe);
            } else if (recipe instanceof FurnaceRecipe) {
                toAdd = CraftFurnaceRecipe.fromBukkitRecipe((FurnaceRecipe) recipe);
            } else {
                return false;
            }
        }
        toAdd.addToCraftingManager();
        net.minecraft.item.crafting.CraftingManager.func_77594_a().sort();
        return true;
    }

    public List<Recipe> getRecipesFor(ItemStack result) {
        List<Recipe> results = new ArrayList<Recipe>();
        Iterator<Recipe> iter = recipeIterator();
        while (iter.hasNext()) {
            Recipe recipe = iter.next();
            ItemStack stack = recipe.getResult();
            if (stack.getType() != result.getType()) {
                continue;
            }
            if (result.getDurability() == -1 || result.getDurability() == stack.getDurability()) {
                results.add(recipe);
            }
        }
        return results;
    }

    public Iterator<Recipe> recipeIterator() {
        return new RecipeIterator();
    }

    public void clearRecipes() {
        net.minecraft.item.crafting.CraftingManager.func_77594_a().field_77597_b.clear();
        net.minecraft.item.crafting.FurnaceRecipes.func_77602_a().field_77604_b.clear();
    }

    public void resetRecipes() {
        net.minecraft.item.crafting.CraftingManager.func_77594_a().field_77597_b = new net.minecraft.item.crafting.CraftingManager().field_77597_b;
        net.minecraft.item.crafting.FurnaceRecipes.func_77602_a().field_77604_b = new net.minecraft.item.crafting.FurnaceRecipes().field_77604_b;
    }

    public Map<String, String[]> getCommandAliases() {
        ConfigurationSection section = configuration.getConfigurationSection("aliases");
        Map<String, String[]> result = new LinkedHashMap<String, String[]>();

        if (section != null) {
            for (String key : section.getKeys(false)) {
                List<String> commands = null;

                if (section.isList(key)) {
                    commands = section.getStringList(key);
                } else {
                    commands = ImmutableList.<String>of(section.getString(key));
                }

                result.put(key, commands.toArray(new String[commands.size()]));
            }
        }

        return result;
    }

    public void removeBukkitSpawnRadius() {
        configuration.set("settings.spawn-radius", null);
        saveConfig();
    }

    public int getBukkitSpawnRadius() {
        return configuration.getInt("settings.spawn-radius", -1);
    }

    public String getShutdownMessage() {
        return configuration.getString("settings.shutdown-message");
    }

    public int getSpawnRadius() {
        return ((net.minecraft.server.dedicated.DedicatedServer) console).field_71340_o.func_73669_a("spawn-protection", 16);
    }

    public void setSpawnRadius(int value) {
        configuration.set("settings.spawn-radius", value);
        saveConfig();
    }

    public boolean getOnlineMode() {
        return online.value;
    }

    public boolean getAllowFlight() {
        return console.func_71231_X();
    }

    public boolean isHardcore() {
        return console.func_71199_h();
    }

    public boolean useExactLoginLocation() {
        return configuration.getBoolean("settings.use-exact-login-location");
    }

    public ChunkGenerator getGenerator(String world) {
        ConfigurationSection section = configuration.getConfigurationSection("worlds");
        ChunkGenerator result = null;

        if (section != null) {
            section = section.getConfigurationSection(world);

            if (section != null) {
                String name = section.getString("generator");

                if ((name != null) && (!name.equals(""))) {
                    String[] split = name.split(":", 2);
                    String id = (split.length > 1) ? split[1] : null;
                    Plugin plugin = pluginManager.getPlugin(split[0]);

                    if (plugin == null) {
                        getLogger().severe("Could not set generator for default world '" + world + "': Plugin '" + split[0] + "' does not exist");
                    } else if (!plugin.isEnabled()) {
                        getLogger().severe("Could not set generator for default world '" + world + "': Plugin '" + split[0] + "' is not enabled yet (is it load:STARTUP?)");
                    } else {
                        result = plugin.getDefaultWorldGenerator(world, id);
                    }
                }
            }
        }

        return result;
    }

    public CraftMapView getMap(short id) {
        net.minecraft.world.storage.MapStorage collection = console.worlds.get(0).field_72988_C;
        net.minecraft.world.storage.MapData worldmap = (net.minecraft.world.storage.MapData) collection.func_75742_a(net.minecraft.world.storage.MapData.class, "map_" + id);
        if (worldmap == null) {
            return null;
        }
        return worldmap.mapView;
    }

    public CraftMapView createMap(World world) {
        net.minecraft.item.ItemStack stack = new net.minecraft.item.ItemStack(net.minecraft.item.Item.field_77744_bd, 1, -1);
        net.minecraft.world.storage.MapData worldmap = net.minecraft.item.Item.field_77744_bd.func_77873_a(stack, ((CraftWorld) world).getHandle());
        return worldmap.mapView;
    }

    public void shutdown() {
        console.func_71263_m();
    }

    public int broadcast(String message, String permission) {
        int count = 0;
        Set<Permissible> permissibles = getPluginManager().getPermissionSubscriptions(permission);

        for (Permissible permissible : permissibles) {
            if (permissible instanceof CommandSender && permissible.hasPermission(permission)) {
                CommandSender user = (CommandSender) permissible;
                user.sendMessage(message);
                count++;
            }
        }

        return count;
    }

    public OfflinePlayer getOfflinePlayer(String name) {
        return getOfflinePlayer(name, true);
    }

    public OfflinePlayer getOfflinePlayer(String name, boolean search) {
        OfflinePlayer result = getPlayerExact(name);
        String lname = name.toLowerCase();

        if (result == null) {
            result = offlinePlayers.get(lname);

            if (result == null) {
                if (search) {
                    net.minecraft.world.storage.SaveHandler storage = (net.minecraft.world.storage.SaveHandler) console.worlds.get(0).func_72860_G();
                    for (String dat : storage.getPlayerDir().list(new DatFileFilter())) {
                        String datName = dat.substring(0, dat.length() - 4);
                        if (datName.equalsIgnoreCase(name)) {
                            name = datName;
                            break;
                        }
                    }
                }

                result = new CraftOfflinePlayer(this, name);
                offlinePlayers.put(lname, result);
            }
        } else {
            offlinePlayers.remove(lname);
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    public Set<String> getIPBans() {
        return playerList.func_72363_f().func_73712_c().keySet();
    }

    public void banIP(String address) {
        net.minecraft.server.management.BanEntry entry = new net.minecraft.server.management.BanEntry(address);
        playerList.func_72363_f().func_73706_a(entry);
        playerList.func_72363_f().func_73711_f();
    }

    public void unbanIP(String address) {
        playerList.func_72363_f().func_73709_b(address);
        playerList.func_72363_f().func_73711_f();
    }

    public Set<OfflinePlayer> getBannedPlayers() {
        Set<OfflinePlayer> result = new HashSet<OfflinePlayer>();

        for (Object name : playerList.func_72390_e().func_73712_c().keySet()) {
            result.add(getOfflinePlayer((String) name));
        }

        return result;
    }

    public void setWhitelist(boolean value) {
        playerList.field_72409_l = value;
        console.getPropertyManager().func_73667_a("white-list", value);
    }

    public Set<OfflinePlayer> getWhitelistedPlayers() {
        Set<OfflinePlayer> result = new LinkedHashSet<OfflinePlayer>();

        for (Object name : playerList.func_72388_h()) {
            if (((String)name).length() == 0 || ((String)name).startsWith("#")) {
                continue;
            }
            result.add(getOfflinePlayer((String) name));
        }

        return result;
    }

    public Set<OfflinePlayer> getOperators() {
        Set<OfflinePlayer> result = new HashSet<OfflinePlayer>();

        for (Object name : playerList.func_72376_i()) {
            result.add(getOfflinePlayer((String) name));
        }

        return result;
    }

    public void reloadWhitelist() {
        playerList.func_72362_j();
    }

    public GameMode getDefaultGameMode() {
        return GameMode.getByValue(console.worlds.get(0).func_72912_H().func_76077_q().func_77148_a());
    }

    public void setDefaultGameMode(GameMode mode) {
        if (mode == null) {
            throw new IllegalArgumentException("Mode cannot be null");
        }

        for (World world : getWorlds()) {
            ((CraftWorld) world).getHandle().field_72986_A.func_76060_a(net.minecraft.world.EnumGameType.func_77146_a(mode.getValue()));
        }
    }

    public ConsoleCommandSender getConsoleSender() {
        return console.console;
    }

    public EntityMetadataStore getEntityMetadata() {
        return entityMetadata;
    }

    public PlayerMetadataStore getPlayerMetadata() {
        return playerMetadata;
    }

    public WorldMetadataStore getWorldMetadata() {
        return worldMetadata;
    }

    public void detectListNameConflict(net.minecraft.entity.player.EntityPlayerMP entityPlayer) {
        // Collisions will make for invisible people
        for (int i = 0; i < getHandle().field_72404_b.size(); ++i) {
            net.minecraft.entity.player.EntityPlayerMP testEntityPlayer = (net.minecraft.entity.player.EntityPlayerMP) getHandle().field_72404_b.get(i);

            // We have a problem!
            if (testEntityPlayer != entityPlayer && testEntityPlayer.listName.equals(entityPlayer.listName)) {
                String oldName = entityPlayer.listName;
                int spaceLeft = 16 - oldName.length();

                if (spaceLeft <= 1) { // We also hit the list name length limit!
                    entityPlayer.listName = oldName.subSequence(0, oldName.length() - 2 - spaceLeft) + String.valueOf(System.currentTimeMillis() % 99);
                } else {
                    entityPlayer.listName = oldName + String.valueOf(System.currentTimeMillis() % 99);
                }

                return;
            }
        }
    }

    public File getWorldContainer() {
        if (this.getServer().field_71308_o != null) {
            return this.getServer().field_71308_o;
        }

        if (container == null) {
            container = new File(configuration.getString("settings.world-container", "."));
        }

        return container;
    }

    public OfflinePlayer[] getOfflinePlayers() {
        net.minecraft.world.storage.SaveHandler storage = (net.minecraft.world.storage.SaveHandler) console.worlds.get(0).func_72860_G();
        String[] files = storage.getPlayerDir().list(new DatFileFilter());
        Set<OfflinePlayer> players = new HashSet<OfflinePlayer>();

        for (String file : files) {
            players.add(getOfflinePlayer(file.substring(0, file.length() - 4), false));
        }
        players.addAll(Arrays.asList(getOnlinePlayers()));

        return players.toArray(new OfflinePlayer[players.size()]);
    }

    public Messenger getMessenger() {
        return messenger;
    }

    public void sendPluginMessage(Plugin source, String channel, byte[] message) {
        StandardMessenger.validatePluginMessage(getMessenger(), source, channel, message);

        for (Player player : getOnlinePlayers()) {
            player.sendPluginMessage(source, channel, message);
        }
    }

    public Set<String> getListeningPluginChannels() {
        Set<String> result = new HashSet<String>();

        for (Player player : getOnlinePlayers()) {
            result.addAll(player.getListeningPluginChannels());
        }

        return result;
    }

    public void onPlayerJoin(Player player) {
        if ((updater.isEnabled()) && (updater.getCurrent() != null) && (player.hasPermission(Server.BROADCAST_CHANNEL_ADMINISTRATIVE))) {
            if ((updater.getCurrent().isBroken()) && (updater.getOnBroken().contains(updater.WARN_OPERATORS))) {
                player.sendMessage(ChatColor.DARK_RED + "The version of CraftBukkit that this server is running is known to be broken. Please consider updating to the latest version at dl.bukkit.org.");
            } else if ((updater.isUpdateAvailable()) && (updater.getOnUpdate().contains(updater.WARN_OPERATORS))) {
                player.sendMessage(ChatColor.DARK_PURPLE + "The version of CraftBukkit that this server is running is out of date. Please consider updating to the latest version at dl.bukkit.org.");
            }
        }
    }

    public Inventory createInventory(InventoryHolder owner, InventoryType type) {
        // TODO: Create the appropriate type, rather than Custom?
        return new CraftInventoryCustom(owner, type);
    }

    public Inventory createInventory(InventoryHolder owner, int size) throws IllegalArgumentException {
        Validate.isTrue(size % 9 == 0, "Chests must have a size that is a multiple of 9!");
        return new CraftInventoryCustom(owner, size);
    }

    public Inventory createInventory(InventoryHolder owner, int size, String title) throws IllegalArgumentException {
        Validate.isTrue(size % 9 == 0, "Chests must have a size that is a multiple of 9!");
        return new CraftInventoryCustom(owner, size, title);
    }

    public HelpMap getHelpMap() {
        return helpMap;
    }

    public SimpleCommandMap getCommandMap() {
        return commandMap;
    }

    public int getMonsterSpawnLimit() {
        return monsterSpawn;
    }

    public int getAnimalSpawnLimit() {
        return animalSpawn;
    }

    public int getWaterAnimalSpawnLimit() {
        return waterAnimalSpawn;
    }

    public int getAmbientSpawnLimit() {
        return ambientSpawn;
    }

    public boolean isPrimaryThread() {
        return Thread.currentThread().equals(console.primaryThread);
    }

    public String getMotd() {
        return console.func_71273_Y();
    }

    public WarningState getWarningState() {
        return warningState;
    }

    public List<String> tabComplete(net.minecraft.command.ICommandSender sender, String message) {
        if (!(sender instanceof net.minecraft.entity.player.EntityPlayerMP)) {
            return ImmutableList.of();
        }

        Player player = ((net.minecraft.entity.player.EntityPlayerMP) sender).getBukkitEntity();
        if (message.startsWith("/")) {
            return tabCompleteCommand(player, message);
        } else {
            return tabCompleteChat(player, message);
        }
    }

    public List<String> tabCompleteCommand(Player player, String message) {
        List<String> completions = null;
        try {
            completions = getCommandMap().tabComplete(player, message.substring(1));
        } catch (CommandException ex) {
            player.sendMessage(ChatColor.RED + "An internal error occurred while attempting to tab-complete this command");
            getLogger().log(Level.SEVERE, "Exception when " + player.getName() + " attempted to tab complete " + message, ex);
        }

        return completions == null ? ImmutableList.<String>of() : completions;
    }

    public List<String> tabCompleteChat(Player player, String message) {
        Player[] players = getOnlinePlayers();
        List<String> completions = new ArrayList<String>();
        PlayerChatTabCompleteEvent event = new PlayerChatTabCompleteEvent(player, message, completions);
        String token = event.getLastToken();
        for (Player p : players) {
            if (player.canSee(p) && StringUtil.startsWithIgnoreCase(p.getName(), token)) {
                completions.add(p.getName());
            }
        }
        pluginManager.callEvent(event);

        Iterator<?> it = completions.iterator();
        while (it.hasNext()) {
            Object current = it.next();
            if (!(current instanceof String)) {
                // Sanity
                it.remove();
            }
        }
        Collections.sort(completions, String.CASE_INSENSITIVE_ORDER);
        return completions;
    }

    public CraftItemFactory getItemFactory() {
        return CraftItemFactory.instance();
    }
}
