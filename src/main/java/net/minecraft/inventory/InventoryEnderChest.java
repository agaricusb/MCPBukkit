package net.minecraft.inventory;

// CraftBukkit start
import java.util.List;
import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityEnderChest;
// CraftBukkit end

public class InventoryEnderChest extends InventoryBasic
{
    private TileEntityEnderChest field_70488_a;

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

    public int func_70297_j_()
    {
        return maxStack;
    }
    // CraftBukkit end

    public InventoryEnderChest()
    {
        super("container.enderchest", 27);
    }

    public void func_70485_a(TileEntityEnderChest p_70485_1_)
    {
        this.field_70488_a = p_70485_1_;
    }

    public void func_70486_a(NBTTagList p_70486_1_)
    {
        int i;

        for (i = 0; i < this.func_70302_i_(); ++i)
        {
            this.func_70299_a(i, (ItemStack)null);
        }

        for (i = 0; i < p_70486_1_.func_74745_c(); ++i)
        {
            NBTTagCompound nbttagcompound = (NBTTagCompound)p_70486_1_.func_74743_b(i);
            int j = nbttagcompound.func_74771_c("Slot") & 255;

            if (j >= 0 && j < this.func_70302_i_())
            {
                this.func_70299_a(j, ItemStack.func_77949_a(nbttagcompound));
            }
        }
    }

    public NBTTagList func_70487_g()
    {
        NBTTagList nbttaglist = new NBTTagList("EnderItems");

        for (int i = 0; i < this.func_70302_i_(); ++i)
        {
            ItemStack itemstack = this.func_70301_a(i);

            if (itemstack != null)
            {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.func_74774_a("Slot", (byte)i);
                itemstack.func_77955_b(nbttagcompound);
                nbttaglist.func_74742_a(nbttagcompound);
            }
        }

        return nbttaglist;
    }

    public boolean func_70300_a(EntityPlayer p_70300_1_)
    {
        return this.field_70488_a != null && !this.field_70488_a.func_70365_a(p_70300_1_) ? false : super.func_70300_a(p_70300_1_);
    }

    public void func_70295_k_()
    {
        if (this.field_70488_a != null)
        {
            this.field_70488_a.func_70364_a();
        }

        super.func_70295_k_();
    }

    public void func_70305_f()
    {
        if (this.field_70488_a != null)
        {
            this.field_70488_a.func_70366_b();
        }

        super.func_70305_f();
        this.field_70488_a = null;
    }
}
