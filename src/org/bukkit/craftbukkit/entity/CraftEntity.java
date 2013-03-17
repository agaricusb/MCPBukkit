package org.bukkit.craftbukkit.entity;

import java.util.List;
import java.util.UUID;


import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public abstract class CraftEntity implements org.bukkit.entity.Entity {
    protected final CraftServer server;
    protected net.minecraft.entity.Entity entity;
    private EntityDamageEvent lastDamageEvent;

    public CraftEntity(final CraftServer server, final net.minecraft.entity.Entity entity) {
        this.server = server;
        this.entity = entity;
    }

    public static CraftEntity getEntity(CraftServer server, net.minecraft.entity.Entity entity) {
        /**
         * Order is *EXTREMELY* important -- keep it right! =D
         */
        if (entity instanceof net.minecraft.entity.EntityLiving) {
            // Players
            if (entity instanceof net.minecraft.entity.player.EntityPlayer) {
                if (entity instanceof net.minecraft.entity.player.EntityPlayerMP) { return new CraftPlayer(server, (net.minecraft.entity.player.EntityPlayerMP) entity); }
                else { return new CraftHumanEntity(server, (net.minecraft.entity.player.EntityPlayer) entity); }
            }
            else if (entity instanceof net.minecraft.entity.EntityCreature) {
                // Animals
                if (entity instanceof net.minecraft.entity.passive.EntityAnimal) {
                    if (entity instanceof net.minecraft.entity.passive.EntityChicken) { return new CraftChicken(server, (net.minecraft.entity.passive.EntityChicken) entity); }
                    else if (entity instanceof net.minecraft.entity.passive.EntityCow) {
                        if (entity instanceof net.minecraft.entity.passive.EntityMooshroom) { return new CraftMushroomCow(server, (net.minecraft.entity.passive.EntityMooshroom) entity); }
                        else { return new CraftCow(server, (net.minecraft.entity.passive.EntityCow) entity); }
                    }
                    else if (entity instanceof net.minecraft.entity.passive.EntityPig) { return new CraftPig(server, (net.minecraft.entity.passive.EntityPig) entity); }
                    else if (entity instanceof net.minecraft.entity.passive.EntityTameable) {
                        if (entity instanceof net.minecraft.entity.passive.EntityWolf) { return new CraftWolf(server, (net.minecraft.entity.passive.EntityWolf) entity); }
                        else if (entity instanceof net.minecraft.entity.passive.EntityOcelot) { return new CraftOcelot(server, (net.minecraft.entity.passive.EntityOcelot) entity); }
                    }
                    else if (entity instanceof net.minecraft.entity.passive.EntitySheep) { return new CraftSheep(server, (net.minecraft.entity.passive.EntitySheep) entity); }
                    else  { return new CraftAnimals(server, (net.minecraft.entity.passive.EntityAnimal) entity); }
                }
                // Monsters
                else if (entity instanceof net.minecraft.entity.monster.EntityMob) {
                    if (entity instanceof net.minecraft.entity.monster.EntityZombie) {
                        if (entity instanceof net.minecraft.entity.monster.EntityPigZombie) { return new CraftPigZombie(server, (net.minecraft.entity.monster.EntityPigZombie) entity); }
                        else { return new CraftZombie(server, (net.minecraft.entity.monster.EntityZombie) entity); }
                    }
                    else if (entity instanceof net.minecraft.entity.monster.EntityCreeper) { return new CraftCreeper(server, (net.minecraft.entity.monster.EntityCreeper) entity); }
                    else if (entity instanceof net.minecraft.entity.monster.EntityEnderman) { return new CraftEnderman(server, (net.minecraft.entity.monster.EntityEnderman) entity); }
                    else if (entity instanceof net.minecraft.entity.monster.EntitySilverfish) { return new CraftSilverfish(server, (net.minecraft.entity.monster.EntitySilverfish) entity); }
                    else if (entity instanceof net.minecraft.entity.monster.EntityGiantZombie) { return new CraftGiant(server, (net.minecraft.entity.monster.EntityGiantZombie) entity); }
                    else if (entity instanceof net.minecraft.entity.monster.EntitySkeleton) { return new CraftSkeleton(server, (net.minecraft.entity.monster.EntitySkeleton) entity); }
                    else if (entity instanceof net.minecraft.entity.monster.EntityBlaze) { return new CraftBlaze(server, (net.minecraft.entity.monster.EntityBlaze) entity); }
                    else if (entity instanceof net.minecraft.entity.monster.EntityWitch) { return new CraftWitch(server, (net.minecraft.entity.monster.EntityWitch) entity); }
                    else if (entity instanceof net.minecraft.entity.boss.EntityWither) { return new CraftWither(server, (net.minecraft.entity.boss.EntityWither) entity); }
                    else if (entity instanceof net.minecraft.entity.monster.EntitySpider) {
                        if (entity instanceof net.minecraft.entity.monster.EntityCaveSpider) { return new CraftCaveSpider(server, (net.minecraft.entity.monster.EntityCaveSpider) entity); }
                        else { return new CraftSpider(server, (net.minecraft.entity.monster.EntitySpider) entity); }
                    }

                    else  { return new CraftMonster(server, (net.minecraft.entity.monster.EntityMob) entity); }
                }
                // Water Animals
                else if (entity instanceof net.minecraft.entity.passive.EntityWaterMob) {
                    if (entity instanceof net.minecraft.entity.passive.EntitySquid) { return new CraftSquid(server, (net.minecraft.entity.passive.EntitySquid) entity); }
                    else { return new CraftWaterMob(server, (net.minecraft.entity.passive.EntityWaterMob) entity); }
                }
                else if (entity instanceof net.minecraft.entity.monster.EntityGolem) {
                    if (entity instanceof net.minecraft.entity.monster.EntitySnowman) { return new CraftSnowman(server, (net.minecraft.entity.monster.EntitySnowman) entity); }
                    else if (entity instanceof net.minecraft.entity.monster.EntityIronGolem) { return new CraftIronGolem(server, (net.minecraft.entity.monster.EntityIronGolem) entity); }
                }
                else if (entity instanceof net.minecraft.entity.passive.EntityVillager) { return new CraftVillager(server, (net.minecraft.entity.passive.EntityVillager) entity); }
                else { return new CraftCreature(server, (net.minecraft.entity.EntityCreature) entity); }
            }
            // Slimes are a special (and broken) case
            else if (entity instanceof net.minecraft.entity.monster.EntitySlime) {
                if (entity instanceof net.minecraft.entity.monster.EntityMagmaCube) { return new CraftMagmaCube(server, (net.minecraft.entity.monster.EntityMagmaCube) entity); }
                else { return new CraftSlime(server, (net.minecraft.entity.monster.EntitySlime) entity); }
            }
            // Flying
            else if (entity instanceof net.minecraft.entity.EntityFlying) {
                if (entity instanceof net.minecraft.entity.monster.EntityGhast) { return new CraftGhast(server, (net.minecraft.entity.monster.EntityGhast) entity); }
                else { return new CraftFlying(server, (net.minecraft.entity.EntityFlying) entity); }
            }
            else if (entity instanceof net.minecraft.entity.boss.EntityDragon) {
                return new CraftEnderDragon(server, (net.minecraft.entity.boss.EntityDragon) entity);
            }
            // Ambient
            else if (entity instanceof net.minecraft.entity.passive.EntityAmbientCreature) {
                if (entity instanceof net.minecraft.entity.passive.EntityBat) { return new CraftBat(server, (net.minecraft.entity.passive.EntityBat) entity); }
                else { return new CraftAmbient(server, (net.minecraft.entity.passive.EntityAmbientCreature) entity); }
            }
            else  { return new CraftLivingEntity(server, (net.minecraft.entity.EntityLiving) entity); }
        }
        else if (entity instanceof net.minecraft.entity.boss.EntityDragonPart) {
            net.minecraft.entity.boss.EntityDragonPart part = (net.minecraft.entity.boss.EntityDragonPart) entity;
            if (part.field_70259_a instanceof net.minecraft.entity.boss.EntityDragon) { return new CraftEnderDragonPart(server, (net.minecraft.entity.boss.EntityDragonPart) entity); }
            else { return new CraftComplexPart(server, (net.minecraft.entity.boss.EntityDragonPart) entity); }
        }
        else if (entity instanceof net.minecraft.entity.item.EntityXPOrb) { return new CraftExperienceOrb(server, (net.minecraft.entity.item.EntityXPOrb) entity); }
        else if (entity instanceof net.minecraft.entity.projectile.EntityArrow) { return new CraftArrow(server, (net.minecraft.entity.projectile.EntityArrow) entity); }
        else if (entity instanceof net.minecraft.entity.item.EntityBoat) { return new CraftBoat(server, (net.minecraft.entity.item.EntityBoat) entity); }
        else if (entity instanceof net.minecraft.entity.projectile.EntityThrowable) {
            if (entity instanceof net.minecraft.entity.projectile.EntityEgg) { return new CraftEgg(server, (net.minecraft.entity.projectile.EntityEgg) entity); }
            else if (entity instanceof net.minecraft.entity.projectile.EntitySnowball) { return new CraftSnowball(server, (net.minecraft.entity.projectile.EntitySnowball) entity); }
            else if (entity instanceof net.minecraft.entity.projectile.EntityPotion) { return new CraftThrownPotion(server, (net.minecraft.entity.projectile.EntityPotion) entity); }
            else if (entity instanceof net.minecraft.entity.item.EntityEnderPearl) { return new CraftEnderPearl(server, (net.minecraft.entity.item.EntityEnderPearl) entity); }
            else if (entity instanceof net.minecraft.entity.item.EntityExpBottle) { return new CraftThrownExpBottle(server, (net.minecraft.entity.item.EntityExpBottle) entity); }
        }
        else if (entity instanceof net.minecraft.entity.item.EntityFallingSand) { return new CraftFallingSand(server, (net.minecraft.entity.item.EntityFallingSand) entity); }
        else if (entity instanceof net.minecraft.entity.projectile.EntityFireball) {
            if (entity instanceof net.minecraft.entity.projectile.EntitySmallFireball) { return new CraftSmallFireball(server, (net.minecraft.entity.projectile.EntitySmallFireball) entity); }
            else if (entity instanceof net.minecraft.entity.projectile.EntityLargeFireball) { return new CraftLargeFireball(server, (net.minecraft.entity.projectile.EntityLargeFireball) entity); }
            else if (entity instanceof net.minecraft.entity.projectile.EntityWitherSkull) { return new CraftWitherSkull(server, (net.minecraft.entity.projectile.EntityWitherSkull) entity); }
            else { return new CraftFireball(server, (net.minecraft.entity.projectile.EntityFireball) entity); }
        }
        else if (entity instanceof net.minecraft.entity.item.EntityEnderEye) { return new CraftEnderSignal(server, (net.minecraft.entity.item.EntityEnderEye) entity); }
        else if (entity instanceof net.minecraft.entity.item.EntityEnderCrystal) { return new CraftEnderCrystal(server, (net.minecraft.entity.item.EntityEnderCrystal) entity); }
        else if (entity instanceof net.minecraft.entity.projectile.EntityFishHook) { return new CraftFish(server, (net.minecraft.entity.projectile.EntityFishHook) entity); }
        else if (entity instanceof net.minecraft.entity.item.EntityItem) { return new CraftItem(server, (net.minecraft.entity.item.EntityItem) entity); }
        else if (entity instanceof net.minecraft.entity.effect.EntityWeatherEffect) {
            if (entity instanceof net.minecraft.entity.effect.EntityLightningBolt) { return new CraftLightningStrike(server, (net.minecraft.entity.effect.EntityLightningBolt) entity); }
            else { return new CraftWeather(server, (net.minecraft.entity.effect.EntityWeatherEffect) entity); }
        }
        else if (entity instanceof net.minecraft.entity.item.EntityMinecart) {
            if (entity instanceof net.minecraft.entity.item.EntityMinecartFurnace) { return new CraftMinecartFurnace(server, (net.minecraft.entity.item.EntityMinecartFurnace) entity); }
            else if (entity instanceof net.minecraft.entity.item.EntityMinecartChest) { return new CraftMinecartChest(server, (net.minecraft.entity.item.EntityMinecartChest) entity); }
            else if (entity instanceof net.minecraft.entity.item.EntityMinecartTNT) { return new CraftMinecartTNT(server, (net.minecraft.entity.item.EntityMinecartTNT) entity); }
            else if (entity instanceof net.minecraft.entity.item.EntityMinecartHopper) { return new CraftMinecartHopper(server, (net.minecraft.entity.item.EntityMinecartHopper) entity); }
            else if (entity instanceof net.minecraft.entity.ai.EntityMinecartMobSpawner) { return new CraftMinecartMobSpawner(server, (net.minecraft.entity.ai.EntityMinecartMobSpawner) entity); }
            else if (entity instanceof net.minecraft.entity.item.EntityMinecartEmpty) { return new CraftMinecartRideable(server, (net.minecraft.entity.item.EntityMinecartEmpty) entity); }
        } else if (entity instanceof net.minecraft.entity.EntityHanging) {
            if (entity instanceof net.minecraft.entity.item.EntityPainting) { return new CraftPainting(server, (net.minecraft.entity.item.EntityPainting) entity); }
            else if (entity instanceof net.minecraft.entity.item.EntityItemFrame) { return new CraftItemFrame(server, (net.minecraft.entity.item.EntityItemFrame) entity); }
            else { return new CraftHanging(server, (net.minecraft.entity.EntityHanging) entity); }
        }
        else if (entity instanceof net.minecraft.entity.item.EntityTNTPrimed) { return new CraftTNTPrimed(server, (net.minecraft.entity.item.EntityTNTPrimed) entity); }
        else if (entity instanceof net.minecraft.entity.item.EntityFireworkRocket) { return new CraftFirework(server, (net.minecraft.entity.item.EntityFireworkRocket) entity); }

        throw new AssertionError("Unknown entity " + entity == null ? null : entity.getClass());
    }

    public Location getLocation() {
        return new Location(getWorld(), entity.field_70165_t, entity.field_70163_u, entity.field_70161_v, entity.field_70177_z, entity.field_70125_A);
    }

    public Location getLocation(Location loc) {
        if (loc != null) {
            loc.setWorld(getWorld());
            loc.setX(entity.field_70165_t);
            loc.setY(entity.field_70163_u);
            loc.setZ(entity.field_70161_v);
            loc.setYaw(entity.field_70177_z);
            loc.setPitch(entity.field_70125_A);
        }

        return loc;
    }

    public Vector getVelocity() {
        return new Vector(entity.field_70159_w, entity.field_70181_x, entity.field_70179_y);
    }

    public void setVelocity(Vector vel) {
        entity.field_70159_w = vel.getX();
        entity.field_70181_x = vel.getY();
        entity.field_70179_y = vel.getZ();
        entity.field_70133_I = true;
    }

    public boolean isOnGround() {
        return entity.field_70122_E;
    }

    public World getWorld() {
        return ((net.minecraft.world.WorldServer) entity.field_70170_p).getWorld();
    }

    public boolean teleport(Location location) {
        return teleport(location, TeleportCause.PLUGIN);
    }

    public boolean teleport(Location location, TeleportCause cause) {
        if (entity.field_70154_o != null || entity.field_70153_n != null || entity.field_70128_L) {
            return false;
        }

        entity.field_70170_p = ((CraftWorld) location.getWorld()).getHandle();
        entity.func_70080_a(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        // entity.setLocation() throws no event, and so cannot be cancelled
        return true;
    }

    public boolean teleport(org.bukkit.entity.Entity destination) {
        return teleport(destination.getLocation());
    }

    public boolean teleport(org.bukkit.entity.Entity destination, TeleportCause cause) {
        return teleport(destination.getLocation(), cause);
    }

    public List<org.bukkit.entity.Entity> getNearbyEntities(double x, double y, double z) {
        @SuppressWarnings("unchecked")
        List<net.minecraft.entity.Entity> notchEntityList = entity.field_70170_p.func_72839_b(entity, entity.field_70121_D.func_72314_b(x, y, z));
        List<org.bukkit.entity.Entity> bukkitEntityList = new java.util.ArrayList<org.bukkit.entity.Entity>(notchEntityList.size());

        for (net.minecraft.entity.Entity e : notchEntityList) {
            bukkitEntityList.add(e.getBukkitEntity());
        }
        return bukkitEntityList;
    }

    public int getEntityId() {
        return entity.field_70157_k;
    }

    public int getFireTicks() {
        return entity.field_70151_c;
    }

    public int getMaxFireTicks() {
        return entity.field_70174_ab;
    }

    public void setFireTicks(int ticks) {
        entity.field_70151_c = ticks;
    }

    public void remove() {
        entity.field_70128_L = true;
    }

    public boolean isDead() {
        return !entity.func_70089_S();
    }

    public boolean isValid() {
        return entity.func_70089_S() && entity.valid;
    }

    public Server getServer() {
        return server;
    }

    public Vector getMomentum() {
        return getVelocity();
    }

    public void setMomentum(Vector value) {
        setVelocity(value);
    }

    public org.bukkit.entity.Entity getPassenger() {
        return isEmpty() ? null : (CraftEntity) getHandle().field_70153_n.getBukkitEntity();
    }

    public boolean setPassenger(org.bukkit.entity.Entity passenger) {
        if (passenger instanceof CraftEntity) {
            ((CraftEntity) passenger).getHandle().setPassengerOf(getHandle());
            return true;
        } else {
            return false;
        }
    }

    public boolean isEmpty() {
        return getHandle().field_70153_n == null;
    }

    public boolean eject() {
        if (getHandle().field_70153_n == null) {
            return false;
        }

        getHandle().field_70153_n.setPassengerOf(null);
        return true;
    }

    public float getFallDistance() {
        return getHandle().field_70143_R;
    }

    public void setFallDistance(float distance) {
        getHandle().field_70143_R = distance;
    }

    public void setLastDamageCause(EntityDamageEvent event) {
        lastDamageEvent = event;
    }

    public EntityDamageEvent getLastDamageCause() {
        return lastDamageEvent;
    }

    public UUID getUniqueId() {
        return getHandle().field_96093_i;
    }

    public int getTicksLived() {
        return getHandle().field_70173_aa;
    }

    public void setTicksLived(int value) {
        if (value <= 0) {
            throw new IllegalArgumentException("Age must be at least 1 tick");
        }
        getHandle().field_70173_aa = value;
    }

    public net.minecraft.entity.Entity getHandle() {
        return entity;
    }

    public void playEffect(EntityEffect type) {
        this.getHandle().field_70170_p.func_72960_a(getHandle(), type.getData());
    }

    public void setHandle(final net.minecraft.entity.Entity entity) {
        this.entity = entity;
    }

    @Override
    public String toString() {
        return "CraftEntity{" + "id=" + getEntityId() + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CraftEntity other = (CraftEntity) obj;
        return (this.getEntityId() == other.getEntityId());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.getEntityId();
        return hash;
    }

    public void setMetadata(String metadataKey, MetadataValue newMetadataValue) {
        server.getEntityMetadata().setMetadata(this, metadataKey, newMetadataValue);
    }

    public List<MetadataValue> getMetadata(String metadataKey) {
        return server.getEntityMetadata().getMetadata(this, metadataKey);
    }

    public boolean hasMetadata(String metadataKey) {
        return server.getEntityMetadata().hasMetadata(this, metadataKey);
    }

    public void removeMetadata(String metadataKey, Plugin owningPlugin) {
        server.getEntityMetadata().removeMetadata(this, metadataKey, owningPlugin);
    }

    public boolean isInsideVehicle() {
        return getHandle().field_70154_o != null;
    }

    public boolean leaveVehicle() {
        if (getHandle().field_70154_o == null) {
            return false;
        }

        getHandle().setPassengerOf(null);
        return true;
    }

    public org.bukkit.entity.Entity getVehicle() {
        if (getHandle().field_70154_o == null) {
            return null;
        }

        return getHandle().field_70154_o.getBukkitEntity();
    }
}
