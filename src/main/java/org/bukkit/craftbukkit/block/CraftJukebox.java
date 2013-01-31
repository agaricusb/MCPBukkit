package org.bukkit.craftbukkit.block;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Jukebox;
import org.bukkit.craftbukkit.CraftWorld;


public class CraftJukebox extends CraftBlockState implements Jukebox {
    private final CraftWorld world;
    private final net.minecraft.block.TileEntityRecordPlayer/*was:TileEntityRecordPlayer*/ jukebox;

    public CraftJukebox(final Block block) {
        super(block);

        world = (CraftWorld) block.getWorld();
        jukebox = (net.minecraft.block.TileEntityRecordPlayer/*was:TileEntityRecordPlayer*/) world.getTileEntityAt(getX(), getY(), getZ());
    }

    public Material getPlaying() {
        net.minecraft.item.ItemStack/*was:ItemStack*/ record = jukebox.field_70417_a/*was:record*/;
        if (record == null) {
            return Material.AIR;
        }
        return Material.getMaterial(record.field_77993_c/*was:id*/);
    }

    public void setPlaying(Material record) {
        if (record == null || net.minecraft.item.Item/*was:Item*/.field_77698_e/*was:byId*/[record.getId()] == null) {
            record = Material.AIR;
            jukebox.field_70417_a/*was:record*/ = null;
        } else {
            jukebox.field_70417_a/*was:record*/ = new net.minecraft.item.ItemStack/*was:ItemStack*/(net.minecraft.item.Item/*was:Item*/.field_77698_e/*was:byId*/[record.getId()], 1);
        }
        jukebox.func_70296_d/*was:update*/();
        if (record == Material.AIR) {
            world.getHandle().func_72921_c/*was:setData*/(getX(), getY(), getZ(), 0);
        } else {
            world.getHandle().func_72921_c/*was:setData*/(getX(), getY(), getZ(), 1);
        }
        world.playEffect(getLocation(), Effect.RECORD_PLAY, record.getId());
    }

    public boolean isPlaying() {
        return getRawData() == 1;
    }

    public boolean eject() {
        boolean result = isPlaying();
        ((net.minecraft.block.BlockJukeBox/*was:BlockJukeBox*/) net.minecraft.block.Block/*was:Block*/.field_72032_aY/*was:JUKEBOX*/).func_72276_j_/*was:dropRecord*/(world.getHandle(), getX(), getY(), getZ());
        return result;
    }
}
