package net.minecraft.entity;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFluid;
import net.minecraft.block.StepSound;
import net.minecraft.block.material.Material;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

// CraftBukkit start
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.TravelAgent;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.painting.PaintingBreakByEntityEvent;
import org.bukkit.event.vehicle.VehicleBlockCollisionEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.plugin.PluginManager;
// CraftBukkit end

public abstract class Entity
{
    // CraftBukkit start
    private static final int CURRENT_LEVEL = 2;
    static boolean isLevelAtLeast(NBTTagCompound tag, int level)
    {
        return tag.func_74764_b("Bukkit.updateLevel") && tag.func_74762_e("Bukkit.updateLevel") >= level;
    }
    // CraftBukkit end

    private static int field_70152_a = 0;
    public int field_70157_k;
    public double field_70155_l;
    public boolean field_70156_m;
    public Entity field_70153_n;
    public Entity field_70154_o;
    public World field_70170_p;
    public double field_70169_q;
    public double field_70167_r;
    public double field_70166_s;
    public double field_70165_t;
    public double field_70163_u;
    public double field_70161_v;
    public double field_70159_w;
    public double field_70181_x;
    public double field_70179_y;
    public float field_70177_z;
    public float field_70125_A;
    public float field_70126_B;
    public float field_70127_C;
    public final AxisAlignedBB field_70121_D;
    public boolean field_70122_E;
    public boolean field_70123_F;
    public boolean field_70124_G;
    public boolean field_70132_H;
    public boolean field_70133_I;
    protected boolean field_70134_J;
    public boolean field_70135_K;
    public boolean field_70128_L;
    public float field_70129_M;
    public float field_70130_N;
    public float field_70131_O;
    public float field_70141_P;
    public float field_70140_Q;
    public float field_82151_R;
    public float field_70143_R;
    private int field_70150_b;
    public double field_70142_S;
    public double field_70137_T;
    public double field_70136_U;
    public float field_70139_V;
    public float field_70138_W;
    public boolean field_70145_X;
    public float field_70144_Y;
    protected Random field_70146_Z;
    public int field_70173_aa;
    public int field_70174_ab;
    public int field_70151_c; // CraftBukkit - private -> public
    protected boolean field_70171_ac;
    public int field_70172_ad;
    private boolean field_70148_d;
    protected boolean field_70178_ae;
    protected DataWatcher field_70180_af;
    private double field_70149_e;
    private double field_70147_f;
    public boolean field_70175_ag;
    public int field_70176_ah;
    public int field_70162_ai;
    public int field_70164_aj;
    public boolean field_70158_ak;
    public boolean field_70160_al;
    public int field_71088_bW;
    protected boolean field_71087_bX;
    protected int field_82153_h;
    public int field_71093_bK;
    protected int field_82152_aq;
    private boolean field_83001_bt;
    public EnumEntitySize field_70168_am;
    public UUID uniqueId = UUID.randomUUID(); // CraftBukkit
    public boolean valid = false; // CraftBukkit

    public Entity(World p_i3438_1_)
    {
        this.field_70157_k = field_70152_a++;
        this.field_70155_l = 1.0D;
        this.field_70156_m = false;
        this.field_70121_D = AxisAlignedBB.func_72330_a(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
        this.field_70122_E = false;
        this.field_70132_H = false;
        this.field_70133_I = false;
        this.field_70135_K = true;
        this.field_70128_L = false;
        this.field_70129_M = 0.0F;
        this.field_70130_N = 0.6F;
        this.field_70131_O = 1.8F;
        this.field_70141_P = 0.0F;
        this.field_70140_Q = 0.0F;
        this.field_82151_R = 0.0F;
        this.field_70143_R = 0.0F;
        this.field_70150_b = 1;
        this.field_70139_V = 0.0F;
        this.field_70138_W = 0.0F;
        this.field_70145_X = false;
        this.field_70144_Y = 0.0F;
        this.field_70146_Z = new Random();
        this.field_70173_aa = 0;
        this.field_70174_ab = 1;
        this.field_70151_c = 0;
        this.field_70171_ac = false;
        this.field_70172_ad = 0;
        this.field_70148_d = true;
        this.field_70178_ae = false;
        this.field_70180_af = new DataWatcher();
        this.field_70175_ag = false;
        this.field_82152_aq = 0;
        this.field_83001_bt = false;
        this.field_70168_am = EnumEntitySize.SIZE_2;
        this.field_70170_p = p_i3438_1_;
        this.func_70107_b(0.0D, 0.0D, 0.0D);

        if (p_i3438_1_ != null)
        {
            this.field_71093_bK = p_i3438_1_.field_73011_w.field_76574_g;
        }

        this.field_70180_af.func_75682_a(0, Byte.valueOf((byte)0));
        this.field_70180_af.func_75682_a(1, Short.valueOf((short)300));
        this.func_70088_a();
    }

    protected abstract void func_70088_a();

    public DataWatcher func_70096_w()
    {
        return this.field_70180_af;
    }

    public boolean equals(Object p_equals_1_)
    {
        return p_equals_1_ instanceof Entity ? ((Entity)p_equals_1_).field_70157_k == this.field_70157_k : false;
    }

    public int hashCode()
    {
        return this.field_70157_k;
    }

    public void func_70106_y()
    {
        this.field_70128_L = true;
    }

    protected void func_70105_a(float p_70105_1_, float p_70105_2_)
    {
        this.field_70130_N = p_70105_1_;
        this.field_70131_O = p_70105_2_;
        float f2 = p_70105_1_ % 2.0F;

        if ((double)f2 < 0.375D)
        {
            this.field_70168_am = EnumEntitySize.SIZE_1;
        }
        else if ((double)f2 < 0.75D)
        {
            this.field_70168_am = EnumEntitySize.SIZE_2;
        }
        else if ((double)f2 < 1.0D)
        {
            this.field_70168_am = EnumEntitySize.SIZE_3;
        }
        else if ((double)f2 < 1.375D)
        {
            this.field_70168_am = EnumEntitySize.SIZE_4;
        }
        else if ((double)f2 < 1.75D)
        {
            this.field_70168_am = EnumEntitySize.SIZE_5;
        }
        else
        {
            this.field_70168_am = EnumEntitySize.SIZE_6;
        }
    }

    protected void func_70101_b(float p_70101_1_, float p_70101_2_)
    {
        // CraftBukkit start - yaw was sometimes set to NaN, so we need to set it back to 0
        if (Float.isNaN(p_70101_1_))
        {
            p_70101_1_ = 0;
        }

        if ((p_70101_1_ == Float.POSITIVE_INFINITY) || (p_70101_1_ == Float.NEGATIVE_INFINITY))
        {
            if (this instanceof EntityPlayerMP)
            {
                System.err.println(((CraftPlayer) this.getBukkitEntity()).getName() + " was caught trying to crash the server with an invalid yaw");
                ((CraftPlayer) this.getBukkitEntity()).kickPlayer("Nope");
            }

            p_70101_1_ = 0;
        }

        // pitch was sometimes set to NaN, so we need to set it back to 0.
        if (Float.isNaN(p_70101_2_))
        {
            p_70101_2_ = 0;
        }

        if ((p_70101_2_ == Float.POSITIVE_INFINITY) || (p_70101_2_ == Float.NEGATIVE_INFINITY))
        {
            if (this instanceof EntityPlayerMP)
            {
                System.err.println(((CraftPlayer) this.getBukkitEntity()).getName() + " was caught trying to crash the server with an invalid pitch");
                ((CraftPlayer) this.getBukkitEntity()).kickPlayer("Nope");
            }

            p_70101_2_ = 0;
        }

        // CraftBukkit end
        this.field_70177_z = p_70101_1_ % 360.0F;
        this.field_70125_A = p_70101_2_ % 360.0F;
    }

    public void func_70107_b(double p_70107_1_, double p_70107_3_, double p_70107_5_)
    {
        this.field_70165_t = p_70107_1_;
        this.field_70163_u = p_70107_3_;
        this.field_70161_v = p_70107_5_;
        float f = this.field_70130_N / 2.0F;
        float f1 = this.field_70131_O;
        this.field_70121_D.func_72324_b(p_70107_1_ - (double)f, p_70107_3_ - (double)this.field_70129_M + (double)this.field_70139_V, p_70107_5_ - (double)f, p_70107_1_ + (double)f, p_70107_3_ - (double)this.field_70129_M + (double)this.field_70139_V + (double)f1, p_70107_5_ + (double)f);
    }

    public void func_70071_h_()
    {
        this.func_70030_z();
    }

    public void func_70030_z()
    {
        this.field_70170_p.field_72984_F.func_76320_a("entityBaseTick");

        if (this.field_70154_o != null && this.field_70154_o.field_70128_L)
        {
            this.field_70154_o = null;
        }

        this.field_70141_P = this.field_70140_Q;
        this.field_70169_q = this.field_70165_t;
        this.field_70167_r = this.field_70163_u;
        this.field_70166_s = this.field_70161_v;
        this.field_70127_C = this.field_70125_A;
        this.field_70126_B = this.field_70177_z;
        int i;

        if (!this.field_70170_p.field_72995_K && this.field_70170_p instanceof WorldServer)
        {
            this.field_70170_p.field_72984_F.func_76320_a("portal");
            MinecraftServer minecraftserver = ((WorldServer)this.field_70170_p).func_73046_m();
            i = this.func_82145_z();

            if (this.field_71087_bX)
            {
                if (true || minecraftserver.func_71255_r())   // CraftBukkit
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

        if (this.func_70051_ag() && !this.func_70090_H())
        {
            int j = MathHelper.func_76128_c(this.field_70165_t);
            i = MathHelper.func_76128_c(this.field_70163_u - 0.20000000298023224D - (double)this.field_70129_M);
            int k = MathHelper.func_76128_c(this.field_70161_v);
            int l = this.field_70170_p.func_72798_a(j, i, k);

            if (l > 0)
            {
                this.field_70170_p.func_72869_a("tilecrack_" + l + "_" + this.field_70170_p.func_72805_g(j, i, k), this.field_70165_t + ((double)this.field_70146_Z.nextFloat() - 0.5D) * (double)this.field_70130_N, this.field_70121_D.field_72338_b + 0.1D, this.field_70161_v + ((double)this.field_70146_Z.nextFloat() - 0.5D) * (double)this.field_70130_N, -this.field_70159_w * 4.0D, 1.5D, -this.field_70179_y * 4.0D);
            }
        }

        this.func_70072_I();

        if (this.field_70170_p.field_72995_K)
        {
            this.field_70151_c = 0;
        }
        else if (this.field_70151_c > 0)
        {
            if (this.field_70178_ae)
            {
                this.field_70151_c -= 4;

                if (this.field_70151_c < 0)
                {
                    this.field_70151_c = 0;
                }
            }
            else
            {
                if (this.field_70151_c % 20 == 0)
                {
                    // CraftBukkit start - TODO: this event spams!
                    if (this instanceof EntityLiving)
                    {
                        EntityDamageEvent event = new EntityDamageEvent(this.getBukkitEntity(), EntityDamageEvent.DamageCause.FIRE_TICK, 1);
                        this.field_70170_p.getServer().getPluginManager().callEvent(event);

                        if (!event.isCancelled())
                        {
                            event.getEntity().setLastDamageCause(event);
                            this.func_70097_a(DamageSource.field_76370_b, event.getDamage());
                        }
                    }
                    else
                    {
                        this.func_70097_a(DamageSource.field_76370_b, 1);
                    }

                    // CraftBukkit end
                }

                --this.field_70151_c;
            }
        }

        if (this.func_70058_J())
        {
            this.func_70044_A();
            this.field_70143_R *= 0.5F;
        }

        if (this.field_70163_u < -64.0D)
        {
            this.func_70076_C();
        }

        if (!this.field_70170_p.field_72995_K)
        {
            this.func_70052_a(0, this.field_70151_c > 0);
            this.func_70052_a(2, this.field_70154_o != null);
        }

        this.field_70148_d = false;
        this.field_70170_p.field_72984_F.func_76319_b();
    }

    public int func_82145_z()
    {
        return 0;
    }

    protected void func_70044_A()
    {
        if (!this.field_70178_ae)
        {
            // CraftBukkit start - fallen in lava TODO: this event spams!
            if (this instanceof EntityLiving)
            {
                Server server = this.field_70170_p.getServer();
                // TODO: shouldn't be sending null for the block.
                org.bukkit.block.Block damager = null; // ((WorldServer) this.l).getWorld().getBlockAt(i, j, k);
                org.bukkit.entity.Entity damagee = this.getBukkitEntity();
                EntityDamageByBlockEvent event = new EntityDamageByBlockEvent(damager, damagee, EntityDamageEvent.DamageCause.LAVA, 4);
                server.getPluginManager().callEvent(event);

                if (!event.isCancelled())
                {
                    damagee.setLastDamageCause(event);
                    this.func_70097_a(DamageSource.field_76371_c, event.getDamage());
                }

                if (this.field_70151_c <= 0)
                {
                    // not on fire yet
                    EntityCombustEvent combustEvent = new org.bukkit.event.entity.EntityCombustByBlockEvent(damager, damagee, 15);
                    server.getPluginManager().callEvent(combustEvent);

                    if (!combustEvent.isCancelled())
                    {
                        this.func_70015_d(combustEvent.getDuration());
                    }
                }
                else
                {
                    // This will be called every single tick the entity is in lava, so don't throw an event
                    this.func_70015_d(15);
                }

                return;
            }

            // CraftBukkit end - we also don't throw an event unless the object in lava is living, to save on some event calls
            this.func_70097_a(DamageSource.field_76371_c, 4);
            this.func_70015_d(15);
        }
    }

    public void func_70015_d(int p_70015_1_)
    {
        int j = p_70015_1_ * 20;
        j = EnchantmentProtection.func_92093_a(this, j);

        if (this.field_70151_c < j)
        {
            this.field_70151_c = j;
        }
    }

    public void func_70066_B()
    {
        this.field_70151_c = 0;
    }

    protected void func_70076_C()
    {
        this.func_70106_y();
    }

    public boolean func_70038_c(double p_70038_1_, double p_70038_3_, double p_70038_5_)
    {
        AxisAlignedBB axisalignedbb = this.field_70121_D.func_72325_c(p_70038_1_, p_70038_3_, p_70038_5_);
        List list = this.field_70170_p.func_72945_a(this, axisalignedbb);
        return !list.isEmpty() ? false : !this.field_70170_p.func_72953_d(axisalignedbb);
    }

    public void func_70091_d(double p_70091_1_, double p_70091_3_, double p_70091_5_)
    {
        if (this.field_70145_X)
        {
            this.field_70121_D.func_72317_d(p_70091_1_, p_70091_3_, p_70091_5_);
            this.field_70165_t = (this.field_70121_D.field_72340_a + this.field_70121_D.field_72336_d) / 2.0D;
            this.field_70163_u = this.field_70121_D.field_72338_b + (double)this.field_70129_M - (double)this.field_70139_V;
            this.field_70161_v = (this.field_70121_D.field_72339_c + this.field_70121_D.field_72334_f) / 2.0D;
        }
        else
        {
            this.field_70170_p.field_72984_F.func_76320_a("move");
            this.field_70139_V *= 0.4F;
            double d3 = this.field_70165_t;
            double d4 = this.field_70163_u;
            double d5 = this.field_70161_v;

            if (this.field_70134_J)
            {
                this.field_70134_J = false;
                p_70091_1_ *= 0.25D;
                p_70091_3_ *= 0.05000000074505806D;
                p_70091_5_ *= 0.25D;
                this.field_70159_w = 0.0D;
                this.field_70181_x = 0.0D;
                this.field_70179_y = 0.0D;
            }

            double d6 = p_70091_1_;
            double d7 = p_70091_3_;
            double d8 = p_70091_5_;
            AxisAlignedBB axisalignedbb = this.field_70121_D.func_72329_c();
            boolean flag = this.field_70122_E && this.func_70093_af() && this instanceof EntityPlayer;

            if (flag)
            {
                double d9;

                for (d9 = 0.05D; p_70091_1_ != 0.0D && this.field_70170_p.func_72945_a(this, this.field_70121_D.func_72325_c(p_70091_1_, -1.0D, 0.0D)).isEmpty(); d6 = p_70091_1_)
                {
                    if (p_70091_1_ < d9 && p_70091_1_ >= -d9)
                    {
                        p_70091_1_ = 0.0D;
                    }
                    else if (p_70091_1_ > 0.0D)
                    {
                        p_70091_1_ -= d9;
                    }
                    else
                    {
                        p_70091_1_ += d9;
                    }
                }

                for (; p_70091_5_ != 0.0D && this.field_70170_p.func_72945_a(this, this.field_70121_D.func_72325_c(0.0D, -1.0D, p_70091_5_)).isEmpty(); d8 = p_70091_5_)
                {
                    if (p_70091_5_ < d9 && p_70091_5_ >= -d9)
                    {
                        p_70091_5_ = 0.0D;
                    }
                    else if (p_70091_5_ > 0.0D)
                    {
                        p_70091_5_ -= d9;
                    }
                    else
                    {
                        p_70091_5_ += d9;
                    }
                }

                while (p_70091_1_ != 0.0D && p_70091_5_ != 0.0D && this.field_70170_p.func_72945_a(this, this.field_70121_D.func_72325_c(p_70091_1_, -1.0D, p_70091_5_)).isEmpty())
                {
                    if (p_70091_1_ < d9 && p_70091_1_ >= -d9)
                    {
                        p_70091_1_ = 0.0D;
                    }
                    else if (p_70091_1_ > 0.0D)
                    {
                        p_70091_1_ -= d9;
                    }
                    else
                    {
                        p_70091_1_ += d9;
                    }

                    if (p_70091_5_ < d9 && p_70091_5_ >= -d9)
                    {
                        p_70091_5_ = 0.0D;
                    }
                    else if (p_70091_5_ > 0.0D)
                    {
                        p_70091_5_ -= d9;
                    }
                    else
                    {
                        p_70091_5_ += d9;
                    }

                    d6 = p_70091_1_;
                    d8 = p_70091_5_;
                }
            }

            List list = this.field_70170_p.func_72945_a(this, this.field_70121_D.func_72321_a(p_70091_1_, p_70091_3_, p_70091_5_));

            for (int i = 0; i < list.size(); ++i)
            {
                p_70091_3_ = ((AxisAlignedBB)list.get(i)).func_72323_b(this.field_70121_D, p_70091_3_);
            }

            this.field_70121_D.func_72317_d(0.0D, p_70091_3_, 0.0D);

            if (!this.field_70135_K && d7 != p_70091_3_)
            {
                p_70091_5_ = 0.0D;
                p_70091_3_ = 0.0D;
                p_70091_1_ = 0.0D;
            }

            boolean flag1 = this.field_70122_E || d7 != p_70091_3_ && d7 < 0.0D;
            int j;

            for (j = 0; j < list.size(); ++j)
            {
                p_70091_1_ = ((AxisAlignedBB)list.get(j)).func_72316_a(this.field_70121_D, p_70091_1_);
            }

            this.field_70121_D.func_72317_d(p_70091_1_, 0.0D, 0.0D);

            if (!this.field_70135_K && d6 != p_70091_1_)
            {
                p_70091_5_ = 0.0D;
                p_70091_3_ = 0.0D;
                p_70091_1_ = 0.0D;
            }

            for (j = 0; j < list.size(); ++j)
            {
                p_70091_5_ = ((AxisAlignedBB)list.get(j)).func_72322_c(this.field_70121_D, p_70091_5_);
            }

            this.field_70121_D.func_72317_d(0.0D, 0.0D, p_70091_5_);

            if (!this.field_70135_K && d8 != p_70091_5_)
            {
                p_70091_5_ = 0.0D;
                p_70091_3_ = 0.0D;
                p_70091_1_ = 0.0D;
            }

            double d10;
            double d11;
            double d12;
            int k;

            if (this.field_70138_W > 0.0F && flag1 && (flag || this.field_70139_V < 0.05F) && (d6 != p_70091_1_ || d8 != p_70091_5_))
            {
                d10 = p_70091_1_;
                d11 = p_70091_3_;
                d12 = p_70091_5_;
                p_70091_1_ = d6;
                p_70091_3_ = (double)this.field_70138_W;
                p_70091_5_ = d8;
                AxisAlignedBB axisalignedbb1 = this.field_70121_D.func_72329_c();
                this.field_70121_D.func_72328_c(axisalignedbb);
                list = this.field_70170_p.func_72945_a(this, this.field_70121_D.func_72321_a(d6, p_70091_3_, d8));

                for (k = 0; k < list.size(); ++k)
                {
                    p_70091_3_ = ((AxisAlignedBB)list.get(k)).func_72323_b(this.field_70121_D, p_70091_3_);
                }

                this.field_70121_D.func_72317_d(0.0D, p_70091_3_, 0.0D);

                if (!this.field_70135_K && d7 != p_70091_3_)
                {
                    p_70091_5_ = 0.0D;
                    p_70091_3_ = 0.0D;
                    p_70091_1_ = 0.0D;
                }

                for (k = 0; k < list.size(); ++k)
                {
                    p_70091_1_ = ((AxisAlignedBB)list.get(k)).func_72316_a(this.field_70121_D, p_70091_1_);
                }

                this.field_70121_D.func_72317_d(p_70091_1_, 0.0D, 0.0D);

                if (!this.field_70135_K && d6 != p_70091_1_)
                {
                    p_70091_5_ = 0.0D;
                    p_70091_3_ = 0.0D;
                    p_70091_1_ = 0.0D;
                }

                for (k = 0; k < list.size(); ++k)
                {
                    p_70091_5_ = ((AxisAlignedBB)list.get(k)).func_72322_c(this.field_70121_D, p_70091_5_);
                }

                this.field_70121_D.func_72317_d(0.0D, 0.0D, p_70091_5_);

                if (!this.field_70135_K && d8 != p_70091_5_)
                {
                    p_70091_5_ = 0.0D;
                    p_70091_3_ = 0.0D;
                    p_70091_1_ = 0.0D;
                }

                if (!this.field_70135_K && d7 != p_70091_3_)
                {
                    p_70091_5_ = 0.0D;
                    p_70091_3_ = 0.0D;
                    p_70091_1_ = 0.0D;
                }
                else
                {
                    p_70091_3_ = (double)(-this.field_70138_W);

                    for (k = 0; k < list.size(); ++k)
                    {
                        p_70091_3_ = ((AxisAlignedBB)list.get(k)).func_72323_b(this.field_70121_D, p_70091_3_);
                    }

                    this.field_70121_D.func_72317_d(0.0D, p_70091_3_, 0.0D);
                }

                if (d10 * d10 + d12 * d12 >= p_70091_1_ * p_70091_1_ + p_70091_5_ * p_70091_5_)
                {
                    p_70091_1_ = d10;
                    p_70091_3_ = d11;
                    p_70091_5_ = d12;
                    this.field_70121_D.func_72328_c(axisalignedbb1);
                }
                else
                {
                    double d13 = this.field_70121_D.field_72338_b - (double)((int)this.field_70121_D.field_72338_b);

                    if (d13 > 0.0D)
                    {
                        this.field_70139_V = (float)((double)this.field_70139_V + d13 + 0.01D);
                    }
                }
            }

            this.field_70170_p.field_72984_F.func_76319_b();
            this.field_70170_p.field_72984_F.func_76320_a("rest");
            this.field_70165_t = (this.field_70121_D.field_72340_a + this.field_70121_D.field_72336_d) / 2.0D;
            this.field_70163_u = this.field_70121_D.field_72338_b + (double)this.field_70129_M - (double)this.field_70139_V;
            this.field_70161_v = (this.field_70121_D.field_72339_c + this.field_70121_D.field_72334_f) / 2.0D;
            this.field_70123_F = d6 != p_70091_1_ || d8 != p_70091_5_;
            this.field_70124_G = d7 != p_70091_3_;
            this.field_70122_E = d7 != p_70091_3_ && d7 < 0.0D;
            this.field_70132_H = this.field_70123_F || this.field_70124_G;
            this.func_70064_a(p_70091_3_, this.field_70122_E);

            if (d6 != p_70091_1_)
            {
                this.field_70159_w = 0.0D;
            }

            if (d7 != p_70091_3_)
            {
                this.field_70181_x = 0.0D;
            }

            if (d8 != p_70091_5_)
            {
                this.field_70179_y = 0.0D;
            }

            d10 = this.field_70165_t - d3;
            d11 = this.field_70163_u - d4;
            d12 = this.field_70161_v - d5;

            // CraftBukkit start
            if ((this.field_70123_F) && (this.getBukkitEntity() instanceof Vehicle))
            {
                Vehicle vehicle = (Vehicle) this.getBukkitEntity();
                org.bukkit.block.Block block = this.field_70170_p.getWorld().getBlockAt(MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c(this.field_70163_u - (double) this.field_70129_M), MathHelper.func_76128_c(this.field_70161_v));

                if (d6 > p_70091_1_)
                {
                    block = block.getRelative(BlockFace.EAST);
                }
                else if (d6 < p_70091_1_)
                {
                    block = block.getRelative(BlockFace.WEST);
                }
                else if (d8 > p_70091_5_)
                {
                    block = block.getRelative(BlockFace.SOUTH);
                }
                else if (d8 < p_70091_5_)
                {
                    block = block.getRelative(BlockFace.NORTH);
                }

                VehicleBlockCollisionEvent event = new VehicleBlockCollisionEvent(vehicle, block);
                this.field_70170_p.getServer().getPluginManager().callEvent(event);
            }

            // CraftBukkit end

            if (this.func_70041_e_() && !flag && this.field_70154_o == null)
            {
                int l = MathHelper.func_76128_c(this.field_70165_t);
                k = MathHelper.func_76128_c(this.field_70163_u - 0.20000000298023224D - (double)this.field_70129_M);
                int i1 = MathHelper.func_76128_c(this.field_70161_v);
                int j1 = this.field_70170_p.func_72798_a(l, k, i1);

                if (j1 == 0)
                {
                    int k1 = this.field_70170_p.func_85175_e(l, k - 1, i1);

                    if (k1 == 11 || k1 == 32 || k1 == 21)
                    {
                        j1 = this.field_70170_p.func_72798_a(l, k - 1, i1);
                    }
                }

                if (j1 != Block.field_72055_aF.field_71990_ca)
                {
                    d11 = 0.0D;
                }

                this.field_70140_Q = (float)((double) this.field_70140_Q + (double) MathHelper.func_76133_a(d10 * d10 + d12 * d12) * 0.6D);
                this.field_82151_R = (float)((double) this.field_82151_R + (double) MathHelper.func_76133_a(d10 * d10 + d11 * d11 + d12 * d12) * 0.6D);

                if (this.field_82151_R > (float)this.field_70150_b && j1 > 0)
                {
                    this.field_70150_b = (int)this.field_82151_R + 1;

                    if (this.func_70090_H())
                    {
                        float f = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w * 0.20000000298023224D + this.field_70181_x * this.field_70181_x + this.field_70179_y * this.field_70179_y * 0.20000000298023224D) * 0.35F;

                        if (f > 1.0F)
                        {
                            f = 1.0F;
                        }

                        this.func_85030_a("liquid.swim", f, 1.0F + (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.4F);
                    }

                    this.func_70036_a(l, k, i1, j1);
                    Block.field_71973_m[j1].func_71891_b(this.field_70170_p, l, k, i1, this);
                }
            }

            this.func_70017_D();
            boolean flag2 = this.func_70026_G();

            if (this.field_70170_p.func_72978_e(this.field_70121_D.func_72331_e(0.001D, 0.001D, 0.001D)))
            {
                this.func_70081_e(1);

                if (!flag2)
                {
                    ++this.field_70151_c;

                    // CraftBukkit start - not on fire yet
                    if (this.field_70151_c <= 0)   // only throw events on the first combust, otherwise it spams
                    {
                        EntityCombustEvent event = new EntityCombustEvent(this.getBukkitEntity(), 8);
                        this.field_70170_p.getServer().getPluginManager().callEvent(event);

                        if (!event.isCancelled())
                        {
                            this.func_70015_d(event.getDuration());
                        }
                    }
                    else
                    {
                        // CraftBukkit end
                        this.func_70015_d(8);
                    }
                }
            }
            else if (this.field_70151_c <= 0)
            {
                this.field_70151_c = -this.field_70174_ab;
            }

            if (flag2 && this.field_70151_c > 0)
            {
                this.func_85030_a("random.fizz", 0.7F, 1.6F + (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.4F);
                this.field_70151_c = -this.field_70174_ab;
            }

            this.field_70170_p.field_72984_F.func_76319_b();
        }
    }

    protected void func_70017_D()
    {
        int i = MathHelper.func_76128_c(this.field_70121_D.field_72340_a + 0.001D);
        int j = MathHelper.func_76128_c(this.field_70121_D.field_72338_b + 0.001D);
        int k = MathHelper.func_76128_c(this.field_70121_D.field_72339_c + 0.001D);
        int l = MathHelper.func_76128_c(this.field_70121_D.field_72336_d - 0.001D);
        int i1 = MathHelper.func_76128_c(this.field_70121_D.field_72337_e - 0.001D);
        int j1 = MathHelper.func_76128_c(this.field_70121_D.field_72334_f - 0.001D);

        if (this.field_70170_p.func_72904_c(i, j, k, l, i1, j1))
        {
            for (int k1 = i; k1 <= l; ++k1)
            {
                for (int l1 = j; l1 <= i1; ++l1)
                {
                    for (int i2 = k; i2 <= j1; ++i2)
                    {
                        int j2 = this.field_70170_p.func_72798_a(k1, l1, i2);

                        if (j2 > 0)
                        {
                            Block.field_71973_m[j2].func_71869_a(this.field_70170_p, k1, l1, i2, this);
                        }
                    }
                }
            }
        }
    }

    protected void func_70036_a(int p_70036_1_, int p_70036_2_, int p_70036_3_, int p_70036_4_)
    {
        StepSound stepsound = Block.field_71973_m[p_70036_4_].field_72020_cn;

        if (this.field_70170_p.func_72798_a(p_70036_1_, p_70036_2_ + 1, p_70036_3_) == Block.field_72037_aS.field_71990_ca)
        {
            stepsound = Block.field_72037_aS.field_72020_cn;
            this.func_85030_a(stepsound.func_72675_d(), stepsound.func_72677_b() * 0.15F, stepsound.func_72678_c());
        }
        else if (!Block.field_71973_m[p_70036_4_].field_72018_cp.func_76224_d())
        {
            this.func_85030_a(stepsound.func_72675_d(), stepsound.func_72677_b() * 0.15F, stepsound.func_72678_c());
        }
    }

    public void func_85030_a(String p_85030_1_, float p_85030_2_, float p_85030_3_)
    {
        this.field_70170_p.func_72956_a(this, p_85030_1_, p_85030_2_, p_85030_3_);
    }

    protected boolean func_70041_e_()
    {
        return true;
    }

    protected void func_70064_a(double p_70064_1_, boolean p_70064_3_)
    {
        if (p_70064_3_)
        {
            if (this.field_70143_R > 0.0F)
            {
                this.func_70069_a(this.field_70143_R);
                this.field_70143_R = 0.0F;
            }
        }
        else if (p_70064_1_ < 0.0D)
        {
            this.field_70143_R = (float)((double)this.field_70143_R - p_70064_1_);
        }
    }

    public AxisAlignedBB func_70046_E()
    {
        return null;
    }

    protected void func_70081_e(int p_70081_1_)
    {
        if (!this.field_70178_ae)
        {
            // CraftBukkit start
            if (this instanceof EntityLiving)
            {
                EntityDamageEvent event = new EntityDamageEvent(this.getBukkitEntity(), EntityDamageEvent.DamageCause.FIRE, p_70081_1_);
                this.field_70170_p.getServer().getPluginManager().callEvent(event);

                if (event.isCancelled())
                {
                    return;
                }

                p_70081_1_ = event.getDamage();
                event.getEntity().setLastDamageCause(event);
            }

            // CraftBukkit end
            this.func_70097_a(DamageSource.field_76372_a, p_70081_1_);
        }
    }

    public final boolean func_70045_F()
    {
        return this.field_70178_ae;
    }

    protected void func_70069_a(float p_70069_1_)
    {
        if (this.field_70153_n != null)
        {
            this.field_70153_n.func_70069_a(p_70069_1_);
        }
    }

    public boolean func_70026_G()
    {
        return this.field_70171_ac || this.field_70170_p.func_72951_B(MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c(this.field_70163_u), MathHelper.func_76128_c(this.field_70161_v)) || this.field_70170_p.func_72951_B(MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c(this.field_70163_u + (double)this.field_70131_O), MathHelper.func_76128_c(this.field_70161_v));
    }

    public boolean func_70090_H()
    {
        return this.field_70171_ac;
    }

    public boolean func_70072_I()
    {
        if (this.field_70170_p.func_72918_a(this.field_70121_D.func_72314_b(0.0D, -0.4000000059604645D, 0.0D).func_72331_e(0.001D, 0.001D, 0.001D), Material.field_76244_g, this))
        {
            if (!this.field_70171_ac && !this.field_70148_d)
            {
                float f = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w * 0.20000000298023224D + this.field_70181_x * this.field_70181_x + this.field_70179_y * this.field_70179_y * 0.20000000298023224D) * 0.2F;

                if (f > 1.0F)
                {
                    f = 1.0F;
                }

                this.func_85030_a("liquid.splash", f, 1.0F + (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.4F);
                float f1 = (float)MathHelper.func_76128_c(this.field_70121_D.field_72338_b);
                int i;
                float f2;
                float f3;

                for (i = 0; (float)i < 1.0F + this.field_70130_N * 20.0F; ++i)
                {
                    f2 = (this.field_70146_Z.nextFloat() * 2.0F - 1.0F) * this.field_70130_N;
                    f3 = (this.field_70146_Z.nextFloat() * 2.0F - 1.0F) * this.field_70130_N;
                    this.field_70170_p.func_72869_a("bubble", this.field_70165_t + (double)f2, (double)(f1 + 1.0F), this.field_70161_v + (double)f3, this.field_70159_w, this.field_70181_x - (double)(this.field_70146_Z.nextFloat() * 0.2F), this.field_70179_y);
                }

                for (i = 0; (float)i < 1.0F + this.field_70130_N * 20.0F; ++i)
                {
                    f2 = (this.field_70146_Z.nextFloat() * 2.0F - 1.0F) * this.field_70130_N;
                    f3 = (this.field_70146_Z.nextFloat() * 2.0F - 1.0F) * this.field_70130_N;
                    this.field_70170_p.func_72869_a("splash", this.field_70165_t + (double)f2, (double)(f1 + 1.0F), this.field_70161_v + (double)f3, this.field_70159_w, this.field_70181_x, this.field_70179_y);
                }
            }

            this.field_70143_R = 0.0F;
            this.field_70171_ac = true;
            this.field_70151_c = 0;
        }
        else
        {
            this.field_70171_ac = false;
        }

        return this.field_70171_ac;
    }

    public boolean func_70055_a(Material p_70055_1_)
    {
        double d0 = this.field_70163_u + (double)this.func_70047_e();
        int i = MathHelper.func_76128_c(this.field_70165_t);
        int j = MathHelper.func_76141_d((float)MathHelper.func_76128_c(d0));
        int k = MathHelper.func_76128_c(this.field_70161_v);
        int l = this.field_70170_p.func_72798_a(i, j, k);

        if (l != 0 && Block.field_71973_m[l].field_72018_cp == p_70055_1_)
        {
            float f = BlockFluid.func_72199_d(this.field_70170_p.func_72805_g(i, j, k)) - 0.11111111F;
            float f1 = (float)(j + 1) - f;
            return d0 < (double)f1;
        }
        else
        {
            return false;
        }
    }

    public float func_70047_e()
    {
        return 0.0F;
    }

    public boolean func_70058_J()
    {
        return this.field_70170_p.func_72875_a(this.field_70121_D.func_72314_b(-0.10000000149011612D, -0.4000000059604645D, -0.10000000149011612D), Material.field_76256_h);
    }

    public void func_70060_a(float p_70060_1_, float p_70060_2_, float p_70060_3_)
    {
        float f3 = p_70060_1_ * p_70060_1_ + p_70060_2_ * p_70060_2_;

        if (f3 >= 1.0E-4F)
        {
            f3 = MathHelper.func_76129_c(f3);

            if (f3 < 1.0F)
            {
                f3 = 1.0F;
            }

            f3 = p_70060_3_ / f3;
            p_70060_1_ *= f3;
            p_70060_2_ *= f3;
            float f4 = MathHelper.func_76126_a(this.field_70177_z * (float)Math.PI / 180.0F);
            float f5 = MathHelper.func_76134_b(this.field_70177_z * (float)Math.PI / 180.0F);
            this.field_70159_w += (double)(p_70060_1_ * f5 - p_70060_2_ * f4);
            this.field_70179_y += (double)(p_70060_2_ * f5 + p_70060_1_ * f4);
        }
    }

    public float func_70013_c(float p_70013_1_)
    {
        int i = MathHelper.func_76128_c(this.field_70165_t);
        int j = MathHelper.func_76128_c(this.field_70161_v);

        if (this.field_70170_p.func_72899_e(i, 0, j))
        {
            double d0 = (this.field_70121_D.field_72337_e - this.field_70121_D.field_72338_b) * 0.66D;
            int k = MathHelper.func_76128_c(this.field_70163_u - (double)this.field_70129_M + d0);
            return this.field_70170_p.func_72801_o(i, k, j);
        }
        else
        {
            return 0.0F;
        }
    }

    public void func_70029_a(World p_70029_1_)
    {
        // CraftBukkit start
        if (p_70029_1_ == null)
        {
            this.func_70106_y();
            this.field_70170_p = ((CraftWorld) Bukkit.getServer().getWorlds().get(0)).getHandle();
            return;
        }

        // CraftBukkit end
        this.field_70170_p = p_70029_1_;
    }

    public void func_70080_a(double p_70080_1_, double p_70080_3_, double p_70080_5_, float p_70080_7_, float p_70080_8_)
    {
        this.field_70169_q = this.field_70165_t = p_70080_1_;
        this.field_70167_r = this.field_70163_u = p_70080_3_;
        this.field_70166_s = this.field_70161_v = p_70080_5_;
        this.field_70126_B = this.field_70177_z = p_70080_7_;
        this.field_70127_C = this.field_70125_A = p_70080_8_;
        this.field_70139_V = 0.0F;
        double d3 = (double)(this.field_70126_B - p_70080_7_);

        if (d3 < -180.0D)
        {
            this.field_70126_B += 360.0F;
        }

        if (d3 >= 180.0D)
        {
            this.field_70126_B -= 360.0F;
        }

        this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
        this.func_70101_b(p_70080_7_, p_70080_8_);
    }

    public void func_70012_b(double p_70012_1_, double p_70012_3_, double p_70012_5_, float p_70012_7_, float p_70012_8_)
    {
        this.field_70142_S = this.field_70169_q = this.field_70165_t = p_70012_1_;
        this.field_70137_T = this.field_70167_r = this.field_70163_u = p_70012_3_ + (double)this.field_70129_M;
        this.field_70136_U = this.field_70166_s = this.field_70161_v = p_70012_5_;
        this.field_70177_z = p_70012_7_;
        this.field_70125_A = p_70012_8_;
        this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
    }

    public float func_70032_d(Entity p_70032_1_)
    {
        float f = (float)(this.field_70165_t - p_70032_1_.field_70165_t);
        float f1 = (float)(this.field_70163_u - p_70032_1_.field_70163_u);
        float f2 = (float)(this.field_70161_v - p_70032_1_.field_70161_v);
        return MathHelper.func_76129_c(f * f + f1 * f1 + f2 * f2);
    }

    public double func_70092_e(double p_70092_1_, double p_70092_3_, double p_70092_5_)
    {
        double d3 = this.field_70165_t - p_70092_1_;
        double d4 = this.field_70163_u - p_70092_3_;
        double d5 = this.field_70161_v - p_70092_5_;
        return d3 * d3 + d4 * d4 + d5 * d5;
    }

    public double func_70011_f(double p_70011_1_, double p_70011_3_, double p_70011_5_)
    {
        double d3 = this.field_70165_t - p_70011_1_;
        double d4 = this.field_70163_u - p_70011_3_;
        double d5 = this.field_70161_v - p_70011_5_;
        return (double)MathHelper.func_76133_a(d3 * d3 + d4 * d4 + d5 * d5);
    }

    public double func_70068_e(Entity p_70068_1_)
    {
        double d0 = this.field_70165_t - p_70068_1_.field_70165_t;
        double d1 = this.field_70163_u - p_70068_1_.field_70163_u;
        double d2 = this.field_70161_v - p_70068_1_.field_70161_v;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public void func_70100_b_(EntityPlayer p_70100_1_) {}

    public void func_70108_f(Entity p_70108_1_)
    {
        if (p_70108_1_.field_70153_n != this && p_70108_1_.field_70154_o != this)
        {
            double d0 = p_70108_1_.field_70165_t - this.field_70165_t;
            double d1 = p_70108_1_.field_70161_v - this.field_70161_v;
            double d2 = MathHelper.func_76132_a(d0, d1);

            if (d2 >= 0.009999999776482582D)
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
                d0 *= 0.05000000074505806D;
                d1 *= 0.05000000074505806D;
                d0 *= (double)(1.0F - this.field_70144_Y);
                d1 *= (double)(1.0F - this.field_70144_Y);
                this.func_70024_g(-d0, 0.0D, -d1);
                p_70108_1_.func_70024_g(d0, 0.0D, d1);
            }
        }
    }

    public void func_70024_g(double p_70024_1_, double p_70024_3_, double p_70024_5_)
    {
        this.field_70159_w += p_70024_1_;
        this.field_70181_x += p_70024_3_;
        this.field_70179_y += p_70024_5_;
        this.field_70160_al = true;
    }

    protected void func_70018_K()
    {
        this.field_70133_I = true;
    }

    public boolean func_70097_a(DamageSource p_70097_1_, int p_70097_2_)
    {
        if (this.func_85032_ar())
        {
            return false;
        }
        else
        {
            this.func_70018_K();
            return false;
        }
    }

    public boolean func_70067_L()
    {
        return false;
    }

    public boolean func_70104_M()
    {
        return false;
    }

    public void func_70084_c(Entity p_70084_1_, int p_70084_2_) {}

    public boolean func_70039_c(NBTTagCompound p_70039_1_)
    {
        String s = this.func_70022_Q();

        if (!this.field_70128_L && s != null)
        {
            p_70039_1_.func_74778_a("id", s);
            this.func_70109_d(p_70039_1_);
            return true;
        }
        else
        {
            return false;
        }
    }

    public void func_70109_d(NBTTagCompound p_70109_1_)
    {
        try
        {
            p_70109_1_.func_74782_a("Pos", this.func_70087_a(new double[] {this.field_70165_t, this.field_70163_u + (double)this.field_70139_V, this.field_70161_v}));
            p_70109_1_.func_74782_a("Motion", this.func_70087_a(new double[] {this.field_70159_w, this.field_70181_x, this.field_70179_y}));

            // CraftBukkit start - checking for NaN pitch/yaw and resetting to zero
            // TODO: make sure this is the best way to address this.
            if (Float.isNaN(this.field_70177_z))
            {
                this.field_70177_z = 0;
            }

            if (Float.isNaN(this.field_70125_A))
            {
                this.field_70125_A = 0;
            }

            // CraftBukkit end
            p_70109_1_.func_74782_a("Rotation", this.func_70049_a(new float[] {this.field_70177_z, this.field_70125_A}));
            p_70109_1_.func_74776_a("FallDistance", this.field_70143_R);
            p_70109_1_.func_74777_a("Fire", (short)this.field_70151_c);
            p_70109_1_.func_74777_a("Air", (short)this.func_70086_ai());
            p_70109_1_.func_74757_a("OnGround", this.field_70122_E);
            p_70109_1_.func_74768_a("Dimension", this.field_71093_bK);
            p_70109_1_.func_74757_a("Invulnerable", this.field_83001_bt);
            p_70109_1_.func_74768_a("PortalCooldown", this.field_71088_bW);
            // CraftBukkit start
            p_70109_1_.func_74772_a("WorldUUIDLeast", this.field_70170_p.func_72860_G().getUUID().getLeastSignificantBits());
            p_70109_1_.func_74772_a("WorldUUIDMost", this.field_70170_p.func_72860_G().getUUID().getMostSignificantBits());
            p_70109_1_.func_74772_a("UUIDLeast", this.uniqueId.getLeastSignificantBits());
            p_70109_1_.func_74772_a("UUIDMost", this.uniqueId.getMostSignificantBits());
            p_70109_1_.func_74768_a("Bukkit.updateLevel", CURRENT_LEVEL);
            // CraftBukkit end
            this.func_70014_b(p_70109_1_);
        }
        catch (Throwable throwable)
        {
            CrashReport crashreport = CrashReport.func_85055_a(throwable, "Saving entity NBT");
            CrashReportCategory crashreportcategory = crashreport.func_85058_a("Entity being saved");
            this.func_85029_a(crashreportcategory);
            throw new ReportedException(crashreport);
        }
    }

    public void func_70020_e(NBTTagCompound p_70020_1_)
    {
        try
        {
            NBTTagList nbttaglist = p_70020_1_.func_74761_m("Pos");
            NBTTagList nbttaglist1 = p_70020_1_.func_74761_m("Motion");
            NBTTagList nbttaglist2 = p_70020_1_.func_74761_m("Rotation");
            this.field_70159_w = ((NBTTagDouble)nbttaglist1.func_74743_b(0)).field_74755_a;
            this.field_70181_x = ((NBTTagDouble)nbttaglist1.func_74743_b(1)).field_74755_a;
            this.field_70179_y = ((NBTTagDouble)nbttaglist1.func_74743_b(2)).field_74755_a;
            /* CraftBukkit start - moved section down
            if (Math.abs(this.motX) > 10.0D) {
                this.motX = 0.0D;
            }

            if (Math.abs(this.motY) > 10.0D) {
                this.motY = 0.0D;
            }

            if (Math.abs(this.motZ) > 10.0D) {
                this.motZ = 0.0D;
            }
            // CraftBukkit end */
            this.field_70169_q = this.field_70142_S = this.field_70165_t = ((NBTTagDouble)nbttaglist.func_74743_b(0)).field_74755_a;
            this.field_70167_r = this.field_70137_T = this.field_70163_u = ((NBTTagDouble)nbttaglist.func_74743_b(1)).field_74755_a;
            this.field_70166_s = this.field_70136_U = this.field_70161_v = ((NBTTagDouble)nbttaglist.func_74743_b(2)).field_74755_a;
            this.field_70126_B = this.field_70177_z = ((NBTTagFloat)nbttaglist2.func_74743_b(0)).field_74750_a;
            this.field_70127_C = this.field_70125_A = ((NBTTagFloat)nbttaglist2.func_74743_b(1)).field_74750_a;
            this.field_70143_R = p_70020_1_.func_74760_g("FallDistance");
            this.field_70151_c = p_70020_1_.func_74765_d("Fire");
            this.func_70050_g(p_70020_1_.func_74765_d("Air"));
            this.field_70122_E = p_70020_1_.func_74767_n("OnGround");
            this.field_71093_bK = p_70020_1_.func_74762_e("Dimension");
            this.field_83001_bt = p_70020_1_.func_74767_n("Invulnerable");
            this.field_71088_bW = p_70020_1_.func_74762_e("PortalCooldown");
            this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
            // CraftBukkit start
            long least = p_70020_1_.func_74763_f("UUIDLeast");
            long most = p_70020_1_.func_74763_f("UUIDMost");

            if (least != 0L && most != 0L)
            {
                this.uniqueId = new UUID(most, least);
            }

            // CraftBukkit end
            this.func_70101_b(this.field_70177_z, this.field_70125_A);
            this.func_70037_a(p_70020_1_);

            // CraftBukkit start
            if (this instanceof EntityLiving)
            {
                EntityLiving entity = (EntityLiving) this;

                // If the entity does not have a max health set yet, update it (it may have changed after loading the entity)
                if (!p_70020_1_.func_74764_b("Bukkit.MaxHealth"))
                {
                    entity.maxHealth = entity.func_70667_aM();
                }

                // Reset the persistence for tamed animals
                if (entity instanceof EntityTameable && !isLevelAtLeast(p_70020_1_, 2) && !p_70020_1_.func_74767_n("PersistenceRequired"))
                {
                    entity.field_82179_bU = !entity.func_70692_ba();
                }
            }

            // CraftBukkit end

            // CraftBukkit start - exempt Vehicles from notch's sanity check
            if (!(this.getBukkitEntity() instanceof Vehicle))
            {
                if (Math.abs(this.field_70159_w) > 10.0D)
                {
                    this.field_70159_w = 0.0D;
                }

                if (Math.abs(this.field_70181_x) > 10.0D)
                {
                    this.field_70181_x = 0.0D;
                }

                if (Math.abs(this.field_70179_y) > 10.0D)
                {
                    this.field_70179_y = 0.0D;
                }
            }

            // CraftBukkit end

            // CraftBukkit start - reset world
            if (this instanceof EntityPlayerMP)
            {
                Server server = Bukkit.getServer();
                org.bukkit.World bworld = null;
                // TODO: Remove World related checks, replaced with WorldUID.
                String worldName = p_70020_1_.func_74779_i("World");

                if (p_70020_1_.func_74764_b("WorldUUIDMost") && p_70020_1_.func_74764_b("WorldUUIDLeast"))
                {
                    UUID uid = new UUID(p_70020_1_.func_74763_f("WorldUUIDMost"), p_70020_1_.func_74763_f("WorldUUIDLeast"));
                    bworld = server.getWorld(uid);
                }
                else
                {
                    bworld = server.getWorld(worldName);
                }

                if (bworld == null)
                {
                    EntityPlayerMP entityPlayer = (EntityPlayerMP) this;
                    bworld = ((org.bukkit.craftbukkit.CraftServer) server).getServer().func_71218_a(entityPlayer.field_71093_bK).getWorld();
                }

                this.func_70029_a(bworld == null ? null : ((CraftWorld) bworld).getHandle());
            }

            // CraftBukkit end
        }
        catch (Throwable throwable)
        {
            CrashReport crashreport = CrashReport.func_85055_a(throwable, "Loading entity NBT");
            CrashReportCategory crashreportcategory = crashreport.func_85058_a("Entity being loaded");
            this.func_85029_a(crashreportcategory);
            throw new ReportedException(crashreport);
        }
    }

    protected final String func_70022_Q()
    {
        return EntityList.func_75621_b(this);
    }

    protected abstract void func_70037_a(NBTTagCompound nbttagcompound);

    protected abstract void func_70014_b(NBTTagCompound nbttagcompound);

    protected NBTTagList func_70087_a(double ... p_70087_1_)
    {
        NBTTagList nbttaglist = new NBTTagList();
        double[] adouble1 = p_70087_1_;
        int i = p_70087_1_.length;

        for (int j = 0; j < i; ++j)
        {
            double d0 = adouble1[j];
            nbttaglist.func_74742_a(new NBTTagDouble((String) null, d0));
        }

        return nbttaglist;
    }

    protected NBTTagList func_70049_a(float ... p_70049_1_)
    {
        NBTTagList nbttaglist = new NBTTagList();
        float[] afloat1 = p_70049_1_;
        int i = p_70049_1_.length;

        for (int j = 0; j < i; ++j)
        {
            float f = afloat1[j];
            nbttaglist.func_74742_a(new NBTTagFloat((String) null, f));
        }

        return nbttaglist;
    }

    public EntityItem func_70025_b(int p_70025_1_, int p_70025_2_)
    {
        return this.func_70054_a(p_70025_1_, p_70025_2_, 0.0F);
    }

    public EntityItem func_70054_a(int p_70054_1_, int p_70054_2_, float p_70054_3_)
    {
        return this.func_70099_a(new ItemStack(p_70054_1_, p_70054_2_, 0), p_70054_3_);
    }

    public EntityItem func_70099_a(ItemStack p_70099_1_, float p_70099_2_)
    {
        EntityItem entityitem = new EntityItem(this.field_70170_p, this.field_70165_t, this.field_70163_u + (double)p_70099_2_, this.field_70161_v, p_70099_1_);
        entityitem.field_70293_c = 10;
        this.field_70170_p.func_72838_d(entityitem);
        return entityitem;
    }

    public boolean func_70089_S()
    {
        return !this.field_70128_L;
    }

    public boolean func_70094_T()
    {
        for (int i = 0; i < 8; ++i)
        {
            float f = ((float)((i >> 0) % 2) - 0.5F) * this.field_70130_N * 0.8F;
            float f1 = ((float)((i >> 1) % 2) - 0.5F) * 0.1F;
            float f2 = ((float)((i >> 2) % 2) - 0.5F) * this.field_70130_N * 0.8F;
            int j = MathHelper.func_76128_c(this.field_70165_t + (double)f);
            int k = MathHelper.func_76128_c(this.field_70163_u + (double)this.func_70047_e() + (double)f1);
            int l = MathHelper.func_76128_c(this.field_70161_v + (double)f2);

            if (this.field_70170_p.func_72809_s(j, k, l))
            {
                return true;
            }
        }

        return false;
    }

    public boolean func_70085_c(EntityPlayer p_70085_1_)
    {
        return false;
    }

    public AxisAlignedBB func_70114_g(Entity p_70114_1_)
    {
        return null;
    }

    public void func_70098_U()
    {
        if (this.field_70154_o.field_70128_L)
        {
            this.field_70154_o = null;
        }
        else
        {
            this.field_70159_w = 0.0D;
            this.field_70181_x = 0.0D;
            this.field_70179_y = 0.0D;
            this.func_70071_h_();

            if (this.field_70154_o != null)
            {
                this.field_70154_o.func_70043_V();
                this.field_70147_f += (double)(this.field_70154_o.field_70177_z - this.field_70154_o.field_70126_B);

                for (this.field_70149_e += (double)(this.field_70154_o.field_70125_A - this.field_70154_o.field_70127_C); this.field_70147_f >= 180.0D; this.field_70147_f -= 360.0D)
                {
                    ;
                }

                while (this.field_70147_f < -180.0D)
                {
                    this.field_70147_f += 360.0D;
                }

                while (this.field_70149_e >= 180.0D)
                {
                    this.field_70149_e -= 360.0D;
                }

                while (this.field_70149_e < -180.0D)
                {
                    this.field_70149_e += 360.0D;
                }

                double d0 = this.field_70147_f * 0.5D;
                double d1 = this.field_70149_e * 0.5D;
                float f = 10.0F;

                if (d0 > (double)f)
                {
                    d0 = (double)f;
                }

                if (d0 < (double)(-f))
                {
                    d0 = (double)(-f);
                }

                if (d1 > (double)f)
                {
                    d1 = (double)f;
                }

                if (d1 < (double)(-f))
                {
                    d1 = (double)(-f);
                }

                this.field_70147_f -= d0;
                this.field_70149_e -= d1;
                this.field_70177_z = (float)((double)this.field_70177_z + d0);
                this.field_70125_A = (float)((double)this.field_70125_A + d1);
            }
        }
    }

    public void func_70043_V()
    {
        if (!(this.field_70153_n instanceof EntityPlayer) || !((EntityPlayer)this.field_70153_n).func_71066_bF())
        {
            this.field_70153_n.field_70142_S = this.field_70142_S;
            this.field_70153_n.field_70137_T = this.field_70137_T + this.func_70042_X() + this.field_70153_n.func_70033_W();
            this.field_70153_n.field_70136_U = this.field_70136_U;
        }

        this.field_70153_n.func_70107_b(this.field_70165_t, this.field_70163_u + this.func_70042_X() + this.field_70153_n.func_70033_W(), this.field_70161_v);
    }

    public double func_70033_W()
    {
        return (double)this.field_70129_M;
    }

    public double func_70042_X()
    {
        return (double)this.field_70131_O * 0.75D;
    }

    public void func_70078_a(Entity p_70078_1_)
    {
        // CraftBukkit start
        this.setPassengerOf(p_70078_1_);
    }

    public CraftEntity bukkitEntity;

    public CraftEntity getBukkitEntity()
    {
        if (this.bukkitEntity == null)
        {
            this.bukkitEntity = org.bukkit.craftbukkit.entity.CraftEntity.getEntity(this.field_70170_p.getServer(), this);
        }

        return this.bukkitEntity;
    }

    public void setPassengerOf(Entity entity)
    {
        // b(null) doesn't really fly for overloaded methods,
        // so this method is needed
        PluginManager pluginManager = Bukkit.getPluginManager();
        this.getBukkitEntity(); // make sure bukkitEntity is initialised
        // CraftBukkit end
        this.field_70149_e = 0.0D;
        this.field_70147_f = 0.0D;

        if (entity == null)
        {
            if (this.field_70154_o != null)
            {
                // CraftBukkit start
                if ((this.bukkitEntity instanceof LivingEntity) && (this.field_70154_o.getBukkitEntity() instanceof Vehicle))
                {
                    VehicleExitEvent event = new VehicleExitEvent((Vehicle) this.field_70154_o.getBukkitEntity(), (LivingEntity) this.bukkitEntity);
                    pluginManager.callEvent(event);
                }

                // CraftBukkit end
                this.func_70012_b(this.field_70154_o.field_70165_t, this.field_70154_o.field_70121_D.field_72338_b + (double)this.field_70154_o.field_70131_O, this.field_70154_o.field_70161_v, this.field_70177_z, this.field_70125_A);
                this.field_70154_o.field_70153_n = null;
            }

            this.field_70154_o = null;
        }
        else if (this.field_70154_o == entity)
        {
            // CraftBukkit start
            if ((this.bukkitEntity instanceof LivingEntity) && (this.field_70154_o.getBukkitEntity() instanceof Vehicle))
            {
                VehicleExitEvent event = new VehicleExitEvent((Vehicle) this.field_70154_o.getBukkitEntity(), (LivingEntity) this.bukkitEntity);
                pluginManager.callEvent(event);
            }

            // CraftBukkit end
            this.func_70061_h(entity);
            this.field_70154_o.field_70153_n = null;
            this.field_70154_o = null;
        }
        else
        {
            // CraftBukkit start
            if ((this.bukkitEntity instanceof LivingEntity) && (entity.getBukkitEntity() instanceof Vehicle))
            {
                VehicleEnterEvent event = new VehicleEnterEvent((Vehicle) entity.getBukkitEntity(), this.bukkitEntity);
                pluginManager.callEvent(event);

                if (event.isCancelled())
                {
                    return;
                }
            }

            // CraftBukkit end

            if (this.field_70154_o != null)
            {
                this.field_70154_o.field_70153_n = null;
            }

            if (entity.field_70153_n != null)
            {
                entity.field_70153_n.field_70154_o = null;
            }

            this.field_70154_o = entity;
            entity.field_70153_n = this;
        }
    }

    public void func_70061_h(Entity p_70061_1_)
    {
        double d0 = p_70061_1_.field_70165_t;
        double d1 = p_70061_1_.field_70121_D.field_72338_b + (double)p_70061_1_.field_70131_O;
        double d2 = p_70061_1_.field_70161_v;

        for (double d3 = -1.5D; d3 < 2.0D; ++d3)
        {
            for (double d4 = -1.5D; d4 < 2.0D; ++d4)
            {
                if (d3 != 0.0D || d4 != 0.0D)
                {
                    int i = (int)(this.field_70165_t + d3);
                    int j = (int)(this.field_70161_v + d4);
                    AxisAlignedBB axisalignedbb = this.field_70121_D.func_72325_c(d3, 1.0D, d4);

                    if (this.field_70170_p.func_72840_a(axisalignedbb).isEmpty())
                    {
                        if (this.field_70170_p.func_72797_t(i, (int)this.field_70163_u, j))
                        {
                            this.func_70012_b(this.field_70165_t + d3, this.field_70163_u + 1.0D, this.field_70161_v + d4, this.field_70177_z, this.field_70125_A);
                            return;
                        }

                        if (this.field_70170_p.func_72797_t(i, (int)this.field_70163_u - 1, j) || this.field_70170_p.func_72803_f(i, (int)this.field_70163_u - 1, j) == Material.field_76244_g)
                        {
                            d0 = this.field_70165_t + d3;
                            d1 = this.field_70163_u + 1.0D;
                            d2 = this.field_70161_v + d4;
                        }
                    }
                }
            }
        }

        this.func_70012_b(d0, d1, d2, this.field_70177_z, this.field_70125_A);
    }

    public float func_70111_Y()
    {
        return 0.1F;
    }

    public Vec3 func_70040_Z()
    {
        return null;
    }

    public void func_70063_aa()
    {
        if (this.field_71088_bW > 0)
        {
            this.field_71088_bW = this.func_82147_ab();
        }
        else
        {
            double d0 = this.field_70169_q - this.field_70165_t;
            double d1 = this.field_70166_s - this.field_70161_v;

            if (!this.field_70170_p.field_72995_K && !this.field_71087_bX)
            {
                this.field_82152_aq = Direction.func_82372_a(d0, d1);
            }

            this.field_71087_bX = true;
        }
    }

    public int func_82147_ab()
    {
        return 900;
    }

    public ItemStack[] func_70035_c()
    {
        return null;
    }

    public void func_70062_b(int p_70062_1_, ItemStack p_70062_2_) {}

    public boolean func_70027_ad()
    {
        return this.field_70151_c > 0 || this.func_70083_f(0);
    }

    public boolean func_70115_ae()
    {
        return this.field_70154_o != null || this.func_70083_f(2);
    }

    public boolean func_70093_af()
    {
        return this.func_70083_f(1);
    }

    public void func_70095_a(boolean p_70095_1_)
    {
        this.func_70052_a(1, p_70095_1_);
    }

    public boolean func_70051_ag()
    {
        return this.func_70083_f(3);
    }

    public void func_70031_b(boolean p_70031_1_)
    {
        this.func_70052_a(3, p_70031_1_);
    }

    public boolean func_82150_aj()
    {
        return this.func_70083_f(5);
    }

    public void func_82142_c(boolean p_82142_1_)
    {
        this.func_70052_a(5, p_82142_1_);
    }

    public void func_70019_c(boolean p_70019_1_)
    {
        this.func_70052_a(4, p_70019_1_);
    }

    protected boolean func_70083_f(int p_70083_1_)
    {
        return (this.field_70180_af.func_75683_a(0) & 1 << p_70083_1_) != 0;
    }

    protected void func_70052_a(int p_70052_1_, boolean p_70052_2_)
    {
        byte b0 = this.field_70180_af.func_75683_a(0);

        if (p_70052_2_)
        {
            this.field_70180_af.func_75692_b(0, Byte.valueOf((byte)(b0 | 1 << p_70052_1_)));
        }
        else
        {
            this.field_70180_af.func_75692_b(0, Byte.valueOf((byte)(b0 & ~(1 << p_70052_1_))));
        }
    }

    public int func_70086_ai()
    {
        return this.field_70180_af.func_75693_b(1);
    }

    public void func_70050_g(int p_70050_1_)
    {
        this.field_70180_af.func_75692_b(1, Short.valueOf((short)p_70050_1_));
    }

    public void func_70077_a(EntityLightningBolt p_70077_1_)
    {
        // CraftBukkit start
        final org.bukkit.entity.Entity thisBukkitEntity = this.getBukkitEntity();
        final org.bukkit.entity.Entity stormBukkitEntity = p_70077_1_.getBukkitEntity();
        final PluginManager pluginManager = Bukkit.getPluginManager();

        if (thisBukkitEntity instanceof Painting)
        {
            PaintingBreakByEntityEvent event = new PaintingBreakByEntityEvent((Painting) thisBukkitEntity, stormBukkitEntity);
            pluginManager.callEvent(event);

            if (event.isCancelled())
            {
                return;
            }
        }

        EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(stormBukkitEntity, thisBukkitEntity, EntityDamageEvent.DamageCause.LIGHTNING, 5);
        pluginManager.callEvent(event);

        if (event.isCancelled())
        {
            return;
        }

        thisBukkitEntity.setLastDamageCause(event);
        this.func_70081_e(event.getDamage());
        // CraftBukkit end
        ++this.field_70151_c;

        if (this.field_70151_c == 0)
        {
            // CraftBukkit start - raise a combust event when lightning strikes
            EntityCombustByEntityEvent entityCombustEvent = new EntityCombustByEntityEvent(stormBukkitEntity, thisBukkitEntity, 8);
            pluginManager.callEvent(entityCombustEvent);

            if (!entityCombustEvent.isCancelled())
            {
                this.func_70015_d(entityCombustEvent.getDuration());
            }

            // CraftBukkit end
        }
    }

    public void func_70074_a(EntityLiving p_70074_1_) {}

    protected boolean func_70048_i(double p_70048_1_, double p_70048_3_, double p_70048_5_)
    {
        int i = MathHelper.func_76128_c(p_70048_1_);
        int j = MathHelper.func_76128_c(p_70048_3_);
        int k = MathHelper.func_76128_c(p_70048_5_);
        double d3 = p_70048_1_ - (double)i;
        double d4 = p_70048_3_ - (double)j;
        double d5 = p_70048_5_ - (double)k;
        List list = this.field_70170_p.func_72840_a(this.field_70121_D);

        if (list.isEmpty() && !this.field_70170_p.func_85174_u(i, j, k))
        {
            return false;
        }
        else
        {
            boolean flag = !this.field_70170_p.func_85174_u(i - 1, j, k);
            boolean flag1 = !this.field_70170_p.func_85174_u(i + 1, j, k);
            boolean flag2 = !this.field_70170_p.func_85174_u(i, j - 1, k);
            boolean flag3 = !this.field_70170_p.func_85174_u(i, j + 1, k);
            boolean flag4 = !this.field_70170_p.func_85174_u(i, j, k - 1);
            boolean flag5 = !this.field_70170_p.func_85174_u(i, j, k + 1);
            byte b0 = 3;
            double d6 = 9999.0D;

            if (flag && d3 < d6)
            {
                d6 = d3;
                b0 = 0;
            }

            if (flag1 && 1.0D - d3 < d6)
            {
                d6 = 1.0D - d3;
                b0 = 1;
            }

            if (flag3 && 1.0D - d4 < d6)
            {
                d6 = 1.0D - d4;
                b0 = 3;
            }

            if (flag4 && d5 < d6)
            {
                d6 = d5;
                b0 = 4;
            }

            if (flag5 && 1.0D - d5 < d6)
            {
                d6 = 1.0D - d5;
                b0 = 5;
            }

            float f = this.field_70146_Z.nextFloat() * 0.2F + 0.1F;

            if (b0 == 0)
            {
                this.field_70159_w = (double)(-f);
            }

            if (b0 == 1)
            {
                this.field_70159_w = (double)f;
            }

            if (b0 == 2)
            {
                this.field_70181_x = (double)(-f);
            }

            if (b0 == 3)
            {
                this.field_70181_x = (double)f;
            }

            if (b0 == 4)
            {
                this.field_70179_y = (double)(-f);
            }

            if (b0 == 5)
            {
                this.field_70179_y = (double)f;
            }

            return true;
        }
    }

    public void func_70110_aj()
    {
        this.field_70134_J = true;
        this.field_70143_R = 0.0F;
    }

    public String func_70023_ak()
    {
        String s = EntityList.func_75621_b(this);

        if (s == null)
        {
            s = "generic";
        }

        return StatCollector.func_74838_a("entity." + s + ".name");
    }

    public Entity[] func_70021_al()
    {
        return null;
    }

    public boolean func_70028_i(Entity p_70028_1_)
    {
        return this == p_70028_1_;
    }

    public float func_70079_am()
    {
        return 0.0F;
    }

    public boolean func_70075_an()
    {
        return true;
    }

    public boolean func_85031_j(Entity p_85031_1_)
    {
        return false;
    }

    public String toString()
    {
        return String.format("%s[\'%s\'/%d, l=\'%s\', x=%.2f, y=%.2f, z=%.2f]", new Object[] {this.getClass().getSimpleName(), this.func_70023_ak(), Integer.valueOf(this.field_70157_k), this.field_70170_p == null ? "~NULL~" : this.field_70170_p.func_72912_H().func_76065_j(), Double.valueOf(this.field_70165_t), Double.valueOf(this.field_70163_u), Double.valueOf(this.field_70161_v)});
    }

    public boolean func_85032_ar()
    {
        return this.field_83001_bt;
    }

    public void func_82149_j(Entity p_82149_1_)
    {
        this.func_70012_b(p_82149_1_.field_70165_t, p_82149_1_.field_70163_u, p_82149_1_.field_70161_v, p_82149_1_.field_70177_z, p_82149_1_.field_70125_A);
    }

    public void func_82141_a(Entity p_82141_1_, boolean p_82141_2_)
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        p_82141_1_.func_70109_d(nbttagcompound);
        this.func_70020_e(nbttagcompound);
        this.field_71088_bW = p_82141_1_.field_71088_bW;
        this.field_82152_aq = p_82141_1_.field_82152_aq;
    }

    public void func_71027_c(int p_71027_1_)
    {
        if (!this.field_70170_p.field_72995_K && !this.field_70128_L)
        {
            this.field_70170_p.field_72984_F.func_76320_a("changeDimension");
            MinecraftServer minecraftserver = MinecraftServer.func_71276_C();
            // CraftBukkit start - move logic into new function "teleportToLocation"
            // int j = this.dimension;
            WorldServer exitWorld = null;

            if (this.field_71093_bK < CraftWorld.CUSTOM_DIMENSION_OFFSET)   // plugins must specify exit from custom Bukkit worlds
            {
                // only target existing worlds (compensate for allow-nether/allow-end as false)
                for (WorldServer world : minecraftserver.worlds)
                {
                    if (world.dimension == p_71027_1_)
                    {
                        exitWorld = world;
                    }
                }
            }

            Location enter = this.getBukkitEntity().getLocation();
            Location exit = exitWorld != null ? minecraftserver.func_71203_ab().calculateTarget(enter, minecraftserver.func_71218_a(p_71027_1_)) : null;
            boolean useTravelAgent = exitWorld != null && !(this.field_71093_bK == 1 && exitWorld.dimension == 1); // don't use agent for custom worlds or return from THE_END
            TravelAgent agent = exit != null ? (TravelAgent)((CraftWorld) exit.getWorld()).getHandle().func_85176_s() : null;
            EntityPortalEvent event = new EntityPortalEvent(this.getBukkitEntity(), enter, exit, agent);
            event.useTravelAgent(useTravelAgent);
            event.getEntity().getServer().getPluginManager().callEvent(event);

            if (event.isCancelled() || event.getTo() == null || !this.func_70089_S())
            {
                return;
            }

            exit = event.useTravelAgent() ? event.getPortalTravelAgent().findOrCreate(exit) : event.getTo();
            this.teleportTo(exit, true);
        }
    }

    public void teleportTo(Location exit, boolean portal)
    {
        if (true)
        {
            WorldServer worldserver = ((CraftWorld) this.getBukkitEntity().getLocation().getWorld()).getHandle();
            WorldServer worldserver1 = ((CraftWorld) exit.getWorld()).getHandle();
            int i = worldserver1.dimension;
            // CraftBukkit end
            this.field_71093_bK = i;
            this.field_70170_p.func_72900_e(this);
            this.field_70128_L = false;
            this.field_70170_p.field_72984_F.func_76320_a("reposition");
            // CraftBukkit start - ensure chunks are loaded in case TravelAgent is not used which would initially cause chunks to load during find/create
            // minecraftserver.getPlayerList().a(this, j, worldserver, worldserver1);
            boolean before = worldserver1.field_73059_b.field_73250_a;
            worldserver1.field_73059_b.field_73250_a = true;
            worldserver1.func_73046_m().func_71203_ab().repositionEntity(this, exit, portal);
            worldserver1.field_73059_b.field_73250_a = before;
            // CraftBukkit end
            this.field_70170_p.field_72984_F.func_76318_c("reloading");
            Entity entity = EntityList.func_75620_a(EntityList.func_75621_b(this), worldserver1);

            if (entity != null)
            {
                entity.func_82141_a(this, true);
                worldserver1.func_72838_d(entity);
                // CraftBukkit start - forward the CraftEntity to the new entity
                this.getBukkitEntity().setHandle(entity);
                entity.bukkitEntity = this.getBukkitEntity();
                // CraftBukkit end
            }

            this.field_70128_L = true;
            this.field_70170_p.field_72984_F.func_76319_b();
            worldserver.func_82742_i();
            worldserver1.func_82742_i();
            this.field_70170_p.field_72984_F.func_76319_b();
        }
    }

    public float func_82146_a(Explosion p_82146_1_, Block p_82146_2_, int p_82146_3_, int p_82146_4_, int p_82146_5_)
    {
        return p_82146_2_.func_71904_a(this);
    }

    public int func_82143_as()
    {
        return 3;
    }

    public int func_82148_at()
    {
        return this.field_82152_aq;
    }

    public boolean func_82144_au()
    {
        return false;
    }

    public void func_85029_a(CrashReportCategory p_85029_1_)
    {
        p_85029_1_.func_71500_a("Entity Type", (Callable)(new CallableEntityType(this)));
        p_85029_1_.func_71507_a("Entity ID", Integer.valueOf(this.field_70157_k));
        p_85029_1_.func_71507_a("Name", this.func_70023_ak());
        p_85029_1_.func_71507_a("Exact location", String.format("%.2f, %.2f, %.2f", new Object[] {Double.valueOf(this.field_70165_t), Double.valueOf(this.field_70163_u), Double.valueOf(this.field_70161_v)}));
        p_85029_1_.func_71507_a("Block location", CrashReportCategory.func_85071_a(MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c(this.field_70163_u), MathHelper.func_76128_c(this.field_70161_v)));
        p_85029_1_.func_71507_a("Momentum", String.format("%.2f, %.2f, %.2f", new Object[] {Double.valueOf(this.field_70159_w), Double.valueOf(this.field_70181_x), Double.valueOf(this.field_70179_y)}));
    }
}