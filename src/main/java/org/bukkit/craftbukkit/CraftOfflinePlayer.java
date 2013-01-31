package org.bukkit.craftbukkit;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

@SerializableAs("Player")
public class CraftOfflinePlayer implements OfflinePlayer, ConfigurationSerializable {
    private final String name;
    private final CraftServer server;
    private final net.minecraft.world.storage.SaveHandler/*was:WorldNBTStorage*/ storage;

    protected CraftOfflinePlayer(CraftServer server, String name) {
        this.server = server;
        this.name = name;
        this.storage = (net.minecraft.world.storage.SaveHandler/*was:WorldNBTStorage*/) (server.console.worlds.get(0).func_72860_G/*was:getDataManager*/());
    }

    public boolean isOnline() {
        return getPlayer() != null;
    }

    public String getName() {
        return name;
    }

    public Server getServer() {
        return server;
    }

    public boolean isOp() {
        return server.getHandle().func_72353_e/*was:isOp*/(getName().toLowerCase());
    }

    public void setOp(boolean value) {
        if (value == isOp()) return;

        if (value) {
            server.getHandle().func_72386_b/*was:addOp*/(getName().toLowerCase());
        } else {
            server.getHandle().func_72360_c/*was:removeOp*/(getName().toLowerCase());
        }
    }

    public boolean isBanned() {
        return server.getHandle().func_72390_e/*was:getNameBans*/().func_73704_a/*was:isBanned*/(name.toLowerCase());
    }

    public void setBanned(boolean value) {
        if (value) {
            net.minecraft.server.management.BanEntry/*was:BanEntry*/ entry = new net.minecraft.server.management.BanEntry/*was:BanEntry*/(name.toLowerCase());
            server.getHandle().func_72390_e/*was:getNameBans*/().func_73706_a/*was:add*/(entry);
        } else {
            server.getHandle().func_72390_e/*was:getNameBans*/().func_73709_b/*was:remove*/(name.toLowerCase());
        }

        server.getHandle().func_72390_e/*was:getNameBans*/().func_73711_f/*was:save*/();
    }

    public boolean isWhitelisted() {
        return server.getHandle().func_72388_h/*was:getWhitelisted*/().contains(name.toLowerCase());
    }

    public void setWhitelisted(boolean value) {
        if (value) {
            server.getHandle().func_72359_h/*was:addWhitelist*/(name.toLowerCase());
        } else {
            server.getHandle().func_72379_i/*was:removeWhitelist*/(name.toLowerCase());
        }
    }

    public Map<String, Object> serialize() {
        Map<String, Object> result = new LinkedHashMap<String, Object>();

        result.put("name", name);

        return result;
    }

    public static OfflinePlayer deserialize(Map<String, Object> args) {
        return Bukkit.getServer().getOfflinePlayer((String) args.get("name"));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[name=" + name + "]";
    }

    public Player getPlayer() {
        for (Object obj : server.getHandle().field_72404_b/*was:players*/) {
            net.minecraft.entity.player.EntityPlayerMP/*was:EntityPlayer*/ player = (net.minecraft.entity.player.EntityPlayerMP/*was:EntityPlayer*/) obj;
            if (player.field_71092_bJ/*was:name*/.equalsIgnoreCase(getName())) {
                return (player.field_71135_a/*was:playerConnection*/ != null) ? player.field_71135_a/*was:playerConnection*/.getPlayer() : null;
            }
        }

        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof OfflinePlayer)) {
            return false;
        }
        OfflinePlayer other = (OfflinePlayer) obj;
        if ((this.getName() == null) || (other.getName() == null)) {
            return false;
        }
        return this.getName().equalsIgnoreCase(other.getName());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.getName() != null ? this.getName().toLowerCase().hashCode() : 0);
        return hash;
    }

    private net.minecraft.nbt.NBTTagCompound/*was:NBTTagCompound*/ getData() {
        return storage.func_75764_a/*was:getPlayerData*/(getName());
    }

    private net.minecraft.nbt.NBTTagCompound/*was:NBTTagCompound*/ getBukkitData() {
        net.minecraft.nbt.NBTTagCompound/*was:NBTTagCompound*/ result = getData();

        if (result != null) {
            if (!result.func_74764_b/*was:hasKey*/("bukkit")) {
                result.func_74766_a/*was:setCompound*/("bukkit", new net.minecraft.nbt.NBTTagCompound/*was:NBTTagCompound*/());
            }
            result = result.func_74775_l/*was:getCompound*/("bukkit");
        }

        return result;
    }

    private File getDataFile() {
        return new File(storage.getPlayerDir(), name + ".dat");
    }

    public long getFirstPlayed() {
        Player player = getPlayer();
        if (player != null) return player.getFirstPlayed();

        net.minecraft.nbt.NBTTagCompound/*was:NBTTagCompound*/ data = getBukkitData();

        if (data != null) {
            if (data.func_74764_b/*was:hasKey*/("firstPlayed")) {
                return data.func_74763_f/*was:getLong*/("firstPlayed");
            } else {
                File file = getDataFile();
                return file.lastModified();
            }
        } else {
            return 0;
        }
    }

    public long getLastPlayed() {
        Player player = getPlayer();
        if (player != null) return player.getLastPlayed();

        net.minecraft.nbt.NBTTagCompound/*was:NBTTagCompound*/ data = getBukkitData();

        if (data != null) {
            if (data.func_74764_b/*was:hasKey*/("lastPlayed")) {
                return data.func_74763_f/*was:getLong*/("lastPlayed");
            } else {
                File file = getDataFile();
                return file.lastModified();
            }
        } else {
            return 0;
        }
    }

    public boolean hasPlayedBefore() {
        return getData() != null;
    }

    public Location getBedSpawnLocation() {
        net.minecraft.nbt.NBTTagCompound/*was:NBTTagCompound*/ data = getData();
        if (data == null) return null;

        if (data.func_74764_b/*was:hasKey*/("SpawnX") && data.func_74764_b/*was:hasKey*/("SpawnY") && data.func_74764_b/*was:hasKey*/("SpawnZ")) {
            String spawnWorld = data.func_74779_i/*was:getString*/("SpawnWorld");
            if (spawnWorld.equals("")) {
                spawnWorld = server.getWorlds().get(0).getName();
            }
            return new Location(server.getWorld(spawnWorld), data.func_74762_e/*was:getInt*/("SpawnX"), data.func_74762_e/*was:getInt*/("SpawnY"), data.func_74762_e/*was:getInt*/("SpawnZ"));
        }
        return null;
    }

    public void setMetadata(String metadataKey, MetadataValue metadataValue) {
        server.getPlayerMetadata().setMetadata(this, metadataKey, metadataValue);
    }

    public List<MetadataValue> getMetadata(String metadataKey) {
        return server.getPlayerMetadata().getMetadata(this, metadataKey);
    }

    public boolean hasMetadata(String metadataKey) {
        return server.getPlayerMetadata().hasMetadata(this, metadataKey);
    }

    public void removeMetadata(String metadataKey, Plugin plugin) {
        server.getPlayerMetadata().removeMetadata(this, metadataKey, plugin);
    }
}
