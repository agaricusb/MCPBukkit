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
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.WeightedRandom;

public class TileEntityMobSpawner extends TileEntity
{
    public int field_70394_a = -1;
    public String field_70390_d = "Pig"; // CraftBukkit - private -> public
    private List field_92060_e = null;
    private TileEntityMobSpawnerSpawnData field_70391_e = null;
    public double field_70392_b;
    public double field_70393_c = 0.0D;
    private int field_70388_f = 200;
    private int field_70389_g = 800;
    private int field_70395_h = 4;
    private Entity field_70396_i;
    private int field_82350_j = 6;
    private int field_82349_r = 16;
    private int field_82348_s = 4;

    public TileEntityMobSpawner()
    {
        this.field_70394_a = 20;
    }

    public String func_70384_a()
    {
        return this.field_70391_e == null ? this.field_70390_d : this.field_70391_e.field_92084_c;
    }

    public void func_70385_a(String p_70385_1_)
    {
        this.field_70390_d = p_70385_1_;
    }

    public boolean func_70386_b()
    {
        return this.field_70331_k.func_72977_a((double)this.field_70329_l + 0.5D, (double)this.field_70330_m + 0.5D, (double)this.field_70327_n + 0.5D, (double)this.field_82349_r) != null;
    }

    public void func_70316_g()
    {
        if (this.func_70386_b())
        {
            double d0;

            if (this.field_70331_k.field_72995_K)
            {
                double d1 = (double)((float)this.field_70329_l + this.field_70331_k.field_73012_v.nextFloat());
                double d2 = (double)((float)this.field_70330_m + this.field_70331_k.field_73012_v.nextFloat());
                d0 = (double)((float)this.field_70327_n + this.field_70331_k.field_73012_v.nextFloat());
                this.field_70331_k.func_72869_a("smoke", d1, d2, d0, 0.0D, 0.0D, 0.0D);
                this.field_70331_k.func_72869_a("flame", d1, d2, d0, 0.0D, 0.0D, 0.0D);

                if (this.field_70394_a > 0)
                {
                    --this.field_70394_a;
                }

                this.field_70393_c = this.field_70392_b;
                this.field_70392_b = (this.field_70392_b + (double)(1000.0F / ((float)this.field_70394_a + 200.0F))) % 360.0D;
            }
            else
            {
                if (this.field_70394_a == -1)
                {
                    this.func_70387_f();
                }

                if (this.field_70394_a > 0)
                {
                    --this.field_70394_a;
                    return;
                }

                boolean flag = false;

                for (int i = 0; i < this.field_70395_h; ++i)
                {
                    Entity entity = EntityList.func_75620_a(this.func_70384_a(), this.field_70331_k);

                    if (entity == null)
                    {
                        return;
                    }

                    int j = this.field_70331_k.func_72872_a(entity.getClass(), AxisAlignedBB.func_72332_a().func_72299_a((double)this.field_70329_l, (double)this.field_70330_m, (double)this.field_70327_n, (double)(this.field_70329_l + 1), (double)(this.field_70330_m + 1), (double)(this.field_70327_n + 1)).func_72314_b((double)(this.field_82348_s * 2), 4.0D, (double)(this.field_82348_s * 2))).size();

                    if (j >= this.field_82350_j)
                    {
                        this.func_70387_f();
                        return;
                    }

                    if (entity != null)
                    {
                        d0 = (double)this.field_70329_l + (this.field_70331_k.field_73012_v.nextDouble() - this.field_70331_k.field_73012_v.nextDouble()) * (double)this.field_82348_s;
                        double d3 = (double)(this.field_70330_m + this.field_70331_k.field_73012_v.nextInt(3) - 1);
                        double d4 = (double)this.field_70327_n + (this.field_70331_k.field_73012_v.nextDouble() - this.field_70331_k.field_73012_v.nextDouble()) * (double)this.field_82348_s;
                        EntityLiving entityliving = entity instanceof EntityLiving ? (EntityLiving)entity : null;
                        entity.func_70012_b(d0, d3, d4, this.field_70331_k.field_73012_v.nextFloat() * 360.0F, 0.0F);

                        if (entityliving == null || entityliving.func_70601_bi())
                        {
                            this.func_70383_a(entity);
                            this.field_70331_k.addEntity(entity, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.SPAWNER); // CraftBukkit
                            this.field_70331_k.func_72926_e(2004, this.field_70329_l, this.field_70330_m, this.field_70327_n, 0);

                            if (entityliving != null)
                            {
                                entityliving.func_70656_aK();
                            }

                            flag = true;
                        }
                    }
                }

                if (flag)
                {
                    this.func_70387_f();
                }
            }

            super.func_70316_g();
        }
    }

    public void func_70383_a(Entity p_70383_1_)
    {
        if (this.field_70391_e != null)
        {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            p_70383_1_.func_70039_c(nbttagcompound);
            Iterator iterator = this.field_70391_e.field_92083_b.func_74758_c().iterator();

            while (iterator.hasNext())
            {
                NBTBase nbtbase = (NBTBase)iterator.next();
                nbttagcompound.func_74782_a(nbtbase.func_74740_e(), nbtbase.func_74737_b());
            }

            p_70383_1_.func_70020_e(nbttagcompound);
        }
        else if (p_70383_1_ instanceof EntityLiving && p_70383_1_.field_70170_p != null)
        {
            ((EntityLiving)p_70383_1_).func_82163_bD();
        }
    }

    private void func_70387_f()
    {
        if (this.field_70389_g <= this.field_70388_f)
        {
            this.field_70394_a = this.field_70388_f;
        }
        else
        {
            this.field_70394_a = this.field_70388_f + this.field_70331_k.field_73012_v.nextInt(this.field_70389_g - this.field_70388_f);
        }

        if (this.field_92060_e != null && this.field_92060_e.size() > 0)
        {
            this.field_70391_e = (TileEntityMobSpawnerSpawnData) WeightedRandom.func_76271_a(this.field_70331_k.field_73012_v, (Collection) this.field_92060_e);
            this.field_70331_k.func_72845_h(this.field_70329_l, this.field_70330_m, this.field_70327_n);
        }

        this.field_70331_k.func_72965_b(this.field_70329_l, this.field_70330_m, this.field_70327_n, this.func_70311_o().field_71990_ca, 1, 0);
    }

    public void func_70307_a(NBTTagCompound p_70307_1_)
    {
        super.func_70307_a(p_70307_1_);
        this.field_70390_d = p_70307_1_.func_74779_i("EntityId");
        this.field_70394_a = p_70307_1_.func_74765_d("Delay");

        if (p_70307_1_.func_74764_b("SpawnPotentials"))
        {
            this.field_92060_e = new ArrayList();
            NBTTagList nbttaglist = p_70307_1_.func_74761_m("SpawnPotentials");

            for (int i = 0; i < nbttaglist.func_74745_c(); ++i)
            {
                this.field_92060_e.add(new TileEntityMobSpawnerSpawnData(this, (NBTTagCompound)nbttaglist.func_74743_b(i)));
            }
        }
        else
        {
            this.field_92060_e = null;
        }

        if (p_70307_1_.func_74764_b("SpawnData"))
        {
            this.field_70391_e = new TileEntityMobSpawnerSpawnData(this, p_70307_1_.func_74775_l("SpawnData"), this.field_70390_d);
        }
        else
        {
            this.field_70391_e = null;
        }

        if (p_70307_1_.func_74764_b("MinSpawnDelay"))
        {
            this.field_70388_f = p_70307_1_.func_74765_d("MinSpawnDelay");
            this.field_70389_g = p_70307_1_.func_74765_d("MaxSpawnDelay");
            this.field_70395_h = p_70307_1_.func_74765_d("SpawnCount");
        }

        if (p_70307_1_.func_74764_b("MaxNearbyEntities"))
        {
            this.field_82350_j = p_70307_1_.func_74765_d("MaxNearbyEntities");
            this.field_82349_r = p_70307_1_.func_74765_d("RequiredPlayerRange");
        }

        if (p_70307_1_.func_74764_b("SpawnRange"))
        {
            this.field_82348_s = p_70307_1_.func_74765_d("SpawnRange");
        }

        if (this.field_70331_k != null && this.field_70331_k.field_72995_K)
        {
            this.field_70396_i = null;
        }
    }

    public void func_70310_b(NBTTagCompound p_70310_1_)
    {
        super.func_70310_b(p_70310_1_);
        p_70310_1_.func_74778_a("EntityId", this.func_70384_a());
        p_70310_1_.func_74777_a("Delay", (short)this.field_70394_a);
        p_70310_1_.func_74777_a("MinSpawnDelay", (short)this.field_70388_f);
        p_70310_1_.func_74777_a("MaxSpawnDelay", (short)this.field_70389_g);
        p_70310_1_.func_74777_a("SpawnCount", (short)this.field_70395_h);
        p_70310_1_.func_74777_a("MaxNearbyEntities", (short)this.field_82350_j);
        p_70310_1_.func_74777_a("RequiredPlayerRange", (short)this.field_82349_r);
        p_70310_1_.func_74777_a("SpawnRange", (short)this.field_82348_s);

        if (this.field_70391_e != null)
        {
            p_70310_1_.func_74766_a("SpawnData", (NBTTagCompound)this.field_70391_e.field_92083_b.func_74737_b());
        }

        if (this.field_70391_e != null || this.field_92060_e != null && this.field_92060_e.size() > 0)
        {
            NBTTagList nbttaglist = new NBTTagList();

            if (this.field_92060_e != null && this.field_92060_e.size() > 0)
            {
                Iterator iterator = this.field_92060_e.iterator();

                while (iterator.hasNext())
                {
                    TileEntityMobSpawnerSpawnData tileentitymobspawnerspawndata = (TileEntityMobSpawnerSpawnData)iterator.next();
                    nbttaglist.func_74742_a(tileentitymobspawnerspawndata.func_92081_a());
                }
            }
            else
            {
                nbttaglist.func_74742_a(this.field_70391_e.func_92081_a());
            }

            p_70310_1_.func_74782_a("SpawnPotentials", nbttaglist);
        }
    }

    public Packet func_70319_e()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.func_70310_b(nbttagcompound);
        nbttagcompound.func_82580_o("SpawnPotentials");
        return new Packet132TileEntityData(this.field_70329_l, this.field_70330_m, this.field_70327_n, 1, nbttagcompound);
    }

    public void func_70315_b(int p_70315_1_, int p_70315_2_)
    {
        if (p_70315_1_ == 1 && this.field_70331_k.field_72995_K)
        {
            this.field_70394_a = this.field_70388_f;
        }
    }
}
