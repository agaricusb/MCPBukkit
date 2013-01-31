package net.minecraft.block;

import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;

// CraftBukkit start
import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.TreeType;
import org.bukkit.block.BlockState;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.world.StructureGrowEvent;
// CraftBukkit end

public class BlockMushroom extends BlockFlower
{
    protected BlockMushroom(int p_i3971_1_, int p_i3971_2_)
    {
        super(p_i3971_1_, p_i3971_2_);
        float f = 0.2F;
        this.func_71905_a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
        this.func_71907_b(true);
    }

    public void func_71847_b(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
    {
        final int sourceX = p_71847_2_, sourceY = p_71847_3_, sourceZ = p_71847_4_; // CraftBukkit

        if (p_71847_5_.nextInt(25) == 0)
        {
            byte b0 = 4;
            int l = 5;
            int i1;
            int j1;
            int k1;

            for (i1 = p_71847_2_ - b0; i1 <= p_71847_2_ + b0; ++i1)
            {
                for (j1 = p_71847_4_ - b0; j1 <= p_71847_4_ + b0; ++j1)
                {
                    for (k1 = p_71847_3_ - 1; k1 <= p_71847_3_ + 1; ++k1)
                    {
                        if (p_71847_1_.func_72798_a(i1, k1, j1) == this.field_71990_ca)
                        {
                            --l;

                            if (l <= 0)
                            {
                                return;
                            }
                        }
                    }
                }
            }

            i1 = p_71847_2_ + p_71847_5_.nextInt(3) - 1;
            j1 = p_71847_3_ + p_71847_5_.nextInt(2) - p_71847_5_.nextInt(2);
            k1 = p_71847_4_ + p_71847_5_.nextInt(3) - 1;

            for (int l1 = 0; l1 < 4; ++l1)
            {
                if (p_71847_1_.func_72799_c(i1, j1, k1) && this.func_71854_d(p_71847_1_, i1, j1, k1))
                {
                    p_71847_2_ = i1;
                    p_71847_3_ = j1;
                    p_71847_4_ = k1;
                }

                i1 = p_71847_2_ + p_71847_5_.nextInt(3) - 1;
                j1 = p_71847_3_ + p_71847_5_.nextInt(2) - p_71847_5_.nextInt(2);
                k1 = p_71847_4_ + p_71847_5_.nextInt(3) - 1;
            }

            if (p_71847_1_.func_72799_c(i1, j1, k1) && this.func_71854_d(p_71847_1_, i1, j1, k1))
            {
                // CraftBukkit start
                org.bukkit.World bworld = p_71847_1_.getWorld();
                BlockState blockState = bworld.getBlockAt(i1, j1, k1).getState();
                blockState.setTypeId(this.field_71990_ca);
                BlockSpreadEvent event = new BlockSpreadEvent(blockState.getBlock(), bworld.getBlockAt(sourceX, sourceY, sourceZ), blockState);
                p_71847_1_.getServer().getPluginManager().callEvent(event);

                if (!event.isCancelled())
                {
                    blockState.update(true);
                }

                // CraftBukkit end
            }
        }
    }

    public boolean func_71930_b(World p_71930_1_, int p_71930_2_, int p_71930_3_, int p_71930_4_)
    {
        return super.func_71930_b(p_71930_1_, p_71930_2_, p_71930_3_, p_71930_4_) && this.func_71854_d(p_71930_1_, p_71930_2_, p_71930_3_, p_71930_4_);
    }

    protected boolean func_72263_d_(int p_72263_1_)
    {
        return Block.field_71970_n[p_72263_1_];
    }

    public boolean func_71854_d(World p_71854_1_, int p_71854_2_, int p_71854_3_, int p_71854_4_)
    {
        if (p_71854_3_ >= 0 && p_71854_3_ < 256)
        {
            int l = p_71854_1_.func_72798_a(p_71854_2_, p_71854_3_ - 1, p_71854_4_);
            return l == Block.field_71994_by.field_71990_ca || p_71854_1_.func_72883_k(p_71854_2_, p_71854_3_, p_71854_4_) < 13 && this.func_72263_d_(l);
        }
        else
        {
            return false;
        }
    }

    // CraftBukkit - added bonemeal, player and itemstack
    public boolean func_72271_c(World p_72271_1_, int p_72271_2_, int p_72271_3_, int p_72271_4_, Random p_72271_5_, boolean bonemeal, org.bukkit.entity.Player player, ItemStack itemstack)
    {
        int l = p_72271_1_.func_72805_g(p_72271_2_, p_72271_3_, p_72271_4_);
        p_72271_1_.func_72822_b(p_72271_2_, p_72271_3_, p_72271_4_, 0);
        // CraftBukkit start
        boolean grown = false;
        StructureGrowEvent event = null;
        Location location = new Location(p_72271_1_.getWorld(), p_72271_2_, p_72271_3_, p_72271_4_);
        WorldGenBigMushroom worldgenbigmushroom = null;

        if (this.field_71990_ca == Block.field_72109_af.field_71990_ca)
        {
            event = new StructureGrowEvent(location, TreeType.BROWN_MUSHROOM, bonemeal, player, new ArrayList<BlockState>());
            worldgenbigmushroom = new WorldGenBigMushroom(0);
        }
        else if (this.field_71990_ca == Block.field_72103_ag.field_71990_ca)
        {
            event = new StructureGrowEvent(location, TreeType.RED_MUSHROOM, bonemeal, player, new ArrayList<BlockState>());
            worldgenbigmushroom = new WorldGenBigMushroom(1);
        }

        if (worldgenbigmushroom != null && event != null)
        {
            grown = worldgenbigmushroom.grow((org.bukkit.BlockChangeDelegate)p_72271_1_, p_72271_5_, p_72271_2_, p_72271_3_, p_72271_4_, event, itemstack, p_72271_1_.getWorld());

            if (event.isFromBonemeal() && itemstack != null)
            {
                --itemstack.field_77994_a;
            }
        }

        if (!grown || event.isCancelled())
        {
            p_72271_1_.func_72961_c(p_72271_2_, p_72271_3_, p_72271_4_, this.field_71990_ca, l);
            return false;
        }

        return true;
        // CraftBukkit end
    }
}
