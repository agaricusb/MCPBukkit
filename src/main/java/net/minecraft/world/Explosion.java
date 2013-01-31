package net.minecraft.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

// CraftBukkit start
import org.bukkit.Bukkit;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.Location;
// CraftBukkit end

public class Explosion
{
    public boolean field_77286_a = false;
    public boolean field_82755_b = true;
    private int field_77289_h = 16;
    private Random field_77290_i = new Random();
    private World field_77287_j;
    public double field_77284_b;
    public double field_77285_c;
    public double field_77282_d;
    public Entity field_77283_e;
    public float field_77280_f;
    public List field_77281_g = new ArrayList();
    private Map field_77288_k = new HashMap();
    public boolean wasCanceled = false; // CraftBukkit

    public Explosion(World p_i3727_1_, Entity p_i3727_2_, double p_i3727_3_, double p_i3727_5_, double p_i3727_7_, float p_i3727_9_)
    {
        this.field_77287_j = p_i3727_1_;
        this.field_77283_e = p_i3727_2_;
        this.field_77280_f = (float) Math.max(p_i3727_9_, 0.0); // CraftBukkit - clamp bad values
        this.field_77284_b = p_i3727_3_;
        this.field_77285_c = p_i3727_5_;
        this.field_77282_d = p_i3727_7_;
    }

    public void func_77278_a()
    {
        // CraftBukkit start
        if (this.field_77280_f < 0.1F)
        {
            return;
        }

        // CraftBukkit end
        float f = this.field_77280_f;
        HashSet hashset = new HashSet();
        int i;
        int j;
        int k;
        double d0;
        double d1;
        double d2;

        for (i = 0; i < this.field_77289_h; ++i)
        {
            for (j = 0; j < this.field_77289_h; ++j)
            {
                for (k = 0; k < this.field_77289_h; ++k)
                {
                    if (i == 0 || i == this.field_77289_h - 1 || j == 0 || j == this.field_77289_h - 1 || k == 0 || k == this.field_77289_h - 1)
                    {
                        double d3 = (double)((float)i / ((float)this.field_77289_h - 1.0F) * 2.0F - 1.0F);
                        double d4 = (double)((float)j / ((float)this.field_77289_h - 1.0F) * 2.0F - 1.0F);
                        double d5 = (double)((float)k / ((float)this.field_77289_h - 1.0F) * 2.0F - 1.0F);
                        double d6 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
                        d3 /= d6;
                        d4 /= d6;
                        d5 /= d6;
                        float f1 = this.field_77280_f * (0.7F + this.field_77287_j.field_73012_v.nextFloat() * 0.6F);
                        d0 = this.field_77284_b;
                        d1 = this.field_77285_c;
                        d2 = this.field_77282_d;

                        for (float f2 = 0.3F; f1 > 0.0F; f1 -= f2 * 0.75F)
                        {
                            int l = MathHelper.func_76128_c(d0);
                            int i1 = MathHelper.func_76128_c(d1);
                            int j1 = MathHelper.func_76128_c(d2);
                            int k1 = this.field_77287_j.func_72798_a(l, i1, j1);

                            if (k1 > 0)
                            {
                                Block block = Block.field_71973_m[k1];
                                float f3 = this.field_77283_e != null ? this.field_77283_e.func_82146_a(this, block, l, i1, j1) : block.func_71904_a(this.field_77283_e);
                                f1 -= (f3 + 0.3F) * f2;
                            }

                            if (f1 > 0.0F && i1 < 256 && i1 >= 0)   // CraftBukkit - don't wrap explosions
                            {
                                hashset.add(new ChunkPosition(l, i1, j1));
                            }

                            d0 += d3 * (double)f2;
                            d1 += d4 * (double)f2;
                            d2 += d5 * (double)f2;
                        }
                    }
                }
            }
        }

        this.field_77281_g.addAll(hashset);
        this.field_77280_f *= 2.0F;
        i = MathHelper.func_76128_c(this.field_77284_b - (double)this.field_77280_f - 1.0D);
        j = MathHelper.func_76128_c(this.field_77284_b + (double)this.field_77280_f + 1.0D);
        k = MathHelper.func_76128_c(this.field_77285_c - (double)this.field_77280_f - 1.0D);
        int l1 = MathHelper.func_76128_c(this.field_77285_c + (double)this.field_77280_f + 1.0D);
        int i2 = MathHelper.func_76128_c(this.field_77282_d - (double)this.field_77280_f - 1.0D);
        int j2 = MathHelper.func_76128_c(this.field_77282_d + (double)this.field_77280_f + 1.0D);
        List list = this.field_77287_j.func_72839_b(this.field_77283_e, AxisAlignedBB.func_72332_a().func_72299_a((double)i, (double)k, (double)i2, (double)j, (double)l1, (double)j2));
        Vec3 vec3 = this.field_77287_j.func_82732_R().func_72345_a(this.field_77284_b, this.field_77285_c, this.field_77282_d);

        for (int k2 = 0; k2 < list.size(); ++k2)
        {
            Entity entity = (Entity)list.get(k2);
            double d7 = entity.func_70011_f(this.field_77284_b, this.field_77285_c, this.field_77282_d) / (double)this.field_77280_f;

            if (d7 <= 1.0D)
            {
                d0 = entity.field_70165_t - this.field_77284_b;
                d1 = entity.field_70163_u + (double)entity.func_70047_e() - this.field_77285_c;
                d2 = entity.field_70161_v - this.field_77282_d;
                double d8 = (double)MathHelper.func_76133_a(d0 * d0 + d1 * d1 + d2 * d2);

                if (d8 != 0.0D)
                {
                    d0 /= d8;
                    d1 /= d8;
                    d2 /= d8;
                    double d9 = (double)this.field_77287_j.func_72842_a(vec3, entity.field_70121_D);
                    double d10 = (1.0D - d7) * d9;
                    // CraftBukkit start - explosion damage hook
                    org.bukkit.entity.Entity damagee = (entity == null) ? null : entity.getBukkitEntity();
                    int damageDone = (int)((d10 * d10 + d10) / 2.0D * 8.0D * (double) this.field_77280_f + 1.0D);

                    if (damagee == null)
                    {
                        // nothing was hurt
                    }
                    else if (this.field_77283_e == null)     // Block explosion (without an entity source; bed etc.)
                    {
                        EntityDamageByBlockEvent event = new EntityDamageByBlockEvent(null, damagee, EntityDamageEvent.DamageCause.BLOCK_EXPLOSION, damageDone);
                        Bukkit.getPluginManager().callEvent(event);

                        if (!event.isCancelled())
                        {
                            damagee.setLastDamageCause(event);
                            entity.func_70097_a(DamageSource.field_76378_k, event.getDamage());
                            double d11 = EnchantmentProtection.func_92092_a(entity, d10);
                            entity.field_70159_w += d0 * d11;
                            entity.field_70181_x += d1 * d11;
                            entity.field_70179_y += d2 * d11;

                            if (entity instanceof EntityPlayer)
                            {
                                this.field_77288_k.put((EntityPlayer) entity, this.field_77287_j.func_82732_R().func_72345_a(d0 * d10, d1 * d10, d2 * d10));
                            }
                        }
                    }
                    else
                    {
                        final org.bukkit.entity.Entity damager = this.field_77283_e.getBukkitEntity();
                        final EntityDamageEvent.DamageCause damageCause;

                        if (damager instanceof org.bukkit.entity.TNTPrimed)
                        {
                            damageCause = EntityDamageEvent.DamageCause.BLOCK_EXPLOSION;
                        }
                        else
                        {
                            damageCause = EntityDamageEvent.DamageCause.ENTITY_EXPLOSION;
                        }

                        EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(damager, damagee, damageCause, damageDone);
                        Bukkit.getPluginManager().callEvent(event);

                        if (!event.isCancelled())
                        {
                            entity.getBukkitEntity().setLastDamageCause(event);
                            entity.func_70097_a(DamageSource.field_76378_k, event.getDamage());
                            entity.field_70159_w += d0 * d10;
                            entity.field_70181_x += d1 * d10;
                            entity.field_70179_y += d2 * d10;

                            if (entity instanceof EntityPlayer)
                            {
                                this.field_77288_k.put((EntityPlayer) entity, this.field_77287_j.func_82732_R().func_72345_a(d0 * d10, d1 * d10, d2 * d10));
                            }
                        }
                    }

                    // CraftBukkit end
                }
            }
        }

        this.field_77280_f = f;
    }

    public void func_77279_a(boolean p_77279_1_)
    {
        this.field_77287_j.func_72908_a(this.field_77284_b, this.field_77285_c, this.field_77282_d, "random.explode", 4.0F, (1.0F + (this.field_77287_j.field_73012_v.nextFloat() - this.field_77287_j.field_73012_v.nextFloat()) * 0.2F) * 0.7F);

        if (this.field_77280_f >= 2.0F && this.field_82755_b)
        {
            this.field_77287_j.func_72869_a("hugeexplosion", this.field_77284_b, this.field_77285_c, this.field_77282_d, 1.0D, 0.0D, 0.0D);
        }
        else
        {
            this.field_77287_j.func_72869_a("largeexplode", this.field_77284_b, this.field_77285_c, this.field_77282_d, 1.0D, 0.0D, 0.0D);
        }

        Iterator iterator;
        ChunkPosition chunkposition;
        int i;
        int j;
        int k;
        int l;

        if (this.field_82755_b)
        {
            // CraftBukkit start
            org.bukkit.World bworld = this.field_77287_j.getWorld();
            org.bukkit.entity.Entity explode = this.field_77283_e == null ? null : this.field_77283_e.getBukkitEntity();
            Location location = new Location(bworld, this.field_77284_b, this.field_77285_c, this.field_77282_d);
            List<org.bukkit.block.Block> blockList = new ArrayList<org.bukkit.block.Block>();

            for (int i1 = this.field_77281_g.size() - 1; i1 >= 0; i1--)
            {
                ChunkPosition cpos = (ChunkPosition) this.field_77281_g.get(i1);
                org.bukkit.block.Block block = bworld.getBlockAt(cpos.field_76930_a, cpos.field_76928_b, cpos.field_76929_c);

                if (block.getType() != org.bukkit.Material.AIR)
                {
                    blockList.add(block);
                }
            }

            EntityExplodeEvent event = new EntityExplodeEvent(explode, location, blockList, 0.3F);
            this.field_77287_j.getServer().getPluginManager().callEvent(event);
            this.field_77281_g.clear();

            for (org.bukkit.block.Block block : event.blockList())
            {
                ChunkPosition coords = new ChunkPosition(block.getX(), block.getY(), block.getZ());
                field_77281_g.add(coords);
            }

            if (event.isCancelled())
            {
                this.wasCanceled = true;
                return;
            }

            // CraftBukkit end
            iterator = this.field_77281_g.iterator();

            while (iterator.hasNext())
            {
                chunkposition = (ChunkPosition)iterator.next();
                i = chunkposition.field_76930_a;
                j = chunkposition.field_76928_b;
                k = chunkposition.field_76929_c;
                l = this.field_77287_j.func_72798_a(i, j, k);

                if (p_77279_1_)
                {
                    double d0 = (double)((float)i + this.field_77287_j.field_73012_v.nextFloat());
                    double d1 = (double)((float)j + this.field_77287_j.field_73012_v.nextFloat());
                    double d2 = (double)((float)k + this.field_77287_j.field_73012_v.nextFloat());
                    double d3 = d0 - this.field_77284_b;
                    double d4 = d1 - this.field_77285_c;
                    double d5 = d2 - this.field_77282_d;
                    double d6 = (double)MathHelper.func_76133_a(d3 * d3 + d4 * d4 + d5 * d5);
                    d3 /= d6;
                    d4 /= d6;
                    d5 /= d6;
                    double d7 = 0.5D / (d6 / (double)this.field_77280_f + 0.1D);
                    d7 *= (double)(this.field_77287_j.field_73012_v.nextFloat() * this.field_77287_j.field_73012_v.nextFloat() + 0.3F);
                    d3 *= d7;
                    d4 *= d7;
                    d5 *= d7;
                    this.field_77287_j.func_72869_a("explode", (d0 + this.field_77284_b * 1.0D) / 2.0D, (d1 + this.field_77285_c * 1.0D) / 2.0D, (d2 + this.field_77282_d * 1.0D) / 2.0D, d3, d4, d5);
                    this.field_77287_j.func_72869_a("smoke", d0, d1, d2, d3, d4, d5);
                }

                // CraftBukkit - stop explosions from putting out fire
                if (l > 0 && l != Block.field_72067_ar.field_71990_ca)
                {
                    Block block = Block.field_71973_m[l];

                    if (block.func_85103_a(this))
                    {
                        // CraftBukkit
                        block.func_71914_a(this.field_77287_j, i, j, k, this.field_77287_j.func_72805_g(i, j, k), event.getYield(), 0);
                    }

                    if (this.field_77287_j.func_72930_a(i, j, k, 0, 0, this.field_77287_j.field_72995_K))
                    {
                        this.field_77287_j.func_72898_h(i, j, k, 0);
                    }

                    block.func_71867_k(this.field_77287_j, i, j, k);
                }
            }
        }

        if (this.field_77286_a)
        {
            iterator = this.field_77281_g.iterator();

            while (iterator.hasNext())
            {
                chunkposition = (ChunkPosition)iterator.next();
                i = chunkposition.field_76930_a;
                j = chunkposition.field_76928_b;
                k = chunkposition.field_76929_c;
                l = this.field_77287_j.func_72798_a(i, j, k);
                int i1 = this.field_77287_j.func_72798_a(i, j - 1, k);

                if (l == 0 && Block.field_71970_n[i1] && this.field_77290_i.nextInt(3) == 0)
                {
                    this.field_77287_j.func_72859_e(i, j, k, Block.field_72067_ar.field_71990_ca);
                }
            }
        }
    }

    public Map func_77277_b()
    {
        return this.field_77288_k;
    }
}
