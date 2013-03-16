package net.minecraft.block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.bukkit.event.block.BlockRedstoneEvent; // CraftBukkit

public class BlockRedstoneTorch extends BlockTorch
{
    private boolean field_72130_a = false;
    private static Map field_72129_b = new HashMap();

    private boolean func_72127_a(World p_72127_1_, int p_72127_2_, int p_72127_3_, int p_72127_4_, boolean p_72127_5_)
    {
        if (!field_72129_b.containsKey(p_72127_1_))
        {
            field_72129_b.put(p_72127_1_, new ArrayList());
        }

        List list = (List)field_72129_b.get(p_72127_1_);

        if (p_72127_5_)
        {
            list.add(new RedstoneUpdateInfo(p_72127_2_, p_72127_3_, p_72127_4_, p_72127_1_.func_82737_E()));
        }

        int l = 0;

        for (int i1 = 0; i1 < list.size(); ++i1)
        {
            RedstoneUpdateInfo redstoneupdateinfo = (RedstoneUpdateInfo)list.get(i1);

            if (redstoneupdateinfo.field_73664_a == p_72127_2_ && redstoneupdateinfo.field_73662_b == p_72127_3_ && redstoneupdateinfo.field_73663_c == p_72127_4_)
            {
                ++l;

                if (l >= 8)
                {
                    return true;
                }
            }
        }

        return false;
    }

    protected BlockRedstoneTorch(int p_i9074_1_, boolean p_i9074_2_)
    {
        super(p_i9074_1_);
        this.field_72130_a = p_i9074_2_;
        this.func_71907_b(true);
        this.func_71849_a((CreativeTabs)null);
    }

    public int func_71859_p_(World p_71859_1_)
    {
        return 2;
    }

    public void func_71861_g(World p_71861_1_, int p_71861_2_, int p_71861_3_, int p_71861_4_)
    {
        if (p_71861_1_.func_72805_g(p_71861_2_, p_71861_3_, p_71861_4_) == 0)
        {
            super.func_71861_g(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_);
        }

        if (this.field_72130_a)
        {
            p_71861_1_.func_72898_h(p_71861_2_, p_71861_3_ - 1, p_71861_4_, this.field_71990_ca);
            p_71861_1_.func_72898_h(p_71861_2_, p_71861_3_ + 1, p_71861_4_, this.field_71990_ca);
            p_71861_1_.func_72898_h(p_71861_2_ - 1, p_71861_3_, p_71861_4_, this.field_71990_ca);
            p_71861_1_.func_72898_h(p_71861_2_ + 1, p_71861_3_, p_71861_4_, this.field_71990_ca);
            p_71861_1_.func_72898_h(p_71861_2_, p_71861_3_, p_71861_4_ - 1, this.field_71990_ca);
            p_71861_1_.func_72898_h(p_71861_2_, p_71861_3_, p_71861_4_ + 1, this.field_71990_ca);
        }
    }

    public void func_71852_a(World p_71852_1_, int p_71852_2_, int p_71852_3_, int p_71852_4_, int p_71852_5_, int p_71852_6_)
    {
        if (this.field_72130_a)
        {
            p_71852_1_.func_72898_h(p_71852_2_, p_71852_3_ - 1, p_71852_4_, this.field_71990_ca);
            p_71852_1_.func_72898_h(p_71852_2_, p_71852_3_ + 1, p_71852_4_, this.field_71990_ca);
            p_71852_1_.func_72898_h(p_71852_2_ - 1, p_71852_3_, p_71852_4_, this.field_71990_ca);
            p_71852_1_.func_72898_h(p_71852_2_ + 1, p_71852_3_, p_71852_4_, this.field_71990_ca);
            p_71852_1_.func_72898_h(p_71852_2_, p_71852_3_, p_71852_4_ - 1, this.field_71990_ca);
            p_71852_1_.func_72898_h(p_71852_2_, p_71852_3_, p_71852_4_ + 1, this.field_71990_ca);
        }
    }

    public int func_71865_a(IBlockAccess p_71865_1_, int p_71865_2_, int p_71865_3_, int p_71865_4_, int p_71865_5_)
    {
        if (!this.field_72130_a)
        {
            return 0;
        }
        else
        {
            int i1 = p_71865_1_.func_72805_g(p_71865_2_, p_71865_3_, p_71865_4_);
            return i1 == 5 && p_71865_5_ == 1 ? 0 : (i1 == 3 && p_71865_5_ == 3 ? 0 : (i1 == 4 && p_71865_5_ == 2 ? 0 : (i1 == 1 && p_71865_5_ == 5 ? 0 : (i1 == 2 && p_71865_5_ == 4 ? 0 : 15))));
        }
    }

    private boolean func_72128_l(World p_72128_1_, int p_72128_2_, int p_72128_3_, int p_72128_4_)
    {
        int l = p_72128_1_.func_72805_g(p_72128_2_, p_72128_3_, p_72128_4_);
        return l == 5 && p_72128_1_.func_94574_k(p_72128_2_, p_72128_3_ - 1, p_72128_4_, 0) ? true : (l == 3 && p_72128_1_.func_94574_k(p_72128_2_, p_72128_3_, p_72128_4_ - 1, 2) ? true : (l == 4 && p_72128_1_.func_94574_k(p_72128_2_, p_72128_3_, p_72128_4_ + 1, 3) ? true : (l == 1 && p_72128_1_.func_94574_k(p_72128_2_ - 1, p_72128_3_, p_72128_4_, 4) ? true : l == 2 && p_72128_1_.func_94574_k(p_72128_2_ + 1, p_72128_3_, p_72128_4_, 5))));
    }

    public void func_71847_b(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
    {
        boolean flag = this.func_72128_l(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_);
        List list = (List)field_72129_b.get(p_71847_1_);

        while (list != null && !list.isEmpty() && p_71847_1_.func_82737_E() - ((RedstoneUpdateInfo)list.get(0)).field_73661_d > 60L)
        {
            list.remove(0);
        }

        // CraftBukkit start
        org.bukkit.plugin.PluginManager manager = p_71847_1_.getServer().getPluginManager();
        org.bukkit.block.Block block = p_71847_1_.getWorld().getBlockAt(p_71847_2_, p_71847_3_, p_71847_4_);
        int oldCurrent = this.field_72130_a ? 15 : 0;
        BlockRedstoneEvent event = new BlockRedstoneEvent(block, oldCurrent, oldCurrent);
        // CraftBukkit end

        if (this.field_72130_a)
        {
            if (flag)
            {
                // CraftBukkit start
                if (oldCurrent != 0)
                {
                    event.setNewCurrent(0);
                    manager.callEvent(event);

                    if (event.getNewCurrent() != 0)
                    {
                        return;
                    }
                }

                // CraftBukkit end
                p_71847_1_.func_72832_d(p_71847_2_, p_71847_3_, p_71847_4_, Block.field_72049_aP.field_71990_ca, p_71847_1_.func_72805_g(p_71847_2_, p_71847_3_, p_71847_4_), 3);

                if (this.func_72127_a(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_, true))
                {
                    p_71847_1_.func_72908_a((double)((float)p_71847_2_ + 0.5F), (double)((float)p_71847_3_ + 0.5F), (double)((float)p_71847_4_ + 0.5F), "random.fizz", 0.5F, 2.6F + (p_71847_1_.field_73012_v.nextFloat() - p_71847_1_.field_73012_v.nextFloat()) * 0.8F);

                    for (int l = 0; l < 5; ++l)
                    {
                        double d0 = (double)p_71847_2_ + p_71847_5_.nextDouble() * 0.6D + 0.2D;
                        double d1 = (double)p_71847_3_ + p_71847_5_.nextDouble() * 0.6D + 0.2D;
                        double d2 = (double)p_71847_4_ + p_71847_5_.nextDouble() * 0.6D + 0.2D;
                        p_71847_1_.func_72869_a("smoke", d0, d1, d2, 0.0D, 0.0D, 0.0D);
                    }
                }
            }
        }
        else if (!flag && !this.func_72127_a(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_, false))
        {
            // CraftBukkit start
            if (oldCurrent != 15)
            {
                event.setNewCurrent(15);
                manager.callEvent(event);

                if (event.getNewCurrent() != 15)
                {
                    return;
                }
            }

            // CraftBukkit end
            p_71847_1_.func_72832_d(p_71847_2_, p_71847_3_, p_71847_4_, Block.field_72035_aQ.field_71990_ca, p_71847_1_.func_72805_g(p_71847_2_, p_71847_3_, p_71847_4_), 3);
        }
    }

    public void func_71863_a(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
    {
        if (!this.func_94397_d(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, p_71863_5_))
        {
            boolean flag = this.func_72128_l(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_);

            if (this.field_72130_a && flag || !this.field_72130_a && !flag)
            {
                p_71863_1_.func_72836_a(p_71863_2_, p_71863_3_, p_71863_4_, this.field_71990_ca, this.func_71859_p_(p_71863_1_));
            }
        }
    }

    public int func_71855_c(IBlockAccess p_71855_1_, int p_71855_2_, int p_71855_3_, int p_71855_4_, int p_71855_5_)
    {
        return p_71855_5_ == 0 ? this.func_71865_a(p_71855_1_, p_71855_2_, p_71855_3_, p_71855_4_, p_71855_5_) : 0;
    }

    public int func_71885_a(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
    {
        return Block.field_72035_aQ.field_71990_ca;
    }

    public boolean func_71853_i()
    {
        return true;
    }

    public boolean func_94334_h(int p_94334_1_)
    {
        return p_94334_1_ == Block.field_72049_aP.field_71990_ca || p_94334_1_ == Block.field_72035_aQ.field_71990_ca;
    }
}
