package net.minecraft.inventory;

import java.util.Iterator;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

// CraftBukkit start
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.craftbukkit.inventory.CraftInventoryAnvil;
import org.bukkit.craftbukkit.inventory.CraftInventoryView;
// CraftBukkit end

public class ContainerRepair extends Container
{
    private IInventory field_82852_f = new InventoryCraftResult();
    private IInventory field_82853_g = new InventoryRepair(this, "Repair", 2);
    private World field_82860_h;
    private int field_82861_i;
    private int field_82858_j;
    private int field_82859_k;
    public int field_82854_e = 0;
    private int field_82856_l = 0;
    private String field_82857_m;
    private final EntityPlayer field_82855_n;
    // CraftBukkit start
    private CraftInventoryView bukkitEntity = null;
    private InventoryPlayer player;
    // CraftBukkit end

    public ContainerRepair(InventoryPlayer p_i5080_1_, World p_i5080_2_, int p_i5080_3_, int p_i5080_4_, int p_i5080_5_, EntityPlayer p_i5080_6_)
    {
        this.player = p_i5080_1_; // CraftBukkit
        this.field_82860_h = p_i5080_2_;
        this.field_82861_i = p_i5080_3_;
        this.field_82858_j = p_i5080_4_;
        this.field_82859_k = p_i5080_5_;
        this.field_82855_n = p_i5080_6_;
        this.func_75146_a(new Slot(this.field_82853_g, 0, 27, 47));
        this.func_75146_a(new Slot(this.field_82853_g, 1, 76, 47));
        this.func_75146_a((Slot)(new SlotRepair(this, this.field_82852_f, 2, 134, 47, p_i5080_2_, p_i5080_3_, p_i5080_4_, p_i5080_5_)));
        int l;

        for (l = 0; l < 3; ++l)
        {
            for (int i1 = 0; i1 < 9; ++i1)
            {
                this.func_75146_a(new Slot(p_i5080_1_, i1 + l * 9 + 9, 8 + i1 * 18, 84 + l * 18));
            }
        }

        for (l = 0; l < 9; ++l)
        {
            this.func_75146_a(new Slot(p_i5080_1_, l, 8 + l * 18, 142));
        }
    }

    public void func_75130_a(IInventory p_75130_1_)
    {
        super.func_75130_a(p_75130_1_);

        if (p_75130_1_ == this.field_82853_g)
        {
            this.func_82848_d();
        }
    }

    public void func_82848_d()
    {
        ItemStack itemstack = this.field_82853_g.func_70301_a(0);
        this.field_82854_e = 0;
        int i = 0;
        byte b0 = 0;
        int j = 0;

        if (itemstack == null)
        {
            this.field_82852_f.func_70299_a(0, (ItemStack)null);
            this.field_82854_e = 0;
        }
        else
        {
            ItemStack itemstack1 = itemstack.func_77946_l();
            ItemStack itemstack2 = this.field_82853_g.func_70301_a(1);
            Map map = EnchantmentHelper.func_82781_a(itemstack1);
            boolean flag = false;
            int k = b0 + itemstack.func_82838_A() + (itemstack2 == null ? 0 : itemstack2.func_82838_A());
            this.field_82856_l = 0;
            int l;
            int i1;
            int j1;
            int k1;
            int l1;
            Iterator iterator;
            Enchantment enchantment;

            if (itemstack2 != null)
            {
                flag = itemstack2.field_77993_c == Item.field_92105_bW.field_77779_bT && Item.field_92105_bW.func_92110_g(itemstack2).func_74745_c() > 0;

                if (itemstack1.func_77984_f() && Item.field_77698_e[itemstack1.field_77993_c].func_82789_a(itemstack, itemstack2))
                {
                    l = Math.min(itemstack1.func_77952_i(), itemstack1.func_77958_k() / 4);

                    if (l <= 0)
                    {
                        this.field_82852_f.func_70299_a(0, (ItemStack)null);
                        this.field_82854_e = 0;
                        return;
                    }

                    for (i1 = 0; l > 0 && i1 < itemstack2.field_77994_a; ++i1)
                    {
                        j1 = itemstack1.func_77952_i() - l;
                        itemstack1.func_77964_b(j1);
                        i += Math.max(1, l / 100) + map.size();
                        l = Math.min(itemstack1.func_77952_i(), itemstack1.func_77958_k() / 4);
                    }

                    this.field_82856_l = i1;
                }
                else
                {
                    if (!flag && (itemstack1.field_77993_c != itemstack2.field_77993_c || !itemstack1.func_77984_f()))
                    {
                        this.field_82852_f.func_70299_a(0, (ItemStack)null);
                        this.field_82854_e = 0;
                        return;
                    }

                    if (itemstack1.func_77984_f() && !flag)
                    {
                        l = itemstack.func_77958_k() - itemstack.func_77952_i();
                        i1 = itemstack2.func_77958_k() - itemstack2.func_77952_i();
                        j1 = i1 + itemstack1.func_77958_k() * 12 / 100;
                        int i2 = l + j1;
                        k1 = itemstack1.func_77958_k() - i2;

                        if (k1 < 0)
                        {
                            k1 = 0;
                        }

                        if (k1 < itemstack1.func_77960_j())
                        {
                            itemstack1.func_77964_b(k1);
                            i += Math.max(1, j1 / 100);
                        }
                    }

                    Map map1 = EnchantmentHelper.func_82781_a(itemstack2);
                    iterator = map1.keySet().iterator();

                    while (iterator.hasNext())
                    {
                        j1 = ((Integer)iterator.next()).intValue();
                        enchantment = Enchantment.field_77331_b[j1];
                        k1 = map.containsKey(Integer.valueOf(j1)) ? ((Integer)map.get(Integer.valueOf(j1))).intValue() : 0;
                        l1 = ((Integer)map1.get(Integer.valueOf(j1))).intValue();
                        int j2;

                        if (k1 == l1)
                        {
                            ++l1;
                            j2 = l1;
                        }
                        else
                        {
                            j2 = Math.max(l1, k1);
                        }

                        l1 = j2;
                        int k2 = l1 - k1;
                        boolean flag1 = enchantment.func_92089_a(itemstack);

                        if (this.field_82855_n.field_71075_bZ.field_75098_d)
                        {
                            flag1 = true;
                        }

                        Iterator iterator1 = map.keySet().iterator();

                        while (iterator1.hasNext())
                        {
                            int l2 = ((Integer)iterator1.next()).intValue();

                            if (l2 != j1 && !enchantment.func_77326_a(Enchantment.field_77331_b[l2]))
                            {
                                flag1 = false;
                                i += k2;
                            }
                        }

                        if (flag1)
                        {
                            if (l1 > enchantment.func_77325_b())
                            {
                                l1 = enchantment.func_77325_b();
                            }

                            map.put(Integer.valueOf(j1), Integer.valueOf(l1));
                            int i3 = 0;

                            switch (enchantment.func_77324_c())
                            {
                                case 1:
                                    i3 = 8;
                                    break;
                                case 2:
                                    i3 = 4;
                                case 3:
                                case 4:
                                case 6:
                                case 7:
                                case 8:
                                case 9:
                                default:
                                    break;
                                case 5:
                                    i3 = 2;
                                    break;
                                case 10:
                                    i3 = 1;
                            }

                            if (flag)
                            {
                                i3 = Math.max(1, i3 / 2);
                            }

                            i += i3 * k2;
                        }
                    }
                }
            }

            if (this.field_82857_m != null && !this.field_82857_m.equalsIgnoreCase(itemstack.func_82833_r()) && this.field_82857_m.length() > 0)
            {
                j = itemstack.func_77984_f() ? 7 : itemstack.field_77994_a * 5;
                i += j;

                if (itemstack.func_82837_s())
                {
                    k += j / 2;
                }

                itemstack1.func_82834_c(this.field_82857_m);
            }

            l = 0;

            for (iterator = map.keySet().iterator(); iterator.hasNext(); k += l + k1 * l1)
            {
                j1 = ((Integer)iterator.next()).intValue();
                enchantment = Enchantment.field_77331_b[j1];
                k1 = ((Integer)map.get(Integer.valueOf(j1))).intValue();
                l1 = 0;
                ++l;

                switch (enchantment.func_77324_c())
                {
                    case 1:
                        l1 = 8;
                        break;
                    case 2:
                        l1 = 4;
                    case 3:
                    case 4:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                    default:
                        break;
                    case 5:
                        l1 = 2;
                        break;
                    case 10:
                        l1 = 1;
                }

                if (flag)
                {
                    l1 = Math.max(1, l1 / 2);
                }
            }

            if (flag)
            {
                k = Math.max(1, k / 2);
            }

            this.field_82854_e = k + i;

            if (i <= 0)
            {
                itemstack1 = null;
            }

            if (j == i && j > 0 && this.field_82854_e >= 40)
            {
                //System.out.println("Naming an item only, cost too high; giving discount to cap cost to 39 levels"); // CraftBukkit -remove debug
                this.field_82854_e = 39;
            }

            if (this.field_82854_e >= 40 && !this.field_82855_n.field_71075_bZ.field_75098_d)
            {
                itemstack1 = null;
            }

            if (itemstack1 != null)
            {
                i1 = itemstack1.func_82838_A();

                if (itemstack2 != null && i1 < itemstack2.func_82838_A())
                {
                    i1 = itemstack2.func_82838_A();
                }

                if (itemstack1.func_82837_s())
                {
                    i1 -= 9;
                }

                if (i1 < 0)
                {
                    i1 = 0;
                }

                i1 += 2;
                itemstack1.func_82841_c(i1);
                EnchantmentHelper.func_82782_a(map, itemstack1);
            }

            this.field_82852_f.func_70299_a(0, itemstack1);
            this.func_75142_b();
        }
    }

    public void func_75132_a(ICrafting p_75132_1_)
    {
        super.func_75132_a(p_75132_1_);
        p_75132_1_.func_71112_a(this, 0, this.field_82854_e);
    }

    public void func_75134_a(EntityPlayer p_75134_1_)
    {
        super.func_75134_a(p_75134_1_);

        if (!this.field_82860_h.field_72995_K)
        {
            for (int i = 0; i < this.field_82853_g.func_70302_i_(); ++i)
            {
                ItemStack itemstack = this.field_82853_g.func_70304_b(i);

                if (itemstack != null)
                {
                    p_75134_1_.func_71021_b(itemstack);
                }
            }
        }
    }

    public boolean func_75145_c(EntityPlayer p_75145_1_)
    {
        if (!this.checkReachable)
        {
            return true;    // CraftBukkit
        }

        return this.field_82860_h.func_72798_a(this.field_82861_i, this.field_82858_j, this.field_82859_k) != Block.field_82510_ck.field_71990_ca ? false : p_75145_1_.func_70092_e((double)this.field_82861_i + 0.5D, (double)this.field_82858_j + 0.5D, (double)this.field_82859_k + 0.5D) <= 64.0D;
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
            else if (p_82846_2_ != 0 && p_82846_2_ != 1)
            {
                if (p_82846_2_ >= 3 && p_82846_2_ < 39 && !this.func_75135_a(itemstack1, 0, 2, false))
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

    public void func_82850_a(String p_82850_1_)
    {
        this.field_82857_m = p_82850_1_;

        if (this.func_75139_a(2).func_75216_d())
        {
            this.func_75139_a(2).func_75211_c().func_82834_c(this.field_82857_m);
        }

        this.func_82848_d();
    }

    static IInventory func_82851_a(ContainerRepair p_82851_0_)
    {
        return p_82851_0_.field_82853_g;
    }

    static int func_82849_b(ContainerRepair p_82849_0_)
    {
        return p_82849_0_.field_82856_l;
    }

    // CraftBukkit start
    public CraftInventoryView getBukkitView()
    {
        if (bukkitEntity != null)
        {
            return bukkitEntity;
        }

        CraftInventory inventory = new CraftInventoryAnvil(this.field_82853_g);
        bukkitEntity = new CraftInventoryView(this.player.field_70458_d.getBukkitEntity(), inventory, this);
        return bukkitEntity;
    }
    // CraftBukkit end
}
