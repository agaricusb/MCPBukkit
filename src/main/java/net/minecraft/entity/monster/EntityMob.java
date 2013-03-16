package net.minecraft.entity.monster;

import org.bukkit.event.entity.EntityTargetEvent; // CraftBukkit
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentThorns;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public abstract class EntityMob extends EntityCreature implements IMob
{
    public EntityMob(World p_i3552_1_)
    {
        super(p_i3552_1_);
        this.field_70728_aV = 5;
    }

    public void func_70636_d()
    {
        this.func_82168_bl();
        float f = this.func_70013_c(1.0F);

        if (f > 0.5F)
        {
            this.field_70708_bq += 2;
        }

        super.func_70636_d();
    }

    public void func_70071_h_()
    {
        super.func_70071_h_();

        if (!this.field_70170_p.field_72995_K && this.field_70170_p.field_73013_u == 0)
        {
            this.func_70106_y();
        }
    }

    protected Entity func_70782_k()
    {
        EntityPlayer entityplayer = this.field_70170_p.func_72856_b(this, 16.0D);
        return entityplayer != null && this.func_70685_l(entityplayer) ? entityplayer : null;
    }

    public boolean func_70097_a(DamageSource p_70097_1_, int p_70097_2_)
    {
        if (this.func_85032_ar())
        {
            return false;
        }
        else if (super.func_70097_a(p_70097_1_, p_70097_2_))
        {
            Entity entity = p_70097_1_.func_76346_g();

            if (this.field_70153_n != entity && this.field_70154_o != entity)
            {
                if (entity != this)
                {
                    // CraftBukkit start - we still need to call events for entities without goals
                    if (entity != this.field_70789_a && (this instanceof EntityBlaze || this instanceof EntityEnderman || this instanceof EntitySpider || this instanceof EntityGiantZombie || this instanceof EntitySilverfish))
                    {
                        EntityTargetEvent event = org.bukkit.craftbukkit.event.CraftEventFactory.callEntityTargetEvent(this, entity, EntityTargetEvent.TargetReason.TARGET_ATTACKED_ENTITY);

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
                        }
                    }
                    else
                    {
                        this.field_70789_a = entity;
                    }

                    // CraftBukkit end
                }

                return true;
            }
            else
            {
                return true;
            }
        }
        else
        {
            return false;
        }
    }

    public boolean func_70652_k(Entity p_70652_1_)
    {
        int i = this.func_82193_c(p_70652_1_);

        if (this.func_70644_a(Potion.field_76420_g))
        {
            i += 3 << this.func_70660_b(Potion.field_76420_g).func_76458_c();
        }

        if (this.func_70644_a(Potion.field_76437_t))
        {
            i -= 2 << this.func_70660_b(Potion.field_76437_t).func_76458_c();
        }

        int j = 0;

        if (p_70652_1_ instanceof EntityLiving)
        {
            i += EnchantmentHelper.func_77512_a((EntityLiving) this, (EntityLiving) p_70652_1_);
            j += EnchantmentHelper.func_77507_b(this, (EntityLiving)p_70652_1_);
        }

        boolean flag = p_70652_1_.func_70097_a(DamageSource.func_76358_a(this), i);

        if (flag)
        {
            if (j > 0)
            {
                p_70652_1_.func_70024_g((double)(-MathHelper.func_76126_a(this.field_70177_z * (float)Math.PI / 180.0F) * (float)j * 0.5F), 0.1D, (double)(MathHelper.func_76134_b(this.field_70177_z * (float)Math.PI / 180.0F) * (float)j * 0.5F));
                this.field_70159_w *= 0.6D;
                this.field_70179_y *= 0.6D;
            }

            int k = EnchantmentHelper.func_90036_a(this);

            if (k > 0)
            {
                p_70652_1_.func_70015_d(k * 4);
            }

            if (p_70652_1_ instanceof EntityLiving)
            {
                EnchantmentThorns.func_92096_a(this, (EntityLiving)p_70652_1_, this.field_70146_Z);
            }
        }

        return flag;
    }

    protected void func_70785_a(Entity p_70785_1_, float p_70785_2_)
    {
        if (this.field_70724_aR <= 0 && p_70785_2_ < 2.0F && p_70785_1_.field_70121_D.field_72337_e > this.field_70121_D.field_72338_b && p_70785_1_.field_70121_D.field_72338_b < this.field_70121_D.field_72337_e)
        {
            this.field_70724_aR = 20;
            this.func_70652_k(p_70785_1_);
        }
    }

    public float func_70783_a(int p_70783_1_, int p_70783_2_, int p_70783_3_)
    {
        return 0.5F - this.field_70170_p.func_72801_o(p_70783_1_, p_70783_2_, p_70783_3_);
    }

    protected boolean func_70814_o()
    {
        int i = MathHelper.func_76128_c(this.field_70165_t);
        int j = MathHelper.func_76128_c(this.field_70121_D.field_72338_b);
        int k = MathHelper.func_76128_c(this.field_70161_v);

        if (this.field_70170_p.func_72972_b(EnumSkyBlock.Sky, i, j, k) > this.field_70146_Z.nextInt(32))
        {
            return false;
        }
        else
        {
            int l = this.field_70170_p.func_72957_l(i, j, k);

            if (this.field_70170_p.func_72911_I())
            {
                int i1 = this.field_70170_p.field_73008_k;
                this.field_70170_p.field_73008_k = 10;
                l = this.field_70170_p.func_72957_l(i, j, k);
                this.field_70170_p.field_73008_k = i1;
            }

            return l <= this.field_70146_Z.nextInt(8);
        }
    }

    public boolean func_70601_bi()
    {
        return this.func_70814_o() && super.func_70601_bi();
    }

    public int func_82193_c(Entity p_82193_1_)
    {
        return 2;
    }
}
