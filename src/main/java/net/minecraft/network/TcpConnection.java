package net.minecraft.network;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javax.crypto.SecretKey;
import net.minecraft.logging.ILogAgent;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet252SharedKey;
import net.minecraft.util.CryptManager;

import java.io.IOException; // CraftBukkit

public class TcpConnection implements INetworkManager
{
    public static AtomicInteger field_74471_a = new AtomicInteger();
    public static AtomicInteger field_74469_b = new AtomicInteger();
    private final Object field_74478_h = new Object();
    private final ILogAgent field_98215_i;
    public Socket field_74479_i; // CraftBukkit - private -> public
    private final SocketAddress field_74476_j;
    private volatile DataInputStream field_74477_k;
    private volatile DataOutputStream field_74474_l;
    private volatile boolean field_74475_m = true;
    private volatile boolean field_74472_n = false;
    private java.util.Queue field_74473_o = new java.util.concurrent.ConcurrentLinkedQueue(); // CraftBukkit - Concurrent linked queue
    private List field_74487_p = Collections.synchronizedList(new ArrayList());
    private List field_74486_q = Collections.synchronizedList(new ArrayList());
    private NetHandler field_74485_r;
    private boolean field_74484_s = false;
    private Thread field_74483_t;
    private Thread field_74482_u;
    private String field_74481_v = "";
    private Object[] field_74480_w;
    private int field_74490_x = 0;
    private int field_74489_y = 0;
    public static int[] field_74470_c = new int[256];
    public static int[] field_74467_d = new int[256];
    public int field_74468_e = 0;
    boolean field_74465_f = false;
    boolean field_74466_g = false;
    private SecretKey field_74488_z = null;
    private PrivateKey field_74463_A = null;
    private int field_74464_B = 50;

    public TcpConnection(ILogAgent p_i11027_1_, Socket p_i11027_2_, String p_i11027_3_, NetHandler p_i11027_4_, PrivateKey p_i11027_5_) throws IOException   // CraftBukkit - throws IOException
    {
        this.field_74463_A = p_i11027_5_;
        this.field_74479_i = p_i11027_2_;
        this.field_98215_i = p_i11027_1_;
        this.field_74476_j = p_i11027_2_.getRemoteSocketAddress();
        this.field_74485_r = p_i11027_4_;

        try
        {
            p_i11027_2_.setSoTimeout(30000);
            p_i11027_2_.setTrafficClass(24);
        }
        catch (SocketException socketexception)
        {
            System.err.println(socketexception.getMessage());
        }

        this.field_74477_k = new DataInputStream(p_i11027_2_.getInputStream());
        this.field_74474_l = new DataOutputStream(new BufferedOutputStream(p_i11027_2_.getOutputStream(), 5120));
        this.field_74482_u = new TcpReaderThread(this, p_i11027_3_ + " read thread");
        this.field_74483_t = new TcpWriterThread(this, p_i11027_3_ + " write thread");
        this.field_74482_u.start();
        this.field_74483_t.start();
    }

    public void func_74425_a(NetHandler p_74425_1_)
    {
        this.field_74485_r = p_74425_1_;
    }

    public void func_74429_a(Packet p_74429_1_)
    {
        if (!this.field_74484_s)
        {
            Object object = this.field_74478_h;

            synchronized (this.field_74478_h)
            {
                this.field_74489_y += p_74429_1_.func_73284_a() + 1;
                this.field_74487_p.add(p_74429_1_);
            }
        }
    }

    private boolean func_74459_h()
    {
        boolean flag = false;

        try
        {
            Packet packet;
            int i;
            int[] aint;

            if (this.field_74468_e == 0 || !this.field_74487_p.isEmpty() && System.currentTimeMillis() - ((Packet)this.field_74487_p.get(0)).field_73295_m >= (long)this.field_74468_e)
            {
                packet = this.func_74460_a(false);

                if (packet != null)
                {
                    Packet.func_73266_a(packet, this.field_74474_l);

                    if (packet instanceof Packet252SharedKey && !this.field_74466_g)
                    {
                        if (!this.field_74485_r.func_72489_a())
                        {
                            this.field_74488_z = ((Packet252SharedKey)packet).func_73304_d();
                        }

                        this.func_74446_k();
                    }

                    aint = field_74467_d;
                    i = packet.func_73281_k();
                    aint[i] += packet.func_73284_a() + 1;
                    flag = true;
                }
            }

            // CraftBukkit - don't allow low priority packet to be sent unless it was placed in the queue before the first packet on the high priority queue TODO: is this still right?
            if ((flag || this.field_74464_B-- <= 0) && !this.field_74486_q.isEmpty() && (this.field_74487_p.isEmpty() || ((Packet) this.field_74487_p.get(0)).field_73295_m > ((Packet) this.field_74486_q.get(0)).field_73295_m))
            {
                packet = this.func_74460_a(true);

                if (packet != null)
                {
                    Packet.func_73266_a(packet, this.field_74474_l);
                    aint = field_74467_d;
                    i = packet.func_73281_k();
                    aint[i] += packet.func_73284_a() + 1;
                    this.field_74464_B = 0;
                    flag = true;
                }
            }

            return flag;
        }
        catch (Exception exception)
        {
            if (!this.field_74472_n)
            {
                this.func_74455_a(exception);
            }

            return false;
        }
    }

    private Packet func_74460_a(boolean p_74460_1_)
    {
        Packet packet = null;
        List list = p_74460_1_ ? this.field_74486_q : this.field_74487_p;
        Object object = this.field_74478_h;

        synchronized (this.field_74478_h)
        {
            while (!list.isEmpty() && packet == null)
            {
                packet = (Packet)list.remove(0);
                this.field_74489_y -= packet.func_73284_a() + 1;

                if (this.func_74454_a(packet, p_74460_1_))
                {
                    packet = null;
                }
            }

            return packet;
        }
    }

    private boolean func_74454_a(Packet p_74454_1_, boolean p_74454_2_)
    {
        if (!p_74454_1_.func_73278_e())
        {
            return false;
        }
        else
        {
            List list = p_74454_2_ ? this.field_74486_q : this.field_74487_p;
            Iterator iterator = list.iterator();
            Packet packet1;

            do
            {
                if (!iterator.hasNext())
                {
                    return false;
                }

                packet1 = (Packet)iterator.next();
            }
            while (packet1.func_73281_k() != p_74454_1_.func_73281_k());

            return p_74454_1_.func_73268_a(packet1);
        }
    }

    public void func_74427_a()
    {
        if (this.field_74482_u != null)
        {
            this.field_74482_u.interrupt();
        }

        if (this.field_74483_t != null)
        {
            this.field_74483_t.interrupt();
        }
    }

    private boolean func_74447_i()
    {
        boolean flag = false;

        try
        {
            Packet packet = Packet.func_73272_a(this.field_98215_i, this.field_74477_k, this.field_74485_r.func_72489_a(), this.field_74479_i);

            if (packet != null)
            {
                if (packet instanceof Packet252SharedKey && !this.field_74465_f)
                {
                    if (this.field_74485_r.func_72489_a())
                    {
                        this.field_74488_z = ((Packet252SharedKey)packet).func_73303_a(this.field_74463_A);
                    }

                    this.func_74448_j();
                }

                int[] aint = field_74470_c;
                int i = packet.func_73281_k();
                aint[i] += packet.func_73284_a() + 1;

                if (!this.field_74484_s)
                {
                    if (packet.func_73277_a_() && this.field_74485_r.func_72469_b())
                    {
                        this.field_74490_x = 0;
                        packet.func_73279_a(this.field_74485_r);
                    }
                    else
                    {
                        this.field_74473_o.add(packet);
                    }
                }

                flag = true;
            }
            else
            {
                this.func_74424_a("disconnect.endOfStream", new Object[0]);
            }

            return flag;
        }
        catch (Exception exception)
        {
            if (!this.field_74472_n)
            {
                this.func_74455_a(exception);
            }

            return false;
        }
    }

    private void func_74455_a(Exception p_74455_1_)
    {
        // exception.printStackTrace(); // CraftBukkit - Remove console spam
        this.func_74424_a("disconnect.genericReason", new Object[] {"Internal exception: " + p_74455_1_.toString()});
    }

    public void func_74424_a(String p_74424_1_, Object ... p_74424_2_)
    {
        if (this.field_74475_m)
        {
            this.field_74472_n = true;
            this.field_74481_v = p_74424_1_;
            this.field_74480_w = p_74424_2_;
            this.field_74475_m = false;
            (new TcpMasterThread(this)).start();

            try
            {
                this.field_74477_k.close();
            }
            catch (Throwable throwable)
            {
                ;
            }

            try
            {
                this.field_74474_l.close();
            }
            catch (Throwable throwable1)
            {
                ;
            }

            try
            {
                this.field_74479_i.close();
            }
            catch (Throwable throwable2)
            {
                ;
            }

            this.field_74477_k = null;
            this.field_74474_l = null;
            this.field_74479_i = null;
        }
    }

    public void func_74428_b()
    {
        if (this.field_74489_y > 2097152)
        {
            this.func_74424_a("disconnect.overflow", new Object[0]);
        }

        if (this.field_74473_o.isEmpty())
        {
            if (this.field_74490_x++ == 1200)
            {
                this.func_74424_a("disconnect.timeout", new Object[0]);
            }
        }
        else
        {
            this.field_74490_x = 0;
        }

        int i = 1000;

        while (!this.field_74473_o.isEmpty() && i-- >= 0)
        {
            Packet packet = (Packet) this.field_74473_o.poll(); // CraftBukkit - remove -> poll

            // CraftBukkit start
            if (this.field_74485_r instanceof NetLoginHandler ? ((NetLoginHandler) this.field_74485_r).field_72539_c : ((NetServerHandler) this.field_74485_r).field_72576_c)
            {
                continue;
            }

            // CraftBukkit end
            packet.func_73279_a(this.field_74485_r);
        }

        this.func_74427_a();

        if (this.field_74472_n && this.field_74473_o.isEmpty())
        {
            this.field_74485_r.func_72515_a(this.field_74481_v, this.field_74480_w);
        }
    }

    public SocketAddress func_74430_c()
    {
        return this.field_74476_j;
    }

    public void func_74423_d()
    {
        if (!this.field_74484_s)
        {
            this.func_74427_a();
            this.field_74484_s = true;
            this.field_74482_u.interrupt();
            (new TcpMonitorThread(this)).start();
        }
    }

    private void func_74448_j() throws IOException   // CraftBukkit - throws IOException
    {
        this.field_74465_f = true;
        InputStream inputstream = this.field_74479_i.getInputStream();
        this.field_74477_k = new DataInputStream(CryptManager.func_75888_a(this.field_74488_z, inputstream));
    }

    private void func_74446_k() throws IOException   // CraftBukkit - throws IOException
    {
        this.field_74474_l.flush();
        this.field_74466_g = true;
        BufferedOutputStream bufferedoutputstream = new BufferedOutputStream(CryptManager.func_75897_a(this.field_74488_z, this.field_74479_i.getOutputStream()), 5120);
        this.field_74474_l = new DataOutputStream(bufferedoutputstream);
    }

    public int func_74426_e()
    {
        return this.field_74486_q.size();
    }

    public Socket func_74452_g()
    {
        return this.field_74479_i;
    }

    static boolean func_74462_a(TcpConnection p_74462_0_)
    {
        return p_74462_0_.field_74475_m;
    }

    static boolean func_74449_b(TcpConnection p_74449_0_)
    {
        return p_74449_0_.field_74484_s;
    }

    static boolean func_74450_c(TcpConnection p_74450_0_)
    {
        return p_74450_0_.func_74447_i();
    }

    static boolean func_74451_d(TcpConnection p_74451_0_)
    {
        return p_74451_0_.func_74459_h();
    }

    static DataOutputStream func_74453_e(TcpConnection p_74453_0_)
    {
        return p_74453_0_.field_74474_l;
    }

    static boolean func_74456_f(TcpConnection p_74456_0_)
    {
        return p_74456_0_.field_74472_n;
    }

    static void func_74458_a(TcpConnection p_74458_0_, Exception p_74458_1_)
    {
        p_74458_0_.func_74455_a(p_74458_1_);
    }

    static Thread func_74457_g(TcpConnection p_74457_0_)
    {
        return p_74457_0_.field_74482_u;
    }

    static Thread func_74461_h(TcpConnection p_74461_0_)
    {
        return p_74461_0_.field_74483_t;
    }
}
