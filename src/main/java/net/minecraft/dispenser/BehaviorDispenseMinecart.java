package net.minecraft.dispenser;

// CraftBukkit start
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.event.block.BlockDispenseEvent;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockRail;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.item.ItemMinecart;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
// CraftBukkit end

public class BehaviorDispenseMinecart extends BehaviorDefaultDispenseItem
{
    private final BehaviorDefaultDispenseItem field_82493_c;

    final MinecraftServer field_82492_b;

    public BehaviorDispenseMinecart(MinecraftServer p_i5048_1_)
    {
        this.field_82492_b = p_i5048_1_;
        this.field_82493_c = new BehaviorDefaultDispenseItem();
    }

    public ItemStack func_82487_b(IBlockSource p_82487_1_, ItemStack p_82487_2_)
    {
        EnumFacing enumfacing = EnumFacing.func_82600_a(p_82487_1_.func_82620_h());
        World world = p_82487_1_.func_82618_k();
        double d0 = p_82487_1_.func_82615_a() + (double)((float)enumfacing.func_82601_c() * 1.125F);
        double d1 = p_82487_1_.func_82617_b();
        double d2 = p_82487_1_.func_82616_c() + (double)((float)enumfacing.func_82599_e() * 1.125F);
        int i = p_82487_1_.func_82623_d() + enumfacing.func_82601_c();
        int j = p_82487_1_.func_82622_e();
        int k = p_82487_1_.func_82621_f() + enumfacing.func_82599_e();
        int l = world.func_72798_a(i, j, k);
        double d3;

        if (BlockRail.func_72184_d(l))
        {
            d3 = 0.0D;
        }
        else
        {
            if (l != 0 || !BlockRail.func_72184_d(world.func_72798_a(i, j - 1, k)))
            {
                return this.field_82493_c.func_82482_a(p_82487_1_, p_82487_2_);
            }

            d3 = -1.0D;
        }

        // CraftBukkit start
        ItemStack itemstack1 = p_82487_2_.func_77979_a(1);
        org.bukkit.block.Block block = world.getWorld().getBlockAt(p_82487_1_.func_82623_d(), p_82487_1_.func_82622_e(), p_82487_1_.func_82621_f());
        CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack1);
        BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new org.bukkit.util.Vector(d0, d1 + d3, d2));

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
        EntityMinecart entityminecart = new EntityMinecart(world, event.getVelocity().getX(), event.getVelocity().getY(), event.getVelocity().getZ(), ((ItemMinecart) itemstack1.func_77973_b()).field_77841_a);
        // CraftBukkit end
        world.func_72838_d(entityminecart);
        // itemstack.a(1); // CraftBukkit - handled during event processing
        return p_82487_2_;
    }

    protected void func_82485_a(IBlockSource p_82485_1_)
    {
        p_82485_1_.func_82618_k().func_72926_e(1000, p_82485_1_.func_82623_d(), p_82485_1_.func_82622_e(), p_82485_1_.func_82621_f(), 0);
    }
}
