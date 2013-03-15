package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

import org.bukkit.craftbukkit.event.CraftEventFactory; // CraftBukkit

public class BlockRedstoneLight extends Block
{
    private final boolean field_72166_a;

    public BlockRedstoneLight(int p_i3988_1_, boolean p_i3988_2_)
    {
        super(p_i3988_1_, 211, Material.field_76263_r);
        this.field_72166_a = p_i3988_2_;

        if (p_i3988_2_)
        {
            this.func_71900_a(1.0F);
            ++this.field_72059_bZ;
        }
    }

    public void func_71861_g(World p_71861_1_, int p_71861_2_, int p_71861_3_, int p_71861_4_)
    {
        if (!p_71861_1_.field_72995_K)
        {
            if (this.field_72166_a && !p_71861_1_.func_72864_z(p_71861_2_, p_71861_3_, p_71861_4_))
            {
                p_71861_1_.func_72836_a(p_71861_2_, p_71861_3_, p_71861_4_, this.field_71990_ca, 4);
            }
            else if (!this.field_72166_a && p_71861_1_.func_72864_z(p_71861_2_, p_71861_3_, p_71861_4_))
            {
                // CraftBukkit start
                if (CraftEventFactory.callRedstoneChange(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_, 0, 15).getNewCurrent() != 15)
                {
                    return;
                }

                // CraftBukkit end
                p_71861_1_.func_72859_e(p_71861_2_, p_71861_3_, p_71861_4_, Block.field_72080_bM.field_71990_ca);
            }
        }
    }

    public void func_71863_a(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
    {
        if (!p_71863_1_.field_72995_K)
        {
            if (this.field_72166_a && !p_71863_1_.func_72864_z(p_71863_2_, p_71863_3_, p_71863_4_))
            {
                p_71863_1_.func_72836_a(p_71863_2_, p_71863_3_, p_71863_4_, this.field_71990_ca, 4);
            }
            else if (!this.field_72166_a && p_71863_1_.func_72864_z(p_71863_2_, p_71863_3_, p_71863_4_))
            {
                // CraftBukkit start
                if (CraftEventFactory.callRedstoneChange(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, 0, 15).getNewCurrent() != 15)
                {
                    return;
                }

                // CraftBukkit end
                p_71863_1_.func_72859_e(p_71863_2_, p_71863_3_, p_71863_4_, Block.field_72080_bM.field_71990_ca);
            }
        }
    }

    public void func_71847_b(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
    {
        if (!p_71847_1_.field_72995_K && this.field_72166_a && !p_71847_1_.func_72864_z(p_71847_2_, p_71847_3_, p_71847_4_))
        {
            // CraftBukkit start
            if (CraftEventFactory.callRedstoneChange(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_, 15, 0).getNewCurrent() != 0)
            {
                return;
            }

            // CraftBukkit end
            p_71847_1_.func_72859_e(p_71847_2_, p_71847_3_, p_71847_4_, Block.field_72078_bL.field_71990_ca);
        }
    }

    public int func_71885_a(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
    {
        return Block.field_72078_bL.field_71990_ca;
    }
}
