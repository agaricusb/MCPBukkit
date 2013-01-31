package org.bukkit.craftbukkit.entity;

import org.apache.commons.lang.Validate;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;

public class CraftVillager extends CraftAgeable implements Villager {
    public CraftVillager(CraftServer server, net.minecraft.entity.passive.EntityVillager/*was:EntityVillager*/ entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.entity.passive.EntityVillager/*was:EntityVillager*/ getHandle() {
        return (net.minecraft.entity.passive.EntityVillager/*was:EntityVillager*/) entity;
    }

    @Override
    public String toString() {
        return "CraftVillager";
    }

    public EntityType getType() {
        return EntityType.VILLAGER;
    }

    public Profession getProfession() {
        return Profession.getProfession(getHandle().func_70946_n/*was:getProfession*/());
    }

    public void setProfession(Profession profession) {
        Validate.notNull(profession);
        getHandle().func_70938_b/*was:setProfession*/(profession.getId());
    }
}
