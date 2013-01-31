package net.minecraft.inventory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

// CraftBukkit start
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.inventory.InventoryView;
// CraftBukkit end

public abstract class Container
{
    public List field_75153_a = new ArrayList();
    public List field_75151_b = new ArrayList();
    public int field_75152_c = 0;
    private short field_75150_e = 0;
    protected List field_75149_d = new ArrayList();
    private Set field_75148_f = new HashSet();

    // CraftBukkit start
    public boolean checkReachable = true;
    public abstract InventoryView getBukkitView();
    public void transferTo(Container other, org.bukkit.craftbukkit.entity.CraftHumanEntity player)
    {
        InventoryView source = this.getBukkitView(), destination = other.getBukkitView();
        ((CraftInventory) source.getTopInventory()).getInventory().onClose(player);
        ((CraftInventory) source.getBottomInventory()).getInventory().onClose(player);
        ((CraftInventory) destination.getTopInventory()).getInventory().onOpen(player);
        ((CraftInventory) destination.getBottomInventory()).getInventory().onOpen(player);
    }
    // CraftBukkit end

    public Container() {}

    protected Slot func_75146_a(Slot p_75146_1_)
    {
        p_75146_1_.field_75222_d = this.field_75151_b.size();
        this.field_75151_b.add(p_75146_1_);
        this.field_75153_a.add(null);
        return p_75146_1_;
    }

    public void func_75132_a(ICrafting p_75132_1_)
    {
        if (this.field_75149_d.contains(p_75132_1_))
        {
            throw new IllegalArgumentException("Listener already listening");
        }
        else
        {
            this.field_75149_d.add(p_75132_1_);
            p_75132_1_.func_71110_a(this, this.func_75138_a());
            this.func_75142_b();
        }
    }

    public List func_75138_a()
    {
        ArrayList arraylist = new ArrayList();

        for (int i = 0; i < this.field_75151_b.size(); ++i)
        {
            arraylist.add(((Slot)this.field_75151_b.get(i)).func_75211_c());
        }

        return arraylist;
    }

    public void func_75142_b()
    {
        for (int i = 0; i < this.field_75151_b.size(); ++i)
        {
            ItemStack itemstack = ((Slot)this.field_75151_b.get(i)).func_75211_c();
            ItemStack itemstack1 = (ItemStack)this.field_75153_a.get(i);

            if (!ItemStack.func_77989_b(itemstack1, itemstack))
            {
                itemstack1 = itemstack == null ? null : itemstack.func_77946_l();
                this.field_75153_a.set(i, itemstack1);

                for (int j = 0; j < this.field_75149_d.size(); ++j)
                {
                    ((ICrafting)this.field_75149_d.get(j)).func_71111_a(this, i, itemstack1);
                }
            }
        }
    }

    public boolean func_75140_a(EntityPlayer p_75140_1_, int p_75140_2_)
    {
        return false;
    }

    public Slot func_75147_a(IInventory p_75147_1_, int p_75147_2_)
    {
        for (int j = 0; j < this.field_75151_b.size(); ++j)
        {
            Slot slot = (Slot)this.field_75151_b.get(j);

            if (slot.func_75217_a(p_75147_1_, p_75147_2_))
            {
                return slot;
            }
        }

        return null;
    }

    public Slot func_75139_a(int p_75139_1_)
    {
        return (Slot)this.field_75151_b.get(p_75139_1_);
    }

    public ItemStack func_82846_b(EntityPlayer p_82846_1_, int p_82846_2_)
    {
        Slot slot = (Slot)this.field_75151_b.get(p_82846_2_);
        return slot != null ? slot.func_75211_c() : null;
    }

    public ItemStack func_75144_a(int p_75144_1_, int p_75144_2_, int p_75144_3_, EntityPlayer p_75144_4_)
    {
        ItemStack itemstack = null;
        InventoryPlayer inventoryplayer = p_75144_4_.field_71071_by;
        Slot slot;
        ItemStack itemstack1;
        int l;
        ItemStack itemstack2;

        if ((p_75144_3_ == 0 || p_75144_3_ == 1) && (p_75144_2_ == 0 || p_75144_2_ == 1))
        {
            if (p_75144_1_ == -999)
            {
                if (inventoryplayer.func_70445_o() != null && p_75144_1_ == -999)
                {
                    if (p_75144_2_ == 0)
                    {
                        p_75144_4_.func_71021_b(inventoryplayer.func_70445_o());
                        inventoryplayer.func_70437_b((ItemStack)null);
                    }

                    if (p_75144_2_ == 1)
                    {
                        // CraftBukkit start - store a reference
                        ItemStack itemstack3 = inventoryplayer.func_70445_o();

                        if (itemstack3.field_77994_a > 0)
                        {
                            p_75144_4_.func_71021_b(itemstack3.func_77979_a(1));
                        }

                        if (itemstack3.field_77994_a == 0)
                        {
                            // CraftBukkit end
                            inventoryplayer.func_70437_b((ItemStack)null);
                        }
                    }
                }
            }
            else if (p_75144_3_ == 1)
            {
                slot = (Slot)this.field_75151_b.get(p_75144_1_);

                if (slot != null && slot.func_82869_a(p_75144_4_))
                {
                    itemstack1 = this.func_82846_b(p_75144_4_, p_75144_1_);

                    if (itemstack1 != null)
                    {
                        int i1 = itemstack1.field_77993_c;
                        itemstack = itemstack1.func_77946_l();

                        if (slot != null && slot.func_75211_c() != null && slot.func_75211_c().field_77993_c == i1)
                        {
                            this.func_75133_b(p_75144_1_, p_75144_2_, true, p_75144_4_);
                        }
                    }
                }
            }
            else
            {
                if (p_75144_1_ < 0)
                {
                    return null;
                }

                slot = (Slot)this.field_75151_b.get(p_75144_1_);

                if (slot != null)
                {
                    itemstack1 = slot.func_75211_c();
                    ItemStack itemstack3 = inventoryplayer.func_70445_o();

                    if (itemstack1 != null)
                    {
                        itemstack = itemstack1.func_77946_l();
                    }

                    if (itemstack1 == null)
                    {
                        if (itemstack3 != null && slot.func_75214_a(itemstack3))
                        {
                            l = p_75144_2_ == 0 ? itemstack3.field_77994_a : 1;

                            if (l > slot.func_75219_a())
                            {
                                l = slot.func_75219_a();
                            }

                            // CraftBukkit start
                            if (itemstack3.field_77994_a >= l)
                            {
                                slot.func_75215_d(itemstack3.func_77979_a(l));
                            }

                            // CraftBukkit end

                            if (itemstack3.field_77994_a == 0)
                            {
                                inventoryplayer.func_70437_b((ItemStack)null);
                            }
                        }
                    }
                    else if (slot.func_82869_a(p_75144_4_))
                    {
                        if (itemstack3 == null)
                        {
                            l = p_75144_2_ == 0 ? itemstack1.field_77994_a : (itemstack1.field_77994_a + 1) / 2;
                            itemstack2 = slot.func_75209_a(l);
                            inventoryplayer.func_70437_b(itemstack2);

                            if (itemstack1.field_77994_a == 0)
                            {
                                slot.func_75215_d((ItemStack)null);
                            }

                            slot.func_82870_a(p_75144_4_, inventoryplayer.func_70445_o());
                        }
                        else if (slot.func_75214_a(itemstack3))
                        {
                            if (itemstack1.field_77993_c == itemstack3.field_77993_c && itemstack1.func_77960_j() == itemstack3.func_77960_j() && ItemStack.func_77970_a(itemstack1, itemstack3))
                            {
                                l = p_75144_2_ == 0 ? itemstack3.field_77994_a : 1;

                                if (l > slot.func_75219_a() - itemstack1.field_77994_a)
                                {
                                    l = slot.func_75219_a() - itemstack1.field_77994_a;
                                }

                                if (l > itemstack3.func_77976_d() - itemstack1.field_77994_a)
                                {
                                    l = itemstack3.func_77976_d() - itemstack1.field_77994_a;
                                }

                                itemstack3.func_77979_a(l);

                                if (itemstack3.field_77994_a == 0)
                                {
                                    inventoryplayer.func_70437_b((ItemStack)null);
                                }

                                itemstack1.field_77994_a += l;
                            }
                            else if (itemstack3.field_77994_a <= slot.func_75219_a())
                            {
                                slot.func_75215_d(itemstack3);
                                inventoryplayer.func_70437_b(itemstack1);
                            }
                        }
                        else if (itemstack1.field_77993_c == itemstack3.field_77993_c && itemstack3.func_77976_d() > 1 && (!itemstack1.func_77981_g() || itemstack1.func_77960_j() == itemstack3.func_77960_j()) && ItemStack.func_77970_a(itemstack1, itemstack3))
                        {
                            l = itemstack1.field_77994_a;

                            if (l > 0 && l + itemstack3.field_77994_a <= itemstack3.func_77976_d())
                            {
                                itemstack3.field_77994_a += l;
                                itemstack1 = slot.func_75209_a(l);

                                if (itemstack1.field_77994_a == 0)
                                {
                                    slot.func_75215_d((ItemStack)null);
                                }

                                slot.func_82870_a(p_75144_4_, inventoryplayer.func_70445_o());
                            }
                        }
                    }

                    slot.func_75218_e();
                }
            }
        }
        else if (p_75144_3_ == 2 && p_75144_2_ >= 0 && p_75144_2_ < 9)
        {
            slot = (Slot)this.field_75151_b.get(p_75144_1_);

            if (slot.func_82869_a(p_75144_4_))
            {
                itemstack1 = inventoryplayer.func_70301_a(p_75144_2_);
                boolean flag = itemstack1 == null || slot.field_75224_c == inventoryplayer && slot.func_75214_a(itemstack1);
                l = -1;

                if (!flag)
                {
                    l = inventoryplayer.func_70447_i();
                    flag |= l > -1;
                }

                if (slot.func_75216_d() && flag)
                {
                    itemstack2 = slot.func_75211_c();
                    inventoryplayer.func_70299_a(p_75144_2_, itemstack2);

                    if ((slot.field_75224_c != inventoryplayer || !slot.func_75214_a(itemstack1)) && itemstack1 != null)
                    {
                        if (l > -1)
                        {
                            inventoryplayer.func_70441_a(itemstack1);
                            slot.func_75209_a(itemstack2.field_77994_a);
                            slot.func_75215_d((ItemStack)null);
                            slot.func_82870_a(p_75144_4_, itemstack2);
                        }
                    }
                    else
                    {
                        slot.func_75209_a(itemstack2.field_77994_a);
                        slot.func_75215_d(itemstack1);
                        slot.func_82870_a(p_75144_4_, itemstack2);
                    }
                }
                else if (!slot.func_75216_d() && itemstack1 != null && slot.func_75214_a(itemstack1))
                {
                    inventoryplayer.func_70299_a(p_75144_2_, (ItemStack)null);
                    slot.func_75215_d(itemstack1);
                }
            }
        }
        else if (p_75144_3_ == 3 && p_75144_4_.field_71075_bZ.field_75098_d && inventoryplayer.func_70445_o() == null && p_75144_1_ >= 0)
        {
            slot = (Slot)this.field_75151_b.get(p_75144_1_);

            if (slot != null && slot.func_75216_d())
            {
                itemstack1 = slot.func_75211_c().func_77946_l();
                itemstack1.field_77994_a = itemstack1.func_77976_d();
                inventoryplayer.func_70437_b(itemstack1);
            }
        }

        return itemstack;
    }

    protected void func_75133_b(int p_75133_1_, int p_75133_2_, boolean p_75133_3_, EntityPlayer p_75133_4_)
    {
        this.func_75144_a(p_75133_1_, p_75133_2_, 1, p_75133_4_);
    }

    public void func_75134_a(EntityPlayer p_75134_1_)
    {
        InventoryPlayer inventoryplayer = p_75134_1_.field_71071_by;

        if (inventoryplayer.func_70445_o() != null)
        {
            p_75134_1_.func_71021_b(inventoryplayer.func_70445_o());
            inventoryplayer.func_70437_b((ItemStack)null);
        }
    }

    public void func_75130_a(IInventory p_75130_1_)
    {
        this.func_75142_b();
    }

    public void func_75141_a(int p_75141_1_, ItemStack p_75141_2_)
    {
        this.func_75139_a(p_75141_1_).func_75215_d(p_75141_2_);
    }

    public boolean func_75129_b(EntityPlayer p_75129_1_)
    {
        return !this.field_75148_f.contains(p_75129_1_);
    }

    public void func_75128_a(EntityPlayer p_75128_1_, boolean p_75128_2_)
    {
        if (p_75128_2_)
        {
            this.field_75148_f.remove(p_75128_1_);
        }
        else
        {
            this.field_75148_f.add(p_75128_1_);
        }
    }

    public abstract boolean func_75145_c(EntityPlayer entityhuman);

    protected boolean func_75135_a(ItemStack p_75135_1_, int p_75135_2_, int p_75135_3_, boolean p_75135_4_)
    {
        boolean flag1 = false;
        int k = p_75135_2_;

        if (p_75135_4_)
        {
            k = p_75135_3_ - 1;
        }

        Slot slot;
        ItemStack itemstack1;

        if (p_75135_1_.func_77985_e())
        {
            while (p_75135_1_.field_77994_a > 0 && (!p_75135_4_ && k < p_75135_3_ || p_75135_4_ && k >= p_75135_2_))
            {
                slot = (Slot)this.field_75151_b.get(k);
                itemstack1 = slot.func_75211_c();

                if (itemstack1 != null && itemstack1.field_77993_c == p_75135_1_.field_77993_c && (!p_75135_1_.func_77981_g() || p_75135_1_.func_77960_j() == itemstack1.func_77960_j()) && ItemStack.func_77970_a(p_75135_1_, itemstack1))
                {
                    int l = itemstack1.field_77994_a + p_75135_1_.field_77994_a;

                    if (l <= p_75135_1_.func_77976_d())
                    {
                        p_75135_1_.field_77994_a = 0;
                        itemstack1.field_77994_a = l;
                        slot.func_75218_e();
                        flag1 = true;
                    }
                    else if (itemstack1.field_77994_a < p_75135_1_.func_77976_d())
                    {
                        p_75135_1_.field_77994_a -= p_75135_1_.func_77976_d() - itemstack1.field_77994_a;
                        itemstack1.field_77994_a = p_75135_1_.func_77976_d();
                        slot.func_75218_e();
                        flag1 = true;
                    }
                }

                if (p_75135_4_)
                {
                    --k;
                }
                else
                {
                    ++k;
                }
            }
        }

        if (p_75135_1_.field_77994_a > 0)
        {
            if (p_75135_4_)
            {
                k = p_75135_3_ - 1;
            }
            else
            {
                k = p_75135_2_;
            }

            while (!p_75135_4_ && k < p_75135_3_ || p_75135_4_ && k >= p_75135_2_)
            {
                slot = (Slot)this.field_75151_b.get(k);
                itemstack1 = slot.func_75211_c();

                if (itemstack1 == null)
                {
                    slot.func_75215_d(p_75135_1_.func_77946_l());
                    slot.func_75218_e();
                    p_75135_1_.field_77994_a = 0;
                    flag1 = true;
                    break;
                }

                if (p_75135_4_)
                {
                    --k;
                }
                else
                {
                    ++k;
                }
            }
        }

        return flag1;
    }
}
