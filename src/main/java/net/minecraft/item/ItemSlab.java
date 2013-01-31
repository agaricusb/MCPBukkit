package net.minecraft.item;

import net.minecraft.block.BlockHalfSlab;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
public class ItemSlab extends ItemBlock
{
    private final boolean field_77891_a;
    private final BlockHalfSlab field_77889_b;
    private final BlockHalfSlab field_77890_c;

    public ItemSlab(int p_i3689_1_, BlockHalfSlab p_i3689_2_, BlockHalfSlab p_i3689_3_, boolean p_i3689_4_)
    {
        super(p_i3689_1_);
        this.field_77889_b = p_i3689_2_;
        this.field_77890_c = p_i3689_3_;
        this.field_77891_a = p_i3689_4_;
        this.func_77656_e(0);
        this.func_77627_a(true);
    }

    public int func_77647_b(int p_77647_1_)
    {
        return p_77647_1_;
    }

    public String func_77667_c(ItemStack p_77667_1_)
    {
        return this.field_77889_b.func_72240_d(p_77667_1_.func_77960_j());
    }

    public boolean func_77648_a(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
        final int clickedX = p_77648_4_, clickedY = p_77648_5_, clickedZ = p_77648_6_; // CraftBukkit

        if (this.field_77891_a)
        {
            return super.func_77648_a(p_77648_1_, p_77648_2_, p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_);
        }
        else if (p_77648_1_.field_77994_a == 0)
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
            int j1 = p_77648_3_.func_72805_g(p_77648_4_, p_77648_5_, p_77648_6_);
            int k1 = j1 & 7;
            boolean flag = (j1 & 8) != 0;

            if ((p_77648_7_ == 1 && !flag || p_77648_7_ == 0 && flag) && i1 == this.field_77889_b.field_71990_ca && k1 == p_77648_1_.func_77960_j())
            {
                // CraftBukkit start - world.setTypeIdAndData -> processBlockPlace()
                if (p_77648_3_.func_72855_b(this.field_77890_c.func_71872_e(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_)) && processBlockPlace(p_77648_3_, p_77648_2_, null, p_77648_4_, p_77648_5_, p_77648_6_, this.field_77890_c.field_71990_ca, k1, clickedX, clickedY, clickedZ))
                {
                    // world.makeSound((double) ((float) i + 0.5F), (double) ((float) j + 0.5F), (double) ((float) k + 0.5F), this.c.stepSound.getPlaceSound(), (this.c.stepSound.getVolume1() + 1.0F) / 2.0F, this.c.stepSound.getVolume2() * 0.8F);
                    // CraftBukkit end
                    --p_77648_1_.field_77994_a;
                }

                return true;
            }
            else
            {
                return this.func_77888_a(p_77648_1_, p_77648_2_, p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_) ? true : super.func_77648_a(p_77648_1_, p_77648_2_, p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_);
            }
        }
    }

    private boolean func_77888_a(ItemStack p_77888_1_, EntityPlayer p_77888_2_, World p_77888_3_, int p_77888_4_, int p_77888_5_, int p_77888_6_, int p_77888_7_)
    {
        final int clickedX = p_77888_4_, clickedY = p_77888_5_, clickedZ = p_77888_6_; // CraftBukkit

        if (p_77888_7_ == 0)
        {
            --p_77888_5_;
        }

        if (p_77888_7_ == 1)
        {
            ++p_77888_5_;
        }

        if (p_77888_7_ == 2)
        {
            --p_77888_6_;
        }

        if (p_77888_7_ == 3)
        {
            ++p_77888_6_;
        }

        if (p_77888_7_ == 4)
        {
            --p_77888_4_;
        }

        if (p_77888_7_ == 5)
        {
            ++p_77888_4_;
        }

        int i1 = p_77888_3_.func_72798_a(p_77888_4_, p_77888_5_, p_77888_6_);
        int j1 = p_77888_3_.func_72805_g(p_77888_4_, p_77888_5_, p_77888_6_);
        int k1 = j1 & 7;

        if (i1 == this.field_77889_b.field_71990_ca && k1 == p_77888_1_.func_77960_j())
        {
            // CraftBukkit start - world.setTypeIdAndData -> processBlockPlace()
            if (p_77888_3_.func_72855_b(this.field_77890_c.func_71872_e(p_77888_3_, p_77888_4_, p_77888_5_, p_77888_6_)) && processBlockPlace(p_77888_3_, p_77888_2_, null, p_77888_4_, p_77888_5_, p_77888_6_, this.field_77890_c.field_71990_ca, k1, clickedX, clickedY, clickedZ))
            {
                // world.makeSound((double) ((float) i + 0.5F), (double) ((float) j + 0.5F), (double) ((float) k + 0.5F, this.c.stepSound.getPlaceSound(), (this.c.stepSound.getVolume1() + 1.0F) / 2.0F, this.c.stepSound.getVolume2() * 0.8F);
                // CraftBukkit end
                --p_77888_1_.field_77994_a;
            }

            return true;
        }
        else
        {
            return false;
        }
    }
}
