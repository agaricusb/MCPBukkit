package net.minecraft.block;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Direction;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.bukkit.event.entity.EntityInteractEvent; // CraftBukkit

public class BlockTripWire extends Block
{
    public BlockTripWire(int p_i4018_1_)
    {
        super(p_i4018_1_, Material.field_76265_p);
        this.func_71905_a(0.0F, 0.0F, 0.0F, 1.0F, 0.15625F, 1.0F);
        this.func_71907_b(true);
    }

    public int func_71859_p_(World p_71859_1_)
    {
        return 10;
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
        return 30;
    }

    public int func_71885_a(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
    {
        return Item.field_77683_K.field_77779_bT;
    }

    public void func_71863_a(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
    {
        int i1 = p_71863_1_.func_72805_g(p_71863_2_, p_71863_3_, p_71863_4_);
        boolean flag = (i1 & 2) == 2;
        boolean flag1 = !p_71863_1_.func_72797_t(p_71863_2_, p_71863_3_ - 1, p_71863_4_);

        if (flag != flag1)
        {
            this.func_71897_c(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, i1, 0);
            p_71863_1_.func_94571_i(p_71863_2_, p_71863_3_, p_71863_4_);
        }
    }

    public void func_71902_a(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_)
    {
        int l = p_71902_1_.func_72805_g(p_71902_2_, p_71902_3_, p_71902_4_);
        boolean flag = (l & 4) == 4;
        boolean flag1 = (l & 2) == 2;

        if (!flag1)
        {
            this.func_71905_a(0.0F, 0.0F, 0.0F, 1.0F, 0.09375F, 1.0F);
        }
        else if (!flag)
        {
            this.func_71905_a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        }
        else
        {
            this.func_71905_a(0.0F, 0.0625F, 0.0F, 1.0F, 0.15625F, 1.0F);
        }
    }

    public void func_71861_g(World p_71861_1_, int p_71861_2_, int p_71861_3_, int p_71861_4_)
    {
        int l = p_71861_1_.func_72797_t(p_71861_2_, p_71861_3_ - 1, p_71861_4_) ? 0 : 2;
        p_71861_1_.func_72921_c(p_71861_2_, p_71861_3_, p_71861_4_, l, 3);
        this.func_72149_e(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_, l);
    }

    public void func_71852_a(World p_71852_1_, int p_71852_2_, int p_71852_3_, int p_71852_4_, int p_71852_5_, int p_71852_6_)
    {
        this.func_72149_e(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_, p_71852_6_ | 1);
    }

    public void func_71846_a(World p_71846_1_, int p_71846_2_, int p_71846_3_, int p_71846_4_, int p_71846_5_, EntityPlayer p_71846_6_)
    {
        if (!p_71846_1_.field_72995_K)
        {
            if (p_71846_6_.func_71045_bC() != null && p_71846_6_.func_71045_bC().field_77993_c == Item.field_77745_be.field_77779_bT)
            {
                p_71846_1_.func_72921_c(p_71846_2_, p_71846_3_, p_71846_4_, p_71846_5_ | 8, 4);
            }
        }
    }

    private void func_72149_e(World p_72149_1_, int p_72149_2_, int p_72149_3_, int p_72149_4_, int p_72149_5_)
    {
        int i1 = 0;

        while (i1 < 2)
        {
            int j1 = 1;

            while (true)
            {
                if (j1 < 42)
                {
                    int k1 = p_72149_2_ + Direction.field_71583_a[i1] * j1;
                    int l1 = p_72149_4_ + Direction.field_71581_b[i1] * j1;
                    int i2 = p_72149_1_.func_72798_a(k1, p_72149_3_, l1);

                    if (i2 == Block.field_72064_bT.field_71990_ca)
                    {
                        int j2 = p_72149_1_.func_72805_g(k1, p_72149_3_, l1) & 3;

                        if (j2 == Direction.field_71580_e[i1])
                        {
                            Block.field_72064_bT.func_72143_a(p_72149_1_, k1, p_72149_3_, l1, i2, p_72149_1_.func_72805_g(k1, p_72149_3_, l1), true, j1, p_72149_5_);
                        }
                    }
                    else if (i2 == Block.field_72062_bU.field_71990_ca)
                    {
                        ++j1;
                        continue;
                    }
                }

                ++i1;
                break;
            }
        }
    }

    public void func_71869_a(World p_71869_1_, int p_71869_2_, int p_71869_3_, int p_71869_4_, Entity p_71869_5_)
    {
        if (!p_71869_1_.field_72995_K)
        {
            if ((p_71869_1_.func_72805_g(p_71869_2_, p_71869_3_, p_71869_4_) & 1) != 1)
            {
                this.func_72147_l(p_71869_1_, p_71869_2_, p_71869_3_, p_71869_4_);
            }
        }
    }

    public void func_71847_b(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
    {
        if (!p_71847_1_.field_72995_K)
        {
            if ((p_71847_1_.func_72805_g(p_71847_2_, p_71847_3_, p_71847_4_) & 1) == 1)
            {
                this.func_72147_l(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_);
            }
        }
    }

    private void func_72147_l(World p_72147_1_, int p_72147_2_, int p_72147_3_, int p_72147_4_)
    {
        int l = p_72147_1_.func_72805_g(p_72147_2_, p_72147_3_, p_72147_4_);
        boolean flag = (l & 1) == 1;
        boolean flag1 = false;
        List list = p_72147_1_.func_72839_b((Entity)null, AxisAlignedBB.func_72332_a().func_72299_a((double)p_72147_2_ + this.field_72026_ch, (double)p_72147_3_ + this.field_72023_ci, (double)p_72147_4_ + this.field_72024_cj, (double)p_72147_2_ + this.field_72021_ck, (double)p_72147_3_ + this.field_72022_cl, (double)p_72147_4_ + this.field_72019_cm));

        if (!list.isEmpty())
        {
            Iterator iterator = list.iterator();

            while (iterator.hasNext())
            {
                Entity entity = (Entity)iterator.next();

                if (!entity.func_82144_au())
                {
                    flag1 = true;
                    break;
                }
            }
        }

        // CraftBukkit start
        org.bukkit.World bworld = p_72147_1_.getWorld();
        org.bukkit.plugin.PluginManager manager = p_72147_1_.getServer().getPluginManager();

        if (flag != flag1)
        {
            if (flag1)
            {
                for (Object object : list)
                {
                    if (object != null)
                    {
                        org.bukkit.event.Cancellable cancellable;

                        if (object instanceof EntityPlayer)
                        {
                            cancellable = org.bukkit.craftbukkit.event.CraftEventFactory.callPlayerInteractEvent((EntityPlayer) object, org.bukkit.event.block.Action.PHYSICAL, p_72147_2_, p_72147_3_, p_72147_4_, -1, null);
                        }
                        else if (object instanceof Entity)
                        {
                            cancellable = new EntityInteractEvent(((Entity) object).getBukkitEntity(), bworld.getBlockAt(p_72147_2_, p_72147_3_, p_72147_4_));
                            manager.callEvent((EntityInteractEvent) cancellable);
                        }
                        else
                        {
                            continue;
                        }

                        if (cancellable.isCancelled())
                        {
                            return;
                        }
                    }
                }
            }
        }

        // CraftBukkit end

        if (flag1 && !flag)
        {
            l |= 1;
        }

        if (!flag1 && flag)
        {
            l &= -2;
        }

        if (flag1 != flag)
        {
            p_72147_1_.func_72921_c(p_72147_2_, p_72147_3_, p_72147_4_, l, 3);
            this.func_72149_e(p_72147_1_, p_72147_2_, p_72147_3_, p_72147_4_, l);
        }

        if (flag1)
        {
            p_72147_1_.func_72836_a(p_72147_2_, p_72147_3_, p_72147_4_, this.field_71990_ca, this.func_71859_p_(p_72147_1_));
        }
    }
}
