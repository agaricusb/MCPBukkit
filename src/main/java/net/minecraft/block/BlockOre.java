package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockOre extends Block
{
    public BlockOre(int p_i9076_1_)
    {
        super(p_i9076_1_, Material.field_76246_e);
        this.func_71849_a(CreativeTabs.field_78030_b);
    }

    public int func_71885_a(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
    {
        return this.field_71990_ca == Block.field_71950_I.field_71990_ca ? Item.field_77705_m.field_77779_bT : (this.field_71990_ca == Block.field_72073_aw.field_71990_ca ? Item.field_77702_n.field_77779_bT : (this.field_71990_ca == Block.field_71947_N.field_71990_ca ? Item.field_77756_aW.field_77779_bT : (this.field_71990_ca == Block.field_72068_bR.field_71990_ca ? Item.field_77817_bH.field_77779_bT : (this.field_71990_ca == Block.field_94342_cr.field_71990_ca ? Item.field_94583_ca.field_77779_bT : this.field_71990_ca))));
    }

    public int func_71925_a(Random p_71925_1_)
    {
        return this.field_71990_ca == Block.field_71947_N.field_71990_ca ? 4 + p_71925_1_.nextInt(5) : 1;
    }

    public int func_71910_a(int p_71910_1_, Random p_71910_2_)
    {
        if (p_71910_1_ > 0 && this.field_71990_ca != this.func_71885_a(0, p_71910_2_, p_71910_1_))
        {
            int j = p_71910_2_.nextInt(p_71910_1_ + 2) - 1;

            if (j < 0)
            {
                j = 0;
            }

            return this.func_71925_a(p_71910_2_) * (j + 1);
        }
        else
        {
            return this.func_71925_a(p_71910_2_);
        }
    }

    public void func_71914_a(World p_71914_1_, int p_71914_2_, int p_71914_3_, int p_71914_4_, int p_71914_5_, float p_71914_6_, int p_71914_7_)
    {
        super.func_71914_a(p_71914_1_, p_71914_2_, p_71914_3_, p_71914_4_, p_71914_5_, p_71914_6_, p_71914_7_);
        /* CraftBukkit start - delegated getExpDrop
        if (this.getDropType(l, world.random, i1) != this.id) {
            int j1 = 0;

            if (this.id == Block.COAL_ORE.id) {
                j1 = MathHelper.nextInt(world.random, 0, 2);
            } else if (this.id == Block.DIAMOND_ORE.id) {
                j1 = MathHelper.nextInt(world.random, 3, 7);
            } else if (this.id == Block.EMERALD_ORE.id) {
                j1 = MathHelper.nextInt(world.random, 3, 7);
            } else if (this.id == Block.LAPIS_ORE.id) {
                j1 = MathHelper.nextInt(world.random, 2, 5);
            } else if (this.id == Block.QUARTZ_ORE.id) {
                j1 = MathHelper.nextInt(world.random, 2, 5);
            }

            this.j(world, i, j, k, j1);
        } */
    }

    public int getExpDrop(World world, int l, int i1)
    {
        if (this.func_71885_a(l, world.field_73012_v, i1) != this.field_71990_ca)
        {
            int j1 = 0;

            if (this.field_71990_ca == Block.field_71950_I.field_71990_ca)
            {
                j1 = MathHelper.func_76136_a(world.field_73012_v, 0, 2);
            }
            else if (this.field_71990_ca == Block.field_72073_aw.field_71990_ca)
            {
                j1 = MathHelper.func_76136_a(world.field_73012_v, 3, 7);
            }
            else if (this.field_71990_ca == Block.field_72068_bR.field_71990_ca)
            {
                j1 = MathHelper.func_76136_a(world.field_73012_v, 3, 7);
            }
            else if (this.field_71990_ca == Block.field_71947_N.field_71990_ca)
            {
                j1 = MathHelper.func_76136_a(world.field_73012_v, 2, 5);
            }
            else if (this.field_71990_ca == Block.field_94342_cr.field_71990_ca)
            {
                j1 = MathHelper.func_76136_a(world.field_73012_v, 2, 5);
            }

            return j1;
        }

        return 0;
        // CraftBukkit end
    }

    public int func_71899_b(int p_71899_1_)
    {
        return this.field_71990_ca == Block.field_71947_N.field_71990_ca ? 4 : 0;
    }
}
