package net.minecraft.world.chunk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

// CraftBukkit start
import org.bukkit.Bukkit;
import org.bukkit.Location;
// CraftBukkit end

public class Chunk
{
    public static boolean field_76640_a;
    private ExtendedBlockStorage[] field_76652_q;
    private byte[] field_76651_r;
    public int[] field_76638_b;
    public boolean[] field_76639_c;
    public boolean field_76636_d;
    public World field_76637_e;
    public int[] field_76634_f;
    public final int field_76635_g;
    public final int field_76647_h;
    private boolean field_76650_s;
    public Map field_76648_i;
    public List[] field_76645_j;
    public boolean field_76646_k;
    public boolean field_76643_l;
    public boolean field_76644_m;
    public long field_76641_n;
    public boolean field_76642_o;
    public int field_82912_p;
    private int field_76649_t;
    boolean field_76653_p;

    public Chunk(World p_i3771_1_, int p_i3771_2_, int p_i3771_3_)
    {
        this.field_76652_q = new ExtendedBlockStorage[16];
        this.field_76651_r = new byte[256];
        this.field_76638_b = new int[256];
        this.field_76639_c = new boolean[256];
        this.field_76650_s = false;
        this.field_76648_i = new HashMap();
        this.field_76646_k = false;
        this.field_76643_l = false;
        this.field_76644_m = false;
        this.field_76641_n = 0L;
        this.field_76642_o = false;
        this.field_82912_p = 0;
        this.field_76649_t = 4096;
        this.field_76653_p = false;
        this.field_76645_j = new List[16];
        this.field_76637_e = p_i3771_1_;
        this.field_76635_g = p_i3771_2_;
        this.field_76647_h = p_i3771_3_;
        this.field_76634_f = new int[256];

        for (int k = 0; k < this.field_76645_j.length; ++k)
        {
            this.field_76645_j[k] = new org.bukkit.craftbukkit.util.UnsafeList(); // CraftBukkit - ArrayList -> UnsafeList
        }

        Arrays.fill(this.field_76638_b, -999);
        Arrays.fill(this.field_76651_r, (byte) - 1);

        // CraftBukkit start
        if (!(this instanceof EmptyChunk))
        {
            this.bukkitChunk = new org.bukkit.craftbukkit.CraftChunk(this);
        }
    }

    public org.bukkit.Chunk bukkitChunk;
    public boolean mustSave;
    // CraftBukkit end

    public Chunk(World p_i3772_1_, byte[] p_i3772_2_, int p_i3772_3_, int p_i3772_4_)
    {
        this(p_i3772_1_, p_i3772_3_, p_i3772_4_);
        int k = p_i3772_2_.length / 256;

        for (int l = 0; l < 16; ++l)
        {
            for (int i1 = 0; i1 < 16; ++i1)
            {
                for (int j1 = 0; j1 < k; ++j1)
                {
                    byte b0 = p_i3772_2_[l << 11 | i1 << 7 | j1];

                    if (b0 != 0)
                    {
                        int k1 = j1 >> 4;

                        if (this.field_76652_q[k1] == null)
                        {
                            this.field_76652_q[k1] = new ExtendedBlockStorage(k1 << 4, !p_i3772_1_.field_73011_w.field_76576_e);
                        }

                        this.field_76652_q[k1].func_76655_a(l, j1 & 15, i1, b0);
                    }
                }
            }
        }
    }

    public boolean func_76600_a(int p_76600_1_, int p_76600_2_)
    {
        return p_76600_1_ == this.field_76635_g && p_76600_2_ == this.field_76647_h;
    }

    public int func_76611_b(int p_76611_1_, int p_76611_2_)
    {
        return this.field_76634_f[p_76611_2_ << 4 | p_76611_1_];
    }

    public int func_76625_h()
    {
        for (int i = this.field_76652_q.length - 1; i >= 0; --i)
        {
            if (this.field_76652_q[i] != null)
            {
                return this.field_76652_q[i].func_76662_d();
            }
        }

        return 0;
    }

    public ExtendedBlockStorage[] func_76587_i()
    {
        return this.field_76652_q;
    }

    public void func_76603_b()
    {
        int i = this.func_76625_h();
        this.field_82912_p = Integer.MAX_VALUE;
        int j;
        int k;

        for (j = 0; j < 16; ++j)
        {
            k = 0;

            while (k < 16)
            {
                this.field_76638_b[j + (k << 4)] = -999;
                int l = i + 16 - 1;

                while (true)
                {
                    if (l > 0)
                    {
                        if (this.func_76596_b(j, l - 1, k) == 0)
                        {
                            --l;
                            continue;
                        }

                        this.field_76634_f[k << 4 | j] = l;

                        if (l < this.field_82912_p)
                        {
                            this.field_82912_p = l;
                        }
                    }

                    if (!this.field_76637_e.field_73011_w.field_76576_e)
                    {
                        l = 15;
                        int i1 = i + 16 - 1;

                        do
                        {
                            l -= this.func_76596_b(j, i1, k);

                            if (l > 0)
                            {
                                ExtendedBlockStorage extendedblockstorage = this.field_76652_q[i1 >> 4];

                                if (extendedblockstorage != null)
                                {
                                    extendedblockstorage.func_76657_c(j, i1 & 15, k, l);
                                    this.field_76637_e.func_72902_n((this.field_76635_g << 4) + j, i1, (this.field_76647_h << 4) + k);
                                }
                            }

                            --i1;
                        }
                        while (i1 > 0 && l > 0);
                    }

                    ++k;
                    break;
                }
            }
        }

        this.field_76643_l = true;

        for (j = 0; j < 16; ++j)
        {
            for (k = 0; k < 16; ++k)
            {
                this.func_76595_e(j, k);
            }
        }
    }

    private void func_76595_e(int p_76595_1_, int p_76595_2_)
    {
        this.field_76639_c[p_76595_1_ + p_76595_2_ * 16] = true;
        this.field_76650_s = true;
    }

    private void func_76593_q()
    {
        this.field_76637_e.field_72984_F.func_76320_a("recheckGaps");

        if (this.field_76637_e.func_72873_a(this.field_76635_g * 16 + 8, 0, this.field_76647_h * 16 + 8, 16))
        {
            for (int i = 0; i < 16; ++i)
            {
                for (int j = 0; j < 16; ++j)
                {
                    if (this.field_76639_c[i + j * 16])
                    {
                        this.field_76639_c[i + j * 16] = false;
                        int k = this.func_76611_b(i, j);
                        int l = this.field_76635_g * 16 + i;
                        int i1 = this.field_76647_h * 16 + j;
                        int j1 = this.field_76637_e.func_82734_g(l - 1, i1);
                        int k1 = this.field_76637_e.func_82734_g(l + 1, i1);
                        int l1 = this.field_76637_e.func_82734_g(l, i1 - 1);
                        int i2 = this.field_76637_e.func_82734_g(l, i1 + 1);

                        if (k1 < j1)
                        {
                            j1 = k1;
                        }

                        if (l1 < j1)
                        {
                            j1 = l1;
                        }

                        if (i2 < j1)
                        {
                            j1 = i2;
                        }

                        this.func_76599_g(l, i1, j1);
                        this.func_76599_g(l - 1, i1, k);
                        this.func_76599_g(l + 1, i1, k);
                        this.func_76599_g(l, i1 - 1, k);
                        this.func_76599_g(l, i1 + 1, k);
                    }
                }
            }

            this.field_76650_s = false;
        }

        this.field_76637_e.field_72984_F.func_76319_b();
    }

    private void func_76599_g(int p_76599_1_, int p_76599_2_, int p_76599_3_)
    {
        int l = this.field_76637_e.func_72976_f(p_76599_1_, p_76599_2_);

        if (l > p_76599_3_)
        {
            this.func_76609_d(p_76599_1_, p_76599_2_, p_76599_3_, l + 1);
        }
        else if (l < p_76599_3_)
        {
            this.func_76609_d(p_76599_1_, p_76599_2_, l, p_76599_3_ + 1);
        }
    }

    private void func_76609_d(int p_76609_1_, int p_76609_2_, int p_76609_3_, int p_76609_4_)
    {
        if (p_76609_4_ > p_76609_3_ && this.field_76637_e.func_72873_a(p_76609_1_, 0, p_76609_2_, 16))
        {
            for (int i1 = p_76609_3_; i1 < p_76609_4_; ++i1)
            {
                this.field_76637_e.func_72936_c(EnumSkyBlock.Sky, p_76609_1_, i1, p_76609_2_);
            }

            this.field_76643_l = true;
        }
    }

    private void func_76615_h(int p_76615_1_, int p_76615_2_, int p_76615_3_)
    {
        int l = this.field_76634_f[p_76615_3_ << 4 | p_76615_1_] & 255;
        int i1 = l;

        if (p_76615_2_ > l)
        {
            i1 = p_76615_2_;
        }

        while (i1 > 0 && this.func_76596_b(p_76615_1_, i1 - 1, p_76615_3_) == 0)
        {
            --i1;
        }

        if (i1 != l)
        {
            this.field_76637_e.func_72975_g(p_76615_1_ + this.field_76635_g * 16, p_76615_3_ + this.field_76647_h * 16, i1, l);
            this.field_76634_f[p_76615_3_ << 4 | p_76615_1_] = i1;
            int j1 = this.field_76635_g * 16 + p_76615_1_;
            int k1 = this.field_76647_h * 16 + p_76615_3_;
            int l1;
            int i2;

            if (!this.field_76637_e.field_73011_w.field_76576_e)
            {
                ExtendedBlockStorage extendedblockstorage;

                if (i1 < l)
                {
                    for (l1 = i1; l1 < l; ++l1)
                    {
                        extendedblockstorage = this.field_76652_q[l1 >> 4];

                        if (extendedblockstorage != null)
                        {
                            extendedblockstorage.func_76657_c(p_76615_1_, l1 & 15, p_76615_3_, 15);
                            this.field_76637_e.func_72902_n((this.field_76635_g << 4) + p_76615_1_, l1, (this.field_76647_h << 4) + p_76615_3_);
                        }
                    }
                }
                else
                {
                    for (l1 = l; l1 < i1; ++l1)
                    {
                        extendedblockstorage = this.field_76652_q[l1 >> 4];

                        if (extendedblockstorage != null)
                        {
                            extendedblockstorage.func_76657_c(p_76615_1_, l1 & 15, p_76615_3_, 0);
                            this.field_76637_e.func_72902_n((this.field_76635_g << 4) + p_76615_1_, l1, (this.field_76647_h << 4) + p_76615_3_);
                        }
                    }
                }

                l1 = 15;

                while (i1 > 0 && l1 > 0)
                {
                    --i1;
                    i2 = this.func_76596_b(p_76615_1_, i1, p_76615_3_);

                    if (i2 == 0)
                    {
                        i2 = 1;
                    }

                    l1 -= i2;

                    if (l1 < 0)
                    {
                        l1 = 0;
                    }

                    ExtendedBlockStorage extendedblockstorage1 = this.field_76652_q[i1 >> 4];

                    if (extendedblockstorage1 != null)
                    {
                        extendedblockstorage1.func_76657_c(p_76615_1_, i1 & 15, p_76615_3_, l1);
                    }
                }
            }

            l1 = this.field_76634_f[p_76615_3_ << 4 | p_76615_1_];
            i2 = l;
            int j2 = l1;

            if (l1 < l)
            {
                i2 = l1;
                j2 = l;
            }

            if (l1 < this.field_82912_p)
            {
                this.field_82912_p = l1;
            }

            if (!this.field_76637_e.field_73011_w.field_76576_e)
            {
                this.func_76609_d(j1 - 1, k1, i2, j2);
                this.func_76609_d(j1 + 1, k1, i2, j2);
                this.func_76609_d(j1, k1 - 1, i2, j2);
                this.func_76609_d(j1, k1 + 1, i2, j2);
                this.func_76609_d(j1, k1, i2, j2);
            }

            this.field_76643_l = true;
        }
    }

    public int func_76596_b(int p_76596_1_, int p_76596_2_, int p_76596_3_)
    {
        return Block.field_71971_o[this.func_76610_a(p_76596_1_, p_76596_2_, p_76596_3_)];
    }

    public int func_76610_a(int p_76610_1_, int p_76610_2_, int p_76610_3_)
    {
        if (p_76610_2_ >> 4 >= this.field_76652_q.length)
        {
            return 0;
        }
        else
        {
            ExtendedBlockStorage extendedblockstorage = this.field_76652_q[p_76610_2_ >> 4];
            return extendedblockstorage != null ? extendedblockstorage.func_76656_a(p_76610_1_, p_76610_2_ & 15, p_76610_3_) : 0;
        }
    }

    public int func_76628_c(int p_76628_1_, int p_76628_2_, int p_76628_3_)
    {
        if (p_76628_2_ >> 4 >= this.field_76652_q.length)
        {
            return 0;
        }
        else
        {
            ExtendedBlockStorage extendedblockstorage = this.field_76652_q[p_76628_2_ >> 4];
            return extendedblockstorage != null ? extendedblockstorage.func_76665_b(p_76628_1_, p_76628_2_ & 15, p_76628_3_) : 0;
        }
    }

    public boolean func_76598_a(int p_76598_1_, int p_76598_2_, int p_76598_3_, int p_76598_4_)
    {
        return this.func_76592_a(p_76598_1_, p_76598_2_, p_76598_3_, p_76598_4_, 0);
    }

    public boolean func_76592_a(int p_76592_1_, int p_76592_2_, int p_76592_3_, int p_76592_4_, int p_76592_5_)
    {
        int j1 = p_76592_3_ << 4 | p_76592_1_;

        if (p_76592_2_ >= this.field_76638_b[j1] - 1)
        {
            this.field_76638_b[j1] = -999;
        }

        int k1 = this.field_76634_f[j1];
        int l1 = this.func_76610_a(p_76592_1_, p_76592_2_, p_76592_3_);
        int i2 = this.func_76628_c(p_76592_1_, p_76592_2_, p_76592_3_);

        if (l1 == p_76592_4_ && i2 == p_76592_5_)
        {
            return false;
        }
        else
        {
            ExtendedBlockStorage extendedblockstorage = this.field_76652_q[p_76592_2_ >> 4];
            boolean flag = false;

            if (extendedblockstorage == null)
            {
                if (p_76592_4_ == 0)
                {
                    return false;
                }

                extendedblockstorage = this.field_76652_q[p_76592_2_ >> 4] = new ExtendedBlockStorage(p_76592_2_ >> 4 << 4, !field_76637_e.field_73011_w.field_76576_e);
                flag = p_76592_2_ >= k1;
            }

            int j2 = this.field_76635_g * 16 + p_76592_1_;
            int k2 = this.field_76647_h * 16 + p_76592_3_;

            if (l1 != 0 && !this.field_76637_e.field_72995_K)
            {
                Block.field_71973_m[l1].func_71927_h(this.field_76637_e, j2, p_76592_2_, k2, i2);
            }

            extendedblockstorage.func_76655_a(p_76592_1_, p_76592_2_ & 15, p_76592_3_, p_76592_4_);

            if (l1 != 0)
            {
                if (!this.field_76637_e.field_72995_K)
                {
                    Block.field_71973_m[l1].func_71852_a(this.field_76637_e, j2, p_76592_2_, k2, l1, i2);
                }
                else if (Block.field_71973_m[l1] instanceof BlockContainer && l1 != p_76592_4_)
                {
                    this.field_76637_e.func_72932_q(j2, p_76592_2_, k2);
                }
            }

            if (extendedblockstorage.func_76656_a(p_76592_1_, p_76592_2_ & 15, p_76592_3_) != p_76592_4_)
            {
                return false;
            }
            else
            {
                extendedblockstorage.func_76654_b(p_76592_1_, p_76592_2_ & 15, p_76592_3_, p_76592_5_);

                if (flag)
                {
                    this.func_76603_b();
                }
                else
                {
                    if (Block.field_71971_o[p_76592_4_ & 4095] > 0)
                    {
                        if (p_76592_2_ >= k1)
                        {
                            this.func_76615_h(p_76592_1_, p_76592_2_ + 1, p_76592_3_);
                        }
                    }
                    else if (p_76592_2_ == k1 - 1)
                    {
                        this.func_76615_h(p_76592_1_, p_76592_2_, p_76592_3_);
                    }

                    this.func_76595_e(p_76592_1_, p_76592_3_);
                }

                TileEntity tileentity;

                if (p_76592_4_ != 0)
                {
                    // CraftBukkit - Don't place while processing the BlockPlaceEvent, unless it's a BlockContainer
                    if (!this.field_76637_e.field_72995_K && (!this.field_76637_e.callingPlaceEvent || (Block.field_71973_m[p_76592_4_] instanceof BlockContainer)))
                    {
                        Block.field_71973_m[p_76592_4_].func_71861_g(this.field_76637_e, j2, p_76592_2_, k2);
                    }

                    if (Block.field_71973_m[p_76592_4_] instanceof BlockContainer)
                    {
                        // CraftBukkit start - don't create tile entity if placement failed
                        if (this.func_76610_a(p_76592_1_, p_76592_2_, p_76592_3_) != p_76592_4_)
                        {
                            return false;
                        }

                        // CraftBukkit end
                        tileentity = this.func_76597_e(p_76592_1_, p_76592_2_, p_76592_3_);

                        if (tileentity == null)
                        {
                            tileentity = ((BlockContainer)Block.field_71973_m[p_76592_4_]).func_72274_a(this.field_76637_e);
                            this.field_76637_e.func_72837_a(j2, p_76592_2_, k2, tileentity);
                        }

                        if (tileentity != null)
                        {
                            tileentity.func_70321_h();
                        }
                    }
                }
                else if (l1 > 0 && Block.field_71973_m[l1] instanceof BlockContainer)
                {
                    tileentity = this.func_76597_e(p_76592_1_, p_76592_2_, p_76592_3_);

                    if (tileentity != null)
                    {
                        tileentity.func_70321_h();
                    }
                }

                this.field_76643_l = true;
                return true;
            }
        }
    }

    public boolean func_76589_b(int p_76589_1_, int p_76589_2_, int p_76589_3_, int p_76589_4_)
    {
        ExtendedBlockStorage extendedblockstorage = this.field_76652_q[p_76589_2_ >> 4];

        if (extendedblockstorage == null)
        {
            return false;
        }
        else
        {
            int i1 = extendedblockstorage.func_76665_b(p_76589_1_, p_76589_2_ & 15, p_76589_3_);

            if (i1 == p_76589_4_)
            {
                return false;
            }
            else
            {
                this.field_76643_l = true;
                extendedblockstorage.func_76654_b(p_76589_1_, p_76589_2_ & 15, p_76589_3_, p_76589_4_);
                int j1 = extendedblockstorage.func_76656_a(p_76589_1_, p_76589_2_ & 15, p_76589_3_);

                if (j1 > 0 && Block.field_71973_m[j1] instanceof BlockContainer)
                {
                    TileEntity tileentity = this.func_76597_e(p_76589_1_, p_76589_2_, p_76589_3_);

                    if (tileentity != null)
                    {
                        tileentity.func_70321_h();
                        tileentity.field_70325_p = p_76589_4_;
                    }
                }

                return true;
            }
        }
    }

    public int func_76614_a(EnumSkyBlock p_76614_1_, int p_76614_2_, int p_76614_3_, int p_76614_4_)
    {
        ExtendedBlockStorage extendedblockstorage = this.field_76652_q[p_76614_3_ >> 4];
        return extendedblockstorage == null ? (this.func_76619_d(p_76614_2_, p_76614_3_, p_76614_4_) ? p_76614_1_.field_77198_c : 0) : (p_76614_1_ == EnumSkyBlock.Sky ? (this.field_76637_e.field_73011_w.field_76576_e ? 0 : extendedblockstorage.func_76670_c(p_76614_2_, p_76614_3_ & 15, p_76614_4_)) : (p_76614_1_ == EnumSkyBlock.Block ? extendedblockstorage.func_76674_d(p_76614_2_, p_76614_3_ & 15, p_76614_4_) : p_76614_1_.field_77198_c));
    }

    public void func_76633_a(EnumSkyBlock p_76633_1_, int p_76633_2_, int p_76633_3_, int p_76633_4_, int p_76633_5_)
    {
        ExtendedBlockStorage extendedblockstorage = this.field_76652_q[p_76633_3_ >> 4];

        if (extendedblockstorage == null)
        {
            extendedblockstorage = this.field_76652_q[p_76633_3_ >> 4] = new ExtendedBlockStorage(p_76633_3_ >> 4 << 4, !field_76637_e.field_73011_w.field_76576_e);
            this.func_76603_b();
        }

        this.field_76643_l = true;

        if (p_76633_1_ == EnumSkyBlock.Sky)
        {
            if (!this.field_76637_e.field_73011_w.field_76576_e)
            {
                extendedblockstorage.func_76657_c(p_76633_2_, p_76633_3_ & 15, p_76633_4_, p_76633_5_);
            }
        }
        else if (p_76633_1_ == EnumSkyBlock.Block)
        {
            extendedblockstorage.func_76677_d(p_76633_2_, p_76633_3_ & 15, p_76633_4_, p_76633_5_);
        }
    }

    public int func_76629_c(int p_76629_1_, int p_76629_2_, int p_76629_3_, int p_76629_4_)
    {
        ExtendedBlockStorage extendedblockstorage = this.field_76652_q[p_76629_2_ >> 4];

        if (extendedblockstorage == null)
        {
            return !this.field_76637_e.field_73011_w.field_76576_e && p_76629_4_ < EnumSkyBlock.Sky.field_77198_c ? EnumSkyBlock.Sky.field_77198_c - p_76629_4_ : 0;
        }
        else
        {
            int i1 = this.field_76637_e.field_73011_w.field_76576_e ? 0 : extendedblockstorage.func_76670_c(p_76629_1_, p_76629_2_ & 15, p_76629_3_);

            if (i1 > 0)
            {
                field_76640_a = true;
            }

            i1 -= p_76629_4_;
            int j1 = extendedblockstorage.func_76674_d(p_76629_1_, p_76629_2_ & 15, p_76629_3_);

            if (j1 > i1)
            {
                i1 = j1;
            }

            return i1;
        }
    }

    public void func_76612_a(Entity p_76612_1_)
    {
        this.field_76644_m = true;
        int i = MathHelper.func_76128_c(p_76612_1_.field_70165_t / 16.0D);
        int j = MathHelper.func_76128_c(p_76612_1_.field_70161_v / 16.0D);

        if (i != this.field_76635_g || j != this.field_76647_h)
        {
            // CraftBukkit start
            Bukkit.getLogger().warning("Wrong location for " + p_76612_1_ + " in world '" + field_76637_e.getWorld().getName() + "'!");
            // Thread.dumpStack();
            Bukkit.getLogger().warning("Entity is at " + p_76612_1_.field_70165_t + "," + p_76612_1_.field_70161_v + " (chunk " + i + "," + j + ") but was stored in chunk " + this.field_76635_g + "," + this.field_76647_h);
            // CraftBukkit end
        }

        int k = MathHelper.func_76128_c(p_76612_1_.field_70163_u / 16.0D);

        if (k < 0)
        {
            k = 0;
        }

        if (k >= this.field_76645_j.length)
        {
            k = this.field_76645_j.length - 1;
        }

        p_76612_1_.field_70175_ag = true;
        p_76612_1_.field_70176_ah = this.field_76635_g;
        p_76612_1_.field_70162_ai = k;
        p_76612_1_.field_70164_aj = this.field_76647_h;
        this.field_76645_j[k].add(p_76612_1_);
    }

    public void func_76622_b(Entity p_76622_1_)
    {
        this.func_76608_a(p_76622_1_, p_76622_1_.field_70162_ai);
    }

    public void func_76608_a(Entity p_76608_1_, int p_76608_2_)
    {
        if (p_76608_2_ < 0)
        {
            p_76608_2_ = 0;
        }

        if (p_76608_2_ >= this.field_76645_j.length)
        {
            p_76608_2_ = this.field_76645_j.length - 1;
        }

        this.field_76645_j[p_76608_2_].remove(p_76608_1_);
    }

    public boolean func_76619_d(int p_76619_1_, int p_76619_2_, int p_76619_3_)
    {
        return p_76619_2_ >= this.field_76634_f[p_76619_3_ << 4 | p_76619_1_];
    }

    public TileEntity func_76597_e(int p_76597_1_, int p_76597_2_, int p_76597_3_)
    {
        ChunkPosition chunkposition = new ChunkPosition(p_76597_1_, p_76597_2_, p_76597_3_);
        TileEntity tileentity = (TileEntity)this.field_76648_i.get(chunkposition);

        if (tileentity == null)
        {
            int l = this.func_76610_a(p_76597_1_, p_76597_2_, p_76597_3_);

            if (l <= 0 || !Block.field_71973_m[l].func_71887_s())
            {
                return null;
            }

            if (tileentity == null)
            {
                tileentity = ((BlockContainer)Block.field_71973_m[l]).func_72274_a(this.field_76637_e);
                this.field_76637_e.func_72837_a(this.field_76635_g * 16 + p_76597_1_, p_76597_2_, this.field_76647_h * 16 + p_76597_3_, tileentity);
            }

            tileentity = (TileEntity)this.field_76648_i.get(chunkposition);
        }

        if (tileentity != null && tileentity.func_70320_p())
        {
            this.field_76648_i.remove(chunkposition);
            return null;
        }
        else
        {
            return tileentity;
        }
    }

    public void func_76620_a(TileEntity p_76620_1_)
    {
        int i = p_76620_1_.field_70329_l - this.field_76635_g * 16;
        int j = p_76620_1_.field_70330_m;
        int k = p_76620_1_.field_70327_n - this.field_76647_h * 16;
        this.func_76604_a(i, j, k, p_76620_1_);

        if (this.field_76636_d)
        {
            this.field_76637_e.field_73009_h.add(p_76620_1_);
        }
    }

    public void func_76604_a(int p_76604_1_, int p_76604_2_, int p_76604_3_, TileEntity p_76604_4_)
    {
        ChunkPosition chunkposition = new ChunkPosition(p_76604_1_, p_76604_2_, p_76604_3_);
        p_76604_4_.func_70308_a(this.field_76637_e);
        p_76604_4_.field_70329_l = this.field_76635_g * 16 + p_76604_1_;
        p_76604_4_.field_70330_m = p_76604_2_;
        p_76604_4_.field_70327_n = this.field_76647_h * 16 + p_76604_3_;

        if (this.func_76610_a(p_76604_1_, p_76604_2_, p_76604_3_) != 0 && Block.field_71973_m[this.func_76610_a(p_76604_1_, p_76604_2_, p_76604_3_)] instanceof BlockContainer)
        {
            p_76604_4_.func_70312_q();
            this.field_76648_i.put(chunkposition, p_76604_4_);
            // CraftBukkit start
        }
        else
        {
            System.out.println("Attempted to place a tile entity (" + p_76604_4_ + ") at " + p_76604_4_.field_70329_l + "," + p_76604_4_.field_70330_m + "," + p_76604_4_.field_70327_n
                    + " (" + org.bukkit.Material.getMaterial(func_76610_a(p_76604_1_, p_76604_2_, p_76604_3_)) + ") where there was no entity tile!");
            System.out.println("Chunk coordinates: " + (this.field_76635_g * 16) + "," + (this.field_76647_h * 16));
            new Exception().printStackTrace();
            // CraftBukkit end
        }
    }

    public void func_76627_f(int p_76627_1_, int p_76627_2_, int p_76627_3_)
    {
        ChunkPosition chunkposition = new ChunkPosition(p_76627_1_, p_76627_2_, p_76627_3_);

        if (this.field_76636_d)
        {
            TileEntity tileentity = (TileEntity)this.field_76648_i.remove(chunkposition);

            if (tileentity != null)
            {
                tileentity.func_70313_j();
            }
        }
    }

    public void func_76631_c()
    {
        this.field_76636_d = true;
        this.field_76637_e.func_72852_a(this.field_76648_i.values());

        for (int i = 0; i < this.field_76645_j.length; ++i)
        {
            this.field_76637_e.func_72868_a(this.field_76645_j[i]);
        }
    }

    public void func_76623_d()
    {
        this.field_76636_d = false;
        Iterator iterator = this.field_76648_i.values().iterator();

        while (iterator.hasNext())
        {
            TileEntity tileentity = (TileEntity)iterator.next();
            this.field_76637_e.func_72928_a(tileentity);
        }

        for (int i = 0; i < this.field_76645_j.length; ++i)
        {
            // CraftBukkit start
            java.util.Iterator<Object> iter = this.field_76645_j[i].iterator();

            while (iter.hasNext())
            {
                Entity entity = (Entity) iter.next();

                // Do not pass along players, as doing so can get them stuck outside of time.
                // (which for example disables inventory icon updates and prevents block breaking)
                if (entity instanceof EntityPlayerMP)
                {
                    iter.remove();
                }
            }

            // CraftBukkit end
            this.field_76637_e.func_72828_b(this.field_76645_j[i]);
        }
    }

    public void func_76630_e()
    {
        this.field_76643_l = true;
    }

    public void func_76588_a(Entity p_76588_1_, AxisAlignedBB p_76588_2_, List p_76588_3_)
    {
        int i = MathHelper.func_76128_c((p_76588_2_.field_72338_b - 2.0D) / 16.0D);
        int j = MathHelper.func_76128_c((p_76588_2_.field_72337_e + 2.0D) / 16.0D);

        if (i < 0)
        {
            i = 0;
        }

        if (j >= this.field_76645_j.length)
        {
            j = this.field_76645_j.length - 1;
        }

        for (int k = i; k <= j; ++k)
        {
            List list1 = this.field_76645_j[k];

            for (int l = 0; l < list1.size(); ++l)
            {
                Entity entity1 = (Entity)list1.get(l);

                if (entity1 != p_76588_1_ && entity1.field_70121_D.func_72326_a(p_76588_2_))
                {
                    p_76588_3_.add(entity1);
                    Entity[] aentity = entity1.func_70021_al();

                    if (aentity != null)
                    {
                        for (int i1 = 0; i1 < aentity.length; ++i1)
                        {
                            entity1 = aentity[i1];

                            if (entity1 != p_76588_1_ && entity1.field_70121_D.func_72326_a(p_76588_2_))
                            {
                                p_76588_3_.add(entity1);
                            }
                        }
                    }
                }
            }
        }
    }

    public void func_76618_a(Class p_76618_1_, AxisAlignedBB p_76618_2_, List p_76618_3_, IEntitySelector p_76618_4_)
    {
        int i = MathHelper.func_76128_c((p_76618_2_.field_72338_b - 2.0D) / 16.0D);
        int j = MathHelper.func_76128_c((p_76618_2_.field_72337_e + 2.0D) / 16.0D);

        if (i < 0)
        {
            i = 0;
        }
        else if (i >= this.field_76645_j.length)
        {
            i = this.field_76645_j.length - 1;
        }

        if (j >= this.field_76645_j.length)
        {
            j = this.field_76645_j.length - 1;
        }
        else if (j < 0)
        {
            j = 0;
        }

        for (int k = i; k <= j; ++k)
        {
            List list1 = this.field_76645_j[k];

            for (int l = 0; l < list1.size(); ++l)
            {
                Entity entity = (Entity)list1.get(l);

                if (p_76618_1_.isAssignableFrom(entity.getClass()) && entity.field_70121_D.func_72326_a(p_76618_2_) && (p_76618_4_ == null || p_76618_4_.func_82704_a(entity)))
                {
                    p_76618_3_.add(entity);
                }
            }
        }
    }

    public boolean func_76601_a(boolean p_76601_1_)
    {
        if (p_76601_1_)
        {
            if (this.field_76644_m && this.field_76637_e.func_82737_E() != this.field_76641_n)
            {
                return true;
            }
        }
        else if (this.field_76644_m && this.field_76637_e.func_82737_E() >= this.field_76641_n + 600L)
        {
            return true;
        }

        return this.field_76643_l;
    }

    public Random func_76617_a(long p_76617_1_)
    {
        return new Random(this.field_76637_e.func_72905_C() + (long)(this.field_76635_g * this.field_76635_g * 4987142) + (long)(this.field_76635_g * 5947611) + (long)(this.field_76647_h * this.field_76647_h) * 4392871L + (long)(this.field_76647_h * 389711) ^ p_76617_1_);
    }

    public boolean func_76621_g()
    {
        return false;
    }

    public void func_76624_a(IChunkProvider p_76624_1_, IChunkProvider p_76624_2_, int p_76624_3_, int p_76624_4_)
    {
        if (!this.field_76646_k && p_76624_1_.func_73149_a(p_76624_3_ + 1, p_76624_4_ + 1) && p_76624_1_.func_73149_a(p_76624_3_, p_76624_4_ + 1) && p_76624_1_.func_73149_a(p_76624_3_ + 1, p_76624_4_))
        {
            p_76624_1_.func_73153_a(p_76624_2_, p_76624_3_, p_76624_4_);
        }

        if (p_76624_1_.func_73149_a(p_76624_3_ - 1, p_76624_4_) && !p_76624_1_.func_73154_d(p_76624_3_ - 1, p_76624_4_).field_76646_k && p_76624_1_.func_73149_a(p_76624_3_ - 1, p_76624_4_ + 1) && p_76624_1_.func_73149_a(p_76624_3_, p_76624_4_ + 1) && p_76624_1_.func_73149_a(p_76624_3_ - 1, p_76624_4_ + 1))
        {
            p_76624_1_.func_73153_a(p_76624_2_, p_76624_3_ - 1, p_76624_4_);
        }

        if (p_76624_1_.func_73149_a(p_76624_3_, p_76624_4_ - 1) && !p_76624_1_.func_73154_d(p_76624_3_, p_76624_4_ - 1).field_76646_k && p_76624_1_.func_73149_a(p_76624_3_ + 1, p_76624_4_ - 1) && p_76624_1_.func_73149_a(p_76624_3_ + 1, p_76624_4_ - 1) && p_76624_1_.func_73149_a(p_76624_3_ + 1, p_76624_4_))
        {
            p_76624_1_.func_73153_a(p_76624_2_, p_76624_3_, p_76624_4_ - 1);
        }

        if (p_76624_1_.func_73149_a(p_76624_3_ - 1, p_76624_4_ - 1) && !p_76624_1_.func_73154_d(p_76624_3_ - 1, p_76624_4_ - 1).field_76646_k && p_76624_1_.func_73149_a(p_76624_3_, p_76624_4_ - 1) && p_76624_1_.func_73149_a(p_76624_3_ - 1, p_76624_4_))
        {
            p_76624_1_.func_73153_a(p_76624_2_, p_76624_3_ - 1, p_76624_4_ - 1);
        }
    }

    public int func_76626_d(int p_76626_1_, int p_76626_2_)
    {
        int k = p_76626_1_ | p_76626_2_ << 4;
        int l = this.field_76638_b[k];

        if (l == -999)
        {
            int i1 = this.func_76625_h() + 15;
            l = -1;

            while (i1 > 0 && l == -1)
            {
                int j1 = this.func_76610_a(p_76626_1_, i1, p_76626_2_);
                Material material = j1 == 0 ? Material.field_76249_a : Block.field_71973_m[j1].field_72018_cp;

                if (!material.func_76230_c() && !material.func_76224_d())
                {
                    --i1;
                }
                else
                {
                    l = i1 + 1;
                }
            }

            this.field_76638_b[k] = l;
        }

        return l;
    }

    public void func_76586_k()
    {
        if (this.field_76650_s && !this.field_76637_e.field_73011_w.field_76576_e)
        {
            this.func_76593_q();
        }
    }

    public ChunkCoordIntPair func_76632_l()
    {
        return new ChunkCoordIntPair(this.field_76635_g, this.field_76647_h);
    }

    public boolean func_76606_c(int p_76606_1_, int p_76606_2_)
    {
        if (p_76606_1_ < 0)
        {
            p_76606_1_ = 0;
        }

        if (p_76606_2_ >= 256)
        {
            p_76606_2_ = 255;
        }

        for (int k = p_76606_1_; k <= p_76606_2_; k += 16)
        {
            ExtendedBlockStorage extendedblockstorage = this.field_76652_q[k >> 4];

            if (extendedblockstorage != null && !extendedblockstorage.func_76663_a())
            {
                return false;
            }
        }

        return true;
    }

    public void func_76602_a(ExtendedBlockStorage[] p_76602_1_)
    {
        this.field_76652_q = p_76602_1_;
    }

    public BiomeGenBase func_76591_a(int p_76591_1_, int p_76591_2_, WorldChunkManager p_76591_3_)
    {
        int k = this.field_76651_r[p_76591_2_ << 4 | p_76591_1_] & 255;

        if (k == 255)
        {
            BiomeGenBase biomegenbase = p_76591_3_.func_76935_a((this.field_76635_g << 4) + p_76591_1_, (this.field_76647_h << 4) + p_76591_2_);
            k = biomegenbase.field_76756_M;
            this.field_76651_r[p_76591_2_ << 4 | p_76591_1_] = (byte)(k & 255);
        }

        return BiomeGenBase.field_76773_a[k] == null ? BiomeGenBase.field_76772_c : BiomeGenBase.field_76773_a[k];
    }

    public byte[] func_76605_m()
    {
        return this.field_76651_r;
    }

    public void func_76616_a(byte[] p_76616_1_)
    {
        this.field_76651_r = p_76616_1_;
    }

    public void func_76613_n()
    {
        this.field_76649_t = 0;
    }

    public void func_76594_o()
    {
        for (int i = 0; i < 8; ++i)
        {
            if (this.field_76649_t >= 4096)
            {
                return;
            }

            int j = this.field_76649_t % 16;
            int k = this.field_76649_t / 16 % 16;
            int l = this.field_76649_t / 256;
            ++this.field_76649_t;
            int i1 = (this.field_76635_g << 4) + k;
            int j1 = (this.field_76647_h << 4) + l;

            for (int k1 = 0; k1 < 16; ++k1)
            {
                int l1 = (j << 4) + k1;

                if (this.field_76652_q[j] == null && (k1 == 0 || k1 == 15 || k == 0 || k == 15 || l == 0 || l == 15) || this.field_76652_q[j] != null && this.field_76652_q[j].func_76656_a(k, k1, l) == 0)
                {
                    if (Block.field_71984_q[this.field_76637_e.func_72798_a(i1, l1 - 1, j1)] > 0)
                    {
                        this.field_76637_e.func_72969_x(i1, l1 - 1, j1);
                    }

                    if (Block.field_71984_q[this.field_76637_e.func_72798_a(i1, l1 + 1, j1)] > 0)
                    {
                        this.field_76637_e.func_72969_x(i1, l1 + 1, j1);
                    }

                    if (Block.field_71984_q[this.field_76637_e.func_72798_a(i1 - 1, l1, j1)] > 0)
                    {
                        this.field_76637_e.func_72969_x(i1 - 1, l1, j1);
                    }

                    if (Block.field_71984_q[this.field_76637_e.func_72798_a(i1 + 1, l1, j1)] > 0)
                    {
                        this.field_76637_e.func_72969_x(i1 + 1, l1, j1);
                    }

                    if (Block.field_71984_q[this.field_76637_e.func_72798_a(i1, l1, j1 - 1)] > 0)
                    {
                        this.field_76637_e.func_72969_x(i1, l1, j1 - 1);
                    }

                    if (Block.field_71984_q[this.field_76637_e.func_72798_a(i1, l1, j1 + 1)] > 0)
                    {
                        this.field_76637_e.func_72969_x(i1, l1, j1 + 1);
                    }

                    this.field_76637_e.func_72969_x(i1, l1, j1);
                }
            }
        }
    }
}
