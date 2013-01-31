package net.minecraft.dispenser;

// CraftBukkit start
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.event.block.BlockDispenseEvent;
import net.minecraft.block.BlockDispenser;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
// CraftBukkit end

public class BehaviorMobEggDispense extends BehaviorDefaultDispenseItem
{
    final MinecraftServer field_74270_a;

    public BehaviorMobEggDispense(MinecraftServer p_i5009_1_)
    {
        this.field_74270_a = p_i5009_1_;
    }

    public ItemStack func_82487_b(IBlockSource p_82487_1_, ItemStack p_82487_2_)
    {
        EnumFacing enumfacing = EnumFacing.func_82600_a(p_82487_1_.func_82620_h());
        double d0 = p_82487_1_.func_82615_a() + (double)enumfacing.func_82601_c();
        double d1 = (double)((float)p_82487_1_.func_82622_e() + 0.2F);
        double d2 = p_82487_1_.func_82616_c() + (double)enumfacing.func_82599_e();
        // CraftBukkit start
        World world = p_82487_1_.func_82618_k();
        ItemStack itemstack1 = p_82487_2_.func_77979_a(1);
        org.bukkit.block.Block block = world.getWorld().getBlockAt(p_82487_1_.func_82623_d(), p_82487_1_.func_82622_e(), p_82487_1_.func_82621_f());
        CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack1);
        BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new org.bukkit.util.Vector(d0, d1, d2));

        if (!BlockDispenser.eventFired)
        {
            world.getServer().getPluginManager().callEvent(event);
        }

        if (event.isCancelled())
        {
            p_82487_2_.field_77994_a++;
            return p_82487_2_;
        }

        if (!event.getItem().equals(craftItem))
        {
            p_82487_2_.field_77994_a++;
            // Chain to handler for new item
            ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
            IBehaviorDispenseItem ibehaviordispenseitem = (IBehaviorDispenseItem) BlockDispenser.field_82527_a.func_82594_a(eventStack.func_77973_b());

            if (ibehaviordispenseitem != IBehaviorDispenseItem.field_82483_a && ibehaviordispenseitem != this)
            {
                ibehaviordispenseitem.func_82482_a(p_82487_1_, eventStack);
                return p_82487_2_;
            }
        }

        itemstack1 = CraftItemStack.asNMSCopy(event.getItem());
        ItemMonsterPlacer.func_77840_a(p_82487_1_.func_82618_k(), itemstack1.func_77960_j(), event.getVelocity().getX(), event.getVelocity().getY(), event.getVelocity().getZ());
        // itemstack.a(1); // Handled during event processing
        // CraftBukkit end
        return p_82487_2_;
    }
}
