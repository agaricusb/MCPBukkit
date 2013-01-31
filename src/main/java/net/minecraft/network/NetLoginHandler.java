package net.minecraft.network;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.network.packet.Packet205ClientCommand;
import net.minecraft.network.packet.Packet252SharedKey;
import net.minecraft.network.packet.Packet253ServerAuthData;
import net.minecraft.network.packet.Packet254ServerPing;
import net.minecraft.network.packet.Packet255KickDisconnect;
import net.minecraft.network.packet.Packet2ClientProtocol;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServerListenThread;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.StringUtils;

public class NetLoginHandler extends NetHandler
{
    private byte[] field_72536_d;
    public static Logger field_72540_a = Logger.getLogger("Minecraft");
    private static Random field_72537_e = new Random();
    public TcpConnection field_72538_b;
    public boolean field_72539_c = false;
    private MinecraftServer field_72534_f;
    private int field_72535_g = 0;
    private String field_72543_h = null;
    private volatile boolean field_72544_i = false;
    private String field_72541_j = Long.toString(field_72537_e.nextLong(), 16); // CraftBukkit - Security fix
    private boolean field_92079_k = false;
    private SecretKey field_72542_k = null;
    public String hostname = ""; // CraftBukkit - add field

    public NetLoginHandler(MinecraftServer p_i3400_1_, Socket p_i3400_2_, String p_i3400_3_) throws java.io.IOException   // CraftBukkit - throws IOException
    {
        this.field_72534_f = p_i3400_1_;
        this.field_72538_b = new TcpConnection(p_i3400_2_, p_i3400_3_, this, p_i3400_1_.func_71250_E().getPrivate());
        this.field_72538_b.field_74468_e = 0;
    }

    // CraftBukkit start
    public Socket getSocket()
    {
        return this.field_72538_b.func_74452_g();
    }
    // CraftBukkit end

    public void func_72532_c()
    {
        if (this.field_72544_i)
        {
            this.func_72529_d();
        }

        if (this.field_72535_g++ == 600)
        {
            this.func_72527_a("Took too long to log in");
        }
        else
        {
            this.field_72538_b.func_74428_b();
        }
    }

    public void func_72527_a(String p_72527_1_)
    {
        try
        {
            field_72540_a.info("Disconnecting " + this.func_72528_e() + ": " + p_72527_1_);
            this.field_72538_b.func_74429_a(new Packet255KickDisconnect(p_72527_1_));
            this.field_72538_b.func_74423_d();
            this.field_72539_c = true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public void func_72500_a(Packet2ClientProtocol p_72500_1_)
    {
        // CraftBukkit start
        this.hostname = p_72500_1_.field_73457_c == null ? "" : p_72500_1_.field_73457_c + ':' + p_72500_1_.field_73455_d;
        // CraftBukkit end
        this.field_72543_h = p_72500_1_.func_73454_f();

        if (!this.field_72543_h.equals(StringUtils.func_76338_a(this.field_72543_h)))
        {
            this.func_72527_a("Invalid username!");
        }
        else
        {
            PublicKey publickey = this.field_72534_f.func_71250_E().getPublic();

            if (p_72500_1_.func_73453_d() != 51)
            {
                if (p_72500_1_.func_73453_d() > 51)
                {
                    this.func_72527_a("Outdated server!");
                }
                else
                {
                    this.func_72527_a("Outdated client!");
                }
            }
            else
            {
                this.field_72541_j = this.field_72534_f.func_71266_T() ? Long.toString(field_72537_e.nextLong(), 16) : "-";
                this.field_72536_d = new byte[4];
                field_72537_e.nextBytes(this.field_72536_d);
                this.field_72538_b.func_74429_a(new Packet253ServerAuthData(this.field_72541_j, publickey, this.field_72536_d));
            }
        }
    }

    public void func_72513_a(Packet252SharedKey p_72513_1_)
    {
        PrivateKey privatekey = this.field_72534_f.func_71250_E().getPrivate();
        this.field_72542_k = p_72513_1_.func_73303_a(privatekey);

        if (!Arrays.equals(this.field_72536_d, p_72513_1_.func_73302_b(privatekey)))
        {
            this.func_72527_a("Invalid client reply");
        }

        this.field_72538_b.func_74429_a(new Packet252SharedKey());
    }

    public void func_72458_a(Packet205ClientCommand p_72458_1_)
    {
        if (p_72458_1_.field_73447_a == 0)
        {
            if (this.field_72534_f.func_71266_T())
            {
                if (this.field_92079_k)
                {
                    this.func_72527_a("Duplicate login");
                    return;
                }

                this.field_92079_k = true;
                (new ThreadLoginVerifier(this, field_72534_f.server)).start(); // CraftBukkit - add CraftServer
            }
            else
            {
                this.field_72544_i = true;
            }
        }
    }

    public void func_72455_a(Packet1Login p_72455_1_) {}

    public void func_72529_d()
    {
        // CraftBukkit start
        EntityPlayerMP s = this.field_72534_f.func_71203_ab().attemptLogin(this, this.field_72543_h, this.hostname);

        if (s == null)
        {
            return;
            // CraftBukkit end
        }
        else
        {
            EntityPlayerMP entityplayermp = this.field_72534_f.func_71203_ab().processLogin(s); // CraftBukkit - this.h -> s

            if (entityplayermp != null)
            {
                this.field_72534_f.func_71203_ab().func_72355_a((INetworkManager) this.field_72538_b, entityplayermp);
            }
        }

        this.field_72539_c = true;
    }

    public void func_72515_a(String p_72515_1_, Object[] p_72515_2_)
    {
        field_72540_a.info(this.func_72528_e() + " lost connection");
        this.field_72539_c = true;
    }

    public void func_72467_a(Packet254ServerPing p_72467_1_)
    {
        if (this.field_72538_b.func_74452_g() == null)
        {
            return;    // CraftBukkit - fix NPE when a client queries a server that is unable to handle it.
        }

        try
        {
            ServerConfigurationManager serverconfigurationmanager = this.field_72534_f.func_71203_ab();
            String s = null;
            // CraftBukkit
            org.bukkit.event.server.ServerListPingEvent pingEvent = org.bukkit.craftbukkit.event.CraftEventFactory.callServerListPingEvent(this.field_72534_f.server, getSocket().getInetAddress(), this.field_72534_f.func_71273_Y(), serverconfigurationmanager.func_72394_k(), serverconfigurationmanager.func_72352_l());

            if (p_72467_1_.field_82559_a == 1)
            {
                // CraftBukkit start - fix decompile issues, don't create a list from an array
                Object[] list = new Object[] { 1, 51, this.field_72534_f.func_71249_w(), pingEvent.getMotd(), serverconfigurationmanager.func_72394_k(), pingEvent.getMaxPlayers() };

                for (Object object : list)
                {
                    if (s == null)
                    {
                        s = "\u00A7";
                    }
                    else
                    {
                        s = s + "\0";
                    }

                    s += org.apache.commons.lang.StringUtils.replace(object.toString(), "\0", "");
                }

                // CraftBukkit end
            }
            else
            {
                // CraftBukkit
                s = pingEvent.getMotd() + "\u00A7" + serverconfigurationmanager.func_72394_k() + "\u00A7" + pingEvent.getMaxPlayers();
            }

            InetAddress inetaddress = null;

            if (this.field_72538_b.func_74452_g() != null)
            {
                inetaddress = this.field_72538_b.func_74452_g().getInetAddress();
            }

            this.field_72538_b.func_74429_a(new Packet255KickDisconnect(s));
            this.field_72538_b.func_74423_d();

            if (inetaddress != null && this.field_72534_f.func_71212_ac() instanceof DedicatedServerListenThread)
            {
                ((DedicatedServerListenThread)this.field_72534_f.func_71212_ac()).func_71761_a(inetaddress);
            }

            this.field_72539_c = true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public void func_72509_a(Packet p_72509_1_)
    {
        this.func_72527_a("Protocol error");
    }

    public String func_72528_e()
    {
        return this.field_72543_h != null ? this.field_72543_h + " [" + this.field_72538_b.func_74430_c().toString() + "]" : this.field_72538_b.func_74430_c().toString();
    }

    public boolean func_72489_a()
    {
        return true;
    }

    static String func_72526_a(NetLoginHandler p_72526_0_)
    {
        return p_72526_0_.field_72541_j;
    }

    static MinecraftServer func_72530_b(NetLoginHandler p_72530_0_)
    {
        return p_72530_0_.field_72534_f;
    }

    static SecretKey func_72525_c(NetLoginHandler p_72525_0_)
    {
        return p_72525_0_.field_72542_k;
    }

    static String func_72533_d(NetLoginHandler p_72533_0_)
    {
        return p_72533_0_.field_72543_h;
    }

    static boolean func_72531_a(NetLoginHandler p_72531_0_, boolean p_72531_1_)
    {
        return p_72531_0_.field_72544_i = p_72531_1_;
    }
}
