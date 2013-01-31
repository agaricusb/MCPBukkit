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

    static class MinecraftInventory implements net.minecraft.inventory.IInventory/*was:IInventory*/ {
        private final net.minecraft.item.ItemStack/*was:ItemStack*/[] items;
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
            this.items = new net.minecraft.item.ItemStack/*was:ItemStack*/[size];
            this.title = title;
            this.viewers = new ArrayList<HumanEntity>();
            this.owner = owner;
            this.type = InventoryType.CHEST;
        }

        public int func_70302_i_/*was:getSize*/() {
            return items.length;
        }

        public net.minecraft.item.ItemStack/*was:ItemStack*/ func_70301_a/*was:getItem*/(int i) {
            return items[i];
        }

        public net.minecraft.item.ItemStack/*was:ItemStack*/ func_70298_a/*was:splitStack*/(int i, int j) {
            net.minecraft.item.ItemStack/*was:ItemStack*/ stack = this.func_70301_a/*was:getItem*/(i);
            net.minecraft.item.ItemStack/*was:ItemStack*/ result;
            if (stack == null) return null;
            if (stack.field_77994_a/*was:count*/ <= j) {
                this.func_70299_a/*was:setItem*/(i, null);
                result = stack;
            } else {
                result = CraftItemStack.copyNMSStack(stack, j);
                stack.field_77994_a/*was:count*/ -= j;
            }
            this.func_70296_d/*was:update*/();
            return result;
        }

        public net.minecraft.item.ItemStack/*was:ItemStack*/ func_70304_b/*was:splitWithoutUpdate*/(int i) {
            net.minecraft.item.ItemStack/*was:ItemStack*/ stack = this.func_70301_a/*was:getItem*/(i);
            net.minecraft.item.ItemStack/*was:ItemStack*/ result;
            if (stack == null) return null;
            if (stack.field_77994_a/*was:count*/ <= 1) {
                this.func_70299_a/*was:setItem*/(i, null);
                result = stack;
            } else {
                result = CraftItemStack.copyNMSStack(stack, 1);
                stack.field_77994_a/*was:count*/ -= 1;
            }
            return result;
        }

        public void func_70299_a/*was:setItem*/(int i, net.minecraft.item.ItemStack/*was:ItemStack*/ itemstack) {
            items[i] = itemstack;
            if (itemstack != null && this.func_70297_j_/*was:getMaxStackSize*/() > 0 && itemstack.field_77994_a/*was:count*/ > this.func_70297_j_/*was:getMaxStackSize*/()) {
                itemstack.field_77994_a/*was:count*/ = this.func_70297_j_/*was:getMaxStackSize*/();
            }
        }

        public String func_70303_b/*was:getName*/() {
            return title;
        }

        public int func_70297_j_/*was:getMaxStackSize*/() {
            return maxStack;
        }

        public void setMaxStackSize(int size) {
            maxStack = size;
        }

        public void func_70296_d/*was:update*/() {}

        public boolean func_70300_a/*was:a_*/(net.minecraft.entity.player.EntityPlayer/*was:EntityHuman*/ entityhuman) {
            return true;
        }

        public net.minecraft.item.ItemStack/*was:ItemStack*/[] getContents() {
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

        public void func_70305_f/*was:f*/() {}

        public void g() {}

        public InventoryHolder getOwner() {
            return owner;
        }

        public void func_70295_k_/*was:startOpen*/() {}
    }
}
