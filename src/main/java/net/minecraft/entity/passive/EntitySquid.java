package net.minecraft.entity.passive;

import org.bukkit.craftbukkit.TrigMath; // CraftBukkit
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntitySquid extends EntityWaterMob
{
    public float field_70861_d = 0.0F;
    public float field_70862_e = 0.0F;
    public float field_70859_f = 0.0F;
    public float field_70860_g = 0.0F;
    public float field_70867_h = 0.0F;
    public float field_70868_i = 0.0F;
    public float field_70866_j = 0.0F;
    public float field_70865_by = 0.0F;
    private float field_70863_bz = 0.0F;
    private float field_70864_bA = 0.0F;
    private float field_70871_bB = 0.0F;
    private float field_70872_bC = 0.0F;
    private float field_70869_bD = 0.0F;
    private float field_70870_bE = 0.0F;

    public EntitySquid(World p_i3523_1_)
    {
        super(p_i3523_1_);
        this.field_70750_az = "/mob/squid.png";
        this.func_70105_a(0.95F, 0.95F);
        this.field_70864_bA = 1.0F / (this.field_70146_Z.nextFloat() + 1.0F) * 0.2F;
    }

    public int func_70667_aM()
    {
        return 10;
    }

    protected String func_70639_aQ()
    {
        return null;
    }

    protected String func_70621_aR()
    {
        return null;
    }

    protected String func_70673_aS()
    {
        return null;
    }

    protected float func_70599_aP()
    {
        return 0.4F;
    }

    protected int func_70633_aT()
    {
        return 0;
    }

    protected void func_70628_a(boolean p_70628_1_, int p_70628_2_)
    {
        // CraftBukkit start - whole method
        java.util.List<org.bukkit.inventory.ItemStack> loot = new java.util.ArrayList<org.bukkit.inventory.ItemStack>();
        int count = this.field_70146_Z.nextInt(3 + p_70628_2_) + 1;

        if (count > 0)
        {
            loot.add(new org.bukkit.inventory.ItemStack(org.bukkit.Material.INK_SACK, count));
        }

        org.bukkit.craftbukkit.event.CraftEventFactory.callEntityDeathEvent(this, loot);
        // CraftBukkit end
    }

    public boolean func_70090_H()
    {
        return this.field_70170_p.func_72918_a(this.field_70121_D.func_72314_b(0.0D, -0.6000000238418579D, 0.0D), Material.field_76244_g, (Entity) this);
    }

    public void func_70636_d()
    {
        super.func_70636_d();
        this.field_70862_e = this.field_70861_d;
        this.field_70860_g = this.field_70859_f;
        this.field_70868_i = this.field_70867_h;
        this.field_70865_by = this.field_70866_j;
        this.field_70867_h += this.field_70864_bA;

        if (this.field_70867_h > ((float)Math.PI * 2F))
        {
            this.field_70867_h -= ((float)Math.PI * 2F);

            if (this.field_70146_Z.nextInt(10) == 0)
            {
                this.field_70864_bA = 1.0F / (this.field_70146_Z.nextFloat() + 1.0F) * 0.2F;
            }
        }

        if (this.func_70090_H())
        {
            float f;

            if (this.field_70867_h < (float)Math.PI)
            {
                f = this.field_70867_h / (float)Math.PI;
                this.field_70866_j = MathHelper.func_76126_a(f * f * (float)Math.PI) * (float)Math.PI * 0.25F;

                if ((double)f > 0.75D)
                {
                    this.field_70863_bz = 1.0F;
                    this.field_70871_bB = 1.0F;
                }
                else
                {
                    this.field_70871_bB *= 0.8F;
                }
            }
            else
            {
                this.field_70866_j = 0.0F;
                this.field_70863_bz *= 0.9F;
                this.field_70871_bB *= 0.99F;
            }

            if (!this.field_70170_p.field_72995_K)
            {
                this.field_70159_w = (double)(this.field_70872_bC * this.field_70863_bz);
                this.field_70181_x = (double)(this.field_70869_bD * this.field_70863_bz);
                this.field_70179_y = (double)(this.field_70870_bE * this.field_70863_bz);
            }

            f = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
            // CraftBukkit - Math -> TrigMath
            this.field_70761_aq += (-((float) TrigMath.atan2(this.field_70159_w, this.field_70179_y)) * 180.0F / (float)Math.PI - this.field_70761_aq) * 0.1F;
            this.field_70177_z = this.field_70761_aq;
            this.field_70859_f += (float)Math.PI * this.field_70871_bB * 1.5F;
            // CraftBukkit - Math -> TrigMath
            this.field_70861_d += (-((float) TrigMath.atan2((double) f, this.field_70181_x)) * 180.0F / (float)Math.PI - this.field_70861_d) * 0.1F;
        }
        else
        {
            this.field_70866_j = MathHelper.func_76135_e(MathHelper.func_76126_a(this.field_70867_h)) * (float)Math.PI * 0.25F;

            if (!this.field_70170_p.field_72995_K)
            {
                this.field_70159_w = 0.0D;
                this.field_70181_x -= 0.08D;
                this.field_70181_x *= 0.9800000190734863D;
                this.field_70179_y = 0.0D;
            }

            this.field_70861_d = (float)((double)this.field_70861_d + (double)(-90.0F - this.field_70861_d) * 0.02D);
        }
    }

    public void func_70612_e(float p_70612_1_, float p_70612_2_)
    {
        this.func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
    }

    protected void func_70626_be()
    {
        ++this.field_70708_bq;

        if (this.field_70708_bq > 100)
        {
            this.field_70872_bC = this.field_70869_bD = this.field_70870_bE = 0.0F;
        }
        else if (this.field_70146_Z.nextInt(50) == 0 || !this.field_70171_ac || this.field_70872_bC == 0.0F && this.field_70869_bD == 0.0F && this.field_70870_bE == 0.0F)
        {
            float f = this.field_70146_Z.nextFloat() * (float)Math.PI * 2.0F;
            this.field_70872_bC = MathHelper.func_76134_b(f) * 0.2F;
            this.field_70869_bD = -0.1F + this.field_70146_Z.nextFloat() * 0.2F;
            this.field_70870_bE = MathHelper.func_76126_a(f) * 0.2F;
        }

        this.func_70623_bb();
    }

    public boolean func_70601_bi()
    {
        return this.field_70163_u > 45.0D && this.field_70163_u < 63.0D && super.func_70601_bi();
    }
}
