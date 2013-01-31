package org.bukkit.craftbukkit.entity;


import org.bukkit.Material;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.Random;

public class CraftFirework extends CraftEntity implements Firework {
    private static final int FIREWORK_ITEM_INDEX = 8;

    private final Random random = new Random();
    private final CraftItemStack item;

    public CraftFirework(CraftServer server, net.minecraft.entity.item.EntityFireworkRocket/*was:EntityFireworks*/ entity) {
        super(server, entity);

        net.minecraft.item.ItemStack/*was:ItemStack*/ item = getHandle().func_70096_w/*was:getDataWatcher*/().func_82710_f/*was:getItemStack*/(FIREWORK_ITEM_INDEX);

        if (item == null) {
            item = new net.minecraft.item.ItemStack/*was:ItemStack*/(net.minecraft.item.Item/*was:Item*/.field_92104_bU/*was:FIREWORKS*/);
            getHandle().func_70096_w/*was:getDataWatcher*/().func_75692_b/*was:watch*/(FIREWORK_ITEM_INDEX, item);
        }

        this.item = CraftItemStack.asCraftMirror(item);

        // Ensure the item is a firework...
        if (this.item.getType() != Material.FIREWORK) {
            this.item.setType(Material.FIREWORK);
        }
    }

    @Override
    public net.minecraft.entity.item.EntityFireworkRocket/*was:EntityFireworks*/ getHandle() {
        return (net.minecraft.entity.item.EntityFireworkRocket/*was:EntityFireworks*/) entity;
    }

    @Override
    public String toString() {
        return "CraftFirework";
    }

    public EntityType getType() {
        return EntityType.FIREWORK;
    }

    public FireworkMeta getFireworkMeta() {
        return (FireworkMeta) item.getItemMeta();
    }

    public void setFireworkMeta(FireworkMeta meta) {
        item.setItemMeta(meta);

        // Copied from EntityFireworks constructor, update firework lifetime/power
        getHandle().field_92055_b/*was:expectedLifespan*/ = 10 * (1 + meta.getPower()) + random.nextInt(6) + random.nextInt(7);

        getHandle().func_70096_w/*was:getDataWatcher*/().func_82708_h/*was:h*/(FIREWORK_ITEM_INDEX); // Update
    }
}
