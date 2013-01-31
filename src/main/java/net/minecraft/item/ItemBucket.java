package net.minecraft.item;

// CraftBukkit start
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
// CraftBukkit end

public class ItemBucket extends Item
{
    private int field_77876_a;

    public ItemBucket(int p_i3625_1_, int p_i3625_2_)
    {
        super(p_i3625_1_);
        this.field_77777_bU = 1;
        this.field_77876_a = p_i3625_2_;
        this.func_77637_a(CreativeTabs.field_78026_f);
    }

    public ItemStack func_77659_a(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
    {
        float f = 1.0F;
        double d0 = p_77659_3_.field_70169_q + (p_77659_3_.field_70165_t - p_77659_3_.field_70169_q) * (double)f;
        double d1 = p_77659_3_.field_70167_r + (p_77659_3_.field_70163_u - p_77659_3_.field_70167_r) * (double)f + 1.62D - (double)p_77659_3_.field_70129_M;
        double d2 = p_77659_3_.field_70166_s + (p_77659_3_.field_70161_v - p_77659_3_.field_70166_s) * (double)f;
        boolean flag = this.field_77876_a == 0;
        MovingObjectPosition movingobjectposition = this.func_77621_a(p_77659_2_, p_77659_3_, flag);

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

                if (!p_77659_2_.func_72962_a(p_77659_3_, i, j, k))
                {
                    return p_77659_1_;
                }

                if (this.field_77876_a == 0)
                {
                    if (!p_77659_3_.func_82247_a(i, j, k, movingobjectposition.field_72310_e, p_77659_1_))
                    {
                        return p_77659_1_;
                    }

                    if (p_77659_2_.func_72803_f(i, j, k) == Material.field_76244_g && p_77659_2_.func_72805_g(i, j, k) == 0)
                    {
                        // CraftBukkit start
                        PlayerBucketFillEvent event = CraftEventFactory.callPlayerBucketFillEvent(p_77659_3_, i, j, k, -1, p_77659_1_, Item.field_77786_ax);

                        if (event.isCancelled())
                        {
                            return p_77659_1_;
                        }

                        // CraftBukkit end
                        p_77659_2_.func_72859_e(i, j, k, 0);

                        if (p_77659_3_.field_71075_bZ.field_75098_d)
                        {
                            return p_77659_1_;
                        }

                        ItemStack result = CraftItemStack.asNMSCopy(event.getItemStack()); // CraftBukkit - TODO: Check this stuff later... Not sure how this behavior should work

                        if (--p_77659_1_.field_77994_a <= 0)
                        {
                            return result; // CraftBukkit
                        }

                        if (!p_77659_3_.field_71071_by.func_70441_a(result))   // CraftBukkit
                        {
                            p_77659_3_.func_71021_b(CraftItemStack.asNMSCopy(event.getItemStack())); // CraftBukkit
                        }

                        return p_77659_1_;
                    }

                    if (p_77659_2_.func_72803_f(i, j, k) == Material.field_76256_h && p_77659_2_.func_72805_g(i, j, k) == 0)
                    {
                        // CraftBukkit start
                        PlayerBucketFillEvent event = CraftEventFactory.callPlayerBucketFillEvent(p_77659_3_, i, j, k, -1, p_77659_1_, Item.field_77775_ay);

                        if (event.isCancelled())
                        {
                            return p_77659_1_;
                        }

                        // CraftBukkit end
                        p_77659_2_.func_72859_e(i, j, k, 0);

                        if (p_77659_3_.field_71075_bZ.field_75098_d)
                        {
                            return p_77659_1_;
                        }

                        ItemStack result = CraftItemStack.asNMSCopy(event.getItemStack()); // CraftBukkit - TODO: Check this stuff later... Not sure how this behavior should work

                        if (--p_77659_1_.field_77994_a <= 0)
                        {
                            return result; // CraftBukkit
                        }

                        if (!p_77659_3_.field_71071_by.func_70441_a(result))   // CraftBukkit
                        {
                            p_77659_3_.func_71021_b(CraftItemStack.asNMSCopy(event.getItemStack())); // CraftBukkit
                        }

                        return p_77659_1_;
                    }
                }
                else
                {
                    if (this.field_77876_a < 0)
                    {
                        // CraftBukkit start
                        PlayerBucketEmptyEvent event = CraftEventFactory.callPlayerBucketEmptyEvent(p_77659_3_, i, j, k, movingobjectposition.field_72310_e, p_77659_1_);

                        if (event.isCancelled())
                        {
                            return p_77659_1_;
                        }

                        return CraftItemStack.asNMSCopy(event.getItemStack());
                    }

                    int clickedX = i, clickedY = j, clickedZ = k;
                    // CraftBukkit end

                    if (movingobjectposition.field_72310_e == 0)
                    {
                        --j;
                    }

                    if (movingobjectposition.field_72310_e == 1)
                    {
                        ++j;
                    }

                    if (movingobjectposition.field_72310_e == 2)
                    {
                        --k;
                    }

                    if (movingobjectposition.field_72310_e == 3)
                    {
                        ++k;
                    }

                    if (movingobjectposition.field_72310_e == 4)
                    {
                        --i;
                    }

                    if (movingobjectposition.field_72310_e == 5)
                    {
                        ++i;
                    }

                    if (!p_77659_3_.func_82247_a(i, j, k, movingobjectposition.field_72310_e, p_77659_1_))
                    {
                        return p_77659_1_;
                    }

                    // CraftBukkit start
                    PlayerBucketEmptyEvent event = CraftEventFactory.callPlayerBucketEmptyEvent(p_77659_3_, clickedX, clickedY, clickedZ, movingobjectposition.field_72310_e, p_77659_1_);

                    if (event.isCancelled())
                    {
                        return p_77659_1_;
                    }

                    // CraftBukkit end

                    if (this.func_77875_a(p_77659_2_, d0, d1, d2, i, j, k) && !p_77659_3_.field_71075_bZ.field_75098_d)
                    {
                        return CraftItemStack.asNMSCopy(event.getItemStack()); // CraftBukkit
                    }
                }
            }
            else if (this.field_77876_a == 0 && movingobjectposition.field_72308_g instanceof EntityCow)
            {
                // CraftBukkit start - This codepath seems to be *NEVER* called
                org.bukkit.Location loc = movingobjectposition.field_72308_g.getBukkitEntity().getLocation();
                PlayerBucketFillEvent event = CraftEventFactory.callPlayerBucketFillEvent(p_77659_3_, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), -1, p_77659_1_, Item.field_77771_aG);

                if (event.isCancelled())
                {
                    return p_77659_1_;
                }

                return CraftItemStack.asNMSCopy(event.getItemStack());
                // CraftBukkit end
            }

            return p_77659_1_;
        }
    }

    public boolean func_77875_a(World p_77875_1_, double p_77875_2_, double p_77875_4_, double p_77875_6_, int p_77875_8_, int p_77875_9_, int p_77875_10_)
    {
        if (this.field_77876_a <= 0)
        {
            return false;
        }
        else if (!p_77875_1_.func_72799_c(p_77875_8_, p_77875_9_, p_77875_10_) && p_77875_1_.func_72803_f(p_77875_8_, p_77875_9_, p_77875_10_).func_76220_a())
        {
            return false;
        }
        else
        {
            if (p_77875_1_.field_73011_w.field_76575_d && this.field_77876_a == Block.field_71942_A.field_71990_ca)
            {
                p_77875_1_.func_72908_a(p_77875_2_ + 0.5D, p_77875_4_ + 0.5D, p_77875_6_ + 0.5D, "random.fizz", 0.5F, 2.6F + (p_77875_1_.field_73012_v.nextFloat() - p_77875_1_.field_73012_v.nextFloat()) * 0.8F);

                for (int l = 0; l < 8; ++l)
                {
                    p_77875_1_.func_72869_a("largesmoke", (double)p_77875_8_ + Math.random(), (double)p_77875_9_ + Math.random(), (double)p_77875_10_ + Math.random(), 0.0D, 0.0D, 0.0D);
                }
            }
            else
            {
                p_77875_1_.func_72832_d(p_77875_8_, p_77875_9_, p_77875_10_, this.field_77876_a, 0);
            }

            return true;
        }
    }
}
