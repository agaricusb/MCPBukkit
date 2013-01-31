package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling.TreeGenerator;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import org.bukkit.BlockChangeDelegate; // CraftBukkit

public class WorldGenHugeTrees extends WorldGenerator implements TreeGenerator   // CraftBukkit add interface
{
    private final int field_76522_a;
    private final int field_76520_b;
    private final int field_76521_c;

    public WorldGenHugeTrees(boolean p_i3795_1_, int p_i3795_2_, int p_i3795_3_, int p_i3795_4_)
    {
        super(p_i3795_1_);
        this.field_76522_a = p_i3795_2_;
        this.field_76520_b = p_i3795_3_;
        this.field_76521_c = p_i3795_4_;
    }

    public boolean func_76484_a(World p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_)
    {
        // CraftBukkit start - moved to generate
        return this.generate((BlockChangeDelegate) p_76484_1_, p_76484_2_, p_76484_3_, p_76484_4_, p_76484_5_);
    }

    public boolean generate(BlockChangeDelegate p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_)
    {
        // CraftBukkit end
        int l = p_76484_2_.nextInt(3) + this.field_76522_a;
        boolean flag = true;

        if (p_76484_4_ >= 1 && p_76484_4_ + l + 1 <= 256)
        {
            int i1;
            int j1;
            int k1;
            int l1;

            for (i1 = p_76484_4_; i1 <= p_76484_4_ + 1 + l; ++i1)
            {
                byte b0 = 2;

                if (i1 == p_76484_4_)
                {
                    b0 = 1;
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

                            if (l1 != 0 && l1 != Block.field_71952_K.field_71990_ca && l1 != Block.field_71980_u.field_71990_ca && l1 != Block.field_71979_v.field_71990_ca && l1 != Block.field_71951_J.field_71990_ca && l1 != Block.field_71987_y.field_71990_ca)
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
                    p_76484_1_.setRawTypeId(p_76484_3_, p_76484_4_ - 1, p_76484_5_, Block.field_71979_v.field_71990_ca);
                    p_76484_1_.setRawTypeId(p_76484_3_ + 1, p_76484_4_ - 1, p_76484_5_, Block.field_71979_v.field_71990_ca);
                    p_76484_1_.setRawTypeId(p_76484_3_, p_76484_4_ - 1, p_76484_5_ + 1, Block.field_71979_v.field_71990_ca);
                    p_76484_1_.setRawTypeId(p_76484_3_ + 1, p_76484_4_ - 1, p_76484_5_ + 1, Block.field_71979_v.field_71990_ca);
                    this.a(p_76484_1_, p_76484_3_, p_76484_5_, p_76484_4_ + l, 2, p_76484_2_);

                    for (int i2 = p_76484_4_ + l - 2 - p_76484_2_.nextInt(4); i2 > p_76484_4_ + l / 2; i2 -= 2 + p_76484_2_.nextInt(4))
                    {
                        float f = p_76484_2_.nextFloat() * (float)Math.PI * 2.0F;
                        k1 = p_76484_3_ + (int)(0.5F + MathHelper.func_76134_b(f) * 4.0F);
                        l1 = p_76484_5_ + (int)(0.5F + MathHelper.func_76126_a(f) * 4.0F);
                        this.a(p_76484_1_, k1, l1, i2, 0, p_76484_2_);

                        for (int j2 = 0; j2 < 5; ++j2)
                        {
                            k1 = p_76484_3_ + (int)(1.5F + MathHelper.func_76134_b(f) * (float)j2);
                            l1 = p_76484_5_ + (int)(1.5F + MathHelper.func_76126_a(f) * (float)j2);
                            this.setTypeAndData(p_76484_1_, k1, i2 - 3 + j2 / 2, l1, Block.field_71951_J.field_71990_ca, this.field_76520_b);
                        }
                    }

                    for (j1 = 0; j1 < l; ++j1)
                    {
                        k1 = p_76484_1_.getTypeId(p_76484_3_, p_76484_4_ + j1, p_76484_5_);

                        if (k1 == 0 || k1 == Block.field_71952_K.field_71990_ca)
                        {
                            this.setTypeAndData(p_76484_1_, p_76484_3_, p_76484_4_ + j1, p_76484_5_, Block.field_71951_J.field_71990_ca, this.field_76520_b);

                            if (j1 > 0)
                            {
                                if (p_76484_2_.nextInt(3) > 0 && p_76484_1_.isEmpty(p_76484_3_ - 1, p_76484_4_ + j1, p_76484_5_))
                                {
                                    this.setTypeAndData(p_76484_1_, p_76484_3_ - 1, p_76484_4_ + j1, p_76484_5_, Block.field_71998_bu.field_71990_ca, 8);
                                }

                                if (p_76484_2_.nextInt(3) > 0 && p_76484_1_.isEmpty(p_76484_3_, p_76484_4_ + j1, p_76484_5_ - 1))
                                {
                                    this.setTypeAndData(p_76484_1_, p_76484_3_, p_76484_4_ + j1, p_76484_5_ - 1, Block.field_71998_bu.field_71990_ca, 1);
                                }
                            }
                        }

                        if (j1 < l - 1)
                        {
                            k1 = p_76484_1_.getTypeId(p_76484_3_ + 1, p_76484_4_ + j1, p_76484_5_);

                            if (k1 == 0 || k1 == Block.field_71952_K.field_71990_ca)
                            {
                                this.setTypeAndData(p_76484_1_, p_76484_3_ + 1, p_76484_4_ + j1, p_76484_5_, Block.field_71951_J.field_71990_ca, this.field_76520_b);

                                if (j1 > 0)
                                {
                                    if (p_76484_2_.nextInt(3) > 0 && p_76484_1_.isEmpty(p_76484_3_ + 2, p_76484_4_ + j1, p_76484_5_))
                                    {
                                        this.setTypeAndData(p_76484_1_, p_76484_3_ + 2, p_76484_4_ + j1, p_76484_5_, Block.field_71998_bu.field_71990_ca, 2);
                                    }

                                    if (p_76484_2_.nextInt(3) > 0 && p_76484_1_.isEmpty(p_76484_3_ + 1, p_76484_4_ + j1, p_76484_5_ - 1))
                                    {
                                        this.setTypeAndData(p_76484_1_, p_76484_3_ + 1, p_76484_4_ + j1, p_76484_5_ - 1, Block.field_71998_bu.field_71990_ca, 1);
                                    }
                                }
                            }

                            k1 = p_76484_1_.getTypeId(p_76484_3_ + 1, p_76484_4_ + j1, p_76484_5_ + 1);

                            if (k1 == 0 || k1 == Block.field_71952_K.field_71990_ca)
                            {
                                this.setTypeAndData(p_76484_1_, p_76484_3_ + 1, p_76484_4_ + j1, p_76484_5_ + 1, Block.field_71951_J.field_71990_ca, this.field_76520_b);

                                if (j1 > 0)
                                {
                                    if (p_76484_2_.nextInt(3) > 0 && p_76484_1_.isEmpty(p_76484_3_ + 2, p_76484_4_ + j1, p_76484_5_ + 1))
                                    {
                                        this.setTypeAndData(p_76484_1_, p_76484_3_ + 2, p_76484_4_ + j1, p_76484_5_ + 1, Block.field_71998_bu.field_71990_ca, 2);
                                    }

                                    if (p_76484_2_.nextInt(3) > 0 && p_76484_1_.isEmpty(p_76484_3_ + 1, p_76484_4_ + j1, p_76484_5_ + 2))
                                    {
                                        this.setTypeAndData(p_76484_1_, p_76484_3_ + 1, p_76484_4_ + j1, p_76484_5_ + 2, Block.field_71998_bu.field_71990_ca, 4);
                                    }
                                }
                            }

                            k1 = p_76484_1_.getTypeId(p_76484_3_, p_76484_4_ + j1, p_76484_5_ + 1);

                            if (k1 == 0 || k1 == Block.field_71952_K.field_71990_ca)
                            {
                                this.setTypeAndData(p_76484_1_, p_76484_3_, p_76484_4_ + j1, p_76484_5_ + 1, Block.field_71951_J.field_71990_ca, this.field_76520_b);

                                if (j1 > 0)
                                {
                                    if (p_76484_2_.nextInt(3) > 0 && p_76484_1_.isEmpty(p_76484_3_ - 1, p_76484_4_ + j1, p_76484_5_ + 1))
                                    {
                                        this.setTypeAndData(p_76484_1_, p_76484_3_ - 1, p_76484_4_ + j1, p_76484_5_ + 1, Block.field_71998_bu.field_71990_ca, 8);
                                    }

                                    if (p_76484_2_.nextInt(3) > 0 && p_76484_1_.isEmpty(p_76484_3_, p_76484_4_ + j1, p_76484_5_ + 2))
                                    {
                                        this.setTypeAndData(p_76484_1_, p_76484_3_, p_76484_4_ + j1, p_76484_5_ + 2, Block.field_71998_bu.field_71990_ca, 4);
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

    // CraftBukkit - Changed signature
    private void a(BlockChangeDelegate world, int i, int j, int k, int l, Random random)
    {
        byte b0 = 2;

        for (int i1 = k - b0; i1 <= k; ++i1)
        {
            int j1 = i1 - k;
            int k1 = l + 1 - j1;

            for (int l1 = i - k1; l1 <= i + k1 + 1; ++l1)
            {
                int i2 = l1 - i;

                for (int j2 = j - k1; j2 <= j + k1 + 1; ++j2)
                {
                    int k2 = j2 - j;

                    if ((i2 >= 0 || k2 >= 0 || i2 * i2 + k2 * k2 <= k1 * k1) && (i2 <= 0 && k2 <= 0 || i2 * i2 + k2 * k2 <= (k1 + 1) * (k1 + 1)) && (random.nextInt(4) != 0 || i2 * i2 + k2 * k2 <= (k1 - 1) * (k1 - 1)) && !Block.field_71970_n[world.getTypeId(l1, i1, j2)])
                    {
                        this.setTypeAndData(world, l1, i1, j2, Block.field_71952_K.field_71990_ca, this.field_76521_c);
                    }
                }
            }
        }
    }
}
