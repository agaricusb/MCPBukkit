package net.minecraft.entity;

// CraftBukkit start
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.event.entity.EntityTargetEvent;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
// CraftBukkit end

public abstract class EntityCreature extends EntityLiving
{
    public PathEntity field_70786_d; // CraftBukkit - private -> public
    public Entity field_70789_a; // CraftBukkit - protected -> public
    protected boolean field_70787_b = false;
    protected int field_70788_c = 0;

    public EntityCreature(World p_i3450_1_)
    {
        super(p_i3450_1_);
    }

    protected boolean func_70780_i()
    {
        return false;
    }

    protected void func_70626_be()
    {
        this.field_70170_p.field_72984_F.func_76320_a("ai");

        if (this.field_70788_c > 0)
        {
            --this.field_70788_c;
        }

        this.field_70787_b = this.func_70780_i();
        float f = 16.0F;

        if (this.field_70789_a == null)
        {
            // CraftBukkit start
            Entity target = this.func_70782_k();

            if (target != null)
            {
                EntityTargetEvent event = new EntityTargetEvent(this.getBukkitEntity(), target.getBukkitEntity(), EntityTargetEvent.TargetReason.CLOSEST_PLAYER);
                this.field_70170_p.getServer().getPluginManager().callEvent(event);

                if (!event.isCancelled())
                {
                    if (event.getTarget() == null)
                    {
                        this.field_70789_a = null;
                    }
                    else
                    {
                        this.field_70789_a = ((CraftEntity) event.getTarget()).getHandle();
                    }
                }
            }

            // CraftBukkit end

            if (this.field_70789_a != null)
            {
                this.field_70786_d = this.field_70170_p.func_72865_a(this, this.field_70789_a, f, true, false, false, true);
            }
        }
        else if (this.field_70789_a.func_70089_S())
        {
            float f1 = this.field_70789_a.func_70032_d((Entity) this);

            if (this.func_70685_l(this.field_70789_a))
            {
                this.func_70785_a(this.field_70789_a, f1);
            }
        }
        else
        {
            // CraftBukkit start
            EntityTargetEvent event = new EntityTargetEvent(this.getBukkitEntity(), null, EntityTargetEvent.TargetReason.TARGET_DIED);
            this.field_70170_p.getServer().getPluginManager().callEvent(event);

            if (!event.isCancelled())
            {
                if (event.getTarget() == null)
                {
                    this.field_70789_a = null;
                }
                else
                {
                    this.field_70789_a = ((CraftEntity) event.getTarget()).getHandle();
                }
            }

            // CraftBukkit end
        }

        this.field_70170_p.field_72984_F.func_76319_b();

        if (!this.field_70787_b && this.field_70789_a != null && (this.field_70786_d == null || this.field_70146_Z.nextInt(20) == 0))
        {
            this.field_70786_d = this.field_70170_p.func_72865_a(this, this.field_70789_a, f, true, false, false, true);
        }
        else if (!this.field_70787_b && (this.field_70786_d == null && this.field_70146_Z.nextInt(180) == 0 || this.field_70146_Z.nextInt(120) == 0 || this.field_70788_c > 0) && this.field_70708_bq < 100)
        {
            this.func_70779_j();
        }

        int i = MathHelper.func_76128_c(this.field_70121_D.field_72338_b + 0.5D);
        boolean flag = this.func_70090_H();
        boolean flag1 = this.func_70058_J();
        this.field_70125_A = 0.0F;

        if (this.field_70786_d != null && this.field_70146_Z.nextInt(100) != 0)
        {
            this.field_70170_p.field_72984_F.func_76320_a("followpath");
            Vec3 vec3 = this.field_70786_d.func_75878_a((Entity) this);
            double d0 = (double)(this.field_70130_N * 2.0F);

            while (vec3 != null && vec3.func_72445_d(this.field_70165_t, vec3.field_72448_b, this.field_70161_v) < d0 * d0)
            {
                this.field_70786_d.func_75875_a();

                if (this.field_70786_d.func_75879_b())
                {
                    vec3 = null;
                    this.field_70786_d = null;
                }
                else
                {
                    vec3 = this.field_70786_d.func_75878_a((Entity) this);
                }
            }

            this.field_70703_bu = false;

            if (vec3 != null)
            {
                double d1 = vec3.field_72450_a - this.field_70165_t;
                double d2 = vec3.field_72449_c - this.field_70161_v;
                double d3 = vec3.field_72448_b - (double)i;
                // CraftBukkit - Math -> TrigMath
                float f2 = (float)(org.bukkit.craftbukkit.TrigMath.atan2(d2, d1) * 180.0D / Math.PI) - 90.0F;
                float f3 = MathHelper.func_76142_g(f2 - this.field_70177_z);
                this.field_70701_bs = this.field_70697_bw;

                if (f3 > 30.0F)
                {
                    f3 = 30.0F;
                }

                if (f3 < -30.0F)
                {
                    f3 = -30.0F;
                }

                this.field_70177_z += f3;

                if (this.field_70787_b && this.field_70789_a != null)
                {
                    double d4 = this.field_70789_a.field_70165_t - this.field_70165_t;
                    double d5 = this.field_70789_a.field_70161_v - this.field_70161_v;
                    float f4 = this.field_70177_z;
                    this.field_70177_z = (float)(Math.atan2(d5, d4) * 180.0D / Math.PI) - 90.0F;
                    f3 = (f4 - this.field_70177_z + 90.0F) * (float)Math.PI / 180.0F;
                    this.field_70702_br = -MathHelper.func_76126_a(f3) * this.field_70701_bs * 1.0F;
                    this.field_70701_bs = MathHelper.func_76134_b(f3) * this.field_70701_bs * 1.0F;
                }

                if (d3 > 0.0D)
                {
                    this.field_70703_bu = true;
                }
            }

            if (this.field_70789_a != null)
            {
                this.func_70625_a(this.field_70789_a, 30.0F, 30.0F);
            }

            if (this.field_70123_F && !this.func_70781_l())
            {
                this.field_70703_bu = true;
            }

            if (this.field_70146_Z.nextFloat() < 0.8F && (flag || flag1))
            {
                this.field_70703_bu = true;
            }

            this.field_70170_p.field_72984_F.func_76319_b();
        }
        else
        {
            super.func_70626_be();
            this.field_70786_d = null;
        }
    }

    protected void func_70779_j()
    {
        this.field_70170_p.field_72984_F.func_76320_a("stroll");
        boolean flag = false;
        int i = -1;
        int j = -1;
        int k = -1;
        float f = -99999.0F;

        for (int l = 0; l < 10; ++l)
        {
            int i1 = MathHelper.func_76128_c(this.field_70165_t + (double)this.field_70146_Z.nextInt(13) - 6.0D);
            int j1 = MathHelper.func_76128_c(this.field_70163_u + (double)this.field_70146_Z.nextInt(7) - 3.0D);
            int k1 = MathHelper.func_76128_c(this.field_70161_v + (double)this.field_70146_Z.nextInt(13) - 6.0D);
            float f1 = this.func_70783_a(i1, j1, k1);

            if (f1 > f)
            {
                f = f1;
                i = i1;
                j = j1;
                k = k1;
                flag = true;
            }
        }

        if (flag)
        {
            this.field_70786_d = this.field_70170_p.func_72844_a(this, i, j, k, 10.0F, true, false, false, true);
        }

        this.field_70170_p.field_72984_F.func_76319_b();
    }

    protected void func_70785_a(Entity p_70785_1_, float p_70785_2_) {}

    public float func_70783_a(int p_70783_1_, int p_70783_2_, int p_70783_3_)
    {
        return 0.0F;
    }

    protected Entity func_70782_k()
    {
        return null;
    }

    public boolean func_70601_bi()
    {
        int i = MathHelper.func_76128_c(this.field_70165_t);
        int j = MathHelper.func_76128_c(this.field_70121_D.field_72338_b);
        int k = MathHelper.func_76128_c(this.field_70161_v);
        return super.func_70601_bi() && this.func_70783_a(i, j, k) >= 0.0F;
    }

    public boolean func_70781_l()
    {
        return this.field_70786_d != null;
    }

    public void func_70778_a(PathEntity p_70778_1_)
    {
        this.field_70786_d = p_70778_1_;
    }

    public Entity func_70777_m()
    {
        return this.field_70789_a;
    }

    public void func_70784_b(Entity p_70784_1_)
    {
        this.field_70789_a = p_70784_1_;
    }

    public float func_70616_bs()
    {
        float f = super.func_70616_bs();

        if (this.field_70788_c > 0 && !this.func_70650_aV())
        {
            f *= 2.0F;
        }

        return f;
    }
}
