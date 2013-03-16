package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling.TreeGenerator;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

import org.bukkit.BlockChangeDelegate; // CraftBukkit

public class WorldGenSwamp extends WorldGenerator implements TreeGenerator   // CraftBukkit add interface
{
    public WorldGenSwamp() {}

    public boolean func_76484_a(World p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_)
    {
        // CraftBukkit start - moved to generate
        return this.generate((BlockChangeDelegate) p_76484_1_, p_76484_2_, p_76484_3_, p_76484_4_, p_76484_5_);
    }

    public boolean generate(BlockChangeDelegate p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_)
    {
        // CraftBukkit end
        int l;

        for (l = p_76484_2_.nextInt(4) + 5; p_76484_1_.getTypeId(p_76484_3_, p_76484_4_ - 1, p_76484_5_) != 0 && Block.field_71973_m[p_76484_1_.getTypeId(p_76484_3_, p_76484_4_ - 1, p_76484_5_)].field_72018_cp == Material.field_76244_g; --p_76484_4_)   // CraftBukkit - bypass world.getMaterial
        {
            ;
        }

        boolean flag = true;

        if (p_76484_4_ >= 1 && p_76484_4_ + l + 1 <= 128)
        {
            int i1;
            int j1;
            int k1;
            int l1;

            for (i1 = p_76484_4_; i1 <= p_76484_4_ + 1 + l; ++i1)
            {
                byte b0 = 1;

                if (i1 == p_76484_4_)
                {
                    b0 = 0;
                }

                if (i1 >= p_76484_4_ + 1 + l - 2)
                {
                    b0 = 3;
                }

                for (j1 = p_76484_3_ - b0; j1 <= p_76484_3_ + b0 && flag; ++j1)
                {
                    for (k1 = p_76484_5_ - b0; k1 <= p_76484_5_ + b0 && flag; ++k1)
                    {
                        if (i1 >= 0 && i1 < 128)
                        {
                            l1 = p_76484_1_.getTypeId(j1, i1, k1);

                            if (l1 != 0 && l1 != Block.field_71952_K.field_71990_ca)
                            {
                                if (l1 != Block.field_71943_B.field_71990_ca && l1 != Block.field_71942_A.field_71990_ca)
                                {
                                    flag = false;
                                }
                                else if (i1 > p_76484_4_)
                                {
                                    flag = false;
                                }
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
                i1 = p_76484_1_.getTypeId(p_76484_3_, p_76484_4_ - 1, p_76484_5_);

                if ((i1 == Block.field_71980_u.field_71990_ca || i1 == Block.field_71979_v.field_71990_ca) && p_76484_4_ < 128 - l - 1)
                {
                    this.setType(p_76484_1_, p_76484_3_, p_76484_4_ - 1, p_76484_5_, Block.field_71979_v.field_71990_ca);
                    int i2;
                    int j2;

                    for (j2 = p_76484_4_ - 3 + l; j2 <= p_76484_4_ + l; ++j2)
                    {
                        j1 = j2 - (p_76484_4_ + l);
                        k1 = 2 - j1 / 2;

                        for (l1 = p_76484_3_ - k1; l1 <= p_76484_3_ + k1; ++l1)
                        {
                            i2 = l1 - p_76484_3_;

                            for (int k2 = p_76484_5_ - k1; k2 <= p_76484_5_ + k1; ++k2)
                            {
                                int l2 = k2 - p_76484_5_;

                                if ((Math.abs(i2) != k1 || Math.abs(l2) != k1 || p_76484_2_.nextInt(2) != 0 && j1 != 0) && !Block.field_71970_n[p_76484_1_.getTypeId(l1, j2, k2)])
                                {
                                    this.setType(p_76484_1_, l1, j2, k2, Block.field_71952_K.field_71990_ca);
                                }
                            }
                        }
                    }

                    for (j2 = 0; j2 < l; ++j2)
                    {
                        j1 = p_76484_1_.getTypeId(p_76484_3_, p_76484_4_ + j2, p_76484_5_);

                        if (j1 == 0 || j1 == Block.field_71952_K.field_71990_ca || j1 == Block.field_71942_A.field_71990_ca || j1 == Block.field_71943_B.field_71990_ca)
                        {
                            this.setType(p_76484_1_, p_76484_3_, p_76484_4_ + j2, p_76484_5_, Block.field_71951_J.field_71990_ca);
                        }
                    }

                    for (j2 = p_76484_4_ - 3 + l; j2 <= p_76484_4_ + l; ++j2)
                    {
                        j1 = j2 - (p_76484_4_ + l);
                        k1 = 2 - j1 / 2;

                        for (l1 = p_76484_3_ - k1; l1 <= p_76484_3_ + k1; ++l1)
                        {
                            for (i2 = p_76484_5_ - k1; i2 <= p_76484_5_ + k1; ++i2)
                            {
                                if (p_76484_1_.getTypeId(l1, j2, i2) == Block.field_71952_K.field_71990_ca)
                                {
                                    if (p_76484_2_.nextInt(4) == 0 && p_76484_1_.getTypeId(l1 - 1, j2, i2) == 0)
                                    {
                                        this.b(p_76484_1_, l1 - 1, j2, i2, 8);
                                    }

                                    if (p_76484_2_.nextInt(4) == 0 && p_76484_1_.getTypeId(l1 + 1, j2, i2) == 0)
                                    {
                                        this.b(p_76484_1_, l1 + 1, j2, i2, 2);
                                    }

                                    if (p_76484_2_.nextInt(4) == 0 && p_76484_1_.getTypeId(l1, j2, i2 - 1) == 0)
                                    {
                                        this.b(p_76484_1_, l1, j2, i2 - 1, 1);
                                    }

                                    if (p_76484_2_.nextInt(4) == 0 && p_76484_1_.getTypeId(l1, j2, i2 + 1) == 0)
                                    {
                                        this.b(p_76484_1_, l1, j2, i2 + 1, 4);
                                    }
                                }
                            }
                        }
                    }

                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        else
        {
            return false;
        }
    }

    // CraftBukkit - change signature
    private void b(BlockChangeDelegate world, int i, int j, int k, int l)
    {
        this.setTypeAndData(world, i, j, k, Block.field_71998_bu.field_71990_ca, l);
        int i1 = 4;

        while (true)
        {
            --j;

            if (world.getTypeId(i, j, k) != 0 || i1 <= 0)
            {
                return;
            }

            this.setTypeAndData(world, i, j, k, Block.field_71998_bu.field_71990_ca, l);
            --i1;
        }
    }
}
