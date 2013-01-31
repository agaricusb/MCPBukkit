package org.bukkit.craftbukkit.inventory;

import java.util.Iterator;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;


public class RecipeIterator implements Iterator<Recipe> {
    private Iterator<net.minecraft.item.crafting.IRecipe/*was:IRecipe*/> recipes;
    private Iterator<Integer> smelting;
    private Iterator<?> removeFrom = null;

    public RecipeIterator() {
        this.recipes = net.minecraft.item.crafting.CraftingManager/*was:CraftingManager*/.func_77594_a/*was:getInstance*/().func_77592_b/*was:getRecipes*/().iterator();
        this.smelting = net.minecraft.item.crafting.FurnaceRecipes/*was:RecipesFurnace*/.func_77602_a/*was:getInstance*/().func_77599_b/*was:getRecipes*/().keySet().iterator();
    }

    public boolean hasNext() {
        if (recipes.hasNext()) {
            return true;
        } else {
            return smelting.hasNext();
        }
    }

    public Recipe next() {
        if (recipes.hasNext()) {
            removeFrom = recipes;
            return recipes.next().toBukkitRecipe();
        } else {
            removeFrom = smelting;
            int id = smelting.next();
            CraftItemStack stack = CraftItemStack.asCraftMirror(net.minecraft.item.crafting.FurnaceRecipes/*was:RecipesFurnace*/.func_77602_a/*was:getInstance*/().func_77603_b/*was:getResult*/(id));
            CraftFurnaceRecipe recipe = new CraftFurnaceRecipe(stack, new ItemStack(id, 1, (short) -1));
            return recipe;
        }
    }

    public void remove() {
        if (removeFrom == null) {
            throw new IllegalStateException();
        }
        removeFrom.remove();
    }
}
