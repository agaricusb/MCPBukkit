package org.bukkit.craftbukkit.inventory;

import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;


public class CraftContainer extends net.minecraft.inventory.Container/*was:Container*/ {
    private final InventoryView view;
    private InventoryType cachedType;
    private String cachedTitle;
    private final int cachedSize;

    public CraftContainer(InventoryView view, int id) {
        this.view = view;
        this.field_75152_c/*was:windowId*/ = id;
        // TODO: Do we need to check that it really is a CraftInventory?
        net.minecraft.inventory.IInventory/*was:IInventory*/ top = ((CraftInventory)view.getTopInventory()).getInventory();
        net.minecraft.inventory.IInventory/*was:IInventory*/ bottom = ((CraftInventory)view.getBottomInventory()).getInventory();
        cachedType = view.getType();
        cachedTitle = view.getTitle();
        cachedSize = getSize();
        setupSlots(top, bottom);
    }

    public CraftContainer(final Inventory inventory, final HumanEntity player, int id) {
        this(new InventoryView() {
            @Override
            public Inventory getTopInventory() {
                return inventory;
            }

            @Override
            public Inventory getBottomInventory() {
                return player.getInventory();
            }

            @Override
            public HumanEntity getPlayer() {
                return player;
            }

            @Override
            public InventoryType getType() {
                return inventory.getType();
            }
        }, id);
    }

    @Override
    public InventoryView getBukkitView() {
        return view;
    }

    private int getSize() {
        return view.getTopInventory().getSize();
    }

    @Override
    public boolean func_75129_b/*was:c*/(net.minecraft.entity.player.EntityPlayer/*was:EntityHuman*/ entityhuman) {
        if (cachedType == view.getType() && cachedSize == getSize() && cachedTitle.equals(view.getTitle())) {
            return true;
        }
        // If the window type has changed for some reason, update the player
        // This method will be called every tick or something, so it's
        // as good a place as any to put something like this.
        boolean typeChanged = (cachedType != view.getType());
        cachedType = view.getType();
        cachedTitle = view.getTitle();
        if (view.getPlayer() instanceof CraftPlayer) {
            CraftPlayer player = (CraftPlayer) view.getPlayer();
            int type = getNotchInventoryType(cachedType);
            net.minecraft.inventory.IInventory/*was:IInventory*/ top = ((CraftInventory)view.getTopInventory()).getInventory();
            net.minecraft.inventory.IInventory/*was:IInventory*/ bottom = ((CraftInventory)view.getBottomInventory()).getInventory();
            this.field_75153_a/*was:b*/.clear();
            this.field_75151_b/*was:c*/.clear();
            if (typeChanged) {
                setupSlots(top, bottom);
            }
            int size = getSize();
            player.getHandle().field_71135_a/*was:playerConnection*/.func_72567_b/*was:sendPacket*/(new net.minecraft.network.packet.Packet100OpenWindow/*was:Packet100OpenWindow*/(this.field_75152_c/*was:windowId*/, type, cachedTitle, size));
            player.updateInventory();
        }
        return true;
    }

    public static int getNotchInventoryType(InventoryType type) {
        int typeID;
        switch(type) {
        case WORKBENCH:
            typeID = 1;
            break;
        case FURNACE:
            typeID = 2;
            break;
        case DISPENSER:
            typeID = 3;
            break;
        case ENCHANTING:
            typeID = 4;
            break;
        case BREWING:
            typeID = 5;
            break;
        case BEACON:
            typeID = 7;
            break;
        case ANVIL:
            typeID = 8;
            break;
        default:
            typeID = 0;
            break;
        }
        return typeID;
    }

    private void setupSlots(net.minecraft.inventory.IInventory/*was:IInventory*/ top, net.minecraft.inventory.IInventory/*was:IInventory*/ bottom) {
        switch(cachedType) {
        case CREATIVE:
            break; // TODO: This should be an error?
        case PLAYER:
        case CHEST:
            setupChest(top, bottom);
            break;
        case DISPENSER:
            setupDispenser(top, bottom);
            break;
        case FURNACE:
            setupFurnace(top, bottom);
            break;
        case CRAFTING: // TODO: This should be an error?
        case WORKBENCH:
            setupWorkbench(top, bottom);
            break;
        case ENCHANTING:
            setupEnchanting(top, bottom);
            break;
        case BREWING:
            setupBrewing(top, bottom);
            break;
        }
    }

    private void setupChest(net.minecraft.inventory.IInventory/*was:IInventory*/ top, net.minecraft.inventory.IInventory/*was:IInventory*/ bottom) {
        int rows = top.func_70302_i_/*was:getSize*/() / 9;
        int row;
        int col;
        // This code copied from ContainerChest
        int i = (rows - 4) * 18;
        for (row = 0; row < rows; ++row) {
            for (col = 0; col < 9; ++col) {
                this.func_75146_a/*was:a*/(new net.minecraft.inventory.Slot/*was:Slot*/(top, col + row * 9, 8 + col * 18, 18 + row * 18));
            }
        }

        for (row = 0; row < 3; ++row) {
            for (col = 0; col < 9; ++col) {
                this.func_75146_a/*was:a*/(new net.minecraft.inventory.Slot/*was:Slot*/(bottom, col + row * 9 + 9, 8 + col * 18, 103 + row * 18 + i));
            }
        }

        for (col = 0; col < 9; ++col) {
            this.func_75146_a/*was:a*/(new net.minecraft.inventory.Slot/*was:Slot*/(bottom, col, 8 + col * 18, 161 + i));
        }
        // End copy from ContainerChest
    }

    private void setupWorkbench(net.minecraft.inventory.IInventory/*was:IInventory*/ top, net.minecraft.inventory.IInventory/*was:IInventory*/ bottom) {
        // This code copied from ContainerWorkbench
        this.func_75146_a/*was:a*/(new net.minecraft.inventory.Slot/*was:Slot*/(top, 0, 124, 35));

        int row;
        int col;

        for (row = 0; row < 3; ++row) {
            for (col = 0; col < 3; ++col) {
                this.func_75146_a/*was:a*/(new net.minecraft.inventory.Slot/*was:Slot*/(top, 1 + col + row * 3, 30 + col * 18, 17 + row * 18));
            }
        }

        for (row = 0; row < 3; ++row) {
            for (col = 0; col < 9; ++col) {
                this.func_75146_a/*was:a*/(new net.minecraft.inventory.Slot/*was:Slot*/(bottom, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        for (col = 0; col < 9; ++col) {
            this.func_75146_a/*was:a*/(new net.minecraft.inventory.Slot/*was:Slot*/(bottom, col, 8 + col * 18, 142));
        }
        // End copy from ContainerWorkbench
    }

    private void setupFurnace(net.minecraft.inventory.IInventory/*was:IInventory*/ top, net.minecraft.inventory.IInventory/*was:IInventory*/ bottom) {
        // This code copied from ContainerFurnace
        this.func_75146_a/*was:a*/(new net.minecraft.inventory.Slot/*was:Slot*/(top, 0, 56, 17));
        this.func_75146_a/*was:a*/(new net.minecraft.inventory.Slot/*was:Slot*/(top, 1, 56, 53));
        this.func_75146_a/*was:a*/(new net.minecraft.inventory.Slot/*was:Slot*/(top, 2, 116, 35));

        int row;
        int col;

        for (row = 0; row < 3; ++row) {
            for (col = 0; col < 9; ++col) {
                this.func_75146_a/*was:a*/(new net.minecraft.inventory.Slot/*was:Slot*/(bottom, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        for (col = 0; col < 9; ++col) {
            this.func_75146_a/*was:a*/(new net.minecraft.inventory.Slot/*was:Slot*/(bottom, col, 8 + col * 18, 142));
        }
        // End copy from ContainerFurnace
    }

    private void setupDispenser(net.minecraft.inventory.IInventory/*was:IInventory*/ top, net.minecraft.inventory.IInventory/*was:IInventory*/ bottom) {
        // This code copied from ContainerDispenser
        int row;
        int col;

        for (row = 0; row < 3; ++row) {
            for (col = 0; col < 3; ++col) {
                this.func_75146_a/*was:a*/(new net.minecraft.inventory.Slot/*was:Slot*/(top, col + row * 3, 61 + col * 18, 17 + row * 18));
            }
        }

        for (row = 0; row < 3; ++row) {
            for (col = 0; col < 9; ++col) {
                this.func_75146_a/*was:a*/(new net.minecraft.inventory.Slot/*was:Slot*/(bottom, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        for (col = 0; col < 9; ++col) {
            this.func_75146_a/*was:a*/(new net.minecraft.inventory.Slot/*was:Slot*/(bottom, col, 8 + col * 18, 142));
        }
        // End copy from ContainerDispenser
    }

    private void setupEnchanting(net.minecraft.inventory.IInventory/*was:IInventory*/ top, net.minecraft.inventory.IInventory/*was:IInventory*/ bottom) {
        // This code copied from ContainerEnchantTable
        this.func_75146_a/*was:a*/((new net.minecraft.inventory.Slot/*was:Slot*/(top, 0, 25, 47)));

        int row;

        for (row = 0; row < 3; ++row) {
            for (int i1 = 0; i1 < 9; ++i1) {
                this.func_75146_a/*was:a*/(new net.minecraft.inventory.Slot/*was:Slot*/(bottom, i1 + row * 9 + 9, 8 + i1 * 18, 84 + row * 18));
            }
        }

        for (row = 0; row < 9; ++row) {
            this.func_75146_a/*was:a*/(new net.minecraft.inventory.Slot/*was:Slot*/(bottom, row, 8 + row * 18, 142));
        }
        // End copy from ContainerEnchantTable
    }

    private void setupBrewing(net.minecraft.inventory.IInventory/*was:IInventory*/ top, net.minecraft.inventory.IInventory/*was:IInventory*/ bottom) {
        // This code copied from ContainerBrewingStand
        this.func_75146_a/*was:a*/(new net.minecraft.inventory.Slot/*was:Slot*/(top, 0, 56, 46));
        this.func_75146_a/*was:a*/(new net.minecraft.inventory.Slot/*was:Slot*/(top, 1, 79, 53));
        this.func_75146_a/*was:a*/(new net.minecraft.inventory.Slot/*was:Slot*/(top, 2, 102, 46));
        this.func_75146_a/*was:a*/(new net.minecraft.inventory.Slot/*was:Slot*/(top, 3, 79, 17));

        int i;

        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.func_75146_a/*was:a*/(new net.minecraft.inventory.Slot/*was:Slot*/(bottom, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i) {
            this.func_75146_a/*was:a*/(new net.minecraft.inventory.Slot/*was:Slot*/(bottom, i, 8 + i * 18, 142));
        }
        // End copy from ContainerBrewingStand
    }

    public boolean func_75145_c/*was:a*/(net.minecraft.entity.player.EntityPlayer/*was:EntityHuman*/ entity) {
        return true;
    }
}
