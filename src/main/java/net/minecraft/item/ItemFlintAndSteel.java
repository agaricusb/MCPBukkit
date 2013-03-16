package net.minecraft.item;

// CraftBukkit start
import org.bukkit.craftbukkit.block.CraftBlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockIgniteEvent;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
// CraftBukkit end

public class ItemFlintAndSteel extends Item
{
    public ItemFlintAndSteel(int p_i3652_1_)
    {
        super(p_i3652_1_);
        this.field_77777_bU = 1;
        this.func_77656_e(64);
        this.func_77637_a(CreativeTabs.field_78040_i);
    }

    public boolean func_77648_a(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
        int clickedX = p_77648_4_, clickedY = p_77648_5_, clickedZ = p_77648_6_; // CraftBukkit

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
                // CraftBukkit start - store the clicked block
                org.bukkit.block.Block blockClicked = p_77648_3_.getWorld().getBlockAt(p_77648_4_, p_77648_5_, p_77648_6_);
                Player thePlayer = (Player) p_77648_2_.getBukkitEntity();
                BlockIgniteEvent eventIgnite = new BlockIgniteEvent(blockClicked, BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL, thePlayer);
                p_77648_3_.getServer().getPluginManager().callEvent(eventIgnite);

                if (eventIgnite.isCancelled())
                {
                    p_77648_1_.func_77972_a(1, p_77648_2_);
                    return false;
                }

                CraftBlockState blockState = CraftBlockState.getBlockState(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_);
                // CraftBukkit end
                p_77648_3_.func_72908_a((double)p_77648_4_ + 0.5D, (double)p_77648_5_ + 0.5D, (double)p_77648_6_ + 0.5D, "fire.ignite", 1.0F, field_77697_d.nextFloat() * 0.4F + 0.8F);
                p_77648_3_.func_94575_c(p_77648_4_, p_77648_5_, p_77648_6_, Block.field_72067_ar.field_71990_ca);
                // CraftBukkit start
                org.bukkit.event.block.BlockPlaceEvent placeEvent = org.bukkit.craftbukkit.event.CraftEventFactory.callBlockPlaceEvent(p_77648_3_, p_77648_2_, blockState, clickedX, clickedY, clickedZ);

                if (placeEvent.isCancelled() || !placeEvent.canBuild())
                {
                    placeEvent.getBlockPlaced().setTypeIdAndData(0, (byte) 0, false);
                    return false;
                }

                // CraftBukkit end
            }

            p_77648_1_.func_77972_a(1, p_77648_2_);
            return true;
        }
    }
}
