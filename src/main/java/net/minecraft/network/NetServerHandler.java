package net.minecraft.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Logger;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerBeacon;
import net.minecraft.inventory.ContainerMerchant;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEditableBook;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWritableBook;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet0KeepAlive;
import net.minecraft.network.packet.Packet101CloseWindow;
import net.minecraft.network.packet.Packet102WindowClick;
import net.minecraft.network.packet.Packet103SetSlot;
import net.minecraft.network.packet.Packet106Transaction;
import net.minecraft.network.packet.Packet107CreativeSetSlot;
import net.minecraft.network.packet.Packet108EnchantItem;
import net.minecraft.network.packet.Packet10Flying;
import net.minecraft.network.packet.Packet130UpdateSign;
import net.minecraft.network.packet.Packet13PlayerLookMove;
import net.minecraft.network.packet.Packet14BlockDig;
import net.minecraft.network.packet.Packet15Place;
import net.minecraft.network.packet.Packet16BlockItemSwitch;
import net.minecraft.network.packet.Packet18Animation;
import net.minecraft.network.packet.Packet19EntityAction;
import net.minecraft.network.packet.Packet202PlayerAbilities;
import net.minecraft.network.packet.Packet203AutoComplete;
import net.minecraft.network.packet.Packet204ClientInfo;
import net.minecraft.network.packet.Packet205ClientCommand;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.network.packet.Packet255KickDisconnect;
import net.minecraft.network.packet.Packet3Chat;
import net.minecraft.network.packet.Packet53BlockChange;
import net.minecraft.network.packet.Packet6SpawnPosition;
import net.minecraft.network.packet.Packet7UseEntity;
import net.minecraft.network.packet.Packet9Respawn;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.BanEntry;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.IntHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldServer;

// CraftBukkit start
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.logging.Level;
import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.inventory.CraftInventoryView;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.craftbukkit.util.LazyPlayerSet;
import org.bukkit.craftbukkit.util.Waitable;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.InventoryView;
// CraftBukkit end

public class NetServerHandler extends NetHandler
{
    public static Logger field_72577_a = Logger.getLogger("Minecraft");
    public INetworkManager field_72575_b;
    public boolean field_72576_c = false;
    private MinecraftServer field_72573_d;
    public EntityPlayerMP field_72574_e;
    private int field_72571_f;
    private int field_72572_g;
    private boolean field_72584_h;
    private int field_72585_i;
    private long field_72582_j;
    private static Random field_72583_k = new Random();
    private long field_72580_l;
    private volatile int field_72581_m = 0;
    private static final AtomicIntegerFieldUpdater chatSpamField = AtomicIntegerFieldUpdater.newUpdater(NetServerHandler.class, "chatThrottle"); // CraftBukkit - multithreaded field
    private int field_72578_n = 0;
    private double field_72579_o;
    private double field_72589_p;
    private double field_72588_q;
    public boolean field_72587_r = true; // CraftBukkit - private -> public
    private IntHashMap field_72586_s = new IntHashMap();

    public NetServerHandler(MinecraftServer p_i5010_1_, INetworkManager p_i5010_2_, EntityPlayerMP p_i5010_3_)
    {
        this.field_72573_d = p_i5010_1_;
        this.field_72575_b = p_i5010_2_;
        p_i5010_2_.func_74425_a(this);
        this.field_72574_e = p_i5010_3_;
        p_i5010_3_.field_71135_a = this;
        // CraftBukkit start
        this.server = p_i5010_1_.server;
    }

    private final org.bukkit.craftbukkit.CraftServer server;
    private int lastTick = MinecraftServer.currentTick;
    private int lastDropTick = MinecraftServer.currentTick;
    private int dropCount = 0;
    private static final int PLACE_DISTANCE_SQUARED = 6 * 6;

    // Get position of last block hit for BlockDamageLevel.STOPPED
    private double lastPosX = Double.MAX_VALUE;
    private double lastPosY = Double.MAX_VALUE;
    private double lastPosZ = Double.MAX_VALUE;
    private float lastPitch = Float.MAX_VALUE;
    private float lastYaw = Float.MAX_VALUE;
    private boolean justTeleported = false;

    // For the packet15 hack :(
    Long lastPacket;

    // Store the last block right clicked and what type it was
    private int lastMaterial;

    public CraftPlayer getPlayer()
    {
        return (this.field_72574_e == null) ? null : (CraftPlayer) this.field_72574_e.getBukkitEntity();
    }
    private final static HashSet<Integer> invalidItems = new HashSet<Integer>(java.util.Arrays.asList(8, 9, 10, 11, 26, 34, 36, 43, 51, 52, 55, 59, 60, 62, 63, 64, 68, 71, 74, 75, 83, 90, 92, 93, 94, 95, 104, 105, 115, 117, 118, 119, 125, 127, 132, 137, 140, 141, 142, 144)); // TODO: Check after every update.
    // CraftBukkit end

    public void func_72570_d()
    {
        this.field_72584_h = false;
        ++this.field_72571_f;
        this.field_72573_d.field_71304_b.func_76320_a("packetflow");
        this.field_72575_b.func_74428_b();
        this.field_72573_d.field_71304_b.func_76318_c("keepAlive");

        if ((long)this.field_72571_f - this.field_72580_l > 20L)
        {
            this.field_72580_l = (long)this.field_72571_f;
            this.field_72582_j = System.nanoTime() / 1000000L;
            this.field_72585_i = field_72583_k.nextInt();
            this.func_72567_b(new Packet0KeepAlive(this.field_72585_i));
        }

        // CraftBukkit start
        for (int spam; (spam = this.field_72581_m) > 0 && !chatSpamField.compareAndSet(this, spam, spam - 1);) ;

        /* Use thread-safe field access instead
        if (this.m > 0) {
            --this.m;
        }
        */
        // CraftBukkit end

        if (this.field_72578_n > 0)
        {
            --this.field_72578_n;
        }

        this.field_72573_d.field_71304_b.func_76318_c("playerTick");
        this.field_72573_d.field_71304_b.func_76319_b();
    }

    public void func_72565_c(String p_72565_1_)
    {
        if (!this.field_72576_c)
        {
            // CraftBukkit start
            String leaveMessage = "\u00A7e" + this.field_72574_e.field_71092_bJ + " left the game.";
            PlayerKickEvent event = new PlayerKickEvent(this.server.getPlayer(this.field_72574_e), p_72565_1_, leaveMessage);

            if (this.server.getServer().func_71278_l())
            {
                this.server.getPluginManager().callEvent(event);
            }

            if (event.isCancelled())
            {
                // Do not kick the player
                return;
            }

            // Send the possibly modified leave message
            p_72565_1_ = event.getReason();
            // CraftBukkit end
            this.field_72574_e.func_71123_m();
            this.func_72567_b(new Packet255KickDisconnect(p_72565_1_));
            this.field_72575_b.func_74423_d();
            // CraftBukkit start
            leaveMessage = event.getLeaveMessage();

            if (leaveMessage != null && leaveMessage.length() > 0)
            {
                this.field_72573_d.func_71203_ab().func_72384_a(new Packet3Chat(leaveMessage));
            }

            // CraftBukkit end
            this.field_72573_d.func_71203_ab().disconnect(this.field_72574_e);
            this.field_72576_c = true;
        }
    }

    public void func_72498_a(Packet10Flying p_72498_1_)
    {
        WorldServer worldserver = this.field_72573_d.func_71218_a(this.field_72574_e.field_71093_bK);
        this.field_72584_h = true;

        if (!this.field_72574_e.field_71136_j)
        {
            double d0;

            if (!this.field_72587_r)
            {
                d0 = p_72498_1_.field_73543_b - this.field_72589_p;

                if (p_72498_1_.field_73545_a == this.field_72579_o && d0 * d0 < 0.01D && p_72498_1_.field_73544_c == this.field_72588_q)
                {
                    this.field_72587_r = true;
                }
            }

            // CraftBukkit start
            Player player = this.getPlayer();
            Location from = new Location(player.getWorld(), lastPosX, lastPosY, lastPosZ, lastYaw, lastPitch); // Get the Players previous Event location.
            Location to = player.getLocation().clone(); // Start off the To location as the Players current location.

            // If the packet contains movement information then we update the To location with the correct XYZ.
            if (p_72498_1_.field_73546_h && !(p_72498_1_.field_73546_h && p_72498_1_.field_73543_b == -999.0D && p_72498_1_.field_73541_d == -999.0D))
            {
                to.setX(p_72498_1_.field_73545_a);
                to.setY(p_72498_1_.field_73543_b);
                to.setZ(p_72498_1_.field_73544_c);
            }

            // If the packet contains look information then we update the To location with the correct Yaw & Pitch.
            if (p_72498_1_.field_73547_i)
            {
                to.setYaw(p_72498_1_.field_73542_e);
                to.setPitch(p_72498_1_.field_73539_f);
            }

            // Prevent 40 event-calls for less than a single pixel of movement >.>
            double delta = Math.pow(this.lastPosX - to.getX(), 2) + Math.pow(this.lastPosY - to.getY(), 2) + Math.pow(this.lastPosZ - to.getZ(), 2);
            float deltaAngle = Math.abs(this.lastYaw - to.getYaw()) + Math.abs(this.lastPitch - to.getPitch());

            if ((delta > 1f / 256 || deltaAngle > 10f) && (this.field_72587_r && !this.field_72574_e.field_70128_L))
            {
                this.lastPosX = to.getX();
                this.lastPosY = to.getY();
                this.lastPosZ = to.getZ();
                this.lastYaw = to.getYaw();
                this.lastPitch = to.getPitch();

                // Skip the first time we do this
                if (from.getX() != Double.MAX_VALUE)
                {
                    PlayerMoveEvent event = new PlayerMoveEvent(player, from, to);
                    this.server.getPluginManager().callEvent(event);

                    // If the event is cancelled we move the player back to their old location.
                    if (event.isCancelled())
                    {
                        this.field_72574_e.field_71135_a.func_72567_b(new Packet13PlayerLookMove(from.getX(), from.getY() + 1.6200000047683716D, from.getY(), from.getZ(), from.getYaw(), from.getPitch(), false));
                        return;
                    }

                    /* If a Plugin has changed the To destination then we teleport the Player
                    there to avoid any 'Moved wrongly' or 'Moved too quickly' errors.
                    We only do this if the Event was not cancelled. */
                    if (!to.equals(event.getTo()) && !event.isCancelled())
                    {
                        this.field_72574_e.getBukkitEntity().teleport(event.getTo(), PlayerTeleportEvent.TeleportCause.UNKNOWN);
                        return;
                    }

                    /* Check to see if the Players Location has some how changed during the call of the event.
                    This can happen due to a plugin teleporting the player instead of using .setTo() */
                    if (!from.equals(this.getPlayer().getLocation()) && this.justTeleported)
                    {
                        this.justTeleported = false;
                        return;
                    }
                }
            }

            if (Double.isNaN(p_72498_1_.field_73545_a) || Double.isNaN(p_72498_1_.field_73543_b) || Double.isNaN(p_72498_1_.field_73544_c) || Double.isNaN(p_72498_1_.field_73541_d))
            {
                player.teleport(player.getWorld().getSpawnLocation(), PlayerTeleportEvent.TeleportCause.UNKNOWN);
                System.err.println(player.getName() + " was caught trying to crash the server with an invalid position.");
                player.kickPlayer("Nope!");
                return;
            }

            if (this.field_72587_r && !this.field_72574_e.field_70128_L)
            {
                // CraftBukkit end
                double d1;
                double d2;
                double d3;
                double d4;

                if (this.field_72574_e.field_70154_o != null)
                {
                    float f = this.field_72574_e.field_70177_z;
                    float f1 = this.field_72574_e.field_70125_A;
                    this.field_72574_e.field_70154_o.func_70043_V();
                    d1 = this.field_72574_e.field_70165_t;
                    d2 = this.field_72574_e.field_70163_u;
                    d3 = this.field_72574_e.field_70161_v;
                    double d5 = 0.0D;
                    d4 = 0.0D;

                    if (p_72498_1_.field_73547_i)
                    {
                        f = p_72498_1_.field_73542_e;
                        f1 = p_72498_1_.field_73539_f;
                    }

                    if (p_72498_1_.field_73546_h && p_72498_1_.field_73543_b == -999.0D && p_72498_1_.field_73541_d == -999.0D)
                    {
                        if (Math.abs(p_72498_1_.field_73545_a) > 1.0D || Math.abs(p_72498_1_.field_73544_c) > 1.0D)
                        {
                            System.err.println(this.field_72574_e.field_71092_bJ + " was caught trying to crash the server with an invalid position.");
                            this.func_72565_c("Nope!");
                            return;
                        }

                        d5 = p_72498_1_.field_73545_a;
                        d4 = p_72498_1_.field_73544_c;
                    }

                    this.field_72574_e.field_70122_E = p_72498_1_.field_73540_g;
                    this.field_72574_e.func_71127_g();
                    this.field_72574_e.func_70091_d(d5, 0.0D, d4);
                    this.field_72574_e.func_70080_a(d1, d2, d3, f, f1);
                    this.field_72574_e.field_70159_w = d5;
                    this.field_72574_e.field_70179_y = d4;

                    if (this.field_72574_e.field_70154_o != null)
                    {
                        worldserver.func_73050_b(this.field_72574_e.field_70154_o, true);
                    }

                    if (this.field_72574_e.field_70154_o != null)
                    {
                        this.field_72574_e.field_70154_o.func_70043_V();
                    }

                    this.field_72573_d.func_71203_ab().func_72358_d(this.field_72574_e);
                    this.field_72579_o = this.field_72574_e.field_70165_t;
                    this.field_72589_p = this.field_72574_e.field_70163_u;
                    this.field_72588_q = this.field_72574_e.field_70161_v;
                    worldserver.func_72870_g(this.field_72574_e);
                    return;
                }

                if (this.field_72574_e.func_70608_bn())
                {
                    this.field_72574_e.func_71127_g();
                    this.field_72574_e.func_70080_a(this.field_72579_o, this.field_72589_p, this.field_72588_q, this.field_72574_e.field_70177_z, this.field_72574_e.field_70125_A);
                    worldserver.func_72870_g(this.field_72574_e);
                    return;
                }

                d0 = this.field_72574_e.field_70163_u;
                this.field_72579_o = this.field_72574_e.field_70165_t;
                this.field_72589_p = this.field_72574_e.field_70163_u;
                this.field_72588_q = this.field_72574_e.field_70161_v;
                d1 = this.field_72574_e.field_70165_t;
                d2 = this.field_72574_e.field_70163_u;
                d3 = this.field_72574_e.field_70161_v;
                float f2 = this.field_72574_e.field_70177_z;
                float f3 = this.field_72574_e.field_70125_A;

                if (p_72498_1_.field_73546_h && p_72498_1_.field_73543_b == -999.0D && p_72498_1_.field_73541_d == -999.0D)
                {
                    p_72498_1_.field_73546_h = false;
                }

                if (p_72498_1_.field_73546_h)
                {
                    d1 = p_72498_1_.field_73545_a;
                    d2 = p_72498_1_.field_73543_b;
                    d3 = p_72498_1_.field_73544_c;
                    d4 = p_72498_1_.field_73541_d - p_72498_1_.field_73543_b;

                    if (!this.field_72574_e.func_70608_bn() && (d4 > 1.65D || d4 < 0.1D))
                    {
                        this.func_72565_c("Illegal stance");
                        field_72577_a.warning(this.field_72574_e.field_71092_bJ + " had an illegal stance: " + d4);
                        return;
                    }

                    if (Math.abs(p_72498_1_.field_73545_a) > 3.2E7D || Math.abs(p_72498_1_.field_73544_c) > 3.2E7D)
                    {
                        // CraftBukkit - teleport to previous position instead of kicking, players get stuck
                        this.func_72569_a(this.field_72579_o, this.field_72589_p, this.field_72588_q, this.field_72574_e.field_70177_z, this.field_72574_e.field_70125_A);
                        return;
                    }
                }

                if (p_72498_1_.field_73547_i)
                {
                    f2 = p_72498_1_.field_73542_e;
                    f3 = p_72498_1_.field_73539_f;
                }

                this.field_72574_e.func_71127_g();
                this.field_72574_e.field_70139_V = 0.0F;
                this.field_72574_e.func_70080_a(this.field_72579_o, this.field_72589_p, this.field_72588_q, f2, f3);

                if (!this.field_72587_r)
                {
                    return;
                }

                d4 = d1 - this.field_72574_e.field_70165_t;
                double d6 = d2 - this.field_72574_e.field_70163_u;
                double d7 = d3 - this.field_72574_e.field_70161_v;
                // CraftBukkit start - min to max
                double d8 = Math.max(Math.abs(d4), Math.abs(this.field_72574_e.field_70159_w));
                double d9 = Math.max(Math.abs(d6), Math.abs(this.field_72574_e.field_70181_x));
                double d10 = Math.max(Math.abs(d7), Math.abs(this.field_72574_e.field_70179_y));
                // CraftBukkit end
                double d11 = d8 * d8 + d9 * d9 + d10 * d10;

                if (d11 > 100.0D && this.field_72587_r && (!this.field_72573_d.func_71264_H() || !this.field_72573_d.func_71214_G().equals(this.field_72574_e.field_71092_bJ)))   // CraftBukkit - Added this.checkMovement condition to solve this check being triggered by teleports
                {
                    field_72577_a.warning(this.field_72574_e.field_71092_bJ + " moved too quickly! " + d4 + "," + d6 + "," + d7 + " (" + d8 + ", " + d9 + ", " + d10 + ")");
                    this.func_72569_a(this.field_72579_o, this.field_72589_p, this.field_72588_q, this.field_72574_e.field_70177_z, this.field_72574_e.field_70125_A);
                    return;
                }

                float f4 = 0.0625F;
                boolean flag = worldserver.func_72945_a(this.field_72574_e, this.field_72574_e.field_70121_D.func_72329_c().func_72331_e((double)f4, (double)f4, (double)f4)).isEmpty();

                if (this.field_72574_e.field_70122_E && !p_72498_1_.field_73540_g && d6 > 0.0D)
                {
                    this.field_72574_e.func_71020_j(0.2F);
                }

                this.field_72574_e.func_70091_d(d4, d6, d7);
                this.field_72574_e.field_70122_E = p_72498_1_.field_73540_g;
                this.field_72574_e.func_71000_j(d4, d6, d7);
                double d12 = d6;
                d4 = d1 - this.field_72574_e.field_70165_t;
                d6 = d2 - this.field_72574_e.field_70163_u;

                if (d6 > -0.5D || d6 < 0.5D)
                {
                    d6 = 0.0D;
                }

                d7 = d3 - this.field_72574_e.field_70161_v;
                d11 = d4 * d4 + d6 * d6 + d7 * d7;
                boolean flag1 = false;

                if (d11 > 0.0625D && !this.field_72574_e.func_70608_bn() && !this.field_72574_e.field_71134_c.func_73083_d())
                {
                    flag1 = true;
                    field_72577_a.warning(this.field_72574_e.field_71092_bJ + " moved wrongly!");
                }

                this.field_72574_e.func_70080_a(d1, d2, d3, f2, f3);
                boolean flag2 = worldserver.func_72945_a(this.field_72574_e, this.field_72574_e.field_70121_D.func_72329_c().func_72331_e((double)f4, (double)f4, (double)f4)).isEmpty();

                if (flag && (flag1 || !flag2) && !this.field_72574_e.func_70608_bn())
                {
                    this.func_72569_a(this.field_72579_o, this.field_72589_p, this.field_72588_q, f2, f3);
                    return;
                }

                AxisAlignedBB axisalignedbb = this.field_72574_e.field_70121_D.func_72329_c().func_72314_b((double)f4, (double)f4, (double)f4).func_72321_a(0.0D, -0.55D, 0.0D);

                if (!this.field_72573_d.func_71231_X() && !this.field_72574_e.field_71075_bZ.field_75101_c && !worldserver.func_72829_c(axisalignedbb))   // CraftBukkit - check abilities instead of creative mode
                {
                    if (d12 >= -0.03125D)
                    {
                        ++this.field_72572_g;

                        if (this.field_72572_g > 80)
                        {
                            field_72577_a.warning(this.field_72574_e.field_71092_bJ + " was kicked for floating too long!");
                            this.func_72565_c("Flying is not enabled on this server");
                            return;
                        }
                    }
                }
                else
                {
                    this.field_72572_g = 0;
                }

                this.field_72574_e.field_70122_E = p_72498_1_.field_73540_g;
                this.field_72573_d.func_71203_ab().func_72358_d(this.field_72574_e);

                if (this.field_72574_e.field_71134_c.func_73083_d())
                {
                    return;    // CraftBukkit - fixed fall distance accumulating while being in Creative mode.
                }

                this.field_72574_e.func_71122_b(this.field_72574_e.field_70163_u - d0, p_72498_1_.field_73540_g);
            }
        }
    }

    public void func_72569_a(double p_72569_1_, double p_72569_3_, double p_72569_5_, float p_72569_7_, float p_72569_8_)
    {
        // CraftBukkit start - Delegate to teleport(Location)
        Player player = this.getPlayer();
        Location from = player.getLocation();
        Location to = new Location(this.getPlayer().getWorld(), p_72569_1_, p_72569_3_, p_72569_5_, p_72569_7_, p_72569_8_);
        PlayerTeleportEvent event = new PlayerTeleportEvent(player, from, to, PlayerTeleportEvent.TeleportCause.UNKNOWN);
        this.server.getPluginManager().callEvent(event);
        from = event.getFrom();
        to = event.isCancelled() ? from : event.getTo();
        this.teleport(to);
    }

    public void teleport(Location dest)
    {
        double d0, d1, d2;
        float f, f1;
        d0 = dest.getX();
        d1 = dest.getY();
        d2 = dest.getZ();
        f = dest.getYaw();
        f1 = dest.getPitch();

        // TODO: make sure this is the best way to address this.
        if (Float.isNaN(f))
        {
            f = 0;
        }

        if (Float.isNaN(f1))
        {
            f1 = 0;
        }

        this.lastPosX = d0;
        this.lastPosY = d1;
        this.lastPosZ = d2;
        this.lastYaw = f;
        this.lastPitch = f1;
        this.justTeleported = true;
        // CraftBukkit end
        this.field_72587_r = false;
        this.field_72579_o = d0;
        this.field_72589_p = d1;
        this.field_72588_q = d2;
        this.field_72574_e.func_70080_a(d0, d1, d2, f, f1);
        this.field_72574_e.field_71135_a.func_72567_b(new Packet13PlayerLookMove(d0, d1 + 1.6200000047683716D, d1, d2, f, f1, false));
    }

    public void func_72510_a(Packet14BlockDig p_72510_1_)
    {
        if (this.field_72574_e.field_70128_L)
        {
            return;    // CraftBukkit
        }

        WorldServer worldserver = this.field_72573_d.func_71218_a(this.field_72574_e.field_71093_bK);

        if (p_72510_1_.field_73342_e == 4)
        {
            // CraftBukkit start
            // If the ticks aren't the same then the count starts from 0 and we update the lastDropTick.
            if (this.lastDropTick != MinecraftServer.currentTick)
            {
                this.dropCount = 0;
                this.lastDropTick = MinecraftServer.currentTick;
            }
            else
            {
                // Else we increment the drop count and check the amount.
                this.dropCount++;

                if (this.dropCount >= 20)
                {
                    field_72577_a.warning(this.field_72574_e.field_71092_bJ + " dropped their items too quickly!");
                    this.func_72565_c("You dropped your items too quickly (Hacking?)");
                    return;
                }
            }

            // CraftBukkit end
            this.field_72574_e.func_71040_bB(false);
        }
        else if (p_72510_1_.field_73342_e == 3)
        {
            this.field_72574_e.func_71040_bB(true);
        }
        else if (p_72510_1_.field_73342_e == 5)
        {
            this.field_72574_e.func_71034_by();
        }
        else
        {
            int i = this.field_72573_d.func_82357_ak();
            boolean flag = worldserver.field_73011_w.field_76574_g != 0 || this.field_72573_d.func_71203_ab().func_72376_i().isEmpty() || this.field_72573_d.func_71203_ab().func_72353_e(this.field_72574_e.field_71092_bJ) || i <= 0 || this.field_72573_d.func_71264_H();
            boolean flag1 = false;

            if (p_72510_1_.field_73342_e == 0)
            {
                flag1 = true;
            }

            if (p_72510_1_.field_73342_e == 1)
            {
                flag1 = true;
            }

            if (p_72510_1_.field_73342_e == 2)
            {
                flag1 = true;
            }

            int j = p_72510_1_.field_73345_a;
            int k = p_72510_1_.field_73343_b;
            int l = p_72510_1_.field_73344_c;

            if (flag1)
            {
                double d0 = this.field_72574_e.field_70165_t - ((double)j + 0.5D);
                double d1 = this.field_72574_e.field_70163_u - ((double)k + 0.5D) + 1.5D;
                double d2 = this.field_72574_e.field_70161_v - ((double)l + 0.5D);
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;

                if (d3 > 36.0D)
                {
                    return;
                }

                if (k >= this.field_72573_d.func_71207_Z())
                {
                    return;
                }
            }

            ChunkCoordinates chunkcoordinates = worldserver.func_72861_E();
            int i1 = MathHelper.func_76130_a(j - chunkcoordinates.field_71574_a);
            int j1 = MathHelper.func_76130_a(l - chunkcoordinates.field_71573_c);

            if (i1 > j1)
            {
                j1 = i1;
            }

            if (p_72510_1_.field_73342_e == 0)
            {
                // CraftBukkit start
                if (j1 < this.server.getSpawnRadius() && !flag)
                {
                    CraftEventFactory.callPlayerInteractEvent(this.field_72574_e, Action.LEFT_CLICK_BLOCK, j, k, l, i1, this.field_72574_e.field_71071_by.func_70448_g());
                    this.field_72574_e.field_71135_a.func_72567_b(new Packet53BlockChange(j, k, l, worldserver));
                    // Update any tile entity data for this block
                    TileEntity tileentity = worldserver.func_72796_p(j, k, l);

                    if (tileentity != null)
                    {
                        this.field_72574_e.field_71135_a.func_72567_b(tileentity.func_70319_e());
                    }

                    // CraftBukkit end
                }
                else
                {
                    this.field_72574_e.field_71134_c.func_73074_a(j, k, l, p_72510_1_.field_73341_d);
                }
            }
            else if (p_72510_1_.field_73342_e == 2)
            {
                this.field_72574_e.field_71134_c.func_73082_a(j, k, l);

                if (worldserver.func_72798_a(j, k, l) != 0)
                {
                    this.field_72574_e.field_71135_a.func_72567_b(new Packet53BlockChange(j, k, l, worldserver));
                }
            }
            else if (p_72510_1_.field_73342_e == 1)
            {
                this.field_72574_e.field_71134_c.func_73073_c(j, k, l);

                if (worldserver.func_72798_a(j, k, l) != 0)
                {
                    this.field_72574_e.field_71135_a.func_72567_b(new Packet53BlockChange(j, k, l, worldserver));
                }
            }
        }
    }

    public void func_72472_a(Packet15Place p_72472_1_)
    {
        WorldServer worldserver = this.field_72573_d.func_71218_a(this.field_72574_e.field_71093_bK);

        // CraftBukkit start
        if (this.field_72574_e.field_70128_L)
        {
            return;
        }

        // This is a horrible hack needed because the client sends 2 packets on 'right mouse click'
        // aimed at a block. We shouldn't need to get the second packet if the data is handled
        // but we cannot know what the client will do, so we might still get it
        //
        // If the time between packets is small enough, and the 'signature' similar, we discard the
        // second one. This sadly has to remain until Mojang makes their packets saner. :(
        //  -- Grum

        if (p_72472_1_.func_73401_h() == 255)
        {
            if (p_72472_1_.func_73405_i() != null && p_72472_1_.func_73405_i().field_77993_c == this.lastMaterial && this.lastPacket != null && p_72472_1_.field_73295_m - this.lastPacket < 100)
            {
                this.lastPacket = null;
                return;
            }
        }
        else
        {
            this.lastMaterial = p_72472_1_.func_73405_i() == null ? -1 : p_72472_1_.func_73405_i().field_77993_c;
            this.lastPacket = p_72472_1_.field_73295_m;
        }

        // CraftBukkit - if rightclick decremented the item, always send the update packet.
        // this is not here for CraftBukkit's own functionality; rather it is to fix
        // a notch bug where the item doesn't update correctly.
        boolean always = false;
        // CraftBukkit end
        ItemStack itemstack = this.field_72574_e.field_71071_by.func_70448_g();
        boolean flag = false;
        int i = p_72472_1_.func_73403_d();
        int j = p_72472_1_.func_73402_f();
        int k = p_72472_1_.func_73407_g();
        int l = p_72472_1_.func_73401_h();
        int i1 = this.field_72573_d.func_82357_ak();
        boolean flag1 = worldserver.field_73011_w.field_76574_g != 0 || this.field_72573_d.func_71203_ab().func_72376_i().isEmpty() || this.field_72573_d.func_71203_ab().func_72353_e(this.field_72574_e.field_71092_bJ) || i1 <= 0 || this.field_72573_d.func_71264_H();

        if (p_72472_1_.func_73401_h() == 255)
        {
            if (itemstack == null)
            {
                return;
            }

            // CraftBukkit start
            int itemstackAmount = itemstack.field_77994_a;
            org.bukkit.event.player.PlayerInteractEvent event = CraftEventFactory.callPlayerInteractEvent(this.field_72574_e, Action.RIGHT_CLICK_AIR, itemstack);

            if (event.useItemInHand() != Event.Result.DENY)
            {
                this.field_72574_e.field_71134_c.func_73085_a(this.field_72574_e, this.field_72574_e.field_70170_p, itemstack);
            }

            // CraftBukkit - notch decrements the counter by 1 in the above method with food,
            // snowballs and so forth, but he does it in a place that doesn't cause the
            // inventory update packet to get sent
            always = (itemstack.field_77994_a != itemstackAmount);
            // CraftBukkit end
        }
        else if (p_72472_1_.func_73402_f() >= this.field_72573_d.func_71207_Z() - 1 && (p_72472_1_.func_73401_h() == 1 || p_72472_1_.func_73402_f() >= this.field_72573_d.func_71207_Z()))
        {
            this.field_72574_e.field_71135_a.func_72567_b(new Packet3Chat("\u00A77Height limit for building is " + this.field_72573_d.func_71207_Z()));
            flag = true;
        }
        else
        {
            ChunkCoordinates chunkcoordinates = worldserver.func_72861_E();
            int j1 = MathHelper.func_76130_a(i - chunkcoordinates.field_71574_a);
            int k1 = MathHelper.func_76130_a(k - chunkcoordinates.field_71573_c);

            if (j1 > k1)
            {
                k1 = j1;
            }

            // CraftBukkit start - Check if we can actually do something over this large a distance
            Location eyeLoc = this.getPlayer().getEyeLocation();

            if (Math.pow(eyeLoc.getX() - i, 2) + Math.pow(eyeLoc.getY() - j, 2) + Math.pow(eyeLoc.getZ() - k, 2) > PLACE_DISTANCE_SQUARED)
            {
                return;
            }

            flag1 = true; // spawn protection moved to ItemBlock!!!

            if (j1 > i1 || flag1)
            {
                // CraftBukkit end
                this.field_72574_e.field_71134_c.func_73078_a(this.field_72574_e, worldserver, itemstack, i, j, k, l, p_72472_1_.func_73406_j(), p_72472_1_.func_73404_l(), p_72472_1_.func_73408_m());
            }

            flag = true;
        }

        if (flag)
        {
            this.field_72574_e.field_71135_a.func_72567_b(new Packet53BlockChange(i, j, k, worldserver));

            if (l == 0)
            {
                --j;
            }

            if (l == 1)
            {
                ++j;
            }

            if (l == 2)
            {
                --k;
            }

            if (l == 3)
            {
                ++k;
            }

            if (l == 4)
            {
                --i;
            }

            if (l == 5)
            {
                ++i;
            }

            this.field_72574_e.field_71135_a.func_72567_b(new Packet53BlockChange(i, j, k, worldserver));
        }

        itemstack = this.field_72574_e.field_71071_by.func_70448_g();

        if (itemstack != null && itemstack.field_77994_a == 0)
        {
            this.field_72574_e.field_71071_by.field_70462_a[this.field_72574_e.field_71071_by.field_70461_c] = null;
            itemstack = null;
        }

        if (itemstack == null || itemstack.func_77988_m() == 0)
        {
            this.field_72574_e.field_71137_h = true;
            this.field_72574_e.field_71071_by.field_70462_a[this.field_72574_e.field_71071_by.field_70461_c] = ItemStack.func_77944_b(this.field_72574_e.field_71071_by.field_70462_a[this.field_72574_e.field_71071_by.field_70461_c]);
            Slot slot = this.field_72574_e.field_71070_bA.func_75147_a((IInventory) this.field_72574_e.field_71071_by, this.field_72574_e.field_71071_by.field_70461_c);
            this.field_72574_e.field_71070_bA.func_75142_b();
            this.field_72574_e.field_71137_h = false;

            // CraftBukkit - TODO CHECK IF NEEDED -- new if structure might not need 'always'. Kept it in for now, but may be able to remove in future
            if (!ItemStack.func_77989_b(this.field_72574_e.field_71071_by.func_70448_g(), p_72472_1_.func_73405_i()) || always)
            {
                this.func_72567_b(new Packet103SetSlot(this.field_72574_e.field_71070_bA.field_75152_c, slot.field_75222_d, this.field_72574_e.field_71071_by.func_70448_g()));
            }
        }
    }

    public void func_72515_a(String p_72515_1_, Object[] p_72515_2_)
    {
        if (this.field_72576_c)
        {
            return;    // CraftBukkit - rarely it would send a disconnect line twice
        }

        field_72577_a.info(this.field_72574_e.field_71092_bJ + " lost connection: " + p_72515_1_);
        // CraftBukkit start - we need to handle custom quit messages
        String quitMessage = this.field_72573_d.func_71203_ab().disconnect(this.field_72574_e);

        if ((quitMessage != null) && (quitMessage.length() > 0))
        {
            this.field_72573_d.func_71203_ab().func_72384_a(new Packet3Chat(quitMessage));
        }

        // CraftBukkit end
        this.field_72576_c = true;

        if (this.field_72573_d.func_71264_H() && this.field_72574_e.field_71092_bJ.equals(this.field_72573_d.func_71214_G()))
        {
            field_72577_a.info("Stopping singleplayer server as player logged out");
            this.field_72573_d.func_71263_m();
        }
    }

    public void func_72509_a(Packet p_72509_1_)
    {
        if (this.field_72576_c)
        {
            return;    // CraftBukkit
        }

        field_72577_a.warning(this.getClass() + " wasn\'t prepared to deal with a " + p_72509_1_.getClass());
        this.func_72565_c("Protocol error, unexpected packet");
    }

    public void func_72567_b(Packet p_72567_1_)
    {
        if (p_72567_1_ instanceof Packet3Chat)
        {
            Packet3Chat packet3chat = (Packet3Chat)p_72567_1_;
            int i = this.field_72574_e.func_71126_v();

            if (i == 2)
            {
                return;
            }

            if (i == 1 && !packet3chat.func_73475_d())
            {
                return;
            }

            // CraftBukkit start
            String message = packet3chat.field_73476_b;

            for (final String line : org.bukkit.craftbukkit.TextWrapper.wrapText(message))
            {
                this.field_72575_b.func_74429_a(new Packet3Chat(line));
            }

            return;
            // CraftBukkit end
        }

        // CraftBukkit start
        if (p_72567_1_ == null)
        {
            return;
        }
        else if (p_72567_1_ instanceof Packet6SpawnPosition)
        {
            Packet6SpawnPosition packet6 = (Packet6SpawnPosition) p_72567_1_;
            this.field_72574_e.compassTarget = new Location(this.getPlayer().getWorld(), packet6.field_73300_a, packet6.field_73298_b, packet6.field_73299_c);
        }

        // CraftBukkit end
        this.field_72575_b.func_74429_a(p_72567_1_);
    }

    public void func_72502_a(Packet16BlockItemSwitch p_72502_1_)
    {
        // CraftBukkit start
        if (this.field_72574_e.field_70128_L)
        {
            return;
        }

        if (p_72502_1_.field_73386_a >= 0 && p_72502_1_.field_73386_a < InventoryPlayer.func_70451_h())
        {
            PlayerItemHeldEvent event = new PlayerItemHeldEvent(this.getPlayer(), this.field_72574_e.field_71071_by.field_70461_c, p_72502_1_.field_73386_a);
            this.server.getPluginManager().callEvent(event);
            // CraftBukkit end
            this.field_72574_e.field_71071_by.field_70461_c = p_72502_1_.field_73386_a;
        }
        else
        {
            field_72577_a.warning(this.field_72574_e.field_71092_bJ + " tried to set an invalid carried item");
            this.func_72565_c("Nope!"); // CraftBukkit
        }
    }

    public void func_72481_a(Packet3Chat p_72481_1_)
    {
        if (this.field_72574_e.func_71126_v() == 2)
        {
            this.func_72567_b(new Packet3Chat("Cannot send chat message."));
        }
        else
        {
            String s = p_72481_1_.field_73476_b;

            if (s.length() > 100)
            {
                // CraftBukkit start
                if (p_72481_1_.func_73277_a_())
                {
                    Waitable waitable = new Waitable()
                    {
                        @Override
                        protected Object evaluate()
                        {
                            NetServerHandler.this.func_72565_c("Chat message too long");
                            return null;
                        }
                    };
                    this.field_72573_d.processQueue.add(waitable);

                    try
                    {
                        waitable.get();
                    }
                    catch (InterruptedException e)
                    {
                        Thread.currentThread().interrupt();
                    }
                    catch (ExecutionException e)
                    {
                        throw new RuntimeException(e);
                    }
                }
                else
                {
                    this.func_72565_c("Chat message too long");
                }

                // CraftBukkit end
            }
            else
            {
                s = s.trim();

                for (int i = 0; i < s.length(); ++i)
                {
                    if (!ChatAllowedCharacters.func_71566_a(s.charAt(i)))
                    {
                        // CraftBukkit start
                        if (p_72481_1_.func_73277_a_())
                        {
                            Waitable waitable = new Waitable()
                            {
                                @Override
                                protected Object evaluate()
                                {
                                    NetServerHandler.this.func_72565_c("Illegal characters in chat");
                                    return null;
                                }
                            };
                            this.field_72573_d.processQueue.add(waitable);

                            try
                            {
                                waitable.get();
                            }
                            catch (InterruptedException e)
                            {
                                Thread.currentThread().interrupt();
                            }
                            catch (ExecutionException e)
                            {
                                throw new RuntimeException(e);
                            }
                        }
                        else
                        {
                            this.func_72565_c("Illegal characters in chat");
                        }

                        // CraftBukkit end
                        return;
                    }
                }

                // CraftBukkit start
                if (this.field_72574_e.func_71126_v() == 1 && !s.startsWith("/"))
                {
                    this.func_72567_b(new Packet3Chat("Cannot send chat message."));
                    return;
                }

                this.chat(s, p_72481_1_.func_73277_a_());

                // This section stays because it is only applicable to packets
                if (chatSpamField.addAndGet(this, 20) > 200 && !this.field_72573_d.func_71203_ab().func_72353_e(this.field_72574_e.field_71092_bJ))   // CraftBukkit use thread-safe spam
                {
                    // CraftBukkit start
                    if (p_72481_1_.func_73277_a_())
                    {
                        Waitable waitable = new Waitable()
                        {
                            @Override
                            protected Object evaluate()
                            {
                                NetServerHandler.this.func_72565_c("disconnect.spam");
                                return null;
                            }
                        };
                        this.field_72573_d.processQueue.add(waitable);

                        try
                        {
                            waitable.get();
                        }
                        catch (InterruptedException e)
                        {
                            Thread.currentThread().interrupt();
                        }
                        catch (ExecutionException e)
                        {
                            throw new RuntimeException(e);
                        }
                    }
                    else
                    {
                        this.func_72565_c("disconnect.spam");
                    }

                    // CraftBukkit end
                }
            }
        }
    }

    public void chat(String s, boolean async)
    {
        if (!this.field_72574_e.field_70128_L)
        {
            if (s.length() == 0)
            {
                field_72577_a.warning(this.field_72574_e.field_71092_bJ + " tried to send an empty message");
                return;
            }

            if (getPlayer().isConversing())
            {
                getPlayer().acceptConversationInput(s);
                return;
            }

            if (s.startsWith("/"))
            {
                this.func_72566_d(s);
                return;
            }
            else
            {
                Player player = this.getPlayer();
                AsyncPlayerChatEvent event = new AsyncPlayerChatEvent(async, player, s, new LazyPlayerSet());
                this.server.getPluginManager().callEvent(event);

                if (PlayerChatEvent.getHandlerList().getRegisteredListeners().length != 0)
                {
                    // Evil plugins still listening to deprecated event
                    final PlayerChatEvent queueEvent = new PlayerChatEvent(player, event.getMessage(), event.getFormat(), event.getRecipients());
                    queueEvent.setCancelled(event.isCancelled());
                    Waitable waitable = new Waitable()
                    {
                        @Override
                        protected Object evaluate()
                        {
                            Bukkit.getPluginManager().callEvent(queueEvent);

                            if (queueEvent.isCancelled())
                            {
                                return null;
                            }

                            String message = String.format(queueEvent.getFormat(), queueEvent.getPlayer().getDisplayName(), queueEvent.getMessage());
                            NetServerHandler.this.field_72573_d.console.sendMessage(message);

                            if (((LazyPlayerSet) queueEvent.getRecipients()).isLazy())
                            {
                                for (Object player : NetServerHandler.this.field_72573_d.func_71203_ab().field_72404_b)
                                {
                                    ((EntityPlayerMP) player).func_70006_a(message);
                                }
                            }
                            else
                            {
                                for (Player player : queueEvent.getRecipients())
                                {
                                    player.sendMessage(message);
                                }
                            }

                            return null;
                        }
                    };

                    if (async)
                    {
                        field_72573_d.processQueue.add(waitable);
                    }
                    else
                    {
                        waitable.run();
                    }

                    try
                    {
                        waitable.get();
                    }
                    catch (InterruptedException e)
                    {
                        Thread.currentThread().interrupt(); // This is proper habit for java. If we aren't handling it, pass it on!
                    }
                    catch (ExecutionException e)
                    {
                        throw new RuntimeException("Exception processing chat event", e.getCause());
                    }
                }
                else
                {
                    if (event.isCancelled())
                    {
                        return;
                    }

                    s = String.format(event.getFormat(), event.getPlayer().getDisplayName(), event.getMessage());
                    field_72573_d.console.sendMessage(s);

                    if (((LazyPlayerSet) event.getRecipients()).isLazy())
                    {
                        for (Object recipient : field_72573_d.func_71203_ab().field_72404_b)
                        {
                            ((EntityPlayerMP) recipient).func_70006_a(s);
                        }
                    }
                    else
                    {
                        for (Player recipient : event.getRecipients())
                        {
                            recipient.sendMessage(s);
                        }
                    }
                }
            }
        }

        return;
    }
    // CraftBukkit end

    private void func_72566_d(String p_72566_1_)
    {
        // CraftBukkit start
        CraftPlayer player = this.getPlayer();
        PlayerCommandPreprocessEvent event = new PlayerCommandPreprocessEvent(player, p_72566_1_, new LazyPlayerSet());
        this.server.getPluginManager().callEvent(event);

        if (event.isCancelled())
        {
            return;
        }

        try
        {
            field_72577_a.info(event.getPlayer().getName() + " issued server command: " + event.getMessage()); // CraftBukkit

            if (this.server.dispatchCommand(event.getPlayer(), event.getMessage().substring(1)))
            {
                return;
            }
        }
        catch (org.bukkit.command.CommandException ex)
        {
            player.sendMessage(org.bukkit.ChatColor.RED + "An internal error occurred while attempting to perform this command");
            Logger.getLogger(NetServerHandler.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        // CraftBukkit end
        /* CraftBukkit start - No longer needed as we have already handled it in server.dispatchServerCommand above.
        this.minecraftServer.getCommandHandler().a(this.player, s);
        // CraftBukkit end */
    }

    public void func_72524_a(Packet18Animation p_72524_1_)
    {
        if (this.field_72574_e.field_70128_L)
        {
            return;    // CraftBukkit
        }

        if (p_72524_1_.field_73469_b == 1)
        {
            // CraftBukkit start - raytrace to look for 'rogue armswings'
            float f = 1.0F;
            float f1 = this.field_72574_e.field_70127_C + (this.field_72574_e.field_70125_A - this.field_72574_e.field_70127_C) * f;
            float f2 = this.field_72574_e.field_70126_B + (this.field_72574_e.field_70177_z - this.field_72574_e.field_70126_B) * f;
            double d0 = this.field_72574_e.field_70169_q + (this.field_72574_e.field_70165_t - this.field_72574_e.field_70169_q) * (double) f;
            double d1 = this.field_72574_e.field_70167_r + (this.field_72574_e.field_70163_u - this.field_72574_e.field_70167_r) * (double) f + 1.62D - (double) this.field_72574_e.field_70129_M;
            double d2 = this.field_72574_e.field_70166_s + (this.field_72574_e.field_70161_v - this.field_72574_e.field_70166_s) * (double) f;
            Vec3 vec3 = this.field_72574_e.field_70170_p.func_82732_R().func_72345_a(d0, d1, d2);
            float f3 = MathHelper.func_76134_b(-f2 * 0.017453292F - (float)Math.PI);
            float f4 = MathHelper.func_76126_a(-f2 * 0.017453292F - (float)Math.PI);
            float f5 = -MathHelper.func_76134_b(-f1 * 0.017453292F);
            float f6 = MathHelper.func_76126_a(-f1 * 0.017453292F);
            float f7 = f4 * f5;
            float f8 = f3 * f5;
            double d3 = 5.0D;
            Vec3 vec31 = vec3.func_72441_c((double) f7 * d3, (double) f6 * d3, (double) f8 * d3);
            MovingObjectPosition movingobjectposition = this.field_72574_e.field_70170_p.func_72901_a(vec3, vec31, true);

            if (movingobjectposition == null || movingobjectposition.field_72313_a != EnumMovingObjectType.TILE)
            {
                CraftEventFactory.callPlayerInteractEvent(this.field_72574_e, Action.LEFT_CLICK_AIR, this.field_72574_e.field_71071_by.func_70448_g());
            }

            // Arm swing animation
            PlayerAnimationEvent event = new PlayerAnimationEvent(this.getPlayer());
            this.server.getPluginManager().callEvent(event);

            if (event.isCancelled())
            {
                return;
            }

            // CraftBukkit end
            this.field_72574_e.func_71038_i();
        }
    }

    public void func_72473_a(Packet19EntityAction p_72473_1_)
    {
        // CraftBukkit start
        if (this.field_72574_e.field_70128_L)
        {
            return;
        }

        if (p_72473_1_.field_73366_b == 1 || p_72473_1_.field_73366_b == 2)
        {
            PlayerToggleSneakEvent event = new PlayerToggleSneakEvent(this.getPlayer(), p_72473_1_.field_73366_b == 1);
            this.server.getPluginManager().callEvent(event);

            if (event.isCancelled())
            {
                return;
            }
        }

        if (p_72473_1_.field_73366_b == 4 || p_72473_1_.field_73366_b == 5)
        {
            PlayerToggleSprintEvent event = new PlayerToggleSprintEvent(this.getPlayer(), p_72473_1_.field_73366_b == 4);
            this.server.getPluginManager().callEvent(event);

            if (event.isCancelled())
            {
                return;
            }
        }

        // CraftBukkit end

        if (p_72473_1_.field_73366_b == 1)
        {
            this.field_72574_e.func_70095_a(true);
        }
        else if (p_72473_1_.field_73366_b == 2)
        {
            this.field_72574_e.func_70095_a(false);
        }
        else if (p_72473_1_.field_73366_b == 4)
        {
            this.field_72574_e.func_70031_b(true);
        }
        else if (p_72473_1_.field_73366_b == 5)
        {
            this.field_72574_e.func_70031_b(false);
        }
        else if (p_72473_1_.field_73366_b == 3)
        {
            this.field_72574_e.func_70999_a(false, true, true);
            // this.checkMovement = false; // CraftBukkit - this is handled in teleport
        }
    }

    public void func_72492_a(Packet255KickDisconnect p_72492_1_)
    {
        this.field_72575_b.func_74424_a("disconnect.quitting", new Object[0]);
    }

    public int func_72568_e()
    {
        return this.field_72575_b.func_74426_e();
    }

    public void func_72507_a(Packet7UseEntity p_72507_1_)
    {
        if (this.field_72574_e.field_70128_L)
        {
            return;    // CraftBukkit
        }

        WorldServer worldserver = this.field_72573_d.func_71218_a(this.field_72574_e.field_71093_bK);
        Entity entity = worldserver.func_73045_a(p_72507_1_.field_73604_b);

        if (entity != null)
        {
            boolean flag = this.field_72574_e.func_70685_l(entity);
            double d0 = 36.0D;

            if (!flag)
            {
                d0 = 9.0D;
            }

            if (this.field_72574_e.func_70068_e(entity) < d0)
            {
                ItemStack itemInHand = this.field_72574_e.field_71071_by.func_70448_g(); // CraftBukkit

                if (p_72507_1_.field_73605_c == 0)
                {
                    // CraftBukkit start
                    PlayerInteractEntityEvent event = new PlayerInteractEntityEvent((Player) this.getPlayer(), entity.getBukkitEntity());
                    this.server.getPluginManager().callEvent(event);

                    if (event.isCancelled())
                    {
                        return;
                    }

                    // CraftBukkit end
                    this.field_72574_e.func_70998_m(entity);

                    // CraftBukkit start - update the client if the item is an infinite one
                    if (itemInHand != null && itemInHand.field_77994_a <= -1)
                    {
                        this.field_72574_e.func_71120_a(this.field_72574_e.field_71070_bA);
                    }
                }
                else if (p_72507_1_.field_73605_c == 1)
                {
                    if ((entity instanceof EntityItem) || (entity instanceof EntityXPOrb) || (entity instanceof EntityArrow))
                    {
                        String type = entity.getClass().getSimpleName();
                        func_72565_c("Attacking an " + type + " is not permitted");
                        System.out.println("Player " + field_72574_e.field_71092_bJ + " tried to attack an " + type + ", so I have disconnected them for exploiting.");
                        return;
                    }

                    this.field_72574_e.func_71059_n(entity);

                    if (itemInHand != null && itemInHand.field_77994_a <= -1)
                    {
                        this.field_72574_e.func_71120_a(this.field_72574_e.field_71070_bA);
                    }

                    // CraftBukkit end
                }
            }
        }
    }

    public void func_72458_a(Packet205ClientCommand p_72458_1_)
    {
        if (p_72458_1_.field_73447_a == 1)
        {
            if (this.field_72574_e.field_71136_j)
            {
                this.field_72573_d.func_71203_ab().changeDimension(this.field_72574_e, 0, TeleportCause.END_PORTAL); // CraftBukkit - reroute logic through custom portal management
            }
            else if (this.field_72574_e.func_71121_q().func_72912_H().func_76093_s())
            {
                if (this.field_72573_d.func_71264_H() && this.field_72574_e.field_71092_bJ.equals(this.field_72573_d.func_71214_G()))
                {
                    this.field_72574_e.field_71135_a.func_72565_c("You have died. Game over, man, it\'s game over!");
                    this.field_72573_d.func_71272_O();
                }
                else
                {
                    BanEntry banentry = new BanEntry(this.field_72574_e.field_71092_bJ);
                    banentry.func_73689_b("Death in Hardcore");
                    this.field_72573_d.func_71203_ab().func_72390_e().func_73706_a(banentry);
                    this.field_72574_e.field_71135_a.func_72565_c("You have died. Game over, man, it\'s game over!");
                }
            }
            else
            {
                if (this.field_72574_e.func_70630_aN() > 0)
                {
                    return;
                }

                this.field_72574_e = this.field_72573_d.func_71203_ab().func_72368_a(this.field_72574_e, 0, false);
            }
        }
    }

    public boolean func_72469_b()
    {
        return true;
    }

    public void func_72483_a(Packet9Respawn p_72483_1_) {}

    public void func_72474_a(Packet101CloseWindow p_72474_1_)
    {
        if (this.field_72574_e.field_70128_L)
        {
            return;    // CraftBukkit
        }

        // CraftBukkit start - INVENTORY_CLOSE hook
        InventoryCloseEvent event = new InventoryCloseEvent(this.field_72574_e.field_71070_bA.getBukkitView());
        server.getPluginManager().callEvent(event);
        this.field_72574_e.field_71070_bA.transferTo(this.field_72574_e.field_71069_bz, getPlayer());
        // CraftBukkit end
        this.field_72574_e.func_71128_l();
    }

    public void func_72523_a(Packet102WindowClick p_72523_1_)
    {
        if (this.field_72574_e.field_70128_L)
        {
            return;    // CraftBukkit
        }

        if (this.field_72574_e.field_71070_bA.field_75152_c == p_72523_1_.field_73444_a && this.field_72574_e.field_71070_bA.func_75129_b(this.field_72574_e))
        {
            // CraftBukkit start - fire InventoryClickEvent
            InventoryView inventory = this.field_72574_e.field_71070_bA.getBukkitView();
            SlotType type = CraftInventoryView.getSlotType(inventory, p_72523_1_.field_73442_b);
            InventoryClickEvent event = new InventoryClickEvent(inventory, type, p_72523_1_.field_73442_b, p_72523_1_.field_73443_c != 0, p_72523_1_.field_73439_f == 1);
            org.bukkit.inventory.Inventory top = inventory.getTopInventory();

            if (p_72523_1_.field_73442_b == 0 && top instanceof CraftingInventory)
            {
                org.bukkit.inventory.Recipe recipe = ((CraftingInventory) top).getRecipe();

                if (recipe != null)
                {
                    event = new CraftItemEvent(recipe, inventory, type, p_72523_1_.field_73442_b, p_72523_1_.field_73443_c != 0, p_72523_1_.field_73439_f == 1);
                }
            }

            server.getPluginManager().callEvent(event);
            ItemStack itemstack = null;
            boolean defaultBehaviour = false;
            switch (event.getResult())
            {
                case DEFAULT:
                    itemstack = this.field_72574_e.field_71070_bA.func_75144_a(p_72523_1_.field_73442_b, p_72523_1_.field_73443_c, p_72523_1_.field_73439_f, this.field_72574_e);
                    defaultBehaviour = true;
                    break;
                case DENY: // Deny any change, including changes from the event
                    break;
                case ALLOW: // Allow changes unconditionally
                    org.bukkit.inventory.ItemStack cursor = event.getCursor();

                    if (cursor == null)
                    {
                        this.field_72574_e.field_71071_by.func_70437_b((ItemStack) null);
                    }
                    else
                    {
                        this.field_72574_e.field_71071_by.func_70437_b(CraftItemStack.asNMSCopy(cursor));
                    }

                    org.bukkit.inventory.ItemStack item = event.getCurrentItem();

                    if (item != null)
                    {
                        itemstack = CraftItemStack.asNMSCopy(item);

                        if (p_72523_1_.field_73442_b == -999)
                        {
                            this.field_72574_e.func_71021_b(itemstack);
                        }
                        else
                        {
                            this.field_72574_e.field_71070_bA.func_75139_a(p_72523_1_.field_73442_b).func_75215_d(itemstack);
                        }
                    }
                    else if (p_72523_1_.field_73442_b != -999)
                    {
                        this.field_72574_e.field_71070_bA.func_75139_a(p_72523_1_.field_73442_b).func_75215_d((ItemStack) null);
                    }

                    break;
            }

            // CraftBukkit end

            if (ItemStack.func_77989_b(p_72523_1_.field_73441_e, itemstack))
            {
                this.field_72574_e.field_71135_a.func_72567_b(new Packet106Transaction(p_72523_1_.field_73444_a, p_72523_1_.field_73440_d, true));
                this.field_72574_e.field_71137_h = true;
                this.field_72574_e.field_71070_bA.func_75142_b();
                this.field_72574_e.func_71113_k();
                this.field_72574_e.field_71137_h = false;
            }
            else
            {
                this.field_72586_s.func_76038_a(this.field_72574_e.field_71070_bA.field_75152_c, Short.valueOf(p_72523_1_.field_73440_d));
                this.field_72574_e.field_71135_a.func_72567_b(new Packet106Transaction(p_72523_1_.field_73444_a, p_72523_1_.field_73440_d, false));
                this.field_72574_e.field_71070_bA.func_75128_a(this.field_72574_e, false);
                ArrayList arraylist = new ArrayList();

                for (int i = 0; i < this.field_72574_e.field_71070_bA.field_75151_b.size(); ++i)
                {
                    arraylist.add(((Slot)this.field_72574_e.field_71070_bA.field_75151_b.get(i)).func_75211_c());
                }

                this.field_72574_e.func_71110_a(this.field_72574_e.field_71070_bA, arraylist);

                // CraftBukkit start - send a Set Slot to update the crafting result slot
                if (type == SlotType.RESULT && itemstack != null)
                {
                    this.field_72574_e.field_71135_a.func_72567_b((Packet)(new Packet103SetSlot(this.field_72574_e.field_71070_bA.field_75152_c, 0, itemstack)));
                }

                // CraftBukkit end
            }
        }
    }

    public void func_72479_a(Packet108EnchantItem p_72479_1_)
    {
        if (this.field_72574_e.field_71070_bA.field_75152_c == p_72479_1_.field_73446_a && this.field_72574_e.field_71070_bA.func_75129_b(this.field_72574_e))
        {
            this.field_72574_e.field_71070_bA.func_75140_a((EntityPlayer) this.field_72574_e, p_72479_1_.field_73445_b);
            this.field_72574_e.field_71070_bA.func_75142_b();
        }
    }

    public void func_72464_a(Packet107CreativeSetSlot p_72464_1_)
    {
        if (this.field_72574_e.field_71134_c.func_73083_d())
        {
            boolean flag = p_72464_1_.field_73385_a < 0;
            ItemStack itemstack = p_72464_1_.field_73384_b;
            boolean flag1 = p_72464_1_.field_73385_a >= 1 && p_72464_1_.field_73385_a < 36 + InventoryPlayer.func_70451_h();
            // CraftBukkit
            boolean flag2 = itemstack == null || itemstack.field_77993_c < Item.field_77698_e.length && itemstack.field_77993_c >= 0 && Item.field_77698_e[itemstack.field_77993_c] != null && !invalidItems.contains(itemstack.field_77993_c);
            boolean flag3 = itemstack == null || itemstack.func_77960_j() >= 0 && itemstack.func_77960_j() >= 0 && itemstack.field_77994_a <= 64 && itemstack.field_77994_a > 0;
            // CraftBukkit start - Fire INVENTORY_CLICK event
            org.bukkit.entity.HumanEntity player = this.field_72574_e.getBukkitEntity();
            InventoryView inventory = new CraftInventoryView(player, player.getInventory(), this.field_72574_e.field_71069_bz);
            SlotType slot = SlotType.QUICKBAR;

            if (p_72464_1_.field_73385_a == -1)
            {
                slot = SlotType.OUTSIDE;
            }

            InventoryClickEvent event = new InventoryClickEvent(inventory, slot, slot == SlotType.OUTSIDE ? -999 : p_72464_1_.field_73385_a, false, false);
            server.getPluginManager().callEvent(event);
            org.bukkit.inventory.ItemStack item = event.getCurrentItem();

            switch (event.getResult())
            {
                case ALLOW:
                    if (slot == SlotType.QUICKBAR)
                    {
                        if (item == null)
                        {
                            this.field_72574_e.field_71069_bz.func_75141_a(p_72464_1_.field_73385_a, (ItemStack) null);
                        }
                        else
                        {
                            this.field_72574_e.field_71069_bz.func_75141_a(p_72464_1_.field_73385_a, CraftItemStack.asNMSCopy(item));
                        }
                    }
                    else if (item != null)
                    {
                        this.field_72574_e.func_71021_b(CraftItemStack.asNMSCopy(item));
                    }

                    return;
                case DENY:
                    // TODO: Will this actually work?
                    if (p_72464_1_.field_73385_a > -1)
                    {
                        this.field_72574_e.field_71135_a.func_72567_b(new Packet103SetSlot(this.field_72574_e.field_71069_bz.field_75152_c, p_72464_1_.field_73385_a, CraftItemStack.asNMSCopy(item)));
                    }

                    return;
                case DEFAULT:
                    // We do the stuff below
                    break;
                default:
                    return;
            }

            // CraftBukkit end

            if (flag1 && flag2 && flag3)
            {
                if (itemstack == null)
                {
                    this.field_72574_e.field_71069_bz.func_75141_a(p_72464_1_.field_73385_a, (ItemStack)null);
                }
                else
                {
                    this.field_72574_e.field_71069_bz.func_75141_a(p_72464_1_.field_73385_a, itemstack);
                }

                this.field_72574_e.field_71069_bz.func_75128_a(this.field_72574_e, true);
            }
            else if (flag && flag2 && flag3 && this.field_72578_n < 200)
            {
                this.field_72578_n += 20;
                EntityItem entityitem = this.field_72574_e.func_71021_b(itemstack);

                if (entityitem != null)
                {
                    entityitem.func_70288_d();
                }
            }
        }
    }

    public void func_72476_a(Packet106Transaction p_72476_1_)
    {
        if (this.field_72574_e.field_70128_L)
        {
            return;    // CraftBukkit
        }

        Short oshort = (Short)this.field_72586_s.func_76041_a(this.field_72574_e.field_71070_bA.field_75152_c);

        if (oshort != null && p_72476_1_.field_73433_b == oshort.shortValue() && this.field_72574_e.field_71070_bA.field_75152_c == p_72476_1_.field_73435_a && !this.field_72574_e.field_71070_bA.func_75129_b(this.field_72574_e))
        {
            this.field_72574_e.field_71070_bA.func_75128_a(this.field_72574_e, true);
        }
    }

    public void func_72487_a(Packet130UpdateSign p_72487_1_)
    {
        if (this.field_72574_e.field_70128_L)
        {
            return;    // CraftBukkit
        }

        WorldServer worldserver = this.field_72573_d.func_71218_a(this.field_72574_e.field_71093_bK);

        if (worldserver.func_72899_e(p_72487_1_.field_73311_a, p_72487_1_.field_73309_b, p_72487_1_.field_73310_c))
        {
            TileEntity tileentity = worldserver.func_72796_p(p_72487_1_.field_73311_a, p_72487_1_.field_73309_b, p_72487_1_.field_73310_c);

            if (tileentity instanceof TileEntitySign)
            {
                TileEntitySign tileentitysign = (TileEntitySign)tileentity;

                if (!tileentitysign.func_70409_a())
                {
                    this.field_72573_d.func_71236_h("Player " + this.field_72574_e.field_71092_bJ + " just tried to change non-editable sign");
                    this.func_72567_b(new Packet130UpdateSign(p_72487_1_.field_73311_a, p_72487_1_.field_73309_b, p_72487_1_.field_73310_c, tileentitysign.field_70412_a)); // CraftBukkit
                    return;
                }
            }

            int i;
            int j;

            for (j = 0; j < 4; ++j)
            {
                boolean flag = true;

                if (p_72487_1_.field_73308_d[j].length() > 15)
                {
                    flag = false;
                }
                else
                {
                    for (i = 0; i < p_72487_1_.field_73308_d[j].length(); ++i)
                    {
                        if (ChatAllowedCharacters.field_71568_a.indexOf(p_72487_1_.field_73308_d[j].charAt(i)) < 0)
                        {
                            flag = false;
                        }
                    }
                }

                if (!flag)
                {
                    p_72487_1_.field_73308_d[j] = "!?";
                }
            }

            if (tileentity instanceof TileEntitySign)
            {
                j = p_72487_1_.field_73311_a;
                int k = p_72487_1_.field_73309_b;
                i = p_72487_1_.field_73310_c;
                TileEntitySign tileentitysign1 = (TileEntitySign)tileentity;
                // CraftBukkit start
                Player player = this.server.getPlayer(this.field_72574_e);
                SignChangeEvent event = new SignChangeEvent((org.bukkit.craftbukkit.block.CraftBlock) player.getWorld().getBlockAt(j, k, i), this.server.getPlayer(this.field_72574_e), p_72487_1_.field_73308_d);
                this.server.getPluginManager().callEvent(event);

                if (!event.isCancelled())
                {
                    for (int l = 0; l < 4; ++l)
                    {
                        tileentitysign1.field_70412_a[l] = event.getLine(l);

                        if (tileentitysign1.field_70412_a[l] == null)
                        {
                            tileentitysign1.field_70412_a[l] = "";
                        }
                    }

                    tileentitysign1.field_70411_c = false;
                }

                // CraftBukkit end
                tileentitysign1.func_70296_d();
                worldserver.func_72845_h(j, k, i);
            }
        }
    }

    public void func_72477_a(Packet0KeepAlive p_72477_1_)
    {
        if (p_72477_1_.field_73592_a == this.field_72585_i)
        {
            int i = (int)(System.nanoTime() / 1000000L - this.field_72582_j);
            this.field_72574_e.field_71138_i = (this.field_72574_e.field_71138_i * 3 + i) / 4;
        }
    }

    public boolean func_72489_a()
    {
        return true;
    }

    public void func_72471_a(Packet202PlayerAbilities p_72471_1_)
    {
        // CraftBukkit start
        if (this.field_72574_e.field_71075_bZ.field_75101_c && this.field_72574_e.field_71075_bZ.field_75100_b != p_72471_1_.func_73350_f())
        {
            PlayerToggleFlightEvent event = new PlayerToggleFlightEvent(this.server.getPlayer(this.field_72574_e), p_72471_1_.func_73350_f());
            this.server.getPluginManager().callEvent(event);

            if (!event.isCancelled())
            {
                this.field_72574_e.field_71075_bZ.field_75100_b = p_72471_1_.func_73350_f(); // Actually set the player's flying status
            }
            else
            {
                this.field_72574_e.func_71016_p(); // Tell the player their ability was reverted
            }
        }

        // CraftBukkit end
    }

    public void func_72461_a(Packet203AutoComplete p_72461_1_)
    {
        StringBuilder stringbuilder = new StringBuilder();
        String s;

        for (Iterator iterator = this.field_72573_d.func_71248_a((ICommandSender) this.field_72574_e, p_72461_1_.func_73473_d()).iterator(); iterator.hasNext(); stringbuilder.append(s))
        {
            s = (String)iterator.next();

            if (stringbuilder.length() > 0)
            {
                stringbuilder.append('\0'); // CraftBukkit - fix decompile issue
            }
        }

        this.field_72574_e.field_71135_a.func_72567_b(new Packet203AutoComplete(stringbuilder.toString()));
    }

    public void func_72504_a(Packet204ClientInfo p_72504_1_)
    {
        this.field_72574_e.func_71125_a(p_72504_1_);
    }

    public void func_72501_a(Packet250CustomPayload p_72501_1_)
    {
        DataInputStream datainputstream;
        ItemStack itemstack;
        ItemStack itemstack1;

        // CraftBukkit start - ignore empty payloads
        if (p_72501_1_.field_73628_b <= 0)
        {
            return;
        }

        // CraftBukkit end

        if ("MC|BEdit".equals(p_72501_1_.field_73630_a))
        {
            try
            {
                datainputstream = new DataInputStream(new ByteArrayInputStream(p_72501_1_.field_73629_c));
                itemstack = Packet.func_73276_c(datainputstream);

                if (!ItemWritableBook.func_77829_a(itemstack.func_77978_p()))
                {
                    throw new IOException("Invalid book tag!");
                }

                itemstack1 = this.field_72574_e.field_71071_by.func_70448_g();

                if (itemstack != null && itemstack.field_77993_c == Item.field_77821_bF.field_77779_bT && itemstack.field_77993_c == itemstack1.field_77993_c)
                {
                    itemstack1.func_77983_a("pages", (NBTBase) itemstack.func_77978_p().func_74761_m("pages"));
                }
            }
            catch (Exception exception)
            {
                // CraftBukkit start
                field_72577_a.log(Level.WARNING, this.field_72574_e.field_71092_bJ + " sent invalid MC|BEdit data", exception);
                this.func_72565_c("Invalid book data!");
                // CraftBukkit end
            }
        }
        else if ("MC|BSign".equals(p_72501_1_.field_73630_a))
        {
            try
            {
                datainputstream = new DataInputStream(new ByteArrayInputStream(p_72501_1_.field_73629_c));
                itemstack = Packet.func_73276_c(datainputstream);

                if (!ItemEditableBook.func_77828_a(itemstack.func_77978_p()))
                {
                    throw new IOException("Invalid book tag!");
                }

                itemstack1 = this.field_72574_e.field_71071_by.func_70448_g();

                if (itemstack != null && itemstack.field_77993_c == Item.field_77823_bG.field_77779_bT && itemstack1.field_77993_c == Item.field_77821_bF.field_77779_bT)
                {
                    itemstack1.func_77983_a("author", (NBTBase)(new NBTTagString("author", this.field_72574_e.field_71092_bJ)));
                    itemstack1.func_77983_a("title", (NBTBase)(new NBTTagString("title", itemstack.func_77978_p().func_74779_i("title"))));
                    itemstack1.func_77983_a("pages", (NBTBase) itemstack.func_77978_p().func_74761_m("pages"));
                    itemstack1.field_77993_c = Item.field_77823_bG.field_77779_bT;
                }
            }
            catch (Exception exception1)
            {
                // CraftBukkit start
                field_72577_a.log(Level.WARNING, this.field_72574_e.field_71092_bJ + " sent invalid MC|BSign data", exception);
                this.func_72565_c("Invalid book data!");
                // CraftBukkit end
            }
        }
        else
        {
            int i;

            if ("MC|TrSel".equals(p_72501_1_.field_73630_a))
            {
                try
                {
                    datainputstream = new DataInputStream(new ByteArrayInputStream(p_72501_1_.field_73629_c));
                    i = datainputstream.readInt();
                    Container container = this.field_72574_e.field_71070_bA;

                    if (container instanceof ContainerMerchant)
                    {
                        ((ContainerMerchant)container).func_75175_c(i);
                    }
                }
                catch (Exception exception2)
                {
                    // CraftBukkit start
                    field_72577_a.log(Level.WARNING, this.field_72574_e.field_71092_bJ + " sent invalid MC|TrSel data", exception);
                    this.func_72565_c("Invalid trade data!");
                    // CraftBukkit end
                }
            }
            else
            {
                int j;

                if ("MC|AdvCdm".equals(p_72501_1_.field_73630_a))
                {
                    if (!this.field_72573_d.func_82356_Z())
                    {
                        this.field_72574_e.func_70006_a(this.field_72574_e.func_70004_a("advMode.notEnabled", new Object[0]));
                    }
                    else if (this.field_72574_e.func_70003_b(2, "") && this.field_72574_e.field_71075_bZ.field_75098_d)
                    {
                        try
                        {
                            datainputstream = new DataInputStream(new ByteArrayInputStream(p_72501_1_.field_73629_c));
                            i = datainputstream.readInt();
                            j = datainputstream.readInt();
                            int k = datainputstream.readInt();
                            String s = Packet.func_73282_a(datainputstream, 256);
                            TileEntity tileentity = this.field_72574_e.field_70170_p.func_72796_p(i, j, k);

                            if (tileentity != null && tileentity instanceof TileEntityCommandBlock)
                            {
                                ((TileEntityCommandBlock)tileentity).func_82352_b(s);
                                this.field_72574_e.field_70170_p.func_72845_h(i, j, k);
                                this.field_72574_e.func_70006_a("Command set: " + s);
                            }
                        }
                        catch (Exception exception3)
                        {
                            // CraftBukkit start
                            field_72577_a.log(Level.WARNING, this.field_72574_e.field_71092_bJ + " sent invalid MC|AdvCdm data", exception);
                            this.func_72565_c("Invalid CommandBlock data!");
                            // CraftBukkit end
                        }
                    }
                    else
                    {
                        this.field_72574_e.func_70006_a(this.field_72574_e.func_70004_a("advMode.notAllowed", new Object[0]));
                    }
                }
                else if ("MC|Beacon".equals(p_72501_1_.field_73630_a))
                {
                    if (this.field_72574_e.field_71070_bA instanceof ContainerBeacon)
                    {
                        try
                        {
                            datainputstream = new DataInputStream(new ByteArrayInputStream(p_72501_1_.field_73629_c));
                            i = datainputstream.readInt();
                            j = datainputstream.readInt();
                            ContainerBeacon containerbeacon = (ContainerBeacon)this.field_72574_e.field_71070_bA;
                            Slot slot = containerbeacon.func_75139_a(0);

                            if (slot.func_75216_d())
                            {
                                slot.func_75209_a(1);
                                TileEntityBeacon tileentitybeacon = containerbeacon.func_82863_d();
                                tileentitybeacon.func_82128_d(i);
                                tileentitybeacon.func_82127_e(j);
                                tileentitybeacon.func_70296_d();
                            }
                        }
                        catch (Exception exception4)
                        {
                            // CraftBukkit start
                            field_72577_a.log(Level.WARNING, this.field_72574_e.field_71092_bJ + " sent invalid MC|Beacon data", exception);
                            this.func_72565_c("Invalid beacon data!");
                            // CraftBukkit end
                        }
                    }
                }
                else if ("MC|ItemName".equals(p_72501_1_.field_73630_a) && this.field_72574_e.field_71070_bA instanceof ContainerRepair)
                {
                    ContainerRepair containerrepair = (ContainerRepair)this.field_72574_e.field_71070_bA;

                    if (p_72501_1_.field_73629_c != null && p_72501_1_.field_73629_c.length >= 1)
                    {
                        String s1 = ChatAllowedCharacters.func_71565_a(new String(p_72501_1_.field_73629_c));

                        if (s1.length() <= 30)
                        {
                            containerrepair.func_82850_a(s1);
                        }
                    }
                    else
                    {
                        containerrepair.func_82850_a("");
                    }
                }
                // CraftBukkit start
                else if (p_72501_1_.field_73630_a.equals("REGISTER"))
                {
                    try
                    {
                        String channels = new String(p_72501_1_.field_73629_c, "UTF8");

                        for (String channel : channels.split("\0"))
                        {
                            getPlayer().addChannel(channel);
                        }
                    }
                    catch (UnsupportedEncodingException ex)
                    {
                        throw new AssertionError(ex);
                    }
                }
                else if (p_72501_1_.field_73630_a.equals("UNREGISTER"))
                {
                    try
                    {
                        String channels = new String(p_72501_1_.field_73629_c, "UTF8");

                        for (String channel : channels.split("\0"))
                        {
                            getPlayer().removeChannel(channel);
                        }
                    }
                    catch (UnsupportedEncodingException ex)
                    {
                        throw new AssertionError(ex);
                    }
                }
                else
                {
                    server.getMessenger().dispatchIncomingMessage(field_72574_e.getBukkitEntity(), p_72501_1_.field_73630_a, p_72501_1_.field_73629_c);
                }

                // CraftBukkit end
            }
        }
    }
}
