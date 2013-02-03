package org.bukkit.craftbukkit.entity;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.MapMaker;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


import org.apache.commons.lang.Validate;
import org.apache.commons.lang.NotImplementedException;

import org.bukkit.*;
import org.bukkit.Achievement;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.conversations.ManuallyAbandonedConversationCanceller;
import org.bukkit.craftbukkit.conversations.ConversationTracker;
import org.bukkit.craftbukkit.CraftEffect;
import org.bukkit.craftbukkit.CraftOfflinePlayer;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftSound;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.map.CraftMapView;
import org.bukkit.craftbukkit.map.RenderData;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerRegisterChannelEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerUnregisterChannelEvent;
import org.bukkit.inventory.InventoryView.Property;
import org.bukkit.map.MapView;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.plugin.messaging.StandardMessenger;

@DelegateDeserialization(CraftOfflinePlayer.class)
public class CraftPlayer extends CraftHumanEntity implements Player {
    private long firstPlayed = 0;
    private long lastPlayed = 0;
    private boolean hasPlayedBefore = false;
    private final ConversationTracker conversationTracker = new ConversationTracker();
    private final Set<String> channels = new HashSet<String>();
    private final Map<String, Player> hiddenPlayers = new MapMaker().softValues().makeMap();
    private int hash = 0;

    public CraftPlayer(CraftServer server, net.minecraft.entity.player.EntityPlayerMP/*was:EntityPlayer*/ entity) {
        super(server, entity);

        firstPlayed = System.currentTimeMillis();
    }

    @Override
    public boolean isOp() {
        return server.getHandle().func_72353_e/*was:isOp*/(getName());
    }

    @Override
    public void setOp(boolean value) {
        if (value == isOp()) return;

        if (value) {
            server.getHandle().func_72386_b/*was:addOp*/(getName());
        } else {
            server.getHandle().func_72360_c/*was:removeOp*/(getName());
        }

        perm.recalculatePermissions();
    }

    public boolean isOnline() {
        for (Object obj : server.getHandle().field_72404_b/*was:players*/) {
            net.minecraft.entity.player.EntityPlayerMP/*was:EntityPlayer*/ player = (net.minecraft.entity.player.EntityPlayerMP/*was:EntityPlayer*/) obj;
            if (player.field_71092_bJ/*was:name*/.equalsIgnoreCase(getName())) {
                return true;
            }
        }
        return false;
    }

    public InetSocketAddress getAddress() {
        if (getHandle().field_71135_a/*was:playerConnection*/ == null) return null;

        SocketAddress addr = getHandle().field_71135_a/*was:playerConnection*/.field_72575_b/*was:networkManager*/.func_74430_c/*was:getSocketAddress*/();
        if (addr instanceof InetSocketAddress) {
            return (InetSocketAddress) addr;
        } else {
            return null;
        }
    }

    @Override
    public double getEyeHeight() {
        return getEyeHeight(false);
    }

    @Override
    public double getEyeHeight(boolean ignoreSneaking) {
        if (ignoreSneaking) {
            return 1.62D;
        } else {
            if (isSneaking()) {
                return 1.54D;
            } else {
                return 1.62D;
            }
        }
    }

    public void sendRawMessage(String message) {
        if (getHandle().field_71135_a/*was:playerConnection*/ == null) return;

        getHandle().field_71135_a/*was:playerConnection*/.func_72567_b/*was:sendPacket*/(new net.minecraft.network.packet.Packet3Chat/*was:Packet3Chat*/(message));
    }

    public void sendMessage(String message) {
        if (!conversationTracker.isConversingModaly()) {
            this.sendRawMessage(message);
        }
    }

    public void sendMessage(String[] messages) {
        for (String message : messages) {
            sendMessage(message);
        }
    }

    public String getDisplayName() {
        return getHandle().displayName;
    }

    public void setDisplayName(final String name) {
        getHandle().displayName = name;
    }

    public String getPlayerListName() {
        return getHandle().listName;
    }

    public void setPlayerListName(String name) {
        String oldName = getHandle().listName;

        if (name == null) {
            name = getName();
        }

        if (oldName.equals(name)) {
            return;
        }

        if (name.length() > 16) {
            throw new IllegalArgumentException("Player list names can only be a maximum of 16 characters long");
        }

        // Collisions will make for invisible people
        for (int i = 0; i < server.getHandle().field_72404_b/*was:players*/.size(); ++i) {
            if (((net.minecraft.entity.player.EntityPlayerMP/*was:EntityPlayer*/) server.getHandle().field_72404_b/*was:players*/.get(i)).listName.equals(name)) {
                throw new IllegalArgumentException(name + " is already assigned as a player list name for someone");
            }
        }

        getHandle().listName = name;

        // Change the name on the client side
        net.minecraft.network.packet.Packet201PlayerInfo/*was:Packet201PlayerInfo*/ oldpacket = new net.minecraft.network.packet.Packet201PlayerInfo/*was:Packet201PlayerInfo*/(oldName, false, 9999);
        net.minecraft.network.packet.Packet201PlayerInfo/*was:Packet201PlayerInfo*/ packet = new net.minecraft.network.packet.Packet201PlayerInfo/*was:Packet201PlayerInfo*/(name, true, getHandle().field_71138_i/*was:ping*/);
        for (int i = 0; i < server.getHandle().field_72404_b/*was:players*/.size(); ++i) {
            net.minecraft.entity.player.EntityPlayerMP/*was:EntityPlayer*/ entityplayer = (net.minecraft.entity.player.EntityPlayerMP/*was:EntityPlayer*/) server.getHandle().field_72404_b/*was:players*/.get(i);
            if (entityplayer.field_71135_a/*was:playerConnection*/ == null) continue;

            if (entityplayer.getBukkitEntity().canSee(this)) {
                entityplayer.field_71135_a/*was:playerConnection*/.func_72567_b/*was:sendPacket*/(oldpacket);
                entityplayer.field_71135_a/*was:playerConnection*/.func_72567_b/*was:sendPacket*/(packet);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof OfflinePlayer)) {
            return false;
        }
        OfflinePlayer other = (OfflinePlayer) obj;
        if ((this.getName() == null) || (other.getName() == null)) {
            return false;
        }

        boolean nameEquals = this.getName().equalsIgnoreCase(other.getName());
        boolean idEquals = true;

        if (other instanceof CraftPlayer) {
            idEquals = this.getEntityId() == ((CraftPlayer) other).getEntityId();
        }

        return nameEquals && idEquals;
    }

    public void kickPlayer(String message) {
        if (getHandle().field_71135_a/*was:playerConnection*/ == null) return;

        getHandle().field_71135_a/*was:playerConnection*/.func_72565_c/*was:disconnect*/(message == null ? "" : message);
    }

    public void setCompassTarget(Location loc) {
        if (getHandle().field_71135_a/*was:playerConnection*/ == null) return;

        // Do not directly assign here, from the packethandler we'll assign it.
        getHandle().field_71135_a/*was:playerConnection*/.func_72567_b/*was:sendPacket*/(new net.minecraft.network.packet.Packet6SpawnPosition/*was:Packet6SpawnPosition*/(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
    }

    public Location getCompassTarget() {
        return getHandle().compassTarget;
    }

    public void chat(String msg) {
        if (getHandle().field_71135_a/*was:playerConnection*/ == null) return;

        getHandle().field_71135_a/*was:playerConnection*/.chat(msg, false);
    }

    public boolean performCommand(String command) {
        return server.dispatchCommand(this, command);
    }

    public void playNote(Location loc, byte instrument, byte note) {
        if (getHandle().field_71135_a/*was:playerConnection*/ == null) return;

        int id = getHandle().field_70170_p/*was:world*/.func_72798_a/*was:getTypeId*/(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        getHandle().field_71135_a/*was:playerConnection*/.func_72567_b/*was:sendPacket*/(new net.minecraft.network.packet.Packet54PlayNoteBlock/*was:Packet54PlayNoteBlock*/(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), id, instrument, note));
    }

    public void playNote(Location loc, Instrument instrument, Note note) {
        if (getHandle().field_71135_a/*was:playerConnection*/ == null) return;

        int id = getHandle().field_70170_p/*was:world*/.func_72798_a/*was:getTypeId*/(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        getHandle().field_71135_a/*was:playerConnection*/.func_72567_b/*was:sendPacket*/(new net.minecraft.network.packet.Packet54PlayNoteBlock/*was:Packet54PlayNoteBlock*/(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), id, instrument.getType(), note.getId()));
    }

    public void playSound(Location loc, Sound sound, float volume, float pitch) {
        if (loc == null || sound == null || getHandle().field_71135_a/*was:playerConnection*/ == null) return;

        double x = loc.getBlockX() + 0.5;
        double y = loc.getBlockY() + 0.5;
        double z = loc.getBlockZ() + 0.5;

        net.minecraft.network.packet.Packet62LevelSound/*was:Packet62NamedSoundEffect*/ packet = new net.minecraft.network.packet.Packet62LevelSound/*was:Packet62NamedSoundEffect*/(CraftSound.getSound(sound), x, y, z, volume, pitch);
        getHandle().field_71135_a/*was:playerConnection*/.func_72567_b/*was:sendPacket*/(packet);
    }

    public void playEffect(Location loc, Effect effect, int data) {
        if (getHandle().field_71135_a/*was:playerConnection*/ == null) return;

        int packetData = effect.getId();
        net.minecraft.network.packet.Packet61DoorChange/*was:Packet61WorldEvent*/ packet = new net.minecraft.network.packet.Packet61DoorChange/*was:Packet61WorldEvent*/(packetData, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), data, false);
        getHandle().field_71135_a/*was:playerConnection*/.func_72567_b/*was:sendPacket*/(packet);
    }

    public <T> void playEffect(Location loc, Effect effect, T data) {
        if (data != null) {
            Validate.isTrue(data.getClass().equals(effect.getData()), "Wrong kind of data for this effect!");
        } else {
            Validate.isTrue(effect.getData() == null, "Wrong kind of data for this effect!");
        }

        int datavalue = data == null ? 0 : CraftEffect.getDataValue(effect, data);
        playEffect(loc, effect, datavalue);
    }

    public void sendBlockChange(Location loc, Material material, byte data) {
        sendBlockChange(loc, material.getId(), data);
    }

    public void sendBlockChange(Location loc, int material, byte data) {
        if (getHandle().field_71135_a/*was:playerConnection*/ == null) return;

        net.minecraft.network.packet.Packet53BlockChange/*was:Packet53BlockChange*/ packet = new net.minecraft.network.packet.Packet53BlockChange/*was:Packet53BlockChange*/(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), ((CraftWorld) loc.getWorld()).getHandle());

        packet.field_73421_d/*was:material*/ = material;
        packet.field_73422_e/*was:data*/ = data;
        getHandle().field_71135_a/*was:playerConnection*/.func_72567_b/*was:sendPacket*/(packet);
    }

    public boolean sendChunkChange(Location loc, int sx, int sy, int sz, byte[] data) {
        if (getHandle().field_71135_a/*was:playerConnection*/ == null) return false;

        /*
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();

        int cx = x >> 4;
        int cz = z >> 4;

        if (sx <= 0 || sy <= 0 || sz <= 0) {
            return false;
        }

        if ((x + sx - 1) >> 4 != cx || (z + sz - 1) >> 4 != cz || y < 0 || y + sy > 128) {
            return false;
        }

        if (data.length != (sx * sy * sz * 5) / 2) {
            return false;
        }

        Packet51MapChunk packet = new Packet51MapChunk(x, y, z, sx, sy, sz, data);

        getHandle().playerConnection.sendPacket(packet);

        return true;
        */

        throw new NotImplementedException("Chunk changes do not yet work"); // TODO: Chunk changes.
    }

    public void sendMap(MapView map) {
        if (getHandle().field_71135_a/*was:playerConnection*/ == null) return;

        RenderData data = ((CraftMapView) map).render(this);
        for (int x = 0; x < 128; ++x) {
            byte[] bytes = new byte[131];
            bytes[1] = (byte) x;
            for (int y = 0; y < 128; ++y) {
                bytes[y + 3] = data.buffer[y * 128 + x];
            }
            net.minecraft.network.packet.Packet131MapData/*was:Packet131ItemData*/ packet = new net.minecraft.network.packet.Packet131MapData/*was:Packet131ItemData*/((short) Material.MAP.getId(), map.getId(), bytes);
            getHandle().field_71135_a/*was:playerConnection*/.func_72567_b/*was:sendPacket*/(packet);
        }
    }

    @Override
    public boolean teleport(Location location, PlayerTeleportEvent.TeleportCause cause) {
        net.minecraft.entity.player.EntityPlayerMP/*was:EntityPlayer*/ entity = getHandle();

        if (getHealth() == 0 || entity.field_70128_L/*was:dead*/) {
            return false;
        }

        if (entity.field_71135_a/*was:playerConnection*/ == null || entity.field_71135_a/*was:playerConnection*/.field_72576_c/*was:disconnected*/) {
            return false;
        }

        if (entity.field_70154_o/*was:vehicle*/ != null || entity.field_70153_n/*was:passenger*/ != null) {
            return false;
        }

        // From = Players current Location
        Location from = this.getLocation();
        // To = Players new Location if Teleport is Successful
        Location to = location;
        // Create & Call the Teleport Event.
        PlayerTeleportEvent event = new PlayerTeleportEvent((Player) this, from, to, cause);
        server.getPluginManager().callEvent(event);

        // Return False to inform the Plugin that the Teleport was unsuccessful/cancelled.
        if (event.isCancelled()) {
            return false;
        }

        // Update the From Location
        from = event.getFrom();
        // Grab the new To Location dependent on whether the event was cancelled.
        to = event.getTo();
        // Grab the To and From World Handles.
        net.minecraft.world.WorldServer/*was:WorldServer*/ fromWorld = ((CraftWorld) from.getWorld()).getHandle();
        net.minecraft.world.WorldServer/*was:WorldServer*/ toWorld = ((CraftWorld) to.getWorld()).getHandle();

        // Check if the fromWorld and toWorld are the same.
        if (fromWorld == toWorld) {
            entity.field_71135_a/*was:playerConnection*/.teleport(to);
        } else {
            // Close any foreign inventory
            if (getHandle().field_71070_bA/*was:activeContainer*/ != getHandle().field_71069_bz/*was:defaultContainer*/){
                getHandle().func_71053_j/*was:closeInventory*/();
            }
            server.getHandle().moveToWorld(entity, toWorld.dimension, true, to, true);
        }
        return true;
    }

    public void setSneaking(boolean sneak) {
        getHandle().func_70095_a/*was:setSneaking*/(sneak);
    }

    public boolean isSneaking() {
        return getHandle().func_70093_af/*was:isSneaking*/();
    }

    public boolean isSprinting() {
        return getHandle().func_70051_ag/*was:isSprinting*/();
    }

    public void setSprinting(boolean sprinting) {
        getHandle().func_70031_b/*was:setSprinting*/(sprinting);
    }

    public void loadData() {
        server.getHandle().field_72412_k/*was:playerFileData*/.func_75752_b/*was:load*/(getHandle());
    }

    public void saveData() {
        server.getHandle().field_72412_k/*was:playerFileData*/.func_75753_a/*was:save*/(getHandle());
    }

    public void updateInventory() {
        getHandle().func_71120_a/*was:updateInventory*/(getHandle().field_71070_bA/*was:activeContainer*/);
    }

    public void setSleepingIgnored(boolean isSleeping) {
        getHandle().fauxSleeping = isSleeping;
        ((CraftWorld) getWorld()).getHandle().checkSleepStatus();
    }

    public boolean isSleepingIgnored() {
        return getHandle().fauxSleeping;
    }

    public void awardAchievement(Achievement achievement) {
        sendStatistic(achievement.getId(), 1);
    }

    public void incrementStatistic(Statistic statistic) {
        incrementStatistic(statistic, 1);
    }

    public void incrementStatistic(Statistic statistic, int amount) {
        sendStatistic(statistic.getId(), amount);
    }

    public void incrementStatistic(Statistic statistic, Material material) {
        incrementStatistic(statistic, material, 1);
    }

    public void incrementStatistic(Statistic statistic, Material material, int amount) {
        if (!statistic.isSubstatistic()) {
            throw new IllegalArgumentException("Given statistic is not a substatistic");
        }
        if (statistic.isBlock() != material.isBlock()) {
            throw new IllegalArgumentException("Given material is not valid for this substatistic");
        }

        int mat = material.getId();

        if (!material.isBlock()) {
            mat -= 255;
        }

        sendStatistic(statistic.getId() + mat, amount);
    }

    private void sendStatistic(int id, int amount) {
        if (getHandle().field_71135_a/*was:playerConnection*/ == null) return;

        while (amount > Byte.MAX_VALUE) {
            sendStatistic(id, Byte.MAX_VALUE);
            amount -= Byte.MAX_VALUE;
        }

        getHandle().field_71135_a/*was:playerConnection*/.func_72567_b/*was:sendPacket*/(new net.minecraft.network.packet.Packet200Statistic/*was:Packet200Statistic*/(id, amount));
    }

    public void setPlayerTime(long time, boolean relative) {
        getHandle().timeOffset = time;
        getHandle().relativeTime = relative;
    }

    public long getPlayerTimeOffset() {
        return getHandle().timeOffset;
    }

    public long getPlayerTime() {
        return getHandle().getPlayerTime();
    }

    public boolean isPlayerTimeRelative() {
        return getHandle().relativeTime;
    }

    public void resetPlayerTime() {
        setPlayerTime(0, true);
    }

    public boolean isBanned() {
        return server.getHandle().func_72390_e/*was:getNameBans*/().func_73704_a/*was:isBanned*/(getName().toLowerCase());
    }

    public void setBanned(boolean value) {
        if (value) {
            net.minecraft.server.management.BanEntry/*was:BanEntry*/ entry = new net.minecraft.server.management.BanEntry/*was:BanEntry*/(getName().toLowerCase());
            server.getHandle().func_72390_e/*was:getNameBans*/().func_73706_a/*was:add*/(entry);
        } else {
            server.getHandle().func_72390_e/*was:getNameBans*/().func_73709_b/*was:remove*/(getName().toLowerCase());
        }

        server.getHandle().func_72390_e/*was:getNameBans*/().func_73711_f/*was:save*/();
    }

    public boolean isWhitelisted() {
        return server.getHandle().func_72388_h/*was:getWhitelisted*/().contains(getName().toLowerCase());
    }

    public void setWhitelisted(boolean value) {
        if (value) {
            server.getHandle().func_72359_h/*was:addWhitelist*/(getName().toLowerCase());
        } else {
            server.getHandle().func_72379_i/*was:removeWhitelist*/(getName().toLowerCase());
        }
    }

    @Override
    public void setGameMode(GameMode mode) {
        if (getHandle().field_71135_a/*was:playerConnection*/ == null) return;

        if (mode == null) {
            throw new IllegalArgumentException("Mode cannot be null");
        }

        if (mode != getGameMode()) {
            PlayerGameModeChangeEvent event = new PlayerGameModeChangeEvent(this, mode);
            server.getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                return;
            }

            getHandle().field_71134_c/*was:playerInteractManager*/.func_73076_a/*was:setGameMode*/(net.minecraft.world.EnumGameType/*was:EnumGamemode*/.func_77146_a/*was:a*/(mode.getValue()));
            getHandle().field_71135_a/*was:playerConnection*/.func_72567_b/*was:sendPacket*/(new net.minecraft.network.packet.Packet70GameEvent/*was:Packet70Bed*/(3, mode.getValue()));
        }
    }

    @Override
    public GameMode getGameMode() {
        return GameMode.getByValue(getHandle().field_71134_c/*was:playerInteractManager*/.func_73081_b/*was:getGameMode*/().func_77148_a/*was:a*/());
    }

    public void giveExp(int exp) {
        getHandle().func_71023_q/*was:giveExp*/(exp);
    }

    public void giveExpLevels(int levels) {
        getHandle().func_82242_a/*was:levelDown*/(levels);
    }

    public float getExp() {
        return getHandle().field_71106_cc/*was:exp*/;
    }

    public void setExp(float exp) {
        getHandle().field_71106_cc/*was:exp*/ = exp;
        getHandle().field_71144_ck/*was:lastSentExp*/ = -1;
    }

    public int getLevel() {
        return getHandle().field_71068_ca/*was:expLevel*/;
    }

    public void setLevel(int level) {
        getHandle().field_71068_ca/*was:expLevel*/ = level;
        getHandle().field_71144_ck/*was:lastSentExp*/ = -1;
    }

    public int getTotalExperience() {
        return getHandle().field_71067_cb/*was:expTotal*/;
    }

    public void setTotalExperience(int exp) {
        getHandle().field_71067_cb/*was:expTotal*/ = exp;
    }

    public float getExhaustion() {
        return getHandle().func_71024_bL/*was:getFoodData*/().field_75126_c/*was:exhaustionLevel*/;
    }

    public void setExhaustion(float value) {
        getHandle().func_71024_bL/*was:getFoodData*/().field_75126_c/*was:exhaustionLevel*/ = value;
    }

    public float getSaturation() {
        return getHandle().func_71024_bL/*was:getFoodData*/().field_75125_b/*was:saturationLevel*/;
    }

    public void setSaturation(float value) {
        getHandle().func_71024_bL/*was:getFoodData*/().field_75125_b/*was:saturationLevel*/ = value;
    }

    public int getFoodLevel() {
        return getHandle().func_71024_bL/*was:getFoodData*/().field_75127_a/*was:foodLevel*/;
    }

    public void setFoodLevel(int value) {
        getHandle().func_71024_bL/*was:getFoodData*/().field_75127_a/*was:foodLevel*/ = value;
    }

    public Location getBedSpawnLocation() {
        World world = getServer().getWorld(getHandle().spawnWorld);
        net.minecraft.util.ChunkCoordinates/*was:ChunkCoordinates*/ bed = getHandle().func_70997_bJ/*was:getBed*/();

        if (world != null && bed != null) {
            bed = net.minecraft.entity.player.EntityPlayer/*was:EntityHuman*/.func_71056_a/*was:getBed*/(((CraftWorld) world).getHandle(), bed, getHandle().func_82245_bX/*was:isRespawnForced*/());
            if (bed != null) {
                return new Location(world, bed.field_71574_a/*was:x*/, bed.field_71572_b/*was:y*/, bed.field_71573_c/*was:z*/);
            }
        }
        return null;
    }

    public void setBedSpawnLocation(Location location) {
        setBedSpawnLocation(location, false);
    }

    public void setBedSpawnLocation(Location location, boolean override) {
        if (location == null) {
            getHandle().func_71063_a/*was:setRespawnPosition*/(null, override);
        } else {
            getHandle().func_71063_a/*was:setRespawnPosition*/(new net.minecraft.util.ChunkCoordinates/*was:ChunkCoordinates*/(location.getBlockX(), location.getBlockY(), location.getBlockZ()), override);
            getHandle().spawnWorld = location.getWorld().getName();
        }
    }

    public void hidePlayer(Player player) {
        Validate.notNull(player, "hidden player cannot be null");
        if (getHandle().field_71135_a/*was:playerConnection*/ == null) return;
        if (equals(player)) return;
        if (hiddenPlayers.containsKey(player.getName())) return;
        hiddenPlayers.put(player.getName(), player);

        //remove this player from the hidden player's EntityTrackerEntry
        net.minecraft.entity.EntityTracker/*was:EntityTracker*/ tracker = ((net.minecraft.world.WorldServer/*was:WorldServer*/) entity.field_70170_p/*was:world*/).field_73062_L/*was:tracker*/;
        net.minecraft.entity.player.EntityPlayerMP/*was:EntityPlayer*/ other = ((CraftPlayer) player).getHandle();
        net.minecraft.entity.EntityTrackerEntry/*was:EntityTrackerEntry*/ entry = (net.minecraft.entity.EntityTrackerEntry/*was:EntityTrackerEntry*/) tracker.field_72794_c/*was:trackedEntities*/.func_76041_a/*was:get*/(other.field_70157_k/*was:id*/);
        if (entry != null) {
            entry.func_73123_c/*was:clear*/(getHandle());
        }

        //remove the hidden player from this player user list
        getHandle().field_71135_a/*was:playerConnection*/.func_72567_b/*was:sendPacket*/(new net.minecraft.network.packet.Packet201PlayerInfo/*was:Packet201PlayerInfo*/(player.getPlayerListName(), false, 9999));
    }

    public void showPlayer(Player player) {
        Validate.notNull(player, "shown player cannot be null");
        if (getHandle().field_71135_a/*was:playerConnection*/ == null) return;
        if (equals(player)) return;
        if (!hiddenPlayers.containsKey(player.getName())) return;
        hiddenPlayers.remove(player.getName());

        net.minecraft.entity.EntityTracker/*was:EntityTracker*/ tracker = ((net.minecraft.world.WorldServer/*was:WorldServer*/) entity.field_70170_p/*was:world*/).field_73062_L/*was:tracker*/;
        net.minecraft.entity.player.EntityPlayerMP/*was:EntityPlayer*/ other = ((CraftPlayer) player).getHandle();
        net.minecraft.entity.EntityTrackerEntry/*was:EntityTrackerEntry*/ entry = (net.minecraft.entity.EntityTrackerEntry/*was:EntityTrackerEntry*/) tracker.field_72794_c/*was:trackedEntities*/.func_76041_a/*was:get*/(other.field_70157_k/*was:id*/);
        if (entry != null && !entry.field_73134_o/*was:trackedPlayers*/.contains(getHandle())) {
            entry.func_73117_b/*was:updatePlayer*/(getHandle());
        }

        getHandle().field_71135_a/*was:playerConnection*/.func_72567_b/*was:sendPacket*/(new net.minecraft.network.packet.Packet201PlayerInfo/*was:Packet201PlayerInfo*/(player.getPlayerListName(), true, getHandle().field_71138_i/*was:ping*/));
    }

    public boolean canSee(Player player) {
        return !hiddenPlayers.containsKey(player.getName());
    }

    public Map<String, Object> serialize() {
        Map<String, Object> result = new LinkedHashMap<String, Object>();

        result.put("name", getName());

        return result;
    }

    public Player getPlayer() {
        return this;
    }

    @Override
    public net.minecraft.entity.player.EntityPlayerMP/*was:EntityPlayer*/ getHandle() {
        return (net.minecraft.entity.player.EntityPlayerMP/*was:EntityPlayer*/) entity;
    }

    public void setHandle(final net.minecraft.entity.player.EntityPlayerMP/*was:EntityPlayer*/ entity) {
        super.setHandle(entity);
    }

    @Override
    public String toString() {
        return "CraftPlayer{" + "name=" + getName() + '}';
    }

    @Override
    public int hashCode() {
        if (hash == 0 || hash == 485) {
            hash = 97 * 5 + (this.getName() != null ? this.getName().toLowerCase().hashCode() : 0);
        }
        return hash;
    }

    public long getFirstPlayed() {
        return firstPlayed;
    }

    public long getLastPlayed() {
        return lastPlayed;
    }

    public boolean hasPlayedBefore() {
        return hasPlayedBefore;
    }

    public void setFirstPlayed(long firstPlayed) {
        this.firstPlayed = firstPlayed;
    }

    public void readExtraData(net.minecraft.nbt.NBTTagCompound/*was:NBTTagCompound*/ nbttagcompound) {
        hasPlayedBefore = true;
        if (nbttagcompound.func_74764_b/*was:hasKey*/("bukkit")) {
            net.minecraft.nbt.NBTTagCompound/*was:NBTTagCompound*/ data = nbttagcompound.func_74775_l/*was:getCompound*/("bukkit");

            if (data.func_74764_b/*was:hasKey*/("firstPlayed")) {
                firstPlayed = data.func_74763_f/*was:getLong*/("firstPlayed");
                lastPlayed = data.func_74763_f/*was:getLong*/("lastPlayed");
            }

            if (data.func_74764_b/*was:hasKey*/("newExp")) {
                net.minecraft.entity.player.EntityPlayerMP/*was:EntityPlayer*/ handle = getHandle();
                handle.newExp = data.func_74762_e/*was:getInt*/("newExp");
                handle.newTotalExp = data.func_74762_e/*was:getInt*/("newTotalExp");
                handle.newLevel = data.func_74762_e/*was:getInt*/("newLevel");
                handle.expToDrop = data.func_74762_e/*was:getInt*/("expToDrop");
                handle.keepLevel = data.func_74767_n/*was:getBoolean*/("keepLevel");
            }
        }
    }

    public void setExtraData(net.minecraft.nbt.NBTTagCompound/*was:NBTTagCompound*/ nbttagcompound) {
        if (!nbttagcompound.func_74764_b/*was:hasKey*/("bukkit")) {
            nbttagcompound.func_74766_a/*was:setCompound*/("bukkit", new net.minecraft.nbt.NBTTagCompound/*was:NBTTagCompound*/());
        }

        net.minecraft.nbt.NBTTagCompound/*was:NBTTagCompound*/ data = nbttagcompound.func_74775_l/*was:getCompound*/("bukkit");
        net.minecraft.entity.player.EntityPlayerMP/*was:EntityPlayer*/ handle = getHandle();
        data.func_74768_a/*was:setInt*/("newExp", handle.newExp);
        data.func_74768_a/*was:setInt*/("newTotalExp", handle.newTotalExp);
        data.func_74768_a/*was:setInt*/("newLevel", handle.newLevel);
        data.func_74768_a/*was:setInt*/("expToDrop", handle.expToDrop);
        data.func_74757_a/*was:setBoolean*/("keepLevel", handle.keepLevel);
        data.func_74772_a/*was:setLong*/("firstPlayed", getFirstPlayed());
        data.func_74772_a/*was:setLong*/("lastPlayed", System.currentTimeMillis());
    }

    public boolean beginConversation(Conversation conversation) {
        return conversationTracker.beginConversation(conversation);
    }

    public void abandonConversation(Conversation conversation) {
        conversationTracker.abandonConversation(conversation, new ConversationAbandonedEvent(conversation, new ManuallyAbandonedConversationCanceller()));
    }

    public void abandonConversation(Conversation conversation, ConversationAbandonedEvent details) {
        conversationTracker.abandonConversation(conversation, details);
    }

    public void acceptConversationInput(String input) {
        conversationTracker.acceptConversationInput(input);
    }

    public boolean isConversing() {
        return conversationTracker.isConversing();
    }

    public void sendPluginMessage(Plugin source, String channel, byte[] message) {
        StandardMessenger.validatePluginMessage(server.getMessenger(), source, channel, message);
        if (getHandle().field_71135_a/*was:playerConnection*/ == null) return;

        if (channels.contains(channel)) {
            net.minecraft.network.packet.Packet250CustomPayload/*was:Packet250CustomPayload*/ packet = new net.minecraft.network.packet.Packet250CustomPayload/*was:Packet250CustomPayload*/();
            packet.field_73630_a/*was:tag*/ = channel;
            packet.field_73628_b/*was:length*/ = message.length;
            packet.field_73629_c/*was:data*/ = message;
            getHandle().field_71135_a/*was:playerConnection*/.func_72567_b/*was:sendPacket*/(packet);
        }
    }

    public void setTexturePack(String url) {
        Validate.notNull(url, "Texture pack URL cannot be null");

        byte[] message = (url + "\0" + "16").getBytes();
        Validate.isTrue(message.length <= Messenger.MAX_MESSAGE_SIZE, "Texture pack URL is too long");

        getHandle().field_71135_a/*was:playerConnection*/.func_72567_b/*was:sendPacket*/(new net.minecraft.network.packet.Packet250CustomPayload/*was:Packet250CustomPayload*/("MC|TPack", message));
    }

    public void addChannel(String channel) {
        if (channels.add(channel)) {
            server.getPluginManager().callEvent(new PlayerRegisterChannelEvent(this, channel));
        }
    }

    public void removeChannel(String channel) {
        if (channels.remove(channel)) {
            server.getPluginManager().callEvent(new PlayerUnregisterChannelEvent(this, channel));
        }
    }

    public Set<String> getListeningPluginChannels() {
        return ImmutableSet.copyOf(channels);
    }

    public void sendSupportedChannels() {
        if (getHandle().field_71135_a/*was:playerConnection*/ == null) return;
        Set<String> listening = server.getMessenger().getIncomingChannels();

        if (!listening.isEmpty()) {
            net.minecraft.network.packet.Packet250CustomPayload/*was:Packet250CustomPayload*/ packet = new net.minecraft.network.packet.Packet250CustomPayload/*was:Packet250CustomPayload*/();

            packet.field_73630_a/*was:tag*/ = "REGISTER";
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            for (String channel : listening) {
                try {
                    stream.write(channel.getBytes("UTF8"));
                    stream.write((byte) 0);
                } catch (IOException ex) {
                    Logger.getLogger(CraftPlayer.class.getName()).log(Level.SEVERE, "Could not send Plugin Channel REGISTER to " + getName(), ex);
                }
            }

            packet.field_73629_c/*was:data*/ = stream.toByteArray();
            packet.field_73628_b/*was:length*/ = packet.field_73629_c/*was:data*/.length;

            getHandle().field_71135_a/*was:playerConnection*/.func_72567_b/*was:sendPacket*/(packet);
        }
    }

    public EntityType getType() {
        return EntityType.PLAYER;
    }

    public void setMetadata(String metadataKey, MetadataValue newMetadataValue) {
        server.getPlayerMetadata().setMetadata(this, metadataKey, newMetadataValue);
    }

    @Override
    public List<MetadataValue> getMetadata(String metadataKey) {
        return server.getPlayerMetadata().getMetadata(this, metadataKey);
    }

    @Override
    public boolean hasMetadata(String metadataKey) {
        return server.getPlayerMetadata().hasMetadata(this, metadataKey);
    }

    @Override
    public void removeMetadata(String metadataKey, Plugin owningPlugin) {
        server.getPlayerMetadata().removeMetadata(this, metadataKey, owningPlugin);
    }

    @Override
    public boolean setWindowProperty(Property prop, int value) {
        net.minecraft.inventory.Container/*was:Container*/ container = getHandle().field_71070_bA/*was:activeContainer*/;
        if (container.getBukkitView().getType() != prop.getType()) {
            return false;
        }
        getHandle().func_71112_a/*was:setContainerData*/(container, prop.getId(), value);
        return true;
    }

    public void disconnect(String reason) {
        conversationTracker.abandonAllConversations();
        perm.clearPermissions();
    }

    public boolean isFlying() {
        return getHandle().field_71075_bZ/*was:abilities*/.field_75100_b/*was:isFlying*/;
    }

    public void setFlying(boolean value) {
        if (!getAllowFlight() && value) {
            throw new IllegalArgumentException("Cannot make player fly if getAllowFlight() is false");
        }

        getHandle().field_71075_bZ/*was:abilities*/.field_75100_b/*was:isFlying*/ = value;
        getHandle().func_71016_p/*was:updateAbilities*/();
    }

    public boolean getAllowFlight() {
        return getHandle().field_71075_bZ/*was:abilities*/.field_75101_c/*was:canFly*/;
    }

    public void setAllowFlight(boolean value) {
        if (isFlying() && !value) {
            getHandle().field_71075_bZ/*was:abilities*/.field_75100_b/*was:isFlying*/ = false;
        }

        getHandle().field_71075_bZ/*was:abilities*/.field_75101_c/*was:canFly*/ = value;
        getHandle().func_71016_p/*was:updateAbilities*/();
    }

    @Override
    public int getNoDamageTicks() {
        if (getHandle().field_71145_cl/*was:invulnerableTicks*/ > 0) {
            return Math.max(getHandle().field_71145_cl/*was:invulnerableTicks*/, getHandle().field_70172_ad/*was:noDamageTicks*/);
        } else {
            return getHandle().field_70172_ad/*was:noDamageTicks*/;
        }
    }

    public void setFlySpeed(float value) {
        validateSpeed(value);
        net.minecraft.entity.player.EntityPlayerMP/*was:EntityPlayer*/ player = getHandle();
        player.field_71075_bZ/*was:abilities*/.field_75096_f/*was:flySpeed*/ = value / 2f;
        player.func_71016_p/*was:updateAbilities*/();

    }

    public void setWalkSpeed(float value) {
        validateSpeed(value);
        net.minecraft.entity.player.EntityPlayerMP/*was:EntityPlayer*/ player = getHandle();
        player.field_71075_bZ/*was:abilities*/.field_75097_g/*was:walkSpeed*/ = value / 2f;
        player.func_71016_p/*was:updateAbilities*/();
    }

    public float getFlySpeed() {
        return getHandle().field_71075_bZ/*was:abilities*/.field_75096_f/*was:flySpeed*/ * 2f;
    }

    public float getWalkSpeed() {
        return getHandle().field_71075_bZ/*was:abilities*/.field_75097_g/*was:walkSpeed*/ * 2f;
    }

    private void validateSpeed(float value) {
        if (value < 0) {
            if (value < -1f) {
                throw new IllegalArgumentException(value + " is too low");
            }
        } else {
            if (value > 1f) {
                throw new IllegalArgumentException(value + " is too high");
            }
        }
    }

    public void setMaxHealth(int amount) {
        super.setMaxHealth(amount);
        getHandle().func_71118_n/*was:triggerHealthUpdate*/();
    }

    public void resetMaxHealth() {
        super.resetMaxHealth();
        getHandle().func_71118_n/*was:triggerHealthUpdate*/();
    }
}
