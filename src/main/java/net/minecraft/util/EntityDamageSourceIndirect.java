package net.minecraft.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
public class EntityDamageSourceIndirect extends EntityDamageSource
{
    private Entity field_76387_p;

    public EntityDamageSourceIndirect(String p_i3431_1_, Entity p_i3431_2_, Entity p_i3431_3_)
    {
        super(p_i3431_1_, p_i3431_2_);
        this.field_76387_p = p_i3431_3_;
    }

    public Entity func_76364_f()
    {
        return this.field_76386_o;
    }

    public Entity func_76346_g()
    {
        return this.field_76387_p;
    }

    public String func_76360_b(EntityLiving p_76360_1_)
    {
        String s = this.field_76387_p == null ? this.field_76386_o.func_96090_ax() : this.field_76387_p.func_96090_ax();
        ItemStack itemstack = this.field_76387_p instanceof EntityLiving ? ((EntityLiving)this.field_76387_p).func_70694_bm() : null;
        String s1 = "death.attack." + this.field_76373_n;
        String s2 = s1 + ".item";
        return itemstack != null && itemstack.func_82837_s() && StatCollector.func_94522_b(s2) ? StatCollector.func_74837_a(s2, new Object[] {p_76360_1_.func_96090_ax(), s, itemstack.func_82833_r()}): StatCollector.func_74837_a(s1, new Object[] {p_76360_1_.func_96090_ax(), s});
    }

    // CraftBukkit start
    public Entity getProximateDamageSource()
    {
        return super.func_76346_g();
    }
    // CraftBukkit end
}
