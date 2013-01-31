package org.bukkit.craftbukkit.util;

import java.util.HashSet;
import java.util.List;

import org.bukkit.entity.Player;

public class LazyPlayerSet extends LazyHashSet<Player> {

    @Override
    HashSet<Player> makeReference() {
        if (reference != null) {
            throw new IllegalStateException("Reference already created!");
        }
        List<net.minecraft.entity.player.EntityPlayerMP/*was:EntityPlayer*/> players = net.minecraft.server.MinecraftServer/*was:MinecraftServer*/.func_71276_C/*was:getServer*/().func_71203_ab/*was:getPlayerList*/().field_72404_b/*was:players*/;
        HashSet<Player> reference = new HashSet<Player>(players.size());
        for (net.minecraft.entity.player.EntityPlayerMP/*was:EntityPlayer*/ player : players) {
            reference.add(player.getBukkitEntity());
        }
        return reference;
    }

}
