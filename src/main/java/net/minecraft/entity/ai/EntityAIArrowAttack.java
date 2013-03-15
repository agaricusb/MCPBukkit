package net.minecraft.entity.ai;

import org.bukkit.event.entity.EntityTargetEvent; // CraftBukkit
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IRangedAttackMob;

public class EntityAIArrowAttack extends EntityAIBase
{
    private final EntityLiving field_75322_b;
    private final IRangedAttackMob field_82641_b;
    private EntityLiving field_75323_c;
    private int field_75320_d = 0;
    private float field_75321_e;
    private int field_75318_f = 0;
    private int field_75325_h;
    private float field_82642_h;

    public EntityAIArrowAttack(IRangedAttackMob p_i5059_1_, float p_i5059_2_, int p_i5059_3_, float p_i5059_4_)
    {
        if (!(p_i5059_1_ instanceof EntityLiving))
        {
            throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
        }
        else
        {
            this.field_82641_b = p_i5059_1_;
            this.field_75322_b = (EntityLiving)p_i5059_1_;
            this.field_75321_e = p_i5059_2_;
            this.field_75325_h = p_i5059_3_;
            this.field_82642_h = p_i5059_4_ * p_i5059_4_;
            this.field_75320_d = p_i5059_3_ / 2;
            this.func_75248_a(3);
        }
    }

    public boolean func_75250_a()
    {
        EntityLiving entityliving = this.field_75322_b.func_70638_az();

        if (entityliving == null)
        {
            return false;
        }
        else
        {
            this.field_75323_c = entityliving;
            return true;
        }
    }

    public boolean func_75253_b()
    {
        return this.func_75250_a() || !this.field_75322_b.func_70661_as().func_75500_f();
    }

    public void func_75251_c()
    {
        // CraftBukkit start
        EntityTargetEvent.TargetReason reason = this.field_75323_c.func_70089_S() ? EntityTargetEvent.TargetReason.FORGOT_TARGET : EntityTargetEvent.TargetReason.TARGET_DIED;
        org.bukkit.craftbukkit.event.CraftEventFactory.callEntityTargetEvent((Entity) field_82641_b, null, reason);
        // CraftBukkit end
        this.field_75323_c = null;
        this.field_75318_f = 0;
        this.field_75320_d = this.field_75325_h / 2;
    }

    public void func_75246_d()
    {
        double d0 = this.field_75322_b.func_70092_e(this.field_75323_c.field_70165_t, this.field_75323_c.field_70121_D.field_72338_b, this.field_75323_c.field_70161_v);
        boolean flag = this.field_75322_b.func_70635_at().func_75522_a(this.field_75323_c);

        if (flag)
        {
            ++this.field_75318_f;
        }
        else
        {
            this.field_75318_f = 0;
        }

        if (d0 <= (double)this.field_82642_h && this.field_75318_f >= 20)
        {
            this.field_75322_b.func_70661_as().func_75499_g();
        }
        else
        {
            this.field_75322_b.func_70661_as().func_75497_a(this.field_75323_c, this.field_75321_e);
        }

        this.field_75322_b.func_70671_ap().func_75651_a(this.field_75323_c, 30.0F, 30.0F);
        this.field_75320_d = Math.max(this.field_75320_d - 1, 0);

        if (this.field_75320_d <= 0)
        {
            if (d0 <= (double)this.field_82642_h && flag)
            {
                this.field_82641_b.func_82196_d(this.field_75323_c);
                this.field_75320_d = this.field_75325_h;
            }
        }
    }
}
