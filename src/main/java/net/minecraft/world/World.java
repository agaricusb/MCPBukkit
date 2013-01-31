package net.minecraft.world;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFluid;
import net.minecraft.block.BlockHalfSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.command.IEntitySelector;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.profiler.Profiler;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ReportedException;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3Pool;
import net.minecraft.village.VillageCollection;
import net.minecraft.village.VillageSiege;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldInfo;

// CraftBukkit start
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.util.LongHashSet;
import org.bukkit.craftbukkit.util.UnsafeList;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
// CraftBukkit end

public abstract class World implements IBlockAccess
{
    public boolean field_72999_e = false;
    public List field_72996_f = new ArrayList();
    protected List field_72997_g = new ArrayList();
    public List field_73009_h = new ArrayList();
    private List field_73002_a = new ArrayList();
    private List field_73000_b = new ArrayList();
    public List field_73010_i = new ArrayList();
    public List field_73007_j = new ArrayList();
    private long field_73001_c = 16777215L;
    public int field_73008_k = 0;
    protected int field_73005_l = (new Random()).nextInt();
    protected final int field_73006_m = 1013904223;
    protected float field_73003_n;
    protected float field_73004_o;
    protected float field_73018_p;
    protected float field_73017_q;
    public int field_73016_r = 0;
    public boolean field_73014_t = false;
    public boolean callingPlaceEvent = false; // CraftBukkit
    public int field_73013_u;
    public Random field_73012_v = new Random();
    public WorldProvider field_73011_w; // CraftBukkit - remove final
    protected List field_73021_x = new ArrayList();
    public IChunkProvider field_73020_y; // CraftBukkit - protected -> public
    protected final ISaveHandler field_73019_z;
    public WorldInfo field_72986_A; // CraftBukkit - protected -> public
    public boolean field_72987_B;
    public MapStorage field_72988_C;
    public final VillageCollection field_72982_D;
    protected final VillageSiege field_72983_E = new VillageSiege(this);
    public final Profiler field_72984_F;
    private final Vec3Pool field_82741_K = new Vec3Pool(300, 2000);
    private final Calendar field_83016_L = Calendar.getInstance();
    private UnsafeList field_72998_d = new UnsafeList(); // CraftBukkit - ArrayList -> UnsafeList
    private boolean field_72989_L;
    // CraftBukkit start - public, longhashset
    public boolean field_72985_G = true;
    public boolean field_72992_H = true;
    protected LongHashSet field_72993_I = new LongHashSet();
    public long ticksPerAnimalSpawns;
    public long ticksPerMonsterSpawns;
    // CraftBukkit end
    private int field_72990_M;
    int[] field_72994_J;
    private List field_72991_N;
    public boolean field_72995_K;

    public BiomeGenBase func_72807_a(int p_72807_1_, int p_72807_2_)
    {
        if (this.func_72899_e(p_72807_1_, 0, p_72807_2_))
        {
            Chunk chunk = this.func_72938_d(p_72807_1_, p_72807_2_);

            if (chunk != null)
            {
                return chunk.func_76591_a(p_72807_1_ & 15, p_72807_2_ & 15, this.field_73011_w.field_76578_c);
            }
        }

        return this.field_73011_w.field_76578_c.func_76935_a(p_72807_1_, p_72807_2_);
    }

    public WorldChunkManager func_72959_q()
    {
        return this.field_73011_w.field_76578_c;
    }

    // CraftBukkit start
    private final CraftWorld world;
    public boolean pvpMode;
    public boolean keepSpawnInMemory = true;
    public ChunkGenerator generator;
    Chunk lastChunkAccessed;
    int lastXAccessed = Integer.MIN_VALUE;
    int lastZAccessed = Integer.MIN_VALUE;
    final Object chunkLock = new Object();

    public CraftWorld getWorld()
    {
        return this.world;
    }

    public CraftServer getServer()
    {
        return (CraftServer) Bukkit.getServer();
    }

    // Changed signature
    public World(ISaveHandler p_i3732_1_, String p_i3732_2_, WorldSettings p_i3732_3_, WorldProvider p_i3732_4_, Profiler p_i3732_5_, ChunkGenerator gen, org.bukkit.World.Environment env)
    {
        this.generator = gen;
        this.world = new CraftWorld((WorldServer) this, gen, env);
        this.ticksPerAnimalSpawns = this.getServer().getTicksPerAnimalSpawns(); // CraftBukkit
        this.ticksPerMonsterSpawns = this.getServer().getTicksPerMonsterSpawns(); // CraftBukkit
        // CraftBukkit end
        this.field_72990_M = this.field_73012_v.nextInt(12000);
        this.field_72994_J = new int[32768];
        this.field_72991_N = new UnsafeList(); // CraftBukkit - ArrayList -> UnsafeList
        this.field_72995_K = false;
        this.field_73019_z = p_i3732_1_;
        this.field_72984_F = p_i3732_5_;
        this.field_72988_C = new MapStorage(p_i3732_1_);
        this.field_72986_A = p_i3732_1_.func_75757_d();

        if (p_i3732_4_ != null)
        {
            this.field_73011_w = p_i3732_4_;
        }
        else if (this.field_72986_A != null && this.field_72986_A.func_76076_i() != 0)
        {
            this.field_73011_w = WorldProvider.func_76570_a(this.field_72986_A.func_76076_i());
        }
        else
        {
            this.field_73011_w = WorldProvider.func_76570_a(0);
        }

        if (this.field_72986_A == null)
        {
            this.field_72986_A = new WorldInfo(p_i3732_3_, p_i3732_2_);
        }
        else
        {
            this.field_72986_A.func_76062_a(p_i3732_2_);
        }

        this.field_73011_w.func_76558_a(this);
        this.field_73020_y = this.func_72970_h();

        if (!this.field_72986_A.func_76070_v())
        {
            try
            {
                this.func_72963_a(p_i3732_3_);
            }
            catch (Throwable throwable)
            {
                CrashReport crashreport = CrashReport.func_85055_a(throwable, "Exception initializing level");

                try
                {
                    this.func_72914_a(crashreport);
                }
                catch (Throwable throwable1)
                {
                    ;
                }

                throw new ReportedException(crashreport);
            }

            this.field_72986_A.func_76091_d(true);
        }

        VillageCollection villagecollection = (VillageCollection)this.field_72988_C.func_75742_a(VillageCollection.class, "villages");

        if (villagecollection == null)
        {
            this.field_72982_D = new VillageCollection(this);
            this.field_72988_C.func_75745_a("villages", this.field_72982_D);
        }
        else
        {
            this.field_72982_D = villagecollection;
            this.field_72982_D.func_82566_a(this);
        }

        this.func_72966_v();
        this.func_72947_a();
        this.getServer().addWorld(this.world); // CraftBukkit
    }

    protected abstract IChunkProvider func_72970_h();

    protected void func_72963_a(WorldSettings p_72963_1_)
    {
        this.field_72986_A.func_76091_d(true);
    }

    public int func_72922_b(int p_72922_1_, int p_72922_2_)
    {
        int k;

        for (k = 63; !this.func_72799_c(p_72922_1_, k + 1, p_72922_2_); ++k)
        {
            ;
        }

        return this.func_72798_a(p_72922_1_, k, p_72922_2_);
    }

    public int func_72798_a(int p_72798_1_, int p_72798_2_, int p_72798_3_)
    {
        if (p_72798_1_ >= -30000000 && p_72798_3_ >= -30000000 && p_72798_1_ < 30000000 && p_72798_3_ < 30000000)
        {
            if (p_72798_2_ < 0)
            {
                return 0;
            }
            else if (p_72798_2_ >= 256)
            {
                return 0;
            }
            else
            {
                Chunk chunk = null;

                try
                {
                    chunk = this.func_72964_e(p_72798_1_ >> 4, p_72798_3_ >> 4);
                    return chunk.func_76610_a(p_72798_1_ & 15, p_72798_2_, p_72798_3_ & 15);
                }
                catch (Throwable throwable)
                {
                    CrashReport crashreport = CrashReport.func_85055_a(throwable, "Exception getting block type in world");
                    CrashReportCategory crashreportcategory = crashreport.func_85058_a("Requested block coordinates");
                    crashreportcategory.func_71507_a("Found chunk", Boolean.valueOf(chunk == null));
                    crashreportcategory.func_71507_a("Location", CrashReportCategory.func_85071_a(p_72798_1_, p_72798_2_, p_72798_3_));
                    throw new ReportedException(crashreport);
                }
            }
        }
        else
        {
            return 0;
        }
    }

    public int func_72952_b(int p_72952_1_, int p_72952_2_, int p_72952_3_)
    {
        return p_72952_1_ >= -30000000 && p_72952_3_ >= -30000000 && p_72952_1_ < 30000000 && p_72952_3_ < 30000000 ? (p_72952_2_ < 0 ? 0 : (p_72952_2_ >= 256 ? 0 : this.func_72964_e(p_72952_1_ >> 4, p_72952_3_ >> 4).func_76596_b(p_72952_1_ & 15, p_72952_2_, p_72952_3_ & 15))) : 0;
    }

    public boolean func_72799_c(int p_72799_1_, int p_72799_2_, int p_72799_3_)
    {
        return this.func_72798_a(p_72799_1_, p_72799_2_, p_72799_3_) == 0;
    }

    public boolean func_72927_d(int p_72927_1_, int p_72927_2_, int p_72927_3_)
    {
        int l = this.func_72798_a(p_72927_1_, p_72927_2_, p_72927_3_);
        return Block.field_71973_m[l] != null && Block.field_71973_m[l].func_71887_s();
    }

    public int func_85175_e(int p_85175_1_, int p_85175_2_, int p_85175_3_)
    {
        int l = this.func_72798_a(p_85175_1_, p_85175_2_, p_85175_3_);
        return Block.field_71973_m[l] != null ? Block.field_71973_m[l].func_71857_b() : -1;
    }

    public boolean func_72899_e(int p_72899_1_, int p_72899_2_, int p_72899_3_)
    {
        return p_72899_2_ >= 0 && p_72899_2_ < 256 ? this.func_72916_c(p_72899_1_ >> 4, p_72899_3_ >> 4) : false;
    }

    public boolean func_72873_a(int p_72873_1_, int p_72873_2_, int p_72873_3_, int p_72873_4_)
    {
        return this.func_72904_c(p_72873_1_ - p_72873_4_, p_72873_2_ - p_72873_4_, p_72873_3_ - p_72873_4_, p_72873_1_ + p_72873_4_, p_72873_2_ + p_72873_4_, p_72873_3_ + p_72873_4_);
    }

    public boolean func_72904_c(int p_72904_1_, int p_72904_2_, int p_72904_3_, int p_72904_4_, int p_72904_5_, int p_72904_6_)
    {
        if (p_72904_5_ >= 0 && p_72904_2_ < 256)
        {
            p_72904_1_ >>= 4;
            p_72904_3_ >>= 4;
            p_72904_4_ >>= 4;
            p_72904_6_ >>= 4;

            for (int k1 = p_72904_1_; k1 <= p_72904_4_; ++k1)
            {
                for (int l1 = p_72904_3_; l1 <= p_72904_6_; ++l1)
                {
                    // CraftBukkit - check unload queue too so we don't leak a chunk
                    if (!this.func_72916_c(k1, l1) || ((WorldServer) this).field_73059_b.field_73248_b.contains(k1, l1))
                    {
                        return false;
                    }
                }
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    protected boolean func_72916_c(int p_72916_1_, int p_72916_2_)
    {
        return this.field_73020_y.func_73149_a(p_72916_1_, p_72916_2_);
    }

    public Chunk func_72938_d(int p_72938_1_, int p_72938_2_)
    {
        return this.func_72964_e(p_72938_1_ >> 4, p_72938_2_ >> 4);
    }

    // CraftBukkit start
    public Chunk func_72964_e(int p_72964_1_, int p_72964_2_)
    {
        Chunk result = null;

        synchronized (this.chunkLock)
        {
            if (this.lastChunkAccessed == null || this.lastXAccessed != p_72964_1_ || this.lastZAccessed != p_72964_2_)
            {
                this.lastChunkAccessed = this.field_73020_y.func_73154_d(p_72964_1_, p_72964_2_);
                this.lastXAccessed = p_72964_1_;
                this.lastZAccessed = p_72964_2_;
            }

            result = this.lastChunkAccessed;
        }

        return result;
    }
    // CraftBukkit end

    public boolean func_72961_c(int p_72961_1_, int p_72961_2_, int p_72961_3_, int p_72961_4_, int p_72961_5_)
    {
        return this.func_72930_a(p_72961_1_, p_72961_2_, p_72961_3_, p_72961_4_, p_72961_5_, true);
    }

    public boolean func_72930_a(int p_72930_1_, int p_72930_2_, int p_72930_3_, int p_72930_4_, int p_72930_5_, boolean p_72930_6_)
    {
        if (p_72930_1_ >= -30000000 && p_72930_3_ >= -30000000 && p_72930_1_ < 30000000 && p_72930_3_ < 30000000)
        {
            if (p_72930_2_ < 0)
            {
                return false;
            }
            else if (p_72930_2_ >= 256)
            {
                return false;
            }
            else
            {
                Chunk chunk = this.func_72964_e(p_72930_1_ >> 4, p_72930_3_ >> 4);
                boolean flag1 = chunk.func_76592_a(p_72930_1_ & 15, p_72930_2_, p_72930_3_ & 15, p_72930_4_, p_72930_5_);
                this.field_72984_F.func_76320_a("checkLight");
                this.func_72969_x(p_72930_1_, p_72930_2_, p_72930_3_);
                this.field_72984_F.func_76319_b();

                if (p_72930_6_ && flag1 && (this.field_72995_K || chunk.field_76642_o))
                {
                    this.func_72845_h(p_72930_1_, p_72930_2_, p_72930_3_);
                }

                return flag1;
            }
        }
        else
        {
            return false;
        }
    }

    public boolean func_72822_b(int p_72822_1_, int p_72822_2_, int p_72822_3_, int p_72822_4_)
    {
        if (p_72822_1_ >= -30000000 && p_72822_3_ >= -30000000 && p_72822_1_ < 30000000 && p_72822_3_ < 30000000)
        {
            if (p_72822_2_ < 0)
            {
                return false;
            }
            else if (p_72822_2_ >= 256)
            {
                return false;
            }
            else
            {
                Chunk chunk = this.func_72964_e(p_72822_1_ >> 4, p_72822_3_ >> 4);
                boolean flag = chunk.func_76598_a(p_72822_1_ & 15, p_72822_2_, p_72822_3_ & 15, p_72822_4_);
                this.field_72984_F.func_76320_a("checkLight");
                this.func_72969_x(p_72822_1_, p_72822_2_, p_72822_3_);
                this.field_72984_F.func_76319_b();

                if (flag && (this.field_72995_K || chunk.field_76642_o))
                {
                    this.func_72845_h(p_72822_1_, p_72822_2_, p_72822_3_);
                }

                return flag;
            }
        }
        else
        {
            return false;
        }
    }

    public Material func_72803_f(int p_72803_1_, int p_72803_2_, int p_72803_3_)
    {
        int l = this.func_72798_a(p_72803_1_, p_72803_2_, p_72803_3_);
        return l == 0 ? Material.field_76249_a : Block.field_71973_m[l].field_72018_cp;
    }

    public int func_72805_g(int p_72805_1_, int p_72805_2_, int p_72805_3_)
    {
        if (p_72805_1_ >= -30000000 && p_72805_3_ >= -30000000 && p_72805_1_ < 30000000 && p_72805_3_ < 30000000)
        {
            if (p_72805_2_ < 0)
            {
                return 0;
            }
            else if (p_72805_2_ >= 256)
            {
                return 0;
            }
            else
            {
                Chunk chunk = this.func_72964_e(p_72805_1_ >> 4, p_72805_3_ >> 4);
                p_72805_1_ &= 15;
                p_72805_3_ &= 15;
                return chunk.func_76628_c(p_72805_1_, p_72805_2_, p_72805_3_);
            }
        }
        else
        {
            return 0;
        }
    }

    public void func_72921_c(int p_72921_1_, int p_72921_2_, int p_72921_3_, int p_72921_4_)
    {
        if (this.func_72881_d(p_72921_1_, p_72921_2_, p_72921_3_, p_72921_4_))
        {
            this.func_72851_f(p_72921_1_, p_72921_2_, p_72921_3_, this.func_72798_a(p_72921_1_, p_72921_2_, p_72921_3_));
        }
    }

    public boolean func_72881_d(int p_72881_1_, int p_72881_2_, int p_72881_3_, int p_72881_4_)
    {
        if (p_72881_1_ >= -30000000 && p_72881_3_ >= -30000000 && p_72881_1_ < 30000000 && p_72881_3_ < 30000000)
        {
            if (p_72881_2_ < 0)
            {
                return false;
            }
            else if (p_72881_2_ >= 256)
            {
                return false;
            }
            else
            {
                Chunk chunk = this.func_72964_e(p_72881_1_ >> 4, p_72881_3_ >> 4);
                int i1 = p_72881_1_ & 15;
                int j1 = p_72881_3_ & 15;
                boolean flag = chunk.func_76589_b(i1, p_72881_2_, j1, p_72881_4_);

                if (flag && (this.field_72995_K || chunk.field_76642_o && Block.field_71983_r[chunk.func_76610_a(i1, p_72881_2_, j1) & 4095]))
                {
                    this.func_72845_h(p_72881_1_, p_72881_2_, p_72881_3_);
                }

                return flag;
            }
        }
        else
        {
            return false;
        }
    }

    public boolean func_72859_e(int p_72859_1_, int p_72859_2_, int p_72859_3_, int p_72859_4_)
    {
        if (this.func_72822_b(p_72859_1_, p_72859_2_, p_72859_3_, p_72859_4_))
        {
            this.func_72851_f(p_72859_1_, p_72859_2_, p_72859_3_, p_72859_4_);
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean func_72832_d(int p_72832_1_, int p_72832_2_, int p_72832_3_, int p_72832_4_, int p_72832_5_)
    {
        if (this.func_72961_c(p_72832_1_, p_72832_2_, p_72832_3_, p_72832_4_, p_72832_5_))
        {
            this.func_72851_f(p_72832_1_, p_72832_2_, p_72832_3_, p_72832_4_);
            return true;
        }
        else
        {
            return false;
        }
    }

    public void func_72845_h(int p_72845_1_, int p_72845_2_, int p_72845_3_)
    {
        for (int l = 0; l < this.field_73021_x.size(); ++l)
        {
            ((IWorldAccess)this.field_73021_x.get(l)).func_72710_a(p_72845_1_, p_72845_2_, p_72845_3_);
        }
    }

    public void func_72851_f(int p_72851_1_, int p_72851_2_, int p_72851_3_, int p_72851_4_)
    {
        this.func_72898_h(p_72851_1_, p_72851_2_, p_72851_3_, p_72851_4_);
    }

    public void func_72975_g(int p_72975_1_, int p_72975_2_, int p_72975_3_, int p_72975_4_)
    {
        int i1;

        if (p_72975_3_ > p_72975_4_)
        {
            i1 = p_72975_4_;
            p_72975_4_ = p_72975_3_;
            p_72975_3_ = i1;
        }

        if (!this.field_73011_w.field_76576_e)
        {
            for (i1 = p_72975_3_; i1 <= p_72975_4_; ++i1)
            {
                this.func_72936_c(EnumSkyBlock.Sky, p_72975_1_, i1, p_72975_2_);
            }
        }

        this.func_72909_d(p_72975_1_, p_72975_3_, p_72975_2_, p_72975_1_, p_72975_4_, p_72975_2_);
    }

    public void func_72862_i(int p_72862_1_, int p_72862_2_, int p_72862_3_)
    {
        for (int l = 0; l < this.field_73021_x.size(); ++l)
        {
            ((IWorldAccess)this.field_73021_x.get(l)).func_72707_a(p_72862_1_, p_72862_2_, p_72862_3_, p_72862_1_, p_72862_2_, p_72862_3_);
        }
    }

    public void func_72909_d(int p_72909_1_, int p_72909_2_, int p_72909_3_, int p_72909_4_, int p_72909_5_, int p_72909_6_)
    {
        for (int k1 = 0; k1 < this.field_73021_x.size(); ++k1)
        {
            ((IWorldAccess)this.field_73021_x.get(k1)).func_72707_a(p_72909_1_, p_72909_2_, p_72909_3_, p_72909_4_, p_72909_5_, p_72909_6_);
        }
    }

    public void func_72898_h(int p_72898_1_, int p_72898_2_, int p_72898_3_, int p_72898_4_)
    {
        this.func_72821_m(p_72898_1_ - 1, p_72898_2_, p_72898_3_, p_72898_4_);
        this.func_72821_m(p_72898_1_ + 1, p_72898_2_, p_72898_3_, p_72898_4_);
        this.func_72821_m(p_72898_1_, p_72898_2_ - 1, p_72898_3_, p_72898_4_);
        this.func_72821_m(p_72898_1_, p_72898_2_ + 1, p_72898_3_, p_72898_4_);
        this.func_72821_m(p_72898_1_, p_72898_2_, p_72898_3_ - 1, p_72898_4_);
        this.func_72821_m(p_72898_1_, p_72898_2_, p_72898_3_ + 1, p_72898_4_);
    }

    private void func_72821_m(int p_72821_1_, int p_72821_2_, int p_72821_3_, int p_72821_4_)
    {
        if (!this.field_73014_t && !this.field_72995_K)
        {
            int i1 = this.func_72798_a(p_72821_1_, p_72821_2_, p_72821_3_);
            Block block = Block.field_71973_m[i1];

            if (block != null)
            {
                try
                {
                    // CraftBukkit start
                    CraftWorld world = ((WorldServer) this).getWorld();

                    if (world != null)
                    {
                        BlockPhysicsEvent event = new BlockPhysicsEvent(world.getBlockAt(p_72821_1_, p_72821_2_, p_72821_3_), p_72821_4_);
                        this.getServer().getPluginManager().callEvent(event);

                        if (event.isCancelled())
                        {
                            return;
                        }
                    }

                    // CraftBukkit end
                    block.func_71863_a(this, p_72821_1_, p_72821_2_, p_72821_3_, p_72821_4_);
                }
                catch (Throwable throwable)
                {
                    CrashReport crashreport = CrashReport.func_85055_a(throwable, "Exception while updating neighbours");
                    CrashReportCategory crashreportcategory = crashreport.func_85058_a("Block being updated");
                    int j1;

                    try
                    {
                        j1 = this.func_72805_g(p_72821_1_, p_72821_2_, p_72821_3_);
                    }
                    catch (Throwable throwable1)
                    {
                        j1 = -1;
                    }

                    crashreportcategory.func_71500_a("Source block type", (Callable)(new CallableLvl1(this, p_72821_4_)));
                    CrashReportCategory.func_85068_a(crashreportcategory, p_72821_1_, p_72821_2_, p_72821_3_, i1, j1);
                    throw new ReportedException(crashreport);
                }
            }
        }
    }

    public boolean func_72937_j(int p_72937_1_, int p_72937_2_, int p_72937_3_)
    {
        return this.func_72964_e(p_72937_1_ >> 4, p_72937_3_ >> 4).func_76619_d(p_72937_1_ & 15, p_72937_2_, p_72937_3_ & 15);
    }

    public int func_72883_k(int p_72883_1_, int p_72883_2_, int p_72883_3_)
    {
        if (p_72883_2_ < 0)
        {
            return 0;
        }
        else
        {
            if (p_72883_2_ >= 256)
            {
                p_72883_2_ = 255;
            }

            return this.func_72964_e(p_72883_1_ >> 4, p_72883_3_ >> 4).func_76629_c(p_72883_1_ & 15, p_72883_2_, p_72883_3_ & 15, 0);
        }
    }

    public int func_72957_l(int p_72957_1_, int p_72957_2_, int p_72957_3_)
    {
        return this.func_72849_a(p_72957_1_, p_72957_2_, p_72957_3_, true);
    }

    public int func_72849_a(int p_72849_1_, int p_72849_2_, int p_72849_3_, boolean p_72849_4_)
    {
        if (p_72849_1_ >= -30000000 && p_72849_3_ >= -30000000 && p_72849_1_ < 30000000 && p_72849_3_ < 30000000)
        {
            if (p_72849_4_)
            {
                int l = this.func_72798_a(p_72849_1_, p_72849_2_, p_72849_3_);

                if (l == Block.field_72079_ak.field_71990_ca || l == Block.field_72092_bO.field_71990_ca || l == Block.field_72050_aA.field_71990_ca || l == Block.field_72057_aH.field_71990_ca || l == Block.field_72063_at.field_71990_ca)
                {
                    int i1 = this.func_72849_a(p_72849_1_, p_72849_2_ + 1, p_72849_3_, false);
                    int j1 = this.func_72849_a(p_72849_1_ + 1, p_72849_2_, p_72849_3_, false);
                    int k1 = this.func_72849_a(p_72849_1_ - 1, p_72849_2_, p_72849_3_, false);
                    int l1 = this.func_72849_a(p_72849_1_, p_72849_2_, p_72849_3_ + 1, false);
                    int i2 = this.func_72849_a(p_72849_1_, p_72849_2_, p_72849_3_ - 1, false);

                    if (j1 > i1)
                    {
                        i1 = j1;
                    }

                    if (k1 > i1)
                    {
                        i1 = k1;
                    }

                    if (l1 > i1)
                    {
                        i1 = l1;
                    }

                    if (i2 > i1)
                    {
                        i1 = i2;
                    }

                    return i1;
                }
            }

            if (p_72849_2_ < 0)
            {
                return 0;
            }
            else
            {
                if (p_72849_2_ >= 256)
                {
                    p_72849_2_ = 255;
                }

                Chunk chunk = this.func_72964_e(p_72849_1_ >> 4, p_72849_3_ >> 4);
                p_72849_1_ &= 15;
                p_72849_3_ &= 15;
                return chunk.func_76629_c(p_72849_1_, p_72849_2_, p_72849_3_, this.field_73008_k);
            }
        }
        else
        {
            return 15;
        }
    }

    public int func_72976_f(int p_72976_1_, int p_72976_2_)
    {
        if (p_72976_1_ >= -30000000 && p_72976_2_ >= -30000000 && p_72976_1_ < 30000000 && p_72976_2_ < 30000000)
        {
            if (!this.func_72916_c(p_72976_1_ >> 4, p_72976_2_ >> 4))
            {
                return 0;
            }
            else
            {
                Chunk chunk = this.func_72964_e(p_72976_1_ >> 4, p_72976_2_ >> 4);
                return chunk.func_76611_b(p_72976_1_ & 15, p_72976_2_ & 15);
            }
        }
        else
        {
            return 0;
        }
    }

    public int func_82734_g(int p_82734_1_, int p_82734_2_)
    {
        if (p_82734_1_ >= -30000000 && p_82734_2_ >= -30000000 && p_82734_1_ < 30000000 && p_82734_2_ < 30000000)
        {
            if (!this.func_72916_c(p_82734_1_ >> 4, p_82734_2_ >> 4))
            {
                return 0;
            }
            else
            {
                Chunk chunk = this.func_72964_e(p_82734_1_ >> 4, p_82734_2_ >> 4);
                return chunk.field_82912_p;
            }
        }
        else
        {
            return 0;
        }
    }

    public int func_72972_b(EnumSkyBlock p_72972_1_, int p_72972_2_, int p_72972_3_, int p_72972_4_)
    {
        if (p_72972_3_ < 0)
        {
            p_72972_3_ = 0;
        }

        if (p_72972_3_ >= 256)
        {
            p_72972_3_ = 255;
        }

        if (p_72972_2_ >= -30000000 && p_72972_4_ >= -30000000 && p_72972_2_ < 30000000 && p_72972_4_ < 30000000)
        {
            int l = p_72972_2_ >> 4;
            int i1 = p_72972_4_ >> 4;

            if (!this.func_72916_c(l, i1))
            {
                return p_72972_1_.field_77198_c;
            }
            else
            {
                Chunk chunk = this.func_72964_e(l, i1);
                return chunk.func_76614_a(p_72972_1_, p_72972_2_ & 15, p_72972_3_, p_72972_4_ & 15);
            }
        }
        else
        {
            return p_72972_1_.field_77198_c;
        }
    }

    public void func_72915_b(EnumSkyBlock p_72915_1_, int p_72915_2_, int p_72915_3_, int p_72915_4_, int p_72915_5_)
    {
        if (p_72915_2_ >= -30000000 && p_72915_4_ >= -30000000 && p_72915_2_ < 30000000 && p_72915_4_ < 30000000)
        {
            if (p_72915_3_ >= 0)
            {
                if (p_72915_3_ < 256)
                {
                    if (this.func_72916_c(p_72915_2_ >> 4, p_72915_4_ >> 4))
                    {
                        Chunk chunk = this.func_72964_e(p_72915_2_ >> 4, p_72915_4_ >> 4);
                        chunk.func_76633_a(p_72915_1_, p_72915_2_ & 15, p_72915_3_, p_72915_4_ & 15, p_72915_5_);

                        for (int i1 = 0; i1 < this.field_73021_x.size(); ++i1)
                        {
                            ((IWorldAccess)this.field_73021_x.get(i1)).func_72711_b(p_72915_2_, p_72915_3_, p_72915_4_);
                        }
                    }
                }
            }
        }
    }

    public void func_72902_n(int p_72902_1_, int p_72902_2_, int p_72902_3_)
    {
        for (int l = 0; l < this.field_73021_x.size(); ++l)
        {
            ((IWorldAccess)this.field_73021_x.get(l)).func_72711_b(p_72902_1_, p_72902_2_, p_72902_3_);
        }
    }

    public float func_72801_o(int p_72801_1_, int p_72801_2_, int p_72801_3_)
    {
        return this.field_73011_w.field_76573_f[this.func_72957_l(p_72801_1_, p_72801_2_, p_72801_3_)];
    }

    public boolean func_72935_r()
    {
        return this.field_73008_k < 4;
    }

    public MovingObjectPosition func_72933_a(Vec3 p_72933_1_, Vec3 p_72933_2_)
    {
        return this.func_72831_a(p_72933_1_, p_72933_2_, false, false);
    }

    public MovingObjectPosition func_72901_a(Vec3 p_72901_1_, Vec3 p_72901_2_, boolean p_72901_3_)
    {
        return this.func_72831_a(p_72901_1_, p_72901_2_, p_72901_3_, false);
    }

    public MovingObjectPosition func_72831_a(Vec3 p_72831_1_, Vec3 p_72831_2_, boolean p_72831_3_, boolean p_72831_4_)
    {
        if (!Double.isNaN(p_72831_1_.field_72450_a) && !Double.isNaN(p_72831_1_.field_72448_b) && !Double.isNaN(p_72831_1_.field_72449_c))
        {
            if (!Double.isNaN(p_72831_2_.field_72450_a) && !Double.isNaN(p_72831_2_.field_72448_b) && !Double.isNaN(p_72831_2_.field_72449_c))
            {
                int i = MathHelper.func_76128_c(p_72831_2_.field_72450_a);
                int j = MathHelper.func_76128_c(p_72831_2_.field_72448_b);
                int k = MathHelper.func_76128_c(p_72831_2_.field_72449_c);
                int l = MathHelper.func_76128_c(p_72831_1_.field_72450_a);
                int i1 = MathHelper.func_76128_c(p_72831_1_.field_72448_b);
                int j1 = MathHelper.func_76128_c(p_72831_1_.field_72449_c);
                int k1 = this.func_72798_a(l, i1, j1);
                int l1 = this.func_72805_g(l, i1, j1);
                Block block = Block.field_71973_m[k1];

                if ((!p_72831_4_ || block == null || block.func_71872_e(this, l, i1, j1) != null) && k1 > 0 && block.func_71913_a(l1, p_72831_3_))
                {
                    MovingObjectPosition movingobjectposition = block.func_71878_a(this, l, i1, j1, p_72831_1_, p_72831_2_);

                    if (movingobjectposition != null)
                    {
                        return movingobjectposition;
                    }
                }

                k1 = 200;

                while (k1-- >= 0)
                {
                    if (Double.isNaN(p_72831_1_.field_72450_a) || Double.isNaN(p_72831_1_.field_72448_b) || Double.isNaN(p_72831_1_.field_72449_c))
                    {
                        return null;
                    }

                    if (l == i && i1 == j && j1 == k)
                    {
                        return null;
                    }

                    boolean flag2 = true;
                    boolean flag3 = true;
                    boolean flag4 = true;
                    double d0 = 999.0D;
                    double d1 = 999.0D;
                    double d2 = 999.0D;

                    if (i > l)
                    {
                        d0 = (double)l + 1.0D;
                    }
                    else if (i < l)
                    {
                        d0 = (double)l + 0.0D;
                    }
                    else
                    {
                        flag2 = false;
                    }

                    if (j > i1)
                    {
                        d1 = (double)i1 + 1.0D;
                    }
                    else if (j < i1)
                    {
                        d1 = (double)i1 + 0.0D;
                    }
                    else
                    {
                        flag3 = false;
                    }

                    if (k > j1)
                    {
                        d2 = (double)j1 + 1.0D;
                    }
                    else if (k < j1)
                    {
                        d2 = (double)j1 + 0.0D;
                    }
                    else
                    {
                        flag4 = false;
                    }

                    double d3 = 999.0D;
                    double d4 = 999.0D;
                    double d5 = 999.0D;
                    double d6 = p_72831_2_.field_72450_a - p_72831_1_.field_72450_a;
                    double d7 = p_72831_2_.field_72448_b - p_72831_1_.field_72448_b;
                    double d8 = p_72831_2_.field_72449_c - p_72831_1_.field_72449_c;

                    if (flag2)
                    {
                        d3 = (d0 - p_72831_1_.field_72450_a) / d6;
                    }

                    if (flag3)
                    {
                        d4 = (d1 - p_72831_1_.field_72448_b) / d7;
                    }

                    if (flag4)
                    {
                        d5 = (d2 - p_72831_1_.field_72449_c) / d8;
                    }

                    boolean flag5 = false;
                    byte b0;

                    if (d3 < d4 && d3 < d5)
                    {
                        if (i > l)
                        {
                            b0 = 4;
                        }
                        else
                        {
                            b0 = 5;
                        }

                        p_72831_1_.field_72450_a = d0;
                        p_72831_1_.field_72448_b += d7 * d3;
                        p_72831_1_.field_72449_c += d8 * d3;
                    }
                    else if (d4 < d5)
                    {
                        if (j > i1)
                        {
                            b0 = 0;
                        }
                        else
                        {
                            b0 = 1;
                        }

                        p_72831_1_.field_72450_a += d6 * d4;
                        p_72831_1_.field_72448_b = d1;
                        p_72831_1_.field_72449_c += d8 * d4;
                    }
                    else
                    {
                        if (k > j1)
                        {
                            b0 = 2;
                        }
                        else
                        {
                            b0 = 3;
                        }

                        p_72831_1_.field_72450_a += d6 * d5;
                        p_72831_1_.field_72448_b += d7 * d5;
                        p_72831_1_.field_72449_c = d2;
                    }

                    Vec3 vec32 = this.func_82732_R().func_72345_a(p_72831_1_.field_72450_a, p_72831_1_.field_72448_b, p_72831_1_.field_72449_c);
                    l = (int)(vec32.field_72450_a = (double)MathHelper.func_76128_c(p_72831_1_.field_72450_a));

                    if (b0 == 5)
                    {
                        --l;
                        ++vec32.field_72450_a;
                    }

                    i1 = (int)(vec32.field_72448_b = (double)MathHelper.func_76128_c(p_72831_1_.field_72448_b));

                    if (b0 == 1)
                    {
                        --i1;
                        ++vec32.field_72448_b;
                    }

                    j1 = (int)(vec32.field_72449_c = (double)MathHelper.func_76128_c(p_72831_1_.field_72449_c));

                    if (b0 == 3)
                    {
                        --j1;
                        ++vec32.field_72449_c;
                    }

                    int i2 = this.func_72798_a(l, i1, j1);
                    int j2 = this.func_72805_g(l, i1, j1);
                    Block block1 = Block.field_71973_m[i2];

                    if ((!p_72831_4_ || block1 == null || block1.func_71872_e(this, l, i1, j1) != null) && i2 > 0 && block1.func_71913_a(j2, p_72831_3_))
                    {
                        MovingObjectPosition movingobjectposition1 = block1.func_71878_a(this, l, i1, j1, p_72831_1_, p_72831_2_);

                        if (movingobjectposition1 != null)
                        {
                            vec32.field_72447_d.release(vec32); // CraftBukkit
                            return movingobjectposition1;
                        }
                    }

                    vec32.field_72447_d.release(vec32); // CraftBukkit
                }

                return null;
            }
            else
            {
                return null;
            }
        }
        else
        {
            return null;
        }
    }

    public void func_72956_a(Entity p_72956_1_, String p_72956_2_, float p_72956_3_, float p_72956_4_)
    {
        if (p_72956_1_ != null && p_72956_2_ != null)
        {
            for (int i = 0; i < this.field_73021_x.size(); ++i)
            {
                ((IWorldAccess)this.field_73021_x.get(i)).func_72704_a(p_72956_2_, p_72956_1_.field_70165_t, p_72956_1_.field_70163_u - (double)p_72956_1_.field_70129_M, p_72956_1_.field_70161_v, p_72956_3_, p_72956_4_);
            }
        }
    }

    public void func_85173_a(EntityPlayer p_85173_1_, String p_85173_2_, float p_85173_3_, float p_85173_4_)
    {
        if (p_85173_1_ != null && p_85173_2_ != null)
        {
            for (int i = 0; i < this.field_73021_x.size(); ++i)
            {
                ((IWorldAccess)this.field_73021_x.get(i)).func_85102_a(p_85173_1_, p_85173_2_, p_85173_1_.field_70165_t, p_85173_1_.field_70163_u - (double)p_85173_1_.field_70129_M, p_85173_1_.field_70161_v, p_85173_3_, p_85173_4_);
            }
        }
    }

    public void func_72908_a(double p_72908_1_, double p_72908_3_, double p_72908_5_, String p_72908_7_, float p_72908_8_, float p_72908_9_)
    {
        if (p_72908_7_ != null)
        {
            for (int i = 0; i < this.field_73021_x.size(); ++i)
            {
                ((IWorldAccess)this.field_73021_x.get(i)).func_72704_a(p_72908_7_, p_72908_1_, p_72908_3_, p_72908_5_, p_72908_8_, p_72908_9_);
            }
        }
    }

    public void func_72980_b(double p_72980_1_, double p_72980_3_, double p_72980_5_, String p_72980_7_, float p_72980_8_, float p_72980_9_, boolean p_72980_10_) {}

    public void func_72934_a(String p_72934_1_, int p_72934_2_, int p_72934_3_, int p_72934_4_)
    {
        for (int l = 0; l < this.field_73021_x.size(); ++l)
        {
            ((IWorldAccess)this.field_73021_x.get(l)).func_72702_a(p_72934_1_, p_72934_2_, p_72934_3_, p_72934_4_);
        }
    }

    public void func_72869_a(String p_72869_1_, double p_72869_2_, double p_72869_4_, double p_72869_6_, double p_72869_8_, double p_72869_10_, double p_72869_12_)
    {
        for (int i = 0; i < this.field_73021_x.size(); ++i)
        {
            ((IWorldAccess)this.field_73021_x.get(i)).func_72708_a(p_72869_1_, p_72869_2_, p_72869_4_, p_72869_6_, p_72869_8_, p_72869_10_, p_72869_12_);
        }
    }

    public boolean func_72942_c(Entity p_72942_1_)
    {
        this.field_73007_j.add(p_72942_1_);
        return true;
    }

    // CraftBukkit start - used for entities other than creatures
    public boolean func_72838_d(Entity p_72838_1_)
    {
        return this.addEntity(p_72838_1_, SpawnReason.DEFAULT); // Set reason as DEFAULT
    }

    public boolean addEntity(Entity entity, SpawnReason spawnReason)   // Changed signature, added SpawnReason
    {
        if (entity == null)
        {
            return false;
        }

        // CraftBukkit end
        int i = MathHelper.func_76128_c(entity.field_70165_t / 16.0D);
        int j = MathHelper.func_76128_c(entity.field_70161_v / 16.0D);
        boolean flag = false;

        if (entity instanceof EntityPlayer)
        {
            flag = true;
        }

        // CraftBukkit start
        org.bukkit.event.Cancellable event = null;

        if (entity instanceof EntityLiving && !(entity instanceof EntityPlayerMP))
        {
            boolean isAnimal = entity instanceof EntityAnimal || entity instanceof EntityWaterMob || entity instanceof EntityGolem;
            boolean isMonster = entity instanceof EntityMob || entity instanceof EntityGhast || entity instanceof EntitySlime;

            if (spawnReason != SpawnReason.CUSTOM)
            {
                if (isAnimal && !field_72992_H || isMonster && !field_72985_G)
                {
                    entity.field_70128_L = true;
                    return false;
                }
            }

            event = CraftEventFactory.callCreatureSpawnEvent((EntityLiving) entity, spawnReason);
        }
        else if (entity instanceof EntityItem)
        {
            event = CraftEventFactory.callItemSpawnEvent((EntityItem) entity);
        }
        else if (entity.getBukkitEntity() instanceof org.bukkit.entity.Projectile)
        {
            // Not all projectiles extend EntityProjectile, so check for Bukkit interface instead
            event = CraftEventFactory.callProjectileLaunchEvent(entity);
        }

        if (event != null && (event.isCancelled() || entity.field_70128_L))
        {
            entity.field_70128_L = true;
            return false;
        }

        // CraftBukkit end

        if (!flag && !this.func_72916_c(i, j))
        {
            entity.field_70128_L = true; // CraftBukkit
            return false;
        }
        else
        {
            if (entity instanceof EntityPlayer)
            {
                EntityPlayer entityplayer = (EntityPlayer) entity;
                this.field_73010_i.add(entityplayer);
                this.func_72854_c();
            }

            this.func_72964_e(i, j).func_76612_a(entity);
            this.field_72996_f.add(entity);
            this.func_72923_a(entity);
            return true;
        }
    }

    protected void func_72923_a(Entity p_72923_1_)
    {
        for (int i = 0; i < this.field_73021_x.size(); ++i)
        {
            ((IWorldAccess)this.field_73021_x.get(i)).func_72703_a(p_72923_1_);
        }

        p_72923_1_.valid = true; // CraftBukkit
    }

    protected void func_72847_b(Entity p_72847_1_)
    {
        for (int i = 0; i < this.field_73021_x.size(); ++i)
        {
            ((IWorldAccess)this.field_73021_x.get(i)).func_72709_b(p_72847_1_);
        }

        p_72847_1_.valid = false; // CraftBukkit
    }

    public void func_72900_e(Entity p_72900_1_)
    {
        if (p_72900_1_.field_70153_n != null)
        {
            p_72900_1_.field_70153_n.func_70078_a((Entity)null);
        }

        if (p_72900_1_.field_70154_o != null)
        {
            p_72900_1_.func_70078_a((Entity)null);
        }

        p_72900_1_.func_70106_y();

        if (p_72900_1_ instanceof EntityPlayer)
        {
            this.field_73010_i.remove(p_72900_1_);
            this.func_72854_c();
        }
    }

    public void func_72973_f(Entity p_72973_1_)
    {
        p_72973_1_.func_70106_y();

        if (p_72973_1_ instanceof EntityPlayer)
        {
            this.field_73010_i.remove(p_72973_1_);
            this.func_72854_c();
        }

        int i = p_72973_1_.field_70176_ah;
        int j = p_72973_1_.field_70164_aj;

        if (p_72973_1_.field_70175_ag && this.func_72916_c(i, j))
        {
            this.func_72964_e(i, j).func_76622_b(p_72973_1_);
        }

        this.field_72996_f.remove(p_72973_1_);
        this.func_72847_b(p_72973_1_);
    }

    public void func_72954_a(IWorldAccess p_72954_1_)
    {
        this.field_73021_x.add(p_72954_1_);
    }

    public List func_72945_a(Entity p_72945_1_, AxisAlignedBB p_72945_2_)
    {
        this.field_72998_d.clear();
        int i = MathHelper.func_76128_c(p_72945_2_.field_72340_a);
        int j = MathHelper.func_76128_c(p_72945_2_.field_72336_d + 1.0D);
        int k = MathHelper.func_76128_c(p_72945_2_.field_72338_b);
        int l = MathHelper.func_76128_c(p_72945_2_.field_72337_e + 1.0D);
        int i1 = MathHelper.func_76128_c(p_72945_2_.field_72339_c);
        int j1 = MathHelper.func_76128_c(p_72945_2_.field_72334_f + 1.0D);

        for (int k1 = i; k1 < j; ++k1)
        {
            for (int l1 = i1; l1 < j1; ++l1)
            {
                if (this.func_72899_e(k1, 64, l1))
                {
                    for (int i2 = k - 1; i2 < l; ++i2)
                    {
                        Block block = Block.field_71973_m[this.func_72798_a(k1, i2, l1)];

                        if (block != null)
                        {
                            block.func_71871_a(this, k1, i2, l1, p_72945_2_, this.field_72998_d, p_72945_1_);
                        }
                    }
                }
            }
        }

        double d0 = 0.25D;
        List list = this.func_72839_b(p_72945_1_, p_72945_2_.func_72314_b(d0, d0, d0));

        for (int j2 = 0; j2 < list.size(); ++j2)
        {
            AxisAlignedBB axisalignedbb1 = ((Entity)list.get(j2)).func_70046_E();

            if (axisalignedbb1 != null && axisalignedbb1.func_72326_a(p_72945_2_))
            {
                this.field_72998_d.add(axisalignedbb1);
            }

            axisalignedbb1 = p_72945_1_.func_70114_g((Entity)list.get(j2));

            if (axisalignedbb1 != null && axisalignedbb1.func_72326_a(p_72945_2_))
            {
                this.field_72998_d.add(axisalignedbb1);
            }
        }

        return this.field_72998_d;
    }

    public List func_72840_a(AxisAlignedBB p_72840_1_)
    {
        this.field_72998_d.clear();
        int i = MathHelper.func_76128_c(p_72840_1_.field_72340_a);
        int j = MathHelper.func_76128_c(p_72840_1_.field_72336_d + 1.0D);
        int k = MathHelper.func_76128_c(p_72840_1_.field_72338_b);
        int l = MathHelper.func_76128_c(p_72840_1_.field_72337_e + 1.0D);
        int i1 = MathHelper.func_76128_c(p_72840_1_.field_72339_c);
        int j1 = MathHelper.func_76128_c(p_72840_1_.field_72334_f + 1.0D);

        for (int k1 = i; k1 < j; ++k1)
        {
            for (int l1 = i1; l1 < j1; ++l1)
            {
                if (this.func_72899_e(k1, 64, l1))
                {
                    for (int i2 = k - 1; i2 < l; ++i2)
                    {
                        Block block = Block.field_71973_m[this.func_72798_a(k1, i2, l1)];

                        if (block != null)
                        {
                            block.func_71871_a(this, k1, i2, l1, p_72840_1_, this.field_72998_d, (Entity)null);
                        }
                    }
                }
            }
        }

        return this.field_72998_d;
    }

    public int func_72967_a(float p_72967_1_)
    {
        float f1 = this.func_72826_c(p_72967_1_);
        float f2 = 1.0F - (MathHelper.func_76134_b(f1 * (float)Math.PI * 2.0F) * 2.0F + 0.5F);

        if (f2 < 0.0F)
        {
            f2 = 0.0F;
        }

        if (f2 > 1.0F)
        {
            f2 = 1.0F;
        }

        f2 = 1.0F - f2;
        f2 = (float)((double)f2 * (1.0D - (double)(this.func_72867_j(p_72967_1_) * 5.0F) / 16.0D));
        f2 = (float)((double)f2 * (1.0D - (double)(this.func_72819_i(p_72967_1_) * 5.0F) / 16.0D));
        f2 = 1.0F - f2;
        return (int)(f2 * 11.0F);
    }

    public float func_72826_c(float p_72826_1_)
    {
        return this.field_73011_w.func_76563_a(this.field_72986_A.func_76073_f(), p_72826_1_);
    }

    public int func_72874_g(int p_72874_1_, int p_72874_2_)
    {
        return this.func_72938_d(p_72874_1_, p_72874_2_).func_76626_d(p_72874_1_ & 15, p_72874_2_ & 15);
    }

    public int func_72825_h(int p_72825_1_, int p_72825_2_)
    {
        Chunk chunk = this.func_72938_d(p_72825_1_, p_72825_2_);
        int k = chunk.func_76625_h() + 15;
        p_72825_1_ &= 15;

        for (p_72825_2_ &= 15; k > 0; --k)
        {
            int l = chunk.func_76610_a(p_72825_1_, k, p_72825_2_);

            if (l != 0 && Block.field_71973_m[l].field_72018_cp.func_76230_c() && Block.field_71973_m[l].field_72018_cp != Material.field_76257_i)
            {
                return k + 1;
            }
        }

        return -1;
    }

    public void func_72836_a(int p_72836_1_, int p_72836_2_, int p_72836_3_, int p_72836_4_, int p_72836_5_) {}

    public void func_82740_a(int p_82740_1_, int p_82740_2_, int p_82740_3_, int p_82740_4_, int p_82740_5_, int p_82740_6_) {}

    public void func_72892_b(int p_72892_1_, int p_72892_2_, int p_72892_3_, int p_72892_4_, int p_72892_5_) {}

    public void func_72939_s()
    {
        this.field_72984_F.func_76320_a("entities");
        this.field_72984_F.func_76320_a("global");
        int i;
        Entity entity;
        CrashReport crashreport;
        CrashReportCategory crashreportcategory;

        for (i = 0; i < this.field_73007_j.size(); ++i)
        {
            entity = (Entity)this.field_73007_j.get(i);

            // CraftBukkit start - fixed an NPE, don't process entities in chunks queued for unload
            if (entity == null)
            {
                continue;
            }

            ChunkProviderServer chunkProviderServer = ((WorldServer) this).field_73059_b;

            if (chunkProviderServer.field_73248_b.contains(MathHelper.func_76128_c(entity.field_70165_t) >> 4, MathHelper.func_76128_c(entity.field_70161_v) >> 4))
            {
                continue;
            }

            // CraftBukkit end

            try
            {
                ++entity.field_70173_aa;
                entity.func_70071_h_();
            }
            catch (Throwable throwable)
            {
                crashreport = CrashReport.func_85055_a(throwable, "Ticking entity");
                crashreportcategory = crashreport.func_85058_a("Entity being ticked");

                if (entity == null)
                {
                    crashreportcategory.func_71507_a("Entity", "~~NULL~~");
                }
                else
                {
                    entity.func_85029_a(crashreportcategory);
                }

                throw new ReportedException(crashreport);
            }

            if (entity.field_70128_L)
            {
                this.field_73007_j.remove(i--);
            }
        }

        this.field_72984_F.func_76318_c("remove");
        this.field_72996_f.removeAll(this.field_72997_g);
        int j;
        int k;

        for (i = 0; i < this.field_72997_g.size(); ++i)
        {
            entity = (Entity)this.field_72997_g.get(i);
            j = entity.field_70176_ah;
            k = entity.field_70164_aj;

            if (entity.field_70175_ag && this.func_72916_c(j, k))
            {
                this.func_72964_e(j, k).func_76622_b(entity);
            }
        }

        for (i = 0; i < this.field_72997_g.size(); ++i)
        {
            this.func_72847_b((Entity)this.field_72997_g.get(i));
        }

        this.field_72997_g.clear();
        this.field_72984_F.func_76318_c("regular");

        for (i = 0; i < this.field_72996_f.size(); ++i)
        {
            entity = (Entity)this.field_72996_f.get(i);
            // CraftBukkit start - don't tick entities in chunks queued for unload
            ChunkProviderServer chunkProviderServer = ((WorldServer) this).field_73059_b;

            if (chunkProviderServer.field_73248_b.contains(MathHelper.func_76128_c(entity.field_70165_t) >> 4, MathHelper.func_76128_c(entity.field_70161_v) >> 4))
            {
                continue;
            }

            // CraftBukkit end

            if (entity.field_70154_o != null)
            {
                if (!entity.field_70154_o.field_70128_L && entity.field_70154_o.field_70153_n == entity)
                {
                    continue;
                }

                entity.field_70154_o.field_70153_n = null;
                entity.field_70154_o = null;
            }

            this.field_72984_F.func_76320_a("tick");

            if (!entity.field_70128_L)
            {
                try
                {
                    this.func_72870_g(entity);
                }
                catch (Throwable throwable1)
                {
                    crashreport = CrashReport.func_85055_a(throwable1, "Ticking entity");
                    crashreportcategory = crashreport.func_85058_a("Entity being ticked");

                    if (entity == null)
                    {
                        crashreportcategory.func_71507_a("Entity", "~~NULL~~");
                    }
                    else
                    {
                        entity.func_85029_a(crashreportcategory);
                    }

                    throw new ReportedException(crashreport);
                }
            }

            this.field_72984_F.func_76319_b();
            this.field_72984_F.func_76320_a("remove");

            if (entity.field_70128_L)
            {
                j = entity.field_70176_ah;
                k = entity.field_70164_aj;

                if (entity.field_70175_ag && this.func_72916_c(j, k))
                {
                    this.func_72964_e(j, k).func_76622_b(entity);
                }

                this.field_72996_f.remove(i--);
                this.func_72847_b(entity);
            }

            this.field_72984_F.func_76319_b();
        }

        this.field_72984_F.func_76318_c("tileEntities");
        this.field_72989_L = true;
        Iterator iterator = this.field_73009_h.iterator();

        while (iterator.hasNext())
        {
            TileEntity tileentity = (TileEntity)iterator.next();
            // CraftBukkit start - don't tick entities in chunks queued for unload
            ChunkProviderServer chunkProviderServer = ((WorldServer) this).field_73059_b;

            if (chunkProviderServer.field_73248_b.contains(tileentity.field_70329_l >> 4, tileentity.field_70327_n >> 4))
            {
                continue;
            }

            // CraftBukkit end

            if (!tileentity.func_70320_p() && tileentity.func_70309_m() && this.func_72899_e(tileentity.field_70329_l, tileentity.field_70330_m, tileentity.field_70327_n))
            {
                try
                {
                    tileentity.func_70316_g();
                }
                catch (Throwable throwable2)
                {
                    crashreport = CrashReport.func_85055_a(throwable2, "Ticking tile entity");
                    crashreportcategory = crashreport.func_85058_a("Tile entity being ticked");

                    if (tileentity == null)
                    {
                        crashreportcategory.func_71507_a("Tile entity", "~~NULL~~");
                    }
                    else
                    {
                        tileentity.func_85027_a(crashreportcategory);
                    }

                    throw new ReportedException(crashreport);
                }
            }

            if (tileentity.func_70320_p())
            {
                iterator.remove();

                if (this.func_72916_c(tileentity.field_70329_l >> 4, tileentity.field_70327_n >> 4))
                {
                    Chunk chunk = this.func_72964_e(tileentity.field_70329_l >> 4, tileentity.field_70327_n >> 4);

                    if (chunk != null)
                    {
                        chunk.func_76627_f(tileentity.field_70329_l & 15, tileentity.field_70330_m, tileentity.field_70327_n & 15);
                    }
                }
            }
        }

        this.field_72989_L = false;

        if (!this.field_73000_b.isEmpty())
        {
            this.field_73009_h.removeAll(this.field_73000_b);
            this.field_73000_b.clear();
        }

        this.field_72984_F.func_76318_c("pendingTileEntities");

        if (!this.field_73002_a.isEmpty())
        {
            for (int l = 0; l < this.field_73002_a.size(); ++l)
            {
                TileEntity tileentity1 = (TileEntity)this.field_73002_a.get(l);

                if (!tileentity1.func_70320_p())
                {
                    /* CraftBukkit start - order matters, moved down
                    if (!this.tileEntityList.contains(tileentity1)) {
                        this.tileEntityList.add(tileentity1);
                    }
                    // CraftBukkit end */
                    if (this.func_72916_c(tileentity1.field_70329_l >> 4, tileentity1.field_70327_n >> 4))
                    {
                        Chunk chunk1 = this.func_72964_e(tileentity1.field_70329_l >> 4, tileentity1.field_70327_n >> 4);

                        if (chunk1 != null)
                        {
                            chunk1.func_76604_a(tileentity1.field_70329_l & 15, tileentity1.field_70330_m, tileentity1.field_70327_n & 15, tileentity1);

                            // CraftBukkit start - moved down from above
                            if (!this.field_73009_h.contains(tileentity1))
                            {
                                this.field_73009_h.add(tileentity1);
                            }

                            // CraftBukkit end
                        }
                    }

                    this.func_72845_h(tileentity1.field_70329_l, tileentity1.field_70330_m, tileentity1.field_70327_n);
                }
            }

            this.field_73002_a.clear();
        }

        this.field_72984_F.func_76319_b();
        this.field_72984_F.func_76319_b();
    }

    public void func_72852_a(Collection p_72852_1_)
    {
        if (this.field_72989_L)
        {
            this.field_73002_a.addAll(p_72852_1_);
        }
        else
        {
            this.field_73009_h.addAll(p_72852_1_);
        }
    }

    public void func_72870_g(Entity p_72870_1_)
    {
        this.func_72866_a(p_72870_1_, true);
    }

    public void func_72866_a(Entity p_72866_1_, boolean p_72866_2_)
    {
        int i = MathHelper.func_76128_c(p_72866_1_.field_70165_t);
        int j = MathHelper.func_76128_c(p_72866_1_.field_70161_v);
        byte b0 = 32;

        if (!p_72866_2_ || this.func_72904_c(i - b0, 0, j - b0, i + b0, 0, j + b0))
        {
            p_72866_1_.field_70142_S = p_72866_1_.field_70165_t;
            p_72866_1_.field_70137_T = p_72866_1_.field_70163_u;
            p_72866_1_.field_70136_U = p_72866_1_.field_70161_v;
            p_72866_1_.field_70126_B = p_72866_1_.field_70177_z;
            p_72866_1_.field_70127_C = p_72866_1_.field_70125_A;

            if (p_72866_2_ && p_72866_1_.field_70175_ag)
            {
                if (p_72866_1_.field_70154_o != null)
                {
                    p_72866_1_.func_70098_U();
                }
                else
                {
                    ++p_72866_1_.field_70173_aa;
                    p_72866_1_.func_70071_h_();
                }
            }

            this.field_72984_F.func_76320_a("chunkCheck");

            if (Double.isNaN(p_72866_1_.field_70165_t) || Double.isInfinite(p_72866_1_.field_70165_t))
            {
                p_72866_1_.field_70165_t = p_72866_1_.field_70142_S;
            }

            if (Double.isNaN(p_72866_1_.field_70163_u) || Double.isInfinite(p_72866_1_.field_70163_u))
            {
                p_72866_1_.field_70163_u = p_72866_1_.field_70137_T;
            }

            if (Double.isNaN(p_72866_1_.field_70161_v) || Double.isInfinite(p_72866_1_.field_70161_v))
            {
                p_72866_1_.field_70161_v = p_72866_1_.field_70136_U;
            }

            if (Double.isNaN((double)p_72866_1_.field_70125_A) || Double.isInfinite((double)p_72866_1_.field_70125_A))
            {
                p_72866_1_.field_70125_A = p_72866_1_.field_70127_C;
            }

            if (Double.isNaN((double)p_72866_1_.field_70177_z) || Double.isInfinite((double)p_72866_1_.field_70177_z))
            {
                p_72866_1_.field_70177_z = p_72866_1_.field_70126_B;
            }

            int k = MathHelper.func_76128_c(p_72866_1_.field_70165_t / 16.0D);
            int l = MathHelper.func_76128_c(p_72866_1_.field_70163_u / 16.0D);
            int i1 = MathHelper.func_76128_c(p_72866_1_.field_70161_v / 16.0D);

            if (!p_72866_1_.field_70175_ag || p_72866_1_.field_70176_ah != k || p_72866_1_.field_70162_ai != l || p_72866_1_.field_70164_aj != i1)
            {
                if (p_72866_1_.field_70175_ag && this.func_72916_c(p_72866_1_.field_70176_ah, p_72866_1_.field_70164_aj))
                {
                    this.func_72964_e(p_72866_1_.field_70176_ah, p_72866_1_.field_70164_aj).func_76608_a(p_72866_1_, p_72866_1_.field_70162_ai);
                }

                if (this.func_72916_c(k, i1))
                {
                    p_72866_1_.field_70175_ag = true;
                    this.func_72964_e(k, i1).func_76612_a(p_72866_1_);
                }
                else
                {
                    p_72866_1_.field_70175_ag = false;
                }
            }

            this.field_72984_F.func_76319_b();

            if (p_72866_2_ && p_72866_1_.field_70175_ag && p_72866_1_.field_70153_n != null)
            {
                if (!p_72866_1_.field_70153_n.field_70128_L && p_72866_1_.field_70153_n.field_70154_o == p_72866_1_)
                {
                    this.func_72870_g(p_72866_1_.field_70153_n);
                }
                else
                {
                    p_72866_1_.field_70153_n.field_70154_o = null;
                    p_72866_1_.field_70153_n = null;
                }
            }
        }
    }

    public boolean func_72855_b(AxisAlignedBB p_72855_1_)
    {
        return this.func_72917_a(p_72855_1_, (Entity)null);
    }

    public boolean func_72917_a(AxisAlignedBB p_72917_1_, Entity p_72917_2_)
    {
        List list = this.func_72839_b((Entity)null, p_72917_1_);

        for (int i = 0; i < list.size(); ++i)
        {
            Entity entity1 = (Entity)list.get(i);

            if (!entity1.field_70128_L && entity1.field_70156_m && entity1 != p_72917_2_)
            {
                return false;
            }
        }

        return true;
    }

    public boolean func_72829_c(AxisAlignedBB p_72829_1_)
    {
        int i = MathHelper.func_76128_c(p_72829_1_.field_72340_a);
        int j = MathHelper.func_76128_c(p_72829_1_.field_72336_d + 1.0D);
        int k = MathHelper.func_76128_c(p_72829_1_.field_72338_b);
        int l = MathHelper.func_76128_c(p_72829_1_.field_72337_e + 1.0D);
        int i1 = MathHelper.func_76128_c(p_72829_1_.field_72339_c);
        int j1 = MathHelper.func_76128_c(p_72829_1_.field_72334_f + 1.0D);

        if (p_72829_1_.field_72340_a < 0.0D)
        {
            --i;
        }

        if (p_72829_1_.field_72338_b < 0.0D)
        {
            --k;
        }

        if (p_72829_1_.field_72339_c < 0.0D)
        {
            --i1;
        }

        for (int k1 = i; k1 < j; ++k1)
        {
            for (int l1 = k; l1 < l; ++l1)
            {
                for (int i2 = i1; i2 < j1; ++i2)
                {
                    Block block = Block.field_71973_m[this.func_72798_a(k1, l1, i2)];

                    if (block != null)
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean func_72953_d(AxisAlignedBB p_72953_1_)
    {
        int i = MathHelper.func_76128_c(p_72953_1_.field_72340_a);
        int j = MathHelper.func_76128_c(p_72953_1_.field_72336_d + 1.0D);
        int k = MathHelper.func_76128_c(p_72953_1_.field_72338_b);
        int l = MathHelper.func_76128_c(p_72953_1_.field_72337_e + 1.0D);
        int i1 = MathHelper.func_76128_c(p_72953_1_.field_72339_c);
        int j1 = MathHelper.func_76128_c(p_72953_1_.field_72334_f + 1.0D);

        if (p_72953_1_.field_72340_a < 0.0D)
        {
            --i;
        }

        if (p_72953_1_.field_72338_b < 0.0D)
        {
            --k;
        }

        if (p_72953_1_.field_72339_c < 0.0D)
        {
            --i1;
        }

        for (int k1 = i; k1 < j; ++k1)
        {
            for (int l1 = k; l1 < l; ++l1)
            {
                for (int i2 = i1; i2 < j1; ++i2)
                {
                    Block block = Block.field_71973_m[this.func_72798_a(k1, l1, i2)];

                    if (block != null && block.field_72018_cp.func_76224_d())
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean func_72978_e(AxisAlignedBB p_72978_1_)
    {
        int i = MathHelper.func_76128_c(p_72978_1_.field_72340_a);
        int j = MathHelper.func_76128_c(p_72978_1_.field_72336_d + 1.0D);
        int k = MathHelper.func_76128_c(p_72978_1_.field_72338_b);
        int l = MathHelper.func_76128_c(p_72978_1_.field_72337_e + 1.0D);
        int i1 = MathHelper.func_76128_c(p_72978_1_.field_72339_c);
        int j1 = MathHelper.func_76128_c(p_72978_1_.field_72334_f + 1.0D);

        if (this.func_72904_c(i, k, i1, j, l, j1))
        {
            for (int k1 = i; k1 < j; ++k1)
            {
                for (int l1 = k; l1 < l; ++l1)
                {
                    for (int i2 = i1; i2 < j1; ++i2)
                    {
                        int j2 = this.func_72798_a(k1, l1, i2);

                        if (j2 == Block.field_72067_ar.field_71990_ca || j2 == Block.field_71944_C.field_71990_ca || j2 == Block.field_71938_D.field_71990_ca)
                        {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public boolean func_72918_a(AxisAlignedBB p_72918_1_, Material p_72918_2_, Entity p_72918_3_)
    {
        int i = MathHelper.func_76128_c(p_72918_1_.field_72340_a);
        int j = MathHelper.func_76128_c(p_72918_1_.field_72336_d + 1.0D);
        int k = MathHelper.func_76128_c(p_72918_1_.field_72338_b);
        int l = MathHelper.func_76128_c(p_72918_1_.field_72337_e + 1.0D);
        int i1 = MathHelper.func_76128_c(p_72918_1_.field_72339_c);
        int j1 = MathHelper.func_76128_c(p_72918_1_.field_72334_f + 1.0D);

        if (!this.func_72904_c(i, k, i1, j, l, j1))
        {
            return false;
        }
        else
        {
            boolean flag = false;
            Vec3 vec3 = this.func_82732_R().func_72345_a(0.0D, 0.0D, 0.0D);

            for (int k1 = i; k1 < j; ++k1)
            {
                for (int l1 = k; l1 < l; ++l1)
                {
                    for (int i2 = i1; i2 < j1; ++i2)
                    {
                        Block block = Block.field_71973_m[this.func_72798_a(k1, l1, i2)];

                        if (block != null && block.field_72018_cp == p_72918_2_)
                        {
                            double d0 = (double)((float)(l1 + 1) - BlockFluid.func_72199_d(this.func_72805_g(k1, l1, i2)));

                            if ((double)l >= d0)
                            {
                                flag = true;
                                block.func_71901_a(this, k1, l1, i2, p_72918_3_, vec3);
                            }
                        }
                    }
                }
            }

            if (vec3.func_72433_c() > 0.0D)
            {
                vec3 = vec3.func_72432_b();
                double d1 = 0.014D;
                p_72918_3_.field_70159_w += vec3.field_72450_a * d1;
                p_72918_3_.field_70181_x += vec3.field_72448_b * d1;
                p_72918_3_.field_70179_y += vec3.field_72449_c * d1;
            }

            vec3.field_72447_d.release(vec3); // CraftBukkit - pop it - we're done
            return flag;
        }
    }

    public boolean func_72875_a(AxisAlignedBB p_72875_1_, Material p_72875_2_)
    {
        int i = MathHelper.func_76128_c(p_72875_1_.field_72340_a);
        int j = MathHelper.func_76128_c(p_72875_1_.field_72336_d + 1.0D);
        int k = MathHelper.func_76128_c(p_72875_1_.field_72338_b);
        int l = MathHelper.func_76128_c(p_72875_1_.field_72337_e + 1.0D);
        int i1 = MathHelper.func_76128_c(p_72875_1_.field_72339_c);
        int j1 = MathHelper.func_76128_c(p_72875_1_.field_72334_f + 1.0D);

        for (int k1 = i; k1 < j; ++k1)
        {
            for (int l1 = k; l1 < l; ++l1)
            {
                for (int i2 = i1; i2 < j1; ++i2)
                {
                    Block block = Block.field_71973_m[this.func_72798_a(k1, l1, i2)];

                    if (block != null && block.field_72018_cp == p_72875_2_)
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean func_72830_b(AxisAlignedBB p_72830_1_, Material p_72830_2_)
    {
        int i = MathHelper.func_76128_c(p_72830_1_.field_72340_a);
        int j = MathHelper.func_76128_c(p_72830_1_.field_72336_d + 1.0D);
        int k = MathHelper.func_76128_c(p_72830_1_.field_72338_b);
        int l = MathHelper.func_76128_c(p_72830_1_.field_72337_e + 1.0D);
        int i1 = MathHelper.func_76128_c(p_72830_1_.field_72339_c);
        int j1 = MathHelper.func_76128_c(p_72830_1_.field_72334_f + 1.0D);

        for (int k1 = i; k1 < j; ++k1)
        {
            for (int l1 = k; l1 < l; ++l1)
            {
                for (int i2 = i1; i2 < j1; ++i2)
                {
                    Block block = Block.field_71973_m[this.func_72798_a(k1, l1, i2)];

                    if (block != null && block.field_72018_cp == p_72830_2_)
                    {
                        int j2 = this.func_72805_g(k1, l1, i2);
                        double d0 = (double)(l1 + 1);

                        if (j2 < 8)
                        {
                            d0 = (double)(l1 + 1) - (double)j2 / 8.0D;
                        }

                        if (d0 >= p_72830_1_.field_72338_b)
                        {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public Explosion func_72876_a(Entity p_72876_1_, double p_72876_2_, double p_72876_4_, double p_72876_6_, float p_72876_8_, boolean p_72876_9_)
    {
        return this.func_72885_a(p_72876_1_, p_72876_2_, p_72876_4_, p_72876_6_, p_72876_8_, false, p_72876_9_);
    }

    public Explosion func_72885_a(Entity p_72885_1_, double p_72885_2_, double p_72885_4_, double p_72885_6_, float p_72885_8_, boolean p_72885_9_, boolean p_72885_10_)
    {
        Explosion explosion = new Explosion(this, p_72885_1_, p_72885_2_, p_72885_4_, p_72885_6_, p_72885_8_);
        explosion.field_77286_a = p_72885_9_;
        explosion.field_82755_b = p_72885_10_;
        explosion.func_77278_a();
        explosion.func_77279_a(true);
        return explosion;
    }

    public float func_72842_a(Vec3 p_72842_1_, AxisAlignedBB p_72842_2_)
    {
        double d0 = 1.0D / ((p_72842_2_.field_72336_d - p_72842_2_.field_72340_a) * 2.0D + 1.0D);
        double d1 = 1.0D / ((p_72842_2_.field_72337_e - p_72842_2_.field_72338_b) * 2.0D + 1.0D);
        double d2 = 1.0D / ((p_72842_2_.field_72334_f - p_72842_2_.field_72339_c) * 2.0D + 1.0D);
        int i = 0;
        int j = 0;
        Vec3 vec32 = p_72842_1_.field_72447_d.func_72345_a(0, 0, 0); // CraftBukkit

        for (float f = 0.0F; f <= 1.0F; f = (float)((double)f + d0))
        {
            for (float f1 = 0.0F; f1 <= 1.0F; f1 = (float)((double)f1 + d1))
            {
                for (float f2 = 0.0F; f2 <= 1.0F; f2 = (float)((double)f2 + d2))
                {
                    double d3 = p_72842_2_.field_72340_a + (p_72842_2_.field_72336_d - p_72842_2_.field_72340_a) * (double)f;
                    double d4 = p_72842_2_.field_72338_b + (p_72842_2_.field_72337_e - p_72842_2_.field_72338_b) * (double)f1;
                    double d5 = p_72842_2_.field_72339_c + (p_72842_2_.field_72334_f - p_72842_2_.field_72339_c) * (double)f2;

                    if (this.func_72933_a(vec32.func_72439_b_CodeFix_Public(d3, d4, d5), p_72842_1_) == null)   // CraftBukkit
                    {
                        ++i;
                    }

                    ++j;
                }
            }
        }

        vec32.field_72447_d.release(vec32); // CraftBukkit
        return (float)i / (float)j;
    }

    public boolean func_72886_a(EntityPlayer p_72886_1_, int p_72886_2_, int p_72886_3_, int p_72886_4_, int p_72886_5_)
    {
        if (p_72886_5_ == 0)
        {
            --p_72886_3_;
        }

        if (p_72886_5_ == 1)
        {
            ++p_72886_3_;
        }

        if (p_72886_5_ == 2)
        {
            --p_72886_4_;
        }

        if (p_72886_5_ == 3)
        {
            ++p_72886_4_;
        }

        if (p_72886_5_ == 4)
        {
            --p_72886_2_;
        }

        if (p_72886_5_ == 5)
        {
            ++p_72886_2_;
        }

        if (this.func_72798_a(p_72886_2_, p_72886_3_, p_72886_4_) == Block.field_72067_ar.field_71990_ca)
        {
            this.func_72889_a(p_72886_1_, 1004, p_72886_2_, p_72886_3_, p_72886_4_, 0);
            this.func_72859_e(p_72886_2_, p_72886_3_, p_72886_4_, 0);
            return true;
        }
        else
        {
            return false;
        }
    }

    public TileEntity func_72796_p(int p_72796_1_, int p_72796_2_, int p_72796_3_)
    {
        if (p_72796_2_ >= 256)
        {
            return null;
        }
        else
        {
            Chunk chunk = this.func_72964_e(p_72796_1_ >> 4, p_72796_3_ >> 4);

            if (chunk == null)
            {
                return null;
            }
            else
            {
                TileEntity tileentity = chunk.func_76597_e(p_72796_1_ & 15, p_72796_2_, p_72796_3_ & 15);

                if (tileentity == null)
                {
                    for (int l = 0; l < this.field_73002_a.size(); ++l)
                    {
                        TileEntity tileentity1 = (TileEntity)this.field_73002_a.get(l);

                        if (!tileentity1.func_70320_p() && tileentity1.field_70329_l == p_72796_1_ && tileentity1.field_70330_m == p_72796_2_ && tileentity1.field_70327_n == p_72796_3_)
                        {
                            tileentity = tileentity1;
                            break;
                        }
                    }
                }

                return tileentity;
            }
        }
    }

    public void func_72837_a(int p_72837_1_, int p_72837_2_, int p_72837_3_, TileEntity p_72837_4_)
    {
        if (p_72837_4_ != null && !p_72837_4_.func_70320_p())
        {
            if (this.field_72989_L)
            {
                p_72837_4_.field_70329_l = p_72837_1_;
                p_72837_4_.field_70330_m = p_72837_2_;
                p_72837_4_.field_70327_n = p_72837_3_;
                this.field_73002_a.add(p_72837_4_);
            }
            else
            {
                this.field_73009_h.add(p_72837_4_);
                Chunk chunk = this.func_72964_e(p_72837_1_ >> 4, p_72837_3_ >> 4);

                if (chunk != null)
                {
                    chunk.func_76604_a(p_72837_1_ & 15, p_72837_2_, p_72837_3_ & 15, p_72837_4_);
                }
            }
        }
    }

    public void func_72932_q(int p_72932_1_, int p_72932_2_, int p_72932_3_)
    {
        TileEntity tileentity = this.func_72796_p(p_72932_1_, p_72932_2_, p_72932_3_);

        if (tileentity != null && this.field_72989_L)
        {
            tileentity.func_70313_j();
            this.field_73002_a.remove(tileentity);
        }
        else
        {
            if (tileentity != null)
            {
                this.field_73002_a.remove(tileentity);
                this.field_73009_h.remove(tileentity);
            }

            Chunk chunk = this.func_72964_e(p_72932_1_ >> 4, p_72932_3_ >> 4);

            if (chunk != null)
            {
                chunk.func_76627_f(p_72932_1_ & 15, p_72932_2_, p_72932_3_ & 15);
            }
        }
    }

    public void func_72928_a(TileEntity p_72928_1_)
    {
        this.field_73000_b.add(p_72928_1_);
    }

    public boolean func_72804_r(int p_72804_1_, int p_72804_2_, int p_72804_3_)
    {
        Block block = Block.field_71973_m[this.func_72798_a(p_72804_1_, p_72804_2_, p_72804_3_)];
        return block == null ? false : block.func_71926_d();
    }

    public boolean func_72809_s(int p_72809_1_, int p_72809_2_, int p_72809_3_)
    {
        return Block.func_71932_i(this.func_72798_a(p_72809_1_, p_72809_2_, p_72809_3_));
    }

    public boolean func_85174_u(int p_85174_1_, int p_85174_2_, int p_85174_3_)
    {
        int l = this.func_72798_a(p_85174_1_, p_85174_2_, p_85174_3_);

        if (l != 0 && Block.field_71973_m[l] != null)
        {
            AxisAlignedBB axisalignedbb = Block.field_71973_m[l].func_71872_e(this, p_85174_1_, p_85174_2_, p_85174_3_);
            return axisalignedbb != null && axisalignedbb.func_72320_b() >= 1.0D;
        }
        else
        {
            return false;
        }
    }

    public boolean func_72797_t(int p_72797_1_, int p_72797_2_, int p_72797_3_)
    {
        Block block = Block.field_71973_m[this.func_72798_a(p_72797_1_, p_72797_2_, p_72797_3_)];
        return block == null ? false : (block.field_72018_cp.func_76218_k() && block.func_71886_c() ? true : (block instanceof BlockStairs ? (this.func_72805_g(p_72797_1_, p_72797_2_, p_72797_3_) & 4) == 4 : (block instanceof BlockHalfSlab ? (this.func_72805_g(p_72797_1_, p_72797_2_, p_72797_3_) & 8) == 8 : false)));
    }

    public boolean func_72887_b(int p_72887_1_, int p_72887_2_, int p_72887_3_, boolean p_72887_4_)
    {
        if (p_72887_1_ >= -30000000 && p_72887_3_ >= -30000000 && p_72887_1_ < 30000000 && p_72887_3_ < 30000000)
        {
            Chunk chunk = this.field_73020_y.func_73154_d(p_72887_1_ >> 4, p_72887_3_ >> 4);

            if (chunk != null && !chunk.func_76621_g())
            {
                Block block = Block.field_71973_m[this.func_72798_a(p_72887_1_, p_72887_2_, p_72887_3_)];
                return block == null ? false : block.field_72018_cp.func_76218_k() && block.func_71886_c();
            }
            else
            {
                return p_72887_4_;
            }
        }
        else
        {
            return p_72887_4_;
        }
    }

    public void func_72966_v()
    {
        int i = this.func_72967_a(1.0F);

        if (i != this.field_73008_k)
        {
            this.field_73008_k = i;
        }
    }

    public void func_72891_a(boolean p_72891_1_, boolean p_72891_2_)
    {
        this.field_72985_G = p_72891_1_;
        this.field_72992_H = p_72891_2_;
    }

    public void func_72835_b()
    {
        this.func_72979_l();
    }

    private void func_72947_a()
    {
        if (this.field_72986_A.func_76059_o())
        {
            this.field_73004_o = 1.0F;

            if (this.field_72986_A.func_76061_m())
            {
                this.field_73017_q = 1.0F;
            }
        }
    }

    protected void func_72979_l()
    {
        if (!this.field_73011_w.field_76576_e)
        {
            int i = this.field_72986_A.func_76071_n();

            if (i <= 0)
            {
                if (this.field_72986_A.func_76061_m())
                {
                    this.field_72986_A.func_76090_f(this.field_73012_v.nextInt(12000) + 3600);
                }
                else
                {
                    this.field_72986_A.func_76090_f(this.field_73012_v.nextInt(168000) + 12000);
                }
            }
            else
            {
                --i;
                this.field_72986_A.func_76090_f(i);

                if (i <= 0)
                {
                    // CraftBukkit start
                    ThunderChangeEvent thunder = new ThunderChangeEvent(this.getWorld(), !this.field_72986_A.func_76061_m());
                    this.getServer().getPluginManager().callEvent(thunder);

                    if (!thunder.isCancelled())
                    {
                        this.field_72986_A.func_76069_a(!this.field_72986_A.func_76061_m());
                    }

                    // CraftBukkit end
                }
            }

            int j = this.field_72986_A.func_76083_p();

            if (j <= 0)
            {
                if (this.field_72986_A.func_76059_o())
                {
                    this.field_72986_A.func_76080_g(this.field_73012_v.nextInt(12000) + 12000);
                }
                else
                {
                    this.field_72986_A.func_76080_g(this.field_73012_v.nextInt(168000) + 12000);
                }
            }
            else
            {
                --j;
                this.field_72986_A.func_76080_g(j);

                if (j <= 0)
                {
                    // CraftBukkit start
                    WeatherChangeEvent weather = new WeatherChangeEvent(this.getWorld(), !this.field_72986_A.func_76059_o());
                    this.getServer().getPluginManager().callEvent(weather);

                    if (!weather.isCancelled())
                    {
                        this.field_72986_A.func_76084_b(!this.field_72986_A.func_76059_o());
                    }

                    // CraftBukkit end
                }
            }

            this.field_73003_n = this.field_73004_o;

            if (this.field_72986_A.func_76059_o())
            {
                this.field_73004_o = (float)((double)this.field_73004_o + 0.01D);
            }
            else
            {
                this.field_73004_o = (float)((double)this.field_73004_o - 0.01D);
            }

            if (this.field_73004_o < 0.0F)
            {
                this.field_73004_o = 0.0F;
            }

            if (this.field_73004_o > 1.0F)
            {
                this.field_73004_o = 1.0F;
            }

            this.field_73018_p = this.field_73017_q;

            if (this.field_72986_A.func_76061_m())
            {
                this.field_73017_q = (float)((double)this.field_73017_q + 0.01D);
            }
            else
            {
                this.field_73017_q = (float)((double)this.field_73017_q - 0.01D);
            }

            if (this.field_73017_q < 0.0F)
            {
                this.field_73017_q = 0.0F;
            }

            if (this.field_73017_q > 1.0F)
            {
                this.field_73017_q = 1.0F;
            }
        }
    }

    public void func_72913_w()
    {
        this.field_72986_A.func_76080_g(1);
    }

    protected void func_72903_x()
    {
        // this.chunkTickList.clear(); // CraftBukkit - removed
        this.field_72984_F.func_76320_a("buildList");
        int i;
        EntityPlayer entityplayer;
        int j;
        int k;

        for (i = 0; i < this.field_73010_i.size(); ++i)
        {
            entityplayer = (EntityPlayer)this.field_73010_i.get(i);
            j = MathHelper.func_76128_c(entityplayer.field_70165_t / 16.0D);
            k = MathHelper.func_76128_c(entityplayer.field_70161_v / 16.0D);
            byte b0 = 7;

            for (int l = -b0; l <= b0; ++l)
            {
                for (int i1 = -b0; i1 <= b0; ++i1)
                {
                    // CraftBukkit start - don't tick chunks queued for unload
                    ChunkProviderServer chunkProviderServer = ((WorldServer) entityplayer.field_70170_p).field_73059_b;

                    if (chunkProviderServer.field_73248_b.contains(l + j, i1 + k))
                    {
                        continue;
                    }

                    // CraftBukkit end
                    this.field_72993_I.add(org.bukkit.craftbukkit.util.LongHash.toLong(l + j, i1 + k)); // CraftBukkit
                }
            }
        }

        this.field_72984_F.func_76319_b();

        if (this.field_72990_M > 0)
        {
            --this.field_72990_M;
        }

        this.field_72984_F.func_76320_a("playerCheckLight");

        if (!this.field_73010_i.isEmpty())
        {
            i = this.field_73012_v.nextInt(this.field_73010_i.size());
            entityplayer = (EntityPlayer)this.field_73010_i.get(i);
            j = MathHelper.func_76128_c(entityplayer.field_70165_t) + this.field_73012_v.nextInt(11) - 5;
            k = MathHelper.func_76128_c(entityplayer.field_70163_u) + this.field_73012_v.nextInt(11) - 5;
            int j1 = MathHelper.func_76128_c(entityplayer.field_70161_v) + this.field_73012_v.nextInt(11) - 5;
            this.func_72969_x(j, k, j1);
        }

        this.field_72984_F.func_76319_b();
    }

    protected void func_72941_a(int p_72941_1_, int p_72941_2_, Chunk p_72941_3_)
    {
        this.field_72984_F.func_76318_c("moodSound");

        if (this.field_72990_M == 0 && !this.field_72995_K)
        {
            this.field_73005_l = this.field_73005_l * 3 + 1013904223;
            int k = this.field_73005_l >> 2;
            int l = k & 15;
            int i1 = k >> 8 & 15;
            int j1 = k >> 16 & 255; // CraftBukkit - 127 -> 255
            int k1 = p_72941_3_.func_76610_a(l, j1, i1);
            l += p_72941_1_;
            i1 += p_72941_2_;

            if (k1 == 0 && this.func_72883_k(l, j1, i1) <= this.field_73012_v.nextInt(8) && this.func_72972_b(EnumSkyBlock.Sky, l, j1, i1) <= 0)
            {
                EntityPlayer entityplayer = this.func_72977_a((double)l + 0.5D, (double)j1 + 0.5D, (double)i1 + 0.5D, 8.0D);

                if (entityplayer != null && entityplayer.func_70092_e((double)l + 0.5D, (double)j1 + 0.5D, (double)i1 + 0.5D) > 4.0D)
                {
                    this.func_72908_a((double)l + 0.5D, (double)j1 + 0.5D, (double)i1 + 0.5D, "ambient.cave.cave", 0.7F, 0.8F + this.field_73012_v.nextFloat() * 0.2F);
                    this.field_72990_M = this.field_73012_v.nextInt(12000) + 6000;
                }
            }
        }

        this.field_72984_F.func_76318_c("checkLight");
        p_72941_3_.func_76594_o();
    }

    protected void func_72893_g()
    {
        this.func_72903_x();
    }

    public boolean func_72884_u(int p_72884_1_, int p_72884_2_, int p_72884_3_)
    {
        return this.func_72834_c(p_72884_1_, p_72884_2_, p_72884_3_, false);
    }

    public boolean func_72850_v(int p_72850_1_, int p_72850_2_, int p_72850_3_)
    {
        return this.func_72834_c(p_72850_1_, p_72850_2_, p_72850_3_, true);
    }

    public boolean func_72834_c(int p_72834_1_, int p_72834_2_, int p_72834_3_, boolean p_72834_4_)
    {
        BiomeGenBase biomegenbase = this.func_72807_a(p_72834_1_, p_72834_3_);
        float f = biomegenbase.func_76743_j();

        if (f > 0.15F)
        {
            return false;
        }
        else
        {
            if (p_72834_2_ >= 0 && p_72834_2_ < 256 && this.func_72972_b(EnumSkyBlock.Block, p_72834_1_, p_72834_2_, p_72834_3_) < 10)
            {
                int l = this.func_72798_a(p_72834_1_, p_72834_2_, p_72834_3_);

                if ((l == Block.field_71943_B.field_71990_ca || l == Block.field_71942_A.field_71990_ca) && this.func_72805_g(p_72834_1_, p_72834_2_, p_72834_3_) == 0)
                {
                    if (!p_72834_4_)
                    {
                        return true;
                    }

                    boolean flag1 = true;

                    if (flag1 && this.func_72803_f(p_72834_1_ - 1, p_72834_2_, p_72834_3_) != Material.field_76244_g)
                    {
                        flag1 = false;
                    }

                    if (flag1 && this.func_72803_f(p_72834_1_ + 1, p_72834_2_, p_72834_3_) != Material.field_76244_g)
                    {
                        flag1 = false;
                    }

                    if (flag1 && this.func_72803_f(p_72834_1_, p_72834_2_, p_72834_3_ - 1) != Material.field_76244_g)
                    {
                        flag1 = false;
                    }

                    if (flag1 && this.func_72803_f(p_72834_1_, p_72834_2_, p_72834_3_ + 1) != Material.field_76244_g)
                    {
                        flag1 = false;
                    }

                    if (!flag1)
                    {
                        return true;
                    }
                }
            }

            return false;
        }
    }

    public boolean func_72858_w(int p_72858_1_, int p_72858_2_, int p_72858_3_)
    {
        BiomeGenBase biomegenbase = this.func_72807_a(p_72858_1_, p_72858_3_);
        float f = biomegenbase.func_76743_j();

        if (f > 0.15F)
        {
            return false;
        }
        else
        {
            if (p_72858_2_ >= 0 && p_72858_2_ < 256 && this.func_72972_b(EnumSkyBlock.Block, p_72858_1_, p_72858_2_, p_72858_3_) < 10)
            {
                int l = this.func_72798_a(p_72858_1_, p_72858_2_ - 1, p_72858_3_);
                int i1 = this.func_72798_a(p_72858_1_, p_72858_2_, p_72858_3_);

                if (i1 == 0 && Block.field_72037_aS.func_71930_b(this, p_72858_1_, p_72858_2_, p_72858_3_) && l != 0 && l != Block.field_72036_aT.field_71990_ca && Block.field_71973_m[l].field_72018_cp.func_76230_c())
                {
                    return true;
                }
            }

            return false;
        }
    }

    public void func_72969_x(int p_72969_1_, int p_72969_2_, int p_72969_3_)
    {
        if (!this.field_73011_w.field_76576_e)
        {
            this.func_72936_c(EnumSkyBlock.Sky, p_72969_1_, p_72969_2_, p_72969_3_);
        }

        this.func_72936_c(EnumSkyBlock.Block, p_72969_1_, p_72969_2_, p_72969_3_);
    }

    private int func_72949_a(int p_72949_1_, int p_72949_2_, int p_72949_3_, int p_72949_4_, int p_72949_5_, int p_72949_6_)
    {
        int k1 = 0;

        if (this.func_72937_j(p_72949_2_, p_72949_3_, p_72949_4_))
        {
            k1 = 15;
        }
        else
        {
            if (p_72949_6_ == 0)
            {
                p_72949_6_ = 1;
            }

            int l1 = this.func_72972_b(EnumSkyBlock.Sky, p_72949_2_ - 1, p_72949_3_, p_72949_4_) - p_72949_6_;
            int i2 = this.func_72972_b(EnumSkyBlock.Sky, p_72949_2_ + 1, p_72949_3_, p_72949_4_) - p_72949_6_;
            int j2 = this.func_72972_b(EnumSkyBlock.Sky, p_72949_2_, p_72949_3_ - 1, p_72949_4_) - p_72949_6_;
            int k2 = this.func_72972_b(EnumSkyBlock.Sky, p_72949_2_, p_72949_3_ + 1, p_72949_4_) - p_72949_6_;
            int l2 = this.func_72972_b(EnumSkyBlock.Sky, p_72949_2_, p_72949_3_, p_72949_4_ - 1) - p_72949_6_;
            int i3 = this.func_72972_b(EnumSkyBlock.Sky, p_72949_2_, p_72949_3_, p_72949_4_ + 1) - p_72949_6_;

            if (l1 > k1)
            {
                k1 = l1;
            }

            if (i2 > k1)
            {
                k1 = i2;
            }

            if (j2 > k1)
            {
                k1 = j2;
            }

            if (k2 > k1)
            {
                k1 = k2;
            }

            if (l2 > k1)
            {
                k1 = l2;
            }

            if (i3 > k1)
            {
                k1 = i3;
            }
        }

        return k1;
    }

    private int func_72895_f(int p_72895_1_, int p_72895_2_, int p_72895_3_, int p_72895_4_, int p_72895_5_, int p_72895_6_)
    {
        int k1 = Block.field_71984_q[p_72895_5_];
        int l1 = this.func_72972_b(EnumSkyBlock.Block, p_72895_2_ - 1, p_72895_3_, p_72895_4_) - p_72895_6_;
        int i2 = this.func_72972_b(EnumSkyBlock.Block, p_72895_2_ + 1, p_72895_3_, p_72895_4_) - p_72895_6_;
        int j2 = this.func_72972_b(EnumSkyBlock.Block, p_72895_2_, p_72895_3_ - 1, p_72895_4_) - p_72895_6_;
        int k2 = this.func_72972_b(EnumSkyBlock.Block, p_72895_2_, p_72895_3_ + 1, p_72895_4_) - p_72895_6_;
        int l2 = this.func_72972_b(EnumSkyBlock.Block, p_72895_2_, p_72895_3_, p_72895_4_ - 1) - p_72895_6_;
        int i3 = this.func_72972_b(EnumSkyBlock.Block, p_72895_2_, p_72895_3_, p_72895_4_ + 1) - p_72895_6_;

        if (l1 > k1)
        {
            k1 = l1;
        }

        if (i2 > k1)
        {
            k1 = i2;
        }

        if (j2 > k1)
        {
            k1 = j2;
        }

        if (k2 > k1)
        {
            k1 = k2;
        }

        if (l2 > k1)
        {
            k1 = l2;
        }

        if (i3 > k1)
        {
            k1 = i3;
        }

        return k1;
    }

    public void func_72936_c(EnumSkyBlock p_72936_1_, int p_72936_2_, int p_72936_3_, int p_72936_4_)
    {
        if (this.func_72873_a(p_72936_2_, p_72936_3_, p_72936_4_, 17))
        {
            int l = 0;
            int i1 = 0;
            this.field_72984_F.func_76320_a("getBrightness");
            int j1 = this.func_72972_b(p_72936_1_, p_72936_2_, p_72936_3_, p_72936_4_);
            boolean flag = false;
            int k1 = this.func_72798_a(p_72936_2_, p_72936_3_, p_72936_4_);
            int l1 = this.func_72952_b(p_72936_2_, p_72936_3_, p_72936_4_);

            if (l1 == 0)
            {
                l1 = 1;
            }

            boolean flag1 = false;
            int i2;

            if (p_72936_1_ == EnumSkyBlock.Sky)
            {
                i2 = this.func_72949_a(j1, p_72936_2_, p_72936_3_, p_72936_4_, k1, l1);
            }
            else
            {
                i2 = this.func_72895_f(j1, p_72936_2_, p_72936_3_, p_72936_4_, k1, l1);
            }

            int j2;
            int k2;
            int l2;
            int i3;
            int j3;
            int k3;
            int l3;
            int i4;

            if (i2 > j1)
            {
                this.field_72994_J[i1++] = 133152;
            }
            else if (i2 < j1)
            {
                if (p_72936_1_ != EnumSkyBlock.Block)
                {
                    ;
                }

                this.field_72994_J[i1++] = 133152 + (j1 << 18);

                while (l < i1)
                {
                    k1 = this.field_72994_J[l++];
                    l1 = (k1 & 63) - 32 + p_72936_2_;
                    i2 = (k1 >> 6 & 63) - 32 + p_72936_3_;
                    j2 = (k1 >> 12 & 63) - 32 + p_72936_4_;
                    k2 = k1 >> 18 & 15;
                    l2 = this.func_72972_b(p_72936_1_, l1, i2, j2);

                    if (l2 == k2)
                    {
                        this.func_72915_b(p_72936_1_, l1, i2, j2, 0);

                        if (k2 > 0)
                        {
                            i3 = l1 - p_72936_2_;
                            k3 = i2 - p_72936_3_;
                            j3 = j2 - p_72936_4_;

                            if (i3 < 0)
                            {
                                i3 = -i3;
                            }

                            if (k3 < 0)
                            {
                                k3 = -k3;
                            }

                            if (j3 < 0)
                            {
                                j3 = -j3;
                            }

                            if (i3 + k3 + j3 < 17)
                            {
                                for (i4 = 0; i4 < 6; ++i4)
                                {
                                    l3 = i4 % 2 * 2 - 1;
                                    int j4 = l1 + i4 / 2 % 3 / 2 * l3;
                                    int k4 = i2 + (i4 / 2 + 1) % 3 / 2 * l3;
                                    int l4 = j2 + (i4 / 2 + 2) % 3 / 2 * l3;
                                    l2 = this.func_72972_b(p_72936_1_, j4, k4, l4);
                                    int i5 = Block.field_71971_o[this.func_72798_a(j4, k4, l4)];

                                    if (i5 == 0)
                                    {
                                        i5 = 1;
                                    }

                                    if (l2 == k2 - i5 && i1 < this.field_72994_J.length)
                                    {
                                        this.field_72994_J[i1++] = j4 - p_72936_2_ + 32 + (k4 - p_72936_3_ + 32 << 6) + (l4 - p_72936_4_ + 32 << 12) + (k2 - i5 << 18);
                                    }
                                }
                            }
                        }
                    }
                }

                l = 0;
            }

            this.field_72984_F.func_76319_b();
            this.field_72984_F.func_76320_a("checkedPosition < toCheckCount");

            while (l < i1)
            {
                k1 = this.field_72994_J[l++];
                l1 = (k1 & 63) - 32 + p_72936_2_;
                i2 = (k1 >> 6 & 63) - 32 + p_72936_3_;
                j2 = (k1 >> 12 & 63) - 32 + p_72936_4_;
                k2 = this.func_72972_b(p_72936_1_, l1, i2, j2);
                l2 = this.func_72798_a(l1, i2, j2);
                i3 = Block.field_71971_o[l2];

                if (i3 == 0)
                {
                    i3 = 1;
                }

                boolean flag2 = false;

                if (p_72936_1_ == EnumSkyBlock.Sky)
                {
                    k3 = this.func_72949_a(k2, l1, i2, j2, l2, i3);
                }
                else
                {
                    k3 = this.func_72895_f(k2, l1, i2, j2, l2, i3);
                }

                if (k3 != k2)
                {
                    this.func_72915_b(p_72936_1_, l1, i2, j2, k3);

                    if (k3 > k2)
                    {
                        j3 = l1 - p_72936_2_;
                        i4 = i2 - p_72936_3_;
                        l3 = j2 - p_72936_4_;

                        if (j3 < 0)
                        {
                            j3 = -j3;
                        }

                        if (i4 < 0)
                        {
                            i4 = -i4;
                        }

                        if (l3 < 0)
                        {
                            l3 = -l3;
                        }

                        if (j3 + i4 + l3 < 17 && i1 < this.field_72994_J.length - 6)
                        {
                            if (this.func_72972_b(p_72936_1_, l1 - 1, i2, j2) < k3)
                            {
                                this.field_72994_J[i1++] = l1 - 1 - p_72936_2_ + 32 + (i2 - p_72936_3_ + 32 << 6) + (j2 - p_72936_4_ + 32 << 12);
                            }

                            if (this.func_72972_b(p_72936_1_, l1 + 1, i2, j2) < k3)
                            {
                                this.field_72994_J[i1++] = l1 + 1 - p_72936_2_ + 32 + (i2 - p_72936_3_ + 32 << 6) + (j2 - p_72936_4_ + 32 << 12);
                            }

                            if (this.func_72972_b(p_72936_1_, l1, i2 - 1, j2) < k3)
                            {
                                this.field_72994_J[i1++] = l1 - p_72936_2_ + 32 + (i2 - 1 - p_72936_3_ + 32 << 6) + (j2 - p_72936_4_ + 32 << 12);
                            }

                            if (this.func_72972_b(p_72936_1_, l1, i2 + 1, j2) < k3)
                            {
                                this.field_72994_J[i1++] = l1 - p_72936_2_ + 32 + (i2 + 1 - p_72936_3_ + 32 << 6) + (j2 - p_72936_4_ + 32 << 12);
                            }

                            if (this.func_72972_b(p_72936_1_, l1, i2, j2 - 1) < k3)
                            {
                                this.field_72994_J[i1++] = l1 - p_72936_2_ + 32 + (i2 - p_72936_3_ + 32 << 6) + (j2 - 1 - p_72936_4_ + 32 << 12);
                            }

                            if (this.func_72972_b(p_72936_1_, l1, i2, j2 + 1) < k3)
                            {
                                this.field_72994_J[i1++] = l1 - p_72936_2_ + 32 + (i2 - p_72936_3_ + 32 << 6) + (j2 + 1 - p_72936_4_ + 32 << 12);
                            }
                        }
                    }
                }
            }

            this.field_72984_F.func_76319_b();
        }
    }

    public boolean func_72955_a(boolean p_72955_1_)
    {
        return false;
    }

    public List func_72920_a(Chunk p_72920_1_, boolean p_72920_2_)
    {
        return null;
    }

    public List func_72839_b(Entity p_72839_1_, AxisAlignedBB p_72839_2_)
    {
        this.field_72991_N.clear();
        int i = MathHelper.func_76128_c((p_72839_2_.field_72340_a - 2.0D) / 16.0D);
        int j = MathHelper.func_76128_c((p_72839_2_.field_72336_d + 2.0D) / 16.0D);
        int k = MathHelper.func_76128_c((p_72839_2_.field_72339_c - 2.0D) / 16.0D);
        int l = MathHelper.func_76128_c((p_72839_2_.field_72334_f + 2.0D) / 16.0D);

        for (int i1 = i; i1 <= j; ++i1)
        {
            for (int j1 = k; j1 <= l; ++j1)
            {
                if (this.func_72916_c(i1, j1))
                {
                    this.func_72964_e(i1, j1).func_76588_a(p_72839_1_, p_72839_2_, this.field_72991_N);
                }
            }
        }

        return this.field_72991_N;
    }

    public List func_72872_a(Class p_72872_1_, AxisAlignedBB p_72872_2_)
    {
        return this.func_82733_a(p_72872_1_, p_72872_2_, (IEntitySelector)null);
    }

    public List func_82733_a(Class p_82733_1_, AxisAlignedBB p_82733_2_, IEntitySelector p_82733_3_)
    {
        int i = MathHelper.func_76128_c((p_82733_2_.field_72340_a - 2.0D) / 16.0D);
        int j = MathHelper.func_76128_c((p_82733_2_.field_72336_d + 2.0D) / 16.0D);
        int k = MathHelper.func_76128_c((p_82733_2_.field_72339_c - 2.0D) / 16.0D);
        int l = MathHelper.func_76128_c((p_82733_2_.field_72334_f + 2.0D) / 16.0D);
        ArrayList arraylist = new ArrayList();

        for (int i1 = i; i1 <= j; ++i1)
        {
            for (int j1 = k; j1 <= l; ++j1)
            {
                if (this.func_72916_c(i1, j1))
                {
                    this.func_72964_e(i1, j1).func_76618_a(p_82733_1_, p_82733_2_, arraylist, p_82733_3_);
                }
            }
        }

        return arraylist;
    }

    public Entity func_72857_a(Class p_72857_1_, AxisAlignedBB p_72857_2_, Entity p_72857_3_)
    {
        List list = this.func_72872_a(p_72857_1_, p_72857_2_);
        Entity entity1 = null;
        double d0 = Double.MAX_VALUE;

        for (int i = 0; i < list.size(); ++i)
        {
            Entity entity2 = (Entity)list.get(i);

            if (entity2 != p_72857_3_)
            {
                double d1 = p_72857_3_.func_70068_e(entity2);

                if (d1 <= d0)
                {
                    entity1 = entity2;
                    d0 = d1;
                }
            }
        }

        return entity1;
    }

    public abstract Entity func_73045_a(int i);

    public void func_72944_b(int p_72944_1_, int p_72944_2_, int p_72944_3_, TileEntity p_72944_4_)
    {
        if (this.func_72899_e(p_72944_1_, p_72944_2_, p_72944_3_))
        {
            this.func_72938_d(p_72944_1_, p_72944_3_).func_76630_e();
        }
    }

    public int func_72907_a(Class p_72907_1_)
    {
        int i = 0;

        for (int j = 0; j < this.field_72996_f.size(); ++j)
        {
            Entity entity = (Entity)this.field_72996_f.get(j);

            if (p_72907_1_.isAssignableFrom(entity.getClass()))
            {
                ++i;
            }
        }

        return i;
    }

    public void func_72868_a(List p_72868_1_)
    {
        // CraftBukkit start
        Entity entity = null;

        for (int i = 0; i < p_72868_1_.size(); ++i)
        {
            entity = (Entity) p_72868_1_.get(i);

            if (entity == null)
            {
                continue;
            }

            this.field_72996_f.add(entity);
            // CraftBukkit end
            this.func_72923_a((Entity)p_72868_1_.get(i));
        }
    }

    public void func_72828_b(List p_72828_1_)
    {
        this.field_72997_g.addAll(p_72828_1_);
    }

    public boolean func_72931_a(int p_72931_1_, int p_72931_2_, int p_72931_3_, int p_72931_4_, boolean p_72931_5_, int p_72931_6_, Entity p_72931_7_)
    {
        int j1 = this.func_72798_a(p_72931_2_, p_72931_3_, p_72931_4_);
        Block block = Block.field_71973_m[j1];
        Block block1 = Block.field_71973_m[p_72931_1_];
        AxisAlignedBB axisalignedbb = block1.func_71872_e(this, p_72931_2_, p_72931_3_, p_72931_4_);

        if (p_72931_5_)
        {
            axisalignedbb = null;
        }

        boolean defaultReturn; // CraftBukkit - store the default action
        if (axisalignedbb != null && !this.func_72917_a(axisalignedbb, p_72931_7_))
        {
            defaultReturn = false; // CraftBukkit
        }
        else
        {
            if (block != null && (block == Block.field_71942_A || block == Block.field_71943_B || block == Block.field_71944_C || block == Block.field_71938_D || block == Block.field_72067_ar || block.field_72018_cp.func_76222_j()))
            {
                block = null;
            }

            // CraftBukkit
            defaultReturn = block != null && block.field_72018_cp == Material.field_76265_p && block1 == Block.field_82510_ck ? true : p_72931_1_ > 0 && block == null && block1.func_71850_a_(this, p_72931_2_, p_72931_3_, p_72931_4_, p_72931_6_);
        }

        // CraftBukkit start
        BlockCanBuildEvent event = new BlockCanBuildEvent(this.getWorld().getBlockAt(p_72931_2_, p_72931_3_, p_72931_4_), p_72931_1_, defaultReturn);
        this.getServer().getPluginManager().callEvent(event);
        return event.isBuildable();
        // CraftBukkit end
    }

    public PathEntity func_72865_a(Entity p_72865_1_, Entity p_72865_2_, float p_72865_3_, boolean p_72865_4_, boolean p_72865_5_, boolean p_72865_6_, boolean p_72865_7_)
    {
        this.field_72984_F.func_76320_a("pathfind");
        int i = MathHelper.func_76128_c(p_72865_1_.field_70165_t);
        int j = MathHelper.func_76128_c(p_72865_1_.field_70163_u + 1.0D);
        int k = MathHelper.func_76128_c(p_72865_1_.field_70161_v);
        int l = (int)(p_72865_3_ + 16.0F);
        int i1 = i - l;
        int j1 = j - l;
        int k1 = k - l;
        int l1 = i + l;
        int i2 = j + l;
        int j2 = k + l;
        ChunkCache chunkcache = new ChunkCache(this, i1, j1, k1, l1, i2, j2);
        PathEntity pathentity = (new PathFinder(chunkcache, p_72865_4_, p_72865_5_, p_72865_6_, p_72865_7_)).func_75856_a(p_72865_1_, p_72865_2_, p_72865_3_);
        this.field_72984_F.func_76319_b();
        return pathentity;
    }

    public PathEntity func_72844_a(Entity p_72844_1_, int p_72844_2_, int p_72844_3_, int p_72844_4_, float p_72844_5_, boolean p_72844_6_, boolean p_72844_7_, boolean p_72844_8_, boolean p_72844_9_)
    {
        this.field_72984_F.func_76320_a("pathfind");
        int l = MathHelper.func_76128_c(p_72844_1_.field_70165_t);
        int i1 = MathHelper.func_76128_c(p_72844_1_.field_70163_u);
        int j1 = MathHelper.func_76128_c(p_72844_1_.field_70161_v);
        int k1 = (int)(p_72844_5_ + 8.0F);
        int l1 = l - k1;
        int i2 = i1 - k1;
        int j2 = j1 - k1;
        int k2 = l + k1;
        int l2 = i1 + k1;
        int i3 = j1 + k1;
        ChunkCache chunkcache = new ChunkCache(this, l1, i2, j2, k2, l2, i3);
        PathEntity pathentity = (new PathFinder(chunkcache, p_72844_6_, p_72844_7_, p_72844_8_, p_72844_9_)).func_75859_a(p_72844_1_, p_72844_2_, p_72844_3_, p_72844_4_, p_72844_5_);
        this.field_72984_F.func_76319_b();
        return pathentity;
    }

    public boolean func_72879_k(int p_72879_1_, int p_72879_2_, int p_72879_3_, int p_72879_4_)
    {
        int i1 = this.func_72798_a(p_72879_1_, p_72879_2_, p_72879_3_);
        return i1 == 0 ? false : Block.field_71973_m[i1].func_71855_c(this, p_72879_1_, p_72879_2_, p_72879_3_, p_72879_4_);
    }

    public boolean func_72871_y(int p_72871_1_, int p_72871_2_, int p_72871_3_)
    {
        return this.func_72879_k(p_72871_1_, p_72871_2_ - 1, p_72871_3_, 0) ? true : (this.func_72879_k(p_72871_1_, p_72871_2_ + 1, p_72871_3_, 1) ? true : (this.func_72879_k(p_72871_1_, p_72871_2_, p_72871_3_ - 1, 2) ? true : (this.func_72879_k(p_72871_1_, p_72871_2_, p_72871_3_ + 1, 3) ? true : (this.func_72879_k(p_72871_1_ - 1, p_72871_2_, p_72871_3_, 4) ? true : this.func_72879_k(p_72871_1_ + 1, p_72871_2_, p_72871_3_, 5)))));
    }

    public boolean func_72878_l(int p_72878_1_, int p_72878_2_, int p_72878_3_, int p_72878_4_)
    {
        if (this.func_72809_s(p_72878_1_, p_72878_2_, p_72878_3_))
        {
            return this.func_72871_y(p_72878_1_, p_72878_2_, p_72878_3_);
        }
        else
        {
            int i1 = this.func_72798_a(p_72878_1_, p_72878_2_, p_72878_3_);
            return i1 == 0 ? false : Block.field_71973_m[i1].func_71865_a(this, p_72878_1_, p_72878_2_, p_72878_3_, p_72878_4_);
        }
    }

    public boolean func_72864_z(int p_72864_1_, int p_72864_2_, int p_72864_3_)
    {
        return this.func_72878_l(p_72864_1_, p_72864_2_ - 1, p_72864_3_, 0) ? true : (this.func_72878_l(p_72864_1_, p_72864_2_ + 1, p_72864_3_, 1) ? true : (this.func_72878_l(p_72864_1_, p_72864_2_, p_72864_3_ - 1, 2) ? true : (this.func_72878_l(p_72864_1_, p_72864_2_, p_72864_3_ + 1, 3) ? true : (this.func_72878_l(p_72864_1_ - 1, p_72864_2_, p_72864_3_, 4) ? true : this.func_72878_l(p_72864_1_ + 1, p_72864_2_, p_72864_3_, 5)))));
    }

    public EntityPlayer func_72890_a(Entity p_72890_1_, double p_72890_2_)
    {
        return this.func_72977_a(p_72890_1_.field_70165_t, p_72890_1_.field_70163_u, p_72890_1_.field_70161_v, p_72890_2_);
    }

    public EntityPlayer func_72977_a(double p_72977_1_, double p_72977_3_, double p_72977_5_, double p_72977_7_)
    {
        double d4 = -1.0D;
        EntityPlayer entityplayer = null;

        for (int i = 0; i < this.field_73010_i.size(); ++i)
        {
            EntityPlayer entityplayer1 = (EntityPlayer)this.field_73010_i.get(i);

            // CraftBukkit start - fixed an NPE
            if (entityplayer1 == null || entityplayer1.field_70128_L)
            {
                continue;
            }

            // CraftBukkit end
            double d5 = entityplayer1.func_70092_e(p_72977_1_, p_72977_3_, p_72977_5_);

            if ((p_72977_7_ < 0.0D || d5 < p_72977_7_ * p_72977_7_) && (d4 == -1.0D || d5 < d4))
            {
                d4 = d5;
                entityplayer = entityplayer1;
            }
        }

        return entityplayer;
    }

    public EntityPlayer func_72856_b(Entity p_72856_1_, double p_72856_2_)
    {
        return this.func_72846_b(p_72856_1_.field_70165_t, p_72856_1_.field_70163_u, p_72856_1_.field_70161_v, p_72856_2_);
    }

    public EntityPlayer func_72846_b(double p_72846_1_, double p_72846_3_, double p_72846_5_, double p_72846_7_)
    {
        double d4 = -1.0D;
        EntityPlayer entityplayer = null;

        for (int i = 0; i < this.field_73010_i.size(); ++i)
        {
            EntityPlayer entityplayer1 = (EntityPlayer)this.field_73010_i.get(i);

            // CraftBukkit start - fixed an NPE
            if (entityplayer1 == null || entityplayer1.field_70128_L)
            {
                continue;
            }

            // CraftBukkit end

            if (!entityplayer1.field_71075_bZ.field_75102_a && entityplayer1.func_70089_S())
            {
                double d5 = entityplayer1.func_70092_e(p_72846_1_, p_72846_3_, p_72846_5_);
                double d6 = p_72846_7_;

                if (entityplayer1.func_70093_af())
                {
                    d6 = p_72846_7_ * 0.800000011920929D;
                }

                if (entityplayer1.func_82150_aj())
                {
                    float f = entityplayer1.func_82243_bO();

                    if (f < 0.1F)
                    {
                        f = 0.1F;
                    }

                    d6 *= (double)(0.7F * f);
                }

                if ((p_72846_7_ < 0.0D || d5 < d6 * d6) && (d4 == -1.0D || d5 < d4))
                {
                    d4 = d5;
                    entityplayer = entityplayer1;
                }
            }
        }

        return entityplayer;
    }

    public EntityPlayer func_72924_a(String p_72924_1_)
    {
        for (int i = 0; i < this.field_73010_i.size(); ++i)
        {
            if (p_72924_1_.equals(((EntityPlayer)this.field_73010_i.get(i)).field_71092_bJ))
            {
                return (EntityPlayer)this.field_73010_i.get(i);
            }
        }

        return null;
    }

    public void func_72906_B() throws MinecraftException   // CraftBukkit - added throws
    {
        this.field_73019_z.func_75762_c();
    }

    public long func_72905_C()
    {
        return this.field_72986_A.func_76063_b();
    }

    public long func_82737_E()
    {
        return this.field_72986_A.func_82573_f();
    }

    public long func_72820_D()
    {
        return this.field_72986_A.func_76073_f();
    }

    public void func_72877_b(long p_72877_1_)
    {
        this.field_72986_A.func_76068_b(p_72877_1_);
    }

    public ChunkCoordinates func_72861_E()
    {
        return new ChunkCoordinates(this.field_72986_A.func_76079_c(), this.field_72986_A.func_76075_d(), this.field_72986_A.func_76074_e());
    }

    public boolean func_72962_a(EntityPlayer p_72962_1_, int p_72962_2_, int p_72962_3_, int p_72962_4_)
    {
        return true;
    }

    public void func_72960_a(Entity p_72960_1_, byte p_72960_2_) {}

    public IChunkProvider func_72863_F()
    {
        return this.field_73020_y;
    }

    public void func_72965_b(int p_72965_1_, int p_72965_2_, int p_72965_3_, int p_72965_4_, int p_72965_5_, int p_72965_6_)
    {
        if (p_72965_4_ > 0)
        {
            Block.field_71973_m[p_72965_4_].func_71883_b(this, p_72965_1_, p_72965_2_, p_72965_3_, p_72965_5_, p_72965_6_);
        }
    }

    public ISaveHandler func_72860_G()
    {
        return this.field_73019_z;
    }

    public WorldInfo func_72912_H()
    {
        return this.field_72986_A;
    }

    public GameRules func_82736_K()
    {
        return this.field_72986_A.func_82574_x();
    }

    public void func_72854_c() {}

    // CraftBukkit start
    // Calls the method that checks to see if players are sleeping
    // Called by CraftPlayer.setPermanentSleeping()
    public void checkSleepStatus()
    {
        if (!this.field_72995_K)
        {
            this.func_72854_c();
        }
    }
    // CraftBukkit end

    public float func_72819_i(float p_72819_1_)
    {
        return (this.field_73018_p + (this.field_73017_q - this.field_73018_p) * p_72819_1_) * this.func_72867_j(p_72819_1_);
    }

    public float func_72867_j(float p_72867_1_)
    {
        return this.field_73003_n + (this.field_73004_o - this.field_73003_n) * p_72867_1_;
    }

    public boolean func_72911_I()
    {
        return (double)this.func_72819_i(1.0F) > 0.9D;
    }

    public boolean func_72896_J()
    {
        return (double)this.func_72867_j(1.0F) > 0.2D;
    }

    public boolean func_72951_B(int p_72951_1_, int p_72951_2_, int p_72951_3_)
    {
        if (!this.func_72896_J())
        {
            return false;
        }
        else if (!this.func_72937_j(p_72951_1_, p_72951_2_, p_72951_3_))
        {
            return false;
        }
        else if (this.func_72874_g(p_72951_1_, p_72951_3_) > p_72951_2_)
        {
            return false;
        }
        else
        {
            BiomeGenBase biomegenbase = this.func_72807_a(p_72951_1_, p_72951_3_);
            return biomegenbase.func_76746_c() ? false : biomegenbase.func_76738_d();
        }
    }

    public boolean func_72958_C(int p_72958_1_, int p_72958_2_, int p_72958_3_)
    {
        BiomeGenBase biomegenbase = this.func_72807_a(p_72958_1_, p_72958_3_);
        return biomegenbase.func_76736_e();
    }

    public void func_72823_a(String p_72823_1_, WorldSavedData p_72823_2_)
    {
        this.field_72988_C.func_75745_a(p_72823_1_, p_72823_2_);
    }

    public WorldSavedData func_72943_a(Class p_72943_1_, String p_72943_2_)
    {
        return this.field_72988_C.func_75742_a(p_72943_1_, p_72943_2_);
    }

    public int func_72841_b(String p_72841_1_)
    {
        return this.field_72988_C.func_75743_a(p_72841_1_);
    }

    public void func_82739_e(int p_82739_1_, int p_82739_2_, int p_82739_3_, int p_82739_4_, int p_82739_5_)
    {
        for (int j1 = 0; j1 < this.field_73021_x.size(); ++j1)
        {
            ((IWorldAccess)this.field_73021_x.get(j1)).func_82746_a(p_82739_1_, p_82739_2_, p_82739_3_, p_82739_4_, p_82739_5_);
        }
    }

    public void func_72926_e(int p_72926_1_, int p_72926_2_, int p_72926_3_, int p_72926_4_, int p_72926_5_)
    {
        this.func_72889_a((EntityPlayer)null, p_72926_1_, p_72926_2_, p_72926_3_, p_72926_4_, p_72926_5_);
    }

    public void func_72889_a(EntityPlayer p_72889_1_, int p_72889_2_, int p_72889_3_, int p_72889_4_, int p_72889_5_, int p_72889_6_)
    {
        try
        {
            for (int j1 = 0; j1 < this.field_73021_x.size(); ++j1)
            {
                ((IWorldAccess)this.field_73021_x.get(j1)).func_72706_a(p_72889_1_, p_72889_2_, p_72889_3_, p_72889_4_, p_72889_5_, p_72889_6_);
            }
        }
        catch (Throwable throwable)
        {
            CrashReport crashreport = CrashReport.func_85055_a(throwable, "Playing level event");
            CrashReportCategory crashreportcategory = crashreport.func_85058_a("Level event being played");
            crashreportcategory.func_71507_a("Block coordinates", CrashReportCategory.func_85071_a(p_72889_3_, p_72889_4_, p_72889_5_));
            crashreportcategory.func_71507_a("Event source", p_72889_1_);
            crashreportcategory.func_71507_a("Event type", Integer.valueOf(p_72889_2_));
            crashreportcategory.func_71507_a("Event data", Integer.valueOf(p_72889_6_));
            throw new ReportedException(crashreport);
        }
    }

    public int func_72800_K()
    {
        return 256;
    }

    public int func_72940_L()
    {
        return this.field_73011_w.field_76576_e ? 128 : 256;
    }

    public IUpdatePlayerListBox func_82735_a(EntityMinecart p_82735_1_)
    {
        return null;
    }

    public Random func_72843_D(int p_72843_1_, int p_72843_2_, int p_72843_3_)
    {
        long l = (long)p_72843_1_ * 341873128712L + (long)p_72843_2_ * 132897987541L + this.func_72912_H().func_76063_b() + (long)p_72843_3_;
        this.field_73012_v.setSeed(l);
        return this.field_73012_v;
    }

    public ChunkPosition func_72946_b(String p_72946_1_, int p_72946_2_, int p_72946_3_, int p_72946_4_)
    {
        return this.func_72863_F().func_73150_a(this, p_72946_1_, p_72946_2_, p_72946_3_, p_72946_4_);
    }

    public CrashReportCategory func_72914_a(CrashReport p_72914_1_)
    {
        CrashReportCategory crashreportcategory = p_72914_1_.func_85057_a("Affected level", 1);
        crashreportcategory.func_71507_a("Level name", (this.field_72986_A == null ? "????" : this.field_72986_A.func_76065_j()));
        crashreportcategory.func_71500_a("All players", (Callable)(new CallableLvl2(this)));
        crashreportcategory.func_71500_a("Chunk stats", (Callable)(new CallableLvl3(this)));

        try
        {
            this.field_72986_A.func_85118_a(crashreportcategory);
        }
        catch (Throwable throwable)
        {
            crashreportcategory.func_71499_a("Level Data Unobtainable", throwable);
        }

        return crashreportcategory;
    }

    public void func_72888_f(int p_72888_1_, int p_72888_2_, int p_72888_3_, int p_72888_4_, int p_72888_5_)
    {
        for (int j1 = 0; j1 < this.field_73021_x.size(); ++j1)
        {
            IWorldAccess iworldaccess = (IWorldAccess)this.field_73021_x.get(j1);
            iworldaccess.func_72705_a(p_72888_1_, p_72888_2_, p_72888_3_, p_72888_4_, p_72888_5_);
        }
    }

    public Vec3Pool func_82732_R()
    {
        return this.field_82741_K;
    }

    public Calendar func_83015_S()
    {
        if (this.func_82737_E() % 600L == 0L)
        {
            this.field_83016_L.setTimeInMillis(System.currentTimeMillis());
        }

        return this.field_83016_L;
    }
}