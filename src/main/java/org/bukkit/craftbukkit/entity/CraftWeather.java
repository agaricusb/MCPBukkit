package org.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Weather;

public class CraftWeather extends CraftEntity implements Weather {
    public CraftWeather(final CraftServer server, final net.minecraft.entity.effect.EntityWeatherEffect/*was:EntityWeather*/ entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.entity.effect.EntityWeatherEffect/*was:EntityWeather*/ getHandle() {
        return (net.minecraft.entity.effect.EntityWeatherEffect/*was:EntityWeather*/) entity;
    }

    @Override
    public String toString() {
        return "CraftWeather";
    }

    public EntityType getType() {
        return EntityType.WEATHER;
    }
}