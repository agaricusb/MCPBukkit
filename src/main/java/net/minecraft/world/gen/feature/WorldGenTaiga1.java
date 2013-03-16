package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling.TreeGenerator;
import net.minecraft.world.World;

import org.bukkit.BlockChangeDelegate; // CraftBukkit

public class WorldGenTaiga1 extends WorldGenerator implements TreeGenerator   // CraftBukkit add interface
{
    public WorldGenTaiga1() {}

    public boolean func_76484_a(World p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_)
    {
        // CraftBukkit start - moved to generate
        return this.generate((BlockChangeDelegate) p_76484_1_, p_76484_2_, p_76484_3_, p_76484_4_, p_76484_5_);
    }

    public boolean generate(BlockChangeDelegate p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_)
    {
        // CraftBukkit end
        int l = p_76484_2_.nextInt(5) + 7;
        int i1 = l - p_76484_2_.nextInt(2) - 3;
        int j1 = l - i1;
        int k1 = 1 + p_76484_2_.nextInt(j1 + 1);
        boolean flag = true;

        if (p_76484_4_ >= 1 && p_76484_4_ + l + 1 <= 128)
        {
            int l1;
            int i2;
            int j2;
            int k2;
            int l2;

            for (l1 = p_76484_4_; l1 <= p_76484_4_ + 1 + l && flag; ++l1)
            {
                boolean flag1 = true;

                if (l1 - p_76484_4_ < i1)
                {
                    l2 = 0;
                }
                else
                {
                    l2 = k1;
                }

                for (i2 = p_76484_3_ - l2; i2 <= p_76484_3_ + l2 && flag; ++i2)
                {
                    for (j2 = p_76484_5_ - l2; j2 <= p_76484_5_ + l2 && flag; ++j2)
                    {
                        if (l1 >= 0 && l1 < 128)
                        {
                            k2 = p_76484_1_.getTypeId(i2, l1, j2);

                            if (k2 != 0 && k2 != Block.field_71952_K.field_71990_ca)
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
                l1 = p_76484_1_.getTypeId(p_76484_3_, p_76484_4_ - 1, p_76484_5_);

                if ((l1 == Block.field_71980_u.field_71990_ca || l1 == Block.field_71979_v.field_71990_ca) && p_76484_4_ < 128 - l - 1)
                {
                    this.setType(p_76484_1_, p_76484_3_, p_76484_4_ - 1, p_76484_5_, Block.field_71979_v.field_71990_ca);
                    l2 = 0;

                    for (i2 = p_76484_4_ + l; i2 >= p_76484_4_ + i1; --i2)
                    {
                        for (j2 = p_76484_3_ - l2; j2 <= p_76484_3_ + l2; ++j2)
                        {
                            k2 = j2 - p_76484_3_;

                            for (int i3 = p_76484_5_ - l2; i3 <= p_76484_5_ + l2; ++i3)
                            {
                                int j3 = i3 - p_76484_5_;

                                if ((Math.abs(k2) != l2 || Math.abs(j3) != l2 || l2 <= 0) && !Block.field_71970_n[p_76484_1_.getTypeId(j2, i2, i3)])
                                {
                                    this.setTypeAndData(p_76484_1_, j2, i2, i3, Block.field_71952_K.field_71990_ca, 1);
                                }
                            }
                        }

                        if (l2 >= 1 && i2 == p_76484_4_ + i1 + 1)
                        {
                            --l2;
                        }
                        else if (l2 < k1)
                        {
                            ++l2;
                        }
                    }

                    for (i2 = 0; i2 < l - 1; ++i2)
                    {
                        j2 = p_76484_1_.getTypeId(p_76484_3_, p_76484_4_ + i2, p_76484_5_);

                        if (j2 == 0 || j2 == Block.field_71952_K.field_71990_ca)
                        {
                            this.setTypeAndData(p_76484_1_, p_76484_3_, p_76484_4_ + i2, p_76484_5_, Block.field_71951_J.field_71990_ca, 1);
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
