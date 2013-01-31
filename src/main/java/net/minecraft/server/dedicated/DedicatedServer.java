package net.minecraft.server.dedicated;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.ServerCommand;
import net.minecraft.crash.CrashReport;
import net.minecraft.network.NetworkListenThread;
import net.minecraft.network.rcon.IServer;
import net.minecraft.network.rcon.RConThreadMain;
import net.minecraft.network.rcon.RConThreadQuery;
import net.minecraft.profiler.PlayerUsageSnooper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.gui.ServerGUI;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.CryptManager;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumGameType;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.storage.AnvilSaveConverter;

// CraftBukkit start
import java.io.PrintStream;

import org.bukkit.craftbukkit.LoggerOutputStream;
import org.bukkit.event.server.ServerCommandEvent;
// CraftBukkit end

public class DedicatedServer extends MinecraftServer implements IServer
{
    private final List field_71341_l = Collections.synchronizedList(new ArrayList());
    private RConThreadQuery field_71342_m;
    private RConThreadMain field_71339_n;
    public PropertyManager field_71340_o; // CraftBukkit - private -> public
    private boolean field_71338_p;
    private EnumGameType field_71337_q;
    private NetworkListenThread field_71336_r;
    private boolean field_71335_s = false;

    // CraftBukkit start - Signature changed
    public DedicatedServer(joptsimple.OptionSet options)
    {
        super(options);
        // CraftBukkit end
        new DedicatedServerSleepThread(this);
    }

    protected boolean func_71197_b() throws java.net.UnknownHostException   // CraftBukkit - throws UnknownHostException
    {
        DedicatedServerCommandThread dedicatedservercommandthread = new DedicatedServerCommandThread(this);
        dedicatedservercommandthread.setDaemon(true);
        dedicatedservercommandthread.start();
        ConsoleLogManager.init(this); // CraftBukkit
        // CraftBukkit start
        System.setOut(new PrintStream(new LoggerOutputStream(field_71306_a, Level.INFO), true));
        System.setErr(new PrintStream(new LoggerOutputStream(field_71306_a, Level.SEVERE), true));
        // CraftBukkit end
        field_71306_a.info("Starting minecraft server version 1.4.7");

        if (Runtime.getRuntime().maxMemory() / 1024L / 1024L < 512L)
        {
            field_71306_a.warning("To start the server with more ram, launch it as \"java -Xmx1024M -Xms1024M -jar minecraft_server.jar\"");
        }

        field_71306_a.info("Loading properties");
        this.field_71340_o = new PropertyManager(this.options); // CraftBukkit - CLI argument support

        if (this.func_71264_H())
        {
            this.func_71189_e("127.0.0.1");
        }
        else
        {
            this.func_71229_d(this.field_71340_o.func_73670_a("online-mode", true));
            this.func_71189_e(this.field_71340_o.func_73671_a("server-ip", ""));
        }

        this.func_71251_e(this.field_71340_o.func_73670_a("spawn-animals", true));
        this.func_71257_f(this.field_71340_o.func_73670_a("spawn-npcs", true));
        this.func_71188_g(this.field_71340_o.func_73670_a("pvp", true));
        this.func_71245_h(this.field_71340_o.func_73670_a("allow-flight", false));
        this.func_71269_o(this.field_71340_o.func_73671_a("texture-pack", ""));
        this.func_71205_p(this.field_71340_o.func_73671_a("motd", "A Minecraft Server"));

        if (this.field_71340_o.func_73669_a("difficulty", 1) < 0)
        {
            this.field_71340_o.func_73667_a("difficulty", Integer.valueOf(0));
        }
        else if (this.field_71340_o.func_73669_a("difficulty", 1) > 3)
        {
            this.field_71340_o.func_73667_a("difficulty", Integer.valueOf(3));
        }

        this.field_71338_p = this.field_71340_o.func_73670_a("generate-structures", true);
        int i = this.field_71340_o.func_73669_a("gamemode", EnumGameType.SURVIVAL.func_77148_a());
        this.field_71337_q = WorldSettings.func_77161_a(i);
        field_71306_a.info("Default game type: " + this.field_71337_q);
        InetAddress inetaddress = null;

        if (this.func_71211_k().length() > 0)
        {
            inetaddress = InetAddress.getByName(this.func_71211_k());
        }

        if (this.func_71215_F() < 0)
        {
            this.func_71208_b(this.field_71340_o.func_73669_a("server-port", 25565));
        }

        field_71306_a.info("Generating keypair");
        this.func_71253_a(CryptManager.func_75891_b());
        field_71306_a.info("Starting Minecraft server on " + (this.func_71211_k().length() == 0 ? "*" : this.func_71211_k()) + ":" + this.func_71215_F());

        try
        {
            this.field_71336_r = new DedicatedServerListenThread(this, inetaddress, this.func_71215_F());
        }
        catch (Throwable ioexception)     // CraftBukkit - IOException -> Throwable
        {
            field_71306_a.warning("**** FAILED TO BIND TO PORT!");
            field_71306_a.log(Level.WARNING, "The exception was: " + ioexception.toString());
            field_71306_a.warning("Perhaps a server is already running on that port?");
            return false;
        }

        this.func_71210_a((ServerConfigurationManager)(new DedicatedPlayerList(this)));  // CraftBukkit

        if (!this.func_71266_T())
        {
            field_71306_a.warning("**** SERVER IS RUNNING IN OFFLINE/INSECURE MODE!");
            field_71306_a.warning("The server will make no attempt to authenticate usernames. Beware.");
            field_71306_a.warning("While this makes the game possible to play without internet access, it also opens up the ability for hackers to connect with any username they choose.");
            field_71306_a.warning("To change this, set \"online-mode\" to \"true\" in the server.properties file.");
        }

        // this.a((PlayerList) (new DedicatedPlayerList(this))); // CraftBukkit - moved up
        this.field_71310_m = new AnvilSaveConverter(server.getWorldContainer()); // CraftBukkit - moved from MinecraftServer constructor
        long j = System.nanoTime();

        if (this.func_71270_I() == null)
        {
            this.func_71261_m(this.field_71340_o.func_73671_a("level-name", "world"));
        }

        String s = this.field_71340_o.func_73671_a("level-seed", "");
        String s1 = this.field_71340_o.func_73671_a("level-type", "DEFAULT");
        String s2 = this.field_71340_o.func_73671_a("generator-settings", "");
        long k = (new Random()).nextLong();

        if (s.length() > 0)
        {
            try
            {
                long l = Long.parseLong(s);

                if (l != 0L)
                {
                    k = l;
                }
            }
            catch (NumberFormatException numberformatexception)
            {
                k = (long)s.hashCode();
            }
        }

        WorldType worldtype = WorldType.func_77130_a(s1);

        if (worldtype == null)
        {
            worldtype = WorldType.field_77137_b;
        }

        this.func_71191_d(this.field_71340_o.func_73669_a("max-build-height", 256));
        this.func_71191_d((this.func_71207_Z() + 8) / 16 * 16);
        this.func_71191_d(MathHelper.func_76125_a(this.func_71207_Z(), 64, 256));
        this.field_71340_o.func_73667_a("max-build-height", Integer.valueOf(this.func_71207_Z()));
        field_71306_a.info("Preparing level \"" + this.func_71270_I() + "\"");
        this.func_71247_a(this.func_71270_I(), this.func_71270_I(), k, worldtype, s2);
        long i1 = System.nanoTime() - j;
        String s3 = String.format("%.3fs", new Object[] {Double.valueOf((double)i1 / 1.0E9D)});
        field_71306_a.info("Done (" + s3 + ")! For help, type \"help\" or \"?\"");

        if (this.field_71340_o.func_73670_a("enable-query", false))
        {
            field_71306_a.info("Starting GS4 status listener");
            this.field_71342_m = new RConThreadQuery(this);
            this.field_71342_m.func_72602_a();
        }

        if (this.field_71340_o.func_73670_a("enable-rcon", false))
        {
            field_71306_a.info("Starting remote control listener");
            this.field_71339_n = new RConThreadMain(this);
            this.field_71339_n.func_72602_a();
            this.remoteConsole = new org.bukkit.craftbukkit.command.CraftRemoteConsoleCommandSender(); // CraftBukkit
        }

        // CraftBukkit start
        if (this.server.getBukkitSpawnRadius() > -1)
        {
            field_71306_a.info("'settings.spawn-radius' in bukkit.yml has been moved to 'spawn-protection' in server.properties. I will move your config for you.");
            this.field_71340_o.field_73672_b.remove("spawn-protection");
            this.field_71340_o.func_73669_a("spawn-protection", this.server.getBukkitSpawnRadius());
            this.server.removeBukkitSpawnRadius();
            this.field_71340_o.func_73668_b();
        }

        return true;
    }

    public PropertyManager getPropertyManager()
    {
        return this.field_71340_o;
    }
    // CraftBukkit end

    public boolean func_71225_e()
    {
        return this.field_71338_p;
    }

    public EnumGameType func_71265_f()
    {
        return this.field_71337_q;
    }

    public int func_71232_g()
    {
        return Math.max(0, Math.min(3, this.field_71340_o.func_73669_a("difficulty", 1))); // CraftBukkit - clamp values
    }

    public boolean func_71199_h()
    {
        return this.field_71340_o.func_73670_a("hardcore", false);
    }

    protected void func_71228_a(CrashReport p_71228_1_)
    {
        while (this.func_71278_l())
        {
            this.func_71333_ah();

            try
            {
                Thread.sleep(10L);
            }
            catch (InterruptedException interruptedexception)
            {
                interruptedexception.printStackTrace();
            }
        }
    }

    public CrashReport func_71230_b(CrashReport p_71230_1_)
    {
        p_71230_1_ = super.func_71230_b(p_71230_1_);
        p_71230_1_.func_85056_g().func_71500_a("Is Modded", (Callable)(new CallableType(this)));
        p_71230_1_.func_85056_g().func_71500_a("Type", (Callable)(new CallableServerType(this)));
        return p_71230_1_;
    }

    protected void func_71240_o()
    {
        System.exit(0);
    }

    public void func_71190_q()
    {
        super.func_71190_q();
        this.func_71333_ah();
    }

    public boolean func_71255_r()
    {
        return this.field_71340_o.func_73670_a("allow-nether", true);
    }

    public boolean func_71193_K()
    {
        return this.field_71340_o.func_73670_a("spawn-monsters", true);
    }

    public void func_70000_a(PlayerUsageSnooper p_70000_1_)
    {
        p_70000_1_.func_76472_a("whitelist_enabled", Boolean.valueOf(this.func_71334_ai().func_72383_n()));
        p_70000_1_.func_76472_a("whitelist_count", Integer.valueOf(this.func_71334_ai().func_72388_h().size()));
        super.func_70000_a(p_70000_1_);
    }

    public boolean func_70002_Q()
    {
        return this.field_71340_o.func_73670_a("snooper-enabled", true);
    }

    public void func_71331_a(String p_71331_1_, ICommandSender p_71331_2_)
    {
        this.field_71341_l.add(new ServerCommand(p_71331_1_, p_71331_2_));
    }

    public void func_71333_ah()
    {
        while (!this.field_71341_l.isEmpty())
        {
            ServerCommand servercommand = (ServerCommand)this.field_71341_l.remove(0);
            // CraftBukkit start - ServerCommand for preprocessing
            ServerCommandEvent event = new ServerCommandEvent(this.console, servercommand.field_73702_a);
            this.server.getPluginManager().callEvent(event);
            servercommand = new ServerCommand(event.getCommand(), servercommand.field_73701_b);
            // this.getCommandHandler().a(servercommand.source, servercommand.command); // Called in dispatchServerCommand
            this.server.dispatchServerCommand(this.console, servercommand);
            // CraftBukkit end
        }
    }

    public boolean func_71262_S()
    {
        return true;
    }

    public DedicatedPlayerList func_71334_ai()
    {
        return (DedicatedPlayerList)super.func_71203_ab();
    }

    public NetworkListenThread func_71212_ac()
    {
        return this.field_71336_r;
    }

    public int func_71327_a(String p_71327_1_, int p_71327_2_)
    {
        return this.field_71340_o.func_73669_a(p_71327_1_, p_71327_2_);
    }

    public String func_71330_a(String p_71330_1_, String p_71330_2_)
    {
        return this.field_71340_o.func_73671_a(p_71330_1_, p_71330_2_);
    }

    public boolean func_71332_a(String p_71332_1_, boolean p_71332_2_)
    {
        return this.field_71340_o.func_73670_a(p_71332_1_, p_71332_2_);
    }

    public void func_71328_a(String p_71328_1_, Object p_71328_2_)
    {
        this.field_71340_o.func_73667_a(p_71328_1_, p_71328_2_);
    }

    public void func_71326_a()
    {
        this.field_71340_o.func_73668_b();
    }

    public String func_71329_c()
    {
        File file1 = this.field_71340_o.func_73665_c();
        return file1 != null ? file1.getAbsolutePath() : "No settings file";
    }

    public void func_82011_an()
    {
        ServerGUI.func_79003_a(this);
        this.field_71335_s = true;
    }

    public boolean func_71279_ae()
    {
        return this.field_71335_s;
    }

    public String func_71206_a(EnumGameType p_71206_1_, boolean p_71206_2_)
    {
        return "";
    }

    public boolean func_82356_Z()
    {
        return this.field_71340_o.func_73670_a("enable-command-block", false);
    }

    public int func_82357_ak()
    {
        return this.field_71340_o.func_73669_a("spawn-protection", super.func_82357_ak());
    }

    public ServerConfigurationManager func_71203_ab()
    {
        return this.func_71334_ai();
    }
}
