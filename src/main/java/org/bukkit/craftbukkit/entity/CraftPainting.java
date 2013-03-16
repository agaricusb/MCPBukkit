package org.bukkit.craftbukkit.entity;


import org.bukkit.Art;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.CraftArt;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Painting;

public class CraftPainting extends CraftHanging implements Painting {

    public CraftPainting(CraftServer server, net.minecraft.entity.item.EntityPainting entity) {
        super(server, entity);
    }

    public Art getArt() {
        net.minecraft.util.EnumArt art = getHandle().field_70522_e;
        return CraftArt.NotchToBukkit(art);
    }

    public boolean setArt(Art art) {
        return setArt(art, false);
    }

    public boolean setArt(Art art, boolean force) {
        net.minecraft.entity.item.EntityPainting painting = this.getHandle();
        net.minecraft.util.EnumArt oldArt = painting.field_70522_e;
        painting.field_70522_e = CraftArt.BukkitToNotch(art);
        painting.func_82328_a(painting.field_82332_a);
        if (!force && !painting.func_70518_d()) {
            // Revert painting since it doesn't fit
            painting.field_70522_e = oldArt;
            painting.func_82328_a(painting.field_82332_a);
            return false;
        }
        this.update();
        return true;
    }

    public boolean setFacingDirection(BlockFace face, boolean force) {
        if (super.setFacingDirection(face, force)) {
            update();
            return true;
        }

        return false;
    }

    private void update() {
        net.minecraft.world.WorldServer world = ((CraftWorld) getWorld()).getHandle();
        net.minecraft.entity.item.EntityPainting painting = new net.minecraft.entity.item.EntityPainting(world);
        painting.field_70523_b = getHandle().field_70523_b;
        painting.field_70524_c = getHandle().field_70524_c;
        painting.field_70521_d = getHandle().field_70521_d;
        painting.field_70522_e = getHandle().field_70522_e;
        painting.func_82328_a(getHandle().field_82332_a);
        getHandle().func_70106_y();
        getHandle().field_70133_I = true; // because this occurs when the painting is broken, so it might be important
        world.func_72838_d(painting);
        this.entity = painting;
    }

    @Override
    public net.minecraft.entity.item.EntityPainting getHandle() {
        return (net.minecraft.entity.item.EntityPainting) entity;
    }

    @Override
    public String toString() {
        return "CraftPainting{art=" + getArt() + "}";
    }

    public EntityType getType() {
        return EntityType.PAINTING;
    }
}
