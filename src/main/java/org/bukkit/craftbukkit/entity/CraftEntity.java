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
    protected net.minecraft.entity.Entity/*was:Entity*/ entity;
    private EntityDamageEvent lastDamageEvent;

    public CraftEntity(final CraftServer server, final net.minecraft.entity.Entity/*was:Entity*/ entity) {
        this.server = server;
        this.entity = entity;
    }

    public static CraftEntity getEntity(CraftServer server, net.minecraft.entity.Entity/*was:Entity*/ entity) {
        /**
         * Order is *EXTREMELY* important -- keep it right! =D
         */
        if (entity instanceof net.minecraft.entity.EntityLiving/*was:EntityLiving*/) {
            // Players
            if (entity instanceof net.minecraft.entity.player.EntityPlayer/*was:EntityHuman*/) {
                if (entity instanceof net.minecraft.entity.player.EntityPlayerMP/*was:EntityPlayer*/) { return new CraftPlayer(server, (net.minecraft.entity.player.EntityPlayerMP/*was:EntityPlayer*/) entity); }
                else { return new CraftHumanEntity(server, (net.minecraft.entity.player.EntityPlayer/*was:EntityHuman*/) entity); }
            }
            else if (entity instanceof net.minecraft.entity.EntityCreature/*was:EntityCreature*/) {
                // Animals
                if (entity instanceof net.minecraft.entity.passive.EntityAnimal/*was:EntityAnimal*/) {
                    if (entity instanceof net.minecraft.entity.passive.EntityChicken/*was:EntityChicken*/) { return new CraftChicken(server, (net.minecraft.entity.passive.EntityChicken/*was:EntityChicken*/) entity); }
                    else if (entity instanceof net.minecraft.entity.passive.EntityCow/*was:EntityCow*/) {
                        if (entity instanceof net.minecraft.entity.passive.EntityMooshroom/*was:EntityMushroomCow*/) { return new CraftMushroomCow(server, (net.minecraft.entity.passive.EntityMooshroom/*was:EntityMushroomCow*/) entity); }
                        else { return new CraftCow(server, (net.minecraft.entity.passive.EntityCow/*was:EntityCow*/) entity); }
                    }
                    else if (entity instanceof net.minecraft.entity.passive.EntityPig/*was:EntityPig*/) { return new CraftPig(server, (net.minecraft.entity.passive.EntityPig/*was:EntityPig*/) entity); }
                    else if (entity instanceof net.minecraft.entity.passive.EntityTameable/*was:EntityTameableAnimal*/) {
                        if (entity instanceof net.minecraft.entity.passive.EntityWolf/*was:EntityWolf*/) { return new CraftWolf(server, (net.minecraft.entity.passive.EntityWolf/*was:EntityWolf*/) entity); }
                        else if (entity instanceof net.minecraft.entity.passive.EntityOcelot/*was:EntityOcelot*/) { return new CraftOcelot(server, (net.minecraft.entity.passive.EntityOcelot/*was:EntityOcelot*/) entity); }
                    }
                    else if (entity instanceof net.minecraft.entity.passive.EntitySheep/*was:EntitySheep*/) { return new CraftSheep(server, (net.minecraft.entity.passive.EntitySheep/*was:EntitySheep*/) entity); }
                    else  { return new CraftAnimals(server, (net.minecraft.entity.passive.EntityAnimal/*was:EntityAnimal*/) entity); }
                }
                // Monsters
                else if (entity instanceof net.minecraft.entity.monster.EntityMob/*was:EntityMonster*/) {
                    if (entity instanceof net.minecraft.entity.monster.EntityZombie/*was:EntityZombie*/) {
                        if (entity instanceof net.minecraft.entity.monster.EntityPigZombie/*was:EntityPigZombie*/) { return new CraftPigZombie(server, (net.minecraft.entity.monster.EntityPigZombie/*was:EntityPigZombie*/) entity); }
                        else { return new CraftZombie(server, (net.minecraft.entity.monster.EntityZombie/*was:EntityZombie*/) entity); }
                    }
                    else if (entity instanceof net.minecraft.entity.monster.EntityCreeper/*was:EntityCreeper*/) { return new CraftCreeper(server, (net.minecraft.entity.monster.EntityCreeper/*was:EntityCreeper*/) entity); }
                    else if (entity instanceof net.minecraft.entity.monster.EntityEnderman/*was:EntityEnderman*/) { return new CraftEnderman(server, (net.minecraft.entity.monster.EntityEnderman/*was:EntityEnderman*/) entity); }
                    else if (entity instanceof net.minecraft.entity.monster.EntitySilverfish/*was:EntitySilverfish*/) { return new CraftSilverfish(server, (net.minecraft.entity.monster.EntitySilverfish/*was:EntitySilverfish*/) entity); }
                    else if (entity instanceof net.minecraft.entity.monster.EntityGiantZombie/*was:EntityGiantZombie*/) { return new CraftGiant(server, (net.minecraft.entity.monster.EntityGiantZombie/*was:EntityGiantZombie*/) entity); }
                    else if (entity instanceof net.minecraft.entity.monster.EntitySkeleton/*was:EntitySkeleton*/) { return new CraftSkeleton(server, (net.minecraft.entity.monster.EntitySkeleton/*was:EntitySkeleton*/) entity); }
                    else if (entity instanceof net.minecraft.entity.monster.EntityBlaze/*was:EntityBlaze*/) { return new CraftBlaze(server, (net.minecraft.entity.monster.EntityBlaze/*was:EntityBlaze*/) entity); }
                    else if (entity instanceof net.minecraft.entity.monster.EntityWitch/*was:EntityWitch*/) { return new CraftWitch(server, (net.minecraft.entity.monster.EntityWitch/*was:EntityWitch*/) entity); }
                    else if (entity instanceof net.minecraft.entity.boss.EntityWither/*was:EntityWither*/) { return new CraftWither(server, (net.minecraft.entity.boss.EntityWither/*was:EntityWither*/) entity); }
                    else if (entity instanceof net.minecraft.entity.monster.EntitySpider/*was:EntitySpider*/) {
                        if (entity instanceof net.minecraft.entity.monster.EntityCaveSpider/*was:EntityCaveSpider*/) { return new CraftCaveSpider(server, (net.minecraft.entity.monster.EntityCaveSpider/*was:EntityCaveSpider*/) entity); }
                        else { return new CraftSpider(server, (net.minecraft.entity.monster.EntitySpider/*was:EntitySpider*/) entity); }
                    }

                    else  { return new CraftMonster(server, (net.minecraft.entity.monster.EntityMob/*was:EntityMonster*/) entity); }
                }
                // Water Animals
                else if (entity instanceof net.minecraft.entity.passive.EntityWaterMob/*was:EntityWaterAnimal*/) {
                    if (entity instanceof net.minecraft.entity.passive.EntitySquid/*was:EntitySquid*/) { return new CraftSquid(server, (net.minecraft.entity.passive.EntitySquid/*was:EntitySquid*/) entity); }
                    else { return new CraftWaterMob(server, (net.minecraft.entity.passive.EntityWaterMob/*was:EntityWaterAnimal*/) entity); }
                }
                else if (entity instanceof net.minecraft.entity.monster.EntityGolem/*was:EntityGolem*/) {
                    if (entity instanceof net.minecraft.entity.monster.EntitySnowman/*was:EntitySnowman*/) { return new CraftSnowman(server, (net.minecraft.entity.monster.EntitySnowman/*was:EntitySnowman*/) entity); }
                    else if (entity instanceof net.minecraft.entity.monster.EntityIronGolem/*was:EntityIronGolem*/) { return new CraftIronGolem(server, (net.minecraft.entity.monster.EntityIronGolem/*was:EntityIronGolem*/) entity); }
                }
                else if (entity instanceof net.minecraft.entity.passive.EntityVillager/*was:EntityVillager*/) { return new CraftVillager(server, (net.minecraft.entity.passive.EntityVillager/*was:EntityVillager*/) entity); }
                else { return new CraftCreature(server, (net.minecraft.entity.EntityCreature/*was:EntityCreature*/) entity); }
            }
            // Slimes are a special (and broken) case
            else if (entity instanceof net.minecraft.entity.monster.EntitySlime/*was:EntitySlime*/) {
                if (entity instanceof net.minecraft.entity.monster.EntityMagmaCube/*was:EntityMagmaCube*/) { return new CraftMagmaCube(server, (net.minecraft.entity.monster.EntityMagmaCube/*was:EntityMagmaCube*/) entity); }
                else { return new CraftSlime(server, (net.minecraft.entity.monster.EntitySlime/*was:EntitySlime*/) entity); }
            }
            // Flying
            else if (entity instanceof net.minecraft.entity.EntityFlying/*was:EntityFlying*/) {
                if (entity instanceof net.minecraft.entity.monster.EntityGhast/*was:EntityGhast*/) { return new CraftGhast(server, (net.minecraft.entity.monster.EntityGhast/*was:EntityGhast*/) entity); }
                else { return new CraftFlying(server, (net.minecraft.entity.EntityFlying/*was:EntityFlying*/) entity); }
            }
            else if (entity instanceof net.minecraft.entity.boss.EntityDragon/*was:EntityEnderDragon*/) {
                return new CraftEnderDragon(server, (net.minecraft.entity.boss.EntityDragon/*was:EntityEnderDragon*/) entity);
            }
            // Ambient
            else if (entity instanceof net.minecraft.entity.passive.EntityAmbientCreature/*was:EntityAmbient*/) {
                if (entity instanceof net.minecraft.entity.passive.EntityBat/*was:EntityBat*/) { return new CraftBat(server, (net.minecraft.entity.passive.EntityBat/*was:EntityBat*/) entity); }
                else { return new CraftAmbient(server, (net.minecraft.entity.passive.EntityAmbientCreature/*was:EntityAmbient*/) entity); }
            }
            else  { return new CraftLivingEntity(server, (net.minecraft.entity.EntityLiving/*was:EntityLiving*/) entity); }
        }
        else if (entity instanceof net.minecraft.entity.boss.EntityDragonPart/*was:EntityComplexPart*/) {
            net.minecraft.entity.boss.EntityDragonPart/*was:EntityComplexPart*/ part = (net.minecraft.entity.boss.EntityDragonPart/*was:EntityComplexPart*/) entity;
            if (part.field_70259_a/*was:owner*/ instanceof net.minecraft.entity.boss.EntityDragon/*was:EntityEnderDragon*/) { return new CraftEnderDragonPart(server, (net.minecraft.entity.boss.EntityDragonPart/*was:EntityComplexPart*/) entity); }
            else { return new CraftComplexPart(server, (net.minecraft.entity.boss.EntityDragonPart/*was:EntityComplexPart*/) entity); }
        }
        else if (entity instanceof net.minecraft.entity.item.EntityXPOrb/*was:EntityExperienceOrb*/) { return new CraftExperienceOrb(server, (net.minecraft.entity.item.EntityXPOrb/*was:EntityExperienceOrb*/) entity); }
        else if (entity instanceof net.minecraft.entity.projectile.EntityArrow/*was:EntityArrow*/) { return new CraftArrow(server, (net.minecraft.entity.projectile.EntityArrow/*was:EntityArrow*/) entity); }
        else if (entity instanceof net.minecraft.entity.item.EntityBoat/*was:EntityBoat*/) { return new CraftBoat(server, (net.minecraft.entity.item.EntityBoat/*was:EntityBoat*/) entity); }
        else if (entity instanceof net.minecraft.entity.projectile.EntityThrowable/*was:EntityProjectile*/) {
            if (entity instanceof net.minecraft.entity.projectile.EntityEgg/*was:EntityEgg*/) { return new CraftEgg(server, (net.minecraft.entity.projectile.EntityEgg/*was:EntityEgg*/) entity); }
            else if (entity instanceof net.minecraft.entity.projectile.EntitySnowball/*was:EntitySnowball*/) { return new CraftSnowball(server, (net.minecraft.entity.projectile.EntitySnowball/*was:EntitySnowball*/) entity); }
            else if (entity instanceof net.minecraft.entity.projectile.EntityPotion/*was:EntityPotion*/) { return new CraftThrownPotion(server, (net.minecraft.entity.projectile.EntityPotion/*was:EntityPotion*/) entity); }
            else if (entity instanceof net.minecraft.entity.item.EntityEnderPearl/*was:EntityEnderPearl*/) { return new CraftEnderPearl(server, (net.minecraft.entity.item.EntityEnderPearl/*was:EntityEnderPearl*/) entity); }
            else if (entity instanceof net.minecraft.entity.item.EntityExpBottle/*was:EntityThrownExpBottle*/) { return new CraftThrownExpBottle(server, (net.minecraft.entity.item.EntityExpBottle/*was:EntityThrownExpBottle*/) entity); }
        }
        else if (entity instanceof net.minecraft.entity.item.EntityFallingSand/*was:EntityFallingBlock*/) { return new CraftFallingSand(server, (net.minecraft.entity.item.EntityFallingSand/*was:EntityFallingBlock*/) entity); }
        else if (entity instanceof net.minecraft.entity.projectile.EntityFireball/*was:EntityFireball*/) {
            if (entity instanceof net.minecraft.entity.projectile.EntitySmallFireball/*was:EntitySmallFireball*/) { return new CraftSmallFireball(server, (net.minecraft.entity.projectile.EntitySmallFireball/*was:EntitySmallFireball*/) entity); }
            else if (entity instanceof net.minecraft.entity.projectile.EntityLargeFireball/*was:EntityLargeFireball*/) { return new CraftLargeFireball(server, (net.minecraft.entity.projectile.EntityLargeFireball/*was:EntityLargeFireball*/) entity); }
            else if (entity instanceof net.minecraft.entity.projectile.EntityWitherSkull/*was:EntityWitherSkull*/) { return new CraftWitherSkull(server, (net.minecraft.entity.projectile.EntityWitherSkull/*was:EntityWitherSkull*/) entity); }
            else { return new CraftFireball(server, (net.minecraft.entity.projectile.EntityFireball/*was:EntityFireball*/) entity); }
        }
        else if (entity instanceof net.minecraft.entity.item.EntityEnderEye/*was:EntityEnderSignal*/) { return new CraftEnderSignal(server, (net.minecraft.entity.item.EntityEnderEye/*was:EntityEnderSignal*/) entity); }
        else if (entity instanceof net.minecraft.entity.item.EntityEnderCrystal/*was:EntityEnderCrystal*/) { return new CraftEnderCrystal(server, (net.minecraft.entity.item.EntityEnderCrystal/*was:EntityEnderCrystal*/) entity); }
        else if (entity instanceof net.minecraft.entity.projectile.EntityFishHook/*was:EntityFishingHook*/) { return new CraftFish(server, (net.minecraft.entity.projectile.EntityFishHook/*was:EntityFishingHook*/) entity); }
        else if (entity instanceof net.minecraft.entity.item.EntityItem/*was:EntityItem*/) { return new CraftItem(server, (net.minecraft.entity.item.EntityItem/*was:EntityItem*/) entity); }
        else if (entity instanceof net.minecraft.entity.effect.EntityWeatherEffect/*was:EntityWeather*/) {
            if (entity instanceof net.minecraft.entity.effect.EntityLightningBolt/*was:EntityLightning*/) { return new CraftLightningStrike(server, (net.minecraft.entity.effect.EntityLightningBolt/*was:EntityLightning*/) entity); }
            else { return new CraftWeather(server, (net.minecraft.entity.effect.EntityWeatherEffect/*was:EntityWeather*/) entity); }
        }
        else if (entity instanceof net.minecraft.entity.item.EntityMinecart/*was:EntityMinecart*/) {
            net.minecraft.entity.item.EntityMinecart/*was:EntityMinecart*/ mc = (net.minecraft.entity.item.EntityMinecart/*was:EntityMinecart*/) entity;
            if (mc.field_70505_a/*was:type*/ == CraftMinecart.Type.StorageMinecart.getId()) { return new CraftStorageMinecart(server, mc); }
            else if (mc.field_70505_a/*was:type*/ == CraftMinecart.Type.PoweredMinecart.getId()) { return new CraftPoweredMinecart(server, mc); }
            else { return new CraftMinecart(server, mc); }
        }
        else if (entity instanceof net.minecraft.entity.EntityHanging/*was:EntityHanging*/) {
            if (entity instanceof net.minecraft.entity.item.EntityPainting/*was:EntityPainting*/) { return new CraftPainting(server, (net.minecraft.entity.item.EntityPainting/*was:EntityPainting*/) entity); }
            else if (entity instanceof net.minecraft.entity.item.EntityItemFrame/*was:EntityItemFrame*/) { return new CraftItemFrame(server, (net.minecraft.entity.item.EntityItemFrame/*was:EntityItemFrame*/) entity); }
            else { return new CraftHanging(server, (net.minecraft.entity.EntityHanging/*was:EntityHanging*/) entity); }
        }
        else if (entity instanceof net.minecraft.entity.item.EntityTNTPrimed/*was:EntityTNTPrimed*/) { return new CraftTNTPrimed(server, (net.minecraft.entity.item.EntityTNTPrimed/*was:EntityTNTPrimed*/) entity); }
        else if (entity instanceof net.minecraft.entity.item.EntityFireworkRocket/*was:EntityFireworks*/) { return new CraftFirework(server, (net.minecraft.entity.item.EntityFireworkRocket/*was:EntityFireworks*/) entity); }

        throw new IllegalArgumentException("Unknown entity");
    }

    public Location getLocation() {
        return new Location(getWorld(), entity.field_70165_t/*was:locX*/, entity.field_70163_u/*was:locY*/, entity.field_70161_v/*was:locZ*/, entity.field_70177_z/*was:yaw*/, entity.field_70125_A/*was:pitch*/);
    }

    public Location getLocation(Location loc) {
        if (loc != null) {
            loc.setWorld(getWorld());
            loc.setX(entity.field_70165_t/*was:locX*/);
            loc.setY(entity.field_70163_u/*was:locY*/);
            loc.setZ(entity.field_70161_v/*was:locZ*/);
            loc.setYaw(entity.field_70177_z/*was:yaw*/);
            loc.setPitch(entity.field_70125_A/*was:pitch*/);
        }

        return loc;
    }

    public Vector getVelocity() {
        return new Vector(entity.field_70159_w/*was:motX*/, entity.field_70181_x/*was:motY*/, entity.field_70179_y/*was:motZ*/);
    }

    public void setVelocity(Vector vel) {
        entity.field_70159_w/*was:motX*/ = vel.getX();
        entity.field_70181_x/*was:motY*/ = vel.getY();
        entity.field_70179_y/*was:motZ*/ = vel.getZ();
        entity.field_70133_I/*was:velocityChanged*/ = true;
    }

    public World getWorld() {
        return ((net.minecraft.world.WorldServer/*was:WorldServer*/) entity.field_70170_p/*was:world*/).getWorld();
    }

    public boolean teleport(Location location) {
        return teleport(location, TeleportCause.PLUGIN);
    }

    public boolean teleport(Location location, TeleportCause cause) {
        if (entity.field_70154_o/*was:vehicle*/ != null || entity.field_70153_n/*was:passenger*/ != null || entity.field_70128_L/*was:dead*/) {
            return false;
        }

        entity.field_70170_p/*was:world*/ = ((CraftWorld) location.getWorld()).getHandle();
        entity.func_70080_a/*was:setLocation*/(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
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
        List<net.minecraft.entity.Entity/*was:Entity*/> notchEntityList = entity.field_70170_p/*was:world*/.func_72839_b/*was:getEntities*/(entity, entity.field_70121_D/*was:boundingBox*/.func_72314_b/*was:grow*/(x, y, z));
        List<org.bukkit.entity.Entity> bukkitEntityList = new java.util.ArrayList<org.bukkit.entity.Entity>(notchEntityList.size());

        for (net.minecraft.entity.Entity/*was:Entity*/ e : notchEntityList) {
            bukkitEntityList.add(e.getBukkitEntity());
        }
        return bukkitEntityList;
    }

    public int getEntityId() {
        return entity.field_70157_k/*was:id*/;
    }

    public int getFireTicks() {
        return entity.field_70151_c/*was:fireTicks*/;
    }

    public int getMaxFireTicks() {
        return entity.field_70174_ab/*was:maxFireTicks*/;
    }

    public void setFireTicks(int ticks) {
        entity.field_70151_c/*was:fireTicks*/ = ticks;
    }

    public void remove() {
        entity.field_70128_L/*was:dead*/ = true;
    }

    public boolean isDead() {
        return !entity.func_70089_S/*was:isAlive*/();
    }

    public boolean isValid() {
        return entity.func_70089_S/*was:isAlive*/() && entity.valid;
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
        return isEmpty() ? null : (CraftEntity) getHandle().field_70153_n/*was:passenger*/.getBukkitEntity();
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
        return getHandle().field_70153_n/*was:passenger*/ == null;
    }

    public boolean eject() {
        if (getHandle().field_70153_n/*was:passenger*/ == null) {
            return false;
        }

        getHandle().field_70153_n/*was:passenger*/.setPassengerOf(null);
        return true;
    }

    public float getFallDistance() {
        return getHandle().field_70143_R/*was:fallDistance*/;
    }

    public void setFallDistance(float distance) {
        getHandle().field_70143_R/*was:fallDistance*/ = distance;
    }

    public void setLastDamageCause(EntityDamageEvent event) {
        lastDamageEvent = event;
    }

    public EntityDamageEvent getLastDamageCause() {
        return lastDamageEvent;
    }

    public UUID getUniqueId() {
        return getHandle().uniqueId;
    }

    public int getTicksLived() {
        return getHandle().field_70173_aa/*was:ticksLived*/;
    }

    public void setTicksLived(int value) {
        if (value <= 0) {
            throw new IllegalArgumentException("Age must be at least 1 tick");
        }
        getHandle().field_70173_aa/*was:ticksLived*/ = value;
    }

    public net.minecraft.entity.Entity/*was:Entity*/ getHandle() {
        return entity;
    }

    public void playEffect(EntityEffect type) {
        this.getHandle().field_70170_p/*was:world*/.func_72960_a/*was:broadcastEntityEffect*/(getHandle(), type.getData());
    }

    public void setHandle(final net.minecraft.entity.Entity/*was:Entity*/ entity) {
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
        return getHandle().field_70154_o/*was:vehicle*/ != null;
    }

    public boolean leaveVehicle() {
        if (getHandle().field_70154_o/*was:vehicle*/ == null) {
            return false;
        }

        getHandle().setPassengerOf(null);
        return true;
    }

    public org.bukkit.entity.Entity getVehicle() {
        if (getHandle().field_70154_o/*was:vehicle*/ == null) {
            return null;
        }

        return getHandle().field_70154_o/*was:vehicle*/.getBukkitEntity();
    }
}
