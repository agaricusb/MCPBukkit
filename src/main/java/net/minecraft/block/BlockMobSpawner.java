package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;

public class BlockMobSpawner extends BlockContainer
{
    protected BlockMobSpawner(int p_i9072_1_)
    {
        super(p_i9072_1_, Material.field_76246_e);
    }

    public TileEntity func_72274_a(World p_72274_1_)
    {
        return new TileEntityMobSpawner();
    }

    public int func_71885_a(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
    {
        return 0;
    }

    public int func_71925_a(Random p_71925_1_)
    {
        return 0;
    }

    public void func_71914_a(World p_71914_1_, int p_71914_2_, int p_71914_3_, int p_71914_4_, int p_71914_5_, float p_71914_6_, int p_71914_7_)
    {
        super.func_71914_a(p_71914_1_, p_71914_2_, p_71914_3_, p_71914_4_, p_71914_5_, p_71914_6_, p_71914_7_);
        /* CraftBukkit start - delegate to getExpDrop
        int j1 = 15 + world.random.nextInt(15) + world.random.nextInt(15);

        this.j(world, i, j, k, j1);*/
    }

    public int getExpDrop(World world, int data, int enchantmentLevel)
    {
        int j1 = 15 + world.field_73012_v.nextInt(15) + world.field_73012_v.nextInt(15);
        return j1;
        // CraftBukkit end
    }

    public boolean func_71926_d()
    {
        return false;
    }
}
