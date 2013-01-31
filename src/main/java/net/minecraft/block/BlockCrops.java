package net.minecraft.block;

import java.util.Random;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockCrops extends BlockFlower
{
    protected BlockCrops(int p_i3931_1_, int p_i3931_2_)
    {
        super(p_i3931_1_, p_i3931_2_);
        this.field_72059_bZ = p_i3931_2_;
        this.func_71907_b(true);
        float f = 0.5F;
        this.func_71905_a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
        this.func_71849_a((CreativeTabs)null);
        this.func_71848_c(0.0F);
        this.func_71884_a(field_71965_g);
        this.func_71896_v();
        this.func_71912_p();
    }

    protected boolean func_72263_d_(int p_72263_1_)
    {
        return p_72263_1_ == Block.field_72050_aA.field_71990_ca;
    }

    public void func_71847_b(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
    {
        super.func_71847_b(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_, p_71847_5_);

        if (p_71847_1_.func_72957_l(p_71847_2_, p_71847_3_ + 1, p_71847_4_) >= 9)
        {
            int l = p_71847_1_.func_72805_g(p_71847_2_, p_71847_3_, p_71847_4_);

            if (l < 7)
            {
                float f = this.func_72273_l(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_);

                if (p_71847_5_.nextInt((int)(25.0F / f) + 1) == 0)
                {
                    org.bukkit.craftbukkit.event.CraftEventFactory.handleBlockGrowEvent(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_, this.field_71990_ca, ++l); // CraftBukkit
                }
            }
        }
    }

    public void func_72272_c_(World p_72272_1_, int p_72272_2_, int p_72272_3_, int p_72272_4_)
    {
        p_72272_1_.func_72921_c(p_72272_2_, p_72272_3_, p_72272_4_, 7);
    }

    private float func_72273_l(World p_72273_1_, int p_72273_2_, int p_72273_3_, int p_72273_4_)
    {
        float f = 1.0F;
        int l = p_72273_1_.func_72798_a(p_72273_2_, p_72273_3_, p_72273_4_ - 1);
        int i1 = p_72273_1_.func_72798_a(p_72273_2_, p_72273_3_, p_72273_4_ + 1);
        int j1 = p_72273_1_.func_72798_a(p_72273_2_ - 1, p_72273_3_, p_72273_4_);
        int k1 = p_72273_1_.func_72798_a(p_72273_2_ + 1, p_72273_3_, p_72273_4_);
        int l1 = p_72273_1_.func_72798_a(p_72273_2_ - 1, p_72273_3_, p_72273_4_ - 1);
        int i2 = p_72273_1_.func_72798_a(p_72273_2_ + 1, p_72273_3_, p_72273_4_ - 1);
        int j2 = p_72273_1_.func_72798_a(p_72273_2_ + 1, p_72273_3_, p_72273_4_ + 1);
        int k2 = p_72273_1_.func_72798_a(p_72273_2_ - 1, p_72273_3_, p_72273_4_ + 1);
        boolean flag = j1 == this.field_71990_ca || k1 == this.field_71990_ca;
        boolean flag1 = l == this.field_71990_ca || i1 == this.field_71990_ca;
        boolean flag2 = l1 == this.field_71990_ca || i2 == this.field_71990_ca || j2 == this.field_71990_ca || k2 == this.field_71990_ca;

        for (int l2 = p_72273_2_ - 1; l2 <= p_72273_2_ + 1; ++l2)
        {
            for (int i3 = p_72273_4_ - 1; i3 <= p_72273_4_ + 1; ++i3)
            {
                int j3 = p_72273_1_.func_72798_a(l2, p_72273_3_ - 1, i3);
                float f1 = 0.0F;

                if (j3 == Block.field_72050_aA.field_71990_ca)
                {
                    f1 = 1.0F;

                    if (p_72273_1_.func_72805_g(l2, p_72273_3_ - 1, i3) > 0)
                    {
                        f1 = 3.0F;
                    }
                }

                if (l2 != p_72273_2_ || i3 != p_72273_4_)
                {
                    f1 /= 4.0F;
                }

                f += f1;
            }
        }

        if (flag2 || flag && flag1)
        {
            f /= 2.0F;
        }

        return f;
    }

    public int func_71858_a(int p_71858_1_, int p_71858_2_)
    {
        if (p_71858_2_ < 0)
        {
            p_71858_2_ = 7;
        }

        return this.field_72059_bZ + p_71858_2_;
    }

    public int func_71857_b()
    {
        return 6;
    }

    protected int func_82532_h()
    {
        return Item.field_77690_S.field_77779_bT;
    }

    protected int func_82533_j()
    {
        return Item.field_77685_T.field_77779_bT;
    }

    public void func_71914_a(World p_71914_1_, int p_71914_2_, int p_71914_3_, int p_71914_4_, int p_71914_5_, float p_71914_6_, int p_71914_7_)
    {
        super.func_71914_a(p_71914_1_, p_71914_2_, p_71914_3_, p_71914_4_, p_71914_5_, p_71914_6_, 0);

        if (!p_71914_1_.field_72995_K)
        {
            if (p_71914_5_ >= 7)
            {
                int j1 = 3 + p_71914_7_;

                for (int k1 = 0; k1 < j1; ++k1)
                {
                    if (p_71914_1_.field_73012_v.nextInt(15) <= p_71914_5_)
                    {
                        this.func_71929_a(p_71914_1_, p_71914_2_, p_71914_3_, p_71914_4_, new ItemStack(this.func_82532_h(), 1, 0));
                    }
                }
            }
        }
    }

    public int func_71885_a(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
    {
        return p_71885_1_ == 7 ? this.func_82533_j() : this.func_82532_h();
    }

    public int func_71925_a(Random p_71925_1_)
    {
        return 1;
    }
}
