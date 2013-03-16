package net.minecraft.entity.boss;

import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEndPortal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet53BlockChange;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

// CraftBukkit start
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.util.BlockStateListPopulator;
import org.bukkit.event.entity.EntityCreatePortalEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.Bukkit;
// CraftBukkit end

public class EntityDragon extends EntityLiving implements IEntityMultiPart
{
    public double field_70980_b;
    public double field_70981_c;
    public double field_70978_d;
    public double[][] field_70979_e = new double[64][3];
    public int field_70976_f = -1;
    public EntityDragonPart[] field_70977_g;
    public EntityDragonPart field_70986_h;
    public EntityDragonPart field_70987_i;
    public EntityDragonPart field_70985_j;
    public EntityDragonPart field_70984_by;
    public EntityDragonPart field_70982_bz;
    public EntityDragonPart field_70983_bA;
    public EntityDragonPart field_70990_bB;
    public float field_70991_bC = 0.0F;
    public float field_70988_bD = 0.0F;
    public boolean field_70989_bE = false;
    public boolean field_70994_bF = false;
    private Entity field_70993_bI;
    public int field_70995_bG = 0;
    public EntityEnderCrystal field_70992_bH = null;

    public EntityDragon(World p_i3531_1_)
    {
        super(p_i3531_1_);
        this.field_70977_g = new EntityDragonPart[] {this.field_70986_h = new EntityDragonPart(this, "head", 6.0F, 6.0F), this.field_70987_i = new EntityDragonPart(this, "body", 8.0F, 8.0F), this.field_70985_j = new EntityDragonPart(this, "tail", 4.0F, 4.0F), this.field_70984_by = new EntityDragonPart(this, "tail", 4.0F, 4.0F), this.field_70982_bz = new EntityDragonPart(this, "tail", 4.0F, 4.0F), this.field_70983_bA = new EntityDragonPart(this, "wing", 4.0F, 4.0F), this.field_70990_bB = new EntityDragonPart(this, "wing", 4.0F, 4.0F)};
        this.func_70606_j(this.func_70667_aM());
        this.field_70750_az = "/mob/enderdragon/ender.png";
        this.func_70105_a(16.0F, 8.0F);
        this.field_70145_X = true;
        this.field_70178_ae = true;
        this.field_70981_c = 100.0D;
        this.field_70158_ak = true;
    }

    public int func_70667_aM()
    {
        return 200;
    }

    protected void func_70088_a()
    {
        super.func_70088_a();
        this.field_70180_af.func_75682_a(16, new Integer(this.func_70667_aM()));
    }

    public double[] func_70974_a(int p_70974_1_, float p_70974_2_)
    {
        if (this.field_70734_aK <= 0)
        {
            p_70974_2_ = 0.0F;
        }

        p_70974_2_ = 1.0F - p_70974_2_;
        int j = this.field_70976_f - p_70974_1_ * 1 & 63;
        int k = this.field_70976_f - p_70974_1_ * 1 - 1 & 63;
        double[] adouble = new double[3];
        double d0 = this.field_70979_e[j][0];
        double d1 = MathHelper.func_76138_g(this.field_70979_e[k][0] - d0);
        adouble[0] = d0 + d1 * (double)p_70974_2_;
        d0 = this.field_70979_e[j][1];
        d1 = this.field_70979_e[k][1] - d0;
        adouble[1] = d0 + d1 * (double)p_70974_2_;
        adouble[2] = this.field_70979_e[j][2] + (this.field_70979_e[k][2] - this.field_70979_e[j][2]) * (double)p_70974_2_;
        return adouble;
    }

    public void func_70636_d()
    {
        float f;
        float f1;

        if (!this.field_70170_p.field_72995_K)
        {
            this.field_70180_af.func_75692_b(16, Integer.valueOf(this.getScaledHealth())); // CraftBukkit - this.health -> this.getScaledHealth()
        }
        else
        {
            f = MathHelper.func_76134_b(this.field_70988_bD * (float)Math.PI * 2.0F);
            f1 = MathHelper.func_76134_b(this.field_70991_bC * (float)Math.PI * 2.0F);

            if (f1 <= -0.3F && f >= -0.3F)
            {
                this.field_70170_p.func_72980_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, "mob.enderdragon.wings", 5.0F, 0.8F + this.field_70146_Z.nextFloat() * 0.3F, false);
            }
        }

        this.field_70991_bC = this.field_70988_bD;
        float f2;

        if (this.field_70734_aK <= 0)
        {
            f = (this.field_70146_Z.nextFloat() - 0.5F) * 8.0F;
            f1 = (this.field_70146_Z.nextFloat() - 0.5F) * 4.0F;
            f2 = (this.field_70146_Z.nextFloat() - 0.5F) * 8.0F;
            this.field_70170_p.func_72869_a("largeexplode", this.field_70165_t + (double)f, this.field_70163_u + 2.0D + (double)f1, this.field_70161_v + (double)f2, 0.0D, 0.0D, 0.0D);
        }
        else
        {
            this.func_70969_j();
            f = 0.2F / (MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y) * 10.0F + 1.0F);
            f *= (float)Math.pow(2.0D, this.field_70181_x);

            if (this.field_70994_bF)
            {
                this.field_70988_bD += f * 0.5F;
            }
            else
            {
                this.field_70988_bD += f;
            }

            this.field_70177_z = MathHelper.func_76142_g(this.field_70177_z);

            if (this.field_70976_f < 0)
            {
                for (int d05 = 0; d05 < this.field_70979_e.length; ++d05)
                {
                    this.field_70979_e[d05][0] = (double) this.field_70177_z;
                    this.field_70979_e[d05][1] = this.field_70163_u;
                }
            }

            if (++this.field_70976_f == this.field_70979_e.length)
            {
                this.field_70976_f = 0;
            }

            this.field_70979_e[this.field_70976_f][0] = (double)this.field_70177_z;
            this.field_70979_e[this.field_70976_f][1] = this.field_70163_u;
            double d0;
            double d1;
            double d2;
            double d3;
            float f3;

            if (this.field_70170_p.field_72995_K)
            {
                if (this.field_70716_bi > 0)
                {
                    d0 = this.field_70165_t + (this.field_70709_bj - this.field_70165_t) / (double) this.field_70716_bi;
                    d1 = this.field_70163_u + (this.field_70710_bk - this.field_70163_u) / (double) this.field_70716_bi;
                    d2 = this.field_70161_v + (this.field_70711_bl - this.field_70161_v) / (double) this.field_70716_bi;
                    d3 = MathHelper.func_76138_g(this.field_70712_bm - (double) this.field_70177_z);
                    this.field_70177_z = (float)((double) this.field_70177_z + d3 / (double) this.field_70716_bi);
                    this.field_70125_A = (float)((double)this.field_70125_A + (this.field_70705_bn - (double)this.field_70125_A) / (double)this.field_70716_bi);
                    --this.field_70716_bi;
                    this.func_70107_b(d0, d1, d2);
                    this.func_70101_b(this.field_70177_z, this.field_70125_A);
                }
            }
            else
            {
                d0 = this.field_70980_b - this.field_70165_t;
                d1 = this.field_70981_c - this.field_70163_u;
                d2 = this.field_70978_d - this.field_70161_v;
                d3 = d0 * d0 + d1 * d1 + d2 * d2;

                if (this.field_70993_bI != null)
                {
                    this.field_70980_b = this.field_70993_bI.field_70165_t;
                    this.field_70978_d = this.field_70993_bI.field_70161_v;
                    double d4 = this.field_70980_b - this.field_70165_t;
                    double d5 = this.field_70978_d - this.field_70161_v;
                    double d6 = Math.sqrt(d4 * d4 + d5 * d5);
                    double d7 = 0.4000000059604645D + d6 / 80.0D - 1.0D;

                    if (d7 > 10.0D)
                    {
                        d7 = 10.0D;
                    }

                    this.field_70981_c = this.field_70993_bI.field_70121_D.field_72338_b + d7;
                }
                else
                {
                    this.field_70980_b += this.field_70146_Z.nextGaussian() * 2.0D;
                    this.field_70978_d += this.field_70146_Z.nextGaussian() * 2.0D;
                }

                if (this.field_70989_bE || d3 < 100.0D || d3 > 22500.0D || this.field_70123_F || this.field_70124_G)
                {
                    this.func_70967_k();
                }

                d1 /= (double) MathHelper.func_76133_a(d0 * d0 + d2 * d2);
                f3 = 0.6F;

                if (d1 < (double)(-f3))
                {
                    d1 = (double)(-f3);
                }

                if (d1 > (double) f3)
                {
                    d1 = (double) f3;
                }

                this.field_70181_x += d1 * 0.10000000149011612D;
                this.field_70177_z = MathHelper.func_76142_g(this.field_70177_z);
                double d8 = 180.0D - Math.atan2(d0, d2) * 180.0D / Math.PI;
                double d9 = MathHelper.func_76138_g(d8 - (double)this.field_70177_z);

                if (d9 > 50.0D)
                {
                    d9 = 50.0D;
                }

                if (d9 < -50.0D)
                {
                    d9 = -50.0D;
                }

                Vec3 vec3 = this.field_70170_p.func_82732_R().func_72345_a(this.field_70980_b - this.field_70165_t, this.field_70981_c - this.field_70163_u, this.field_70978_d - this.field_70161_v).func_72432_b();
                Vec3 vec31 = this.field_70170_p.func_82732_R().func_72345_a((double)MathHelper.func_76126_a(this.field_70177_z * (float)Math.PI / 180.0F), this.field_70181_x, (double)(-MathHelper.func_76134_b(this.field_70177_z * (float)Math.PI / 180.0F))).func_72432_b();
                float f4 = (float)(vec31.func_72430_b(vec3) + 0.5D) / 1.5F;

                if (f4 < 0.0F)
                {
                    f4 = 0.0F;
                }

                this.field_70704_bt *= 0.8F;
                float f5 = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y) * 1.0F + 1.0F;
                double d10 = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y) * 1.0D + 1.0D;

                if (d10 > 40.0D)
                {
                    d10 = 40.0D;
                }

                this.field_70704_bt = (float)((double)this.field_70704_bt + d9 * (0.699999988079071D / d10 / (double)f5));
                this.field_70177_z += this.field_70704_bt * 0.1F;
                float f6 = (float)(2.0D / (d10 + 1.0D));
                float f7 = 0.06F;
                this.func_70060_a(0.0F, -1.0F, f7 * (f4 * f6 + (1.0F - f6)));

                if (this.field_70994_bF)
                {
                    this.func_70091_d(this.field_70159_w * 0.800000011920929D, this.field_70181_x * 0.800000011920929D, this.field_70179_y * 0.800000011920929D);
                }
                else
                {
                    this.func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
                }

                Vec3 vec32 = this.field_70170_p.func_82732_R().func_72345_a(this.field_70159_w, this.field_70181_x, this.field_70179_y).func_72432_b();
                float f8 = (float)(vec32.func_72430_b(vec31) + 1.0D) / 2.0F;
                f8 = 0.8F + 0.15F * f8;
                this.field_70159_w *= (double)f8;
                this.field_70179_y *= (double)f8;
                this.field_70181_x *= 0.9100000262260437D;
            }

            this.field_70761_aq = this.field_70177_z;
            this.field_70986_h.field_70130_N = this.field_70986_h.field_70131_O = 3.0F;
            this.field_70985_j.field_70130_N = this.field_70985_j.field_70131_O = 2.0F;
            this.field_70984_by.field_70130_N = this.field_70984_by.field_70131_O = 2.0F;
            this.field_70982_bz.field_70130_N = this.field_70982_bz.field_70131_O = 2.0F;
            this.field_70987_i.field_70131_O = 3.0F;
            this.field_70987_i.field_70130_N = 5.0F;
            this.field_70983_bA.field_70131_O = 2.0F;
            this.field_70983_bA.field_70130_N = 4.0F;
            this.field_70990_bB.field_70131_O = 3.0F;
            this.field_70990_bB.field_70130_N = 4.0F;
            f1 = (float)(this.func_70974_a(5, 1.0F)[1] - this.func_70974_a(10, 1.0F)[1]) * 10.0F / 180.0F * (float)Math.PI;
            f2 = MathHelper.func_76134_b(f1);
            float f9 = -MathHelper.func_76126_a(f1);
            float f10 = this.field_70177_z * (float)Math.PI / 180.0F;
            float f11 = MathHelper.func_76126_a(f10);
            float f12 = MathHelper.func_76134_b(f10);
            this.field_70987_i.func_70071_h_();
            this.field_70987_i.func_70012_b(this.field_70165_t + (double)(f11 * 0.5F), this.field_70163_u, this.field_70161_v - (double)(f12 * 0.5F), 0.0F, 0.0F);
            this.field_70983_bA.func_70071_h_();
            this.field_70983_bA.func_70012_b(this.field_70165_t + (double)(f12 * 4.5F), this.field_70163_u + 2.0D, this.field_70161_v + (double)(f11 * 4.5F), 0.0F, 0.0F);
            this.field_70990_bB.func_70071_h_();
            this.field_70990_bB.func_70012_b(this.field_70165_t - (double)(f12 * 4.5F), this.field_70163_u + 2.0D, this.field_70161_v - (double)(f11 * 4.5F), 0.0F, 0.0F);

            if (!this.field_70170_p.field_72995_K && this.field_70737_aN == 0)
            {
                this.func_70970_a(this.field_70170_p.func_72839_b(this, this.field_70983_bA.field_70121_D.func_72314_b(4.0D, 2.0D, 4.0D).func_72317_d(0.0D, -2.0D, 0.0D)));
                this.func_70970_a(this.field_70170_p.func_72839_b(this, this.field_70990_bB.field_70121_D.func_72314_b(4.0D, 2.0D, 4.0D).func_72317_d(0.0D, -2.0D, 0.0D)));
                this.func_70971_b(this.field_70170_p.func_72839_b(this, this.field_70986_h.field_70121_D.func_72314_b(1.0D, 1.0D, 1.0D)));
            }

            double[] adouble = this.func_70974_a(5, 1.0F);
            double[] adouble1 = this.func_70974_a(0, 1.0F);
            f3 = MathHelper.func_76126_a(this.field_70177_z * (float)Math.PI / 180.0F - this.field_70704_bt * 0.01F);
            float f13 = MathHelper.func_76134_b(this.field_70177_z * (float)Math.PI / 180.0F - this.field_70704_bt * 0.01F);
            this.field_70986_h.func_70071_h_();
            this.field_70986_h.func_70012_b(this.field_70165_t + (double)(f3 * 5.5F * f2), this.field_70163_u + (adouble1[1] - adouble[1]) * 1.0D + (double)(f9 * 5.5F), this.field_70161_v - (double)(f13 * 5.5F * f2), 0.0F, 0.0F);

            for (int j = 0; j < 3; ++j)
            {
                EntityDragonPart entitydragonpart = null;

                if (j == 0)
                {
                    entitydragonpart = this.field_70985_j;
                }

                if (j == 1)
                {
                    entitydragonpart = this.field_70984_by;
                }

                if (j == 2)
                {
                    entitydragonpart = this.field_70982_bz;
                }

                double[] adouble2 = this.func_70974_a(12 + j * 2, 1.0F);
                float f14 = this.field_70177_z * (float)Math.PI / 180.0F + this.func_70973_b(adouble2[0] - adouble[0]) * (float)Math.PI / 180.0F * 1.0F;
                float f15 = MathHelper.func_76126_a(f14);
                float f16 = MathHelper.func_76134_b(f14);
                float f17 = 1.5F;
                float f18 = (float)(j + 1) * 2.0F;
                entitydragonpart.func_70071_h_();
                entitydragonpart.func_70012_b(this.field_70165_t - (double)((f11 * f17 + f15 * f18) * f2), this.field_70163_u + (adouble2[1] - adouble[1]) * 1.0D - (double)((f18 + f17) * f9) + 1.5D, this.field_70161_v + (double)((f12 * f17 + f16 * f18) * f2), 0.0F, 0.0F);
            }

            if (!this.field_70170_p.field_72995_K)
            {
                this.field_70994_bF = this.func_70972_a(this.field_70986_h.field_70121_D) | this.func_70972_a(this.field_70987_i.field_70121_D);
            }
        }
    }

    private void func_70969_j()
    {
        if (this.field_70992_bH != null)
        {
            if (this.field_70992_bH.field_70128_L)
            {
                if (!this.field_70170_p.field_72995_K)
                {
                    this.func_70965_a(this.field_70986_h, DamageSource.func_94539_a((Explosion)null), 10);
                }

                this.field_70992_bH = null;
            }
            else if (this.field_70173_aa % 10 == 0 && this.field_70734_aK < this.maxHealth)     // CraftBukkit - this.getMaxHealth() -> this.maxHealth
            {
                // CraftBukkit start
                EntityRegainHealthEvent event = new EntityRegainHealthEvent(this.getBukkitEntity(), 1, EntityRegainHealthEvent.RegainReason.ENDER_CRYSTAL);
                this.field_70170_p.getServer().getPluginManager().callEvent(event);

                if (!event.isCancelled())
                {
                    this.func_70606_j(this.func_70630_aN() + event.getAmount());
                }

                // CraftBukkit end
            }
        }

        if (this.field_70146_Z.nextInt(10) == 0)
        {
            float f = 32.0F;
            List list = this.field_70170_p.func_72872_a(EntityEnderCrystal.class, this.field_70121_D.func_72314_b((double)f, (double)f, (double)f));
            EntityEnderCrystal entityendercrystal = null;
            double d0 = Double.MAX_VALUE;
            Iterator iterator = list.iterator();

            while (iterator.hasNext())
            {
                EntityEnderCrystal entityendercrystal1 = (EntityEnderCrystal)iterator.next();
                double d1 = entityendercrystal1.func_70068_e(this);

                if (d1 < d0)
                {
                    d0 = d1;
                    entityendercrystal = entityendercrystal1;
                }
            }

            this.field_70992_bH = entityendercrystal;
        }
    }

    private void func_70970_a(List p_70970_1_)
    {
        double d0 = (this.field_70987_i.field_70121_D.field_72340_a + this.field_70987_i.field_70121_D.field_72336_d) / 2.0D;
        double d1 = (this.field_70987_i.field_70121_D.field_72339_c + this.field_70987_i.field_70121_D.field_72334_f) / 2.0D;
        Iterator iterator = p_70970_1_.iterator();

        while (iterator.hasNext())
        {
            Entity entity = (Entity)iterator.next();

            if (entity instanceof EntityLiving)
            {
                double d2 = entity.field_70165_t - d0;
                double d3 = entity.field_70161_v - d1;
                double d4 = d2 * d2 + d3 * d3;
                entity.func_70024_g(d2 / d4 * 4.0D, 0.20000000298023224D, d3 / d4 * 4.0D);
            }
        }
    }

    private void func_70971_b(List p_70971_1_)
    {
        for (int i = 0; i < p_70971_1_.size(); ++i)
        {
            Entity entity = (Entity)p_70971_1_.get(i);

            if (entity instanceof EntityLiving)
            {
                // CraftBukkit start - throw damage events when the dragon attacks
                // The EntityHuman case is handled in EntityHuman, so don't throw it here
                if (!(entity instanceof EntityPlayer))
                {
                    EntityDamageByEntityEvent damageEvent = new EntityDamageByEntityEvent(this.getBukkitEntity(), entity.getBukkitEntity(), org.bukkit.event.entity.EntityDamageEvent.DamageCause.ENTITY_ATTACK, 10);
                    Bukkit.getPluginManager().callEvent(damageEvent);

                    if (!damageEvent.isCancelled())
                    {
                        entity.getBukkitEntity().setLastDamageCause(damageEvent);
                        entity.func_70097_a(DamageSource.func_76358_a(this), damageEvent.getDamage());
                    }
                }
                else
                {
                    entity.func_70097_a(DamageSource.func_76358_a(this), 10);
                }

                // CraftBukkit end
            }
        }
    }

    private void func_70967_k()
    {
        this.field_70989_bE = false;

        if (this.field_70146_Z.nextInt(2) == 0 && !this.field_70170_p.field_73010_i.isEmpty())
        {
            this.field_70993_bI = (Entity)this.field_70170_p.field_73010_i.get(this.field_70146_Z.nextInt(this.field_70170_p.field_73010_i.size()));
        }
        else
        {
            boolean flag = false;

            do
            {
                this.field_70980_b = 0.0D;
                this.field_70981_c = (double)(70.0F + this.field_70146_Z.nextFloat() * 50.0F);
                this.field_70978_d = 0.0D;
                this.field_70980_b += (double)(this.field_70146_Z.nextFloat() * 120.0F - 60.0F);
                this.field_70978_d += (double)(this.field_70146_Z.nextFloat() * 120.0F - 60.0F);
                double d0 = this.field_70165_t - this.field_70980_b;
                double d1 = this.field_70163_u - this.field_70981_c;
                double d2 = this.field_70161_v - this.field_70978_d;
                flag = d0 * d0 + d1 * d1 + d2 * d2 > 100.0D;
            }
            while (!flag);

            this.field_70993_bI = null;
        }
    }

    private float func_70973_b(double p_70973_1_)
    {
        return (float)MathHelper.func_76138_g(p_70973_1_);
    }

    private boolean func_70972_a(AxisAlignedBB p_70972_1_)
    {
        int i = MathHelper.func_76128_c(p_70972_1_.field_72340_a);
        int j = MathHelper.func_76128_c(p_70972_1_.field_72338_b);
        int k = MathHelper.func_76128_c(p_70972_1_.field_72339_c);
        int l = MathHelper.func_76128_c(p_70972_1_.field_72336_d);
        int i1 = MathHelper.func_76128_c(p_70972_1_.field_72337_e);
        int j1 = MathHelper.func_76128_c(p_70972_1_.field_72334_f);
        boolean flag = false;
        boolean flag1 = false;
        // CraftBukkit start - create a list to hold all the destroyed blocks
        List<org.bukkit.block.Block> destroyedBlocks = new java.util.ArrayList<org.bukkit.block.Block>();
        org.bukkit.craftbukkit.CraftWorld craftWorld = this.field_70170_p.getWorld();
        // CraftBukkit end

        for (int k1 = i; k1 <= l; ++k1)
        {
            for (int l1 = j; l1 <= i1; ++l1)
            {
                for (int i2 = k; i2 <= j1; ++i2)
                {
                    int j2 = this.field_70170_p.func_72798_a(k1, l1, i2);

                    if (j2 != 0)
                    {
                        if (j2 != Block.field_72089_ap.field_71990_ca && j2 != Block.field_72082_bJ.field_71990_ca && j2 != Block.field_71986_z.field_71990_ca)
                        {
                            // CraftBukkit start - add blocks to list rather than destroying them
                            // flag1 = this.world.setAir(k1, l1, i2) || flag1;
                            flag1 = true;
                            destroyedBlocks.add(craftWorld.getBlockAt(k1, l1, i2));
                            // CraftBukkit end
                        }
                        else
                        {
                            flag = true;
                        }
                    }
                }
            }
        }

        if (flag1)
        {
            // CraftBukkit start - set off an EntityExplodeEvent for the dragon exploding all these blocks
            org.bukkit.entity.Entity bukkitEntity = this.getBukkitEntity();
            EntityExplodeEvent event = new EntityExplodeEvent(bukkitEntity, bukkitEntity.getLocation(), destroyedBlocks, 0F);
            Bukkit.getPluginManager().callEvent(event);

            if (event.isCancelled())
            {
                // this flag literally means 'Dragon hit something hard' (Obsidian, White Stone or Bedrock) and will cause the dragon to slow down.
                // We should consider adding an event extension for it, or perhaps returning true if the event is cancelled.
                return flag;
            }
            else
            {
                for (org.bukkit.block.Block block : event.blockList())
                {
                    craftWorld.explodeBlock(block, event.getYield());
                }
            }

            // CraftBukkit end
            double d0 = p_70972_1_.field_72340_a + (p_70972_1_.field_72336_d - p_70972_1_.field_72340_a) * (double)this.field_70146_Z.nextFloat();
            double d1 = p_70972_1_.field_72338_b + (p_70972_1_.field_72337_e - p_70972_1_.field_72338_b) * (double)this.field_70146_Z.nextFloat();
            double d2 = p_70972_1_.field_72339_c + (p_70972_1_.field_72334_f - p_70972_1_.field_72339_c) * (double)this.field_70146_Z.nextFloat();
            this.field_70170_p.func_72869_a("largeexplode", d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }

        return flag;
    }

    public boolean func_70965_a(EntityDragonPart p_70965_1_, DamageSource p_70965_2_, int p_70965_3_)
    {
        if (p_70965_1_ != this.field_70986_h)
        {
            p_70965_3_ = p_70965_3_ / 4 + 1;
        }

        float f = this.field_70177_z * (float)Math.PI / 180.0F;
        float f1 = MathHelper.func_76126_a(f);
        float f2 = MathHelper.func_76134_b(f);
        this.field_70980_b = this.field_70165_t + (double)(f1 * 5.0F) + (double)((this.field_70146_Z.nextFloat() - 0.5F) * 2.0F);
        this.field_70981_c = this.field_70163_u + (double)(this.field_70146_Z.nextFloat() * 3.0F) + 1.0D;
        this.field_70978_d = this.field_70161_v - (double)(f2 * 5.0F) + (double)((this.field_70146_Z.nextFloat() - 0.5F) * 2.0F);
        this.field_70993_bI = null;

        if (p_70965_2_.func_76346_g() instanceof EntityPlayer || p_70965_2_.func_94541_c())
        {
            this.func_82195_e(p_70965_2_, p_70965_3_);
        }

        return true;
    }

    public boolean func_70097_a(DamageSource p_70097_1_, int p_70097_2_)
    {
        return false;
    }

    public boolean func_82195_e(DamageSource p_82195_1_, int p_82195_2_)   // CraftBukkit - protected -> public
    {
        return super.func_70097_a(p_82195_1_, p_82195_2_);
    }

    protected void func_70609_aI()
    {
        ++this.field_70995_bG;

        if (this.field_70995_bG >= 180 && this.field_70995_bG <= 200)
        {
            float f = (this.field_70146_Z.nextFloat() - 0.5F) * 8.0F;
            float f1 = (this.field_70146_Z.nextFloat() - 0.5F) * 4.0F;
            float f2 = (this.field_70146_Z.nextFloat() - 0.5F) * 8.0F;
            this.field_70170_p.func_72869_a("hugeexplosion", this.field_70165_t + (double)f, this.field_70163_u + 2.0D + (double)f1, this.field_70161_v + (double)f2, 0.0D, 0.0D, 0.0D);
        }

        int i;
        int j;

        if (!this.field_70170_p.field_72995_K)
        {
            if (this.field_70995_bG > 150 && this.field_70995_bG % 5 == 0)
            {
                i = expToDrop / 12; // CraftBukkit - drop experience as dragon falls from sky. use experience drop from death event. This is now set in getExpReward()

                while (i > 0)
                {
                    j = EntityXPOrb.func_70527_a(i);
                    i -= j;
                    this.field_70170_p.func_72838_d(new EntityXPOrb(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, j));
                }
            }

            if (this.field_70995_bG == 1)
            {
                this.field_70170_p.func_82739_e(1018, (int)this.field_70165_t, (int)this.field_70163_u, (int)this.field_70161_v, 0);
            }
        }

        this.func_70091_d(0.0D, 0.10000000149011612D, 0.0D);
        this.field_70761_aq = this.field_70177_z += 20.0F;

        if (this.field_70995_bG == 200 && !this.field_70170_p.field_72995_K)
        {
            i = expToDrop - 10 * (expToDrop / 12); // CraftBukkit - drop the remaining experience

            while (i > 0)
            {
                j = EntityXPOrb.func_70527_a(i);
                i -= j;
                this.field_70170_p.func_72838_d(new EntityXPOrb(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, j));
            }

            this.func_70975_a(MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c(this.field_70161_v));
            this.func_70106_y();
        }
    }

    private void func_70975_a(int p_70975_1_, int p_70975_2_)
    {
        byte b0 = 64;
        BlockEndPortal.field_72275_a = true;
        byte b1 = 4;
        // CraftBukkit start - Replace any "this.world" in the following with just "world"!
        BlockStateListPopulator world = new BlockStateListPopulator(this.field_70170_p.getWorld());

        for (int k = b0 - 1; k <= b0 + 32; ++k)
        {
            for (int l = p_70975_1_ - b1; l <= p_70975_1_ + b1; ++l)
            {
                for (int i1 = p_70975_2_ - b1; i1 <= p_70975_2_ + b1; ++i1)
                {
                    double d0 = (double)(l - p_70975_1_);
                    double d1 = (double)(i1 - p_70975_2_);
                    double d2 = d0 * d0 + d1 * d1;

                    if (d2 <= ((double)b1 - 0.5D) * ((double)b1 - 0.5D))
                    {
                        if (k < b0)
                        {
                            if (d2 <= ((double)(b1 - 1) - 0.5D) * ((double)(b1 - 1) - 0.5D))
                            {
                                world.setTypeId(l, k, i1, Block.field_71986_z.field_71990_ca);
                            }
                        }
                        else if (k > b0)
                        {
                            world.setTypeId(l, k, i1, 0);
                        }
                        else if (d2 > ((double)(b1 - 1) - 0.5D) * ((double)(b1 - 1) - 0.5D))
                        {
                            world.setTypeId(l, k, i1, Block.field_71986_z.field_71990_ca);
                        }
                        else
                        {
                            world.setTypeId(l, k, i1, Block.field_72102_bH.field_71990_ca);
                        }
                    }
                }
            }
        }

        world.setTypeId(p_70975_1_, b0 + 0, p_70975_2_, Block.field_71986_z.field_71990_ca);
        world.setTypeId(p_70975_1_, b0 + 1, p_70975_2_, Block.field_71986_z.field_71990_ca);
        world.setTypeId(p_70975_1_, b0 + 2, p_70975_2_, Block.field_71986_z.field_71990_ca);
        world.setTypeId(p_70975_1_ - 1, b0 + 2, p_70975_2_, Block.field_72069_aq.field_71990_ca);
        world.setTypeId(p_70975_1_ + 1, b0 + 2, p_70975_2_, Block.field_72069_aq.field_71990_ca);
        world.setTypeId(p_70975_1_, b0 + 2, p_70975_2_ - 1, Block.field_72069_aq.field_71990_ca);
        world.setTypeId(p_70975_1_, b0 + 2, p_70975_2_ + 1, Block.field_72069_aq.field_71990_ca);
        world.setTypeId(p_70975_1_, b0 + 3, p_70975_2_, Block.field_71986_z.field_71990_ca);
        world.setTypeId(p_70975_1_, b0 + 4, p_70975_2_, Block.field_72084_bK.field_71990_ca);
        EntityCreatePortalEvent event = new EntityCreatePortalEvent((org.bukkit.entity.LivingEntity) this.getBukkitEntity(), java.util.Collections.unmodifiableList(world.getList()), org.bukkit.PortalType.ENDER);
        this.field_70170_p.getServer().getPluginManager().callEvent(event);

        if (!event.isCancelled())
        {
            for (BlockState state : event.getBlocks())
            {
                state.update(true);
            }
        }
        else
        {
            for (BlockState state : event.getBlocks())
            {
                Packet53BlockChange packet = new Packet53BlockChange(state.getX(), state.getY(), state.getZ(), this.field_70170_p);

                for (Iterator it = this.field_70170_p.field_73010_i.iterator(); it.hasNext();)
                {
                    EntityPlayer entity = (EntityPlayer) it.next();

                    if (entity instanceof EntityPlayerMP)
                    {
                        ((EntityPlayerMP) entity).field_71135_a.func_72567_b(packet);
                    }
                }
            }
        }

        // CraftBukkit end
        BlockEndPortal.field_72275_a = false;
    }

    protected void func_70623_bb() {}

    public Entity[] func_70021_al()
    {
        return this.field_70977_g;
    }

    public boolean func_70067_L()
    {
        return false;
    }

    public World func_82194_d()
    {
        return this.field_70170_p;
    }

    protected String func_70639_aQ()
    {
        return "mob.enderdragon.growl";
    }

    protected String func_70621_aR()
    {
        return "mob.enderdragon.hit";
    }

    protected float func_70599_aP()
    {
        return 5.0F;
    }

    // CraftBukkit start
    public int getExpReward()
    {
        // This value is equal to the amount of experience dropped while falling from the sky (10 * 1000)
        // plus what is dropped when the dragon hits the ground (2000)
        return 12000;
    }
    // CraftBukkit end
}
