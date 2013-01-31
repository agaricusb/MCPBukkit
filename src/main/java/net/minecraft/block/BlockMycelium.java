package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.World;

// CraftBukkit start
import org.bukkit.block.BlockState;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockSpreadEvent;
// CraftBukkit end

public class BlockMycelium extends Block
{
    protected BlockMycelium(int p_i3973_1_)
    {
        super(p_i3973_1_, Material.field_76247_b);
        this.field_72059_bZ = 77;
        this.func_71907_b(true);
        this.func_71849_a(CreativeTabs.field_78030_b);
    }

    public int func_71858_a(int p_71858_1_, int p_71858_2_)
    {
        return p_71858_1_ == 1 ? 78 : (p_71858_1_ == 0 ? 2 : 77);
    }

    public void func_71847_b(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
    {
        if (!p_71847_1_.field_72995_K)
        {
            if (p_71847_1_.func_72957_l(p_71847_2_, p_71847_3_ + 1, p_71847_4_) < 4 && Block.field_71971_o[p_71847_1_.func_72798_a(p_71847_2_, p_71847_3_ + 1, p_71847_4_)] > 2)
            {
                // CraftBukkit start
                org.bukkit.World bworld = p_71847_1_.getWorld();
                BlockState blockState = bworld.getBlockAt(p_71847_2_, p_71847_3_, p_71847_4_).getState();
                blockState.setTypeId(Block.field_71979_v.field_71990_ca);
                BlockFadeEvent event = new BlockFadeEvent(blockState.getBlock(), blockState);
                p_71847_1_.getServer().getPluginManager().callEvent(event);

                if (!event.isCancelled())
                {
                    blockState.update(true);
                }

                // CraftBukkit end
            }
            else if (p_71847_1_.func_72957_l(p_71847_2_, p_71847_3_ + 1, p_71847_4_) >= 9)
            {
                for (int l = 0; l < 4; ++l)
                {
                    int i1 = p_71847_2_ + p_71847_5_.nextInt(3) - 1;
                    int j1 = p_71847_3_ + p_71847_5_.nextInt(5) - 3;
                    int k1 = p_71847_4_ + p_71847_5_.nextInt(3) - 1;
                    int l1 = p_71847_1_.func_72798_a(i1, j1 + 1, k1);

                    if (p_71847_1_.func_72798_a(i1, j1, k1) == Block.field_71979_v.field_71990_ca && p_71847_1_.func_72957_l(i1, j1 + 1, k1) >= 4 && Block.field_71971_o[l1] <= 2)
                    {
                        // CraftBukkit start
                        org.bukkit.World bworld = p_71847_1_.getWorld();
                        BlockState blockState = bworld.getBlockAt(i1, j1, k1).getState();
                        blockState.setTypeId(this.field_71990_ca);
                        BlockSpreadEvent event = new BlockSpreadEvent(blockState.getBlock(), bworld.getBlockAt(p_71847_2_, p_71847_3_, p_71847_4_), blockState);
                        p_71847_1_.getServer().getPluginManager().callEvent(event);

                        if (!event.isCancelled())
                        {
                            blockState.update(true);
                        }

                        // CraftBukkit end
                    }
                }
            }
        }
    }

    public int func_71885_a(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
    {
        return Block.field_71979_v.func_71885_a(0, p_71885_2_, p_71885_3_);
    }
}
