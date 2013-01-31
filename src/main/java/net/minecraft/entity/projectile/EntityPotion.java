package net.minecraft.entity.projectile;

import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

// CraftBukkit start
import java.util.HashMap;

import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
// CraftBukkit end

public class EntityPotion extends EntityThrowable
{
    private ItemStack field_70197_d;

    public EntityPotion(World p_i3595_1_)
    {
        super(p_i3595_1_);
    }

    public EntityPotion(World p_i3596_1_, EntityLiving p_i3596_2_, int p_i3596_3_)
    {
        this(p_i3596_1_, p_i3596_2_, new ItemStack(Item.field_77726_bs, 1, p_i3596_3_));
    }

    public EntityPotion(World p_i5070_1_, EntityLiving p_i5070_2_, ItemStack p_i5070_3_)
    {
        super(p_i5070_1_, p_i5070_2_);
        this.field_70197_d = p_i5070_3_;
    }

    public EntityPotion(World p_i5071_1_, double p_i5071_2_, double p_i5071_4_, double p_i5071_6_, ItemStack p_i5071_8_)
    {
        super(p_i5071_1_, p_i5071_2_, p_i5071_4_, p_i5071_6_);
        this.field_70197_d = p_i5071_8_;
    }

    protected float func_70185_h()
    {
        return 0.05F;
    }

    protected float func_70182_d()
    {
        return 0.5F;
    }

    protected float func_70183_g()
    {
        return -20.0F;
    }

    public void func_82340_a(int p_82340_1_)
    {
        if (this.field_70197_d == null)
        {
            this.field_70197_d = new ItemStack(Item.field_77726_bs, 1, 0);
        }

        this.field_70197_d.func_77964_b(p_82340_1_);
    }

    public int func_70196_i()
    {
        if (this.field_70197_d == null)
        {
            this.field_70197_d = new ItemStack(Item.field_77726_bs, 1, 0);
        }

        return this.field_70197_d.func_77960_j();
    }

    protected void func_70184_a(MovingObjectPosition p_70184_1_)
    {
        if (!this.field_70170_p.field_72995_K)
        {
            List list = Item.field_77726_bs.func_77832_l(this.field_70197_d);

            if (list != null && !list.isEmpty())
            {
                AxisAlignedBB axisalignedbb = this.field_70121_D.func_72314_b(4.0D, 2.0D, 4.0D);
                List list1 = this.field_70170_p.func_72872_a(EntityLiving.class, axisalignedbb);

                if (list1 != null)   // CraftBukkit - Run code even if there are no entities around
                {
                    Iterator iterator = list1.iterator();
                    // CraftBukkit
                    HashMap<LivingEntity, Double> affected = new HashMap<LivingEntity, Double>();

                    while (iterator.hasNext())
                    {
                        EntityLiving entityliving = (EntityLiving)iterator.next();
                        double d0 = this.func_70068_e(entityliving);

                        if (d0 < 16.0D)
                        {
                            double d1 = 1.0D - Math.sqrt(d0) / 4.0D;

                            if (entityliving == p_70184_1_.field_72308_g)
                            {
                                d1 = 1.0D;
                            }

                            // CraftBukkit start
                            affected.put((LivingEntity) entityliving.getBukkitEntity(), d1);
                        }
                    }

                    org.bukkit.event.entity.PotionSplashEvent event = org.bukkit.craftbukkit.event.CraftEventFactory.callPotionSplashEvent(this, affected);

                    if (!event.isCancelled())
                    {
                        for (LivingEntity victim : event.getAffectedEntities())
                        {
                            if (!(victim instanceof CraftLivingEntity))
                            {
                                continue;
                            }

                            EntityLiving entityliving = ((CraftLivingEntity) victim).getHandle();
                            double d1 = event.getIntensity(victim);
                            // CraftBukkit end
                            Iterator iterator1 = list.iterator();

                            while (iterator1.hasNext())
                            {
                                PotionEffect potioneffect = (PotionEffect)iterator1.next();
                                int i = potioneffect.func_76456_a();

                                // CraftBukkit start - abide by PVP settings - for players only!
                                if (!this.field_70170_p.pvpMode && this.func_85052_h() instanceof EntityPlayerMP && entityliving instanceof EntityPlayerMP && entityliving != this.func_85052_h())
                                {
                                    // Block SLOWER_MOVEMENT, SLOWER_DIG, HARM, BLINDNESS, HUNGER, WEAKNESS and POISON potions
                                    if (i == 2 || i == 4 || i == 7 || i == 15 || i == 17 || i == 18 || i == 19)
                                    {
                                        continue;
                                    }
                                }

                                // CraftBukkit end

                                if (Potion.field_76425_a[i].func_76403_b())
                                {
                                    // CraftBukkit - added 'this'
                                    Potion.field_76425_a[i].applyInstantEffect(this.func_85052_h(), entityliving, potioneffect.func_76458_c(), d1, this);
                                }
                                else
                                {
                                    int j = (int)(d1 * (double)potioneffect.func_76459_b() + 0.5D);

                                    if (j > 20)
                                    {
                                        entityliving.func_70690_d(new PotionEffect(i, j, potioneffect.func_76458_c()));
                                    }
                                }
                            }
                        }
                    }
                }
            }

            this.field_70170_p.func_72926_e(2002, (int)Math.round(this.field_70165_t), (int)Math.round(this.field_70163_u), (int)Math.round(this.field_70161_v), this.func_70196_i());
            this.func_70106_y();
        }
    }

    public void func_70037_a(NBTTagCompound p_70037_1_)
    {
        super.func_70037_a(p_70037_1_);

        if (p_70037_1_.func_74764_b("Potion"))
        {
            this.field_70197_d = ItemStack.func_77949_a(p_70037_1_.func_74775_l("Potion"));
        }
        else
        {
            this.func_82340_a(p_70037_1_.func_74762_e("potionValue"));
        }

        if (this.field_70197_d == null)
        {
            this.func_70106_y();
        }
    }

    public void func_70014_b(NBTTagCompound p_70014_1_)
    {
        super.func_70014_b(p_70014_1_);

        if (this.field_70197_d != null)
        {
            p_70014_1_.func_74766_a("Potion", this.field_70197_d.func_77955_b(new NBTTagCompound()));
        }
    }
}
