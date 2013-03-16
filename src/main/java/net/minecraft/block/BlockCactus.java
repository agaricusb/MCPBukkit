package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import org.bukkit.event.entity.EntityDamageByBlockEvent; // CraftBukkit

public class BlockCactus extends Block
{
    protected BlockCactus(int p_i9043_1_)
    {
        super(p_i9043_1_, Material.field_76268_x);
        this.func_71907_b(true);
        this.func_71849_a(CreativeTabs.field_78031_c);
    }

    public void func_71847_b(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
    {
        if (p_71847_1_.func_72799_c(p_71847_2_, p_71847_3_ + 1, p_71847_4_))
        {
            int l;

            for (l = 1; p_71847_1_.func_72798_a(p_71847_2_, p_71847_3_ - l, p_71847_4_) == this.field_71990_ca; ++l)
            {
                ;
            }

            if (l < 3)
            {
                int i1 = p_71847_1_.func_72805_g(p_71847_2_, p_71847_3_, p_71847_4_);

                if (i1 == 15)
                {
                    org.bukkit.craftbukkit.event.CraftEventFactory.handleBlockGrowEvent(p_71847_1_, p_71847_2_, p_71847_3_ + 1, p_71847_4_, this.field_71990_ca, 0); // CraftBukkit
                    p_71847_1_.func_72921_c(p_71847_2_, p_71847_3_, p_71847_4_, 0, 4);
                    this.func_71863_a(p_71847_1_, p_71847_2_, p_71847_3_ + 1, p_71847_4_, this.field_71990_ca);
                }
                else
                {
                    p_71847_1_.func_72921_c(p_71847_2_, p_71847_3_, p_71847_4_, i1 + 1, 4);
                }
            }
        }
    }

    public AxisAlignedBB func_71872_e(World p_71872_1_, int p_71872_2_, int p_71872_3_, int p_71872_4_)
    {
        float f = 0.0625F;
        return AxisAlignedBB.func_72332_a().func_72299_a((double)((float)p_71872_2_ + f), (double)p_71872_3_, (double)((float)p_71872_4_ + f), (double)((float)(p_71872_2_ + 1) - f), (double)((float)(p_71872_3_ + 1) - f), (double)((float)(p_71872_4_ + 1) - f));
    }

    public boolean func_71886_c()
    {
        return false;
    }

    public boolean func_71926_d()
    {
        return false;
    }

    public int func_71857_b()
    {
        return 13;
    }

    public boolean func_71930_b(World p_71930_1_, int p_71930_2_, int p_71930_3_, int p_71930_4_)
    {
        return !super.func_71930_b(p_71930_1_, p_71930_2_, p_71930_3_, p_71930_4_) ? false : this.func_71854_d(p_71930_1_, p_71930_2_, p_71930_3_, p_71930_4_);
    }

    public void func_71863_a(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
    {
        if (!this.func_71854_d(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_))
        {
            p_71863_1_.func_94578_a(p_71863_2_, p_71863_3_, p_71863_4_, true);
        }
    }

    public boolean func_71854_d(World p_71854_1_, int p_71854_2_, int p_71854_3_, int p_71854_4_)
    {
        if (p_71854_1_.func_72803_f(p_71854_2_ - 1, p_71854_3_, p_71854_4_).func_76220_a())
        {
            return false;
        }
        else if (p_71854_1_.func_72803_f(p_71854_2_ + 1, p_71854_3_, p_71854_4_).func_76220_a())
        {
            return false;
        }
        else if (p_71854_1_.func_72803_f(p_71854_2_, p_71854_3_, p_71854_4_ - 1).func_76220_a())
        {
            return false;
        }
        else if (p_71854_1_.func_72803_f(p_71854_2_, p_71854_3_, p_71854_4_ + 1).func_76220_a())
        {
            return false;
        }
        else
        {
            int l = p_71854_1_.func_72798_a(p_71854_2_, p_71854_3_ - 1, p_71854_4_);
            return l == Block.field_72038_aV.field_71990_ca || l == Block.field_71939_E.field_71990_ca;
        }
    }

    public void func_71869_a(World p_71869_1_, int p_71869_2_, int p_71869_3_, int p_71869_4_, Entity p_71869_5_)
    {
        // CraftBukkit start - EntityDamageByBlock event
        if (p_71869_5_ instanceof EntityLiving)
        {
            org.bukkit.block.Block damager = p_71869_1_.getWorld().getBlockAt(p_71869_2_, p_71869_3_, p_71869_4_);
            org.bukkit.entity.Entity damagee = (p_71869_5_ == null) ? null : p_71869_5_.getBukkitEntity();
            EntityDamageByBlockEvent event = new EntityDamageByBlockEvent(damager, damagee, org.bukkit.event.entity.EntityDamageEvent.DamageCause.CONTACT, 1);
            p_71869_1_.getServer().getPluginManager().callEvent(event);

            if (!event.isCancelled())
            {
                damagee.setLastDamageCause(event);
                p_71869_5_.func_70097_a(DamageSource.field_76367_g, event.getDamage());
            }

            return;
        }

        // CraftBukkit end
        p_71869_5_.func_70097_a(DamageSource.field_76367_g, 1);
    }
}
