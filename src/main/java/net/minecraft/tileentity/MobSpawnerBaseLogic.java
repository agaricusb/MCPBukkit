package net.minecraft.tileentity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;

import org.bukkit.event.entity.CreatureSpawnEvent; // CraftBukkit

public abstract class MobSpawnerBaseLogic
{
    public int field_98286_b = 20;
    private String field_98288_a = "Pig";
    private List field_98285_e = null;
    private WeightedRandomMinecart field_98282_f = null;
    public double field_98287_c;
    public double field_98284_d = 0.0D;
    private int field_98283_g = 200;
    private int field_98293_h = 800;
    private int field_98294_i = 4;
    private Entity field_98291_j;
    private int field_98292_k = 6;
    private int field_98289_l = 16;
    private int field_98290_m = 4;

    public MobSpawnerBaseLogic() {}

    public String func_98276_e()
    {
        if (this.func_98269_i() == null)
        {
            if (this.field_98288_a.equals("Minecart"))
            {
                this.field_98288_a = "MinecartRideable";
            }

            return this.field_98288_a;
        }
        else
        {
            return this.func_98269_i().field_98223_c;
        }
    }

    public void func_98272_a(String p_98272_1_)
    {
        this.field_98288_a = p_98272_1_;
    }

    public boolean func_98279_f()
    {
        return this.func_98271_a().func_72977_a((double)this.func_98275_b() + 0.5D, (double)this.func_98274_c() + 0.5D, (double)this.func_98266_d() + 0.5D, (double)this.field_98289_l) != null;
    }

    public void func_98278_g()
    {
        if (this.func_98279_f())
        {
            double d0;

            if (this.func_98271_a().field_72995_K)
            {
                double d1 = (double)((float)this.func_98275_b() + this.func_98271_a().field_73012_v.nextFloat());
                double d2 = (double)((float)this.func_98274_c() + this.func_98271_a().field_73012_v.nextFloat());
                d0 = (double)((float)this.func_98266_d() + this.func_98271_a().field_73012_v.nextFloat());
                this.func_98271_a().func_72869_a("smoke", d1, d2, d0, 0.0D, 0.0D, 0.0D);
                this.func_98271_a().func_72869_a("flame", d1, d2, d0, 0.0D, 0.0D, 0.0D);

                if (this.field_98286_b > 0)
                {
                    --this.field_98286_b;
                }

                this.field_98284_d = this.field_98287_c;
                this.field_98287_c = (this.field_98287_c + (double)(1000.0F / ((float)this.field_98286_b + 200.0F))) % 360.0D;
            }
            else
            {
                if (this.field_98286_b == -1)
                {
                    this.func_98273_j();
                }

                if (this.field_98286_b > 0)
                {
                    --this.field_98286_b;
                    return;
                }

                boolean flag = false;

                for (int i = 0; i < this.field_98294_i; ++i)
                {
                    Entity entity = EntityList.func_75620_a(this.func_98276_e(), this.func_98271_a());

                    if (entity == null)
                    {
                        return;
                    }

                    int j = this.func_98271_a().func_72872_a(entity.getClass(), AxisAlignedBB.func_72332_a().func_72299_a((double)this.func_98275_b(), (double)this.func_98274_c(), (double)this.func_98266_d(), (double)(this.func_98275_b() + 1), (double)(this.func_98274_c() + 1), (double)(this.func_98266_d() + 1)).func_72314_b((double)(this.field_98290_m * 2), 4.0D, (double)(this.field_98290_m * 2))).size();

                    if (j >= this.field_98292_k)
                    {
                        this.func_98273_j();
                        return;
                    }

                    d0 = (double)this.func_98275_b() + (this.func_98271_a().field_73012_v.nextDouble() - this.func_98271_a().field_73012_v.nextDouble()) * (double)this.field_98290_m;
                    double d3 = (double)(this.func_98274_c() + this.func_98271_a().field_73012_v.nextInt(3) - 1);
                    double d4 = (double)this.func_98266_d() + (this.func_98271_a().field_73012_v.nextDouble() - this.func_98271_a().field_73012_v.nextDouble()) * (double)this.field_98290_m;
                    EntityLiving entityliving = entity instanceof EntityLiving ? (EntityLiving)entity : null;
                    entity.func_70012_b(d0, d3, d4, this.func_98271_a().field_73012_v.nextFloat() * 360.0F, 0.0F);

                    if (entityliving == null || entityliving.func_70601_bi())
                    {
                        this.func_98265_a(entity);
                        this.func_98271_a().func_72926_e(2004, this.func_98275_b(), this.func_98274_c(), this.func_98266_d(), 0);

                        if (entityliving != null)
                        {
                            entityliving.func_70656_aK();
                        }

                        flag = true;
                    }
                }

                if (flag)
                {
                    this.func_98273_j();
                }
            }
        }
    }

    public Entity func_98265_a(Entity p_98265_1_)
    {
        if (this.func_98269_i() != null)
        {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            p_98265_1_.func_70039_c(nbttagcompound);
            Iterator iterator = this.func_98269_i().field_98222_b.func_74758_c().iterator();

            while (iterator.hasNext())
            {
                NBTBase nbtbase = (NBTBase)iterator.next();
                nbttagcompound.func_74782_a(nbtbase.func_74740_e(), nbtbase.func_74737_b());
            }

            p_98265_1_.func_70020_e(nbttagcompound);

            if (p_98265_1_.field_70170_p != null)
            {
                p_98265_1_.field_70170_p.addEntity(p_98265_1_, CreatureSpawnEvent.SpawnReason.SPAWNER); // CraftBukkit
            }

            NBTTagCompound nbttagcompound1;

            for (Entity entity1 = p_98265_1_; nbttagcompound.func_74764_b("Riding"); nbttagcompound = nbttagcompound1)
            {
                nbttagcompound1 = nbttagcompound.func_74775_l("Riding");
                Entity entity2 = EntityList.func_75620_a(nbttagcompound1.func_74779_i("id"), this.func_98271_a());

                if (entity2 != null)
                {
                    NBTTagCompound nbttagcompound2 = new NBTTagCompound();
                    entity2.func_70039_c(nbttagcompound2);
                    Iterator iterator1 = nbttagcompound1.func_74758_c().iterator();

                    while (iterator1.hasNext())
                    {
                        NBTBase nbtbase1 = (NBTBase)iterator1.next();
                        nbttagcompound2.func_74782_a(nbtbase1.func_74740_e(), nbtbase1.func_74737_b());
                    }

                    entity2.func_70020_e(nbttagcompound2);
                    entity2.func_70012_b(entity1.field_70165_t, entity1.field_70163_u, entity1.field_70161_v, entity1.field_70177_z, entity1.field_70125_A);
                    this.func_98271_a().addEntity(entity2, CreatureSpawnEvent.SpawnReason.SPAWNER); // CraftBukkit);
                    entity1.func_70078_a(entity2);
                }

                entity1 = entity2;
            }
        }
        else if (p_98265_1_ instanceof EntityLiving && p_98265_1_.field_70170_p != null)
        {
            ((EntityLiving)p_98265_1_).func_82163_bD();
            this.func_98271_a().addEntity(p_98265_1_, CreatureSpawnEvent.SpawnReason.SPAWNER); // CraftBukkit);
        }

        return p_98265_1_;
    }

    private void func_98273_j()
    {
        if (this.field_98293_h <= this.field_98283_g)
        {
            this.field_98286_b = this.field_98283_g;
        }
        else
        {
            int i = this.field_98293_h - this.field_98283_g;
            this.field_98286_b = this.field_98283_g + this.func_98271_a().field_73012_v.nextInt(i);
        }

        if (this.field_98285_e != null && this.field_98285_e.size() > 0)
        {
            this.func_98277_a((WeightedRandomMinecart) WeightedRandom.func_76271_a(this.func_98271_a().field_73012_v, (Collection) this.field_98285_e));
        }

        this.func_98267_a(1);
    }

    public void func_98270_a(NBTTagCompound p_98270_1_)
    {
        this.field_98288_a = p_98270_1_.func_74779_i("EntityId");
        this.field_98286_b = p_98270_1_.func_74765_d("Delay");

        if (p_98270_1_.func_74764_b("SpawnPotentials"))
        {
            this.field_98285_e = new ArrayList();
            NBTTagList nbttaglist = p_98270_1_.func_74761_m("SpawnPotentials");

            for (int i = 0; i < nbttaglist.func_74745_c(); ++i)
            {
                this.field_98285_e.add(new WeightedRandomMinecart(this, (NBTTagCompound)nbttaglist.func_74743_b(i)));
            }
        }
        else
        {
            this.field_98285_e = null;
        }

        if (p_98270_1_.func_74764_b("SpawnData"))
        {
            this.func_98277_a(new WeightedRandomMinecart(this, p_98270_1_.func_74775_l("SpawnData"), this.field_98288_a));
        }
        else
        {
            this.func_98277_a((WeightedRandomMinecart)null);
        }

        if (p_98270_1_.func_74764_b("MinSpawnDelay"))
        {
            this.field_98283_g = p_98270_1_.func_74765_d("MinSpawnDelay");
            this.field_98293_h = p_98270_1_.func_74765_d("MaxSpawnDelay");
            this.field_98294_i = p_98270_1_.func_74765_d("SpawnCount");
        }

        if (p_98270_1_.func_74764_b("MaxNearbyEntities"))
        {
            this.field_98292_k = p_98270_1_.func_74765_d("MaxNearbyEntities");
            this.field_98289_l = p_98270_1_.func_74765_d("RequiredPlayerRange");
        }

        if (p_98270_1_.func_74764_b("SpawnRange"))
        {
            this.field_98290_m = p_98270_1_.func_74765_d("SpawnRange");
        }

        if (this.func_98271_a() != null && this.func_98271_a().field_72995_K)
        {
            this.field_98291_j = null;
        }
    }

    public void func_98280_b(NBTTagCompound p_98280_1_)
    {
        p_98280_1_.func_74778_a("EntityId", this.func_98276_e());
        p_98280_1_.func_74777_a("Delay", (short)this.field_98286_b);
        p_98280_1_.func_74777_a("MinSpawnDelay", (short)this.field_98283_g);
        p_98280_1_.func_74777_a("MaxSpawnDelay", (short)this.field_98293_h);
        p_98280_1_.func_74777_a("SpawnCount", (short)this.field_98294_i);
        p_98280_1_.func_74777_a("MaxNearbyEntities", (short)this.field_98292_k);
        p_98280_1_.func_74777_a("RequiredPlayerRange", (short)this.field_98289_l);
        p_98280_1_.func_74777_a("SpawnRange", (short)this.field_98290_m);

        if (this.func_98269_i() != null)
        {
            p_98280_1_.func_74766_a("SpawnData", (NBTTagCompound)this.func_98269_i().field_98222_b.func_74737_b());
        }

        if (this.func_98269_i() != null || this.field_98285_e != null && this.field_98285_e.size() > 0)
        {
            NBTTagList nbttaglist = new NBTTagList();

            if (this.field_98285_e != null && this.field_98285_e.size() > 0)
            {
                Iterator iterator = this.field_98285_e.iterator();

                while (iterator.hasNext())
                {
                    WeightedRandomMinecart weightedrandomminecart = (WeightedRandomMinecart)iterator.next();
                    nbttaglist.func_74742_a(weightedrandomminecart.func_98220_a());
                }
            }
            else
            {
                nbttaglist.func_74742_a(this.func_98269_i().func_98220_a());
            }

            p_98280_1_.func_74782_a("SpawnPotentials", nbttaglist);
        }
    }

    public boolean func_98268_b(int p_98268_1_)
    {
        if (p_98268_1_ == 1 && this.func_98271_a().field_72995_K)
        {
            this.field_98286_b = this.field_98283_g;
            return true;
        }
        else
        {
            return false;
        }
    }

    public WeightedRandomMinecart func_98269_i()
    {
        return this.field_98282_f;
    }

    public void func_98277_a(WeightedRandomMinecart p_98277_1_)
    {
        this.field_98282_f = p_98277_1_;
    }

    public abstract void func_98267_a(int i);

    public abstract World func_98271_a();

    public abstract int func_98275_b();

    public abstract int func_98274_c();

    public abstract int func_98266_d();
}
