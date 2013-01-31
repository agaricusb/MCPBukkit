package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.bukkit.event.block.BlockRedstoneEvent; // CraftBukkit

public class BlockSign extends BlockContainer
{
    private Class field_72279_a;
    private boolean field_72278_b;

    protected BlockSign(int p_i3993_1_, Class p_i3993_2_, boolean p_i3993_3_)
    {
        super(p_i3993_1_, Material.field_76245_d);
        this.field_72278_b = p_i3993_3_;
        this.field_72059_bZ = 4;
        this.field_72279_a = p_i3993_2_;
        float f = 0.25F;
        float f1 = 1.0F;
        this.func_71905_a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
    }

    public AxisAlignedBB func_71872_e(World p_71872_1_, int p_71872_2_, int p_71872_3_, int p_71872_4_)
    {
        return null;
    }

    public void func_71902_a(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_)
    {
        if (!this.field_72278_b)
        {
            int l = p_71902_1_.func_72805_g(p_71902_2_, p_71902_3_, p_71902_4_);
            float f = 0.28125F;
            float f1 = 0.78125F;
            float f2 = 0.0F;
            float f3 = 1.0F;
            float f4 = 0.125F;
            this.func_71905_a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);

            if (l == 2)
            {
                this.func_71905_a(f2, f, 1.0F - f4, f3, f1, 1.0F);
            }

            if (l == 3)
            {
                this.func_71905_a(f2, f, 0.0F, f3, f1, f4);
            }

            if (l == 4)
            {
                this.func_71905_a(1.0F - f4, f, f2, 1.0F, f1, f3);
            }

            if (l == 5)
            {
                this.func_71905_a(0.0F, f, f2, f4, f1, f3);
            }
        }
    }

    public int func_71857_b()
    {
        return -1;
    }

    public boolean func_71886_c()
    {
        return false;
    }

    public boolean func_71918_c(IBlockAccess p_71918_1_, int p_71918_2_, int p_71918_3_, int p_71918_4_)
    {
        return true;
    }

    public boolean func_71926_d()
    {
        return false;
    }

    public TileEntity func_72274_a(World p_72274_1_)
    {
        try
        {
            return (TileEntity)this.field_72279_a.newInstance();
        }
        catch (Exception exception)
        {
            throw new RuntimeException(exception);
        }
    }

    public int func_71885_a(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
    {
        return Item.field_77792_au.field_77779_bT;
    }

    public void func_71863_a(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
    {
        boolean flag = false;

        if (this.field_72278_b)
        {
            if (!p_71863_1_.func_72803_f(p_71863_2_, p_71863_3_ - 1, p_71863_4_).func_76220_a())
            {
                flag = true;
            }
        }
        else
        {
            int i1 = p_71863_1_.func_72805_g(p_71863_2_, p_71863_3_, p_71863_4_);
            flag = true;

            if (i1 == 2 && p_71863_1_.func_72803_f(p_71863_2_, p_71863_3_, p_71863_4_ + 1).func_76220_a())
            {
                flag = false;
            }

            if (i1 == 3 && p_71863_1_.func_72803_f(p_71863_2_, p_71863_3_, p_71863_4_ - 1).func_76220_a())
            {
                flag = false;
            }

            if (i1 == 4 && p_71863_1_.func_72803_f(p_71863_2_ + 1, p_71863_3_, p_71863_4_).func_76220_a())
            {
                flag = false;
            }

            if (i1 == 5 && p_71863_1_.func_72803_f(p_71863_2_ - 1, p_71863_3_, p_71863_4_).func_76220_a())
            {
                flag = false;
            }
        }

        if (flag)
        {
            this.func_71897_c(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, p_71863_1_.func_72805_g(p_71863_2_, p_71863_3_, p_71863_4_), 0);
            p_71863_1_.func_72859_e(p_71863_2_, p_71863_3_, p_71863_4_, 0);
        }

        super.func_71863_a(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, p_71863_5_);

        // CraftBukkit start
        if (Block.field_71973_m[p_71863_5_] != null && Block.field_71973_m[p_71863_5_].func_71853_i())
        {
            org.bukkit.block.Block block = p_71863_1_.getWorld().getBlockAt(p_71863_2_, p_71863_3_, p_71863_4_);
            int power = block.getBlockPower();
            BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, power, power);
            p_71863_1_.getServer().getPluginManager().callEvent(eventRedstone);
        }

        // CraftBukkit end
    }
}
