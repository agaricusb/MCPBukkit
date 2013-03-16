package net.minecraft.block;

import org.bukkit.event.block.BlockRedstoneEvent; // CraftBukkit
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTrapDoor extends Block
{
    protected BlockTrapDoor(int p_i4015_1_, Material p_i4015_2_)
    {
        super(p_i4015_1_, p_i4015_2_);
        float f = 0.5F;
        float f1 = 1.0F;
        this.func_71905_a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
        this.func_71849_a(CreativeTabs.field_78028_d);
    }

    public boolean func_71926_d()
    {
        return false;
    }

    public boolean func_71886_c()
    {
        return false;
    }

    public boolean func_71918_c(IBlockAccess p_71918_1_, int p_71918_2_, int p_71918_3_, int p_71918_4_)
    {
        return !func_72137_g(p_71918_1_.func_72805_g(p_71918_2_, p_71918_3_, p_71918_4_));
    }

    public int func_71857_b()
    {
        return 0;
    }

    public AxisAlignedBB func_71872_e(World p_71872_1_, int p_71872_2_, int p_71872_3_, int p_71872_4_)
    {
        this.func_71902_a(p_71872_1_, p_71872_2_, p_71872_3_, p_71872_4_);
        return super.func_71872_e(p_71872_1_, p_71872_2_, p_71872_3_, p_71872_4_);
    }

    public void func_71902_a(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_)
    {
        this.func_72139_e(p_71902_1_.func_72805_g(p_71902_2_, p_71902_3_, p_71902_4_));
    }

    public void func_71919_f()
    {
        float f = 0.1875F;
        this.func_71905_a(0.0F, 0.5F - f / 2.0F, 0.0F, 1.0F, 0.5F + f / 2.0F, 1.0F);
    }

    public void func_72139_e(int p_72139_1_)
    {
        float f = 0.1875F;

        if ((p_72139_1_ & 8) != 0)
        {
            this.func_71905_a(0.0F, 1.0F - f, 0.0F, 1.0F, 1.0F, 1.0F);
        }
        else
        {
            this.func_71905_a(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
        }

        if (func_72137_g(p_72139_1_))
        {
            if ((p_72139_1_ & 3) == 0)
            {
                this.func_71905_a(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
            }

            if ((p_72139_1_ & 3) == 1)
            {
                this.func_71905_a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
            }

            if ((p_72139_1_ & 3) == 2)
            {
                this.func_71905_a(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            }

            if ((p_72139_1_ & 3) == 3)
            {
                this.func_71905_a(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
            }
        }
    }

    public void func_71921_a(World p_71921_1_, int p_71921_2_, int p_71921_3_, int p_71921_4_, EntityPlayer p_71921_5_) {}

    public boolean func_71903_a(World p_71903_1_, int p_71903_2_, int p_71903_3_, int p_71903_4_, EntityPlayer p_71903_5_, int p_71903_6_, float p_71903_7_, float p_71903_8_, float p_71903_9_)
    {
        if (this.field_72018_cp == Material.field_76243_f)
        {
            return true;
        }
        else
        {
            int i1 = p_71903_1_.func_72805_g(p_71903_2_, p_71903_3_, p_71903_4_);
            p_71903_1_.func_72921_c(p_71903_2_, p_71903_3_, p_71903_4_, i1 ^ 4, 2);
            p_71903_1_.func_72889_a(p_71903_5_, 1003, p_71903_2_, p_71903_3_, p_71903_4_, 0);
            return true;
        }
    }

    public void func_72138_a(World p_72138_1_, int p_72138_2_, int p_72138_3_, int p_72138_4_, boolean p_72138_5_)
    {
        int l = p_72138_1_.func_72805_g(p_72138_2_, p_72138_3_, p_72138_4_);
        boolean flag1 = (l & 4) > 0;

        if (flag1 != p_72138_5_)
        {
            p_72138_1_.func_72921_c(p_72138_2_, p_72138_3_, p_72138_4_, l ^ 4, 2);
            p_72138_1_.func_72889_a((EntityPlayer)null, 1003, p_72138_2_, p_72138_3_, p_72138_4_, 0);
        }
    }

    public void func_71863_a(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
    {
        if (!p_71863_1_.field_72995_K)
        {
            int i1 = p_71863_1_.func_72805_g(p_71863_2_, p_71863_3_, p_71863_4_);
            int j1 = p_71863_2_;
            int k1 = p_71863_4_;

            if ((i1 & 3) == 0)
            {
                k1 = p_71863_4_ + 1;
            }

            if ((i1 & 3) == 1)
            {
                --k1;
            }

            if ((i1 & 3) == 2)
            {
                j1 = p_71863_2_ + 1;
            }

            if ((i1 & 3) == 3)
            {
                --j1;
            }

            if (!func_72140_j(p_71863_1_.func_72798_a(j1, p_71863_3_, k1)))
            {
                p_71863_1_.func_94571_i(p_71863_2_, p_71863_3_, p_71863_4_);
                this.func_71897_c(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, i1, 0);
            }

            // CraftBukkit start
            if (p_71863_5_ == 0 || p_71863_5_ > 0 && Block.field_71973_m[p_71863_5_] != null && Block.field_71973_m[p_71863_5_].func_71853_i())
            {
                org.bukkit.World bworld = p_71863_1_.getWorld();
                org.bukkit.block.Block block = bworld.getBlockAt(p_71863_2_, p_71863_3_, p_71863_4_);
                int power = block.getBlockPower();
                int oldPower = (p_71863_1_.func_72805_g(p_71863_2_, p_71863_3_, p_71863_4_) & 4) > 0 ? 15 : 0;

                if (oldPower == 0 ^ power == 0 || (Block.field_71973_m[p_71863_5_] != null && Block.field_71973_m[p_71863_5_].func_71853_i()))
                {
                    BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, oldPower, power);
                    p_71863_1_.getServer().getPluginManager().callEvent(eventRedstone);
                    this.func_72138_a(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, eventRedstone.getNewCurrent() > 0);
                }

                // CraftBukkit end
            }
        }
    }

    public MovingObjectPosition func_71878_a(World p_71878_1_, int p_71878_2_, int p_71878_3_, int p_71878_4_, Vec3 p_71878_5_, Vec3 p_71878_6_)
    {
        this.func_71902_a(p_71878_1_, p_71878_2_, p_71878_3_, p_71878_4_);
        return super.func_71878_a(p_71878_1_, p_71878_2_, p_71878_3_, p_71878_4_, p_71878_5_, p_71878_6_);
    }

    public int func_85104_a(World p_85104_1_, int p_85104_2_, int p_85104_3_, int p_85104_4_, int p_85104_5_, float p_85104_6_, float p_85104_7_, float p_85104_8_, int p_85104_9_)
    {
        int j1 = 0;

        if (p_85104_5_ == 2)
        {
            j1 = 0;
        }

        if (p_85104_5_ == 3)
        {
            j1 = 1;
        }

        if (p_85104_5_ == 4)
        {
            j1 = 2;
        }

        if (p_85104_5_ == 5)
        {
            j1 = 3;
        }

        if (p_85104_5_ != 1 && p_85104_5_ != 0 && p_85104_7_ > 0.5F)
        {
            j1 |= 8;
        }

        return j1;
    }

    public boolean func_71850_a_(World p_71850_1_, int p_71850_2_, int p_71850_3_, int p_71850_4_, int p_71850_5_)
    {
        if (p_71850_5_ == 0)
        {
            return false;
        }
        else if (p_71850_5_ == 1)
        {
            return false;
        }
        else
        {
            if (p_71850_5_ == 2)
            {
                ++p_71850_4_;
            }

            if (p_71850_5_ == 3)
            {
                --p_71850_4_;
            }

            if (p_71850_5_ == 4)
            {
                ++p_71850_2_;
            }

            if (p_71850_5_ == 5)
            {
                --p_71850_2_;
            }

            return func_72140_j(p_71850_1_.func_72798_a(p_71850_2_, p_71850_3_, p_71850_4_));
        }
    }

    public static boolean func_72137_g(int p_72137_0_)
    {
        return (p_72137_0_ & 4) != 0;
    }

    private static boolean func_72140_j(int p_72140_0_)
    {
        if (p_72140_0_ <= 0)
        {
            return false;
        }
        else
        {
            Block block = Block.field_71973_m[p_72140_0_];
            return block != null && block.field_72018_cp.func_76218_k() && block.func_71886_c() || block == Block.field_72014_bd || block instanceof BlockHalfSlab || block instanceof BlockStairs;
        }
    }
}
