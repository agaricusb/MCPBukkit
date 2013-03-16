package net.minecraft.block;

import org.bukkit.event.block.BlockRedstoneEvent; // CraftBukkit
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.World;

public class BlockNetherrack extends Block
{
    public BlockNetherrack(int p_i9063_1_)
    {
        super(p_i9063_1_, Material.field_76246_e);
        this.func_71849_a(CreativeTabs.field_78030_b);
    }

    // CraftBukkit start
    public void func_71863_a(World world, int i, int j, int k, int l)
    {
        if (Block.field_71973_m[l] != null && Block.field_71973_m[l].func_71853_i())
        {
            org.bukkit.block.Block block = world.getWorld().getBlockAt(i, j, k);
            int power = block.getBlockPower();
            BlockRedstoneEvent event = new BlockRedstoneEvent(block, power, power);
            world.getServer().getPluginManager().callEvent(event);
        }
    }
    // CraftBukkit end
}
