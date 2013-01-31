package net.minecraft.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public class Packet51MapChunk extends Packet
{
    public int field_73601_a;
    public int field_73599_b;
    public int field_73600_c;
    public int field_73597_d;
    private byte[] field_73595_f;
    private byte[] field_73596_g;
    public boolean field_73598_e;
    private int field_73602_h;
    private static byte[] field_73603_i = new byte[196864];

    public Packet51MapChunk()
    {
        this.field_73287_r = true;
    }

    public Packet51MapChunk(Chunk p_i3323_1_, boolean p_i3323_2_, int p_i3323_3_)
    {
        this.field_73287_r = true;
        this.field_73601_a = p_i3323_1_.field_76635_g;
        this.field_73599_b = p_i3323_1_.field_76647_h;
        this.field_73598_e = p_i3323_2_;
        Packet51MapChunkData packet51mapchunkdata = func_73594_a(p_i3323_1_, p_i3323_2_, p_i3323_3_);
        Deflater deflater = new Deflater(-1);
        this.field_73597_d = packet51mapchunkdata.field_74581_c;
        this.field_73600_c = packet51mapchunkdata.field_74580_b;

        try
        {
            this.field_73596_g = packet51mapchunkdata.field_74582_a;
            deflater.setInput(packet51mapchunkdata.field_74582_a, 0, packet51mapchunkdata.field_74582_a.length);
            deflater.finish();
            this.field_73595_f = new byte[packet51mapchunkdata.field_74582_a.length];
            this.field_73602_h = deflater.deflate(this.field_73595_f);
        }
        finally
        {
            deflater.end();
        }
    }

    public void func_73267_a(DataInputStream p_73267_1_) throws IOException   // CraftBukkit - throws IOException
    {
        this.field_73601_a = p_73267_1_.readInt();
        this.field_73599_b = p_73267_1_.readInt();
        this.field_73598_e = p_73267_1_.readBoolean();
        this.field_73600_c = p_73267_1_.readShort();
        this.field_73597_d = p_73267_1_.readShort();
        this.field_73602_h = p_73267_1_.readInt();

        if (field_73603_i.length < this.field_73602_h)
        {
            field_73603_i = new byte[this.field_73602_h];
        }

        p_73267_1_.readFully(field_73603_i, 0, this.field_73602_h);
        int i = 0;
        int j;

        for (j = 0; j < 16; ++j)
        {
            i += this.field_73600_c >> j & 1;
        }

        j = 12288 * i;

        if (this.field_73598_e)
        {
            j += 256;
        }

        this.field_73596_g = new byte[j];
        Inflater inflater = new Inflater();
        inflater.setInput(field_73603_i, 0, this.field_73602_h);

        try
        {
            inflater.inflate(this.field_73596_g);
        }
        catch (DataFormatException dataformatexception)
        {
            throw new IOException("Bad compressed data format");
        }
        finally
        {
            inflater.end();
        }
    }

    public void func_73273_a(DataOutputStream p_73273_1_) throws IOException   // CraftBukkit - throws IOException
    {
        p_73273_1_.writeInt(this.field_73601_a);
        p_73273_1_.writeInt(this.field_73599_b);
        p_73273_1_.writeBoolean(this.field_73598_e);
        p_73273_1_.writeShort((short)(this.field_73600_c & 65535));
        p_73273_1_.writeShort((short)(this.field_73597_d & 65535));
        p_73273_1_.writeInt(this.field_73602_h);
        p_73273_1_.write(this.field_73595_f, 0, this.field_73602_h);
    }

    public void func_73279_a(NetHandler p_73279_1_)
    {
        p_73279_1_.func_72463_a(this);
    }

    public int func_73284_a()
    {
        return 17 + this.field_73602_h;
    }

    public static Packet51MapChunkData func_73594_a(Chunk p_73594_0_, boolean p_73594_1_, int p_73594_2_)
    {
        int j = 0;
        ExtendedBlockStorage[] aextendedblockstorage = p_73594_0_.func_76587_i();
        int k = 0;
        Packet51MapChunkData packet51mapchunkdata = new Packet51MapChunkData();
        byte[] abyte = field_73603_i;

        if (p_73594_1_)
        {
            p_73594_0_.field_76642_o = true;
        }

        int l;

        for (l = 0; l < aextendedblockstorage.length; ++l)
        {
            if (aextendedblockstorage[l] != null && (!p_73594_1_ || !aextendedblockstorage[l].func_76663_a()) && (p_73594_2_ & 1 << l) != 0)
            {
                packet51mapchunkdata.field_74580_b |= 1 << l;

                if (aextendedblockstorage[l].func_76660_i() != null)
                {
                    packet51mapchunkdata.field_74581_c |= 1 << l;
                    ++k;
                }
            }
        }

        for (l = 0; l < aextendedblockstorage.length; ++l)
        {
            if (aextendedblockstorage[l] != null && (!p_73594_1_ || !aextendedblockstorage[l].func_76663_a()) && (p_73594_2_ & 1 << l) != 0)
            {
                byte[] abyte1 = aextendedblockstorage[l].func_76658_g();
                System.arraycopy(abyte1, 0, abyte, j, abyte1.length);
                j += abyte1.length;
            }
        }

        NibbleArray nibblearray;

        for (l = 0; l < aextendedblockstorage.length; ++l)
        {
            if (aextendedblockstorage[l] != null && (!p_73594_1_ || !aextendedblockstorage[l].func_76663_a()) && (p_73594_2_ & 1 << l) != 0)
            {
                nibblearray = aextendedblockstorage[l].func_76669_j();
                System.arraycopy(nibblearray.field_76585_a, 0, abyte, j, nibblearray.field_76585_a.length);
                j += nibblearray.field_76585_a.length;
            }
        }

        for (l = 0; l < aextendedblockstorage.length; ++l)
        {
            if (aextendedblockstorage[l] != null && (!p_73594_1_ || !aextendedblockstorage[l].func_76663_a()) && (p_73594_2_ & 1 << l) != 0)
            {
                nibblearray = aextendedblockstorage[l].func_76661_k();
                System.arraycopy(nibblearray.field_76585_a, 0, abyte, j, nibblearray.field_76585_a.length);
                j += nibblearray.field_76585_a.length;
            }
        }

        if (!p_73594_0_.field_76637_e.field_73011_w.field_76576_e)
        {
            for (l = 0; l < aextendedblockstorage.length; ++l)
            {
                if (aextendedblockstorage[l] != null && (!p_73594_1_ || !aextendedblockstorage[l].func_76663_a()) && (p_73594_2_ & 1 << l) != 0)
                {
                    nibblearray = aextendedblockstorage[l].func_76671_l();
                    System.arraycopy(nibblearray.field_76585_a, 0, abyte, j, nibblearray.field_76585_a.length);
                    j += nibblearray.field_76585_a.length;
                }
            }
        }

        if (k > 0)
        {
            for (l = 0; l < aextendedblockstorage.length; ++l)
            {
                if (aextendedblockstorage[l] != null && (!p_73594_1_ || !aextendedblockstorage[l].func_76663_a()) && aextendedblockstorage[l].func_76660_i() != null && (p_73594_2_ & 1 << l) != 0)
                {
                    nibblearray = aextendedblockstorage[l].func_76660_i();
                    System.arraycopy(nibblearray.field_76585_a, 0, abyte, j, nibblearray.field_76585_a.length);
                    j += nibblearray.field_76585_a.length;
                }
            }
        }

        if (p_73594_1_)
        {
            byte[] abyte2 = p_73594_0_.func_76605_m();
            System.arraycopy(abyte2, 0, abyte, j, abyte2.length);
            j += abyte2.length;
        }

        packet51mapchunkdata.field_74582_a = new byte[j];
        System.arraycopy(abyte, 0, packet51mapchunkdata.field_74582_a, 0, j);
        return packet51mapchunkdata;
    }
}
