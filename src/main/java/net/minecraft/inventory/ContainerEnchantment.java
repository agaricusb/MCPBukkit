package net.minecraft.inventory;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

// CraftBukkit start
import java.util.Map;

import org.bukkit.craftbukkit.inventory.CraftInventoryEnchanting;
import org.bukkit.craftbukkit.inventory.CraftInventoryView;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.entity.Player;
// CraftBukkit end

public class ContainerEnchantment extends Container
{
    // CraftBukkit - make type specific (changed from IInventory)
    public SlotEnchantmentTable field_75168_e = new SlotEnchantmentTable(this, "Enchant", true, 1);
    private World field_75172_h;
    private int field_75173_i;
    private int field_75170_j;
    private int field_75171_k;
    private Random field_75169_l = new Random();
    public long field_75166_f;
    public int[] field_75167_g = new int[3];
    // CraftBukkit start
    private CraftInventoryView bukkitEntity = null;
    private Player player;
    // CraftBukkit end

    public ContainerEnchantment(InventoryPlayer p_i3606_1_, World p_i3606_2_, int p_i3606_3_, int p_i3606_4_, int p_i3606_5_)
    {
        this.field_75172_h = p_i3606_2_;
        this.field_75173_i = p_i3606_3_;
        this.field_75170_j = p_i3606_4_;
        this.field_75171_k = p_i3606_5_;
        this.func_75146_a((Slot)(new SlotEnchantment(this, this.field_75168_e, 0, 25, 47)));
        int l;

        for (l = 0; l < 3; ++l)
        {
            for (int i1 = 0; i1 < 9; ++i1)
            {
                this.func_75146_a(new Slot(p_i3606_1_, i1 + l * 9 + 9, 8 + i1 * 18, 84 + l * 18));
            }
        }

        for (l = 0; l < 9; ++l)
        {
            this.func_75146_a(new Slot(p_i3606_1_, l, 8 + l * 18, 142));
        }

        // CraftBukkit start
        player = (Player) p_i3606_1_.field_70458_d.getBukkitEntity();
        field_75168_e.player = player;
        // CraftBukkit end
    }

    public void func_75132_a(ICrafting p_75132_1_)
    {
        super.func_75132_a(p_75132_1_);
        p_75132_1_.func_71112_a(this, 0, this.field_75167_g[0]);
        p_75132_1_.func_71112_a(this, 1, this.field_75167_g[1]);
        p_75132_1_.func_71112_a(this, 2, this.field_75167_g[2]);
    }

    public void func_75142_b()
    {
        super.func_75142_b();

        for (int i = 0; i < this.field_75149_d.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.field_75149_d.get(i);
            icrafting.func_71112_a(this, 0, this.field_75167_g[0]);
            icrafting.func_71112_a(this, 1, this.field_75167_g[1]);
            icrafting.func_71112_a(this, 2, this.field_75167_g[2]);
        }
    }

    public void func_75130_a(IInventory p_75130_1_)
    {
        if (p_75130_1_ == this.field_75168_e)
        {
            ItemStack itemstack = p_75130_1_.func_70301_a(0);
            int i;

            if (itemstack != null && itemstack.func_77956_u())
            {
                this.field_75166_f = this.field_75169_l.nextLong();

                if (!this.field_75172_h.field_72995_K)
                {
                    i = 0;
                    int j;

                    for (j = -1; j <= 1; ++j)
                    {
                        for (int k = -1; k <= 1; ++k)
                        {
                            if ((j != 0 || k != 0) && this.field_75172_h.func_72799_c(this.field_75173_i + k, this.field_75170_j, this.field_75171_k + j) && this.field_75172_h.func_72799_c(this.field_75173_i + k, this.field_75170_j + 1, this.field_75171_k + j))
                            {
                                if (this.field_75172_h.func_72798_a(this.field_75173_i + k * 2, this.field_75170_j, this.field_75171_k + j * 2) == Block.field_72093_an.field_71990_ca)
                                {
                                    ++i;
                                }

                                if (this.field_75172_h.func_72798_a(this.field_75173_i + k * 2, this.field_75170_j + 1, this.field_75171_k + j * 2) == Block.field_72093_an.field_71990_ca)
                                {
                                    ++i;
                                }

                                if (k != 0 && j != 0)
                                {
                                    if (this.field_75172_h.func_72798_a(this.field_75173_i + k * 2, this.field_75170_j, this.field_75171_k + j) == Block.field_72093_an.field_71990_ca)
                                    {
                                        ++i;
                                    }

                                    if (this.field_75172_h.func_72798_a(this.field_75173_i + k * 2, this.field_75170_j + 1, this.field_75171_k + j) == Block.field_72093_an.field_71990_ca)
                                    {
                                        ++i;
                                    }

                                    if (this.field_75172_h.func_72798_a(this.field_75173_i + k, this.field_75170_j, this.field_75171_k + j * 2) == Block.field_72093_an.field_71990_ca)
                                    {
                                        ++i;
                                    }

                                    if (this.field_75172_h.func_72798_a(this.field_75173_i + k, this.field_75170_j + 1, this.field_75171_k + j * 2) == Block.field_72093_an.field_71990_ca)
                                    {
                                        ++i;
                                    }
                                }
                            }
                        }
                    }

                    for (j = 0; j < 3; ++j)
                    {
                        this.field_75167_g[j] = EnchantmentHelper.func_77514_a(this.field_75169_l, j, i, itemstack);
                    }

                    // CraftBukkit start
                    CraftItemStack item = CraftItemStack.asCraftMirror(itemstack);
                    PrepareItemEnchantEvent event = new PrepareItemEnchantEvent(player, this.getBukkitView(), this.field_75172_h.getWorld().getBlockAt(this.field_75173_i, this.field_75170_j, this.field_75171_k), item, this.field_75167_g, i);
                    this.field_75172_h.getServer().getPluginManager().callEvent(event);

                    if (event.isCancelled())
                    {
                        for (i = 0; i < 3; ++i)
                        {
                            this.field_75167_g[i] = 0;
                        }

                        return;
                    }

                    // CraftBukkit end
                    this.func_75142_b();
                }
            }
            else
            {
                for (i = 0; i < 3; ++i)
                {
                    this.field_75167_g[i] = 0;
                }
            }
        }
    }

    public boolean func_75140_a(EntityPlayer p_75140_1_, int p_75140_2_)
    {
        ItemStack itemstack = this.field_75168_e.func_70301_a(0);

        if (this.field_75167_g[p_75140_2_] > 0 && itemstack != null && (p_75140_1_.field_71068_ca >= this.field_75167_g[p_75140_2_] || p_75140_1_.field_71075_bZ.field_75098_d))
        {
            if (!this.field_75172_h.field_72995_K)
            {
                List list = EnchantmentHelper.func_77513_b(this.field_75169_l, itemstack, this.field_75167_g[p_75140_2_]);
                boolean flag = itemstack.field_77993_c == Item.field_77760_aL.field_77779_bT;

                if (list != null)
                {
                    // CraftBukkit start
                    Map<org.bukkit.enchantments.Enchantment, Integer> enchants = new java.util.HashMap<org.bukkit.enchantments.Enchantment, Integer>();

                    for (Object obj : list)
                    {
                        EnchantmentData instance = (EnchantmentData) obj;
                        enchants.put(org.bukkit.enchantments.Enchantment.getById(instance.field_76302_b.field_77352_x), instance.field_76303_c);
                    }

                    CraftItemStack item = CraftItemStack.asCraftMirror(itemstack);
                    EnchantItemEvent event = new EnchantItemEvent((Player) p_75140_1_.getBukkitEntity(), this.getBukkitView(), this.field_75172_h.getWorld().getBlockAt(this.field_75173_i, this.field_75170_j, this.field_75171_k), item, this.field_75167_g[p_75140_2_], enchants, p_75140_2_);
                    this.field_75172_h.getServer().getPluginManager().callEvent(event);
                    int level = event.getExpLevelCost();

                    if (event.isCancelled() || (level > p_75140_1_.field_71068_ca && !p_75140_1_.field_71075_bZ.field_75098_d) || enchants.isEmpty())
                    {
                        return false;
                    }

                    boolean applied = !flag;

                    for (Map.Entry<org.bukkit.enchantments.Enchantment, Integer> entry : event.getEnchantsToAdd().entrySet())
                    {
                        try
                        {
                            if (flag)
                            {
                                int enchantId = entry.getKey().getId();

                                if (Enchantment.field_77331_b[enchantId] == null)
                                {
                                    continue;
                                }

                                EnchantmentData enchantment = new EnchantmentData(enchantId, entry.getValue());
                                Item.field_92105_bW.func_92115_a(itemstack, enchantment);
                                applied = true;
                                itemstack.field_77993_c = Item.field_92105_bW.field_77779_bT;
                                break;
                            }
                            else
                            {
                                item.addEnchantment(entry.getKey(), entry.getValue());
                            }
                        }
                        catch (IllegalArgumentException e)
                        {
                            /* Just swallow invalid enchantments */
                        }
                    }

                    // Only down level if we've applied the enchantments
                    if (applied)
                    {
                        p_75140_1_.func_82242_a(-level);
                    }

                    // CraftBukkit end
                    this.func_75130_a(this.field_75168_e);
                }
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    public void func_75134_a(EntityPlayer p_75134_1_)
    {
        super.func_75134_a(p_75134_1_);

        if (!this.field_75172_h.field_72995_K)
        {
            ItemStack itemstack = this.field_75168_e.func_70304_b(0);

            if (itemstack != null)
            {
                p_75134_1_.func_71021_b(itemstack);
            }
        }
    }

    public boolean func_75145_c(EntityPlayer p_75145_1_)
    {
        if (!this.checkReachable)
        {
            return true;    // CraftBukkit
        }

        return this.field_75172_h.func_72798_a(this.field_75173_i, this.field_75170_j, this.field_75171_k) != Block.field_72096_bE.field_71990_ca ? false : p_75145_1_.func_70092_e((double)this.field_75173_i + 0.5D, (double)this.field_75170_j + 0.5D, (double)this.field_75171_k + 0.5D) <= 64.0D;
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
                if (!this.func_75135_a(itemstack1, 1, 37, true))
                {
                    return null;
                }
            }
            else
            {
                if (((Slot)this.field_75151_b.get(0)).func_75216_d() || !((Slot)this.field_75151_b.get(0)).func_75214_a(itemstack1))
                {
                    return null;
                }

                if (itemstack1.func_77942_o() && itemstack1.field_77994_a == 1)
                {
                    ((Slot)this.field_75151_b.get(0)).func_75215_d(itemstack1.func_77946_l());
                    itemstack1.field_77994_a = 0;
                }
                else if (itemstack1.field_77994_a >= 1)
                {
                    ((Slot)this.field_75151_b.get(0)).func_75215_d(new ItemStack(itemstack1.field_77993_c, 1, itemstack1.func_77960_j()));
                    --itemstack1.field_77994_a;
                }
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

        CraftInventoryEnchanting inventory = new CraftInventoryEnchanting(this.field_75168_e);
        bukkitEntity = new CraftInventoryView(this.player, inventory, this);
        return bukkitEntity;
    }
    // CraftBukkit end
}
