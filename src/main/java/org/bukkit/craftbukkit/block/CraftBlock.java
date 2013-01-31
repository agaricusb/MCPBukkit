package org.bukkit.craftbukkit.block;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.craftbukkit.CraftChunk;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BlockVector;

public class CraftBlock implements Block {
    private final CraftChunk chunk;
    private final int x;
    private final int y;
    private final int z;
    private static final Biome BIOME_MAPPING[];
    private static final net.minecraft.world.biome.BiomeGenBase/*was:BiomeBase*/ BIOMEBASE_MAPPING[];

    public CraftBlock(CraftChunk chunk, int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.chunk = chunk;
    }

    public World getWorld() {
        return chunk.getWorld();
    }

    public Location getLocation() {
        return new Location(getWorld(), x, y, z);
    }

    public Location getLocation(Location loc) {
        if (loc != null) {
            loc.setWorld(getWorld());
            loc.setX(x);
            loc.setY(y);
            loc.setZ(z);
            loc.setYaw(0);
            loc.setPitch(0);
        }

        return loc;
    }

    public BlockVector getVector() {
        return new BlockVector(x, y, z);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public Chunk getChunk() {
        return chunk;
    }

    public void setData(final byte data) {
        chunk.getHandle().field_76637_e/*was:world*/.func_72921_c/*was:setData*/(x, y, z, data);
    }

    public void setData(final byte data, boolean applyPhysics) {
        if (applyPhysics) {
            chunk.getHandle().field_76637_e/*was:world*/.func_72921_c/*was:setData*/(x, y, z, data);
        } else {
            chunk.getHandle().field_76637_e/*was:world*/.func_72881_d/*was:setRawData*/(x, y, z, data);
        }
    }

    public byte getData() {
        return (byte) chunk.getHandle().func_76628_c/*was:getData*/(this.x & 0xF, this.y & 0xFF, this.z & 0xF);
    }

    public void setType(final Material type) {
        setTypeId(type.getId());
    }

    public boolean setTypeId(final int type) {
        return chunk.getHandle().field_76637_e/*was:world*/.func_72859_e/*was:setTypeId*/(x, y, z, type);
    }

    public boolean setTypeId(final int type, final boolean applyPhysics) {
        if (applyPhysics) {
            return setTypeId(type);
        } else {
            return chunk.getHandle().field_76637_e/*was:world*/.func_72822_b/*was:setRawTypeId*/(x, y, z, type);
        }
    }

    public boolean setTypeIdAndData(final int type, final byte data, final boolean applyPhysics) {
        if (applyPhysics) {
            return chunk.getHandle().field_76637_e/*was:world*/.func_72832_d/*was:setTypeIdAndData*/(x, y, z, type, data);
        } else {
            boolean success = chunk.getHandle().field_76637_e/*was:world*/.func_72961_c/*was:setRawTypeIdAndData*/(x, y, z, type, data);
            if (success) {
                chunk.getHandle().field_76637_e/*was:world*/.func_72845_h/*was:notify*/(x, y, z);
            }
            return success;
        }
    }

    public Material getType() {
        return Material.getMaterial(getTypeId());
    }

    public int getTypeId() {
        return chunk.getHandle().func_76610_a/*was:getTypeId*/(this.x & 0xF, this.y & 0xFF, this.z & 0xF);
    }

    public byte getLightLevel() {
        return (byte) chunk.getHandle().field_76637_e/*was:world*/.func_72957_l/*was:getLightLevel*/(this.x, this.y, this.z);
    }

    public byte getLightFromSky() {
        return (byte) chunk.getHandle().func_76614_a/*was:getBrightness*/(net.minecraft.world.EnumSkyBlock/*was:EnumSkyBlock*/.Sky/*was:SKY*/, this.x & 0xF, this.y & 0xFF, this.z & 0xF);
    }

    public byte getLightFromBlocks() {
        return (byte) chunk.getHandle().func_76614_a/*was:getBrightness*/(net.minecraft.world.EnumSkyBlock/*was:EnumSkyBlock*/.Block/*was:BLOCK*/, this.x & 0xF, this.y & 0xFF, this.z & 0xF);
    }


    public Block getFace(final BlockFace face) {
        return getRelative(face, 1);
    }

    public Block getFace(final BlockFace face, final int distance) {
        return getRelative(face, distance);
    }

    public Block getRelative(final int modX, final int modY, final int modZ) {
        return getWorld().getBlockAt(getX() + modX, getY() + modY, getZ() + modZ);
    }

    public Block getRelative(BlockFace face) {
        return getRelative(face, 1);
    }

    public Block getRelative(BlockFace face, int distance) {
        return getRelative(face.getModX() * distance, face.getModY() * distance, face.getModZ() * distance);
    }

    public BlockFace getFace(final Block block) {
        BlockFace[] values = BlockFace.values();

        for (BlockFace face : values) {
            if ((this.getX() + face.getModX() == block.getX()) &&
                (this.getY() + face.getModY() == block.getY()) &&
                (this.getZ() + face.getModZ() == block.getZ())
            ) {
                return face;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return "CraftBlock{" + "chunk=" + chunk + ",x=" + x + ",y=" + y + ",z=" + z + ",type=" + getType() + ",data=" + getData() + '}';
    }

    /**
     * Notch uses a 0-5 to mean DOWN, UP, NORTH, SOUTH, WEST, EAST
     * in that order all over. This method is convenience to convert for us.
     *
     * @return BlockFace the BlockFace represented by this number
     */
    public static BlockFace notchToBlockFace(int notch) {
        switch (notch) {
        case 0:
            return BlockFace.DOWN;
        case 1:
            return BlockFace.UP;
        case 2:
            return BlockFace.NORTH;
        case 3:
            return BlockFace.SOUTH;
        case 4:
            return BlockFace.WEST;
        case 5:
            return BlockFace.EAST;
        default:
            return BlockFace.SELF;
        }
    }

    public static int blockFaceToNotch(BlockFace face) {
        switch (face) {
        case DOWN:
            return 0;
        case UP:
            return 1;
        case NORTH:
            return 2;
        case SOUTH:
            return 3;
        case WEST:
            return 4;
        case EAST:
            return 5;
        default:
            return 7; // Good as anything here, but technically invalid
        }
    }

    public BlockState getState() {
        Material material = getType();

        switch (material) {
        case SIGN:
        case SIGN_POST:
        case WALL_SIGN:
            return new CraftSign(this);
        case CHEST:
            return new CraftChest(this);
        case BURNING_FURNACE:
        case FURNACE:
            return new CraftFurnace(this);
        case DISPENSER:
            return new CraftDispenser(this);
        case MOB_SPAWNER:
            return new CraftCreatureSpawner(this);
        case NOTE_BLOCK:
            return new CraftNoteBlock(this);
        case JUKEBOX:
            return new CraftJukebox(this);
        case BREWING_STAND:
            return new CraftBrewingStand(this);
        case SKULL:
            return new CraftSkull(this);
        default:
            return new CraftBlockState(this);
        }
    }

    public Biome getBiome() {
        return getWorld().getBiome(x, z);
    }

    public void setBiome(Biome bio) {
        getWorld().setBiome(x, z, bio);
    }

    public static Biome biomeBaseToBiome(net.minecraft.world.biome.BiomeGenBase/*was:BiomeBase*/ base) {
        if (base == null) {
            return null;
        }

        return BIOME_MAPPING[base.field_76756_M/*was:id*/];
    }

    public static net.minecraft.world.biome.BiomeGenBase/*was:BiomeBase*/ biomeToBiomeBase(Biome bio) {
        if (bio == null) {
            return null;
        }
        return BIOMEBASE_MAPPING[bio.ordinal()];
    }

    public double getTemperature() {
        return getWorld().getTemperature(x, z);
    }

    public double getHumidity() {
        return getWorld().getHumidity(x, z);
    }

    public boolean isBlockPowered() {
        return chunk.getHandle().field_76637_e/*was:world*/.func_72871_y/*was:isBlockPowered*/(x, y, z);
    }

    public boolean isBlockIndirectlyPowered() {
        return chunk.getHandle().field_76637_e/*was:world*/.func_72864_z/*was:isBlockIndirectlyPowered*/(x, y, z);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof CraftBlock)) return false;
        CraftBlock other = (CraftBlock) o;

        return this.x == other.x && this.y == other.y && this.z == other.z && this.getWorld().equals(other.getWorld());
    }

    @Override
    public int hashCode() {
        return this.y << 24 ^ this.x ^ this.z ^ this.getWorld().hashCode();
    }

    public boolean isBlockFacePowered(BlockFace face) {
        return chunk.getHandle().field_76637_e/*was:world*/.func_72879_k/*was:isBlockFacePowered*/(x, y, z, blockFaceToNotch(face));
    }

    public boolean isBlockFaceIndirectlyPowered(BlockFace face) {
        return chunk.getHandle().field_76637_e/*was:world*/.func_72878_l/*was:isBlockFaceIndirectlyPowered*/(x, y, z, blockFaceToNotch(face));
    }

    public int getBlockPower(BlockFace face) {
        int power = 0;
        net.minecraft.block.BlockRedstoneWire/*was:BlockRedstoneWire*/ wire = (net.minecraft.block.BlockRedstoneWire/*was:BlockRedstoneWire*/) net.minecraft.block.Block/*was:Block*/.field_72075_av/*was:REDSTONE_WIRE*/;
        net.minecraft.world.World/*was:net.minecraft.server.World*/ world = chunk.getHandle().field_76637_e/*was:world*/;
        if ((face == BlockFace.DOWN || face == BlockFace.SELF) && world.func_72879_k/*was:isBlockFacePowered*/(x, y - 1, z, 0)) power = wire.func_72170_e/*was:getPower*/(world, x, y - 1, z, power);
        if ((face == BlockFace.UP || face == BlockFace.SELF) && world.func_72879_k/*was:isBlockFacePowered*/(x, y + 1, z, 1)) power = wire.func_72170_e/*was:getPower*/(world, x, y + 1, z, power);
        if ((face == BlockFace.EAST || face == BlockFace.SELF) && world.func_72879_k/*was:isBlockFacePowered*/(x + 1, y, z, 2)) power = wire.func_72170_e/*was:getPower*/(world, x + 1, y, z, power);
        if ((face == BlockFace.WEST || face == BlockFace.SELF) && world.func_72879_k/*was:isBlockFacePowered*/(x - 1, y, z, 3)) power = wire.func_72170_e/*was:getPower*/(world, x - 1, y, z, power);
        if ((face == BlockFace.NORTH || face == BlockFace.SELF) && world.func_72879_k/*was:isBlockFacePowered*/(x, y, z - 1, 4)) power = wire.func_72170_e/*was:getPower*/(world, x, y, z - 1, power);
        if ((face == BlockFace.SOUTH || face == BlockFace.SELF) && world.func_72879_k/*was:isBlockFacePowered*/(x, y, z + 1, 5)) power = wire.func_72170_e/*was:getPower*/(world, x, y, z - 1, power);
        return power > 0 ? power : (face == BlockFace.SELF ? isBlockIndirectlyPowered() : isBlockFaceIndirectlyPowered(face)) ? 15 : 0;
    }

    public int getBlockPower() {
        return getBlockPower(BlockFace.SELF);
    }

    public boolean isEmpty() {
        return getType() == Material.AIR;
    }

    public boolean isLiquid() {
        return (getType() == Material.WATER) || (getType() == Material.STATIONARY_WATER) || (getType() == Material.LAVA) || (getType() == Material.STATIONARY_LAVA);
    }

    public PistonMoveReaction getPistonMoveReaction() {
        return PistonMoveReaction.getById(net.minecraft.block.Block/*was:Block*/.field_71973_m/*was:byId*/[this.getTypeId()].field_72018_cp/*was:material*/.func_76227_m/*was:getPushReaction*/());
    }

    private boolean itemCausesDrops(ItemStack item) {
        net.minecraft.block.Block/*was:Block*/ block = net.minecraft.block.Block/*was:Block*/.field_71973_m/*was:byId*/[this.getTypeId()];
        net.minecraft.item.Item/*was:net.minecraft.server.Item*/ itemType = item != null ? net.minecraft.item.Item/*was:Item*/.field_77698_e/*was:byId*/[item.getTypeId()] : null;
        return block != null && (block.field_72018_cp/*was:material*/.func_76229_l/*was:isAlwaysDestroyable*/() || (itemType != null && itemType.func_77641_a/*was:canDestroySpecialBlock*/(block)));
    }

    public boolean breakNaturally() {
        // Order matters here, need to drop before setting to air so skulls can get their data
        net.minecraft.block.Block/*was:Block*/ block = net.minecraft.block.Block/*was:Block*/.field_71973_m/*was:byId*/[this.getTypeId()];
        byte data = getData();
        boolean result = false;

        if (block != null) {
            block.func_71914_a/*was:dropNaturally*/(chunk.getHandle().field_76637_e/*was:world*/, x, y, z, data, 1.0F, 0);
            result = true;
        }

        setTypeId(Material.AIR.getId());
        return result;
    }

    public boolean breakNaturally(ItemStack item) {
        if (itemCausesDrops(item)) {
            return breakNaturally();
        } else {
            return setTypeId(Material.AIR.getId());
        }
    }

    public Collection<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList<ItemStack>();

        net.minecraft.block.Block/*was:Block*/ block = net.minecraft.block.Block/*was:Block*/.field_71973_m/*was:byId*/[this.getTypeId()];
        if (block != null) {
            byte data = getData();
            // based on nms.Block.dropNaturally
            int count = block.func_71910_a/*was:getDropCount*/(0, chunk.getHandle().field_76637_e/*was:world*/.field_73012_v/*was:random*/);
            for (int i = 0; i < count; ++i) {
                int item = block.func_71885_a/*was:getDropType*/(data, chunk.getHandle().field_76637_e/*was:world*/.field_73012_v/*was:random*/, 0);
                if (item > 0) {
                    // Skulls are special, their data is based on the tile entity
                    if (net.minecraft.block.Block/*was:Block*/.field_82512_cj/*was:SKULL*/.field_71990_ca/*was:id*/ == this.getTypeId()) {
                        net.minecraft.item.ItemStack/*was:net.minecraft.server.ItemStack*/ nmsStack = new net.minecraft.item.ItemStack/*was:net.minecraft.server.ItemStack*/(item, 1, block.func_71873_h/*was:getDropData*/(chunk.getHandle().field_76637_e/*was:world*/, x, y, z));
                        net.minecraft.tileentity.TileEntitySkull/*was:TileEntitySkull*/ tileentityskull = (net.minecraft.tileentity.TileEntitySkull/*was:TileEntitySkull*/) chunk.getHandle().field_76637_e/*was:world*/.func_72796_p/*was:getTileEntity*/(x, y, z);

                        if (tileentityskull.func_82117_a/*was:getSkullType*/() == 3 && tileentityskull.func_82120_c/*was:getExtraType*/() != null && tileentityskull.func_82120_c/*was:getExtraType*/().length() > 0) {
                            nmsStack.func_77982_d/*was:setTag*/(new net.minecraft.nbt.NBTTagCompound/*was:NBTTagCompound*/());
                            nmsStack.func_77978_p/*was:getTag*/().func_74778_a/*was:setString*/("SkullOwner", tileentityskull.func_82120_c/*was:getExtraType*/());
                        }

                        drops.add(CraftItemStack.asBukkitCopy(nmsStack));
                    } else {
                        drops.add(new ItemStack(item, 1, (short) block.func_71899_b/*was:getDropData*/(data)));
                    }
                }
            }
        }
        return drops;
    }

    public Collection<ItemStack> getDrops(ItemStack item) {
        if (itemCausesDrops(item)) {
            return getDrops();
        } else {
            return Collections.emptyList();
        }
    }

    /* Build biome index based lookup table for BiomeBase to Biome mapping */
    static {
        BIOME_MAPPING = new Biome[net.minecraft.world.biome.BiomeGenBase/*was:BiomeBase*/.field_76773_a/*was:biomes*/.length];
        BIOMEBASE_MAPPING = new net.minecraft.world.biome.BiomeGenBase/*was:BiomeBase*/[Biome.values().length];
        BIOME_MAPPING[net.minecraft.world.biome.BiomeGenBase/*was:BiomeBase*/.field_76780_h/*was:SWAMPLAND*/.field_76756_M/*was:id*/] = Biome.SWAMPLAND;
        BIOME_MAPPING[net.minecraft.world.biome.BiomeGenBase/*was:BiomeBase*/.field_76767_f/*was:FOREST*/.field_76756_M/*was:id*/] = Biome.FOREST;
        BIOME_MAPPING[net.minecraft.world.biome.BiomeGenBase/*was:BiomeBase*/.field_76768_g/*was:TAIGA*/.field_76756_M/*was:id*/] = Biome.TAIGA;
        BIOME_MAPPING[net.minecraft.world.biome.BiomeGenBase/*was:BiomeBase*/.field_76769_d/*was:DESERT*/.field_76756_M/*was:id*/] = Biome.DESERT;
        BIOME_MAPPING[net.minecraft.world.biome.BiomeGenBase/*was:BiomeBase*/.field_76772_c/*was:PLAINS*/.field_76756_M/*was:id*/] = Biome.PLAINS;
        BIOME_MAPPING[net.minecraft.world.biome.BiomeGenBase/*was:BiomeBase*/.field_76778_j/*was:HELL*/.field_76756_M/*was:id*/] = Biome.HELL;
        BIOME_MAPPING[net.minecraft.world.biome.BiomeGenBase/*was:BiomeBase*/.field_76779_k/*was:SKY*/.field_76756_M/*was:id*/] = Biome.SKY;
        BIOME_MAPPING[net.minecraft.world.biome.BiomeGenBase/*was:BiomeBase*/.field_76781_i/*was:RIVER*/.field_76756_M/*was:id*/] = Biome.RIVER;
        BIOME_MAPPING[net.minecraft.world.biome.BiomeGenBase/*was:BiomeBase*/.field_76770_e/*was:EXTREME_HILLS*/.field_76756_M/*was:id*/] = Biome.EXTREME_HILLS;
        BIOME_MAPPING[net.minecraft.world.biome.BiomeGenBase/*was:BiomeBase*/.field_76771_b/*was:OCEAN*/.field_76756_M/*was:id*/] = Biome.OCEAN;
        BIOME_MAPPING[net.minecraft.world.biome.BiomeGenBase/*was:BiomeBase*/.field_76776_l/*was:FROZEN_OCEAN*/.field_76756_M/*was:id*/] = Biome.FROZEN_OCEAN;
        BIOME_MAPPING[net.minecraft.world.biome.BiomeGenBase/*was:BiomeBase*/.field_76777_m/*was:FROZEN_RIVER*/.field_76756_M/*was:id*/] = Biome.FROZEN_RIVER;
        BIOME_MAPPING[net.minecraft.world.biome.BiomeGenBase/*was:BiomeBase*/.field_76774_n/*was:ICE_PLAINS*/.field_76756_M/*was:id*/] = Biome.ICE_PLAINS;
        BIOME_MAPPING[net.minecraft.world.biome.BiomeGenBase/*was:BiomeBase*/.field_76775_o/*was:ICE_MOUNTAINS*/.field_76756_M/*was:id*/] = Biome.ICE_MOUNTAINS;
        BIOME_MAPPING[net.minecraft.world.biome.BiomeGenBase/*was:BiomeBase*/.field_76789_p/*was:MUSHROOM_ISLAND*/.field_76756_M/*was:id*/] = Biome.MUSHROOM_ISLAND;
        BIOME_MAPPING[net.minecraft.world.biome.BiomeGenBase/*was:BiomeBase*/.field_76788_q/*was:MUSHROOM_SHORE*/.field_76756_M/*was:id*/] = Biome.MUSHROOM_SHORE;
        BIOME_MAPPING[net.minecraft.world.biome.BiomeGenBase/*was:BiomeBase*/.field_76787_r/*was:BEACH*/.field_76756_M/*was:id*/] = Biome.BEACH;
        BIOME_MAPPING[net.minecraft.world.biome.BiomeGenBase/*was:BiomeBase*/.field_76786_s/*was:DESERT_HILLS*/.field_76756_M/*was:id*/] = Biome.DESERT_HILLS;
        BIOME_MAPPING[net.minecraft.world.biome.BiomeGenBase/*was:BiomeBase*/.field_76785_t/*was:FOREST_HILLS*/.field_76756_M/*was:id*/] = Biome.FOREST_HILLS;
        BIOME_MAPPING[net.minecraft.world.biome.BiomeGenBase/*was:BiomeBase*/.field_76784_u/*was:TAIGA_HILLS*/.field_76756_M/*was:id*/] = Biome.TAIGA_HILLS;
        BIOME_MAPPING[net.minecraft.world.biome.BiomeGenBase/*was:BiomeBase*/.field_76783_v/*was:SMALL_MOUNTAINS*/.field_76756_M/*was:id*/] = Biome.SMALL_MOUNTAINS;
        BIOME_MAPPING[net.minecraft.world.biome.BiomeGenBase/*was:BiomeBase*/.field_76782_w/*was:JUNGLE*/.field_76756_M/*was:id*/] = Biome.JUNGLE;
        BIOME_MAPPING[net.minecraft.world.biome.BiomeGenBase/*was:BiomeBase*/.field_76792_x/*was:JUNGLE_HILLS*/.field_76756_M/*was:id*/] = Biome.JUNGLE_HILLS;
        /* Sanity check - we should have a record for each record in the BiomeBase.a table */
        /* Helps avoid missed biomes when we upgrade bukkit to new code with new biomes */
        for (int i = 0; i < BIOME_MAPPING.length; i++) {
            if ((net.minecraft.world.biome.BiomeGenBase/*was:BiomeBase*/.field_76773_a/*was:biomes*/[i] != null) && (BIOME_MAPPING[i] == null)) {
                throw new IllegalArgumentException("Missing Biome mapping for BiomeBase[" + i + "]");
            }
            if (BIOME_MAPPING[i] != null) {  /* Build reverse mapping for setBiome */
                BIOMEBASE_MAPPING[BIOME_MAPPING[i].ordinal()] = net.minecraft.world.biome.BiomeGenBase/*was:BiomeBase*/.field_76773_a/*was:biomes*/[i];
            }
        }
    }

    public void setMetadata(String metadataKey, MetadataValue newMetadataValue) {
        chunk.getCraftWorld().getBlockMetadata().setMetadata(this, metadataKey, newMetadataValue);
    }

    public List<MetadataValue> getMetadata(String metadataKey) {
        return chunk.getCraftWorld().getBlockMetadata().getMetadata(this, metadataKey);
    }

    public boolean hasMetadata(String metadataKey) {
        return chunk.getCraftWorld().getBlockMetadata().hasMetadata(this, metadataKey);
    }

    public void removeMetadata(String metadataKey, Plugin owningPlugin) {
        chunk.getCraftWorld().getBlockMetadata().removeMetadata(this, metadataKey, owningPlugin);
    }
}
