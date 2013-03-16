package org.bukkit.craftbukkit.block;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Jukebox;
import org.bukkit.craftbukkit.CraftWorld;


public class CraftJukebox extends CraftBlockState implements Jukebox {
    private final CraftWorld world;
    private final net.minecraft.tileentity.TileEntityRecordPlayer jukebox;

    public CraftJukebox(final Block block) {
        super(block);

        world = (CraftWorld) block.getWorld();
        jukebox = (net.minecraft.tileentity.TileEntityRecordPlayer) world.getTileEntityAt(getX(), getY(), getZ());
    }

    public Material getPlaying() {
        net.minecraft.item.ItemStack record = jukebox.func_96097_a();
        if (record == null) {
            return Material.AIR;
        }
        return Material.getMaterial(record.field_77993_c);
    }

    public void setPlaying(Material record) {
        if (record == null || net.minecraft.item.Item.field_77698_e[record.getId()] == null) {
            record = Material.AIR;
            jukebox.func_96098_a(null);
        } else {
            jukebox.func_96098_a(new net.minecraft.item.ItemStack(net.minecraft.item.Item.field_77698_e[record.getId()], 1));
        }
        jukebox.func_70296_d();
        if (record == Material.AIR) {
            world.getHandle().func_72921_c(getX(), getY(), getZ(), 0, 3);
        } else {
            world.getHandle().func_72921_c(getX(), getY(), getZ(), 1, 3);
        }
        world.playEffect(getLocation(), Effect.RECORD_PLAY, record.getId());
    }

    public boolean isPlaying() {
        return getRawData() == 1;
    }

    public boolean eject() {
        boolean result = isPlaying();
        ((net.minecraft.block.BlockJukeBox) net.minecraft.block.Block.field_72032_aY).func_72276_j_(world.getHandle(), getX(), getY(), getZ());
        return result;
    }
}
