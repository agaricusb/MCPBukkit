package net.minecraft.item;

// CraftBukkit start
import org.bukkit.entity.Player;
import org.bukkit.event.entity.SheepDyeWoolEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCloth;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockMushroom;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockStem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
// CraftBukkit end

public class ItemDye extends Item
{
    public static final String[] field_77860_a = new String[] {"black", "red", "green", "brown", "blue", "purple", "cyan", "silver", "gray", "pink", "lime", "yellow", "lightBlue", "magenta", "orange", "white"};
    public static final int[] field_77859_b = new int[] {1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 11250603, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 15790320};

    public ItemDye(int p_i3645_1_)
    {
        super(p_i3645_1_);
        this.func_77627_a(true);
        this.func_77656_e(0);
        this.func_77637_a(CreativeTabs.field_78035_l);
    }

    public String func_77667_c(ItemStack p_77667_1_)
    {
        int i = MathHelper.func_76125_a(p_77667_1_.func_77960_j(), 0, 15);
        return super.func_77658_a() + "." + field_77860_a[i];
    }

    public boolean func_77648_a(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
        if (!p_77648_2_.func_82247_a(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_))
        {
            return false;
        }
        else
        {
            int i1;
            int j1;
            int k1;

            if (p_77648_1_.func_77960_j() == 15)
            {
                i1 = p_77648_3_.func_72798_a(p_77648_4_, p_77648_5_, p_77648_6_);

                if (i1 == Block.field_71987_y.field_71990_ca)
                {
                    if (!p_77648_3_.field_72995_K)
                    {
                        // CraftBukkit start
                        Player player = (p_77648_2_ instanceof EntityPlayerMP) ? (Player) p_77648_2_.getBukkitEntity() : null;
                        ((BlockSapling) Block.field_71987_y).func_72269_c(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_3_.field_73012_v, true, player, p_77648_1_);
                        //--itemstack.count; - called later if the bonemeal attempt was succesful
                        // CraftBukkit end
                    }

                    return true;
                }

                if (i1 == Block.field_72109_af.field_71990_ca || i1 == Block.field_72103_ag.field_71990_ca)
                {
                    // CraftBukkit start
                    if (!p_77648_3_.field_72995_K)
                    {
                        Player player = (p_77648_2_ instanceof EntityPlayerMP) ? (Player) p_77648_2_.getBukkitEntity() : null;
                        ((BlockMushroom) Block.field_71973_m[i1]).func_72271_c(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_3_.field_73012_v, true, player, p_77648_1_);
                        //--itemstack.count; - called later if the bonemeal attempt was succesful
                        // CraftBukkit end
                    }

                    return true;
                }

                if (i1 == Block.field_71999_bt.field_71990_ca || i1 == Block.field_71996_bs.field_71990_ca)
                {
                    if (p_77648_3_.func_72805_g(p_77648_4_, p_77648_5_, p_77648_6_) == 7)
                    {
                        return false;
                    }

                    if (!p_77648_3_.field_72995_K)
                    {
                        ((BlockStem)Block.field_71973_m[i1]).func_72264_l(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_);
                        --p_77648_1_.field_77994_a;
                    }

                    return true;
                }

                if (i1 > 0 && Block.field_71973_m[i1] instanceof BlockCrops)
                {
                    if (p_77648_3_.func_72805_g(p_77648_4_, p_77648_5_, p_77648_6_) == 7)
                    {
                        return false;
                    }

                    if (!p_77648_3_.field_72995_K)
                    {
                        ((BlockCrops)Block.field_71973_m[i1]).func_72272_c_(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_);
                        --p_77648_1_.field_77994_a;
                    }

                    return true;
                }

                if (i1 == Block.field_72086_bP.field_71990_ca)
                {
                    if (!p_77648_3_.field_72995_K)
                    {
                        p_77648_3_.func_72921_c(p_77648_4_, p_77648_5_, p_77648_6_, 8 | BlockDirectional.func_72217_d(p_77648_3_.func_72805_g(p_77648_4_, p_77648_5_, p_77648_6_)));
                        --p_77648_1_.field_77994_a;
                    }

                    return true;
                }

                if (i1 == Block.field_71980_u.field_71990_ca)
                {
                    if (!p_77648_3_.field_72995_K)
                    {
                        --p_77648_1_.field_77994_a;
                        label133:

                        for (j1 = 0; j1 < 128; ++j1)
                        {
                            k1 = p_77648_4_;
                            int l1 = p_77648_5_ + 1;
                            int i2 = p_77648_6_;

                            for (int j2 = 0; j2 < j1 / 16; ++j2)
                            {
                                k1 += field_77697_d.nextInt(3) - 1;
                                l1 += (field_77697_d.nextInt(3) - 1) * field_77697_d.nextInt(3) / 2;
                                i2 += field_77697_d.nextInt(3) - 1;

                                if (p_77648_3_.func_72798_a(k1, l1 - 1, i2) != Block.field_71980_u.field_71990_ca || p_77648_3_.func_72809_s(k1, l1, i2))
                                {
                                    continue label133;
                                }
                            }

                            if (p_77648_3_.func_72798_a(k1, l1, i2) == 0)
                            {
                                if (field_77697_d.nextInt(10) != 0)
                                {
                                    if (Block.field_71962_X.func_71854_d(p_77648_3_, k1, l1, i2))
                                    {
                                        p_77648_3_.func_72832_d(k1, l1, i2, Block.field_71962_X.field_71990_ca, 1);
                                    }
                                }
                                else if (field_77697_d.nextInt(3) != 0)
                                {
                                    if (Block.field_72097_ad.func_71854_d(p_77648_3_, k1, l1, i2))
                                    {
                                        p_77648_3_.func_72859_e(k1, l1, i2, Block.field_72097_ad.field_71990_ca);
                                    }
                                }
                                else if (Block.field_72107_ae.func_71854_d(p_77648_3_, k1, l1, i2))
                                {
                                    p_77648_3_.func_72859_e(k1, l1, i2, Block.field_72107_ae.field_71990_ca);
                                }
                            }
                        }
                    }

                    return true;
                }
            }
            else if (p_77648_1_.func_77960_j() == 3)
            {
                i1 = p_77648_3_.func_72798_a(p_77648_4_, p_77648_5_, p_77648_6_);
                j1 = p_77648_3_.func_72805_g(p_77648_4_, p_77648_5_, p_77648_6_);

                if (i1 == Block.field_71951_J.field_71990_ca && BlockLog.func_72141_e(j1) == 3)
                {
                    if (p_77648_7_ == 0)
                    {
                        return false;
                    }

                    if (p_77648_7_ == 1)
                    {
                        return false;
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

                    if (p_77648_3_.func_72799_c(p_77648_4_, p_77648_5_, p_77648_6_))
                    {
                        k1 = Block.field_71973_m[Block.field_72086_bP.field_71990_ca].func_85104_a(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_, 0);
                        p_77648_3_.func_72832_d(p_77648_4_, p_77648_5_, p_77648_6_, Block.field_72086_bP.field_71990_ca, k1);

                        if (!p_77648_2_.field_71075_bZ.field_75098_d)
                        {
                            --p_77648_1_.field_77994_a;
                        }
                    }

                    return true;
                }
            }

            return false;
        }
    }

    public boolean func_77646_a(ItemStack p_77646_1_, EntityLiving p_77646_2_)
    {
        if (p_77646_2_ instanceof EntitySheep)
        {
            EntitySheep entitysheep = (EntitySheep)p_77646_2_;
            int i = BlockCloth.func_72238_e_(p_77646_1_.func_77960_j());

            if (!entitysheep.func_70892_o() && entitysheep.func_70896_n() != i)
            {
                // CraftBukkit start
                byte bColor = (byte) i;
                SheepDyeWoolEvent event = new SheepDyeWoolEvent((org.bukkit.entity.Sheep) entitysheep.getBukkitEntity(), org.bukkit.DyeColor.getByData(bColor));
                entitysheep.field_70170_p.getServer().getPluginManager().callEvent(event);

                if (event.isCancelled())
                {
                    return false;
                }

                i = (byte) event.getColor().getWoolData();
                // CraftBukkit end
                entitysheep.func_70891_b(i);
                --p_77646_1_.field_77994_a;
            }

            return true;
        }
        else
        {
            return false;
        }
    }
}
