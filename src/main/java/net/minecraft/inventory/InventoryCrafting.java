package net.minecraft.inventory;

// CraftBukkit start
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
// CraftBukkit end

public class InventoryCrafting implements IInventory
{
    private ItemStack[] field_70466_a;
    private int field_70464_b;
    private Container field_70465_c;

    // CraftBukkit start
    public List<HumanEntity> transaction = new java.util.ArrayList<HumanEntity>();
    public IRecipe currentRecipe;
    public IInventory resultInventory;
    private EntityPlayer owner;
    private int maxStack = MAX_STACK;

    public ItemStack[] getContents()
    {
        return this.field_70466_a;
    }

    public void onOpen(CraftHumanEntity who)
    {
        transaction.add(who);
    }

    public InventoryType getInvType()
    {
        return field_70466_a.length == 4 ? InventoryType.CRAFTING : InventoryType.WORKBENCH;
    }

    public void onClose(CraftHumanEntity who)
    {
        transaction.remove(who);
    }

    public List<HumanEntity> getViewers()
    {
        return transaction;
    }

    public org.bukkit.inventory.InventoryHolder getOwner()
    {
        return owner.getBukkitEntity();
    }

    public void setMaxStackSize(int size)
    {
        maxStack = size;
        resultInventory.setMaxStackSize(size);
    }

    public InventoryCrafting(Container container, int i, int j, EntityPlayer player)
    {
        this(container, i, j);
        this.owner = player;
    }
    // CraftBukkit end

    public InventoryCrafting(Container p_i3602_1_, int p_i3602_2_, int p_i3602_3_)
    {
        int k = p_i3602_2_ * p_i3602_3_;
        this.field_70466_a = new ItemStack[k];
        this.field_70465_c = p_i3602_1_;
        this.field_70464_b = p_i3602_2_;
    }

    public int func_70302_i_()
    {
        return this.field_70466_a.length;
    }

    public ItemStack func_70301_a(int p_70301_1_)
    {
        return p_70301_1_ >= this.func_70302_i_() ? null : this.field_70466_a[p_70301_1_];
    }

    public ItemStack func_70463_b(int p_70463_1_, int p_70463_2_)
    {
        if (p_70463_1_ >= 0 && p_70463_1_ < this.field_70464_b)
        {
            int k = p_70463_1_ + p_70463_2_ * this.field_70464_b;
            return this.func_70301_a(k);
        }
        else
        {
            return null;
        }
    }

    public String func_70303_b()
    {
        return "container.crafting";
    }

    public ItemStack func_70304_b(int p_70304_1_)
    {
        if (this.field_70466_a[p_70304_1_] != null)
        {
            ItemStack itemstack = this.field_70466_a[p_70304_1_];
            this.field_70466_a[p_70304_1_] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    public ItemStack func_70298_a(int p_70298_1_, int p_70298_2_)
    {
        if (this.field_70466_a[p_70298_1_] != null)
        {
            ItemStack itemstack;

            if (this.field_70466_a[p_70298_1_].field_77994_a <= p_70298_2_)
            {
                itemstack = this.field_70466_a[p_70298_1_];
                this.field_70466_a[p_70298_1_] = null;
                this.field_70465_c.func_75130_a((IInventory) this);
                return itemstack;
            }
            else
            {
                itemstack = this.field_70466_a[p_70298_1_].func_77979_a(p_70298_2_);

                if (this.field_70466_a[p_70298_1_].field_77994_a == 0)
                {
                    this.field_70466_a[p_70298_1_] = null;
                }

                this.field_70465_c.func_75130_a((IInventory) this);
                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }

    public void func_70299_a(int p_70299_1_, ItemStack p_70299_2_)
    {
        this.field_70466_a[p_70299_1_] = p_70299_2_;
        this.field_70465_c.func_75130_a((IInventory) this);
    }

    public int func_70297_j_()
    {
        return maxStack; // CraftBukkit
    }

    public void func_70296_d() {}

    public boolean func_70300_a(EntityPlayer p_70300_1_)
    {
        return true;
    }

    public void func_70295_k_() {}

    public void func_70305_f() {}
}
