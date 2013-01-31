package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
public class ItemBlock extends Item
{
    private int field_77885_a;

    public ItemBlock(int p_i3690_1_)
    {
        super(p_i3690_1_);
        this.field_77885_a = p_i3690_1_ + 256;
        this.func_77665_c(Block.field_71973_m[p_i3690_1_ + 256].func_71851_a(2));
    }

    public int func_77883_f()
    {
        return this.field_77885_a;
    }

    public boolean func_77648_a(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
        final int clickedX = p_77648_4_, clickedY = p_77648_5_, clickedZ = p_77648_6_;
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

        if (p_77648_1_.field_77994_a == 0)
        {
            return false;
        }
        else if (!p_77648_2_.func_82247_a(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_))
        {
            return false;
        }
        else if (p_77648_5_ == 255 && Block.field_71973_m[this.field_77885_a].field_72018_cp.func_76220_a())
        {
            return false;
        }
        else if (p_77648_3_.func_72931_a(this.field_77885_a, p_77648_4_, p_77648_5_, p_77648_6_, false, p_77648_7_, p_77648_2_))
        {
            Block block = Block.field_71973_m[this.field_77885_a];
            int j1 = this.func_77647_b(p_77648_1_.func_77960_j());
            int k1 = Block.field_71973_m[this.field_77885_a].func_85104_a(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_, j1);
            // CraftBukkit start - redirect to common function handler
            /*
            if (world.setTypeIdAndData(i, j, k, this.id, k1)) {
                if (world.getTypeId(i, j, k) == this.id) {
                    Block.byId[this.id].postPlace(world, i, j, k, entityplayer);
                    Block.byId[this.id].postPlace(world, i, j, k, k1);
                }

                world.makeSound((double) ((float) i + 0.5F), (double) ((float) j + 0.5F), (double) ((float) k + 0.5F), block.stepSound.getPlaceSound(), (block.stepSound.getVolume1() + 1.0F) / 2.0F, block.stepSound.getVolume2() * 0.8F);
                --itemstack.count;
            }
            */
            return processBlockPlace(p_77648_3_, p_77648_2_, p_77648_1_, p_77648_4_, p_77648_5_, p_77648_6_, this.field_77885_a, k1, clickedX, clickedY, clickedZ);
            // CraftBukkit end
        }
        else
        {
            return false;
        }
    }

    // CraftBukkit start - add method to process block placement
    static boolean processBlockPlace(final World world, final EntityPlayer entityplayer, final ItemStack itemstack, final int x, final int y, final int z, final int id, final int data, final int clickedX, final int clickedY, final int clickedZ)
    {
        org.bukkit.block.BlockState blockstate = org.bukkit.craftbukkit.block.CraftBlockState.getBlockState(world, x, y, z);
        world.field_73014_t = true;
        world.callingPlaceEvent = true;
        world.func_72961_c(x, y, z, id, data);
        org.bukkit.event.block.BlockPlaceEvent event = org.bukkit.craftbukkit.event.CraftEventFactory.callBlockPlaceEvent(world, entityplayer, blockstate, clickedX, clickedY, clickedZ);

        if (event.isCancelled() || !event.canBuild())
        {
            blockstate.update(true);
            world.field_73014_t = false;
            world.callingPlaceEvent = false;
            return false;
        }

        world.field_73014_t = false;
        world.callingPlaceEvent = false;
        int newId = world.func_72798_a(x, y, z);
        int newData = world.func_72805_g(x, y, z);
        Block block = Block.field_71973_m[newId];

        if (block != null && !(block instanceof BlockContainer))   // Containers get placed automatically
        {
            block.func_71861_g(world, x, y, z);
        }

        world.func_72851_f(x, y, z, newId);

        // Skulls don't get block data applied to them
        if (block != null && block != Block.field_82512_cj)
        {
            block.func_71860_a(world, x, y, z, entityplayer);
            block.func_85105_g(world, x, y, z, newData);
            world.func_72908_a((double)((float) x + 0.5F), (double)((float) y + 0.5F), (double)((float) z + 0.5F), block.field_72020_cn.func_82593_b(), (block.field_72020_cn.func_72677_b() + 1.0F) / 2.0F, block.field_72020_cn.func_72678_c() * 0.8F);
        }

        if (itemstack != null)
        {
            --itemstack.field_77994_a;
        }

        return true;
    }
    // CraftBukkit end

    public String func_77667_c(ItemStack p_77667_1_)
    {
        return Block.field_71973_m[this.field_77885_a].func_71917_a();
    }

    public String func_77658_a()
    {
        return Block.field_71973_m[this.field_77885_a].func_71917_a();
    }
}
