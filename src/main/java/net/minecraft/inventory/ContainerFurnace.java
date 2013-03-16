package net.minecraft.inventory;

// CraftBukkit start
import org.bukkit.craftbukkit.inventory.CraftInventoryFurnace;
import org.bukkit.craftbukkit.inventory.CraftInventoryView;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;
// CraftBukkit end

public class ContainerFurnace extends Container
{
    private TileEntityFurnace field_75158_e;
    private int field_75156_f = 0;
    private int field_75157_g = 0;
    private int field_75159_h = 0;

    // CraftBukkit start
    private CraftInventoryView bukkitEntity = null;
    private InventoryPlayer player;

    public CraftInventoryView getBukkitView()
    {
        if (bukkitEntity != null)
        {
            return bukkitEntity;
        }

        CraftInventoryFurnace inventory = new CraftInventoryFurnace(this.field_75158_e);
        bukkitEntity = new CraftInventoryView(this.player.field_70458_d.getBukkitEntity(), inventory, this);
        return bukkitEntity;
    }
    // CraftBukkit end

    public ContainerFurnace(InventoryPlayer p_i3607_1_, TileEntityFurnace p_i3607_2_)
    {
        this.field_75158_e = p_i3607_2_;
        this.func_75146_a(new Slot(p_i3607_2_, 0, 56, 17));
        this.func_75146_a(new Slot(p_i3607_2_, 1, 56, 53));
        this.func_75146_a(new SlotFurnace(p_i3607_1_.field_70458_d, p_i3607_2_, 2, 116, 35));
        this.player = p_i3607_1_; // CraftBukkit - save player
        int i;

        for (i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.func_75146_a(new Slot(p_i3607_1_, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i)
        {
            this.func_75146_a(new Slot(p_i3607_1_, i, 8 + i * 18, 142));
        }
    }

    public void func_75132_a(ICrafting p_75132_1_)
    {
        super.func_75132_a(p_75132_1_);
        p_75132_1_.func_71112_a(this, 0, this.field_75158_e.field_70406_c);
        p_75132_1_.func_71112_a(this, 1, this.field_75158_e.field_70407_a);
        p_75132_1_.func_71112_a(this, 2, this.field_75158_e.field_70405_b);
    }

    public void func_75142_b()
    {
        super.func_75142_b();

        for (int i = 0; i < this.field_75149_d.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.field_75149_d.get(i);

            if (this.field_75156_f != this.field_75158_e.field_70406_c)
            {
                icrafting.func_71112_a(this, 0, this.field_75158_e.field_70406_c);
            }

            if (this.field_75157_g != this.field_75158_e.field_70407_a)
            {
                icrafting.func_71112_a(this, 1, this.field_75158_e.field_70407_a);
            }

            if (this.field_75159_h != this.field_75158_e.field_70405_b)
            {
                icrafting.func_71112_a(this, 2, this.field_75158_e.field_70405_b);
            }
        }

        this.field_75156_f = this.field_75158_e.field_70406_c;
        this.field_75157_g = this.field_75158_e.field_70407_a;
        this.field_75159_h = this.field_75158_e.field_70405_b;
    }

    public boolean func_75145_c(EntityPlayer p_75145_1_)
    {
        if (!this.checkReachable)
        {
            return true;    // CraftBukkit
        }

        return this.field_75158_e.func_70300_a(p_75145_1_);
    }

    public ItemStack func_82846_b(EntityPlayer p_82846_1_, int p_82846_2_)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.field_75151_b.get(p_82846_2_);

        if (slot != null && slot.func_75216_d())
        {
            ItemStack itemstack1 = slot.func_75211_c();
            itemstack = itemstack1.func_77946_l();

            if (p_82846_2_ == 2)
            {
                if (!this.func_75135_a(itemstack1, 3, 39, true))
                {
                    return null;
                }

                slot.func_75220_a(itemstack1, itemstack);
            }
            else if (p_82846_2_ != 1 && p_82846_2_ != 0)
            {
                if (FurnaceRecipes.func_77602_a().func_77603_b(itemstack1.func_77973_b().field_77779_bT) != null)
                {
                    if (!this.func_75135_a(itemstack1, 0, 1, false))
                    {
                        return null;
                    }
                }
                else if (TileEntityFurnace.func_70401_b(itemstack1))
                {
                    if (!this.func_75135_a(itemstack1, 1, 2, false))
                    {
                        return null;
                    }
                }
                else if (p_82846_2_ >= 3 && p_82846_2_ < 30)
                {
                    if (!this.func_75135_a(itemstack1, 30, 39, false))
                    {
                        return null;
                    }
                }
                else if (p_82846_2_ >= 30 && p_82846_2_ < 39 && !this.func_75135_a(itemstack1, 3, 30, false))
                {
                    return null;
                }
            }
            else if (!this.func_75135_a(itemstack1, 3, 39, false))
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
}
