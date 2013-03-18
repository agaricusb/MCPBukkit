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
        List<net.minecraft.entity.player.EntityPlayerMP> players = net.minecraft.server.MinecraftServer.func_71276_C().func_71203_ab().field_72404_b;
        HashSet<Player> reference = new HashSet<Player>(players.size());
        for (net.minecraft.entity.player.EntityPlayerMP player : players) {
            reference.add(player.getBukkitEntity());
        }
        return reference;
    }

}
