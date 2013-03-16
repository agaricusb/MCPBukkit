package net.minecraft.util;

import org.bukkit.event.entity.EntityDamageEvent; // CraftBukkit
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemFood;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet8UpdateHealth;

public class FoodStats
{
    // CraftBukkit start - all made public
    public int field_75127_a = 20;
    public float field_75125_b = 5.0F;
    public float field_75126_c;
    public int field_75123_d = 0;
    // CraftBukkit end
    private int field_75124_e = 20;

    public FoodStats() {}

    public void func_75122_a(int p_75122_1_, float p_75122_2_)
    {
        this.field_75127_a = Math.min(p_75122_1_ + this.field_75127_a, 20);
        this.field_75125_b = Math.min(this.field_75125_b + (float)p_75122_1_ * p_75122_2_ * 2.0F, (float)this.field_75127_a);
    }

    public void func_75111_a(ItemFood p_75111_1_)
    {
        this.func_75122_a(p_75111_1_.func_77847_f(), p_75111_1_.func_77846_g());
    }

    public void func_75118_a(EntityPlayer p_75118_1_)
    {
        int i = p_75118_1_.field_70170_p.field_73013_u;
        this.field_75124_e = this.field_75127_a;

        if (this.field_75126_c > 4.0F)
        {
            this.field_75126_c -= 4.0F;

            if (this.field_75125_b > 0.0F)
            {
                this.field_75125_b = Math.max(this.field_75125_b - 1.0F, 0.0F);
            }
            else if (i > 0)
            {
                // CraftBukkit start
                org.bukkit.event.entity.FoodLevelChangeEvent event = org.bukkit.craftbukkit.event.CraftEventFactory.callFoodLevelChangeEvent(p_75118_1_, Math.max(this.field_75127_a - 1, 0));

                if (!event.isCancelled())
                {
                    this.field_75127_a = event.getFoodLevel();
                }

                ((EntityPlayerMP) p_75118_1_).field_71135_a.func_72567_b(new Packet8UpdateHealth(p_75118_1_.func_70630_aN(), this.field_75127_a, this.field_75125_b));
                // CraftBukkit end
            }
        }

        if (this.field_75127_a >= 18 && p_75118_1_.func_70996_bM())
        {
            ++this.field_75123_d;

            if (this.field_75123_d >= 80)
            {
                // CraftBukkit - added RegainReason
                p_75118_1_.heal(1, org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason.SATIATED);
                this.field_75123_d = 0;
            }
        }
        else if (this.field_75127_a <= 0)
        {
            ++this.field_75123_d;

            if (this.field_75123_d >= 80)
            {
                if (p_75118_1_.func_70630_aN() > 10 || i >= 3 || p_75118_1_.func_70630_aN() > 1 && i >= 2)
                {
                    // CraftBukkit start
                    EntityDamageEvent event = new EntityDamageEvent(p_75118_1_.getBukkitEntity(), EntityDamageEvent.DamageCause.STARVATION, 1);
                    p_75118_1_.field_70170_p.getServer().getPluginManager().callEvent(event);

                    if (!event.isCancelled())
                    {
                        event.getEntity().setLastDamageCause(event);
                        p_75118_1_.func_70097_a(DamageSource.field_76366_f, event.getDamage());
                    }

                    // CraftBukkit end
                }

                this.field_75123_d = 0;
            }
        }
        else
        {
            this.field_75123_d = 0;
        }
    }

    public void func_75112_a(NBTTagCompound p_75112_1_)
    {
        if (p_75112_1_.func_74764_b("foodLevel"))
        {
            this.field_75127_a = p_75112_1_.func_74762_e("foodLevel");
            this.field_75123_d = p_75112_1_.func_74762_e("foodTickTimer");
            this.field_75125_b = p_75112_1_.func_74760_g("foodSaturationLevel");
            this.field_75126_c = p_75112_1_.func_74760_g("foodExhaustionLevel");
        }
    }

    public void func_75117_b(NBTTagCompound p_75117_1_)
    {
        p_75117_1_.func_74768_a("foodLevel", this.field_75127_a);
        p_75117_1_.func_74768_a("foodTickTimer", this.field_75123_d);
        p_75117_1_.func_74776_a("foodSaturationLevel", this.field_75125_b);
        p_75117_1_.func_74776_a("foodExhaustionLevel", this.field_75126_c);
    }

    public int func_75116_a()
    {
        return this.field_75127_a;
    }

    public boolean func_75121_c()
    {
        return this.field_75127_a < 20;
    }

    public void func_75113_a(float p_75113_1_)
    {
        this.field_75126_c = Math.min(this.field_75126_c + p_75113_1_, 40.0F);
    }

    public float func_75115_e()
    {
        return this.field_75125_b;
    }
}
