package org.bukkit.craftbukkit.entity;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Hanging;

public class CraftHanging extends CraftEntity implements Hanging {
    public CraftHanging(CraftServer server, net.minecraft.entity.EntityHanging entity) {
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
        net.minecraft.entity.EntityHanging hanging = getHandle();
        int x = hanging.field_70523_b, y = hanging.field_70524_c, z = hanging.field_70521_d, dir = hanging.field_82332_a;
        hanging.field_70523_b = block.getX();
        hanging.field_70524_c = block.getY();
        hanging.field_70521_d = block.getZ();
        switch (face) {
            case SOUTH:
            default:
                getHandle().func_82328_a(0);
                break;
            case WEST:
                getHandle().func_82328_a(1);
                break;
            case NORTH:
                getHandle().func_82328_a(2);
                break;
            case EAST:
                getHandle().func_82328_a(3);
                break;
        }
        if (!force && !hanging.func_70518_d()) {
            // Revert since it doesn't fit
            hanging.field_70523_b = x;
            hanging.field_70524_c = y;
            hanging.field_70521_d = z;
            hanging.func_82328_a(dir);
            return false;
        }
        return true;
    }

    public BlockFace getFacing() {
        switch (this.getHandle().field_82332_a) {
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
    public net.minecraft.entity.EntityHanging getHandle() {
        return (net.minecraft.entity.EntityHanging) entity;
    }

    @Override
    public String toString() {
        return "CraftHanging";
    }

    public EntityType getType() {
        return EntityType.UNKNOWN;
    }
}
