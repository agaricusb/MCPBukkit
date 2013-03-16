package net.minecraft.entity.passive;

import net.minecraft.block.BlockCloth;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBeg;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
public class EntityWolf extends EntityTameable
{
    private float field_70926_e;
    private float field_70924_f;
    private boolean field_70925_g;
    private boolean field_70928_h;
    private float field_70929_i;
    private float field_70927_j;

    public EntityWolf(World p_i3526_1_)
    {
        super(p_i3526_1_);
        this.field_70750_az = "/mob/wolf.png";
        this.func_70105_a(0.6F, 0.8F);
        this.field_70697_bw = 0.3F;
        this.func_70661_as().func_75491_a(true);
        this.field_70714_bg.func_75776_a(1, new EntityAISwimming(this));
        this.field_70714_bg.func_75776_a(2, this.field_70911_d);
        this.field_70714_bg.func_75776_a(3, new EntityAILeapAtTarget(this, 0.4F));
        this.field_70714_bg.func_75776_a(4, new EntityAIAttackOnCollide(this, this.field_70697_bw, true));
        this.field_70714_bg.func_75776_a(5, new EntityAIFollowOwner(this, this.field_70697_bw, 10.0F, 2.0F));
        this.field_70714_bg.func_75776_a(6, new EntityAIMate(this, this.field_70697_bw));
        this.field_70714_bg.func_75776_a(7, new EntityAIWander(this, this.field_70697_bw));
        this.field_70714_bg.func_75776_a(8, new EntityAIBeg(this, 8.0F));
        this.field_70714_bg.func_75776_a(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.field_70714_bg.func_75776_a(9, new EntityAILookIdle(this));
        this.field_70715_bh.func_75776_a(1, new EntityAIOwnerHurtByTarget(this));
        this.field_70715_bh.func_75776_a(2, new EntityAIOwnerHurtTarget(this));
        this.field_70715_bh.func_75776_a(3, new EntityAIHurtByTarget(this, true));
        this.field_70715_bh.func_75776_a(4, new EntityAITargetNonTamed(this, EntitySheep.class, 16.0F, 200, false));
    }

    public boolean func_70650_aV()
    {
        return true;
    }

    public void func_70624_b(EntityLiving p_70624_1_)
    {
        super.func_70624_b(p_70624_1_);

        if (p_70624_1_ instanceof EntityPlayer)
        {
            this.func_70916_h(true);
        }
    }

    protected void func_70629_bd()
    {
        this.field_70180_af.func_75692_b(18, Integer.valueOf(this.getScaledHealth())); // CraftBukkit - this.getHealth() -> this.getScaledHealth()
    }

    public int func_70667_aM()
    {
        return this.func_70909_n() ? 20 : 8;
    }

    protected void func_70088_a()
    {
        super.func_70088_a();
        this.field_70180_af.func_75682_a(18, new Integer(this.func_70630_aN()));
        this.field_70180_af.func_75682_a(19, new Byte((byte)0));
        this.field_70180_af.func_75682_a(20, new Byte((byte)BlockCloth.func_72238_e_(1)));
    }

    protected void func_70036_a(int p_70036_1_, int p_70036_2_, int p_70036_3_, int p_70036_4_)
    {
        this.func_85030_a("mob.wolf.step", 0.15F, 1.0F);
    }

    public void func_70014_b(NBTTagCompound p_70014_1_)
    {
        super.func_70014_b(p_70014_1_);
        p_70014_1_.func_74757_a("Angry", this.func_70919_bu());
        p_70014_1_.func_74774_a("CollarColor", (byte)this.func_82186_bH());
    }

    public void func_70037_a(NBTTagCompound p_70037_1_)
    {
        super.func_70037_a(p_70037_1_);
        this.func_70916_h(p_70037_1_.func_74767_n("Angry"));

        if (p_70037_1_.func_74764_b("CollarColor"))
        {
            this.func_82185_r(p_70037_1_.func_74771_c("CollarColor"));
        }
    }

    protected boolean func_70692_ba()
    {
        // CraftBukkit - added && !this.isTamed()
        return this.func_70919_bu() && !this.func_70909_n();
    }

    protected String func_70639_aQ()
    {
        // CraftBukkit - getInt(18) < 10 -> < this.maxHealth / 2
        return this.func_70919_bu() ? "mob.wolf.growl" : (this.field_70146_Z.nextInt(3) == 0 ? (this.func_70909_n() && this.field_70180_af.func_75679_c(18) < this.maxHealth / 2 ? "mob.wolf.whine" : "mob.wolf.panting") : "mob.wolf.bark");
    }

    protected String func_70621_aR()
    {
        return "mob.wolf.hurt";
    }

    protected String func_70673_aS()
    {
        return "mob.wolf.death";
    }

    protected float func_70599_aP()
    {
        return 0.4F;
    }

    protected int func_70633_aT()
    {
        return -1;
    }

    public void func_70636_d()
    {
        super.func_70636_d();

        if (!this.field_70170_p.field_72995_K && this.field_70925_g && !this.field_70928_h && !this.func_70781_l() && this.field_70122_E)
        {
            this.field_70928_h = true;
            this.field_70929_i = 0.0F;
            this.field_70927_j = 0.0F;
            this.field_70170_p.func_72960_a(this, (byte)8);
        }
    }

    public void func_70071_h_()
    {
        super.func_70071_h_();
        this.field_70924_f = this.field_70926_e;

        if (this.func_70922_bv())
        {
            this.field_70926_e += (1.0F - this.field_70926_e) * 0.4F;
        }
        else
        {
            this.field_70926_e += (0.0F - this.field_70926_e) * 0.4F;
        }

        if (this.func_70922_bv())
        {
            this.field_70700_bx = 10;
        }

        if (this.func_70026_G())
        {
            this.field_70925_g = true;
            this.field_70928_h = false;
            this.field_70929_i = 0.0F;
            this.field_70927_j = 0.0F;
        }
        else if ((this.field_70925_g || this.field_70928_h) && this.field_70928_h)
        {
            if (this.field_70929_i == 0.0F)
            {
                this.func_85030_a("mob.wolf.shake", this.func_70599_aP(), (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F);
            }

            this.field_70927_j = this.field_70929_i;
            this.field_70929_i += 0.05F;

            if (this.field_70927_j >= 2.0F)
            {
                this.field_70925_g = false;
                this.field_70928_h = false;
                this.field_70927_j = 0.0F;
                this.field_70929_i = 0.0F;
            }

            if (this.field_70929_i > 0.4F)
            {
                float f = (float)this.field_70121_D.field_72338_b;
                int i = (int)(MathHelper.func_76126_a((this.field_70929_i - 0.4F) * (float)Math.PI) * 7.0F);

                for (int j = 0; j < i; ++j)
                {
                    float f1 = (this.field_70146_Z.nextFloat() * 2.0F - 1.0F) * this.field_70130_N * 0.5F;
                    float f2 = (this.field_70146_Z.nextFloat() * 2.0F - 1.0F) * this.field_70130_N * 0.5F;
                    this.field_70170_p.func_72869_a("splash", this.field_70165_t + (double)f1, (double)(f + 0.8F), this.field_70161_v + (double)f2, this.field_70159_w, this.field_70181_x, this.field_70179_y);
                }
            }
        }
    }

    public float func_70047_e()
    {
        return this.field_70131_O * 0.8F;
    }

    public int func_70646_bf()
    {
        return this.func_70906_o() ? 20 : super.func_70646_bf();
    }

    public boolean func_70097_a(DamageSource p_70097_1_, int p_70097_2_)
    {
        if (this.func_85032_ar())
        {
            return false;
        }
        else
        {
            Entity entity = p_70097_1_.func_76346_g();
            this.field_70911_d.func_75270_a(false);

            if (entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow))
            {
                p_70097_2_ = (p_70097_2_ + 1) / 2;
            }

            return super.func_70097_a(p_70097_1_, p_70097_2_);
        }
    }

    public boolean func_70652_k(Entity p_70652_1_)
    {
        int i = this.func_70909_n() ? 4 : 2;
        return p_70652_1_.func_70097_a(DamageSource.func_76358_a(this), i);
    }

    public boolean func_70085_c(EntityPlayer p_70085_1_)
    {
        ItemStack itemstack = p_70085_1_.field_71071_by.func_70448_g();

        if (this.func_70909_n())
        {
            if (itemstack != null)
            {
                if (Item.field_77698_e[itemstack.field_77993_c] instanceof ItemFood)
                {
                    ItemFood itemfood = (ItemFood)Item.field_77698_e[itemstack.field_77993_c];

                    if (itemfood.func_77845_h() && this.field_70180_af.func_75679_c(18) < 20)
                    {
                        if (!p_70085_1_.field_71075_bZ.field_75098_d)
                        {
                            --itemstack.field_77994_a;
                        }

                        this.func_70691_i(itemfood.func_77847_f());

                        if (itemstack.field_77994_a <= 0)
                        {
                            p_70085_1_.field_71071_by.func_70299_a(p_70085_1_.field_71071_by.field_70461_c, (ItemStack)null);
                        }

                        return true;
                    }
                }
                else if (itemstack.field_77993_c == Item.field_77756_aW.field_77779_bT)
                {
                    int i = BlockCloth.func_72238_e_(itemstack.func_77960_j());

                    if (i != this.func_82186_bH())
                    {
                        this.func_82185_r(i);

                        if (!p_70085_1_.field_71075_bZ.field_75098_d && --itemstack.field_77994_a <= 0)
                        {
                            p_70085_1_.field_71071_by.func_70299_a(p_70085_1_.field_71071_by.field_70461_c, (ItemStack)null);
                        }

                        return true;
                    }
                }
            }

            if (p_70085_1_.field_71092_bJ.equalsIgnoreCase(this.func_70905_p()) && !this.field_70170_p.field_72995_K && !this.func_70877_b(itemstack))
            {
                this.field_70911_d.func_75270_a(!this.func_70906_o());
                this.field_70703_bu = false;
                this.func_70778_a((PathEntity)null);
            }
        }
        else if (itemstack != null && itemstack.field_77993_c == Item.field_77755_aX.field_77779_bT && !this.func_70919_bu())
        {
            if (!p_70085_1_.field_71075_bZ.field_75098_d)
            {
                --itemstack.field_77994_a;
            }

            if (itemstack.field_77994_a <= 0)
            {
                p_70085_1_.field_71071_by.func_70299_a(p_70085_1_.field_71071_by.field_70461_c, (ItemStack)null);
            }

            if (!this.field_70170_p.field_72995_K)
            {
                // CraftBukkit - added event call and isCancelled check.
                if (this.field_70146_Z.nextInt(3) == 0 && !org.bukkit.craftbukkit.event.CraftEventFactory.callEntityTameEvent(this, p_70085_1_).isCancelled())
                {
                    boolean updateMaxHealth = this.func_70667_aM() == this.maxHealth; // CraftBukkit
                    this.func_70903_f(true);
                    this.func_70778_a((PathEntity)null);
                    this.func_70624_b((EntityLiving)null);
                    this.field_70911_d.func_75270_a(true);

                    // CraftBukkit start
                    if (updateMaxHealth)
                    {
                        this.maxHealth = this.func_70667_aM();
                    }

                    this.func_70606_j(this.maxHealth);
                    // CraftBukkit end
                    this.func_70910_a(p_70085_1_.field_71092_bJ);
                    this.func_70908_e(true);
                    this.field_70170_p.func_72960_a(this, (byte)7);
                }
                else
                {
                    this.func_70908_e(false);
                    this.field_70170_p.func_72960_a(this, (byte)6);
                }
            }

            return true;
        }

        return super.func_70085_c(p_70085_1_);
    }

    public boolean func_70877_b(ItemStack p_70877_1_)
    {
        return p_70877_1_ == null ? false : (!(Item.field_77698_e[p_70877_1_.field_77993_c] instanceof ItemFood) ? false : ((ItemFood)Item.field_77698_e[p_70877_1_.field_77993_c]).func_77845_h());
    }

    public int func_70641_bl()
    {
        return 8;
    }

    public boolean func_70919_bu()
    {
        return (this.field_70180_af.func_75683_a(16) & 2) != 0;
    }

    public void func_70916_h(boolean p_70916_1_)
    {
        byte b0 = this.field_70180_af.func_75683_a(16);

        if (p_70916_1_)
        {
            this.field_70180_af.func_75692_b(16, Byte.valueOf((byte)(b0 | 2)));
        }
        else
        {
            this.field_70180_af.func_75692_b(16, Byte.valueOf((byte)(b0 & -3)));
        }
    }

    public int func_82186_bH()
    {
        return this.field_70180_af.func_75683_a(20) & 15;
    }

    public void func_82185_r(int p_82185_1_)
    {
        this.field_70180_af.func_75692_b(20, Byte.valueOf((byte)(p_82185_1_ & 15)));
    }

    public EntityWolf func_70879_a(EntityAgeable p_70879_1_)
    {
        EntityWolf entitywolf = new EntityWolf(this.field_70170_p);
        String s = this.func_70905_p();

        if (s != null && s.trim().length() > 0)
        {
            entitywolf.func_70910_a(s);
            entitywolf.func_70903_f(true);
        }

        return entitywolf;
    }

    public void func_70918_i(boolean p_70918_1_)
    {
        byte b0 = this.field_70180_af.func_75683_a(19);

        if (p_70918_1_)
        {
            this.field_70180_af.func_75692_b(19, Byte.valueOf((byte)1));
        }
        else
        {
            this.field_70180_af.func_75692_b(19, Byte.valueOf((byte)0));
        }
    }

    public boolean func_70878_b(EntityAnimal p_70878_1_)
    {
        if (p_70878_1_ == this)
        {
            return false;
        }
        else if (!this.func_70909_n())
        {
            return false;
        }
        else if (!(p_70878_1_ instanceof EntityWolf))
        {
            return false;
        }
        else
        {
            EntityWolf entitywolf = (EntityWolf)p_70878_1_;
            return !entitywolf.func_70909_n() ? false : (entitywolf.func_70906_o() ? false : this.func_70880_s() && entitywolf.func_70880_s());
        }
    }

    public boolean func_70922_bv()
    {
        return this.field_70180_af.func_75683_a(19) == 1;
    }

    public EntityAgeable func_90011_a(EntityAgeable p_90011_1_)
    {
        return this.func_70879_a(p_90011_1_);
    }
}
