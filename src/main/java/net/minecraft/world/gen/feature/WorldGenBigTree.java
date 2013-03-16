package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling.TreeGenerator;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import org.bukkit.BlockChangeDelegate; // CraftBukkit

public class WorldGenBigTree extends WorldGenerator implements TreeGenerator   // CraftBukkit add interface
{
    static final byte[] field_76507_a = new byte[] {(byte)2, (byte)0, (byte)0, (byte)1, (byte)2, (byte)1};
    Random field_76505_b = new Random();
    BlockChangeDelegate field_76506_c; // CraftBukkit
    int[] field_76503_d = new int[] {0, 0, 0};
    int field_76504_e = 0;
    int field_76501_f;
    double field_76502_g = 0.618D;
    double field_76514_h = 1.0D;
    double field_76515_i = 0.381D;
    double field_76512_j = 1.0D;
    double field_76513_k = 1.0D;
    int field_76510_l = 1;
    int field_76511_m = 12;
    int field_76508_n = 4;
    int[][] field_76509_o;

    public WorldGenBigTree(boolean p_i3784_1_)
    {
        super(p_i3784_1_);
    }

    void func_76489_a()
    {
        this.field_76501_f = (int)((double)this.field_76504_e * this.field_76502_g);

        if (this.field_76501_f >= this.field_76504_e)
        {
            this.field_76501_f = this.field_76504_e - 1;
        }

        int i = (int)(1.382D + Math.pow(this.field_76513_k * (double)this.field_76504_e / 13.0D, 2.0D));

        if (i < 1)
        {
            i = 1;
        }

        int[][] aint = new int[i * this.field_76504_e][4];
        int j = this.field_76503_d[1] + this.field_76504_e - this.field_76508_n;
        int k = 1;
        int l = this.field_76503_d[1] + this.field_76501_f;
        int i1 = j - this.field_76503_d[1];
        aint[0][0] = this.field_76503_d[0];
        aint[0][1] = j;
        aint[0][2] = this.field_76503_d[2];
        aint[0][3] = l;
        --j;

        while (i1 >= 0)
        {
            int j1 = 0;
            float f = this.func_76490_a(i1);

            if (f < 0.0F)
            {
                --j;
                --i1;
            }
            else
            {
                for (double d0 = 0.5D; j1 < i; ++j1)
                {
                    double d1 = this.field_76512_j * (double)f * ((double)this.field_76505_b.nextFloat() + 0.328D);
                    double d2 = (double)this.field_76505_b.nextFloat() * 2.0D * Math.PI;
                    int k1 = MathHelper.func_76128_c(d1 * Math.sin(d2) + (double)this.field_76503_d[0] + d0);
                    int l1 = MathHelper.func_76128_c(d1 * Math.cos(d2) + (double)this.field_76503_d[2] + d0);
                    int[] aint1 = new int[] {k1, j, l1};
                    int[] aint2 = new int[] {k1, j + this.field_76508_n, l1};

                    if (this.func_76496_a(aint1, aint2) == -1)
                    {
                        int[] aint3 = new int[] {this.field_76503_d[0], this.field_76503_d[1], this.field_76503_d[2]};
                        double d3 = Math.sqrt(Math.pow((double)Math.abs(this.field_76503_d[0] - aint1[0]), 2.0D) + Math.pow((double)Math.abs(this.field_76503_d[2] - aint1[2]), 2.0D));
                        double d4 = d3 * this.field_76515_i;

                        if ((double)aint1[1] - d4 > (double)l)
                        {
                            aint3[1] = l;
                        }
                        else
                        {
                            aint3[1] = (int)((double)aint1[1] - d4);
                        }

                        if (this.func_76496_a(aint3, aint1) == -1)
                        {
                            aint[k][0] = k1;
                            aint[k][1] = j;
                            aint[k][2] = l1;
                            aint[k][3] = aint3[1];
                            ++k;
                        }
                    }
                }

                --j;
                --i1;
            }
        }

        this.field_76509_o = new int[k][4];
        System.arraycopy(aint, 0, this.field_76509_o, 0, k);
    }

    void func_76492_a(int p_76492_1_, int p_76492_2_, int p_76492_3_, float p_76492_4_, byte p_76492_5_, int p_76492_6_)
    {
        int i1 = (int)((double)p_76492_4_ + 0.618D);
        byte b1 = field_76507_a[p_76492_5_];
        byte b2 = field_76507_a[p_76492_5_ + 3];
        int[] aint = new int[] {p_76492_1_, p_76492_2_, p_76492_3_};
        int[] aint1 = new int[] {0, 0, 0};
        int j1 = -i1;
        int k1 = -i1;

        for (aint1[p_76492_5_] = aint[p_76492_5_]; j1 <= i1; ++j1)
        {
            aint1[b1] = aint[b1] + j1;
            k1 = -i1;

            while (k1 <= i1)
            {
                double d0 = Math.pow((double)Math.abs(j1) + 0.5D, 2.0D) + Math.pow((double)Math.abs(k1) + 0.5D, 2.0D);

                if (d0 > (double)(p_76492_4_ * p_76492_4_))
                {
                    ++k1;
                }
                else
                {
                    aint1[b2] = aint[b2] + k1;
                    int l1 = this.field_76506_c.getTypeId(aint1[0], aint1[1], aint1[2]);

                    if (l1 != 0 && l1 != Block.field_71952_K.field_71990_ca)
                    {
                        ++k1;
                    }
                    else
                    {
                        this.setTypeAndData(this.field_76506_c, aint1[0], aint1[1], aint1[2], p_76492_6_, 0);
                        ++k1;
                    }
                }
            }
        }
    }

    float func_76490_a(int p_76490_1_)
    {
        if ((double)p_76490_1_ < (double)((float)this.field_76504_e) * 0.3D)
        {
            return -1.618F;
        }
        else
        {
            float f = (float)this.field_76504_e / 2.0F;
            float f1 = (float)this.field_76504_e / 2.0F - (float)p_76490_1_;
            float f2;

            if (f1 == 0.0F)
            {
                f2 = f;
            }
            else if (Math.abs(f1) >= f)
            {
                f2 = 0.0F;
            }
            else
            {
                f2 = (float)Math.sqrt(Math.pow((double)Math.abs(f), 2.0D) - Math.pow((double)Math.abs(f1), 2.0D));
            }

            f2 *= 0.5F;
            return f2;
        }
    }

    float func_76495_b(int p_76495_1_)
    {
        return p_76495_1_ >= 0 && p_76495_1_ < this.field_76508_n ? (p_76495_1_ != 0 && p_76495_1_ != this.field_76508_n - 1 ? 3.0F : 2.0F) : -1.0F;
    }

    void func_76491_a(int p_76491_1_, int p_76491_2_, int p_76491_3_)
    {
        int l = p_76491_2_;

        for (int i1 = p_76491_2_ + this.field_76508_n; l < i1; ++l)
        {
            float f = this.func_76495_b(l - p_76491_2_);
            this.func_76492_a(p_76491_1_, l, p_76491_3_, f, (byte)1, Block.field_71952_K.field_71990_ca);
        }
    }

    void func_76500_a(int[] p_76500_1_, int[] p_76500_2_, int p_76500_3_)
    {
        int[] aint2 = new int[] {0, 0, 0};
        byte b0 = 0;
        byte b1;

        for (b1 = 0; b0 < 3; ++b0)
        {
            aint2[b0] = p_76500_2_[b0] - p_76500_1_[b0];

            if (Math.abs(aint2[b0]) > Math.abs(aint2[b1]))
            {
                b1 = b0;
            }
        }

        if (aint2[b1] != 0)
        {
            byte b2 = field_76507_a[b1];
            byte b3 = field_76507_a[b1 + 3];
            byte b4;

            if (aint2[b1] > 0)
            {
                b4 = 1;
            }
            else
            {
                b4 = -1;
            }

            double d0 = (double)aint2[b2] / (double)aint2[b1];
            double d1 = (double)aint2[b3] / (double)aint2[b1];
            int[] aint3 = new int[] {0, 0, 0};
            int j = 0;

            for (int k = aint2[b1] + b4; j != k; j += b4)
            {
                aint3[b1] = MathHelper.func_76128_c((double)(p_76500_1_[b1] + j) + 0.5D);
                aint3[b2] = MathHelper.func_76128_c((double)p_76500_1_[b2] + (double)j * d0 + 0.5D);
                aint3[b3] = MathHelper.func_76128_c((double)p_76500_1_[b3] + (double)j * d1 + 0.5D);
                byte b5 = 0;
                int l = Math.abs(aint3[0] - p_76500_1_[0]);
                int i1 = Math.abs(aint3[2] - p_76500_1_[2]);
                int j1 = Math.max(l, i1);

                if (j1 > 0)
                {
                    if (l == j1)
                    {
                        b5 = 4;
                    }
                    else if (i1 == j1)
                    {
                        b5 = 8;
                    }
                }

                this.setTypeAndData(this.field_76506_c, aint3[0], aint3[1], aint3[2], p_76500_3_, b5);
            }
        }
    }

    void func_76498_b()
    {
        int i = 0;

        for (int j = this.field_76509_o.length; i < j; ++i)
        {
            int k = this.field_76509_o[i][0];
            int l = this.field_76509_o[i][1];
            int i1 = this.field_76509_o[i][2];
            this.func_76491_a(k, l, i1);
        }
    }

    boolean func_76493_c(int p_76493_1_)
    {
        return (double)p_76493_1_ >= (double)this.field_76504_e * 0.2D;
    }

    void func_76499_c()
    {
        int i = this.field_76503_d[0];
        int j = this.field_76503_d[1];
        int k = this.field_76503_d[1] + this.field_76501_f;
        int l = this.field_76503_d[2];
        int[] aint = new int[] {i, j, l};
        int[] aint1 = new int[] {i, k, l};
        this.func_76500_a(aint, aint1, Block.field_71951_J.field_71990_ca);

        if (this.field_76510_l == 2)
        {
            ++aint[0];
            ++aint1[0];
            this.func_76500_a(aint, aint1, Block.field_71951_J.field_71990_ca);
            ++aint[2];
            ++aint1[2];
            this.func_76500_a(aint, aint1, Block.field_71951_J.field_71990_ca);
            aint[0] += -1;
            aint1[0] += -1;
            this.func_76500_a(aint, aint1, Block.field_71951_J.field_71990_ca);
        }
    }

    void func_76494_d()
    {
        int i = 0;
        int j = this.field_76509_o.length;

        for (int[] aint = new int[] {this.field_76503_d[0], this.field_76503_d[1], this.field_76503_d[2]}; i < j; ++i)
        {
            int[] aint1 = this.field_76509_o[i];
            int[] aint2 = new int[] {aint1[0], aint1[1], aint1[2]};
            aint[1] = aint1[3];
            int k = aint[1] - this.field_76503_d[1];

            if (this.func_76493_c(k))
            {
                this.func_76500_a(aint, aint2, (byte)Block.field_71951_J.field_71990_ca);
            }
        }
    }

    int func_76496_a(int[] p_76496_1_, int[] p_76496_2_)
    {
        int[] aint2 = new int[] {0, 0, 0};
        byte b0 = 0;
        byte b1;

        for (b1 = 0; b0 < 3; ++b0)
        {
            aint2[b0] = p_76496_2_[b0] - p_76496_1_[b0];

            if (Math.abs(aint2[b0]) > Math.abs(aint2[b1]))
            {
                b1 = b0;
            }
        }

        if (aint2[b1] == 0)
        {
            return -1;
        }
        else
        {
            byte b2 = field_76507_a[b1];
            byte b3 = field_76507_a[b1 + 3];
            byte b4;

            if (aint2[b1] > 0)
            {
                b4 = 1;
            }
            else
            {
                b4 = -1;
            }

            double d0 = (double)aint2[b2] / (double)aint2[b1];
            double d1 = (double)aint2[b3] / (double)aint2[b1];
            int[] aint3 = new int[] {0, 0, 0};
            int i = 0;
            int j;

            for (j = aint2[b1] + b4; i != j; i += b4)
            {
                aint3[b1] = p_76496_1_[b1] + i;
                aint3[b2] = MathHelper.func_76128_c((double)p_76496_1_[b2] + (double)i * d0);
                aint3[b3] = MathHelper.func_76128_c((double)p_76496_1_[b3] + (double)i * d1);
                int k = this.field_76506_c.getTypeId(aint3[0], aint3[1], aint3[2]);

                if ((k != 0 && k != Block.field_71952_K.field_71990_ca) || aint3[1] >= 256)   // CraftBukkit - fix trees wrapping around
                {
                    break;
                }
            }

            return i == j ? -1 : Math.abs(i);
        }
    }

    boolean func_76497_e()
    {
        int[] aint = new int[] {this.field_76503_d[0], this.field_76503_d[1], this.field_76503_d[2]};
        int[] aint1 = new int[] {this.field_76503_d[0], this.field_76503_d[1] + this.field_76504_e - 1, this.field_76503_d[2]};
        int i = this.field_76506_c.getTypeId(this.field_76503_d[0], this.field_76503_d[1] - 1, this.field_76503_d[2]);

        if (i != 2 && i != 3)
        {
            return false;
        }
        else
        {
            int j = this.func_76496_a(aint, aint1);

            if (j == -1)
            {
                return true;
            }
            else if (j < 6)
            {
                return false;
            }
            else
            {
                this.field_76504_e = j;
                return true;
            }
        }
    }

    public void func_76487_a(double p_76487_1_, double p_76487_3_, double p_76487_5_)
    {
        this.field_76511_m = (int)(p_76487_1_ * 12.0D);

        if (p_76487_1_ > 0.5D)
        {
            this.field_76508_n = 5;
        }

        this.field_76512_j = p_76487_3_;
        this.field_76513_k = p_76487_5_;
    }

    public boolean func_76484_a(World p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_)
    {
        // CraftBukkit start - moved to generate
        // sk: The idea is to have (our) WorldServer implement
        // BlockChangeDelegate and then we can implicitly cast World to
        // WorldServer (a safe cast, AFAIK) and no code will be broken. This
        // then allows plugins to catch manually-invoked generation events
        return this.generate((BlockChangeDelegate) p_76484_1_, p_76484_2_, p_76484_3_, p_76484_4_, p_76484_5_);
    }

    public boolean generate(BlockChangeDelegate p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_)
    {
        // CraftBukkit end
        this.field_76506_c = p_76484_1_;
        long l = p_76484_2_.nextLong();
        this.field_76505_b.setSeed(l);
        this.field_76503_d[0] = p_76484_3_;
        this.field_76503_d[1] = p_76484_4_;
        this.field_76503_d[2] = p_76484_5_;

        if (this.field_76504_e == 0)
        {
            this.field_76504_e = 5 + this.field_76505_b.nextInt(this.field_76511_m);
        }

        if (!this.func_76497_e())
        {
            return false;
        }
        else
        {
            this.func_76489_a();
            this.func_76498_b();
            this.func_76499_c();
            this.func_76494_d();
            return true;
        }
    }
}
