package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSkull;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
public class ItemSkull extends Item
{
    private static final String[] field_82807_a = new String[] {"skeleton", "wither", "zombie", "char", "creeper"};
    public static final String[] field_94587_a = new String[] {"skull_skeleton", "skull_wither", "skull_zombie", "skull_char", "skull_creeper"};

    public ItemSkull(int p_i5088_1_)
    {
        super(p_i5088_1_);
        this.func_77637_a(CreativeTabs.field_78031_c);
        this.func_77656_e(0);
        this.func_77627_a(true);
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
            else if (!Block.field_82512_cj.func_71930_b(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_))
            {
                return false;
            }
            else
            {
                // CraftBukkit start - handle in ItemBlock
                // world.setTypeIdAndData(i, j, k, Block.SKULL.id, l, 2);
                if (!ItemBlock.processBlockPlace(p_77648_3_, p_77648_2_, null, p_77648_4_, p_77648_5_, p_77648_6_, Block.field_82512_cj.field_71990_ca, p_77648_7_, clickedX, clickedY, clickedZ))
                {
                    return false;
                }

                p_77648_7_ = p_77648_3_.func_72805_g(p_77648_4_, p_77648_5_, p_77648_6_);
                // CraftBukkit end
                int i1 = 0;

                if (p_77648_7_ == 1)
                {
                    i1 = MathHelper.func_76128_c((double)(p_77648_2_.field_70177_z * 16.0F / 360.0F) + 0.5D) & 15;
                }

                TileEntity tileentity = p_77648_3_.func_72796_p(p_77648_4_, p_77648_5_, p_77648_6_);

                if (tileentity != null && tileentity instanceof TileEntitySkull)
                {
                    String s = "";

                    if (p_77648_1_.func_77942_o() && p_77648_1_.func_77978_p().func_74764_b("SkullOwner"))
                    {
                        s = p_77648_1_.func_77978_p().func_74779_i("SkullOwner");
                    }

                    ((TileEntitySkull)tileentity).func_82118_a(p_77648_1_.func_77960_j(), s);
                    ((TileEntitySkull)tileentity).func_82116_a(i1);
                    ((BlockSkull)Block.field_82512_cj).func_82529_a(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, (TileEntitySkull)tileentity);
                }

                --p_77648_1_.field_77994_a;
                return true;
            }
        }
    }

    public int func_77647_b(int p_77647_1_)
    {
        return p_77647_1_;
    }

    public String func_77667_c(ItemStack p_77667_1_)
    {
        int i = p_77667_1_.func_77960_j();

        if (i < 0 || i >= field_82807_a.length)
        {
            i = 0;
        }

        return super.func_77658_a() + "." + field_82807_a[i];
    }

    public String func_77628_j(ItemStack p_77628_1_)
    {
        return p_77628_1_.func_77960_j() == 3 && p_77628_1_.func_77942_o() && p_77628_1_.func_77978_p().func_74764_b("SkullOwner") ? StatCollector.func_74837_a("item.skull.player.name", new Object[] {p_77628_1_.func_77978_p().func_74779_i("SkullOwner")}): super.func_77628_j(p_77628_1_);
    }
}
