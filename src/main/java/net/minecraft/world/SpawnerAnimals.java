package net.minecraft.world;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;

// CraftBukkit start
import org.bukkit.craftbukkit.util.LongHash;
import org.bukkit.craftbukkit.util.LongObjectHashMap;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
// CraftBukkit end

public final class SpawnerAnimals
{
    private static LongObjectHashMap<Boolean> field_77193_b = new LongObjectHashMap<Boolean>(); // CraftBukkit - HashMap -> LongObjectHashMap
    protected static final Class[] field_77194_a = new Class[] {EntitySpider.class, EntityZombie.class, EntitySkeleton.class};

    protected static ChunkPosition func_77189_a(World p_77189_0_, int p_77189_1_, int p_77189_2_)
    {
        Chunk chunk = p_77189_0_.func_72964_e(p_77189_1_, p_77189_2_);
        int k = p_77189_1_ * 16 + p_77189_0_.field_73012_v.nextInt(16);
        int l = p_77189_2_ * 16 + p_77189_0_.field_73012_v.nextInt(16);
        int i1 = p_77189_0_.field_73012_v.nextInt(chunk == null ? p_77189_0_.func_72940_L() : chunk.func_76625_h() + 16 - 1);
        return new ChunkPosition(k, i1, l);
    }

    public static final int func_77192_a(WorldServer p_77192_0_, boolean p_77192_1_, boolean p_77192_2_, boolean p_77192_3_)
    {
        if (!p_77192_1_ && !p_77192_2_)
        {
            return 0;
        }
        else
        {
            field_77193_b.clear();
            int i;
            int j;

            for (i = 0; i < p_77192_0_.field_73010_i.size(); ++i)
            {
                EntityPlayer entityplayer = (EntityPlayer)p_77192_0_.field_73010_i.get(i);
                int k = MathHelper.func_76128_c(entityplayer.field_70165_t / 16.0D);
                j = MathHelper.func_76128_c(entityplayer.field_70161_v / 16.0D);
                byte b0 = 8;

                for (int l = -b0; l <= b0; ++l)
                {
                    for (int i1 = -b0; i1 <= b0; ++i1)
                    {
                        boolean flag3 = l == -b0 || l == b0 || i1 == -b0 || i1 == b0;
                        // CraftBukkit start
                        long chunkCoords = LongHash.toLong(l + k, i1 + j);

                        if (!flag3)
                        {
                            field_77193_b.put(chunkCoords, false);
                        }
                        else if (!field_77193_b.containsKey(chunkCoords))
                        {
                            field_77193_b.put(chunkCoords, true);
                        }

                        // CraftBukkit end
                    }
                }
            }

            i = 0;
            ChunkCoordinates chunkcoordinates = p_77192_0_.func_72861_E();
            EnumCreatureType[] aenumcreaturetype = EnumCreatureType.values();
            j = aenumcreaturetype.length;

            for (int j1 = 0; j1 < j; ++j1)
            {
                EnumCreatureType enumcreaturetype = aenumcreaturetype[j1];
                // CraftBukkit start - use per-world spawn limits
                int limit = enumcreaturetype.func_75601_b();

                switch (enumcreaturetype)
                {
                    case monster:
                        limit = p_77192_0_.getWorld().getMonsterSpawnLimit();
                        break;

                    case creature:
                        limit = p_77192_0_.getWorld().getAnimalSpawnLimit();
                        break;

                    case waterCreature:
                        limit = p_77192_0_.getWorld().getWaterAnimalSpawnLimit();
                        break;

                    case ambient:
                        limit = p_77192_0_.getWorld().getAmbientSpawnLimit();
                        break;
                }

                if (limit == 0)
                {
                    continue;
                }

                // CraftBukkit end

                if ((!enumcreaturetype.func_75599_d() || p_77192_2_) && (enumcreaturetype.func_75599_d() || p_77192_1_) && (!enumcreaturetype.func_82705_e() || p_77192_3_) && p_77192_0_.func_72907_a(enumcreaturetype.func_75598_a()) <= limit * field_77193_b.size() / 256)   // CraftBukkit - use per-world limits
                {
                    Iterator iterator = field_77193_b.keySet().iterator();
                    label110:

                    while (iterator.hasNext())
                    {
                        // CraftBukkit start
                        long key = ((Long) iterator.next()).longValue();

                        if (!field_77193_b.get(key))
                        {
                            ChunkPosition chunkposition = func_77189_a(p_77192_0_, LongHash.msw(key), LongHash.lsw(key));
                            // CraftBukkit end
                            int k1 = chunkposition.field_76930_a;
                            int l1 = chunkposition.field_76928_b;
                            int i2 = chunkposition.field_76929_c;

                            if (!p_77192_0_.func_72809_s(k1, l1, i2) && p_77192_0_.func_72803_f(k1, l1, i2) == enumcreaturetype.func_75600_c())
                            {
                                int j2 = 0;
                                int k2 = 0;

                                while (k2 < 3)
                                {
                                    int l2 = k1;
                                    int i3 = l1;
                                    int j3 = i2;
                                    byte b1 = 6;
                                    SpawnListEntry spawnlistentry = null;
                                    int k3 = 0;

                                    while (true)
                                    {
                                        if (k3 < 4)
                                        {
                                            label103:
                                            {
                                                l2 += p_77192_0_.field_73012_v.nextInt(b1) - p_77192_0_.field_73012_v.nextInt(b1);
                                                i3 += p_77192_0_.field_73012_v.nextInt(1) - p_77192_0_.field_73012_v.nextInt(1);
                                                j3 += p_77192_0_.field_73012_v.nextInt(b1) - p_77192_0_.field_73012_v.nextInt(b1);

                                                if (func_77190_a(enumcreaturetype, p_77192_0_, l2, i3, j3))
                                                {
                                                    float f = (float)l2 + 0.5F;
                                                    float f1 = (float)i3;
                                                    float f2 = (float)j3 + 0.5F;

                                                    if (p_77192_0_.func_72977_a((double)f, (double)f1, (double)f2, 24.0D) == null)
                                                    {
                                                        float f3 = f - (float)chunkcoordinates.field_71574_a;
                                                        float f4 = f1 - (float)chunkcoordinates.field_71572_b;
                                                        float f5 = f2 - (float)chunkcoordinates.field_71573_c;
                                                        float f6 = f3 * f3 + f4 * f4 + f5 * f5;

                                                        if (f6 >= 576.0F)
                                                        {
                                                            if (spawnlistentry == null)
                                                            {
                                                                spawnlistentry = p_77192_0_.func_73057_a(enumcreaturetype, l2, i3, j3);

                                                                if (spawnlistentry == null)
                                                                {
                                                                    break label103;
                                                                }
                                                            }

                                                            EntityLiving entityliving;

                                                            try
                                                            {
                                                                entityliving = (EntityLiving)spawnlistentry.field_76300_b.getConstructor(new Class[] {World.class}).newInstance(new Object[] {p_77192_0_});
                                                            }
                                                            catch (Exception exception)
                                                            {
                                                                exception.printStackTrace();
                                                                return i;
                                                            }

                                                            entityliving.func_70012_b((double)f, (double)f1, (double)f2, p_77192_0_.field_73012_v.nextFloat() * 360.0F, 0.0F);

                                                            if (entityliving.func_70601_bi())
                                                            {
                                                                ++j2;
                                                                // CraftBukkit start - added a reason for spawning this creature, moved a(entityliving, world...) up
                                                                func_77188_a(entityliving, p_77192_0_, f, f1, f2);
                                                                p_77192_0_.addEntity(entityliving, SpawnReason.NATURAL);

                                                                // CraftBukkit end
                                                                if (j2 >= entityliving.func_70641_bl())
                                                                {
                                                                    continue label110;
                                                                }
                                                            }

                                                            i += j2;
                                                        }
                                                    }
                                                }

                                                ++k3;
                                                continue;
                                            }
                                        }

                                        ++k2;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            return i;
        }
    }

    public static boolean func_77190_a(EnumCreatureType p_77190_0_, World p_77190_1_, int p_77190_2_, int p_77190_3_, int p_77190_4_)
    {
        if (p_77190_0_.func_75600_c() == Material.field_76244_g)
        {
            return p_77190_1_.func_72803_f(p_77190_2_, p_77190_3_, p_77190_4_).func_76224_d() && p_77190_1_.func_72803_f(p_77190_2_, p_77190_3_ - 1, p_77190_4_).func_76224_d() && !p_77190_1_.func_72809_s(p_77190_2_, p_77190_3_ + 1, p_77190_4_);
        }
        else if (!p_77190_1_.func_72797_t(p_77190_2_, p_77190_3_ - 1, p_77190_4_))
        {
            return false;
        }
        else
        {
            int l = p_77190_1_.func_72798_a(p_77190_2_, p_77190_3_ - 1, p_77190_4_);
            return l != Block.field_71986_z.field_71990_ca && !p_77190_1_.func_72809_s(p_77190_2_, p_77190_3_, p_77190_4_) && !p_77190_1_.func_72803_f(p_77190_2_, p_77190_3_, p_77190_4_).func_76224_d() && !p_77190_1_.func_72809_s(p_77190_2_, p_77190_3_ + 1, p_77190_4_);
        }
    }

    private static void func_77188_a(EntityLiving p_77188_0_, World p_77188_1_, float p_77188_2_, float p_77188_3_, float p_77188_4_)
    {
        p_77188_0_.func_82163_bD();
    }

    public static void func_77191_a(World p_77191_0_, BiomeGenBase p_77191_1_, int p_77191_2_, int p_77191_3_, int p_77191_4_, int p_77191_5_, Random p_77191_6_)
    {
        List list = p_77191_1_.func_76747_a(EnumCreatureType.creature);

        if (!list.isEmpty())
        {
            while (p_77191_6_.nextFloat() < p_77191_1_.func_76741_f())
            {
                SpawnListEntry spawnlistentry = (SpawnListEntry) WeightedRandom.func_76271_a(p_77191_0_.field_73012_v, (Collection) list);
                int i1 = spawnlistentry.field_76301_c + p_77191_6_.nextInt(1 + spawnlistentry.field_76299_d - spawnlistentry.field_76301_c);
                int j1 = p_77191_2_ + p_77191_6_.nextInt(p_77191_4_);
                int k1 = p_77191_3_ + p_77191_6_.nextInt(p_77191_5_);
                int l1 = j1;
                int i2 = k1;

                for (int j2 = 0; j2 < i1; ++j2)
                {
                    boolean flag = false;

                    for (int k2 = 0; !flag && k2 < 4; ++k2)
                    {
                        int l2 = p_77191_0_.func_72825_h(j1, k1);

                        if (func_77190_a(EnumCreatureType.creature, p_77191_0_, j1, l2, k1))
                        {
                            float f = (float)j1 + 0.5F;
                            float f1 = (float)l2;
                            float f2 = (float)k1 + 0.5F;
                            EntityLiving entityliving;

                            try
                            {
                                entityliving = (EntityLiving)spawnlistentry.field_76300_b.getConstructor(new Class[] {World.class}).newInstance(new Object[] {p_77191_0_});
                            }
                            catch (Exception exception)
                            {
                                exception.printStackTrace();
                                continue;
                            }

                            entityliving.func_70012_b((double)f, (double)f1, (double)f2, p_77191_6_.nextFloat() * 360.0F, 0.0F);
                            // CraftBukkit start - added a reason for spawning this creature, moved a(entity, world...) up
                            func_77188_a(entityliving, p_77191_0_, f, f1, f2);
                            p_77191_0_.addEntity(entityliving, SpawnReason.CHUNK_GEN);
                            // CraftBukkit end
                            flag = true;
                        }

                        j1 += p_77191_6_.nextInt(5) - p_77191_6_.nextInt(5);

                        for (k1 += p_77191_6_.nextInt(5) - p_77191_6_.nextInt(5); j1 < p_77191_2_ || j1 >= p_77191_2_ + p_77191_4_ || k1 < p_77191_3_ || k1 >= p_77191_3_ + p_77191_4_; k1 = i2 + p_77191_6_.nextInt(5) - p_77191_6_.nextInt(5))
                        {
                            j1 = l1 + p_77191_6_.nextInt(5) - p_77191_6_.nextInt(5);
                        }
                    }
                }
            }
        }
    }
}
