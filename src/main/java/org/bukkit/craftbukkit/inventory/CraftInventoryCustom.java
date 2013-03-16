package org.bukkit.craftbukkit.inventory;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;


public class CraftInventoryCustom extends CraftInventory {
    public CraftInventoryCustom(InventoryHolder owner, InventoryType type) {
        super(new MinecraftInventory(owner, type));
    }

    public CraftInventoryCustom(InventoryHolder owner, int size) {
        super(new MinecraftInventory(owner, size));
    }

    public CraftInventoryCustom(InventoryHolder owner, int size, String title) {
        super(new MinecraftInventory(owner, size, title));
    }

    static class MinecraftInventory implements net.minecraft.inventory.IInventory {
        private final net.minecraft.item.ItemStack[] items;
        private int maxStack = MAX_STACK;
        private final List<HumanEntity> viewers;
        private final String title;
        private InventoryType type;
        private final InventoryHolder owner;

        public MinecraftInventory(InventoryHolder owner, InventoryType type) {
            this(owner, type.getDefaultSize(), type.getDefaultTitle());
            this.type = type;
        }

        public MinecraftInventory(InventoryHolder owner, int size) {
            this(owner, size, "Chest");
        }

        public MinecraftInventory(InventoryHolder owner, int size, String title) {
            this.items = new net.minecraft.item.ItemStack[size];
            this.title = title;
            this.viewers = new ArrayList<HumanEntity>();
            this.owner = owner;
            this.type = InventoryType.CHEST;
        }

        public int func_70302_i_() {
            return items.length;
        }

        public net.minecraft.item.ItemStack func_70301_a(int i) {
            return items[i];
        }

        public net.minecraft.item.ItemStack func_70298_a(int i, int j) {
            net.minecraft.item.ItemStack stack = this.func_70301_a(i);
            net.minecraft.item.ItemStack result;
            if (stack == null) return null;
            if (stack.field_77994_a <= j) {
                this.func_70299_a(i, null);
                result = stack;
            } else {
                result = CraftItemStack.copyNMSStack(stack, j);
                stack.field_77994_a -= j;
            }
            this.func_70296_d();
            return result;
        }

        public net.minecraft.item.ItemStack func_70304_b(int i) {
            net.minecraft.item.ItemStack stack = this.func_70301_a(i);
            net.minecraft.item.ItemStack result;
            if (stack == null) return null;
            if (stack.field_77994_a <= 1) {
                this.func_70299_a(i, null);
                result = stack;
            } else {
                result = CraftItemStack.copyNMSStack(stack, 1);
                stack.field_77994_a -= 1;
            }
            return result;
        }

        public void func_70299_a(int i, net.minecraft.item.ItemStack itemstack) {
            items[i] = itemstack;
            if (itemstack != null && this.func_70297_j_() > 0 && itemstack.field_77994_a > this.func_70297_j_()) {
                itemstack.field_77994_a = this.func_70297_j_();
            }
        }

        public String func_70303_b() {
            return title;
        }

        public int func_70297_j_() {
            return maxStack;
        }

        public void setMaxStackSize(int size) {
            maxStack = size;
        }

        public void func_70296_d() {}

        public boolean func_70300_a(net.minecraft.entity.player.EntityPlayer entityhuman) {
            return true;
        }

        public net.minecraft.item.ItemStack[] getContents() {
            return items;
        }

        public void onOpen(CraftHumanEntity who) {
            viewers.add(who);
        }

        public void onClose(CraftHumanEntity who) {
            viewers.remove(who);
        }

        public List<HumanEntity> getViewers() {
            return viewers;
        }

        public InventoryType getType() {
            return type;
        }

        public void func_70305_f() {}

        public InventoryHolder getOwner() {
            return owner;
        }

        public void func_70295_k_() {}

        public boolean func_94042_c() {
            return false;
        }

        public boolean func_94041_b(int i, net.minecraft.item.ItemStack itemstack) {
            return true;
        }
    }
}
