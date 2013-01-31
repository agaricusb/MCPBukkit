package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
public class ItemReed extends Item
{
    private int field_77830_a;

    public ItemReed(int p_i3691_1_, Block p_i3691_2_)
    {
        super(p_i3691_1_);
        this.field_77830_a = p_i3691_2_.field_71990_ca;
    }

    public boolean func_77648_a(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
        final int clickedX = p_77648_4_, clickedY = p_77648_5_, clickedZ = p_77648_6_; // CraftBukkit
        int i1 = p_77648_3_.func_72798_a(p_77648_4_, p_77648_5_, p_77648_6_);

        if (i1 == Block.field_72037_aS.field_71990_ca)
        {
            p_77648_7_ = 1;
        }
        else if (i1 != Block.field_71998_bu.field_71990_ca && i1 != Block.field_71962_X.field_71990_ca && i1 != Block.field_71961_Y.field_71990_ca)
        {
            if (p_77648_7_ == 0)
            {
                --p_77648_5_;
            }

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
        }

        if (!p_77648_2_.func_82247_a(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_))
        {
            return false;
        }
        else if (p_77648_1_.field_77994_a == 0)
        {
            return false;
        }
        else
        {
            if (p_77648_3_.func_72931_a(this.field_77830_a, p_77648_4_, p_77648_5_, p_77648_6_, false, p_77648_7_, (Entity)null))
            {
                Block block = Block.field_71973_m[this.field_77830_a];
                int j1 = block.func_85104_a(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_, 0);
                // CraftBukkit start - redirect to common handler
                ItemBlock.processBlockPlace(p_77648_3_, p_77648_2_, p_77648_1_, p_77648_4_, p_77648_5_, p_77648_6_, this.field_77830_a, j1, clickedX, clickedY, clickedZ);
                /*
                if (world.setTypeIdAndData(i, j, k, this.id, j1)) {
                    if (world.getTypeId(i, j, k) == this.id) {
                        Block.byId[this.id].postPlace(world, i, j, k, entityhuman);
                        Block.byId[this.id].postPlace(world, i, j, k, j1);
                    }

                    world.makeSound((double) ((float) i + 0.5F), (double) ((float) j + 0.5F), (double) ((float) k + 0.5F), block.stepSound.getPlaceSound(), (block.stepSound.getVolume1() + 1.0F) / 2.0F, block.stepSound.getVolume2() * 0.8F);
                    --itemstack.count;
                }
                */
                // CraftBukkit end
            }

            return true;
        }
    }
}
