package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.BlockSapling.TreeGenerator;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenForest;
import net.minecraft.world.gen.feature.WorldGenHugeTrees;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenTrees;

// CraftBukkit start
import org.bukkit.Location;
import org.bukkit.TreeType;
import org.bukkit.craftbukkit.util.StructureGrowDelegate;
import org.bukkit.event.world.StructureGrowEvent;
// CraftBukkit end

public class BlockSapling extends BlockFlower
{
    public static final String[] field_72270_a = new String[] {"oak", "spruce", "birch", "jungle"};

    protected BlockSapling(int p_i3992_1_, int p_i3992_2_)
    {
        super(p_i3992_1_, p_i3992_2_);
        float f = 0.4F;
        this.func_71905_a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
        this.func_71849_a(CreativeTabs.field_78031_c);
    }

    public void func_71847_b(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
    {
        if (!p_71847_1_.field_72995_K)
        {
            super.func_71847_b(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_, p_71847_5_);

            if (p_71847_1_.func_72957_l(p_71847_2_, p_71847_3_ + 1, p_71847_4_) >= 9 && p_71847_5_.nextInt(7) == 0)
            {
                int l = p_71847_1_.func_72805_g(p_71847_2_, p_71847_3_, p_71847_4_);

                if ((l & 8) == 0)
                {
                    p_71847_1_.func_72921_c(p_71847_2_, p_71847_3_, p_71847_4_, l | 8);
                }
                else
                {
                    this.func_72269_c(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_, p_71847_5_, false, null, null); // CraftBukkit - added bonemeal, player and itemstack
                }
            }
        }
    }

    public int func_71858_a(int p_71858_1_, int p_71858_2_)
    {
        p_71858_2_ &= 3;
        return p_71858_2_ == 1 ? 63 : (p_71858_2_ == 2 ? 79 : (p_71858_2_ == 3 ? 30 : super.func_71858_a(p_71858_1_, p_71858_2_)));
    }

    // CraftBukkit - added bonemeal, player and itemstack
    public void func_72269_c(World p_72269_1_, int p_72269_2_, int p_72269_3_, int p_72269_4_, Random p_72269_5_, boolean bonemeal, org.bukkit.entity.Player player, ItemStack itemstack)
    {
        int l = p_72269_1_.func_72805_g(p_72269_2_, p_72269_3_, p_72269_4_) & 3;
        int i1 = 0;
        int j1 = 0;
        boolean flag = false;
        // CraftBukkit start - records tree generation and calls StructureGrowEvent
        StructureGrowDelegate delegate = new StructureGrowDelegate(p_72269_1_);
        TreeType treeType = null;
        TreeGenerator gen = null;
        boolean grownTree = false;

        if (l == 1)
        {
            treeType = TreeType.REDWOOD;
            gen = new WorldGenTaiga2(false);
        }
        else if (l == 2)
        {
            treeType = TreeType.BIRCH;
            gen = new WorldGenForest(false);
        }
        else if (l == 3)
        {
            for (i1 = 0; i1 >= -1; --i1)
            {
                for (j1 = 0; j1 >= -1; --j1)
                {
                    if (this.func_72268_e(p_72269_1_, p_72269_2_ + i1, p_72269_3_, p_72269_4_ + j1, 3) && this.func_72268_e(p_72269_1_, p_72269_2_ + i1 + 1, p_72269_3_, p_72269_4_ + j1, 3) && this.func_72268_e(p_72269_1_, p_72269_2_ + i1, p_72269_3_, p_72269_4_ + j1 + 1, 3) && this.func_72268_e(p_72269_1_, p_72269_2_ + i1 + 1, p_72269_3_, p_72269_4_ + j1 + 1, 3))
                    {
                        treeType = TreeType.JUNGLE;
                        gen = new WorldGenHugeTrees(false, 10 + p_72269_5_.nextInt(20), 3, 3);
                        flag = true;
                        break;
                    }
                }

                if (gen != null)
                {
                    break;
                }
            }

            if (gen == null)
            {
                j1 = 0;
                i1 = 0;
                treeType = TreeType.SMALL_JUNGLE;
                gen = new WorldGenTrees(false, 4 + p_72269_5_.nextInt(7), 3, 3, false);
            }
        }
        else
        {
            treeType = TreeType.TREE;
            gen = new WorldGenTrees(false);

            if (p_72269_5_.nextInt(10) == 0)
            {
                treeType = TreeType.BIG_TREE;
                gen = new WorldGenBigTree(false);
            }
        }

        if (flag)
        {
            p_72269_1_.func_72822_b(p_72269_2_ + i1, p_72269_3_, p_72269_4_ + j1, 0);
            p_72269_1_.func_72822_b(p_72269_2_ + i1 + 1, p_72269_3_, p_72269_4_ + j1, 0);
            p_72269_1_.func_72822_b(p_72269_2_ + i1, p_72269_3_, p_72269_4_ + j1 + 1, 0);
            p_72269_1_.func_72822_b(p_72269_2_ + i1 + 1, p_72269_3_, p_72269_4_ + j1 + 1, 0);
        }
        else
        {
            p_72269_1_.func_72822_b(p_72269_2_, p_72269_3_, p_72269_4_, 0);
        }

        grownTree = gen.generate(delegate, p_72269_5_, p_72269_2_ + i1, p_72269_3_, p_72269_4_ + j1);

        if (grownTree)
        {
            Location location = new Location(p_72269_1_.getWorld(), p_72269_2_, p_72269_3_, p_72269_4_);
            StructureGrowEvent event = new StructureGrowEvent(location, treeType, bonemeal, player, delegate.getBlocks());
            org.bukkit.Bukkit.getPluginManager().callEvent(event);

            if (event.isCancelled())
            {
                grownTree = false;
            }
            else
            {
                for (org.bukkit.block.BlockState state : event.getBlocks())
                {
                    state.update(true);
                }

                if (event.isFromBonemeal() && itemstack != null)
                {
                    --itemstack.field_77994_a;
                }
            }
        }

        if (!grownTree)
        {
            if (flag)
            {
                p_72269_1_.func_72961_c(p_72269_2_ + i1, p_72269_3_, p_72269_4_ + j1, this.field_71990_ca, l);
                p_72269_1_.func_72961_c(p_72269_2_ + i1 + 1, p_72269_3_, p_72269_4_ + j1, this.field_71990_ca, l);
                p_72269_1_.func_72961_c(p_72269_2_ + i1, p_72269_3_, p_72269_4_ + j1 + 1, this.field_71990_ca, l);
                p_72269_1_.func_72961_c(p_72269_2_ + i1 + 1, p_72269_3_, p_72269_4_ + j1 + 1, this.field_71990_ca, l);
            }
            else
            {
                p_72269_1_.func_72961_c(p_72269_2_, p_72269_3_, p_72269_4_, this.field_71990_ca, l);
            }
        }

        // CraftBukkit end
    }

    public boolean func_72268_e(World p_72268_1_, int p_72268_2_, int p_72268_3_, int p_72268_4_, int p_72268_5_)
    {
        return p_72268_1_.func_72798_a(p_72268_2_, p_72268_3_, p_72268_4_) == this.field_71990_ca && (p_72268_1_.func_72805_g(p_72268_2_, p_72268_3_, p_72268_4_) & 3) == p_72268_5_;
    }

    public int func_71899_b(int p_71899_1_)
    {
        return p_71899_1_ & 3;
    }

    // CraftBukkit start
    public interface TreeGenerator
    {
        public boolean func_76484_a(World world, Random random, int i, int j, int k);

        public boolean generate(org.bukkit.BlockChangeDelegate p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_);
    }
    // CraftBukkit end
}
