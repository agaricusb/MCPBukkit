package net.minecraft.entity.item;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

// CraftBukkit start
import org.bukkit.Location;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
// CraftBukkit end

public class EntityBoat extends Entity
{
    private boolean field_70279_a;
    private double field_70276_b;
    private int field_70277_c;
    private double field_70274_d;
    private double field_70275_e;
    private double field_70272_f;
    private double field_70273_g;
    private double field_70281_h;

    // CraftBukkit start
    public double maxSpeed = 0.4D;
    public double occupiedDeceleration = 0.2D;
    public double unoccupiedDeceleration = -1;
    public boolean landBoats = false;

    @Override
    public void func_70108_f(Entity entity)
    {
        org.bukkit.entity.Entity hitEntity = (entity == null) ? null : entity.getBukkitEntity();
        VehicleEntityCollisionEvent event = new VehicleEntityCollisionEvent((Vehicle) this.getBukkitEntity(), hitEntity);
        this.field_70170_p.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled())
        {
            return;
        }

        super.func_70108_f(entity);
    }
    // CraftBukkit end

    public EntityBoat(World p_i3534_1_)
    {
        super(p_i3534_1_);
        this.field_70279_a = true;
        this.field_70276_b = 0.07D;
        this.field_70156_m = true;
        this.func_70105_a(1.5F, 0.6F);
        this.field_70129_M = this.field_70131_O / 2.0F;
    }

    protected boolean func_70041_e_()
    {
        return false;
    }

    protected void func_70088_a()
    {
        this.field_70180_af.func_75682_a(17, new Integer(0));
        this.field_70180_af.func_75682_a(18, new Integer(1));
        this.field_70180_af.func_75682_a(19, new Integer(0));
    }

    public AxisAlignedBB func_70114_g(Entity p_70114_1_)
    {
        return p_70114_1_.field_70121_D;
    }

    public AxisAlignedBB func_70046_E()
    {
        return this.field_70121_D;
    }

    public boolean func_70104_M()
    {
        return true;
    }

    public EntityBoat(World p_i3535_1_, double p_i3535_2_, double p_i3535_4_, double p_i3535_6_)
    {
        this(p_i3535_1_);
        this.func_70107_b(p_i3535_2_, p_i3535_4_ + (double)this.field_70129_M, p_i3535_6_);
        this.field_70159_w = 0.0D;
        this.field_70181_x = 0.0D;
        this.field_70179_y = 0.0D;
        this.field_70169_q = p_i3535_2_;
        this.field_70167_r = p_i3535_4_;
        this.field_70166_s = p_i3535_6_;
        this.field_70170_p.getServer().getPluginManager().callEvent(new org.bukkit.event.vehicle.VehicleCreateEvent((Vehicle) this.getBukkitEntity())); // CraftBukkit
    }

    public double func_70042_X()
    {
        return (double)this.field_70131_O * 0.0D - 0.30000001192092896D;
    }

    public boolean func_70097_a(DamageSource p_70097_1_, int p_70097_2_)
    {
        if (this.func_85032_ar())
        {
            return false;
        }
        else if (!this.field_70170_p.field_72995_K && !this.field_70128_L)
        {
            // CraftBukkit start
            Vehicle vehicle = (Vehicle) this.getBukkitEntity();
            org.bukkit.entity.Entity attacker = (p_70097_1_.func_76346_g() == null) ? null : p_70097_1_.func_76346_g().getBukkitEntity();
            VehicleDamageEvent event = new VehicleDamageEvent(vehicle, attacker, p_70097_2_);
            this.field_70170_p.getServer().getPluginManager().callEvent(event);

            if (event.isCancelled())
            {
                return true;
            }

            // i = event.getDamage(); // TODO Why don't we do this?
            // CraftBukkit end
            this.func_70269_c(-this.func_70267_i());
            this.func_70265_b(10);
            this.func_70266_a(this.func_70271_g() + p_70097_2_ * 10);
            this.func_70018_K();

            if (p_70097_1_.func_76346_g() instanceof EntityPlayer && ((EntityPlayer)p_70097_1_.func_76346_g()).field_71075_bZ.field_75098_d)
            {
                this.func_70266_a(100);
            }

            if (this.func_70271_g() > 40)
            {
                // CraftBukkit start
                VehicleDestroyEvent destroyEvent = new VehicleDestroyEvent(vehicle, attacker);
                this.field_70170_p.getServer().getPluginManager().callEvent(destroyEvent);

                if (destroyEvent.isCancelled())
                {
                    this.func_70266_a(40); // Maximize damage so this doesn't get triggered again right away
                    return true;
                }

                // CraftBukkit end

                if (this.field_70153_n != null)
                {
                    this.field_70153_n.func_70078_a(this);
                }

                this.func_70054_a(Item.field_77769_aE.field_77779_bT, 1, 0.0F);
                this.func_70106_y();
            }

            return true;
        }
        else
        {
            return true;
        }
    }

    public boolean func_70067_L()
    {
        return !this.field_70128_L;
    }

    public void func_70071_h_()
    {
        // CraftBukkit start
        double prevX = this.field_70165_t;
        double prevY = this.field_70163_u;
        double prevZ = this.field_70161_v;
        float prevYaw = this.field_70177_z;
        float prevPitch = this.field_70125_A;
        // CraftBukkit end
        super.func_70071_h_();

        if (this.func_70268_h() > 0)
        {
            this.func_70265_b(this.func_70268_h() - 1);
        }

        if (this.func_70271_g() > 0)
        {
            this.func_70266_a(this.func_70271_g() - 1);
        }

        this.field_70169_q = this.field_70165_t;
        this.field_70167_r = this.field_70163_u;
        this.field_70166_s = this.field_70161_v;
        byte b0 = 5;
        double d0 = 0.0D;

        for (int i = 0; i < b0; ++i)
        {
            double d1 = this.field_70121_D.field_72338_b + (this.field_70121_D.field_72337_e - this.field_70121_D.field_72338_b) * (double)(i + 0) / (double)b0 - 0.125D;
            double d2 = this.field_70121_D.field_72338_b + (this.field_70121_D.field_72337_e - this.field_70121_D.field_72338_b) * (double)(i + 1) / (double)b0 - 0.125D;
            AxisAlignedBB axisalignedbb = AxisAlignedBB.func_72332_a().func_72299_a(this.field_70121_D.field_72340_a, d1, this.field_70121_D.field_72339_c, this.field_70121_D.field_72336_d, d2, this.field_70121_D.field_72334_f);

            if (this.field_70170_p.func_72830_b(axisalignedbb, Material.field_76244_g))
            {
                d0 += 1.0D / (double)b0;
            }
        }

        double d3 = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
        double d4;
        double d5;

        if (d3 > 0.26249999999999996D)
        {
            d4 = Math.cos((double)this.field_70177_z * Math.PI / 180.0D);
            d5 = Math.sin((double)this.field_70177_z * Math.PI / 180.0D);

            for (int j = 0; (double)j < 1.0D + d3 * 60.0D; ++j)
            {
                double d6 = (double)(this.field_70146_Z.nextFloat() * 2.0F - 1.0F);
                double d7 = (double)(this.field_70146_Z.nextInt(2) * 2 - 1) * 0.7D;
                double d8;
                double d9;

                if (this.field_70146_Z.nextBoolean())
                {
                    d8 = this.field_70165_t - d4 * d6 * 0.8D + d5 * d7;
                    d9 = this.field_70161_v - d5 * d6 * 0.8D - d4 * d7;
                    this.field_70170_p.func_72869_a("splash", d8, this.field_70163_u - 0.125D, d9, this.field_70159_w, this.field_70181_x, this.field_70179_y);
                }
                else
                {
                    d8 = this.field_70165_t + d4 + d5 * d6 * 0.7D;
                    d9 = this.field_70161_v + d5 - d4 * d6 * 0.7D;
                    this.field_70170_p.func_72869_a("splash", d8, this.field_70163_u - 0.125D, d9, this.field_70159_w, this.field_70181_x, this.field_70179_y);
                }
            }
        }

        double d10;
        double d11;

        if (this.field_70170_p.field_72995_K && this.field_70279_a)
        {
            if (this.field_70277_c > 0)
            {
                d4 = this.field_70165_t + (this.field_70274_d - this.field_70165_t) / (double)this.field_70277_c;
                d5 = this.field_70163_u + (this.field_70275_e - this.field_70163_u) / (double)this.field_70277_c;
                d10 = this.field_70161_v + (this.field_70272_f - this.field_70161_v) / (double) this.field_70277_c;
                d11 = MathHelper.func_76138_g(this.field_70273_g - (double) this.field_70177_z);
                this.field_70177_z = (float)((double) this.field_70177_z + d11 / (double) this.field_70277_c);
                this.field_70125_A = (float)((double)this.field_70125_A + (this.field_70281_h - (double)this.field_70125_A) / (double)this.field_70277_c);
                --this.field_70277_c;
                this.func_70107_b(d4, d5, d10);
                this.func_70101_b(this.field_70177_z, this.field_70125_A);
            }
            else
            {
                d4 = this.field_70165_t + this.field_70159_w;
                d5 = this.field_70163_u + this.field_70181_x;
                d10 = this.field_70161_v + this.field_70179_y;
                this.func_70107_b(d4, d5, d10);

                if (this.field_70122_E)
                {
                    this.field_70159_w *= 0.5D;
                    this.field_70181_x *= 0.5D;
                    this.field_70179_y *= 0.5D;
                }

                this.field_70159_w *= 0.9900000095367432D;
                this.field_70181_x *= 0.949999988079071D;
                this.field_70179_y *= 0.9900000095367432D;
            }
        }
        else
        {
            if (d0 < 1.0D)
            {
                d4 = d0 * 2.0D - 1.0D;
                this.field_70181_x += 0.03999999910593033D * d4;
            }
            else
            {
                if (this.field_70181_x < 0.0D)
                {
                    this.field_70181_x /= 2.0D;
                }

                this.field_70181_x += 0.007000000216066837D;
            }

            if (this.field_70153_n != null)
            {
                this.field_70159_w += this.field_70153_n.field_70159_w * this.field_70276_b;
                this.field_70179_y += this.field_70153_n.field_70179_y * this.field_70276_b;
            }
            // CraftBukkit start - block not in vanilla
            else if (unoccupiedDeceleration >= 0)
            {
                this.field_70159_w *= unoccupiedDeceleration;
                this.field_70179_y *= unoccupiedDeceleration;

                // Kill lingering speed
                if (field_70159_w <= 0.00001)
                {
                    field_70159_w = 0;
                }

                if (field_70179_y <= 0.00001)
                {
                    field_70179_y = 0;
                }
            }

            // CraftBukkit end
            d4 = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);

            if (d4 > 0.35D)
            {
                d5 = 0.35D / d4;
                this.field_70159_w *= d5;
                this.field_70179_y *= d5;
                d4 = 0.35D;
            }

            if (d4 > d3 && this.field_70276_b < 0.35D)
            {
                this.field_70276_b += (0.35D - this.field_70276_b) / 35.0D;

                if (this.field_70276_b > 0.35D)
                {
                    this.field_70276_b = 0.35D;
                }
            }
            else
            {
                this.field_70276_b -= (this.field_70276_b - 0.07D) / 35.0D;

                if (this.field_70276_b < 0.07D)
                {
                    this.field_70276_b = 0.07D;
                }
            }

            if (this.field_70122_E && !this.landBoats)   // CraftBukkit
            {
                this.field_70159_w *= 0.5D;
                this.field_70181_x *= 0.5D;
                this.field_70179_y *= 0.5D;
            }

            this.func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);

            if (this.field_70123_F && d3 > 0.2D)
            {
                if (!this.field_70170_p.field_72995_K)
                {
                    // CraftBukkit start
                    Vehicle vehicle = (Vehicle) this.getBukkitEntity();
                    VehicleDestroyEvent destroyEvent = new VehicleDestroyEvent(vehicle, null);
                    this.field_70170_p.getServer().getPluginManager().callEvent(destroyEvent);

                    if (!destroyEvent.isCancelled())
                    {
                        this.func_70106_y();
                        int k;

                        for (k = 0; k < 3; ++k)
                        {
                            this.func_70054_a(Block.field_71988_x.field_71990_ca, 1, 0.0F);
                        }

                        for (k = 0; k < 2; ++k)
                        {
                            this.func_70054_a(Item.field_77669_D.field_77779_bT, 1, 0.0F);
                        }
                    }

                    // CraftBukkit end
                }
            }
            else
            {
                this.field_70159_w *= 0.9900000095367432D;
                this.field_70181_x *= 0.949999988079071D;
                this.field_70179_y *= 0.9900000095367432D;
            }

            this.field_70125_A = 0.0F;
            d5 = (double)this.field_70177_z;
            d10 = this.field_70169_q - this.field_70165_t;
            d11 = this.field_70166_s - this.field_70161_v;

            if (d10 * d10 + d11 * d11 > 0.001D)
            {
                d5 = (double)((float)(Math.atan2(d11, d10) * 180.0D / Math.PI));
            }

            double d12 = MathHelper.func_76138_g(d5 - (double)this.field_70177_z);

            if (d12 > 20.0D)
            {
                d12 = 20.0D;
            }

            if (d12 < -20.0D)
            {
                d12 = -20.0D;
            }

            this.field_70177_z = (float)((double)this.field_70177_z + d12);
            this.func_70101_b(this.field_70177_z, this.field_70125_A);
            // CraftBukkit start
            org.bukkit.Server server = this.field_70170_p.getServer();
            org.bukkit.World bworld = this.field_70170_p.getWorld();
            Location from = new Location(bworld, prevX, prevY, prevZ, prevYaw, prevPitch);
            Location to = new Location(bworld, this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, this.field_70125_A);
            Vehicle vehicle = (Vehicle) this.getBukkitEntity();
            server.getPluginManager().callEvent(new org.bukkit.event.vehicle.VehicleUpdateEvent(vehicle));

            if (!from.equals(to))
            {
                VehicleMoveEvent event = new VehicleMoveEvent(vehicle, from, to);
                server.getPluginManager().callEvent(event);
            }

            // CraftBukkit end

            if (!this.field_70170_p.field_72995_K)
            {
                List list = this.field_70170_p.func_72839_b(this, this.field_70121_D.func_72314_b(0.20000000298023224D, 0.0D, 0.20000000298023224D));
                int l;

                if (list != null && !list.isEmpty())
                {
                    for (l = 0; l < list.size(); ++l)
                    {
                        Entity entity = (Entity)list.get(l);

                        if (entity != this.field_70153_n && entity.func_70104_M() && entity instanceof EntityBoat)
                        {
                            entity.func_70108_f(this);
                        }
                    }
                }

                for (l = 0; l < 4; ++l)
                {
                    int i1 = MathHelper.func_76128_c(this.field_70165_t + ((double)(l % 2) - 0.5D) * 0.8D);
                    int j1 = MathHelper.func_76128_c(this.field_70161_v + ((double)(l / 2) - 0.5D) * 0.8D);

                    for (int k1 = 0; k1 < 2; ++k1)
                    {
                        int l1 = MathHelper.func_76128_c(this.field_70163_u) + k1;
                        int i2 = this.field_70170_p.func_72798_a(i1, l1, j1);
                        int j2 = this.field_70170_p.func_72805_g(i1, l1, j1);

                        if (i2 == Block.field_72037_aS.field_71990_ca)
                        {
                            this.field_70170_p.func_72859_e(i1, l1, j1, 0);
                        }
                        else if (i2 == Block.field_71991_bz.field_71990_ca)
                        {
                            Block.field_71991_bz.func_71914_a(this.field_70170_p, i1, l1, j1, j2, 0.3F, 0);
                            this.field_70170_p.func_72859_e(i1, l1, j1, 0);
                        }
                    }
                }

                if (this.field_70153_n != null && this.field_70153_n.field_70128_L)
                {
                    this.field_70153_n.field_70154_o = null; // CraftBukkit
                    this.field_70153_n = null;
                }
            }
        }
    }

    public void func_70043_V()
    {
        if (this.field_70153_n != null)
        {
            double d0 = Math.cos((double)this.field_70177_z * Math.PI / 180.0D) * 0.4D;
            double d1 = Math.sin((double)this.field_70177_z * Math.PI / 180.0D) * 0.4D;
            this.field_70153_n.func_70107_b(this.field_70165_t + d0, this.field_70163_u + this.func_70042_X() + this.field_70153_n.func_70033_W(), this.field_70161_v + d1);
        }
    }

    protected void func_70014_b(NBTTagCompound p_70014_1_) {}

    protected void func_70037_a(NBTTagCompound p_70037_1_) {}

    public boolean func_70085_c(EntityPlayer p_70085_1_)
    {
        if (this.field_70153_n != null && this.field_70153_n instanceof EntityPlayer && this.field_70153_n != p_70085_1_)
        {
            return true;
        }
        else
        {
            if (!this.field_70170_p.field_72995_K)
            {
                p_70085_1_.func_70078_a(this);
            }

            return true;
        }
    }

    public void func_70266_a(int p_70266_1_)
    {
        this.field_70180_af.func_75692_b(19, Integer.valueOf(p_70266_1_));
    }

    public int func_70271_g()
    {
        return this.field_70180_af.func_75679_c(19);
    }

    public void func_70265_b(int p_70265_1_)
    {
        this.field_70180_af.func_75692_b(17, Integer.valueOf(p_70265_1_));
    }

    public int func_70268_h()
    {
        return this.field_70180_af.func_75679_c(17);
    }

    public void func_70269_c(int p_70269_1_)
    {
        this.field_70180_af.func_75692_b(18, Integer.valueOf(p_70269_1_));
    }

    public int func_70267_i()
    {
        return this.field_70180_af.func_75679_c(18);
    }
}
