package net.minecraft.inventory;

// CraftBukkit start
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.craftbukkit.inventory.CraftInventoryView;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
// CraftBukkit end

public class ContainerChest extends Container
{
    public IInventory field_75155_e; // CraftBukkit - private->public
    private int field_75154_f;
    // CraftBukkit start
    private CraftInventoryView bukkitEntity = null;
    private InventoryPlayer player;

    public CraftInventoryView getBukkitView()
    {
        if (bukkitEntity != null)
        {
            return bukkitEntity;
        }

        CraftInventory inventory;

        if (this.field_75155_e instanceof InventoryPlayer)
        {
            inventory = new org.bukkit.craftbukkit.inventory.CraftInventoryPlayer((InventoryPlayer) this.field_75155_e);
        }
        else if (this.field_75155_e instanceof InventoryLargeChest)
        {
            inventory = new org.bukkit.craftbukkit.inventory.CraftInventoryDoubleChest((InventoryLargeChest) this.field_75155_e);
        }
        else
        {
            inventory = new CraftInventory(this.field_75155_e);
        }

        bukkitEntity = new CraftInventoryView(this.player.field_70458_d.getBukkitEntity(), inventory, this);
        return bukkitEntity;
    }
    // CraftBukkit end

    public ContainerChest(IInventory p_i3601_1_, IInventory p_i3601_2_)
    {
        this.field_75155_e = p_i3601_2_;
        this.field_75154_f = p_i3601_2_.func_70302_i_() / 9;
        p_i3601_2_.func_70295_k_();
        int i = (this.field_75154_f - 4) * 18;
        // CraftBukkit start - save player
        // TODO: Should we check to make sure it really is an InventoryPlayer?
        this.player = (InventoryPlayer)p_i3601_1_;
        // CraftBukkit end
        int j;
        int k;

        for (j = 0; j < this.field_75154_f; ++j)
        {
            for (k = 0; k < 9; ++k)
            {
                this.func_75146_a(new Slot(p_i3601_2_, k + j * 9, 8 + k * 18, 18 + j * 18));
            }
        }

        for (j = 0; j < 3; ++j)
        {
            for (k = 0; k < 9; ++k)
            {
                this.func_75146_a(new Slot(p_i3601_1_, k + j * 9 + 9, 8 + k * 18, 103 + j * 18 + i));
            }
        }

        for (j = 0; j < 9; ++j)
        {
            this.func_75146_a(new Slot(p_i3601_1_, j, 8 + j * 18, 161 + i));
        }
    }

    public boolean func_75145_c(EntityPlayer p_75145_1_)
    {
        if (!this.checkReachable)
        {
            return true;    // CraftBukkit
        }

        return this.field_75155_e.func_70300_a(p_75145_1_);
    }

    public ItemStack func_82846_b(EntityPlayer p_82846_1_, int p_82846_2_)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.field_75151_b.get(p_82846_2_);

        if (slot != null && slot.func_75216_d())
        {
            ItemStack itemstack1 = slot.func_75211_c();
            itemstack = itemstack1.func_77946_l();

            if (p_82846_2_ < this.field_75154_f * 9)
            {
                if (!this.func_75135_a(itemstack1, this.field_75154_f * 9, this.field_75151_b.size(), true))
                {
                    return null;
                }
            }
            else if (!this.func_75135_a(itemstack1, 0, this.field_75154_f * 9, false))
            {
                return null;
            }

            if (itemstack1.field_77994_a == 0)
            {
                slot.func_75215_d((ItemStack)null);
            }
            else
            {
                slot.func_75218_e();
            }
        }

        return itemstack;
    }

    public void func_75134_a(EntityPlayer p_75134_1_)
    {
        super.func_75134_a(p_75134_1_);
        this.field_75155_e.func_70305_f();
    }

    public IInventory func_85151_d()
    {
        return this.field_75155_e;
    }
}
