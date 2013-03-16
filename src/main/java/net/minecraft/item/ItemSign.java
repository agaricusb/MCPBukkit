package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
public class ItemSign extends Item
{
    public ItemSign(int p_i3685_1_)
    {
        super(p_i3685_1_);
        this.field_77777_bU = 16;
        this.func_77637_a(CreativeTabs.field_78031_c);
    }

    public boolean func_77648_a(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
        final int clickedX = p_77648_4_, clickedY = p_77648_5_, clickedZ = p_77648_6_; // CraftBukkit

        if (p_77648_7_ == 0)
        {
            return false;
        }
        else if (!p_77648_3_.func_72803_f(p_77648_4_, p_77648_5_, p_77648_6_).func_76220_a())
        {
            return false;
        }
        else
        {
            if (p_77648_7_ == 1)
            {
                ++p_77648_5_;
            }

            if (p_77648_7_ == 2)
            {
                --p_77648_6_;
            }

            if (p_77648_7_ == 3)
            {
                ++p_77648_6_;
            }

            if (p_77648_7_ == 4)
            {
                --p_77648_4_;
            }

            if (p_77648_7_ == 5)
            {
                ++p_77648_4_;
            }

            if (!p_77648_2_.func_82247_a(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_))
            {
                return false;
            }
            else if (!Block.field_72053_aD.func_71930_b(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_))
            {
                return false;
            }
            else
            {
                // CraftBukkit start
                final Block block;

                if (p_77648_7_ == 1)
                {
                    int i1 = MathHelper.func_76128_c((double)((p_77648_2_.field_70177_z + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15;
                    // world.setTypeIdAndData(i, j, k, Block.SIGN_POST.id, i1, 2);
                    block = Block.field_72053_aD;
                    p_77648_7_ = i1;
                }
                else
                {
                    // world.setTypeIdAndData(i, j, k, Block.WALL_SIGN.id, l, 2);
                    block = Block.field_72042_aI;
                }

                if (!ItemBlock.processBlockPlace(p_77648_3_, p_77648_2_, null, p_77648_4_, p_77648_5_, p_77648_6_, block.field_71990_ca, p_77648_7_, clickedX, clickedY, clickedZ))
                {
                    return false;
                }

                // CraftBukkit end
                --p_77648_1_.field_77994_a;
                TileEntitySign tileentitysign = (TileEntitySign)p_77648_3_.func_72796_p(p_77648_4_, p_77648_5_, p_77648_6_);

                if (tileentitysign != null)
                {
                    p_77648_2_.func_71014_a((TileEntity) tileentitysign);
                }

                return true;
            }
        }
    }
}
