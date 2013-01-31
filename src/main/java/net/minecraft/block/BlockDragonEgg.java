package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityFallingSand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import org.bukkit.event.block.BlockFromToEvent; // CraftBukkit

public class BlockDragonEgg extends Block
{
    public BlockDragonEgg(int p_i3940_1_, int p_i3940_2_)
    {
        super(p_i3940_1_, p_i3940_2_, Material.field_76236_A);
        this.func_71905_a(0.0625F, 0.0F, 0.0625F, 0.9375F, 1.0F, 0.9375F);
    }

    public void func_71861_g(World p_71861_1_, int p_71861_2_, int p_71861_3_, int p_71861_4_)
    {
        p_71861_1_.func_72836_a(p_71861_2_, p_71861_3_, p_71861_4_, this.field_71990_ca, this.func_71859_p_());
    }

    public void func_71863_a(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
    {
        p_71863_1_.func_72836_a(p_71863_2_, p_71863_3_, p_71863_4_, this.field_71990_ca, this.func_71859_p_());
    }

    public void func_71847_b(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
    {
        this.func_72236_l(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_);
    }

    private void func_72236_l(World p_72236_1_, int p_72236_2_, int p_72236_3_, int p_72236_4_)
    {
        if (BlockSand.func_72191_e_(p_72236_1_, p_72236_2_, p_72236_3_ - 1, p_72236_4_) && p_72236_3_ >= 0)
        {
            byte b0 = 32;

            if (!BlockSand.field_72192_a && p_72236_1_.func_72904_c(p_72236_2_ - b0, p_72236_3_ - b0, p_72236_4_ - b0, p_72236_2_ + b0, p_72236_3_ + b0, p_72236_4_ + b0))
            {
                // CraftBukkit - added data
                EntityFallingSand entityfallingsand = new EntityFallingSand(p_72236_1_, (double)((float) p_72236_2_ + 0.5F), (double)((float) p_72236_3_ + 0.5F), (double)((float) p_72236_4_ + 0.5F), this.field_71990_ca, p_72236_1_.func_72805_g(p_72236_2_, p_72236_3_, p_72236_4_));
                p_72236_1_.func_72838_d(entityfallingsand);
            }
            else
            {
                p_72236_1_.func_72859_e(p_72236_2_, p_72236_3_, p_72236_4_, 0);

                while (BlockSand.func_72191_e_(p_72236_1_, p_72236_2_, p_72236_3_ - 1, p_72236_4_) && p_72236_3_ > 0)
                {
                    --p_72236_3_;
                }

                if (p_72236_3_ > 0)
                {
                    p_72236_1_.func_72859_e(p_72236_2_, p_72236_3_, p_72236_4_, this.field_71990_ca);
                }
            }
        }
    }

    public boolean func_71903_a(World p_71903_1_, int p_71903_2_, int p_71903_3_, int p_71903_4_, EntityPlayer p_71903_5_, int p_71903_6_, float p_71903_7_, float p_71903_8_, float p_71903_9_)
    {
        this.func_72237_n(p_71903_1_, p_71903_2_, p_71903_3_, p_71903_4_);
        return true;
    }

    public void func_71921_a(World p_71921_1_, int p_71921_2_, int p_71921_3_, int p_71921_4_, EntityPlayer p_71921_5_)
    {
        this.func_72237_n(p_71921_1_, p_71921_2_, p_71921_3_, p_71921_4_);
    }

    private void func_72237_n(World p_72237_1_, int p_72237_2_, int p_72237_3_, int p_72237_4_)
    {
        if (p_72237_1_.func_72798_a(p_72237_2_, p_72237_3_, p_72237_4_) == this.field_71990_ca)
        {
            for (int l = 0; l < 1000; ++l)
            {
                int i1 = p_72237_2_ + p_72237_1_.field_73012_v.nextInt(16) - p_72237_1_.field_73012_v.nextInt(16);
                int j1 = p_72237_3_ + p_72237_1_.field_73012_v.nextInt(8) - p_72237_1_.field_73012_v.nextInt(8);
                int k1 = p_72237_4_ + p_72237_1_.field_73012_v.nextInt(16) - p_72237_1_.field_73012_v.nextInt(16);

                if (p_72237_1_.func_72798_a(i1, j1, k1) == 0)
                {
                    // CraftBukkit start
                    org.bukkit.block.Block from = p_72237_1_.getWorld().getBlockAt(p_72237_2_, p_72237_3_, p_72237_4_);
                    org.bukkit.block.Block to = p_72237_1_.getWorld().getBlockAt(i1, j1, k1);
                    BlockFromToEvent event = new BlockFromToEvent(from, to);
                    org.bukkit.Bukkit.getPluginManager().callEvent(event);

                    if (event.isCancelled())
                    {
                        return;
                    }

                    i1 = event.getToBlock().getX();
                    j1 = event.getToBlock().getY();
                    k1 = event.getToBlock().getZ();
                    // CraftBukkit end

                    if (!p_72237_1_.field_72995_K)
                    {
                        p_72237_1_.func_72832_d(i1, j1, k1, this.field_71990_ca, p_72237_1_.func_72805_g(p_72237_2_, p_72237_3_, p_72237_4_));
                        p_72237_1_.func_72859_e(p_72237_2_, p_72237_3_, p_72237_4_, 0);
                    }
                    else
                    {
                        short short1 = 128;

                        for (int l1 = 0; l1 < short1; ++l1)
                        {
                            double d0 = p_72237_1_.field_73012_v.nextDouble();
                            float f = (p_72237_1_.field_73012_v.nextFloat() - 0.5F) * 0.2F;
                            float f1 = (p_72237_1_.field_73012_v.nextFloat() - 0.5F) * 0.2F;
                            float f2 = (p_72237_1_.field_73012_v.nextFloat() - 0.5F) * 0.2F;
                            double d1 = (double)i1 + (double)(p_72237_2_ - i1) * d0 + (p_72237_1_.field_73012_v.nextDouble() - 0.5D) * 1.0D + 0.5D;
                            double d2 = (double)j1 + (double)(p_72237_3_ - j1) * d0 + p_72237_1_.field_73012_v.nextDouble() * 1.0D - 0.5D;
                            double d3 = (double)k1 + (double)(p_72237_4_ - k1) * d0 + (p_72237_1_.field_73012_v.nextDouble() - 0.5D) * 1.0D + 0.5D;
                            p_72237_1_.func_72869_a("portal", d1, d2, d3, (double)f, (double)f1, (double)f2);
                        }
                    }

                    return;
                }
            }
        }
    }

    public int func_71859_p_()
    {
        return 5;
    }

    public boolean func_71926_d()
    {
        return false;
    }

    public boolean func_71886_c()
    {
        return false;
    }

    public int func_71857_b()
    {
        return 27;
    }
}
