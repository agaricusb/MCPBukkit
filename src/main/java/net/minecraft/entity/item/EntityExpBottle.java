package net.minecraft.entity.item;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
public class EntityExpBottle extends EntityThrowable
{
    public EntityExpBottle(World p_i3592_1_)
    {
        super(p_i3592_1_);
    }

    public EntityExpBottle(World p_i3593_1_, EntityLiving p_i3593_2_)
    {
        super(p_i3593_1_, p_i3593_2_);
    }

    public EntityExpBottle(World p_i3594_1_, double p_i3594_2_, double p_i3594_4_, double p_i3594_6_)
    {
        super(p_i3594_1_, p_i3594_2_, p_i3594_4_, p_i3594_6_);
    }

    protected float func_70185_h()
    {
        return 0.07F;
    }

    protected float func_70182_d()
    {
        return 0.7F;
    }

    protected float func_70183_g()
    {
        return -20.0F;
    }

    protected void func_70184_a(MovingObjectPosition p_70184_1_)
    {
        if (!this.field_70170_p.field_72995_K)
        {
            // CraftBukkit moved after event
            // this.world.triggerEffect(2002, (int) Math.round(this.locX), (int) Math.round(this.locY), (int) Math.round(this.locZ), 0);
            int i = 3 + this.field_70170_p.field_73012_v.nextInt(5) + this.field_70170_p.field_73012_v.nextInt(5);
            // CraftBukkit start
            org.bukkit.event.entity.ExpBottleEvent event = org.bukkit.craftbukkit.event.CraftEventFactory.callExpBottleEvent(this, i);
            i = event.getExperience();

            if (event.getShowEffect())
            {
                this.field_70170_p.func_72926_e(2002, (int) Math.round(this.field_70165_t), (int) Math.round(this.field_70163_u), (int) Math.round(this.field_70161_v), 0);
            }

            // CraftBukkit end

            while (i > 0)
            {
                int j = EntityXPOrb.func_70527_a(i);
                i -= j;
                this.field_70170_p.func_72838_d(new EntityXPOrb(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, j));
            }

            this.func_70106_y();
        }
    }
}
