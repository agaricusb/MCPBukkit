package net.minecraft.item;

// CraftBukkit start
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockIgniteEvent;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
// CraftBukkit end

public class ItemFireball extends Item
{
    public ItemFireball(int p_i3650_1_)
    {
        super(p_i3650_1_);
        this.func_77637_a(CreativeTabs.field_78026_f);
    }

    public boolean func_77648_a(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
        if (p_77648_3_.field_72995_K)
        {
            return true;
        }
        else
        {
            if (p_77648_7_ == 0)
            {
                --p_77648_5_;
            }

            if (p_77648_7_ == 1)
            {
                ++p_77648_5_;
            }

            if (p_77648_7_ == 2)
            {
                --p_77648_6_;
            }

            if (p_77648_7_ == 3)
            {
                ++p_77648_6_;
            }

            if (p_77648_7_ == 4)
            {
                --p_77648_4_;
            }

            if (p_77648_7_ == 5)
            {
                ++p_77648_4_;
            }

            if (!p_77648_2_.func_82247_a(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_))
            {
                return false;
            }
            else
            {
                int i1 = p_77648_3_.func_72798_a(p_77648_4_, p_77648_5_, p_77648_6_);

                if (i1 == 0)
                {
                    // CraftBukkit start
                    org.bukkit.block.Block blockClicked = p_77648_3_.getWorld().getBlockAt(p_77648_4_, p_77648_5_, p_77648_6_);
                    Player thePlayer = (Player) p_77648_2_.getBukkitEntity();
                    BlockIgniteEvent eventIgnite = new BlockIgniteEvent(blockClicked, BlockIgniteEvent.IgniteCause.FIREBALL, thePlayer);
                    p_77648_3_.getServer().getPluginManager().callEvent(eventIgnite);

                    if (eventIgnite.isCancelled())
                    {
                        if (!p_77648_2_.field_71075_bZ.field_75098_d)
                        {
                            --p_77648_1_.field_77994_a;
                        }

                        return false;
                    }

                    // CraftBukkit end
                    p_77648_3_.func_72908_a((double)p_77648_4_ + 0.5D, (double)p_77648_5_ + 0.5D, (double)p_77648_6_ + 0.5D, "fire.ignite", 1.0F, field_77697_d.nextFloat() * 0.4F + 0.8F);
                    p_77648_3_.func_72859_e(p_77648_4_, p_77648_5_, p_77648_6_, Block.field_72067_ar.field_71990_ca);
                }

                if (!p_77648_2_.field_71075_bZ.field_75098_d)
                {
                    --p_77648_1_.field_77994_a;
                }

                return true;
            }
        }
    }
}
