package net.minecraft.dispenser;

// CraftBukkit start
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.event.block.BlockDispenseEvent;
import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
// CraftBukkit end

public abstract class BehaviorProjectileDispense extends BehaviorDefaultDispenseItem
{
    public BehaviorProjectileDispense() {}

    public ItemStack func_82487_b(IBlockSource p_82487_1_, ItemStack p_82487_2_)
    {
        World world = p_82487_1_.func_82618_k();
        IPosition iposition = BlockDispenser.func_82525_a(p_82487_1_);
        EnumFacing enumfacing = EnumFacing.func_82600_a(p_82487_1_.func_82620_h());
        IProjectile iprojectile = this.func_82499_a(world, iposition);
        // CraftBukkit start
        ItemStack itemstack1 = p_82487_2_.func_77979_a(1);
        org.bukkit.block.Block block = world.getWorld().getBlockAt(p_82487_1_.func_82623_d(), p_82487_1_.func_82622_e(), p_82487_1_.func_82621_f());
        CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack1);
        BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new org.bukkit.util.Vector((double) enumfacing.func_82601_c(), 0.10000000149011612D, (double) enumfacing.func_82599_e()));

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

        iprojectile.func_70186_c(event.getVelocity().getX(), event.getVelocity().getY(), event.getVelocity().getZ(), this.func_82500_b(), this.func_82498_a());
        // CraftBukkit end
        world.func_72838_d((Entity)iprojectile);
        // itemstack.a(1); // CraftBukkit - Handled during event processing
        return p_82487_2_;
    }

    protected void func_82485_a(IBlockSource p_82485_1_)
    {
        p_82485_1_.func_82618_k().func_72926_e(1002, p_82485_1_.func_82623_d(), p_82485_1_.func_82622_e(), p_82485_1_.func_82621_f(), 0);
    }

    protected abstract IProjectile func_82499_a(World world, IPosition iposition);

    protected float func_82498_a()
    {
        return 6.0F;
    }

    protected float func_82500_b()
    {
        return 1.1F;
    }
}
