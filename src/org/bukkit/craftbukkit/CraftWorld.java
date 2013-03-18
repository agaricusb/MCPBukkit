package org.bukkit.craftbukkit;

import java.io.File;
import java.util.Iterator;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.apache.commons.lang.Validate;

import org.bukkit.craftbukkit.entity.*;
import org.bukkit.craftbukkit.metadata.BlockMetadataStore;
import org.bukkit.entity.*;
import org.bukkit.entity.Entity;


import org.bukkit.entity.Arrow;
import org.bukkit.Effect;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.world.SpawnChangeEvent;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Boat;
import org.bukkit.Chunk;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import org.bukkit.BlockChangeDelegate;
import org.bukkit.Bukkit;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Location;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.Difficulty;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.block.CraftBlock;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.plugin.messaging.StandardMessenger;
import org.bukkit.craftbukkit.util.LongHash;

public class CraftWorld implements World {
    public static final int CUSTOM_DIMENSION_OFFSET = 10;

    private final net.minecraft.world.WorldServer world;
    private Environment environment;
    private final CraftServer server = (CraftServer) Bukkit.getServer();
    private final ChunkGenerator generator;
    private final List<BlockPopulator> populators = new ArrayList<BlockPopulator>();
    private final BlockMetadataStore blockMetadata = new BlockMetadataStore(this);
    private int monsterSpawn = -1;
    private int animalSpawn = -1;
    private int waterAnimalSpawn = -1;
    private int ambientSpawn = -1;
    private int chunkLoadCount = 0;
    private int chunkGCTickCount;

    private static final Random rand = new Random();

    public CraftWorld(net.minecraft.world.WorldServer world, ChunkGenerator gen, Environment env) {
        this.world = world;
        this.generator = gen;

        environment = env;

        if (server.chunkGCPeriod > 0) {
            chunkGCTickCount = rand.nextInt(server.chunkGCPeriod);
        }
    }

    public Block getBlockAt(int x, int y, int z) {
        return getChunkAt(x >> 4, z >> 4).getBlock(x & 0xF, y & 0xFF, z & 0xF);
    }

    public int getBlockTypeIdAt(int x, int y, int z) {
        return world.func_72798_a(x, y, z);
    }

    public int getHighestBlockYAt(int x, int z) {
        if (!isChunkLoaded(x >> 4, z >> 4)) {
            loadChunk(x >> 4, z >> 4);
        }

        return world.func_72976_f(x, z);
    }

    public Location getSpawnLocation() {
        net.minecraft.util.ChunkCoordinates spawn = world.func_72861_E();
        return new Location(this, spawn.field_71574_a, spawn.field_71572_b, spawn.field_71573_c);
    }

    public boolean setSpawnLocation(int x, int y, int z) {
        try {
            Location previousLocation = getSpawnLocation();
            world.field_72986_A.func_76081_a(x, y, z);

            // Notify anyone who's listening.
            SpawnChangeEvent event = new SpawnChangeEvent(this, previousLocation);
            server.getPluginManager().callEvent(event);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Chunk getChunkAt(int x, int z) {
        return this.world.field_73059_b.func_73158_c(x, z).bukkitChunk;
    }

    public Chunk getChunkAt(Block block) {
        return getChunkAt(block.getX() >> 4, block.getZ() >> 4);
    }

    public boolean isChunkLoaded(int x, int z) {
        return world.field_73059_b.func_73149_a(x, z);
    }

    public Chunk[] getLoadedChunks() {
        Object[] chunks = world.field_73059_b.field_73244_f.values().toArray();
        org.bukkit.Chunk[] craftChunks = new CraftChunk[chunks.length];

        for (int i = 0; i < chunks.length; i++) {
            net.minecraft.world.chunk.Chunk chunk = (net.minecraft.world.chunk.Chunk) chunks[i];
            craftChunks[i] = chunk.bukkitChunk;
        }

        return craftChunks;
    }

    public void loadChunk(int x, int z) {
        loadChunk(x, z, true);
    }

    public boolean unloadChunk(Chunk chunk) {
        return unloadChunk(chunk.getX(), chunk.getZ());
    }

    public boolean unloadChunk(int x, int z) {
        return unloadChunk(x, z, true);
    }

    public boolean unloadChunk(int x, int z, boolean save) {
        return unloadChunk(x, z, save, false);
    }

    public boolean unloadChunkRequest(int x, int z) {
        return unloadChunkRequest(x, z, true);
    }

    public boolean unloadChunkRequest(int x, int z, boolean safe) {
        if (safe && isChunkInUse(x, z)) {
            return false;
        }

        world.field_73059_b.func_73241_b(x, z);

        return true;
    }

    public boolean unloadChunk(int x, int z, boolean save, boolean safe) {
        if (safe && isChunkInUse(x, z)) {
            return false;
        }

        net.minecraft.world.chunk.Chunk chunk = world.field_73059_b.func_73154_d(x, z);
        if (chunk.mustSave) {   // If chunk had previously been queued to save, must do save to avoid loss of that data
            save = true;
        }

        chunk.func_76623_d(); // Always remove entities - even if discarding, need to get them out of world table

        if (save && !(chunk instanceof net.minecraft.world.chunk.EmptyChunk)) {
            world.field_73059_b.func_73242_b(chunk);
            world.field_73059_b.func_73243_a(chunk);
        }

        world.field_73059_b.field_73248_b.remove(x, z);
        world.field_73059_b.field_73244_f.remove(LongHash.toLong(x, z));

        return true;
    }

    public boolean regenerateChunk(int x, int z) {
        unloadChunk(x, z, false, false);

        world.field_73059_b.field_73248_b.remove(x, z);

        net.minecraft.world.chunk.Chunk chunk = null;

        if (world.field_73059_b.field_73246_d == null) {
            chunk = world.field_73059_b.field_73249_c;
        } else {
            chunk = world.field_73059_b.field_73246_d.func_73154_d(x, z);
        }

        chunkLoadPostProcess(chunk, x, z);

        refreshChunk(x, z);

        return chunk != null;
    }

    public boolean refreshChunk(int x, int z) {
        if (!isChunkLoaded(x, z)) {
            return false;
        }

        int px = x << 4;
        int pz = z << 4;

        // If there are more than 64 updates to a chunk at once, it will update all 'touched' sections within the chunk
        // And will include biome data if all sections have been 'touched'
        // This flags 65 blocks distributed across all the sections of the chunk, so that everything is sent, including biomes
        int height = getMaxHeight() / 16;
        for (int idx = 0; idx < 64; idx++) {
            world.func_72845_h(px + (idx / height), ((idx % height) * 16), pz);
        }
        world.func_72845_h(px + 15, (height * 16) - 1, pz + 15);

        return true;
    }

    public boolean isChunkInUse(int x, int z) {
        return world.func_73040_p().isChunkInUse(x, z);
    }

    public boolean loadChunk(int x, int z, boolean generate) {
        chunkLoadCount++;
        if (generate) {
            // Use the default variant of loadChunk when generate == true.
            return world.field_73059_b.func_73158_c(x, z) != null;
        }

        world.field_73059_b.field_73248_b.remove(x, z);
        net.minecraft.world.chunk.Chunk chunk = (net.minecraft.world.chunk.Chunk) world.field_73059_b.field_73244_f.get(LongHash.toLong(x, z));

        if (chunk == null) {
            chunk = world.field_73059_b.func_73239_e(x, z);

            chunkLoadPostProcess(chunk, x, z);
        }
        return chunk != null;
    }

    @SuppressWarnings("unchecked")
    private void chunkLoadPostProcess(net.minecraft.world.chunk.Chunk chunk, int x, int z) {
        if (chunk != null) {
            world.field_73059_b.field_73244_f.put(LongHash.toLong(x, z), chunk);

            chunk.func_76631_c();

            if (!chunk.field_76646_k && world.field_73059_b.func_73149_a(x + 1, z + 1) && world.field_73059_b.func_73149_a(x, z + 1) && world.field_73059_b.func_73149_a(x + 1, z)) {
                world.field_73059_b.func_73153_a(world.field_73059_b, x, z);
            }

            if (world.field_73059_b.func_73149_a(x - 1, z) && !world.field_73059_b.func_73154_d(x - 1, z).field_76646_k && world.field_73059_b.func_73149_a(x - 1, z + 1) && world.field_73059_b.func_73149_a(x, z + 1) && world.field_73059_b.func_73149_a(x - 1, z)) {
                world.field_73059_b.func_73153_a(world.field_73059_b, x - 1, z);
            }

            if (world.field_73059_b.func_73149_a(x, z - 1) && !world.field_73059_b.func_73154_d(x, z - 1).field_76646_k && world.field_73059_b.func_73149_a(x + 1, z - 1) && world.field_73059_b.func_73149_a(x, z - 1) && world.field_73059_b.func_73149_a(x + 1, z)) {
                world.field_73059_b.func_73153_a(world.field_73059_b, x, z - 1);
            }

            if (world.field_73059_b.func_73149_a(x - 1, z - 1) && !world.field_73059_b.func_73154_d(x - 1, z - 1).field_76646_k && world.field_73059_b.func_73149_a(x - 1, z - 1) && world.field_73059_b.func_73149_a(x, z - 1) && world.field_73059_b.func_73149_a(x - 1, z)) {
                world.field_73059_b.func_73153_a(world.field_73059_b, x - 1, z - 1);
            }
        }
    }

    public boolean isChunkLoaded(Chunk chunk) {
        return isChunkLoaded(chunk.getX(), chunk.getZ());
    }

    public void loadChunk(Chunk chunk) {
        loadChunk(chunk.getX(), chunk.getZ());
        ((CraftChunk) getChunkAt(chunk.getX(), chunk.getZ())).getHandle().bukkitChunk = chunk;
    }

    public net.minecraft.world.WorldServer getHandle() {
        return world;
    }

    public org.bukkit.entity.Item dropItem(Location loc, ItemStack item) {
        Validate.notNull(item, "Cannot drop a Null item.");
        Validate.isTrue(item.getTypeId() != 0, "Cannot drop AIR.");
        net.minecraft.entity.item.EntityItem entity = new net.minecraft.entity.item.EntityItem(world, loc.getX(), loc.getY(), loc.getZ(), CraftItemStack.asNMSCopy(item));
        entity.field_70293_c = 10;
        world.func_72838_d(entity);
        // TODO this is inconsistent with how Entity.getBukkitEntity() works.
        // However, this entity is not at the moment backed by a server entity class so it may be left.
        return new CraftItem(world.getServer(), entity);
    }

    public org.bukkit.entity.Item dropItemNaturally(Location loc, ItemStack item) {
        double xs = world.field_73012_v.nextFloat() * 0.7F + (1.0F - 0.7F) * 0.5D;
        double ys = world.field_73012_v.nextFloat() * 0.7F + (1.0F - 0.7F) * 0.5D;
        double zs = world.field_73012_v.nextFloat() * 0.7F + (1.0F - 0.7F) * 0.5D;
        loc = loc.clone();
        loc.setX(loc.getX() + xs);
        loc.setY(loc.getY() + ys);
        loc.setZ(loc.getZ() + zs);
        return dropItem(loc, item);
    }

    public Arrow spawnArrow(Location loc, Vector velocity, float speed, float spread) {
        Validate.notNull(loc, "Can not spawn arrow with a null location");
        Validate.notNull(velocity, "Can not spawn arrow with a null velocity");

        net.minecraft.entity.projectile.EntityArrow arrow = new net.minecraft.entity.projectile.EntityArrow(world);
        arrow.func_70012_b(loc.getX(), loc.getY(), loc.getZ(), loc.getPitch(), loc.getYaw());
        arrow.func_70186_c(velocity.getX(), velocity.getY(), velocity.getZ(), speed, spread);
        world.func_72838_d(arrow);
        return (Arrow) arrow.getBukkitEntity();
    }

    @Deprecated
    public LivingEntity spawnCreature(Location loc, CreatureType creatureType) {
        return spawnCreature(loc, creatureType.toEntityType());
    }

    @Deprecated
    public LivingEntity spawnCreature(Location loc, EntityType creatureType) {
        Validate.isTrue(creatureType.isAlive(), "EntityType not instance of LivingEntity");
        return (LivingEntity) spawnEntity(loc, creatureType);
    }

    public Entity spawnEntity(Location loc, EntityType entityType) {
        return spawn(loc, entityType.getEntityClass());
    }

    public LightningStrike strikeLightning(Location loc) {
        net.minecraft.entity.effect.EntityLightningBolt lightning = new net.minecraft.entity.effect.EntityLightningBolt(world, loc.getX(), loc.getY(), loc.getZ());
        world.func_72942_c(lightning);
        return new CraftLightningStrike(server, lightning);
    }

    public LightningStrike strikeLightningEffect(Location loc) {
        net.minecraft.entity.effect.EntityLightningBolt lightning = new net.minecraft.entity.effect.EntityLightningBolt(world, loc.getX(), loc.getY(), loc.getZ(), true);
        world.func_72942_c(lightning);
        return new CraftLightningStrike(server, lightning);
    }

    public boolean generateTree(Location loc, TreeType type) {
        return generateTree(loc, type, world);
    }

    public boolean generateTree(Location loc, TreeType type, BlockChangeDelegate delegate) {
        net.minecraft.block.BlockSapling.TreeGenerator gen;
        switch (type) {
        case BIG_TREE:
            gen = new net.minecraft.world.gen.feature.WorldGenBigTree(true);
            break;
        case BIRCH:
            gen = new net.minecraft.world.gen.feature.WorldGenForest(true);
            break;
        case REDWOOD:
            gen = new net.minecraft.world.gen.feature.WorldGenTaiga2(true);
            break;
        case TALL_REDWOOD:
            gen = new net.minecraft.world.gen.feature.WorldGenTaiga1();
            break;
        case JUNGLE:
            gen = new net.minecraft.world.gen.feature.WorldGenHugeTrees(true, 10 + rand.nextInt(20), 3, 3);
            break;
        case SMALL_JUNGLE:
            gen = new net.minecraft.world.gen.feature.WorldGenTrees(true, 4 + rand.nextInt(7), 3, 3, false);
            break;
        case JUNGLE_BUSH:
            gen = new net.minecraft.world.gen.feature.WorldGenShrub(3, 0);
            break;
        case RED_MUSHROOM:
            gen = new net.minecraft.world.gen.feature.WorldGenBigMushroom(1);
            break;
        case BROWN_MUSHROOM:
            gen = new net.minecraft.world.gen.feature.WorldGenBigMushroom(0);
            break;
        case SWAMP:
            gen = new net.minecraft.world.gen.feature.WorldGenSwamp();
            break;
        case TREE:
        default:
            gen = new net.minecraft.world.gen.feature.WorldGenTrees(true);
            break;
        }

        return gen.generate(delegate, rand, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }

    public net.minecraft.tileentity.TileEntity getTileEntityAt(final int x, final int y, final int z) {
        return world.func_72796_p(x, y, z);
    }

    public String getName() {
        return world.field_72986_A.func_76065_j();
    }

    @Deprecated
    public long getId() {
        return world.field_72986_A.func_76063_b();
    }

    public UUID getUID() {
        return world.func_72860_G().getUUID();
    }

    @Override
    public String toString() {
        return "CraftWorld{name=" + getName() + '}';
    }

    public long getTime() {
        long time = getFullTime() % 24000;
        if (time < 0) time += 24000;
        return time;
    }

    public void setTime(long time) {
        long margin = (time - getFullTime()) % 24000;
        if (margin < 0) margin += 24000;
        setFullTime(getFullTime() + margin);
    }

    public long getFullTime() {
        return world.func_72820_D();
    }

    public void setFullTime(long time) {
        world.func_72877_b(time);

        // Forces the client to update to the new time immediately
        for (Player p : getPlayers()) {
            CraftPlayer cp = (CraftPlayer) p;
            if (cp.getHandle().field_71135_a == null) continue;

            cp.getHandle().field_71135_a.func_72567_b(new net.minecraft.network.packet.Packet4UpdateTime(cp.getHandle().field_70170_p.func_82737_E(), cp.getHandle().getPlayerTime()));
        }
    }

    public boolean createExplosion(double x, double y, double z, float power) {
        return createExplosion(x, y, z, power, false, true);
    }

    public boolean createExplosion(double x, double y, double z, float power, boolean setFire) {
        return createExplosion(x, y, z, power, setFire, true);
    }

    public boolean createExplosion(double x, double y, double z, float power, boolean setFire, boolean breakBlocks) {
        return !world.func_72885_a(null, x, y, z, power, setFire, breakBlocks).wasCanceled;
    }

    public boolean createExplosion(Location loc, float power) {
        return createExplosion(loc, power, false);
    }

    public boolean createExplosion(Location loc, float power, boolean setFire) {
        return createExplosion(loc.getX(), loc.getY(), loc.getZ(), power, setFire);
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment env) {
        if (environment != env) {
            environment = env;
            world.field_73011_w = net.minecraft.world.WorldProvider.func_76570_a(environment.getId());
        }
    }

    public Block getBlockAt(Location location) {
        return getBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public int getBlockTypeIdAt(Location location) {
        return getBlockTypeIdAt(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public int getHighestBlockYAt(Location location) {
        return getHighestBlockYAt(location.getBlockX(), location.getBlockZ());
    }

    public Chunk getChunkAt(Location location) {
        return getChunkAt(location.getBlockX() >> 4, location.getBlockZ() >> 4);
    }

    public ChunkGenerator getGenerator() {
        return generator;
    }

    public List<BlockPopulator> getPopulators() {
        return populators;
    }

    public Block getHighestBlockAt(int x, int z) {
        return getBlockAt(x, getHighestBlockYAt(x, z), z);
    }

    public Block getHighestBlockAt(Location location) {
        return getHighestBlockAt(location.getBlockX(), location.getBlockZ());
    }

    public Biome getBiome(int x, int z) {
        return CraftBlock.biomeBaseToBiome(this.world.func_72807_a(x, z));
    }

    public void setBiome(int x, int z, Biome bio) {
        net.minecraft.world.biome.BiomeGenBase bb = CraftBlock.biomeToBiomeBase(bio);
        if (this.world.func_72899_e(x, 0, z)) {
            net.minecraft.world.chunk.Chunk chunk = this.world.func_72938_d(x, z);

            if (chunk != null) {
                byte[] biomevals = chunk.func_76605_m();
                biomevals[((z & 0xF) << 4) | (x & 0xF)] = (byte)bb.field_76756_M;
            }
        }
    }

    public double getTemperature(int x, int z) {
        return this.world.func_72807_a(x, z).field_76750_F;
    }

    public double getHumidity(int x, int z) {
        return this.world.func_72807_a(x, z).field_76751_G;
    }

    public List<Entity> getEntities() {
        List<Entity> list = new ArrayList<Entity>();

        for (Object o : world.field_72996_f) {
            if (o instanceof net.minecraft.entity.Entity) {
                net.minecraft.entity.Entity mcEnt = (net.minecraft.entity.Entity) o;
                Entity bukkitEntity = mcEnt.getBukkitEntity();

                // Assuming that bukkitEntity isn't null
                if (bukkitEntity != null) {
                    list.add(bukkitEntity);
                }
            }
        }

        return list;
    }

    public List<LivingEntity> getLivingEntities() {
        List<LivingEntity> list = new ArrayList<LivingEntity>();

        for (Object o : world.field_72996_f) {
            if (o instanceof net.minecraft.entity.Entity) {
                net.minecraft.entity.Entity mcEnt = (net.minecraft.entity.Entity) o;
                Entity bukkitEntity = mcEnt.getBukkitEntity();

                // Assuming that bukkitEntity isn't null
                if (bukkitEntity != null && bukkitEntity instanceof LivingEntity) {
                    list.add((LivingEntity) bukkitEntity);
                }
            }
        }

        return list;
    }

    @SuppressWarnings("unchecked")
    @Deprecated
    public <T extends Entity> Collection<T> getEntitiesByClass(Class<T>... classes) {
        return (Collection<T>)getEntitiesByClasses(classes);
    }

    @SuppressWarnings("unchecked")
    public <T extends Entity> Collection<T> getEntitiesByClass(Class<T> clazz) {
        Collection<T> list = new ArrayList<T>();

        for (Object entity: world.field_72996_f) {
            if (entity instanceof net.minecraft.entity.Entity) {
                Entity bukkitEntity = ((net.minecraft.entity.Entity) entity).getBukkitEntity();

                if (bukkitEntity == null) {
                    continue;
                }

                Class<?> bukkitClass = bukkitEntity.getClass();

                if (clazz.isAssignableFrom(bukkitClass)) {
                    list.add((T) bukkitEntity);
                }
            }
        }

        return list;
    }

    public Collection<Entity> getEntitiesByClasses(Class<?>... classes) {
        Collection<Entity> list = new ArrayList<Entity>();

        for (Object entity: world.field_72996_f) {
            if (entity instanceof net.minecraft.entity.Entity) {
                Entity bukkitEntity = ((net.minecraft.entity.Entity) entity).getBukkitEntity();

                if (bukkitEntity == null) {
                    continue;
                }

                Class<?> bukkitClass = bukkitEntity.getClass();

                for (Class<?> clazz : classes) {
                    if (clazz.isAssignableFrom(bukkitClass)) {
                        list.add(bukkitEntity);
                        break;
                    }
                }
            }
        }

        return list;
    }

    public List<Player> getPlayers() {
        List<Player> list = new ArrayList<Player>();

        for (Object o : world.field_72996_f) {
            if (o instanceof net.minecraft.entity.Entity) {
                net.minecraft.entity.Entity mcEnt = (net.minecraft.entity.Entity) o;
                Entity bukkitEntity = mcEnt.getBukkitEntity();

                if ((bukkitEntity != null) && (bukkitEntity instanceof Player)) {
                    list.add((Player) bukkitEntity);
                }
            }
        }

        return list;
    }

    public void save() {
        try {
            boolean oldSave = world.field_73058_d;

            world.field_73058_d = false;
            world.func_73044_a(true, null);

            world.field_73058_d = oldSave;
        } catch (net.minecraft.world.MinecraftException ex) {
            ex.printStackTrace();
        }
    }

    public boolean isAutoSave() {
        return !world.field_73058_d;
    }

    public void setAutoSave(boolean value) {
        world.field_73058_d = !value;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.getHandle().field_73013_u = difficulty.getValue();
    }

    public Difficulty getDifficulty() {
        return Difficulty.getByValue(this.getHandle().field_73013_u);
    }

    public BlockMetadataStore getBlockMetadata() {
        return blockMetadata;
    }

    public boolean hasStorm() {
        return world.field_72986_A.func_76059_o();
    }

    public void setStorm(boolean hasStorm) {
        CraftServer server = world.getServer();

        WeatherChangeEvent weather = new WeatherChangeEvent((org.bukkit.World) this, hasStorm);
        server.getPluginManager().callEvent(weather);
        if (!weather.isCancelled()) {
            world.field_72986_A.func_76084_b(hasStorm);

            // These numbers are from Minecraft
            if (hasStorm) {
                setWeatherDuration(rand.nextInt(12000) + 12000);
            } else {
                setWeatherDuration(rand.nextInt(168000) + 12000);
            }
        }
    }

    public int getWeatherDuration() {
        return world.field_72986_A.func_76083_p();
    }

    public void setWeatherDuration(int duration) {
        world.field_72986_A.func_76080_g(duration);
    }

    public boolean isThundering() {
        return hasStorm() && world.field_72986_A.func_76061_m();
    }

    public void setThundering(boolean thundering) {
        if (thundering && !hasStorm()) setStorm(true);
        CraftServer server = world.getServer();

        ThunderChangeEvent thunder = new ThunderChangeEvent((org.bukkit.World) this, thundering);
        server.getPluginManager().callEvent(thunder);
        if (!thunder.isCancelled()) {
            world.field_72986_A.func_76069_a(thundering);

            // These numbers are from Minecraft
            if (thundering) {
                setThunderDuration(rand.nextInt(12000) + 3600);
            } else {
                setThunderDuration(rand.nextInt(168000) + 12000);
            }
        }
    }

    public int getThunderDuration() {
        return world.field_72986_A.func_76071_n();
    }

    public void setThunderDuration(int duration) {
        world.field_72986_A.func_76090_f(duration);
    }

    public long getSeed() {
        return world.field_72986_A.func_76063_b();
    }

    public boolean getPVP() {
        return world.pvpMode;
    }

    public void setPVP(boolean pvp) {
        world.pvpMode = pvp;
    }

    public void playEffect(Player player, Effect effect, int data) {
        playEffect(player.getLocation(), effect, data, 0);
    }

    public void playEffect(Location location, Effect effect, int data) {
        playEffect(location, effect, data, 64);
    }

    public <T> void playEffect(Location loc, Effect effect, T data) {
        playEffect(loc, effect, data, 64);
    }

    public <T> void playEffect(Location loc, Effect effect, T data, int radius) {
        if (data != null) {
            Validate.isTrue(data.getClass().equals(effect.getData()), "Wrong kind of data for this effect!");
        } else {
            Validate.isTrue(effect.getData() == null, "Wrong kind of data for this effect!");
        }

        int datavalue = data == null ? 0 : CraftEffect.getDataValue(effect, data);
        playEffect(loc, effect, datavalue, radius);
    }

    public void playEffect(Location location, Effect effect, int data, int radius) {
        Validate.notNull(location, "Location cannot be null");
        Validate.notNull(effect, "Effect cannot be null");
        Validate.notNull(location.getWorld(), "World cannot be null");
        int packetData = effect.getId();
        net.minecraft.network.packet.Packet61DoorChange packet = new net.minecraft.network.packet.Packet61DoorChange(packetData, location.getBlockX(), location.getBlockY(), location.getBlockZ(), data, false);
        int distance;
        radius *= radius;

        for (Player player : getPlayers()) {
            if (((CraftPlayer) player).getHandle().field_71135_a == null) continue;
            if (!location.getWorld().equals(player.getWorld())) continue;

            distance = (int) player.getLocation().distanceSquared(location);
            if (distance <= radius) {
                ((CraftPlayer) player).getHandle().field_71135_a.func_72567_b(packet);
            }
        }
    }

    public <T extends Entity> T spawn(Location location, Class<T> clazz) throws IllegalArgumentException {
        return spawn(location, clazz, SpawnReason.CUSTOM);
    }

    public FallingBlock spawnFallingBlock(Location location, org.bukkit.Material material, byte data) throws IllegalArgumentException {
        Validate.notNull(location, "Location cannot be null");
        Validate.notNull(material, "Material cannot be null");
        Validate.isTrue(material.isBlock(), "Material must be a block");

        double x = location.getBlockX() + 0.5;
        double y = location.getBlockY() + 0.5;
        double z = location.getBlockZ() + 0.5;

        net.minecraft.entity.item.EntityFallingSand entity = new net.minecraft.entity.item.EntityFallingSand(world, x, y, z, material.getId(), data);
        entity.field_70286_c = 1; // ticksLived

        world.addEntity(entity, SpawnReason.CUSTOM);
        return (FallingBlock) entity.getBukkitEntity();
    }

    public FallingBlock spawnFallingBlock(Location location, int blockId, byte blockData) throws IllegalArgumentException {
        return spawnFallingBlock(location, org.bukkit.Material.getMaterial(blockId), blockData);
    }

    @SuppressWarnings("unchecked")
    public <T extends Entity> T spawn(Location location, Class<T> clazz, SpawnReason reason) throws IllegalArgumentException {
        if (location == null || clazz == null) {
            throw new IllegalArgumentException("Location or entity class cannot be null");
        }

        net.minecraft.entity.Entity entity = null;

        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        float pitch = location.getPitch();
        float yaw = location.getYaw();

        // order is important for some of these
        if (Boat.class.isAssignableFrom(clazz)) {
            entity = new net.minecraft.entity.item.EntityBoat(world, x, y, z);
        } else if (FallingBlock.class.isAssignableFrom(clazz)) {
            x = location.getBlockX();
            y = location.getBlockY();
            z = location.getBlockZ();
            int type = world.func_72798_a((int) x, (int) y, (int) z);
            int data = world.func_72805_g((int) x, (int) y, (int) z);

            entity = new net.minecraft.entity.item.EntityFallingSand(world, x + 0.5, y + 0.5, z + 0.5, type, data);
        } else if (Projectile.class.isAssignableFrom(clazz)) {
            if (Snowball.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.projectile.EntitySnowball(world, x, y, z);
            } else if (Egg.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.projectile.EntityEgg(world, x, y, z);
            } else if (Arrow.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.projectile.EntityArrow(world);
                entity.func_70012_b(x, y, z, 0, 0);
            } else if (ThrownExpBottle.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.item.EntityExpBottle(world);
                entity.func_70012_b(x, y, z, 0, 0);
            } else if (Fireball.class.isAssignableFrom(clazz)) {
                if (SmallFireball.class.isAssignableFrom(clazz)) {
                    entity = new net.minecraft.entity.projectile.EntitySmallFireball(world);
                } else if (WitherSkull.class.isAssignableFrom(clazz)) {
                    entity = new net.minecraft.entity.projectile.EntityWitherSkull(world);
                } else {
                    entity = new net.minecraft.entity.projectile.EntityLargeFireball(world);
                }
                ((net.minecraft.entity.projectile.EntityFireball) entity).func_70012_b(x, y, z, yaw, pitch);
                Vector direction = location.getDirection().multiply(10);
                ((net.minecraft.entity.projectile.EntityFireball) entity).setDirection(direction.getX(), direction.getY(), direction.getZ());
            }
        } else if (Minecart.class.isAssignableFrom(clazz)) {
            if (PoweredMinecart.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.item.EntityMinecartFurnace(world, x, y, z);
            } else if (StorageMinecart.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.item.EntityMinecartChest(world, x, y, z);
            } else if (MinecartTNT.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.item.EntityMinecartTNT(world, x, y, z);
            } else if (MinecartHopper.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.item.EntityMinecartHopper(world, x, y, z);
            } else if (MinecartMobSpawner.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.ai.EntityMinecartMobSpawner(world, x, y, z);
            } else {
                entity = new net.minecraft.entity.item.EntityMinecartEmpty(world, x, y, z);
            }
        } else if (EnderSignal.class.isAssignableFrom(clazz)) {
            entity = new net.minecraft.entity.item.EntityEnderEye(world, x, y, z);
        } else if (EnderCrystal.class.isAssignableFrom(clazz)) {
            entity = new net.minecraft.entity.item.EntityEnderCrystal(world);
            entity.func_70012_b(x, y, z, 0, 0);
        } else if (LivingEntity.class.isAssignableFrom(clazz)) {
            if (Chicken.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.passive.EntityChicken(world);
            } else if (Cow.class.isAssignableFrom(clazz)) {
                if (MushroomCow.class.isAssignableFrom(clazz)) {
                    entity = new net.minecraft.entity.passive.EntityMooshroom(world);
                } else {
                    entity = new net.minecraft.entity.passive.EntityCow(world);
                }
            } else if (Golem.class.isAssignableFrom(clazz)) {
                if (Snowman.class.isAssignableFrom(clazz)) {
                    entity = new net.minecraft.entity.monster.EntitySnowman(world);
                } else if (IronGolem.class.isAssignableFrom(clazz)) {
                    entity = new net.minecraft.entity.monster.EntityIronGolem(world);
                }
            } else if (Creeper.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.monster.EntityCreeper(world);
            } else if (Ghast.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.monster.EntityGhast(world);
            } else if (Pig.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.passive.EntityPig(world);
            } else if (Player.class.isAssignableFrom(clazz)) {
                // need a net server handler for this one
            } else if (Sheep.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.passive.EntitySheep(world);
            } else if (Skeleton.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.monster.EntitySkeleton(world);
            } else if (Slime.class.isAssignableFrom(clazz)) {
                if (MagmaCube.class.isAssignableFrom(clazz)) {
                    entity = new net.minecraft.entity.monster.EntityMagmaCube(world);
                } else {
                    entity = new net.minecraft.entity.monster.EntitySlime(world);
                }
            } else if (Spider.class.isAssignableFrom(clazz)) {
                if (CaveSpider.class.isAssignableFrom(clazz)) {
                    entity = new net.minecraft.entity.monster.EntityCaveSpider(world);
                } else {
                    entity = new net.minecraft.entity.monster.EntitySpider(world);
                }
            } else if (Squid.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.passive.EntitySquid(world);
            } else if (Tameable.class.isAssignableFrom(clazz)) {
                if (Wolf.class.isAssignableFrom(clazz)) {
                    entity = new net.minecraft.entity.passive.EntityWolf(world);
                } else if (Ocelot.class.isAssignableFrom(clazz)) {
                    entity = new net.minecraft.entity.passive.EntityOcelot(world);
                }
            } else if (PigZombie.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.monster.EntityPigZombie(world);
            } else if (Zombie.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.monster.EntityZombie(world);
            } else if (Giant.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.monster.EntityGiantZombie(world);
            } else if (Silverfish.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.monster.EntitySilverfish(world);
            } else if (Enderman.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.monster.EntityEnderman(world);
            } else if (Blaze.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.monster.EntityBlaze(world);
            } else if (Villager.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.passive.EntityVillager(world);
            } else if (Witch.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.monster.EntityWitch(world);
            } else if (Wither.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.boss.EntityWither(world);
            } else if (ComplexLivingEntity.class.isAssignableFrom(clazz)) {
                if (EnderDragon.class.isAssignableFrom(clazz)) {
                    entity = new net.minecraft.entity.boss.EntityDragon(world);
                }
            } else if (Ambient.class.isAssignableFrom(clazz)) {
                if (Bat.class.isAssignableFrom(clazz)) {
                    entity = new net.minecraft.entity.passive.EntityBat(world);
                }
            }

            if (entity != null) {
                entity.func_70080_a(x, y, z, pitch, yaw);
            }
        } else if (Hanging.class.isAssignableFrom(clazz)) {
            Block block = getBlockAt(location);
            BlockFace face = BlockFace.SELF;
            if (block.getRelative(BlockFace.EAST).getTypeId() == 0) {
                face = BlockFace.EAST;
            } else if (block.getRelative(BlockFace.NORTH).getTypeId() == 0) {
                face = BlockFace.NORTH;
            } else if (block.getRelative(BlockFace.WEST).getTypeId() == 0) {
                face = BlockFace.WEST;
            } else if (block.getRelative(BlockFace.SOUTH).getTypeId() == 0) {
                face = BlockFace.SOUTH;
            }
            int dir;
            switch (face) {
            case SOUTH:
            default:
                dir = 0;
                break;
            case WEST:
                dir = 1;
                break;
            case NORTH:
                dir = 2;
                break;
            case EAST:
                dir = 3;
                break;
            }

            if (Painting.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.item.EntityPainting(world, (int) x, (int) y, (int) z, dir);
            } else if (ItemFrame.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.item.EntityItemFrame(world, (int) x, (int) y, (int) z, dir);
            }

            if (entity != null && !((net.minecraft.entity.EntityHanging) entity).func_70518_d()) {
                entity = null;
            }
        } else if (TNTPrimed.class.isAssignableFrom(clazz)) {
            entity = new net.minecraft.entity.item.EntityTNTPrimed(world, x, y, z, null);
        } else if (ExperienceOrb.class.isAssignableFrom(clazz)) {
            entity = new net.minecraft.entity.item.EntityXPOrb(world, x, y, z, 0);
        } else if (Weather.class.isAssignableFrom(clazz)) {
            // not sure what this can do
            entity = new net.minecraft.entity.effect.EntityLightningBolt(world, x, y, z);
        } else if (LightningStrike.class.isAssignableFrom(clazz)) {
            // what is this, I don't even
        } else if (Fish.class.isAssignableFrom(clazz)) {
            // this is not a fish, it's a bobber, and it's probably useless
            entity = new net.minecraft.entity.projectile.EntityFishHook(world);
            entity.func_70080_a(x, y, z, pitch, yaw);
        } else if (Firework.class.isAssignableFrom(clazz)) {
            entity = new net.minecraft.entity.item.EntityFireworkRocket(world, x, y, z, null);
        }

        if (entity != null) {
            world.addEntity(entity, reason);
            return (T) entity.getBukkitEntity();
        }

        throw new IllegalArgumentException("Cannot spawn an entity for " + clazz.getName());
    }

    public ChunkSnapshot getEmptyChunkSnapshot(int x, int z, boolean includeBiome, boolean includeBiomeTempRain) {
        return CraftChunk.getEmptyChunkSnapshot(x, z, this, includeBiome, includeBiomeTempRain);
    }

    public void setSpawnFlags(boolean allowMonsters, boolean allowAnimals) {
        world.func_72891_a(allowMonsters, allowAnimals);
    }

    public boolean getAllowAnimals() {
        return world.field_72992_H;
    }

    public boolean getAllowMonsters() {
        return world.field_72985_G;
    }

    public int getMaxHeight() {
        return world.func_72800_K();
    }

    public int getSeaLevel() {
        return 64;
    }

    public boolean getKeepSpawnInMemory() {
        return world.keepSpawnInMemory;
    }

    public void setKeepSpawnInMemory(boolean keepLoaded) {
        world.keepSpawnInMemory = keepLoaded;
        // Grab the worlds spawn chunk
        net.minecraft.util.ChunkCoordinates chunkcoordinates = this.world.func_72861_E();
        int chunkCoordX = chunkcoordinates.field_71574_a >> 4;
        int chunkCoordZ = chunkcoordinates.field_71573_c >> 4;
        // Cycle through the 25x25 Chunks around it to load/unload the chunks.
        for (int x = -12; x <= 12; x++) {
            for (int z = -12; z <= 12; z++) {
                if (keepLoaded) {
                    loadChunk(chunkCoordX + x, chunkCoordZ + z);
                } else {
                    if (isChunkLoaded(chunkCoordX + x, chunkCoordZ + z)) {
                        if (this.getHandle().func_72964_e(chunkCoordX + x, chunkCoordZ + z) instanceof net.minecraft.world.chunk.EmptyChunk) {
                            unloadChunk(chunkCoordX + x, chunkCoordZ + z, false);
                        } else {
                            unloadChunk(chunkCoordX + x, chunkCoordZ + z);
                        }
                    }
                }
            }
        }
    }

    @Override
    public int hashCode() {
        return getUID().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final CraftWorld other = (CraftWorld) obj;

        return this.getUID() == other.getUID();
    }

    public File getWorldFolder() {
        return ((net.minecraft.world.storage.SaveHandler) world.func_72860_G()).func_75765_b();
    }

    public void explodeBlock(Block block, float yield) {
        // First of all, don't explode fire
        if (block.getType().equals(org.bukkit.Material.AIR) || block.getType().equals(org.bukkit.Material.FIRE)) {
            return;
        }
        int blockId = block.getTypeId();
        int blockX = block.getX();
        int blockY = block.getY();
        int blockZ = block.getZ();
        // following code is lifted from Explosion.a(boolean), and modified
        net.minecraft.block.Block.field_71973_m[blockId].func_71914_a(this.world, blockX, blockY, blockZ, block.getData(), yield, 0);
        block.setType(org.bukkit.Material.AIR);
        // not sure what this does, seems to have something to do with the 'base' material of a block.
        // For example, WOODEN_STAIRS does something with WOOD in this method
        net.minecraft.block.Block.field_71973_m[blockId].func_71867_k(this.world, blockX, blockY, blockZ, null);
    }

    public void sendPluginMessage(Plugin source, String channel, byte[] message) {
        StandardMessenger.validatePluginMessage(server.getMessenger(), source, channel, message);

        for (Player player : getPlayers()) {
            player.sendPluginMessage(source, channel, message);
        }
    }

    public Set<String> getListeningPluginChannels() {
        Set<String> result = new HashSet<String>();

        for (Player player : getPlayers()) {
            result.addAll(player.getListeningPluginChannels());
        }

        return result;
    }

    public org.bukkit.WorldType getWorldType() {
        return org.bukkit.WorldType.getByName(world.func_72912_H().func_76067_t().func_77127_a());
    }

    public boolean canGenerateStructures() {
        return world.func_72912_H().func_76089_r();
    }

    public long getTicksPerAnimalSpawns() {
        return world.ticksPerAnimalSpawns;
    }

    public void setTicksPerAnimalSpawns(int ticksPerAnimalSpawns) {
        world.ticksPerAnimalSpawns = ticksPerAnimalSpawns;
    }

    public long getTicksPerMonsterSpawns() {
        return world.ticksPerMonsterSpawns;
    }

    public void setTicksPerMonsterSpawns(int ticksPerMonsterSpawns) {
        world.ticksPerMonsterSpawns = ticksPerMonsterSpawns;
    }

    public void setMetadata(String metadataKey, MetadataValue newMetadataValue) {
        server.getWorldMetadata().setMetadata(this, metadataKey, newMetadataValue);
    }

    public List<MetadataValue> getMetadata(String metadataKey) {
        return server.getWorldMetadata().getMetadata(this, metadataKey);
    }

    public boolean hasMetadata(String metadataKey) {
        return server.getWorldMetadata().hasMetadata(this, metadataKey);
    }

    public void removeMetadata(String metadataKey, Plugin owningPlugin) {
        server.getWorldMetadata().removeMetadata(this, metadataKey, owningPlugin);
    }

    public int getMonsterSpawnLimit() {
        if (monsterSpawn < 0) {
            return server.getMonsterSpawnLimit();
        }

        return monsterSpawn;
    }

    public void setMonsterSpawnLimit(int limit) {
        monsterSpawn = limit;
    }

    public int getAnimalSpawnLimit() {
        if (animalSpawn < 0) {
            return server.getAnimalSpawnLimit();
        }

        return animalSpawn;
    }

    public void setAnimalSpawnLimit(int limit) {
        animalSpawn = limit;
    }

    public int getWaterAnimalSpawnLimit() {
        if (waterAnimalSpawn < 0) {
            return server.getWaterAnimalSpawnLimit();
        }

        return waterAnimalSpawn;
    }

    public void setWaterAnimalSpawnLimit(int limit) {
        waterAnimalSpawn = limit;
    }

    public int getAmbientSpawnLimit() {
        if (ambientSpawn < 0) {
            return server.getAmbientSpawnLimit();
        }

        return ambientSpawn;
    }

    public void setAmbientSpawnLimit(int limit) {
        ambientSpawn = limit;
    }


    public void playSound(Location loc, Sound sound, float volume, float pitch) {
        if (loc == null || sound == null) return;

        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();

        getHandle().func_72908_a(x, y, z, CraftSound.getSound(sound), volume, pitch);
    }

    public String getGameRuleValue(String rule) {
        return getHandle().func_82736_K().func_82767_a(rule);
    }

    public boolean setGameRuleValue(String rule, String value) {
        // No null values allowed
        if (rule == null || value == null) return false;

        if (!isGameRule(rule)) return false;

        getHandle().func_82736_K().func_82764_b(rule, value);
        return true;
    }

    public String[] getGameRules() {
        return getHandle().func_82736_K().func_82763_b();
    }

    public boolean isGameRule(String rule) {
        return getHandle().func_82736_K().func_82765_e(rule);
    }

    public void processChunkGC() {
        chunkGCTickCount++;

        if (chunkLoadCount >= server.chunkGCLoadThresh && server.chunkGCLoadThresh > 0) {
            chunkLoadCount = 0;
        } else if (chunkGCTickCount >= server.chunkGCPeriod && server.chunkGCPeriod > 0) {
            chunkGCTickCount = 0;
        } else {
            return;
        }

        net.minecraft.world.gen.ChunkProviderServer cps = world.field_73059_b;
        Iterator<net.minecraft.world.chunk.Chunk> iter = cps.field_73244_f.values().iterator();
        while (iter.hasNext()) {
            net.minecraft.world.chunk.Chunk chunk = iter.next();
            // If in use, skip it
            if (isChunkInUse(chunk.field_76635_g, chunk.field_76647_h)) {
                continue;
            }

            // Already unloading?
            if (cps.field_73248_b.contains(chunk.field_76635_g, chunk.field_76647_h)) {
                continue;
            }

            // Add unload request
            cps.func_73241_b(chunk.field_76635_g,  chunk.field_76647_h);
        }
    }
}
