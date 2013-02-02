package net.minecraft.server.management;

import java.io.File;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemInWorldManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.NetServerHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet16BlockItemSwitch;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.network.packet.Packet201PlayerInfo;
import net.minecraft.network.packet.Packet202PlayerAbilities;
import net.minecraft.network.packet.Packet3Chat;
import net.minecraft.network.packet.Packet41EntityEffect;
import net.minecraft.network.packet.Packet43Experience;
import net.minecraft.network.packet.Packet4UpdateTime;
import net.minecraft.network.packet.Packet6SpawnPosition;
import net.minecraft.network.packet.Packet70GameEvent;
import net.minecraft.network.packet.Packet9Respawn;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumGameType;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.demo.DemoWorldManager;
import net.minecraft.world.storage.IPlayerFileData;

// CraftBukkit start
import org.bukkit.Location;
import org.bukkit.TravelAgent;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.chunkio.ChunkIOExecutor;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.util.Vector;
import org.bukkit.Bukkit;
// CraftBukkit end

public abstract class ServerConfigurationManager
{
    private static final SimpleDateFormat field_72403_e = new SimpleDateFormat("yyyy-MM-dd \'at\' HH:mm:ss z");
    public static final Logger field_72406_a = Logger.getLogger("Minecraft");
    private final MinecraftServer field_72400_f;
    public final List field_72404_b = new java.util.concurrent.CopyOnWriteArrayList(); // CraftBukkit - ArrayList -> CopyOnWriteArrayList: Iterator safety
    private final BanList field_72401_g = new BanList(new File("banned-players.txt"));
    private final BanList field_72413_h = new BanList(new File("banned-ips.txt"));
    private Set field_72414_i = new HashSet();
    private Set field_72411_j = new java.util.LinkedHashSet(); // CraftBukkit - HashSet -> LinkedHashSet
    public IPlayerFileData field_72412_k; // CraftBukkit - private -> public
    public boolean field_72409_l; // CraftBukkit - private -> public
    protected int field_72405_c;
    protected int field_72402_d;
    private EnumGameType field_72410_m;
    private boolean field_72407_n;
    private int field_72408_o = 0;

    // CraftBukkit start
    private CraftServer cserver;

    public ServerConfigurationManager(MinecraftServer p_i3376_1_)
    {
        p_i3376_1_.server = new CraftServer(p_i3376_1_, this);
        p_i3376_1_.console = org.bukkit.craftbukkit.command.ColouredConsoleSender.getInstance();
        this.cserver = p_i3376_1_.server;
        // CraftBukkit end
        this.field_72400_f = p_i3376_1_;
        this.field_72401_g.func_73708_a(false);
        this.field_72413_h.func_73708_a(false);
        this.field_72405_c = 8;
    }

    public void func_72355_a(INetworkManager p_72355_1_, EntityPlayerMP p_72355_2_)
    {
        this.func_72380_a(p_72355_2_);
        p_72355_2_.func_70029_a(this.field_72400_f.func_71218_a(p_72355_2_.field_71093_bK));
        p_72355_2_.field_71134_c.func_73080_a((WorldServer)p_72355_2_.field_70170_p);
        String s = "local";

        if (p_72355_1_.func_74430_c() != null)
        {
            s = p_72355_1_.func_74430_c().toString();
        }

        // CraftBukkit - add world and location to 'logged in' message.
        field_72406_a.info(p_72355_2_.field_71092_bJ + "[" + s + "] logged in with entity id " + p_72355_2_.field_70157_k + " at ([" + p_72355_2_.field_70170_p.field_72986_A.func_76065_j() + "] " + p_72355_2_.field_70165_t + ", " + p_72355_2_.field_70163_u + ", " + p_72355_2_.field_70161_v + ")");
        WorldServer worldserver = this.field_72400_f.func_71218_a(p_72355_2_.field_71093_bK);
        ChunkCoordinates chunkcoordinates = worldserver.func_72861_E();
        this.func_72381_a(p_72355_2_, (EntityPlayerMP)null, worldserver);
        NetServerHandler netserverhandler = new NetServerHandler(this.field_72400_f, p_72355_1_, p_72355_2_);
        // CraftBukkit start -- Don't send a higher than 60 MaxPlayer size, otherwise the PlayerInfo window won't render correctly.
        int maxPlayers = this.func_72352_l();

        if (maxPlayers > 60)
        {
            maxPlayers = 60;
        }

        netserverhandler.func_72567_b(new Packet1Login(p_72355_2_.field_70157_k, worldserver.func_72912_H().func_76067_t(), p_72355_2_.field_71134_c.func_73081_b(), worldserver.func_72912_H().func_76093_s(), worldserver.field_73011_w.field_76574_g, worldserver.field_73013_u, worldserver.func_72800_K(), maxPlayers));
        p_72355_2_.getBukkitEntity().sendSupportedChannels();
        // CraftBukkit end
        netserverhandler.func_72567_b(new Packet6SpawnPosition(chunkcoordinates.field_71574_a, chunkcoordinates.field_71572_b, chunkcoordinates.field_71573_c));
        netserverhandler.func_72567_b(new Packet202PlayerAbilities(p_72355_2_.field_71075_bZ));
        netserverhandler.func_72567_b(new Packet16BlockItemSwitch(p_72355_2_.field_71071_by.field_70461_c));
        this.func_72354_b(p_72355_2_, worldserver);
        // this.sendAll(new Packet3Chat("\u00A7e" + entityplayermp.name + " joined the game.")); // CraftBukkit - handled in event
        this.func_72377_c(p_72355_2_);
        netserverhandler.func_72569_a(p_72355_2_.field_70165_t, p_72355_2_.field_70163_u, p_72355_2_.field_70161_v, p_72355_2_.field_70177_z, p_72355_2_.field_70125_A);
        this.field_72400_f.func_71212_ac().func_71745_a(netserverhandler);
        netserverhandler.func_72567_b(new Packet4UpdateTime(worldserver.func_82737_E(), worldserver.func_72820_D()));

        if (this.field_72400_f.func_71202_P().length() > 0)
        {
            p_72355_2_.func_71115_a(this.field_72400_f.func_71202_P(), this.field_72400_f.func_71227_R());
        }

        Iterator iterator = p_72355_2_.func_70651_bq().iterator();

        while (iterator.hasNext())
        {
            PotionEffect potioneffect = (PotionEffect)iterator.next();
            netserverhandler.func_72567_b(new Packet41EntityEffect(p_72355_2_.field_70157_k, potioneffect));
        }

        p_72355_2_.func_71116_b();
    }

    public void func_72364_a(WorldServer[] p_72364_1_)
    {
        if (this.field_72412_k != null)
        {
            return;    // CraftBukkit
        }

        this.field_72412_k = p_72364_1_[0].func_72860_G().func_75756_e();
    }

    public void func_72375_a(EntityPlayerMP p_72375_1_, WorldServer p_72375_2_)
    {
        WorldServer worldserver1 = p_72375_1_.func_71121_q();

        if (p_72375_2_ != null)
        {
            p_72375_2_.func_73040_p().func_72695_c(p_72375_1_);
        }

        worldserver1.func_73040_p().func_72683_a(p_72375_1_);
        worldserver1.field_73059_b.func_73158_c((int)p_72375_1_.field_70165_t >> 4, (int)p_72375_1_.field_70161_v >> 4);
    }

    public int func_72372_a()
    {
        return PlayerManager.func_72686_a(this.func_72395_o());
    }

    public void func_72380_a(EntityPlayerMP p_72380_1_)
    {
        NBTTagCompound nbttagcompound = this.field_72400_f.worlds.get(0).func_72912_H().func_76072_h(); // CraftBukkit

        if (p_72380_1_.func_70005_c_().equals(this.field_72400_f.func_71214_G()) && nbttagcompound != null)
        {
            p_72380_1_.func_70020_e(nbttagcompound);
        }
        else
        {
            this.field_72412_k.func_75752_b(p_72380_1_);
        }
    }

    protected void func_72391_b(EntityPlayerMP p_72391_1_)
    {
        this.field_72412_k.func_75753_a(p_72391_1_);
    }

    public void func_72377_c(EntityPlayerMP p_72377_1_)
    {
        cserver.detectListNameConflict(p_72377_1_); // CraftBukkit
        // this.sendAll(new Packet201PlayerInfo(entityplayermp.name, true, 1000)); // CraftBukkit - replaced with loop below
        this.field_72404_b.add(p_72377_1_);
        WorldServer worldserver = this.field_72400_f.func_71218_a(p_72377_1_.field_71093_bK);
        // CraftBukkit start
        PlayerJoinEvent playerJoinEvent = new PlayerJoinEvent(this.cserver.getPlayer(p_72377_1_), "\u00A7e" + p_72377_1_.field_71092_bJ + " joined the game.");
        this.cserver.getPluginManager().callEvent(playerJoinEvent);
        String joinMessage = playerJoinEvent.getJoinMessage();

        if ((joinMessage != null) && (joinMessage.length() > 0))
        {
            this.field_72400_f.func_71203_ab().func_72384_a(new Packet3Chat(joinMessage));
        }

        this.cserver.onPlayerJoin(playerJoinEvent.getPlayer());
        ChunkIOExecutor.adjustPoolSize(this.func_72394_k());
        // CraftBukkit end

        // CraftBukkit start - only add if the player wasn't moved in the event
        if (p_72377_1_.field_70170_p == worldserver && !worldserver.field_73010_i.contains(p_72377_1_))
        {
            worldserver.func_72838_d(p_72377_1_);
            this.func_72375_a(p_72377_1_, (WorldServer) null);
        }

        // CraftBukkit end
        // CraftBukkit start - sendAll above replaced with this loop
        Packet201PlayerInfo packet = new Packet201PlayerInfo(p_72377_1_.listName, true, 1000);

        for (int i = 0; i < this.field_72404_b.size(); ++i)
        {
            EntityPlayerMP entityplayermp1 = (EntityPlayerMP)this.field_72404_b.get(i);

            if (entityplayermp1.getBukkitEntity().canSee(p_72377_1_.getBukkitEntity()))
            {
                entityplayermp1.field_71135_a.func_72567_b(packet);
            }
        }

        // CraftBukkit end

        for (int i = 0; i < this.field_72404_b.size(); ++i)
        {
            EntityPlayerMP entityplayermp1 = (EntityPlayerMP)this.field_72404_b.get(i);

            // CraftBukkit start - .name -> .listName
            if (p_72377_1_.getBukkitEntity().canSee(entityplayermp1.getBukkitEntity()))
            {
                p_72377_1_.field_71135_a.func_72567_b(new Packet201PlayerInfo(entityplayermp1.listName, true, entityplayermp1.field_71138_i));
            }

            // CraftBukkit end
        }
    }

    public void func_72358_d(EntityPlayerMP p_72358_1_)
    {
        p_72358_1_.func_71121_q().func_73040_p().func_72685_d(p_72358_1_);
    }

    public String disconnect(EntityPlayerMP entityplayermp)   // CraftBukkit - return string
    {
        if (entityplayermp.field_71135_a.field_72576_c)
        {
            return null;    // CraftBukkit - exploitsies fix
        }

        // CraftBukkit start - quitting must be before we do final save of data, in case plugins need to modify it
        PlayerQuitEvent playerQuitEvent = new PlayerQuitEvent(this.cserver.getPlayer(entityplayermp), "\u00A7e" + entityplayermp.field_71092_bJ + " left the game.");
        this.cserver.getPluginManager().callEvent(playerQuitEvent);
        entityplayermp.getBukkitEntity().disconnect(playerQuitEvent.getQuitMessage());
        // CraftBukkit end
        this.func_72391_b(entityplayermp);
        WorldServer worldserver = entityplayermp.func_71121_q();
        worldserver.func_72900_e(entityplayermp);
        worldserver.func_73040_p().func_72695_c(entityplayermp);
        this.field_72404_b.remove(entityplayermp);
        ChunkIOExecutor.adjustPoolSize(this.func_72394_k()); // CraftBukkit
        // CraftBukkit start - .name -> .listName, replace sendAll with loop
        Packet201PlayerInfo packet = new Packet201PlayerInfo(entityplayermp.listName, false, 9999);

        for (int i = 0; i < this.field_72404_b.size(); ++i)
        {
            EntityPlayerMP entityplayermp1 = (EntityPlayerMP)this.field_72404_b.get(i);

            if (entityplayermp1.getBukkitEntity().canSee(entityplayermp.getBukkitEntity()))
            {
                entityplayermp1.field_71135_a.func_72567_b(packet);
            }
        }

        return playerQuitEvent.getQuitMessage();
        // CraftBukkit end
    }

    // CraftBukkit start - Whole method and signature
    public EntityPlayerMP attemptLogin(NetLoginHandler pendingconnection, String s, String hostname)
    {
        // Instead of kicking then returning, we need to store the kick reason
        // in the event, check with plugins to see if it's ok, and THEN kick
        // depending on the outcome.
        EntityPlayerMP entity = new EntityPlayerMP(this.field_72400_f, this.field_72400_f.func_71218_a(0), s, this.field_72400_f.func_71242_L() ? new DemoWorldManager(this.field_72400_f.func_71218_a(0)) : new ItemInWorldManager(this.field_72400_f.func_71218_a(0)));
        Player player = entity.getBukkitEntity();
        PlayerLoginEvent event = new PlayerLoginEvent(player, hostname, pendingconnection.getSocket().getInetAddress());
        SocketAddress socketaddress = pendingconnection.field_72538_b.func_74430_c();

        if (this.field_72401_g.func_73704_a(s))
        {
            BanEntry banentry = (BanEntry) this.field_72401_g.func_73712_c().get(s);
            String s1 = "You are banned from this server!\nReason: " + banentry.func_73686_f();

            if (banentry.func_73680_d() != null)
            {
                s1 = s1 + "\nYour ban will be removed on " + field_72403_e.format(banentry.func_73680_d());
            }

            event.disallow(PlayerLoginEvent.Result.KICK_BANNED, s1);
        }
        else if (!this.func_72370_d(s))
        {
            event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, "You are not white-listed on this server!");
        }
        else
        {
            String s2 = socketaddress.toString();
            s2 = s2.substring(s2.indexOf("/") + 1);
            s2 = s2.substring(0, s2.indexOf(":"));

            if (this.field_72413_h.func_73704_a(s2))
            {
                BanEntry banentry1 = (BanEntry)this.field_72413_h.func_73712_c().get(s2);
                String s3 = "Your IP address is banned from this server!\nReason: " + banentry1.func_73686_f();

                if (banentry1.func_73680_d() != null)
                {
                    s3 = s3 + "\nYour ban will be removed on " + field_72403_e.format(banentry1.func_73680_d());
                }

                event.disallow(PlayerLoginEvent.Result.KICK_BANNED, s3);
            }
            else if (this.field_72404_b.size() >= this.field_72405_c)
            {
                event.disallow(PlayerLoginEvent.Result.KICK_FULL, "The server is full!");
            }
            else
            {
                event.disallow(PlayerLoginEvent.Result.ALLOWED, s2);
            }
        }

        this.cserver.getPluginManager().callEvent(event);

        if (event.getResult() != PlayerLoginEvent.Result.ALLOWED)
        {
            pendingconnection.func_72527_a(event.getKickMessage());
            return null;
        }

        return entity;
        // CraftBukkit end
    }

    public EntityPlayerMP processLogin(EntityPlayerMP player)   // CraftBukkit - String -> EntityPlayer
    {
        String s = player.field_71092_bJ; // CraftBukkit
        ArrayList arraylist = new ArrayList();
        EntityPlayerMP entityplayermp;

        for (int i = 0; i < this.field_72404_b.size(); ++i)
        {
            entityplayermp = (EntityPlayerMP)this.field_72404_b.get(i);

            if (entityplayermp.field_71092_bJ.equalsIgnoreCase(s))
            {
                arraylist.add(entityplayermp);
            }
        }

        Iterator iterator = arraylist.iterator();

        while (iterator.hasNext())
        {
            entityplayermp = (EntityPlayerMP)iterator.next();
            entityplayermp.field_71135_a.func_72565_c("You logged in from another location");
        }

        /* CraftBukkit start
        Object object;

        if (this.server.M()) {
            object = new DemoPlayerInteractManager(this.server.getWorldServer(0));
        } else {
            object = new PlayerInteractManager(this.server.getWorldServer(0));
        }

        return new EntityPlayer(this.server, this.server.getWorldServer(0), s, (PlayerInteractManager) object);
        */
        return player;
        // CraftBukkit end
    }

    // CraftBukkit start
    public EntityPlayerMP func_72368_a(EntityPlayerMP p_72368_1_, int p_72368_2_, boolean p_72368_3_)
    {
        return this.moveToWorld(p_72368_1_, p_72368_2_, p_72368_3_, null);
    }

    public EntityPlayerMP moveToWorld(EntityPlayerMP entityplayermp, int i, boolean flag, Location location)
    {
        // CraftBukkit end
        entityplayermp.func_71121_q().func_73039_n().func_72787_a(entityplayermp);
        // entityplayermp.p().getTracker().untrackEntity(entityplayermp); // CraftBukkit
        entityplayermp.func_71121_q().func_73040_p().func_72695_c(entityplayermp);
        this.field_72404_b.remove(entityplayermp);
        this.field_72400_f.func_71218_a(entityplayermp.field_71093_bK).func_72973_f(entityplayermp);
        ChunkCoordinates chunkcoordinates = entityplayermp.func_70997_bJ();
        boolean flag1 = entityplayermp.func_82245_bX();
        // CraftBukkit start
        EntityPlayerMP entityplayermp1 = entityplayermp;
        org.bukkit.World fromWorld = entityplayermp1.getBukkitEntity().getWorld();
        entityplayermp1.field_71136_j = false;
        entityplayermp1.func_71049_a(entityplayermp, flag);
        ChunkCoordinates chunkcoordinates1;

        if (location == null)
        {
            boolean isBedSpawn = false;
            CraftWorld cworld = (CraftWorld) this.field_72400_f.server.getWorld(entityplayermp.spawnWorld);

            if (cworld != null && chunkcoordinates != null)
            {
                chunkcoordinates1 = EntityPlayer.func_71056_a(cworld.getHandle(), chunkcoordinates, flag1);

                if (chunkcoordinates1 != null)
                {
                    isBedSpawn = true;
                    location = new Location(cworld, chunkcoordinates1.field_71574_a + 0.5, chunkcoordinates1.field_71572_b, chunkcoordinates1.field_71573_c + 0.5);
                }
                else
                {
                    entityplayermp1.func_71063_a(null, true);
                    entityplayermp1.field_71135_a.func_72567_b(new Packet70GameEvent(0, 0));
                }
            }

            if (location == null)
            {
                cworld = (CraftWorld) this.field_72400_f.server.getWorlds().get(0);
                chunkcoordinates = cworld.getHandle().func_72861_E();
                location = new Location(cworld, chunkcoordinates.field_71574_a + 0.5, chunkcoordinates.field_71572_b, chunkcoordinates.field_71573_c + 0.5);
            }

            Player respawnPlayer = this.cserver.getPlayer(entityplayermp1);
            PlayerRespawnEvent respawnEvent = new PlayerRespawnEvent(respawnPlayer, location, isBedSpawn);
            this.cserver.getPluginManager().callEvent(respawnEvent);
            location = respawnEvent.getRespawnLocation();
            entityplayermp.reset();
        }
        else
        {
            location.setWorld(this.field_72400_f.func_71218_a(i).getWorld());
        }

        WorldServer worldserver = ((CraftWorld) location.getWorld()).getHandle();
        entityplayermp1.func_70080_a(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        // CraftBukkit end
        worldserver.field_73059_b.func_73158_c((int)entityplayermp1.field_70165_t >> 4, (int)entityplayermp1.field_70161_v >> 4);

        while (!worldserver.func_72945_a(entityplayermp1, entityplayermp1.field_70121_D).isEmpty())
        {
            entityplayermp1.func_70107_b(entityplayermp1.field_70165_t, entityplayermp1.field_70163_u + 1.0D, entityplayermp1.field_70161_v);
        }

        // CraftBukkit start
        byte actualDimension = (byte)(worldserver.getWorld().getEnvironment().getId());
        // Force the client to refresh their chunk cache.
        entityplayermp1.field_71135_a.func_72567_b(new Packet9Respawn((byte)(actualDimension >= 0 ? -1 : 0), (byte) worldserver.field_73013_u, worldserver.func_72912_H().func_76067_t(), worldserver.func_72800_K(), entityplayermp.field_71134_c.func_73081_b()));
        entityplayermp1.field_71135_a.func_72567_b(new Packet9Respawn(actualDimension, (byte) worldserver.field_73013_u, worldserver.func_72912_H().func_76067_t(), worldserver.func_72800_K(), entityplayermp.field_71134_c.func_73081_b()));
        entityplayermp1.func_70029_a(worldserver);
        entityplayermp1.field_70128_L = false;
        entityplayermp1.field_71135_a.teleport(new Location(worldserver.getWorld(), entityplayermp1.field_70165_t, entityplayermp1.field_70163_u, entityplayermp1.field_70161_v, entityplayermp1.field_70177_z, entityplayermp1.field_70125_A));
        entityplayermp1.func_70095_a(false);
        chunkcoordinates1 = worldserver.func_72861_E();
        // CraftBukkit end
        entityplayermp1.field_71135_a.func_72567_b(new Packet6SpawnPosition(chunkcoordinates1.field_71574_a, chunkcoordinates1.field_71572_b, chunkcoordinates1.field_71573_c));
        entityplayermp1.field_71135_a.func_72567_b(new Packet43Experience(entityplayermp1.field_71106_cc, entityplayermp1.field_71067_cb, entityplayermp1.field_71068_ca));
        this.func_72354_b(entityplayermp1, worldserver);
        worldserver.func_73040_p().func_72683_a(entityplayermp1);
        worldserver.func_72838_d(entityplayermp1);
        this.field_72404_b.add(entityplayermp1);
        // CraftBukkit start - added from changeDimension
        this.func_72385_f(entityplayermp1); // CraftBukkit
        entityplayermp1.func_71016_p();
        Iterator iterator = entityplayermp1.func_70651_bq().iterator();

        while (iterator.hasNext())
        {
            PotionEffect potioneffect = (PotionEffect)iterator.next();
            entityplayermp1.field_71135_a.func_72567_b(new Packet41EntityEffect(entityplayermp1.field_70157_k, potioneffect));
        }

        // entityplayermp1.syncInventory();
        // CraftBukkit end

        // CraftBukkit start - don't fire on respawn
        if (fromWorld != location.getWorld())
        {
            PlayerChangedWorldEvent event = new PlayerChangedWorldEvent((Player) entityplayermp1.getBukkitEntity(), fromWorld);
            Bukkit.getServer().getPluginManager().callEvent(event);
        }

        // CraftBukkit end
        return entityplayermp1;
    }

    // CraftBukkit start - Replaced the standard handling of portals with a more customised method.
    public void changeDimension(EntityPlayerMP entityplayermp, int i, TeleportCause cause)
    {
        WorldServer exitWorld = null;

        if (entityplayermp.field_71093_bK < CraftWorld.CUSTOM_DIMENSION_OFFSET)   // plugins must specify exit from custom Bukkit worlds
        {
            // only target existing worlds (compensate for allow-nether/allow-end as false)
            for (WorldServer world : this.field_72400_f.worlds)
            {
                if (world.dimension == i)
                {
                    exitWorld = world;
                }
            }
        }

        Location enter = entityplayermp.getBukkitEntity().getLocation();
        Location exit = null;
        boolean useTravelAgent = false; // don't use agent for custom worlds or return from THE_END

        if (exitWorld != null)
        {
            if ((cause == TeleportCause.END_PORTAL) && (i == 0))
            {
                // THE_END -> NORMAL; use bed if available, otherwise default spawn
                exit = ((CraftPlayer) entityplayermp.getBukkitEntity()).getBedSpawnLocation();

                if (exit == null || ((CraftWorld) exit.getWorld()).getHandle().dimension != 0)
                {
                    exit = exitWorld.getWorld().getSpawnLocation();
                }
            }
            else
            {
                // NORMAL <-> NETHER or NORMAL -> THE_END
                exit = this.calculateTarget(enter, exitWorld);
                useTravelAgent = true;
            }
        }

        TravelAgent agent = exit != null ? (TravelAgent)((CraftWorld) exit.getWorld()).getHandle().func_85176_s() : null;
        PlayerPortalEvent event = new PlayerPortalEvent(entityplayermp.getBukkitEntity(), enter, exit, agent, cause);
        event.useTravelAgent(useTravelAgent);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled() || event.getTo() == null)
        {
            return;
        }

        exit = event.useTravelAgent() ? event.getPortalTravelAgent().findOrCreate(event.getTo()) : event.getTo();
        exitWorld = ((CraftWorld) exit.getWorld()).getHandle();
        Vector velocity = entityplayermp.getBukkitEntity().getVelocity();
        boolean before = exitWorld.field_73059_b.field_73250_a;
        exitWorld.field_73059_b.field_73250_a = true;
        exitWorld.func_85176_s().adjustExit(entityplayermp, exit, velocity);
        exitWorld.field_73059_b.field_73250_a = before;
        this.moveToWorld(entityplayermp, exitWorld.dimension, true, exit);

        if (entityplayermp.field_70159_w != velocity.getX() || entityplayermp.field_70181_x != velocity.getY() || entityplayermp.field_70179_y != velocity.getZ())
        {
            entityplayermp.getBukkitEntity().setVelocity(velocity);
        }

        // CraftBukkit end
    }

    public void func_82448_a(Entity p_82448_1_, int p_82448_2_, WorldServer p_82448_3_, WorldServer p_82448_4_)
    {
        // CraftBukkit start - split into modular functions
        Location exit = this.calculateTarget(p_82448_1_.getBukkitEntity().getLocation(), p_82448_4_);
        this.repositionEntity(p_82448_1_, exit, true);
    }

    // copy of original a(Entity, int, WorldServer, WorldServer) method with only location calculation logic
    public Location calculateTarget(Location enter, World target)
    {
        WorldServer worldserver = ((CraftWorld) enter.getWorld()).getHandle();
        WorldServer worldserver1 = ((CraftWorld) target.getWorld()).getHandle();
        int i = worldserver.dimension;
        double y = enter.getY();
        float yaw = enter.getYaw();
        float pitch = enter.getPitch();
        double d0 = enter.getX();
        double d1 = enter.getZ();
        double d2 = 8.0D;

        /*
        double d3 = entity.locX;
        double d4 = entity.locY;
        double d5 = entity.locZ;
        float f = entity.yaw;

        worldserver.methodProfiler.a("moving");
        */
        if (worldserver1.dimension == -1)
        {
            d0 /= d2;
            d1 /= d2;
            /*
            entity.setPositionRotation(d0, entity.locY, d1, entity.yaw, entity.pitch);
            if (entity.isAlive()) {
                worldserver.entityJoinedWorld(entity, false);
            }
            */
        }
        else if (worldserver1.dimension == 0)
        {
            d0 *= d2;
            d1 *= d2;
            /*
            entity.setPositionRotation(d0, entity.locY, d1, entity.yaw, entity.pitch);
            if (entity.isAlive()) {
                worldserver.entityJoinedWorld(entity, false);
            }
            */
        }
        else
        {
            ChunkCoordinates chunkcoordinates;

            if (i == 1)
            {
                // use default NORMAL world spawn instead of target
                worldserver1 = this.field_72400_f.worlds.get(0);
                chunkcoordinates = worldserver1.func_72861_E();
            }
            else
            {
                chunkcoordinates = worldserver1.func_73054_j();
            }

            d0 = (double)chunkcoordinates.field_71574_a;
            y = (double) chunkcoordinates.field_71572_b;
            d1 = (double)chunkcoordinates.field_71573_c;
            yaw = 90.0F;
            pitch = 0.0F;
            /*
            entity.setPositionRotation(d0, entity.locY, d1, 90.0F, 0.0F);
            if (entity.isAlive()) {
                worldserver.entityJoinedWorld(entity, false);
            }
            */
        }

        // worldserver.methodProfiler.b();
        if (i != 1)
        {
            // worldserver.methodProfiler.a("placing");
            d0 = (double)MathHelper.func_76125_a((int)d0, -29999872, 29999872);
            d1 = (double)MathHelper.func_76125_a((int)d1, -29999872, 29999872);
            /*
            if (entity.isAlive()) {
                worldserver1.addEntity(entity);
                entity.setPositionRotation(d0, entity.locY, d1, entity.yaw, entity.pitch);
                worldserver1.entityJoinedWorld(entity, false);
                worldserver1.s().a(entity, d3, d4, d5, f);
            }

            worldserver.methodProfiler.b();
            */
        }

        // entity.spawnIn(worldserver1);
        return new Location(worldserver1.getWorld(), d0, y, d1, yaw, pitch);
    }

    // copy of original a(Entity, int, WorldServer, WorldServer) method with only entity repositioning logic
    public void repositionEntity(Entity entity, Location exit, boolean portal)
    {
        int i = entity.field_71093_bK;
        WorldServer worldserver = (WorldServer) entity.field_70170_p;
        WorldServer worldserver1 = ((CraftWorld) exit.getWorld()).getHandle();
        /*
        double d0 = entity.locX;
        double d1 = entity.locZ;
        double d2 = 8.0D;
        double d3 = entity.locX;
        double d4 = entity.locY;
        double d5 = entity.locZ;
        float f = entity.yaw;
        */
        worldserver.field_72984_F.func_76320_a("moving");
        entity.func_70012_b(exit.getX(), exit.getY(), exit.getZ(), exit.getYaw(), exit.getPitch());

        if (entity.func_70089_S())
        {
            worldserver.func_72866_a(entity, false);
        }

        /*
        if (entity.dimension == -1) {
            d0 /= d2;
            d1 /= d2;
            entity.setPositionRotation(d0, entity.locY, d1, entity.yaw, entity.pitch);
            if (entity.isAlive()) {
                worldserver.entityJoinedWorld(entity, false);
            }
        } else if (entity.dimension == 0) {
            d0 *= d2;
            d1 *= d2;
            entity.setPositionRotation(d0, entity.locY, d1, entity.yaw, entity.pitch);
            if (entity.isAlive()) {
                worldserver.entityJoinedWorld(entity, false);
            }
        } else {
            ChunkCoordinates chunkcoordinates;

            if (i == 1) {
                chunkcoordinates = worldserver1.getSpawn();
            } else {
                chunkcoordinates = worldserver1.getDimensionSpawn();
            }

            d0 = (double) chunkcoordinates.x;
            entity.locY = (double) chunkcoordinates.y;
            d1 = (double) chunkcoordinates.z;
            entity.setPositionRotation(d0, entity.locY, d1, 90.0F, 0.0F);
            if (entity.isAlive()) {
                worldserver.entityJoinedWorld(entity, false);
            }
        }
        */
        worldserver.field_72984_F.func_76319_b();

        if (i != 1)
        {
            worldserver.field_72984_F.func_76320_a("placing");

            /*
            d0 = (double) MathHelper.a((int) d0, -29999872, 29999872);
            d1 = (double) MathHelper.a((int) d1, -29999872, 29999872);
            */
            if (entity.func_70089_S())
            {
                worldserver1.func_72838_d(entity);
                // entity.setPositionRotation(d0, entity.locY, d1, entity.yaw, entity.pitch)
                worldserver1.func_72866_a(entity, false);

                // worldserver1.s().a(entity, d3, d4, d5, f);
                if (portal)
                {
                    Vector velocity = entity.getBukkitEntity().getVelocity();
                    worldserver1.func_85176_s().adjustExit(entity, exit, velocity);
                    entity.func_70012_b(exit.getX(), exit.getY(), exit.getZ(), exit.getYaw(), exit.getPitch());

                    if (entity.field_70159_w != velocity.getX() || entity.field_70181_x != velocity.getY() || entity.field_70179_y != velocity.getZ())
                    {
                        entity.getBukkitEntity().setVelocity(velocity);
                    }
                }
            }

            worldserver.field_72984_F.func_76319_b();
        }

        entity.func_70029_a(worldserver1);
        // CraftBukkit end
    }

    public void func_72374_b()
    {
        if (++this.field_72408_o > 600)
        {
            this.field_72408_o = 0;
        }

        /* CraftBukkit start - remove updating of lag to players -- it spams way to much on big servers.
        if (this.o < this.players.size()) {
            EntityPlayer entityplayermp = (EntityPlayer) this.players.get(this.o);

            this.sendAll(new Packet201PlayerInfo(entityplayermp.name, true, entityplayermp.ping));
        }
        // CraftBukkit end */
    }

    public void func_72384_a(Packet p_72384_1_)
    {
        for (int i = 0; i < this.field_72404_b.size(); ++i)
        {
            ((EntityPlayerMP)this.field_72404_b.get(i)).field_71135_a.func_72567_b(p_72384_1_);
        }
    }

    public void func_72396_a(Packet p_72396_1_, int p_72396_2_)
    {
        for (int j = 0; j < this.field_72404_b.size(); ++j)
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)this.field_72404_b.get(j);

            if (entityplayermp.field_71093_bK == p_72396_2_)
            {
                entityplayermp.field_71135_a.func_72567_b(p_72396_1_);
            }
        }
    }

    public String func_72398_c()
    {
        String s = "";

        for (int i = 0; i < this.field_72404_b.size(); ++i)
        {
            if (i > 0)
            {
                s = s + ", ";
            }

            s = s + ((EntityPlayerMP)this.field_72404_b.get(i)).field_71092_bJ;
        }

        return s;
    }

    public String[] func_72369_d()
    {
        String[] astring = new String[this.field_72404_b.size()];

        for (int i = 0; i < this.field_72404_b.size(); ++i)
        {
            astring[i] = ((EntityPlayerMP)this.field_72404_b.get(i)).field_71092_bJ;
        }

        return astring;
    }

    public BanList func_72390_e()
    {
        return this.field_72401_g;
    }

    public BanList func_72363_f()
    {
        return this.field_72413_h;
    }

    public void func_72386_b(String p_72386_1_)
    {
        this.field_72414_i.add(p_72386_1_.toLowerCase());
        // CraftBukkit start
        Player player = field_72400_f.server.getPlayer(p_72386_1_);

        if (player != null)
        {
            player.recalculatePermissions();
        }

        // CraftBukkit end
    }

    public void func_72360_c(String p_72360_1_)
    {
        this.field_72414_i.remove(p_72360_1_.toLowerCase());
        // CraftBukkit start
        Player player = field_72400_f.server.getPlayer(p_72360_1_);

        if (player != null)
        {
            player.recalculatePermissions();
        }

        // CraftBukkit end
    }

    public boolean func_72370_d(String p_72370_1_)
    {
        p_72370_1_ = p_72370_1_.trim().toLowerCase();
        return !this.field_72409_l || this.field_72414_i.contains(p_72370_1_) || this.field_72411_j.contains(p_72370_1_);
    }

    public boolean func_72353_e(String p_72353_1_)
    {
        // CraftBukkit
        return this.field_72414_i.contains(p_72353_1_.trim().toLowerCase()) || this.field_72400_f.func_71264_H() && this.field_72400_f.worlds.get(0).func_72912_H().func_76086_u() && this.field_72400_f.func_71214_G().equalsIgnoreCase(p_72353_1_) || this.field_72407_n;
    }

    public EntityPlayerMP func_72361_f(String p_72361_1_)
    {
        Iterator iterator = this.field_72404_b.iterator();
        EntityPlayerMP entityplayermp;

        do
        {
            if (!iterator.hasNext())
            {
                return null;
            }

            entityplayermp = (EntityPlayerMP)iterator.next();
        }
        while (!entityplayermp.field_71092_bJ.equalsIgnoreCase(p_72361_1_));

        return entityplayermp;
    }

    public List func_82449_a(ChunkCoordinates p_82449_1_, int p_82449_2_, int p_82449_3_, int p_82449_4_, int p_82449_5_, int p_82449_6_, int p_82449_7_)
    {
        if (this.field_72404_b.isEmpty())
        {
            return null;
        }
        else
        {
            Object object = new ArrayList();
            boolean flag = p_82449_4_ < 0;
            int k1 = p_82449_2_ * p_82449_2_;
            int l1 = p_82449_3_ * p_82449_3_;
            p_82449_4_ = MathHelper.func_76130_a(p_82449_4_);

            for (int i2 = 0; i2 < this.field_72404_b.size(); ++i2)
            {
                EntityPlayerMP entityplayermp = (EntityPlayerMP)this.field_72404_b.get(i2);

                if (p_82449_1_ != null && (p_82449_2_ > 0 || p_82449_3_ > 0))
                {
                    float f = p_82449_1_.func_82371_e(entityplayermp.func_82114_b());

                    if (p_82449_2_ > 0 && f < (float)k1 || p_82449_3_ > 0 && f > (float)l1)
                    {
                        continue;
                    }
                }

                if ((p_82449_5_ == EnumGameType.NOT_SET.func_77148_a() || p_82449_5_ == entityplayermp.field_71134_c.func_73081_b().func_77148_a()) && (p_82449_6_ <= 0 || entityplayermp.field_71068_ca >= p_82449_6_) && entityplayermp.field_71068_ca <= p_82449_7_)
                {
                    ((List)object).add(entityplayermp);
                }
            }

            if (p_82449_1_ != null)
            {
                Collections.sort((List)object, new PlayerPositionComparator(p_82449_1_));
            }

            if (flag)
            {
                Collections.reverse((List)object);
            }

            if (p_82449_4_ > 0)
            {
                object = ((List)object).subList(0, Math.min(p_82449_4_, ((List)object).size()));
            }

            return (List)object;
        }
    }

    public void func_72393_a(double p_72393_1_, double p_72393_3_, double p_72393_5_, double p_72393_7_, int p_72393_9_, Packet p_72393_10_)
    {
        this.func_72397_a((EntityPlayer)null, p_72393_1_, p_72393_3_, p_72393_5_, p_72393_7_, p_72393_9_, p_72393_10_);
    }

    public void func_72397_a(EntityPlayer p_72397_1_, double p_72397_2_, double p_72397_4_, double p_72397_6_, double p_72397_8_, int p_72397_10_, Packet p_72397_11_)
    {
        for (int j = 0; j < this.field_72404_b.size(); ++j)
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)this.field_72404_b.get(j);

            // CraftBukkit start - Test if player receiving packet can see the source of the packet
            if (p_72397_1_ != null && p_72397_1_ instanceof EntityPlayerMP && !entityplayermp.getBukkitEntity().canSee(((EntityPlayerMP) p_72397_1_).getBukkitEntity()))
            {
                continue;
            }

            // CraftBukkit end
            if (entityplayermp != p_72397_1_ && entityplayermp.field_71093_bK == p_72397_10_)
            {
                double d4 = p_72397_2_ - entityplayermp.field_70165_t;
                double d5 = p_72397_4_ - entityplayermp.field_70163_u;
                double d6 = p_72397_6_ - entityplayermp.field_70161_v;

                if (d4 * d4 + d5 * d5 + d6 * d6 < p_72397_8_ * p_72397_8_)
                {
                    entityplayermp.field_71135_a.func_72567_b(p_72397_11_);
                }
            }
        }
    }

    public void func_72389_g()
    {
        for (int i = 0; i < this.field_72404_b.size(); ++i)
        {
            this.func_72391_b((EntityPlayerMP)this.field_72404_b.get(i));
        }
    }

    public void func_72359_h(String p_72359_1_)
    {
        this.field_72411_j.add(p_72359_1_);
    }

    public void func_72379_i(String p_72379_1_)
    {
        this.field_72411_j.remove(p_72379_1_);
    }

    public Set func_72388_h()
    {
        return this.field_72411_j;
    }

    public Set func_72376_i()
    {
        return this.field_72414_i;
    }

    public void func_72362_j() {}

    public void func_72354_b(EntityPlayerMP p_72354_1_, WorldServer p_72354_2_)
    {
        p_72354_1_.field_71135_a.func_72567_b(new Packet4UpdateTime(p_72354_2_.func_82737_E(), p_72354_2_.func_72820_D()));

        if (p_72354_2_.func_72896_J())
        {
            p_72354_1_.field_71135_a.func_72567_b(new Packet70GameEvent(1, 0));
        }
    }

    public void func_72385_f(EntityPlayerMP p_72385_1_)
    {
        p_72385_1_.func_71120_a(p_72385_1_.field_71069_bz);
        p_72385_1_.func_71118_n();
        p_72385_1_.field_71135_a.func_72567_b(new Packet16BlockItemSwitch(p_72385_1_.field_71071_by.field_70461_c));
    }

    public int func_72394_k()
    {
        return this.field_72404_b.size();
    }

    public int func_72352_l()
    {
        return this.field_72405_c;
    }

    public String[] func_72373_m()
    {
        return this.field_72400_f.worlds.get(0).func_72860_G().func_75756_e().func_75754_f(); // CraftBukkit
    }

    public boolean func_72383_n()
    {
        return this.field_72409_l;
    }

    public void func_72371_a(boolean p_72371_1_)
    {
        this.field_72409_l = p_72371_1_;
    }

    public List func_72382_j(String p_72382_1_)
    {
        ArrayList arraylist = new ArrayList();
        Iterator iterator = this.field_72404_b.iterator();

        while (iterator.hasNext())
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)iterator.next();

            if (entityplayermp.func_71114_r().equals(p_72382_1_))
            {
                arraylist.add(entityplayermp);
            }
        }

        return arraylist;
    }

    public int func_72395_o()
    {
        return this.field_72402_d;
    }

    public MinecraftServer func_72365_p()
    {
        return this.field_72400_f;
    }

    public NBTTagCompound func_72378_q()
    {
        return null;
    }

    private void func_72381_a(EntityPlayerMP p_72381_1_, EntityPlayerMP p_72381_2_, World p_72381_3_)
    {
        if (p_72381_2_ != null)
        {
            p_72381_1_.field_71134_c.func_73076_a(p_72381_2_.field_71134_c.func_73081_b());
        }
        else if (this.field_72410_m != null)
        {
            p_72381_1_.field_71134_c.func_73076_a(this.field_72410_m);
        }

        p_72381_1_.field_71134_c.func_73077_b(p_72381_3_.func_72912_H().func_76077_q());
    }

    public void func_72392_r()
    {
        while (!this.field_72404_b.isEmpty())
        {
            ((EntityPlayerMP) this.field_72404_b.get(0)).field_71135_a.func_72565_c(this.field_72400_f.server.getShutdownMessage()); // CraftBukkit - add custom shutdown message
        }
    }

    public void func_92062_k(String p_92062_1_)
    {
        this.field_72400_f.func_71244_g(p_92062_1_);
        this.func_72384_a(new Packet3Chat(p_92062_1_));
    }
}
