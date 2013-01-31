package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.world.World;

import org.bukkit.event.block.BlockRedstoneEvent; // CraftBukkit

public class BlockCommandBlock extends BlockContainer
{
    public BlockCommandBlock(int p_i5102_1_)
    {
        super(p_i5102_1_, 184, Material.field_76243_f);
    }

    public TileEntity func_72274_a(World p_72274_1_)
    {
        return new TileEntityCommandBlock();
    }

    public void func_71863_a(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
    {
        if (!p_71863_1_.field_72995_K)
        {
            boolean flag = p_71863_1_.func_72864_z(p_71863_2_, p_71863_3_, p_71863_4_);
            int i1 = p_71863_1_.func_72805_g(p_71863_2_, p_71863_3_, p_71863_4_);
            boolean flag1 = (i1 & 1) != 0;
            // CraftBukkit start
            org.bukkit.block.Block block = p_71863_1_.getWorld().getBlockAt(p_71863_2_, p_71863_3_, p_71863_4_);
            int old = flag1 ? 1 : 0;
            int current = flag ? 1 : 0;
            BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, old, current);
            p_71863_1_.getServer().getPluginManager().callEvent(eventRedstone);
            // CraftBukkit end

            if (eventRedstone.getNewCurrent() > 0 && !(eventRedstone.getOldCurrent() > 0))   // CraftBukkit
            {
                p_71863_1_.func_72881_d(p_71863_2_, p_71863_3_, p_71863_4_, i1 | 1);
                p_71863_1_.func_72836_a(p_71863_2_, p_71863_3_, p_71863_4_, this.field_71990_ca, this.func_71859_p_());
            }
            else if (!(eventRedstone.getNewCurrent() > 0) && eventRedstone.getOldCurrent() > 0)     // CraftBukkit
            {
                p_71863_1_.func_72881_d(p_71863_2_, p_71863_3_, p_71863_4_, i1 & -2);
            }
        }
    }

    public void func_71847_b(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
    {
        TileEntity tileentity = p_71847_1_.func_72796_p(p_71847_2_, p_71847_3_, p_71847_4_);

        if (tileentity != null && tileentity instanceof TileEntityCommandBlock)
        {
            ((TileEntityCommandBlock)tileentity).func_82351_a(p_71847_1_);
        }
    }

    public int func_71859_p_()
    {
        return 1;
    }

    public boolean func_71903_a(World p_71903_1_, int p_71903_2_, int p_71903_3_, int p_71903_4_, EntityPlayer p_71903_5_, int p_71903_6_, float p_71903_7_, float p_71903_8_, float p_71903_9_)
    {
        TileEntityCommandBlock tileentitycommandblock = (TileEntityCommandBlock)p_71903_1_.func_72796_p(p_71903_2_, p_71903_3_, p_71903_4_);

        if (tileentitycommandblock != null)
        {
            p_71903_5_.func_71014_a((TileEntity) tileentitycommandblock);
        }

        return true;
    }
}
