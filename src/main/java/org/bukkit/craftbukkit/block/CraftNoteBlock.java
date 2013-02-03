package org.bukkit.craftbukkit.block;


import org.bukkit.Instrument;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.block.Block;
import org.bukkit.block.NoteBlock;
import org.bukkit.craftbukkit.CraftWorld;

public class CraftNoteBlock extends CraftBlockState implements NoteBlock {
    private final CraftWorld world;
    private final net.minecraft.tileentity.TileEntityNote/*was:TileEntityNote*/ note;

    public CraftNoteBlock(final Block block) {
        super(block);

        world = (CraftWorld) block.getWorld();
        note = (net.minecraft.tileentity.TileEntityNote/*was:TileEntityNote*/) world.getTileEntityAt(getX(), getY(), getZ());
    }

    public Note getNote() {
        return new Note(note.field_70416_a/*was:note*/);
    }

    public byte getRawNote() {
        return note.field_70416_a/*was:note*/;
    }

    public void setNote(Note n) {
        note.field_70416_a/*was:note*/ = n.getId();
    }

    public void setRawNote(byte n) {
        note.field_70416_a/*was:note*/ = n;
    }

    public boolean play() {
        Block block = getBlock();

        synchronized (block) {
            if (block.getType() == Material.NOTE_BLOCK) {
                note.func_70414_a/*was:play*/(world.getHandle(), getX(), getY(), getZ());
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean play(byte instrument, byte note) {
        Block block = getBlock();

        synchronized (block) {
            if (block.getType() == Material.NOTE_BLOCK) {
                world.getHandle().func_72965_b/*was:playNote*/(getX(), getY(), getZ(), block.getTypeId(), instrument, note);
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean play(Instrument instrument, Note note) {
        Block block = getBlock();

        synchronized (block) {
            if (block.getType() == Material.NOTE_BLOCK) {
                world.getHandle().func_72965_b/*was:playNote*/(getX(), getY(), getZ(), block.getTypeId(), instrument.getType(), note.getId());
                return true;
            } else {
                return false;
            }
        }
    }
}