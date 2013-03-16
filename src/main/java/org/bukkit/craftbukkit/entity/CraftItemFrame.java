package org.bukkit.craftbukkit.entity;


import org.apache.commons.lang.Validate;

import org.bukkit.Rotation;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;

public class CraftItemFrame extends CraftHanging implements ItemFrame {
    public CraftItemFrame(CraftServer server, net.minecraft.entity.item.EntityItemFrame entity) {
        super(server, entity);
    }

    public void setItem(org.bukkit.inventory.ItemStack item) {
        if (item == null || item.getTypeId() == 0) {
            getHandle().func_70096_w().func_82709_a(2, 5);
            getHandle().func_70096_w().func_82708_h(2);
        } else {
            getHandle().func_82334_a(CraftItemStack.asNMSCopy(item));
        }
    }

    public org.bukkit.inventory.ItemStack getItem() {
        return CraftItemStack.asBukkitCopy(getHandle().func_82335_i());
    }

    public Rotation getRotation() {
        return toBukkitRotation(getHandle().func_82333_j());
    }

    Rotation toBukkitRotation(int value) {
        // Translate NMS rotation integer to Bukkit API
        switch (value) {
        case 0:
            return Rotation.NONE;
        case 1:
            return Rotation.CLOCKWISE;
        case 2:
            return Rotation.FLIPPED;
        case 3:
            return Rotation.COUNTER_CLOCKWISE;
        default:
            throw new AssertionError("Unknown rotation " + getHandle().func_82333_j() + " for " + getHandle());
        }
    }

    public void setRotation(Rotation rotation) {
        Validate.notNull(rotation, "Rotation cannot be null");
        getHandle().func_82336_g(toInteger(rotation)); // Should be setRotation
    }

    static int toInteger(Rotation rotation) {
        // Translate Bukkit API rotation to NMS integer
        switch (rotation) {
        case NONE:
            return 0;
        case CLOCKWISE:
            return 1;
        case FLIPPED:
            return 2;
        case COUNTER_CLOCKWISE:
            return 3;
        default:
            throw new IllegalArgumentException(rotation + " is not applicable to an ItemFrame");
        }
    }

    @Override
    public net.minecraft.entity.item.EntityItemFrame getHandle() {
        return (net.minecraft.entity.item.EntityItemFrame) entity;
    }

    @Override
    public String toString() {
        return "CraftItemFrame{item=" + getItem() + ", rotation=" + getRotation() + "}";
    }

    public EntityType getType() {
        return EntityType.ITEM_FRAME;
    }
}
