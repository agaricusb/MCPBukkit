package net.minecraft.world.chunk.storage;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

public class RegionFile
{
    private static final byte[] field_76720_a = new byte[4096];
    private final File field_76718_b;
    private RandomAccessFile field_76719_c;
    private final int[] field_76716_d = new int[1024];
    private final int[] field_76717_e = new int[1024];
    private ArrayList field_76714_f;
    private int field_76715_g;
    private long field_76721_h = 0L;

    public RegionFile(File p_i3777_1_)
    {
        this.field_76718_b = p_i3777_1_;
        this.field_76715_g = 0;

        try
        {
            if (p_i3777_1_.exists())
            {
                this.field_76721_h = p_i3777_1_.lastModified();
            }

            this.field_76719_c = new RandomAccessFile(p_i3777_1_, "rw");
            int i;

            if (this.field_76719_c.length() < 4096L)
            {
                for (i = 0; i < 1024; ++i)
                {
                    this.field_76719_c.writeInt(0);
                }

                for (i = 0; i < 1024; ++i)
                {
                    this.field_76719_c.writeInt(0);
                }

                this.field_76715_g += 8192;
            }

            if ((this.field_76719_c.length() & 4095L) != 0L)
            {
                for (i = 0; (long)i < (this.field_76719_c.length() & 4095L); ++i)
                {
                    this.field_76719_c.write(0);
                }
            }

            i = (int)this.field_76719_c.length() / 4096;
            this.field_76714_f = new ArrayList(i);
            int j;

            for (j = 0; j < i; ++j)
            {
                this.field_76714_f.add(Boolean.valueOf(true));
            }

            this.field_76714_f.set(0, Boolean.valueOf(false));
            this.field_76714_f.set(1, Boolean.valueOf(false));
            this.field_76719_c.seek(0L);
            int k;

            for (j = 0; j < 1024; ++j)
            {
                k = this.field_76719_c.readInt();
                this.field_76716_d[j] = k;

                if (k != 0 && (k >> 8) + (k & 255) <= this.field_76714_f.size())
                {
                    for (int l = 0; l < (k & 255); ++l)
                    {
                        this.field_76714_f.set((k >> 8) + l, Boolean.valueOf(false));
                    }
                }
            }

            for (j = 0; j < 1024; ++j)
            {
                k = this.field_76719_c.readInt();
                this.field_76717_e[j] = k;
            }
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
        }
    }

    // CraftBukkit start - this is a copy (sort of) of the method below it, make sure they stay in sync

    public synchronized boolean chunkExists(int i, int j)
    {
        if (this.func_76705_d(i, j))
        {
            return false;
        }
        else
        {
            try
            {
                int k = this.func_76707_e(i, j);

                if (k == 0)
                {
                    return false;
                }
                else
                {
                    int l = k >> 8;
                    int i1 = k & 255;

                    if (l + i1 > this.field_76714_f.size())
                    {
                        return false;
                    }

                    this.field_76719_c.seek((long)(l * 4096));
                    int j1 = this.field_76719_c.readInt();

                    if (j1 > 4096 * i1 || j1 <= 0)
                    {
                        return false;
                    }

                    byte b0 = this.field_76719_c.readByte();

                    if (b0 == 1 || b0 == 2)
                    {
                        return true;
                    }
                }
            }
            catch (IOException ioexception)
            {
                return false;
            }
        }

        return false;
    }

    // CraftBukkit end

    public synchronized DataInputStream func_76704_a(int p_76704_1_, int p_76704_2_)
    {
        if (this.func_76705_d(p_76704_1_, p_76704_2_))
        {
            return null;
        }
        else
        {
            try
            {
                int k = this.func_76707_e(p_76704_1_, p_76704_2_);

                if (k == 0)
                {
                    return null;
                }
                else
                {
                    int l = k >> 8;
                    int i1 = k & 255;

                    if (l + i1 > this.field_76714_f.size())
                    {
                        return null;
                    }
                    else
                    {
                        this.field_76719_c.seek((long)(l * 4096));
                        int j1 = this.field_76719_c.readInt();

                        if (j1 > 4096 * i1)
                        {
                            return null;
                        }
                        else if (j1 <= 0)
                        {
                            return null;
                        }
                        else
                        {
                            byte b0 = this.field_76719_c.readByte();
                            byte[] abyte;

                            if (b0 == 1)
                            {
                                abyte = new byte[j1 - 1];
                                this.field_76719_c.read(abyte);
                                return new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(abyte))));
                            }
                            else if (b0 == 2)
                            {
                                abyte = new byte[j1 - 1];
                                this.field_76719_c.read(abyte);
                                return new DataInputStream(new BufferedInputStream(new InflaterInputStream(new ByteArrayInputStream(abyte))));
                            }
                            else
                            {
                                return null;
                            }
                        }
                    }
                }
            }
            catch (IOException ioexception)
            {
                return null;
            }
        }
    }

    public DataOutputStream func_76710_b(int p_76710_1_, int p_76710_2_)
    {
        return this.func_76705_d(p_76710_1_, p_76710_2_) ? null : new DataOutputStream(new DeflaterOutputStream(new RegionFileChunkBuffer(this, p_76710_1_, p_76710_2_)));
    }

    protected synchronized void func_76706_a(int p_76706_1_, int p_76706_2_, byte[] p_76706_3_, int p_76706_4_)
    {
        try
        {
            int l = this.func_76707_e(p_76706_1_, p_76706_2_);
            int i1 = l >> 8;
            int j1 = l & 255;
            int k1 = (p_76706_4_ + 5) / 4096 + 1;

            if (k1 >= 256)
            {
                return;
            }

            if (i1 != 0 && j1 == k1)
            {
                this.func_76712_a(i1, p_76706_3_, p_76706_4_);
            }
            else
            {
                int l1;

                for (l1 = 0; l1 < j1; ++l1)
                {
                    this.field_76714_f.set(i1 + l1, Boolean.valueOf(true));
                }

                l1 = this.field_76714_f.indexOf(Boolean.valueOf(true));
                int i2 = 0;
                int j2;

                if (l1 != -1)
                {
                    for (j2 = l1; j2 < this.field_76714_f.size(); ++j2)
                    {
                        if (i2 != 0)
                        {
                            if (((Boolean)this.field_76714_f.get(j2)).booleanValue())
                            {
                                ++i2;
                            }
                            else
                            {
                                i2 = 0;
                            }
                        }
                        else if (((Boolean)this.field_76714_f.get(j2)).booleanValue())
                        {
                            l1 = j2;
                            i2 = 1;
                        }

                        if (i2 >= k1)
                        {
                            break;
                        }
                    }
                }

                if (i2 >= k1)
                {
                    i1 = l1;
                    this.func_76711_a(p_76706_1_, p_76706_2_, l1 << 8 | k1);

                    for (j2 = 0; j2 < k1; ++j2)
                    {
                        this.field_76714_f.set(i1 + j2, Boolean.valueOf(false));
                    }

                    this.func_76712_a(i1, p_76706_3_, p_76706_4_);
                }
                else
                {
                    this.field_76719_c.seek(this.field_76719_c.length());
                    i1 = this.field_76714_f.size();

                    for (j2 = 0; j2 < k1; ++j2)
                    {
                        this.field_76719_c.write(field_76720_a);
                        this.field_76714_f.add(Boolean.valueOf(false));
                    }

                    this.field_76715_g += 4096 * k1;
                    this.func_76712_a(i1, p_76706_3_, p_76706_4_);
                    this.func_76711_a(p_76706_1_, p_76706_2_, i1 << 8 | k1);
                }
            }

            this.func_76713_b(p_76706_1_, p_76706_2_, (int)(System.currentTimeMillis() / 1000L));
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
        }
    }

    private void func_76712_a(int p_76712_1_, byte[] p_76712_2_, int p_76712_3_) throws IOException   // CraftBukkit - added throws
    {
        this.field_76719_c.seek((long)(p_76712_1_ * 4096));
        this.field_76719_c.writeInt(p_76712_3_ + 1);
        this.field_76719_c.writeByte(2);
        this.field_76719_c.write(p_76712_2_, 0, p_76712_3_);
    }

    private boolean func_76705_d(int p_76705_1_, int p_76705_2_)
    {
        return p_76705_1_ < 0 || p_76705_1_ >= 32 || p_76705_2_ < 0 || p_76705_2_ >= 32;
    }

    private int func_76707_e(int p_76707_1_, int p_76707_2_)
    {
        return this.field_76716_d[p_76707_1_ + p_76707_2_ * 32];
    }

    public boolean func_76709_c(int p_76709_1_, int p_76709_2_)
    {
        return this.func_76707_e(p_76709_1_, p_76709_2_) != 0;
    }

    private void func_76711_a(int p_76711_1_, int p_76711_2_, int p_76711_3_) throws IOException   // CraftBukkit - added throws
    {
        this.field_76716_d[p_76711_1_ + p_76711_2_ * 32] = p_76711_3_;
        this.field_76719_c.seek((long)((p_76711_1_ + p_76711_2_ * 32) * 4));
        this.field_76719_c.writeInt(p_76711_3_);
    }

    private void func_76713_b(int p_76713_1_, int p_76713_2_, int p_76713_3_) throws IOException   // CraftBukkit - added throws
    {
        this.field_76717_e[p_76713_1_ + p_76713_2_ * 32] = p_76713_3_;
        this.field_76719_c.seek((long)(4096 + (p_76713_1_ + p_76713_2_ * 32) * 4));
        this.field_76719_c.writeInt(p_76713_3_);
    }

    public void func_76708_c() throws IOException   // CraftBukkit - added throws
    {
        if (this.field_76719_c != null)
        {
            this.field_76719_c.close();
        }
    }
}
