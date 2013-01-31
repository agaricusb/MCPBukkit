package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling.TreeGenerator;
import net.minecraft.world.World;

import org.bukkit.BlockChangeDelegate; // CraftBukkit

public class WorldGenTaiga2 extends WorldGenerator implements TreeGenerator   // CraftBukkit add interface
{
    public WorldGenTaiga2(boolean p_i3800_1_)
    {
        super(p_i3800_1_);
    }

    public boolean func_76484_a(World p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_)
    {
        // CraftBukkit start - moved to generate
        return this.generate((BlockChangeDelegate) p_76484_1_, p_76484_2_, p_76484_3_, p_76484_4_, p_76484_5_);
    }

    public boolean generate(BlockChangeDelegate p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_)
    {
        // CraftBukkit end
        int l = p_76484_2_.nextInt(4) + 6;
        int i1 = 1 + p_76484_2_.nextInt(2);
        int j1 = l - i1;
        int k1 = 2 + p_76484_2_.nextInt(2);
        boolean flag = true;

        if (p_76484_4_ >= 1 && p_76484_4_ + l + 1 <= 256)
        {
            int l1;
            int i2;
            int j2;
            int k2;

            for (l1 = p_76484_4_; l1 <= p_76484_4_ + 1 + l && flag; ++l1)
            {
                boolean flag1 = true;

                if (l1 - p_76484_4_ < i1)
                {
                    k2 = 0;
                }
                else
                {
                    k2 = k1;
                }

                for (i2 = p_76484_3_ - k2; i2 <= p_76484_3_ + k2 && flag; ++i2)
                {
                    for (int l2 = p_76484_5_ - k2; l2 <= p_76484_5_ + k2 && flag; ++l2)
                    {
                        if (l1 >= 0 && l1 < 256)
                        {
                            j2 = p_76484_1_.getTypeId(i2, l1, l2);

                            if (j2 != 0 && j2 != Block.field_71952_K.field_71990_ca)
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

                if ((l1 == Block.field_71980_u.field_71990_ca || l1 == Block.field_71979_v.field_71990_ca) && p_76484_4_ < 256 - l - 1)
                {
                    this.setType(p_76484_1_, p_76484_3_, p_76484_4_ - 1, p_76484_5_, Block.field_71979_v.field_71990_ca);
                    k2 = p_76484_2_.nextInt(2);
                    i2 = 1;
                    byte b0 = 0;
                    int i3;
                    int j3;

                    for (j2 = 0; j2 <= j1; ++j2)
                    {
                        j3 = p_76484_4_ + l - j2;

                        for (i3 = p_76484_3_ - k2; i3 <= p_76484_3_ + k2; ++i3)
                        {
                            int k3 = i3 - p_76484_3_;

                            for (int l3 = p_76484_5_ - k2; l3 <= p_76484_5_ + k2; ++l3)
                            {
                                int i4 = l3 - p_76484_5_;

                                if ((Math.abs(k3) != k2 || Math.abs(i4) != k2 || k2 <= 0) && !Block.field_71970_n[p_76484_1_.getTypeId(i3, j3, l3)])
                                {
                                    this.setTypeAndData(p_76484_1_, i3, j3, l3, Block.field_71952_K.field_71990_ca, 1);
                                }
                            }
                        }

                        if (k2 >= i2)
                        {
                            k2 = b0;
                            b0 = 1;
                            ++i2;

                            if (i2 > k1)
                            {
                                i2 = k1;
                            }
                        }
                        else
                        {
                            ++k2;
                        }
                    }

                    j2 = p_76484_2_.nextInt(3);

                    for (j3 = 0; j3 < l - j2; ++j3)
                    {
                        i3 = p_76484_1_.getTypeId(p_76484_3_, p_76484_4_ + j3, p_76484_5_);

                        if (i3 == 0 || i3 == Block.field_71952_K.field_71990_ca)
                        {
                            this.setTypeAndData(p_76484_1_, p_76484_3_, p_76484_4_ + j3, p_76484_5_, Block.field_71951_J.field_71990_ca, 1);
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
