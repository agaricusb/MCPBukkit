package net.minecraft.world;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Direction;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.MathHelper;

// CraftBukkit start
import org.bukkit.Location;
import org.bukkit.event.entity.EntityPortalExitEvent;
import org.bukkit.util.Vector;
// CraftBukkit end

public class Teleporter
{
    private final WorldServer field_85192_a;
    private final Random field_77187_a;
    private final LongHashMap field_85191_c = new LongHashMap();
    private final List field_85190_d = new ArrayList();

    public Teleporter(WorldServer p_i6816_1_)
    {
        this.field_85192_a = p_i6816_1_;
        this.field_77187_a = new Random(p_i6816_1_.func_72905_C());
    }

    public void func_77185_a(Entity p_77185_1_, double p_77185_2_, double p_77185_4_, double p_77185_6_, float p_77185_8_)
    {
        if (this.field_85192_a.field_73011_w.field_76574_g != 1)
        {
            if (!this.func_77184_b(p_77185_1_, p_77185_2_, p_77185_4_, p_77185_6_, p_77185_8_))
            {
                this.func_85188_a(p_77185_1_);
                this.func_77184_b(p_77185_1_, p_77185_2_, p_77185_4_, p_77185_6_, p_77185_8_);
            }
        }
        else
        {
            // CraftBukkit start - modularize end portal creation
            ChunkCoordinates created = this.createEndPortal(p_77185_2_, p_77185_4_, p_77185_6_);
            p_77185_1_.func_70012_b((double) created.field_71574_a, (double) created.field_71572_b, (double) created.field_71573_c, p_77185_1_.field_70177_z, 0.0F);
            p_77185_1_.field_70159_w = p_77185_1_.field_70181_x = p_77185_1_.field_70179_y = 0.0D;
        }
    }

    // split out from original a(Entity, double, double, double, float) method in order to enable being called from createPortal
    private ChunkCoordinates createEndPortal(double x, double y, double z)
    {
        int i = MathHelper.func_76128_c(x);
        int j = MathHelper.func_76128_c(y) - 1;
        int k = MathHelper.func_76128_c(z);
        // CraftBukkit end
        byte b0 = 1;
        byte b1 = 0;

        for (int l = -2; l <= 2; ++l)
        {
            for (int i1 = -2; i1 <= 2; ++i1)
            {
                for (int j1 = -1; j1 < 3; ++j1)
                {
                    int k1 = i + i1 * b0 + l * b1;
                    int l1 = j + j1;
                    int i2 = k + i1 * b1 - l * b0;
                    boolean flag = j1 < 0;
                    this.field_85192_a.func_72859_e(k1, l1, i2, flag ? Block.field_72089_ap.field_71990_ca : 0);
                }
            }
        }

        // CraftBukkit start
        return new ChunkCoordinates(i, j, k);
    }

    // use logic based on creation to verify end portal
    private ChunkCoordinates findEndPortal(ChunkCoordinates portal)
    {
        int i = portal.field_71574_a;
        int j = portal.field_71572_b - 1;
        int k = portal.field_71573_c;
        byte b0 = 1;
        byte b1 = 0;

        for (int l = -2; l <= 2; ++l)
        {
            for (int i1 = -2; i1 <= 2; ++i1)
            {
                for (int j1 = -1; j1 < 3; ++j1)
                {
                    int k1 = i + i1 * b0 + l * b1;
                    int l1 = j + j1;
                    int i2 = k + i1 * b1 - l * b0;
                    boolean flag = j1 < 0;

                    if (this.field_85192_a.func_72798_a(k1, l1, i2) != (flag ? Block.field_72089_ap.field_71990_ca : 0))
                    {
                        return null;
                    }
                }
            }
        }

        return new ChunkCoordinates(i, j, k);
    }
    // CraftBukkit end

    public boolean func_77184_b(Entity p_77184_1_, double p_77184_2_, double p_77184_4_, double p_77184_6_, float p_77184_8_)
    {
        // CraftBukkit start - modularize portal search process and entity teleportation
        ChunkCoordinates found = this.findPortal(p_77184_1_.field_70165_t, p_77184_1_.field_70163_u, p_77184_1_.field_70161_v, 128);

        if (found == null)
        {
            return false;
        }

        Location exit = new Location(this.field_85192_a.getWorld(), found.field_71574_a, found.field_71572_b, found.field_71573_c, p_77184_8_, p_77184_1_.field_70125_A);
        Vector velocity = p_77184_1_.getBukkitEntity().getVelocity();
        this.adjustExit(p_77184_1_, exit, velocity);
        p_77184_1_.func_70012_b(exit.getX(), exit.getY(), exit.getZ(), exit.getYaw(), exit.getPitch());

        if (p_77184_1_.field_70159_w != velocity.getX() || p_77184_1_.field_70181_x != velocity.getY() || p_77184_1_.field_70179_y != velocity.getZ())
        {
            p_77184_1_.getBukkitEntity().setVelocity(velocity);
        }

        return true;
    }

    public ChunkCoordinates findPortal(double x, double y, double z, int short1)
    {
        if (this.field_85192_a.getWorld().getEnvironment() == org.bukkit.World.Environment.THE_END)
        {
            return this.findEndPortal(this.field_85192_a.field_73011_w.func_76554_h());
        }

        // CraftBukkit end
        double d3 = -1.0D;
        int i = 0;
        int j = 0;
        int k = 0;
        // CraftBukkit start
        int l = MathHelper.func_76128_c(x);
        int i1 = MathHelper.func_76128_c(z);
        // CraftBukkit end
        long j1 = ChunkCoordIntPair.func_77272_a(l, i1);
        boolean flag = true;
        double d4;
        int k1;

        if (this.field_85191_c.func_76161_b(j1))
        {
            PortalPosition portalposition = (PortalPosition)this.field_85191_c.func_76164_a(j1);
            d3 = 0.0D;
            i = portalposition.field_71574_a;
            j = portalposition.field_71572_b;
            k = portalposition.field_71573_c;
            portalposition.field_85087_d = this.field_85192_a.func_82737_E();
            flag = false;
        }
        else
        {
            for (k1 = l - short1; k1 <= l + short1; ++k1)
            {
                double d5 = (double) k1 + 0.5D - x; // CraftBukkit

                for (int l1 = i1 - short1; l1 <= i1 + short1; ++l1)
                {
                    double d6 = (double) l1 + 0.5D - z; // CraftBukkit

                    for (int i2 = this.field_85192_a.func_72940_L() - 1; i2 >= 0; --i2)
                    {
                        if (this.field_85192_a.func_72798_a(k1, i2, l1) == Block.field_72015_be.field_71990_ca)
                        {
                            while (this.field_85192_a.func_72798_a(k1, i2 - 1, l1) == Block.field_72015_be.field_71990_ca)
                            {
                                --i2;
                            }

                            d4 = (double) i2 + 0.5D - y; // CraftBukkit
                            double d7 = d5 * d5 + d4 * d4 + d6 * d6;

                            if (d3 < 0.0D || d7 < d3)
                            {
                                d3 = d7;
                                i = k1;
                                j = i2;
                                k = l1;
                            }
                        }
                    }
                }
            }
        }

        if (d3 >= 0.0D)
        {
            if (flag)
            {
                this.field_85191_c.func_76163_a(j1, new PortalPosition(this, i, j, k, this.field_85192_a.func_82737_E()));
                this.field_85190_d.add(Long.valueOf(j1));
            }

            // CraftBukkit start - moved entity teleportation logic into exit
            return new ChunkCoordinates(i, j, k);
        }
        else
        {
            return null;
        }
    }
    // entity repositioning logic split out from original b method and combined with repositioning logic for The End from original a method
    public void adjustExit(Entity entity, Location position, Vector velocity)
    {
        Location from = position.clone();
        Vector before = velocity.clone();
        int i = position.getBlockX();
        int j = position.getBlockY();
        int k = position.getBlockZ();
        float f = position.getYaw();

        if (this.field_85192_a.getWorld().getEnvironment() == org.bukkit.World.Environment.THE_END)
        {
            // entity.setPositionRotation((double) i, (double) j, (double) k, entity.yaw, 0.0F);
            // entity.motX = entity.motY = entity.motZ = 0.0D;
            position.setPitch(0.0F);
            velocity.setX(0);
            velocity.setY(0);
            velocity.setZ(0);
        }
        else
        {
            double d4;
            int k1;
            // CraftBukkit end
            double d8 = (double)i + 0.5D;
            double d9 = (double)j + 0.5D;
            d4 = (double)k + 0.5D;
            int j2 = -1;

            if (this.field_85192_a.func_72798_a(i - 1, j, k) == Block.field_72015_be.field_71990_ca)
            {
                j2 = 2;
            }

            if (this.field_85192_a.func_72798_a(i + 1, j, k) == Block.field_72015_be.field_71990_ca)
            {
                j2 = 0;
            }

            if (this.field_85192_a.func_72798_a(i, j, k - 1) == Block.field_72015_be.field_71990_ca)
            {
                j2 = 3;
            }

            if (this.field_85192_a.func_72798_a(i, j, k + 1) == Block.field_72015_be.field_71990_ca)
            {
                j2 = 1;
            }

            int k2 = entity.func_82148_at();

            if (j2 > -1)
            {
                int l2 = Direction.field_71578_g[j2];
                int i3 = Direction.field_71583_a[j2];
                int j3 = Direction.field_71581_b[j2];
                int k3 = Direction.field_71583_a[l2];
                int l3 = Direction.field_71581_b[l2];
                boolean flag1 = !this.field_85192_a.func_72799_c(i + i3 + k3, j, k + j3 + l3) || !this.field_85192_a.func_72799_c(i + i3 + k3, j + 1, k + j3 + l3);
                boolean flag2 = !this.field_85192_a.func_72799_c(i + i3, j, k + j3) || !this.field_85192_a.func_72799_c(i + i3, j + 1, k + j3);

                if (flag1 && flag2)
                {
                    j2 = Direction.field_71580_e[j2];
                    l2 = Direction.field_71580_e[l2];
                    i3 = Direction.field_71583_a[j2];
                    j3 = Direction.field_71581_b[j2];
                    k3 = Direction.field_71583_a[l2];
                    l3 = Direction.field_71581_b[l2];
                    k1 = i - k3;
                    d8 -= (double)k3;
                    int i4 = k - l3;
                    d4 -= (double)l3;
                    flag1 = !this.field_85192_a.func_72799_c(k1 + i3 + k3, j, i4 + j3 + l3) || !this.field_85192_a.func_72799_c(k1 + i3 + k3, j + 1, i4 + j3 + l3);
                    flag2 = !this.field_85192_a.func_72799_c(k1 + i3, j, i4 + j3) || !this.field_85192_a.func_72799_c(k1 + i3, j + 1, i4 + j3);
                }

                float f1 = 0.5F;
                float f2 = 0.5F;

                if (!flag1 && flag2)
                {
                    f1 = 1.0F;
                }
                else if (flag1 && !flag2)
                {
                    f1 = 0.0F;
                }
                else if (flag1 && flag2)
                {
                    f2 = 0.0F;
                }

                d8 += (double)((float)k3 * f1 + f2 * (float)i3);
                d4 += (double)((float)l3 * f1 + f2 * (float)j3);
                float f3 = 0.0F;
                float f4 = 0.0F;
                float f5 = 0.0F;
                float f6 = 0.0F;

                if (j2 == k2)
                {
                    f3 = 1.0F;
                    f4 = 1.0F;
                }
                else if (j2 == Direction.field_71580_e[k2])
                {
                    f3 = -1.0F;
                    f4 = -1.0F;
                }
                else if (j2 == Direction.field_71577_f[k2])
                {
                    f5 = 1.0F;
                    f6 = -1.0F;
                }
                else
                {
                    f5 = -1.0F;
                    f6 = 1.0F;
                }

                // CraftBukkit start
                double d10 = velocity.getX();
                double d11 = velocity.getZ();
                // CraftBukkit end
                // CraftBukkit start - adjust position and velocity instances instead of entity
                velocity.setX(d10 * (double) f3 + d11 * (double) f6);
                velocity.setZ(d10 * (double) f5 + d11 * (double) f4);
                f = f - (float)(k2 * 90) + (float)(j2 * 90);
            }
            else
            {
                // entity.motX = entity.motY = entity.motZ = 0.0D;
                velocity.setX(0);
                velocity.setY(0);
                velocity.setZ(0);
            }

            // entity.setPositionRotation(d8, d9, d4, entity.yaw, entity.pitch);
            position.setX(d8);
            position.setY(d9);
            position.setZ(d4);
            position.setYaw(f);
        }

        EntityPortalExitEvent event = new EntityPortalExitEvent(entity.getBukkitEntity(), from, position, before, velocity);
        this.field_85192_a.getServer().getPluginManager().callEvent(event);
        Location to = event.getTo();

        if (event.isCancelled() || to == null || !entity.func_70089_S())
        {
            position = from;
            velocity = before;
        }
        else
        {
            position = to;
            velocity = event.getAfter();
        }

        // CraftBukkit end
    }

    public boolean func_85188_a(Entity p_85188_1_)
    {
        // CraftBukkit start - allow for portal creation to be based on coordinates instead of entity
        return this.createPortal(p_85188_1_.field_70165_t, p_85188_1_.field_70163_u, p_85188_1_.field_70161_v, 16);
    }

    public boolean createPortal(double x, double y, double z, int b0)
    {
        if (this.field_85192_a.getWorld().getEnvironment() == org.bukkit.World.Environment.THE_END)
        {
            this.createEndPortal(x, y, z);
            return true;
        }

        // CraftBukkit end
        double d0 = -1.0D;
        // CraftBukkit start
        int i = MathHelper.func_76128_c(x);
        int j = MathHelper.func_76128_c(y);
        int k = MathHelper.func_76128_c(z);
        // CraftBukkit end
        int l = i;
        int i1 = j;
        int j1 = k;
        int k1 = 0;
        int l1 = this.field_77187_a.nextInt(4);
        int i2;
        double d1;
        double d2;
        int j2;
        int k2;
        int l2;
        int i3;
        int j3;
        int k3;
        int l3;
        int i4;
        int j4;
        int k4;
        double d3;
        double d4;

        for (i2 = i - b0; i2 <= i + b0; ++i2)
        {
            d1 = (double) i2 + 0.5D - x; // CraftBukkit

            for (j2 = k - b0; j2 <= k + b0; ++j2)
            {
                d2 = (double) j2 + 0.5D - z; // CraftBukkit
                label274:

                for (k2 = this.field_85192_a.func_72940_L() - 1; k2 >= 0; --k2)
                {
                    if (this.field_85192_a.func_72799_c(i2, k2, j2))
                    {
                        while (k2 > 0 && this.field_85192_a.func_72799_c(i2, k2 - 1, j2))
                        {
                            --k2;
                        }

                        for (i3 = l1; i3 < l1 + 4; ++i3)
                        {
                            l2 = i3 % 2;
                            k3 = 1 - l2;

                            if (i3 % 4 >= 2)
                            {
                                l2 = -l2;
                                k3 = -k3;
                            }

                            for (j3 = 0; j3 < 3; ++j3)
                            {
                                for (i4 = 0; i4 < 4; ++i4)
                                {
                                    for (l3 = -1; l3 < 4; ++l3)
                                    {
                                        k4 = i2 + (i4 - 1) * l2 + j3 * k3;
                                        j4 = k2 + l3;
                                        int l4 = j2 + (i4 - 1) * k3 - j3 * l2;

                                        if (l3 < 0 && !this.field_85192_a.func_72803_f(k4, j4, l4).func_76220_a() || l3 >= 0 && !this.field_85192_a.func_72799_c(k4, j4, l4))
                                        {
                                            continue label274;
                                        }
                                    }
                                }
                            }

                            d3 = (double) k2 + 0.5D - y; // CraftBukkit
                            d4 = d1 * d1 + d3 * d3 + d2 * d2;

                            if (d0 < 0.0D || d4 < d0)
                            {
                                d0 = d4;
                                l = i2;
                                i1 = k2;
                                j1 = j2;
                                k1 = i3 % 4;
                            }
                        }
                    }
                }
            }
        }

        if (d0 < 0.0D)
        {
            for (i2 = i - b0; i2 <= i + b0; ++i2)
            {
                d1 = (double) i2 + 0.5D - x; // CraftBukkit

                for (j2 = k - b0; j2 <= k + b0; ++j2)
                {
                    d2 = (double) j2 + 0.5D - z; // CraftBukkit
                    label222:

                    for (k2 = this.field_85192_a.func_72940_L() - 1; k2 >= 0; --k2)
                    {
                        if (this.field_85192_a.func_72799_c(i2, k2, j2))
                        {
                            while (k2 > 0 && this.field_85192_a.func_72799_c(i2, k2 - 1, j2))
                            {
                                --k2;
                            }

                            for (i3 = l1; i3 < l1 + 2; ++i3)
                            {
                                l2 = i3 % 2;
                                k3 = 1 - l2;

                                for (j3 = 0; j3 < 4; ++j3)
                                {
                                    for (i4 = -1; i4 < 4; ++i4)
                                    {
                                        l3 = i2 + (j3 - 1) * l2;
                                        k4 = k2 + i4;
                                        j4 = j2 + (j3 - 1) * k3;

                                        if (i4 < 0 && !this.field_85192_a.func_72803_f(l3, k4, j4).func_76220_a() || i4 >= 0 && !this.field_85192_a.func_72799_c(l3, k4, j4))
                                        {
                                            continue label222;
                                        }
                                    }
                                }

                                d3 = (double) k2 + 0.5D - y; // CraftBukkit
                                d4 = d1 * d1 + d3 * d3 + d2 * d2;

                                if (d0 < 0.0D || d4 < d0)
                                {
                                    d0 = d4;
                                    l = i2;
                                    i1 = k2;
                                    j1 = j2;
                                    k1 = i3 % 2;
                                }
                            }
                        }
                    }
                }
            }
        }

        int i5 = l;
        int j5 = i1;
        j2 = j1;
        int k5 = k1 % 2;
        int l5 = 1 - k5;

        if (k1 % 4 >= 2)
        {
            k5 = -k5;
            l5 = -l5;
        }

        boolean flag;

        if (d0 < 0.0D)
        {
            if (i1 < 70)
            {
                i1 = 70;
            }

            if (i1 > this.field_85192_a.func_72940_L() - 10)
            {
                i1 = this.field_85192_a.func_72940_L() - 10;
            }

            j5 = i1;

            for (k2 = -1; k2 <= 1; ++k2)
            {
                for (i3 = 1; i3 < 3; ++i3)
                {
                    for (l2 = -1; l2 < 3; ++l2)
                    {
                        k3 = i5 + (i3 - 1) * k5 + k2 * l5;
                        j3 = j5 + l2;
                        i4 = j2 + (i3 - 1) * l5 - k2 * k5;
                        flag = l2 < 0;
                        this.field_85192_a.func_72859_e(k3, j3, i4, flag ? Block.field_72089_ap.field_71990_ca : 0);
                    }
                }
            }
        }

        for (k2 = 0; k2 < 4; ++k2)
        {
            this.field_85192_a.field_73014_t = true;

            for (i3 = 0; i3 < 4; ++i3)
            {
                for (l2 = -1; l2 < 4; ++l2)
                {
                    k3 = i5 + (i3 - 1) * k5;
                    j3 = j5 + l2;
                    i4 = j2 + (i3 - 1) * l5;
                    flag = i3 == 0 || i3 == 3 || l2 == -1 || l2 == 3;
                    this.field_85192_a.func_72859_e(k3, j3, i4, flag ? Block.field_72089_ap.field_71990_ca : Block.field_72015_be.field_71990_ca);
                }
            }

            this.field_85192_a.field_73014_t = false;

            for (i3 = 0; i3 < 4; ++i3)
            {
                for (l2 = -1; l2 < 4; ++l2)
                {
                    k3 = i5 + (i3 - 1) * k5;
                    j3 = j5 + l2;
                    i4 = j2 + (i3 - 1) * l5;
                    this.field_85192_a.func_72898_h(k3, j3, i4, this.field_85192_a.func_72798_a(k3, j3, i4));
                }
            }
        }

        return true;
    }

    public void func_85189_a(long p_85189_1_)
    {
        if (p_85189_1_ % 100L == 0L)
        {
            Iterator iterator = this.field_85190_d.iterator();
            long j = p_85189_1_ - 600L;

            while (iterator.hasNext())
            {
                Long olong = (Long)iterator.next();
                PortalPosition portalposition = (PortalPosition)this.field_85191_c.func_76164_a(olong.longValue());

                if (portalposition == null || portalposition.field_85087_d < j)
                {
                    iterator.remove();
                    this.field_85191_c.func_76159_d(olong.longValue());
                }
            }
        }
    }
}
