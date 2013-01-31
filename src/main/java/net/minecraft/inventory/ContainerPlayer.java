package net.minecraft.inventory;

// CraftBukkit start
import org.bukkit.craftbukkit.inventory.CraftInventoryCrafting;
import org.bukkit.craftbukkit.inventory.CraftInventoryView;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.network.packet.Packet103SetSlot;
// CraftBukkit end

public class ContainerPlayer extends Container
{
    public InventoryCrafting field_75181_e = new InventoryCrafting(this, 2, 2);
    public IInventory field_75179_f = new InventoryCraftResult();
    public boolean field_75180_g = false;
    private final EntityPlayer field_82862_h;
    // CraftBukkit start
    private CraftInventoryView bukkitEntity = null;
    private InventoryPlayer player;
    // CraftBukkit end

    public ContainerPlayer(InventoryPlayer p_i5077_1_, boolean p_i5077_2_, EntityPlayer p_i5077_3_)
    {
        this.field_75180_g = p_i5077_2_;
        this.field_82862_h = p_i5077_3_;
        this.field_75179_f = new InventoryCraftResult(); // CraftBukkit - moved to before InventoryCrafting construction
        this.field_75181_e = new InventoryCrafting(this, 2, 2, p_i5077_1_.field_70458_d); // CraftBukkit - pass player
        this.field_75181_e.resultInventory = this.field_75179_f; // CraftBukkit - let InventoryCrafting know about its result slot
        this.player = p_i5077_1_; // CraftBukkit - save player
        this.func_75146_a((Slot)(new SlotCrafting(p_i5077_1_.field_70458_d, this.field_75181_e, this.field_75179_f, 0, 144, 36)));
        int i;
        int j;

        for (i = 0; i < 2; ++i)
        {
            for (j = 0; j < 2; ++j)
            {
                this.func_75146_a(new Slot(this.field_75181_e, j + i * 2, 88 + j * 18, 26 + i * 18));
            }
        }

        for (i = 0; i < 4; ++i)
        {
            this.func_75146_a((Slot)(new SlotArmor(this, p_i5077_1_, p_i5077_1_.func_70302_i_() - 1 - i, 8, 8 + i * 18, i)));
        }

        for (i = 0; i < 3; ++i)
        {
            for (j = 0; j < 9; ++j)
            {
                this.func_75146_a(new Slot(p_i5077_1_, j + (i + 1) * 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i)
        {
            this.func_75146_a(new Slot(p_i5077_1_, i, 8 + i * 18, 142));
        }

        // this.a((IInventory) this.craftInventory); // CraftBukkit - unneeded since it just sets result slot to empty
    }

    public void func_75130_a(IInventory p_75130_1_)
    {
        // CraftBukkit start (Note: the following line would cause an error if called during construction)
        CraftingManager.func_77594_a().lastCraftView = getBukkitView();
        ItemStack craftResult = CraftingManager.func_77594_a().func_82787_a(this.field_75181_e, this.field_82862_h.field_70170_p);
        this.field_75179_f.func_70299_a(0, craftResult);

        if (super.field_75149_d.size() < 1)
        {
            return;
        }

        EntityPlayerMP player = (EntityPlayerMP) super.field_75149_d.get(0); // TODO: Is this _always_ correct? Seems like it.
        player.field_71135_a.func_72567_b(new Packet103SetSlot(player.field_71070_bA.field_75152_c, 0, craftResult));
        // CraftBukkit end
    }

    public void func_75134_a(EntityPlayer p_75134_1_)
    {
        super.func_75134_a(p_75134_1_);

        for (int i = 0; i < 4; ++i)
        {
            ItemStack itemstack = this.field_75181_e.func_70304_b(i);

            if (itemstack != null)
            {
                p_75134_1_.func_71021_b(itemstack);
            }
        }

        this.field_75179_f.func_70299_a(0, (ItemStack)null);
    }

    public boolean func_75145_c(EntityPlayer p_75145_1_)
    {
        return true;
    }

    public ItemStack func_82846_b(EntityPlayer p_82846_1_, int p_82846_2_)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.field_75151_b.get(p_82846_2_);

        if (slot != null && slot.func_75216_d())
        {
            ItemStack itemstack1 = slot.func_75211_c();
            itemstack = itemstack1.func_77946_l();

            if (p_82846_2_ == 0)
            {
                if (!this.func_75135_a(itemstack1, 9, 45, true))
                {
                    return null;
                }

                slot.func_75220_a(itemstack1, itemstack);
            }
            else if (p_82846_2_ >= 1 && p_82846_2_ < 5)
            {
                if (!this.func_75135_a(itemstack1, 9, 45, false))
                {
                    return null;
                }
            }
            else if (p_82846_2_ >= 5 && p_82846_2_ < 9)
            {
                if (!this.func_75135_a(itemstack1, 9, 45, false))
                {
                    return null;
                }
            }
            else if (itemstack.func_77973_b() instanceof ItemArmor && !((Slot)this.field_75151_b.get(5 + ((ItemArmor)itemstack.func_77973_b()).field_77881_a)).func_75216_d())
            {
                int j = 5 + ((ItemArmor)itemstack.func_77973_b()).field_77881_a;

                if (!this.func_75135_a(itemstack1, j, j + 1, false))
                {
                    return null;
                }
            }
            else if (p_82846_2_ >= 9 && p_82846_2_ < 36)
            {
                if (!this.func_75135_a(itemstack1, 36, 45, false))
                {
                    return null;
                }
            }
            else if (p_82846_2_ >= 36 && p_82846_2_ < 45)
            {
                if (!this.func_75135_a(itemstack1, 9, 36, false))
                {
                    return null;
                }
            }
            else if (!this.func_75135_a(itemstack1, 9, 45, false))
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

            if (itemstack1.field_77994_a == itemstack.field_77994_a)
            {
                return null;
            }

            slot.func_82870_a(p_82846_1_, itemstack1);
        }

        return itemstack;
    }

    // CraftBukkit start
    public CraftInventoryView getBukkitView()
    {
        if (bukkitEntity != null)
        {
            return bukkitEntity;
        }

        CraftInventoryCrafting inventory = new CraftInventoryCrafting(this.field_75181_e, this.field_75179_f);
        bukkitEntity = new CraftInventoryView(this.player.field_70458_d.getBukkitEntity(), inventory, this);
        return bukkitEntity;
    }
    // CraftBukkit end
}
