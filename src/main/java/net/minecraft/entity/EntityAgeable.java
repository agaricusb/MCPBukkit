package net.minecraft.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
public abstract class EntityAgeable extends EntityCreature
{
    public boolean ageLocked = false; // CraftBukkit

    public EntityAgeable(World p_i3436_1_)
    {
        super(p_i3436_1_);
    }

    public abstract EntityAgeable func_90011_a(EntityAgeable entityageable);

    public boolean func_70085_c(EntityPlayer p_70085_1_)
    {
        ItemStack itemstack = p_70085_1_.field_71071_by.func_70448_g();

        if (itemstack != null && itemstack.field_77993_c == Item.field_77815_bC.field_77779_bT && !this.field_70170_p.field_72995_K)
        {
            Class oclass = EntityList.func_90035_a(itemstack.func_77960_j());

            if (oclass != null && oclass.isAssignableFrom(this.getClass()))
            {
                EntityAgeable entityageable = this.func_90011_a(this);

                if (entityageable != null)
                {
                    entityageable.func_70873_a(-24000);
                    entityageable.func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, 0.0F, 0.0F);
                    this.field_70170_p.addEntity(entityageable, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.SPAWNER_EGG); // CraftBukkit

                    if (!p_70085_1_.field_71075_bZ.field_75098_d)
                    {
                        --itemstack.field_77994_a;

                        if (itemstack.field_77994_a == 0)   // CraftBukkit - allow less than 0 stacks as "infinit"
                        {
                            p_70085_1_.field_71071_by.func_70299_a(p_70085_1_.field_71071_by.field_70461_c, (ItemStack)null);
                        }
                    }
                }
            }
        }

        return super.func_70085_c(p_70085_1_);
    }

    protected void func_70088_a()
    {
        super.func_70088_a();
        this.field_70180_af.func_75682_a(12, new Integer(0));
    }

    public int func_70874_b()
    {
        return this.field_70180_af.func_75679_c(12);
    }

    public void func_70873_a(int p_70873_1_)
    {
        this.field_70180_af.func_75692_b(12, Integer.valueOf(p_70873_1_));
    }

    public void func_70014_b(NBTTagCompound p_70014_1_)
    {
        super.func_70014_b(p_70014_1_);
        p_70014_1_.func_74768_a("Age", this.func_70874_b());
        p_70014_1_.func_74757_a("AgeLocked", this.ageLocked); // CraftBukkit
    }

    public void func_70037_a(NBTTagCompound p_70037_1_)
    {
        super.func_70037_a(p_70037_1_);
        this.func_70873_a(p_70037_1_.func_74762_e("Age"));
        this.ageLocked = p_70037_1_.func_74767_n("AgeLocked"); // CraftBukkit
    }

    public void func_70636_d()
    {
        super.func_70636_d();
        int i = this.func_70874_b();

        if (ageLocked)
        {
            return;    // CraftBukkit
        }

        if (i < 0)
        {
            ++i;
            this.func_70873_a(i);
        }
        else if (i > 0)
        {
            --i;
            this.func_70873_a(i);
        }
    }

    public boolean func_70631_g_()
    {
        return this.func_70874_b() < 0;
    }
}
