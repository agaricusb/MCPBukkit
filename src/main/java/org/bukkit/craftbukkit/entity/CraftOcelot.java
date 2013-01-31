package org.bukkit.craftbukkit.entity;

import org.apache.commons.lang.Validate;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ocelot;

public class CraftOcelot extends CraftTameableAnimal implements Ocelot {
    public CraftOcelot(CraftServer server, net.minecraft.entity.passive.EntityOcelot/*was:EntityOcelot*/ wolf) {
        super(server, wolf);
    }

    @Override
    public net.minecraft.entity.passive.EntityOcelot/*was:EntityOcelot*/ getHandle() {
        return (net.minecraft.entity.passive.EntityOcelot/*was:EntityOcelot*/) entity;
    }

    public Type getCatType() {
        return Type.getType(getHandle().func_70913_u/*was:getCatType*/());
    }

    public void setCatType(Type type) {
        Validate.notNull(type, "Cat type cannot be null");
        getHandle().func_70912_b/*was:setCatType*/(type.getId());
    }

    @Override
    public EntityType getType() {
        return EntityType.OCELOT;
    }
}
