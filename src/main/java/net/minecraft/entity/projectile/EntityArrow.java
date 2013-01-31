package net.minecraft.entity.projectile;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentThorns;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet70GameEvent;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

// CraftBukkit start
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
// CraftBukkit end

public class EntityArrow extends Entity implements IProjectile
{
    private int field_70247_d = -1;
    private int field_70248_e = -1;
    private int field_70245_f = -1;
    private int field_70246_g = 0;
    private int field_70253_h = 0;
    private boolean field_70254_i = false;
    public int field_70251_a = 0;
    public int field_70249_b = 0;
    public Entity field_70250_c;
    private int field_70252_j;
    private int field_70257_an = 0;
    private double field_70255_ao = 2.0D;
    private int field_70256_ap;

    public EntityArrow(World p_i3565_1_)
    {
        super(p_i3565_1_);
        this.field_70155_l = 10.0D;
        this.func_70105_a(0.5F, 0.5F);
    }

    public EntityArrow(World p_i3566_1_, double p_i3566_2_, double p_i3566_4_, double p_i3566_6_)
    {
        super(p_i3566_1_);
        this.field_70155_l = 10.0D;
        this.func_70105_a(0.5F, 0.5F);
        this.func_70107_b(p_i3566_2_, p_i3566_4_, p_i3566_6_);
        this.field_70129_M = 0.0F;
    }

    public EntityArrow(World p_i3567_1_, EntityLiving p_i3567_2_, EntityLiving p_i3567_3_, float p_i3567_4_, float p_i3567_5_)
    {
        super(p_i3567_1_);
        this.field_70155_l = 10.0D;
        this.field_70250_c = p_i3567_2_;

        if (p_i3567_2_ instanceof EntityPlayer)
        {
            this.field_70251_a = 1;
        }

        this.field_70163_u = p_i3567_2_.field_70163_u + (double)p_i3567_2_.func_70047_e() - 0.10000000149011612D;
        double d0 = p_i3567_3_.field_70165_t - p_i3567_2_.field_70165_t;
        double d1 = p_i3567_3_.field_70163_u + (double)p_i3567_3_.func_70047_e() - 0.699999988079071D - this.field_70163_u;
        double d2 = p_i3567_3_.field_70161_v - p_i3567_2_.field_70161_v;
        double d3 = (double)MathHelper.func_76133_a(d0 * d0 + d2 * d2);

        if (d3 >= 1.0E-7D)
        {
            float f2 = (float)(Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
            float f3 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
            double d4 = d0 / d3;
            double d5 = d2 / d3;
            this.func_70012_b(p_i3567_2_.field_70165_t + d4, this.field_70163_u, p_i3567_2_.field_70161_v + d5, f2, f3);
            this.field_70129_M = 0.0F;
            float f4 = (float)d3 * 0.2F;
            this.func_70186_c(d0, d1 + (double)f4, d2, p_i3567_4_, p_i3567_5_);
        }
    }

    public EntityArrow(World p_i3568_1_, EntityLiving p_i3568_2_, float p_i3568_3_)
    {
        super(p_i3568_1_);
        this.field_70155_l = 10.0D;
        this.field_70250_c = p_i3568_2_;

        if (p_i3568_2_ instanceof EntityPlayer)
        {
            this.field_70251_a = 1;
        }

        this.func_70105_a(0.5F, 0.5F);
        this.func_70012_b(p_i3568_2_.field_70165_t, p_i3568_2_.field_70163_u + (double)p_i3568_2_.func_70047_e(), p_i3568_2_.field_70161_v, p_i3568_2_.field_70177_z, p_i3568_2_.field_70125_A);
        this.field_70165_t -= (double)(MathHelper.func_76134_b(this.field_70177_z / 180.0F * (float)Math.PI) * 0.16F);
        this.field_70163_u -= 0.10000000149011612D;
        this.field_70161_v -= (double)(MathHelper.func_76126_a(this.field_70177_z / 180.0F * (float)Math.PI) * 0.16F);
        this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
        this.field_70129_M = 0.0F;
        this.field_70159_w = (double)(-MathHelper.func_76126_a(this.field_70177_z / 180.0F * (float)Math.PI) * MathHelper.func_76134_b(this.field_70125_A / 180.0F * (float)Math.PI));
        this.field_70179_y = (double)(MathHelper.func_76134_b(this.field_70177_z / 180.0F * (float)Math.PI) * MathHelper.func_76134_b(this.field_70125_A / 180.0F * (float)Math.PI));
        this.field_70181_x = (double)(-MathHelper.func_76126_a(this.field_70125_A / 180.0F * (float)Math.PI));
        this.func_70186_c(this.field_70159_w, this.field_70181_x, this.field_70179_y, p_i3568_3_ * 1.5F, 1.0F);
    }

    protected void func_70088_a()
    {
        this.field_70180_af.func_75682_a(16, Byte.valueOf((byte)0));
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
        this.field_70252_j = 0;
    }

    public void func_70071_h_()
    {
        super.func_70071_h_();

        if (this.field_70127_C == 0.0F && this.field_70126_B == 0.0F)
        {
            float f = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
            this.field_70126_B = this.field_70177_z = (float)(Math.atan2(this.field_70159_w, this.field_70179_y) * 180.0D / Math.PI);
            this.field_70127_C = this.field_70125_A = (float)(Math.atan2(this.field_70181_x, (double)f) * 180.0D / Math.PI);
        }

        int i = this.field_70170_p.func_72798_a(this.field_70247_d, this.field_70248_e, this.field_70245_f);

        if (i > 0)
        {
            Block.field_71973_m[i].func_71902_a(this.field_70170_p, this.field_70247_d, this.field_70248_e, this.field_70245_f);
            AxisAlignedBB axisalignedbb = Block.field_71973_m[i].func_71872_e(this.field_70170_p, this.field_70247_d, this.field_70248_e, this.field_70245_f);

            if (axisalignedbb != null && axisalignedbb.func_72318_a(this.field_70170_p.func_82732_R().func_72345_a(this.field_70165_t, this.field_70163_u, this.field_70161_v)))
            {
                this.field_70254_i = true;
            }
        }

        if (this.field_70249_b > 0)
        {
            --this.field_70249_b;
        }

        if (this.field_70254_i)
        {
            int j = this.field_70170_p.func_72798_a(this.field_70247_d, this.field_70248_e, this.field_70245_f);
            int k = this.field_70170_p.func_72805_g(this.field_70247_d, this.field_70248_e, this.field_70245_f);

            if (j == this.field_70246_g && k == this.field_70253_h)
            {
                ++this.field_70252_j;

                if (this.field_70252_j == 1200)
                {
                    this.func_70106_y();
                }
            }
            else
            {
                this.field_70254_i = false;
                this.field_70159_w *= (double)(this.field_70146_Z.nextFloat() * 0.2F);
                this.field_70181_x *= (double)(this.field_70146_Z.nextFloat() * 0.2F);
                this.field_70179_y *= (double)(this.field_70146_Z.nextFloat() * 0.2F);
                this.field_70252_j = 0;
                this.field_70257_an = 0;
            }
        }
        else
        {
            ++this.field_70257_an;
            Vec3 vec3 = this.field_70170_p.func_82732_R().func_72345_a(this.field_70165_t, this.field_70163_u, this.field_70161_v);
            Vec3 vec31 = this.field_70170_p.func_82732_R().func_72345_a(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
            MovingObjectPosition movingobjectposition = this.field_70170_p.func_72831_a(vec3, vec31, false, true);
            vec3 = this.field_70170_p.func_82732_R().func_72345_a(this.field_70165_t, this.field_70163_u, this.field_70161_v);
            vec31 = this.field_70170_p.func_82732_R().func_72345_a(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);

            if (movingobjectposition != null)
            {
                vec31 = this.field_70170_p.func_82732_R().func_72345_a(movingobjectposition.field_72307_f.field_72450_a, movingobjectposition.field_72307_f.field_72448_b, movingobjectposition.field_72307_f.field_72449_c);
            }

            Entity entity = null;
            List list = this.field_70170_p.func_72839_b(this, this.field_70121_D.func_72321_a(this.field_70159_w, this.field_70181_x, this.field_70179_y).func_72314_b(1.0D, 1.0D, 1.0D));
            double d0 = 0.0D;
            int l;
            float f1;

            for (l = 0; l < list.size(); ++l)
            {
                Entity entity1 = (Entity)list.get(l);

                if (entity1.func_70067_L() && (entity1 != this.field_70250_c || this.field_70257_an >= 5))
                {
                    f1 = 0.3F;
                    AxisAlignedBB axisalignedbb1 = entity1.field_70121_D.func_72314_b((double)f1, (double)f1, (double)f1);
                    MovingObjectPosition movingobjectposition1 = axisalignedbb1.func_72327_a(vec3, vec31);

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

            float f2;
            float f3;

            if (movingobjectposition != null)
            {
                // CraftBukkit start
                Projectile projectile = (Projectile) this.getBukkitEntity();
                ProjectileHitEvent phe = new ProjectileHitEvent(projectile);
                this.field_70170_p.getServer().getPluginManager().callEvent(phe);

                // CraftBukkit end
                if (movingobjectposition.field_72308_g != null)
                {
                    f2 = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70181_x * this.field_70181_x + this.field_70179_y * this.field_70179_y);
                    int i1 = MathHelper.func_76143_f((double)f2 * this.field_70255_ao);

                    if (this.func_70241_g())
                    {
                        i1 += this.field_70146_Z.nextInt(i1 / 2 + 2);
                    }

                    DamageSource damagesource = null;

                    if (this.field_70250_c == null)
                    {
                        damagesource = DamageSource.func_76353_a(this, this);
                    }
                    else
                    {
                        damagesource = DamageSource.func_76353_a(this, this.field_70250_c);
                    }

                    // CraftBukkit start - moved damage call
                    if (movingobjectposition.field_72308_g.func_70097_a(damagesource, i1))
                    {
                        if (this.func_70027_ad() && !(movingobjectposition.field_72308_g instanceof EntityEnderman) && (!(movingobjectposition.field_72308_g instanceof EntityPlayerMP) || !(this.field_70250_c instanceof EntityPlayerMP) || this.field_70170_p.pvpMode))   // CraftBukkit - abide by pvp setting if destination is a player.
                        {
                            EntityCombustByEntityEvent combustEvent = new EntityCombustByEntityEvent(this.getBukkitEntity(), entity.getBukkitEntity(), 5);
                            org.bukkit.Bukkit.getPluginManager().callEvent(combustEvent);

                            if (!combustEvent.isCancelled())
                            {
                                movingobjectposition.field_72308_g.func_70015_d(combustEvent.getDuration());
                            }

                            // CraftBukkit end
                        }

                        // if (movingobjectposition.entity.damageEntity(damagesource, i1)) { // CraftBukkit - moved up
                        if (movingobjectposition.field_72308_g instanceof EntityLiving)
                        {
                            EntityLiving entityliving = (EntityLiving)movingobjectposition.field_72308_g;

                            if (!this.field_70170_p.field_72995_K)
                            {
                                entityliving.func_85034_r(entityliving.func_85035_bI() + 1);
                            }

                            if (this.field_70256_ap > 0)
                            {
                                f3 = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);

                                if (f3 > 0.0F)
                                {
                                    movingobjectposition.field_72308_g.func_70024_g(this.field_70159_w * (double)this.field_70256_ap * 0.6000000238418579D / (double)f3, 0.1D, this.field_70179_y * (double)this.field_70256_ap * 0.6000000238418579D / (double)f3);
                                }
                            }

                            if (this.field_70250_c != null)
                            {
                                EnchantmentThorns.func_92096_a(this.field_70250_c, entityliving, this.field_70146_Z);
                            }

                            if (this.field_70250_c != null && movingobjectposition.field_72308_g != this.field_70250_c && movingobjectposition.field_72308_g instanceof EntityPlayer && this.field_70250_c instanceof EntityPlayerMP)
                            {
                                ((EntityPlayerMP)this.field_70250_c).field_71135_a.func_72567_b(new Packet70GameEvent(6, 0));
                            }
                        }

                        this.func_85030_a("random.bowhit", 1.0F, 1.2F / (this.field_70146_Z.nextFloat() * 0.2F + 0.9F));

                        if (!(movingobjectposition.field_72308_g instanceof EntityEnderman))
                        {
                            this.func_70106_y();
                        }
                    }
                    else
                    {
                        this.field_70159_w *= -0.10000000149011612D;
                        this.field_70181_x *= -0.10000000149011612D;
                        this.field_70179_y *= -0.10000000149011612D;
                        this.field_70177_z += 180.0F;
                        this.field_70126_B += 180.0F;
                        this.field_70257_an = 0;
                    }
                }
                else
                {
                    this.field_70247_d = movingobjectposition.field_72311_b;
                    this.field_70248_e = movingobjectposition.field_72312_c;
                    this.field_70245_f = movingobjectposition.field_72309_d;
                    this.field_70246_g = this.field_70170_p.func_72798_a(this.field_70247_d, this.field_70248_e, this.field_70245_f);
                    this.field_70253_h = this.field_70170_p.func_72805_g(this.field_70247_d, this.field_70248_e, this.field_70245_f);
                    this.field_70159_w = (double)((float)(movingobjectposition.field_72307_f.field_72450_a - this.field_70165_t));
                    this.field_70181_x = (double)((float)(movingobjectposition.field_72307_f.field_72448_b - this.field_70163_u));
                    this.field_70179_y = (double)((float)(movingobjectposition.field_72307_f.field_72449_c - this.field_70161_v));
                    f2 = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70181_x * this.field_70181_x + this.field_70179_y * this.field_70179_y);
                    this.field_70165_t -= this.field_70159_w / (double)f2 * 0.05000000074505806D;
                    this.field_70163_u -= this.field_70181_x / (double)f2 * 0.05000000074505806D;
                    this.field_70161_v -= this.field_70179_y / (double)f2 * 0.05000000074505806D;
                    this.func_85030_a("random.bowhit", 1.0F, 1.2F / (this.field_70146_Z.nextFloat() * 0.2F + 0.9F));
                    this.field_70254_i = true;
                    this.field_70249_b = 7;
                    this.func_70243_d(false);

                    if (this.field_70246_g != 0)
                    {
                        Block.field_71973_m[this.field_70246_g].func_71869_a(this.field_70170_p, this.field_70247_d, this.field_70248_e, this.field_70245_f, this);
                    }
                }
            }

            if (this.func_70241_g())
            {
                for (l = 0; l < 4; ++l)
                {
                    this.field_70170_p.func_72869_a("crit", this.field_70165_t + this.field_70159_w * (double)l / 4.0D, this.field_70163_u + this.field_70181_x * (double)l / 4.0D, this.field_70161_v + this.field_70179_y * (double)l / 4.0D, -this.field_70159_w, -this.field_70181_x + 0.2D, -this.field_70179_y);
                }
            }

            this.field_70165_t += this.field_70159_w;
            this.field_70163_u += this.field_70181_x;
            this.field_70161_v += this.field_70179_y;
            f2 = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
            this.field_70177_z = (float)(Math.atan2(this.field_70159_w, this.field_70179_y) * 180.0D / Math.PI);

            for (this.field_70125_A = (float)(Math.atan2(this.field_70181_x, (double)f2) * 180.0D / Math.PI); this.field_70125_A - this.field_70127_C < -180.0F; this.field_70127_C -= 360.0F)
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
            float f4 = 0.99F;
            f1 = 0.05F;

            if (this.func_70090_H())
            {
                for (int j1 = 0; j1 < 4; ++j1)
                {
                    f3 = 0.25F;
                    this.field_70170_p.func_72869_a("bubble", this.field_70165_t - this.field_70159_w * (double)f3, this.field_70163_u - this.field_70181_x * (double)f3, this.field_70161_v - this.field_70179_y * (double)f3, this.field_70159_w, this.field_70181_x, this.field_70179_y);
                }

                f4 = 0.8F;
            }

            this.field_70159_w *= (double)f4;
            this.field_70181_x *= (double)f4;
            this.field_70179_y *= (double)f4;
            this.field_70181_x -= (double)f1;
            this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
            this.func_70017_D();
        }
    }

    public void func_70014_b(NBTTagCompound p_70014_1_)
    {
        p_70014_1_.func_74777_a("xTile", (short)this.field_70247_d);
        p_70014_1_.func_74777_a("yTile", (short)this.field_70248_e);
        p_70014_1_.func_74777_a("zTile", (short)this.field_70245_f);
        p_70014_1_.func_74774_a("inTile", (byte)this.field_70246_g);
        p_70014_1_.func_74774_a("inData", (byte)this.field_70253_h);
        p_70014_1_.func_74774_a("shake", (byte)this.field_70249_b);
        p_70014_1_.func_74774_a("inGround", (byte)(this.field_70254_i ? 1 : 0));
        p_70014_1_.func_74774_a("pickup", (byte)this.field_70251_a);
        p_70014_1_.func_74780_a("damage", this.field_70255_ao);
    }

    public void func_70037_a(NBTTagCompound p_70037_1_)
    {
        this.field_70247_d = p_70037_1_.func_74765_d("xTile");
        this.field_70248_e = p_70037_1_.func_74765_d("yTile");
        this.field_70245_f = p_70037_1_.func_74765_d("zTile");
        this.field_70246_g = p_70037_1_.func_74771_c("inTile") & 255;
        this.field_70253_h = p_70037_1_.func_74771_c("inData") & 255;
        this.field_70249_b = p_70037_1_.func_74771_c("shake") & 255;
        this.field_70254_i = p_70037_1_.func_74771_c("inGround") == 1;

        if (p_70037_1_.func_74764_b("damage"))
        {
            this.field_70255_ao = p_70037_1_.func_74769_h("damage");
        }

        if (p_70037_1_.func_74764_b("pickup"))
        {
            this.field_70251_a = p_70037_1_.func_74771_c("pickup");
        }
        else if (p_70037_1_.func_74764_b("player"))
        {
            this.field_70251_a = p_70037_1_.func_74767_n("player") ? 1 : 0;
        }
    }

    public void func_70100_b_(EntityPlayer p_70100_1_)
    {
        if (!this.field_70170_p.field_72995_K && this.field_70254_i && this.field_70249_b <= 0)
        {
            // CraftBukkit start
            ItemStack itemstack = new ItemStack(Item.field_77704_l);

            if (this.field_70251_a == 1 && p_70100_1_.field_71071_by.canHold(itemstack) > 0)
            {
                EntityItem item = new EntityItem(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, itemstack);
                PlayerPickupItemEvent event = new PlayerPickupItemEvent((org.bukkit.entity.Player) p_70100_1_.getBukkitEntity(), new org.bukkit.craftbukkit.entity.CraftItem(this.field_70170_p.getServer(), this, item), 0);
                event.setCancelled(!p_70100_1_.field_82172_bs);
                this.field_70170_p.getServer().getPluginManager().callEvent(event);

                if (event.isCancelled())
                {
                    return;
                }
            }

            // CraftBukkit end
            boolean flag = this.field_70251_a == 1 || this.field_70251_a == 2 && p_70100_1_.field_71075_bZ.field_75098_d;

            if (this.field_70251_a == 1 && !p_70100_1_.field_71071_by.func_70441_a(new ItemStack(Item.field_77704_l, 1)))
            {
                flag = false;
            }

            if (flag)
            {
                this.func_85030_a("random.pop", 0.2F, ((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                p_70100_1_.func_71001_a(this, 1);
                this.func_70106_y();
            }
        }
    }

    protected boolean func_70041_e_()
    {
        return false;
    }

    public void func_70239_b(double p_70239_1_)
    {
        this.field_70255_ao = p_70239_1_;
    }

    public double func_70242_d()
    {
        return this.field_70255_ao;
    }

    public void func_70240_a(int p_70240_1_)
    {
        this.field_70256_ap = p_70240_1_;
    }

    public boolean func_70075_an()
    {
        return false;
    }

    public void func_70243_d(boolean p_70243_1_)
    {
        byte b0 = this.field_70180_af.func_75683_a(16);

        if (p_70243_1_)
        {
            this.field_70180_af.func_75692_b(16, Byte.valueOf((byte)(b0 | 1)));
        }
        else
        {
            this.field_70180_af.func_75692_b(16, Byte.valueOf((byte)(b0 & -2)));
        }
    }

    public boolean func_70241_g()
    {
        byte b0 = this.field_70180_af.func_75683_a(16);
        return (b0 & 1) != 0;
    }
}
