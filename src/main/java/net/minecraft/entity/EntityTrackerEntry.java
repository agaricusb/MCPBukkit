package net.minecraft.entity;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityFallingSand;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet17Sleep;
import net.minecraft.network.packet.Packet20NamedEntitySpawn;
import net.minecraft.network.packet.Packet23VehicleSpawn;
import net.minecraft.network.packet.Packet24MobSpawn;
import net.minecraft.network.packet.Packet25EntityPainting;
import net.minecraft.network.packet.Packet26EntityExpOrb;
import net.minecraft.network.packet.Packet28EntityVelocity;
import net.minecraft.network.packet.Packet31RelEntityMove;
import net.minecraft.network.packet.Packet32EntityLook;
import net.minecraft.network.packet.Packet33RelEntityMoveLook;
import net.minecraft.network.packet.Packet34EntityTeleport;
import net.minecraft.network.packet.Packet35EntityHeadRotation;
import net.minecraft.network.packet.Packet39AttachEntity;
import net.minecraft.network.packet.Packet40EntityMetadata;
import net.minecraft.network.packet.Packet41EntityEffect;
import net.minecraft.network.packet.Packet5PlayerInventory;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.storage.MapData;

// CraftBukkit start
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerVelocityEvent;
// CraftBukkit end

public class EntityTrackerEntry
{
    public Entity field_73132_a;
    public int field_73130_b;
    public int field_73131_c;
    public int field_73128_d;
    public int field_73129_e;
    public int field_73126_f;
    public int field_73127_g;
    public int field_73139_h;
    public int field_73140_i;
    public double field_73137_j;
    public double field_73138_k;
    public double field_73135_l;
    public int field_73136_m = 0;
    private double field_73147_p;
    private double field_73146_q;
    private double field_73145_r;
    private boolean field_73144_s = false;
    private boolean field_73143_t;
    private int field_73142_u = 0;
    private Entity field_85178_v;
    private boolean field_73141_v = false;
    public boolean field_73133_n = false;
    public Set field_73134_o = new HashSet();

    public EntityTrackerEntry(Entity p_i3398_1_, int p_i3398_2_, int p_i3398_3_, boolean p_i3398_4_)
    {
        this.field_73132_a = p_i3398_1_;
        this.field_73130_b = p_i3398_2_;
        this.field_73131_c = p_i3398_3_;
        this.field_73143_t = p_i3398_4_;
        this.field_73128_d = MathHelper.func_76128_c(p_i3398_1_.field_70165_t * 32.0D);
        this.field_73129_e = MathHelper.func_76128_c(p_i3398_1_.field_70163_u * 32.0D);
        this.field_73126_f = MathHelper.func_76128_c(p_i3398_1_.field_70161_v * 32.0D);
        this.field_73127_g = MathHelper.func_76141_d(p_i3398_1_.field_70177_z * 256.0F / 360.0F);
        this.field_73139_h = MathHelper.func_76141_d(p_i3398_1_.field_70125_A * 256.0F / 360.0F);
        this.field_73140_i = MathHelper.func_76141_d(p_i3398_1_.func_70079_am() * 256.0F / 360.0F);
    }

    public boolean equals(Object p_equals_1_)
    {
        return p_equals_1_ instanceof EntityTrackerEntry ? ((EntityTrackerEntry)p_equals_1_).field_73132_a.field_70157_k == this.field_73132_a.field_70157_k : false;
    }

    public int hashCode()
    {
        return this.field_73132_a.field_70157_k;
    }

    public void func_73122_a(List p_73122_1_)
    {
        this.field_73133_n = false;

        if (!this.field_73144_s || this.field_73132_a.func_70092_e(this.field_73147_p, this.field_73146_q, this.field_73145_r) > 16.0D)
        {
            this.field_73147_p = this.field_73132_a.field_70165_t;
            this.field_73146_q = this.field_73132_a.field_70163_u;
            this.field_73145_r = this.field_73132_a.field_70161_v;
            this.field_73144_s = true;
            this.field_73133_n = true;
            this.func_73125_b(p_73122_1_);
        }

        if (this.field_85178_v != this.field_73132_a.field_70154_o)
        {
            this.field_85178_v = this.field_73132_a.field_70154_o;
            this.func_73120_a(new Packet39AttachEntity(this.field_73132_a, this.field_73132_a.field_70154_o));
        }

        if (this.field_73132_a instanceof EntityItemFrame && this.field_73136_m % 10 == 0)
        {
            EntityItemFrame i4 = (EntityItemFrame) this.field_73132_a;
            ItemStack i5 = i4.func_82335_i();

            if (i5 != null && i5.func_77973_b() instanceof ItemMap)
            {
                MapData i7 = Item.field_77744_bd.func_77873_a(i5, this.field_73132_a.field_70170_p);
                Iterator j0 = p_73122_1_.iterator();

                while (j0.hasNext())
                {
                    EntityPlayer j1 = (EntityPlayer) j0.next();
                    EntityPlayerMP j2 = (EntityPlayerMP) j1;
                    i7.func_76191_a(j2, i5);

                    if (j2.field_71135_a.func_72568_e() <= 5)
                    {
                        Packet j3 = Item.field_77744_bd.func_77871_c(i5, this.field_73132_a.field_70170_p, j2);

                        if (j3 != null)
                        {
                            j2.field_71135_a.func_72567_b(j3);
                        }
                    }
                }
            }

            DataWatcher i9 = this.field_73132_a.func_70096_w();

            if (i9.func_75684_a())
            {
                this.func_73116_b(new Packet40EntityMetadata(this.field_73132_a.field_70157_k, i9, false));
            }
        }
        else if (this.field_73136_m++ % this.field_73131_c == 0 || this.field_73132_a.field_70160_al)
        {
            int i;
            int j;

            if (this.field_73132_a.field_70154_o == null)
            {
                ++this.field_73142_u;
                i = this.field_73132_a.field_70168_am.func_75630_a(this.field_73132_a.field_70165_t);
                j = MathHelper.func_76128_c(this.field_73132_a.field_70163_u * 32.0D);
                int k = this.field_73132_a.field_70168_am.func_75630_a(this.field_73132_a.field_70161_v);
                int l = MathHelper.func_76141_d(this.field_73132_a.field_70177_z * 256.0F / 360.0F);
                int i1 = MathHelper.func_76141_d(this.field_73132_a.field_70125_A * 256.0F / 360.0F);
                int j1 = i - this.field_73128_d;
                int k1 = j - this.field_73129_e;
                int l1 = k - this.field_73126_f;
                Object object = null;
                boolean flag = Math.abs(j1) >= 4 || Math.abs(k1) >= 4 || Math.abs(l1) >= 4 || this.field_73136_m % 60 == 0;
                boolean flag1 = Math.abs(l - this.field_73127_g) >= 4 || Math.abs(i1 - this.field_73139_h) >= 4;

                // CraftBukkit start - code moved from below
                if (flag)
                {
                    this.field_73128_d = i;
                    this.field_73129_e = j;
                    this.field_73126_f = k;
                }

                if (flag1)
                {
                    this.field_73127_g = l;
                    this.field_73139_h = i1;
                }

                // CraftBukkit end

                if (j1 >= -128 && j1 < 128 && k1 >= -128 && k1 < 128 && l1 >= -128 && l1 < 128 && this.field_73142_u <= 400 && !this.field_73141_v)
                {
                    if (flag && flag1)
                    {
                        object = new Packet33RelEntityMoveLook(this.field_73132_a.field_70157_k, (byte)j1, (byte)k1, (byte)l1, (byte)l, (byte)i1);
                    }
                    else if (flag)
                    {
                        object = new Packet31RelEntityMove(this.field_73132_a.field_70157_k, (byte)j1, (byte)k1, (byte)l1);
                    }
                    else if (flag1)
                    {
                        object = new Packet32EntityLook(this.field_73132_a.field_70157_k, (byte)l, (byte)i1);
                    }
                }
                else
                {
                    this.field_73142_u = 0;

                    // CraftBukkit start - refresh list of who can see a player before sending teleport packet
                    if (this.field_73132_a instanceof EntityPlayerMP)
                    {
                        this.func_73125_b(new java.util.ArrayList(this.field_73134_o));
                    }

                    // CraftBukkit end
                    object = new Packet34EntityTeleport(this.field_73132_a.field_70157_k, i, j, k, (byte)l, (byte)i1);
                }

                if (this.field_73143_t)
                {
                    double d0 = this.field_73132_a.field_70159_w - this.field_73137_j;
                    double d1 = this.field_73132_a.field_70181_x - this.field_73138_k;
                    double d2 = this.field_73132_a.field_70179_y - this.field_73135_l;
                    double d3 = 0.02D;
                    double d4 = d0 * d0 + d1 * d1 + d2 * d2;

                    if (d4 > d3 * d3 || d4 > 0.0D && this.field_73132_a.field_70159_w == 0.0D && this.field_73132_a.field_70181_x == 0.0D && this.field_73132_a.field_70179_y == 0.0D)
                    {
                        this.field_73137_j = this.field_73132_a.field_70159_w;
                        this.field_73138_k = this.field_73132_a.field_70181_x;
                        this.field_73135_l = this.field_73132_a.field_70179_y;
                        this.func_73120_a(new Packet28EntityVelocity(this.field_73132_a.field_70157_k, this.field_73137_j, this.field_73138_k, this.field_73135_l));
                    }
                }

                if (object != null)
                {
                    this.func_73120_a((Packet)object);
                }

                DataWatcher datawatcher1 = this.field_73132_a.func_70096_w();

                if (datawatcher1.func_75684_a())
                {
                    this.func_73116_b(new Packet40EntityMetadata(this.field_73132_a.field_70157_k, datawatcher1, false));
                }

                /* CraftBukkit start - code moved up
                if (flag) {
                    this.xLoc = i;
                    this.yLoc = j;
                    this.zLoc = k;
                }

                if (flag1) {
                    this.yRot = l;
                    this.xRot = i1;
                }
                // CraftBukkit end */
                this.field_73141_v = false;
            }
            else
            {
                i = MathHelper.func_76141_d(this.field_73132_a.field_70177_z * 256.0F / 360.0F);
                j = MathHelper.func_76141_d(this.field_73132_a.field_70125_A * 256.0F / 360.0F);
                boolean flag2 = Math.abs(i - this.field_73127_g) >= 4 || Math.abs(j - this.field_73139_h) >= 4;

                if (flag2)
                {
                    this.func_73120_a(new Packet32EntityLook(this.field_73132_a.field_70157_k, (byte)i, (byte)j));
                    this.field_73127_g = i;
                    this.field_73139_h = j;
                }

                this.field_73128_d = this.field_73132_a.field_70168_am.func_75630_a(this.field_73132_a.field_70165_t);
                this.field_73129_e = MathHelper.func_76128_c(this.field_73132_a.field_70163_u * 32.0D);
                this.field_73126_f = this.field_73132_a.field_70168_am.func_75630_a(this.field_73132_a.field_70161_v);
                DataWatcher datawatcher2 = this.field_73132_a.func_70096_w();

                if (datawatcher2.func_75684_a())
                {
                    this.func_73116_b(new Packet40EntityMetadata(this.field_73132_a.field_70157_k, datawatcher2, false));
                }

                this.field_73141_v = true;
            }

            i = MathHelper.func_76141_d(this.field_73132_a.func_70079_am() * 256.0F / 360.0F);

            if (Math.abs(i - this.field_73140_i) >= 4)
            {
                this.func_73120_a(new Packet35EntityHeadRotation(this.field_73132_a.field_70157_k, (byte)i));
                this.field_73140_i = i;
            }

            this.field_73132_a.field_70160_al = false;
        }

        if (this.field_73132_a.field_70133_I)
        {
            // CraftBukkit start - create PlayerVelocity event
            boolean cancelled = false;

            if (this.field_73132_a instanceof EntityPlayerMP)
            {
                Player player = (Player) this.field_73132_a.getBukkitEntity();
                org.bukkit.util.Vector velocity = player.getVelocity();
                PlayerVelocityEvent event = new PlayerVelocityEvent(player, velocity);
                this.field_73132_a.field_70170_p.getServer().getPluginManager().callEvent(event);

                if (event.isCancelled())
                {
                    cancelled = true;
                }
                else if (!velocity.equals(event.getVelocity()))
                {
                    player.setVelocity(velocity);
                }
            }

            if (!cancelled)
            {
                this.func_73116_b((Packet)(new Packet28EntityVelocity(this.field_73132_a)));
            }

            // CraftBukkit end
            this.field_73132_a.field_70133_I = false;
        }
    }

    public void func_73120_a(Packet p_73120_1_)
    {
        Iterator iterator = this.field_73134_o.iterator();

        while (iterator.hasNext())
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)iterator.next();
            entityplayermp.field_71135_a.func_72567_b(p_73120_1_);
        }
    }

    public void func_73116_b(Packet p_73116_1_)
    {
        this.func_73120_a(p_73116_1_);

        if (this.field_73132_a instanceof EntityPlayerMP)
        {
            ((EntityPlayerMP)this.field_73132_a).field_71135_a.func_72567_b(p_73116_1_);
        }
    }

    public void func_73119_a()
    {
        Iterator iterator = this.field_73134_o.iterator();

        while (iterator.hasNext())
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)iterator.next();
            entityplayermp.field_71130_g.add(Integer.valueOf(this.field_73132_a.field_70157_k));
        }
    }

    public void func_73118_a(EntityPlayerMP p_73118_1_)
    {
        if (this.field_73134_o.contains(p_73118_1_))
        {
            p_73118_1_.field_71130_g.add(Integer.valueOf(this.field_73132_a.field_70157_k));
            this.field_73134_o.remove(p_73118_1_);
        }
    }

    public void func_73117_b(EntityPlayerMP p_73117_1_)
    {
        if (p_73117_1_ != this.field_73132_a)
        {
            double d0 = p_73117_1_.field_70165_t - (double)(this.field_73128_d / 32);
            double d1 = p_73117_1_.field_70161_v - (double)(this.field_73126_f / 32);

            if (d0 >= (double)(-this.field_73130_b) && d0 <= (double)this.field_73130_b && d1 >= (double)(-this.field_73130_b) && d1 <= (double)this.field_73130_b)
            {
                if (!this.field_73134_o.contains(p_73117_1_) && this.func_73121_d(p_73117_1_))
                {
                    // CraftBukkit start
                    if (this.field_73132_a instanceof EntityPlayerMP)
                    {
                        Player player = ((EntityPlayerMP) this.field_73132_a).getBukkitEntity();

                        if (!p_73117_1_.getBukkitEntity().canSee(player))
                        {
                            return;
                        }
                    }

                    p_73117_1_.field_71130_g.remove(Integer.valueOf(this.field_73132_a.field_70157_k));
                    // CraftBukkit end
                    this.field_73134_o.add(p_73117_1_);
                    Packet packet = this.func_73124_b();
                    p_73117_1_.field_71135_a.func_72567_b(packet);

                    if (!this.field_73132_a.func_70096_w().func_92085_d())
                    {
                        p_73117_1_.field_71135_a.func_72567_b(new Packet40EntityMetadata(this.field_73132_a.field_70157_k, this.field_73132_a.func_70096_w(), true));
                    }

                    this.field_73137_j = this.field_73132_a.field_70159_w;
                    this.field_73138_k = this.field_73132_a.field_70181_x;
                    this.field_73135_l = this.field_73132_a.field_70179_y;

                    if (this.field_73143_t && !(packet instanceof Packet24MobSpawn))
                    {
                        p_73117_1_.field_71135_a.func_72567_b(new Packet28EntityVelocity(this.field_73132_a.field_70157_k, this.field_73132_a.field_70159_w, this.field_73132_a.field_70181_x, this.field_73132_a.field_70179_y));
                    }

                    if (this.field_73132_a.field_70154_o != null)
                    {
                        p_73117_1_.field_71135_a.func_72567_b(new Packet39AttachEntity(this.field_73132_a, this.field_73132_a.field_70154_o));
                    }

                    if (this.field_73132_a instanceof EntityLiving)
                    {
                        for (int i = 0; i < 5; ++i)
                        {
                            ItemStack itemstack = ((EntityLiving)this.field_73132_a).func_71124_b(i);

                            if (itemstack != null)
                            {
                                p_73117_1_.field_71135_a.func_72567_b(new Packet5PlayerInventory(this.field_73132_a.field_70157_k, i, itemstack));
                            }
                        }
                    }

                    if (this.field_73132_a instanceof EntityPlayer)
                    {
                        EntityPlayer entityplayer = (EntityPlayer)this.field_73132_a;

                        if (entityplayer.func_70608_bn())
                        {
                            p_73117_1_.field_71135_a.func_72567_b(new Packet17Sleep(this.field_73132_a, 0, MathHelper.func_76128_c(this.field_73132_a.field_70165_t), MathHelper.func_76128_c(this.field_73132_a.field_70163_u), MathHelper.func_76128_c(this.field_73132_a.field_70161_v)));
                        }
                    }

                    // CraftBukkit start - Fix for nonsensical head yaw
                    this.field_73140_i = MathHelper.func_76141_d(this.field_73132_a.func_70079_am() * 256.0F / 360.0F); // tracker.am() should be getHeadRotation
                    this.func_73120_a(new Packet35EntityHeadRotation(this.field_73132_a.field_70157_k, (byte) field_73140_i));
                    // CraftBukkit end

                    if (this.field_73132_a instanceof EntityLiving)
                    {
                        EntityLiving entityliving = (EntityLiving)this.field_73132_a;
                        Iterator iterator = entityliving.func_70651_bq().iterator();

                        while (iterator.hasNext())
                        {
                            PotionEffect potioneffect = (PotionEffect)iterator.next();
                            p_73117_1_.field_71135_a.func_72567_b(new Packet41EntityEffect(this.field_73132_a.field_70157_k, potioneffect));
                        }
                    }
                }
            }
            else if (this.field_73134_o.contains(p_73117_1_))
            {
                this.field_73134_o.remove(p_73117_1_);
                p_73117_1_.field_71130_g.add(Integer.valueOf(this.field_73132_a.field_70157_k));
            }
        }
    }

    private boolean func_73121_d(EntityPlayerMP p_73121_1_)
    {
        return p_73121_1_.func_71121_q().func_73040_p().func_72694_a(p_73121_1_, this.field_73132_a.field_70176_ah, this.field_73132_a.field_70164_aj);
    }

    public void func_73125_b(List p_73125_1_)
    {
        for (int i = 0; i < p_73125_1_.size(); ++i)
        {
            this.func_73117_b((EntityPlayerMP)p_73125_1_.get(i));
        }
    }

    private Packet func_73124_b()
    {
        if (this.field_73132_a.field_70128_L)
        {
            // CraftBukkit start - remove useless error spam, just return
            // System.out.println("Fetching addPacket for removed entity");
            return null;
            // CraftBukkit end
        }

        if (this.field_73132_a instanceof EntityItem)
        {
            return new Packet23VehicleSpawn(this.field_73132_a, 2, 1);
        }
        else if (this.field_73132_a instanceof EntityPlayerMP)
        {
            return new Packet20NamedEntitySpawn((EntityPlayer)this.field_73132_a);
        }
        else
        {
            if (this.field_73132_a instanceof EntityMinecart)
            {
                EntityMinecart entityminecart = (EntityMinecart)this.field_73132_a;

                if (entityminecart.field_70505_a == 0)
                {
                    return new Packet23VehicleSpawn(this.field_73132_a, 10);
                }

                if (entityminecart.field_70505_a == 1)
                {
                    return new Packet23VehicleSpawn(this.field_73132_a, 11);
                }

                if (entityminecart.field_70505_a == 2)
                {
                    return new Packet23VehicleSpawn(this.field_73132_a, 12);
                }
            }

            if (this.field_73132_a instanceof EntityBoat)
            {
                return new Packet23VehicleSpawn(this.field_73132_a, 1);
            }
            else if (!(this.field_73132_a instanceof IAnimals) && !(this.field_73132_a instanceof EntityDragon))
            {
                if (this.field_73132_a instanceof EntityFishHook)
                {
                    EntityPlayer entityplayer = ((EntityFishHook)this.field_73132_a).field_70204_b;
                    return new Packet23VehicleSpawn(this.field_73132_a, 90, entityplayer != null ? entityplayer.field_70157_k : this.field_73132_a.field_70157_k);
                }
                else if (this.field_73132_a instanceof EntityArrow)
                {
                    Entity entity = ((EntityArrow)this.field_73132_a).field_70250_c;
                    return new Packet23VehicleSpawn(this.field_73132_a, 60, entity != null ? entity.field_70157_k : this.field_73132_a.field_70157_k);
                }
                else if (this.field_73132_a instanceof EntitySnowball)
                {
                    return new Packet23VehicleSpawn(this.field_73132_a, 61);
                }
                else if (this.field_73132_a instanceof EntityPotion)
                {
                    return new Packet23VehicleSpawn(this.field_73132_a, 73, ((EntityPotion)this.field_73132_a).func_70196_i());
                }
                else if (this.field_73132_a instanceof EntityExpBottle)
                {
                    return new Packet23VehicleSpawn(this.field_73132_a, 75);
                }
                else if (this.field_73132_a instanceof EntityEnderPearl)
                {
                    return new Packet23VehicleSpawn(this.field_73132_a, 65);
                }
                else if (this.field_73132_a instanceof EntityEnderEye)
                {
                    return new Packet23VehicleSpawn(this.field_73132_a, 72);
                }
                else if (this.field_73132_a instanceof EntityFireworkRocket)
                {
                    return new Packet23VehicleSpawn(this.field_73132_a, 76);
                }
                else
                {
                    Packet23VehicleSpawn packet23vehiclespawn;

                    if (this.field_73132_a instanceof EntityFireball)
                    {
                        EntityFireball entityfireball = (EntityFireball)this.field_73132_a;
                        packet23vehiclespawn = null;
                        byte b0 = 63;

                        if (this.field_73132_a instanceof EntitySmallFireball)
                        {
                            b0 = 64;
                        }
                        else if (this.field_73132_a instanceof EntityWitherSkull)
                        {
                            b0 = 66;
                        }

                        if (entityfireball.field_70235_a != null)
                        {
                            packet23vehiclespawn = new Packet23VehicleSpawn(this.field_73132_a, b0, ((EntityFireball)this.field_73132_a).field_70235_a.field_70157_k);
                        }
                        else
                        {
                            packet23vehiclespawn = new Packet23VehicleSpawn(this.field_73132_a, b0, 0);
                        }

                        packet23vehiclespawn.field_73523_e = (int)(entityfireball.field_70232_b * 8000.0D);
                        packet23vehiclespawn.field_73520_f = (int)(entityfireball.field_70233_c * 8000.0D);
                        packet23vehiclespawn.field_73521_g = (int)(entityfireball.field_70230_d * 8000.0D);
                        return packet23vehiclespawn;
                    }
                    else if (this.field_73132_a instanceof EntityEgg)
                    {
                        return new Packet23VehicleSpawn(this.field_73132_a, 62);
                    }
                    else if (this.field_73132_a instanceof EntityTNTPrimed)
                    {
                        return new Packet23VehicleSpawn(this.field_73132_a, 50);
                    }
                    else if (this.field_73132_a instanceof EntityEnderCrystal)
                    {
                        return new Packet23VehicleSpawn(this.field_73132_a, 51);
                    }
                    else if (this.field_73132_a instanceof EntityFallingSand)
                    {
                        EntityFallingSand entityfallingsand = (EntityFallingSand)this.field_73132_a;
                        return new Packet23VehicleSpawn(this.field_73132_a, 70, entityfallingsand.field_70287_a | entityfallingsand.field_70285_b << 16);
                    }
                    else if (this.field_73132_a instanceof EntityPainting)
                    {
                        return new Packet25EntityPainting((EntityPainting)this.field_73132_a);
                    }
                    else if (this.field_73132_a instanceof EntityItemFrame)
                    {
                        EntityItemFrame entityitemframe = (EntityItemFrame)this.field_73132_a;
                        packet23vehiclespawn = new Packet23VehicleSpawn(this.field_73132_a, 71, entityitemframe.field_82332_a);
                        packet23vehiclespawn.field_73524_b = MathHelper.func_76141_d((float)(entityitemframe.field_70523_b * 32));
                        packet23vehiclespawn.field_73525_c = MathHelper.func_76141_d((float)(entityitemframe.field_70524_c * 32));
                        packet23vehiclespawn.field_73522_d = MathHelper.func_76141_d((float)(entityitemframe.field_70521_d * 32));
                        return packet23vehiclespawn;
                    }
                    else if (this.field_73132_a instanceof EntityXPOrb)
                    {
                        return new Packet26EntityExpOrb((EntityXPOrb)this.field_73132_a);
                    }
                    else
                    {
                        throw new IllegalArgumentException("Don\'t know how to add " + this.field_73132_a.getClass() + "!");
                    }
                }
            }
            else
            {
                this.field_73140_i = MathHelper.func_76141_d(this.field_73132_a.func_70079_am() * 256.0F / 360.0F);
                return new Packet24MobSpawn((EntityLiving)this.field_73132_a);
            }
        }
    }

    public void func_73123_c(EntityPlayerMP p_73123_1_)
    {
        if (this.field_73134_o.contains(p_73123_1_))
        {
            this.field_73134_o.remove(p_73123_1_);
            p_73123_1_.field_71130_g.add(Integer.valueOf(this.field_73132_a.field_70157_k));
        }
    }
}
