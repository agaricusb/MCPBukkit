package org.bukkit.craftbukkit.block;


import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.EntityType;

public class CraftCreatureSpawner extends CraftBlockState implements CreatureSpawner {
    private final CraftWorld world;
    private final net.minecraft.tileentity.TileEntityMobSpawner/*was:TileEntityMobSpawner*/ spawner;

    public CraftCreatureSpawner(final Block block) {
        super(block);

        world = (CraftWorld) block.getWorld();
        spawner = (net.minecraft.tileentity.TileEntityMobSpawner/*was:TileEntityMobSpawner*/) world.getTileEntityAt(getX(), getY(), getZ());
    }

    @Deprecated
    public CreatureType getCreatureType() {
        return CreatureType.fromName(spawner.field_70390_d/*was:mobName*/);
    }

    public EntityType getSpawnedType() {
        return EntityType.fromName(spawner.field_70390_d/*was:mobName*/);
    }

    @Deprecated
    public void setCreatureType(CreatureType creatureType) {
        spawner.field_70390_d/*was:mobName*/ = creatureType.getName();
    }

    public void setSpawnedType(EntityType entityType) {
        if (entityType == null || entityType.getName() == null) {
            throw new IllegalArgumentException("Can't spawn EntityType " + entityType + " from mobspawners!");
        }

        spawner.field_70390_d/*was:mobName*/ = entityType.getName();
    }

    @Deprecated
    public String getCreatureTypeId() {
        return spawner.field_70390_d/*was:mobName*/;
    }

    @Deprecated
    public void setCreatureTypeId(String creatureName) {
        setCreatureTypeByName(creatureName);
    }

    public String getCreatureTypeName() {
        return spawner.field_70390_d/*was:mobName*/;
    }

    public void setCreatureTypeByName(String creatureType) {
        // Verify input
        EntityType type = EntityType.fromName(creatureType);
        if (type == null) {
            return;
        }
        setSpawnedType(type);
    }

    public int getDelay() {
        return spawner.field_70394_a/*was:spawnDelay*/;
    }

    public void setDelay(int delay) {
        spawner.field_70394_a/*was:spawnDelay*/ = delay;
    }

}
