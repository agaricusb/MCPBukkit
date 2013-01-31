package net.minecraft.entity.monster;

import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityWitch extends EntityMob implements IRangedAttackMob
{
    private static final int[] field_82199_d = new int[] {Item.field_77751_aT.field_77779_bT, Item.field_77747_aY.field_77779_bT, Item.field_77767_aC.field_77779_bT, Item.field_77728_bu.field_77779_bT, Item.field_77729_bt.field_77779_bT, Item.field_77677_M.field_77779_bT, Item.field_77669_D.field_77779_bT, Item.field_77669_D.field_77779_bT};
    private int field_82200_e = 0;

    public EntityWitch(World p_i5066_1_)
    {
        super(p_i5066_1_);
        this.field_70750_az = "/mob/villager/witch.png";
        this.field_70697_bw = 0.25F;
        this.field_70714_bg.func_75776_a(1, new EntityAISwimming(this));
        this.field_70714_bg.func_75776_a(2, new EntityAIArrowAttack(this, this.field_70697_bw, 60, 10.0F));
        this.field_70714_bg.func_75776_a(2, new EntityAIWander(this, this.field_70697_bw));
        this.field_70714_bg.func_75776_a(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.field_70714_bg.func_75776_a(3, new EntityAILookIdle(this));
        this.field_70715_bh.func_75776_a(1, new EntityAIHurtByTarget(this, false));
        this.field_70715_bh.func_75776_a(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 16.0F, 0, true));
    }

    protected void func_70088_a()
    {
        super.func_70088_a();
        this.func_70096_w().func_75682_a(21, Byte.valueOf((byte)0));
    }

    protected String func_70639_aQ()
    {
        return "mob.witch.idle";
    }

    protected String func_70621_aR()
    {
        return "mob.witch.hurt";
    }

    protected String func_70673_aS()
    {
        return "mob.witch.death";
    }

    public void func_82197_f(boolean p_82197_1_)
    {
        this.func_70096_w().func_75692_b(21, Byte.valueOf((byte)(p_82197_1_ ? 1 : 0)));
    }

    public boolean func_82198_m()
    {
        return this.func_70096_w().func_75683_a(21) == 1;
    }

    public int func_70667_aM()
    {
        return 26;
    }

    public boolean func_70650_aV()
    {
        return true;
    }

    public void func_70636_d()
    {
        if (!this.field_70170_p.field_72995_K)
        {
            if (this.func_82198_m())
            {
                if (this.field_82200_e-- <= 0)
                {
                    this.func_82197_f(false);
                    ItemStack itemstack = this.func_70694_bm();
                    this.func_70062_b(0, (ItemStack)null);

                    if (itemstack != null && itemstack.field_77993_c == Item.field_77726_bs.field_77779_bT)
                    {
                        List list = Item.field_77726_bs.func_77832_l(itemstack);

                        if (list != null)
                        {
                            Iterator iterator = list.iterator();

                            while (iterator.hasNext())
                            {
                                PotionEffect potioneffect = (PotionEffect)iterator.next();
                                this.func_70690_d(new PotionEffect(potioneffect));
                            }
                        }
                    }
                }
            }
            else
            {
                short short1 = -1;

                if (this.field_70146_Z.nextFloat() < 0.15F && this.func_70027_ad() && !this.func_70644_a(Potion.field_76426_n))
                {
                    short1 = 16307;
                }
                else if (this.field_70146_Z.nextFloat() < 0.05F && this.field_70734_aK < this.maxHealth)     // CraftBukkit - this.getMaxHealth -> this.maxHealth
                {
                    short1 = 16341;
                }
                else if (this.field_70146_Z.nextFloat() < 0.25F && this.func_70638_az() != null && !this.func_70644_a(Potion.field_76424_c) && this.func_70638_az().func_70068_e(this) > 121.0D)
                {
                    short1 = 16274;
                }
                else if (this.field_70146_Z.nextFloat() < 0.25F && this.func_70638_az() != null && !this.func_70644_a(Potion.field_76424_c) && this.func_70638_az().func_70068_e(this) > 121.0D)
                {
                    short1 = 16274;
                }

                if (short1 > -1)
                {
                    this.func_70062_b(0, new ItemStack(Item.field_77726_bs, 1, short1));
                    this.field_82200_e = this.func_70694_bm().func_77988_m();
                    this.func_82197_f(true);
                }
            }

            if (this.field_70146_Z.nextFloat() < 7.5E-4F)
            {
                this.field_70170_p.func_72960_a(this, (byte)15);
            }
        }

        super.func_70636_d();
    }

    protected int func_70672_c(DamageSource p_70672_1_, int p_70672_2_)
    {
        p_70672_2_ = super.func_70672_c(p_70672_1_, p_70672_2_);

        if (p_70672_1_.func_76346_g() == this)
        {
            p_70672_2_ = 0;
        }

        if (p_70672_1_.func_82725_o())
        {
            p_70672_2_ = (int)((double)p_70672_2_ * 0.15D);
        }

        return p_70672_2_;
    }

    public float func_70616_bs()
    {
        float f = super.func_70616_bs();

        if (this.func_82198_m())
        {
            f *= 0.75F;
        }

        return f;
    }

    protected void func_70628_a(boolean p_70628_1_, int p_70628_2_)
    {
        // CraftBukkit start
        java.util.List<org.bukkit.inventory.ItemStack> loot = new java.util.ArrayList<org.bukkit.inventory.ItemStack>();
        int j = this.field_70146_Z.nextInt(3) + 1;

        for (int k = 0; k < j; ++k)
        {
            int l = this.field_70146_Z.nextInt(3);
            int i1 = field_82199_d[this.field_70146_Z.nextInt(field_82199_d.length)];

            if (p_70628_2_ > 0)
            {
                l += this.field_70146_Z.nextInt(p_70628_2_ + 1);
            }

            loot.add(new org.bukkit.inventory.ItemStack(i1, l));
        }

        org.bukkit.craftbukkit.event.CraftEventFactory.callEntityDeathEvent(this, loot);
        // CraftBukkit end
    }

    public void func_82196_d(EntityLiving p_82196_1_)
    {
        if (!this.func_82198_m())
        {
            EntityPotion entitypotion = new EntityPotion(this.field_70170_p, this, 32732);
            entitypotion.field_70125_A -= -20.0F;
            double d0 = p_82196_1_.field_70165_t + p_82196_1_.field_70159_w - this.field_70165_t;
            double d1 = p_82196_1_.field_70163_u + (double)p_82196_1_.func_70047_e() - 1.100000023841858D - this.field_70163_u;
            double d2 = p_82196_1_.field_70161_v + p_82196_1_.field_70179_y - this.field_70161_v;
            float f = MathHelper.func_76133_a(d0 * d0 + d2 * d2);

            if (f >= 8.0F && !p_82196_1_.func_70644_a(Potion.field_76421_d))
            {
                entitypotion.func_82340_a(32698);
            }
            else if (p_82196_1_.func_70630_aN() >= 8 && !p_82196_1_.func_70644_a(Potion.field_76436_u))
            {
                entitypotion.func_82340_a(32660);
            }
            else if (f <= 3.0F && !p_82196_1_.func_70644_a(Potion.field_76437_t) && this.field_70146_Z.nextFloat() < 0.25F)
            {
                entitypotion.func_82340_a(32696);
            }

            entitypotion.func_70186_c(d0, d1 + (double)(f * 0.2F), d2, 0.75F, 8.0F);
            this.field_70170_p.func_72838_d(entitypotion);
        }
    }
}
