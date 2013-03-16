package net.minecraft.entity.ai;

// CraftBukkit start
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.Material;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
// CraftBukkit end

public class EntityAIEatGrass extends EntityAIBase
{
    private EntityLiving field_75363_b;
    private World field_75364_c;
    int field_75365_a = 0;

    public EntityAIEatGrass(EntityLiving p_i3463_1_)
    {
        this.field_75363_b = p_i3463_1_;
        this.field_75364_c = p_i3463_1_.field_70170_p;
        this.func_75248_a(7);
    }

    public boolean func_75250_a()
    {
        if (this.field_75363_b.func_70681_au().nextInt(this.field_75363_b.func_70631_g_() ? 50 : 1000) != 0)
        {
            return false;
        }
        else
        {
            int i = MathHelper.func_76128_c(this.field_75363_b.field_70165_t);
            int j = MathHelper.func_76128_c(this.field_75363_b.field_70163_u);
            int k = MathHelper.func_76128_c(this.field_75363_b.field_70161_v);
            return this.field_75364_c.func_72798_a(i, j, k) == Block.field_71962_X.field_71990_ca && this.field_75364_c.func_72805_g(i, j, k) == 1 ? true : this.field_75364_c.func_72798_a(i, j - 1, k) == Block.field_71980_u.field_71990_ca;
        }
    }

    public void func_75249_e()
    {
        this.field_75365_a = 40;
        this.field_75364_c.func_72960_a(this.field_75363_b, (byte)10);
        this.field_75363_b.func_70661_as().func_75499_g();
    }

    public void func_75251_c()
    {
        this.field_75365_a = 0;
    }

    public boolean func_75253_b()
    {
        return this.field_75365_a > 0;
    }

    public int func_75362_f()
    {
        return this.field_75365_a;
    }

    public void func_75246_d()
    {
        this.field_75365_a = Math.max(0, this.field_75365_a - 1);

        if (this.field_75365_a == 4)
        {
            int i = MathHelper.func_76128_c(this.field_75363_b.field_70165_t);
            int j = MathHelper.func_76128_c(this.field_75363_b.field_70163_u);
            int k = MathHelper.func_76128_c(this.field_75363_b.field_70161_v);

            if (this.field_75364_c.func_72798_a(i, j, k) == Block.field_71962_X.field_71990_ca)
            {
                // CraftBukkit start
                if (!CraftEventFactory.callEntityChangeBlockEvent(this.field_75363_b.getBukkitEntity(), this.field_75363_b.field_70170_p.getWorld().getBlockAt(i, j, k), Material.AIR).isCancelled())
                {
                    this.field_75364_c.func_94578_a(i, j, k, false);
                    this.field_75363_b.func_70615_aA();
                }

                // CraftBukkit end
            }
            else if (this.field_75364_c.func_72798_a(i, j - 1, k) == Block.field_71980_u.field_71990_ca)
            {
                // CraftBukkit start
                if (!CraftEventFactory.callEntityChangeBlockEvent(this.field_75363_b.getBukkitEntity(), this.field_75363_b.field_70170_p.getWorld().getBlockAt(i, j - 1, k), Material.DIRT).isCancelled())
                {
                    this.field_75364_c.func_72926_e(2001, i, j - 1, k, Block.field_71980_u.field_71990_ca);
                    this.field_75364_c.func_72832_d(i, j - 1, k, Block.field_71979_v.field_71990_ca, 0, 2);
                    this.field_75363_b.func_70615_aA();
                }

                // CraftBukkit end
            }
        }
    }
}
