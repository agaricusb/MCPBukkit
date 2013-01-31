package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
public class ItemLilyPad extends ItemColored
{
    public ItemLilyPad(int p_i3694_1_)
    {
        super(p_i3694_1_, false);
    }

    public ItemStack func_77659_a(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
    {
        MovingObjectPosition movingobjectposition = this.func_77621_a(p_77659_2_, p_77659_3_, true);

        if (movingobjectposition == null)
        {
            return p_77659_1_;
        }
        else
        {
            if (movingobjectposition.field_72313_a == EnumMovingObjectType.TILE)
            {
                int i = movingobjectposition.field_72311_b;
                int j = movingobjectposition.field_72312_c;
                int k = movingobjectposition.field_72309_d;
                final int clickedX = i, clickedY = j, clickedZ = k; // CraftBukkit

                if (!p_77659_2_.func_72962_a(p_77659_3_, i, j, k))
                {
                    return p_77659_1_;
                }

                if (!p_77659_3_.func_82247_a(i, j, k, movingobjectposition.field_72310_e, p_77659_1_))
                {
                    return p_77659_1_;
                }

                if (p_77659_2_.func_72803_f(i, j, k) == Material.field_76244_g && p_77659_2_.func_72805_g(i, j, k) == 0 && p_77659_2_.func_72799_c(i, j + 1, k))
                {
                    // CraftBukkit start - waterlily
                    // world.setTypeId(i, j + 1, k, Block.WATER_LILY.id);
                    if (!processBlockPlace(p_77659_2_, p_77659_3_, null, i, j + 1, k, Block.field_71991_bz.field_71990_ca, 0, clickedX, clickedY, clickedZ))
                    {
                        return p_77659_1_;
                    }

                    // CraftBukkit end

                    if (!p_77659_3_.field_71075_bZ.field_75098_d)
                    {
                        --p_77659_1_.field_77994_a;
                    }
                }
            }

            return p_77659_1_;
        }
    }
}
