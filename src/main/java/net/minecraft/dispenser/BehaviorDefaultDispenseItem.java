package net.minecraft.dispenser;

// CraftBukkit start
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.event.block.BlockDispenseEvent;
import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
// CraftBukkit end

public class BehaviorDefaultDispenseItem implements IBehaviorDispenseItem
{
    public BehaviorDefaultDispenseItem() {}

    public final ItemStack func_82482_a(IBlockSource p_82482_1_, ItemStack p_82482_2_)
    {
        ItemStack itemstack1 = this.func_82487_b(p_82482_1_, p_82482_2_);
        this.func_82485_a(p_82482_1_);
        this.func_82489_a(p_82482_1_, BlockDispenser.func_100009_j_(p_82482_1_.func_82620_h()));
        return itemstack1;
    }

    protected ItemStack func_82487_b(IBlockSource p_82487_1_, ItemStack p_82487_2_)
    {
        EnumFacing enumfacing = BlockDispenser.func_100009_j_(p_82487_1_.func_82620_h());
        IPosition iposition = BlockDispenser.func_82525_a(p_82487_1_);
        ItemStack itemstack1 = p_82487_2_.func_77979_a(1);

        // CraftBukkit start
        if (!func_82486_a(p_82487_1_.func_82618_k(), itemstack1, 6, enumfacing, p_82487_1_))
        {
            p_82487_2_.field_77994_a++;
        }

        // CraftBukkit end
        return p_82487_2_;
    }

    // CraftBukkit start - void -> boolean return, IPosition -> ISourceBlock last argument
    public static boolean func_82486_a(World p_82486_0_, ItemStack p_82486_1_, int p_82486_2_, EnumFacing p_82486_3_, IBlockSource p_82486_4_)
    {
        IPosition iposition = BlockDispenser.func_82525_a(p_82486_4_);
        // CraftBukkit end
        double d0 = iposition.func_82615_a();
        double d1 = iposition.func_82617_b();
        double d2 = iposition.func_82616_c();
        EntityItem entityitem = new EntityItem(p_82486_0_, d0, d1 - 0.3D, d2, p_82486_1_);
        double d3 = p_82486_0_.field_73012_v.nextDouble() * 0.1D + 0.2D;
        entityitem.field_70159_w = (double)p_82486_3_.func_82601_c() * d3;
        entityitem.field_70181_x = 0.20000000298023224D;
        entityitem.field_70179_y = (double)p_82486_3_.func_82599_e() * d3;
        entityitem.field_70159_w += p_82486_0_.field_73012_v.nextGaussian() * 0.007499999832361937D * (double)p_82486_2_;
        entityitem.field_70181_x += p_82486_0_.field_73012_v.nextGaussian() * 0.007499999832361937D * (double)p_82486_2_;
        entityitem.field_70179_y += p_82486_0_.field_73012_v.nextGaussian() * 0.007499999832361937D * (double)p_82486_2_;
        // CraftBukkit start
        org.bukkit.block.Block block = p_82486_0_.getWorld().getBlockAt(p_82486_4_.func_82623_d(), p_82486_4_.func_82622_e(), p_82486_4_.func_82621_f());
        CraftItemStack craftItem = CraftItemStack.asCraftMirror(p_82486_1_);
        BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new org.bukkit.util.Vector(entityitem.field_70159_w, entityitem.field_70181_x, entityitem.field_70179_y));

        if (!BlockDispenser.eventFired)
        {
            p_82486_0_.getServer().getPluginManager().callEvent(event);
        }

        if (event.isCancelled())
        {
            return false;
        }

        entityitem.func_92058_a(CraftItemStack.asNMSCopy(event.getItem()));
        entityitem.field_70159_w = event.getVelocity().getX();
        entityitem.field_70181_x = event.getVelocity().getY();
        entityitem.field_70179_y = event.getVelocity().getZ();

        if (!event.getItem().equals(craftItem))
        {
            // Chain to handler for new item
            ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
            IBehaviorDispenseItem ibehaviordispenseitem = (IBehaviorDispenseItem) BlockDispenser.field_82527_a.func_82594_a(eventStack.func_77973_b());

            if (ibehaviordispenseitem != IBehaviorDispenseItem.field_82483_a && ibehaviordispenseitem.getClass() != BehaviorDefaultDispenseItem.class)
            {
                ibehaviordispenseitem.func_82482_a(p_82486_4_, eventStack);
            }
            else
            {
                p_82486_0_.func_72838_d(entityitem);
            }

            return false;
        }

        p_82486_0_.func_72838_d(entityitem);
        return true;
        // CraftBukkit end
    }

    protected void func_82485_a(IBlockSource p_82485_1_)
    {
        p_82485_1_.func_82618_k().func_72926_e(1000, p_82485_1_.func_82623_d(), p_82485_1_.func_82622_e(), p_82485_1_.func_82621_f(), 0);
    }

    protected void func_82489_a(IBlockSource p_82489_1_, EnumFacing p_82489_2_)
    {
        p_82489_1_.func_82618_k().func_72926_e(2000, p_82489_1_.func_82623_d(), p_82489_1_.func_82622_e(), p_82489_1_.func_82621_f(), this.func_82488_a(p_82489_2_));
    }

    private int func_82488_a(EnumFacing p_82488_1_)
    {
        return p_82488_1_.func_82601_c() + 1 + (p_82488_1_.func_82599_e() + 1) * 3;
    }
}
