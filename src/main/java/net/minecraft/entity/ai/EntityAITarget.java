package net.minecraft.entity.ai;

import org.bukkit.event.entity.EntityTargetEvent; // CraftBukkit
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.MathHelper;

public abstract class EntityAITarget extends EntityAIBase
{
    protected EntityLiving field_75299_d;
    protected float field_75300_e;
    protected boolean field_75297_f;
    private boolean field_75303_a;
    private int field_75301_b;
    private int field_75302_c;
    private int field_75298_g;

    public EntityAITarget(EntityLiving p_i3505_1_, float p_i3505_2_, boolean p_i3505_3_)
    {
        this(p_i3505_1_, p_i3505_2_, p_i3505_3_, false);
    }

    public EntityAITarget(EntityLiving p_i3506_1_, float p_i3506_2_, boolean p_i3506_3_, boolean p_i3506_4_)
    {
        this.field_75301_b = 0;
        this.field_75302_c = 0;
        this.field_75298_g = 0;
        this.field_75299_d = p_i3506_1_;
        this.field_75300_e = p_i3506_2_;
        this.field_75297_f = p_i3506_3_;
        this.field_75303_a = p_i3506_4_;
    }

    public boolean func_75253_b()
    {
        EntityLiving entityliving = this.field_75299_d.func_70638_az();

        if (entityliving == null)
        {
            return false;
        }
        else if (!entityliving.func_70089_S())
        {
            return false;
        }
        else if (this.field_75299_d.func_70068_e(entityliving) > (double)(this.field_75300_e * this.field_75300_e))
        {
            return false;
        }
        else
        {
            if (this.field_75297_f)
            {
                if (this.field_75299_d.func_70635_at().func_75522_a(entityliving))
                {
                    this.field_75298_g = 0;
                }
                else if (++this.field_75298_g > 60)
                {
                    return false;
                }
            }

            return true;
        }
    }

    public void func_75249_e()
    {
        this.field_75301_b = 0;
        this.field_75302_c = 0;
        this.field_75298_g = 0;
    }

    public void func_75251_c()
    {
        this.field_75299_d.func_70624_b((EntityLiving)null);
    }

    protected boolean func_75296_a(EntityLiving p_75296_1_, boolean p_75296_2_)
    {
        if (p_75296_1_ == null)
        {
            return false;
        }
        else if (p_75296_1_ == this.field_75299_d)
        {
            return false;
        }
        else if (!p_75296_1_.func_70089_S())
        {
            return false;
        }
        else if (!this.field_75299_d.func_70686_a(p_75296_1_.getClass()))
        {
            return false;
        }
        else
        {
            if (this.field_75299_d instanceof EntityTameable && ((EntityTameable)this.field_75299_d).func_70909_n())
            {
                if (p_75296_1_ instanceof EntityTameable && ((EntityTameable)p_75296_1_).func_70909_n())
                {
                    return false;
                }

                if (p_75296_1_ == ((EntityTameable)this.field_75299_d).func_70902_q())
                {
                    return false;
                }
            }
            else if (p_75296_1_ instanceof EntityPlayer && !p_75296_2_ && ((EntityPlayer)p_75296_1_).field_71075_bZ.field_75102_a)
            {
                return false;
            }

            if (!this.field_75299_d.func_70649_d(MathHelper.func_76128_c(p_75296_1_.field_70165_t), MathHelper.func_76128_c(p_75296_1_.field_70163_u), MathHelper.func_76128_c(p_75296_1_.field_70161_v)))
            {
                return false;
            }
            else if (this.field_75297_f && !this.field_75299_d.func_70635_at().func_75522_a(p_75296_1_))
            {
                return false;
            }
            else
            {
                if (this.field_75303_a)
                {
                    if (--this.field_75302_c <= 0)
                    {
                        this.field_75301_b = 0;
                    }

                    if (this.field_75301_b == 0)
                    {
                        this.field_75301_b = this.func_75295_a(p_75296_1_) ? 1 : 2;
                    }

                    if (this.field_75301_b == 2)
                    {
                        return false;
                    }
                }

                // CraftBukkit start - check all the different target goals for the reason, default to RANDOM_TARGET
                EntityTargetEvent.TargetReason reason = EntityTargetEvent.TargetReason.RANDOM_TARGET;

                if (this instanceof EntityAIDefendVillage)
                {
                    reason = EntityTargetEvent.TargetReason.DEFEND_VILLAGE;
                }
                else if (this instanceof EntityAIHurtByTarget)
                {
                    reason = EntityTargetEvent.TargetReason.TARGET_ATTACKED_ENTITY;
                }
                else if (this instanceof EntityAINearestAttackableTarget)
                {
                    if (p_75296_1_ instanceof EntityPlayer)
                    {
                        reason = EntityTargetEvent.TargetReason.CLOSEST_PLAYER;
                    }
                }
                else if (this instanceof EntityAIOwnerHurtByTarget)
                {
                    reason = EntityTargetEvent.TargetReason.TARGET_ATTACKED_OWNER;
                }
                else if (this instanceof EntityAIOwnerHurtTarget)
                {
                    reason = EntityTargetEvent.TargetReason.OWNER_ATTACKED_TARGET;
                }

                org.bukkit.event.entity.EntityTargetLivingEntityEvent event = org.bukkit.craftbukkit.event.CraftEventFactory.callEntityTargetLivingEvent(this.field_75299_d, p_75296_1_, reason);

                if (event.isCancelled() || event.getTarget() == null)
                {
                    this.field_75299_d.func_70624_b(null);
                    return false;
                }
                else if (p_75296_1_.getBukkitEntity() != event.getTarget())
                {
                    this.field_75299_d.func_70624_b((EntityLiving)((org.bukkit.craftbukkit.entity.CraftEntity) event.getTarget()).getHandle());
                }

                if (this.field_75299_d instanceof EntityCreature)
                {
                    ((EntityCreature) this.field_75299_d).field_70789_a = ((org.bukkit.craftbukkit.entity.CraftEntity) event.getTarget()).getHandle();
                }

                // CraftBukkit end
                return true;
            }
        }
    }

    private boolean func_75295_a(EntityLiving p_75295_1_)
    {
        this.field_75302_c = 10 + this.field_75299_d.func_70681_au().nextInt(5);
        PathEntity pathentity = this.field_75299_d.func_70661_as().func_75494_a(p_75295_1_);

        if (pathentity == null)
        {
            return false;
        }
        else
        {
            PathPoint pathpoint = pathentity.func_75870_c();

            if (pathpoint == null)
            {
                return false;
            }
            else
            {
                int i = pathpoint.field_75839_a - MathHelper.func_76128_c(p_75295_1_.field_70165_t);
                int j = pathpoint.field_75838_c - MathHelper.func_76128_c(p_75295_1_.field_70161_v);
                return (double)(i * i + j * j) <= 2.25D;
            }
        }
    }
}
