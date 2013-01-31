package org.bukkit.craftbukkit.entity;


import org.apache.commons.lang.Validate;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;

public class CraftSkeleton extends CraftMonster implements Skeleton {

    public CraftSkeleton(CraftServer server, net.minecraft.entity.monster.EntitySkeleton/*was:EntitySkeleton*/ entity) {
        super(server, entity);
    }

    @Override
    public net.minecraft.entity.monster.EntitySkeleton/*was:EntitySkeleton*/ getHandle() {
        return (net.minecraft.entity.monster.EntitySkeleton/*was:EntitySkeleton*/) entity;
    }

    @Override
    public String toString() {
        return "CraftSkeleton";
    }

    public EntityType getType() {
        return EntityType.SKELETON;
    }

    public SkeletonType getSkeletonType() {
        return SkeletonType.getType(getHandle().func_82202_m/*was:getSkeletonType*/());
    }

    public void setSkeletonType(SkeletonType type) {
        Validate.notNull(type);
        getHandle().func_82201_a/*was:setSkeletonType*/(type.getId());
    }
}
