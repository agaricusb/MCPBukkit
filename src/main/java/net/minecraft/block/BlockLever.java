package net.minecraft.block;

import org.bukkit.event.block.BlockRedstoneEvent; // CraftBukkit
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLever extends Block
{
    protected BlockLever(int p_i9069_1_)
    {
        super(p_i9069_1_, Material.field_76265_p);
        this.func_71849_a(CreativeTabs.field_78028_d);
    }

    public AxisAlignedBB func_71872_e(World p_71872_1_, int p_71872_2_, int p_71872_3_, int p_71872_4_)
    {
        return null;
    }

    public boolean func_71926_d()
    {
        return false;
    }

    public boolean func_71886_c()
    {
        return false;
    }

    public int func_71857_b()
    {
        return 12;
    }

    public boolean func_71850_a_(World p_71850_1_, int p_71850_2_, int p_71850_3_, int p_71850_4_, int p_71850_5_)
    {
        return p_71850_5_ == 0 && p_71850_1_.func_72809_s(p_71850_2_, p_71850_3_ + 1, p_71850_4_) ? true : (p_71850_5_ == 1 && p_71850_1_.func_72797_t(p_71850_2_, p_71850_3_ - 1, p_71850_4_) ? true : (p_71850_5_ == 2 && p_71850_1_.func_72809_s(p_71850_2_, p_71850_3_, p_71850_4_ + 1) ? true : (p_71850_5_ == 3 && p_71850_1_.func_72809_s(p_71850_2_, p_71850_3_, p_71850_4_ - 1) ? true : (p_71850_5_ == 4 && p_71850_1_.func_72809_s(p_71850_2_ + 1, p_71850_3_, p_71850_4_) ? true : p_71850_5_ == 5 && p_71850_1_.func_72809_s(p_71850_2_ - 1, p_71850_3_, p_71850_4_)))));
    }

    public boolean func_71930_b(World p_71930_1_, int p_71930_2_, int p_71930_3_, int p_71930_4_)
    {
        return p_71930_1_.func_72809_s(p_71930_2_ - 1, p_71930_3_, p_71930_4_) ? true : (p_71930_1_.func_72809_s(p_71930_2_ + 1, p_71930_3_, p_71930_4_) ? true : (p_71930_1_.func_72809_s(p_71930_2_, p_71930_3_, p_71930_4_ - 1) ? true : (p_71930_1_.func_72809_s(p_71930_2_, p_71930_3_, p_71930_4_ + 1) ? true : (p_71930_1_.func_72797_t(p_71930_2_, p_71930_3_ - 1, p_71930_4_) ? true : p_71930_1_.func_72809_s(p_71930_2_, p_71930_3_ + 1, p_71930_4_)))));
    }

    public int func_85104_a(World p_85104_1_, int p_85104_2_, int p_85104_3_, int p_85104_4_, int p_85104_5_, float p_85104_6_, float p_85104_7_, float p_85104_8_, int p_85104_9_)
    {
        int j1 = p_85104_9_ & 8;
        int k1 = p_85104_9_ & 7;
        byte b0 = -1;

        if (p_85104_5_ == 0 && p_85104_1_.func_72809_s(p_85104_2_, p_85104_3_ + 1, p_85104_4_))
        {
            b0 = 0;
        }

        if (p_85104_5_ == 1 && p_85104_1_.func_72797_t(p_85104_2_, p_85104_3_ - 1, p_85104_4_))
        {
            b0 = 5;
        }

        if (p_85104_5_ == 2 && p_85104_1_.func_72809_s(p_85104_2_, p_85104_3_, p_85104_4_ + 1))
        {
            b0 = 4;
        }

        if (p_85104_5_ == 3 && p_85104_1_.func_72809_s(p_85104_2_, p_85104_3_, p_85104_4_ - 1))
        {
            b0 = 3;
        }

        if (p_85104_5_ == 4 && p_85104_1_.func_72809_s(p_85104_2_ + 1, p_85104_3_, p_85104_4_))
        {
            b0 = 2;
        }

        if (p_85104_5_ == 5 && p_85104_1_.func_72809_s(p_85104_2_ - 1, p_85104_3_, p_85104_4_))
        {
            b0 = 1;
        }

        return b0 + j1;
    }

    public void func_71860_a(World p_71860_1_, int p_71860_2_, int p_71860_3_, int p_71860_4_, EntityLiving p_71860_5_, ItemStack p_71860_6_)
    {
        int l = p_71860_1_.func_72805_g(p_71860_2_, p_71860_3_, p_71860_4_);
        int i1 = l & 7;
        int j1 = l & 8;

        if (i1 == func_72196_d(1))
        {
            if ((MathHelper.func_76128_c((double)(p_71860_5_.field_70177_z * 4.0F / 360.0F) + 0.5D) & 1) == 0)
            {
                p_71860_1_.func_72921_c(p_71860_2_, p_71860_3_, p_71860_4_, 5 | j1, 2);
            }
            else
            {
                p_71860_1_.func_72921_c(p_71860_2_, p_71860_3_, p_71860_4_, 6 | j1, 2);
            }
        }
        else if (i1 == func_72196_d(0))
        {
            if ((MathHelper.func_76128_c((double)(p_71860_5_.field_70177_z * 4.0F / 360.0F) + 0.5D) & 1) == 0)
            {
                p_71860_1_.func_72921_c(p_71860_2_, p_71860_3_, p_71860_4_, 7 | j1, 2);
            }
            else
            {
                p_71860_1_.func_72921_c(p_71860_2_, p_71860_3_, p_71860_4_, 0 | j1, 2);
            }
        }
    }

    public static int func_72196_d(int p_72196_0_)
    {
        switch (p_72196_0_)
        {
            case 0:
                return 0;

            case 1:
                return 5;

            case 2:
                return 4;

            case 3:
                return 3;

            case 4:
                return 2;

            case 5:
                return 1;

            default:
                return -1;
        }
    }

    public void func_71863_a(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
    {
        if (this.func_72195_l(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_))
        {
            int i1 = p_71863_1_.func_72805_g(p_71863_2_, p_71863_3_, p_71863_4_) & 7;
            boolean flag = false;

            if (!p_71863_1_.func_72809_s(p_71863_2_ - 1, p_71863_3_, p_71863_4_) && i1 == 1)
            {
                flag = true;
            }

            if (!p_71863_1_.func_72809_s(p_71863_2_ + 1, p_71863_3_, p_71863_4_) && i1 == 2)
            {
                flag = true;
            }

            if (!p_71863_1_.func_72809_s(p_71863_2_, p_71863_3_, p_71863_4_ - 1) && i1 == 3)
            {
                flag = true;
            }

            if (!p_71863_1_.func_72809_s(p_71863_2_, p_71863_3_, p_71863_4_ + 1) && i1 == 4)
            {
                flag = true;
            }

            if (!p_71863_1_.func_72797_t(p_71863_2_, p_71863_3_ - 1, p_71863_4_) && i1 == 5)
            {
                flag = true;
            }

            if (!p_71863_1_.func_72797_t(p_71863_2_, p_71863_3_ - 1, p_71863_4_) && i1 == 6)
            {
                flag = true;
            }

            if (!p_71863_1_.func_72809_s(p_71863_2_, p_71863_3_ + 1, p_71863_4_) && i1 == 0)
            {
                flag = true;
            }

            if (!p_71863_1_.func_72809_s(p_71863_2_, p_71863_3_ + 1, p_71863_4_) && i1 == 7)
            {
                flag = true;
            }

            if (flag)
            {
                this.func_71897_c(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, p_71863_1_.func_72805_g(p_71863_2_, p_71863_3_, p_71863_4_), 0);
                p_71863_1_.func_94571_i(p_71863_2_, p_71863_3_, p_71863_4_);
            }
        }
    }

    private boolean func_72195_l(World p_72195_1_, int p_72195_2_, int p_72195_3_, int p_72195_4_)
    {
        if (!this.func_71930_b(p_72195_1_, p_72195_2_, p_72195_3_, p_72195_4_))
        {
            this.func_71897_c(p_72195_1_, p_72195_2_, p_72195_3_, p_72195_4_, p_72195_1_.func_72805_g(p_72195_2_, p_72195_3_, p_72195_4_), 0);
            p_72195_1_.func_94571_i(p_72195_2_, p_72195_3_, p_72195_4_);
            return false;
        }
        else
        {
            return true;
        }
    }

    public void func_71902_a(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_)
    {
        int l = p_71902_1_.func_72805_g(p_71902_2_, p_71902_3_, p_71902_4_) & 7;
        float f = 0.1875F;

        if (l == 1)
        {
            this.func_71905_a(0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
        }
        else if (l == 2)
        {
            this.func_71905_a(1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
        }
        else if (l == 3)
        {
            this.func_71905_a(0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
        }
        else if (l == 4)
        {
            this.func_71905_a(0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
        }
        else if (l != 5 && l != 6)
        {
            if (l == 0 || l == 7)
            {
                f = 0.25F;
                this.func_71905_a(0.5F - f, 0.4F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
            }
        }
        else
        {
            f = 0.25F;
            this.func_71905_a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.6F, 0.5F + f);
        }
    }

    public boolean func_71903_a(World p_71903_1_, int p_71903_2_, int p_71903_3_, int p_71903_4_, EntityPlayer p_71903_5_, int p_71903_6_, float p_71903_7_, float p_71903_8_, float p_71903_9_)
    {
        if (p_71903_1_.field_72995_K)
        {
            return true;
        }
        else
        {
            int i1 = p_71903_1_.func_72805_g(p_71903_2_, p_71903_3_, p_71903_4_);
            int j1 = i1 & 7;
            int k1 = 8 - (i1 & 8);
            // CraftBukkit start - Interact Lever
            org.bukkit.block.Block block = p_71903_1_.getWorld().getBlockAt(p_71903_2_, p_71903_3_, p_71903_4_);
            int old = (k1 != 8) ? 15 : 0;
            int current = (k1 == 8) ? 15 : 0;
            BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, old, current);
            p_71903_1_.getServer().getPluginManager().callEvent(eventRedstone);

            if ((eventRedstone.getNewCurrent() > 0) != (k1 == 8))
            {
                return true;
            }

            // CraftBukkit end
            p_71903_1_.func_72921_c(p_71903_2_, p_71903_3_, p_71903_4_, j1 + k1, 3);
            p_71903_1_.func_72908_a((double)p_71903_2_ + 0.5D, (double)p_71903_3_ + 0.5D, (double)p_71903_4_ + 0.5D, "random.click", 0.3F, k1 > 0 ? 0.6F : 0.5F);
            p_71903_1_.func_72898_h(p_71903_2_, p_71903_3_, p_71903_4_, this.field_71990_ca);

            if (j1 == 1)
            {
                p_71903_1_.func_72898_h(p_71903_2_ - 1, p_71903_3_, p_71903_4_, this.field_71990_ca);
            }
            else if (j1 == 2)
            {
                p_71903_1_.func_72898_h(p_71903_2_ + 1, p_71903_3_, p_71903_4_, this.field_71990_ca);
            }
            else if (j1 == 3)
            {
                p_71903_1_.func_72898_h(p_71903_2_, p_71903_3_, p_71903_4_ - 1, this.field_71990_ca);
            }
            else if (j1 == 4)
            {
                p_71903_1_.func_72898_h(p_71903_2_, p_71903_3_, p_71903_4_ + 1, this.field_71990_ca);
            }
            else if (j1 != 5 && j1 != 6)
            {
                if (j1 == 0 || j1 == 7)
                {
                    p_71903_1_.func_72898_h(p_71903_2_, p_71903_3_ + 1, p_71903_4_, this.field_71990_ca);
                }
            }
            else
            {
                p_71903_1_.func_72898_h(p_71903_2_, p_71903_3_ - 1, p_71903_4_, this.field_71990_ca);
            }

            return true;
        }
    }

    public void func_71852_a(World p_71852_1_, int p_71852_2_, int p_71852_3_, int p_71852_4_, int p_71852_5_, int p_71852_6_)
    {
        if ((p_71852_6_ & 8) > 0)
        {
            p_71852_1_.func_72898_h(p_71852_2_, p_71852_3_, p_71852_4_, this.field_71990_ca);
            int j1 = p_71852_6_ & 7;

            if (j1 == 1)
            {
                p_71852_1_.func_72898_h(p_71852_2_ - 1, p_71852_3_, p_71852_4_, this.field_71990_ca);
            }
            else if (j1 == 2)
            {
                p_71852_1_.func_72898_h(p_71852_2_ + 1, p_71852_3_, p_71852_4_, this.field_71990_ca);
            }
            else if (j1 == 3)
            {
                p_71852_1_.func_72898_h(p_71852_2_, p_71852_3_, p_71852_4_ - 1, this.field_71990_ca);
            }
            else if (j1 == 4)
            {
                p_71852_1_.func_72898_h(p_71852_2_, p_71852_3_, p_71852_4_ + 1, this.field_71990_ca);
            }
            else if (j1 != 5 && j1 != 6)
            {
                if (j1 == 0 || j1 == 7)
                {
                    p_71852_1_.func_72898_h(p_71852_2_, p_71852_3_ + 1, p_71852_4_, this.field_71990_ca);
                }
            }
            else
            {
                p_71852_1_.func_72898_h(p_71852_2_, p_71852_3_ - 1, p_71852_4_, this.field_71990_ca);
            }
        }

        super.func_71852_a(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_, p_71852_5_, p_71852_6_);
    }

    public int func_71865_a(IBlockAccess p_71865_1_, int p_71865_2_, int p_71865_3_, int p_71865_4_, int p_71865_5_)
    {
        return (p_71865_1_.func_72805_g(p_71865_2_, p_71865_3_, p_71865_4_) & 8) > 0 ? 15 : 0;
    }

    public int func_71855_c(IBlockAccess p_71855_1_, int p_71855_2_, int p_71855_3_, int p_71855_4_, int p_71855_5_)
    {
        int i1 = p_71855_1_.func_72805_g(p_71855_2_, p_71855_3_, p_71855_4_);

        if ((i1 & 8) == 0)
        {
            return 0;
        }
        else
        {
            int j1 = i1 & 7;
            return j1 == 0 && p_71855_5_ == 0 ? 15 : (j1 == 7 && p_71855_5_ == 0 ? 15 : (j1 == 6 && p_71855_5_ == 1 ? 15 : (j1 == 5 && p_71855_5_ == 1 ? 15 : (j1 == 4 && p_71855_5_ == 2 ? 15 : (j1 == 3 && p_71855_5_ == 3 ? 15 : (j1 == 2 && p_71855_5_ == 4 ? 15 : (j1 == 1 && p_71855_5_ == 5 ? 15 : 0)))))));
        }
    }

    public boolean func_71853_i()
    {
        return true;
    }
}
