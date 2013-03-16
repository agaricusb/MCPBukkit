package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.bukkit.event.block.BlockRedstoneEvent; // CraftBukkit

public class BlockDetectorRail extends BlockRailBase
{
    public BlockDetectorRail(int p_i9051_1_)
    {
        super(p_i9051_1_, true);
        this.func_71907_b(true);
    }

    public int func_71859_p_(World p_71859_1_)
    {
        return 20;
    }

    public boolean func_71853_i()
    {
        return true;
    }

    public void func_71869_a(World p_71869_1_, int p_71869_2_, int p_71869_3_, int p_71869_4_, Entity p_71869_5_)
    {
        if (!p_71869_1_.field_72995_K)
        {
            int l = p_71869_1_.func_72805_g(p_71869_2_, p_71869_3_, p_71869_4_);

            if ((l & 8) == 0)
            {
                this.func_72187_e(p_71869_1_, p_71869_2_, p_71869_3_, p_71869_4_, l);
            }
        }
    }

    public void func_71847_b(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
    {
        if (!p_71847_1_.field_72995_K)
        {
            int l = p_71847_1_.func_72805_g(p_71847_2_, p_71847_3_, p_71847_4_);

            if ((l & 8) != 0)
            {
                this.func_72187_e(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_, l);
            }
        }
    }

    public int func_71865_a(IBlockAccess p_71865_1_, int p_71865_2_, int p_71865_3_, int p_71865_4_, int p_71865_5_)
    {
        return (p_71865_1_.func_72805_g(p_71865_2_, p_71865_3_, p_71865_4_) & 8) != 0 ? 15 : 0;
    }

    public int func_71855_c(IBlockAccess p_71855_1_, int p_71855_2_, int p_71855_3_, int p_71855_4_, int p_71855_5_)
    {
        return (p_71855_1_.func_72805_g(p_71855_2_, p_71855_3_, p_71855_4_) & 8) == 0 ? 0 : (p_71855_5_ == 1 ? 15 : 0);
    }

    private void func_72187_e(World p_72187_1_, int p_72187_2_, int p_72187_3_, int p_72187_4_, int p_72187_5_)
    {
        boolean flag = (p_72187_5_ & 8) != 0;
        boolean flag1 = false;
        float f = 0.125F;
        List list = p_72187_1_.func_72872_a(EntityMinecart.class, AxisAlignedBB.func_72332_a().func_72299_a((double)((float)p_72187_2_ + f), (double)p_72187_3_, (double)((float)p_72187_4_ + f), (double)((float)(p_72187_2_ + 1) - f), (double)((float)(p_72187_3_ + 1) - f), (double)((float)(p_72187_4_ + 1) - f)));

        if (!list.isEmpty())
        {
            flag1 = true;
        }

        // CraftBukkit start
        if (flag != flag1)
        {
            org.bukkit.block.Block block = p_72187_1_.getWorld().getBlockAt(p_72187_2_, p_72187_3_, p_72187_4_);
            BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, flag ? 15 : 0, flag1 ? 15 : 0);
            p_72187_1_.getServer().getPluginManager().callEvent(eventRedstone);
            flag1 = eventRedstone.getNewCurrent() > 0;
        }

        // CraftBukkit end

        if (flag1 && !flag)
        {
            p_72187_1_.func_72921_c(p_72187_2_, p_72187_3_, p_72187_4_, p_72187_5_ | 8, 3);
            p_72187_1_.func_72898_h(p_72187_2_, p_72187_3_, p_72187_4_, this.field_71990_ca);
            p_72187_1_.func_72898_h(p_72187_2_, p_72187_3_ - 1, p_72187_4_, this.field_71990_ca);
            p_72187_1_.func_72909_d(p_72187_2_, p_72187_3_, p_72187_4_, p_72187_2_, p_72187_3_, p_72187_4_);
        }

        if (!flag1 && flag)
        {
            p_72187_1_.func_72921_c(p_72187_2_, p_72187_3_, p_72187_4_, p_72187_5_ & 7, 3);
            p_72187_1_.func_72898_h(p_72187_2_, p_72187_3_, p_72187_4_, this.field_71990_ca);
            p_72187_1_.func_72898_h(p_72187_2_, p_72187_3_ - 1, p_72187_4_, this.field_71990_ca);
            p_72187_1_.func_72909_d(p_72187_2_, p_72187_3_, p_72187_4_, p_72187_2_, p_72187_3_, p_72187_4_);
        }

        if (flag1)
        {
            p_72187_1_.func_72836_a(p_72187_2_, p_72187_3_, p_72187_4_, this.field_71990_ca, this.func_71859_p_(p_72187_1_));
        }

        p_72187_1_.func_96440_m(p_72187_2_, p_72187_3_, p_72187_4_, this.field_71990_ca);
    }

    public void func_71861_g(World p_71861_1_, int p_71861_2_, int p_71861_3_, int p_71861_4_)
    {
        super.func_71861_g(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_);
        this.func_72187_e(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_, p_71861_1_.func_72805_g(p_71861_2_, p_71861_3_, p_71861_4_));
    }

    public boolean func_96468_q_()
    {
        return true;
    }

    public int func_94328_b_(World p_94328_1_, int p_94328_2_, int p_94328_3_, int p_94328_4_, int p_94328_5_)
    {
        if ((p_94328_1_.func_72805_g(p_94328_2_, p_94328_3_, p_94328_4_) & 8) > 0)
        {
            float f = 0.125F;
            List list = p_94328_1_.func_82733_a(EntityMinecart.class, AxisAlignedBB.func_72332_a().func_72299_a((double)((float)p_94328_2_ + f), (double)p_94328_3_, (double)((float)p_94328_4_ + f), (double)((float)(p_94328_2_ + 1) - f), (double)((float)(p_94328_3_ + 1) - f), (double)((float)(p_94328_4_ + 1) - f)), IEntitySelector.field_96566_b);

            if (list.size() > 0)
            {
                return Container.func_94526_b((IInventory)list.get(0));
            }
        }

        return 0;
    }
}
