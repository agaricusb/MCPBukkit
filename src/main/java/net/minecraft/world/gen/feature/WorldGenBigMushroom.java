package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling.TreeGenerator;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

// CraftBukkit start
import org.bukkit.BlockChangeDelegate;
import org.bukkit.block.BlockState;
import org.bukkit.material.MaterialData;
// CraftBukkit end

public class WorldGenBigMushroom extends WorldGenerator implements TreeGenerator   // CraftBukkit - add interface
{
    private int field_76523_a = -1;

    public WorldGenBigMushroom(int p_i3793_1_)
    {
        super(true);
        this.field_76523_a = p_i3793_1_;
    }

    public WorldGenBigMushroom()
    {
        super(false);
    }

    public boolean func_76484_a(World p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_)
    {
        // CraftBukkit start - moved to generate
        return grow((BlockChangeDelegate) p_76484_1_, p_76484_2_, p_76484_3_, p_76484_4_, p_76484_5_, null, null, null);
    }

    public boolean generate(BlockChangeDelegate p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_)
    {
        return grow(p_76484_1_, p_76484_2_, p_76484_3_, p_76484_4_, p_76484_5_, null, null, null);
    }

    public boolean grow(BlockChangeDelegate world, Random random, int i, int j, int k, org.bukkit.event.world.StructureGrowEvent event, ItemStack itemstack, org.bukkit.craftbukkit.CraftWorld bukkitWorld)
    {
        // CraftBukkit end
        int l = random.nextInt(2);

        if (this.field_76523_a >= 0)
        {
            l = this.field_76523_a;
        }

        int i1 = random.nextInt(3) + 4;
        boolean flag = true;

        if (j >= 1 && j + i1 + 1 < 256)
        {
            int j1;
            int k1;
            int l1;
            int i2;

            for (j1 = j; j1 <= j + 1 + i1; ++j1)
            {
                byte b0 = 3;

                if (j1 <= j + 3)
                {
                    b0 = 0;
                }

                for (k1 = i - b0; k1 <= i + b0 && flag; ++k1)
                {
                    for (l1 = k - b0; l1 <= k + b0 && flag; ++l1)
                    {
                        if (j1 >= 0 && j1 < 256)
                        {
                            i2 = world.getTypeId(k1, j1, l1);

                            if (i2 != 0 && i2 != Block.field_71952_K.field_71990_ca)
                            {
                                flag = false;
                            }
                        }
                        else
                        {
                            flag = false;
                        }
                    }
                }
            }

            if (!flag)
            {
                return false;
            }
            else
            {
                j1 = world.getTypeId(i, j - 1, k);

                if (j1 != Block.field_71979_v.field_71990_ca && j1 != Block.field_71980_u.field_71990_ca && j1 != Block.field_71994_by.field_71990_ca)
                {
                    return false;
                }
                else
                {
                    // CraftBukkit start
                    if (event == null)
                    {
                        this.setTypeAndData(world, i, j - 1, k, Block.field_71979_v.field_71990_ca, 0);
                    }
                    else
                    {
                        BlockState dirtState = bukkitWorld.getBlockAt(i, j - 1, k).getState();
                        dirtState.setTypeId(Block.field_71979_v.field_71990_ca);
                        event.getBlocks().add(dirtState);
                    }

                    // CraftBukkit end
                    int j2 = j + i1;

                    if (l == 1)
                    {
                        j2 = j + i1 - 3;
                    }

                    for (k1 = j2; k1 <= j + i1; ++k1)
                    {
                        l1 = 1;

                        if (k1 < j + i1)
                        {
                            ++l1;
                        }

                        if (l == 0)
                        {
                            l1 = 3;
                        }

                        for (i2 = i - l1; i2 <= i + l1; ++i2)
                        {
                            for (int k2 = k - l1; k2 <= k + l1; ++k2)
                            {
                                int l2 = 5;

                                if (i2 == i - l1)
                                {
                                    --l2;
                                }

                                if (i2 == i + l1)
                                {
                                    ++l2;
                                }

                                if (k2 == k - l1)
                                {
                                    l2 -= 3;
                                }

                                if (k2 == k + l1)
                                {
                                    l2 += 3;
                                }

                                if (l == 0 || k1 < j + i1)
                                {
                                    if ((i2 == i - l1 || i2 == i + l1) && (k2 == k - l1 || k2 == k + l1))
                                    {
                                        continue;
                                    }

                                    if (i2 == i - (l1 - 1) && k2 == k - l1)
                                    {
                                        l2 = 1;
                                    }

                                    if (i2 == i - l1 && k2 == k - (l1 - 1))
                                    {
                                        l2 = 1;
                                    }

                                    if (i2 == i + (l1 - 1) && k2 == k - l1)
                                    {
                                        l2 = 3;
                                    }

                                    if (i2 == i + l1 && k2 == k - (l1 - 1))
                                    {
                                        l2 = 3;
                                    }

                                    if (i2 == i - (l1 - 1) && k2 == k + l1)
                                    {
                                        l2 = 7;
                                    }

                                    if (i2 == i - l1 && k2 == k + (l1 - 1))
                                    {
                                        l2 = 7;
                                    }

                                    if (i2 == i + (l1 - 1) && k2 == k + l1)
                                    {
                                        l2 = 9;
                                    }

                                    if (i2 == i + l1 && k2 == k + (l1 - 1))
                                    {
                                        l2 = 9;
                                    }
                                }

                                if (l2 == 5 && k1 < j + i1)
                                {
                                    l2 = 0;
                                }

                                if ((l2 != 0 || j >= j + i1 - 1) && !Block.field_71970_n[world.getTypeId(i2, k1, k2)])
                                {
                                    // CraftBukkit start
                                    if (event == null)
                                    {
                                        this.setTypeAndData(world, i2, k1, k2, Block.field_72000_bn.field_71990_ca + l, l2);
                                    }
                                    else
                                    {
                                        BlockState state = bukkitWorld.getBlockAt(i2, k1, k2).getState();
                                        state.setTypeId(Block.field_72000_bn.field_71990_ca + l);
                                        state.setData(new MaterialData(Block.field_72000_bn.field_71990_ca + l, (byte) l2));
                                        event.getBlocks().add(state);
                                    }

                                    // CraftBukkit end
                                }
                            }
                        }
                    }

                    for (k1 = 0; k1 < i1; ++k1)
                    {
                        l1 = world.getTypeId(i, j + k1, k);

                        if (!Block.field_71970_n[l1])
                        {
                            // CraftBukkit start
                            if (event == null)
                            {
                                this.setTypeAndData(world, i, j + k1, k, Block.field_72000_bn.field_71990_ca + l, 10);
                            }
                            else
                            {
                                BlockState state = bukkitWorld.getBlockAt(i, j + k1, k).getState();
                                state.setTypeId(Block.field_72000_bn.field_71990_ca + l);
                                state.setData(new MaterialData(Block.field_72000_bn.field_71990_ca + l, (byte) 10));
                                event.getBlocks().add(state);
                            }

                            // CraftBukkit end
                        }
                    }

                    // CraftBukkit start
                    if (event != null)
                    {
                        org.bukkit.Bukkit.getPluginManager().callEvent(event);

                        if (!event.isCancelled())
                        {
                            for (BlockState state : event.getBlocks())
                            {
                                state.update(true);
                            }
                        }
                    }

                    // CraftBukkit end
                    return true;
                }
            }
        }
        else
        {
            return false;
        }
    }
}
