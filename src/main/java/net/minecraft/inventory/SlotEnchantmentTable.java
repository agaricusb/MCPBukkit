package net.minecraft.inventory;

// CraftBukkit start
import java.util.List;
import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import net.minecraft.item.ItemStack;
// CraftBukkit end

public class SlotEnchantmentTable extends InventoryBasic   // CraftBukkit -> public
{
    private final ContainerEnchantment field_70484_a;

    // CraftBukkit start
    public List<HumanEntity> transaction = new java.util.ArrayList<HumanEntity>();
    public org.bukkit.entity.Player player;
    private int maxStack = MAX_STACK;

    public ItemStack[] getContents()
    {
        return this.field_70482_c;
    }

    public void onOpen(CraftHumanEntity who)
    {
        transaction.add(who);
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
        return this.player;
    }

    public void setMaxStackSize(int size)
    {
        maxStack = size;
    }
    // CraftBukkit end

    SlotEnchantmentTable(ContainerEnchantment p_i3604_1_, String p_i3604_2_, int p_i3604_3_)
    {
        super(p_i3604_2_, p_i3604_3_);
        this.field_70484_a = p_i3604_1_;
        this.setMaxStackSize(1); // CraftBukkit
    }

    public int func_70297_j_()
    {
        return maxStack; // CraftBukkit
    }

    public void func_70296_d()
    {
        super.func_70296_d();
        this.field_70484_a.func_75130_a((IInventory) this);
    }
}
