package org.bukkit.craftbukkit.entity;


import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Slime;

public class CraftSlime extends CraftLivingEntity implements Slime {

    public CraftSlime(CraftServer server, net.minecraft.entity.monster.EntitySlime/*was:EntitySlime*/ entity) {
        super(server, entity);
    }

    public int getSize() {
        return getHandle().func_70809_q/*was:getSize*/();
    }

    public void setSize(int size) {
        getHandle().func_70799_a/*was:setSize*/(size);
    }

    @Override
    public net.minecraft.entity.monster.EntitySlime/*was:EntitySlime*/ getHandle() {
        return (net.minecraft.entity.monster.EntitySlime/*was:EntitySlime*/) entity;
    }

    @Override
    public String toString() {
        return "CraftSlime";
    }

    public EntityType getType() {
        return EntityType.SLIME;
    }
}
