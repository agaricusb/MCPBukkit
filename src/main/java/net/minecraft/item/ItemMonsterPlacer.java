package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
public class ItemMonsterPlacer extends Item
{
    public ItemMonsterPlacer(int p_i3671_1_)
    {
        super(p_i3671_1_);
        this.func_77627_a(true);
        this.func_77637_a(CreativeTabs.field_78026_f);
    }

    public String func_77657_g(ItemStack itemstack)
    {
        String s = ("" + StatCollector.func_74838_a(this.func_77658_a() + ".name")).trim();
        String s1 = EntityList.func_75617_a(itemstack.func_77960_j());

        if (s1 != null)
        {
            s = s + " " + StatCollector.func_74838_a("entity." + s1 + ".name");
        }

        return s;
    }

    public boolean func_77648_a(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
        if (p_77648_3_.field_72995_K || p_77648_1_.func_77960_j() == 48 || p_77648_1_.func_77960_j() == 49 || p_77648_1_.func_77960_j() == 63 || p_77648_1_.func_77960_j() == 64)   // CraftBukkit
        {
            return true;
        }
        else
        {
            int i1 = p_77648_3_.func_72798_a(p_77648_4_, p_77648_5_, p_77648_6_);
            p_77648_4_ += Facing.field_71586_b[p_77648_7_];
            p_77648_5_ += Facing.field_71587_c[p_77648_7_];
            p_77648_6_ += Facing.field_71585_d[p_77648_7_];
            double d0 = 0.0D;

            if (p_77648_7_ == 1 && Block.field_71973_m[i1] != null && Block.field_71973_m[i1].func_71857_b() == 11)
            {
                d0 = 0.5D;
            }

            Entity entity = func_77840_a(p_77648_3_, p_77648_1_.func_77960_j(), (double)p_77648_4_ + 0.5D, (double)p_77648_5_ + d0, (double)p_77648_6_ + 0.5D);

            if (entity != null)
            {
                if (entity instanceof EntityLiving && p_77648_1_.func_82837_s())
                {
                    ((EntityLiving)entity).func_94058_c(p_77648_1_.func_82833_r());
                }

                if (!p_77648_2_.field_71075_bZ.field_75098_d)
                {
                    --p_77648_1_.field_77994_a;
                }
            }

            return true;
        }
    }

    public static Entity func_77840_a(World p_77840_0_, int p_77840_1_, double p_77840_2_, double p_77840_4_, double p_77840_6_)
    {
        if (!EntityList.field_75627_a.containsKey(Integer.valueOf(p_77840_1_)))
        {
            return null;
        }
        else
        {
            Entity entity = null;

            for (int j = 0; j < 1; ++j)
            {
                entity = EntityList.func_75616_a(p_77840_1_, p_77840_0_);

                if (entity != null && entity instanceof EntityLiving)
                {
                    EntityLiving entityliving = (EntityLiving)entity;
                    entity.func_70012_b(p_77840_2_, p_77840_4_, p_77840_6_, MathHelper.func_76142_g(p_77840_0_.field_73012_v.nextFloat() * 360.0F), 0.0F);
                    entityliving.field_70759_as = entityliving.field_70177_z;
                    entityliving.field_70761_aq = entityliving.field_70177_z;
                    entityliving.func_82163_bD();
                    p_77840_0_.addEntity(entity, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.SPAWNER_EGG); // CraftBukkit
                    entityliving.func_70642_aH();
                }
            }

            return entity;
        }
    }
}
