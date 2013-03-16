package net.minecraft.item;

// CraftBukkit start
import org.bukkit.entity.Player;
import org.bukkit.event.entity.SheepDyeWoolEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCloth;
import net.minecraft.block.BlockCocoa;
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
    public static final String[] field_94595_b = new String[] {"dyePowder_black", "dyePowder_red", "dyePowder_green", "dyePowder_brown", "dyePowder_blue", "dyePowder_purple", "dyePowder_cyan", "dyePowder_silver", "dyePowder_gray", "dyePowder_pink", "dyePowder_lime", "dyePowder_yellow", "dyePowder_lightBlue", "dyePowder_magenta", "dyePowder_orange", "dyePowder_white"};
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
            if (p_77648_1_.func_77960_j() == 15)
            {
                if (a(p_77648_1_, p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_2_))
                {
                    if (!p_77648_3_.field_72995_K)
                    {
                        p_77648_3_.func_72926_e(2005, p_77648_4_, p_77648_5_, p_77648_6_, 0);
                    }

                    return true;
                }
            }
            else if (p_77648_1_.func_77960_j() == 3)
            {
                int i1 = p_77648_3_.func_72798_a(p_77648_4_, p_77648_5_, p_77648_6_);
                int j1 = p_77648_3_.func_72805_g(p_77648_4_, p_77648_5_, p_77648_6_);

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
                        int k1 = Block.field_71973_m[Block.field_72086_bP.field_71990_ca].func_85104_a(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_, 0);
                        p_77648_3_.func_72832_d(p_77648_4_, p_77648_5_, p_77648_6_, Block.field_72086_bP.field_71990_ca, k1, 2);

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

    // CraftBukkit start
    public static boolean func_96604_a(ItemStack p_96604_0_, World p_96604_1_, int p_96604_2_, int p_96604_3_, int p_96604_4_)
    {
        return a(p_96604_0_, p_96604_1_, p_96604_2_, p_96604_3_, p_96604_4_, null);
    }

    public static boolean a(ItemStack itemstack, World world, int i, int j, int k, EntityPlayer entityplayer)
    {
        // CraftBukkit end
        int l = world.func_72798_a(i, j, k);

        if (l == Block.field_71987_y.field_71990_ca)
        {
            if (!world.field_72995_K)
            {
                if ((double) world.field_73012_v.nextFloat() < 0.45D)
                {
                    // CraftBukkit start
                    Player player = (entityplayer instanceof EntityPlayerMP) ? (Player) entityplayer.getBukkitEntity() : null;
                    ((BlockSapling) Block.field_71987_y).func_72269_c(world, i, j, k, world.field_73012_v, true, player, null);
                    // CraftBukkit end
                }

                --itemstack.field_77994_a;
            }

            return true;
        }
        else if (l != Block.field_72109_af.field_71990_ca && l != Block.field_72103_ag.field_71990_ca)
        {
            if (l != Block.field_71999_bt.field_71990_ca && l != Block.field_71996_bs.field_71990_ca)
            {
                if (l > 0 && Block.field_71973_m[l] instanceof BlockCrops)
                {
                    if (world.func_72805_g(i, j, k) == 7)
                    {
                        return false;
                    }
                    else
                    {
                        if (!world.field_72995_K)
                        {
                            ((BlockCrops) Block.field_71973_m[l]).func_72272_c_(world, i, j, k);
                            --itemstack.field_77994_a;
                        }

                        return true;
                    }
                }
                else
                {
                    int i1;
                    int j1;
                    int k1;

                    if (l == Block.field_72086_bP.field_71990_ca)
                    {
                        i1 = world.func_72805_g(i, j, k);
                        j1 = BlockDirectional.func_72217_d(i1);
                        k1 = BlockCocoa.func_72219_c(i1);

                        if (k1 >= 2)
                        {
                            return false;
                        }
                        else
                        {
                            if (!world.field_72995_K)
                            {
                                ++k1;
                                world.func_72921_c(i, j, k, k1 << 2 | j1, 2);
                                --itemstack.field_77994_a;
                            }

                            return true;
                        }
                    }
                    else if (l != Block.field_71980_u.field_71990_ca)
                    {
                        return false;
                    }
                    else
                    {
                        if (!world.field_72995_K)
                        {
                            --itemstack.field_77994_a;
                            label102:

                            for (i1 = 0; i1 < 128; ++i1)
                            {
                                j1 = i;
                                k1 = j + 1;
                                int l1 = k;

                                for (int i2 = 0; i2 < i1 / 16; ++i2)
                                {
                                    j1 += field_77697_d.nextInt(3) - 1;
                                    k1 += (field_77697_d.nextInt(3) - 1) * field_77697_d.nextInt(3) / 2;
                                    l1 += field_77697_d.nextInt(3) - 1;

                                    if (world.func_72798_a(j1, k1 - 1, l1) != Block.field_71980_u.field_71990_ca || world.func_72809_s(j1, k1, l1))
                                    {
                                        continue label102;
                                    }
                                }

                                if (world.func_72798_a(j1, k1, l1) == 0)
                                {
                                    if (field_77697_d.nextInt(10) != 0)
                                    {
                                        if (Block.field_71962_X.func_71854_d(world, j1, k1, l1))
                                        {
                                            world.func_72832_d(j1, k1, l1, Block.field_71962_X.field_71990_ca, 1, 3);
                                        }
                                    }
                                    else if (field_77697_d.nextInt(3) != 0)
                                    {
                                        if (Block.field_72097_ad.func_71854_d(world, j1, k1, l1))
                                        {
                                            world.func_94575_c(j1, k1, l1, Block.field_72097_ad.field_71990_ca);
                                        }
                                    }
                                    else if (Block.field_72107_ae.func_71854_d(world, j1, k1, l1))
                                    {
                                        world.func_94575_c(j1, k1, l1, Block.field_72107_ae.field_71990_ca);
                                    }
                                }
                            }
                        }

                        return true;
                    }
                }
            }
            else if (world.func_72805_g(i, j, k) == 7)
            {
                return false;
            }
            else
            {
                if (!world.field_72995_K)
                {
                    ((BlockStem) Block.field_71973_m[l]).func_72264_l(world, i, j, k);
                    --itemstack.field_77994_a;
                }

                return true;
            }
        }
        else
        {
            if (!world.field_72995_K)
            {
                if ((double) world.field_73012_v.nextFloat() < 0.4D)
                {
                    // CraftBukkit start - validate
                    Player player = (entityplayer instanceof EntityPlayerMP) ? (Player) entityplayer.getBukkitEntity() : null;
                    ((BlockMushroom) Block.field_71973_m[l]).func_72271_c(world, i, j, k, world.field_73012_v, true, player, itemstack);
                }

                //--itemstack.count; - called later if the bonemeal attempt was not cancelled by a plugin
                // CraftBukkit end
            }

            return true;
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
