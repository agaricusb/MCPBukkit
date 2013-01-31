package net.minecraft.dispenser;

// CraftBukkit start
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.event.block.BlockDispenseEvent;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
// CraftBukkit end

public class BehaviorBucketEmptyDispense extends BehaviorDefaultDispenseItem
{
    private final BehaviorDefaultDispenseItem field_92073_c;

    final MinecraftServer field_82310_a;

    public BehaviorBucketEmptyDispense(MinecraftServer p_i5004_1_)
    {
        this.field_82310_a = p_i5004_1_;
        this.field_92073_c = new BehaviorDefaultDispenseItem();
    }

    public ItemStack func_82487_b(IBlockSource p_82487_1_, ItemStack p_82487_2_)
    {
        EnumFacing enumfacing = EnumFacing.func_82600_a(p_82487_1_.func_82620_h());
        World world = p_82487_1_.func_82618_k();
        int i = p_82487_1_.func_82623_d() + enumfacing.func_82601_c();
        int j = p_82487_1_.func_82622_e();
        int k = p_82487_1_.func_82621_f() + enumfacing.func_82599_e();
        Material material = world.func_72803_f(i, j, k);
        int l = world.func_72805_g(i, j, k);
        Item item;

        if (Material.field_76244_g.equals(material) && l == 0)
        {
            item = Item.field_77786_ax;
        }
        else
        {
            if (!Material.field_76256_h.equals(material) || l != 0)
            {
                return super.func_82487_b(p_82487_1_, p_82487_2_);
            }

            item = Item.field_77775_ay;
        }

        // CraftBukkit start
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

        // CraftBukkit end
        world.func_72859_e(i, j, k, 0);

        if (--p_82487_2_.field_77994_a == 0)
        {
            p_82487_2_.field_77993_c = item.field_77779_bT;
            p_82487_2_.field_77994_a = 1;
        }
        else if (((TileEntityDispenser)p_82487_1_.func_82619_j()).func_70360_a(new ItemStack(item)) < 0)
        {
            this.field_92073_c.func_82482_a(p_82487_1_, new ItemStack(item));
        }

        return p_82487_2_;
    }
}
