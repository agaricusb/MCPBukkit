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

    private final net.minecraft.world.WorldServer/*was:WorldServer*/ world;
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

    public CraftWorld(net.minecraft.world.WorldServer/*was:WorldServer*/ world, ChunkGenerator gen, Environment env) {
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
        return world.func_72798_a/*was:getTypeId*/(x, y, z);
    }

    public int getHighestBlockYAt(int x, int z) {
        if (!isChunkLoaded(x >> 4, z >> 4)) {
            loadChunk(x >> 4, z >> 4);
        }

        return world.func_72976_f/*was:getHighestBlockYAt*/(x, z);
    }

    public Location getSpawnLocation() {
        net.minecraft.util.ChunkCoordinates/*was:ChunkCoordinates*/ spawn = world.func_72861_E/*was:getSpawn*/();
        return new Location(this, spawn.field_71574_a/*was:x*/, spawn.field_71572_b/*was:y*/, spawn.field_71573_c/*was:z*/);
    }

    public boolean setSpawnLocation(int x, int y, int z) {
        try {
            Location previousLocation = getSpawnLocation();
            world.field_72986_A/*was:worldData*/.func_76081_a/*was:setSpawn*/(x, y, z);

            // Notify anyone who's listening.
            SpawnChangeEvent event = new SpawnChangeEvent(this, previousLocation);
            server.getPluginManager().callEvent(event);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Chunk getChunkAt(int x, int z) {
        return this.world.field_73059_b/*was:chunkProviderServer*/.func_73158_c/*was:getChunkAt*/(x, z).bukkitChunk;
    }

    public Chunk getChunkAt(Block block) {
        return getChunkAt(block.getX() >> 4, block.getZ() >> 4);
    }

    public boolean isChunkLoaded(int x, int z) {
        return world.field_73059_b/*was:chunkProviderServer*/.func_73149_a/*was:isChunkLoaded*/(x, z);
    }

    public Chunk[] getLoadedChunks() {
        Object[] chunks = world.field_73059_b/*was:chunkProviderServer*/.field_73244_f/*was:chunks*/.values().toArray();
        org.bukkit.Chunk[] craftChunks = new CraftChunk[chunks.length];

        for (int i = 0; i < chunks.length; i++) {
            net.minecraft.world.chunk.Chunk/*was:net.minecraft.server.Chunk*/ chunk = (net.minecraft.world.chunk.Chunk/*was:net.minecraft.server.Chunk*/) chunks[i];
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

        world.field_73059_b/*was:chunkProviderServer*/.func_73241_b/*was:queueUnload*/(x, z);

        return true;
    }

    public boolean unloadChunk(int x, int z, boolean save, boolean safe) {
        if (safe && isChunkInUse(x, z)) {
            return false;
        }

        net.minecraft.world.chunk.Chunk/*was:net.minecraft.server.Chunk*/ chunk = world.field_73059_b/*was:chunkProviderServer*/.func_73154_d/*was:getOrCreateChunk*/(x, z);
        if (chunk.mustSave) {   // If chunk had previously been queued to save, must do save to avoid loss of that data
            save = true;
        }

        chunk.func_76623_d/*was:removeEntities*/(); // Always remove entities - even if discarding, need to get them out of world table

        if (save && !(chunk instanceof net.minecraft.world.chunk.EmptyChunk/*was:EmptyChunk*/)) {
            world.field_73059_b/*was:chunkProviderServer*/.func_73242_b/*was:saveChunk*/(chunk);
            world.field_73059_b/*was:chunkProviderServer*/.func_73243_a/*was:saveChunkNOP*/(chunk);
        }

        world.field_73059_b/*was:chunkProviderServer*/.field_73248_b/*was:unloadQueue*/.remove(x, z);
        world.field_73059_b/*was:chunkProviderServer*/.field_73244_f/*was:chunks*/.remove(LongHash.toLong(x, z));

        return true;
    }

    public boolean regenerateChunk(int x, int z) {
        unloadChunk(x, z, false, false);

        world.field_73059_b/*was:chunkProviderServer*/.field_73248_b/*was:unloadQueue*/.remove(x, z);

        net.minecraft.world.chunk.Chunk/*was:net.minecraft.server.Chunk*/ chunk = null;

        if (world.field_73059_b/*was:chunkProviderServer*/.field_73246_d/*was:chunkProvider*/ == null) {
            chunk = world.field_73059_b/*was:chunkProviderServer*/.field_73249_c/*was:emptyChunk*/;
        } else {
            chunk = world.field_73059_b/*was:chunkProviderServer*/.field_73246_d/*was:chunkProvider*/.func_73154_d/*was:getOrCreateChunk*/(x, z);
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
            world.func_72845_h/*was:notify*/(px + (idx / height), ((idx % height) * 16), pz);
        }
        world.func_72845_h/*was:notify*/(px + 15, (height * 16) - 1, pz + 15);

        return true;
    }

    public boolean isChunkInUse(int x, int z) {
        return world.func_73040_p/*was:getPlayerChunkMap*/().isChunkInUse(x, z);
    }

    public boolean loadChunk(int x, int z, boolean generate) {
        chunkLoadCount++;
        if (generate) {
            // Use the default variant of loadChunk when generate == true.
            return world.field_73059_b/*was:chunkProviderServer*/.func_73158_c/*was:getChunkAt*/(x, z) != null;
        }

        world.field_73059_b/*was:chunkProviderServer*/.field_73248_b/*was:unloadQueue*/.remove(x, z);
        net.minecraft.world.chunk.Chunk/*was:net.minecraft.server.Chunk*/ chunk = (net.minecraft.world.chunk.Chunk/*was:net.minecraft.server.Chunk*/) world.field_73059_b/*was:chunkProviderServer*/.field_73244_f/*was:chunks*/.get(LongHash.toLong(x, z));

        if (chunk == null) {
            chunk = world.field_73059_b/*was:chunkProviderServer*/.func_73239_e/*was:loadChunk*/(x, z);

            chunkLoadPostProcess(chunk, x, z);
        }
        return chunk != null;
    }

    @SuppressWarnings("unchecked")
    private void chunkLoadPostProcess(net.minecraft.world.chunk.Chunk/*was:net.minecraft.server.Chunk*/ chunk, int x, int z) {
        if (chunk != null) {
            world.field_73059_b/*was:chunkProviderServer*/.field_73244_f/*was:chunks*/.put(LongHash.toLong(x, z), chunk);

            chunk.func_76631_c/*was:addEntities*/();

            if (!chunk.field_76646_k/*was:done*/ && world.field_73059_b/*was:chunkProviderServer*/.func_73149_a/*was:isChunkLoaded*/(x + 1, z + 1) && world.field_73059_b/*was:chunkProviderServer*/.func_73149_a/*was:isChunkLoaded*/(x, z + 1) && world.field_73059_b/*was:chunkProviderServer*/.func_73149_a/*was:isChunkLoaded*/(x + 1, z)) {
                world.field_73059_b/*was:chunkProviderServer*/.func_73153_a/*was:getChunkAt*/(world.field_73059_b/*was:chunkProviderServer*/, x, z);
            }

            if (world.field_73059_b/*was:chunkProviderServer*/.func_73149_a/*was:isChunkLoaded*/(x - 1, z) && !world.field_73059_b/*was:chunkProviderServer*/.func_73154_d/*was:getOrCreateChunk*/(x - 1, z).field_76646_k/*was:done*/ && world.field_73059_b/*was:chunkProviderServer*/.func_73149_a/*was:isChunkLoaded*/(x - 1, z + 1) && world.field_73059_b/*was:chunkProviderServer*/.func_73149_a/*was:isChunkLoaded*/(x, z + 1) && world.field_73059_b/*was:chunkProviderServer*/.func_73149_a/*was:isChunkLoaded*/(x - 1, z)) {
                world.field_73059_b/*was:chunkProviderServer*/.func_73153_a/*was:getChunkAt*/(world.field_73059_b/*was:chunkProviderServer*/, x - 1, z);
            }

            if (world.field_73059_b/*was:chunkProviderServer*/.func_73149_a/*was:isChunkLoaded*/(x, z - 1) && !world.field_73059_b/*was:chunkProviderServer*/.func_73154_d/*was:getOrCreateChunk*/(x, z - 1).field_76646_k/*was:done*/ && world.field_73059_b/*was:chunkProviderServer*/.func_73149_a/*was:isChunkLoaded*/(x + 1, z - 1) && world.field_73059_b/*was:chunkProviderServer*/.func_73149_a/*was:isChunkLoaded*/(x, z - 1) && world.field_73059_b/*was:chunkProviderServer*/.func_73149_a/*was:isChunkLoaded*/(x + 1, z)) {
                world.field_73059_b/*was:chunkProviderServer*/.func_73153_a/*was:getChunkAt*/(world.field_73059_b/*was:chunkProviderServer*/, x, z - 1);
            }

            if (world.field_73059_b/*was:chunkProviderServer*/.func_73149_a/*was:isChunkLoaded*/(x - 1, z - 1) && !world.field_73059_b/*was:chunkProviderServer*/.func_73154_d/*was:getOrCreateChunk*/(x - 1, z - 1).field_76646_k/*was:done*/ && world.field_73059_b/*was:chunkProviderServer*/.func_73149_a/*was:isChunkLoaded*/(x - 1, z - 1) && world.field_73059_b/*was:chunkProviderServer*/.func_73149_a/*was:isChunkLoaded*/(x, z - 1) && world.field_73059_b/*was:chunkProviderServer*/.func_73149_a/*was:isChunkLoaded*/(x - 1, z)) {
                world.field_73059_b/*was:chunkProviderServer*/.func_73153_a/*was:getChunkAt*/(world.field_73059_b/*was:chunkProviderServer*/, x - 1, z - 1);
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

    public net.minecraft.world.WorldServer/*was:WorldServer*/ getHandle() {
        return world;
    }

    public org.bukkit.entity.Item dropItem(Location loc, ItemStack item) {
        Validate.notNull(item, "Cannot drop a Null item.");
        Validate.isTrue(item.getTypeId() != 0, "Cannot drop AIR.");
        net.minecraft.entity.item.EntityItem/*was:EntityItem*/ entity = new net.minecraft.entity.item.EntityItem/*was:EntityItem*/(world, loc.getX(), loc.getY(), loc.getZ(), CraftItemStack.asNMSCopy(item));
        entity.field_70293_c/*was:pickupDelay*/ = 10;
        world.func_72838_d/*was:addEntity*/(entity);
        // TODO this is inconsistent with how Entity.getBukkitEntity() works.
        // However, this entity is not at the moment backed by a server entity class so it may be left.
        return new CraftItem(world.getServer(), entity);
    }

    public org.bukkit.entity.Item dropItemNaturally(Location loc, ItemStack item) {
        double xs = world.field_73012_v/*was:random*/.nextFloat() * 0.7F + (1.0F - 0.7F) * 0.5D;
        double ys = world.field_73012_v/*was:random*/.nextFloat() * 0.7F + (1.0F - 0.7F) * 0.5D;
        double zs = world.field_73012_v/*was:random*/.nextFloat() * 0.7F + (1.0F - 0.7F) * 0.5D;
        loc = loc.clone();
        loc.setX(loc.getX() + xs);
        loc.setY(loc.getY() + ys);
        loc.setZ(loc.getZ() + zs);
        return dropItem(loc, item);
    }

    public Arrow spawnArrow(Location loc, Vector velocity, float speed, float spread) {
        Validate.notNull(loc, "Can not spawn arrow with a null location");
        Validate.notNull(velocity, "Can not spawn arrow with a null velocity");

        net.minecraft.entity.projectile.EntityArrow/*was:EntityArrow*/ arrow = new net.minecraft.entity.projectile.EntityArrow/*was:EntityArrow*/(world);
        arrow.func_70012_b/*was:setPositionRotation*/(loc.getX(), loc.getY(), loc.getZ(), loc.getPitch(), loc.getYaw());
        arrow.func_70186_c/*was:shoot*/(velocity.getX(), velocity.getY(), velocity.getZ(), speed, spread);
        world.func_72838_d/*was:addEntity*/(arrow);
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
        net.minecraft.entity.effect.EntityLightningBolt/*was:EntityLightning*/ lightning = new net.minecraft.entity.effect.EntityLightningBolt/*was:EntityLightning*/(world, loc.getX(), loc.getY(), loc.getZ());
        world.func_72942_c/*was:strikeLightning*/(lightning);
        return new CraftLightningStrike(server, lightning);
    }

    public LightningStrike strikeLightningEffect(Location loc) {
        net.minecraft.entity.effect.EntityLightningBolt/*was:EntityLightning*/ lightning = new net.minecraft.entity.effect.EntityLightningBolt/*was:EntityLightning*/(world, loc.getX(), loc.getY(), loc.getZ(), true);
        world.func_72942_c/*was:strikeLightning*/(lightning);
        return new CraftLightningStrike(server, lightning);
    }

    public boolean generateTree(Location loc, TreeType type) {
        return generateTree(loc, type, world);
    }

    public boolean generateTree(Location loc, TreeType type, BlockChangeDelegate delegate) {
        net.minecraft.block.BlockSapling.TreeGenerator/*was:BlockSapling.TreeGenerator*/ gen;
        switch (type) {
        case BIG_TREE:
            gen = new net.minecraft.world.gen.feature.WorldGenBigTree/*was:WorldGenBigTree*/(true);
            break;
        case BIRCH:
            gen = new net.minecraft.world.gen.feature.WorldGenForest/*was:WorldGenForest*/(true);
            break;
        case REDWOOD:
            gen = new net.minecraft.world.gen.feature.WorldGenTaiga2/*was:WorldGenTaiga2*/(true);
            break;
        case TALL_REDWOOD:
            gen = new net.minecraft.world.gen.feature.WorldGenTaiga1/*was:WorldGenTaiga1*/();
            break;
        case JUNGLE:
            gen = new net.minecraft.world.gen.feature.WorldGenHugeTrees/*was:WorldGenMegaTree*/(true, 10 + rand.nextInt(20), 3, 3);
            break;
        case SMALL_JUNGLE:
            gen = new net.minecraft.world.gen.feature.WorldGenTrees/*was:WorldGenTrees*/(true, 4 + rand.nextInt(7), 3, 3, false);
            break;
        case JUNGLE_BUSH:
            gen = new net.minecraft.world.gen.feature.WorldGenShrub/*was:WorldGenGroundBush*/(3, 0);
            break;
        case RED_MUSHROOM:
            gen = new net.minecraft.world.gen.feature.WorldGenBigMushroom/*was:WorldGenHugeMushroom*/(1);
            break;
        case BROWN_MUSHROOM:
            gen = new net.minecraft.world.gen.feature.WorldGenBigMushroom/*was:WorldGenHugeMushroom*/(0);
            break;
        case SWAMP:
            gen = new net.minecraft.world.gen.feature.WorldGenSwamp/*was:WorldGenSwampTree*/();
            break;
        case TREE:
        default:
            gen = new net.minecraft.world.gen.feature.WorldGenTrees/*was:WorldGenTrees*/(true);
            break;
        }

        return gen.generate(delegate, rand, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }

    public net.minecraft.tileentity.TileEntity/*was:TileEntity*/ getTileEntityAt(final int x, final int y, final int z) {
        return world.func_72796_p/*was:getTileEntity*/(x, y, z);
    }

    public String getName() {
        return world.field_72986_A/*was:worldData*/.func_76065_j/*was:getName*/();
    }

    @Deprecated
    public long getId() {
        return world.field_72986_A/*was:worldData*/.func_76063_b/*was:getSeed*/();
    }

    public UUID getUID() {
        return world.func_72860_G/*was:getDataManager*/().getUUID();
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
        return world.func_72820_D/*was:getDayTime*/();
    }

    public void setFullTime(long time) {
        world.func_72877_b/*was:setDayTime*/(time);

        // Forces the client to update to the new time immediately
        for (Player p : getPlayers()) {
            CraftPlayer cp = (CraftPlayer) p;
            if (cp.getHandle().field_71135_a/*was:playerConnection*/ == null) continue;

            cp.getHandle().field_71135_a/*was:playerConnection*/.func_72567_b/*was:sendPacket*/(new net.minecraft.network.packet.Packet4UpdateTime/*was:Packet4UpdateTime*/(cp.getHandle().field_70170_p/*was:world*/.func_82737_E/*was:getTime*/(), cp.getHandle().getPlayerTime()));
        }
    }

    public boolean createExplosion(double x, double y, double z, float power) {
        return createExplosion(x, y, z, power, false, true);
    }

    public boolean createExplosion(double x, double y, double z, float power, boolean setFire) {
        return createExplosion(x, y, z, power, setFire, true);
    }

    public boolean createExplosion(double x, double y, double z, float power, boolean setFire, boolean breakBlocks) {
        return !world.func_72885_a/*was:createExplosion*/(null, x, y, z, power, setFire, breakBlocks).wasCanceled;
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
            world.field_73011_w/*was:worldProvider*/ = net.minecraft.world.WorldProvider/*was:WorldProvider*/.func_76570_a/*was:byDimension*/(environment.getId());
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
        return CraftBlock.biomeBaseToBiome(this.world.func_72807_a/*was:getBiome*/(x, z));
    }

    public void setBiome(int x, int z, Biome bio) {
        net.minecraft.world.biome.BiomeGenBase/*was:BiomeBase*/ bb = CraftBlock.biomeToBiomeBase(bio);
        if (this.world.func_72899_e/*was:isLoaded*/(x, 0, z)) {
            net.minecraft.world.chunk.Chunk/*was:net.minecraft.server.Chunk*/ chunk = this.world.func_72938_d/*was:getChunkAtWorldCoords*/(x, z);

            if (chunk != null) {
                byte[] biomevals = chunk.func_76605_m/*was:m*/();
                biomevals[((z & 0xF) << 4) | (x & 0xF)] = (byte)bb.field_76756_M/*was:id*/;
            }
        }
    }

    public double getTemperature(int x, int z) {
        return this.world.func_72807_a/*was:getBiome*/(x, z).field_76750_F/*was:temperature*/;
    }

    public double getHumidity(int x, int z) {
        return this.world.func_72807_a/*was:getBiome*/(x, z).field_76751_G/*was:humidity*/;
    }

    public List<Entity> getEntities() {
        List<Entity> list = new ArrayList<Entity>();

        for (Object o : world.field_72996_f/*was:entityList*/) {
            if (o instanceof net.minecraft.entity.Entity/*was:net.minecraft.server.Entity*/) {
                net.minecraft.entity.Entity/*was:net.minecraft.server.Entity*/ mcEnt = (net.minecraft.entity.Entity/*was:net.minecraft.server.Entity*/) o;
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

        for (Object o : world.field_72996_f/*was:entityList*/) {
            if (o instanceof net.minecraft.entity.Entity/*was:net.minecraft.server.Entity*/) {
                net.minecraft.entity.Entity/*was:net.minecraft.server.Entity*/ mcEnt = (net.minecraft.entity.Entity/*was:net.minecraft.server.Entity*/) o;
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

        for (Object entity: world.field_72996_f/*was:entityList*/) {
            if (entity instanceof net.minecraft.entity.Entity/*was:net.minecraft.server.Entity*/) {
                Entity bukkitEntity = ((net.minecraft.entity.Entity/*was:net.minecraft.server.Entity*/) entity).getBukkitEntity();

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

        for (Object entity: world.field_72996_f/*was:entityList*/) {
            if (entity instanceof net.minecraft.entity.Entity/*was:net.minecraft.server.Entity*/) {
                Entity bukkitEntity = ((net.minecraft.entity.Entity/*was:net.minecraft.server.Entity*/) entity).getBukkitEntity();

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

        for (Object o : world.field_72996_f/*was:entityList*/) {
            if (o instanceof net.minecraft.entity.Entity/*was:net.minecraft.server.Entity*/) {
                net.minecraft.entity.Entity/*was:net.minecraft.server.Entity*/ mcEnt = (net.minecraft.entity.Entity/*was:net.minecraft.server.Entity*/) o;
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
            boolean oldSave = world.field_73058_d/*was:savingDisabled*/;

            world.field_73058_d/*was:savingDisabled*/ = false;
            world.func_73044_a/*was:save*/(true, null);

            world.field_73058_d/*was:savingDisabled*/ = oldSave;
        } catch (net.minecraft.world.MinecraftException/*was:ExceptionWorldConflict*/ ex) {
            ex.printStackTrace();
        }
    }

    public boolean isAutoSave() {
        return !world.field_73058_d/*was:savingDisabled*/;
    }

    public void setAutoSave(boolean value) {
        world.field_73058_d/*was:savingDisabled*/ = !value;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.getHandle().field_73013_u/*was:difficulty*/ = difficulty.getValue();
    }

    public Difficulty getDifficulty() {
        return Difficulty.getByValue(this.getHandle().field_73013_u/*was:difficulty*/);
    }

    public BlockMetadataStore getBlockMetadata() {
        return blockMetadata;
    }

    public boolean hasStorm() {
        return world.field_72986_A/*was:worldData*/.func_76059_o/*was:hasStorm*/();
    }

    public void setStorm(boolean hasStorm) {
        CraftServer server = world.getServer();

        WeatherChangeEvent weather = new WeatherChangeEvent((org.bukkit.World) this, hasStorm);
        server.getPluginManager().callEvent(weather);
        if (!weather.isCancelled()) {
            world.field_72986_A/*was:worldData*/.func_76084_b/*was:setStorm*/(hasStorm);

            // These numbers are from Minecraft
            if (hasStorm) {
                setWeatherDuration(rand.nextInt(12000) + 12000);
            } else {
                setWeatherDuration(rand.nextInt(168000) + 12000);
            }
        }
    }

    public int getWeatherDuration() {
        return world.field_72986_A/*was:worldData*/.func_76083_p/*was:getWeatherDuration*/();
    }

    public void setWeatherDuration(int duration) {
        world.field_72986_A/*was:worldData*/.func_76080_g/*was:setWeatherDuration*/(duration);
    }

    public boolean isThundering() {
        return hasStorm() && world.field_72986_A/*was:worldData*/.func_76061_m/*was:isThundering*/();
    }

    public void setThundering(boolean thundering) {
        if (thundering && !hasStorm()) setStorm(true);
        CraftServer server = world.getServer();

        ThunderChangeEvent thunder = new ThunderChangeEvent((org.bukkit.World) this, thundering);
        server.getPluginManager().callEvent(thunder);
        if (!thunder.isCancelled()) {
            world.field_72986_A/*was:worldData*/.func_76069_a/*was:setThundering*/(thundering);

            // These numbers are from Minecraft
            if (thundering) {
                setThunderDuration(rand.nextInt(12000) + 3600);
            } else {
                setThunderDuration(rand.nextInt(168000) + 12000);
            }
        }
    }

    public int getThunderDuration() {
        return world.field_72986_A/*was:worldData*/.func_76071_n/*was:getThunderDuration*/();
    }

    public void setThunderDuration(int duration) {
        world.field_72986_A/*was:worldData*/.func_76090_f/*was:setThunderDuration*/(duration);
    }

    public long getSeed() {
        return world.field_72986_A/*was:worldData*/.func_76063_b/*was:getSeed*/();
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
        net.minecraft.network.packet.Packet61DoorChange/*was:Packet61WorldEvent*/ packet = new net.minecraft.network.packet.Packet61DoorChange/*was:Packet61WorldEvent*/(packetData, location.getBlockX(), location.getBlockY(), location.getBlockZ(), data, false);
        int distance;
        radius *= radius;

        for (Player player : getPlayers()) {
            if (((CraftPlayer) player).getHandle().field_71135_a/*was:playerConnection*/ == null) continue;
            if (!location.getWorld().equals(player.getWorld())) continue;

            distance = (int) player.getLocation().distanceSquared(location);
            if (distance <= radius) {
                ((CraftPlayer) player).getHandle().field_71135_a/*was:playerConnection*/.func_72567_b/*was:sendPacket*/(packet);
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

        net.minecraft.entity.item.EntityFallingSand/*was:EntityFallingBlock*/ entity = new net.minecraft.entity.item.EntityFallingSand/*was:EntityFallingBlock*/(world, x, y, z, material.getId(), data);
        entity.field_70286_c/*was:c*/ = 1; // ticksLived

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

        net.minecraft.entity.Entity/*was:net.minecraft.server.Entity*/ entity = null;

        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        float pitch = location.getPitch();
        float yaw = location.getYaw();

        // order is important for some of these
        if (Boat.class.isAssignableFrom(clazz)) {
            entity = new net.minecraft.entity.item.EntityBoat/*was:EntityBoat*/(world, x, y, z);
        } else if (FallingBlock.class.isAssignableFrom(clazz)) {
            x = location.getBlockX();
            y = location.getBlockY();
            z = location.getBlockZ();
            int type = world.func_72798_a/*was:getTypeId*/((int) x, (int) y, (int) z);
            int data = world.func_72805_g/*was:getData*/((int) x, (int) y, (int) z);

            entity = new net.minecraft.entity.item.EntityFallingSand/*was:EntityFallingBlock*/(world, x + 0.5, y + 0.5, z + 0.5, type, data);
        } else if (Projectile.class.isAssignableFrom(clazz)) {
            if (Snowball.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.projectile.EntitySnowball/*was:EntitySnowball*/(world, x, y, z);
            } else if (Egg.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.projectile.EntityEgg/*was:EntityEgg*/(world, x, y, z);
            } else if (Arrow.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.projectile.EntityArrow/*was:EntityArrow*/(world);
                entity.func_70012_b/*was:setPositionRotation*/(x, y, z, 0, 0);
            } else if (ThrownExpBottle.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.item.EntityExpBottle/*was:EntityThrownExpBottle*/(world);
                entity.func_70012_b/*was:setPositionRotation*/(x, y, z, 0, 0);
            } else if (Fireball.class.isAssignableFrom(clazz)) {
                if (SmallFireball.class.isAssignableFrom(clazz)) {
                    entity = new net.minecraft.entity.projectile.EntitySmallFireball/*was:EntitySmallFireball*/(world);
                } else if (WitherSkull.class.isAssignableFrom(clazz)) {
                    entity = new net.minecraft.entity.projectile.EntityWitherSkull/*was:EntityWitherSkull*/(world);
                } else {
                    entity = new net.minecraft.entity.projectile.EntityLargeFireball/*was:EntityLargeFireball*/(world);
                }
                ((net.minecraft.entity.projectile.EntityFireball/*was:EntityFireball*/) entity).func_70012_b/*was:setPositionRotation*/(x, y, z, yaw, pitch);
                Vector direction = location.getDirection().multiply(10);
                ((net.minecraft.entity.projectile.EntityFireball/*was:EntityFireball*/) entity).setDirection(direction.getX(), direction.getY(), direction.getZ());
            }
        } else if (Minecart.class.isAssignableFrom(clazz)) {
            if (PoweredMinecart.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.item.EntityMinecart/*was:EntityMinecart*/(world, x, y, z, CraftMinecart.Type.PoweredMinecart.getId());
            } else if (StorageMinecart.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.item.EntityMinecart/*was:EntityMinecart*/(world, x, y, z, CraftMinecart.Type.StorageMinecart.getId());
            } else {
                entity = new net.minecraft.entity.item.EntityMinecart/*was:EntityMinecart*/(world, x, y, z, CraftMinecart.Type.Minecart.getId());
            }
        } else if (EnderSignal.class.isAssignableFrom(clazz)) {
            entity = new net.minecraft.entity.item.EntityEnderEye/*was:EntityEnderSignal*/(world, x, y, z);
        } else if (EnderCrystal.class.isAssignableFrom(clazz)) {
            entity = new net.minecraft.entity.item.EntityEnderCrystal/*was:EntityEnderCrystal*/(world);
            entity.func_70012_b/*was:setPositionRotation*/(x, y, z, 0, 0);
        } else if (LivingEntity.class.isAssignableFrom(clazz)) {
            if (Chicken.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.passive.EntityChicken/*was:EntityChicken*/(world);
            } else if (Cow.class.isAssignableFrom(clazz)) {
                if (MushroomCow.class.isAssignableFrom(clazz)) {
                    entity = new net.minecraft.entity.passive.EntityMooshroom/*was:EntityMushroomCow*/(world);
                } else {
                    entity = new net.minecraft.entity.passive.EntityCow/*was:EntityCow*/(world);
                }
            } else if (Golem.class.isAssignableFrom(clazz)) {
                if (Snowman.class.isAssignableFrom(clazz)) {
                    entity = new net.minecraft.entity.monster.EntitySnowman/*was:EntitySnowman*/(world);
                } else if (IronGolem.class.isAssignableFrom(clazz)) {
                    entity = new net.minecraft.entity.monster.EntityIronGolem/*was:EntityIronGolem*/(world);
                }
            } else if (Creeper.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.monster.EntityCreeper/*was:EntityCreeper*/(world);
            } else if (Ghast.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.monster.EntityGhast/*was:EntityGhast*/(world);
            } else if (Pig.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.passive.EntityPig/*was:EntityPig*/(world);
            } else if (Player.class.isAssignableFrom(clazz)) {
                // need a net server handler for this one
            } else if (Sheep.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.passive.EntitySheep/*was:EntitySheep*/(world);
            } else if (Skeleton.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.monster.EntitySkeleton/*was:EntitySkeleton*/(world);
            } else if (Slime.class.isAssignableFrom(clazz)) {
                if (MagmaCube.class.isAssignableFrom(clazz)) {
                    entity = new net.minecraft.entity.monster.EntityMagmaCube/*was:EntityMagmaCube*/(world);
                } else {
                    entity = new net.minecraft.entity.monster.EntitySlime/*was:EntitySlime*/(world);
                }
            } else if (Spider.class.isAssignableFrom(clazz)) {
                if (CaveSpider.class.isAssignableFrom(clazz)) {
                    entity = new net.minecraft.entity.monster.EntityCaveSpider/*was:EntityCaveSpider*/(world);
                } else {
                    entity = new net.minecraft.entity.monster.EntitySpider/*was:EntitySpider*/(world);
                }
            } else if (Squid.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.passive.EntitySquid/*was:EntitySquid*/(world);
            } else if (Tameable.class.isAssignableFrom(clazz)) {
                if (Wolf.class.isAssignableFrom(clazz)) {
                    entity = new net.minecraft.entity.passive.EntityWolf/*was:EntityWolf*/(world);
                } else if (Ocelot.class.isAssignableFrom(clazz)) {
                    entity = new net.minecraft.entity.passive.EntityOcelot/*was:EntityOcelot*/(world);
                }
            } else if (PigZombie.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.monster.EntityPigZombie/*was:EntityPigZombie*/(world);
            } else if (Zombie.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.monster.EntityZombie/*was:EntityZombie*/(world);
            } else if (Giant.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.monster.EntityGiantZombie/*was:EntityGiantZombie*/(world);
            } else if (Silverfish.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.monster.EntitySilverfish/*was:EntitySilverfish*/(world);
            } else if (Enderman.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.monster.EntityEnderman/*was:EntityEnderman*/(world);
            } else if (Blaze.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.monster.EntityBlaze/*was:EntityBlaze*/(world);
            } else if (Villager.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.passive.EntityVillager/*was:EntityVillager*/(world);
            } else if (Witch.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.monster.EntityWitch/*was:EntityWitch*/(world);
            } else if (Wither.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.boss.EntityWither/*was:EntityWither*/(world);
            } else if (ComplexLivingEntity.class.isAssignableFrom(clazz)) {
                if (EnderDragon.class.isAssignableFrom(clazz)) {
                    entity = new net.minecraft.entity.boss.EntityDragon/*was:EntityEnderDragon*/(world);
                }
            } else if (Ambient.class.isAssignableFrom(clazz)) {
                if (Bat.class.isAssignableFrom(clazz)) {
                    entity = new net.minecraft.entity.passive.EntityBat/*was:EntityBat*/(world);
                }
            }

            if (entity != null) {
                entity.func_70080_a/*was:setLocation*/(x, y, z, pitch, yaw);
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
                entity = new net.minecraft.entity.item.EntityPainting/*was:EntityPainting*/(world, (int) x, (int) y, (int) z, dir);
            } else if (ItemFrame.class.isAssignableFrom(clazz)) {
                entity = new net.minecraft.entity.item.EntityItemFrame/*was:EntityItemFrame*/(world, (int) x, (int) y, (int) z, dir);
            }

            if (entity != null && !((net.minecraft.entity.EntityHanging/*was:EntityHanging*/) entity).func_70518_d/*was:survives*/()) {
                entity = null;
            }
        } else if (TNTPrimed.class.isAssignableFrom(clazz)) {
            entity = new net.minecraft.entity.item.EntityTNTPrimed/*was:EntityTNTPrimed*/(world, x, y, z);
        } else if (ExperienceOrb.class.isAssignableFrom(clazz)) {
            entity = new net.minecraft.entity.item.EntityXPOrb/*was:EntityExperienceOrb*/(world, x, y, z, 0);
        } else if (Weather.class.isAssignableFrom(clazz)) {
            // not sure what this can do
            entity = new net.minecraft.entity.effect.EntityLightningBolt/*was:EntityLightning*/(world, x, y, z);
        } else if (LightningStrike.class.isAssignableFrom(clazz)) {
            // what is this, I don't even
        } else if (Fish.class.isAssignableFrom(clazz)) {
            // this is not a fish, it's a bobber, and it's probably useless
            entity = new net.minecraft.entity.projectile.EntityFishHook/*was:EntityFishingHook*/(world);
            entity.func_70080_a/*was:setLocation*/(x, y, z, pitch, yaw);
        } else if (Firework.class.isAssignableFrom(clazz)) {
            entity = new net.minecraft.entity.item.EntityFireworkRocket/*was:EntityFireworks*/(world, x, y, z, null);
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
        world.func_72891_a/*was:setSpawnFlags*/(allowMonsters, allowAnimals);
    }

    public boolean getAllowAnimals() {
        return world.field_72992_H/*was:allowAnimals*/;
    }

    public boolean getAllowMonsters() {
        return world.field_72985_G/*was:allowMonsters*/;
    }

    public int getMaxHeight() {
        return world.func_72800_K/*was:getHeight*/();
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
        net.minecraft.util.ChunkCoordinates/*was:ChunkCoordinates*/ chunkcoordinates = this.world.func_72861_E/*was:getSpawn*/();
        int chunkCoordX = chunkcoordinates.field_71574_a/*was:x*/ >> 4;
        int chunkCoordZ = chunkcoordinates.field_71573_c/*was:z*/ >> 4;
        // Cycle through the 25x25 Chunks around it to load/unload the chunks.
        for (int x = -12; x <= 12; x++) {
            for (int z = -12; z <= 12; z++) {
                if (keepLoaded) {
                    loadChunk(chunkCoordX + x, chunkCoordZ + z);
                } else {
                    if (isChunkLoaded(chunkCoordX + x, chunkCoordZ + z)) {
                        if (this.getHandle().func_72964_e/*was:getChunkAt*/(chunkCoordX + x, chunkCoordZ + z) instanceof net.minecraft.world.chunk.EmptyChunk/*was:EmptyChunk*/) {
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
        return ((net.minecraft.world.storage.SaveHandler/*was:WorldNBTStorage*/) world.func_72860_G/*was:getDataManager*/()).func_75765_b/*was:getDirectory*/();
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
        net.minecraft.block.Block/*was:Block*/.field_71973_m/*was:byId*/[blockId].func_71914_a/*was:dropNaturally*/(this.world, blockX, blockY, blockZ, block.getData(), yield, 0);
        block.setType(org.bukkit.Material.AIR);
        // not sure what this does, seems to have something to do with the 'base' material of a block.
        // For example, WOODEN_STAIRS does something with WOOD in this method
        net.minecraft.block.Block/*was:Block*/.field_71973_m/*was:byId*/[blockId].func_71867_k/*was:wasExploded*/(this.world, blockX, blockY, blockZ);
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
        return org.bukkit.WorldType.getByName(world.func_72912_H/*was:getWorldData*/().func_76067_t/*was:getType*/().func_77127_a/*was:name*/());
    }

    public boolean canGenerateStructures() {
        return world.func_72912_H/*was:getWorldData*/().func_76089_r/*was:shouldGenerateMapFeatures*/();
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

        getHandle().func_72908_a/*was:makeSound*/(x, y, z, CraftSound.getSound(sound), volume, pitch);
    }

    public String getGameRuleValue(String rule) {
        return getHandle().func_82736_K/*was:getGameRules*/().func_82767_a/*was:get*/(rule);
    }

    public boolean setGameRuleValue(String rule, String value) {
        // No null values allowed
        if (rule == null || value == null) return false;

        if (!isGameRule(rule)) return false;

        getHandle().func_82736_K/*was:getGameRules*/().func_82764_b/*was:set*/(rule, value);
        return true;
    }

    public String[] getGameRules() {
        return getHandle().func_82736_K/*was:getGameRules*/().func_82763_b/*was:b*/();
    }

    public boolean isGameRule(String rule) {
        return getHandle().func_82736_K/*was:getGameRules*/().func_82765_e/*was:e*/(rule);
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

        net.minecraft.world.gen.ChunkProviderServer/*was:ChunkProviderServer*/ cps = world.field_73059_b/*was:chunkProviderServer*/;
        Iterator<net.minecraft.world.chunk.Chunk/*was:net.minecraft.server.Chunk*/> iter = cps.field_73244_f/*was:chunks*/.values().iterator();
        while (iter.hasNext()) {
            net.minecraft.world.chunk.Chunk/*was:net.minecraft.server.Chunk*/ chunk = iter.next();
            // If in use, skip it
            if (isChunkInUse(chunk.field_76635_g/*was:x*/, chunk.field_76647_h/*was:z*/)) {
                continue;
            }

            // Already unloading?
            if (cps.field_73248_b/*was:unloadQueue*/.contains(chunk.field_76635_g/*was:x*/, chunk.field_76647_h/*was:z*/)) {
                continue;
            }

            // Add unload request
            cps.func_73241_b/*was:queueUnload*/(chunk.field_76635_g/*was:x*/,  chunk.field_76647_h/*was:z*/);
        }
    }
}
