package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
public class ItemBed extends Item
{
    public ItemBed(int p_i3620_1_)
    {
        super(p_i3620_1_);
        this.func_77637_a(CreativeTabs.field_78031_c);
    }

    public boolean func_77648_a(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
        final int clickedX = p_77648_4_, clickedY = p_77648_5_, clickedZ = p_77648_6_; // CraftBukkit

        if (p_77648_3_.field_72995_K)
        {
            return true;
        }
        else if (p_77648_7_ != 1)
        {
            return false;
        }
        else
        {
            ++p_77648_5_;
            BlockBed blockbed = (BlockBed)Block.field_71959_S;
            int i1 = MathHelper.func_76128_c((double)(p_77648_2_.field_70177_z * 4.0F / 360.0F) + 0.5D) & 3;
            byte b0 = 0;
            byte b1 = 0;

            if (i1 == 0)
            {
                b1 = 1;
            }

            if (i1 == 1)
            {
                b0 = -1;
            }

            if (i1 == 2)
            {
                b1 = -1;
            }

            if (i1 == 3)
            {
                b0 = 1;
            }

            if (p_77648_2_.func_82247_a(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_) && p_77648_2_.func_82247_a(p_77648_4_ + b0, p_77648_5_, p_77648_6_ + b1, p_77648_7_, p_77648_1_))
            {
                if (p_77648_3_.func_72799_c(p_77648_4_, p_77648_5_, p_77648_6_) && p_77648_3_.func_72799_c(p_77648_4_ + b0, p_77648_5_, p_77648_6_ + b1) && p_77648_3_.func_72797_t(p_77648_4_, p_77648_5_ - 1, p_77648_6_) && p_77648_3_.func_85174_u(p_77648_4_ + b0, p_77648_5_ - 1, p_77648_6_ + b1))
                {
                    // CraftBukkit start
                    // world.setTypeIdAndData(i, j, k, blockbed.id, i1, 3);
                    if (!ItemBlock.processBlockPlace(p_77648_3_, p_77648_2_, null, p_77648_4_, p_77648_5_, p_77648_6_, blockbed.field_71990_ca, i1, clickedX, clickedY, clickedZ))
                    {
                        return false;
                    }

                    // CraftBukkit end

                    if (p_77648_3_.func_72798_a(p_77648_4_, p_77648_5_, p_77648_6_) == blockbed.field_71990_ca)
                    {
                        p_77648_3_.func_72832_d(p_77648_4_ + b0, p_77648_5_, p_77648_6_ + b1, blockbed.field_71990_ca, i1 + 8, 3);
                    }

                    --p_77648_1_.field_77994_a;
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
    }
}
