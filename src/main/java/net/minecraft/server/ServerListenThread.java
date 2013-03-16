package net.minecraft.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.NetworkListenThread;

public class ServerListenThread extends Thread
{
    private final List field_71775_b = Collections.synchronizedList(new ArrayList());
    private final HashMap field_71776_c = new HashMap();
    private int field_71773_d = 0;
    private final ServerSocket field_71774_e;
    private NetworkListenThread field_71771_f;
    private final InetAddress field_71772_g;
    private final int field_71778_h;

    long connectionThrottle; // CraftBukkit

    public ServerListenThread(NetworkListenThread p_i3384_1_, InetAddress p_i3384_2_, int p_i3384_3_) throws IOException   // CraftBukkit - added throws
    {
        super("Listen thread");
        this.field_71771_f = p_i3384_1_;
        this.field_71778_h = p_i3384_3_;
        this.field_71774_e = new ServerSocket(p_i3384_3_, 0, p_i3384_2_);
        this.field_71772_g = p_i3384_2_ == null ? this.field_71774_e.getInetAddress() : p_i3384_2_;
        this.field_71774_e.setPerformancePreferences(0, 2, 1);
    }

    public void func_71766_a()
    {
        List list = this.field_71775_b;

        synchronized (this.field_71775_b)
        {
            for (int i = 0; i < this.field_71775_b.size(); ++i)
            {
                NetLoginHandler netloginhandler = (NetLoginHandler)this.field_71775_b.get(i);

                try
                {
                    netloginhandler.func_72532_c();
                }
                catch (Exception exception)
                {
                    netloginhandler.func_72527_a("Internal server error");
                    this.field_71771_f.func_71746_d().func_98033_al().func_98235_b("Failed to handle packet for " + netloginhandler.func_72528_e() + ": " + exception, (Throwable) exception);
                }

                if (netloginhandler.field_72539_c)
                {
                    this.field_71775_b.remove(i--);
                }

                netloginhandler.field_72538_b.func_74427_a();
            }
        }
    }

    public void run()
    {
        while (this.field_71771_f.field_71749_b)
        {
            try
            {
                Socket socket = this.field_71774_e.accept();
                InetAddress inetaddress = socket.getInetAddress();
                long i = System.currentTimeMillis();
                HashMap hashmap = this.field_71776_c;

                // CraftBukkit start
                if (((MinecraftServer) this.field_71771_f.func_71746_d()).server == null)
                {
                    socket.close();
                    continue;
                }

                connectionThrottle = ((MinecraftServer) this.field_71771_f.func_71746_d()).server.getConnectionThrottle();
                // CraftBukkit end

                synchronized (this.field_71776_c)
                {
                    if (this.field_71776_c.containsKey(inetaddress) && !func_71770_b(inetaddress) && i - ((Long) this.field_71776_c.get(inetaddress)).longValue() < connectionThrottle)
                    {
                        this.field_71776_c.put(inetaddress, Long.valueOf(i));
                        socket.close();
                        continue;
                    }

                    this.field_71776_c.put(inetaddress, Long.valueOf(i));
                }

                NetLoginHandler netloginhandler = new NetLoginHandler(this.field_71771_f.func_71746_d(), socket, "Connection #" + this.field_71773_d++);
                this.func_71764_a(netloginhandler);
            }
            catch (IOException ioexception)
            {
                this.field_71771_f.func_71746_d().func_98033_al().func_98236_b("DSCT: " + ioexception.getMessage()); // CraftBukkit
            }
        }

        this.field_71771_f.func_71746_d().func_98033_al().func_98233_a("Closing listening thread");
    }

    private void func_71764_a(NetLoginHandler p_71764_1_)
    {
        if (p_71764_1_ == null)
        {
            throw new IllegalArgumentException("Got null netloginhandler!");
        }
        else
        {
            List list = this.field_71775_b;

            synchronized (this.field_71775_b)
            {
                this.field_71775_b.add(p_71764_1_);
            }
        }
    }

    private static boolean func_71770_b(InetAddress p_71770_0_)
    {
        return "127.0.0.1".equals(p_71770_0_.getHostAddress());
    }

    public void func_71769_a(InetAddress p_71769_1_)
    {
        if (p_71769_1_ != null)
        {
            HashMap hashmap = this.field_71776_c;

            synchronized (this.field_71776_c)
            {
                this.field_71776_c.remove(p_71769_1_);
            }
        }
    }

    public void func_71768_b()
    {
        try
        {
            this.field_71774_e.close();
        }
        catch (Throwable throwable)
        {
            ;
        }
    }
}
