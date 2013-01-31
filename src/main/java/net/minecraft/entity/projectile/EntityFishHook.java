package net.minecraft.entity.projectile;

import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

// CraftBukkit start
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerFishEvent;
// CraftBukkit end

public class EntityFishHook extends Entity
{
    private int field_70202_d = -1;
    private int field_70203_e = -1;
    private int field_70200_f = -1;
    private int field_70201_g = 0;
    private boolean field_70214_h = false;
    public int field_70206_a = 0;
    public EntityPlayer field_70204_b;
    private int field_70216_i;
    private int field_70211_j = 0;
    private int field_70219_an = 0;
    public Entity field_70205_c = null;
    private int field_70217_ao;
    private double field_70218_ap;
    private double field_70210_aq;
    private double field_70209_ar;
    private double field_70208_as;
    private double field_70207_at;

    public EntityFishHook(World p_i3574_1_)
    {
        super(p_i3574_1_);
        this.func_70105_a(0.25F, 0.25F);
        this.field_70158_ak = true;
    }

    public EntityFishHook(World p_i3576_1_, EntityPlayer p_i3576_2_)
    {
        super(p_i3576_1_);
        this.field_70158_ak = true;
        this.field_70204_b = p_i3576_2_;
        this.field_70204_b.field_71104_cf = this;
        this.func_70105_a(0.25F, 0.25F);
        this.func_70012_b(p_i3576_2_.field_70165_t, p_i3576_2_.field_70163_u + 1.62D - (double)p_i3576_2_.field_70129_M, p_i3576_2_.field_70161_v, p_i3576_2_.field_70177_z, p_i3576_2_.field_70125_A);
        this.field_70165_t -= (double)(MathHelper.func_76134_b(this.field_70177_z / 180.0F * (float)Math.PI) * 0.16F);
        this.field_70163_u -= 0.10000000149011612D;
        this.field_70161_v -= (double)(MathHelper.func_76126_a(this.field_70177_z / 180.0F * (float)Math.PI) * 0.16F);
        this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
        this.field_70129_M = 0.0F;
        float f = 0.4F;
        this.field_70159_w = (double)(-MathHelper.func_76126_a(this.field_70177_z / 180.0F * (float)Math.PI) * MathHelper.func_76134_b(this.field_70125_A / 180.0F * (float)Math.PI) * f);
        this.field_70179_y = (double)(MathHelper.func_76134_b(this.field_70177_z / 180.0F * (float)Math.PI) * MathHelper.func_76134_b(this.field_70125_A / 180.0F * (float)Math.PI) * f);
        this.field_70181_x = (double)(-MathHelper.func_76126_a(this.field_70125_A / 180.0F * (float)Math.PI) * f);
        this.func_70199_c(this.field_70159_w, this.field_70181_x, this.field_70179_y, 1.5F, 1.0F);
    }

    protected void func_70088_a() {}

    public void func_70199_c(double p_70199_1_, double p_70199_3_, double p_70199_5_, float p_70199_7_, float p_70199_8_)
    {
        float f2 = MathHelper.func_76133_a(p_70199_1_ * p_70199_1_ + p_70199_3_ * p_70199_3_ + p_70199_5_ * p_70199_5_);
        p_70199_1_ /= (double)f2;
        p_70199_3_ /= (double)f2;
        p_70199_5_ /= (double)f2;
        p_70199_1_ += this.field_70146_Z.nextGaussian() * 0.007499999832361937D * (double)p_70199_8_;
        p_70199_3_ += this.field_70146_Z.nextGaussian() * 0.007499999832361937D * (double)p_70199_8_;
        p_70199_5_ += this.field_70146_Z.nextGaussian() * 0.007499999832361937D * (double)p_70199_8_;
        p_70199_1_ *= (double)p_70199_7_;
        p_70199_3_ *= (double)p_70199_7_;
        p_70199_5_ *= (double)p_70199_7_;
        this.field_70159_w = p_70199_1_;
        this.field_70181_x = p_70199_3_;
        this.field_70179_y = p_70199_5_;
        float f3 = MathHelper.func_76133_a(p_70199_1_ * p_70199_1_ + p_70199_5_ * p_70199_5_);
        this.field_70126_B = this.field_70177_z = (float)(Math.atan2(p_70199_1_, p_70199_5_) * 180.0D / Math.PI);
        this.field_70127_C = this.field_70125_A = (float)(Math.atan2(p_70199_3_, (double)f3) * 180.0D / Math.PI);
        this.field_70216_i = 0;
    }

    public void func_70071_h_()
    {
        super.func_70071_h_();

        if (this.field_70217_ao > 0)
        {
            double d0 = this.field_70165_t + (this.field_70218_ap - this.field_70165_t) / (double)this.field_70217_ao;
            double d1 = this.field_70163_u + (this.field_70210_aq - this.field_70163_u) / (double)this.field_70217_ao;
            double d2 = this.field_70161_v + (this.field_70209_ar - this.field_70161_v) / (double)this.field_70217_ao;
            double d3 = MathHelper.func_76138_g(this.field_70208_as - (double)this.field_70177_z);
            this.field_70177_z = (float)((double)this.field_70177_z + d3 / (double)this.field_70217_ao);
            this.field_70125_A = (float)((double)this.field_70125_A + (this.field_70207_at - (double)this.field_70125_A) / (double)this.field_70217_ao);
            --this.field_70217_ao;
            this.func_70107_b(d0, d1, d2);
            this.func_70101_b(this.field_70177_z, this.field_70125_A);
        }
        else
        {
            if (!this.field_70170_p.field_72995_K)
            {
                ItemStack itemstack = this.field_70204_b.func_71045_bC();

                if (this.field_70204_b.field_70128_L || !this.field_70204_b.func_70089_S() || itemstack == null || itemstack.func_77973_b() != Item.field_77749_aR || this.func_70068_e(this.field_70204_b) > 1024.0D)
                {
                    this.func_70106_y();
                    this.field_70204_b.field_71104_cf = null;
                    return;
                }

                if (this.field_70205_c != null)
                {
                    if (!this.field_70205_c.field_70128_L)
                    {
                        this.field_70165_t = this.field_70205_c.field_70165_t;
                        this.field_70163_u = this.field_70205_c.field_70121_D.field_72338_b + (double)this.field_70205_c.field_70131_O * 0.8D;
                        this.field_70161_v = this.field_70205_c.field_70161_v;
                        return;
                    }

                    this.field_70205_c = null;
                }
            }

            if (this.field_70206_a > 0)
            {
                --this.field_70206_a;
            }

            if (this.field_70214_h)
            {
                int i = this.field_70170_p.func_72798_a(this.field_70202_d, this.field_70203_e, this.field_70200_f);

                if (i == this.field_70201_g)
                {
                    ++this.field_70216_i;

                    if (this.field_70216_i == 1200)
                    {
                        this.func_70106_y();
                    }

                    return;
                }

                this.field_70214_h = false;
                this.field_70159_w *= (double)(this.field_70146_Z.nextFloat() * 0.2F);
                this.field_70181_x *= (double)(this.field_70146_Z.nextFloat() * 0.2F);
                this.field_70179_y *= (double)(this.field_70146_Z.nextFloat() * 0.2F);
                this.field_70216_i = 0;
                this.field_70211_j = 0;
            }
            else
            {
                ++this.field_70211_j;
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

            Entity entity = null;
            List list = this.field_70170_p.func_72839_b(this, this.field_70121_D.func_72321_a(this.field_70159_w, this.field_70181_x, this.field_70179_y).func_72314_b(1.0D, 1.0D, 1.0D));
            double d4 = 0.0D;
            double d5;

            for (int j = 0; j < list.size(); ++j)
            {
                Entity entity1 = (Entity)list.get(j);

                if (entity1.func_70067_L() && (entity1 != this.field_70204_b || this.field_70211_j >= 5))
                {
                    float f = 0.3F;
                    AxisAlignedBB axisalignedbb = entity1.field_70121_D.func_72314_b((double)f, (double)f, (double)f);
                    MovingObjectPosition movingobjectposition1 = axisalignedbb.func_72327_a(vec3, vec31);

                    if (movingobjectposition1 != null)
                    {
                        d5 = vec3.func_72436_e(movingobjectposition1.field_72307_f); // CraftBukkit - distance efficiency

                        if (d5 < d4 || d4 == 0.0D)
                        {
                            entity = entity1;
                            d4 = d5;
                        }
                    }
                }
            }

            if (entity != null)
            {
                movingobjectposition = new MovingObjectPosition(entity);
            }

            if (movingobjectposition != null)
            {
                if (movingobjectposition.field_72308_g != null)
                {
                    if (movingobjectposition.field_72308_g.func_70097_a(DamageSource.func_76356_a(this, this.field_70204_b), 0))
                    {
                        this.field_70205_c = movingobjectposition.field_72308_g;
                    }
                }
                else
                {
                    this.field_70214_h = true;
                }
            }

            if (!this.field_70214_h)
            {
                this.func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
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
                float f2 = 0.92F;

                if (this.field_70122_E || this.field_70123_F)
                {
                    f2 = 0.5F;
                }

                byte b0 = 5;
                double d6 = 0.0D;

                for (int k = 0; k < b0; ++k)
                {
                    double d7 = this.field_70121_D.field_72338_b + (this.field_70121_D.field_72337_e - this.field_70121_D.field_72338_b) * (double)(k + 0) / (double)b0 - 0.125D + 0.125D;
                    double d8 = this.field_70121_D.field_72338_b + (this.field_70121_D.field_72337_e - this.field_70121_D.field_72338_b) * (double)(k + 1) / (double)b0 - 0.125D + 0.125D;
                    AxisAlignedBB axisalignedbb1 = AxisAlignedBB.func_72332_a().func_72299_a(this.field_70121_D.field_72340_a, d7, this.field_70121_D.field_72339_c, this.field_70121_D.field_72336_d, d8, this.field_70121_D.field_72334_f);

                    if (this.field_70170_p.func_72830_b(axisalignedbb1, Material.field_76244_g))
                    {
                        d6 += 1.0D / (double)b0;
                    }
                }

                if (d6 > 0.0D)
                {
                    if (this.field_70219_an > 0)
                    {
                        --this.field_70219_an;
                    }
                    else
                    {
                        short short1 = 500;

                        if (this.field_70170_p.func_72951_B(MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c(this.field_70163_u) + 1, MathHelper.func_76128_c(this.field_70161_v)))
                        {
                            short1 = 300;
                        }

                        if (this.field_70146_Z.nextInt(short1) == 0)
                        {
                            this.field_70219_an = this.field_70146_Z.nextInt(30) + 10;
                            this.field_70181_x -= 0.20000000298023224D;
                            this.func_85030_a("random.splash", 0.25F, 1.0F + (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.4F);
                            float f3 = (float)MathHelper.func_76128_c(this.field_70121_D.field_72338_b);
                            float f4;
                            int l;
                            float f5;

                            for (l = 0; (float)l < 1.0F + this.field_70130_N * 20.0F; ++l)
                            {
                                f5 = (this.field_70146_Z.nextFloat() * 2.0F - 1.0F) * this.field_70130_N;
                                f4 = (this.field_70146_Z.nextFloat() * 2.0F - 1.0F) * this.field_70130_N;
                                this.field_70170_p.func_72869_a("bubble", this.field_70165_t + (double)f5, (double)(f3 + 1.0F), this.field_70161_v + (double)f4, this.field_70159_w, this.field_70181_x - (double)(this.field_70146_Z.nextFloat() * 0.2F), this.field_70179_y);
                            }

                            for (l = 0; (float)l < 1.0F + this.field_70130_N * 20.0F; ++l)
                            {
                                f5 = (this.field_70146_Z.nextFloat() * 2.0F - 1.0F) * this.field_70130_N;
                                f4 = (this.field_70146_Z.nextFloat() * 2.0F - 1.0F) * this.field_70130_N;
                                this.field_70170_p.func_72869_a("splash", this.field_70165_t + (double)f5, (double)(f3 + 1.0F), this.field_70161_v + (double)f4, this.field_70159_w, this.field_70181_x, this.field_70179_y);
                            }
                        }
                    }
                }

                if (this.field_70219_an > 0)
                {
                    this.field_70181_x -= (double)(this.field_70146_Z.nextFloat() * this.field_70146_Z.nextFloat() * this.field_70146_Z.nextFloat()) * 0.2D;
                }

                d5 = d6 * 2.0D - 1.0D;
                this.field_70181_x += 0.03999999910593033D * d5;

                if (d6 > 0.0D)
                {
                    f2 = (float)((double)f2 * 0.9D);
                    this.field_70181_x *= 0.8D;
                }

                this.field_70159_w *= (double)f2;
                this.field_70181_x *= (double)f2;
                this.field_70179_y *= (double)f2;
                this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
            }
        }
    }

    public void func_70014_b(NBTTagCompound p_70014_1_)
    {
        p_70014_1_.func_74777_a("xTile", (short)this.field_70202_d);
        p_70014_1_.func_74777_a("yTile", (short)this.field_70203_e);
        p_70014_1_.func_74777_a("zTile", (short)this.field_70200_f);
        p_70014_1_.func_74774_a("inTile", (byte)this.field_70201_g);
        p_70014_1_.func_74774_a("shake", (byte)this.field_70206_a);
        p_70014_1_.func_74774_a("inGround", (byte)(this.field_70214_h ? 1 : 0));
    }

    public void func_70037_a(NBTTagCompound p_70037_1_)
    {
        this.field_70202_d = p_70037_1_.func_74765_d("xTile");
        this.field_70203_e = p_70037_1_.func_74765_d("yTile");
        this.field_70200_f = p_70037_1_.func_74765_d("zTile");
        this.field_70201_g = p_70037_1_.func_74771_c("inTile") & 255;
        this.field_70206_a = p_70037_1_.func_74771_c("shake") & 255;
        this.field_70214_h = p_70037_1_.func_74771_c("inGround") == 1;
    }

    public int func_70198_d()
    {
        if (this.field_70170_p.field_72995_K)
        {
            return 0;
        }
        else
        {
            byte b0 = 0;

            if (this.field_70205_c != null)
            {
                // CraftBukkit start
                PlayerFishEvent playerFishEvent = new PlayerFishEvent((Player) this.field_70204_b.getBukkitEntity(), this.field_70205_c.getBukkitEntity(), PlayerFishEvent.State.CAUGHT_ENTITY);
                this.field_70170_p.getServer().getPluginManager().callEvent(playerFishEvent);

                if (playerFishEvent.isCancelled())
                {
                    this.func_70106_y();
                    this.field_70204_b.field_71104_cf = null;
                    return 0;
                }

                // CraftBukkit end
                double d0 = this.field_70204_b.field_70165_t - this.field_70165_t;
                double d1 = this.field_70204_b.field_70163_u - this.field_70163_u;
                double d2 = this.field_70204_b.field_70161_v - this.field_70161_v;
                double d3 = (double)MathHelper.func_76133_a(d0 * d0 + d1 * d1 + d2 * d2);
                double d4 = 0.1D;
                this.field_70205_c.field_70159_w += d0 * d4;
                this.field_70205_c.field_70181_x += d1 * d4 + (double)MathHelper.func_76133_a(d3) * 0.08D;
                this.field_70205_c.field_70179_y += d2 * d4;
                b0 = 3;
            }
            else if (this.field_70219_an > 0)
            {
                EntityItem entityitem = new EntityItem(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, new ItemStack(Item.field_77754_aU));
                // CraftBukkit start
                PlayerFishEvent playerFishEvent = new PlayerFishEvent((Player) this.field_70204_b.getBukkitEntity(), entityitem.getBukkitEntity(), PlayerFishEvent.State.CAUGHT_FISH);
                playerFishEvent.setExpToDrop(this.field_70146_Z.nextInt(6) + 1);
                this.field_70170_p.getServer().getPluginManager().callEvent(playerFishEvent);

                if (playerFishEvent.isCancelled())
                {
                    this.func_70106_y();
                    this.field_70204_b.field_71104_cf = null;
                    return 0;
                }

                // CraftBukkit end
                double d5 = this.field_70204_b.field_70165_t - this.field_70165_t;
                double d6 = this.field_70204_b.field_70163_u - this.field_70163_u;
                double d7 = this.field_70204_b.field_70161_v - this.field_70161_v;
                double d8 = (double)MathHelper.func_76133_a(d5 * d5 + d6 * d6 + d7 * d7);
                double d9 = 0.1D;
                entityitem.field_70159_w = d5 * d9;
                entityitem.field_70181_x = d6 * d9 + (double)MathHelper.func_76133_a(d8) * 0.08D;
                entityitem.field_70179_y = d7 * d9;
                this.field_70170_p.func_72838_d(entityitem);
                this.field_70204_b.func_71064_a(StatList.field_75933_B, 1);
                // CraftBukkit - this.random.nextInt(6) + 1 -> playerFishEvent.getExpToDrop()
                this.field_70204_b.field_70170_p.func_72838_d(new EntityXPOrb(this.field_70204_b.field_70170_p, this.field_70204_b.field_70165_t, this.field_70204_b.field_70163_u + 0.5D, this.field_70204_b.field_70161_v + 0.5D, playerFishEvent.getExpToDrop()));
                b0 = 1;
            }

            if (this.field_70214_h)
            {
                // CraftBukkit start
                PlayerFishEvent playerFishEvent = new PlayerFishEvent((Player) this.field_70204_b.getBukkitEntity(), null, PlayerFishEvent.State.IN_GROUND);
                this.field_70170_p.getServer().getPluginManager().callEvent(playerFishEvent);

                if (playerFishEvent.isCancelled())
                {
                    this.func_70106_y();
                    this.field_70204_b.field_71104_cf = null;
                    return 0;
                }

                // CraftBukkit end
                b0 = 2;
            }

            // CraftBukkit start
            if (b0 == 0)
            {
                PlayerFishEvent playerFishEvent = new PlayerFishEvent((Player) this.field_70204_b.getBukkitEntity(), null, PlayerFishEvent.State.FAILED_ATTEMPT);
                this.field_70170_p.getServer().getPluginManager().callEvent(playerFishEvent);
            }

            // CraftBukkit end
            this.func_70106_y();
            this.field_70204_b.field_71104_cf = null;
            return b0;
        }
    }

    public void func_70106_y()
    {
        super.func_70106_y();

        if (this.field_70204_b != null)
        {
            this.field_70204_b.field_71104_cf = null;
        }
    }
}
