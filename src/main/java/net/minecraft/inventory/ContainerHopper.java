package net.minecraft.inventory;

// CraftBukkit start
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.craftbukkit.inventory.CraftInventoryView;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
// CraftBukkit end

public class ContainerHopper extends Container
{
    private final IInventory field_94538_a;

    // CraftBukkit start
    private CraftInventoryView bukkitEntity = null;
    private InventoryPlayer player;

    public CraftInventoryView getBukkitView()
    {
        if (bukkitEntity != null)
        {
            return bukkitEntity;
        }

        CraftInventory inventory = new CraftInventory(this.field_94538_a); // Should be this.hopper
        bukkitEntity = new CraftInventoryView(this.player.field_70458_d.getBukkitEntity(), inventory, this);
        return bukkitEntity;
    }
    // CraftBukkit end

    public ContainerHopper(InventoryPlayer p_i10056_1_, IInventory p_i10056_2_)
    {
        this.field_94538_a = p_i10056_2_;
        this.player = p_i10056_1_; // CraftBukkit - save player
        p_i10056_2_.func_70295_k_();
        byte b0 = 51;
        int i;

        for (i = 0; i < p_i10056_2_.func_70302_i_(); ++i)
        {
            this.func_75146_a(new Slot(p_i10056_2_, i, 44 + i * 18, 20));
        }

        for (i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.func_75146_a(new Slot(p_i10056_1_, j + i * 9 + 9, 8 + j * 18, i * 18 + b0));
            }
        }

        for (i = 0; i < 9; ++i)
        {
            this.func_75146_a(new Slot(p_i10056_1_, i, 8 + i * 18, 58 + b0));
        }
    }

    public boolean func_75145_c(EntityPlayer p_75145_1_)
    {
        if (!this.checkReachable)
        {
            return true;    // CraftBukkit
        }

        return this.field_94538_a.func_70300_a(p_75145_1_);
    }

    public ItemStack func_82846_b(EntityPlayer p_82846_1_, int p_82846_2_)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.field_75151_b.get(p_82846_2_);

        if (slot != null && slot.func_75216_d())
        {
            ItemStack itemstack1 = slot.func_75211_c();
            itemstack = itemstack1.func_77946_l();

            if (p_82846_2_ < this.field_94538_a.func_70302_i_())
            {
                if (!this.func_75135_a(itemstack1, this.field_94538_a.func_70302_i_(), this.field_75151_b.size(), true))
                {
                    return null;
                }
            }
            else if (!this.func_75135_a(itemstack1, 0, this.field_94538_a.func_70302_i_(), false))
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
        this.field_94538_a.func_70305_f();
    }
}
