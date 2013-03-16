package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
public class ItemSnow extends ItemBlockWithMetadata
{
    public ItemSnow(int p_i10058_1_, Block p_i10058_2_)
    {
        super(p_i10058_1_, p_i10058_2_);
    }

    public boolean func_77648_a(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
        final int clickedX = p_77648_4_, clickedY = p_77648_5_, clickedZ = p_77648_6_;

        if (p_77648_1_.field_77994_a == 0)
        {
            return false;
        }
        else if (!p_77648_2_.func_82247_a(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_))
        {
            return false;
        }
        else
        {
            int i1 = p_77648_3_.func_72798_a(p_77648_4_, p_77648_5_, p_77648_6_);

            if (i1 == Block.field_72037_aS.field_71990_ca)
            {
                Block block = Block.field_71973_m[this.func_77883_f()];
                int j1 = p_77648_3_.func_72805_g(p_77648_4_, p_77648_5_, p_77648_6_);
                int k1 = j1 & 7;

                // CraftBukkit start - redirect to common handler
                if (k1 <= 6 && p_77648_3_.func_72855_b(block.func_71872_e(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_)) && ItemBlock.processBlockPlace(p_77648_3_, p_77648_2_, p_77648_1_, p_77648_4_, p_77648_5_, p_77648_6_, Block.field_72037_aS.field_71990_ca, k1 + 1 | j1 & -8, clickedX, clickedY, clickedZ))
                {
                    return true;
                }

                /*
                if (k1 <= 6 && world.b(block.b(world, i, j, k)) && world.setData(i, j, k, k1 + 1 | j1 & -8, 2)) {
                    world.makeSound((double) ((float) i + 0.5F), (double) ((float) j + 0.5F), (double) ((float) k + 0.5F), block.stepSound.getPlaceSound(), (block.stepSound.getVolume1() + 1.0F) / 2.0F, block.stepSound.getVolume2() * 0.8F);
                    --itemstack.count;
                    return true;
                }
                */
                // CraftBukkit end
            }

            return super.func_77648_a(p_77648_1_, p_77648_2_, p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_);
        }
    }
}
