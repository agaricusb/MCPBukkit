package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.world.World;

import org.bukkit.BlockChangeDelegate; // CraftBukkit

public abstract class WorldGenerator
{
    private final boolean field_76488_a;
    private int field_82631_b = 1;

    public WorldGenerator()
    {
        this.field_76488_a = false;
    }

    public WorldGenerator(boolean p_i3789_1_)
    {
        this.field_76488_a = p_i3789_1_;
    }

    public abstract boolean func_76484_a(World world, Random random, int i, int j, int k);

    public void func_76487_a(double p_76487_1_, double p_76487_3_, double p_76487_5_) {}

    // CraftBukkit - change signature
    protected void setType(BlockChangeDelegate world, int i, int j, int k, int l)
    {
        this.setTypeAndData(world, i, j, k, l, 0);
    }

    // CraftBukkit - change signature
    protected void setTypeAndData(BlockChangeDelegate world, int i, int j, int k, int l, int i1)
    {
        if (this.field_76488_a)
        {
            world.setTypeIdAndData(i, j, k, l, i1);
        }
        else
        {
            world.setRawTypeIdAndData(i, j, k, l, i1);
        }
    }
}
