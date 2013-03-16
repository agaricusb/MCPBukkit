package net.minecraft.entity.item;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityMinecartMobSpawner;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

// CraftBukkit start
import org.bukkit.Location;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.bukkit.util.Vector;
import org.bukkit.craftbukkit.entity.CraftHumanEntity;
// CraftBukkit end

public abstract class EntityMinecart extends Entity
{
    private boolean field_70499_f;
    private final IUpdatePlayerListBox field_82344_g;
    private String field_94102_c;
    private static final int[][][] field_70500_g = new int[][][] {{{0, 0, -1}, {0, 0, 1}}, {{ -1, 0, 0}, {1, 0, 0}}, {{ -1, -1, 0}, {1, 0, 0}}, {{ -1, 0, 0}, {1, -1, 0}}, {{0, 0, -1}, {0, -1, 1}}, {{0, -1, -1}, {0, 0, 1}}, {{0, 0, 1}, {1, 0, 0}}, {{0, 0, 1}, { -1, 0, 0}}, {{0, 0, -1}, { -1, 0, 0}}, {{0, 0, -1}, {1, 0, 0}}};
    private int field_70510_h;
    private double field_70511_i;
    private double field_70509_j;
    private double field_70514_an;
    private double field_70512_ao;
    private double field_70513_ap;

    // CraftBukkit start
    public boolean slowWhenEmpty = true;
    private double derailedX = 0.5;
    private double derailedY = 0.5;
    private double derailedZ = 0.5;
    private double flyingX = 0.95;
    private double flyingY = 0.95;
    private double flyingZ = 0.95;
    public double maxSpeed = 0.4D;
    // CraftBukkit end

    public EntityMinecart(World p_i3541_1_)
    {
        super(p_i3541_1_);
        this.field_70499_f = false;
        this.field_70156_m = true;
        this.func_70105_a(0.98F, 0.7F);
        this.field_70129_M = this.field_70131_O / 2.0F;
        this.field_82344_g = p_i3541_1_ != null ? p_i3541_1_.func_82735_a(this) : null;
    }

    public static EntityMinecart func_94090_a(World p_94090_0_, double p_94090_1_, double p_94090_3_, double p_94090_5_, int p_94090_7_)
    {
        switch (p_94090_7_)
        {
            case 1:
                return new EntityMinecartChest(p_94090_0_, p_94090_1_, p_94090_3_, p_94090_5_);

            case 2:
                return new EntityMinecartFurnace(p_94090_0_, p_94090_1_, p_94090_3_, p_94090_5_);

            case 3:
                return new EntityMinecartTNT(p_94090_0_, p_94090_1_, p_94090_3_, p_94090_5_);

            case 4:
                return new EntityMinecartMobSpawner(p_94090_0_, p_94090_1_, p_94090_3_, p_94090_5_);

            case 5:
                return new EntityMinecartHopper(p_94090_0_, p_94090_1_, p_94090_3_, p_94090_5_);

            default:
                return new EntityMinecartEmpty(p_94090_0_, p_94090_1_, p_94090_3_, p_94090_5_);
        }
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
        this.field_70180_af.func_75682_a(20, new Integer(0));
        this.field_70180_af.func_75682_a(21, new Integer(6));
        this.field_70180_af.func_75682_a(22, Byte.valueOf((byte)0));
    }

    public AxisAlignedBB func_70114_g(Entity p_70114_1_)
    {
        return p_70114_1_.func_70104_M() ? p_70114_1_.field_70121_D : null;
    }

    public AxisAlignedBB func_70046_E()
    {
        return null;
    }

    public boolean func_70104_M()
    {
        return true;
    }

    public EntityMinecart(World p_i9021_1_, double p_i9021_2_, double p_i9021_4_, double p_i9021_6_)
    {
        this(p_i9021_1_);
        this.func_70107_b(p_i9021_2_, p_i9021_4_ + (double)this.field_70129_M, p_i9021_6_);
        this.field_70159_w = 0.0D;
        this.field_70181_x = 0.0D;
        this.field_70179_y = 0.0D;
        this.field_70169_q = p_i9021_2_;
        this.field_70167_r = p_i9021_4_;
        this.field_70166_s = p_i9021_6_;
        this.field_70170_p.getServer().getPluginManager().callEvent(new org.bukkit.event.vehicle.VehicleCreateEvent((Vehicle) this.getBukkitEntity())); // CraftBukkit
    }

    public double func_70042_X()
    {
        return (double)this.field_70131_O * 0.0D - 0.30000001192092896D;
    }

    public boolean func_70097_a(DamageSource p_70097_1_, int p_70097_2_)
    {
        if (!this.field_70170_p.field_72995_K && !this.field_70128_L)
        {
            if (this.func_85032_ar())
            {
                return false;
            }
            else
            {
                // CraftBukkit start
                Vehicle vehicle = (Vehicle) this.getBukkitEntity();
                org.bukkit.entity.Entity passenger = (p_70097_1_.func_76346_g() == null) ? null : p_70097_1_.func_76346_g().getBukkitEntity();
                VehicleDamageEvent event = new VehicleDamageEvent(vehicle, passenger, p_70097_2_);
                this.field_70170_p.getServer().getPluginManager().callEvent(event);

                if (event.isCancelled())
                {
                    return true;
                }

                p_70097_2_ = event.getDamage();
                // CraftBukkit end
                this.func_70494_i(-this.func_70493_k());
                this.func_70497_h(10);
                this.func_70018_K();
                this.func_70492_c(this.func_70491_i() + p_70097_2_ * 10);
                boolean flag = p_70097_1_.func_76346_g() instanceof EntityPlayer && ((EntityPlayer)p_70097_1_.func_76346_g()).field_71075_bZ.field_75098_d;

                if (flag || this.func_70491_i() > 40)
                {
                    if (this.field_70153_n != null)
                    {
                        this.field_70153_n.func_70078_a(this);
                    }

                    // CraftBukkit start
                    VehicleDestroyEvent destroyEvent = new VehicleDestroyEvent(vehicle, passenger);
                    this.field_70170_p.getServer().getPluginManager().callEvent(destroyEvent);

                    if (destroyEvent.isCancelled())
                    {
                        this.func_70492_c(40); // Maximize damage so this doesn't get triggered again right away
                        return true;
                    }

                    // CraftBukkit end

                    if (flag && !this.func_94042_c())
                    {
                        this.func_70106_y();
                    }
                    else
                    {
                        this.func_94095_a(p_70097_1_);
                    }
                }

                return true;
            }
        }
        else
        {
            return true;
        }
    }

    public void func_94095_a(DamageSource p_94095_1_)
    {
        this.func_70106_y();
        ItemStack itemstack = new ItemStack(Item.field_77773_az, 1);

        if (this.field_94102_c != null)
        {
            itemstack.func_82834_c(this.field_94102_c);
        }

        this.func_70099_a(itemstack, 0.0F);
    }

    public boolean func_70067_L()
    {
        return !this.field_70128_L;
    }

    public void func_70106_y()
    {
        super.func_70106_y();

        if (this.field_82344_g != null)
        {
            this.field_82344_g.func_73660_a();
        }
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

        if (this.field_82344_g != null)
        {
            this.field_82344_g.func_73660_a();
        }

        if (this.func_70496_j() > 0)
        {
            this.func_70497_h(this.func_70496_j() - 1);
        }

        if (this.func_70491_i() > 0)
        {
            this.func_70492_c(this.func_70491_i() - 1);
        }

        if (this.field_70163_u < -64.0D)
        {
            this.func_70076_C();
        }

        int i;

        if (!this.field_70170_p.field_72995_K && this.field_70170_p instanceof WorldServer)
        {
            this.field_70170_p.field_72984_F.func_76320_a("portal");
            MinecraftServer minecraftserver = ((WorldServer)this.field_70170_p).func_73046_m();
            i = this.func_82145_z();

            if (this.field_71087_bX)
            {
                if (true || minecraftserver.func_71255_r())   // CraftBukkit - multi-world should still allow teleport even if default vanilla nether disabled
                {
                    if (this.field_70154_o == null && this.field_82153_h++ >= i)
                    {
                        this.field_82153_h = i;
                        this.field_71088_bW = this.func_82147_ab();
                        byte b0;

                        if (this.field_70170_p.field_73011_w.field_76574_g == -1)
                        {
                            b0 = 0;
                        }
                        else
                        {
                            b0 = -1;
                        }

                        this.func_71027_c(b0);
                    }

                    this.field_71087_bX = false;
                }
            }
            else
            {
                if (this.field_82153_h > 0)
                {
                    this.field_82153_h -= 4;
                }

                if (this.field_82153_h < 0)
                {
                    this.field_82153_h = 0;
                }
            }

            if (this.field_71088_bW > 0)
            {
                --this.field_71088_bW;
            }

            this.field_70170_p.field_72984_F.func_76319_b();
        }

        if (this.field_70170_p.field_72995_K)
        {
            if (this.field_70510_h > 0)
            {
                double d0 = this.field_70165_t + (this.field_70511_i - this.field_70165_t) / (double)this.field_70510_h;
                double d1 = this.field_70163_u + (this.field_70509_j - this.field_70163_u) / (double)this.field_70510_h;
                double d2 = this.field_70161_v + (this.field_70514_an - this.field_70161_v) / (double)this.field_70510_h;
                double d3 = MathHelper.func_76138_g(this.field_70512_ao - (double)this.field_70177_z);
                this.field_70177_z = (float)((double)this.field_70177_z + d3 / (double)this.field_70510_h);
                this.field_70125_A = (float)((double)this.field_70125_A + (this.field_70513_ap - (double)this.field_70125_A) / (double)this.field_70510_h);
                --this.field_70510_h;
                this.func_70107_b(d0, d1, d2);
                this.func_70101_b(this.field_70177_z, this.field_70125_A);
            }
            else
            {
                this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
                this.func_70101_b(this.field_70177_z, this.field_70125_A);
            }
        }
        else
        {
            this.field_70169_q = this.field_70165_t;
            this.field_70167_r = this.field_70163_u;
            this.field_70166_s = this.field_70161_v;
            this.field_70181_x -= 0.03999999910593033D;
            int j = MathHelper.func_76128_c(this.field_70165_t);
            i = MathHelper.func_76128_c(this.field_70163_u);
            int k = MathHelper.func_76128_c(this.field_70161_v);

            if (BlockRailBase.func_72180_d_(this.field_70170_p, j, i - 1, k))
            {
                --i;
            }

            double d4 = this.maxSpeed; // CraftBukkit
            double d5 = 0.0078125D;
            int l = this.field_70170_p.func_72798_a(j, i, k);

            if (BlockRailBase.func_72184_d(l))
            {
                int i1 = this.field_70170_p.func_72805_g(j, i, k);
                this.func_94091_a(j, i, k, d4, d5, l, i1);

                if (l == Block.field_94337_cv.field_71990_ca)
                {
                    this.func_96095_a(j, i, k, (i1 & 8) != 0);
                }
            }
            else
            {
                this.func_94088_b(d4);
            }

            this.func_70017_D();
            this.field_70125_A = 0.0F;
            double d6 = this.field_70169_q - this.field_70165_t;
            double d7 = this.field_70166_s - this.field_70161_v;

            if (d6 * d6 + d7 * d7 > 0.001D)
            {
                this.field_70177_z = (float)(Math.atan2(d7, d6) * 180.0D / Math.PI);

                if (this.field_70499_f)
                {
                    this.field_70177_z += 180.0F;
                }
            }

            double d8 = (double)MathHelper.func_76142_g(this.field_70177_z - this.field_70126_B);

            if (d8 < -170.0D || d8 >= 170.0D)
            {
                this.field_70177_z += 180.0F;
                this.field_70499_f = !this.field_70499_f;
            }

            this.func_70101_b(this.field_70177_z, this.field_70125_A);
            // CraftBukkit start
            org.bukkit.World bworld = this.field_70170_p.getWorld();
            Location from = new Location(bworld, prevX, prevY, prevZ, prevYaw, prevPitch);
            Location to = new Location(bworld, this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, this.field_70125_A);
            Vehicle vehicle = (Vehicle) this.getBukkitEntity();
            this.field_70170_p.getServer().getPluginManager().callEvent(new org.bukkit.event.vehicle.VehicleUpdateEvent(vehicle));

            if (!from.equals(to))
            {
                this.field_70170_p.getServer().getPluginManager().callEvent(new org.bukkit.event.vehicle.VehicleMoveEvent(vehicle, from, to));
            }

            // CraftBukkit end
            List list = this.field_70170_p.func_72839_b(this, this.field_70121_D.func_72314_b(0.20000000298023224D, 0.0D, 0.20000000298023224D));

            if (list != null && !list.isEmpty())
            {
                for (int j1 = 0; j1 < list.size(); ++j1)
                {
                    Entity entity = (Entity)list.get(j1);

                    if (entity != this.field_70153_n && entity.func_70104_M() && entity instanceof EntityMinecart)
                    {
                        entity.func_70108_f(this);
                    }
                }
            }

            if (this.field_70153_n != null && this.field_70153_n.field_70128_L)
            {
                if (this.field_70153_n.field_70154_o == this)
                {
                    this.field_70153_n.field_70154_o = null;
                }

                this.field_70153_n = null;
            }
        }
    }

    public void func_96095_a(int p_96095_1_, int p_96095_2_, int p_96095_3_, boolean p_96095_4_) {}

    protected void func_94088_b(double p_94088_1_)
    {
        if (this.field_70159_w < -p_94088_1_)
        {
            this.field_70159_w = -p_94088_1_;
        }

        if (this.field_70159_w > p_94088_1_)
        {
            this.field_70159_w = p_94088_1_;
        }

        if (this.field_70179_y < -p_94088_1_)
        {
            this.field_70179_y = -p_94088_1_;
        }

        if (this.field_70179_y > p_94088_1_)
        {
            this.field_70179_y = p_94088_1_;
        }

        if (this.field_70122_E)
        {
            // CraftBukkit start
            this.field_70159_w *= this.derailedX;
            this.field_70181_x *= this.derailedY;
            this.field_70179_y *= this.derailedZ;
            // CraftBukkit end
        }

        this.func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);

        if (!this.field_70122_E)
        {
            // CraftBukkit start
            this.field_70159_w *= this.flyingX;
            this.field_70181_x *= this.flyingY;
            this.field_70179_y *= this.flyingZ;
            // CraftBukkit end
        }
    }

    protected void func_94091_a(int p_94091_1_, int p_94091_2_, int p_94091_3_, double p_94091_4_, double p_94091_6_, int p_94091_8_, int p_94091_9_)
    {
        this.field_70143_R = 0.0F;
        Vec3 vec3 = this.func_70489_a(this.field_70165_t, this.field_70163_u, this.field_70161_v);
        this.field_70163_u = (double)p_94091_2_;
        boolean flag = false;
        boolean flag1 = false;

        if (p_94091_8_ == Block.field_71954_T.field_71990_ca)
        {
            flag = (p_94091_9_ & 8) != 0;
            flag1 = !flag;
        }

        if (((BlockRailBase)Block.field_71973_m[p_94091_8_]).func_72183_n())
        {
            p_94091_9_ &= 7;
        }

        if (p_94091_9_ >= 2 && p_94091_9_ <= 5)
        {
            this.field_70163_u = (double)(p_94091_2_ + 1);
        }

        if (p_94091_9_ == 2)
        {
            this.field_70159_w -= p_94091_6_;
        }

        if (p_94091_9_ == 3)
        {
            this.field_70159_w += p_94091_6_;
        }

        if (p_94091_9_ == 4)
        {
            this.field_70179_y += p_94091_6_;
        }

        if (p_94091_9_ == 5)
        {
            this.field_70179_y -= p_94091_6_;
        }

        int[][] aint = field_70500_g[p_94091_9_];
        double d2 = (double)(aint[1][0] - aint[0][0]);
        double d3 = (double)(aint[1][2] - aint[0][2]);
        double d4 = Math.sqrt(d2 * d2 + d3 * d3);
        double d5 = this.field_70159_w * d2 + this.field_70179_y * d3;

        if (d5 < 0.0D)
        {
            d2 = -d2;
            d3 = -d3;
        }

        double d6 = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);

        if (d6 > 2.0D)
        {
            d6 = 2.0D;
        }

        this.field_70159_w = d6 * d2 / d4;
        this.field_70179_y = d6 * d3 / d4;
        double d7;
        double d8;

        if (this.field_70153_n != null)
        {
            d7 = this.field_70153_n.field_70159_w * this.field_70153_n.field_70159_w + this.field_70153_n.field_70179_y * this.field_70153_n.field_70179_y;
            d8 = this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y;

            if (d7 > 1.0E-4D && d8 < 0.01D)
            {
                this.field_70159_w += this.field_70153_n.field_70159_w * 0.1D;
                this.field_70179_y += this.field_70153_n.field_70179_y * 0.1D;
                flag1 = false;
            }
        }

        if (flag1)
        {
            d7 = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);

            if (d7 < 0.03D)
            {
                this.field_70159_w *= 0.0D;
                this.field_70181_x *= 0.0D;
                this.field_70179_y *= 0.0D;
            }
            else
            {
                this.field_70159_w *= 0.5D;
                this.field_70181_x *= 0.0D;
                this.field_70179_y *= 0.5D;
            }
        }

        d7 = 0.0D;
        d8 = (double)p_94091_1_ + 0.5D + (double)aint[0][0] * 0.5D;
        double d9 = (double)p_94091_3_ + 0.5D + (double)aint[0][2] * 0.5D;
        double d10 = (double)p_94091_1_ + 0.5D + (double)aint[1][0] * 0.5D;
        double d11 = (double)p_94091_3_ + 0.5D + (double)aint[1][2] * 0.5D;
        d2 = d10 - d8;
        d3 = d11 - d9;
        double d12;
        double d13;

        if (d2 == 0.0D)
        {
            this.field_70165_t = (double)p_94091_1_ + 0.5D;
            d7 = this.field_70161_v - (double)p_94091_3_;
        }
        else if (d3 == 0.0D)
        {
            this.field_70161_v = (double)p_94091_3_ + 0.5D;
            d7 = this.field_70165_t - (double)p_94091_1_;
        }
        else
        {
            d12 = this.field_70165_t - d8;
            d13 = this.field_70161_v - d9;
            d7 = (d12 * d2 + d13 * d3) * 2.0D;
        }

        this.field_70165_t = d8 + d2 * d7;
        this.field_70161_v = d9 + d3 * d7;
        this.func_70107_b(this.field_70165_t, this.field_70163_u + (double)this.field_70129_M, this.field_70161_v);
        d12 = this.field_70159_w;
        d13 = this.field_70179_y;

        if (this.field_70153_n != null)
        {
            d12 *= 0.75D;
            d13 *= 0.75D;
        }

        if (d12 < -p_94091_4_)
        {
            d12 = -p_94091_4_;
        }

        if (d12 > p_94091_4_)
        {
            d12 = p_94091_4_;
        }

        if (d13 < -p_94091_4_)
        {
            d13 = -p_94091_4_;
        }

        if (d13 > p_94091_4_)
        {
            d13 = p_94091_4_;
        }

        this.func_70091_d(d12, 0.0D, d13);

        if (aint[0][1] != 0 && MathHelper.func_76128_c(this.field_70165_t) - p_94091_1_ == aint[0][0] && MathHelper.func_76128_c(this.field_70161_v) - p_94091_3_ == aint[0][2])
        {
            this.func_70107_b(this.field_70165_t, this.field_70163_u + (double)aint[0][1], this.field_70161_v);
        }
        else if (aint[1][1] != 0 && MathHelper.func_76128_c(this.field_70165_t) - p_94091_1_ == aint[1][0] && MathHelper.func_76128_c(this.field_70161_v) - p_94091_3_ == aint[1][2])
        {
            this.func_70107_b(this.field_70165_t, this.field_70163_u + (double)aint[1][1], this.field_70161_v);
        }

        this.func_94101_h();
        Vec3 vec31 = this.func_70489_a(this.field_70165_t, this.field_70163_u, this.field_70161_v);

        if (vec31 != null && vec3 != null)
        {
            double d14 = (vec3.field_72448_b - vec31.field_72448_b) * 0.05D;
            d6 = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);

            if (d6 > 0.0D)
            {
                this.field_70159_w = this.field_70159_w / d6 * (d6 + d14);
                this.field_70179_y = this.field_70179_y / d6 * (d6 + d14);
            }

            this.func_70107_b(this.field_70165_t, vec31.field_72448_b, this.field_70161_v);
        }

        int j1 = MathHelper.func_76128_c(this.field_70165_t);
        int k1 = MathHelper.func_76128_c(this.field_70161_v);

        if (j1 != p_94091_1_ || k1 != p_94091_3_)
        {
            d6 = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
            this.field_70159_w = d6 * (double)(j1 - p_94091_1_);
            this.field_70179_y = d6 * (double)(k1 - p_94091_3_);
        }

        if (flag)
        {
            double d15 = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);

            if (d15 > 0.01D)
            {
                double d16 = 0.06D;
                this.field_70159_w += this.field_70159_w / d15 * d16;
                this.field_70179_y += this.field_70179_y / d15 * d16;
            }
            else if (p_94091_9_ == 1)
            {
                if (this.field_70170_p.func_72809_s(p_94091_1_ - 1, p_94091_2_, p_94091_3_))
                {
                    this.field_70159_w = 0.02D;
                }
                else if (this.field_70170_p.func_72809_s(p_94091_1_ + 1, p_94091_2_, p_94091_3_))
                {
                    this.field_70159_w = -0.02D;
                }
            }
            else if (p_94091_9_ == 0)
            {
                if (this.field_70170_p.func_72809_s(p_94091_1_, p_94091_2_, p_94091_3_ - 1))
                {
                    this.field_70179_y = 0.02D;
                }
                else if (this.field_70170_p.func_72809_s(p_94091_1_, p_94091_2_, p_94091_3_ + 1))
                {
                    this.field_70179_y = -0.02D;
                }
            }
        }
    }

    protected void func_94101_h()
    {
        if (this.field_70153_n != null || !this.slowWhenEmpty)   // CraftBukkit
        {
            this.field_70159_w *= 0.996999979019165D;
            this.field_70181_x *= 0.0D;
            this.field_70179_y *= 0.996999979019165D;
        }
        else
        {
            this.field_70159_w *= 0.9599999785423279D;
            this.field_70181_x *= 0.0D;
            this.field_70179_y *= 0.9599999785423279D;
        }
    }

    public Vec3 func_70489_a(double p_70489_1_, double p_70489_3_, double p_70489_5_)
    {
        int i = MathHelper.func_76128_c(p_70489_1_);
        int j = MathHelper.func_76128_c(p_70489_3_);
        int k = MathHelper.func_76128_c(p_70489_5_);

        if (BlockRailBase.func_72180_d_(this.field_70170_p, i, j - 1, k))
        {
            --j;
        }

        int l = this.field_70170_p.func_72798_a(i, j, k);

        if (BlockRailBase.func_72184_d(l))
        {
            int i1 = this.field_70170_p.func_72805_g(i, j, k);
            p_70489_3_ = (double)j;

            if (((BlockRailBase)Block.field_71973_m[l]).func_72183_n())
            {
                i1 &= 7;
            }

            if (i1 >= 2 && i1 <= 5)
            {
                p_70489_3_ = (double)(j + 1);
            }

            int[][] aint = field_70500_g[i1];
            double d3 = 0.0D;
            double d4 = (double)i + 0.5D + (double)aint[0][0] * 0.5D;
            double d5 = (double)j + 0.5D + (double)aint[0][1] * 0.5D;
            double d6 = (double)k + 0.5D + (double)aint[0][2] * 0.5D;
            double d7 = (double)i + 0.5D + (double)aint[1][0] * 0.5D;
            double d8 = (double)j + 0.5D + (double)aint[1][1] * 0.5D;
            double d9 = (double)k + 0.5D + (double)aint[1][2] * 0.5D;
            double d10 = d7 - d4;
            double d11 = (d8 - d5) * 2.0D;
            double d12 = d9 - d6;

            if (d10 == 0.0D)
            {
                p_70489_1_ = (double)i + 0.5D;
                d3 = p_70489_5_ - (double)k;
            }
            else if (d12 == 0.0D)
            {
                p_70489_5_ = (double)k + 0.5D;
                d3 = p_70489_1_ - (double)i;
            }
            else
            {
                double d13 = p_70489_1_ - d4;
                double d14 = p_70489_5_ - d6;
                d3 = (d13 * d10 + d14 * d12) * 2.0D;
            }

            p_70489_1_ = d4 + d10 * d3;
            p_70489_3_ = d5 + d11 * d3;
            p_70489_5_ = d6 + d12 * d3;

            if (d11 < 0.0D)
            {
                ++p_70489_3_;
            }

            if (d11 > 0.0D)
            {
                p_70489_3_ += 0.5D;
            }

            return this.field_70170_p.func_82732_R().func_72345_a(p_70489_1_, p_70489_3_, p_70489_5_);
        }
        else
        {
            return null;
        }
    }

    protected void func_70037_a(NBTTagCompound p_70037_1_)
    {
        if (p_70037_1_.func_74767_n("CustomDisplayTile"))
        {
            this.func_94094_j(p_70037_1_.func_74762_e("DisplayTile"));
            this.func_94092_k(p_70037_1_.func_74762_e("DisplayData"));
            this.func_94086_l(p_70037_1_.func_74762_e("DisplayOffset"));
        }

        if (p_70037_1_.func_74764_b("CustomName") && p_70037_1_.func_74779_i("CustomName").length() > 0)
        {
            this.field_94102_c = p_70037_1_.func_74779_i("CustomName");
        }
    }

    protected void func_70014_b(NBTTagCompound p_70014_1_)
    {
        if (this.func_94100_s())
        {
            p_70014_1_.func_74757_a("CustomDisplayTile", true);
            p_70014_1_.func_74768_a("DisplayTile", this.func_94089_m() == null ? 0 : this.func_94089_m().field_71990_ca);
            p_70014_1_.func_74768_a("DisplayData", this.func_94098_o());
            p_70014_1_.func_74768_a("DisplayOffset", this.func_94099_q());
        }

        if (this.field_94102_c != null && this.field_94102_c.length() > 0)
        {
            p_70014_1_.func_74778_a("CustomName", this.field_94102_c);
        }
    }

    public void func_70108_f(Entity p_70108_1_)
    {
        if (!this.field_70170_p.field_72995_K)
        {
            if (p_70108_1_ != this.field_70153_n)
            {
                // CraftBukkit start
                Vehicle vehicle = (Vehicle) this.getBukkitEntity();
                org.bukkit.entity.Entity hitEntity = (p_70108_1_ == null) ? null : p_70108_1_.getBukkitEntity();
                VehicleEntityCollisionEvent collisionEvent = new VehicleEntityCollisionEvent(vehicle, hitEntity);
                this.field_70170_p.getServer().getPluginManager().callEvent(collisionEvent);

                if (collisionEvent.isCancelled())
                {
                    return;
                }

                // CraftBukkit end

                if (p_70108_1_ instanceof EntityLiving && !(p_70108_1_ instanceof EntityPlayer) && !(p_70108_1_ instanceof EntityIronGolem) && this.func_94087_l() == 0 && this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y > 0.01D && this.field_70153_n == null && p_70108_1_.field_70154_o == null)
                {
                    p_70108_1_.func_70078_a(this);
                }

                double d0 = p_70108_1_.field_70165_t - this.field_70165_t;
                double d1 = p_70108_1_.field_70161_v - this.field_70161_v;
                double d2 = d0 * d0 + d1 * d1;

                // CraftBukkit - collision
                if (d2 >= 9.999999747378752E-5D && !collisionEvent.isCollisionCancelled())
                {
                    d2 = (double)MathHelper.func_76133_a(d2);
                    d0 /= d2;
                    d1 /= d2;
                    double d3 = 1.0D / d2;

                    if (d3 > 1.0D)
                    {
                        d3 = 1.0D;
                    }

                    d0 *= d3;
                    d1 *= d3;
                    d0 *= 0.10000000149011612D;
                    d1 *= 0.10000000149011612D;
                    d0 *= (double)(1.0F - this.field_70144_Y);
                    d1 *= (double)(1.0F - this.field_70144_Y);
                    d0 *= 0.5D;
                    d1 *= 0.5D;

                    if (p_70108_1_ instanceof EntityMinecart)
                    {
                        double d4 = p_70108_1_.field_70165_t - this.field_70165_t;
                        double d5 = p_70108_1_.field_70161_v - this.field_70161_v;
                        Vec3 vec3 = this.field_70170_p.func_82732_R().func_72345_a(d4, 0.0D, d5).func_72432_b();
                        Vec3 vec31 = this.field_70170_p.func_82732_R().func_72345_a((double)MathHelper.func_76134_b(this.field_70177_z * (float)Math.PI / 180.0F), 0.0D, (double)MathHelper.func_76126_a(this.field_70177_z * (float)Math.PI / 180.0F)).func_72432_b();
                        double d6 = Math.abs(vec3.func_72430_b(vec31));

                        if (d6 < 0.800000011920929D)
                        {
                            return;
                        }

                        double d7 = p_70108_1_.field_70159_w + this.field_70159_w;
                        double d8 = p_70108_1_.field_70179_y + this.field_70179_y;

                        if (((EntityMinecart)p_70108_1_).func_94087_l() == 2 && this.func_94087_l() != 2)
                        {
                            this.field_70159_w *= 0.20000000298023224D;
                            this.field_70179_y *= 0.20000000298023224D;
                            this.func_70024_g(p_70108_1_.field_70159_w - d0, 0.0D, p_70108_1_.field_70179_y - d1);
                            p_70108_1_.field_70159_w *= 0.949999988079071D;
                            p_70108_1_.field_70179_y *= 0.949999988079071D;
                        }
                        else if (((EntityMinecart)p_70108_1_).func_94087_l() != 2 && this.func_94087_l() == 2)
                        {
                            p_70108_1_.field_70159_w *= 0.20000000298023224D;
                            p_70108_1_.field_70179_y *= 0.20000000298023224D;
                            p_70108_1_.func_70024_g(this.field_70159_w + d0, 0.0D, this.field_70179_y + d1);
                            this.field_70159_w *= 0.949999988079071D;
                            this.field_70179_y *= 0.949999988079071D;
                        }
                        else
                        {
                            d7 /= 2.0D;
                            d8 /= 2.0D;
                            this.field_70159_w *= 0.20000000298023224D;
                            this.field_70179_y *= 0.20000000298023224D;
                            this.func_70024_g(d7 - d0, 0.0D, d8 - d1);
                            p_70108_1_.field_70159_w *= 0.20000000298023224D;
                            p_70108_1_.field_70179_y *= 0.20000000298023224D;
                            p_70108_1_.func_70024_g(d7 + d0, 0.0D, d8 + d1);
                        }
                    }
                    else
                    {
                        this.func_70024_g(-d0, 0.0D, -d1);
                        p_70108_1_.func_70024_g(d0 / 4.0D, 0.0D, d1 / 4.0D);
                    }
                }
            }
        }
    }

    public void func_70492_c(int p_70492_1_)
    {
        this.field_70180_af.func_75692_b(19, Integer.valueOf(p_70492_1_));
    }

    public int func_70491_i()
    {
        return this.field_70180_af.func_75679_c(19);
    }

    public void func_70497_h(int p_70497_1_)
    {
        this.field_70180_af.func_75692_b(17, Integer.valueOf(p_70497_1_));
    }

    public int func_70496_j()
    {
        return this.field_70180_af.func_75679_c(17);
    }

    public void func_70494_i(int p_70494_1_)
    {
        this.field_70180_af.func_75692_b(18, Integer.valueOf(p_70494_1_));
    }

    public int func_70493_k()
    {
        return this.field_70180_af.func_75679_c(18);
    }

    public abstract int func_94087_l();

    public Block func_94089_m()
    {
        if (!this.func_94100_s())
        {
            return this.func_94093_n();
        }
        else
        {
            int i = this.func_70096_w().func_75679_c(20) & 65535;
            return i > 0 && i < Block.field_71973_m.length ? Block.field_71973_m[i] : null;
        }
    }

    public Block func_94093_n()
    {
        return null;
    }

    public int func_94098_o()
    {
        return !this.func_94100_s() ? this.func_94097_p() : this.func_70096_w().func_75679_c(20) >> 16;
    }

    public int func_94097_p()
    {
        return 0;
    }

    public int func_94099_q()
    {
        return !this.func_94100_s() ? this.func_94085_r() : this.func_70096_w().func_75679_c(21);
    }

    public int func_94085_r()
    {
        return 6;
    }

    public void func_94094_j(int p_94094_1_)
    {
        this.func_70096_w().func_75692_b(20, Integer.valueOf(p_94094_1_ & 65535 | this.func_94098_o() << 16));
        this.func_94096_e(true);
    }

    public void func_94092_k(int p_94092_1_)
    {
        Block block = this.func_94089_m();
        int j = block == null ? 0 : block.field_71990_ca;
        this.func_70096_w().func_75692_b(20, Integer.valueOf(j & 65535 | p_94092_1_ << 16));
        this.func_94096_e(true);
    }

    public void func_94086_l(int p_94086_1_)
    {
        this.func_70096_w().func_75692_b(21, Integer.valueOf(p_94086_1_));
        this.func_94096_e(true);
    }

    public boolean func_94100_s()
    {
        return this.func_70096_w().func_75683_a(22) == 1;
    }

    public void func_94096_e(boolean p_94096_1_)
    {
        this.func_70096_w().func_75692_b(22, Byte.valueOf((byte)(p_94096_1_ ? 1 : 0)));
    }

    public void func_96094_a(String p_96094_1_)
    {
        this.field_94102_c = p_96094_1_;
    }

    public String func_70023_ak()
    {
        return this.field_94102_c != null ? this.field_94102_c : super.func_70023_ak();
    }

    public boolean func_94042_c()
    {
        return this.field_94102_c != null;
    }

    public String func_95999_t()
    {
        return this.field_94102_c;
    }

    // CraftBukkit start - methods for getting and setting flying and derailed velocity modifiers
    public Vector getFlyingVelocityMod()
    {
        return new Vector(flyingX, flyingY, flyingZ);
    }

    public void setFlyingVelocityMod(Vector flying)
    {
        flyingX = flying.getX();
        flyingY = flying.getY();
        flyingZ = flying.getZ();
    }

    public Vector getDerailedVelocityMod()
    {
        return new Vector(derailedX, derailedY, derailedZ);
    }

    public void setDerailedVelocityMod(Vector derailed)
    {
        derailedX = derailed.getX();
        derailedY = derailed.getY();
        derailedZ = derailed.getZ();
    }
    // CraftBukkit end
}
