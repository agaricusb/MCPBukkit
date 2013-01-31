package net.minecraft.item;

import org.bukkit.event.player.PlayerFishEvent; // CraftBukkit
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.world.World;

public class ItemFishingRod extends Item
{
    public ItemFishingRod(int p_i3651_1_)
    {
        super(p_i3651_1_);
        this.func_77656_e(64);
        this.func_77625_d(1);
        this.func_77637_a(CreativeTabs.field_78040_i);
    }

    public ItemStack func_77659_a(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
    {
        if (p_77659_3_.field_71104_cf != null)
        {
            int i = p_77659_3_.field_71104_cf.func_70198_d();
            p_77659_1_.func_77972_a(i, p_77659_3_);
            p_77659_3_.func_71038_i();
        }
        else
        {
            // CraftBukkit start
            PlayerFishEvent playerFishEvent = new PlayerFishEvent((org.bukkit.entity.Player) p_77659_3_.getBukkitEntity(), null, PlayerFishEvent.State.FISHING);
            p_77659_2_.getServer().getPluginManager().callEvent(playerFishEvent);

            if (playerFishEvent.isCancelled())
            {
                return p_77659_1_;
            }

            // CraftBukkit end
            p_77659_2_.func_72956_a(p_77659_3_, "random.bow", 0.5F, 0.4F / (field_77697_d.nextFloat() * 0.4F + 0.8F));

            if (!p_77659_2_.field_72995_K)
            {
                p_77659_2_.func_72838_d(new EntityFishHook(p_77659_2_, p_77659_3_));
            }

            p_77659_3_.func_71038_i();
        }

        return p_77659_1_;
    }
}
