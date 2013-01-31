package net.minecraft.entity.passive;

import org.bukkit.event.player.PlayerShearEntityEvent; // CraftBukkit
import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityMooshroom extends EntityCow
{
    public EntityMooshroom(World p_i3518_1_)
    {
        super(p_i3518_1_);
        this.field_70750_az = "/mob/redcow.png";
        this.func_70105_a(0.9F, 1.3F);
    }

    public boolean func_70085_c(EntityPlayer p_70085_1_)
    {
        ItemStack itemstack = p_70085_1_.field_71071_by.func_70448_g();

        if (itemstack != null && itemstack.field_77993_c == Item.field_77670_E.field_77779_bT && this.func_70874_b() >= 0)
        {
            if (itemstack.field_77994_a == 1)
            {
                p_70085_1_.field_71071_by.func_70299_a(p_70085_1_.field_71071_by.field_70461_c, new ItemStack(Item.field_77671_F));
                return true;
            }

            if (p_70085_1_.field_71071_by.func_70441_a(new ItemStack(Item.field_77671_F)) && !p_70085_1_.field_71075_bZ.field_75098_d)
            {
                p_70085_1_.field_71071_by.func_70298_a(p_70085_1_.field_71071_by.field_70461_c, 1);
                return true;
            }
        }

        if (itemstack != null && itemstack.field_77993_c == Item.field_77745_be.field_77779_bT && this.func_70874_b() >= 0)
        {
            // CraftBukkit start
            PlayerShearEntityEvent event = new PlayerShearEntityEvent((org.bukkit.entity.Player) p_70085_1_.getBukkitEntity(), this.getBukkitEntity());
            this.field_70170_p.getServer().getPluginManager().callEvent(event);

            if (event.isCancelled())
            {
                return false;
            }

            // CraftBukkit end
            this.func_70106_y();
            this.field_70170_p.func_72869_a("largeexplode", this.field_70165_t, this.field_70163_u + (double)(this.field_70131_O / 2.0F), this.field_70161_v, 0.0D, 0.0D, 0.0D);

            if (!this.field_70170_p.field_72995_K)
            {
                EntityCow entitycow = new EntityCow(this.field_70170_p);
                entitycow.func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, this.field_70125_A);
                entitycow.func_70606_j(this.func_70630_aN());
                entitycow.field_70761_aq = this.field_70761_aq;
                this.field_70170_p.func_72838_d(entitycow);

                for (int i = 0; i < 5; ++i)
                {
                    this.field_70170_p.func_72838_d(new EntityItem(this.field_70170_p, this.field_70165_t, this.field_70163_u + (double)this.field_70131_O, this.field_70161_v, new ItemStack(Block.field_72103_ag)));
                }
            }

            return true;
        }
        else
        {
            return super.func_70085_c(p_70085_1_);
        }
    }

    public EntityMooshroom func_70879_a(EntityAgeable p_70879_1_)
    {
        return new EntityMooshroom(this.field_70170_p);
    }

    /*
public EntityCow func_70879_a(EntityAgeable p_70879_1_){
  return this.func_70879_a(p_70879_1_);
}
*/

    public EntityAgeable func_90011_a(EntityAgeable p_90011_1_)
    {
        return this.func_70879_a(p_90011_1_);
    }
}