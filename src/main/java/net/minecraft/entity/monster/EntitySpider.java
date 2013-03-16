package net.minecraft.entity.monster;

import org.bukkit.event.entity.EntityTargetEvent; // CraftBukkit
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntitySpider extends EntityMob
{
    public EntitySpider(World p_i3557_1_)
    {
        super(p_i3557_1_);
        this.field_70750_az = "/mob/spider.png";
        this.func_70105_a(1.4F, 0.9F);
        this.field_70697_bw = 0.8F;
    }

    protected void func_70088_a()
    {
        super.func_70088_a();
        this.field_70180_af.func_75682_a(16, new Byte((byte)0));
    }

    public void func_70071_h_()
    {
        super.func_70071_h_();

        if (!this.field_70170_p.field_72995_K)
        {
            this.func_70839_e(this.field_70123_F);
        }
    }

    public int func_70667_aM()
    {
        return 16;
    }

    public double func_70042_X()
    {
        return (double)this.field_70131_O * 0.75D - 0.5D;
    }

    protected Entity func_70782_k()
    {
        float f = this.func_70013_c(1.0F);

        if (f < 0.5F)
        {
            double d0 = 16.0D;
            return this.field_70170_p.func_72856_b(this, d0);
        }
        else
        {
            return null;
        }
    }

    protected String func_70639_aQ()
    {
        return "mob.spider.say";
    }

    protected String func_70621_aR()
    {
        return "mob.spider.say";
    }

    protected String func_70673_aS()
    {
        return "mob.spider.death";
    }

    protected void func_70036_a(int p_70036_1_, int p_70036_2_, int p_70036_3_, int p_70036_4_)
    {
        this.func_85030_a("mob.spider.step", 0.15F, 1.0F);
    }

    protected void func_70785_a(Entity p_70785_1_, float p_70785_2_)
    {
        float f1 = this.func_70013_c(1.0F);

        if (f1 > 0.5F && this.field_70146_Z.nextInt(100) == 0)
        {
            // CraftBukkit start
            EntityTargetEvent event = new EntityTargetEvent(this.getBukkitEntity(), null, EntityTargetEvent.TargetReason.FORGOT_TARGET);
            this.field_70170_p.getServer().getPluginManager().callEvent(event);

            if (!event.isCancelled())
            {
                if (event.getTarget() == null)
                {
                    this.field_70789_a = null;
                }
                else
                {
                    this.field_70789_a = ((org.bukkit.craftbukkit.entity.CraftEntity) event.getTarget()).getHandle();
                }

                return;
            }

            // CraftBukkit end
        }
        else
        {
            if (p_70785_2_ > 2.0F && p_70785_2_ < 6.0F && this.field_70146_Z.nextInt(10) == 0)
            {
                if (this.field_70122_E)
                {
                    double d0 = p_70785_1_.field_70165_t - this.field_70165_t;
                    double d1 = p_70785_1_.field_70161_v - this.field_70161_v;
                    float f2 = MathHelper.func_76133_a(d0 * d0 + d1 * d1);
                    this.field_70159_w = d0 / (double)f2 * 0.5D * 0.800000011920929D + this.field_70159_w * 0.20000000298023224D;
                    this.field_70179_y = d1 / (double)f2 * 0.5D * 0.800000011920929D + this.field_70179_y * 0.20000000298023224D;
                    this.field_70181_x = 0.4000000059604645D;
                }
            }
            else
            {
                super.func_70785_a(p_70785_1_, p_70785_2_);
            }
        }
    }

    protected int func_70633_aT()
    {
        return Item.field_77683_K.field_77779_bT;
    }

    protected void func_70628_a(boolean p_70628_1_, int p_70628_2_)
    {
        // CraftBukkit start - whole method; adapted from super.dropDeathLoot.
        java.util.List<org.bukkit.inventory.ItemStack> loot = new java.util.ArrayList<org.bukkit.inventory.ItemStack>();
        int k = this.field_70146_Z.nextInt(3);

        if (p_70628_2_ > 0)
        {
            k += this.field_70146_Z.nextInt(p_70628_2_ + 1);
        }

        if (k > 0)
        {
            loot.add(new org.bukkit.inventory.ItemStack(Item.field_77683_K.field_77779_bT, k));
        }

        if (p_70628_1_ && (this.field_70146_Z.nextInt(3) == 0 || this.field_70146_Z.nextInt(1 + p_70628_2_) > 0))
        {
            loot.add(new org.bukkit.inventory.ItemStack(Item.field_77728_bu.field_77779_bT, 1));
        }

        org.bukkit.craftbukkit.event.CraftEventFactory.callEntityDeathEvent(this, loot); // raise event even for those times when the entity does not drop loot
        // CraftBukkit end
    }

    public boolean func_70617_f_()
    {
        return this.func_70841_p();
    }

    public void func_70110_aj() {}

    public EnumCreatureAttribute func_70668_bt()
    {
        return EnumCreatureAttribute.ARTHROPOD;
    }

    public boolean func_70687_e(PotionEffect p_70687_1_)
    {
        return p_70687_1_.func_76456_a() == Potion.field_76436_u.field_76415_H ? false : super.func_70687_e(p_70687_1_);
    }

    public boolean func_70841_p()
    {
        return (this.field_70180_af.func_75683_a(16) & 1) != 0;
    }

    public void func_70839_e(boolean p_70839_1_)
    {
        byte b0 = this.field_70180_af.func_75683_a(16);

        if (p_70839_1_)
        {
            b0 = (byte)(b0 | 1);
        }
        else
        {
            b0 &= -2;
        }

        this.field_70180_af.func_75692_b(16, Byte.valueOf(b0));
    }

    public void func_82163_bD()
    {
        if (this.field_70170_p.field_73012_v.nextInt(100) == 0)
        {
            EntitySkeleton entityskeleton = new EntitySkeleton(this.field_70170_p);
            entityskeleton.func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, 0.0F);
            entityskeleton.func_82163_bD();
            this.field_70170_p.func_72838_d(entityskeleton);
            entityskeleton.func_70078_a(this);
        }
    }
}
