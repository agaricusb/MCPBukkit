package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

// CraftBukkit start
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.entity.EntityInteractEvent;
// CraftBukkit end

public abstract class BlockButton extends Block
{
    private final boolean field_82537_a;

    protected BlockButton(int p_i9042_1_, boolean p_i9042_2_)
    {
        super(p_i9042_1_, Material.field_76265_p);
        this.func_71907_b(true);
        this.func_71849_a(CreativeTabs.field_78028_d);
        this.field_82537_a = p_i9042_2_;
    }

    public AxisAlignedBB func_71872_e(World p_71872_1_, int p_71872_2_, int p_71872_3_, int p_71872_4_)
    {
        return null;
    }

    public int func_71859_p_(World p_71859_1_)
    {
        return this.field_82537_a ? 30 : 20;
    }

    public boolean func_71926_d()
    {
        return false;
    }

    public boolean func_71886_c()
    {
        return false;
    }

    public boolean func_71850_a_(World p_71850_1_, int p_71850_2_, int p_71850_3_, int p_71850_4_, int p_71850_5_)
    {
        return p_71850_5_ == 2 && p_71850_1_.func_72809_s(p_71850_2_, p_71850_3_, p_71850_4_ + 1) ? true : (p_71850_5_ == 3 && p_71850_1_.func_72809_s(p_71850_2_, p_71850_3_, p_71850_4_ - 1) ? true : (p_71850_5_ == 4 && p_71850_1_.func_72809_s(p_71850_2_ + 1, p_71850_3_, p_71850_4_) ? true : p_71850_5_ == 5 && p_71850_1_.func_72809_s(p_71850_2_ - 1, p_71850_3_, p_71850_4_)));
    }

    public boolean func_71930_b(World p_71930_1_, int p_71930_2_, int p_71930_3_, int p_71930_4_)
    {
        return p_71930_1_.func_72809_s(p_71930_2_ - 1, p_71930_3_, p_71930_4_) ? true : (p_71930_1_.func_72809_s(p_71930_2_ + 1, p_71930_3_, p_71930_4_) ? true : (p_71930_1_.func_72809_s(p_71930_2_, p_71930_3_, p_71930_4_ - 1) ? true : p_71930_1_.func_72809_s(p_71930_2_, p_71930_3_, p_71930_4_ + 1)));
    }

    public int func_85104_a(World p_85104_1_, int p_85104_2_, int p_85104_3_, int p_85104_4_, int p_85104_5_, float p_85104_6_, float p_85104_7_, float p_85104_8_, int p_85104_9_)
    {
        int j1 = p_85104_1_.func_72805_g(p_85104_2_, p_85104_3_, p_85104_4_);
        int k1 = j1 & 8;
        j1 &= 7;

        if (p_85104_5_ == 2 && p_85104_1_.func_72809_s(p_85104_2_, p_85104_3_, p_85104_4_ + 1))
        {
            j1 = 4;
        }
        else if (p_85104_5_ == 3 && p_85104_1_.func_72809_s(p_85104_2_, p_85104_3_, p_85104_4_ - 1))
        {
            j1 = 3;
        }
        else if (p_85104_5_ == 4 && p_85104_1_.func_72809_s(p_85104_2_ + 1, p_85104_3_, p_85104_4_))
        {
            j1 = 2;
        }
        else if (p_85104_5_ == 5 && p_85104_1_.func_72809_s(p_85104_2_ - 1, p_85104_3_, p_85104_4_))
        {
            j1 = 1;
        }
        else
        {
            j1 = this.func_72260_l(p_85104_1_, p_85104_2_, p_85104_3_, p_85104_4_);
        }

        return j1 + k1;
    }

    private int func_72260_l(World p_72260_1_, int p_72260_2_, int p_72260_3_, int p_72260_4_)
    {
        return p_72260_1_.func_72809_s(p_72260_2_ - 1, p_72260_3_, p_72260_4_) ? 1 : (p_72260_1_.func_72809_s(p_72260_2_ + 1, p_72260_3_, p_72260_4_) ? 2 : (p_72260_1_.func_72809_s(p_72260_2_, p_72260_3_, p_72260_4_ - 1) ? 3 : (p_72260_1_.func_72809_s(p_72260_2_, p_72260_3_, p_72260_4_ + 1) ? 4 : 1)));
    }

    public void func_71863_a(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
    {
        if (this.func_72261_n(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_))
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

            if (flag)
            {
                this.func_71897_c(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, p_71863_1_.func_72805_g(p_71863_2_, p_71863_3_, p_71863_4_), 0);
                p_71863_1_.func_94571_i(p_71863_2_, p_71863_3_, p_71863_4_);
            }
        }
    }

    private boolean func_72261_n(World p_72261_1_, int p_72261_2_, int p_72261_3_, int p_72261_4_)
    {
        if (!this.func_71930_b(p_72261_1_, p_72261_2_, p_72261_3_, p_72261_4_))
        {
            this.func_71897_c(p_72261_1_, p_72261_2_, p_72261_3_, p_72261_4_, p_72261_1_.func_72805_g(p_72261_2_, p_72261_3_, p_72261_4_), 0);
            p_72261_1_.func_94571_i(p_72261_2_, p_72261_3_, p_72261_4_);
            return false;
        }
        else
        {
            return true;
        }
    }

    public void func_71902_a(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_)
    {
        int l = p_71902_1_.func_72805_g(p_71902_2_, p_71902_3_, p_71902_4_);
        this.func_82534_e(l);
    }

    private void func_82534_e(int p_82534_1_)
    {
        int j = p_82534_1_ & 7;
        boolean flag = (p_82534_1_ & 8) > 0;
        float f = 0.375F;
        float f1 = 0.625F;
        float f2 = 0.1875F;
        float f3 = 0.125F;

        if (flag)
        {
            f3 = 0.0625F;
        }

        if (j == 1)
        {
            this.func_71905_a(0.0F, f, 0.5F - f2, f3, f1, 0.5F + f2);
        }
        else if (j == 2)
        {
            this.func_71905_a(1.0F - f3, f, 0.5F - f2, 1.0F, f1, 0.5F + f2);
        }
        else if (j == 3)
        {
            this.func_71905_a(0.5F - f2, f, 0.0F, 0.5F + f2, f1, f3);
        }
        else if (j == 4)
        {
            this.func_71905_a(0.5F - f2, f, 1.0F - f3, 0.5F + f2, f1, 1.0F);
        }
    }

    public void func_71921_a(World p_71921_1_, int p_71921_2_, int p_71921_3_, int p_71921_4_, EntityPlayer p_71921_5_) {}

    public boolean func_71903_a(World p_71903_1_, int p_71903_2_, int p_71903_3_, int p_71903_4_, EntityPlayer p_71903_5_, int p_71903_6_, float p_71903_7_, float p_71903_8_, float p_71903_9_)
    {
        int i1 = p_71903_1_.func_72805_g(p_71903_2_, p_71903_3_, p_71903_4_);
        int j1 = i1 & 7;
        int k1 = 8 - (i1 & 8);

        if (k1 == 0)
        {
            return true;
        }
        else
        {
            // CraftBukkit start
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
            p_71903_1_.func_72909_d(p_71903_2_, p_71903_3_, p_71903_4_, p_71903_2_, p_71903_3_, p_71903_4_);
            p_71903_1_.func_72908_a((double)p_71903_2_ + 0.5D, (double)p_71903_3_ + 0.5D, (double)p_71903_4_ + 0.5D, "random.click", 0.3F, 0.6F);
            this.func_82536_d(p_71903_1_, p_71903_2_, p_71903_3_, p_71903_4_, j1);
            p_71903_1_.func_72836_a(p_71903_2_, p_71903_3_, p_71903_4_, this.field_71990_ca, this.func_71859_p_(p_71903_1_));
            return true;
        }
    }

    public void func_71852_a(World p_71852_1_, int p_71852_2_, int p_71852_3_, int p_71852_4_, int p_71852_5_, int p_71852_6_)
    {
        if ((p_71852_6_ & 8) > 0)
        {
            int j1 = p_71852_6_ & 7;
            this.func_82536_d(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_, j1);
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
            return j1 == 5 && p_71855_5_ == 1 ? 15 : (j1 == 4 && p_71855_5_ == 2 ? 15 : (j1 == 3 && p_71855_5_ == 3 ? 15 : (j1 == 2 && p_71855_5_ == 4 ? 15 : (j1 == 1 && p_71855_5_ == 5 ? 15 : 0))));
        }
    }

    public boolean func_71853_i()
    {
        return true;
    }

    public void func_71847_b(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
    {
        if (!p_71847_1_.field_72995_K)
        {
            int l = p_71847_1_.func_72805_g(p_71847_2_, p_71847_3_, p_71847_4_);

            if ((l & 8) != 0)
            {
                // CraftBukkit start
                org.bukkit.block.Block block = p_71847_1_.getWorld().getBlockAt(p_71847_2_, p_71847_3_, p_71847_4_);
                BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, 15, 0);
                p_71847_1_.getServer().getPluginManager().callEvent(eventRedstone);

                if (eventRedstone.getNewCurrent() > 0)
                {
                    return;
                }

                // CraftBukkit end

                if (this.field_82537_a)
                {
                    this.func_82535_o(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_);
                }
                else
                {
                    p_71847_1_.func_72921_c(p_71847_2_, p_71847_3_, p_71847_4_, l & 7, 3);
                    int i1 = l & 7;
                    this.func_82536_d(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_, i1);
                    p_71847_1_.func_72908_a((double)p_71847_2_ + 0.5D, (double)p_71847_3_ + 0.5D, (double)p_71847_4_ + 0.5D, "random.click", 0.3F, 0.5F);
                    p_71847_1_.func_72909_d(p_71847_2_, p_71847_3_, p_71847_4_, p_71847_2_, p_71847_3_, p_71847_4_);
                }
            }
        }
    }

    public void func_71919_f()
    {
        float f = 0.1875F;
        float f1 = 0.125F;
        float f2 = 0.125F;
        this.func_71905_a(0.5F - f, 0.5F - f1, 0.5F - f2, 0.5F + f, 0.5F + f1, 0.5F + f2);
    }

    public void func_71869_a(World p_71869_1_, int p_71869_2_, int p_71869_3_, int p_71869_4_, Entity p_71869_5_)
    {
        if (!p_71869_1_.field_72995_K)
        {
            if (this.field_82537_a)
            {
                if ((p_71869_1_.func_72805_g(p_71869_2_, p_71869_3_, p_71869_4_) & 8) == 0)
                {
                    // CraftBukkit start - Call interaction when entities (currently arrows) hit wooden buttons
                    EntityInteractEvent event = new EntityInteractEvent(p_71869_5_.getBukkitEntity(), p_71869_1_.getWorld().getBlockAt(p_71869_2_, p_71869_3_, p_71869_4_));
                    p_71869_1_.getServer().getPluginManager().callEvent(event);

                    if (event.isCancelled())
                    {
                        return;
                    }

                    // CraftBukkit end
                    this.func_82535_o(p_71869_1_, p_71869_2_, p_71869_3_, p_71869_4_);
                }
            }
        }
    }

    private void func_82535_o(World p_82535_1_, int p_82535_2_, int p_82535_3_, int p_82535_4_)
    {
        int l = p_82535_1_.func_72805_g(p_82535_2_, p_82535_3_, p_82535_4_);
        int i1 = l & 7;
        boolean flag = (l & 8) != 0;
        this.func_82534_e(l);
        List list = p_82535_1_.func_72872_a(EntityArrow.class, AxisAlignedBB.func_72332_a().func_72299_a((double)p_82535_2_ + this.field_72026_ch, (double)p_82535_3_ + this.field_72023_ci, (double)p_82535_4_ + this.field_72024_cj, (double)p_82535_2_ + this.field_72021_ck, (double)p_82535_3_ + this.field_72022_cl, (double)p_82535_4_ + this.field_72019_cm));
        boolean flag1 = !list.isEmpty();

        if (flag1 && !flag)
        {
            p_82535_1_.func_72921_c(p_82535_2_, p_82535_3_, p_82535_4_, i1 | 8, 3);
            this.func_82536_d(p_82535_1_, p_82535_2_, p_82535_3_, p_82535_4_, i1);
            p_82535_1_.func_72909_d(p_82535_2_, p_82535_3_, p_82535_4_, p_82535_2_, p_82535_3_, p_82535_4_);
            p_82535_1_.func_72908_a((double)p_82535_2_ + 0.5D, (double)p_82535_3_ + 0.5D, (double)p_82535_4_ + 0.5D, "random.click", 0.3F, 0.6F);
        }

        if (!flag1 && flag)
        {
            p_82535_1_.func_72921_c(p_82535_2_, p_82535_3_, p_82535_4_, i1, 3);
            this.func_82536_d(p_82535_1_, p_82535_2_, p_82535_3_, p_82535_4_, i1);
            p_82535_1_.func_72909_d(p_82535_2_, p_82535_3_, p_82535_4_, p_82535_2_, p_82535_3_, p_82535_4_);
            p_82535_1_.func_72908_a((double)p_82535_2_ + 0.5D, (double)p_82535_3_ + 0.5D, (double)p_82535_4_ + 0.5D, "random.click", 0.3F, 0.5F);
        }

        if (flag1)
        {
            p_82535_1_.func_72836_a(p_82535_2_, p_82535_3_, p_82535_4_, this.field_71990_ca, this.func_71859_p_(p_82535_1_));
        }
    }

    private void func_82536_d(World p_82536_1_, int p_82536_2_, int p_82536_3_, int p_82536_4_, int p_82536_5_)
    {
        p_82536_1_.func_72898_h(p_82536_2_, p_82536_3_, p_82536_4_, this.field_71990_ca);

        if (p_82536_5_ == 1)
        {
            p_82536_1_.func_72898_h(p_82536_2_ - 1, p_82536_3_, p_82536_4_, this.field_71990_ca);
        }
        else if (p_82536_5_ == 2)
        {
            p_82536_1_.func_72898_h(p_82536_2_ + 1, p_82536_3_, p_82536_4_, this.field_71990_ca);
        }
        else if (p_82536_5_ == 3)
        {
            p_82536_1_.func_72898_h(p_82536_2_, p_82536_3_, p_82536_4_ - 1, this.field_71990_ca);
        }
        else if (p_82536_5_ == 4)
        {
            p_82536_1_.func_72898_h(p_82536_2_, p_82536_3_, p_82536_4_ + 1, this.field_71990_ca);
        }
        else
        {
            p_82536_1_.func_72898_h(p_82536_2_, p_82536_3_ - 1, p_82536_4_, this.field_71990_ca);
        }
    }
}
