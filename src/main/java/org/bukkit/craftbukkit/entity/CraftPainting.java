package org.bukkit.craftbukkit.entity;


import org.bukkit.Art;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.CraftArt;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Painting;

public class CraftPainting extends CraftHanging implements Painting {

    public CraftPainting(CraftServer server, net.minecraft.entity.item.EntityPainting/*was:EntityPainting*/ entity) {
        super(server, entity);
    }

    public Art getArt() {
        net.minecraft.util.EnumArt/*was:EnumArt*/ art = getHandle().field_70522_e/*was:art*/;
        return CraftArt.NotchToBukkit(art);
    }

    public boolean setArt(Art art) {
        return setArt(art, false);
    }

    public boolean setArt(Art art, boolean force) {
        net.minecraft.entity.item.EntityPainting/*was:EntityPainting*/ painting = this.getHandle();
        net.minecraft.util.EnumArt/*was:EnumArt*/ oldArt = painting.field_70522_e/*was:art*/;
        painting.field_70522_e/*was:art*/ = CraftArt.BukkitToNotch(art);
        painting.func_82328_a/*was:setDirection*/(painting.field_82332_a/*was:direction*/);
        if (!force && !painting.func_70518_d/*was:survives*/()) {
            // Revert painting since it doesn't fit
            painting.field_70522_e/*was:art*/ = oldArt;
            painting.func_82328_a/*was:setDirection*/(painting.field_82332_a/*was:direction*/);
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
        net.minecraft.world.WorldServer/*was:WorldServer*/ world = ((CraftWorld) getWorld()).getHandle();
        net.minecraft.entity.item.EntityPainting/*was:EntityPainting*/ painting = new net.minecraft.entity.item.EntityPainting/*was:EntityPainting*/(world);
        painting.field_70523_b/*was:x*/ = getHandle().field_70523_b/*was:x*/;
        painting.field_70524_c/*was:y*/ = getHandle().field_70524_c/*was:y*/;
        painting.field_70521_d/*was:z*/ = getHandle().field_70521_d/*was:z*/;
        painting.field_70522_e/*was:art*/ = getHandle().field_70522_e/*was:art*/;
        painting.func_82328_a/*was:setDirection*/(getHandle().field_82332_a/*was:direction*/);
        getHandle().func_70106_y/*was:die*/();
        getHandle().field_70133_I/*was:velocityChanged*/ = true; // because this occurs when the painting is broken, so it might be important
        world.func_72838_d/*was:addEntity*/(painting);
        this.entity = painting;
    }

    @Override
    public net.minecraft.entity.item.EntityPainting/*was:EntityPainting*/ getHandle() {
        return (net.minecraft.entity.item.EntityPainting/*was:EntityPainting*/) entity;
    }

    @Override
    public String toString() {
        return "CraftPainting{art=" + getArt() + "}";
    }

    public EntityType getType() {
        return EntityType.PAINTING;
    }
}
