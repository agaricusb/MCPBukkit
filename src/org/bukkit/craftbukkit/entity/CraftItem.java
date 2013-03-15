package org.bukkit.craftbukkit.entity;


import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.craftbukkit.CraftServer;

public class CraftItem extends CraftEntity implements Item {
    private final net.minecraft.entity.item.EntityItem item;

    public CraftItem(CraftServer server, net.minecraft.entity.Entity entity, net.minecraft.entity.item.EntityItem item) {
        super(server, entity);
        this.item = item;
    }

    public CraftItem(CraftServer server, net.minecraft.entity.item.EntityItem entity) {
        this(server, entity, entity);
    }

    public ItemStack getItemStack() {
        return CraftItemStack.asCraftMirror(item.func_92059_d());
    }

    public void setItemStack(ItemStack stack) {
        item.func_92058_a(CraftItemStack.asNMSCopy(stack));
    }

    public int getPickupDelay() {
        return item.field_70293_c;
    }

    public void setPickupDelay(int delay) {
        item.field_70293_c = delay;
    }

    @Override
    public String toString() {
        return "CraftItem";
    }

    public EntityType getType() {
        return EntityType.DROPPED_ITEM;
    }
}
