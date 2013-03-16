package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.MapData;
public class ItemEmptyMap extends ItemMapBase
{
    protected ItemEmptyMap(int p_i5083_1_)
    {
        super(p_i5083_1_);
        this.func_77637_a(CreativeTabs.field_78026_f);
    }

    public ItemStack func_77659_a(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
    {
        ItemStack itemstack1 = new ItemStack(Item.field_77744_bd, 1, p_77659_2_.func_72841_b("map"));
        String s = "map_" + itemstack1.func_77960_j();
        MapData mapdata = new MapData(s);
        p_77659_2_.func_72823_a(s, (WorldSavedData) mapdata);
        mapdata.field_76197_d = 0;
        int i = 128 * (1 << mapdata.field_76197_d);
        mapdata.field_76201_a = (int)(Math.round(p_77659_3_.field_70165_t / (double)i) * (long)i);
        mapdata.field_76199_b = (int)(Math.round(p_77659_3_.field_70161_v / (double)i) * (long)i);
        mapdata.field_76200_c = (byte)((WorldServer) p_77659_2_).dimension;  // CraftBukkit - use bukkit dimension
        mapdata.func_76185_a();
        org.bukkit.craftbukkit.event.CraftEventFactory.callEvent(new org.bukkit.event.server.MapInitializeEvent(mapdata.mapView)); // CraftBukkit
        --p_77659_1_.field_77994_a;

        if (p_77659_1_.field_77994_a <= 0)
        {
            return itemstack1;
        }
        else
        {
            if (!p_77659_3_.field_71071_by.func_70441_a(itemstack1.func_77946_l()))
            {
                p_77659_3_.func_71021_b(itemstack1);
            }

            return p_77659_1_;
        }
    }
}
