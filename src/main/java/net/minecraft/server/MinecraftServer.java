package net.minecraft.server;

import java.awt.GraphicsEnvironment;
import java.io.File;
import java.security.KeyPair;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.ServerCommand;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.crash.CrashReport;
import net.minecraft.dispenser.DispenserBehaviors;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.logging.ILogAgent;
import net.minecraft.network.NetworkListenThread;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet4UpdateTime;
import net.minecraft.network.rcon.RConConsoleSource;
import net.minecraft.profiler.IPlayerUsage;
import net.minecraft.profiler.PlayerUsageSnooper;
import net.minecraft.profiler.Profiler;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.PropertyManager;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.util.StringTranslate;
import net.minecraft.util.StringUtils;
import net.minecraft.world.EnumGameType;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.World;
import net.minecraft.world.WorldManager;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldServerMulti;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.storage.AnvilSaveHandler;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;

// CraftBukkit start
import java.util.concurrent.ExecutionException;
import java.io.IOException;

import com.google.common.io.Files;
import jline.console.ConsoleReader;
import joptsimple.OptionSet;

import org.bukkit.World.Environment;
import org.bukkit.craftbukkit.util.Waitable;
import org.bukkit.event.server.RemoteServerCommandEvent;
import org.bukkit.event.world.WorldSaveEvent;
// CraftBukkit end

public abstract class MinecraftServer implements ICommandSender, Runnable, IPlayerUsage
{
    private static MinecraftServer field_71309_l = null;
    public ISaveFormat field_71310_m; // CraftBukkit - private final -> public
    private final PlayerUsageSnooper field_71307_n = new PlayerUsageSnooper("server", this);
    public File field_71308_o; // CraftBukkit - private final -> public
    private final List field_71322_p = new ArrayList();
    private final ICommandManager field_71321_q;
    public final Profiler field_71304_b = new Profiler();
    private String field_71320_r;
    private int field_71319_s = -1;
    public WorldServer[] field_71305_c;
    private ServerConfigurationManager field_71318_t;
    private boolean field_71317_u = true;
    private boolean field_71316_v = false;
    private int field_71315_w = 0;
    public String field_71302_d;
    public int field_71303_e;
    private boolean field_71325_x;
    private boolean field_71324_y;
    private boolean field_71323_z;
    private boolean field_71284_A;
    private boolean field_71285_B;
    private String field_71286_C;
    private int field_71280_D;
    private long field_71281_E;
    private long field_71282_F;
    private long field_71283_G;
    private long field_71291_H;
    public final long[] field_71300_f = new long[100];
    public final long[] field_71301_g = new long[100];
    public final long[] field_71313_h = new long[100];
    public final long[] field_71314_i = new long[100];
    public final long[] field_71311_j = new long[100];
    public long[][] field_71312_k;
    private KeyPair field_71292_I;
    private String field_71293_J;
    private String field_71294_K;
    private boolean field_71288_M;
    private boolean field_71289_N;
    private boolean field_71290_O;
    private String field_71297_P = "";
    private boolean field_71296_Q = false;
    private long field_71299_R;
    private String field_71298_S;
    private boolean field_71295_T;

    // CraftBukkit start
    public List<WorldServer> worlds = new ArrayList<WorldServer>();
    public org.bukkit.craftbukkit.CraftServer server;
    public OptionSet options;
    public org.bukkit.command.ConsoleCommandSender console;
    public org.bukkit.command.RemoteConsoleCommandSender remoteConsole;
    public ConsoleReader reader;
    public static int currentTick;
    public final Thread primaryThread;
    public java.util.Queue<Runnable> processQueue = new java.util.concurrent.ConcurrentLinkedQueue<Runnable>();
    public int autosavePeriod;
    // CraftBukkit end

    public MinecraftServer(OptionSet options)   // CraftBukkit - signature file -> OptionSet
    {
        field_71309_l = this;
        // this.universe = file1; // CraftBukkit
        this.field_71321_q = new ServerCommandManager();
        // this.convertable = new WorldLoaderServer(server.getWorldContainer()); // CraftBukkit - moved to DedicatedServer.init
        this.func_82355_al();
        // CraftBukkit start
        this.options = options;

        try
        {
            this.reader = new ConsoleReader(System.in, System.out);
            this.reader.setExpandEvents(false); // Avoid parsing exceptions for uncommonly used event designators
        }
        catch (Exception e)
        {
            try
            {
                // Try again with jline disabled for Windows users without C++ 2008 Redistributable
                System.setProperty("jline.terminal", "jline.UnsupportedTerminal");
                System.setProperty("user.language", "en");
                org.bukkit.craftbukkit.Main.useJline = false;
                this.reader = new ConsoleReader(System.in, System.out);
                this.reader.setExpandEvents(false);
            }
            catch (java.io.IOException ex)
            {
                Logger.getLogger(MinecraftServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        Runtime.getRuntime().addShutdownHook(new org.bukkit.craftbukkit.util.ServerShutdownThread(this));
        primaryThread = new ThreadMinecraftServer(this, "Server thread"); // Moved from main
    }

    public abstract PropertyManager getPropertyManager();
    // CraftBukkit end

    private void func_82355_al()
    {
        DispenserBehaviors.func_96467_a();
    }

    protected abstract boolean func_71197_b() throws java.net.UnknownHostException; // CraftBukkit - throws UnknownHostException

    protected void func_71237_c(String p_71237_1_)
    {
        if (this.func_71254_M().func_75801_b(p_71237_1_))
        {
            this.func_98033_al().func_98233_a("Converting map!");
            this.func_71192_d("menu.convertingLevel");
            this.func_71254_M().func_75805_a(p_71237_1_, new ConvertingProgressUpdate(this));
        }
    }

    protected synchronized void func_71192_d(String p_71192_1_)
    {
        this.field_71298_S = p_71192_1_;
    }

    protected void func_71247_a(String p_71247_1_, String p_71247_2_, long p_71247_3_, WorldType p_71247_5_, String p_71247_6_)
    {
        this.func_71237_c(p_71247_1_);
        this.func_71192_d("menu.loadingLevel");
        this.field_71305_c = new WorldServer[3];
        // CraftBukkit - removed ticktime arrays
        ISaveHandler isavehandler = this.field_71310_m.func_75804_a(p_71247_1_, true);
        WorldInfo worldinfo = isavehandler.func_75757_d();
        // CraftBukkit start - removed worldsettings
        int worldCount = 3;

        for (int j = 0; j < worldCount; ++j)
        {
            WorldServer world;
            int dimension = 0;

            if (j == 1)
            {
                if (this.func_71255_r())
                {
                    dimension = -1;
                }
                else
                {
                    continue;
                }
            }

            if (j == 2)
            {
                if (this.server.getAllowEnd())
                {
                    dimension = 1;
                }
                else
                {
                    continue;
                }
            }

            String worldType = Environment.getEnvironment(dimension).toString().toLowerCase();
            String name = (dimension == 0) ? p_71247_1_ : p_71247_1_ + "_" + worldType;
            org.bukkit.generator.ChunkGenerator gen = this.server.getGenerator(name);
            WorldSettings worldsettings = new WorldSettings(p_71247_3_, this.func_71265_f(), this.func_71225_e(), this.func_71199_h(), p_71247_5_);
            worldsettings.func_82750_a(p_71247_6_);

            if (j == 0)
            {
                if (this.func_71242_L())   // Strip out DEMO?
                {
                    // CraftBukkit
                    world = new DemoWorldServer(this, new AnvilSaveHandler(server.getWorldContainer(), p_71247_2_, true), p_71247_2_, dimension, this.field_71304_b, this.func_98033_al());
                }
                else
                {
                    // CraftBukkit
                    world = new WorldServer(this, new AnvilSaveHandler(server.getWorldContainer(), p_71247_2_, true), p_71247_2_, dimension, worldsettings, this.field_71304_b, this.func_98033_al(), Environment.getEnvironment(dimension), gen);
                }
            }
            else
            {
                String dim = "DIM" + dimension;
                File newWorld = new File(new File(name), dim);
                File oldWorld = new File(new File(p_71247_1_), dim);

                if ((!newWorld.isDirectory()) && (oldWorld.isDirectory()))
                {
                    final ILogAgent log = this.func_98033_al();
                    log.func_98233_a("---- Migration of old " + worldType + " folder required ----");
                    log.func_98233_a("Unfortunately due to the way that Minecraft implemented multiworld support in 1.6, Bukkit requires that you move your " + worldType + " folder to a new location in order to operate correctly.");
                    log.func_98233_a("We will move this folder for you, but it will mean that you need to move it back should you wish to stop using Bukkit in the future.");
                    log.func_98233_a("Attempting to move " + oldWorld + " to " + newWorld + "...");

                    if (newWorld.exists())
                    {
                        log.func_98232_c("A file or folder already exists at " + newWorld + "!");
                        log.func_98233_a("---- Migration of old " + worldType + " folder failed ----");
                    }
                    else if (newWorld.getParentFile().mkdirs())
                    {
                        if (oldWorld.renameTo(newWorld))
                        {
                            log.func_98233_a("Success! To restore " + worldType + " in the future, simply move " + newWorld + " to " + oldWorld);

                            // Migrate world data too.
                            try
                            {
                                Files.copy(new File(new File(p_71247_1_), "level.dat"), new File(new File(name), "level.dat"));
                            }
                            catch (IOException exception)
                            {
                                log.func_98232_c("Unable to migrate world data.");
                            }

                            log.func_98233_a("---- Migration of old " + worldType + " folder complete ----");
                        }
                        else
                        {
                            log.func_98232_c("Could not move folder " + oldWorld + " to " + newWorld + "!");
                            log.func_98233_a("---- Migration of old " + worldType + " folder failed ----");
                        }
                    }
                    else
                    {
                        log.func_98232_c("Could not create path for " + newWorld + "!");
                        log.func_98233_a("---- Migration of old " + worldType + " folder failed ----");
                    }
                }

                this.func_71192_d(name);
                // CraftBukkit
                world = new WorldServerMulti(this, new AnvilSaveHandler(server.getWorldContainer(), name, true), name, dimension, worldsettings, this.worlds.get(0), this.field_71304_b, this.func_98033_al(), Environment.getEnvironment(dimension), gen);
            }

            if (gen != null)
            {
                world.getWorld().getPopulators().addAll(gen.getDefaultPopulators(world.getWorld()));
            }

            this.server.getPluginManager().callEvent(new org.bukkit.event.world.WorldInitEvent(world.getWorld()));
            world.func_72954_a(new WorldManager(this, world));

            if (!this.func_71264_H())
            {
                world.func_72912_H().func_76060_a(this.func_71265_f());
            }

            this.worlds.add(world);
            this.field_71318_t.func_72364_a(this.worlds.toArray(new WorldServer[this.worlds.size()]));
            // CraftBukkit end
        }

        this.func_71226_c(this.func_71232_g());
        this.func_71222_d();
    }

    protected void func_71222_d()
    {
        long i = System.currentTimeMillis(); // CraftBukkit - current time
        this.func_71192_d("menu.generatingTerrain");
        byte b0 = 0;

        // CraftBukkit start
        for (int j = 0; j < this.worlds.size(); ++j)
        {
            WorldServer worldserver = this.worlds.get(j);
            this.func_98033_al().func_98233_a("Preparing start region for level " + j + " (Seed: " + worldserver.func_72905_C() + ")");

            if (!worldserver.getWorld().getKeepSpawnInMemory())
            {
                continue;
            }

            ChunkCoordinates chunkcoordinates = worldserver.func_72861_E();

            for (int k = -192; k <= 192 && this.func_71278_l(); k += 16)
            {
                for (int l = -192; l <= 192 && this.func_71278_l(); l += 16)
                {
                    long i1 = System.currentTimeMillis();

                    if (i1 < i)
                    {
                        i = i1;
                    }

                    if (i1 > i + 1000L)
                    {
                        int j1 = (192 * 2 + 1) * (192 * 2 + 1);
                        int k1 = (k + 192) * (192 * 2 + 1) + l + 1;
                        this.func_71216_a_("Preparing spawn area", k1 * 100 / j1);
                        i = i1;
                    }

                    worldserver.field_73059_b.func_73158_c(chunkcoordinates.field_71574_a + k >> 4, chunkcoordinates.field_71573_c + l >> 4);
                }
            }
        }

        // CraftBukkit end
        this.func_71243_i();
    }

    public abstract boolean func_71225_e();

    public abstract EnumGameType func_71265_f();

    public abstract int func_71232_g();

    public abstract boolean func_71199_h();

    protected void func_71216_a_(String p_71216_1_, int p_71216_2_)
    {
        this.field_71302_d = p_71216_1_;
        this.field_71303_e = p_71216_2_;
        this.func_98033_al().func_98233_a(p_71216_1_ + ": " + p_71216_2_ + "%");
    }

    protected void func_71243_i()
    {
        this.field_71302_d = null;
        this.field_71303_e = 0;
        this.server.enablePlugins(org.bukkit.plugin.PluginLoadOrder.POSTWORLD); // CraftBukkit
    }

    protected void func_71267_a(boolean p_71267_1_) throws MinecraftException   // CraftBukkit - added throws
    {
        if (!this.field_71290_O)
        {
            // CraftBukkit start
            for (int j = 0; j < this.worlds.size(); ++j)
            {
                WorldServer worldserver = this.worlds.get(j);

                if (worldserver != null)
                {
                    if (!p_71267_1_)
                    {
                        this.func_98033_al().func_98233_a("Saving chunks for level \'" + worldserver.func_72912_H().func_76065_j() + "\'/" + worldserver.field_73011_w.func_80007_l());
                    }

                    worldserver.func_73044_a(true, (IProgressUpdate) null);
                    worldserver.func_73041_k();
                    WorldSaveEvent event = new WorldSaveEvent(worldserver.getWorld());
                    this.server.getPluginManager().callEvent(event);
                }
            }

            // CraftBukkit end
        }
    }

    public void func_71260_j() throws MinecraftException   // CraftBukkit - added throws
    {
        if (!this.field_71290_O)
        {
            this.func_98033_al().func_98233_a("Stopping server");

            // CraftBukkit start
            if (this.server != null)
            {
                this.server.disablePlugins();
            }

            // CraftBukkit end

            if (this.func_71212_ac() != null)
            {
                this.func_71212_ac().func_71744_a();
            }

            if (this.field_71318_t != null)
            {
                this.func_98033_al().func_98233_a("Saving players");
                this.field_71318_t.func_72389_g();
                this.field_71318_t.func_72392_r();
            }

            this.func_98033_al().func_98233_a("Saving worlds");
            this.func_71267_a(false);

            /* CraftBukkit start - handled in saveChunks
            for (int i = 0; i < this.worldServer.length; ++i) {
                WorldServer worldserver = this.worldServer[i];

                worldserver.saveLevel();
            }
            // CraftBukkit end */
            if (this.field_71307_n != null && this.field_71307_n.func_76468_d())
            {
                this.field_71307_n.func_76470_e();
            }
        }
    }

    public String func_71211_k()
    {
        return this.field_71320_r;
    }

    public void func_71189_e(String p_71189_1_)
    {
        this.field_71320_r = p_71189_1_;
    }

    public boolean func_71278_l()
    {
        return this.field_71317_u;
    }

    public void func_71263_m()
    {
        this.field_71317_u = false;
    }

    public void run()
    {
        try
        {
            if (this.func_71197_b())
            {
                long i = System.currentTimeMillis();

                for (long j = 0L; this.field_71317_u; this.field_71296_Q = true)
                {
                    long k = System.currentTimeMillis();
                    long l = k - i;

                    if (l > 2000L && i - this.field_71299_R >= 15000L)
                    {
                        if (this.server.getWarnOnOverload()) // CraftBukkit - Added option to suppress warning messages
                        {
                            this.func_98033_al().func_98236_b("Can\'t keep up! Did the system time change, or is the server overloaded?");
                        }

                        l = 2000L;
                        this.field_71299_R = i;
                    }

                    if (l < 0L)
                    {
                        this.func_98033_al().func_98236_b("Time ran backwards! Did the system time change?");
                        l = 0L;
                    }

                    j += l;
                    i = k;

                    if (this.worlds.get(0).func_73056_e())   // CraftBukkit
                    {
                        this.func_71217_p();
                        j = 0L;
                    }
                    else
                    {
                        while (j > 50L)
                        {
                            MinecraftServer.currentTick = (int)(System.currentTimeMillis() / 50);  // CraftBukkit
                            j -= 50L;
                            this.func_71217_p();
                        }
                    }

                    Thread.sleep(1L);
                }
            }
            else
            {
                this.func_71228_a((CrashReport)null);
            }
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();
            this.func_98033_al().func_98234_c("Encountered an unexpected exception " + throwable.getClass().getSimpleName(), throwable);
            CrashReport crashreport = null;

            if (throwable instanceof ReportedException)
            {
                crashreport = this.func_71230_b(((ReportedException)throwable).func_71575_a());
            }
            else
            {
                crashreport = this.func_71230_b(new CrashReport("Exception in server tick loop", throwable));
            }

            File file1 = new File(new File(this.func_71238_n(), "crash-reports"), "crash-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + "-server.txt");

            if (crashreport.func_71508_a(file1, this.func_98033_al()))
            {
                this.func_98033_al().func_98232_c("This crash report has been saved to: " + file1.getAbsolutePath());
            }
            else
            {
                this.func_98033_al().func_98232_c("We were unable to save this crash report to disk.");
            }

            this.func_71228_a(crashreport);
        }
        finally
        {
            try
            {
                this.func_71260_j();
                this.field_71316_v = true;
            }
            catch (Throwable throwable1)
            {
                throwable1.printStackTrace();
            }
            finally
            {
                // CraftBukkit start - restore terminal to original settings
                try
                {
                    this.reader.getTerminal().restore();
                }
                catch (Exception e)
                {
                }

                // CraftBukkit end
                this.func_71240_o();
            }
        }
    }

    protected File func_71238_n()
    {
        return new File(".");
    }

    protected void func_71228_a(CrashReport p_71228_1_) {}

    protected void func_71240_o() {}

    protected void func_71217_p() throws MinecraftException   // CraftBukkit - added throws
    {
        long i = System.nanoTime();
        AxisAlignedBB.func_72332_a().func_72298_a();
        ++this.field_71315_w;

        if (this.field_71295_T)
        {
            this.field_71295_T = false;
            this.field_71304_b.field_76327_a = true;
            this.field_71304_b.func_76317_a();
        }

        this.field_71304_b.func_76320_a("root");
        this.func_71190_q();

        if ((this.autosavePeriod > 0) && ((this.field_71315_w % this.autosavePeriod) == 0))   // CraftBukkit
        {
            this.field_71304_b.func_76320_a("save");
            this.field_71318_t.func_72389_g();
            this.func_71267_a(true);
            this.field_71304_b.func_76319_b();
        }

        this.field_71304_b.func_76320_a("tallying");
        this.field_71311_j[this.field_71315_w % 100] = System.nanoTime() - i;
        this.field_71300_f[this.field_71315_w % 100] = Packet.field_73290_p - this.field_71281_E;
        this.field_71281_E = Packet.field_73290_p;
        this.field_71301_g[this.field_71315_w % 100] = Packet.field_73289_q - this.field_71282_F;
        this.field_71282_F = Packet.field_73289_q;
        this.field_71313_h[this.field_71315_w % 100] = Packet.field_73292_n - this.field_71283_G;
        this.field_71283_G = Packet.field_73292_n;
        this.field_71314_i[this.field_71315_w % 100] = Packet.field_73293_o - this.field_71291_H;
        this.field_71291_H = Packet.field_73293_o;
        this.field_71304_b.func_76319_b();
        this.field_71304_b.func_76320_a("snooper");

        if (!this.field_71307_n.func_76468_d() && this.field_71315_w > 100)
        {
            this.field_71307_n.func_76463_a();
        }

        if (this.field_71315_w % 6000 == 0)
        {
            this.field_71307_n.func_76471_b();
        }

        this.field_71304_b.func_76319_b();
        this.field_71304_b.func_76319_b();
    }

    public void func_71190_q()
    {
        this.field_71304_b.func_76320_a("levels");
        // CraftBukkit start - only send timeupdates to the people in that world
        this.server.getScheduler().mainThreadHeartbeat(this.field_71315_w);

        // Run tasks that are waiting on processing
        while (!processQueue.isEmpty())
        {
            processQueue.remove().run();
        }

        org.bukkit.craftbukkit.chunkio.ChunkIOExecutor.tick();

        // Send timeupdates to everyone, it will get the right time from the world the player is in.
        if (this.field_71315_w % 20 == 0)
        {
            for (int i = 0; i < this.func_71203_ab().field_72404_b.size(); ++i)
            {
                EntityPlayerMP entityplayermp = (EntityPlayerMP) this.func_71203_ab().field_72404_b.get(i);
                entityplayermp.field_71135_a.func_72567_b(new Packet4UpdateTime(entityplayermp.field_70170_p.func_82737_E(), entityplayermp.getPlayerTime())); // Add support for per player time
            }
        }

        int i;

        for (i = 0; i < this.worlds.size(); ++i)
        {
            long j = System.nanoTime();
            // if (i == 0 || this.getAllowNether()) {
            WorldServer worldserver = this.worlds.get(i);
            this.field_71304_b.func_76320_a(worldserver.func_72912_H().func_76065_j());
            this.field_71304_b.func_76320_a("pools");
            worldserver.func_82732_R().func_72343_a();
            this.field_71304_b.func_76319_b();
            /* Drop global time updates
            if (this.ticks % 20 == 0) {
                this.methodProfiler.a("timeSync");
                this.s.a(new Packet4UpdateTime(worldserver.getTime(), worldserver.getDayTime()), worldserver.worldProvider.dimension);
                this.methodProfiler.b();
            }
            // CraftBukkit end */
            this.field_71304_b.func_76320_a("tick");
            CrashReport crashreport;

            try
            {
                worldserver.func_72835_b();
            }
            catch (Throwable throwable)
            {
                crashreport = CrashReport.func_85055_a(throwable, "Exception ticking world");
                worldserver.func_72914_a(crashreport);
                throw new ReportedException(crashreport);
            }

            try
            {
                worldserver.func_72939_s();
            }
            catch (Throwable throwable1)
            {
                crashreport = CrashReport.func_85055_a(throwable1, "Exception ticking world entities");
                worldserver.func_72914_a(crashreport);
                throw new ReportedException(crashreport);
            }

            this.field_71304_b.func_76319_b();
            this.field_71304_b.func_76320_a("tracker");
            worldserver.func_73039_n().func_72788_a();
            this.field_71304_b.func_76319_b();
            this.field_71304_b.func_76319_b();
            // } // CraftBukkit
            // this.j[i][this.ticks % 100] = System.nanoTime() - j; // CraftBukkit
        }

        this.field_71304_b.func_76318_c("connection");
        this.func_71212_ac().func_71747_b();
        this.field_71304_b.func_76318_c("players");
        this.field_71318_t.func_72374_b();
        this.field_71304_b.func_76318_c("tickables");

        for (i = 0; i < this.field_71322_p.size(); ++i)
        {
            ((IUpdatePlayerListBox)this.field_71322_p.get(i)).func_73660_a();
        }

        this.field_71304_b.func_76319_b();
    }

    public boolean func_71255_r()
    {
        return true;
    }

    public void func_82010_a(IUpdatePlayerListBox p_82010_1_)
    {
        this.field_71322_p.add(p_82010_1_);
    }

    public static void main(final OptionSet options)   // CraftBukkit - replaces main(String[] astring)
    {
        StatList.func_75919_a();
        ILogAgent ilogagent = null;

        try
        {
            /* CraftBukkit start - replace everything
            boolean flag = false;
            String s = null;
            String s1 = ".";
            String s2 = null;
            boolean flag1 = false;
            boolean flag2 = false;
            int i = -1;

            for (int j = 0; j < astring.length; ++j) {
                String s3 = astring[j];
                String s4 = j == astring.length - 1 ? null : astring[j + 1];
                boolean flag3 = false;

                if (!s3.equals("nogui") && !s3.equals("--nogui")) {
                    if (s3.equals("--port") && s4 != null) {
                        flag3 = true;

                        try {
                            i = Integer.parseInt(s4);
                        } catch (NumberFormatException numberformatexception) {
                            ;
                        }
                    } else if (s3.equals("--singleplayer") && s4 != null) {
                        flag3 = true;
                        s = s4;
                    } else if (s3.equals("--universe") && s4 != null) {
                        flag3 = true;
                        s1 = s4;
                    } else if (s3.equals("--world") && s4 != null) {
                        flag3 = true;
                        s2 = s4;
                    } else if (s3.equals("--demo")) {
                        flag1 = true;
                    } else if (s3.equals("--bonusChest")) {
                        flag2 = true;
                    }
                } else {
                    flag = false;
                }

                if (flag3) {
                    ++j;
                }
            }
            // */
            DedicatedServer dedicatedserver = new DedicatedServer(options);
            ilogagent = dedicatedserver.func_98033_al();

            if (options.has("port"))
            {
                int port = (Integer) options.valueOf("port");

                if (port > 0)
                {
                    dedicatedserver.func_71208_b(port);
                }
            }

            if (options.has("universe"))
            {
                dedicatedserver.field_71308_o = (File) options.valueOf("universe");
            }

            if (options.has("world"))
            {
                dedicatedserver.func_71261_m((String) options.valueOf("world"));
            }

            /*
            if (s != null) {
                dedicatedserver.k(s);
            }

            if (s2 != null) {
                dedicatedserver.l(s2);
            }

            if (i >= 0) {
                dedicatedserver.setPort(i);
            }

            if (flag1) {
                dedicatedserver.b(true);
            }

            if (flag2) {
                dedicatedserver.c(true);
            }

            if (flag) {
                dedicatedserver.ao();
            }
            */
            dedicatedserver.primaryThread.start();
            // Runtime.getRuntime().addShutdownHook(new ThreadShutdown(dedicatedserver));
            // CraftBukkit end
        }
        catch (Exception exception)
        {
            if (ilogagent != null)
            {
                ilogagent.func_98234_c("Failed to start the minecraft server", exception);
            }
            else
            {
                Logger.getAnonymousLogger().log(Level.SEVERE, "Failed to start the minecraft server", exception);
            }
        }
    }

    public void func_71256_s()
    {
        // (new ThreadServerApplication(this, "Server thread")).start(); // CraftBukkit - prevent abuse
    }

    public File func_71209_f(String p_71209_1_)
    {
        return new File(this.func_71238_n(), p_71209_1_);
    }

    public void func_71244_g(String p_71244_1_)
    {
        this.func_98033_al().func_98233_a(p_71244_1_);
    }

    public void func_71236_h(String p_71236_1_)
    {
        this.func_98033_al().func_98236_b(p_71236_1_);
    }

    public WorldServer func_71218_a(int p_71218_1_)
    {
        // CraftBukkit start
        for (WorldServer world : this.worlds)
        {
            if (world.dimension == p_71218_1_)
            {
                return world;
            }
        }

        return this.worlds.get(0);
        // CraftBukkit end
    }

    public String func_71277_t()
    {
        return this.field_71320_r;
    }

    public int func_71234_u()
    {
        return this.field_71319_s;
    }

    public String func_71274_v()
    {
        return this.field_71286_C;
    }

    public String func_71249_w()
    {
        return "1.5";
    }

    public int func_71233_x()
    {
        return this.field_71318_t.func_72394_k();
    }

    public int func_71275_y()
    {
        return this.field_71318_t.func_72352_l();
    }

    public String[] func_71213_z()
    {
        return this.field_71318_t.func_72369_d();
    }

    public String func_71258_A()
    {
        // CraftBukkit start - whole method
        StringBuilder result = new StringBuilder();
        org.bukkit.plugin.Plugin[] plugins = server.getPluginManager().getPlugins();
        result.append(server.getName());
        result.append(" on Bukkit ");
        result.append(server.getBukkitVersion());

        if (plugins.length > 0 && this.server.getQueryPlugins())
        {
            result.append(": ");

            for (int i = 0; i < plugins.length; i++)
            {
                if (i > 0)
                {
                    result.append("; ");
                }

                result.append(plugins[i].getDescription().getName());
                result.append(" ");
                result.append(plugins[i].getDescription().getVersion().replaceAll(";", ","));
            }
        }

        return result.toString();
        // CraftBukkit end
    }

    // CraftBukkit start
    public String func_71252_i(final String p_71252_1_)   // CraftBukkit - final parameter
    {
        Waitable<String> waitable = new Waitable<String>()
        {
            @Override
            protected String evaluate()
            {
                RConConsoleSource.field_70010_a.func_70007_b();
                // Event changes start
                RemoteServerCommandEvent event = new RemoteServerCommandEvent(MinecraftServer.this.remoteConsole, p_71252_1_);
                MinecraftServer.this.server.getPluginManager().callEvent(event);
                // Event changes end
                ServerCommand servercommand = new ServerCommand(event.getCommand(), RConConsoleSource.field_70010_a);
                // this.p.a(RemoteControlCommandListener.instance, s);
                MinecraftServer.this.server.dispatchServerCommand(MinecraftServer.this.remoteConsole, servercommand); // CraftBukkit
                return RConConsoleSource.field_70010_a.func_70008_c();
            }
        };
        processQueue.add(waitable);

        try
        {
            return waitable.get();
        }
        catch (ExecutionException e)
        {
            throw new RuntimeException("Exception processing rcon command " + p_71252_1_, e.getCause());
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt(); // Maintain interrupted state
            throw new RuntimeException("Interrupted processing rcon command " + p_71252_1_, e);
        }

        // CraftBukkit end
    }

    public boolean func_71239_B()
    {
        return this.getPropertyManager().func_73670_a("debug", false); // CraftBukkit - don't hardcode
    }

    public void func_71201_j(String p_71201_1_)
    {
        this.func_98033_al().func_98232_c(p_71201_1_);
    }

    public void func_71198_k(String p_71198_1_)
    {
        if (this.func_71239_B())
        {
            this.func_98033_al().func_98233_a(p_71198_1_);
        }
    }

    public String getServerModName()
    {
        return "craftbukkit"; // CraftBukkit - cb > vanilla!
    }

    public CrashReport func_71230_b(CrashReport p_71230_1_)
    {
        p_71230_1_.func_85056_g().func_71500_a("Profiler Position", (Callable)(new CallableIsServerModded(this)));

        if (this.worlds != null && this.worlds.size() > 0 && this.worlds.get(0) != null)
        {
            p_71230_1_.func_85056_g().func_71500_a("Vec3 Pool Size", (Callable)(new CallableServerProfiler(this)));
        }

        if (this.field_71318_t != null)
        {
            p_71230_1_.func_85056_g().func_71500_a("Player Count", (Callable)(new CallableServerMemoryStats(this)));
        }

        return p_71230_1_;
    }

    public List func_71248_a(ICommandSender p_71248_1_, String p_71248_2_)
    {
        // CraftBukkit start - Allow tab-completion of Bukkit commands
        /*
        ArrayList arraylist = new ArrayList();

        if (s.startsWith("/")) {
            s = s.substring(1);
            boolean flag = !s.contains(" ");
            List list = this.p.b(icommandlistener, s);

            if (list != null) {
                Iterator iterator = list.iterator();

                while (iterator.hasNext()) {
                    String s1 = (String)iterator.next();

                    if (flag) {
                        arraylist.add("/" + s1);
                    } else {
                        arraylist.add(s1);
                    }
                }
            }

            return arraylist;
        } else {
            String[] astring = s.split(" ", -1);
            String s2 = astring[astring.length - 1];
            String[] astring1 = this.s.d();
            int i = astring1.length;

            for (int j = 0; j < i; ++j) {
                String s3 = astring1[j];

                if (CommandAbstract.a(s2, s3)) {
                    arraylist.add(s3);
                }
            }

            return arraylist;
        }
        */
        return this.server.tabComplete(p_71248_1_, p_71248_2_);
        // CraftBukkit end
    }

    public static MinecraftServer func_71276_C()
    {
        return field_71309_l;
    }

    public String func_70005_c_()
    {
        return "Server";
    }

    public void func_70006_a(String p_70006_1_)
    {
        this.func_98033_al().func_98233_a(StringUtils.func_76338_a(p_70006_1_));
    }

    public boolean func_70003_b(int p_70003_1_, String p_70003_2_)
    {
        return true;
    }

    public String func_70004_a(String p_70004_1_, Object ... p_70004_2_)
    {
        return StringTranslate.func_74808_a().func_74803_a(p_70004_1_, p_70004_2_);
    }

    public ICommandManager func_71187_D()
    {
        return this.field_71321_q;
    }

    public KeyPair func_71250_E()
    {
        return this.field_71292_I;
    }

    public int func_71215_F()
    {
        return this.field_71319_s;
    }

    public void func_71208_b(int p_71208_1_)
    {
        this.field_71319_s = p_71208_1_;
    }

    public String func_71214_G()
    {
        return this.field_71293_J;
    }

    public void func_71224_l(String p_71224_1_)
    {
        this.field_71293_J = p_71224_1_;
    }

    public boolean func_71264_H()
    {
        return this.field_71293_J != null;
    }

    public String func_71270_I()
    {
        return this.field_71294_K;
    }

    public void func_71261_m(String p_71261_1_)
    {
        this.field_71294_K = p_71261_1_;
    }

    public void func_71253_a(KeyPair p_71253_1_)
    {
        this.field_71292_I = p_71253_1_;
    }

    public void func_71226_c(int p_71226_1_)
    {
        // CraftBukkit start
        for (int j = 0; j < this.worlds.size(); ++j)
        {
            WorldServer worldserver = this.worlds.get(j);
            // CraftBukkit end

            if (worldserver != null)
            {
                if (worldserver.func_72912_H().func_76093_s())
                {
                    worldserver.field_73013_u = 3;
                    worldserver.func_72891_a(true, true);
                }
                else if (this.func_71264_H())
                {
                    worldserver.field_73013_u = p_71226_1_;
                    worldserver.func_72891_a(worldserver.field_73013_u > 0, true);
                }
                else
                {
                    worldserver.field_73013_u = p_71226_1_;
                    worldserver.func_72891_a(this.func_71193_K(), this.field_71324_y);
                }
            }
        }
    }

    protected boolean func_71193_K()
    {
        return true;
    }

    public boolean func_71242_L()
    {
        return this.field_71288_M;
    }

    public void func_71204_b(boolean p_71204_1_)
    {
        this.field_71288_M = p_71204_1_;
    }

    public void func_71194_c(boolean p_71194_1_)
    {
        this.field_71289_N = p_71194_1_;
    }

    public ISaveFormat func_71254_M()
    {
        return this.field_71310_m;
    }

    public void func_71272_O()
    {
        this.field_71290_O = true;
        this.func_71254_M().func_75800_d();

        // CraftBukkit start - This needs review, what does it do? (it's new)
        for (int i = 0; i < this.worlds.size(); ++i)
        {
            WorldServer worldserver = this.worlds.get(i);
            // CraftBukkit end

            if (worldserver != null)
            {
                worldserver.func_73041_k();
            }
        }

        this.func_71254_M().func_75802_e(this.worlds.get(0).func_72860_G().func_75760_g()); // CraftBukkit
        this.func_71263_m();
    }

    public String func_71202_P()
    {
        return this.field_71297_P;
    }

    public void func_71269_o(String p_71269_1_)
    {
        this.field_71297_P = p_71269_1_;
    }

    public void func_70000_a(PlayerUsageSnooper p_70000_1_)
    {
        p_70000_1_.func_76472_a("whitelist_enabled", Boolean.valueOf(false));
        p_70000_1_.func_76472_a("whitelist_count", Integer.valueOf(0));
        p_70000_1_.func_76472_a("players_current", Integer.valueOf(this.func_71233_x()));
        p_70000_1_.func_76472_a("players_max", Integer.valueOf(this.func_71275_y()));
        p_70000_1_.func_76472_a("players_seen", Integer.valueOf(this.field_71318_t.func_72373_m().length));
        p_70000_1_.func_76472_a("uses_auth", Boolean.valueOf(this.field_71325_x));
        p_70000_1_.func_76472_a("gui_state", this.func_71279_ae() ? "enabled" : "disabled");
        p_70000_1_.func_76472_a("avg_tick_ms", Integer.valueOf((int)(MathHelper.func_76127_a(this.field_71311_j) * 1.0E-6D)));
        p_70000_1_.func_76472_a("avg_sent_packet_count", Integer.valueOf((int)MathHelper.func_76127_a(this.field_71300_f)));
        p_70000_1_.func_76472_a("avg_sent_packet_size", Integer.valueOf((int)MathHelper.func_76127_a(this.field_71301_g)));
        p_70000_1_.func_76472_a("avg_rec_packet_count", Integer.valueOf((int)MathHelper.func_76127_a(this.field_71313_h)));
        p_70000_1_.func_76472_a("avg_rec_packet_size", Integer.valueOf((int)MathHelper.func_76127_a(this.field_71314_i)));
        int i = 0;

        // CraftBukkit start
        for (int j = 0; j < this.worlds.size(); ++j)
        {
            // if (this.worldServer[j] != null) {
            WorldServer worldserver = this.worlds.get(j);
            // CraftBukkit end
            WorldInfo worldinfo = worldserver.func_72912_H();
            p_70000_1_.func_76472_a("world[" + i + "][dimension]", Integer.valueOf(worldserver.field_73011_w.field_76574_g));
            p_70000_1_.func_76472_a("world[" + i + "][mode]", worldinfo.func_76077_q());
            p_70000_1_.func_76472_a("world[" + i + "][difficulty]", Integer.valueOf(worldserver.field_73013_u));
            p_70000_1_.func_76472_a("world[" + i + "][hardcore]", Boolean.valueOf(worldinfo.func_76093_s()));
            p_70000_1_.func_76472_a("world[" + i + "][generator_name]", worldinfo.func_76067_t().func_77127_a());
            p_70000_1_.func_76472_a("world[" + i + "][generator_version]", Integer.valueOf(worldinfo.func_76067_t().func_77131_c()));
            p_70000_1_.func_76472_a("world[" + i + "][height]", Integer.valueOf(this.field_71280_D));
            p_70000_1_.func_76472_a("world[" + i + "][chunks_loaded]", Integer.valueOf(worldserver.func_72863_F().func_73152_e()));
            ++i;
            // } // CraftBukkit
        }

        p_70000_1_.func_76472_a("worlds", Integer.valueOf(i));
    }

    public void func_70001_b(PlayerUsageSnooper p_70001_1_)
    {
        p_70001_1_.func_76472_a("singleplayer", Boolean.valueOf(this.func_71264_H()));
        p_70001_1_.func_76472_a("server_brand", this.getServerModName());
        p_70001_1_.func_76472_a("gui_supported", GraphicsEnvironment.isHeadless() ? "headless" : "supported");
        p_70001_1_.func_76472_a("dedicated", Boolean.valueOf(this.func_71262_S()));
    }

    public boolean func_70002_Q()
    {
        return true;
    }

    public int func_71227_R()
    {
        return 16;
    }

    public abstract boolean func_71262_S();

    public boolean func_71266_T()
    {
        return this.server.getOnlineMode(); // CraftBukkit
    }

    public void func_71229_d(boolean p_71229_1_)
    {
        this.field_71325_x = p_71229_1_;
    }

    public boolean func_71268_U()
    {
        return this.field_71324_y;
    }

    public void func_71251_e(boolean p_71251_1_)
    {
        this.field_71324_y = p_71251_1_;
    }

    public boolean func_71220_V()
    {
        return this.field_71323_z;
    }

    public void func_71257_f(boolean p_71257_1_)
    {
        this.field_71323_z = p_71257_1_;
    }

    public boolean func_71219_W()
    {
        return this.field_71284_A;
    }

    public void func_71188_g(boolean p_71188_1_)
    {
        this.field_71284_A = p_71188_1_;
    }

    public boolean func_71231_X()
    {
        return this.field_71285_B;
    }

    public void func_71245_h(boolean p_71245_1_)
    {
        this.field_71285_B = p_71245_1_;
    }

    public abstract boolean func_82356_Z();

    public String func_71273_Y()
    {
        return this.field_71286_C;
    }

    public void func_71205_p(String p_71205_1_)
    {
        this.field_71286_C = p_71205_1_;
    }

    public int func_71207_Z()
    {
        return this.field_71280_D;
    }

    public void func_71191_d(int p_71191_1_)
    {
        this.field_71280_D = p_71191_1_;
    }

    public boolean func_71241_aa()
    {
        return this.field_71316_v;
    }

    public ServerConfigurationManager func_71203_ab()
    {
        return this.field_71318_t;
    }

    public void func_71210_a(ServerConfigurationManager p_71210_1_)
    {
        this.field_71318_t = p_71210_1_;
    }

    public void func_71235_a(EnumGameType p_71235_1_)
    {
        // CraftBukkit start
        for (int i = 0; i < this.worlds.size(); ++i)
        {
            func_71276_C().worlds.get(i).func_72912_H().func_76060_a(p_71235_1_);
            // CraftBukkit end
        }
    }

    public abstract NetworkListenThread func_71212_ac();

    public boolean func_71279_ae()
    {
        return false;
    }

    public abstract String func_71206_a(EnumGameType enumgamemode, boolean flag);

    public int func_71259_af()
    {
        return this.field_71315_w;
    }

    public void func_71223_ag()
    {
        this.field_71295_T = true;
    }

    public ChunkCoordinates func_82114_b()
    {
        return new ChunkCoordinates(0, 0, 0);
    }

    public int func_82357_ak()
    {
        return 16;
    }

    public boolean func_96290_a(World p_96290_1_, int p_96290_2_, int p_96290_3_, int p_96290_4_, EntityPlayer p_96290_5_)
    {
        return false;
    }

    public abstract ILogAgent func_98033_al();

    public static ServerConfigurationManager func_71196_a(MinecraftServer p_71196_0_)
    {
        return p_71196_0_.field_71318_t;
    }
}
