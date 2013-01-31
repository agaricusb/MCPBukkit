package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCocoa extends BlockDirectional
{
    public BlockCocoa(int p_i3930_1_)
    {
        super(p_i3930_1_, 168, Material.field_76254_j);
        this.func_71907_b(true);
    }

    public void func_71847_b(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
    {
        if (!this.func_71854_d(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_))
        {
            this.func_71897_c(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_, p_71847_1_.func_72805_g(p_71847_2_, p_71847_3_, p_71847_4_), 0);
            p_71847_1_.func_72859_e(p_71847_2_, p_71847_3_, p_71847_4_, 0);
        }
        else if (p_71847_1_.field_73012_v.nextInt(5) == 0)
        {
            int l = p_71847_1_.func_72805_g(p_71847_2_, p_71847_3_, p_71847_4_);
            int i1 = func_72219_c(l);

            if (i1 < 2)
            {
                ++i1;
                org.bukkit.craftbukkit.event.CraftEventFactory.handleBlockGrowEvent(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_, this.field_71990_ca, i1 << 2 | func_72217_d(l)); // CraftBukkit
            }
        }
    }

    public boolean func_71854_d(World p_71854_1_, int p_71854_2_, int p_71854_3_, int p_71854_4_)
    {
        int l = func_72217_d(p_71854_1_.func_72805_g(p_71854_2_, p_71854_3_, p_71854_4_));
        p_71854_2_ += Direction.field_71583_a[l];
        p_71854_4_ += Direction.field_71581_b[l];
        int i1 = p_71854_1_.func_72798_a(p_71854_2_, p_71854_3_, p_71854_4_);
        return i1 == Block.field_71951_J.field_71990_ca && BlockLog.func_72141_e(p_71854_1_.func_72805_g(p_71854_2_, p_71854_3_, p_71854_4_)) == 3;
    }

    public int func_71857_b()
    {
        return 28;
    }

    public boolean func_71886_c()
    {
        return false;
    }

    public boolean func_71926_d()
    {
        return false;
    }

    public AxisAlignedBB func_71872_e(World p_71872_1_, int p_71872_2_, int p_71872_3_, int p_71872_4_)
    {
        this.func_71902_a(p_71872_1_, p_71872_2_, p_71872_3_, p_71872_4_);
        return super.func_71872_e(p_71872_1_, p_71872_2_, p_71872_3_, p_71872_4_);
    }

    public void func_71902_a(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_)
    {
        int l = p_71902_1_.func_72805_g(p_71902_2_, p_71902_3_, p_71902_4_);
        int i1 = func_72217_d(l);
        int j1 = func_72219_c(l);
        int k1 = 4 + j1 * 2;
        int l1 = 5 + j1 * 2;
        float f = (float)k1 / 2.0F;

        switch (i1)
        {
            case 0:
                this.func_71905_a((8.0F - f) / 16.0F, (12.0F - (float)l1) / 16.0F, (15.0F - (float)k1) / 16.0F, (8.0F + f) / 16.0F, 0.75F, 0.9375F);
                break;
            case 1:
                this.func_71905_a(0.0625F, (12.0F - (float)l1) / 16.0F, (8.0F - f) / 16.0F, (1.0F + (float)k1) / 16.0F, 0.75F, (8.0F + f) / 16.0F);
                break;
            case 2:
                this.func_71905_a((8.0F - f) / 16.0F, (12.0F - (float)l1) / 16.0F, 0.0625F, (8.0F + f) / 16.0F, 0.75F, (1.0F + (float)k1) / 16.0F);
                break;
            case 3:
                this.func_71905_a((15.0F - (float)k1) / 16.0F, (12.0F - (float)l1) / 16.0F, (8.0F - f) / 16.0F, 0.9375F, 0.75F, (8.0F + f) / 16.0F);
        }
    }

    public void func_71860_a(World p_71860_1_, int p_71860_2_, int p_71860_3_, int p_71860_4_, EntityLiving p_71860_5_)
    {
        int l = ((MathHelper.func_76128_c((double)(p_71860_5_.field_70177_z * 4.0F / 360.0F) + 0.5D) & 3) + 0) % 4;
        p_71860_1_.func_72921_c(p_71860_2_, p_71860_3_, p_71860_4_, l);
    }

    public int func_85104_a(World p_85104_1_, int p_85104_2_, int p_85104_3_, int p_85104_4_, int p_85104_5_, float p_85104_6_, float p_85104_7_, float p_85104_8_, int p_85104_9_)
    {
        if (p_85104_5_ == 1 || p_85104_5_ == 0)
        {
            p_85104_5_ = 2;
        }

        return Direction.field_71580_e[Direction.field_71579_d[p_85104_5_]];
    }

    public void func_71863_a(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
    {
        if (!this.func_71854_d(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_))
        {
            this.func_71897_c(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, p_71863_1_.func_72805_g(p_71863_2_, p_71863_3_, p_71863_4_), 0);
            p_71863_1_.func_72859_e(p_71863_2_, p_71863_3_, p_71863_4_, 0);
        }
    }

    public static int func_72219_c(int p_72219_0_)
    {
        return (p_72219_0_ & 12) >> 2;
    }

    public void func_71914_a(World p_71914_1_, int p_71914_2_, int p_71914_3_, int p_71914_4_, int p_71914_5_, float p_71914_6_, int p_71914_7_)
    {
        int j1 = func_72219_c(p_71914_5_);
        byte b0 = 1;

        if (j1 >= 2)
        {
            b0 = 3;
        }

        for (int k1 = 0; k1 < b0; ++k1)
        {
            this.func_71929_a(p_71914_1_, p_71914_2_, p_71914_3_, p_71914_4_, new ItemStack(Item.field_77756_aW, 1, 3));
        }
    }

    public int func_71873_h(World p_71873_1_, int p_71873_2_, int p_71873_3_, int p_71873_4_)
    {
        return 3;
    }
}
