package net.minecraft.entity.projectile;

import org.bukkit.event.entity.ExplosionPrimeEvent; // CraftBukkit
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityLargeFireball extends EntityFireball
{
    public int field_92057_e = 1;

    public EntityLargeFireball(World p_i5067_1_)
    {
        super(p_i5067_1_);
    }

    public EntityLargeFireball(World p_i5069_1_, EntityLiving p_i5069_2_, double p_i5069_3_, double p_i5069_5_, double p_i5069_7_)
    {
        super(p_i5069_1_, p_i5069_2_, p_i5069_3_, p_i5069_5_, p_i5069_7_);
    }

    protected void func_70227_a(MovingObjectPosition p_70227_1_)
    {
        if (!this.field_70170_p.field_72995_K)
        {
            if (p_70227_1_.field_72308_g != null)
            {
                p_70227_1_.field_72308_g.func_70097_a(DamageSource.func_76362_a(this, this.field_70235_a), 6);
            }

            // CraftBukkit start
            ExplosionPrimeEvent event = new ExplosionPrimeEvent((org.bukkit.entity.Explosive) org.bukkit.craftbukkit.entity.CraftEntity.getEntity(this.field_70170_p.getServer(), this));
            this.field_70170_p.getServer().getPluginManager().callEvent(event);

            if (!event.isCancelled())
            {
                // give 'this' instead of (Entity) null so we know what causes the damage
                this.field_70170_p.func_72885_a(this, this.field_70165_t, this.field_70163_u, this.field_70161_v, event.getRadius(), event.getFire(), this.field_70170_p.func_82736_K().func_82766_b("mobGriefing"));
            }

            // CraftBukkit end
            this.func_70106_y();
        }
    }

    public void func_70014_b(NBTTagCompound p_70014_1_)
    {
        super.func_70014_b(p_70014_1_);
        p_70014_1_.func_74768_a("ExplosionPower", this.field_92057_e);
    }

    public void func_70037_a(NBTTagCompound p_70037_1_)
    {
        super.func_70037_a(p_70037_1_);

        if (p_70037_1_.func_74764_b("ExplosionPower"))
        {
            // CraftBukkit - set yield when setting explosionpower
            this.yield = this.field_92057_e = p_70037_1_.func_74762_e("ExplosionPower");
        }
    }
}
