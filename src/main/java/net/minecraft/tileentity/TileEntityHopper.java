package net.minecraft.tileentity;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockHopper;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

// CraftBukkit start
import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
// CraftBukkit end

public class TileEntityHopper extends TileEntity implements Hopper
{
    private ItemStack[] field_94124_b = new ItemStack[5];
    private String field_94123_d;
    private int field_98048_c = -1;

    // CraftBukkit start
    public List<HumanEntity> transaction = new java.util.ArrayList<HumanEntity>();
    private int maxStack = MAX_STACK;

    public ItemStack[] getContents()
    {
        return this.field_94124_b;
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

    public void setMaxStackSize(int size)
    {
        maxStack = size;
    }
    // CraftBukkit end

    public TileEntityHopper() {}

    public void func_70307_a(NBTTagCompound p_70307_1_)
    {
        super.func_70307_a(p_70307_1_);
        NBTTagList nbttaglist = p_70307_1_.func_74761_m("Items");
        this.field_94124_b = new ItemStack[this.func_70302_i_()];

        if (p_70307_1_.func_74764_b("CustomName"))
        {
            this.field_94123_d = p_70307_1_.func_74779_i("CustomName");
        }

        this.field_98048_c = p_70307_1_.func_74762_e("TransferCooldown");

        for (int i = 0; i < nbttaglist.func_74745_c(); ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.func_74743_b(i);
            byte b0 = nbttagcompound1.func_74771_c("Slot");

            if (b0 >= 0 && b0 < this.field_94124_b.length)
            {
                this.field_94124_b[b0] = ItemStack.func_77949_a(nbttagcompound1);
            }
        }
    }

    public void func_70310_b(NBTTagCompound p_70310_1_)
    {
        super.func_70310_b(p_70310_1_);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.field_94124_b.length; ++i)
        {
            if (this.field_94124_b[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.func_74774_a("Slot", (byte)i);
                this.field_94124_b[i].func_77955_b(nbttagcompound1);
                nbttaglist.func_74742_a(nbttagcompound1);
            }
        }

        p_70310_1_.func_74782_a("Items", nbttaglist);
        p_70310_1_.func_74768_a("TransferCooldown", this.field_98048_c);

        if (this.func_94042_c())
        {
            p_70310_1_.func_74778_a("CustomName", this.field_94123_d);
        }
    }

    public void func_70296_d()
    {
        super.func_70296_d();
    }

    public int func_70302_i_()
    {
        return this.field_94124_b.length;
    }

    public ItemStack func_70301_a(int p_70301_1_)
    {
        return this.field_94124_b[p_70301_1_];
    }

    public ItemStack func_70298_a(int p_70298_1_, int p_70298_2_)
    {
        if (this.field_94124_b[p_70298_1_] != null)
        {
            ItemStack itemstack;

            if (this.field_94124_b[p_70298_1_].field_77994_a <= p_70298_2_)
            {
                itemstack = this.field_94124_b[p_70298_1_];
                this.field_94124_b[p_70298_1_] = null;
                return itemstack;
            }
            else
            {
                itemstack = this.field_94124_b[p_70298_1_].func_77979_a(p_70298_2_);

                if (this.field_94124_b[p_70298_1_].field_77994_a == 0)
                {
                    this.field_94124_b[p_70298_1_] = null;
                }

                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }

    public ItemStack func_70304_b(int p_70304_1_)
    {
        if (this.field_94124_b[p_70304_1_] != null)
        {
            ItemStack itemstack = this.field_94124_b[p_70304_1_];
            this.field_94124_b[p_70304_1_] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    public void func_70299_a(int p_70299_1_, ItemStack p_70299_2_)
    {
        this.field_94124_b[p_70299_1_] = p_70299_2_;

        if (p_70299_2_ != null && p_70299_2_.field_77994_a > this.func_70297_j_())
        {
            p_70299_2_.field_77994_a = this.func_70297_j_();
        }
    }

    public String func_70303_b()
    {
        return this.func_94042_c() ? this.field_94123_d : "container.hopper";
    }

    public boolean func_94042_c()
    {
        return this.field_94123_d != null && this.field_94123_d.length() > 0;
    }

    public void func_96115_a(String p_96115_1_)
    {
        this.field_94123_d = p_96115_1_;
    }

    public int func_70297_j_()
    {
        return 64;
    }

    public boolean func_70300_a(EntityPlayer p_70300_1_)
    {
        return this.field_70331_k.func_72796_p(this.field_70329_l, this.field_70330_m, this.field_70327_n) != this ? false : p_70300_1_.func_70092_e((double)this.field_70329_l + 0.5D, (double)this.field_70330_m + 0.5D, (double)this.field_70327_n + 0.5D) <= 64.0D;
    }

    public void func_70295_k_() {}

    public void func_70305_f() {}

    public boolean func_94041_b(int p_94041_1_, ItemStack p_94041_2_)
    {
        return true;
    }

    public void func_70316_g()
    {
        if (this.field_70331_k != null && !this.field_70331_k.field_72995_K)
        {
            --this.field_98048_c;

            if (!this.func_98047_l())
            {
                this.func_98046_c(0);
                this.func_98045_j();
            }
        }
    }

    public boolean func_98045_j()
    {
        if (this.field_70331_k != null && !this.field_70331_k.field_72995_K)
        {
            if (!this.func_98047_l() && BlockHopper.func_94452_d(this.func_70322_n()))
            {
                boolean flag = this.func_94116_j() | func_96116_a((Hopper) this);

                if (flag)
                {
                    this.func_98046_c(8);
                    this.func_70296_d();
                    return true;
                }
            }

            return false;
        }
        else
        {
            return false;
        }
    }

    private boolean func_94116_j()
    {
        int i = func_94115_a(this, -1);
        boolean flag = false;

        if (i > -1)
        {
            IInventory iinventory = this.func_94119_v();

            if (iinventory != null)
            {
                ItemStack itemstack = this.func_70301_a(i).func_77946_l();
                ItemStack itemstack1 = func_94117_a(iinventory, this.func_70298_a(i, 1), Facing.field_71588_a[BlockHopper.func_94451_c(this.func_70322_n())]);

                if (itemstack1 != null && itemstack1.field_77994_a != 0)
                {
                    this.func_70299_a(i, itemstack);
                }
                else
                {
                    flag |= true;
                    iinventory.func_70296_d();
                }
            }
        }

        return flag;
    }

    public static boolean func_96116_a(Hopper p_96116_0_)
    {
        boolean flag = false;
        IInventory iinventory = func_96118_b(p_96116_0_);

        if (iinventory != null)
        {
            byte b0 = 0;
            int i = 0;
            int j = iinventory.func_70302_i_();

            if (iinventory instanceof ISidedInventory && b0 > -1)
            {
                ISidedInventory isidedinventory = (ISidedInventory)iinventory;
                i = isidedinventory.func_94127_c(b0);
                j = Math.min(j, i + isidedinventory.func_94128_d(b0));
            }

            for (int k = i; k < j && !flag; ++k)
            {
                ItemStack itemstack = iinventory.func_70301_a(k);

                if (itemstack != null)
                {
                    ItemStack itemstack1 = itemstack.func_77946_l();
                    ItemStack itemstack2 = func_94117_a(p_96116_0_, iinventory.func_70298_a(k, 1), -1);

                    if (itemstack2 != null && itemstack2.field_77994_a != 0)
                    {
                        iinventory.func_70299_a(k, itemstack1);
                    }
                    else
                    {
                        flag |= true;
                        iinventory.func_70296_d();
                    }
                }
            }
        }
        else
        {
            EntityItem entityitem = func_96119_a(p_96116_0_.func_70314_l(), p_96116_0_.func_96107_aA(), p_96116_0_.func_96109_aB() + 1.0D, p_96116_0_.func_96108_aC());

            if (entityitem != null)
            {
                flag |= func_96114_a((IInventory) p_96116_0_, entityitem);
            }
        }

        return flag;
    }

    public static boolean func_96114_a(IInventory p_96114_0_, EntityItem p_96114_1_)
    {
        boolean flag = false;

        if (p_96114_1_ == null)
        {
            return false;
        }
        else
        {
            ItemStack itemstack = p_96114_1_.func_92059_d().func_77946_l();
            ItemStack itemstack1 = func_94117_a(p_96114_0_, itemstack, -1);

            if (itemstack1 != null && itemstack1.field_77994_a != 0)
            {
                p_96114_1_.func_92058_a(itemstack1);
            }
            else
            {
                flag = true;
                p_96114_1_.func_70106_y();
            }

            return flag;
        }
    }

    public static int func_94115_a(IInventory p_94115_1_, int p_94115_2_)
    {
        int j = 0;
        int k = p_94115_1_.func_70302_i_();

        if (p_94115_1_ instanceof ISidedInventory && p_94115_2_ > -1)
        {
            ISidedInventory isidedinventory = (ISidedInventory)p_94115_1_;
            j = isidedinventory.func_94127_c(p_94115_2_);
            k = Math.min(k, j + isidedinventory.func_94128_d(p_94115_2_));
        }

        for (int l = j; l < k; ++l)
        {
            if (p_94115_1_.func_70301_a(l) != null)
            {
                return l;
            }
        }

        return -1;
    }

    public static ItemStack func_94117_a(IInventory p_94117_1_, ItemStack p_94117_2_, int p_94117_3_)
    {
        int j = 0;
        int k = p_94117_1_.func_70302_i_();

        if (p_94117_1_ instanceof ISidedInventory && p_94117_3_ > -1)
        {
            ISidedInventory isidedinventory = (ISidedInventory)p_94117_1_;
            j = isidedinventory.func_94127_c(p_94117_3_);
            k = Math.min(k, j + isidedinventory.func_94128_d(p_94117_3_));
        }

        for (int l = j; l < k && p_94117_2_ != null && p_94117_2_.field_77994_a > 0; ++l)
        {
            ItemStack itemstack1 = p_94117_1_.func_70301_a(l);

            if (p_94117_1_.func_94041_b(l, p_94117_2_))
            {
                boolean flag = false;

                if (itemstack1 == null)
                {
                    p_94117_1_.func_70299_a(l, p_94117_2_);
                    p_94117_2_ = null;
                    flag = true;
                }
                else if (func_94114_a(itemstack1, p_94117_2_))
                {
                    int i1 = p_94117_2_.func_77976_d() - itemstack1.field_77994_a;
                    int j1 = Math.min(p_94117_2_.field_77994_a, i1);
                    p_94117_2_.field_77994_a -= j1;
                    itemstack1.field_77994_a += j1;
                    flag = j1 > 0;
                }

                if (flag)
                {
                    if (p_94117_1_ instanceof TileEntityHopper)
                    {
                        ((TileEntityHopper)p_94117_1_).func_98046_c(8);
                    }

                    p_94117_1_.func_70296_d();
                }
            }
        }

        if (p_94117_2_ != null && p_94117_2_.field_77994_a == 0)
        {
            p_94117_2_ = null;
        }

        return p_94117_2_;
    }

    private IInventory func_94119_v()
    {
        int i = BlockHopper.func_94451_c(this.func_70322_n());
        return func_96117_b(this.func_70314_l(), (double)(this.field_70329_l + Facing.field_71586_b[i]), (double)(this.field_70330_m + Facing.field_71587_c[i]), (double)(this.field_70327_n + Facing.field_71585_d[i]));
    }

    public static IInventory func_96118_b(Hopper p_96118_0_)
    {
        return func_96117_b(p_96118_0_.func_70314_l(), p_96118_0_.func_96107_aA(), p_96118_0_.func_96109_aB() + 1.0D, p_96118_0_.func_96108_aC());
    }

    public static EntityItem func_96119_a(World p_96119_0_, double p_96119_1_, double p_96119_3_, double p_96119_5_)
    {
        List list = p_96119_0_.func_82733_a(EntityItem.class, AxisAlignedBB.func_72332_a().func_72299_a(p_96119_1_, p_96119_3_, p_96119_5_, p_96119_1_ + 1.0D, p_96119_3_ + 1.0D, p_96119_5_ + 1.0D), IEntitySelector.field_94557_a);
        return list.size() > 0 ? (EntityItem)list.get(0) : null;
    }

    public static IInventory func_96117_b(World p_96117_0_, double p_96117_1_, double p_96117_3_, double p_96117_5_)
    {
        IInventory iinventory = null;
        int i = MathHelper.func_76128_c(p_96117_1_);
        int j = MathHelper.func_76128_c(p_96117_3_);
        int k = MathHelper.func_76128_c(p_96117_5_);

        if (iinventory == null)
        {
            TileEntity tileentity = p_96117_0_.func_72796_p(i, j, k);

            if (tileentity != null && tileentity instanceof IInventory)
            {
                iinventory = (IInventory)tileentity;

                if (iinventory instanceof TileEntityChest)
                {
                    int l = p_96117_0_.func_72798_a(i, j, k);
                    Block block = Block.field_71973_m[l];

                    if (block instanceof BlockChest)
                    {
                        iinventory = ((BlockChest)block).func_94442_h_(p_96117_0_, i, j, k);
                    }
                }
            }
        }

        if (iinventory == null)
        {
            List list = p_96117_0_.func_94576_a((Entity)null, AxisAlignedBB.func_72332_a().func_72299_a(p_96117_1_, p_96117_3_, p_96117_5_, p_96117_1_ + 1.0D, p_96117_3_ + 1.0D, p_96117_5_ + 1.0D), IEntitySelector.field_96566_b);

            if (list != null && list.size() > 0)
            {
                iinventory = (IInventory)list.get(p_96117_0_.field_73012_v.nextInt(list.size()));
            }
        }

        return iinventory;
    }

    private static boolean func_94114_a(ItemStack p_94114_1_, ItemStack p_94114_2_)
    {
        return p_94114_1_.field_77993_c != p_94114_2_.field_77993_c ? false : (p_94114_1_.func_77960_j() != p_94114_2_.func_77960_j() ? false : (p_94114_1_.field_77994_a > p_94114_1_.func_77976_d() ? false : ItemStack.func_77970_a(p_94114_1_, p_94114_2_)));
    }

    public double func_96107_aA()
    {
        return (double)this.field_70329_l;
    }

    public double func_96109_aB()
    {
        return (double)this.field_70330_m;
    }

    public double func_96108_aC()
    {
        return (double)this.field_70327_n;
    }

    public void func_98046_c(int p_98046_1_)
    {
        this.field_98048_c = p_98046_1_;
    }

    public boolean func_98047_l()
    {
        return this.field_98048_c > 0;
    }
}
