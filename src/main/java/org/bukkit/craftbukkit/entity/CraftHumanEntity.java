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

    public CraftHumanEntity(final CraftServer server, final net.minecraft.entity.player.EntityPlayer entity) {
        super(server, entity);
        mode = server.getDefaultGameMode();
        this.inventory = new CraftInventoryPlayer(entity.field_71071_by);
        enderChest = new CraftInventory(entity.func_71005_bN());
    }

    public String getName() {
        return getHandle().field_71092_bJ;
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
        return CraftItemStack.asCraftMirror(getHandle().field_71071_by.func_70445_o());
    }

    public void setItemOnCursor(ItemStack item) {
        net.minecraft.item.ItemStack stack = CraftItemStack.asNMSCopy(item);
        getHandle().field_71071_by.func_70437_b(stack);
        if (this instanceof CraftPlayer) {
            ((net.minecraft.entity.player.EntityPlayerMP) getHandle()).func_71113_k(); // Send set slot for cursor
        }
    }

    public boolean isSleeping() {
        return getHandle().field_71083_bS;
    }

    public int getSleepTicks() {
        return getHandle().field_71076_b;
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
    public net.minecraft.entity.player.EntityPlayer getHandle() {
        return (net.minecraft.entity.player.EntityPlayer) entity;
    }

    public void setHandle(final net.minecraft.entity.player.EntityPlayer entity) {
        super.setHandle(entity);
        this.inventory = new CraftInventoryPlayer(entity.field_71071_by);
    }

    @Override
    public String toString() {
        return "CraftHumanEntity{" + "id=" + getEntityId() + "name=" + getName() + '}';
    }

    public InventoryView getOpenInventory() {
        return getHandle().field_71070_bA.getBukkitView();
    }

    public InventoryView openInventory(Inventory inventory) {
        if(!(getHandle() instanceof net.minecraft.entity.player.EntityPlayerMP)) return null;
        net.minecraft.entity.player.EntityPlayerMP player = (net.minecraft.entity.player.EntityPlayerMP) getHandle();
        InventoryType type = inventory.getType();
        net.minecraft.inventory.Container formerContainer = getHandle().field_71070_bA;
        // TODO: Should we check that it really IS a CraftInventory first?
        CraftInventory craftinv = (CraftInventory) inventory;
        switch(type) {
        case PLAYER:
        case CHEST:
        case ENDER_CHEST:
            getHandle().func_71007_a(craftinv.getInventory());
            break;
        case DISPENSER:
            if (craftinv.getInventory() instanceof net.minecraft.tileentity.TileEntityDispenser) {
                getHandle().func_71006_a((net.minecraft.tileentity.TileEntityDispenser)craftinv.getInventory());
            } else {
                openCustomInventory(inventory, player, 3);
            }
            break;
        case FURNACE:
            if (craftinv.getInventory() instanceof net.minecraft.tileentity.TileEntityFurnace) {
                getHandle().func_71042_a((net.minecraft.tileentity.TileEntityFurnace)craftinv.getInventory());
            } else {
                openCustomInventory(inventory, player, 2);
            }
            break;
        case WORKBENCH:
            openCustomInventory(inventory, player, 1);
            break;
        case BREWING:
            if (craftinv.getInventory() instanceof net.minecraft.tileentity.TileEntityBrewingStand) {
                getHandle().func_71017_a((net.minecraft.tileentity.TileEntityBrewingStand)craftinv.getInventory());
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
        if (getHandle().field_71070_bA == formerContainer) {
            return null;
        }
        getHandle().field_71070_bA.checkReachable = false;
        return getHandle().field_71070_bA.getBukkitView();
    }

    private void openCustomInventory(Inventory inventory, net.minecraft.entity.player.EntityPlayerMP player, int windowType) {
        if (player.field_71135_a == null) return;
        net.minecraft.inventory.Container container = new CraftContainer(inventory, this, player.nextContainerCounter());

        container = CraftEventFactory.callInventoryOpenEvent(player, container);
        if(container == null) return;

        String title = container.getBukkitView().getTitle();
        int size = container.getBukkitView().getTopInventory().getSize();

        player.field_71135_a.func_72567_b(new net.minecraft.network.packet.Packet100OpenWindow(container.field_75152_c, windowType, title, size, true));
        getHandle().field_71070_bA = container;
        getHandle().field_71070_bA.func_75132_a(player);
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
        getHandle().func_71058_b(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        if (force) {
            getHandle().field_71070_bA.checkReachable = false;
        }
        return getHandle().field_71070_bA.getBukkitView();
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
        getHandle().func_71002_c(location.getBlockX(), location.getBlockY(), location.getBlockZ(), null);
        if (force) {
            getHandle().field_71070_bA.checkReachable = false;
        }
        return getHandle().field_71070_bA.getBukkitView();
    }

    public void openInventory(InventoryView inventory) {
        if (!(getHandle() instanceof net.minecraft.entity.player.EntityPlayerMP)) return; // TODO: NPC support?
        if (((net.minecraft.entity.player.EntityPlayerMP) getHandle()).field_71135_a == null) return;
        if (getHandle().field_71070_bA != getHandle().field_71069_bz) {
            // fire INVENTORY_CLOSE if one already open
            ((net.minecraft.entity.player.EntityPlayerMP)getHandle()).field_71135_a.func_72474_a(new net.minecraft.network.packet.Packet101CloseWindow(getHandle().field_71070_bA.field_75152_c));
        }
        net.minecraft.entity.player.EntityPlayerMP player = (net.minecraft.entity.player.EntityPlayerMP) getHandle();
        net.minecraft.inventory.Container container;
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
        player.field_71135_a.func_72567_b(new net.minecraft.network.packet.Packet100OpenWindow(container.field_75152_c, windowType, title, size, false));
        player.field_71070_bA = container;
        player.field_71070_bA.func_75132_a(player);
    }

    public void closeInventory() {
        getHandle().func_71053_j();
    }

    public boolean isBlocking() {
        return getHandle().func_70632_aY(); // Should be isBlocking
    }

    public boolean setWindowProperty(InventoryView.Property prop, int value) {
        return false;
    }

    public int getExpToLevel() {
        return getHandle().func_71050_bK();
    }
}
