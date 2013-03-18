package org.bukkit.craftbukkit.entity;


import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Slime;

public class CraftSlime extends CraftLivingEntity implements Slime {

    public CraftSlime(CraftServer server, net.minecraft.entity.monster.EntitySlime entity) {
        super(server, entity);
    }

    public int getSize() {
        return getHandle().func_70809_q();
    }

    public void setSize(int size) {
        getHandle().func_70799_a(size);
    }

    @Override
    public net.minecraft.entity.monster.EntitySlime getHandle() {
        return (net.minecraft.entity.monster.EntitySlime) entity;
    }

    @Override
    public String toString() {
        return "CraftSlime";
    }

    public EntityType getType() {
        return EntityType.SLIME;
    }
}
