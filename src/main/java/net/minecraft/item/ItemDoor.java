package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet53BlockChange;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
public class ItemDoor extends Item
{
    private Material field_77870_a;

    public ItemDoor(int p_i3644_1_, Material p_i3644_2_)
    {
        super(p_i3644_1_);
        this.field_77870_a = p_i3644_2_;
        this.field_77777_bU = 1;
        this.func_77637_a(CreativeTabs.field_78028_d);
    }

    public boolean func_77648_a(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
        final int clickedX = p_77648_4_, clickedY = p_77648_5_, clickedZ = p_77648_6_; // CraftBukkit

        if (p_77648_7_ != 1)
        {
            return false;
        }
        else
        {
            ++p_77648_5_;
            Block block;

            if (this.field_77870_a == Material.field_76245_d)
            {
                block = Block.field_72054_aE;
            }
            else
            {
                block = Block.field_72045_aL;
            }

            if (p_77648_2_.func_82247_a(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_) && p_77648_2_.func_82247_a(p_77648_4_, p_77648_5_ + 1, p_77648_6_, p_77648_7_, p_77648_1_))
            {
                if (!block.func_71930_b(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_))
                {
                    return false;
                }
                else
                {
                    int i1 = MathHelper.func_76128_c((double)((p_77648_2_.field_70177_z + 180.0F) * 4.0F / 360.0F) - 0.5D) & 3;

                    // CraftBukkit start
                    if (!place(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, i1, block, p_77648_2_, clickedX, clickedY, clickedZ))
                    {
                        return false;
                    }

                    // CraftBukkit end
                    --p_77648_1_.field_77994_a;
                    return true;
                }
            }
            else
            {
                return false;
            }
        }
    }

    public static void func_77869_a(World p_77869_0_, int p_77869_1_, int p_77869_2_, int p_77869_3_, int p_77869_4_, Block p_77869_5_)
    {
        // CraftBukkit start
        place(p_77869_0_, p_77869_1_, p_77869_2_, p_77869_3_, p_77869_4_, p_77869_5_, null, p_77869_1_, p_77869_2_, p_77869_3_);
    }

    public static boolean place(World p_77869_0_, int p_77869_1_, int p_77869_2_, int p_77869_3_, int p_77869_4_, Block p_77869_5_, EntityPlayer entityplayer, int clickedX, int clickedY, int clickedZ)
    {
        // CraftBukkit end
        byte b0 = 0;
        byte b1 = 0;

        if (p_77869_4_ == 0)
        {
            b1 = 1;
        }

        if (p_77869_4_ == 1)
        {
            b0 = -1;
        }

        if (p_77869_4_ == 2)
        {
            b1 = -1;
        }

        if (p_77869_4_ == 3)
        {
            b0 = 1;
        }

        int i1 = (p_77869_0_.func_72809_s(p_77869_1_ - b0, p_77869_2_, p_77869_3_ - b1) ? 1 : 0) + (p_77869_0_.func_72809_s(p_77869_1_ - b0, p_77869_2_ + 1, p_77869_3_ - b1) ? 1 : 0);
        int j1 = (p_77869_0_.func_72809_s(p_77869_1_ + b0, p_77869_2_, p_77869_3_ + b1) ? 1 : 0) + (p_77869_0_.func_72809_s(p_77869_1_ + b0, p_77869_2_ + 1, p_77869_3_ + b1) ? 1 : 0);
        boolean flag = p_77869_0_.func_72798_a(p_77869_1_ - b0, p_77869_2_, p_77869_3_ - b1) == p_77869_5_.field_71990_ca || p_77869_0_.func_72798_a(p_77869_1_ - b0, p_77869_2_ + 1, p_77869_3_ - b1) == p_77869_5_.field_71990_ca;
        boolean flag1 = p_77869_0_.func_72798_a(p_77869_1_ + b0, p_77869_2_, p_77869_3_ + b1) == p_77869_5_.field_71990_ca || p_77869_0_.func_72798_a(p_77869_1_ + b0, p_77869_2_ + 1, p_77869_3_ + b1) == p_77869_5_.field_71990_ca;
        boolean flag2 = false;

        if (flag && !flag1)
        {
            flag2 = true;
        }
        else if (j1 > i1)
        {
            flag2 = true;
        }

        // CraftBukkit start
        if (entityplayer != null)
        {
            if (!ItemBlock.processBlockPlace(p_77869_0_, entityplayer, null, p_77869_1_, p_77869_2_, p_77869_3_, p_77869_5_.field_71990_ca, p_77869_4_, clickedX, clickedY, clickedZ))
            {
                ((EntityPlayerMP) entityplayer).field_71135_a.func_72567_b(new Packet53BlockChange(p_77869_1_, p_77869_2_ + 1, p_77869_3_, p_77869_0_));
                return false;
            }

            if (p_77869_0_.func_72798_a(p_77869_1_, p_77869_2_, p_77869_3_) != p_77869_5_.field_71990_ca)
            {
                ((EntityPlayerMP) entityplayer).field_71135_a.func_72567_b(new Packet53BlockChange(p_77869_1_, p_77869_2_ + 1, p_77869_3_, p_77869_0_));
                return true;
            }
        }
        else
        {
            p_77869_0_.func_72832_d(p_77869_1_, p_77869_2_, p_77869_3_, p_77869_5_.field_71990_ca, p_77869_4_, 2);
        }

        // CraftBukkit end
        p_77869_0_.func_72832_d(p_77869_1_, p_77869_2_ + 1, p_77869_3_, p_77869_5_.field_71990_ca, 8 | (flag2 ? 1 : 0), 2);
        p_77869_0_.func_72898_h(p_77869_1_, p_77869_2_, p_77869_3_, p_77869_5_.field_71990_ca);
        p_77869_0_.func_72898_h(p_77869_1_, p_77869_2_ + 1, p_77869_3_, p_77869_5_.field_71990_ca);
        return true; // CraftBukkit
    }
}
