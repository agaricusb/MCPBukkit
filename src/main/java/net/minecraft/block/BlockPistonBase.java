package net.minecraft.block;

import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

// CraftBukkit start
import org.bukkit.craftbukkit.block.CraftBlock;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
// CraftBukkit end

public class BlockPistonBase extends Block
{
    private final boolean field_72119_a;

    public BlockPistonBase(int p_i9105_1_, boolean p_i9105_2_)
    {
        super(p_i9105_1_, Material.field_76233_E);
        this.field_72119_a = p_i9105_2_;
        this.func_71884_a(field_71976_h);
        this.func_71848_c(0.5F);
        this.func_71849_a(CreativeTabs.field_78028_d);
    }

    public int func_71857_b()
    {
        return 16;
    }

    public boolean func_71926_d()
    {
        return false;
    }

    public boolean func_71903_a(World p_71903_1_, int p_71903_2_, int p_71903_3_, int p_71903_4_, EntityPlayer p_71903_5_, int p_71903_6_, float p_71903_7_, float p_71903_8_, float p_71903_9_)
    {
        return false;
    }

    public void func_71860_a(World p_71860_1_, int p_71860_2_, int p_71860_3_, int p_71860_4_, EntityLiving p_71860_5_, ItemStack p_71860_6_)
    {
        int l = func_72116_b(p_71860_1_, p_71860_2_, p_71860_3_, p_71860_4_, p_71860_5_);
        p_71860_1_.func_72921_c(p_71860_2_, p_71860_3_, p_71860_4_, l, 2);

        if (!p_71860_1_.field_72995_K)
        {
            this.func_72110_l(p_71860_1_, p_71860_2_, p_71860_3_, p_71860_4_);
        }
    }

    public void func_71863_a(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
    {
        if (!p_71863_1_.field_72995_K)
        {
            this.func_72110_l(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_);
        }
    }

    public void func_71861_g(World p_71861_1_, int p_71861_2_, int p_71861_3_, int p_71861_4_)
    {
        if (!p_71861_1_.field_72995_K && p_71861_1_.func_72796_p(p_71861_2_, p_71861_3_, p_71861_4_) == null)
        {
            this.func_72110_l(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_);
        }
    }

    private void func_72110_l(World p_72110_1_, int p_72110_2_, int p_72110_3_, int p_72110_4_)
    {
        int l = p_72110_1_.func_72805_g(p_72110_2_, p_72110_3_, p_72110_4_);
        int i1 = func_72117_e(l);

        if (i1 != 7)
        {
            boolean flag = this.func_72113_e(p_72110_1_, p_72110_2_, p_72110_3_, p_72110_4_, i1);

            if (flag && !func_72114_f(l))
            {
                // CraftBukkit start
                int length = func_72112_i(p_72110_1_, p_72110_2_, p_72110_3_, p_72110_4_, i1);

                if (length >= 0)
                {
                    org.bukkit.block.Block block = p_72110_1_.getWorld().getBlockAt(p_72110_2_, p_72110_3_, p_72110_4_);
                    BlockPistonExtendEvent event = new BlockPistonExtendEvent(block, length, CraftBlock.notchToBlockFace(i1));
                    p_72110_1_.getServer().getPluginManager().callEvent(event);

                    if (event.isCancelled())
                    {
                        return;
                    }

                    // CraftBukkit end
                    p_72110_1_.func_72965_b(p_72110_2_, p_72110_3_, p_72110_4_, this.field_71990_ca, 0, i1);
                }
            }
            else if (!flag && func_72114_f(l))
            {
                // CraftBukkit start
                org.bukkit.block.Block block = p_72110_1_.getWorld().getBlockAt(p_72110_2_, p_72110_3_, p_72110_4_);
                BlockPistonRetractEvent event = new BlockPistonRetractEvent(block, CraftBlock.notchToBlockFace(i1));
                p_72110_1_.getServer().getPluginManager().callEvent(event);

                if (event.isCancelled())
                {
                    return;
                }

                // CraftBukkit end
                p_72110_1_.func_72921_c(p_72110_2_, p_72110_3_, p_72110_4_, i1, 2);
                p_72110_1_.func_72965_b(p_72110_2_, p_72110_3_, p_72110_4_, this.field_71990_ca, 1, i1);
            }
        }
    }

    private boolean func_72113_e(World p_72113_1_, int p_72113_2_, int p_72113_3_, int p_72113_4_, int p_72113_5_)
    {
        return p_72113_5_ != 0 && p_72113_1_.func_94574_k(p_72113_2_, p_72113_3_ - 1, p_72113_4_, 0) ? true : (p_72113_5_ != 1 && p_72113_1_.func_94574_k(p_72113_2_, p_72113_3_ + 1, p_72113_4_, 1) ? true : (p_72113_5_ != 2 && p_72113_1_.func_94574_k(p_72113_2_, p_72113_3_, p_72113_4_ - 1, 2) ? true : (p_72113_5_ != 3 && p_72113_1_.func_94574_k(p_72113_2_, p_72113_3_, p_72113_4_ + 1, 3) ? true : (p_72113_5_ != 5 && p_72113_1_.func_94574_k(p_72113_2_ + 1, p_72113_3_, p_72113_4_, 5) ? true : (p_72113_5_ != 4 && p_72113_1_.func_94574_k(p_72113_2_ - 1, p_72113_3_, p_72113_4_, 4) ? true : (p_72113_1_.func_94574_k(p_72113_2_, p_72113_3_, p_72113_4_, 0) ? true : (p_72113_1_.func_94574_k(p_72113_2_, p_72113_3_ + 2, p_72113_4_, 1) ? true : (p_72113_1_.func_94574_k(p_72113_2_, p_72113_3_ + 1, p_72113_4_ - 1, 2) ? true : (p_72113_1_.func_94574_k(p_72113_2_, p_72113_3_ + 1, p_72113_4_ + 1, 3) ? true : (p_72113_1_.func_94574_k(p_72113_2_ - 1, p_72113_3_ + 1, p_72113_4_, 4) ? true : p_72113_1_.func_94574_k(p_72113_2_ + 1, p_72113_3_ + 1, p_72113_4_, 5)))))))))));
    }

    public boolean func_71883_b(World p_71883_1_, int p_71883_2_, int p_71883_3_, int p_71883_4_, int p_71883_5_, int p_71883_6_)
    {
        if (!p_71883_1_.field_72995_K)
        {
            boolean flag = this.func_72113_e(p_71883_1_, p_71883_2_, p_71883_3_, p_71883_4_, p_71883_6_);

            if (flag && p_71883_5_ == 1)
            {
                p_71883_1_.func_72921_c(p_71883_2_, p_71883_3_, p_71883_4_, p_71883_6_ | 8, 2);
                return false;
            }

            if (!flag && p_71883_5_ == 0)
            {
                return false;
            }
        }

        if (p_71883_5_ == 0)
        {
            if (!this.func_72115_j(p_71883_1_, p_71883_2_, p_71883_3_, p_71883_4_, p_71883_6_))
            {
                return false;
            }

            p_71883_1_.func_72921_c(p_71883_2_, p_71883_3_, p_71883_4_, p_71883_6_ | 8, 2);
            p_71883_1_.func_72908_a((double)p_71883_2_ + 0.5D, (double)p_71883_3_ + 0.5D, (double)p_71883_4_ + 0.5D, "tile.piston.out", 0.5F, p_71883_1_.field_73012_v.nextFloat() * 0.25F + 0.6F);
        }
        else if (p_71883_5_ == 1)
        {
            TileEntity tileentity = p_71883_1_.func_72796_p(p_71883_2_ + Facing.field_71586_b[p_71883_6_], p_71883_3_ + Facing.field_71587_c[p_71883_6_], p_71883_4_ + Facing.field_71585_d[p_71883_6_]);

            if (tileentity instanceof TileEntityPiston)
            {
                ((TileEntityPiston)tileentity).func_70339_i();
            }

            p_71883_1_.func_72832_d(p_71883_2_, p_71883_3_, p_71883_4_, Block.field_72095_ac.field_71990_ca, p_71883_6_, 3);
            p_71883_1_.func_72837_a(p_71883_2_, p_71883_3_, p_71883_4_, BlockPistonMoving.func_72297_a(this.field_71990_ca, p_71883_6_, p_71883_6_, false, true));

            if (this.field_72119_a)
            {
                int j1 = p_71883_2_ + Facing.field_71586_b[p_71883_6_] * 2;
                int k1 = p_71883_3_ + Facing.field_71587_c[p_71883_6_] * 2;
                int l1 = p_71883_4_ + Facing.field_71585_d[p_71883_6_] * 2;
                int i2 = p_71883_1_.func_72798_a(j1, k1, l1);
                int j2 = p_71883_1_.func_72805_g(j1, k1, l1);
                boolean flag1 = false;

                if (i2 == Block.field_72095_ac.field_71990_ca)
                {
                    TileEntity tileentity1 = p_71883_1_.func_72796_p(j1, k1, l1);

                    if (tileentity1 instanceof TileEntityPiston)
                    {
                        TileEntityPiston tileentitypiston = (TileEntityPiston)tileentity1;

                        if (tileentitypiston.func_70336_c() == p_71883_6_ && tileentitypiston.func_70341_b())
                        {
                            tileentitypiston.func_70339_i();
                            i2 = tileentitypiston.func_70340_a();
                            j2 = tileentitypiston.func_70322_n();
                            flag1 = true;
                        }
                    }
                }

                if (!flag1 && i2 > 0 && func_72111_a(i2, p_71883_1_, j1, k1, l1, false) && (Block.field_71973_m[i2].func_71915_e() == 0 || i2 == Block.field_71963_Z.field_71990_ca || i2 == Block.field_71956_V.field_71990_ca))
                {
                    p_71883_2_ += Facing.field_71586_b[p_71883_6_];
                    p_71883_3_ += Facing.field_71587_c[p_71883_6_];
                    p_71883_4_ += Facing.field_71585_d[p_71883_6_];
                    p_71883_1_.func_72832_d(p_71883_2_, p_71883_3_, p_71883_4_, Block.field_72095_ac.field_71990_ca, j2, 3);
                    p_71883_1_.func_72837_a(p_71883_2_, p_71883_3_, p_71883_4_, BlockPistonMoving.func_72297_a(i2, j2, p_71883_6_, false, false));
                    p_71883_1_.func_94571_i(j1, k1, l1);
                }
                else if (!flag1)
                {
                    p_71883_1_.func_94571_i(p_71883_2_ + Facing.field_71586_b[p_71883_6_], p_71883_3_ + Facing.field_71587_c[p_71883_6_], p_71883_4_ + Facing.field_71585_d[p_71883_6_]);
                }
            }
            else
            {
                p_71883_1_.func_94571_i(p_71883_2_ + Facing.field_71586_b[p_71883_6_], p_71883_3_ + Facing.field_71587_c[p_71883_6_], p_71883_4_ + Facing.field_71585_d[p_71883_6_]);
            }

            p_71883_1_.func_72908_a((double)p_71883_2_ + 0.5D, (double)p_71883_3_ + 0.5D, (double)p_71883_4_ + 0.5D, "tile.piston.in", 0.5F, p_71883_1_.field_73012_v.nextFloat() * 0.15F + 0.6F);
        }

        return true;
    }

    public void func_71902_a(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_)
    {
        int l = p_71902_1_.func_72805_g(p_71902_2_, p_71902_3_, p_71902_4_);

        if (func_72114_f(l))
        {
            switch (func_72117_e(l))
            {
                case 0:
                    this.func_71905_a(0.0F, 0.25F, 0.0F, 1.0F, 1.0F, 1.0F);
                    break;

                case 1:
                    this.func_71905_a(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
                    break;

                case 2:
                    this.func_71905_a(0.0F, 0.0F, 0.25F, 1.0F, 1.0F, 1.0F);
                    break;

                case 3:
                    this.func_71905_a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.75F);
                    break;

                case 4:
                    this.func_71905_a(0.25F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                    break;

                case 5:
                    this.func_71905_a(0.0F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F);
            }
        }
        else
        {
            this.func_71905_a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    public void func_71919_f()
    {
        this.func_71905_a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public void func_71871_a(World p_71871_1_, int p_71871_2_, int p_71871_3_, int p_71871_4_, AxisAlignedBB p_71871_5_, List p_71871_6_, Entity p_71871_7_)
    {
        this.func_71905_a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        super.func_71871_a(p_71871_1_, p_71871_2_, p_71871_3_, p_71871_4_, p_71871_5_, p_71871_6_, p_71871_7_);
    }

    public AxisAlignedBB func_71872_e(World p_71872_1_, int p_71872_2_, int p_71872_3_, int p_71872_4_)
    {
        this.func_71902_a(p_71872_1_, p_71872_2_, p_71872_3_, p_71872_4_);
        return super.func_71872_e(p_71872_1_, p_71872_2_, p_71872_3_, p_71872_4_);
    }

    public boolean func_71886_c()
    {
        return false;
    }

    public static int func_72117_e(int p_72117_0_)
    {
        if ((p_72117_0_ & 7) >= Facing.field_71588_a.length)
        {
            return 7;    // CraftBukkit - check for AIOOB on piston data
        }

        return p_72117_0_ & 7;
    }

    public static boolean func_72114_f(int p_72114_0_)
    {
        return (p_72114_0_ & 8) != 0;
    }

    public static int func_72116_b(World p_72116_0_, int p_72116_1_, int p_72116_2_, int p_72116_3_, EntityLiving p_72116_4_)
    {
        if (MathHelper.func_76135_e((float)p_72116_4_.field_70165_t - (float)p_72116_1_) < 2.0F && MathHelper.func_76135_e((float)p_72116_4_.field_70161_v - (float)p_72116_3_) < 2.0F)
        {
            double d0 = p_72116_4_.field_70163_u + 1.82D - (double)p_72116_4_.field_70129_M;

            if (d0 - (double)p_72116_2_ > 2.0D)
            {
                return 1;
            }

            if ((double)p_72116_2_ - d0 > 0.0D)
            {
                return 0;
            }
        }

        int l = MathHelper.func_76128_c((double)(p_72116_4_.field_70177_z * 4.0F / 360.0F) + 0.5D) & 3;
        return l == 0 ? 2 : (l == 1 ? 5 : (l == 2 ? 3 : (l == 3 ? 4 : 0)));
    }

    private static boolean func_72111_a(int p_72111_0_, World p_72111_1_, int p_72111_2_, int p_72111_3_, int p_72111_4_, boolean p_72111_5_)
    {
        if (p_72111_0_ == Block.field_72089_ap.field_71990_ca)
        {
            return false;
        }
        else
        {
            if (p_72111_0_ != Block.field_71963_Z.field_71990_ca && p_72111_0_ != Block.field_71956_V.field_71990_ca)
            {
                if (Block.field_71973_m[p_72111_0_].func_71934_m(p_72111_1_, p_72111_2_, p_72111_3_, p_72111_4_) == -1.0F)
                {
                    return false;
                }

                if (Block.field_71973_m[p_72111_0_].func_71915_e() == 2)
                {
                    return false;
                }

                if (Block.field_71973_m[p_72111_0_].func_71915_e() == 1)
                {
                    if (!p_72111_5_)
                    {
                        return false;
                    }

                    return true;
                }
            }
            else if (func_72114_f(p_72111_1_.func_72805_g(p_72111_2_, p_72111_3_, p_72111_4_)))
            {
                return false;
            }

            return !(Block.field_71973_m[p_72111_0_] instanceof ITileEntityProvider);
        }
    }

    // CraftBukkit - boolean -> int return
    private static int func_72112_i(World p_72112_0_, int p_72112_1_, int p_72112_2_, int p_72112_3_, int p_72112_4_)
    {
        int i1 = p_72112_1_ + Facing.field_71586_b[p_72112_4_];
        int j1 = p_72112_2_ + Facing.field_71587_c[p_72112_4_];
        int k1 = p_72112_3_ + Facing.field_71585_d[p_72112_4_];
        int l1 = 0;

        while (true)
        {
            if (l1 < 13)
            {
                if (j1 <= 0 || j1 >= 255)
                {
                    return -1; // CraftBukkit
                }

                int i2 = p_72112_0_.func_72798_a(i1, j1, k1);

                if (i2 != 0)
                {
                    if (!func_72111_a(i2, p_72112_0_, i1, j1, k1, true))
                    {
                        return -1; // CraftBukkit
                    }

                    if (Block.field_71973_m[i2].func_71915_e() != 1)
                    {
                        if (l1 == 12)
                        {
                            return -1; // CraftBukkit
                        }

                        i1 += Facing.field_71586_b[p_72112_4_];
                        j1 += Facing.field_71587_c[p_72112_4_];
                        k1 += Facing.field_71585_d[p_72112_4_];
                        ++l1;
                        continue;
                    }
                }
            }

            return l1; // CraftBukkit
        }
    }

    private boolean func_72115_j(World p_72115_1_, int p_72115_2_, int p_72115_3_, int p_72115_4_, int p_72115_5_)
    {
        int i1 = p_72115_2_ + Facing.field_71586_b[p_72115_5_];
        int j1 = p_72115_3_ + Facing.field_71587_c[p_72115_5_];
        int k1 = p_72115_4_ + Facing.field_71585_d[p_72115_5_];
        int l1 = 0;

        while (true)
        {
            int i2;

            if (l1 < 13)
            {
                if (j1 <= 0 || j1 >= 255)
                {
                    return false;
                }

                i2 = p_72115_1_.func_72798_a(i1, j1, k1);

                if (i2 != 0)
                {
                    if (!func_72111_a(i2, p_72115_1_, i1, j1, k1, true))
                    {
                        return false;
                    }

                    if (Block.field_71973_m[i2].func_71915_e() != 1)
                    {
                        if (l1 == 12)
                        {
                            return false;
                        }

                        i1 += Facing.field_71586_b[p_72115_5_];
                        j1 += Facing.field_71587_c[p_72115_5_];
                        k1 += Facing.field_71585_d[p_72115_5_];
                        ++l1;
                        continue;
                    }

                    Block.field_71973_m[i2].func_71897_c(p_72115_1_, i1, j1, k1, p_72115_1_.func_72805_g(i1, j1, k1), 0);
                    p_72115_1_.func_94571_i(i1, j1, k1);
                }
            }

            l1 = i1;
            i2 = j1;
            int j2 = k1;
            int k2 = 0;
            int[] aint;
            int l2;
            int i3;
            int j3;

            for (aint = new int[13]; i1 != p_72115_2_ || j1 != p_72115_3_ || k1 != p_72115_4_; k1 = j3)
            {
                l2 = i1 - Facing.field_71586_b[p_72115_5_];
                i3 = j1 - Facing.field_71587_c[p_72115_5_];
                j3 = k1 - Facing.field_71585_d[p_72115_5_];
                int k3 = p_72115_1_.func_72798_a(l2, i3, j3);
                int l3 = p_72115_1_.func_72805_g(l2, i3, j3);

                if (k3 == this.field_71990_ca && l2 == p_72115_2_ && i3 == p_72115_3_ && j3 == p_72115_4_)
                {
                    p_72115_1_.func_72832_d(i1, j1, k1, Block.field_72095_ac.field_71990_ca, p_72115_5_ | (this.field_72119_a ? 8 : 0), 4);
                    p_72115_1_.func_72837_a(i1, j1, k1, BlockPistonMoving.func_72297_a(Block.field_72099_aa.field_71990_ca, p_72115_5_ | (this.field_72119_a ? 8 : 0), p_72115_5_, true, false));
                }
                else
                {
                    p_72115_1_.func_72832_d(i1, j1, k1, Block.field_72095_ac.field_71990_ca, l3, 4);
                    p_72115_1_.func_72837_a(i1, j1, k1, BlockPistonMoving.func_72297_a(k3, l3, p_72115_5_, true, false));
                }

                aint[k2++] = k3;
                i1 = l2;
                j1 = i3;
            }

            i1 = l1;
            j1 = i2;
            k1 = j2;

            for (k2 = 0; i1 != p_72115_2_ || j1 != p_72115_3_ || k1 != p_72115_4_; k1 = j3)
            {
                l2 = i1 - Facing.field_71586_b[p_72115_5_];
                i3 = j1 - Facing.field_71587_c[p_72115_5_];
                j3 = k1 - Facing.field_71585_d[p_72115_5_];
                p_72115_1_.func_72898_h(l2, i3, j3, aint[k2++]);
                i1 = l2;
                j1 = i3;
            }

            return true;
        }
    }
}
