package net.minecraft.dispenser;

// CraftBukkit start
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.event.block.BlockDispenseEvent;
import net.minecraft.block.BlockDispenser;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
// CraftBukkit end

public class BehaviorBucketFullDispense extends BehaviorDefaultDispenseItem
{
    private final BehaviorDefaultDispenseItem field_82497_c;

    final MinecraftServer field_82496_b;

    public BehaviorBucketFullDispense(MinecraftServer p_i5003_1_)
    {
        this.field_82496_b = p_i5003_1_;
        this.field_82497_c = new BehaviorDefaultDispenseItem();
    }

    public ItemStack func_82487_b(IBlockSource p_82487_1_, ItemStack p_82487_2_)
    {
        ItemBucket itembucket = (ItemBucket)p_82487_2_.func_77973_b();
        int i = p_82487_1_.func_82623_d();
        int j = p_82487_1_.func_82622_e();
        int k = p_82487_1_.func_82621_f();
        EnumFacing enumfacing = EnumFacing.func_82600_a(p_82487_1_.func_82620_h());
        // CraftBukkit start
        World world = p_82487_1_.func_82618_k();
        int i2 = i + enumfacing.func_82601_c();
        int k2 = k + enumfacing.func_82599_e();

        if (world.func_72799_c(i2, j, k2) || world.func_72803_f(i2, j, k2).func_76220_a())
        {
            org.bukkit.block.Block block = world.getWorld().getBlockAt(i, j, k);
            CraftItemStack craftItem = CraftItemStack.asCraftMirror(p_82487_2_);
            BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new org.bukkit.util.Vector(0, 0, 0));

            if (!BlockDispenser.eventFired)
            {
                world.getServer().getPluginManager().callEvent(event);
            }

            if (event.isCancelled())
            {
                return p_82487_2_;
            }

            if (!event.getItem().equals(craftItem))
            {
                // Chain to handler for new item
                ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
                IBehaviorDispenseItem ibehaviordispenseitem = (IBehaviorDispenseItem) BlockDispenser.field_82527_a.func_82594_a(eventStack.func_77973_b());

                if (ibehaviordispenseitem != IBehaviorDispenseItem.field_82483_a && ibehaviordispenseitem != this)
                {
                    ibehaviordispenseitem.func_82482_a(p_82487_1_, eventStack);
                    return p_82487_2_;
                }
            }

            itembucket = (ItemBucket) CraftItemStack.asNMSCopy(event.getItem()).func_77973_b();
        }

        // CraftBukkit end

        if (itembucket.func_77875_a(p_82487_1_.func_82618_k(), (double)i, (double)j, (double)k, i + enumfacing.func_82601_c(), j, k + enumfacing.func_82599_e()))
        {
            // CraftBukkit start - handle stacked buckets
            Item item = Item.field_77788_aw;

            if (--p_82487_2_.field_77994_a == 0)
            {
                p_82487_2_.field_77993_c = item.field_77779_bT;
                p_82487_2_.field_77994_a = 1;
            }
            else if (((TileEntityDispenser) p_82487_1_.func_82619_j()).func_70360_a(new ItemStack(item)) < 0)
            {
                this.field_82497_c.func_82482_a(p_82487_1_, new ItemStack(item));
            }

            // CraftBukkit end
            return p_82487_2_;
        }
        else
        {
            return this.field_82497_c.func_82482_a(p_82487_1_, p_82487_2_);
        }
    }
}
