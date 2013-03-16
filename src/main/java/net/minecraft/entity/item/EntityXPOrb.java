package net.minecraft.entity.item;

// CraftBukkit start
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.event.entity.EntityTargetEvent;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
// CraftBukkit end

public class EntityXPOrb extends Entity
{
    public int field_70533_a;
    public int field_70531_b = 0;
    public int field_70532_c;
    private int field_70529_d = 5;
    public int field_70530_e; // CraftBukkit - private -> public
    private EntityPlayer field_80001_f;
    private int field_80002_g;

    public EntityXPOrb(World p_i3440_1_, double p_i3440_2_, double p_i3440_4_, double p_i3440_6_, int p_i3440_8_)
    {
        super(p_i3440_1_);
        this.func_70105_a(0.5F, 0.5F);
        this.field_70129_M = this.field_70131_O / 2.0F;
        this.func_70107_b(p_i3440_2_, p_i3440_4_, p_i3440_6_);
        this.field_70177_z = (float)(Math.random() * 360.0D);
        this.field_70159_w = (double)((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 2.0F);
        this.field_70181_x = (double)((float)(Math.random() * 0.2D) * 2.0F);
        this.field_70179_y = (double)((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 2.0F);
        this.field_70530_e = p_i3440_8_;
    }

    protected boolean func_70041_e_()
    {
        return false;
    }

    public EntityXPOrb(World p_i3441_1_)
    {
        super(p_i3441_1_);
        this.func_70105_a(0.25F, 0.25F);
        this.field_70129_M = this.field_70131_O / 2.0F;
    }

    protected void func_70088_a() {}

    public void func_70071_h_()
    {
        super.func_70071_h_();

        if (this.field_70532_c > 0)
        {
            --this.field_70532_c;
        }

        this.field_70169_q = this.field_70165_t;
        this.field_70167_r = this.field_70163_u;
        this.field_70166_s = this.field_70161_v;
        this.field_70181_x -= 0.029999999329447746D;

        if (this.field_70170_p.func_72803_f(MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c(this.field_70163_u), MathHelper.func_76128_c(this.field_70161_v)) == Material.field_76256_h)
        {
            this.field_70181_x = 0.20000000298023224D;
            this.field_70159_w = (double)((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F);
            this.field_70179_y = (double)((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F);
            this.func_85030_a("random.fizz", 0.4F, 2.0F + this.field_70146_Z.nextFloat() * 0.4F);
        }

        this.func_70048_i(this.field_70165_t, (this.field_70121_D.field_72338_b + this.field_70121_D.field_72337_e) / 2.0D, this.field_70161_v);
        double d0 = 8.0D;

        if (this.field_80002_g < this.field_70533_a - 20 + this.field_70157_k % 100)
        {
            if (this.field_80001_f == null || this.field_80001_f.func_70068_e(this) > d0 * d0)
            {
                this.field_80001_f = this.field_70170_p.func_72890_a(this, d0);
            }

            this.field_80002_g = this.field_70533_a;
        }

        if (this.field_80001_f != null)
        {
            // CraftBukkit start
            EntityTargetEvent event = CraftEventFactory.callEntityTargetEvent(this, field_80001_f, EntityTargetEvent.TargetReason.CLOSEST_PLAYER);
            Entity target = event.getTarget() == null ? null : ((org.bukkit.craftbukkit.entity.CraftEntity) event.getTarget()).getHandle();

            if (!event.isCancelled() && target != null)
            {
                double d1 = (target.field_70165_t - this.field_70165_t) / d0;
                double d2 = (target.field_70163_u + (double) target.func_70047_e() - this.field_70163_u) / d0;
                double d3 = (target.field_70161_v - this.field_70161_v) / d0;
                double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
                double d5 = 1.0D - d4;

                if (d5 > 0.0D)
                {
                    d5 *= d5;
                    this.field_70159_w += d1 / d4 * d5 * 0.1D;
                    this.field_70181_x += d2 / d4 * d5 * 0.1D;
                    this.field_70179_y += d3 / d4 * d5 * 0.1D;
                }

                // CraftBukkit end
            }
        }

        this.func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
        float f = 0.98F;

        if (this.field_70122_E)
        {
            f = 0.58800006F;
            int i = this.field_70170_p.func_72798_a(MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c(this.field_70121_D.field_72338_b) - 1, MathHelper.func_76128_c(this.field_70161_v));

            if (i > 0)
            {
                f = Block.field_71973_m[i].field_72016_cq * 0.98F;
            }
        }

        this.field_70159_w *= (double)f;
        this.field_70181_x *= 0.9800000190734863D;
        this.field_70179_y *= (double)f;

        if (this.field_70122_E)
        {
            this.field_70181_x *= -0.8999999761581421D;
        }

        ++this.field_70533_a;
        ++this.field_70531_b;

        if (this.field_70531_b >= 6000)
        {
            this.func_70106_y();
        }
    }

    public boolean func_70072_I()
    {
        return this.field_70170_p.func_72918_a(this.field_70121_D, Material.field_76244_g, (Entity) this);
    }

    protected void func_70081_e(int p_70081_1_)
    {
        this.func_70097_a(DamageSource.field_76372_a, p_70081_1_);
    }

    public boolean func_70097_a(DamageSource p_70097_1_, int p_70097_2_)
    {
        if (this.func_85032_ar())
        {
            return false;
        }
        else
        {
            this.func_70018_K();
            this.field_70529_d -= p_70097_2_;

            if (this.field_70529_d <= 0)
            {
                this.func_70106_y();
            }

            return false;
        }
    }

    public void func_70014_b(NBTTagCompound p_70014_1_)
    {
        p_70014_1_.func_74777_a("Health", (short)((byte)this.field_70529_d));
        p_70014_1_.func_74777_a("Age", (short)this.field_70531_b);
        p_70014_1_.func_74777_a("Value", (short)this.field_70530_e);
    }

    public void func_70037_a(NBTTagCompound p_70037_1_)
    {
        this.field_70529_d = p_70037_1_.func_74765_d("Health") & 255;
        this.field_70531_b = p_70037_1_.func_74765_d("Age");
        this.field_70530_e = p_70037_1_.func_74765_d("Value");
    }

    public void func_70100_b_(EntityPlayer p_70100_1_)
    {
        if (!this.field_70170_p.field_72995_K)
        {
            if (this.field_70532_c == 0 && p_70100_1_.field_71090_bL == 0)
            {
                p_70100_1_.field_71090_bL = 2;
                this.func_85030_a("random.orb", 0.1F, 0.5F * ((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.7F + 1.8F));
                p_70100_1_.func_71001_a(this, 1);
                p_70100_1_.func_71023_q(CraftEventFactory.callPlayerExpChangeEvent(p_70100_1_, this.field_70530_e).getAmount()); // CraftBukkit - this.value to event.getAmount()
                this.func_70106_y();
            }
        }
    }

    public int func_70526_d()
    {
        return this.field_70530_e;
    }

    public static int func_70527_a(int p_70527_0_)
    {
        // CraftBukkit start
        if (p_70527_0_ > 162670129)
        {
            return p_70527_0_ - 100000;
        }

        if (p_70527_0_ > 81335063)
        {
            return 81335063;
        }

        if (p_70527_0_ > 40667527)
        {
            return 40667527;
        }

        if (p_70527_0_ > 20333759)
        {
            return 20333759;
        }

        if (p_70527_0_ > 10166857)
        {
            return 10166857;
        }

        if (p_70527_0_ > 5083423)
        {
            return 5083423;
        }

        if (p_70527_0_ > 2541701)
        {
            return 2541701;
        }

        if (p_70527_0_ > 1270849)
        {
            return 1270849;
        }

        if (p_70527_0_ > 635413)
        {
            return 635413;
        }

        if (p_70527_0_ > 317701)
        {
            return 317701;
        }

        if (p_70527_0_ > 158849)
        {
            return 158849;
        }

        if (p_70527_0_ > 79423)
        {
            return 79423;
        }

        if (p_70527_0_ > 39709)
        {
            return 39709;
        }

        if (p_70527_0_ > 19853)
        {
            return 19853;
        }

        if (p_70527_0_ > 9923)
        {
            return 9923;
        }

        if (p_70527_0_ > 4957)
        {
            return 4957;
        }

        // CraftBukkit end
        return p_70527_0_ >= 2477 ? 2477 : (p_70527_0_ >= 1237 ? 1237 : (p_70527_0_ >= 617 ? 617 : (p_70527_0_ >= 307 ? 307 : (p_70527_0_ >= 149 ? 149 : (p_70527_0_ >= 73 ? 73 : (p_70527_0_ >= 37 ? 37 : (p_70527_0_ >= 17 ? 17 : (p_70527_0_ >= 7 ? 7 : (p_70527_0_ >= 3 ? 3 : 1)))))))));
    }

    public boolean func_70075_an()
    {
        return false;
    }
}
