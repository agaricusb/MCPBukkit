package org.bukkit.craftbukkit.entity;

import java.util.Set;


import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.craftbukkit.inventory.CraftContainer;
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.craftbukkit.inventory.CraftInventoryPlayer;
import org.bukkit.craftbukkit.inventory.CraftInventoryView;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.permissions.PermissibleBase;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

public class CraftHumanEntity extends CraftLivingEntity implements HumanEntity {
    private CraftInventoryPlayer inventory;
    private final CraftInventory enderChest;
    protected final PermissibleBase perm = new PermissibleBase(this);
    private boolean op;
    private GameMode mode;

    public CraftHumanEntity(final CraftServer server, final net.minecraft.entity.player.EntityPlayer/*was:EntityHuman*/ entity) {
        super(server, entity);
        mode = server.getDefaultGameMode();
        this.inventory = new CraftInventoryPlayer(entity.field_71071_by/*was:inventory*/);
        enderChest = new CraftInventory(entity.func_71005_bN/*was:getEnderChest*/());
    }

    public String getName() {
        return getHandle().field_71092_bJ/*was:name*/;
    }

    public PlayerInventory getInventory() {
        return inventory;
    }

    public EntityEquipment getEquipment() {
        return inventory;
    }

    public Inventory getEnderChest() {
        return enderChest;
    }

    public ItemStack getItemInHand() {
        return getInventory().getItemInHand();
    }

    public void setItemInHand(ItemStack item) {
        getInventory().setItemInHand(item);
    }

    public ItemStack getItemOnCursor() {
        return CraftItemStack.asCraftMirror(getHandle().field_71071_by/*was:inventory*/.func_70445_o/*was:getCarried*/());
    }

    public void setItemOnCursor(ItemStack item) {
        net.minecraft.item.ItemStack/*was:net.minecraft.server.ItemStack*/ stack = CraftItemStack.asNMSCopy(item);
        getHandle().field_71071_by/*was:inventory*/.func_70437_b/*was:setCarried*/(stack);
        if (this instanceof CraftPlayer) {
            ((net.minecraft.entity.player.EntityPlayerMP/*was:EntityPlayer*/) getHandle()).func_71113_k/*was:broadcastCarriedItem*/(); // Send set slot for cursor
        }
    }

    public boolean isSleeping() {
        return getHandle().field_71083_bS/*was:sleeping*/;
    }

    public int getSleepTicks() {
        return getHandle().field_71076_b/*was:sleepTicks*/;
    }

    public boolean isOp() {
        return op;
    }

    public boolean isPermissionSet(String name) {
        return perm.isPermissionSet(name);
    }

    public boolean isPermissionSet(Permission perm) {
        return this.perm.isPermissionSet(perm);
    }

    public boolean hasPermission(String name) {
        return perm.hasPermission(name);
    }

    public boolean hasPermission(Permission perm) {
        return this.perm.hasPermission(perm);
    }

    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) {
        return perm.addAttachment(plugin, name, value);
    }

    public PermissionAttachment addAttachment(Plugin plugin) {
        return perm.addAttachment(plugin);
    }

    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks) {
        return perm.addAttachment(plugin, name, value, ticks);
    }

    public PermissionAttachment addAttachment(Plugin plugin, int ticks) {
        return perm.addAttachment(plugin, ticks);
    }

    public void removeAttachment(PermissionAttachment attachment) {
        perm.removeAttachment(attachment);
    }

    public void recalculatePermissions() {
        perm.recalculatePermissions();
    }

    public void setOp(boolean value) {
        this.op = value;
        perm.recalculatePermissions();
    }

    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return perm.getEffectivePermissions();
    }

    public GameMode getGameMode() {
        return mode;
    }

    public void setGameMode(GameMode mode) {
        if (mode == null) {
            throw new IllegalArgumentException("Mode cannot be null");
        }

        this.mode = mode;
    }

    @Override
    public net.minecraft.entity.player.EntityPlayer/*was:EntityHuman*/ getHandle() {
        return (net.minecraft.entity.player.EntityPlayer/*was:EntityHuman*/) entity;
    }

    public void setHandle(final net.minecraft.entity.player.EntityPlayer/*was:EntityHuman*/ entity) {
        super.setHandle(entity);
        this.inventory = new CraftInventoryPlayer(entity.field_71071_by/*was:inventory*/);
    }

    @Override
    public String toString() {
        return "CraftHumanEntity{" + "id=" + getEntityId() + "name=" + getName() + '}';
    }

    public InventoryView getOpenInventory() {
        return getHandle().field_71070_bA/*was:activeContainer*/.getBukkitView();
    }

    public InventoryView openInventory(Inventory inventory) {
        if(!(getHandle() instanceof net.minecraft.entity.player.EntityPlayerMP/*was:EntityPlayer*/)) return null;
        net.minecraft.entity.player.EntityPlayerMP/*was:EntityPlayer*/ player = (net.minecraft.entity.player.EntityPlayerMP/*was:EntityPlayer*/) getHandle();
        InventoryType type = inventory.getType();
        net.minecraft.inventory.Container/*was:Container*/ formerContainer = getHandle().field_71070_bA/*was:activeContainer*/;
        // TODO: Should we check that it really IS a CraftInventory first?
        CraftInventory craftinv = (CraftInventory) inventory;
        switch(type) {
        case PLAYER:
        case CHEST:
        case ENDER_CHEST:
            getHandle().func_71007_a/*was:openContainer*/(craftinv.getInventory());
            break;
        case DISPENSER:
            if (craftinv.getInventory() instanceof net.minecraft.tileentity.TileEntityDispenser/*was:TileEntityDispenser*/) {
                getHandle().func_71006_a/*was:openDispenser*/((net.minecraft.tileentity.TileEntityDispenser/*was:TileEntityDispenser*/)craftinv.getInventory());
            } else {
                openCustomInventory(inventory, player, 3);
            }
            break;
        case FURNACE:
            if (craftinv.getInventory() instanceof net.minecraft.tileentity.TileEntityFurnace/*was:TileEntityFurnace*/) {
                getHandle().func_71042_a/*was:openFurnace*/((net.minecraft.tileentity.TileEntityFurnace/*was:TileEntityFurnace*/)craftinv.getInventory());
            } else {
                openCustomInventory(inventory, player, 2);
            }
            break;
        case WORKBENCH:
            openCustomInventory(inventory, player, 1);
            break;
        case BREWING:
            if (craftinv.getInventory() instanceof net.minecraft.tileentity.TileEntityBrewingStand/*was:TileEntityBrewingStand*/) {
                getHandle().func_71017_a/*was:openBrewingStand*/((net.minecraft.tileentity.TileEntityBrewingStand/*was:TileEntityBrewingStand*/)craftinv.getInventory());
            } else {
                openCustomInventory(inventory, player, 5);
            }
            break;
        case ENCHANTING:
                openCustomInventory(inventory, player, 4);
            break;
        case CREATIVE:
        case CRAFTING:
            throw new IllegalArgumentException("Can't open a " + type + " inventory!");
        }
        if (getHandle().field_71070_bA/*was:activeContainer*/ == formerContainer) {
            return null;
        }
        getHandle().field_71070_bA/*was:activeContainer*/.checkReachable = false;
        return getHandle().field_71070_bA/*was:activeContainer*/.getBukkitView();
    }

    private void openCustomInventory(Inventory inventory, net.minecraft.entity.player.EntityPlayerMP/*was:EntityPlayer*/ player, int windowType) {
        if (player.field_71135_a/*was:playerConnection*/ == null) return;
        net.minecraft.inventory.Container/*was:Container*/ container = new CraftContainer(inventory, this, player.nextContainerCounter());

        container = CraftEventFactory.callInventoryOpenEvent(player, container);
        if(container == null) return;

        String title = container.getBukkitView().getTitle();
        int size = container.getBukkitView().getTopInventory().getSize();

        player.field_71135_a/*was:playerConnection*/.func_72567_b/*was:sendPacket*/(new net.minecraft.network.packet.Packet100OpenWindow/*was:Packet100OpenWindow*/(container.field_75152_c/*was:windowId*/, windowType, title, size));
        getHandle().field_71070_bA/*was:activeContainer*/ = container;
        getHandle().field_71070_bA/*was:activeContainer*/.func_75132_a/*was:addSlotListener*/(player);
    }

    public InventoryView openWorkbench(Location location, boolean force) {
        if (!force) {
            Block block = location.getBlock();
            if (block.getType() != Material.WORKBENCH) {
                return null;
            }
        }
        if (location == null) {
            location = getLocation();
        }
        getHandle().func_71058_b/*was:startCrafting*/(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        if (force) {
            getHandle().field_71070_bA/*was:activeContainer*/.checkReachable = false;
        }
        return getHandle().field_71070_bA/*was:activeContainer*/.getBukkitView();
    }

    public InventoryView openEnchanting(Location location, boolean force) {
        if (!force) {
            Block block = location.getBlock();
            if (block.getType() != Material.ENCHANTMENT_TABLE) {
                return null;
            }
        }
        if (location == null) {
            location = getLocation();
        }
        getHandle().func_71002_c/*was:startEnchanting*/(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        if (force) {
            getHandle().field_71070_bA/*was:activeContainer*/.checkReachable = false;
        }
        return getHandle().field_71070_bA/*was:activeContainer*/.getBukkitView();
    }

    public void openInventory(InventoryView inventory) {
        if (!(getHandle() instanceof net.minecraft.entity.player.EntityPlayerMP/*was:EntityPlayer*/)) return; // TODO: NPC support?
        if (((net.minecraft.entity.player.EntityPlayerMP/*was:EntityPlayer*/) getHandle()).field_71135_a/*was:playerConnection*/ == null) return;
        if (getHandle().field_71070_bA/*was:activeContainer*/ != getHandle().field_71069_bz/*was:defaultContainer*/) {
            // fire INVENTORY_CLOSE if one already open
            ((net.minecraft.entity.player.EntityPlayerMP/*was:EntityPlayer*/)getHandle()).field_71135_a/*was:playerConnection*/.func_72474_a/*was:handleContainerClose*/(new net.minecraft.network.packet.Packet101CloseWindow/*was:Packet101CloseWindow*/(getHandle().field_71070_bA/*was:activeContainer*/.field_75152_c/*was:windowId*/));
        }
        net.minecraft.entity.player.EntityPlayerMP/*was:EntityPlayer*/ player = (net.minecraft.entity.player.EntityPlayerMP/*was:EntityPlayer*/) getHandle();
        net.minecraft.inventory.Container/*was:Container*/ container;
        if (inventory instanceof CraftInventoryView) {
            container = ((CraftInventoryView) inventory).getHandle();
        } else {
            container = new CraftContainer(inventory, player.nextContainerCounter());
        }

        // Trigger an INVENTORY_OPEN event
        container = CraftEventFactory.callInventoryOpenEvent(player, container);
        if (container == null) {
            return;
        }

        // Now open the window
        InventoryType type = inventory.getType();
        int windowType = CraftContainer.getNotchInventoryType(type);
        String title = inventory.getTitle();
        int size = inventory.getTopInventory().getSize();
        player.field_71135_a/*was:playerConnection*/.func_72567_b/*was:sendPacket*/(new net.minecraft.network.packet.Packet100OpenWindow/*was:Packet100OpenWindow*/(container.field_75152_c/*was:windowId*/, windowType, title, size));
        player.field_71070_bA/*was:activeContainer*/ = container;
        player.field_71070_bA/*was:activeContainer*/.func_75132_a/*was:addSlotListener*/(player);
    }

    public void closeInventory() {
        getHandle().func_71053_j/*was:closeInventory*/();
    }

    public boolean isBlocking() {
        return getHandle().func_70632_aY/*was:bh*/();
    }

    public boolean setWindowProperty(InventoryView.Property prop, int value) {
        return false;
    }

    public int getExpToLevel() {
        return getHandle().func_71050_bK/*was:getExpToLevel*/();
    }
}
