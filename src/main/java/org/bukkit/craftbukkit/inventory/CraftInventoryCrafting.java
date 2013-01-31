package org.bukkit.craftbukkit.inventory;


import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.util.Java15Compat;

public class CraftInventoryCrafting extends CraftInventory implements CraftingInventory {
    private final net.minecraft.inventory.IInventory/*was:IInventory*/ resultInventory;

    public CraftInventoryCrafting(net.minecraft.inventory.InventoryCrafting/*was:InventoryCrafting*/ inventory, net.minecraft.inventory.IInventory/*was:IInventory*/ resultInventory) {
        super(inventory);
        this.resultInventory = resultInventory;
    }

    public net.minecraft.inventory.IInventory/*was:IInventory*/ getResultInventory() {
        return resultInventory;
    }

    public net.minecraft.inventory.IInventory/*was:IInventory*/ getMatrixInventory() {
        return inventory;
    }

    @Override
    public int getSize() {
        return getResultInventory().func_70302_i_/*was:getSize*/() + getMatrixInventory().func_70302_i_/*was:getSize*/();
    }

    @Override
    public void setContents(ItemStack[] items) {
        int resultLen = getResultInventory().getContents().length;
        int len = getMatrixInventory().getContents().length + resultLen;
        if (len > items.length) {
            throw new IllegalArgumentException("Invalid inventory size; expected " + len + " or less");
        }
        setContents(items[0], Java15Compat.Arrays_copyOfRange(items, 1, items.length));
    }

    @Override
    public ItemStack[] getContents() {
        ItemStack[] items = new ItemStack[getSize()];
        net.minecraft.item.ItemStack/*was:net.minecraft.server.ItemStack*/[] mcResultItems = getResultInventory().getContents();

        int i = 0;
        for (i = 0; i < mcResultItems.length; i++ ) {
            items[i] = CraftItemStack.asCraftMirror(mcResultItems[i]);
        }

        net.minecraft.item.ItemStack/*was:net.minecraft.server.ItemStack*/[] mcItems = getMatrixInventory().getContents();

        for (int j = 0; j < mcItems.length; j++) {
            items[i + j] = CraftItemStack.asCraftMirror(mcItems[j]);
        }

        return items;
    }

    public void setContents(ItemStack result, ItemStack[] contents) {
        setResult(result);
        setMatrix(contents);
    }

    @Override
    public CraftItemStack getItem(int index) {
        if (index < getResultInventory().func_70302_i_/*was:getSize*/()) {
            net.minecraft.item.ItemStack/*was:net.minecraft.server.ItemStack*/ item = getResultInventory().func_70301_a/*was:getItem*/(index);
            return item == null ? null : CraftItemStack.asCraftMirror(item);
        } else {
            net.minecraft.item.ItemStack/*was:net.minecraft.server.ItemStack*/ item = getMatrixInventory().func_70301_a/*was:getItem*/(index - getResultInventory().func_70302_i_/*was:getSize*/());
            return item == null ? null : CraftItemStack.asCraftMirror(item);
        }
    }

    @Override
    public void setItem(int index, ItemStack item) {
        if (index < getResultInventory().func_70302_i_/*was:getSize*/()) {
            getResultInventory().func_70299_a/*was:setItem*/(index, (item == null ? null : CraftItemStack.asNMSCopy(item)));
        } else {
            getMatrixInventory().func_70299_a/*was:setItem*/((index - getResultInventory().func_70302_i_/*was:getSize*/()), (item == null ? null : CraftItemStack.asNMSCopy(item)));
        }
    }

    public ItemStack[] getMatrix() {
        ItemStack[] items = new ItemStack[getSize()];
        net.minecraft.item.ItemStack/*was:net.minecraft.server.ItemStack*/[] matrix = getMatrixInventory().getContents();

        for (int i = 0; i < matrix.length; i++ ) {
            items[i] = CraftItemStack.asCraftMirror(matrix[i]);
        }

        return items;
    }

    public ItemStack getResult() {
        net.minecraft.item.ItemStack/*was:net.minecraft.server.ItemStack*/ item = getResultInventory().func_70301_a/*was:getItem*/(0);
        if(item != null) return CraftItemStack.asCraftMirror(item);
        return null;
    }

    public void setMatrix(ItemStack[] contents) {
        if (getMatrixInventory().getContents().length > contents.length) {
            throw new IllegalArgumentException("Invalid inventory size; expected " + getMatrixInventory().getContents().length + " or less");
        }

        net.minecraft.item.ItemStack/*was:net.minecraft.server.ItemStack*/[] mcItems = getMatrixInventory().getContents();

        for (int i = 0; i < mcItems.length; i++ ) {
            if (i < contents.length) {
                ItemStack item = contents[i];
                if (item == null || item.getTypeId() <= 0) {
                    mcItems[i] = null;
                } else {
                    mcItems[i] = CraftItemStack.asNMSCopy(item);
                }
            } else {
                mcItems[i] = null;
            }
        }
    }

    public void setResult(ItemStack item) {
        net.minecraft.item.ItemStack/*was:net.minecraft.server.ItemStack*/[] contents = getResultInventory().getContents();
        if (item == null || item.getTypeId() <= 0) {
            contents[0] = null;
        } else {
            contents[0] = CraftItemStack.asNMSCopy(item);
        }
    }

    public Recipe getRecipe() {
        net.minecraft.item.crafting.IRecipe/*was:IRecipe*/ recipe = ((net.minecraft.inventory.InventoryCrafting/*was:InventoryCrafting*/)getInventory()).currentRecipe;
        return recipe == null ? null : recipe.toBukkitRecipe();
    }
}
