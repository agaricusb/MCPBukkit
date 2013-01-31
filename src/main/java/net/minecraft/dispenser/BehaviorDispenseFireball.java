package net.minecraft.dispenser;

import java.util.Random;
import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

// CraftBukkit start
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.event.block.BlockDispenseEvent;
// CraftBukkit end

public class BehaviorDispenseFireball extends BehaviorDefaultDispenseItem
{
    final MinecraftServer field_82490_b;

    public BehaviorDispenseFireball(MinecraftServer p_i5047_1_)
    {
        this.field_82490_b = p_i5047_1_;
    }

    public ItemStack func_82487_b(IBlockSource p_82487_1_, ItemStack p_82487_2_)
    {
        EnumFacing enumfacing = EnumFacing.func_82600_a(p_82487_1_.func_82620_h());
        IPosition iposition = BlockDispenser.func_82525_a(p_82487_1_);
        double d0 = iposition.func_82615_a() + (double)((float)enumfacing.func_82601_c() * 0.3F);
        double d1 = iposition.func_82617_b();
        double d2 = iposition.func_82616_c() + (double)((float)enumfacing.func_82599_e() * 0.3F);
        World world = p_82487_1_.func_82618_k();
        Random random = world.field_73012_v;
        double d3 = random.nextGaussian() * 0.05D + (double)enumfacing.func_82601_c();
        double d4 = random.nextGaussian() * 0.05D;
        double d5 = random.nextGaussian() * 0.05D + (double)enumfacing.func_82599_e();
        // CraftBukkit start
        ItemStack itemstack1 = p_82487_2_.func_77979_a(1);
        org.bukkit.block.Block block = world.getWorld().getBlockAt(p_82487_1_.func_82623_d(), p_82487_1_.func_82622_e(), p_82487_1_.func_82621_f());
        CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack1);
        BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new org.bukkit.util.Vector(d3, d4, d5));

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

        world.func_72838_d(new EntitySmallFireball(world, d0, d1, d2, event.getVelocity().getX(), event.getVelocity().getY(), event.getVelocity().getZ()));
        // itemstack.a(1); // Handled during event processing
        // CraftBukkit end
        return p_82487_2_;
    }

    protected void func_82485_a(IBlockSource p_82485_1_)
    {
        p_82485_1_.func_82618_k().func_72926_e(1009, p_82485_1_.func_82623_d(), p_82485_1_.func_82622_e(), p_82485_1_.func_82621_f(), 0);
    }
}
