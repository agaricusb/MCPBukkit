package net.minecraft.item;

// CraftBukkit start
import org.bukkit.Bukkit;
import org.bukkit.event.server.MapInitializeEvent;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet131MapData;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.MapInfo;
// CraftBukkit end

public class ItemMap extends ItemMapBase
{
    protected ItemMap(int p_i3668_1_)
    {
        super(p_i3668_1_);
        this.func_77627_a(true);
    }

    public MapData func_77873_a(ItemStack p_77873_1_, World p_77873_2_)
    {
        String s = "map_" + p_77873_1_.func_77960_j();
        MapData mapdata = (MapData)p_77873_2_.func_72943_a(MapData.class, s);

        if (mapdata == null && !p_77873_2_.field_72995_K)
        {
            p_77873_1_.func_77964_b(p_77873_2_.func_72841_b("map"));
            s = "map_" + p_77873_1_.func_77960_j();
            mapdata = new MapData(s);
            mapdata.field_76197_d = 3;
            int i = 128 * (1 << mapdata.field_76197_d);
            mapdata.field_76201_a = Math.round((float)p_77873_2_.func_72912_H().func_76079_c() / (float)i) * i;
            mapdata.field_76199_b = Math.round((float)(p_77873_2_.func_72912_H().func_76074_e() / i)) * i;
            mapdata.field_76200_c = (byte)((WorldServer) p_77873_2_).dimension;  // CraftBukkit - fixes Bukkit multiworld maps
            mapdata.func_76185_a();
            p_77873_2_.func_72823_a(s, (WorldSavedData) mapdata);
            // CraftBukkit start
            MapInitializeEvent event = new MapInitializeEvent(mapdata.mapView);
            Bukkit.getServer().getPluginManager().callEvent(event);
            // CraftBukkit end
        }

        return mapdata;
    }

    public void func_77872_a(World p_77872_1_, Entity p_77872_2_, MapData p_77872_3_)
    {
        // CraftBukkit
        if (((WorldServer) p_77872_1_).dimension == p_77872_3_.field_76200_c && p_77872_2_ instanceof EntityPlayer)
        {
            short short1 = 128;
            short short2 = 128;
            int i = 1 << p_77872_3_.field_76197_d;
            int j = p_77872_3_.field_76201_a;
            int k = p_77872_3_.field_76199_b;
            int l = MathHelper.func_76128_c(p_77872_2_.field_70165_t - (double)j) / i + short1 / 2;
            int i1 = MathHelper.func_76128_c(p_77872_2_.field_70161_v - (double)k) / i + short2 / 2;
            int j1 = 128 / i;

            if (p_77872_1_.field_73011_w.field_76576_e)
            {
                j1 /= 2;
            }

            MapInfo mapinfo = p_77872_3_.func_82568_a((EntityPlayer)p_77872_2_);
            ++mapinfo.field_82569_d;

            for (int k1 = l - j1 + 1; k1 < l + j1; ++k1)
            {
                if ((k1 & 15) == (mapinfo.field_82569_d & 15))
                {
                    int l1 = 255;
                    int i2 = 0;
                    double d0 = 0.0D;

                    for (int j2 = i1 - j1 - 1; j2 < i1 + j1; ++j2)
                    {
                        if (k1 >= 0 && j2 >= -1 && k1 < short1 && j2 < short2)
                        {
                            int k2 = k1 - l;
                            int l2 = j2 - i1;
                            boolean flag = k2 * k2 + l2 * l2 > (j1 - 2) * (j1 - 2);
                            int i3 = (j / i + k1 - short1 / 2) * i;
                            int j3 = (k / i + j2 - short2 / 2) * i;
                            int[] aint = new int[256];
                            Chunk chunk = p_77872_1_.func_72938_d(i3, j3);

                            if (!chunk.func_76621_g())
                            {
                                int k3 = i3 & 15;
                                int l3 = j3 & 15;
                                int i4 = 0;
                                double d1 = 0.0D;
                                int j4;
                                int k4;
                                int l4;
                                int i5;

                                if (p_77872_1_.field_73011_w.field_76576_e)
                                {
                                    j4 = i3 + j3 * 231871;
                                    j4 = j4 * j4 * 31287121 + j4 * 11;

                                    if ((j4 >> 20 & 1) == 0)
                                    {
                                        aint[Block.field_71979_v.field_71990_ca] += 10;
                                    }
                                    else
                                    {
                                        aint[Block.field_71981_t.field_71990_ca] += 10;
                                    }

                                    d1 = 100.0D;
                                }
                                else
                                {
                                    for (j4 = 0; j4 < i; ++j4)
                                    {
                                        for (k4 = 0; k4 < i; ++k4)
                                        {
                                            l4 = chunk.func_76611_b(j4 + k3, k4 + l3) + 1;
                                            int j5 = 0;

                                            if (l4 > 1)
                                            {
                                                boolean flag1;

                                                do
                                                {
                                                    flag1 = true;
                                                    j5 = chunk.func_76610_a(j4 + k3, l4 - 1, k4 + l3);

                                                    if (j5 == 0)
                                                    {
                                                        flag1 = false;
                                                    }
                                                    else if (l4 > 0 && j5 > 0 && Block.field_71973_m[j5].field_72018_cp.field_76234_F == MapColor.field_76279_b)
                                                    {
                                                        flag1 = false;
                                                    }

                                                    if (!flag1)
                                                    {
                                                        --l4;

                                                        if (l4 <= 0)
                                                        {
                                                            break;
                                                        }

                                                        j5 = chunk.func_76610_a(j4 + k3, l4 - 1, k4 + l3);
                                                    }
                                                }
                                                while (l4 > 0 && !flag1);

                                                if (l4 > 0 && j5 != 0 && Block.field_71973_m[j5].field_72018_cp.func_76224_d())
                                                {
                                                    i5 = l4 - 1;
                                                    boolean flag2 = false;
                                                    int k5;

                                                    do
                                                    {
                                                        k5 = chunk.func_76610_a(j4 + k3, i5--, k4 + l3);
                                                        ++i4;
                                                    }
                                                    while (i5 > 0 && k5 != 0 && Block.field_71973_m[k5].field_72018_cp.func_76224_d());
                                                }
                                            }

                                            d1 += (double)l4 / (double)(i * i);
                                            ++aint[j5];
                                        }
                                    }
                                }

                                i4 /= i * i;
                                j4 = 0;
                                k4 = 0;

                                for (l4 = 0; l4 < 256; ++l4)
                                {
                                    if (aint[l4] > j4)
                                    {
                                        k4 = l4;
                                        j4 = aint[l4];
                                    }
                                }

                                double d2 = (d1 - d0) * 4.0D / (double)(i + 4) + ((double)(k1 + j2 & 1) - 0.5D) * 0.4D;
                                byte b0 = 1;

                                if (d2 > 0.6D)
                                {
                                    b0 = 2;
                                }

                                if (d2 < -0.6D)
                                {
                                    b0 = 0;
                                }

                                i5 = 0;

                                if (k4 > 0)
                                {
                                    MapColor mapcolor = Block.field_71973_m[k4].field_72018_cp.field_76234_F;

                                    if (mapcolor == MapColor.field_76282_n)
                                    {
                                        d2 = (double)i4 * 0.1D + (double)(k1 + j2 & 1) * 0.2D;
                                        b0 = 1;

                                        if (d2 < 0.5D)
                                        {
                                            b0 = 2;
                                        }

                                        if (d2 > 0.9D)
                                        {
                                            b0 = 0;
                                        }
                                    }

                                    i5 = mapcolor.field_76290_q;
                                }

                                d0 = d1;

                                if (j2 >= 0 && k2 * k2 + l2 * l2 < j1 * j1 && (!flag || (k1 + j2 & 1) != 0))
                                {
                                    byte b1 = p_77872_3_.field_76198_e[k1 + j2 * short1];
                                    byte b2 = (byte)(i5 * 4 + b0);

                                    if (b1 != b2)
                                    {
                                        if (l1 > j2)
                                        {
                                            l1 = j2;
                                        }

                                        if (i2 < j2)
                                        {
                                            i2 = j2;
                                        }

                                        p_77872_3_.field_76198_e[k1 + j2 * short1] = b2;
                                    }
                                }
                            }
                        }
                    }

                    if (l1 <= i2)
                    {
                        p_77872_3_.func_76194_a(k1, l1, i2);
                    }
                }
            }
        }
    }

    public void func_77663_a(ItemStack p_77663_1_, World p_77663_2_, Entity p_77663_3_, int p_77663_4_, boolean p_77663_5_)
    {
        if (!p_77663_2_.field_72995_K)
        {
            MapData mapdata = this.func_77873_a(p_77663_1_, p_77663_2_);

            if (p_77663_3_ instanceof EntityPlayer)
            {
                EntityPlayer entityplayer = (EntityPlayer)p_77663_3_;
                mapdata.func_76191_a(entityplayer, p_77663_1_);
            }

            if (p_77663_5_)
            {
                this.func_77872_a(p_77663_2_, p_77663_3_, mapdata);
            }
        }
    }

    public Packet func_77871_c(ItemStack p_77871_1_, World p_77871_2_, EntityPlayer p_77871_3_)
    {
        byte[] abyte = this.func_77873_a(p_77871_1_, p_77871_2_).func_76193_a(p_77871_1_, p_77871_2_, p_77871_3_);
        return abyte == null ? null : new Packet131MapData((short)Item.field_77744_bd.field_77779_bT, (short)p_77871_1_.func_77960_j(), abyte);
    }

    public void func_77622_d(ItemStack p_77622_1_, World p_77622_2_, EntityPlayer p_77622_3_)
    {
        if (p_77622_1_.func_77942_o() && p_77622_1_.func_77978_p().func_74767_n("map_is_scaling"))
        {
            MapData mapdata = Item.field_77744_bd.func_77873_a(p_77622_1_, p_77622_2_);
            p_77622_1_.func_77964_b(p_77622_2_.func_72841_b("map"));
            MapData mapdata1 = new MapData("map_" + p_77622_1_.func_77960_j());
            mapdata1.field_76197_d = (byte)(mapdata.field_76197_d + 1);

            if (mapdata1.field_76197_d > 4)
            {
                mapdata1.field_76197_d = 4;
            }

            mapdata1.field_76201_a = mapdata.field_76201_a;
            mapdata1.field_76199_b = mapdata.field_76199_b;
            mapdata1.field_76200_c = mapdata.field_76200_c;
            mapdata1.func_76185_a();
            p_77622_2_.func_72823_a("map_" + p_77622_1_.func_77960_j(), (WorldSavedData) mapdata1);
            // CraftBukkit start
            MapInitializeEvent event = new MapInitializeEvent(mapdata1.mapView);
            Bukkit.getServer().getPluginManager().callEvent(event);
            // CraftBukkit end
        }
    }
}
