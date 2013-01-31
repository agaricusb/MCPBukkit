package org.bukkit.craftbukkit.block;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.CraftWorld;

public class CraftSign extends CraftBlockState implements Sign {
    private final net.minecraft.tileentity.TileEntitySign/*was:TileEntitySign*/ sign;
    private final String[] lines;

    public CraftSign(final Block block) {
        super(block);

        CraftWorld world = (CraftWorld) block.getWorld();
        sign = (net.minecraft.tileentity.TileEntitySign/*was:TileEntitySign*/) world.getTileEntityAt(getX(), getY(), getZ());
        lines = new String[sign.field_70412_a/*was:lines*/.length];
        System.arraycopy(sign.field_70412_a/*was:lines*/, 0, lines, 0, lines.length);
    }

    public String[] getLines() {
        return lines;
    }

    public String getLine(int index) throws IndexOutOfBoundsException {
        return lines[index];
    }

    public void setLine(int index, String line) throws IndexOutOfBoundsException {
        lines[index] = line;
    }

    @Override
    public boolean update(boolean force) {
        boolean result = super.update(force);

        if (result) {
            for(int i = 0; i < 4; i++) {
                if(lines[i] != null) {
                    sign.field_70412_a/*was:lines*/[i] = lines[i];
                } else {
                    sign.field_70412_a/*was:lines*/[i] = "";
                }
            }
            sign.func_70296_d/*was:update*/();
        }

        return result;
    }
}
