package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.bukkit.event.block.BlockRedstoneEvent; // CraftBukkit

public abstract class BlockBasePressurePlate extends Block
{
    private String field_94356_a;

    protected BlockBasePressurePlate(int p_i9036_1_, String p_i9036_2_, Material p_i9036_3_)
    {
        super(p_i9036_1_, p_i9036_3_);
        this.field_94356_a = p_i9036_2_;
        this.func_71849_a(CreativeTabs.field_78028_d);
        this.func_71907_b(true);
        this.func_94353_c_(this.func_94355_d(15));
    }

    public void func_71902_a(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_)
    {
        this.func_94353_c_(p_71902_1_.func_72805_g(p_71902_2_, p_71902_3_, p_71902_4_));
    }

    protected void func_94353_c_(int p_94353_1_)
    {
        boolean flag = this.func_94350_c(p_94353_1_) > 0;
        float f = 0.0625F;

        if (flag)
        {
            this.func_71905_a(f, 0.0F, f, 1.0F - f, 0.03125F, 1.0F - f);
        }
        else
        {
            this.func_71905_a(f, 0.0F, f, 1.0F - f, 0.0625F, 1.0F - f);
        }
    }

    public int func_71859_p_(World p_71859_1_)
    {
        return 20;
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

    public boolean func_71918_c(IBlockAccess p_71918_1_, int p_71918_2_, int p_71918_3_, int p_71918_4_)
    {
        return true;
    }

    public boolean func_71930_b(World p_71930_1_, int p_71930_2_, int p_71930_3_, int p_71930_4_)
    {
        return p_71930_1_.func_72797_t(p_71930_2_, p_71930_3_ - 1, p_71930_4_) || BlockFence.func_72249_c(p_71930_1_.func_72798_a(p_71930_2_, p_71930_3_ - 1, p_71930_4_));
    }

    public void func_71863_a(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
    {
        boolean flag = false;

        if (!p_71863_1_.func_72797_t(p_71863_2_, p_71863_3_ - 1, p_71863_4_) && !BlockFence.func_72249_c(p_71863_1_.func_72798_a(p_71863_2_, p_71863_3_ - 1, p_71863_4_)))
        {
            flag = true;
        }

        if (flag)
        {
            this.func_71897_c(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, p_71863_1_.func_72805_g(p_71863_2_, p_71863_3_, p_71863_4_), 0);
            p_71863_1_.func_94571_i(p_71863_2_, p_71863_3_, p_71863_4_);
        }
    }

    public void func_71847_b(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
    {
        if (!p_71847_1_.field_72995_K)
        {
            int l = this.func_94350_c(p_71847_1_.func_72805_g(p_71847_2_, p_71847_3_, p_71847_4_));

            if (l > 0)
            {
                this.func_72193_l(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_, l);
            }
        }
    }

    public void func_71869_a(World p_71869_1_, int p_71869_2_, int p_71869_3_, int p_71869_4_, Entity p_71869_5_)
    {
        if (!p_71869_1_.field_72995_K)
        {
            int l = this.func_94350_c(p_71869_1_.func_72805_g(p_71869_2_, p_71869_3_, p_71869_4_));

            if (l == 0)
            {
                this.func_72193_l(p_71869_1_, p_71869_2_, p_71869_3_, p_71869_4_, l);
            }
        }
    }

    protected void func_72193_l(World p_72193_1_, int p_72193_2_, int p_72193_3_, int p_72193_4_, int p_72193_5_)
    {
        int i1 = this.func_94351_d(p_72193_1_, p_72193_2_, p_72193_3_, p_72193_4_);
        boolean flag = p_72193_5_ > 0;
        boolean flag1 = i1 > 0;
        // CraftBukkit start - Interact Pressure Plate
        org.bukkit.World bworld = p_72193_1_.getWorld();
        org.bukkit.plugin.PluginManager manager = p_72193_1_.getServer().getPluginManager();

        if (flag != flag1)
        {
            BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(bworld.getBlockAt(p_72193_2_, p_72193_3_, p_72193_4_), p_72193_5_, i1);
            manager.callEvent(eventRedstone);
            flag1 = eventRedstone.getNewCurrent() > 0;
            i1 = eventRedstone.getNewCurrent();
        }

        // CraftBukkit end

        if (p_72193_5_ != i1)
        {
            p_72193_1_.func_72921_c(p_72193_2_, p_72193_3_, p_72193_4_, this.func_94355_d(i1), 2);
            this.func_94354_b_(p_72193_1_, p_72193_2_, p_72193_3_, p_72193_4_);
            p_72193_1_.func_72909_d(p_72193_2_, p_72193_3_, p_72193_4_, p_72193_2_, p_72193_3_, p_72193_4_);
        }

        if (!flag1 && flag)
        {
            p_72193_1_.func_72908_a((double)p_72193_2_ + 0.5D, (double)p_72193_3_ + 0.1D, (double)p_72193_4_ + 0.5D, "random.click", 0.3F, 0.5F);
        }
        else if (flag1 && !flag)
        {
            p_72193_1_.func_72908_a((double)p_72193_2_ + 0.5D, (double)p_72193_3_ + 0.1D, (double)p_72193_4_ + 0.5D, "random.click", 0.3F, 0.6F);
        }

        if (flag1)
        {
            p_72193_1_.func_72836_a(p_72193_2_, p_72193_3_, p_72193_4_, this.field_71990_ca, this.func_71859_p_(p_72193_1_));
        }
    }

    protected AxisAlignedBB func_94352_a(int p_94352_1_, int p_94352_2_, int p_94352_3_)
    {
        float f = 0.125F;
        return AxisAlignedBB.func_72332_a().func_72299_a((double)((float)p_94352_1_ + f), (double)p_94352_2_, (double)((float)p_94352_3_ + f), (double)((float)(p_94352_1_ + 1) - f), (double)p_94352_2_ + 0.25D, (double)((float)(p_94352_3_ + 1) - f));
    }

    public void func_71852_a(World p_71852_1_, int p_71852_2_, int p_71852_3_, int p_71852_4_, int p_71852_5_, int p_71852_6_)
    {
        if (this.func_94350_c(p_71852_6_) > 0)
        {
            this.func_94354_b_(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_);
        }

        super.func_71852_a(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_, p_71852_5_, p_71852_6_);
    }

    protected void func_94354_b_(World p_94354_1_, int p_94354_2_, int p_94354_3_, int p_94354_4_)
    {
        p_94354_1_.func_72898_h(p_94354_2_, p_94354_3_, p_94354_4_, this.field_71990_ca);
        p_94354_1_.func_72898_h(p_94354_2_, p_94354_3_ - 1, p_94354_4_, this.field_71990_ca);
    }

    public int func_71865_a(IBlockAccess p_71865_1_, int p_71865_2_, int p_71865_3_, int p_71865_4_, int p_71865_5_)
    {
        return this.func_94350_c(p_71865_1_.func_72805_g(p_71865_2_, p_71865_3_, p_71865_4_));
    }

    public int func_71855_c(IBlockAccess p_71855_1_, int p_71855_2_, int p_71855_3_, int p_71855_4_, int p_71855_5_)
    {
        return p_71855_5_ == 1 ? this.func_94350_c(p_71855_1_.func_72805_g(p_71855_2_, p_71855_3_, p_71855_4_)) : 0;
    }

    public boolean func_71853_i()
    {
        return true;
    }

    public void func_71919_f()
    {
        float f = 0.5F;
        float f1 = 0.125F;
        float f2 = 0.5F;
        this.func_71905_a(0.5F - f, 0.5F - f1, 0.5F - f2, 0.5F + f, 0.5F + f1, 0.5F + f2);
    }

    public int func_71915_e()
    {
        return 1;
    }

    protected abstract int func_94351_d(World world, int i, int j, int k);

    protected abstract int func_94350_c(int i);

    protected abstract int func_94355_d(int i);
}
