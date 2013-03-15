package org.bukkit.craftbukkit.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;


import org.apache.commons.lang.Validate;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.inventory.CraftEntityEquipment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

public class CraftLivingEntity extends CraftEntity implements LivingEntity {
    private CraftEntityEquipment equipment;

    public CraftLivingEntity(final CraftServer server, final net.minecraft.entity.EntityLiving entity) {
        super(server, entity);

        if (!(this instanceof HumanEntity)) {
            equipment = new CraftEntityEquipment(this);
        }
    }

    public int getHealth() {
        return Math.min(Math.max(0, getHandle().func_70630_aN()), getMaxHealth());
    }

    public void setHealth(int health) {
        if ((health < 0) || (health > getMaxHealth())) {
            throw new IllegalArgumentException("Health must be between 0 and " + getMaxHealth());
        }

        if (entity instanceof net.minecraft.entity.player.EntityPlayerMP && health == 0) {
            ((net.minecraft.entity.player.EntityPlayerMP) entity).func_70645_a(net.minecraft.util.DamageSource.field_76377_j);
        }

        getHandle().func_70606_j(health);
    }

    public int getMaxHealth() {
        return getHandle().maxHealth;
    }

    public void setMaxHealth(int amount) {
        Validate.isTrue(amount > 0, "Max health must be greater than 0");

        getHandle().maxHealth = amount;

        if (getHealth() > amount) {
            setHealth(amount);
        }
    }

    public void resetMaxHealth() {
        setMaxHealth(getHandle().func_70667_aM());
    }

    @Deprecated
    public Egg throwEgg() {
        return launchProjectile(Egg.class);
    }

    @Deprecated
    public Snowball throwSnowball() {
        return launchProjectile(Snowball.class);
    }

    public double getEyeHeight() {
        return getHandle().func_70047_e();
    }

    public double getEyeHeight(boolean ignoreSneaking) {
        return getEyeHeight();
    }

    private List<Block> getLineOfSight(HashSet<Byte> transparent, int maxDistance, int maxLength) {
        if (maxDistance > 120) {
            maxDistance = 120;
        }
        ArrayList<Block> blocks = new ArrayList<Block>();
        Iterator<Block> itr = new BlockIterator(this, maxDistance);
        while (itr.hasNext()) {
            Block block = itr.next();
            blocks.add(block);
            if (maxLength != 0 && blocks.size() > maxLength) {
                blocks.remove(0);
            }
            int id = block.getTypeId();
            if (transparent == null) {
                if (id != 0) {
                    break;
                }
            } else {
                if (!transparent.contains((byte) id)) {
                    break;
                }
            }
        }
        return blocks;
    }

    public List<Block> getLineOfSight(HashSet<Byte> transparent, int maxDistance) {
        return getLineOfSight(transparent, maxDistance, 0);
    }

    public Block getTargetBlock(HashSet<Byte> transparent, int maxDistance) {
        List<Block> blocks = getLineOfSight(transparent, maxDistance, 1);
        return blocks.get(0);
    }

    public List<Block> getLastTwoTargetBlocks(HashSet<Byte> transparent, int maxDistance) {
        return getLineOfSight(transparent, maxDistance, 2);
    }

    @Deprecated
    public Arrow shootArrow() {
        return launchProjectile(Arrow.class);
    }

    public int getRemainingAir() {
        return getHandle().func_70086_ai();
    }

    public void setRemainingAir(int ticks) {
        getHandle().func_70050_g(ticks);
    }

    public int getMaximumAir() {
        return getHandle().maxAirTicks;
    }

    public void setMaximumAir(int ticks) {
        getHandle().maxAirTicks = ticks;
    }

    public void damage(int amount) {
        damage(amount, null);
    }

    public void damage(int amount, org.bukkit.entity.Entity source) {
        net.minecraft.util.DamageSource reason = net.minecraft.util.DamageSource.field_76377_j;

        if (source instanceof HumanEntity) {
            reason = net.minecraft.util.DamageSource.func_76365_a(((CraftHumanEntity) source).getHandle());
        } else if (source instanceof LivingEntity) {
            reason = net.minecraft.util.DamageSource.func_76358_a(((CraftLivingEntity) source).getHandle());
        }

        if (entity instanceof net.minecraft.entity.boss.EntityDragon) {
            ((net.minecraft.entity.boss.EntityDragon) entity).func_82195_e(reason, amount);
        } else {
            entity.func_70097_a(reason, amount);
        }
    }

    public Location getEyeLocation() {
        Location loc = getLocation();
        loc.setY(loc.getY() + getEyeHeight());
        return loc;
    }

    public int getMaximumNoDamageTicks() {
        return getHandle().field_70771_an;
    }

    public void setMaximumNoDamageTicks(int ticks) {
        getHandle().field_70771_an = ticks;
    }

    public int getLastDamage() {
        return getHandle().field_70707_bp;
    }

    public void setLastDamage(int damage) {
        getHandle().field_70707_bp = damage;
    }

    public int getNoDamageTicks() {
        return getHandle().field_70172_ad;
    }

    public void setNoDamageTicks(int ticks) {
        getHandle().field_70172_ad = ticks;
    }

    @Override
    public net.minecraft.entity.EntityLiving getHandle() {
        return (net.minecraft.entity.EntityLiving) entity;
    }

    public void setHandle(final net.minecraft.entity.EntityLiving entity) {
        super.setHandle(entity);
    }

    @Override
    public String toString() {
        return "CraftLivingEntity{" + "id=" + getEntityId() + '}';
    }

    public Player getKiller() {
        return getHandle().field_70717_bb == null ? null : (Player) getHandle().field_70717_bb.getBukkitEntity();
    }

    public boolean addPotionEffect(PotionEffect effect) {
        return addPotionEffect(effect, false);
    }

    public boolean addPotionEffect(PotionEffect effect, boolean force) {
        if (hasPotionEffect(effect.getType())) {
            if (!force) {
                return false;
            }
            removePotionEffect(effect.getType());
        }
        getHandle().func_70690_d(new net.minecraft.potion.PotionEffect(effect.getType().getId(), effect.getDuration(), effect.getAmplifier()));
        return true;
    }

    public boolean addPotionEffects(Collection<PotionEffect> effects) {
        boolean success = true;
        for (PotionEffect effect : effects) {
            success &= addPotionEffect(effect);
        }
        return success;
    }

    public boolean hasPotionEffect(PotionEffectType type) {
        return getHandle().func_70644_a(net.minecraft.potion.Potion.field_76425_a[type.getId()]);
    }

    public void removePotionEffect(PotionEffectType type) {
        getHandle().field_70713_bf.remove(type.getId());
        getHandle().field_70752_e = true;
        if (getHandle() instanceof net.minecraft.entity.player.EntityPlayerMP) {
            if (((net.minecraft.entity.player.EntityPlayerMP) getHandle()).field_71135_a == null) return;
            ((net.minecraft.entity.player.EntityPlayerMP) getHandle()).field_71135_a.func_72567_b(new net.minecraft.network.packet.Packet42RemoveEntityEffect(getHandle().field_70157_k, new net.minecraft.potion.PotionEffect(type.getId(), 0, 0)));
        }
    }

    public Collection<PotionEffect> getActivePotionEffects() {
        List<PotionEffect> effects = new ArrayList<PotionEffect>();
        for (Object raw : getHandle().field_70713_bf.values()) {
            if (!(raw instanceof net.minecraft.potion.PotionEffect))
                continue;
            net.minecraft.potion.PotionEffect handle = (net.minecraft.potion.PotionEffect) raw;
            effects.add(new PotionEffect(PotionEffectType.getById(handle.func_76456_a()), handle.func_76459_b(), handle.func_76458_c()));
        }
        return effects;
    }

    @SuppressWarnings("unchecked")
    public <T extends Projectile> T launchProjectile(Class<? extends T> projectile) {
        net.minecraft.world.World world = ((CraftWorld) getWorld()).getHandle();
        net.minecraft.entity.Entity launch = null;

        if (Snowball.class.isAssignableFrom(projectile)) {
            launch = new net.minecraft.entity.projectile.EntitySnowball(world, getHandle());
        } else if (Egg.class.isAssignableFrom(projectile)) {
            launch = new net.minecraft.entity.projectile.EntityEgg(world, getHandle());
        } else if (EnderPearl.class.isAssignableFrom(projectile)) {
            launch = new net.minecraft.entity.item.EntityEnderPearl(world, getHandle());
        } else if (Arrow.class.isAssignableFrom(projectile)) {
            launch = new net.minecraft.entity.projectile.EntityArrow(world, getHandle(), 1);
        } else if (Fireball.class.isAssignableFrom(projectile)) {
            Location location = getEyeLocation();
            Vector direction = location.getDirection().multiply(10);

            if (SmallFireball.class.isAssignableFrom(projectile)) {
                launch = new net.minecraft.entity.projectile.EntitySmallFireball(world, getHandle(), direction.getX(), direction.getY(), direction.getZ());
            } else if (WitherSkull.class.isAssignableFrom(projectile)) {
                launch = new net.minecraft.entity.projectile.EntityWitherSkull(world, getHandle(), direction.getX(), direction.getY(), direction.getZ());
            } else {
                launch = new net.minecraft.entity.projectile.EntityLargeFireball(world, getHandle(), direction.getX(), direction.getY(), direction.getZ());
            }

            launch.func_70012_b(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        }

        Validate.notNull(launch, "Projectile not supported");

        world.func_72838_d(launch);
        return (T) launch.getBukkitEntity();
    }

    public EntityType getType() {
        return EntityType.UNKNOWN;
    }

    public boolean hasLineOfSight(Entity other) {
        return getHandle().func_70635_at().func_75522_a(((CraftEntity) other).getHandle()); // az should be getEntitySenses
    }

    public boolean getRemoveWhenFarAway() {
        return !getHandle().field_82179_bU;
    }

    public void setRemoveWhenFarAway(boolean remove) {
        getHandle().field_82179_bU = !remove;
    }

    public EntityEquipment getEquipment() {
        return equipment;
    }

    public void setCanPickupItems(boolean pickup) {
        getHandle().field_82172_bs = pickup;
    }

    public boolean getCanPickupItems() {
        return getHandle().field_82172_bs;
    }

    @Override
    public boolean teleport(Location location, PlayerTeleportEvent.TeleportCause cause) {
        if (getHealth() == 0) {
            return false;
        }

        return super.teleport(location, cause);
    }

    public void setCustomName(String name) {
        if (name == null) {
            name = "";
        }

        getHandle().func_94058_c(name);
    }

    public String getCustomName() {
        String name = getHandle().func_94057_bL();

        if (name == null || name.length() == 0) {
            return null;
        }

        return name;
    }

    public void setCustomNameVisible(boolean flag) {
        getHandle().func_94061_f(flag);
    }

    public boolean isCustomNameVisible() {
        return getHandle().func_94062_bN();
    }
}
