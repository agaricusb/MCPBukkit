package net.minecraft.entity.projectile;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import org.bukkit.event.entity.ProjectileHitEvent; // CraftBukkit

public abstract class EntityThrowable extends Entity implements IProjectile
{
    private int field_70189_d = -1;
    private int field_70190_e = -1;
    private int field_70187_f = -1;
    private int field_70188_g = 0;
    protected boolean field_70193_a = false;
    public int field_70191_b = 0;
    public EntityLiving field_70192_c; // CraftBukkit - private -> public
    public String field_85053_h = null; // CraftBukkit - private -> public
    private int field_70194_h;
    private int field_70195_i = 0;

    public EntityThrowable(World p_i3583_1_)
    {
        super(p_i3583_1_);
        this.func_70105_a(0.25F, 0.25F);
    }

    protected void func_70088_a() {}

    public EntityThrowable(World p_i3584_1_, EntityLiving p_i3584_2_)
    {
        super(p_i3584_1_);
        this.field_70192_c = p_i3584_2_;
        this.func_70105_a(0.25F, 0.25F);
        this.func_70012_b(p_i3584_2_.field_70165_t, p_i3584_2_.field_70163_u + (double)p_i3584_2_.func_70047_e(), p_i3584_2_.field_70161_v, p_i3584_2_.field_70177_z, p_i3584_2_.field_70125_A);
        this.field_70165_t -= (double)(MathHelper.func_76134_b(this.field_70177_z / 180.0F * (float)Math.PI) * 0.16F);
        this.field_70163_u -= 0.10000000149011612D;
        this.field_70161_v -= (double)(MathHelper.func_76126_a(this.field_70177_z / 180.0F * (float)Math.PI) * 0.16F);
        this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
        this.field_70129_M = 0.0F;
        float f = 0.4F;
        this.field_70159_w = (double)(-MathHelper.func_76126_a(this.field_70177_z / 180.0F * (float)Math.PI) * MathHelper.func_76134_b(this.field_70125_A / 180.0F * (float)Math.PI) * f);
        this.field_70179_y = (double)(MathHelper.func_76134_b(this.field_70177_z / 180.0F * (float)Math.PI) * MathHelper.func_76134_b(this.field_70125_A / 180.0F * (float)Math.PI) * f);
        this.field_70181_x = (double)(-MathHelper.func_76126_a((this.field_70125_A + this.func_70183_g()) / 180.0F * (float)Math.PI) * f);
        this.func_70186_c(this.field_70159_w, this.field_70181_x, this.field_70179_y, this.func_70182_d(), 1.0F);
    }

    public EntityThrowable(World p_i3585_1_, double p_i3585_2_, double p_i3585_4_, double p_i3585_6_)
    {
        super(p_i3585_1_);
        this.field_70194_h = 0;
        this.func_70105_a(0.25F, 0.25F);
        this.func_70107_b(p_i3585_2_, p_i3585_4_, p_i3585_6_);
        this.field_70129_M = 0.0F;
    }

    protected float func_70182_d()
    {
        return 1.5F;
    }

    protected float func_70183_g()
    {
        return 0.0F;
    }

    public void func_70186_c(double p_70186_1_, double p_70186_3_, double p_70186_5_, float p_70186_7_, float p_70186_8_)
    {
        float f2 = MathHelper.func_76133_a(p_70186_1_ * p_70186_1_ + p_70186_3_ * p_70186_3_ + p_70186_5_ * p_70186_5_);
        p_70186_1_ /= (double)f2;
        p_70186_3_ /= (double)f2;
        p_70186_5_ /= (double)f2;
        p_70186_1_ += this.field_70146_Z.nextGaussian() * 0.007499999832361937D * (double)p_70186_8_;
        p_70186_3_ += this.field_70146_Z.nextGaussian() * 0.007499999832361937D * (double)p_70186_8_;
        p_70186_5_ += this.field_70146_Z.nextGaussian() * 0.007499999832361937D * (double)p_70186_8_;
        p_70186_1_ *= (double)p_70186_7_;
        p_70186_3_ *= (double)p_70186_7_;
        p_70186_5_ *= (double)p_70186_7_;
        this.field_70159_w = p_70186_1_;
        this.field_70181_x = p_70186_3_;
        this.field_70179_y = p_70186_5_;
        float f3 = MathHelper.func_76133_a(p_70186_1_ * p_70186_1_ + p_70186_5_ * p_70186_5_);
        this.field_70126_B = this.field_70177_z = (float)(Math.atan2(p_70186_1_, p_70186_5_) * 180.0D / Math.PI);
        this.field_70127_C = this.field_70125_A = (float)(Math.atan2(p_70186_3_, (double)f3) * 180.0D / Math.PI);
        this.field_70194_h = 0;
    }

    public void func_70071_h_()
    {
        this.field_70142_S = this.field_70165_t;
        this.field_70137_T = this.field_70163_u;
        this.field_70136_U = this.field_70161_v;
        super.func_70071_h_();

        if (this.field_70191_b > 0)
        {
            --this.field_70191_b;
        }

        if (this.field_70193_a)
        {
            int i = this.field_70170_p.func_72798_a(this.field_70189_d, this.field_70190_e, this.field_70187_f);

            if (i == this.field_70188_g)
            {
                ++this.field_70194_h;

                if (this.field_70194_h == 1200)
                {
                    this.func_70106_y();
                }

                return;
            }

            this.field_70193_a = false;
            this.field_70159_w *= (double)(this.field_70146_Z.nextFloat() * 0.2F);
            this.field_70181_x *= (double)(this.field_70146_Z.nextFloat() * 0.2F);
            this.field_70179_y *= (double)(this.field_70146_Z.nextFloat() * 0.2F);
            this.field_70194_h = 0;
            this.field_70195_i = 0;
        }
        else
        {
            ++this.field_70195_i;
        }

        Vec3 vec3 = this.field_70170_p.func_82732_R().func_72345_a(this.field_70165_t, this.field_70163_u, this.field_70161_v);
        Vec3 vec31 = this.field_70170_p.func_82732_R().func_72345_a(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
        MovingObjectPosition movingobjectposition = this.field_70170_p.func_72933_a(vec3, vec31);
        vec3 = this.field_70170_p.func_82732_R().func_72345_a(this.field_70165_t, this.field_70163_u, this.field_70161_v);
        vec31 = this.field_70170_p.func_82732_R().func_72345_a(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);

        if (movingobjectposition != null)
        {
            vec31 = this.field_70170_p.func_82732_R().func_72345_a(movingobjectposition.field_72307_f.field_72450_a, movingobjectposition.field_72307_f.field_72448_b, movingobjectposition.field_72307_f.field_72449_c);
        }

        if (!this.field_70170_p.field_72995_K)
        {
            Entity entity = null;
            List list = this.field_70170_p.func_72839_b(this, this.field_70121_D.func_72321_a(this.field_70159_w, this.field_70181_x, this.field_70179_y).func_72314_b(1.0D, 1.0D, 1.0D));
            double d0 = 0.0D;
            EntityLiving entityliving = this.func_85052_h();

            for (int j = 0; j < list.size(); ++j)
            {
                Entity entity1 = (Entity)list.get(j);

                if (entity1.func_70067_L() && (entity1 != entityliving || this.field_70195_i >= 5))
                {
                    float f = 0.3F;
                    AxisAlignedBB axisalignedbb = entity1.field_70121_D.func_72314_b((double)f, (double)f, (double)f);
                    MovingObjectPosition movingobjectposition1 = axisalignedbb.func_72327_a(vec3, vec31);

                    if (movingobjectposition1 != null)
                    {
                        double d1 = vec3.func_72436_e(movingobjectposition1.field_72307_f); // CraftBukkit - distance efficiency

                        if (d1 < d0 || d0 == 0.0D)
                        {
                            entity = entity1;
                            d0 = d1;
                        }
                    }
                }
            }

            if (entity != null)
            {
                movingobjectposition = new MovingObjectPosition(entity);
            }
        }

        if (movingobjectposition != null)
        {
            if (movingobjectposition.field_72313_a == EnumMovingObjectType.TILE && this.field_70170_p.func_72798_a(movingobjectposition.field_72311_b, movingobjectposition.field_72312_c, movingobjectposition.field_72309_d) == Block.field_72015_be.field_71990_ca)
            {
                this.func_70063_aa();
            }
            else
            {
                this.func_70184_a(movingobjectposition);

                // CraftBukkit start
                if (this.field_70128_L)
                {
                    ProjectileHitEvent hitEvent = new ProjectileHitEvent((org.bukkit.entity.Projectile) this.getBukkitEntity());
                    org.bukkit.Bukkit.getPluginManager().callEvent(hitEvent);
                }

                // CraftBukkit end
            }
        }

        this.field_70165_t += this.field_70159_w;
        this.field_70163_u += this.field_70181_x;
        this.field_70161_v += this.field_70179_y;
        float f1 = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
        this.field_70177_z = (float)(Math.atan2(this.field_70159_w, this.field_70179_y) * 180.0D / Math.PI);

        for (this.field_70125_A = (float)(Math.atan2(this.field_70181_x, (double)f1) * 180.0D / Math.PI); this.field_70125_A - this.field_70127_C < -180.0F; this.field_70127_C -= 360.0F)
        {
            ;
        }

        while (this.field_70125_A - this.field_70127_C >= 180.0F)
        {
            this.field_70127_C += 360.0F;
        }

        while (this.field_70177_z - this.field_70126_B < -180.0F)
        {
            this.field_70126_B -= 360.0F;
        }

        while (this.field_70177_z - this.field_70126_B >= 180.0F)
        {
            this.field_70126_B += 360.0F;
        }

        this.field_70125_A = this.field_70127_C + (this.field_70125_A - this.field_70127_C) * 0.2F;
        this.field_70177_z = this.field_70126_B + (this.field_70177_z - this.field_70126_B) * 0.2F;
        float f2 = 0.99F;
        float f3 = this.func_70185_h();

        if (this.func_70090_H())
        {
            for (int k = 0; k < 4; ++k)
            {
                float f4 = 0.25F;
                this.field_70170_p.func_72869_a("bubble", this.field_70165_t - this.field_70159_w * (double)f4, this.field_70163_u - this.field_70181_x * (double)f4, this.field_70161_v - this.field_70179_y * (double)f4, this.field_70159_w, this.field_70181_x, this.field_70179_y);
            }

            f2 = 0.8F;
        }

        this.field_70159_w *= (double)f2;
        this.field_70181_x *= (double)f2;
        this.field_70179_y *= (double)f2;
        this.field_70181_x -= (double)f3;
        this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
    }

    protected float func_70185_h()
    {
        return 0.03F;
    }

    protected abstract void func_70184_a(MovingObjectPosition movingobjectposition);

    public void func_70014_b(NBTTagCompound p_70014_1_)
    {
        p_70014_1_.func_74777_a("xTile", (short)this.field_70189_d);
        p_70014_1_.func_74777_a("yTile", (short)this.field_70190_e);
        p_70014_1_.func_74777_a("zTile", (short)this.field_70187_f);
        p_70014_1_.func_74774_a("inTile", (byte)this.field_70188_g);
        p_70014_1_.func_74774_a("shake", (byte)this.field_70191_b);
        p_70014_1_.func_74774_a("inGround", (byte)(this.field_70193_a ? 1 : 0));

        if ((this.field_85053_h == null || this.field_85053_h.length() == 0) && this.field_70192_c != null && this.field_70192_c instanceof EntityPlayer)
        {
            this.field_85053_h = this.field_70192_c.func_70023_ak();
        }

        p_70014_1_.func_74778_a("ownerName", this.field_85053_h == null ? "" : this.field_85053_h);
    }

    public void func_70037_a(NBTTagCompound p_70037_1_)
    {
        this.field_70189_d = p_70037_1_.func_74765_d("xTile");
        this.field_70190_e = p_70037_1_.func_74765_d("yTile");
        this.field_70187_f = p_70037_1_.func_74765_d("zTile");
        this.field_70188_g = p_70037_1_.func_74771_c("inTile") & 255;
        this.field_70191_b = p_70037_1_.func_74771_c("shake") & 255;
        this.field_70193_a = p_70037_1_.func_74771_c("inGround") == 1;
        this.field_85053_h = p_70037_1_.func_74779_i("ownerName");

        if (this.field_85053_h != null && this.field_85053_h.length() == 0)
        {
            this.field_85053_h = null;
        }
    }

    public EntityLiving func_85052_h()
    {
        if (this.field_70192_c == null && this.field_85053_h != null && this.field_85053_h.length() > 0)
        {
            this.field_70192_c = this.field_70170_p.func_72924_a(this.field_85053_h);
        }

        return this.field_70192_c;
    }
}
