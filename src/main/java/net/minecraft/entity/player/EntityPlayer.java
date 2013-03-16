package net.minecraft.entity.player;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.material.Material;
import net.minecraft.command.ICommandSender;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentThorns;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartHopper;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet103SetSlot;
import net.minecraft.potion.Potion;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScoreObjectiveCriteria;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.FoodStats;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StringTranslate;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumGameType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

// CraftBukkit start
import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.entity.CraftItem;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
// CraftBukkit end

public abstract class EntityPlayer extends EntityLiving implements ICommandSender
{
    public InventoryPlayer field_71071_by = new InventoryPlayer(this);
    private InventoryEnderChest field_71078_a = new InventoryEnderChest();
    public Container field_71069_bz;
    public Container field_71070_bA;
    protected FoodStats field_71100_bB = new FoodStats();
    protected int field_71101_bC = 0;
    public byte field_71098_bD = 0;
    public float field_71107_bF;
    public float field_71109_bG;
    public String field_71092_bJ;
    public int field_71090_bL = 0;
    public double field_71091_bM;
    public double field_71096_bN;
    public double field_71097_bO;
    public double field_71094_bP;
    public double field_71095_bQ;
    public double field_71085_bR;
    // CraftBukkit start
    public boolean field_71083_bS; // protected -> public
    public boolean fauxSleeping;
    public String spawnWorld = "";

    @Override
    public CraftHumanEntity getBukkitEntity()
    {
        return (CraftHumanEntity) super.getBukkitEntity();
    }
    // CraftBukkit end

    public ChunkCoordinates field_71081_bT;
    public int field_71076_b; // CraftBukkit - private -> public
    public float field_71079_bU;
    public float field_71089_bV;
    private ChunkCoordinates field_71077_c;
    private boolean field_82248_d;
    private ChunkCoordinates field_71073_d;
    public PlayerCapabilities field_71075_bZ = new PlayerCapabilities();
    public int oldLevel = -1; // CraftBukkit
    public int field_71068_ca;
    public int field_71067_cb;
    public float field_71106_cc;
    private ItemStack field_71074_e;
    private int field_71072_f;
    protected float field_71108_cd = 0.1F;
    protected float field_71102_ce = 0.02F;
    private int field_82249_h = 0;
    public EntityFishHook field_71104_cf = null;

    public EntityPlayer(World p_i3564_1_)
    {
        super(p_i3564_1_);
        this.field_71069_bz = new ContainerPlayer(this.field_71071_by, !p_i3564_1_.field_72995_K, this);
        this.field_71070_bA = this.field_71069_bz;
        this.field_70129_M = 1.62F;
        ChunkCoordinates chunkcoordinates = p_i3564_1_.func_72861_E();
        this.func_70012_b((double)chunkcoordinates.field_71574_a + 0.5D, (double)(chunkcoordinates.field_71572_b + 1), (double)chunkcoordinates.field_71573_c + 0.5D, 0.0F, 0.0F);
        this.field_70742_aC = "humanoid";
        this.field_70741_aB = 180.0F;
        this.field_70174_ab = 20;
        this.field_70750_az = "/mob/char.png";
    }

    public int func_70667_aM()
    {
        return 20;
    }

    protected void func_70088_a()
    {
        super.func_70088_a();
        this.field_70180_af.func_75682_a(16, Byte.valueOf((byte)0));
        this.field_70180_af.func_75682_a(17, Byte.valueOf((byte)0));
        this.field_70180_af.func_75682_a(18, Integer.valueOf(0));
    }

    public boolean func_71039_bw()
    {
        return this.field_71074_e != null;
    }

    public void func_71034_by()
    {
        if (this.field_71074_e != null)
        {
            this.field_71074_e.func_77974_b(this.field_70170_p, this, this.field_71072_f);
        }

        this.func_71041_bz();
    }

    public void func_71041_bz()
    {
        this.field_71074_e = null;
        this.field_71072_f = 0;

        if (!this.field_70170_p.field_72995_K)
        {
            this.func_70019_c(false);
        }
    }

    public boolean func_70632_aY()
    {
        return this.func_71039_bw() && Item.field_77698_e[this.field_71074_e.field_77993_c].func_77661_b(this.field_71074_e) == EnumAction.block;
    }

    public void func_70071_h_()
    {
        if (this.field_71074_e != null)
        {
            ItemStack itemstack = this.field_71071_by.func_70448_g();

            if (itemstack == this.field_71074_e)
            {
                if (this.field_71072_f <= 25 && this.field_71072_f % 4 == 0)
                {
                    this.func_71010_c(itemstack, 5);
                }

                if (--this.field_71072_f == 0 && !this.field_70170_p.field_72995_K)
                {
                    this.func_71036_o();
                }
            }
            else
            {
                this.func_71041_bz();
            }
        }

        if (this.field_71090_bL > 0)
        {
            --this.field_71090_bL;
        }

        if (this.func_70608_bn())
        {
            ++this.field_71076_b;

            if (this.field_71076_b > 100)
            {
                this.field_71076_b = 100;
            }

            if (!this.field_70170_p.field_72995_K)
            {
                if (!this.func_71065_l())
                {
                    this.func_70999_a(true, true, false);
                }
                else if (this.field_70170_p.func_72935_r())
                {
                    this.func_70999_a(false, true, true);
                }
            }
        }
        else if (this.field_71076_b > 0)
        {
            ++this.field_71076_b;

            if (this.field_71076_b >= 110)
            {
                this.field_71076_b = 0;
            }
        }

        super.func_70071_h_();

        if (!this.field_70170_p.field_72995_K && this.field_71070_bA != null && !this.field_71070_bA.func_75145_c(this))
        {
            this.func_71053_j();
            this.field_71070_bA = this.field_71069_bz;
        }

        if (this.func_70027_ad() && this.field_71075_bZ.field_75102_a)
        {
            this.func_70066_B();
        }

        this.field_71091_bM = this.field_71094_bP;
        this.field_71096_bN = this.field_71095_bQ;
        this.field_71097_bO = this.field_71085_bR;
        double d0 = this.field_70165_t - this.field_71094_bP;
        double d1 = this.field_70163_u - this.field_71095_bQ;
        double d2 = this.field_70161_v - this.field_71085_bR;
        double d3 = 10.0D;

        if (d0 > d3)
        {
            this.field_71091_bM = this.field_71094_bP = this.field_70165_t;
        }

        if (d2 > d3)
        {
            this.field_71097_bO = this.field_71085_bR = this.field_70161_v;
        }

        if (d1 > d3)
        {
            this.field_71096_bN = this.field_71095_bQ = this.field_70163_u;
        }

        if (d0 < -d3)
        {
            this.field_71091_bM = this.field_71094_bP = this.field_70165_t;
        }

        if (d2 < -d3)
        {
            this.field_71097_bO = this.field_71085_bR = this.field_70161_v;
        }

        if (d1 < -d3)
        {
            this.field_71096_bN = this.field_71095_bQ = this.field_70163_u;
        }

        this.field_71094_bP += d0 * 0.25D;
        this.field_71085_bR += d2 * 0.25D;
        this.field_71095_bQ += d1 * 0.25D;
        this.func_71064_a(StatList.field_75948_k, 1);

        if (this.field_70154_o == null)
        {
            this.field_71073_d = null;
        }

        if (!this.field_70170_p.field_72995_K)
        {
            this.field_71100_bB.func_75118_a(this);
        }
    }

    public int func_82145_z()
    {
        return this.field_71075_bZ.field_75102_a ? 0 : 80;
    }

    public int func_82147_ab()
    {
        return 10;
    }

    public void func_85030_a(String p_85030_1_, float p_85030_2_, float p_85030_3_)
    {
        this.field_70170_p.func_85173_a(this, p_85030_1_, p_85030_2_, p_85030_3_);
    }

    protected void func_71010_c(ItemStack p_71010_1_, int p_71010_2_)
    {
        if (p_71010_1_.func_77975_n() == EnumAction.drink)
        {
            this.func_85030_a("random.drink", 0.5F, this.field_70170_p.field_73012_v.nextFloat() * 0.1F + 0.9F);
        }

        if (p_71010_1_.func_77975_n() == EnumAction.eat)
        {
            for (int j = 0; j < p_71010_2_; ++j)
            {
                Vec3 vec3 = this.field_70170_p.func_82732_R().func_72345_a(((double)this.field_70146_Z.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
                vec3.func_72440_a(-this.field_70125_A * (float)Math.PI / 180.0F);
                vec3.func_72442_b(-this.field_70177_z * (float)Math.PI / 180.0F);
                Vec3 vec31 = this.field_70170_p.func_82732_R().func_72345_a(((double)this.field_70146_Z.nextFloat() - 0.5D) * 0.3D, (double)(-this.field_70146_Z.nextFloat()) * 0.6D - 0.3D, 0.6D);
                vec31.func_72440_a(-this.field_70125_A * (float)Math.PI / 180.0F);
                vec31.func_72442_b(-this.field_70177_z * (float)Math.PI / 180.0F);
                vec31 = vec31.func_72441_c(this.field_70165_t, this.field_70163_u + (double)this.func_70047_e(), this.field_70161_v);
                this.field_70170_p.func_72869_a("iconcrack_" + p_71010_1_.func_77973_b().field_77779_bT, vec31.field_72450_a, vec31.field_72448_b, vec31.field_72449_c, vec3.field_72450_a, vec3.field_72448_b + 0.05D, vec3.field_72449_c);
            }

            this.func_85030_a("random.eat", 0.5F + 0.5F * (float)this.field_70146_Z.nextInt(2), (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F);
        }
    }

    protected void func_71036_o()
    {
        if (this.field_71074_e != null)
        {
            this.func_71010_c(this.field_71074_e, 16);
            int i = this.field_71074_e.field_77994_a;
            // CraftBukkit start
            org.bukkit.inventory.ItemStack craftItem = CraftItemStack.asBukkitCopy(this.field_71074_e);
            PlayerItemConsumeEvent event = new PlayerItemConsumeEvent((Player) this.getBukkitEntity(), craftItem);
            field_70170_p.getServer().getPluginManager().callEvent(event);

            if (event.isCancelled())
            {
                // Update client
                if (this instanceof EntityPlayerMP)
                {
                    ((EntityPlayerMP) this).field_71135_a.func_72567_b(new Packet103SetSlot((byte) 0, field_71070_bA.func_75147_a((IInventory) this.field_71071_by, this.field_71071_by.field_70461_c).field_75225_a, this.field_71074_e));
                }

                return;
            }

            // Plugin modified the item, process it but don't remove it
            if (!craftItem.equals(event.getItem()))
            {
                CraftItemStack.asNMSCopy(event.getItem()).func_77950_b(this.field_70170_p, this);

                // Update client
                if (this instanceof EntityPlayerMP)
                {
                    ((EntityPlayerMP) this).field_71135_a.func_72567_b(new Packet103SetSlot((byte) 0, field_71070_bA.func_75147_a((IInventory) this.field_71071_by, this.field_71071_by.field_70461_c).field_75225_a, this.field_71074_e));
                }

                return;
            }

            // CraftBukkit end
            ItemStack itemstack = this.field_71074_e.func_77950_b(this.field_70170_p, this);

            if (itemstack != this.field_71074_e || itemstack != null && itemstack.field_77994_a != i)
            {
                this.field_71071_by.field_70462_a[this.field_71071_by.field_70461_c] = itemstack;

                if (itemstack.field_77994_a == 0)
                {
                    this.field_71071_by.field_70462_a[this.field_71071_by.field_70461_c] = null;
                }
            }

            this.func_71041_bz();
        }
    }

    protected boolean func_70610_aX()
    {
        return this.func_70630_aN() <= 0 || this.func_70608_bn();
    }

    // CraftBukkit - protected -> public
    public void func_71053_j()
    {
        this.field_71070_bA = this.field_71069_bz;
    }

    public void func_70078_a(Entity p_70078_1_)
    {
        // CraftBukkit start - mirror Entity mount changes
        this.setPassengerOf(p_70078_1_);
    }

    public void setPassengerOf(Entity entity)
    {
        // CraftBukkit end
        if (this.field_70154_o == entity)
        {
            this.func_70061_h(entity);

            if (this.field_70154_o != null)
            {
                this.field_70154_o.field_70153_n = null;
            }

            this.field_70154_o = null;
        }
        else
        {
            super.setPassengerOf(entity); // CraftBukkit - call new parent
        }
    }

    public void func_70098_U()
    {
        double d0 = this.field_70165_t;
        double d1 = this.field_70163_u;
        double d2 = this.field_70161_v;
        float f = this.field_70177_z;
        float f1 = this.field_70125_A;
        super.func_70098_U();
        this.field_71107_bF = this.field_71109_bG;
        this.field_71109_bG = 0.0F;
        this.func_71015_k(this.field_70165_t - d0, this.field_70163_u - d1, this.field_70161_v - d2);

        if (this.field_70154_o instanceof EntityPig)
        {
            this.field_70125_A = f1;
            this.field_70177_z = f;
            this.field_70761_aq = ((EntityPig)this.field_70154_o).field_70761_aq;
        }
    }

    protected void func_70626_be()
    {
        this.func_82168_bl();
    }

    public void func_70636_d()
    {
        if (this.field_71101_bC > 0)
        {
            --this.field_71101_bC;
        }

        // CraftBukkit - this.getMaxHealth() -> this.maxHealth
        if (this.field_70170_p.field_73013_u == 0 && this.func_70630_aN() < this.maxHealth && this.field_70173_aa % 20 * 12 == 0)
        {
            // CraftBukkit - added regain reason of "REGEN" for filtering purposes.
            this.heal(1, org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason.REGEN);
        }

        this.field_71071_by.func_70429_k();
        this.field_71107_bF = this.field_71109_bG;
        super.func_70636_d();
        this.field_70746_aG = this.field_71075_bZ.func_75094_b();
        this.field_70747_aH = this.field_71102_ce;

        if (this.func_70051_ag())
        {
            this.field_70746_aG = (float)((double)this.field_70746_aG + (double)this.field_71075_bZ.func_75094_b() * 0.3D);
            this.field_70747_aH = (float)((double)this.field_70747_aH + (double)this.field_71102_ce * 0.3D);
        }

        float f = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
        // CraftBukkit - Math -> TrigMath
        float f1 = (float) org.bukkit.craftbukkit.TrigMath.atan(-this.field_70181_x * 0.20000000298023224D) * 15.0F;

        if (f > 0.1F)
        {
            f = 0.1F;
        }

        if (!this.field_70122_E || this.func_70630_aN() <= 0)
        {
            f = 0.0F;
        }

        if (this.field_70122_E || this.func_70630_aN() <= 0)
        {
            f1 = 0.0F;
        }

        this.field_71109_bG += (f - this.field_71109_bG) * 0.4F;
        this.field_70726_aT += (f1 - this.field_70726_aT) * 0.8F;

        if (this.func_70630_aN() > 0)
        {
            List list = this.field_70170_p.func_72839_b(this, this.field_70121_D.func_72314_b(1.0D, 0.5D, 1.0D));

            if (list != null)
            {
                for (int i = 0; i < list.size(); ++i)
                {
                    Entity entity = (Entity)list.get(i);

                    if (!entity.field_70128_L)
                    {
                        this.func_71044_o(entity);
                    }
                }
            }
        }
    }

    private void func_71044_o(Entity p_71044_1_)
    {
        p_71044_1_.func_70100_b_(this);
    }

    public int func_71037_bA()
    {
        return this.field_70180_af.func_75679_c(18);
    }

    public void func_85040_s(int p_85040_1_)
    {
        this.field_70180_af.func_75692_b(18, Integer.valueOf(p_85040_1_));
    }

    public void func_85039_t(int p_85039_1_)
    {
        int j = this.func_71037_bA();
        this.field_70180_af.func_75692_b(18, Integer.valueOf(j + p_85039_1_));
    }

    public void func_70645_a(DamageSource p_70645_1_)
    {
        super.func_70645_a(p_70645_1_);
        this.func_70105_a(0.2F, 0.2F);
        this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
        this.field_70181_x = 0.10000000149011612D;

        if (this.field_71092_bJ.equals("Notch"))
        {
            this.func_71019_a(new ItemStack(Item.field_77706_j, 1), true);
        }

        if (!this.field_70170_p.func_82736_K().func_82766_b("keepInventory"))
        {
            this.field_71071_by.func_70436_m();
        }

        if (p_70645_1_ != null)
        {
            this.field_70159_w = (double)(-MathHelper.func_76134_b((this.field_70739_aP + this.field_70177_z) * (float)Math.PI / 180.0F) * 0.1F);
            this.field_70179_y = (double)(-MathHelper.func_76126_a((this.field_70739_aP + this.field_70177_z) * (float)Math.PI / 180.0F) * 0.1F);
        }
        else
        {
            this.field_70159_w = this.field_70179_y = 0.0D;
        }

        this.field_70129_M = 0.1F;
        this.func_71064_a(StatList.field_75960_y, 1);
    }

    public void func_70084_c(Entity p_70084_1_, int p_70084_2_)
    {
        this.func_85039_t(p_70084_2_);
        Collection collection = this.func_96123_co().func_96520_a(ScoreObjectiveCriteria.field_96640_e);

        if (p_70084_1_ instanceof EntityPlayer)
        {
            this.func_71064_a(StatList.field_75932_A, 1);
            collection.addAll(this.func_96123_co().func_96520_a(ScoreObjectiveCriteria.field_96639_d));
        }
        else
        {
            this.func_71064_a(StatList.field_75959_z, 1);
        }

        Iterator iterator = collection.iterator();

        while (iterator.hasNext())
        {
            ScoreObjective scoreobjective = (ScoreObjective)iterator.next();
            Score score = this.func_96123_co().func_96529_a(this.func_70023_ak(), scoreobjective);
            score.func_96648_a();
        }
    }

    public EntityItem func_71040_bB(boolean p_71040_1_)
    {
        return this.func_71019_a(this.field_71071_by.func_70298_a(this.field_71071_by.field_70461_c, p_71040_1_ && this.field_71071_by.func_70448_g() != null ? this.field_71071_by.func_70448_g().field_77994_a : 1), false);
    }

    public EntityItem func_71021_b(ItemStack p_71021_1_)
    {
        return this.func_71019_a(p_71021_1_, false);
    }

    public EntityItem func_71019_a(ItemStack p_71019_1_, boolean p_71019_2_)
    {
        if (p_71019_1_ == null)
        {
            return null;
        }
        else
        {
            EntityItem entityitem = new EntityItem(this.field_70170_p, this.field_70165_t, this.field_70163_u - 0.30000001192092896D + (double)this.func_70047_e(), this.field_70161_v, p_71019_1_);
            entityitem.field_70293_c = 40;
            float f = 0.1F;
            float f1;

            if (p_71019_2_)
            {
                f1 = this.field_70146_Z.nextFloat() * 0.5F;
                float f2 = this.field_70146_Z.nextFloat() * (float)Math.PI * 2.0F;
                entityitem.field_70159_w = (double)(-MathHelper.func_76126_a(f2) * f1);
                entityitem.field_70179_y = (double)(MathHelper.func_76134_b(f2) * f1);
                entityitem.field_70181_x = 0.20000000298023224D;
            }
            else
            {
                f = 0.3F;
                entityitem.field_70159_w = (double)(-MathHelper.func_76126_a(this.field_70177_z / 180.0F * (float)Math.PI) * MathHelper.func_76134_b(this.field_70125_A / 180.0F * (float)Math.PI) * f);
                entityitem.field_70179_y = (double)(MathHelper.func_76134_b(this.field_70177_z / 180.0F * (float)Math.PI) * MathHelper.func_76134_b(this.field_70125_A / 180.0F * (float)Math.PI) * f);
                entityitem.field_70181_x = (double)(-MathHelper.func_76126_a(this.field_70125_A / 180.0F * (float)Math.PI) * f + 0.1F);
                f = 0.02F;
                f1 = this.field_70146_Z.nextFloat() * (float)Math.PI * 2.0F;
                f *= this.field_70146_Z.nextFloat();
                entityitem.field_70159_w += Math.cos((double)f1) * (double)f;
                entityitem.field_70181_x += (double)((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.1F);
                entityitem.field_70179_y += Math.sin((double)f1) * (double)f;
            }

            // CraftBukkit start
            Player player = (Player) this.getBukkitEntity();
            CraftItem drop = new CraftItem(this.field_70170_p.getServer(), entityitem);
            PlayerDropItemEvent event = new PlayerDropItemEvent(player, drop);
            this.field_70170_p.getServer().getPluginManager().callEvent(event);

            if (event.isCancelled())
            {
                player.getInventory().addItem(drop.getItemStack());
                return null;
            }

            // CraftBukkit end
            this.func_71012_a(entityitem);
            this.func_71064_a(StatList.field_75952_v, 1);
            return entityitem;
        }
    }

    protected void func_71012_a(EntityItem p_71012_1_)
    {
        this.field_70170_p.func_72838_d(p_71012_1_);
    }

    public float func_71055_a(Block p_71055_1_, boolean p_71055_2_)
    {
        float f = this.field_71071_by.func_70438_a(p_71055_1_);

        if (f > 1.0F)
        {
            int i = EnchantmentHelper.func_77509_b(this);
            ItemStack itemstack = this.field_71071_by.func_70448_g();

            if (i > 0 && itemstack != null)
            {
                float f1 = (float)(i * i + 1);

                if (!itemstack.func_77987_b(p_71055_1_) && f <= 1.0F)
                {
                    f += f1 * 0.08F;
                }
                else
                {
                    f += f1;
                }
            }
        }

        if (this.func_70644_a(Potion.field_76422_e))
        {
            f *= 1.0F + (float)(this.func_70660_b(Potion.field_76422_e).func_76458_c() + 1) * 0.2F;
        }

        if (this.func_70644_a(Potion.field_76419_f))
        {
            f *= 1.0F - (float)(this.func_70660_b(Potion.field_76419_f).func_76458_c() + 1) * 0.2F;
        }

        if (this.func_70055_a(Material.field_76244_g) && !EnchantmentHelper.func_77510_g(this))
        {
            f /= 5.0F;
        }

        if (!this.field_70122_E)
        {
            f /= 5.0F;
        }

        return f;
    }

    public boolean func_71062_b(Block p_71062_1_)
    {
        return this.field_71071_by.func_70454_b(p_71062_1_);
    }

    public void func_70037_a(NBTTagCompound p_70037_1_)
    {
        super.func_70037_a(p_70037_1_);
        NBTTagList nbttaglist = p_70037_1_.func_74761_m("Inventory");
        this.field_71071_by.func_70443_b(nbttaglist);
        this.field_71071_by.field_70461_c = p_70037_1_.func_74762_e("SelectedItemSlot");
        this.field_71083_bS = p_70037_1_.func_74767_n("Sleeping");
        this.field_71076_b = p_70037_1_.func_74765_d("SleepTimer");
        this.field_71106_cc = p_70037_1_.func_74760_g("XpP");
        this.field_71068_ca = p_70037_1_.func_74762_e("XpLevel");
        this.field_71067_cb = p_70037_1_.func_74762_e("XpTotal");
        this.func_85040_s(p_70037_1_.func_74762_e("Score"));

        if (this.field_71083_bS)
        {
            this.field_71081_bT = new ChunkCoordinates(MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c(this.field_70163_u), MathHelper.func_76128_c(this.field_70161_v));
            this.func_70999_a(true, true, false);
        }

        // CraftBukkit start
        this.spawnWorld = p_70037_1_.func_74779_i("SpawnWorld");

        if ("".equals(spawnWorld))
        {
            this.spawnWorld = this.field_70170_p.getServer().getWorlds().get(0).getName();
        }

        // CraftBukkit end

        if (p_70037_1_.func_74764_b("SpawnX") && p_70037_1_.func_74764_b("SpawnY") && p_70037_1_.func_74764_b("SpawnZ"))
        {
            this.field_71077_c = new ChunkCoordinates(p_70037_1_.func_74762_e("SpawnX"), p_70037_1_.func_74762_e("SpawnY"), p_70037_1_.func_74762_e("SpawnZ"));
            this.field_82248_d = p_70037_1_.func_74767_n("SpawnForced");
        }

        this.field_71100_bB.func_75112_a(p_70037_1_);
        this.field_71075_bZ.func_75095_b(p_70037_1_);

        if (p_70037_1_.func_74764_b("EnderItems"))
        {
            NBTTagList nbttaglist1 = p_70037_1_.func_74761_m("EnderItems");
            this.field_71078_a.func_70486_a(nbttaglist1);
        }
    }

    public void func_70014_b(NBTTagCompound p_70014_1_)
    {
        super.func_70014_b(p_70014_1_);
        p_70014_1_.func_74782_a("Inventory", this.field_71071_by.func_70442_a(new NBTTagList()));
        p_70014_1_.func_74768_a("SelectedItemSlot", this.field_71071_by.field_70461_c);
        p_70014_1_.func_74757_a("Sleeping", this.field_71083_bS);
        p_70014_1_.func_74777_a("SleepTimer", (short)this.field_71076_b);
        p_70014_1_.func_74776_a("XpP", this.field_71106_cc);
        p_70014_1_.func_74768_a("XpLevel", this.field_71068_ca);
        p_70014_1_.func_74768_a("XpTotal", this.field_71067_cb);
        p_70014_1_.func_74768_a("Score", this.func_71037_bA());

        if (this.field_71077_c != null)
        {
            p_70014_1_.func_74768_a("SpawnX", this.field_71077_c.field_71574_a);
            p_70014_1_.func_74768_a("SpawnY", this.field_71077_c.field_71572_b);
            p_70014_1_.func_74768_a("SpawnZ", this.field_71077_c.field_71573_c);
            p_70014_1_.func_74757_a("SpawnForced", this.field_82248_d);
            p_70014_1_.func_74778_a("SpawnWorld", spawnWorld); // CraftBukkit - fixes bed spawns for multiworld worlds
        }

        this.field_71100_bB.func_75117_b(p_70014_1_);
        this.field_71075_bZ.func_75091_a(p_70014_1_);
        p_70014_1_.func_74782_a("EnderItems", this.field_71078_a.func_70487_g());
    }

    public void func_71007_a(IInventory p_71007_1_) {}

    public void func_94064_a(TileEntityHopper p_94064_1_) {}

    public void func_96125_a(EntityMinecartHopper p_96125_1_) {}

    public void func_71002_c(int p_71002_1_, int p_71002_2_, int p_71002_3_, String p_71002_4_) {}

    public void func_82244_d(int p_82244_1_, int p_82244_2_, int p_82244_3_) {}

    public void func_71058_b(int p_71058_1_, int p_71058_2_, int p_71058_3_) {}

    public float func_70047_e()
    {
        return 0.12F;
    }

    protected void func_71061_d_()
    {
        this.field_70129_M = 1.62F;
    }

    public boolean func_70097_a(DamageSource p_70097_1_, int p_70097_2_)
    {
        if (this.func_85032_ar())
        {
            return false;
        }
        else if (this.field_71075_bZ.field_75102_a && !p_70097_1_.func_76357_e())
        {
            return false;
        }
        else
        {
            this.field_70708_bq = 0;

            if (this.func_70630_aN() <= 0)
            {
                return false;
            }
            else
            {
                if (this.func_70608_bn() && !this.field_70170_p.field_72995_K)
                {
                    this.func_70999_a(true, true, false);
                }

                if (p_70097_1_.func_76350_n())
                {
                    if (this.field_70170_p.field_73013_u == 0)
                    {
                        return false; // CraftBukkit - i = 0 -> return false
                    }

                    if (this.field_70170_p.field_73013_u == 1)
                    {
                        p_70097_2_ = p_70097_2_ / 2 + 1;
                    }

                    if (this.field_70170_p.field_73013_u == 3)
                    {
                        p_70097_2_ = p_70097_2_ * 3 / 2;
                    }
                }

                if (false && p_70097_2_ == 0)   // CraftBukkit - Don't filter out 0 damage
                {
                    return false;
                }
                else
                {
                    Entity entity = p_70097_1_.func_76346_g();

                    if (entity instanceof EntityArrow && ((EntityArrow)entity).field_70250_c != null)
                    {
                        entity = ((EntityArrow)entity).field_70250_c;
                    }

                    if (entity instanceof EntityLiving)
                    {
                        this.func_71022_a((EntityLiving)entity, false);
                    }

                    this.func_71064_a(StatList.field_75961_x, p_70097_2_);
                    return super.func_70097_a(p_70097_1_, p_70097_2_);
                }
            }
        }
    }

    public boolean func_96122_a(EntityPlayer p_96122_1_)
    {
        ScorePlayerTeam scoreplayerteam = this.func_96124_cp();
        ScorePlayerTeam scoreplayerteam1 = p_96122_1_.func_96124_cp();
        return scoreplayerteam != scoreplayerteam1 ? true : (scoreplayerteam != null ? scoreplayerteam.func_96665_g() : true);
    }

    protected void func_71022_a(EntityLiving p_71022_1_, boolean p_71022_2_)
    {
        if (!(p_71022_1_ instanceof EntityCreeper) && !(p_71022_1_ instanceof EntityGhast))
        {
            if (p_71022_1_ instanceof EntityWolf)
            {
                EntityWolf entitywolf = (EntityWolf)p_71022_1_;

                if (entitywolf.func_70909_n() && this.field_71092_bJ.equals(entitywolf.func_70905_p()))
                {
                    return;
                }
            }

            if (!(p_71022_1_ instanceof EntityPlayer) || this.func_96122_a((EntityPlayer)p_71022_1_))
            {
                List list = this.field_70170_p.func_72872_a(EntityWolf.class, AxisAlignedBB.func_72332_a().func_72299_a(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70165_t + 1.0D, this.field_70163_u + 1.0D, this.field_70161_v + 1.0D).func_72314_b(16.0D, 4.0D, 16.0D));
                Iterator iterator = list.iterator();

                while (iterator.hasNext())
                {
                    EntityWolf entitywolf1 = (EntityWolf)iterator.next();

                    if (entitywolf1.func_70909_n() && entitywolf1.func_70777_m() == null && this.field_71092_bJ.equals(entitywolf1.func_70905_p()) && (!p_71022_2_ || !entitywolf1.func_70906_o()))
                    {
                        entitywolf1.func_70904_g(false);
                        entitywolf1.func_70784_b(p_71022_1_);
                    }
                }
            }
        }
    }

    protected void func_70675_k(int p_70675_1_)
    {
        this.field_71071_by.func_70449_g(p_70675_1_);
    }

    public int func_70658_aO()
    {
        return this.field_71071_by.func_70430_l();
    }

    public float func_82243_bO()
    {
        int i = 0;
        ItemStack[] aitemstack = this.field_71071_by.field_70460_b;
        int j = aitemstack.length;

        for (int k = 0; k < j; ++k)
        {
            ItemStack itemstack = aitemstack[k];

            if (itemstack != null)
            {
                ++i;
            }
        }

        return (float)i / (float)this.field_71071_by.field_70460_b.length;
    }

    protected void func_70665_d(DamageSource p_70665_1_, int p_70665_2_)
    {
        if (!this.func_85032_ar())
        {
            if (!p_70665_1_.func_76363_c() && this.func_70632_aY())
            {
                p_70665_2_ = 1 + p_70665_2_ >> 1;
            }

            p_70665_2_ = this.func_70655_b(p_70665_1_, p_70665_2_);
            p_70665_2_ = this.func_70672_c(p_70665_1_, p_70665_2_);
            this.func_71020_j(p_70665_1_.func_76345_d());
            int j = this.func_70630_aN();
            this.func_70606_j(this.func_70630_aN() - p_70665_2_);
            this.field_94063_bt.func_94547_a(p_70665_1_, j, p_70665_2_);
        }
    }

    public void func_71042_a(TileEntityFurnace p_71042_1_) {}

    public void func_71006_a(TileEntityDispenser p_71006_1_) {}

    public void func_71014_a(TileEntity p_71014_1_) {}

    public void func_71017_a(TileEntityBrewingStand p_71017_1_) {}

    public void func_82240_a(TileEntityBeacon p_82240_1_) {}

    public void func_71030_a(IMerchant p_71030_1_, String p_71030_2_) {}

    public void func_71048_c(ItemStack p_71048_1_) {}

    public boolean func_70998_m(Entity p_70998_1_)
    {
        if (p_70998_1_.func_70085_c(this))
        {
            return true;
        }
        else
        {
            ItemStack itemstack = this.func_71045_bC();

            if (itemstack != null && p_70998_1_ instanceof EntityLiving)
            {
                if (this.field_71075_bZ.field_75098_d)
                {
                    itemstack = itemstack.func_77946_l();
                }

                if (itemstack.func_77947_a((EntityLiving)p_70998_1_))
                {
                    // CraftBukkit - bypass infinite items; <= 0 -> == 0
                    if (itemstack.field_77994_a == 0 && !this.field_71075_bZ.field_75098_d)
                    {
                        this.func_71028_bD();
                    }

                    return true;
                }
            }

            return false;
        }
    }

    public ItemStack func_71045_bC()
    {
        return this.field_71071_by.func_70448_g();
    }

    public void func_71028_bD()
    {
        this.field_71071_by.func_70299_a(this.field_71071_by.field_70461_c, (ItemStack)null);
    }

    public double func_70033_W()
    {
        return (double)(this.field_70129_M - 0.5F);
    }

    public void func_71059_n(Entity p_71059_1_)
    {
        if (p_71059_1_.func_70075_an())
        {
            if (!p_71059_1_.func_85031_j(this))
            {
                int i = this.field_71071_by.func_70444_a(p_71059_1_);

                if (this.func_70644_a(Potion.field_76420_g))
                {
                    i += 3 << this.func_70660_b(Potion.field_76420_g).func_76458_c();
                }

                if (this.func_70644_a(Potion.field_76437_t))
                {
                    i -= 2 << this.func_70660_b(Potion.field_76437_t).func_76458_c();
                }

                int j = 0;
                int k = 0;

                if (p_71059_1_ instanceof EntityLiving)
                {
                    k = EnchantmentHelper.func_77512_a((EntityLiving) this, (EntityLiving) p_71059_1_);
                    j += EnchantmentHelper.func_77507_b(this, (EntityLiving)p_71059_1_);
                }

                if (this.func_70051_ag())
                {
                    ++j;
                }

                if (i > 0 || k > 0)
                {
                    boolean flag = this.field_70143_R > 0.0F && !this.field_70122_E && !this.func_70617_f_() && !this.func_70090_H() && !this.func_70644_a(Potion.field_76440_q) && this.field_70154_o == null && p_71059_1_ instanceof EntityLiving;

                    if (flag && i > 0)
                    {
                        i += this.field_70146_Z.nextInt(i / 2 + 2);
                    }

                    i += k;
                    boolean flag1 = false;
                    int l = EnchantmentHelper.func_90036_a(this);

                    if (p_71059_1_ instanceof EntityLiving && l > 0 && !p_71059_1_.func_70027_ad())
                    {
                        flag1 = true;
                        p_71059_1_.func_70015_d(1);
                    }

                    boolean flag2 = p_71059_1_.func_70097_a(DamageSource.func_76365_a(this), i);

                    // CraftBukkit start - Return when the damage fails so that the item will not lose durability
                    if (!flag2)
                    {
                        if (flag1)
                        {
                            p_71059_1_.func_70066_B();
                        }

                        return;
                    }

                    // CraftBukkit end

                    if (flag2)
                    {
                        if (j > 0)
                        {
                            p_71059_1_.func_70024_g((double)(-MathHelper.func_76126_a(this.field_70177_z * (float)Math.PI / 180.0F) * (float)j * 0.5F), 0.1D, (double)(MathHelper.func_76134_b(this.field_70177_z * (float)Math.PI / 180.0F) * (float)j * 0.5F));
                            this.field_70159_w *= 0.6D;
                            this.field_70179_y *= 0.6D;
                            this.func_70031_b(false);
                        }

                        if (flag)
                        {
                            this.func_71009_b(p_71059_1_);
                        }

                        if (k > 0)
                        {
                            this.func_71047_c(p_71059_1_);
                        }

                        if (i >= 18)
                        {
                            this.func_71029_a((StatBase) AchievementList.field_75999_E);
                        }

                        this.func_70607_j(p_71059_1_);

                        if (p_71059_1_ instanceof EntityLiving)
                        {
                            EnchantmentThorns.func_92096_a(this, (EntityLiving)p_71059_1_, this.field_70146_Z);
                        }
                    }

                    ItemStack itemstack = this.func_71045_bC();
                    Object object = p_71059_1_;

                    if (p_71059_1_ instanceof EntityDragonPart)
                    {
                        IEntityMultiPart ientitymultipart = ((EntityDragonPart)p_71059_1_).field_70259_a;

                        if (ientitymultipart != null && ientitymultipart instanceof EntityLiving)
                        {
                            object = (EntityLiving)ientitymultipart;
                        }
                    }

                    if (itemstack != null && object instanceof EntityLiving)
                    {
                        itemstack.func_77961_a((EntityLiving)object, this);

                        // CraftBukkit - bypass infinite items; <= 0 -> == 0
                        if (itemstack.field_77994_a == 0)
                        {
                            this.func_71028_bD();
                        }
                    }

                    if (p_71059_1_ instanceof EntityLiving)
                    {
                        if (p_71059_1_.func_70089_S())
                        {
                            this.func_71022_a((EntityLiving)p_71059_1_, true);
                        }

                        this.func_71064_a(StatList.field_75951_w, i);

                        if (l > 0 && flag2)
                        {
                            // CraftBukkit start - raise a combust event when somebody hits with a fire enchanted item
                            EntityCombustByEntityEvent combustEvent = new EntityCombustByEntityEvent(this.getBukkitEntity(), p_71059_1_.getBukkitEntity(), l * 4);
                            org.bukkit.Bukkit.getPluginManager().callEvent(combustEvent);

                            if (!combustEvent.isCancelled())
                            {
                                p_71059_1_.func_70015_d(combustEvent.getDuration());
                            }

                            // CraftBukkit end
                        }
                        else if (flag1)
                        {
                            p_71059_1_.func_70066_B();
                        }
                    }

                    this.func_71020_j(0.3F);
                }
            }
        }
    }

    public void func_71009_b(Entity p_71009_1_) {}

    public void func_71047_c(Entity p_71047_1_) {}

    public void func_70106_y()
    {
        super.func_70106_y();
        this.field_71069_bz.func_75134_a(this);

        if (this.field_71070_bA != null)
        {
            this.field_71070_bA.func_75134_a(this);
        }
    }

    public boolean func_70094_T()
    {
        return !this.field_71083_bS && super.func_70094_T();
    }

    public boolean func_71066_bF()
    {
        return false;
    }

    public EnumStatus func_71018_a(int p_71018_1_, int p_71018_2_, int p_71018_3_)
    {
        if (!this.field_70170_p.field_72995_K)
        {
            if (this.func_70608_bn() || !this.func_70089_S())
            {
                return EnumStatus.OTHER_PROBLEM;
            }

            if (!this.field_70170_p.field_73011_w.func_76569_d())
            {
                return EnumStatus.NOT_POSSIBLE_HERE;
            }

            if (this.field_70170_p.func_72935_r())
            {
                return EnumStatus.NOT_POSSIBLE_NOW;
            }

            if (Math.abs(this.field_70165_t - (double)p_71018_1_) > 3.0D || Math.abs(this.field_70163_u - (double)p_71018_2_) > 2.0D || Math.abs(this.field_70161_v - (double)p_71018_3_) > 3.0D)
            {
                return EnumStatus.TOO_FAR_AWAY;
            }

            double d0 = 8.0D;
            double d1 = 5.0D;
            List list = this.field_70170_p.func_72872_a(EntityMob.class, AxisAlignedBB.func_72332_a().func_72299_a((double)p_71018_1_ - d0, (double)p_71018_2_ - d1, (double)p_71018_3_ - d0, (double)p_71018_1_ + d0, (double)p_71018_2_ + d1, (double)p_71018_3_ + d0));

            if (!list.isEmpty())
            {
                return EnumStatus.NOT_SAFE;
            }
        }

        // CraftBukkit start
        if (this.getBukkitEntity() instanceof Player)
        {
            Player player = (Player) this.getBukkitEntity();
            org.bukkit.block.Block bed = this.field_70170_p.getWorld().getBlockAt(p_71018_1_, p_71018_2_, p_71018_3_);
            PlayerBedEnterEvent event = new PlayerBedEnterEvent(player, bed);
            this.field_70170_p.getServer().getPluginManager().callEvent(event);

            if (event.isCancelled())
            {
                return EnumStatus.OTHER_PROBLEM;
            }
        }

        // CraftBukkit end
        this.func_70105_a(0.2F, 0.2F);
        this.field_70129_M = 0.2F;

        if (this.field_70170_p.func_72899_e(p_71018_1_, p_71018_2_, p_71018_3_))
        {
            int l = this.field_70170_p.func_72805_g(p_71018_1_, p_71018_2_, p_71018_3_);
            int i1 = BlockBed.func_72217_d(l);
            float f = 0.5F;
            float f1 = 0.5F;

            switch (i1)
            {
                case 0:
                    f1 = 0.9F;
                    break;

                case 1:
                    f = 0.1F;
                    break;

                case 2:
                    f1 = 0.1F;
                    break;

                case 3:
                    f = 0.9F;
            }

            this.func_71013_b(i1);
            this.func_70107_b((double)((float)p_71018_1_ + f), (double)((float)p_71018_2_ + 0.9375F), (double)((float)p_71018_3_ + f1));
        }
        else
        {
            this.func_70107_b((double)((float)p_71018_1_ + 0.5F), (double)((float)p_71018_2_ + 0.9375F), (double)((float)p_71018_3_ + 0.5F));
        }

        this.field_71083_bS = true;
        this.field_71076_b = 0;
        this.field_71081_bT = new ChunkCoordinates(p_71018_1_, p_71018_2_, p_71018_3_);
        this.field_70159_w = this.field_70179_y = this.field_70181_x = 0.0D;

        if (!this.field_70170_p.field_72995_K)
        {
            this.field_70170_p.func_72854_c();
        }

        return EnumStatus.OK;
    }

    private void func_71013_b(int p_71013_1_)
    {
        this.field_71079_bU = 0.0F;
        this.field_71089_bV = 0.0F;

        switch (p_71013_1_)
        {
            case 0:
                this.field_71089_bV = -1.8F;
                break;

            case 1:
                this.field_71079_bU = 1.8F;
                break;

            case 2:
                this.field_71089_bV = 1.8F;
                break;

            case 3:
                this.field_71079_bU = -1.8F;
        }
    }

    public void func_70999_a(boolean p_70999_1_, boolean p_70999_2_, boolean p_70999_3_)
    {
        this.func_70105_a(0.6F, 1.8F);
        this.func_71061_d_();
        ChunkCoordinates chunkcoordinates = this.field_71081_bT;
        ChunkCoordinates chunkcoordinates1 = this.field_71081_bT;

        if (chunkcoordinates != null && this.field_70170_p.func_72798_a(chunkcoordinates.field_71574_a, chunkcoordinates.field_71572_b, chunkcoordinates.field_71573_c) == Block.field_71959_S.field_71990_ca)
        {
            BlockBed.func_72228_a(this.field_70170_p, chunkcoordinates.field_71574_a, chunkcoordinates.field_71572_b, chunkcoordinates.field_71573_c, false);
            chunkcoordinates1 = BlockBed.func_72226_b(this.field_70170_p, chunkcoordinates.field_71574_a, chunkcoordinates.field_71572_b, chunkcoordinates.field_71573_c, 0);

            if (chunkcoordinates1 == null)
            {
                chunkcoordinates1 = new ChunkCoordinates(chunkcoordinates.field_71574_a, chunkcoordinates.field_71572_b + 1, chunkcoordinates.field_71573_c);
            }

            this.func_70107_b((double)((float)chunkcoordinates1.field_71574_a + 0.5F), (double)((float)chunkcoordinates1.field_71572_b + this.field_70129_M + 0.1F), (double)((float)chunkcoordinates1.field_71573_c + 0.5F));
        }

        this.field_71083_bS = false;

        if (!this.field_70170_p.field_72995_K && p_70999_2_)
        {
            this.field_70170_p.func_72854_c();
        }

        // CraftBukkit start
        if (this.getBukkitEntity() instanceof Player)
        {
            Player player = (Player) this.getBukkitEntity();
            org.bukkit.block.Block bed;

            if (chunkcoordinates != null)
            {
                bed = this.field_70170_p.getWorld().getBlockAt(chunkcoordinates.field_71574_a, chunkcoordinates.field_71572_b, chunkcoordinates.field_71573_c);
            }
            else
            {
                bed = this.field_70170_p.getWorld().getBlockAt(player.getLocation());
            }

            PlayerBedLeaveEvent event = new PlayerBedLeaveEvent(player, bed);
            this.field_70170_p.getServer().getPluginManager().callEvent(event);
        }

        // CraftBukkit end

        if (p_70999_1_)
        {
            this.field_71076_b = 0;
        }
        else
        {
            this.field_71076_b = 100;
        }

        if (p_70999_3_)
        {
            this.func_71063_a(this.field_71081_bT, false);
        }
    }

    private boolean func_71065_l()
    {
        return this.field_70170_p.func_72798_a(this.field_71081_bT.field_71574_a, this.field_71081_bT.field_71572_b, this.field_71081_bT.field_71573_c) == Block.field_71959_S.field_71990_ca;
    }

    public static ChunkCoordinates func_71056_a(World p_71056_0_, ChunkCoordinates p_71056_1_, boolean p_71056_2_)
    {
        IChunkProvider ichunkprovider = p_71056_0_.func_72863_F();
        ichunkprovider.func_73158_c(p_71056_1_.field_71574_a - 3 >> 4, p_71056_1_.field_71573_c - 3 >> 4);
        ichunkprovider.func_73158_c(p_71056_1_.field_71574_a + 3 >> 4, p_71056_1_.field_71573_c - 3 >> 4);
        ichunkprovider.func_73158_c(p_71056_1_.field_71574_a - 3 >> 4, p_71056_1_.field_71573_c + 3 >> 4);
        ichunkprovider.func_73158_c(p_71056_1_.field_71574_a + 3 >> 4, p_71056_1_.field_71573_c + 3 >> 4);

        if (p_71056_0_.func_72798_a(p_71056_1_.field_71574_a, p_71056_1_.field_71572_b, p_71056_1_.field_71573_c) == Block.field_71959_S.field_71990_ca)
        {
            ChunkCoordinates chunkcoordinates1 = BlockBed.func_72226_b(p_71056_0_, p_71056_1_.field_71574_a, p_71056_1_.field_71572_b, p_71056_1_.field_71573_c, 0);
            return chunkcoordinates1;
        }
        else
        {
            Material material = p_71056_0_.func_72803_f(p_71056_1_.field_71574_a, p_71056_1_.field_71572_b, p_71056_1_.field_71573_c);
            Material material1 = p_71056_0_.func_72803_f(p_71056_1_.field_71574_a, p_71056_1_.field_71572_b + 1, p_71056_1_.field_71573_c);
            boolean flag1 = !material.func_76220_a() && !material.func_76224_d();
            boolean flag2 = !material1.func_76220_a() && !material1.func_76224_d();
            return p_71056_2_ && flag1 && flag2 ? p_71056_1_ : null;
        }
    }

    public boolean func_70608_bn()
    {
        return this.field_71083_bS;
    }

    public boolean func_71026_bH()
    {
        return this.field_71083_bS && this.field_71076_b >= 100;
    }

    protected void func_82239_b(int p_82239_1_, boolean p_82239_2_)
    {
        byte b0 = this.field_70180_af.func_75683_a(16);

        if (p_82239_2_)
        {
            this.field_70180_af.func_75692_b(16, Byte.valueOf((byte)(b0 | 1 << p_82239_1_)));
        }
        else
        {
            this.field_70180_af.func_75692_b(16, Byte.valueOf((byte)(b0 & ~(1 << p_82239_1_))));
        }
    }

    public void func_71035_c(String p_71035_1_) {}

    public ChunkCoordinates func_70997_bJ()
    {
        return this.field_71077_c;
    }

    public boolean func_82245_bX()
    {
        return this.field_82248_d;
    }

    public void func_71063_a(ChunkCoordinates p_71063_1_, boolean p_71063_2_)
    {
        if (p_71063_1_ != null)
        {
            this.field_71077_c = new ChunkCoordinates(p_71063_1_);
            this.field_82248_d = p_71063_2_;
            this.spawnWorld = this.field_70170_p.field_72986_A.func_76065_j(); // CraftBukkit
        }
        else
        {
            this.field_71077_c = null;
            this.field_82248_d = false;
            this.spawnWorld = ""; // CraftBukkit
        }
    }

    public void func_71029_a(StatBase p_71029_1_)
    {
        this.func_71064_a(p_71029_1_, 1);
    }

    public void func_71064_a(StatBase p_71064_1_, int p_71064_2_) {}

    protected void func_70664_aZ()
    {
        super.func_70664_aZ();
        this.func_71064_a(StatList.field_75953_u, 1);

        if (this.func_70051_ag())
        {
            this.func_71020_j(0.8F);
        }
        else
        {
            this.func_71020_j(0.2F);
        }
    }

    public void func_70612_e(float p_70612_1_, float p_70612_2_)
    {
        double d0 = this.field_70165_t;
        double d1 = this.field_70163_u;
        double d2 = this.field_70161_v;

        if (this.field_71075_bZ.field_75100_b && this.field_70154_o == null)
        {
            double d3 = this.field_70181_x;
            float f2 = this.field_70747_aH;
            this.field_70747_aH = this.field_71075_bZ.func_75093_a();
            super.func_70612_e(p_70612_1_, p_70612_2_);
            this.field_70181_x = d3 * 0.6D;
            this.field_70747_aH = f2;
        }
        else
        {
            super.func_70612_e(p_70612_1_, p_70612_2_);
        }

        this.func_71000_j(this.field_70165_t - d0, this.field_70163_u - d1, this.field_70161_v - d2);
    }

    public void func_71000_j(double p_71000_1_, double p_71000_3_, double p_71000_5_)
    {
        if (this.field_70154_o == null)
        {
            int i;

            if (this.func_70055_a(Material.field_76244_g))
            {
                i = Math.round(MathHelper.func_76133_a(p_71000_1_ * p_71000_1_ + p_71000_3_ * p_71000_3_ + p_71000_5_ * p_71000_5_) * 100.0F);

                if (i > 0)
                {
                    this.func_71064_a(StatList.field_75957_q, i);
                    this.func_71020_j(0.015F * (float)i * 0.01F);
                }
            }
            else if (this.func_70090_H())
            {
                i = Math.round(MathHelper.func_76133_a(p_71000_1_ * p_71000_1_ + p_71000_5_ * p_71000_5_) * 100.0F);

                if (i > 0)
                {
                    this.func_71064_a(StatList.field_75946_m, i);
                    this.func_71020_j(0.015F * (float)i * 0.01F);
                }
            }
            else if (this.func_70617_f_())
            {
                if (p_71000_3_ > 0.0D)
                {
                    this.func_71064_a(StatList.field_75944_o, (int)Math.round(p_71000_3_ * 100.0D));
                }
            }
            else if (this.field_70122_E)
            {
                i = Math.round(MathHelper.func_76133_a(p_71000_1_ * p_71000_1_ + p_71000_5_ * p_71000_5_) * 100.0F);

                if (i > 0)
                {
                    this.func_71064_a(StatList.field_75945_l, i);

                    if (this.func_70051_ag())
                    {
                        this.func_71020_j(0.099999994F * (float)i * 0.01F);
                    }
                    else
                    {
                        this.func_71020_j(0.01F * (float)i * 0.01F);
                    }
                }
            }
            else
            {
                i = Math.round(MathHelper.func_76133_a(p_71000_1_ * p_71000_1_ + p_71000_5_ * p_71000_5_) * 100.0F);

                if (i > 25)
                {
                    this.func_71064_a(StatList.field_75958_p, i);
                }
            }
        }
    }

    private void func_71015_k(double p_71015_1_, double p_71015_3_, double p_71015_5_)
    {
        if (this.field_70154_o != null)
        {
            int i = Math.round(MathHelper.func_76133_a(p_71015_1_ * p_71015_1_ + p_71015_3_ * p_71015_3_ + p_71015_5_ * p_71015_5_) * 100.0F);

            if (i > 0)
            {
                if (this.field_70154_o instanceof EntityMinecart)
                {
                    this.func_71064_a(StatList.field_75956_r, i);

                    if (this.field_71073_d == null)
                    {
                        this.field_71073_d = new ChunkCoordinates(MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c(this.field_70163_u), MathHelper.func_76128_c(this.field_70161_v));
                    }
                    else if ((double)this.field_71073_d.func_71569_e(MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c(this.field_70163_u), MathHelper.func_76128_c(this.field_70161_v)) >= 1000000.0D)
                    {
                        this.func_71064_a((StatBase) AchievementList.field_76025_q, 1);
                    }
                }
                else if (this.field_70154_o instanceof EntityBoat)
                {
                    this.func_71064_a(StatList.field_75955_s, i);
                }
                else if (this.field_70154_o instanceof EntityPig)
                {
                    this.func_71064_a(StatList.field_75954_t, i);
                }
            }
        }
    }

    protected void func_70069_a(float p_70069_1_)
    {
        if (!this.field_71075_bZ.field_75101_c)
        {
            if (p_70069_1_ >= 2.0F)
            {
                this.func_71064_a(StatList.field_75943_n, (int)Math.round((double)p_70069_1_ * 100.0D));
            }

            super.func_70069_a(p_70069_1_);
        }
    }

    public void func_70074_a(EntityLiving p_70074_1_)
    {
        if (p_70074_1_ instanceof IMob)
        {
            this.func_71029_a((StatBase) AchievementList.field_76023_s);
        }
    }

    public void func_70110_aj()
    {
        if (!this.field_71075_bZ.field_75100_b)
        {
            super.func_70110_aj();
        }
    }

    public ItemStack func_82169_q(int p_82169_1_)
    {
        return this.field_71071_by.func_70440_f(p_82169_1_);
    }

    protected void func_82164_bB() {}

    protected void func_82162_bC() {}

    public void func_71023_q(int p_71023_1_)
    {
        this.func_85039_t(p_71023_1_);
        int j = Integer.MAX_VALUE - this.field_71067_cb;

        if (p_71023_1_ > j)
        {
            p_71023_1_ = j;
        }

        this.field_71106_cc += (float)p_71023_1_ / (float)this.func_71050_bK();

        for (this.field_71067_cb += p_71023_1_; this.field_71106_cc >= 1.0F; this.field_71106_cc /= (float)this.func_71050_bK())
        {
            this.field_71106_cc = (this.field_71106_cc - 1.0F) * (float)this.func_71050_bK();
            this.func_82242_a(1);
        }
    }

    public void func_82242_a(int p_82242_1_)
    {
        this.field_71068_ca += p_82242_1_;

        if (this.field_71068_ca < 0)
        {
            this.field_71068_ca = 0;
            this.field_71106_cc = 0.0F;
            this.field_71067_cb = 0;
        }

        if (p_82242_1_ > 0 && this.field_71068_ca % 5 == 0 && (float)this.field_82249_h < (float)this.field_70173_aa - 100.0F)
        {
            float f = this.field_71068_ca > 30 ? 1.0F : (float)this.field_71068_ca / 30.0F;
            this.field_70170_p.func_72956_a(this, "random.levelup", f * 0.75F, 1.0F);
            this.field_82249_h = this.field_70173_aa;
        }
    }

    public int func_71050_bK()
    {
        return this.field_71068_ca >= 30 ? 62 + (this.field_71068_ca - 30) * 7 : (this.field_71068_ca >= 15 ? 17 + (this.field_71068_ca - 15) * 3 : 17);
    }

    public void func_71020_j(float p_71020_1_)
    {
        if (!this.field_71075_bZ.field_75102_a)
        {
            if (!this.field_70170_p.field_72995_K)
            {
                this.field_71100_bB.func_75113_a(p_71020_1_);
            }
        }
    }

    public FoodStats func_71024_bL()
    {
        return this.field_71100_bB;
    }

    public boolean func_71043_e(boolean p_71043_1_)
    {
        return (p_71043_1_ || this.field_71100_bB.func_75121_c()) && !this.field_71075_bZ.field_75102_a;
    }

    public boolean func_70996_bM()
    {
        return this.func_70630_aN() > 0 && this.func_70630_aN() < this.maxHealth; // CraftBukkit - this.getMaxHealth() -> this.maxHealth
    }

    public void func_71008_a(ItemStack p_71008_1_, int p_71008_2_)
    {
        if (p_71008_1_ != this.field_71074_e)
        {
            this.field_71074_e = p_71008_1_;
            this.field_71072_f = p_71008_2_;

            if (!this.field_70170_p.field_72995_K)
            {
                this.func_70019_c(true);
            }
        }
    }

    public boolean func_82246_f(int p_82246_1_, int p_82246_2_, int p_82246_3_)
    {
        if (this.field_71075_bZ.field_75099_e)
        {
            return true;
        }
        else
        {
            int l = this.field_70170_p.func_72798_a(p_82246_1_, p_82246_2_, p_82246_3_);

            if (l > 0)
            {
                Block block = Block.field_71973_m[l];

                if (block.field_72018_cp.func_85157_q())
                {
                    return true;
                }

                if (this.func_71045_bC() != null)
                {
                    ItemStack itemstack = this.func_71045_bC();

                    if (itemstack.func_77987_b(block) || itemstack.func_77967_a(block) > 1.0F)
                    {
                        return true;
                    }
                }
            }

            return false;
        }
    }

    public boolean func_82247_a(int p_82247_1_, int p_82247_2_, int p_82247_3_, int p_82247_4_, ItemStack p_82247_5_)
    {
        return this.field_71075_bZ.field_75099_e ? true : (p_82247_5_ != null ? p_82247_5_.func_82835_x() : false);
    }

    protected int func_70693_a(EntityPlayer p_70693_1_)
    {
        if (this.field_70170_p.func_82736_K().func_82766_b("keepInventory"))
        {
            return 0;
        }
        else
        {
            int i = this.field_71068_ca * 7;
            return i > 100 ? 100 : i;
        }
    }

    protected boolean func_70684_aJ()
    {
        return true;
    }

    public String func_70023_ak()
    {
        return this.field_71092_bJ;
    }

    public boolean func_94062_bN()
    {
        return super.func_94062_bN();
    }

    /* CraftBukkit start - we use canPickUpLoot on players, can't have this
    public boolean bS() {
        return false;
    }
    // CraftBukkit end */

    public void func_71049_a(EntityPlayer p_71049_1_, boolean p_71049_2_)
    {
        if (p_71049_2_)
        {
            this.field_71071_by.func_70455_b(p_71049_1_.field_71071_by);
            this.field_70734_aK = p_71049_1_.field_70734_aK;
            this.field_71100_bB = p_71049_1_.field_71100_bB;
            this.field_71068_ca = p_71049_1_.field_71068_ca;
            this.field_71067_cb = p_71049_1_.field_71067_cb;
            this.field_71106_cc = p_71049_1_.field_71106_cc;
            this.func_85040_s(p_71049_1_.func_71037_bA());
            this.field_82152_aq = p_71049_1_.field_82152_aq;
        }
        else if (this.field_70170_p.func_82736_K().func_82766_b("keepInventory"))
        {
            this.field_71071_by.func_70455_b(p_71049_1_.field_71071_by);
            this.field_71068_ca = p_71049_1_.field_71068_ca;
            this.field_71067_cb = p_71049_1_.field_71067_cb;
            this.field_71106_cc = p_71049_1_.field_71106_cc;
            this.func_85040_s(p_71049_1_.func_71037_bA());
        }

        this.field_71078_a = p_71049_1_.field_71078_a;
    }

    protected boolean func_70041_e_()
    {
        return !this.field_71075_bZ.field_75100_b;
    }

    public void func_71016_p() {}

    public void func_71033_a(EnumGameType p_71033_1_) {}

    public String func_70005_c_()
    {
        return this.field_71092_bJ;
    }

    public StringTranslate func_71025_t()
    {
        return StringTranslate.func_74808_a();
    }

    public String func_70004_a(String p_70004_1_, Object ... p_70004_2_)
    {
        return this.func_71025_t().func_74803_a(p_70004_1_, p_70004_2_);
    }

    public InventoryEnderChest func_71005_bN()
    {
        return this.field_71078_a;
    }

    public ItemStack func_71124_b(int p_71124_1_)
    {
        return p_71124_1_ == 0 ? this.field_71071_by.func_70448_g() : this.field_71071_by.field_70460_b[p_71124_1_ - 1];
    }

    public ItemStack func_70694_bm()
    {
        return this.field_71071_by.func_70448_g();
    }

    public void func_70062_b(int p_70062_1_, ItemStack p_70062_2_)
    {
        this.field_71071_by.field_70460_b[p_70062_1_] = p_70062_2_;
    }

    public ItemStack[] func_70035_c()
    {
        return this.field_71071_by.field_70460_b;
    }

    public boolean func_96092_aw()
    {
        return !this.field_71075_bZ.field_75100_b;
    }

    public Scoreboard func_96123_co()
    {
        return this.field_70170_p.func_96441_U();
    }

    public ScorePlayerTeam func_96124_cp()
    {
        return this.func_96123_co().func_96509_i(this.field_71092_bJ);
    }

    public String func_96090_ax()
    {
        return ScorePlayerTeam.func_96667_a(this.func_96124_cp(), this.field_71092_bJ);
    }
}
