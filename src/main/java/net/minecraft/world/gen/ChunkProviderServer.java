package net.minecraft.world.gen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.block.BlockSand;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.ReportedException;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.EmptyChunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import net.minecraft.world.chunk.storage.IChunkLoader;

// CraftBukkit start
import java.util.Random;

import org.bukkit.Server;
import org.bukkit.craftbukkit.chunkio.ChunkIOExecutor;
import org.bukkit.craftbukkit.util.LongHash;
import org.bukkit.craftbukkit.util.LongHashSet;
import org.bukkit.craftbukkit.util.LongObjectHashMap;
import org.bukkit.event.world.ChunkUnloadEvent;
// CraftBukkit end

public class ChunkProviderServer implements IChunkProvider
{
    // CraftBukkit start
    public LongHashSet field_73248_b = new LongHashSet();
    public Chunk field_73249_c;
    public IChunkProvider field_73246_d; // CraftBukkit
    private IChunkLoader field_73247_e;
    public boolean field_73250_a = false; // true -> false
    public LongObjectHashMap<Chunk> field_73244_f = new LongObjectHashMap<Chunk>();
    public WorldServer field_73251_h;
    // CraftBukkit end

    public ChunkProviderServer(WorldServer p_i3393_1_, IChunkLoader p_i3393_2_, IChunkProvider p_i3393_3_)
    {
        this.field_73249_c = new EmptyChunk(p_i3393_1_, 0, 0);
        this.field_73251_h = p_i3393_1_;
        this.field_73247_e = p_i3393_2_;
        this.field_73246_d = p_i3393_3_;
    }

    public boolean func_73149_a(int p_73149_1_, int p_73149_2_)
    {
        return this.field_73244_f.containsKey(LongHash.toLong(p_73149_1_, p_73149_2_)); // CraftBukkit
    }

    public void func_73241_b(int p_73241_1_, int p_73241_2_)
    {
        if (this.field_73251_h.field_73011_w.func_76567_e())
        {
            ChunkCoordinates chunkcoordinates = this.field_73251_h.func_72861_E();
            int k = p_73241_1_ * 16 + 8 - chunkcoordinates.field_71574_a;
            int l = p_73241_2_ * 16 + 8 - chunkcoordinates.field_71573_c;
            short short1 = 128;

            // CraftBukkit start
            if (k < -short1 || k > short1 || l < -short1 || l > short1 || !(this.field_73251_h.keepSpawnInMemory))   // Added 'this.world.keepSpawnInMemory'
            {
                this.field_73248_b.add(p_73241_1_, p_73241_2_);
                Chunk c = this.field_73244_f.get(LongHash.toLong(p_73241_1_, p_73241_2_));

                if (c != null)
                {
                    c.mustSave = true;
                }
            }

            // CraftBukkit end
        }
        else
        {
            // CraftBukkit start
            this.field_73248_b.add(p_73241_1_, p_73241_2_);
            Chunk c = this.field_73244_f.get(LongHash.toLong(p_73241_1_, p_73241_2_));

            if (c != null)
            {
                c.mustSave = true;
            }

            // CraftBukkit end
        }
    }

    public void func_73240_a()
    {
        Iterator iterator = this.field_73244_f.values().iterator(); // CraftBukkit

        while (iterator.hasNext())
        {
            Chunk chunk = (Chunk)iterator.next();
            this.func_73241_b(chunk.field_76635_g, chunk.field_76647_h);
        }
    }

    // CraftBukkit start - add async variant, provide compatibility
    public Chunk func_73158_c(int p_73158_1_, int p_73158_2_)
    {
        return getChunkAt(p_73158_1_, p_73158_2_, null);
    }

    public Chunk getChunkAt(int i, int j, Runnable runnable)
    {
        this.field_73248_b.remove(i, j);
        Chunk chunk = (Chunk) this.field_73244_f.get(LongHash.toLong(i, j));
        boolean newChunk = false;
        AnvilChunkLoader loader = null;

        if (this.field_73247_e instanceof AnvilChunkLoader)
        {
            loader = (AnvilChunkLoader) this.field_73247_e;
        }

        // If the chunk exists but isn't loaded do it async
        if (chunk == null && runnable != null && loader != null && loader.chunkExists(this.field_73251_h, i, j))
        {
            ChunkIOExecutor.queueChunkLoad(this.field_73251_h, loader, this, i, j, runnable);
            return null;
        }

        // CraftBukkit end

        if (chunk == null)
        {
            chunk = this.func_73239_e(i, j);

            if (chunk == null)
            {
                if (this.field_73246_d == null)
                {
                    chunk = this.field_73249_c;
                }
                else
                {
                    try
                    {
                        chunk = this.field_73246_d.func_73154_d(i, j);
                    }
                    catch (Throwable throwable)
                    {
                        CrashReport crashreport = CrashReport.func_85055_a(throwable, "Exception generating new chunk");
                        CrashReportCategory crashreportcategory = crashreport.func_85058_a("Chunk to be generated");
                        crashreportcategory.func_71507_a("Location", String.format("%d,%d", new Object[] { Integer.valueOf(i), Integer.valueOf(j)}));
                        crashreportcategory.func_71507_a("Position hash", Long.valueOf(LongHash.toLong(i, j)));
                        crashreportcategory.func_71507_a("Generator", this.field_73246_d.func_73148_d());
                        throw new ReportedException(crashreport);
                    }
                }

                newChunk = true; // CraftBukkit
            }

            this.field_73244_f.put(LongHash.toLong(i, j), chunk); // CraftBukkit

            if (chunk != null)
            {
                chunk.func_76631_c();
            }

            // CraftBukkit start
            Server server = this.field_73251_h.getServer();

            if (server != null)
            {
                /*
                 * If it's a new world, the first few chunks are generated inside
                 * the World constructor. We can't reliably alter that, so we have
                 * no way of creating a CraftWorld/CraftServer at that point.
                 */
                server.getPluginManager().callEvent(new org.bukkit.event.world.ChunkLoadEvent(chunk.bukkitChunk, newChunk));
            }

            // CraftBukkit end
            chunk.func_76624_a(this, this, i, j);
        }

        // CraftBukkit start - If we didn't need to load the chunk run the callback now
        if (runnable != null)
        {
            runnable.run();
        }

        // CraftBukkit end
        return chunk;
    }

    public Chunk func_73154_d(int p_73154_1_, int p_73154_2_)
    {
        // CraftBukkit start
        Chunk chunk = (Chunk) this.field_73244_f.get(LongHash.toLong(p_73154_1_, p_73154_2_));
        chunk = chunk == null ? (!this.field_73251_h.field_72987_B && !this.field_73250_a ? this.field_73249_c : this.func_73158_c(p_73154_1_, p_73154_2_)) : chunk;

        if (chunk == this.field_73249_c)
        {
            return chunk;
        }

        if (p_73154_1_ != chunk.field_76635_g || p_73154_2_ != chunk.field_76647_h)
        {
            this.field_73251_h.func_98180_V().func_98232_c("Chunk (" + chunk.field_76635_g + ", " + chunk.field_76647_h + ") stored at  (" + p_73154_1_ + ", " + p_73154_2_ + ") in world '" + field_73251_h.getWorld().getName() + "'");
            this.field_73251_h.func_98180_V().func_98232_c(chunk.getClass().getName());
            Throwable ex = new Throwable();
            ex.fillInStackTrace();
            ex.printStackTrace();
        }

        return chunk;
        // CraftBukkit end
    }

    public Chunk func_73239_e(int p_73239_1_, int p_73239_2_)   // CraftBukkit - private -> public
    {
        if (this.field_73247_e == null)
        {
            return null;
        }
        else
        {
            try
            {
                Chunk chunk = this.field_73247_e.func_75815_a(this.field_73251_h, p_73239_1_, p_73239_2_);

                if (chunk != null)
                {
                    chunk.field_76641_n = this.field_73251_h.func_82737_E();

                    if (this.field_73246_d != null)
                    {
                        this.field_73246_d.func_82695_e(p_73239_1_, p_73239_2_);
                    }
                }

                return chunk;
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                return null;
            }
        }
    }

    public void func_73243_a(Chunk p_73243_1_)   // CraftBukkit - private -> public
    {
        if (this.field_73247_e != null)
        {
            try
            {
                this.field_73247_e.func_75819_b(this.field_73251_h, p_73243_1_);
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
    }

    public void func_73242_b(Chunk p_73242_1_)   // CraftBukkit - private -> public
    {
        if (this.field_73247_e != null)
        {
            try
            {
                p_73242_1_.field_76641_n = this.field_73251_h.func_82737_E();
                this.field_73247_e.func_75816_a(this.field_73251_h, p_73242_1_);
            }
            catch (Exception ioexception)     // CraftBukkit - IOException -> Exception
            {
                ioexception.printStackTrace();
                // CraftBukkit start - remove extra exception
            }

            // } catch (ExceptionWorldConflict exceptionworldconflict) {
            //     exceptionworldconflict.printStackTrace();
            // }
            // CraftBukkit end
        }
    }

    public void func_73153_a(IChunkProvider p_73153_1_, int p_73153_2_, int p_73153_3_)
    {
        Chunk chunk = this.func_73154_d(p_73153_2_, p_73153_3_);

        if (!chunk.field_76646_k)
        {
            chunk.field_76646_k = true;

            if (this.field_73246_d != null)
            {
                this.field_73246_d.func_73153_a(p_73153_1_, p_73153_2_, p_73153_3_);
                // CraftBukkit start
                BlockSand.field_72192_a = true;
                Random random = new Random();
                random.setSeed(field_73251_h.func_72905_C());
                long xRand = random.nextLong() / 2L * 2L + 1L;
                long zRand = random.nextLong() / 2L * 2L + 1L;
                random.setSeed((long) p_73153_2_ * xRand + (long) p_73153_3_ * zRand ^ field_73251_h.func_72905_C());
                org.bukkit.World world = this.field_73251_h.getWorld();

                if (world != null)
                {
                    for (org.bukkit.generator.BlockPopulator populator : world.getPopulators())
                    {
                        populator.populate(world, random, chunk.bukkitChunk);
                    }
                }

                BlockSand.field_72192_a = false;
                this.field_73251_h.getServer().getPluginManager().callEvent(new org.bukkit.event.world.ChunkPopulateEvent(chunk.bukkitChunk));
                // CraftBukkit end
                chunk.func_76630_e();
            }
        }
    }

    public boolean func_73151_a(boolean p_73151_1_, IProgressUpdate p_73151_2_)
    {
        int i = 0;
        // CraftBukkit start
        Iterator iterator = this.field_73244_f.values().iterator();

        while (iterator.hasNext())
        {
            Chunk chunk = (Chunk)iterator.next();
            // CraftBukkit end

            if (p_73151_1_)
            {
                this.func_73243_a(chunk);
            }

            if (chunk.func_76601_a(p_73151_1_))
            {
                this.func_73242_b(chunk);
                chunk.field_76643_l = false;
                ++i;

                if (i == 24 && !p_73151_1_)
                {
                    return false;
                }
            }
        }

        if (p_73151_1_)
        {
            if (this.field_73247_e == null)
            {
                return true;
            }

            this.field_73247_e.func_75818_b();
        }

        return true;
    }

    public boolean func_73156_b()
    {
        if (!this.field_73251_h.field_73058_d)
        {
            // CraftBukkit start
            Server server = this.field_73251_h.getServer();

            for (int i = 0; i < 100 && !this.field_73248_b.isEmpty(); i++)
            {
                long chunkcoordinates = this.field_73248_b.popFirst();
                Chunk chunk = this.field_73244_f.get(chunkcoordinates);

                if (chunk == null)
                {
                    continue;
                }

                ChunkUnloadEvent event = new ChunkUnloadEvent(chunk.bukkitChunk);
                server.getPluginManager().callEvent(event);

                if (!event.isCancelled())
                {
                    chunk.func_76623_d();
                    this.func_73242_b(chunk);
                    this.func_73243_a(chunk);
                    // this.unloadQueue.remove(integer);
                    this.field_73244_f.remove(chunkcoordinates); // CraftBukkit
                }
            }

            // CraftBukkit end

            if (this.field_73247_e != null)
            {
                this.field_73247_e.func_75817_a();
            }
        }

        return this.field_73246_d.func_73156_b();
    }

    public boolean func_73157_c()
    {
        return !this.field_73251_h.field_73058_d;
    }

    public String func_73148_d()
    {
        return "ServerChunkCache: " + this.field_73244_f.values().size() + " Drop: " + this.field_73248_b.size(); // CraftBukkit
    }

    public List func_73155_a(EnumCreatureType p_73155_1_, int p_73155_2_, int p_73155_3_, int p_73155_4_)
    {
        return this.field_73246_d.func_73155_a(p_73155_1_, p_73155_2_, p_73155_3_, p_73155_4_);
    }

    public ChunkPosition func_73150_a(World p_73150_1_, String p_73150_2_, int p_73150_3_, int p_73150_4_, int p_73150_5_)
    {
        return this.field_73246_d.func_73150_a(p_73150_1_, p_73150_2_, p_73150_3_, p_73150_4_, p_73150_5_);
    }

    public int func_73152_e()
    {
        return this.field_73244_f.values().size(); // CraftBukkit
    }

    public void func_82695_e(int p_82695_1_, int p_82695_2_) {}
}
