package net.minecraft.entity.item;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRail;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
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
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.craftbukkit.entity.CraftHumanEntity;
// CraftBukkit end

public class EntityMinecart extends Entity implements IInventory
{
    private ItemStack[] field_70501_d;
    private int field_70502_e;
    private boolean field_70499_f;
    public int field_70505_a;
    public double field_70503_b;
    public double field_70504_c;
    private final IUpdatePlayerListBox field_82344_g;
    private boolean field_82345_h;
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
    public List<HumanEntity> transaction = new java.util.ArrayList<HumanEntity>();
    private int maxStack = MAX_STACK;

    public ItemStack[] getContents()
    {
        return this.field_70501_d;
    }

    public void onOpen(CraftHumanEntity who)
    {
        transaction.add(who);
    }

    public void onClose(CraftHumanEntity who)
    {
        transaction.remove(who);
    }

    public List<HumanEntity> getViewers()
    {
        return transaction;
    }

    public InventoryHolder getOwner()
    {
        org.bukkit.entity.Entity cart = getBukkitEntity();

        if (cart instanceof InventoryHolder)
        {
            return (InventoryHolder) cart;
        }

        return null;
    }

    public void setMaxStackSize(int size)
    {
        maxStack = size;
    }
    // CraftBukkit end

    public EntityMinecart(World p_i3541_1_)
    {
        super(p_i3541_1_);
        this.field_70501_d = new ItemStack[27]; // CraftBukkit
        this.field_70502_e = 0;
        this.field_70499_f = false;
        this.field_82345_h = true;
        this.field_70156_m = true;
        this.func_70105_a(0.98F, 0.7F);
        this.field_70129_M = this.field_70131_O / 2.0F;
        this.field_82344_g = p_i3541_1_ != null ? p_i3541_1_.func_82735_a(this) : null;
    }

    protected boolean func_70041_e_()
    {
        return false;
    }

    protected void func_70088_a()
    {
        this.field_70180_af.func_75682_a(16, new Byte((byte)0));
        this.field_70180_af.func_75682_a(17, new Integer(0));
        this.field_70180_af.func_75682_a(18, new Integer(1));
        this.field_70180_af.func_75682_a(19, new Integer(0));
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

    public EntityMinecart(World p_i3542_1_, double p_i3542_2_, double p_i3542_4_, double p_i3542_6_, int p_i3542_8_)
    {
        this(p_i3542_1_);
        this.func_70107_b(p_i3542_2_, p_i3542_4_ + (double)this.field_70129_M, p_i3542_6_);
        this.field_70159_w = 0.0D;
        this.field_70181_x = 0.0D;
        this.field_70179_y = 0.0D;
        this.field_70169_q = p_i3542_2_;
        this.field_70167_r = p_i3542_4_;
        this.field_70166_s = p_i3542_6_;
        this.field_70505_a = p_i3542_8_;
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
        else
        {
            if (!this.field_70170_p.field_72995_K && !this.field_70128_L)
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

                if (p_70097_1_.func_76346_g() instanceof EntityPlayer && ((EntityPlayer)p_70097_1_.func_76346_g()).field_71075_bZ.field_75098_d)
                {
                    this.func_70492_c(100);
                }

                if (this.func_70491_i() > 40)
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
                    this.func_70106_y();
                    this.func_70054_a(Item.field_77773_az.field_77779_bT, 1, 0.0F);

                    if (this.field_70505_a == 1)
                    {
                        EntityMinecart entityminecart = this;

                        for (int j = 0; j < entityminecart.func_70302_i_(); ++j)
                        {
                            ItemStack itemstack = entityminecart.func_70301_a(j);

                            if (itemstack != null)
                            {
                                float f = this.field_70146_Z.nextFloat() * 0.8F + 0.1F;
                                float f1 = this.field_70146_Z.nextFloat() * 0.8F + 0.1F;
                                float f2 = this.field_70146_Z.nextFloat() * 0.8F + 0.1F;

                                while (itemstack.field_77994_a > 0)
                                {
                                    int k = this.field_70146_Z.nextInt(21) + 10;

                                    if (k > itemstack.field_77994_a)
                                    {
                                        k = itemstack.field_77994_a;
                                    }

                                    itemstack.field_77994_a -= k;
                                    // CraftBukkit - include enchantments in the new itemstack
                                    EntityItem entityitem = new EntityItem(this.field_70170_p, this.field_70165_t + (double) f, this.field_70163_u + (double) f1, this.field_70161_v + (double) f2, org.bukkit.craftbukkit.inventory.CraftItemStack.copyNMSStack(itemstack, k));
                                    float f3 = 0.05F;
                                    entityitem.field_70159_w = (double)((float)this.field_70146_Z.nextGaussian() * f3);
                                    entityitem.field_70181_x = (double)((float)this.field_70146_Z.nextGaussian() * f3 + 0.2F);
                                    entityitem.field_70179_y = (double)((float)this.field_70146_Z.nextGaussian() * f3);
                                    this.field_70170_p.func_72838_d(entityitem);
                                }
                            }
                        }

                        this.func_70054_a(Block.field_72077_au.field_71990_ca, 1, 0.0F);
                    }
                    else if (this.field_70505_a == 2)
                    {
                        this.func_70054_a(Block.field_72051_aB.field_71990_ca, 1, 0.0F);
                    }
                }

                return true;
            }
            else
            {
                return true;
            }
        }
    }

    public boolean func_70067_L()
    {
        return !this.field_70128_L;
    }

    public void func_70106_y()
    {
        if (this.field_82345_h)
        {
            for (int i = 0; i < this.func_70302_i_(); ++i)
            {
                ItemStack itemstack = this.func_70301_a(i);

                if (itemstack != null)
                {
                    float f = this.field_70146_Z.nextFloat() * 0.8F + 0.1F;
                    float f1 = this.field_70146_Z.nextFloat() * 0.8F + 0.1F;
                    float f2 = this.field_70146_Z.nextFloat() * 0.8F + 0.1F;

                    while (itemstack.field_77994_a > 0)
                    {
                        int j = this.field_70146_Z.nextInt(21) + 10;

                        if (j > itemstack.field_77994_a)
                        {
                            j = itemstack.field_77994_a;
                        }

                        itemstack.field_77994_a -= j;
                        EntityItem entityitem = new EntityItem(this.field_70170_p, this.field_70165_t + (double)f, this.field_70163_u + (double)f1, this.field_70161_v + (double)f2, new ItemStack(itemstack.field_77993_c, j, itemstack.func_77960_j()));

                        if (itemstack.func_77942_o())
                        {
                            entityitem.func_92059_d().func_77982_d((NBTTagCompound)itemstack.func_77978_p().func_74737_b());
                        }

                        float f3 = 0.05F;
                        entityitem.field_70159_w = (double)((float)this.field_70146_Z.nextGaussian() * f3);
                        entityitem.field_70181_x = (double)((float)this.field_70146_Z.nextGaussian() * f3 + 0.2F);
                        entityitem.field_70179_y = (double)((float)this.field_70146_Z.nextGaussian() * f3);
                        this.field_70170_p.func_72838_d(entityitem);
                    }
                }
            }
        }

        super.func_70106_y();

        if (this.field_82344_g != null)
        {
            this.field_82344_g.func_73660_a();
        }
    }

    public void func_71027_c(int p_71027_1_)
    {
        this.field_82345_h = false;
        super.func_71027_c(p_71027_1_);
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

        if (this.func_70490_h() && this.field_70146_Z.nextInt(4) == 0)
        {
            this.field_70170_p.func_72869_a("largesmoke", this.field_70165_t, this.field_70163_u + 0.8D, this.field_70161_v, 0.0D, 0.0D, 0.0D);
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

            if (BlockRail.func_72180_d_(this.field_70170_p, j, i - 1, k))
            {
                --i;
            }

            // CraftBukkit
            double d4 = this.maxSpeed;
            double d5 = 0.0078125D;
            int l = this.field_70170_p.func_72798_a(j, i, k);

            if (BlockRail.func_72184_d(l))
            {
                this.field_70143_R = 0.0F;
                Vec3 vec3 = this.func_70489_a(this.field_70165_t, this.field_70163_u, this.field_70161_v);
                int i1 = this.field_70170_p.func_72805_g(j, i, k);
                this.field_70163_u = (double)i;
                boolean flag = false;
                boolean flag1 = false;

                if (l == Block.field_71954_T.field_71990_ca)
                {
                    flag = (i1 & 8) != 0;
                    flag1 = !flag;
                }

                if (((BlockRail)Block.field_71973_m[l]).func_72183_n())
                {
                    i1 &= 7;
                }

                if (i1 >= 2 && i1 <= 5)
                {
                    this.field_70163_u = (double)(i + 1);
                }

                if (i1 == 2)
                {
                    this.field_70159_w -= d5;
                }

                if (i1 == 3)
                {
                    this.field_70159_w += d5;
                }

                if (i1 == 4)
                {
                    this.field_70179_y += d5;
                }

                if (i1 == 5)
                {
                    this.field_70179_y -= d5;
                }

                int[][] aint = field_70500_g[i1];
                double d6 = (double)(aint[1][0] - aint[0][0]);
                double d7 = (double)(aint[1][2] - aint[0][2]);
                double d8 = Math.sqrt(d6 * d6 + d7 * d7);
                double d9 = this.field_70159_w * d6 + this.field_70179_y * d7;

                if (d9 < 0.0D)
                {
                    d6 = -d6;
                    d7 = -d7;
                }

                double d10 = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
                this.field_70159_w = d10 * d6 / d8;
                this.field_70179_y = d10 * d7 / d8;
                double d11;
                double d12;

                if (this.field_70153_n != null)
                {
                    d12 = this.field_70153_n.field_70159_w * this.field_70153_n.field_70159_w + this.field_70153_n.field_70179_y * this.field_70153_n.field_70179_y;
                    d11 = this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y;

                    if (d12 > 1.0E-4D && d11 < 0.01D)
                    {
                        this.field_70159_w += this.field_70153_n.field_70159_w * 0.1D;
                        this.field_70179_y += this.field_70153_n.field_70179_y * 0.1D;
                        flag1 = false;
                    }
                }

                if (flag1)
                {
                    d12 = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);

                    if (d12 < 0.03D)
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

                d12 = 0.0D;
                d11 = (double) j + 0.5D + (double) aint[0][0] * 0.5D;
                double d13 = (double)k + 0.5D + (double)aint[0][2] * 0.5D;
                double d14 = (double)j + 0.5D + (double)aint[1][0] * 0.5D;
                double d15 = (double)k + 0.5D + (double)aint[1][2] * 0.5D;
                d6 = d14 - d11;
                d7 = d15 - d13;
                double d16;
                double d17;

                if (d6 == 0.0D)
                {
                    this.field_70165_t = (double)j + 0.5D;
                    d12 = this.field_70161_v - (double) k;
                }
                else if (d7 == 0.0D)
                {
                    this.field_70161_v = (double)k + 0.5D;
                    d12 = this.field_70165_t - (double) j;
                }
                else
                {
                    d16 = this.field_70165_t - d11;
                    d17 = this.field_70161_v - d13;
                    d12 = (d16 * d6 + d17 * d7) * 2.0D;
                }

                this.field_70165_t = d11 + d6 * d12;
                this.field_70161_v = d13 + d7 * d12;
                this.func_70107_b(this.field_70165_t, this.field_70163_u + (double)this.field_70129_M, this.field_70161_v);
                d16 = this.field_70159_w;
                d17 = this.field_70179_y;

                if (this.field_70153_n != null)
                {
                    d16 *= 0.75D;
                    d17 *= 0.75D;
                }

                if (d16 < -d4)
                {
                    d16 = -d4;
                }

                if (d16 > d4)
                {
                    d16 = d4;
                }

                if (d17 < -d4)
                {
                    d17 = -d4;
                }

                if (d17 > d4)
                {
                    d17 = d4;
                }

                this.func_70091_d(d16, 0.0D, d17);

                if (aint[0][1] != 0 && MathHelper.func_76128_c(this.field_70165_t) - j == aint[0][0] && MathHelper.func_76128_c(this.field_70161_v) - k == aint[0][2])
                {
                    this.func_70107_b(this.field_70165_t, this.field_70163_u + (double)aint[0][1], this.field_70161_v);
                }
                else if (aint[1][1] != 0 && MathHelper.func_76128_c(this.field_70165_t) - j == aint[1][0] && MathHelper.func_76128_c(this.field_70161_v) - k == aint[1][2])
                {
                    this.func_70107_b(this.field_70165_t, this.field_70163_u + (double)aint[1][1], this.field_70161_v);
                }

                // CraftBukkit
                if (this.field_70153_n != null || !this.slowWhenEmpty)
                {
                    this.field_70159_w *= 0.996999979019165D;
                    this.field_70181_x *= 0.0D;
                    this.field_70179_y *= 0.996999979019165D;
                }
                else
                {
                    if (this.field_70505_a == 2)
                    {
                        double d18 = this.field_70503_b * this.field_70503_b + this.field_70504_c * this.field_70504_c;

                        if (d18 > 1.0E-4D)
                        {
                            d18 = (double)MathHelper.func_76133_a(d18);
                            this.field_70503_b /= d18;
                            this.field_70504_c /= d18;
                            double d19 = 0.04D;
                            this.field_70159_w *= 0.800000011920929D;
                            this.field_70181_x *= 0.0D;
                            this.field_70179_y *= 0.800000011920929D;
                            this.field_70159_w += this.field_70503_b * d19;
                            this.field_70179_y += this.field_70504_c * d19;
                        }
                        else
                        {
                            this.field_70159_w *= 0.8999999761581421D;
                            this.field_70181_x *= 0.0D;
                            this.field_70179_y *= 0.8999999761581421D;
                        }
                    }

                    this.field_70159_w *= 0.9599999785423279D;
                    this.field_70181_x *= 0.0D;
                    this.field_70179_y *= 0.9599999785423279D;
                }

                Vec3 vec31 = this.func_70489_a(this.field_70165_t, this.field_70163_u, this.field_70161_v);

                if (vec31 != null && vec3 != null)
                {
                    double d20 = (vec3.field_72448_b - vec31.field_72448_b) * 0.05D;
                    d10 = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);

                    if (d10 > 0.0D)
                    {
                        this.field_70159_w = this.field_70159_w / d10 * (d10 + d20);
                        this.field_70179_y = this.field_70179_y / d10 * (d10 + d20);
                    }

                    this.func_70107_b(this.field_70165_t, vec31.field_72448_b, this.field_70161_v);
                }

                int j1 = MathHelper.func_76128_c(this.field_70165_t);
                int k1 = MathHelper.func_76128_c(this.field_70161_v);

                if (j1 != j || k1 != k)
                {
                    d10 = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
                    this.field_70159_w = d10 * (double)(j1 - j);
                    this.field_70179_y = d10 * (double)(k1 - k);
                }

                double d21;

                if (this.field_70505_a == 2)
                {
                    d21 = this.field_70503_b * this.field_70503_b + this.field_70504_c * this.field_70504_c;

                    if (d21 > 1.0E-4D && this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y > 0.001D)
                    {
                        d21 = (double)MathHelper.func_76133_a(d21);
                        this.field_70503_b /= d21;
                        this.field_70504_c /= d21;

                        if (this.field_70503_b * this.field_70159_w + this.field_70504_c * this.field_70179_y < 0.0D)
                        {
                            this.field_70503_b = 0.0D;
                            this.field_70504_c = 0.0D;
                        }
                        else
                        {
                            this.field_70503_b = this.field_70159_w;
                            this.field_70504_c = this.field_70179_y;
                        }
                    }
                }

                if (flag)
                {
                    d21 = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);

                    if (d21 > 0.01D)
                    {
                        double d22 = 0.06D;
                        this.field_70159_w += this.field_70159_w / d21 * d22;
                        this.field_70179_y += this.field_70179_y / d21 * d22;
                    }
                    else if (i1 == 1)
                    {
                        if (this.field_70170_p.func_72809_s(j - 1, i, k))
                        {
                            this.field_70159_w = 0.02D;
                        }
                        else if (this.field_70170_p.func_72809_s(j + 1, i, k))
                        {
                            this.field_70159_w = -0.02D;
                        }
                    }
                    else if (i1 == 0)
                    {
                        if (this.field_70170_p.func_72809_s(j, i, k - 1))
                        {
                            this.field_70179_y = 0.02D;
                        }
                        else if (this.field_70170_p.func_72809_s(j, i, k + 1))
                        {
                            this.field_70179_y = -0.02D;
                        }
                    }
                }
            }
            else
            {
                if (this.field_70159_w < -d4)
                {
                    this.field_70159_w = -d4;
                }

                if (this.field_70159_w > d4)
                {
                    this.field_70159_w = d4;
                }

                if (this.field_70179_y < -d4)
                {
                    this.field_70179_y = -d4;
                }

                if (this.field_70179_y > d4)
                {
                    this.field_70179_y = d4;
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

            this.func_70017_D();
            this.field_70125_A = 0.0F;
            double d23 = this.field_70169_q - this.field_70165_t;
            double d24 = this.field_70166_s - this.field_70161_v;

            if (d23 * d23 + d24 * d24 > 0.001D)
            {
                this.field_70177_z = (float)(Math.atan2(d24, d23) * 180.0D / Math.PI);

                if (this.field_70499_f)
                {
                    this.field_70177_z += 180.0F;
                }
            }

            double d25 = (double)MathHelper.func_76142_g(this.field_70177_z - this.field_70126_B);

            if (d25 < -170.0D || d25 >= 170.0D)
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
                for (int l1 = 0; l1 < list.size(); ++l1)
                {
                    Entity entity = (Entity)list.get(l1);

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

            if (this.field_70502_e > 0)
            {
                --this.field_70502_e;
            }

            if (this.field_70502_e <= 0)
            {
                this.field_70503_b = this.field_70504_c = 0.0D;
            }

            this.func_70498_d(this.field_70502_e > 0);
        }
    }

    public Vec3 func_70489_a(double p_70489_1_, double p_70489_3_, double p_70489_5_)
    {
        int i = MathHelper.func_76128_c(p_70489_1_);
        int j = MathHelper.func_76128_c(p_70489_3_);
        int k = MathHelper.func_76128_c(p_70489_5_);

        if (BlockRail.func_72180_d_(this.field_70170_p, i, j - 1, k))
        {
            --j;
        }

        int l = this.field_70170_p.func_72798_a(i, j, k);

        if (BlockRail.func_72184_d(l))
        {
            int i1 = this.field_70170_p.func_72805_g(i, j, k);
            p_70489_3_ = (double)j;

            if (((BlockRail)Block.field_71973_m[l]).func_72183_n())
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

    protected void func_70014_b(NBTTagCompound p_70014_1_)
    {
        p_70014_1_.func_74768_a("Type", this.field_70505_a);

        if (this.field_70505_a == 2)
        {
            p_70014_1_.func_74780_a("PushX", this.field_70503_b);
            p_70014_1_.func_74780_a("PushZ", this.field_70504_c);
            p_70014_1_.func_74777_a("Fuel", (short)this.field_70502_e);
        }
        else if (this.field_70505_a == 1)
        {
            NBTTagList nbttaglist = new NBTTagList();

            for (int i = 0; i < this.field_70501_d.length; ++i)
            {
                if (this.field_70501_d[i] != null)
                {
                    NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                    nbttagcompound1.func_74774_a("Slot", (byte)i);
                    this.field_70501_d[i].func_77955_b(nbttagcompound1);
                    nbttaglist.func_74742_a(nbttagcompound1);
                }
            }

            p_70014_1_.func_74782_a("Items", nbttaglist);
        }
    }

    protected void func_70037_a(NBTTagCompound p_70037_1_)
    {
        this.field_70505_a = p_70037_1_.func_74762_e("Type");

        if (this.field_70505_a == 2)
        {
            this.field_70503_b = p_70037_1_.func_74769_h("PushX");
            this.field_70504_c = p_70037_1_.func_74769_h("PushZ");
            this.field_70502_e = p_70037_1_.func_74765_d("Fuel");
        }
        else if (this.field_70505_a == 1)
        {
            NBTTagList nbttaglist = p_70037_1_.func_74761_m("Items");
            this.field_70501_d = new ItemStack[this.func_70302_i_()];

            for (int i = 0; i < nbttaglist.func_74745_c(); ++i)
            {
                NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.func_74743_b(i);
                int j = nbttagcompound1.func_74771_c("Slot") & 255;

                if (j >= 0 && j < this.field_70501_d.length)
                {
                    this.field_70501_d[j] = ItemStack.func_77949_a(nbttagcompound1);
                }
            }
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

                if (p_70108_1_ instanceof EntityLiving && !(p_70108_1_ instanceof EntityPlayer) && !(p_70108_1_ instanceof EntityIronGolem) && this.field_70505_a == 0 && this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y > 0.01D && this.field_70153_n == null && p_70108_1_.field_70154_o == null)
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

                        if (((EntityMinecart)p_70108_1_).field_70505_a == 2 && this.field_70505_a != 2)
                        {
                            this.field_70159_w *= 0.20000000298023224D;
                            this.field_70179_y *= 0.20000000298023224D;
                            this.func_70024_g(p_70108_1_.field_70159_w - d0, 0.0D, p_70108_1_.field_70179_y - d1);
                            p_70108_1_.field_70159_w *= 0.949999988079071D;
                            p_70108_1_.field_70179_y *= 0.949999988079071D;
                        }
                        else if (((EntityMinecart)p_70108_1_).field_70505_a != 2 && this.field_70505_a == 2)
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

    public int func_70302_i_()
    {
        return 27;
    }

    public ItemStack func_70301_a(int p_70301_1_)
    {
        return this.field_70501_d[p_70301_1_];
    }

    public ItemStack func_70298_a(int p_70298_1_, int p_70298_2_)
    {
        if (this.field_70501_d[p_70298_1_] != null)
        {
            ItemStack itemstack;

            if (this.field_70501_d[p_70298_1_].field_77994_a <= p_70298_2_)
            {
                itemstack = this.field_70501_d[p_70298_1_];
                this.field_70501_d[p_70298_1_] = null;
                return itemstack;
            }
            else
            {
                itemstack = this.field_70501_d[p_70298_1_].func_77979_a(p_70298_2_);

                if (this.field_70501_d[p_70298_1_].field_77994_a == 0)
                {
                    this.field_70501_d[p_70298_1_] = null;
                }

                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }

    public ItemStack func_70304_b(int p_70304_1_)
    {
        if (this.field_70501_d[p_70304_1_] != null)
        {
            ItemStack itemstack = this.field_70501_d[p_70304_1_];
            this.field_70501_d[p_70304_1_] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    public void func_70299_a(int p_70299_1_, ItemStack p_70299_2_)
    {
        this.field_70501_d[p_70299_1_] = p_70299_2_;

        if (p_70299_2_ != null && p_70299_2_.field_77994_a > this.func_70297_j_())
        {
            p_70299_2_.field_77994_a = this.func_70297_j_();
        }
    }

    public String func_70303_b()
    {
        return "container.minecart";
    }

    public int func_70297_j_()
    {
        return maxStack; // CraftBukkit
    }

    public void func_70296_d() {}

    public boolean func_70085_c(EntityPlayer p_70085_1_)
    {
        if (this.field_70505_a == 0)
        {
            if (this.field_70153_n != null && this.field_70153_n instanceof EntityPlayer && this.field_70153_n != p_70085_1_)
            {
                return true;
            }

            if (!this.field_70170_p.field_72995_K)
            {
                p_70085_1_.func_70078_a(this);
            }
        }
        else if (this.field_70505_a == 1)
        {
            if (!this.field_70170_p.field_72995_K)
            {
                p_70085_1_.func_71007_a(this);
            }
        }
        else if (this.field_70505_a == 2)
        {
            ItemStack itemstack = p_70085_1_.field_71071_by.func_70448_g();

            if (itemstack != null && itemstack.field_77993_c == Item.field_77705_m.field_77779_bT)
            {
                if (--itemstack.field_77994_a == 0)
                {
                    p_70085_1_.field_71071_by.func_70299_a(p_70085_1_.field_71071_by.field_70461_c, (ItemStack)null);
                }

                this.field_70502_e += 3600;
            }

            this.field_70503_b = this.field_70165_t - p_70085_1_.field_70165_t;
            this.field_70504_c = this.field_70161_v - p_70085_1_.field_70161_v;
        }

        return true;
    }

    public boolean func_70300_a(EntityPlayer p_70300_1_)
    {
        return this.field_70128_L ? false : p_70300_1_.func_70068_e(this) <= 64.0D;
    }

    protected boolean func_70490_h()
    {
        return (this.field_70180_af.func_75683_a(16) & 1) != 0;
    }

    protected void func_70498_d(boolean p_70498_1_)
    {
        if (p_70498_1_)
        {
            this.field_70180_af.func_75692_b(16, Byte.valueOf((byte)(this.field_70180_af.func_75683_a(16) | 1)));
        }
        else
        {
            this.field_70180_af.func_75692_b(16, Byte.valueOf((byte)(this.field_70180_af.func_75683_a(16) & -2)));
        }
    }

    public void func_70295_k_() {}

    public void func_70305_f() {}

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
