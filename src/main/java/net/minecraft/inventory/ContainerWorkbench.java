package net.minecraft.inventory;

// CraftBukkit start
import org.bukkit.craftbukkit.inventory.CraftInventoryCrafting;
import org.bukkit.craftbukkit.inventory.CraftInventoryView;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.network.packet.Packet103SetSlot;
import net.minecraft.world.World;
// CraftBukkit end

public class ContainerWorkbench extends Container
{
    public InventoryCrafting field_75162_e; // CraftBukkit - move initialization into constructor
    public IInventory field_75160_f; // CraftBukkit - move initialization into constructor
    private World field_75161_g;
    private int field_75164_h;
    private int field_75165_i;
    private int field_75163_j;
    // CraftBukkit start
    private CraftInventoryView bukkitEntity = null;
    private InventoryPlayer player;
    // CraftBukkit end

    public ContainerWorkbench(InventoryPlayer p_i3603_1_, World p_i3603_2_, int p_i3603_3_, int p_i3603_4_, int p_i3603_5_)
    {
        // CraftBukkit start - switched order of IInventory construction and stored player
        this.field_75160_f = new InventoryCraftResult();
        this.field_75162_e = new InventoryCrafting(this, 3, 3, p_i3603_1_.field_70458_d); // CraftBukkit - pass player
        this.field_75162_e.resultInventory = this.field_75160_f;
        this.player = p_i3603_1_;
        // CraftBukkit end
        this.field_75161_g = p_i3603_2_;
        this.field_75164_h = p_i3603_3_;
        this.field_75165_i = p_i3603_4_;
        this.field_75163_j = p_i3603_5_;
        this.func_75146_a((Slot)(new SlotCrafting(p_i3603_1_.field_70458_d, this.field_75162_e, this.field_75160_f, 0, 124, 35)));
        int l;
        int i1;

        for (l = 0; l < 3; ++l)
        {
            for (i1 = 0; i1 < 3; ++i1)
            {
                this.func_75146_a(new Slot(this.field_75162_e, i1 + l * 3, 30 + i1 * 18, 17 + l * 18));
            }
        }

        for (l = 0; l < 3; ++l)
        {
            for (i1 = 0; i1 < 9; ++i1)
            {
                this.func_75146_a(new Slot(p_i3603_1_, i1 + l * 9 + 9, 8 + i1 * 18, 84 + l * 18));
            }
        }

        for (l = 0; l < 9; ++l)
        {
            this.func_75146_a(new Slot(p_i3603_1_, l, 8 + l * 18, 142));
        }

        this.func_75130_a((IInventory) this.field_75162_e);
    }

    public void func_75130_a(IInventory p_75130_1_)
    {
        // CraftBukkit start
        CraftingManager.func_77594_a().lastCraftView = getBukkitView();
        ItemStack craftResult = CraftingManager.func_77594_a().func_82787_a(this.field_75162_e, this.field_75161_g);
        this.field_75160_f.func_70299_a(0, craftResult);

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

        if (!this.field_75161_g.field_72995_K)
        {
            for (int i = 0; i < 9; ++i)
            {
                ItemStack itemstack = this.field_75162_e.func_70304_b(i);

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

        return this.field_75161_g.func_72798_a(this.field_75164_h, this.field_75165_i, this.field_75163_j) != Block.field_72060_ay.field_71990_ca ? false : p_75145_1_.func_70092_e((double)this.field_75164_h + 0.5D, (double)this.field_75165_i + 0.5D, (double)this.field_75163_j + 0.5D) <= 64.0D;
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
                if (!this.func_75135_a(itemstack1, 10, 46, true))
                {
                    return null;
                }

                slot.func_75220_a(itemstack1, itemstack);
            }
            else if (p_82846_2_ >= 10 && p_82846_2_ < 37)
            {
                if (!this.func_75135_a(itemstack1, 37, 46, false))
                {
                    return null;
                }
            }
            else if (p_82846_2_ >= 37 && p_82846_2_ < 46)
            {
                if (!this.func_75135_a(itemstack1, 10, 37, false))
                {
                    return null;
                }
            }
            else if (!this.func_75135_a(itemstack1, 10, 46, false))
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

        CraftInventoryCrafting inventory = new CraftInventoryCrafting(this.field_75162_e, this.field_75160_f);
        bukkitEntity = new CraftInventoryView(this.player.field_70458_d.getBukkitEntity(), inventory, this);
        return bukkitEntity;
    }
    // CraftBukkit end
}
