package net.minecraft.entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.StepSound;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityJumpHelper;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.ai.EntitySenses;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet18Animation;
import net.minecraft.network.packet.Packet22Collect;
import net.minecraft.network.packet.Packet5PlayerInventory;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

// CraftBukkit start
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
// CraftBukkit end

public abstract class EntityLiving extends Entity
{
    private static final float[] field_82177_b = new float[] {0.0F, 0.0F, 0.05F, 0.1F};
    private static final float[] field_82178_c = new float[] {0.0F, 0.0F, 0.05F, 0.2F};
    private static final float[] field_82176_d = new float[] {0.0F, 0.0F, 0.05F, 0.02F};
    public static final float[] field_82181_as = new float[] {0.0F, 0.1F, 0.15F, 0.45F};
    public int field_70771_an = 20;
    public float field_70769_ao;
    public float field_70770_ap;
    public float field_70761_aq = 0.0F;
    public float field_70760_ar = 0.0F;
    public float field_70759_as = 0.0F;
    public float field_70758_at = 0.0F;
    protected float field_70768_au;
    protected float field_70766_av;
    protected float field_70764_aw;
    protected float field_70763_ax;
    protected boolean field_70753_ay = true;
    protected String field_70750_az = "/mob/char.png";
    protected boolean field_70740_aA = true;
    protected float field_70741_aB = 0.0F;
    protected String field_70742_aC = null;
    protected float field_70743_aD = 1.0F;
    protected int field_70744_aE = 0;
    protected float field_70745_aF = 0.0F;
    public float field_70746_aG = 0.1F;
    public float field_70747_aH = 0.02F;
    public float field_70732_aI;
    public float field_70733_aJ;
    protected int field_70734_aK = this.func_70667_aM();
    public int field_70735_aL;
    protected int field_70736_aM;
    public int field_70757_a;
    public int field_70737_aN;
    public int field_70738_aO;
    public float field_70739_aP = 0.0F;
    public int field_70725_aQ = 0;
    public int field_70724_aR = 0;
    public float field_70727_aS;
    public float field_70726_aT;
    protected boolean field_70729_aU = false;
    protected int field_70728_aV;
    public int field_70731_aW = -1;
    public float field_70730_aX = (float)(Math.random() * 0.8999999761581421D + 0.10000000149011612D);
    public float field_70722_aY;
    public float field_70721_aZ;
    public float field_70754_ba;
    public EntityPlayer field_70717_bb = null; // CraftBukkit - protected -> public
    protected int field_70718_bc = 0;
    public EntityLiving field_70755_b = null; // CraftBukkit - private -> public
    private int field_70756_c = 0;
    private EntityLiving field_70751_d = null;
    public int field_70720_be = 0;
    public HashMap field_70713_bf = new HashMap(); // CraftBukkit - protected -> public
    public boolean field_70752_e = true; // CraftBukkit - private -> public
    private int field_70748_f;
    private EntityLookHelper field_70749_g;
    private EntityMoveHelper field_70765_h;
    private EntityJumpHelper field_70767_i;
    private EntityBodyHelper field_70762_j;
    private PathNavigate field_70699_by;
    protected final EntityAITasks field_70714_bg;
    protected final EntityAITasks field_70715_bh;
    private EntityLiving field_70696_bz;
    private EntitySenses field_70723_bA;
    private float field_70774_bB;
    private ChunkCoordinates field_70775_bC = new ChunkCoordinates(0, 0, 0);
    private float field_70772_bD = -1.0F;
    private ItemStack[] field_82182_bS = new ItemStack[5];
    public float[] field_82174_bp = new float[5]; // CraftBukkit - protected -> public
    private ItemStack[] field_82180_bT = new ItemStack[5];
    public boolean field_82175_bq = false;
    public int field_82173_br = 0;
    public boolean field_82172_bs = false; // CraftBukkit - protected -> public
    public boolean field_82179_bU = !this.func_70692_ba(); // CraftBukkit - private -> public, change value
    protected int field_70716_bi;
    protected double field_70709_bj;
    protected double field_70710_bk;
    protected double field_70711_bl;
    protected double field_70712_bm;
    protected double field_70705_bn;
    float field_70706_bo = 0.0F;
    public int field_70707_bp = 0; // CraftBukkit - protected -> public
    protected int field_70708_bq = 0;
    protected float field_70702_br;
    protected float field_70701_bs;
    protected float field_70704_bt;
    protected boolean field_70703_bu = false;
    protected float field_70698_bv = 0.0F;
    protected float field_70697_bw = 0.7F;
    private int field_70773_bE = 0;
    private Entity field_70776_bF;
    protected int field_70700_bx = 0;
    // CraftBukkit start
    public int expToDrop = 0;
    public int maxAirTicks = 300;
    public int maxHealth = this.func_70667_aM();
    // CraftBukkit end

    public EntityLiving(World p_i3443_1_)
    {
        super(p_i3443_1_);
        this.field_70156_m = true;
        this.field_70714_bg = new EntityAITasks(p_i3443_1_ != null && p_i3443_1_.field_72984_F != null ? p_i3443_1_.field_72984_F : null);
        this.field_70715_bh = new EntityAITasks(p_i3443_1_ != null && p_i3443_1_.field_72984_F != null ? p_i3443_1_.field_72984_F : null);
        this.field_70749_g = new EntityLookHelper(this);
        this.field_70765_h = new EntityMoveHelper(this);
        this.field_70767_i = new EntityJumpHelper(this);
        this.field_70762_j = new EntityBodyHelper(this);
        this.field_70699_by = new PathNavigate(this, p_i3443_1_, 16.0F);
        this.field_70723_bA = new EntitySenses(this);
        this.field_70770_ap = (float)(Math.random() + 1.0D) * 0.01F;
        this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
        this.field_70769_ao = (float)Math.random() * 12398.0F;
        this.field_70177_z = (float)(Math.random() * Math.PI * 2.0D);
        this.field_70759_as = this.field_70177_z;

        for (int i = 0; i < this.field_82174_bp.length; ++i)
        {
            this.field_82174_bp[i] = 0.05F;
        }

        this.field_70138_W = 0.5F;
    }

    public EntityLookHelper func_70671_ap()
    {
        return this.field_70749_g;
    }

    public EntityMoveHelper func_70605_aq()
    {
        return this.field_70765_h;
    }

    public EntityJumpHelper func_70683_ar()
    {
        return this.field_70767_i;
    }

    public PathNavigate func_70661_as()
    {
        return this.field_70699_by;
    }

    public EntitySenses func_70635_at()
    {
        return this.field_70723_bA;
    }

    public Random func_70681_au()
    {
        return this.field_70146_Z;
    }

    public EntityLiving func_70643_av()
    {
        return this.field_70755_b;
    }

    public EntityLiving func_70680_aw()
    {
        return this.field_70751_d;
    }

    public void func_70607_j(Entity p_70607_1_)
    {
        if (p_70607_1_ instanceof EntityLiving)
        {
            this.field_70751_d = (EntityLiving)p_70607_1_;
        }
    }

    public int func_70654_ax()
    {
        return this.field_70708_bq;
    }

    public float func_70079_am()
    {
        return this.field_70759_as;
    }

    public float func_70689_ay()
    {
        return this.field_70774_bB;
    }

    public void func_70659_e(float p_70659_1_)
    {
        this.field_70774_bB = p_70659_1_;
        this.func_70657_f(p_70659_1_);
    }

    public boolean func_70652_k(Entity p_70652_1_)
    {
        this.func_70607_j(p_70652_1_);
        return false;
    }

    public EntityLiving func_70638_az()
    {
        return this.field_70696_bz;
    }

    public void func_70624_b(EntityLiving p_70624_1_)
    {
        this.field_70696_bz = p_70624_1_;
    }

    public boolean func_70686_a(Class p_70686_1_)
    {
        return EntityCreeper.class != p_70686_1_ && EntityGhast.class != p_70686_1_;
    }

    public void func_70615_aA() {}

    protected void func_70064_a(double p_70064_1_, boolean p_70064_3_)
    {
        if (!this.func_70090_H())
        {
            this.func_70072_I();
        }

        if (p_70064_3_ && this.field_70143_R > 0.0F)
        {
            int i = MathHelper.func_76128_c(this.field_70165_t);
            int j = MathHelper.func_76128_c(this.field_70163_u - 0.20000000298023224D - (double)this.field_70129_M);
            int k = MathHelper.func_76128_c(this.field_70161_v);
            int l = this.field_70170_p.func_72798_a(i, j, k);

            if (l == 0)
            {
                int i1 = this.field_70170_p.func_85175_e(i, j - 1, k);

                if (i1 == 11 || i1 == 32 || i1 == 21)
                {
                    l = this.field_70170_p.func_72798_a(i, j - 1, k);
                }
            }

            if (l > 0)
            {
                Block.field_71973_m[l].func_71866_a(this.field_70170_p, i, j, k, this, this.field_70143_R);
            }
        }

        super.func_70064_a(p_70064_1_, p_70064_3_);
    }

    public boolean func_70611_aB()
    {
        return this.func_70649_d(MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c(this.field_70163_u), MathHelper.func_76128_c(this.field_70161_v));
    }

    public boolean func_70649_d(int p_70649_1_, int p_70649_2_, int p_70649_3_)
    {
        return this.field_70772_bD == -1.0F ? true : this.field_70775_bC.func_71569_e(p_70649_1_, p_70649_2_, p_70649_3_) < this.field_70772_bD * this.field_70772_bD;
    }

    public void func_70598_b(int p_70598_1_, int p_70598_2_, int p_70598_3_, int p_70598_4_)
    {
        this.field_70775_bC.func_71571_b(p_70598_1_, p_70598_2_, p_70598_3_);
        this.field_70772_bD = (float)p_70598_4_;
    }

    public ChunkCoordinates func_70602_aC()
    {
        return this.field_70775_bC;
    }

    public float func_70640_aD()
    {
        return this.field_70772_bD;
    }

    public void func_70677_aE()
    {
        this.field_70772_bD = -1.0F;
    }

    public boolean func_70622_aF()
    {
        return this.field_70772_bD != -1.0F;
    }

    public void func_70604_c(EntityLiving p_70604_1_)
    {
        this.field_70755_b = p_70604_1_;
        this.field_70756_c = this.field_70755_b != null ? 60 : 0;
    }

    protected void func_70088_a()
    {
        this.field_70180_af.func_75682_a(8, Integer.valueOf(this.field_70748_f));
        this.field_70180_af.func_75682_a(9, Byte.valueOf((byte)0));
        this.field_70180_af.func_75682_a(10, Byte.valueOf((byte)0));
    }

    public boolean func_70685_l(Entity p_70685_1_)
    {
        return this.field_70170_p.func_72933_a(this.field_70170_p.func_82732_R().func_72345_a(this.field_70165_t, this.field_70163_u + (double)this.func_70047_e(), this.field_70161_v), this.field_70170_p.func_82732_R().func_72345_a(p_70685_1_.field_70165_t, p_70685_1_.field_70163_u + (double)p_70685_1_.func_70047_e(), p_70685_1_.field_70161_v)) == null;
    }

    public boolean func_70067_L()
    {
        return !this.field_70128_L;
    }

    public boolean func_70104_M()
    {
        return !this.field_70128_L;
    }

    public float func_70047_e()
    {
        return this.field_70131_O * 0.85F;
    }

    public int func_70627_aG()
    {
        return 80;
    }

    public void func_70642_aH()
    {
        String s = this.func_70639_aQ();

        if (s != null)
        {
            this.func_85030_a(s, this.func_70599_aP(), this.func_70647_i());
        }
    }

    public void func_70030_z()
    {
        this.field_70732_aI = this.field_70733_aJ;
        super.func_70030_z();
        this.field_70170_p.field_72984_F.func_76320_a("mobBaseTick");

        if (this.func_70089_S() && this.field_70146_Z.nextInt(1000) < this.field_70757_a++)
        {
            this.field_70757_a = -this.func_70627_aG();
            this.func_70642_aH();
        }

        // CraftBukkit start
        if (this.func_70089_S() && this.func_70094_T() && !(this instanceof EntityDragon))   // EnderDragon's don't suffocate.
        {
            EntityDamageEvent event = new EntityDamageEvent(this.getBukkitEntity(), EntityDamageEvent.DamageCause.SUFFOCATION, 1);
            this.field_70170_p.getServer().getPluginManager().callEvent(event);

            if (!event.isCancelled())
            {
                event.getEntity().setLastDamageCause(event);
                this.func_70097_a(DamageSource.field_76368_d, event.getDamage());
            }

            // CraftBukkit end
        }

        if (this.func_70045_F() || this.field_70170_p.field_72995_K)
        {
            this.func_70066_B();
        }

        boolean flag = this instanceof EntityPlayer && ((EntityPlayer)this).field_71075_bZ.field_75102_a;

        if (this.func_70089_S() && this.func_70055_a(Material.field_76244_g) && !this.func_70648_aU() && !this.field_70713_bf.containsKey(Integer.valueOf(Potion.field_76427_o.field_76415_H)) && !flag)
        {
            this.func_70050_g(this.func_70682_h(this.func_70086_ai()));

            if (this.func_70086_ai() == -20)
            {
                this.func_70050_g(0);

                for (int i = 0; i < 8; ++i)
                {
                    float f = this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat();
                    float f1 = this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat();
                    float f2 = this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat();
                    this.field_70170_p.func_72869_a("bubble", this.field_70165_t + (double)f, this.field_70163_u + (double)f1, this.field_70161_v + (double)f2, this.field_70159_w, this.field_70181_x, this.field_70179_y);
                }

                // CraftBukkit start
                EntityDamageEvent event = new EntityDamageEvent(this.getBukkitEntity(), EntityDamageEvent.DamageCause.DROWNING, 2);
                this.field_70170_p.getServer().getPluginManager().callEvent(event);

                if (!event.isCancelled() && event.getDamage() != 0)
                {
                    event.getEntity().setLastDamageCause(event);
                    this.func_70097_a(DamageSource.field_76369_e, event.getDamage());
                }

                // CraftBukkit end
            }

            this.func_70066_B();
        }
        else
        {
            // CraftBukkit start - only set if needed to work around a datawatcher inefficiency
            if (this.func_70086_ai() != 300)
            {
                this.func_70050_g(maxAirTicks);
            }

            // CraftBukkit end
        }

        this.field_70727_aS = this.field_70726_aT;

        if (this.field_70724_aR > 0)
        {
            --this.field_70724_aR;
        }

        if (this.field_70737_aN > 0)
        {
            --this.field_70737_aN;
        }

        if (this.field_70172_ad > 0)
        {
            --this.field_70172_ad;
        }

        if (this.field_70734_aK <= 0)
        {
            this.func_70609_aI();
        }

        if (this.field_70718_bc > 0)
        {
            --this.field_70718_bc;
        }
        else
        {
            this.field_70717_bb = null;
        }

        if (this.field_70751_d != null && !this.field_70751_d.func_70089_S())
        {
            this.field_70751_d = null;
        }

        if (this.field_70755_b != null)
        {
            if (!this.field_70755_b.func_70089_S())
            {
                this.func_70604_c((EntityLiving)null);
            }
            else if (this.field_70756_c > 0)
            {
                --this.field_70756_c;
            }
            else
            {
                this.func_70604_c((EntityLiving)null);
            }
        }

        this.func_70679_bo();
        this.field_70763_ax = this.field_70764_aw;
        this.field_70760_ar = this.field_70761_aq;
        this.field_70758_at = this.field_70759_as;
        this.field_70126_B = this.field_70177_z;
        this.field_70127_C = this.field_70125_A;
        this.field_70170_p.field_72984_F.func_76319_b();
    }

    // CraftBukkit start
    public int getExpReward()
    {
        int exp = this.func_70693_a(this.field_70717_bb);

        if (!this.field_70170_p.field_72995_K && (this.field_70718_bc > 0 || this.func_70684_aJ()) && !this.func_70631_g_())
        {
            return exp;
        }
        else
        {
            return 0;
        }
    }

    public int getScaledHealth()
    {
        if (this.maxHealth != this.func_70667_aM() && this.func_70630_aN() > 0)
        {
            return this.func_70630_aN() * this.func_70667_aM() / this.maxHealth + 1;
        }
        else
        {
            return this.func_70630_aN();
        }
    }
    // CraftBukkit end

    protected void func_70609_aI()
    {
        ++this.field_70725_aQ;

        if (this.field_70725_aQ >= 20 && !this.field_70128_L)   // CraftBukkit - (this.deathTicks == 20) -> (this.deathTicks >= 20 && !this.dead)
        {
            int i;
            // CraftBukkit start - update getExpReward() above if the removed if() changes!
            i = this.expToDrop;

            while (i > 0)
            {
                int j = EntityXPOrb.func_70527_a(i);
                i -= j;
                this.field_70170_p.func_72838_d(new EntityXPOrb(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, j));
            }

            this.expToDrop = 0;
            // CraftBukkit end
            this.func_70106_y();

            for (i = 0; i < 20; ++i)
            {
                double d0 = this.field_70146_Z.nextGaussian() * 0.02D;
                double d1 = this.field_70146_Z.nextGaussian() * 0.02D;
                double d2 = this.field_70146_Z.nextGaussian() * 0.02D;
                this.field_70170_p.func_72869_a("explode", this.field_70165_t + (double)(this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - (double)this.field_70130_N, this.field_70163_u + (double)(this.field_70146_Z.nextFloat() * this.field_70131_O), this.field_70161_v + (double)(this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - (double)this.field_70130_N, d0, d1, d2);
            }
        }
    }

    protected int func_70682_h(int p_70682_1_)
    {
        int j = EnchantmentHelper.func_77501_a(this);
        return j > 0 && this.field_70146_Z.nextInt(j + 1) > 0 ? p_70682_1_ : p_70682_1_ - 1;
    }

    protected int func_70693_a(EntityPlayer p_70693_1_)
    {
        if (this.field_70728_aV > 0)
        {
            int i = this.field_70728_aV;
            ItemStack[] aitemstack = this.func_70035_c();

            for (int j = 0; j < aitemstack.length; ++j)
            {
                if (aitemstack[j] != null && this.field_82174_bp[j] <= 1.0F)
                {
                    i += 1 + this.field_70146_Z.nextInt(3);
                }
            }

            return i;
        }
        else
        {
            return this.field_70728_aV;
        }
    }

    protected boolean func_70684_aJ()
    {
        return false;
    }

    public void func_70656_aK()
    {
        for (int i = 0; i < 20; ++i)
        {
            double d0 = this.field_70146_Z.nextGaussian() * 0.02D;
            double d1 = this.field_70146_Z.nextGaussian() * 0.02D;
            double d2 = this.field_70146_Z.nextGaussian() * 0.02D;
            double d3 = 10.0D;
            this.field_70170_p.func_72869_a("explode", this.field_70165_t + (double)(this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - (double)this.field_70130_N - d0 * d3, this.field_70163_u + (double)(this.field_70146_Z.nextFloat() * this.field_70131_O) - d1 * d3, this.field_70161_v + (double)(this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - (double)this.field_70130_N - d2 * d3, d0, d1, d2);
        }
    }

    public void func_70098_U()
    {
        super.func_70098_U();
        this.field_70768_au = this.field_70766_av;
        this.field_70766_av = 0.0F;
        this.field_70143_R = 0.0F;
    }

    public void func_70071_h_()
    {
        super.func_70071_h_();

        if (!this.field_70170_p.field_72995_K)
        {
            int i;

            for (i = 0; i < 5; ++i)
            {
                ItemStack itemstack = this.func_71124_b(i);

                if (!ItemStack.func_77989_b(itemstack, this.field_82180_bT[i]))
                {
                    ((WorldServer) this.field_70170_p).func_73039_n().func_72784_a((Entity) this, (Packet)(new Packet5PlayerInventory(this.field_70157_k, i, itemstack)));
                    this.field_82180_bT[i] = itemstack == null ? null : itemstack.func_77946_l();
                }
            }

            i = this.func_85035_bI();

            if (i > 0)
            {
                if (this.field_70720_be <= 0)
                {
                    this.field_70720_be = 20 * (30 - i);
                }

                --this.field_70720_be;

                if (this.field_70720_be <= 0)
                {
                    this.func_85034_r(i - 1);
                }
            }
        }

        this.func_70636_d();
        double d0 = this.field_70165_t - this.field_70169_q;
        double d1 = this.field_70161_v - this.field_70166_s;
        float f = (float)(d0 * d0 + d1 * d1);
        float f1 = this.field_70761_aq;
        float f2 = 0.0F;
        this.field_70768_au = this.field_70766_av;
        float f3 = 0.0F;

        if (f > 0.0025000002F)
        {
            f3 = 1.0F;
            f2 = (float)Math.sqrt((double)f) * 3.0F;
            // CraftBukkit - Math -> TrigMath
            f1 = (float) org.bukkit.craftbukkit.TrigMath.atan2(d1, d0) * 180.0F / (float)Math.PI - 90.0F;
        }

        if (this.field_70733_aJ > 0.0F)
        {
            f1 = this.field_70177_z;
        }

        if (!this.field_70122_E)
        {
            f3 = 0.0F;
        }

        this.field_70766_av += (f3 - this.field_70766_av) * 0.3F;
        this.field_70170_p.field_72984_F.func_76320_a("headTurn");

        if (this.func_70650_aV())
        {
            this.field_70762_j.func_75664_a();
        }
        else
        {
            float f4 = MathHelper.func_76142_g(f1 - this.field_70761_aq);
            this.field_70761_aq += f4 * 0.3F;
            float f5 = MathHelper.func_76142_g(this.field_70177_z - this.field_70761_aq);
            boolean flag = f5 < -90.0F || f5 >= 90.0F;

            if (f5 < -75.0F)
            {
                f5 = -75.0F;
            }

            if (f5 >= 75.0F)
            {
                f5 = 75.0F;
            }

            this.field_70761_aq = this.field_70177_z - f5;

            if (f5 * f5 > 2500.0F)
            {
                this.field_70761_aq += f5 * 0.2F;
            }

            if (flag)
            {
                f2 *= -1.0F;
            }
        }

        this.field_70170_p.field_72984_F.func_76319_b();
        this.field_70170_p.field_72984_F.func_76320_a("rangeChecks");

        while (this.field_70177_z - this.field_70126_B < -180.0F)
        {
            this.field_70126_B -= 360.0F;
        }

        while (this.field_70177_z - this.field_70126_B >= 180.0F)
        {
            this.field_70126_B += 360.0F;
        }

        while (this.field_70761_aq - this.field_70760_ar < -180.0F)
        {
            this.field_70760_ar -= 360.0F;
        }

        while (this.field_70761_aq - this.field_70760_ar >= 180.0F)
        {
            this.field_70760_ar += 360.0F;
        }

        while (this.field_70125_A - this.field_70127_C < -180.0F)
        {
            this.field_70127_C -= 360.0F;
        }

        while (this.field_70125_A - this.field_70127_C >= 180.0F)
        {
            this.field_70127_C += 360.0F;
        }

        while (this.field_70759_as - this.field_70758_at < -180.0F)
        {
            this.field_70758_at -= 360.0F;
        }

        while (this.field_70759_as - this.field_70758_at >= 180.0F)
        {
            this.field_70758_at += 360.0F;
        }

        this.field_70170_p.field_72984_F.func_76319_b();
        this.field_70764_aw += f2;
    }

    // CraftBukkit start - delegate so we can handle providing a reason for health being regained
    public void func_70691_i(int p_70691_1_)
    {
        heal(p_70691_1_, EntityRegainHealthEvent.RegainReason.CUSTOM);
    }

    public void heal(int i, EntityRegainHealthEvent.RegainReason regainReason)
    {
        if (this.field_70734_aK > 0)
        {
            EntityRegainHealthEvent event = new EntityRegainHealthEvent(this.getBukkitEntity(), i, regainReason);
            this.field_70170_p.getServer().getPluginManager().callEvent(event);

            if (!event.isCancelled())
            {
                this.field_70734_aK += event.getAmount();
            }

            // this.getMaxHealth() -> this.maxHealth
            if (this.field_70734_aK > this.maxHealth)
            {
                this.field_70734_aK = this.maxHealth;
                // CraftBukkit end
            }

            this.field_70172_ad = this.field_70771_an / 2;
        }
    }

    public abstract int func_70667_aM();

    public int func_70630_aN()
    {
        return this.field_70734_aK;
    }

    public void func_70606_j(int p_70606_1_)
    {
        this.field_70734_aK = p_70606_1_;

        if (p_70606_1_ > this.func_70667_aM())
        {
            p_70606_1_ = this.func_70667_aM();
        }
    }

    public boolean func_70097_a(DamageSource p_70097_1_, int p_70097_2_)
    {
        if (this.func_85032_ar())
        {
            return false;
        }
        else if (this.field_70170_p.field_72995_K)
        {
            return false;
        }
        else
        {
            this.field_70708_bq = 0;

            if (this.field_70734_aK <= 0)
            {
                return false;
            }
            else if (p_70097_1_.func_76347_k() && this.func_70644_a(Potion.field_76426_n))
            {
                return false;
            }
            else
            {
                if ((p_70097_1_ == DamageSource.field_82728_o || p_70097_1_ == DamageSource.field_82729_p) && this.func_71124_b(4) != null)
                {
                    this.func_71124_b(4).func_77972_a(p_70097_2_ * 4 + this.field_70146_Z.nextInt(p_70097_2_ * 2), this);
                    p_70097_2_ = (int)((float)p_70097_2_ * 0.75F);
                }

                this.field_70721_aZ = 1.5F;
                boolean flag = true;

                // CraftBukkit start
                if (p_70097_1_ instanceof EntityDamageSource)
                {
                    EntityDamageEvent event = CraftEventFactory.handleEntityDamageEvent(this, p_70097_1_, p_70097_2_);

                    if (event.isCancelled())
                    {
                        return false;
                    }

                    p_70097_2_ = event.getDamage();
                }

                // CraftBukkit end

                if ((float)this.field_70172_ad > (float)this.field_70771_an / 2.0F)
                {
                    if (p_70097_2_ <= this.field_70707_bp)
                    {
                        return false;
                    }

                    this.func_70665_d(p_70097_1_, p_70097_2_ - this.field_70707_bp);
                    this.field_70707_bp = p_70097_2_;
                    flag = false;
                }
                else
                {
                    this.field_70707_bp = p_70097_2_;
                    this.field_70735_aL = this.field_70734_aK;
                    this.field_70172_ad = this.field_70771_an;
                    this.func_70665_d(p_70097_1_, p_70097_2_);
                    this.field_70737_aN = this.field_70738_aO = 10;
                }

                this.field_70739_aP = 0.0F;
                Entity entity = p_70097_1_.func_76346_g();

                if (entity != null)
                {
                    if (entity instanceof EntityLiving)
                    {
                        this.func_70604_c((EntityLiving)entity);
                    }

                    if (entity instanceof EntityPlayer)
                    {
                        this.field_70718_bc = 60;
                        this.field_70717_bb = (EntityPlayer)entity;
                    }
                    else if (entity instanceof EntityWolf)
                    {
                        EntityWolf entitywolf = (EntityWolf)entity;

                        if (entitywolf.func_70909_n())
                        {
                            this.field_70718_bc = 60;
                            this.field_70717_bb = null;
                        }
                    }
                }

                if (flag)
                {
                    this.field_70170_p.func_72960_a(this, (byte)2);

                    if (p_70097_1_ != DamageSource.field_76369_e && p_70097_1_ != DamageSource.field_76375_l)
                    {
                        this.func_70018_K();
                    }

                    if (entity != null)
                    {
                        double d0 = entity.field_70165_t - this.field_70165_t;
                        double d1;

                        for (d1 = entity.field_70161_v - this.field_70161_v; d0 * d0 + d1 * d1 < 1.0E-4D; d1 = (Math.random() - Math.random()) * 0.01D)
                        {
                            d0 = (Math.random() - Math.random()) * 0.01D;
                        }

                        this.field_70739_aP = (float)(Math.atan2(d1, d0) * 180.0D / Math.PI) - this.field_70177_z;
                        this.func_70653_a(entity, p_70097_2_, d0, d1);
                    }
                    else
                    {
                        this.field_70739_aP = (float)((int)(Math.random() * 2.0D) * 180);
                    }
                }

                if (this.field_70734_aK <= 0)
                {
                    if (flag)
                    {
                        this.func_85030_a(this.func_70673_aS(), this.func_70599_aP(), this.func_70647_i());
                    }

                    this.func_70645_a(p_70097_1_);
                }
                else if (flag)
                {
                    this.func_85030_a(this.func_70621_aR(), this.func_70599_aP(), this.func_70647_i());
                }

                return true;
            }
        }
    }

    protected float func_70647_i()
    {
        return this.func_70631_g_() ? (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.5F : (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F;
    }

    public int func_70658_aO()
    {
        int i = 0;
        ItemStack[] aitemstack = this.func_70035_c();
        int j = aitemstack.length;

        for (int k = 0; k < j; ++k)
        {
            ItemStack itemstack = aitemstack[k];

            if (itemstack != null && itemstack.func_77973_b() instanceof ItemArmor)
            {
                int l = ((ItemArmor)itemstack.func_77973_b()).field_77879_b;
                i += l;
            }
        }

        return i;
    }

    protected void func_70675_k(int p_70675_1_) {}

    protected int func_70655_b(DamageSource p_70655_1_, int p_70655_2_)
    {
        if (!p_70655_1_.func_76363_c())
        {
            int j = 25 - this.func_70658_aO();
            int k = p_70655_2_ * j + this.field_70736_aM;
            this.func_70675_k(p_70655_2_);
            p_70655_2_ = k / 25;
            this.field_70736_aM = k % 25;
        }

        return p_70655_2_;
    }

    protected int func_70672_c(DamageSource p_70672_1_, int p_70672_2_)
    {
        if (this.func_70644_a(Potion.field_76429_m))
        {
            int j = (this.func_70660_b(Potion.field_76429_m).func_76458_c() + 1) * 5;
            int k = 25 - j;
            int l = p_70672_2_ * k + this.field_70736_aM;
            p_70672_2_ = l / 25;
            this.field_70736_aM = l % 25;
        }

        return p_70672_2_;
    }

    protected void func_70665_d(DamageSource p_70665_1_, int p_70665_2_)
    {
        if (!this.func_85032_ar())
        {
            p_70665_2_ = this.func_70655_b(p_70665_1_, p_70665_2_);
            p_70665_2_ = this.func_70672_c(p_70665_1_, p_70665_2_);
            this.field_70734_aK -= p_70665_2_;
        }
    }

    protected float func_70599_aP()
    {
        return 1.0F;
    }

    protected String func_70639_aQ()
    {
        return null;
    }

    protected String func_70621_aR()
    {
        return "damage.hit";
    }

    protected String func_70673_aS()
    {
        return "damage.hit";
    }

    public void func_70653_a(Entity p_70653_1_, int p_70653_2_, double p_70653_3_, double p_70653_5_)
    {
        this.field_70160_al = true;
        float f = MathHelper.func_76133_a(p_70653_3_ * p_70653_3_ + p_70653_5_ * p_70653_5_);
        float f1 = 0.4F;
        this.field_70159_w /= 2.0D;
        this.field_70181_x /= 2.0D;
        this.field_70179_y /= 2.0D;
        this.field_70159_w -= p_70653_3_ / (double)f * (double)f1;
        this.field_70181_x += (double)f1;
        this.field_70179_y -= p_70653_5_ / (double)f * (double)f1;

        if (this.field_70181_x > 0.4000000059604645D)
        {
            this.field_70181_x = 0.4000000059604645D;
        }
    }

    public void func_70645_a(DamageSource p_70645_1_)
    {
        Entity entity = p_70645_1_.func_76346_g();

        if (this.field_70744_aE >= 0 && entity != null)
        {
            entity.func_70084_c(this, this.field_70744_aE);
        }

        if (entity != null)
        {
            entity.func_70074_a(this);
        }

        this.field_70729_aU = true;

        if (!this.field_70170_p.field_72995_K)
        {
            int i = 0;

            if (entity instanceof EntityPlayer)
            {
                i = EnchantmentHelper.func_77519_f((EntityLiving)entity);
            }

            if (!this.func_70631_g_() && this.field_70170_p.func_82736_K().func_82766_b("doMobLoot"))
            {
                this.func_70628_a(this.field_70718_bc > 0, i);
                this.func_82160_b(this.field_70718_bc > 0, i);

                if (false && this.field_70718_bc > 0)   // CraftBukkit - move rare item drop call to dropDeathLoot
                {
                    int j = this.field_70146_Z.nextInt(200) - i;

                    if (j < 5)
                    {
                        this.func_70600_l(j <= 0 ? 1 : 0);
                    }
                }
            }
            else     // CraftBukkit
            {
                CraftEventFactory.callEntityDeathEvent(this); // CraftBukkit
            }
        }

        this.field_70170_p.func_72960_a(this, (byte)3);
    }

    // CraftBukkit start - change return type to ItemStack
    protected ItemStack func_70600_l(int i)
    {
        return null;
    }
    // CraftBukkit end

    protected void func_70628_a(boolean p_70628_1_, int p_70628_2_)
    {
        // CraftBukkit start - whole method
        List<org.bukkit.inventory.ItemStack> loot = new java.util.ArrayList<org.bukkit.inventory.ItemStack>();
        int j = this.func_70633_aT();

        if (j > 0)
        {
            int k = this.field_70146_Z.nextInt(3);

            if (p_70628_2_ > 0)
            {
                k += this.field_70146_Z.nextInt(p_70628_2_ + 1);
            }

            if (k > 0)
            {
                loot.add(new org.bukkit.inventory.ItemStack(j, k));
            }
        }

        // Determine rare item drops and add them to the loot
        if (this.field_70718_bc > 0)
        {
            int k = this.field_70146_Z.nextInt(200) - p_70628_2_;

            if (k < 5)
            {
                ItemStack itemstack = this.func_70600_l(k <= 0 ? 1 : 0);

                if (itemstack != null)
                {
                    loot.add(org.bukkit.craftbukkit.inventory.CraftItemStack.asCraftMirror(itemstack));
                }
            }
        }

        CraftEventFactory.callEntityDeathEvent(this, loot); // raise event even for those times when the entity does not drop loot
        // CraftBukkit end
    }

    protected int func_70633_aT()
    {
        return 0;
    }

    protected void func_70069_a(float p_70069_1_)
    {
        super.func_70069_a(p_70069_1_);
        int i = MathHelper.func_76123_f(p_70069_1_ - 3.0F);

        if (i > 0)
        {
            // CraftBukkit start
            EntityDamageEvent event = new EntityDamageEvent(this.getBukkitEntity(), EntityDamageEvent.DamageCause.FALL, i);
            this.field_70170_p.getServer().getPluginManager().callEvent(event);

            if (!event.isCancelled() && event.getDamage() != 0)
            {
                i = event.getDamage();

                if (i > 4)
                {
                    this.func_85030_a("damage.fallbig", 1.0F, 1.0F);
                }
                else
                {
                    this.func_85030_a("damage.fallsmall", 1.0F, 1.0F);
                }

                this.getBukkitEntity().setLastDamageCause(event);
                this.func_70097_a(DamageSource.field_76379_h, i);
            }

            // CraftBukkit end
            int j = this.field_70170_p.func_72798_a(MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c(this.field_70163_u - 0.20000000298023224D - (double)this.field_70129_M), MathHelper.func_76128_c(this.field_70161_v));

            if (j > 0)
            {
                StepSound stepsound = Block.field_71973_m[j].field_72020_cn;
                this.func_85030_a(stepsound.func_72675_d(), stepsound.func_72677_b() * 0.5F, stepsound.func_72678_c() * 0.75F);
            }
        }
    }

    public void func_70612_e(float p_70612_1_, float p_70612_2_)
    {
        double d0;

        if (this.func_70090_H() && (!(this instanceof EntityPlayer) || !((EntityPlayer)this).field_71075_bZ.field_75100_b))
        {
            d0 = this.field_70163_u;
            this.func_70060_a(p_70612_1_, p_70612_2_, this.func_70650_aV() ? 0.04F : 0.02F);
            this.func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
            this.field_70159_w *= 0.800000011920929D;
            this.field_70181_x *= 0.800000011920929D;
            this.field_70179_y *= 0.800000011920929D;
            this.field_70181_x -= 0.02D;

            if (this.field_70123_F && this.func_70038_c(this.field_70159_w, this.field_70181_x + 0.6000000238418579D - this.field_70163_u + d0, this.field_70179_y))
            {
                this.field_70181_x = 0.30000001192092896D;
            }
        }
        else if (this.func_70058_J() && (!(this instanceof EntityPlayer) || !((EntityPlayer)this).field_71075_bZ.field_75100_b))
        {
            d0 = this.field_70163_u;
            this.func_70060_a(p_70612_1_, p_70612_2_, 0.02F);
            this.func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
            this.field_70159_w *= 0.5D;
            this.field_70181_x *= 0.5D;
            this.field_70179_y *= 0.5D;
            this.field_70181_x -= 0.02D;

            if (this.field_70123_F && this.func_70038_c(this.field_70159_w, this.field_70181_x + 0.6000000238418579D - this.field_70163_u + d0, this.field_70179_y))
            {
                this.field_70181_x = 0.30000001192092896D;
            }
        }
        else
        {
            float f2 = 0.91F;

            if (this.field_70122_E)
            {
                f2 = 0.54600006F;
                int i = this.field_70170_p.func_72798_a(MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c(this.field_70121_D.field_72338_b) - 1, MathHelper.func_76128_c(this.field_70161_v));

                if (i > 0)
                {
                    f2 = Block.field_71973_m[i].field_72016_cq * 0.91F;
                }
            }

            float f3 = 0.16277136F / (f2 * f2 * f2);
            float f4;

            if (this.field_70122_E)
            {
                if (this.func_70650_aV())
                {
                    f4 = this.func_70689_ay();
                }
                else
                {
                    f4 = this.field_70746_aG;
                }

                f4 *= f3;
            }
            else
            {
                f4 = this.field_70747_aH;
            }

            this.func_70060_a(p_70612_1_, p_70612_2_, f4);
            f2 = 0.91F;

            if (this.field_70122_E)
            {
                f2 = 0.54600006F;
                int j = this.field_70170_p.func_72798_a(MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c(this.field_70121_D.field_72338_b) - 1, MathHelper.func_76128_c(this.field_70161_v));

                if (j > 0)
                {
                    f2 = Block.field_71973_m[j].field_72016_cq * 0.91F;
                }
            }

            if (this.func_70617_f_())
            {
                float f5 = 0.15F;

                if (this.field_70159_w < (double)(-f5))
                {
                    this.field_70159_w = (double)(-f5);
                }

                if (this.field_70159_w > (double)f5)
                {
                    this.field_70159_w = (double)f5;
                }

                if (this.field_70179_y < (double)(-f5))
                {
                    this.field_70179_y = (double)(-f5);
                }

                if (this.field_70179_y > (double)f5)
                {
                    this.field_70179_y = (double)f5;
                }

                this.field_70143_R = 0.0F;

                if (this.field_70181_x < -0.15D)
                {
                    this.field_70181_x = -0.15D;
                }

                boolean flag = this.func_70093_af() && this instanceof EntityPlayer;

                if (flag && this.field_70181_x < 0.0D)
                {
                    this.field_70181_x = 0.0D;
                }
            }

            this.func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);

            if (this.field_70123_F && this.func_70617_f_())
            {
                this.field_70181_x = 0.2D;
            }

            if (this.field_70170_p.field_72995_K && (!this.field_70170_p.func_72899_e((int)this.field_70165_t, 0, (int)this.field_70161_v) || !this.field_70170_p.func_72938_d((int)this.field_70165_t, (int)this.field_70161_v).field_76636_d))
            {
                if (this.field_70163_u > 0.0D)
                {
                    this.field_70181_x = -0.1D;
                }
                else
                {
                    this.field_70181_x = 0.0D;
                }
            }
            else
            {
                this.field_70181_x -= 0.08D;
            }

            this.field_70181_x *= 0.9800000190734863D;
            this.field_70159_w *= (double)f2;
            this.field_70179_y *= (double)f2;
        }

        this.field_70722_aY = this.field_70721_aZ;
        d0 = this.field_70165_t - this.field_70169_q;
        double d1 = this.field_70161_v - this.field_70166_s;
        float f6 = MathHelper.func_76133_a(d0 * d0 + d1 * d1) * 4.0F;

        if (f6 > 1.0F)
        {
            f6 = 1.0F;
        }

        this.field_70721_aZ += (f6 - this.field_70721_aZ) * 0.4F;
        this.field_70754_ba += this.field_70721_aZ;
    }

    public boolean func_70617_f_()
    {
        int i = MathHelper.func_76128_c(this.field_70165_t);
        int j = MathHelper.func_76128_c(this.field_70121_D.field_72338_b);
        int k = MathHelper.func_76128_c(this.field_70161_v);
        int l = this.field_70170_p.func_72798_a(i, j, k);
        return l == Block.field_72055_aF.field_71990_ca || l == Block.field_71998_bu.field_71990_ca;
    }

    public void func_70014_b(NBTTagCompound p_70014_1_)
    {
        if (this.field_70734_aK < -32768)
        {
            this.field_70734_aK = -32768;
        }

        p_70014_1_.func_74777_a("Health", (short)this.field_70734_aK);
        p_70014_1_.func_74777_a("HurtTime", (short)this.field_70737_aN);
        p_70014_1_.func_74777_a("DeathTime", (short)this.field_70725_aQ);
        p_70014_1_.func_74777_a("AttackTime", (short)this.field_70724_aR);
        p_70014_1_.func_74757_a("CanPickUpLoot", this.field_82172_bs);
        p_70014_1_.func_74757_a("PersistenceRequired", this.field_82179_bU);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.field_82182_bS.length; ++i)
        {
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();

            if (this.field_82182_bS[i] != null)
            {
                this.field_82182_bS[i].func_77955_b(nbttagcompound1);
            }

            nbttaglist.func_74742_a(nbttagcompound1);
        }

        p_70014_1_.func_74782_a("Equipment", nbttaglist);
        NBTTagList nbttaglist1;

        if (!this.field_70713_bf.isEmpty())
        {
            nbttaglist1 = new NBTTagList();
            Iterator iterator = this.field_70713_bf.values().iterator();

            while (iterator.hasNext())
            {
                PotionEffect potioneffect = (PotionEffect)iterator.next();
                nbttaglist1.func_74742_a(potioneffect.func_82719_a(new NBTTagCompound()));
            }

            p_70014_1_.func_74782_a("ActiveEffects", nbttaglist1);
        }

        nbttaglist1 = new NBTTagList();

        for (int j = 0; j < this.field_82174_bp.length; ++j)
        {
            nbttaglist1.func_74742_a(new NBTTagFloat(j + "", this.field_82174_bp[j]));
        }

        p_70014_1_.func_74782_a("DropChances", nbttaglist1);
        p_70014_1_.func_74768_a("Bukkit.MaxHealth", this.maxHealth); // CraftBukkit
    }

    public void func_70037_a(NBTTagCompound p_70037_1_)
    {
        this.field_70734_aK = p_70037_1_.func_74765_d("Health");

        // CraftBukkit start
        if (p_70037_1_.func_74764_b("Bukkit.MaxHealth"))
        {
            this.maxHealth = p_70037_1_.func_74762_e("Bukkit.MaxHealth");
        }

        if (!p_70037_1_.func_74764_b("Health"))
        {
            this.field_70734_aK = this.maxHealth; // this.getMaxHealth() -> this.maxHealth
            // CraftBukkit
        }

        this.field_70737_aN = p_70037_1_.func_74765_d("HurtTime");
        this.field_70725_aQ = p_70037_1_.func_74765_d("DeathTime");
        this.field_70724_aR = p_70037_1_.func_74765_d("AttackTime");
        // CraftBukkit start - if looting or persistence is false only use it if it was set after we started using it
        boolean data = p_70037_1_.func_74767_n("CanPickUpLoot");

        if (isLevelAtLeast(p_70037_1_, 1) || data)
        {
            this.field_82172_bs = data;
        }

        data = p_70037_1_.func_74767_n("PersistenceRequired");

        if (isLevelAtLeast(p_70037_1_, 1) || data)
        {
            this.field_82179_bU = data;
        }

        // CraftBukkit end
        NBTTagList nbttaglist;
        int i;

        if (p_70037_1_.func_74764_b("Equipment"))
        {
            nbttaglist = p_70037_1_.func_74761_m("Equipment");

            for (i = 0; i < this.field_82182_bS.length; ++i)
            {
                this.field_82182_bS[i] = ItemStack.func_77949_a((NBTTagCompound)nbttaglist.func_74743_b(i));
            }
        }

        if (p_70037_1_.func_74764_b("ActiveEffects"))
        {
            nbttaglist = p_70037_1_.func_74761_m("ActiveEffects");

            for (i = 0; i < nbttaglist.func_74745_c(); ++i)
            {
                NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.func_74743_b(i);
                PotionEffect potioneffect = PotionEffect.func_82722_b(nbttagcompound1);
                this.field_70713_bf.put(Integer.valueOf(potioneffect.func_76456_a()), potioneffect);
            }
        }

        if (p_70037_1_.func_74764_b("DropChances"))
        {
            nbttaglist = p_70037_1_.func_74761_m("DropChances");

            for (i = 0; i < nbttaglist.func_74745_c(); ++i)
            {
                this.field_82174_bp[i] = ((NBTTagFloat)nbttaglist.func_74743_b(i)).field_74750_a;
            }
        }
    }

    public boolean func_70089_S()
    {
        return !this.field_70128_L && this.field_70734_aK > 0;
    }

    public boolean func_70648_aU()
    {
        return false;
    }

    public void func_70657_f(float p_70657_1_)
    {
        this.field_70701_bs = p_70657_1_;
    }

    public void func_70637_d(boolean p_70637_1_)
    {
        this.field_70703_bu = p_70637_1_;
    }

    public void func_70636_d()
    {
        if (this.field_70773_bE > 0)
        {
            --this.field_70773_bE;
        }

        if (this.field_70716_bi > 0)
        {
            double d0 = this.field_70165_t + (this.field_70709_bj - this.field_70165_t) / (double)this.field_70716_bi;
            double d1 = this.field_70163_u + (this.field_70710_bk - this.field_70163_u) / (double)this.field_70716_bi;
            double d2 = this.field_70161_v + (this.field_70711_bl - this.field_70161_v) / (double)this.field_70716_bi;
            double d3 = MathHelper.func_76138_g(this.field_70712_bm - (double)this.field_70177_z);
            this.field_70177_z = (float)((double)this.field_70177_z + d3 / (double)this.field_70716_bi);
            this.field_70125_A = (float)((double)this.field_70125_A + (this.field_70705_bn - (double)this.field_70125_A) / (double)this.field_70716_bi);
            --this.field_70716_bi;
            this.func_70107_b(d0, d1, d2);
            this.func_70101_b(this.field_70177_z, this.field_70125_A);
        }
        else if (!this.func_70613_aW())
        {
            this.field_70159_w *= 0.98D;
            this.field_70181_x *= 0.98D;
            this.field_70179_y *= 0.98D;
        }

        if (Math.abs(this.field_70159_w) < 0.005D)
        {
            this.field_70159_w = 0.0D;
        }

        if (Math.abs(this.field_70181_x) < 0.005D)
        {
            this.field_70181_x = 0.0D;
        }

        if (Math.abs(this.field_70179_y) < 0.005D)
        {
            this.field_70179_y = 0.0D;
        }

        this.field_70170_p.field_72984_F.func_76320_a("ai");

        if (this.func_70610_aX())
        {
            this.field_70703_bu = false;
            this.field_70702_br = 0.0F;
            this.field_70701_bs = 0.0F;
            this.field_70704_bt = 0.0F;
        }
        else if (this.func_70613_aW())
        {
            if (this.func_70650_aV())
            {
                this.field_70170_p.field_72984_F.func_76320_a("newAi");
                this.func_70619_bc();
                this.field_70170_p.field_72984_F.func_76319_b();
            }
            else
            {
                this.field_70170_p.field_72984_F.func_76320_a("oldAi");
                this.func_70626_be();
                this.field_70170_p.field_72984_F.func_76319_b();
                this.field_70759_as = this.field_70177_z;
            }
        }

        this.field_70170_p.field_72984_F.func_76319_b();
        this.field_70170_p.field_72984_F.func_76320_a("jump");

        if (this.field_70703_bu)
        {
            if (!this.func_70090_H() && !this.func_70058_J())
            {
                if (this.field_70122_E && this.field_70773_bE == 0)
                {
                    this.func_70664_aZ();
                    this.field_70773_bE = 10;
                }
            }
            else
            {
                this.field_70181_x += 0.03999999910593033D;
            }
        }
        else
        {
            this.field_70773_bE = 0;
        }

        this.field_70170_p.field_72984_F.func_76319_b();
        this.field_70170_p.field_72984_F.func_76320_a("travel");
        this.field_70702_br *= 0.98F;
        this.field_70701_bs *= 0.98F;
        this.field_70704_bt *= 0.9F;
        float f = this.field_70746_aG;
        this.field_70746_aG *= this.func_70616_bs();
        this.func_70612_e(this.field_70702_br, this.field_70701_bs);
        this.field_70746_aG = f;
        this.field_70170_p.field_72984_F.func_76319_b();
        this.field_70170_p.field_72984_F.func_76320_a("push");

        if (!this.field_70170_p.field_72995_K)
        {
            this.func_85033_bc();
        }

        this.field_70170_p.field_72984_F.func_76319_b();
        this.field_70170_p.field_72984_F.func_76320_a("looting");

        // CraftBukkit - Don't run mob pickup code on players
        if (!this.field_70170_p.field_72995_K && !(this instanceof EntityPlayerMP) && this.field_82172_bs && !this.field_70729_aU && this.field_70170_p.func_82736_K().func_82766_b("mobGriefing"))
        {
            List list = this.field_70170_p.func_72872_a(EntityItem.class, this.field_70121_D.func_72314_b(1.0D, 0.0D, 1.0D));
            Iterator iterator = list.iterator();

            while (iterator.hasNext())
            {
                EntityItem entityitem = (EntityItem)iterator.next();

                if (!entityitem.field_70128_L && entityitem.func_92059_d() != null)
                {
                    ItemStack itemstack = entityitem.func_92059_d();
                    int i = func_82159_b(itemstack);

                    if (i > -1)
                    {
                        boolean flag = true;
                        ItemStack itemstack1 = this.func_71124_b(i);

                        if (itemstack1 != null)
                        {
                            if (i == 0)
                            {
                                if (itemstack.func_77973_b() instanceof ItemSword && !(itemstack1.func_77973_b() instanceof ItemSword))
                                {
                                    flag = true;
                                }
                                else if (itemstack.func_77973_b() instanceof ItemSword && itemstack1.func_77973_b() instanceof ItemSword)
                                {
                                    ItemSword itemsword = (ItemSword)itemstack.func_77973_b();
                                    ItemSword itemsword1 = (ItemSword)itemstack1.func_77973_b();

                                    if (itemsword.func_82803_g() == itemsword1.func_82803_g())
                                    {
                                        flag = itemstack.func_77960_j() > itemstack1.func_77960_j() || itemstack.func_77942_o() && !itemstack1.func_77942_o();
                                    }
                                    else
                                    {
                                        flag = itemsword.func_82803_g() > itemsword1.func_82803_g();
                                    }
                                }
                                else
                                {
                                    flag = false;
                                }
                            }
                            else if (itemstack.func_77973_b() instanceof ItemArmor && !(itemstack1.func_77973_b() instanceof ItemArmor))
                            {
                                flag = true;
                            }
                            else if (itemstack.func_77973_b() instanceof ItemArmor && itemstack1.func_77973_b() instanceof ItemArmor)
                            {
                                ItemArmor itemarmor = (ItemArmor)itemstack.func_77973_b();
                                ItemArmor itemarmor1 = (ItemArmor)itemstack1.func_77973_b();

                                if (itemarmor.field_77879_b == itemarmor1.field_77879_b)
                                {
                                    flag = itemstack.func_77960_j() > itemstack1.func_77960_j() || itemstack.func_77942_o() && !itemstack1.func_77942_o();
                                }
                                else
                                {
                                    flag = itemarmor.field_77879_b > itemarmor1.field_77879_b;
                                }
                            }
                            else
                            {
                                flag = false;
                            }
                        }

                        if (flag)
                        {
                            if (itemstack1 != null && this.field_70146_Z.nextFloat() - 0.1F < this.field_82174_bp[i])
                            {
                                this.func_70099_a(itemstack1, 0.0F);
                            }

                            this.func_70062_b(i, itemstack);
                            this.field_82174_bp[i] = 2.0F;
                            this.field_82179_bU = true;
                            this.func_71001_a(entityitem, 1);
                            entityitem.func_70106_y();
                        }
                    }
                }
            }
        }

        this.field_70170_p.field_72984_F.func_76319_b();
    }

    protected void func_85033_bc()
    {
        List list = this.field_70170_p.func_72839_b(this, this.field_70121_D.func_72314_b(0.20000000298023224D, 0.0D, 0.20000000298023224D));

        if (list != null && !list.isEmpty())
        {
            for (int i = 0; i < list.size(); ++i)
            {
                Entity entity = (Entity)list.get(i);

                if (entity.func_70104_M())
                {
                    this.func_82167_n(entity);
                }
            }
        }
    }

    protected void func_82167_n(Entity p_82167_1_)
    {
        p_82167_1_.func_70108_f(this);
    }

    protected boolean func_70650_aV()
    {
        return false;
    }

    protected boolean func_70613_aW()
    {
        return !this.field_70170_p.field_72995_K;
    }

    protected boolean func_70610_aX()
    {
        return this.field_70734_aK <= 0;
    }

    public boolean func_70632_aY()
    {
        return false;
    }

    protected void func_70664_aZ()
    {
        this.field_70181_x = 0.41999998688697815D;

        if (this.func_70644_a(Potion.field_76430_j))
        {
            this.field_70181_x += (double)((float)(this.func_70660_b(Potion.field_76430_j).func_76458_c() + 1) * 0.1F);
        }

        if (this.func_70051_ag())
        {
            float f = this.field_70177_z * 0.017453292F;
            this.field_70159_w -= (double)(MathHelper.func_76126_a(f) * 0.2F);
            this.field_70179_y += (double)(MathHelper.func_76134_b(f) * 0.2F);
        }

        this.field_70160_al = true;
    }

    protected boolean func_70692_ba()
    {
        return true;
    }

    protected void func_70623_bb()
    {
        if (!this.field_82179_bU)
        {
            EntityPlayer entityplayer = this.field_70170_p.func_72890_a(this, -1.0D);

            if (entityplayer != null)
            {
                double d0 = entityplayer.field_70165_t - this.field_70165_t;
                double d1 = entityplayer.field_70163_u - this.field_70163_u;
                double d2 = entityplayer.field_70161_v - this.field_70161_v;
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;

                if (d3 > 16384.0D)   // CraftBukkit - remove this.bj() check
                {
                    this.func_70106_y();
                }

                if (this.field_70708_bq > 600 && this.field_70146_Z.nextInt(800) == 0 && d3 > 1024.0D)   // CraftBukkit - remove this.bj() check
                {
                    this.func_70106_y();
                }
                else if (d3 < 1024.0D)
                {
                    this.field_70708_bq = 0;
                }
            }

            // CraftBukkit start
        }
        else
        {
            this.field_70708_bq = 0;
        }

        // CraftBukkit end
    }

    protected void func_70619_bc()
    {
        ++this.field_70708_bq;
        this.field_70170_p.field_72984_F.func_76320_a("checkDespawn");
        this.func_70623_bb();
        this.field_70170_p.field_72984_F.func_76319_b();
        this.field_70170_p.field_72984_F.func_76320_a("sensing");
        this.field_70723_bA.func_75523_a();
        this.field_70170_p.field_72984_F.func_76319_b();
        this.field_70170_p.field_72984_F.func_76320_a("targetSelector");
        this.field_70715_bh.func_75774_a();
        this.field_70170_p.field_72984_F.func_76319_b();
        this.field_70170_p.field_72984_F.func_76320_a("goalSelector");
        this.field_70714_bg.func_75774_a();
        this.field_70170_p.field_72984_F.func_76319_b();
        this.field_70170_p.field_72984_F.func_76320_a("navigation");
        this.field_70699_by.func_75501_e();
        this.field_70170_p.field_72984_F.func_76319_b();
        this.field_70170_p.field_72984_F.func_76320_a("mob tick");
        this.func_70629_bd();
        this.field_70170_p.field_72984_F.func_76319_b();
        this.field_70170_p.field_72984_F.func_76320_a("controls");
        this.field_70170_p.field_72984_F.func_76320_a("move");
        this.field_70765_h.func_75641_c();
        this.field_70170_p.field_72984_F.func_76318_c("look");
        this.field_70749_g.func_75649_a();
        this.field_70170_p.field_72984_F.func_76318_c("jump");
        this.field_70767_i.func_75661_b();
        this.field_70170_p.field_72984_F.func_76319_b();
        this.field_70170_p.field_72984_F.func_76319_b();
    }

    protected void func_70629_bd() {}

    protected void func_70626_be()
    {
        ++this.field_70708_bq;
        this.func_70623_bb();
        this.field_70702_br = 0.0F;
        this.field_70701_bs = 0.0F;
        float f = 8.0F;

        if (this.field_70146_Z.nextFloat() < 0.02F)
        {
            EntityPlayer entityplayer = this.field_70170_p.func_72890_a(this, (double)f);

            if (entityplayer != null)
            {
                this.field_70776_bF = entityplayer;
                this.field_70700_bx = 10 + this.field_70146_Z.nextInt(20);
            }
            else
            {
                this.field_70704_bt = (this.field_70146_Z.nextFloat() - 0.5F) * 20.0F;
            }
        }

        if (this.field_70776_bF != null)
        {
            this.func_70625_a(this.field_70776_bF, 10.0F, (float)this.func_70646_bf());

            if (this.field_70700_bx-- <= 0 || this.field_70776_bF.field_70128_L || this.field_70776_bF.func_70068_e((Entity) this) > (double)(f * f))
            {
                this.field_70776_bF = null;
            }
        }
        else
        {
            if (this.field_70146_Z.nextFloat() < 0.05F)
            {
                this.field_70704_bt = (this.field_70146_Z.nextFloat() - 0.5F) * 20.0F;
            }

            this.field_70177_z += this.field_70704_bt;
            this.field_70125_A = this.field_70698_bv;
        }

        boolean flag = this.func_70090_H();
        boolean flag1 = this.func_70058_J();

        if (flag || flag1)
        {
            this.field_70703_bu = this.field_70146_Z.nextFloat() < 0.8F;
        }
    }

    protected void func_82168_bl()
    {
        int i = this.func_82166_i();

        if (this.field_82175_bq)
        {
            ++this.field_82173_br;

            if (this.field_82173_br >= i)
            {
                this.field_82173_br = 0;
                this.field_82175_bq = false;
            }
        }
        else
        {
            this.field_82173_br = 0;
        }

        this.field_70733_aJ = (float)this.field_82173_br / (float)i;
    }

    public int func_70646_bf()
    {
        return 40;
    }

    public void func_70625_a(Entity p_70625_1_, float p_70625_2_, float p_70625_3_)
    {
        double d0 = p_70625_1_.field_70165_t - this.field_70165_t;
        double d1 = p_70625_1_.field_70161_v - this.field_70161_v;
        double d2;

        if (p_70625_1_ instanceof EntityLiving)
        {
            EntityLiving entityliving = (EntityLiving)p_70625_1_;
            d2 = this.field_70163_u + (double)this.func_70047_e() - (entityliving.field_70163_u + (double)entityliving.func_70047_e());
        }
        else
        {
            d2 = (p_70625_1_.field_70121_D.field_72338_b + p_70625_1_.field_70121_D.field_72337_e) / 2.0D - (this.field_70163_u + (double)this.func_70047_e());
        }

        double d3 = (double)MathHelper.func_76133_a(d0 * d0 + d1 * d1);
        float f2 = (float)(Math.atan2(d1, d0) * 180.0D / Math.PI) - 90.0F;
        float f3 = (float)(-(Math.atan2(d2, d3) * 180.0D / Math.PI));
        this.field_70125_A = -this.func_70663_b(this.field_70125_A, f3, p_70625_3_);
        this.field_70177_z = this.func_70663_b(this.field_70177_z, f2, p_70625_2_);
    }

    private float func_70663_b(float p_70663_1_, float p_70663_2_, float p_70663_3_)
    {
        float f3 = MathHelper.func_76142_g(p_70663_2_ - p_70663_1_);

        if (f3 > p_70663_3_)
        {
            f3 = p_70663_3_;
        }

        if (f3 < -p_70663_3_)
        {
            f3 = -p_70663_3_;
        }

        return p_70663_1_ + f3;
    }

    public boolean func_70601_bi()
    {
        return this.field_70170_p.func_72855_b(this.field_70121_D) && this.field_70170_p.func_72945_a(this, this.field_70121_D).isEmpty() && !this.field_70170_p.func_72953_d(this.field_70121_D);
    }

    protected void func_70076_C()
    {
        // CraftBukkit start
        EntityDamageByBlockEvent event = new EntityDamageByBlockEvent(null, this.getBukkitEntity(), EntityDamageEvent.DamageCause.VOID, 4);
        this.field_70170_p.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled() || event.getDamage() == 0)
        {
            return;
        }

        event.getEntity().setLastDamageCause(event);
        this.func_70097_a(DamageSource.field_76380_i, event.getDamage());
        // CraftBukkit end
    }

    public Vec3 func_70040_Z()
    {
        return this.func_70676_i(1.0F);
    }

    public Vec3 func_70676_i(float p_70676_1_)
    {
        float f1;
        float f2;
        float f3;
        float f4;

        if (p_70676_1_ == 1.0F)
        {
            f1 = MathHelper.func_76134_b(-this.field_70177_z * 0.017453292F - (float)Math.PI);
            f2 = MathHelper.func_76126_a(-this.field_70177_z * 0.017453292F - (float)Math.PI);
            f3 = -MathHelper.func_76134_b(-this.field_70125_A * 0.017453292F);
            f4 = MathHelper.func_76126_a(-this.field_70125_A * 0.017453292F);
            return this.field_70170_p.func_82732_R().func_72345_a((double)(f2 * f3), (double)f4, (double)(f1 * f3));
        }
        else
        {
            f1 = this.field_70127_C + (this.field_70125_A - this.field_70127_C) * p_70676_1_;
            f2 = this.field_70126_B + (this.field_70177_z - this.field_70126_B) * p_70676_1_;
            f3 = MathHelper.func_76134_b(-f2 * 0.017453292F - (float)Math.PI);
            f4 = MathHelper.func_76126_a(-f2 * 0.017453292F - (float)Math.PI);
            float f5 = -MathHelper.func_76134_b(-f1 * 0.017453292F);
            float f6 = MathHelper.func_76126_a(-f1 * 0.017453292F);
            return this.field_70170_p.func_82732_R().func_72345_a((double)(f4 * f5), (double)f6, (double)(f3 * f5));
        }
    }

    public int func_70641_bl()
    {
        return 4;
    }

    public boolean func_70608_bn()
    {
        return false;
    }

    protected void func_70679_bo()
    {
        Iterator iterator = this.field_70713_bf.keySet().iterator();

        while (iterator.hasNext())
        {
            Integer integer = (Integer)iterator.next();
            PotionEffect potioneffect = (PotionEffect)this.field_70713_bf.get(integer);

            if (!potioneffect.func_76455_a(this))
            {
                if (!this.field_70170_p.field_72995_K)
                {
                    iterator.remove();
                    this.func_70688_c(potioneffect);
                }
            }
            else if (potioneffect.func_76459_b() % 600 == 0)
            {
                this.func_70695_b(potioneffect);
            }
        }

        int i;

        if (this.field_70752_e)
        {
            if (!this.field_70170_p.field_72995_K)
            {
                if (this.field_70713_bf.isEmpty())
                {
                    this.field_70180_af.func_75692_b(9, Byte.valueOf((byte)0));
                    this.field_70180_af.func_75692_b(8, Integer.valueOf(0));
                    this.func_82142_c(false);
                }
                else
                {
                    i = PotionHelper.func_77911_a(this.field_70713_bf.values());
                    this.field_70180_af.func_75692_b(9, Byte.valueOf((byte)(PotionHelper.func_82817_b(this.field_70713_bf.values()) ? 1 : 0)));
                    this.field_70180_af.func_75692_b(8, Integer.valueOf(i));
                    this.func_82142_c(this.func_82165_m(Potion.field_76441_p.field_76415_H));
                }
            }

            this.field_70752_e = false;
        }

        i = this.field_70180_af.func_75679_c(8);
        boolean flag = this.field_70180_af.func_75683_a(9) > 0;

        if (i > 0)
        {
            boolean flag1 = false;

            if (!this.func_82150_aj())
            {
                flag1 = this.field_70146_Z.nextBoolean();
            }
            else
            {
                flag1 = this.field_70146_Z.nextInt(15) == 0;
            }

            if (flag)
            {
                flag1 &= this.field_70146_Z.nextInt(5) == 0;
            }

            if (flag1 && i > 0)
            {
                double d0 = (double)(i >> 16 & 255) / 255.0D;
                double d1 = (double)(i >> 8 & 255) / 255.0D;
                double d2 = (double)(i >> 0 & 255) / 255.0D;
                this.field_70170_p.func_72869_a(flag ? "mobSpellAmbient" : "mobSpell", this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N, this.field_70163_u + this.field_70146_Z.nextDouble() * (double)this.field_70131_O - (double)this.field_70129_M, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N, d0, d1, d2);
            }
        }
    }

    public void func_70674_bp()
    {
        Iterator iterator = this.field_70713_bf.keySet().iterator();

        while (iterator.hasNext())
        {
            Integer integer = (Integer)iterator.next();
            PotionEffect potioneffect = (PotionEffect)this.field_70713_bf.get(integer);

            if (!this.field_70170_p.field_72995_K)
            {
                iterator.remove();
                this.func_70688_c(potioneffect);
            }
        }
    }

    public Collection func_70651_bq()
    {
        return this.field_70713_bf.values();
    }

    public boolean func_82165_m(int p_82165_1_)
    {
        return this.field_70713_bf.containsKey(Integer.valueOf(p_82165_1_));
    }

    public boolean func_70644_a(Potion p_70644_1_)
    {
        return this.field_70713_bf.containsKey(Integer.valueOf(p_70644_1_.field_76415_H));
    }

    public PotionEffect func_70660_b(Potion p_70660_1_)
    {
        return (PotionEffect)this.field_70713_bf.get(Integer.valueOf(p_70660_1_.field_76415_H));
    }

    public void func_70690_d(PotionEffect p_70690_1_)
    {
        if (this.func_70687_e(p_70690_1_))
        {
            if (this.field_70713_bf.containsKey(Integer.valueOf(p_70690_1_.func_76456_a())))
            {
                ((PotionEffect)this.field_70713_bf.get(Integer.valueOf(p_70690_1_.func_76456_a()))).func_76452_a(p_70690_1_);
                this.func_70695_b((PotionEffect)this.field_70713_bf.get(Integer.valueOf(p_70690_1_.func_76456_a())));
            }
            else
            {
                this.field_70713_bf.put(Integer.valueOf(p_70690_1_.func_76456_a()), p_70690_1_);
                this.func_70670_a(p_70690_1_);
            }
        }
    }

    public boolean func_70687_e(PotionEffect p_70687_1_)
    {
        if (this.func_70668_bt() == EnumCreatureAttribute.UNDEAD)
        {
            int i = p_70687_1_.func_76456_a();

            if (i == Potion.field_76428_l.field_76415_H || i == Potion.field_76436_u.field_76415_H)
            {
                return false;
            }
        }

        return true;
    }

    public boolean func_70662_br()
    {
        return this.func_70668_bt() == EnumCreatureAttribute.UNDEAD;
    }

    public void func_82170_o(int p_82170_1_)
    {
        PotionEffect potioneffect = (PotionEffect)this.field_70713_bf.remove(Integer.valueOf(p_82170_1_));

        if (potioneffect != null)
        {
            this.func_70688_c(potioneffect);
        }
    }

    protected void func_70670_a(PotionEffect p_70670_1_)
    {
        this.field_70752_e = true;
    }

    protected void func_70695_b(PotionEffect p_70695_1_)
    {
        this.field_70752_e = true;
    }

    protected void func_70688_c(PotionEffect p_70688_1_)
    {
        this.field_70752_e = true;
    }

    public float func_70616_bs()
    {
        float f = 1.0F;

        if (this.func_70644_a(Potion.field_76424_c))
        {
            f *= 1.0F + 0.2F * (float)(this.func_70660_b(Potion.field_76424_c).func_76458_c() + 1);
        }

        if (this.func_70644_a(Potion.field_76421_d))
        {
            f *= 1.0F - 0.15F * (float)(this.func_70660_b(Potion.field_76421_d).func_76458_c() + 1);
        }

        return f;
    }

    public void func_70634_a(double p_70634_1_, double p_70634_3_, double p_70634_5_)
    {
        this.func_70012_b(p_70634_1_, p_70634_3_, p_70634_5_, this.field_70177_z, this.field_70125_A);
    }

    public boolean func_70631_g_()
    {
        return false;
    }

    public EnumCreatureAttribute func_70668_bt()
    {
        return EnumCreatureAttribute.UNDEFINED;
    }

    public void func_70669_a(ItemStack p_70669_1_)
    {
        this.func_85030_a("random.break", 0.8F, 0.8F + this.field_70170_p.field_73012_v.nextFloat() * 0.4F);

        for (int i = 0; i < 5; ++i)
        {
            Vec3 vec3 = this.field_70170_p.func_82732_R().func_72345_a(((double)this.field_70146_Z.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
            vec3.func_72440_a(-this.field_70125_A * (float)Math.PI / 180.0F);
            vec3.func_72442_b(-this.field_70177_z * (float)Math.PI / 180.0F);
            Vec3 vec31 = this.field_70170_p.func_82732_R().func_72345_a(((double)this.field_70146_Z.nextFloat() - 0.5D) * 0.3D, (double)(-this.field_70146_Z.nextFloat()) * 0.6D - 0.3D, 0.6D);
            vec31.func_72440_a(-this.field_70125_A * (float)Math.PI / 180.0F);
            vec31.func_72442_b(-this.field_70177_z * (float)Math.PI / 180.0F);
            vec31 = vec31.func_72441_c(this.field_70165_t, this.field_70163_u + (double)this.func_70047_e(), this.field_70161_v);
            this.field_70170_p.func_72869_a("iconcrack_" + p_70669_1_.func_77973_b().field_77779_bT, vec31.field_72450_a, vec31.field_72448_b, vec31.field_72449_c, vec3.field_72450_a, vec3.field_72448_b + 0.05D, vec3.field_72449_c);
        }
    }

    public int func_82143_as()
    {
        if (this.func_70638_az() == null)
        {
            return 3;
        }
        else
        {
            int i = (int)((float) this.field_70734_aK - (float) this.maxHealth * 0.33F);  // this.getMaxHealth() -> this.maxHealth
            i -= (3 - this.field_70170_p.field_73013_u) * 4;

            if (i < 0)
            {
                i = 0;
            }

            return i + 3;
        }
    }

    public ItemStack func_70694_bm()
    {
        return this.field_82182_bS[0];
    }

    public ItemStack func_71124_b(int p_71124_1_)
    {
        return this.field_82182_bS[p_71124_1_];
    }

    public ItemStack func_82169_q(int p_82169_1_)
    {
        return this.field_82182_bS[p_82169_1_ + 1];
    }

    public void func_70062_b(int p_70062_1_, ItemStack p_70062_2_)
    {
        this.field_82182_bS[p_70062_1_] = p_70062_2_;
    }

    public ItemStack[] func_70035_c()
    {
        return this.field_82182_bS;
    }

    protected void func_82160_b(boolean p_82160_1_, int p_82160_2_)
    {
        for (int j = 0; j < this.func_70035_c().length; ++j)
        {
            ItemStack itemstack = this.func_71124_b(j);
            boolean flag1 = this.field_82174_bp[j] > 1.0F;

            if (itemstack != null && (p_82160_1_ || flag1) && this.field_70146_Z.nextFloat() - (float)p_82160_2_ * 0.01F < this.field_82174_bp[j])
            {
                if (!flag1 && itemstack.func_77984_f())
                {
                    int k = Math.max(itemstack.func_77958_k() - 25, 1);
                    int l = itemstack.func_77958_k() - this.field_70146_Z.nextInt(this.field_70146_Z.nextInt(k) + 1);

                    if (l > k)
                    {
                        l = k;
                    }

                    if (l < 1)
                    {
                        l = 1;
                    }

                    itemstack.func_77964_b(l);
                }

                this.func_70099_a(itemstack, 0.0F);
            }
        }
    }

    protected void func_82164_bB()
    {
        if (this.field_70146_Z.nextFloat() < field_82176_d[this.field_70170_p.field_73013_u])
        {
            int i = this.field_70146_Z.nextInt(2);
            float f = this.field_70170_p.field_73013_u == 3 ? 0.1F : 0.25F;

            if (this.field_70146_Z.nextFloat() < 0.1F)
            {
                ++i;
            }

            if (this.field_70146_Z.nextFloat() < 0.1F)
            {
                ++i;
            }

            if (this.field_70146_Z.nextFloat() < 0.1F)
            {
                ++i;
            }

            for (int j = 3; j >= 0; --j)
            {
                ItemStack itemstack = this.func_82169_q(j);

                if (j < 3 && this.field_70146_Z.nextFloat() < f)
                {
                    break;
                }

                if (itemstack == null)
                {
                    Item item = func_82161_a(j + 1, i);

                    if (item != null)
                    {
                        this.func_70062_b(j + 1, new ItemStack(item));
                    }
                }
            }
        }
    }

    public void func_71001_a(Entity p_71001_1_, int p_71001_2_)
    {
        if (!p_71001_1_.field_70128_L && !this.field_70170_p.field_72995_K)
        {
            EntityTracker entitytracker = ((WorldServer)this.field_70170_p).func_73039_n();

            if (p_71001_1_ instanceof EntityItem)
            {
                entitytracker.func_72784_a(p_71001_1_, (Packet)(new Packet22Collect(p_71001_1_.field_70157_k, this.field_70157_k)));
            }

            if (p_71001_1_ instanceof EntityArrow)
            {
                entitytracker.func_72784_a(p_71001_1_, (Packet)(new Packet22Collect(p_71001_1_.field_70157_k, this.field_70157_k)));
            }

            if (p_71001_1_ instanceof EntityXPOrb)
            {
                entitytracker.func_72784_a(p_71001_1_, (Packet)(new Packet22Collect(p_71001_1_.field_70157_k, this.field_70157_k)));
            }
        }
    }

    public static int func_82159_b(ItemStack p_82159_0_)
    {
        if (p_82159_0_.field_77993_c != Block.field_72061_ba.field_71990_ca && p_82159_0_.field_77993_c != Item.field_82799_bQ.field_77779_bT)
        {
            if (p_82159_0_.func_77973_b() instanceof ItemArmor)
            {
                switch (((ItemArmor)p_82159_0_.func_77973_b()).field_77881_a)
                {
                    case 0:
                        return 4;
                    case 1:
                        return 3;
                    case 2:
                        return 2;
                    case 3:
                        return 1;
                }
            }

            return 0;
        }
        else
        {
            return 4;
        }
    }

    public static Item func_82161_a(int p_82161_0_, int p_82161_1_)
    {
        switch (p_82161_0_)
        {
            case 4:
                if (p_82161_1_ == 0)
                {
                    return Item.field_77687_V;
                }
                else if (p_82161_1_ == 1)
                {
                    return Item.field_77796_al;
                }
                else if (p_82161_1_ == 2)
                {
                    return Item.field_77694_Z;
                }
                else if (p_82161_1_ == 3)
                {
                    return Item.field_77812_ad;
                }
                else if (p_82161_1_ == 4)
                {
                    return Item.field_77820_ah;
                }
            case 3:
                if (p_82161_1_ == 0)
                {
                    return Item.field_77686_W;
                }
                else if (p_82161_1_ == 1)
                {
                    return Item.field_77806_am;
                }
                else if (p_82161_1_ == 2)
                {
                    return Item.field_77814_aa;
                }
                else if (p_82161_1_ == 3)
                {
                    return Item.field_77822_ae;
                }
                else if (p_82161_1_ == 4)
                {
                    return Item.field_77798_ai;
                }
            case 2:
                if (p_82161_1_ == 0)
                {
                    return Item.field_77693_X;
                }
                else if (p_82161_1_ == 1)
                {
                    return Item.field_77808_an;
                }
                else if (p_82161_1_ == 2)
                {
                    return Item.field_77816_ab;
                }
                else if (p_82161_1_ == 3)
                {
                    return Item.field_77824_af;
                }
                else if (p_82161_1_ == 4)
                {
                    return Item.field_77800_aj;
                }
            case 1:
                if (p_82161_1_ == 0)
                {
                    return Item.field_77692_Y;
                }
                else if (p_82161_1_ == 1)
                {
                    return Item.field_77802_ao;
                }
                else if (p_82161_1_ == 2)
                {
                    return Item.field_77810_ac;
                }
                else if (p_82161_1_ == 3)
                {
                    return Item.field_77818_ag;
                }
                else if (p_82161_1_ == 4)
                {
                    return Item.field_77794_ak;
                }
            default:
                return null;
        }
    }

    protected void func_82162_bC()
    {
        if (this.func_70694_bm() != null && this.field_70146_Z.nextFloat() < field_82177_b[this.field_70170_p.field_73013_u])
        {
            EnchantmentHelper.func_77504_a(this.field_70146_Z, this.func_70694_bm(), 5 + this.field_70170_p.field_73013_u * this.field_70146_Z.nextInt(6));
        }

        for (int i = 0; i < 4; ++i)
        {
            ItemStack itemstack = this.func_82169_q(i);

            if (itemstack != null && this.field_70146_Z.nextFloat() < field_82178_c[this.field_70170_p.field_73013_u])
            {
                EnchantmentHelper.func_77504_a(this.field_70146_Z, itemstack, 5 + this.field_70170_p.field_73013_u * this.field_70146_Z.nextInt(6));
            }
        }
    }

    public void func_82163_bD() {}

    private int func_82166_i()
    {
        return this.func_70644_a(Potion.field_76422_e) ? 6 - (1 + this.func_70660_b(Potion.field_76422_e).func_76458_c()) * 1 : (this.func_70644_a(Potion.field_76419_f) ? 6 + (1 + this.func_70660_b(Potion.field_76419_f).func_76458_c()) * 2 : 6);
    }

    public void func_71038_i()
    {
        if (!this.field_82175_bq || this.field_82173_br >= this.func_82166_i() / 2 || this.field_82173_br < 0)
        {
            this.field_82173_br = -1;
            this.field_82175_bq = true;

            if (this.field_70170_p instanceof WorldServer)
            {
                ((WorldServer) this.field_70170_p).func_73039_n().func_72784_a((Entity) this, (Packet)(new Packet18Animation(this, 1)));
            }
        }
    }

    public boolean func_82171_bF()
    {
        return false;
    }

    public final int func_85035_bI()
    {
        return this.field_70180_af.func_75683_a(10);
    }

    public final void func_85034_r(int p_85034_1_)
    {
        this.field_70180_af.func_75692_b(10, Byte.valueOf((byte)p_85034_1_));
    }
}
