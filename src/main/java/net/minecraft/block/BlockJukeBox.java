package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
public class BlockJukeBox extends BlockContainer
{
    protected BlockJukeBox(int p_i3985_1_, int p_i3985_2_)
    {
        super(p_i3985_1_, p_i3985_2_, Material.field_76245_d);
        this.func_71849_a(CreativeTabs.field_78031_c);
    }

    public int func_71851_a(int p_71851_1_)
    {
        return this.field_72059_bZ + (p_71851_1_ == 1 ? 1 : 0);
    }

    public boolean func_71903_a(World p_71903_1_, int p_71903_2_, int p_71903_3_, int p_71903_4_, EntityPlayer p_71903_5_, int p_71903_6_, float p_71903_7_, float p_71903_8_, float p_71903_9_)
    {
        if (p_71903_1_.func_72805_g(p_71903_2_, p_71903_3_, p_71903_4_) == 0)
        {
            return false;
        }
        else
        {
            this.func_72276_j_(p_71903_1_, p_71903_2_, p_71903_3_, p_71903_4_);
            return true;
        }
    }

    public void func_85106_a(World p_85106_1_, int p_85106_2_, int p_85106_3_, int p_85106_4_, ItemStack p_85106_5_)
    {
        if (!p_85106_1_.field_72995_K)
        {
            TileEntityRecordPlayer tileentityrecordplayer = (TileEntityRecordPlayer)p_85106_1_.func_72796_p(p_85106_2_, p_85106_3_, p_85106_4_);

            if (tileentityrecordplayer != null)
            {
                tileentityrecordplayer.field_70417_a = p_85106_5_.func_77946_l();
                tileentityrecordplayer.field_70417_a.field_77994_a = 1; // CraftBukkit - There can be only one
                tileentityrecordplayer.func_70296_d();
                p_85106_1_.func_72921_c(p_85106_2_, p_85106_3_, p_85106_4_, 1);
            }
        }
    }

    public void func_72276_j_(World p_72276_1_, int p_72276_2_, int p_72276_3_, int p_72276_4_)
    {
        if (!p_72276_1_.field_72995_K)
        {
            TileEntityRecordPlayer tileentityrecordplayer = (TileEntityRecordPlayer)p_72276_1_.func_72796_p(p_72276_2_, p_72276_3_, p_72276_4_);

            if (tileentityrecordplayer != null)
            {
                ItemStack itemstack = tileentityrecordplayer.field_70417_a;

                if (itemstack != null)
                {
                    p_72276_1_.func_72926_e(1005, p_72276_2_, p_72276_3_, p_72276_4_, 0);
                    p_72276_1_.func_72934_a((String)null, p_72276_2_, p_72276_3_, p_72276_4_);
                    tileentityrecordplayer.field_70417_a = null;
                    tileentityrecordplayer.func_70296_d();
                    p_72276_1_.func_72921_c(p_72276_2_, p_72276_3_, p_72276_4_, 0);
                    float f = 0.7F;
                    double d0 = (double)(p_72276_1_.field_73012_v.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
                    double d1 = (double)(p_72276_1_.field_73012_v.nextFloat() * f) + (double)(1.0F - f) * 0.2D + 0.6D;
                    double d2 = (double)(p_72276_1_.field_73012_v.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
                    ItemStack itemstack1 = itemstack.func_77946_l();
                    EntityItem entityitem = new EntityItem(p_72276_1_, (double)p_72276_2_ + d0, (double)p_72276_3_ + d1, (double)p_72276_4_ + d2, itemstack1);
                    entityitem.field_70293_c = 10;
                    p_72276_1_.func_72838_d(entityitem);
                }
            }
        }
    }

    public void func_71852_a(World p_71852_1_, int p_71852_2_, int p_71852_3_, int p_71852_4_, int p_71852_5_, int p_71852_6_)
    {
        this.func_72276_j_(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_);
        super.func_71852_a(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_, p_71852_5_, p_71852_6_);
    }

    public void func_71914_a(World p_71914_1_, int p_71914_2_, int p_71914_3_, int p_71914_4_, int p_71914_5_, float p_71914_6_, int p_71914_7_)
    {
        if (!p_71914_1_.field_72995_K)
        {
            super.func_71914_a(p_71914_1_, p_71914_2_, p_71914_3_, p_71914_4_, p_71914_5_, p_71914_6_, 0);
        }
    }

    public TileEntity func_72274_a(World p_72274_1_)
    {
        return new TileEntityRecordPlayer();
    }
}
