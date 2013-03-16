package net.minecraft.inventory;

// CraftBukkit start
import org.bukkit.craftbukkit.inventory.CraftInventoryBrewer;
import org.bukkit.craftbukkit.inventory.CraftInventoryView;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityBrewingStand;
// CraftBukkit end

public class ContainerBrewingStand extends Container
{
    private TileEntityBrewingStand field_75188_e;
    private final Slot field_75186_f;
    private int field_75187_g = 0;
    // CraftBukkit start
    private CraftInventoryView bukkitEntity = null;
    private InventoryPlayer player;
    // CraftBukkit end

    public ContainerBrewingStand(InventoryPlayer p_i3600_1_, TileEntityBrewingStand p_i3600_2_)
    {
        player = p_i3600_1_; // CraftBukkit
        this.field_75188_e = p_i3600_2_;
        this.func_75146_a(new SlotBrewingStandPotion(p_i3600_1_.field_70458_d, p_i3600_2_, 0, 56, 46));
        this.func_75146_a(new SlotBrewingStandPotion(p_i3600_1_.field_70458_d, p_i3600_2_, 1, 79, 53));
        this.func_75146_a(new SlotBrewingStandPotion(p_i3600_1_.field_70458_d, p_i3600_2_, 2, 102, 46));
        this.field_75186_f = this.func_75146_a(new SlotBrewingStandIngredient(this, p_i3600_2_, 3, 79, 17));
        int i;

        for (i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.func_75146_a(new Slot(p_i3600_1_, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i)
        {
            this.func_75146_a(new Slot(p_i3600_1_, i, 8 + i * 18, 142));
        }
    }

    public void func_75132_a(ICrafting p_75132_1_)
    {
        super.func_75132_a(p_75132_1_);
        p_75132_1_.func_71112_a(this, 0, this.field_75188_e.func_70355_t_());
    }

    public void func_75142_b()
    {
        super.func_75142_b();

        for (int i = 0; i < this.field_75149_d.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.field_75149_d.get(i);

            if (this.field_75187_g != this.field_75188_e.func_70355_t_())
            {
                icrafting.func_71112_a(this, 0, this.field_75188_e.func_70355_t_());
            }
        }

        this.field_75187_g = this.field_75188_e.func_70355_t_();
    }

    public boolean func_75145_c(EntityPlayer p_75145_1_)
    {
        if (!this.checkReachable)
        {
            return true;    // CraftBukkit
        }

        return this.field_75188_e.func_70300_a(p_75145_1_);
    }

    public ItemStack func_82846_b(EntityPlayer p_82846_1_, int p_82846_2_)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.field_75151_b.get(p_82846_2_);

        if (slot != null && slot.func_75216_d())
        {
            ItemStack itemstack1 = slot.func_75211_c();
            itemstack = itemstack1.func_77946_l();

            if ((p_82846_2_ < 0 || p_82846_2_ > 2) && p_82846_2_ != 3)
            {
                if (!this.field_75186_f.func_75216_d() && this.field_75186_f.func_75214_a(itemstack1))
                {
                    if (!this.func_75135_a(itemstack1, 3, 4, false))
                    {
                        return null;
                    }
                }
                else if (SlotBrewingStandPotion.func_75243_a_(itemstack))
                {
                    if (!this.func_75135_a(itemstack1, 0, 3, false))
                    {
                        return null;
                    }
                }
                else if (p_82846_2_ >= 4 && p_82846_2_ < 31)
                {
                    if (!this.func_75135_a(itemstack1, 31, 40, false))
                    {
                        return null;
                    }
                }
                else if (p_82846_2_ >= 31 && p_82846_2_ < 40)
                {
                    if (!this.func_75135_a(itemstack1, 4, 31, false))
                    {
                        return null;
                    }
                }
                else if (!this.func_75135_a(itemstack1, 4, 40, false))
                {
                    return null;
                }
            }
            else
            {
                if (!this.func_75135_a(itemstack1, 4, 40, true))
                {
                    return null;
                }

                slot.func_75220_a(itemstack1, itemstack);
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

        CraftInventoryBrewer inventory = new CraftInventoryBrewer(this.field_75188_e);
        bukkitEntity = new CraftInventoryView(this.player.field_70458_d.getBukkitEntity(), inventory, this);
        return bukkitEntity;
    }
    // CraftBukkit end
}
