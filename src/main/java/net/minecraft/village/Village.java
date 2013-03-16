package net.minecraft.village;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class Village
{
    private World field_75586_a;
    private final List field_75584_b = new ArrayList();
    private final ChunkCoordinates field_75585_c = new ChunkCoordinates(0, 0, 0);
    private final ChunkCoordinates field_75582_d = new ChunkCoordinates(0, 0, 0);
    private int field_75583_e = 0;
    private int field_75580_f = 0;
    private int field_75581_g = 0;
    private int field_75588_h = 0;
    private int field_82694_i;
    private TreeMap field_82693_j = new TreeMap();
    private List field_75589_i = new ArrayList();
    private int field_75587_j = 0;

    public Village() {}

    public Village(World p_i3511_1_)
    {
        this.field_75586_a = p_i3511_1_;
    }

    public void func_82691_a(World p_82691_1_)
    {
        this.field_75586_a = p_82691_1_;
    }

    public void func_75560_a(int p_75560_1_)
    {
        this.field_75581_g = p_75560_1_;
        this.func_75557_k();
        this.func_75565_j();

        if (p_75560_1_ % 20 == 0)
        {
            this.func_75572_i();
        }

        if (p_75560_1_ % 30 == 0)
        {
            this.func_75579_h();
        }

        int j = this.field_75588_h / 10;

        if (this.field_75587_j < j && this.field_75584_b.size() > 20 && this.field_75586_a.field_73012_v.nextInt(7000) == 0)
        {
            Vec3 vec3 = this.func_75559_a(MathHelper.func_76141_d((float)this.field_75582_d.field_71574_a), MathHelper.func_76141_d((float)this.field_75582_d.field_71572_b), MathHelper.func_76141_d((float)this.field_75582_d.field_71573_c), 2, 4, 2);

            if (vec3 != null)
            {
                EntityIronGolem entityirongolem = new EntityIronGolem(this.field_75586_a);
                entityirongolem.func_70107_b(vec3.field_72450_a, vec3.field_72448_b, vec3.field_72449_c);
                this.field_75586_a.addEntity(entityirongolem, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.VILLAGE_DEFENSE); // CraftBukkit
                ++this.field_75587_j;
            }
        }
    }

    private Vec3 func_75559_a(int p_75559_1_, int p_75559_2_, int p_75559_3_, int p_75559_4_, int p_75559_5_, int p_75559_6_)
    {
        for (int k1 = 0; k1 < 10; ++k1)
        {
            int l1 = p_75559_1_ + this.field_75586_a.field_73012_v.nextInt(16) - 8;
            int i2 = p_75559_2_ + this.field_75586_a.field_73012_v.nextInt(6) - 3;
            int j2 = p_75559_3_ + this.field_75586_a.field_73012_v.nextInt(16) - 8;

            if (this.func_75570_a(l1, i2, j2) && this.func_75563_b(l1, i2, j2, p_75559_4_, p_75559_5_, p_75559_6_))
            {
                return this.field_75586_a.func_82732_R().func_72345_a((double)l1, (double)i2, (double)j2);
            }
        }

        return null;
    }

    private boolean func_75563_b(int p_75563_1_, int p_75563_2_, int p_75563_3_, int p_75563_4_, int p_75563_5_, int p_75563_6_)
    {
        if (!this.field_75586_a.func_72797_t(p_75563_1_, p_75563_2_ - 1, p_75563_3_))
        {
            return false;
        }
        else
        {
            int k1 = p_75563_1_ - p_75563_4_ / 2;
            int l1 = p_75563_3_ - p_75563_6_ / 2;

            for (int i2 = k1; i2 < k1 + p_75563_4_; ++i2)
            {
                for (int j2 = p_75563_2_; j2 < p_75563_2_ + p_75563_5_; ++j2)
                {
                    for (int k2 = l1; k2 < l1 + p_75563_6_; ++k2)
                    {
                        if (this.field_75586_a.func_72809_s(i2, j2, k2))
                        {
                            return false;
                        }
                    }
                }
            }

            return true;
        }
    }

    private void func_75579_h()
    {
        List list = this.field_75586_a.func_72872_a(EntityIronGolem.class, AxisAlignedBB.func_72332_a().func_72299_a((double)(this.field_75582_d.field_71574_a - this.field_75583_e), (double)(this.field_75582_d.field_71572_b - 4), (double)(this.field_75582_d.field_71573_c - this.field_75583_e), (double)(this.field_75582_d.field_71574_a + this.field_75583_e), (double)(this.field_75582_d.field_71572_b + 4), (double)(this.field_75582_d.field_71573_c + this.field_75583_e)));
        this.field_75587_j = list.size();
    }

    private void func_75572_i()
    {
        List list = this.field_75586_a.func_72872_a(EntityVillager.class, AxisAlignedBB.func_72332_a().func_72299_a((double)(this.field_75582_d.field_71574_a - this.field_75583_e), (double)(this.field_75582_d.field_71572_b - 4), (double)(this.field_75582_d.field_71573_c - this.field_75583_e), (double)(this.field_75582_d.field_71574_a + this.field_75583_e), (double)(this.field_75582_d.field_71572_b + 4), (double)(this.field_75582_d.field_71573_c + this.field_75583_e)));
        this.field_75588_h = list.size();

        if (this.field_75588_h == 0)
        {
            this.field_82693_j.clear();
        }
    }

    public ChunkCoordinates func_75577_a()
    {
        return this.field_75582_d;
    }

    public int func_75568_b()
    {
        return this.field_75583_e;
    }

    public int func_75567_c()
    {
        return this.field_75584_b.size();
    }

    public int func_75561_d()
    {
        return this.field_75581_g - this.field_75580_f;
    }

    public int func_75562_e()
    {
        return this.field_75588_h;
    }

    public boolean func_75570_a(int p_75570_1_, int p_75570_2_, int p_75570_3_)
    {
        return this.field_75582_d.func_71569_e(p_75570_1_, p_75570_2_, p_75570_3_) < (float)(this.field_75583_e * this.field_75583_e);
    }

    public List func_75558_f()
    {
        return this.field_75584_b;
    }

    public VillageDoorInfo func_75564_b(int p_75564_1_, int p_75564_2_, int p_75564_3_)
    {
        VillageDoorInfo villagedoorinfo = null;
        int l = Integer.MAX_VALUE;
        Iterator iterator = this.field_75584_b.iterator();

        while (iterator.hasNext())
        {
            VillageDoorInfo villagedoorinfo1 = (VillageDoorInfo)iterator.next();
            int i1 = villagedoorinfo1.func_75474_b(p_75564_1_, p_75564_2_, p_75564_3_);

            if (i1 < l)
            {
                villagedoorinfo = villagedoorinfo1;
                l = i1;
            }
        }

        return villagedoorinfo;
    }

    public VillageDoorInfo func_75569_c(int p_75569_1_, int p_75569_2_, int p_75569_3_)
    {
        VillageDoorInfo villagedoorinfo = null;
        int l = Integer.MAX_VALUE;
        Iterator iterator = this.field_75584_b.iterator();

        while (iterator.hasNext())
        {
            VillageDoorInfo villagedoorinfo1 = (VillageDoorInfo)iterator.next();
            int i1 = villagedoorinfo1.func_75474_b(p_75569_1_, p_75569_2_, p_75569_3_);

            if (i1 > 256)
            {
                i1 *= 1000;
            }
            else
            {
                i1 = villagedoorinfo1.func_75468_f();
            }

            if (i1 < l)
            {
                villagedoorinfo = villagedoorinfo1;
                l = i1;
            }
        }

        return villagedoorinfo;
    }

    public VillageDoorInfo func_75578_e(int p_75578_1_, int p_75578_2_, int p_75578_3_)
    {
        if (this.field_75582_d.func_71569_e(p_75578_1_, p_75578_2_, p_75578_3_) > (float)(this.field_75583_e * this.field_75583_e))
        {
            return null;
        }
        else
        {
            Iterator iterator = this.field_75584_b.iterator();
            VillageDoorInfo villagedoorinfo;

            do
            {
                if (!iterator.hasNext())
                {
                    return null;
                }

                villagedoorinfo = (VillageDoorInfo)iterator.next();
            }
            while (villagedoorinfo.field_75481_a != p_75578_1_ || villagedoorinfo.field_75480_c != p_75578_3_ || Math.abs(villagedoorinfo.field_75479_b - p_75578_2_) > 1);

            return villagedoorinfo;
        }
    }

    public void func_75576_a(VillageDoorInfo p_75576_1_)
    {
        this.field_75584_b.add(p_75576_1_);
        this.field_75585_c.field_71574_a += p_75576_1_.field_75481_a;
        this.field_75585_c.field_71572_b += p_75576_1_.field_75479_b;
        this.field_75585_c.field_71573_c += p_75576_1_.field_75480_c;
        this.func_75573_l();
        this.field_75580_f = p_75576_1_.field_75475_f;
    }

    public boolean func_75566_g()
    {
        return this.field_75584_b.isEmpty();
    }

    public void func_75575_a(EntityLiving p_75575_1_)
    {
        Iterator iterator = this.field_75589_i.iterator();
        VillageAgressor villageagressor;

        do
        {
            if (!iterator.hasNext())
            {
                this.field_75589_i.add(new VillageAgressor(this, p_75575_1_, this.field_75581_g));
                return;
            }

            villageagressor = (VillageAgressor)iterator.next();
        }
        while (villageagressor.field_75592_a != p_75575_1_);

        villageagressor.field_75590_b = this.field_75581_g;
    }

    public EntityLiving func_75571_b(EntityLiving p_75571_1_)
    {
        double d0 = Double.MAX_VALUE;
        VillageAgressor villageagressor = null;

        for (int i = 0; i < this.field_75589_i.size(); ++i)
        {
            VillageAgressor villageagressor1 = (VillageAgressor)this.field_75589_i.get(i);
            double d1 = villageagressor1.field_75592_a.func_70068_e(p_75571_1_);

            if (d1 <= d0)
            {
                villageagressor = villageagressor1;
                d0 = d1;
            }
        }

        return villageagressor != null ? villageagressor.field_75592_a : null;
    }

    public EntityPlayer func_82685_c(EntityLiving p_82685_1_)
    {
        double d0 = Double.MAX_VALUE;
        EntityPlayer entityplayer = null;
        Iterator iterator = this.field_82693_j.keySet().iterator();

        while (iterator.hasNext())
        {
            String s = (String)iterator.next();

            if (this.func_82687_d(s))
            {
                EntityPlayer entityplayer1 = this.field_75586_a.func_72924_a(s);

                if (entityplayer1 != null)
                {
                    double d1 = entityplayer1.func_70068_e(p_82685_1_);

                    if (d1 <= d0)
                    {
                        entityplayer = entityplayer1;
                        d0 = d1;
                    }
                }
            }
        }

        return entityplayer;
    }

    private void func_75565_j()
    {
        Iterator iterator = this.field_75589_i.iterator();

        while (iterator.hasNext())
        {
            VillageAgressor villageagressor = (VillageAgressor)iterator.next();

            if (!villageagressor.field_75592_a.func_70089_S() || Math.abs(this.field_75581_g - villageagressor.field_75590_b) > 300)
            {
                iterator.remove();
            }
        }
    }

    private void func_75557_k()
    {
        boolean flag = false;
        boolean flag1 = this.field_75586_a.field_73012_v.nextInt(50) == 0;
        Iterator iterator = this.field_75584_b.iterator();

        while (iterator.hasNext())
        {
            VillageDoorInfo villagedoorinfo = (VillageDoorInfo)iterator.next();

            if (flag1)
            {
                villagedoorinfo.func_75466_d();
            }

            if (!this.func_75574_f(villagedoorinfo.field_75481_a, villagedoorinfo.field_75479_b, villagedoorinfo.field_75480_c) || Math.abs(this.field_75581_g - villagedoorinfo.field_75475_f) > 1200)
            {
                this.field_75585_c.field_71574_a -= villagedoorinfo.field_75481_a;
                this.field_75585_c.field_71572_b -= villagedoorinfo.field_75479_b;
                this.field_75585_c.field_71573_c -= villagedoorinfo.field_75480_c;
                flag = true;
                villagedoorinfo.field_75476_g = true;
                iterator.remove();
            }
        }

        if (flag)
        {
            this.func_75573_l();
        }
    }

    private boolean func_75574_f(int p_75574_1_, int p_75574_2_, int p_75574_3_)
    {
        int l = this.field_75586_a.func_72798_a(p_75574_1_, p_75574_2_, p_75574_3_);
        return l <= 0 ? false : l == Block.field_72054_aE.field_71990_ca;
    }

    private void func_75573_l()
    {
        int i = this.field_75584_b.size();

        if (i == 0)
        {
            this.field_75582_d.func_71571_b(0, 0, 0);
            this.field_75583_e = 0;
        }
        else
        {
            this.field_75582_d.func_71571_b(this.field_75585_c.field_71574_a / i, this.field_75585_c.field_71572_b / i, this.field_75585_c.field_71573_c / i);
            int j = 0;
            VillageDoorInfo villagedoorinfo;

            for (Iterator iterator = this.field_75584_b.iterator(); iterator.hasNext(); j = Math.max(villagedoorinfo.func_75474_b(this.field_75582_d.field_71574_a, this.field_75582_d.field_71572_b, this.field_75582_d.field_71573_c), j))
            {
                villagedoorinfo = (VillageDoorInfo)iterator.next();
            }

            this.field_75583_e = Math.max(32, (int)Math.sqrt((double)j) + 1);
        }
    }

    public int func_82684_a(String p_82684_1_)
    {
        Integer integer = (Integer)this.field_82693_j.get(p_82684_1_);
        return integer != null ? integer.intValue() : 0;
    }

    public int func_82688_a(String p_82688_1_, int p_82688_2_)
    {
        int j = this.func_82684_a(p_82688_1_);
        int k = MathHelper.func_76125_a(j + p_82688_2_, -30, 10);
        this.field_82693_j.put(p_82688_1_, Integer.valueOf(k));
        return k;
    }

    public boolean func_82687_d(String p_82687_1_)
    {
        return this.func_82684_a(p_82687_1_) <= -15;
    }

    public void func_82690_a(NBTTagCompound p_82690_1_)
    {
        this.field_75588_h = p_82690_1_.func_74762_e("PopSize");
        this.field_75583_e = p_82690_1_.func_74762_e("Radius");
        this.field_75587_j = p_82690_1_.func_74762_e("Golems");
        this.field_75580_f = p_82690_1_.func_74762_e("Stable");
        this.field_75581_g = p_82690_1_.func_74762_e("Tick");
        this.field_82694_i = p_82690_1_.func_74762_e("MTick");
        this.field_75582_d.field_71574_a = p_82690_1_.func_74762_e("CX");
        this.field_75582_d.field_71572_b = p_82690_1_.func_74762_e("CY");
        this.field_75582_d.field_71573_c = p_82690_1_.func_74762_e("CZ");
        this.field_75585_c.field_71574_a = p_82690_1_.func_74762_e("ACX");
        this.field_75585_c.field_71572_b = p_82690_1_.func_74762_e("ACY");
        this.field_75585_c.field_71573_c = p_82690_1_.func_74762_e("ACZ");
        NBTTagList nbttaglist = p_82690_1_.func_74761_m("Doors");

        for (int i = 0; i < nbttaglist.func_74745_c(); ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.func_74743_b(i);
            VillageDoorInfo villagedoorinfo = new VillageDoorInfo(nbttagcompound1.func_74762_e("X"), nbttagcompound1.func_74762_e("Y"), nbttagcompound1.func_74762_e("Z"), nbttagcompound1.func_74762_e("IDX"), nbttagcompound1.func_74762_e("IDZ"), nbttagcompound1.func_74762_e("TS"));
            this.field_75584_b.add(villagedoorinfo);
        }

        NBTTagList nbttaglist1 = p_82690_1_.func_74761_m("Players");

        for (int j = 0; j < nbttaglist1.func_74745_c(); ++j)
        {
            NBTTagCompound nbttagcompound2 = (NBTTagCompound)nbttaglist1.func_74743_b(j);
            this.field_82693_j.put(nbttagcompound2.func_74779_i("Name"), Integer.valueOf(nbttagcompound2.func_74762_e("S")));
        }
    }

    public void func_82689_b(NBTTagCompound p_82689_1_)
    {
        p_82689_1_.func_74768_a("PopSize", this.field_75588_h);
        p_82689_1_.func_74768_a("Radius", this.field_75583_e);
        p_82689_1_.func_74768_a("Golems", this.field_75587_j);
        p_82689_1_.func_74768_a("Stable", this.field_75580_f);
        p_82689_1_.func_74768_a("Tick", this.field_75581_g);
        p_82689_1_.func_74768_a("MTick", this.field_82694_i);
        p_82689_1_.func_74768_a("CX", this.field_75582_d.field_71574_a);
        p_82689_1_.func_74768_a("CY", this.field_75582_d.field_71572_b);
        p_82689_1_.func_74768_a("CZ", this.field_75582_d.field_71573_c);
        p_82689_1_.func_74768_a("ACX", this.field_75585_c.field_71574_a);
        p_82689_1_.func_74768_a("ACY", this.field_75585_c.field_71572_b);
        p_82689_1_.func_74768_a("ACZ", this.field_75585_c.field_71573_c);
        NBTTagList nbttaglist = new NBTTagList("Doors");
        Iterator iterator = this.field_75584_b.iterator();

        while (iterator.hasNext())
        {
            VillageDoorInfo villagedoorinfo = (VillageDoorInfo)iterator.next();
            NBTTagCompound nbttagcompound1 = new NBTTagCompound("Door");
            nbttagcompound1.func_74768_a("X", villagedoorinfo.field_75481_a);
            nbttagcompound1.func_74768_a("Y", villagedoorinfo.field_75479_b);
            nbttagcompound1.func_74768_a("Z", villagedoorinfo.field_75480_c);
            nbttagcompound1.func_74768_a("IDX", villagedoorinfo.field_75477_d);
            nbttagcompound1.func_74768_a("IDZ", villagedoorinfo.field_75478_e);
            nbttagcompound1.func_74768_a("TS", villagedoorinfo.field_75475_f);
            nbttaglist.func_74742_a(nbttagcompound1);
        }

        p_82689_1_.func_74782_a("Doors", nbttaglist);
        NBTTagList nbttaglist1 = new NBTTagList("Players");
        Iterator iterator1 = this.field_82693_j.keySet().iterator();

        while (iterator1.hasNext())
        {
            String s = (String)iterator1.next();
            NBTTagCompound nbttagcompound2 = new NBTTagCompound(s);
            nbttagcompound2.func_74778_a("Name", s);
            nbttagcompound2.func_74768_a("S", ((Integer)this.field_82693_j.get(s)).intValue());
            nbttaglist1.func_74742_a(nbttagcompound2);
        }

        p_82689_1_.func_74782_a("Players", nbttaglist1);
    }

    public void func_82692_h()
    {
        this.field_82694_i = this.field_75581_g;
    }

    public boolean func_82686_i()
    {
        return this.field_82694_i == 0 || this.field_75581_g - this.field_82694_i >= 3600;
    }

    public void func_82683_b(int p_82683_1_)
    {
        Iterator iterator = this.field_82693_j.keySet().iterator();

        while (iterator.hasNext())
        {
            String s = (String)iterator.next();
            this.func_82688_a(s, p_82683_1_);
        }
    }
}
