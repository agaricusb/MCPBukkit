package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling.TreeGenerator;
import net.minecraft.world.World;

import org.bukkit.BlockChangeDelegate; // CraftBukkit

public class WorldGenForest extends WorldGenerator implements TreeGenerator   // CraftBukkit add interface
{
    public WorldGenForest(boolean p_i3785_1_)
    {
        super(p_i3785_1_);
    }

    public boolean func_76484_a(World p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_)
    {
        // CraftBukkit start - moved to generate
        return this.generate((BlockChangeDelegate) p_76484_1_, p_76484_2_, p_76484_3_, p_76484_4_, p_76484_5_);
    }

    public boolean generate(BlockChangeDelegate p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_)
    {
        // CraftBukkit end
        int l = p_76484_2_.nextInt(3) + 5;
        boolean flag = true;

        if (p_76484_4_ >= 1 && p_76484_4_ + l + 1 <= 256)
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
                    b0 = 2;
                }

                for (j1 = p_76484_3_ - b0; j1 <= p_76484_3_ + b0 && flag; ++j1)
                {
                    for (k1 = p_76484_5_ - b0; k1 <= p_76484_5_ + b0 && flag; ++k1)
                    {
                        if (i1 >= 0 && i1 < 256)
                        {
                            l1 = p_76484_1_.getTypeId(j1, i1, k1);

                            if (l1 != 0 && l1 != Block.field_71952_K.field_71990_ca)
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
                i1 = p_76484_1_.getTypeId(p_76484_3_, p_76484_4_ - 1, p_76484_5_);

                if ((i1 == Block.field_71980_u.field_71990_ca || i1 == Block.field_71979_v.field_71990_ca) && p_76484_4_ < 256 - l - 1)
                {
                    this.setType(p_76484_1_, p_76484_3_, p_76484_4_ - 1, p_76484_5_, Block.field_71979_v.field_71990_ca);
                    int i2;

                    for (i2 = p_76484_4_ - 3 + l; i2 <= p_76484_4_ + l; ++i2)
                    {
                        j1 = i2 - (p_76484_4_ + l);
                        k1 = 1 - j1 / 2;

                        for (l1 = p_76484_3_ - k1; l1 <= p_76484_3_ + k1; ++l1)
                        {
                            int j2 = l1 - p_76484_3_;

                            for (int k2 = p_76484_5_ - k1; k2 <= p_76484_5_ + k1; ++k2)
                            {
                                int l2 = k2 - p_76484_5_;

                                if (Math.abs(j2) != k1 || Math.abs(l2) != k1 || p_76484_2_.nextInt(2) != 0 && j1 != 0)
                                {
                                    int i3 = p_76484_1_.getTypeId(l1, i2, k2);

                                    if (i3 == 0 || i3 == Block.field_71952_K.field_71990_ca)
                                    {
                                        this.setTypeAndData(p_76484_1_, l1, i2, k2, Block.field_71952_K.field_71990_ca, 2);
                                    }
                                }
                            }
                        }
                    }

                    for (i2 = 0; i2 < l; ++i2)
                    {
                        j1 = p_76484_1_.getTypeId(p_76484_3_, p_76484_4_ + i2, p_76484_5_);

                        if (j1 == 0 || j1 == Block.field_71952_K.field_71990_ca)
                        {
                            this.setTypeAndData(p_76484_1_, p_76484_3_, p_76484_4_ + i2, p_76484_5_, Block.field_71951_J.field_71990_ca, 2);
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
}
