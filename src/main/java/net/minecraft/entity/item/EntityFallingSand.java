package net.minecraft.entity.item;

import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

//CraftBukkit start
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.event.entity.EntityDamageEvent;
// CraftBukkit end

public class EntityFallingSand extends Entity
{
    public int field_70287_a;
    public int field_70285_b;
    public int field_70286_c;
    public boolean field_70284_d;
    private boolean field_82157_e;
    private boolean field_82155_f;
    private int field_82156_g;
    private float field_82158_h;
    private NBTTagCompound tileEntityData; // CraftBukkit

    public EntityFallingSand(World p_i3536_1_)
    {
        super(p_i3536_1_);
        this.field_70286_c = 0;
        this.field_70284_d = true;
        this.field_82157_e = false;
        this.field_82155_f = false;
        this.field_82156_g = 40;
        this.field_82158_h = 2.0F;
    }

    public EntityFallingSand(World p_i3537_1_, double p_i3537_2_, double p_i3537_4_, double p_i3537_6_, int p_i3537_8_)
    {
        this(p_i3537_1_, p_i3537_2_, p_i3537_4_, p_i3537_6_, p_i3537_8_, 0);
    }

    public EntityFallingSand(World p_i3538_1_, double p_i3538_2_, double p_i3538_4_, double p_i3538_6_, int p_i3538_8_, int p_i3538_9_)
    {
        super(p_i3538_1_);
        this.field_70286_c = 0;
        this.field_70284_d = true;
        this.field_82157_e = false;
        this.field_82155_f = false;
        this.field_82156_g = 40;
        this.field_82158_h = 2.0F;
        this.field_70287_a = p_i3538_8_;
        this.field_70285_b = p_i3538_9_;
        this.field_70156_m = true;
        this.func_70105_a(0.98F, 0.98F);
        this.field_70129_M = this.field_70131_O / 2.0F;
        this.func_70107_b(p_i3538_2_, p_i3538_4_, p_i3538_6_);
        this.field_70159_w = 0.0D;
        this.field_70181_x = 0.0D;
        this.field_70179_y = 0.0D;
        this.field_70169_q = p_i3538_2_;
        this.field_70167_r = p_i3538_4_;
        this.field_70166_s = p_i3538_6_;
    }

    protected boolean func_70041_e_()
    {
        return false;
    }

    protected void func_70088_a() {}

    public boolean func_70067_L()
    {
        return !this.field_70128_L;
    }

    public void func_70071_h_()
    {
        if (this.field_70287_a == 0)
        {
            this.func_70106_y();
        }
        else
        {
            this.field_70169_q = this.field_70165_t;
            this.field_70167_r = this.field_70163_u;
            this.field_70166_s = this.field_70161_v;
            ++this.field_70286_c;
            this.field_70181_x -= 0.03999999910593033D;
            this.func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
            this.field_70159_w *= 0.9800000190734863D;
            this.field_70181_x *= 0.9800000190734863D;
            this.field_70179_y *= 0.9800000190734863D;

            if (!this.field_70170_p.field_72995_K)
            {
                int i = MathHelper.func_76128_c(this.field_70165_t);
                int j = MathHelper.func_76128_c(this.field_70163_u);
                int k = MathHelper.func_76128_c(this.field_70161_v);

                if (this.field_70286_c == 1)
                {
                    // CraftBukkit - compare data and call event
                    if (this.field_70286_c != 1 || this.field_70170_p.func_72798_a(i, j, k) != this.field_70287_a || this.field_70170_p.func_72805_g(i, j, k) != this.field_70285_b || CraftEventFactory.callEntityChangeBlockEvent(this, i, j, k, 0, 0).isCancelled())
                    {
                        this.func_70106_y();
                        return;
                    }

                    // CraftBukkit start - Store the block tile entity with this entity
                    TileEntity tile = this.field_70170_p.func_72796_p(i, j, k);

                    if (tile != null)
                    {
                        tileEntityData = new NBTTagCompound();
                        // Save the data
                        tile.func_70310_b(tileEntityData);
                        // Remove the existing tile entity
                        this.field_70170_p.func_72932_q(i, j, k);
                    }

                    // CraftBukkit end
                    this.field_70170_p.func_72859_e(i, j, k, 0);
                }

                if (this.field_70122_E)
                {
                    this.field_70159_w *= 0.699999988079071D;
                    this.field_70179_y *= 0.699999988079071D;
                    this.field_70181_x *= -0.5D;

                    if (this.field_70170_p.func_72798_a(i, j, k) != Block.field_72095_ac.field_71990_ca)
                    {
                        this.func_70106_y();

                        // CraftBukkit start
                        if (!this.field_82157_e && this.field_70170_p.func_72931_a(this.field_70287_a, i, j, k, true, 1, (Entity) null) && !BlockSand.func_72191_e_(this.field_70170_p, i, j - 1, k) /* mimic the false conditions of setTypeIdAndData */ && i >= -30000000 && k >= -30000000 && i < 30000000 && k < 30000000 && j > 0 && j < 256 && !(this.field_70170_p.func_72798_a(i, j, k) == this.field_70287_a && this.field_70170_p.func_72805_g(i, j, k) == this.field_70285_b))
                        {
                            if (CraftEventFactory.callEntityChangeBlockEvent(this, i, j, k, this.field_70287_a, this.field_70285_b).isCancelled())
                            {
                                return;
                            }

                            this.field_70170_p.func_72832_d(i, j, k, this.field_70287_a, this.field_70285_b);

                            if (this.tileEntityData != null)
                            {
                                this.field_70170_p.func_72837_a(i, j, k, TileEntity.func_70317_c(this.tileEntityData));
                            }

                            // CraftBukkit end
                            if (Block.field_71973_m[this.field_70287_a] instanceof BlockSand)
                            {
                                ((BlockSand)Block.field_71973_m[this.field_70287_a]).func_82519_a_(this.field_70170_p, i, j, k, this.field_70285_b);
                            }
                        }
                        else if (this.field_70284_d && !this.field_82157_e)
                        {
                            this.func_70099_a(new ItemStack(this.field_70287_a, 1, Block.field_71973_m[this.field_70287_a].func_71899_b(this.field_70285_b)), 0.0F);
                        }
                    }
                }
                else if (this.field_70286_c > 100 && !this.field_70170_p.field_72995_K && (j < 1 || j > 256) || this.field_70286_c > 600)
                {
                    if (this.field_70284_d)
                    {
                        this.func_70099_a(new ItemStack(this.field_70287_a, 1, Block.field_71973_m[this.field_70287_a].func_71899_b(this.field_70285_b)), 0.0F);
                    }

                    this.func_70106_y();
                }
            }
        }
    }

    protected void func_70069_a(float p_70069_1_)
    {
        if (this.field_82155_f)
        {
            int i = MathHelper.func_76123_f(p_70069_1_ - 1.0F);

            if (i > 0)
            {
                ArrayList arraylist = new ArrayList(this.field_70170_p.func_72839_b(this, this.field_70121_D));
                DamageSource damagesource = this.field_70287_a == Block.field_82510_ck.field_71990_ca ? DamageSource.field_82728_o : DamageSource.field_82729_p;
                Iterator iterator = arraylist.iterator();

                while (iterator.hasNext())
                {
                    Entity entity = (Entity)iterator.next();
                    // CraftBukkit start
                    int damage = Math.min(MathHelper.func_76141_d((float) i * this.field_82158_h), this.field_82156_g);
                    EntityDamageEvent event = CraftEventFactory.callEntityDamageEvent(this, entity, EntityDamageEvent.DamageCause.FALLING_BLOCK, damage);

                    if (event.isCancelled())
                    {
                        continue;
                    }

                    entity.func_70097_a(damagesource, event.getDamage());
                    // CraftBukkit end
                }

                if (this.field_70287_a == Block.field_82510_ck.field_71990_ca && (double)this.field_70146_Z.nextFloat() < 0.05000000074505806D + (double)i * 0.05D)
                {
                    int j = this.field_70285_b >> 2;
                    int k = this.field_70285_b & 3;
                    ++j;

                    if (j > 2)
                    {
                        this.field_82157_e = true;
                    }
                    else
                    {
                        this.field_70285_b = k | j << 2;
                    }
                }
            }
        }
    }

    protected void func_70014_b(NBTTagCompound p_70014_1_)
    {
        p_70014_1_.func_74774_a("Tile", (byte)this.field_70287_a);
        p_70014_1_.func_74774_a("Data", (byte)this.field_70285_b);
        p_70014_1_.func_74774_a("Time", (byte)this.field_70286_c);
        p_70014_1_.func_74757_a("DropItem", this.field_70284_d);
        p_70014_1_.func_74757_a("HurtEntities", this.field_82155_f);
        p_70014_1_.func_74776_a("FallHurtAmount", this.field_82158_h);
        p_70014_1_.func_74768_a("FallHurtMax", this.field_82156_g);

        // CraftBukkit start - store the tile data
        if (this.tileEntityData != null)
        {
            p_70014_1_.func_74782_a("Bukkit.tileData", this.tileEntityData.func_74737_b());
        }

        // CraftBukkit end
    }

    protected void func_70037_a(NBTTagCompound p_70037_1_)
    {
        this.field_70287_a = p_70037_1_.func_74771_c("Tile") & 255;
        this.field_70285_b = p_70037_1_.func_74771_c("Data") & 255;
        this.field_70286_c = p_70037_1_.func_74771_c("Time") & 255;

        if (p_70037_1_.func_74764_b("HurtEntities"))
        {
            this.field_82155_f = p_70037_1_.func_74767_n("HurtEntities");
            this.field_82158_h = p_70037_1_.func_74760_g("FallHurtAmount");
            this.field_82156_g = p_70037_1_.func_74762_e("FallHurtMax");
        }
        else if (this.field_70287_a == Block.field_82510_ck.field_71990_ca)
        {
            this.field_82155_f = true;
        }

        // CraftBukkit start - load tileData
        if (p_70037_1_.func_74764_b("Bukkit.tileData"))
        {
            this.tileEntityData = (NBTTagCompound) p_70037_1_.func_74775_l("Bukkit.tileData").func_74737_b();
        }

        // CraftBukkit end

        if (p_70037_1_.func_74764_b("DropItem"))
        {
            this.field_70284_d = p_70037_1_.func_74767_n("DropItem");
        }

        if (this.field_70287_a == 0)
        {
            this.field_70287_a = Block.field_71939_E.field_71990_ca;
        }
    }

    public void func_82154_e(boolean p_82154_1_)
    {
        this.field_82155_f = p_82154_1_;
    }

    public void func_85029_a(CrashReportCategory p_85029_1_)
    {
        super.func_85029_a(p_85029_1_);
        p_85029_1_.func_71507_a("Immitating block ID", Integer.valueOf(this.field_70287_a));
        p_85029_1_.func_71507_a("Immitating block data", Integer.valueOf(this.field_70285_b));
    }
}
