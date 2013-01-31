package net.minecraft.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import net.minecraft.world.chunk.Chunk;

public class Packet56MapChunks extends Packet
{
    private int[] field_73589_c;
    private int[] field_73586_d;
    public int[] field_73590_a;
    public int[] field_73588_b;
    private byte[] field_73587_e;
    private byte[][] field_73584_f;
    private int field_73585_g;
    private boolean field_92076_h;
    private byte[] field_73591_h = new byte[0]; // CraftBukkit - remove static
    // CraftBukkit start
    static final ThreadLocal<Deflater> localDeflater = new ThreadLocal<Deflater>()
    {
        @Override
        protected Deflater initialValue()
        {
            // Don't use higher compression level, slows things down too much
            return new Deflater(6);
        }
    };
    // CraftBukkit end

    public Packet56MapChunks() {}

    public Packet56MapChunks(List p_i3324_1_)
    {
        int i = p_i3324_1_.size();
        this.field_73589_c = new int[i];
        this.field_73586_d = new int[i];
        this.field_73590_a = new int[i];
        this.field_73588_b = new int[i];
        this.field_73584_f = new byte[i][];
        this.field_92076_h = !p_i3324_1_.isEmpty() && !((Chunk)p_i3324_1_.get(0)).field_76637_e.field_73011_w.field_76576_e;
        int j = 0;

        for (int k = 0; k < i; ++k)
        {
            Chunk chunk = (Chunk)p_i3324_1_.get(k);
            Packet51MapChunkData packet51mapchunkdata = Packet51MapChunk.func_73594_a(chunk, true, 65535);

            if (field_73591_h.length < j + packet51mapchunkdata.field_74582_a.length)
            {
                byte[] abyte = new byte[j + packet51mapchunkdata.field_74582_a.length];
                System.arraycopy(field_73591_h, 0, abyte, 0, field_73591_h.length);
                field_73591_h = abyte;
            }

            System.arraycopy(packet51mapchunkdata.field_74582_a, 0, field_73591_h, j, packet51mapchunkdata.field_74582_a.length);
            j += packet51mapchunkdata.field_74582_a.length;
            this.field_73589_c[k] = chunk.field_76635_g;
            this.field_73586_d[k] = chunk.field_76647_h;
            this.field_73590_a[k] = packet51mapchunkdata.field_74580_b;
            this.field_73588_b[k] = packet51mapchunkdata.field_74581_c;
            this.field_73584_f[k] = packet51mapchunkdata.field_74582_a;
        }

        /* CraftBukkit start - moved to compress()
        Deflater deflater = new Deflater(-1);

        try {
            deflater.setInput(buildBuffer, 0, j);
            deflater.finish();
            this.buffer = new byte[j];
            this.size = deflater.deflate(this.buffer);
        } finally {
            deflater.end();
        }
        */
    }

    // Add compression method
    public void compress()
    {
        if (this.field_73587_e != null)
        {
            return;
        }

        Deflater deflater = localDeflater.get();
        deflater.reset();
        deflater.setInput(this.field_73591_h);
        deflater.finish();
        this.field_73587_e = new byte[this.field_73591_h.length + 100];
        this.field_73585_g = deflater.deflate(this.field_73587_e);
    }
    // CraftBukkit end

    public void func_73267_a(DataInputStream p_73267_1_) throws IOException   // CraftBukkit - throws IOException
    {
        short short1 = p_73267_1_.readShort();
        this.field_73585_g = p_73267_1_.readInt();
        this.field_92076_h = p_73267_1_.readBoolean();
        this.field_73589_c = new int[short1];
        this.field_73586_d = new int[short1];
        this.field_73590_a = new int[short1];
        this.field_73588_b = new int[short1];
        this.field_73584_f = new byte[short1][];

        if (field_73591_h.length < this.field_73585_g)
        {
            field_73591_h = new byte[this.field_73585_g];
        }

        p_73267_1_.readFully(field_73591_h, 0, this.field_73585_g);
        byte[] abyte = new byte[196864 * short1];
        Inflater inflater = new Inflater();
        inflater.setInput(field_73591_h, 0, this.field_73585_g);

        try
        {
            inflater.inflate(abyte);
        }
        catch (DataFormatException dataformatexception)
        {
            throw new IOException("Bad compressed data format");
        }
        finally
        {
            inflater.end();
        }

        int i = 0;

        for (int j = 0; j < short1; ++j)
        {
            this.field_73589_c[j] = p_73267_1_.readInt();
            this.field_73586_d[j] = p_73267_1_.readInt();
            this.field_73590_a[j] = p_73267_1_.readShort();
            this.field_73588_b[j] = p_73267_1_.readShort();
            int k = 0;
            int l = 0;
            int i1;

            for (i1 = 0; i1 < 16; ++i1)
            {
                k += this.field_73590_a[j] >> i1 & 1;
                l += this.field_73588_b[j] >> i1 & 1;
            }

            i1 = 2048 * 4 * k + 256;
            i1 += 2048 * l;

            if (this.field_92076_h)
            {
                i1 += 2048 * k;
            }

            this.field_73584_f[j] = new byte[i1];
            System.arraycopy(abyte, i, this.field_73584_f[j], 0, i1);
            i += i1;
        }
    }

    public void func_73273_a(DataOutputStream p_73273_1_) throws IOException   // CraftBukkit - throws IOException
    {
        compress(); // CraftBukkit
        p_73273_1_.writeShort(this.field_73589_c.length);
        p_73273_1_.writeInt(this.field_73585_g);
        p_73273_1_.writeBoolean(this.field_92076_h);
        p_73273_1_.write(this.field_73587_e, 0, this.field_73585_g);

        for (int i = 0; i < this.field_73589_c.length; ++i)
        {
            p_73273_1_.writeInt(this.field_73589_c[i]);
            p_73273_1_.writeInt(this.field_73586_d[i]);
            p_73273_1_.writeShort((short)(this.field_73590_a[i] & 65535));
            p_73273_1_.writeShort((short)(this.field_73588_b[i] & 65535));
        }
    }

    public void func_73279_a(NetHandler p_73279_1_)
    {
        p_73279_1_.func_72453_a(this);
    }

    public int func_73284_a()
    {
        return 6 + this.field_73585_g + 12 * this.func_73581_d();
    }

    public int func_73581_d()
    {
        return this.field_73589_c.length;
    }
}
