package org.bukkit.craftbukkit.entity;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Hanging;

public class CraftHanging extends CraftEntity implements Hanging {
    public CraftHanging(CraftServer server, net.minecraft.entity.EntityHanging/*was:EntityHanging*/ entity) {
        super(server, entity);
    }

    public BlockFace getAttachedFace() {
        return getFacing().getOppositeFace();
    }

    public void setFacingDirection(BlockFace face) {
        setFacingDirection(face, false);
    }

    public boolean setFacingDirection(BlockFace face, boolean force) {
        Block block = getLocation().getBlock().getRelative(getAttachedFace()).getRelative(face.getOppositeFace()).getRelative(getFacing());
        net.minecraft.entity.EntityHanging/*was:EntityHanging*/ hanging = getHandle();
        int x = hanging.field_70523_b/*was:x*/, y = hanging.field_70524_c/*was:y*/, z = hanging.field_70521_d/*was:z*/, dir = hanging.field_82332_a/*was:direction*/;
        hanging.field_70523_b/*was:x*/ = block.getX();
        hanging.field_70524_c/*was:y*/ = block.getY();
        hanging.field_70521_d/*was:z*/ = block.getZ();
        switch (face) {
            case SOUTH:
            default:
                getHandle().func_82328_a/*was:setDirection*/(0);
                break;
            case WEST:
                getHandle().func_82328_a/*was:setDirection*/(1);
                break;
            case NORTH:
                getHandle().func_82328_a/*was:setDirection*/(2);
                break;
            case EAST:
                getHandle().func_82328_a/*was:setDirection*/(3);
                break;
        }
        if (!force && !hanging.func_70518_d/*was:survives*/()) {
            // Revert since it doesn't fit
            hanging.field_70523_b/*was:x*/ = x;
            hanging.field_70524_c/*was:y*/ = y;
            hanging.field_70521_d/*was:z*/ = z;
            hanging.func_82328_a/*was:setDirection*/(dir);
            return false;
        }
        return true;
    }

    public BlockFace getFacing() {
        switch (this.getHandle().field_82332_a/*was:direction*/) {
            case 0:
            default:
                return BlockFace.SOUTH;
            case 1:
                return BlockFace.WEST;
            case 2:
                return BlockFace.NORTH;
            case 3:
                return BlockFace.EAST;
        }
    }

    @Override
    public net.minecraft.entity.EntityHanging/*was:EntityHanging*/ getHandle() {
        return (net.minecraft.entity.EntityHanging/*was:EntityHanging*/) entity;
    }

    @Override
    public String toString() {
        return "CraftHanging";
    }

    public EntityType getType() {
        return EntityType.UNKNOWN;
    }
}
