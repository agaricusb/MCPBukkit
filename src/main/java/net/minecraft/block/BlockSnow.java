package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSnow extends Block
{
    protected BlockSnow(int p_i9096_1_)
    {
        super(p_i9096_1_, Material.field_76259_v);
        this.func_71905_a(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
        this.func_71907_b(true);
        this.func_71849_a(CreativeTabs.field_78031_c);
        this.func_96478_d(0);
    }

    public AxisAlignedBB func_71872_e(World p_71872_1_, int p_71872_2_, int p_71872_3_, int p_71872_4_)
    {
        int l = p_71872_1_.func_72805_g(p_71872_2_, p_71872_3_, p_71872_4_) & 7;
        float f = 0.125F;
        return AxisAlignedBB.func_72332_a().func_72299_a((double)p_71872_2_ + this.field_72026_ch, (double)p_71872_3_ + this.field_72023_ci, (double)p_71872_4_ + this.field_72024_cj, (double)p_71872_2_ + this.field_72021_ck, (double)((float)p_71872_3_ + (float)l * f), (double)p_71872_4_ + this.field_72019_cm);
    }

    public boolean func_71926_d()
    {
        return false;
    }

    public boolean func_71886_c()
    {
        return false;
    }

    public void func_71919_f()
    {
        this.func_96478_d(0);
    }

    public void func_71902_a(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_)
    {
        this.func_96478_d(p_71902_1_.func_72805_g(p_71902_2_, p_71902_3_, p_71902_4_));
    }

    protected void func_96478_d(int p_96478_1_)
    {
        int j = p_96478_1_ & 7;
        float f = (float)(2 * (1 + j)) / 16.0F;
        this.func_71905_a(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
    }

    public boolean func_71930_b(World p_71930_1_, int p_71930_2_, int p_71930_3_, int p_71930_4_)
    {
        int l = p_71930_1_.func_72798_a(p_71930_2_, p_71930_3_ - 1, p_71930_4_);
        return l == 0 ? false : (l == this.field_71990_ca && (p_71930_1_.func_72805_g(p_71930_2_, p_71930_3_ - 1, p_71930_4_) & 7) == 7 ? true : (l != Block.field_71952_K.field_71990_ca && !Block.field_71973_m[l].func_71926_d() ? false : p_71930_1_.func_72803_f(p_71930_2_, p_71930_3_ - 1, p_71930_4_).func_76230_c()));
    }

    public void func_71863_a(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
    {
        this.func_72124_n(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_);
    }

    private boolean func_72124_n(World p_72124_1_, int p_72124_2_, int p_72124_3_, int p_72124_4_)
    {
        if (!this.func_71930_b(p_72124_1_, p_72124_2_, p_72124_3_, p_72124_4_))
        {
            this.func_71897_c(p_72124_1_, p_72124_2_, p_72124_3_, p_72124_4_, p_72124_1_.func_72805_g(p_72124_2_, p_72124_3_, p_72124_4_), 0);
            p_72124_1_.func_94571_i(p_72124_2_, p_72124_3_, p_72124_4_);
            return false;
        }
        else
        {
            return true;
        }
    }

    public void func_71893_a(World p_71893_1_, EntityPlayer p_71893_2_, int p_71893_3_, int p_71893_4_, int p_71893_5_, int p_71893_6_)
    {
        int i1 = Item.field_77768_aD.field_77779_bT;
        int j1 = p_71893_6_ & 7;
        this.func_71929_a(p_71893_1_, p_71893_3_, p_71893_4_, p_71893_5_, new ItemStack(i1, j1 + 1, 0));
        p_71893_1_.func_94571_i(p_71893_3_, p_71893_4_, p_71893_5_);
        p_71893_2_.func_71064_a(StatList.field_75934_C[this.field_71990_ca], 1);
    }

    public int func_71885_a(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
    {
        return Item.field_77768_aD.field_77779_bT;
    }

    public int func_71925_a(Random p_71925_1_)
    {
        return 0;
    }

    public void func_71847_b(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
    {
        if (p_71847_1_.func_72972_b(EnumSkyBlock.Block, p_71847_2_, p_71847_3_, p_71847_4_) > 11)
        {
            // CraftBukkit start
            if (org.bukkit.craftbukkit.event.CraftEventFactory.callBlockFadeEvent(p_71847_1_.getWorld().getBlockAt(p_71847_2_, p_71847_3_, p_71847_4_), 0).isCancelled())
            {
                return;
            }

            // CraftBukkit end
            this.func_71897_c(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_, p_71847_1_.func_72805_g(p_71847_2_, p_71847_3_, p_71847_4_), 0);
            p_71847_1_.func_94571_i(p_71847_2_, p_71847_3_, p_71847_4_);
        }
    }
}
