package org.bukkit.craftbukkit.command;

import org.bukkit.block.Block;
import org.bukkit.command.BlockCommandSender;

/**
 * Represents input from a command block
 */
public class CraftBlockCommandSender extends ServerCommandSender implements BlockCommandSender {
    private final net.minecraft.tileentity.TileEntityCommandBlock commandBlock;

    public CraftBlockCommandSender(net.minecraft.tileentity.TileEntityCommandBlock commandBlock) {
        super();
        this.commandBlock = commandBlock;
    }

    public Block getBlock() {
        return commandBlock.func_70314_l().getWorld().getBlockAt(commandBlock.field_70329_l, commandBlock.field_70330_m, commandBlock.field_70327_n);
    }

    public void sendMessage(String message) {
    }

    public void sendRawMessage(String message) {
    }

    public void sendMessage(String[] messages) {
    }

    public String getName() {
        return commandBlock.func_70005_c_();
    }

    public boolean isOp() {
        return true;
    }

    public void setOp(boolean value) {
        throw new UnsupportedOperationException("Cannot change operator status of a block");
    }
}
