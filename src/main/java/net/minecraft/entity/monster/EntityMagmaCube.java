package net.minecraft.entity.monster;

import net.minecraft.item.Item;
import net.minecraft.world.World;
public class EntityMagmaCube extends EntitySlime
{
    public EntityMagmaCube(World p_i3551_1_)
    {
        super(p_i3551_1_);
        this.field_70750_az = "/mob/lava.png";
        this.field_70178_ae = true;
        this.field_70746_aG = 0.2F;
    }

    public boolean func_70601_bi()
    {
        return this.field_70170_p.field_73013_u > 0 && this.field_70170_p.func_72855_b(this.field_70121_D) && this.field_70170_p.func_72945_a(this, this.field_70121_D).isEmpty() && !this.field_70170_p.func_72953_d(this.field_70121_D);
    }

    public int func_70658_aO()
    {
        return this.func_70809_q() * 3;
    }

    public float func_70013_c(float p_70013_1_)
    {
        return 1.0F;
    }

    protected String func_70801_i()
    {
        return "flame";
    }

    protected EntitySlime func_70802_j()
    {
        return new EntityMagmaCube(this.field_70170_p);
    }

    protected int func_70633_aT()
    {
        return Item.field_77725_bx.field_77779_bT;
    }

    protected void func_70628_a(boolean p_70628_1_, int p_70628_2_)
    {
        // CraftBukkit start - whole method
        java.util.List<org.bukkit.inventory.ItemStack> loot = new java.util.ArrayList<org.bukkit.inventory.ItemStack>();
        int j = this.func_70633_aT();

        if (j > 0 && this.func_70809_q() > 1)
        {
            int k = this.field_70146_Z.nextInt(4) - 2;

            if (p_70628_2_ > 0)
            {
                k += this.field_70146_Z.nextInt(p_70628_2_ + 1);
            }

            if (k > 0)
            {
                loot.add(new org.bukkit.inventory.ItemStack(j, k));
            }
        }

        org.bukkit.craftbukkit.event.CraftEventFactory.callEntityDeathEvent(this, loot);
        // CraftBukkit end
    }

    public boolean func_70027_ad()
    {
        return false;
    }

    protected int func_70806_k()
    {
        return super.func_70806_k() * 4;
    }

    protected void func_70808_l()
    {
        this.field_70813_a *= 0.9F;
    }

    protected void func_70664_aZ()
    {
        this.field_70181_x = (double)(0.42F + (float)this.func_70809_q() * 0.1F);
        this.field_70160_al = true;
    }

    protected void func_70069_a(float p_70069_1_) {}

    protected boolean func_70800_m()
    {
        return true;
    }

    protected int func_70805_n()
    {
        return super.func_70805_n() + 2;
    }

    protected String func_70621_aR()
    {
        return "mob.slime." + (this.func_70809_q() > 1 ? "big" : "small");
    }

    protected String func_70673_aS()
    {
        return "mob.slime." + (this.func_70809_q() > 1 ? "big" : "small");
    }

    protected String func_70803_o()
    {
        return this.func_70809_q() > 1 ? "mob.magmacube.big" : "mob.magmacube.small";
    }

    public boolean func_70058_J()
    {
        return false;
    }

    protected boolean func_70804_p()
    {
        return true;
    }
}
