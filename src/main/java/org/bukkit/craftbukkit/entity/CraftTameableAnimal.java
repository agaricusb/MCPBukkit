package org.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Tameable;

public class CraftTameableAnimal extends CraftAnimals implements Tameable, Creature {
    public CraftTameableAnimal(CraftServer server, net.minecraft.entity.passive.EntityTameable entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.entity.passive.EntityTameable getHandle() {
        return (net.minecraft.entity.passive.EntityTameable)super.getHandle();
    }

    public AnimalTamer getOwner() {
        if (("").equals(getOwnerName())) return null;

        AnimalTamer owner = getServer().getPlayerExact(getOwnerName());
        if (owner == null) {
            owner = getServer().getOfflinePlayer(getOwnerName());
        }

        return owner;
    }

    public String getOwnerName() {
        return getHandle().func_70905_p();
    }

    public boolean isTamed() {
        return getHandle().func_70909_n();
    }

    public void setOwner(AnimalTamer tamer) {
        if (tamer != null) {
            setTamed(true);
            getHandle().func_70778_a(null);
            setOwnerName(tamer.getName());
        } else {
            setTamed(false);
            setOwnerName("");
        }
    }

    public void setOwnerName(String ownerName) {
        getHandle().func_70910_a(ownerName == null ? "" : ownerName);
    }

    public void setTamed(boolean tame) {
        getHandle().func_70903_f(tame);
        if (!tame) {
            setOwnerName("");
        }
    }

    public boolean isSitting() {
        return getHandle().func_70906_o();
    }

    public void setSitting(boolean sitting) {
        getHandle().func_70907_r().func_75270_a(sitting);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{owner=" + getOwner() + ",tamed=" + isTamed() + "}";
    }
}
