package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling.TreeGenerator;
import net.minecraft.util.Direction;
import net.minecraft.world.World;

import org.bukkit.BlockChangeDelegate; // CraftBukkit

public class WorldGenTrees extends WorldGenerator implements TreeGenerator   // CraftBukkit add interface
{
    private final int field_76533_a;
    private final boolean field_76531_b;
    private final int field_76532_c;
    private final int field_76530_d;

    public WorldGenTrees(boolean p_i3802_1_)
    {
        this(p_i3802_1_, 4, 0, 0, false);
    }

    public WorldGenTrees(boolean p_i3803_1_, int p_i3803_2_, int p_i3803_3_, int p_i3803_4_, boolean p_i3803_5_)
    {
        super(p_i3803_1_);
        this.field_76533_a = p_i3803_2_;
        this.field_76532_c = p_i3803_3_;
        this.field_76530_d = p_i3803_4_;
        this.field_76531_b = p_i3803_5_;
    }

    public boolean func_76484_a(World p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_)
    {
        // CraftBukkit start - moved to generate
        return this.generate((BlockChangeDelegate) p_76484_1_, p_76484_2_, p_76484_3_, p_76484_4_, p_76484_5_);
    }

    public boolean generate(BlockChangeDelegate p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_)
    {
        // CraftBukkit end
        int l = p_76484_2_.nextInt(3) + this.field_76533_a;
        boolean flag = true;

        if (p_76484_4_ >= 1 && p_76484_4_ + l + 1 <= 256)
        {
            int i1;
            byte b0;
            int j1;
            int k1;

            for (i1 = p_76484_4_; i1 <= p_76484_4_ + 1 + l; ++i1)
            {
                b0 = 1;

                if (i1 == p_76484_4_)
                {
                    b0 = 0;
                }

                if (i1 >= p_76484_4_ + 1 + l - 2)
                {
                    b0 = 2;
                }

                for (int l1 = p_76484_3_ - b0; l1 <= p_76484_3_ + b0 && flag; ++l1)
                {
                    for (j1 = p_76484_5_ - b0; j1 <= p_76484_5_ + b0 && flag; ++j1)
                    {
                        if (i1 >= 0 && i1 < 256)
                        {
                            k1 = p_76484_1_.getTypeId(l1, i1, j1);

                            if (k1 != 0 && k1 != Block.field_71952_K.field_71990_ca && k1 != Block.field_71980_u.field_71990_ca && k1 != Block.field_71979_v.field_71990_ca && k1 != Block.field_71951_J.field_71990_ca)
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
                    b0 = 3;
                    byte b1 = 0;
                    int i2;
                    int j2;
                    int k2;

                    for (j1 = p_76484_4_ - b0 + l; j1 <= p_76484_4_ + l; ++j1)
                    {
                        k1 = j1 - (p_76484_4_ + l);
                        i2 = b1 + 1 - k1 / 2;

                        for (j2 = p_76484_3_ - i2; j2 <= p_76484_3_ + i2; ++j2)
                        {
                            k2 = j2 - p_76484_3_;

                            for (int l2 = p_76484_5_ - i2; l2 <= p_76484_5_ + i2; ++l2)
                            {
                                int i3 = l2 - p_76484_5_;

                                if ((Math.abs(k2) != i2 || Math.abs(i3) != i2 || p_76484_2_.nextInt(2) != 0 && k1 != 0) && p_76484_1_.isEmpty(j2, j1, l2))
                                {
                                    this.setTypeAndData(p_76484_1_, j2, j1, l2, Block.field_71952_K.field_71990_ca, this.field_76530_d);
                                }
                            }
                        }
                    }

                    for (j1 = 0; j1 < l; ++j1)
                    {
                        k1 = p_76484_1_.getTypeId(p_76484_3_, p_76484_4_ + j1, p_76484_5_);

                        if (k1 == 0 || k1 == Block.field_71952_K.field_71990_ca)
                        {
                            this.setTypeAndData(p_76484_1_, p_76484_3_, p_76484_4_ + j1, p_76484_5_, Block.field_71951_J.field_71990_ca, this.field_76532_c);

                            if (this.field_76531_b && j1 > 0)
                            {
                                if (p_76484_2_.nextInt(3) > 0 && p_76484_1_.isEmpty(p_76484_3_ - 1, p_76484_4_ + j1, p_76484_5_))
                                {
                                    this.setTypeAndData(p_76484_1_, p_76484_3_ - 1, p_76484_4_ + j1, p_76484_5_, Block.field_71998_bu.field_71990_ca, 8);
                                }

                                if (p_76484_2_.nextInt(3) > 0 && p_76484_1_.isEmpty(p_76484_3_ + 1, p_76484_4_ + j1, p_76484_5_))
                                {
                                    this.setTypeAndData(p_76484_1_, p_76484_3_ + 1, p_76484_4_ + j1, p_76484_5_, Block.field_71998_bu.field_71990_ca, 2);
                                }

                                if (p_76484_2_.nextInt(3) > 0 && p_76484_1_.isEmpty(p_76484_3_, p_76484_4_ + j1, p_76484_5_ - 1))
                                {
                                    this.setTypeAndData(p_76484_1_, p_76484_3_, p_76484_4_ + j1, p_76484_5_ - 1, Block.field_71998_bu.field_71990_ca, 1);
                                }

                                if (p_76484_2_.nextInt(3) > 0 && p_76484_1_.isEmpty(p_76484_3_, p_76484_4_ + j1, p_76484_5_ + 1))
                                {
                                    this.setTypeAndData(p_76484_1_, p_76484_3_, p_76484_4_ + j1, p_76484_5_ + 1, Block.field_71998_bu.field_71990_ca, 4);
                                }
                            }
                        }
                    }

                    if (this.field_76531_b)
                    {
                        for (j1 = p_76484_4_ - 3 + l; j1 <= p_76484_4_ + l; ++j1)
                        {
                            k1 = j1 - (p_76484_4_ + l);
                            i2 = 2 - k1 / 2;

                            for (j2 = p_76484_3_ - i2; j2 <= p_76484_3_ + i2; ++j2)
                            {
                                for (k2 = p_76484_5_ - i2; k2 <= p_76484_5_ + i2; ++k2)
                                {
                                    if (p_76484_1_.getTypeId(j2, j1, k2) == Block.field_71952_K.field_71990_ca)
                                    {
                                        if (p_76484_2_.nextInt(4) == 0 && p_76484_1_.getTypeId(j2 - 1, j1, k2) == 0)
                                        {
                                            this.func_76529_b(p_76484_1_, j2 - 1, j1, k2, 8);
                                        }

                                        if (p_76484_2_.nextInt(4) == 0 && p_76484_1_.getTypeId(j2 + 1, j1, k2) == 0)
                                        {
                                            this.func_76529_b(p_76484_1_, j2 + 1, j1, k2, 2);
                                        }

                                        if (p_76484_2_.nextInt(4) == 0 && p_76484_1_.getTypeId(j2, j1, k2 - 1) == 0)
                                        {
                                            this.func_76529_b(p_76484_1_, j2, j1, k2 - 1, 1);
                                        }

                                        if (p_76484_2_.nextInt(4) == 0 && p_76484_1_.getTypeId(j2, j1, k2 + 1) == 0)
                                        {
                                            this.func_76529_b(p_76484_1_, j2, j1, k2 + 1, 4);
                                        }
                                    }
                                }
                            }
                        }

                        if (p_76484_2_.nextInt(5) == 0 && l > 5)
                        {
                            for (j1 = 0; j1 < 2; ++j1)
                            {
                                for (k1 = 0; k1 < 4; ++k1)
                                {
                                    if (p_76484_2_.nextInt(4 - j1) == 0)
                                    {
                                        i2 = p_76484_2_.nextInt(3);
                                        this.setTypeAndData(p_76484_1_, p_76484_3_ + Direction.field_71583_a[Direction.field_71580_e[k1]], p_76484_4_ + l - 5 + j1, p_76484_5_ + Direction.field_71581_b[Direction.field_71580_e[k1]], Block.field_72086_bP.field_71990_ca, i2 << 2 | k1);
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

    // CraftBukkit - Changed world to BlockChangeDelegate
    private void func_76529_b(BlockChangeDelegate p_76529_1_, int p_76529_2_, int p_76529_3_, int p_76529_4_, int p_76529_5_)
    {
        this.setTypeAndData(p_76529_1_, p_76529_2_, p_76529_3_, p_76529_4_, Block.field_71998_bu.field_71990_ca, p_76529_5_);
        int i1 = 4;

        while (true)
        {
            --p_76529_3_;

            if (p_76529_1_.getTypeId(p_76529_2_, p_76529_3_, p_76529_4_) != 0 || i1 <= 0)
            {
                return;
            }

            this.setTypeAndData(p_76529_1_, p_76529_2_, p_76529_3_, p_76529_4_, Block.field_71998_bu.field_71990_ca, p_76529_5_);
            --i1;
        }
    }
}
