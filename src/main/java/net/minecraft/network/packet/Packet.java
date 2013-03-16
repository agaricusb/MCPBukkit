package net.minecraft.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.minecraft.item.ItemStack;
import net.minecraft.logging.ILogAgent;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IntHashMap;

public abstract class Packet
{
    public static IntHashMap field_73294_l = new IntHashMap();
    private static Map field_73291_a = new HashMap();
    private static Set field_73286_b = new HashSet();
    private static Set field_73288_c = new HashSet();
    protected ILogAgent field_98193_m;
    public final long field_73295_m = System.currentTimeMillis();
    public static long field_73292_n;
    public static long field_73293_o;
    public static long field_73290_p;
    public static long field_73289_q;
    public boolean field_73287_r = false;
    // CraftBukkit start - calculate packet ID once - used a bunch of times
    private int packetID;

    public Packet()
    {
        packetID = ((Integer) field_73291_a.get(this.getClass())).intValue();
    }
    // CraftBukkit end

    static void func_73285_a(int p_73285_0_, boolean p_73285_1_, boolean p_73285_2_, Class p_73285_3_)
    {
        if (field_73294_l.func_76037_b(p_73285_0_))
        {
            throw new IllegalArgumentException("Duplicate packet id:" + p_73285_0_);
        }
        else if (field_73291_a.containsKey(p_73285_3_))
        {
            throw new IllegalArgumentException("Duplicate packet class:" + p_73285_3_);
        }
        else
        {
            field_73294_l.func_76038_a(p_73285_0_, p_73285_3_);
            field_73291_a.put(p_73285_3_, Integer.valueOf(p_73285_0_));

            if (p_73285_1_)
            {
                field_73286_b.add(Integer.valueOf(p_73285_0_));
            }

            if (p_73285_2_)
            {
                field_73288_c.add(Integer.valueOf(p_73285_0_));
            }
        }
    }

    public static Packet func_73269_d(ILogAgent p_73269_0_, int p_73269_1_)
    {
        try
        {
            Class oclass = (Class)field_73294_l.func_76041_a(p_73269_1_);
            return oclass == null ? null : (Packet)oclass.newInstance();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            p_73269_0_.func_98232_c("Skipping packet with id " + p_73269_1_);
            return null;
        }
    }

    public static void func_73274_a(DataOutputStream p_73274_0_, byte[] p_73274_1_) throws IOException   // CraftBukkit - throws IOException
    {
        p_73274_0_.writeShort(p_73274_1_.length);
        p_73274_0_.write(p_73274_1_);
    }

    public static byte[] func_73280_b(DataInputStream p_73280_0_) throws IOException   // CraftBukkit - throws IOException
    {
        short short1 = p_73280_0_.readShort();

        if (short1 < 0)
        {
            throw new IOException("Key was smaller than nothing!  Weird key!");
        }
        else
        {
            byte[] abyte = new byte[short1];
            p_73280_0_.readFully(abyte);
            return abyte;
        }
    }

    public final int func_73281_k()
    {
        return packetID; // ((Integer) a.get(this.getClass())).intValue(); // CraftBukkit
    }

    public static Packet func_73272_a(ILogAgent p_73272_0_, DataInputStream p_73272_1_, boolean p_73272_2_, Socket p_73272_3_) throws IOException   // CraftBukkit - throws IOException
    {
        boolean flag1 = false;
        Packet packet = null;
        int i = p_73272_3_.getSoTimeout();
        int j;

        try
        {
            j = p_73272_1_.read();

            if (j == -1)
            {
                return null;
            }

            if (p_73272_2_ && !field_73288_c.contains(Integer.valueOf(j)) || !p_73272_2_ && !field_73286_b.contains(Integer.valueOf(j)))
            {
                throw new IOException("Bad packet id " + j);
            }

            packet = func_73269_d(p_73272_0_, j);

            if (packet == null)
            {
                throw new IOException("Bad packet id " + j);
            }

            packet.field_98193_m = p_73272_0_;

            if (packet instanceof Packet254ServerPing)
            {
                p_73272_3_.setSoTimeout(1500);
            }

            packet.func_73267_a(p_73272_1_);
            ++field_73292_n;
            field_73293_o += (long)packet.func_73284_a();
        }
        catch (EOFException eofexception)
        {
            p_73272_0_.func_98232_c("Reached end of stream");
            return null;
        }
        // CraftBukkit start
        catch (java.net.SocketTimeoutException exception)
        {
            p_73272_0_.func_98232_c("Read timed out");
            return null;
        }
        catch (java.net.SocketException exception)
        {
            p_73272_0_.func_98232_c("Connection reset");
            return null;
        }

        // CraftBukkit end
        PacketCount.func_76118_a(j, (long)packet.func_73284_a());
        ++field_73292_n;
        field_73293_o += (long)packet.func_73284_a();
        p_73272_3_.setSoTimeout(i);
        return packet;
    }

    public static void func_73266_a(Packet p_73266_0_, DataOutputStream p_73266_1_) throws IOException   // CraftBukkit - throws IOException
    {
        p_73266_1_.write(p_73266_0_.func_73281_k());
        p_73266_0_.func_73273_a(p_73266_1_);
        ++field_73290_p;
        field_73289_q += (long)p_73266_0_.func_73284_a();
    }

    public static void func_73271_a(String p_73271_0_, DataOutputStream p_73271_1_) throws IOException   // CraftBukkit - throws IOException
    {
        if (p_73271_0_.length() > 32767)
        {
            throw new IOException("String too big");
        }
        else
        {
            p_73271_1_.writeShort(p_73271_0_.length());
            p_73271_1_.writeChars(p_73271_0_);
        }
    }

    public static String func_73282_a(DataInputStream p_73282_0_, int p_73282_1_) throws IOException   // CraftBukkit - throws IOException
    {
        short short1 = p_73282_0_.readShort();

        if (short1 > p_73282_1_)
        {
            throw new IOException("Received string length longer than maximum allowed (" + short1 + " > " + p_73282_1_ + ")");
        }
        else if (short1 < 0)
        {
            throw new IOException("Received string length is less than zero! Weird string!");
        }
        else
        {
            StringBuilder stringbuilder = new StringBuilder();

            for (int j = 0; j < short1; ++j)
            {
                stringbuilder.append(p_73282_0_.readChar());
            }

            return stringbuilder.toString();
        }
    }

    public abstract void func_73267_a(DataInputStream datainputstream) throws IOException; // CraftBukkit - throws IOException

    public abstract void func_73273_a(DataOutputStream dataoutputstream) throws IOException; // CraftBukkit - throws IOException

    public abstract void func_73279_a(NetHandler connection);

    public abstract int func_73284_a();

    public boolean func_73278_e()
    {
        return false;
    }

    public boolean func_73268_a(Packet p_73268_1_)
    {
        return false;
    }

    public boolean func_73277_a_()
    {
        return false;
    }

    public String toString()
    {
        String s = this.getClass().getSimpleName();
        return s;
    }

    public static ItemStack func_73276_c(DataInputStream p_73276_0_) throws IOException   // CraftBukkit - throws IOException
    {
        ItemStack itemstack = null;
        short short1 = p_73276_0_.readShort();

        if (short1 >= 0)
        {
            byte b0 = p_73276_0_.readByte();
            short short2 = p_73276_0_.readShort();
            itemstack = new ItemStack(short1, b0, short2);
            itemstack.field_77990_d = func_73283_d(p_73276_0_);
        }

        return itemstack;
    }

    public static void func_73270_a(ItemStack p_73270_0_, DataOutputStream p_73270_1_) throws IOException   // CraftBukkit - throws IOException
    {
        if (p_73270_0_ == null || p_73270_0_.func_77973_b() == null)   // CraftBukkit - NPE fix itemstack.getItem()
        {
            p_73270_1_.writeShort(-1);
        }
        else
        {
            p_73270_1_.writeShort(p_73270_0_.field_77993_c);
            p_73270_1_.writeByte(p_73270_0_.field_77994_a);
            p_73270_1_.writeShort(p_73270_0_.func_77960_j());
            NBTTagCompound nbttagcompound = null;

            if (p_73270_0_.func_77973_b().func_77645_m() || p_73270_0_.func_77973_b().func_77651_p())
            {
                nbttagcompound = p_73270_0_.field_77990_d;
            }

            func_73275_a(nbttagcompound, p_73270_1_);
        }
    }

    public static NBTTagCompound func_73283_d(DataInputStream p_73283_0_) throws IOException   // CraftBukkit - throws IOException
    {
        short short1 = p_73283_0_.readShort();

        if (short1 < 0)
        {
            return null;
        }
        else
        {
            byte[] abyte = new byte[short1];
            p_73283_0_.readFully(abyte);
            return CompressedStreamTools.func_74792_a(abyte);
        }
    }

    protected static void func_73275_a(NBTTagCompound p_73275_0_, DataOutputStream p_73275_1_) throws IOException   // CraftBukkit - throws IOException
    {
        if (p_73275_0_ == null)
        {
            p_73275_1_.writeShort(-1);
        }
        else
        {
            byte[] abyte = CompressedStreamTools.func_74798_a(p_73275_0_);
            p_73275_1_.writeShort((short)abyte.length);
            p_73275_1_.write(abyte);
        }
    }

    static
    {
        func_73285_a(0, true, true, Packet0KeepAlive.class);
        func_73285_a(1, true, true, Packet1Login.class);
        func_73285_a(2, false, true, Packet2ClientProtocol.class);
        func_73285_a(3, true, true, Packet3Chat.class);
        func_73285_a(4, true, false, Packet4UpdateTime.class);
        func_73285_a(5, true, false, Packet5PlayerInventory.class);
        func_73285_a(6, true, false, Packet6SpawnPosition.class);
        func_73285_a(7, false, true, Packet7UseEntity.class);
        func_73285_a(8, true, false, Packet8UpdateHealth.class);
        func_73285_a(9, true, true, Packet9Respawn.class);
        func_73285_a(10, true, true, Packet10Flying.class);
        func_73285_a(11, true, true, Packet11PlayerPosition.class);
        func_73285_a(12, true, true, Packet12PlayerLook.class);
        func_73285_a(13, true, true, Packet13PlayerLookMove.class);
        func_73285_a(14, false, true, Packet14BlockDig.class);
        func_73285_a(15, false, true, Packet15Place.class);
        func_73285_a(16, true, true, Packet16BlockItemSwitch.class);
        func_73285_a(17, true, false, Packet17Sleep.class);
        func_73285_a(18, true, true, Packet18Animation.class);
        func_73285_a(19, false, true, Packet19EntityAction.class);
        func_73285_a(20, true, false, Packet20NamedEntitySpawn.class);
        func_73285_a(22, true, false, Packet22Collect.class);
        func_73285_a(23, true, false, Packet23VehicleSpawn.class);
        func_73285_a(24, true, false, Packet24MobSpawn.class);
        func_73285_a(25, true, false, Packet25EntityPainting.class);
        func_73285_a(26, true, false, Packet26EntityExpOrb.class);
        func_73285_a(28, true, false, Packet28EntityVelocity.class);
        func_73285_a(29, true, false, Packet29DestroyEntity.class);
        func_73285_a(30, true, false, Packet30Entity.class);
        func_73285_a(31, true, false, Packet31RelEntityMove.class);
        func_73285_a(32, true, false, Packet32EntityLook.class);
        func_73285_a(33, true, false, Packet33RelEntityMoveLook.class);
        func_73285_a(34, true, false, Packet34EntityTeleport.class);
        func_73285_a(35, true, false, Packet35EntityHeadRotation.class);
        func_73285_a(38, true, false, Packet38EntityStatus.class);
        func_73285_a(39, true, false, Packet39AttachEntity.class);
        func_73285_a(40, true, false, Packet40EntityMetadata.class);
        func_73285_a(41, true, false, Packet41EntityEffect.class);
        func_73285_a(42, true, false, Packet42RemoveEntityEffect.class);
        func_73285_a(43, true, false, Packet43Experience.class);
        func_73285_a(51, true, false, Packet51MapChunk.class);
        func_73285_a(52, true, false, Packet52MultiBlockChange.class);
        func_73285_a(53, true, false, Packet53BlockChange.class);
        func_73285_a(54, true, false, Packet54PlayNoteBlock.class);
        func_73285_a(55, true, false, Packet55BlockDestroy.class);
        func_73285_a(56, true, false, Packet56MapChunks.class);
        func_73285_a(60, true, false, Packet60Explosion.class);
        func_73285_a(61, true, false, Packet61DoorChange.class);
        func_73285_a(62, true, false, Packet62LevelSound.class);
        func_73285_a(63, true, false, Packet63WorldParticles.class);
        func_73285_a(70, true, false, Packet70GameEvent.class);
        func_73285_a(71, true, false, Packet71Weather.class);
        func_73285_a(100, true, false, Packet100OpenWindow.class);
        func_73285_a(101, true, true, Packet101CloseWindow.class);
        func_73285_a(102, false, true, Packet102WindowClick.class);
        func_73285_a(103, true, false, Packet103SetSlot.class);
        func_73285_a(104, true, false, Packet104WindowItems.class);
        func_73285_a(105, true, false, Packet105UpdateProgressbar.class);
        func_73285_a(106, true, true, Packet106Transaction.class);
        func_73285_a(107, true, true, Packet107CreativeSetSlot.class);
        func_73285_a(108, false, true, Packet108EnchantItem.class);
        func_73285_a(130, true, true, Packet130UpdateSign.class);
        func_73285_a(131, true, false, Packet131MapData.class);
        func_73285_a(132, true, false, Packet132TileEntityData.class);
        func_73285_a(200, true, false, Packet200Statistic.class);
        func_73285_a(201, true, false, Packet201PlayerInfo.class);
        func_73285_a(202, true, true, Packet202PlayerAbilities.class);
        func_73285_a(203, true, true, Packet203AutoComplete.class);
        func_73285_a(204, false, true, Packet204ClientInfo.class);
        func_73285_a(205, false, true, Packet205ClientCommand.class);
        func_73285_a(206, true, false, Packet206SetObjective.class);
        func_73285_a(207, true, false, Packet207SetScore.class);
        func_73285_a(208, true, false, Packet208SetDisplayObjective.class);
        func_73285_a(209, true, false, Packet209SetPlayerTeam.class);
        func_73285_a(250, true, true, Packet250CustomPayload.class);
        func_73285_a(252, true, true, Packet252SharedKey.class);
        func_73285_a(253, true, false, Packet253ServerAuthData.class);
        func_73285_a(254, false, true, Packet254ServerPing.class);
        func_73285_a(255, true, true, Packet255KickDisconnect.class);
    }
}
