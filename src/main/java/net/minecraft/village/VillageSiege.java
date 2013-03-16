package net.minecraft.village;

import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;

public class VillageSiege
{
    private World field_75537_a;
    private boolean field_75535_b = false;
    private int field_75536_c = -1;
    private int field_75533_d;
    private int field_75534_e;
    private Village field_75531_f;
    private int field_75532_g;
    private int field_75538_h;
    private int field_75539_i;

    public VillageSiege(World p_i3512_1_)
    {
        this.field_75537_a = p_i3512_1_;
    }

    public void func_75528_a()
    {
        boolean flag = false;

        if (flag)
        {
            if (this.field_75536_c == 2)
            {
                this.field_75533_d = 100;
                return;
            }
        }
        else
        {
            if (this.field_75537_a.func_72935_r())
            {
                this.field_75536_c = 0;
                return;
            }

            if (this.field_75536_c == 2)
            {
                return;
            }

            if (this.field_75536_c == 0)
            {
                float f = this.field_75537_a.func_72826_c(0.0F);

                if ((double)f < 0.5D || (double)f > 0.501D)
                {
                    return;
                }

                this.field_75536_c = this.field_75537_a.field_73012_v.nextInt(10) == 0 ? 1 : 2;
                this.field_75535_b = false;

                if (this.field_75536_c == 2)
                {
                    return;
                }
            }
        }

        if (!this.field_75535_b)
        {
            if (!this.func_75529_b())
            {
                return;
            }

            this.field_75535_b = true;
        }

        if (this.field_75534_e > 0)
        {
            --this.field_75534_e;
        }
        else
        {
            this.field_75534_e = 2;

            if (this.field_75533_d > 0)
            {
                this.func_75530_c();
                --this.field_75533_d;
            }
            else
            {
                this.field_75536_c = 2;
            }
        }
    }

    private boolean func_75529_b()
    {
        List list = this.field_75537_a.field_73010_i;
        Iterator iterator = list.iterator();

        while (iterator.hasNext())
        {
            EntityPlayer entityplayer = (EntityPlayer)iterator.next();
            this.field_75531_f = this.field_75537_a.field_72982_D.func_75550_a((int)entityplayer.field_70165_t, (int)entityplayer.field_70163_u, (int)entityplayer.field_70161_v, 1);

            if (this.field_75531_f != null && this.field_75531_f.func_75567_c() >= 10 && this.field_75531_f.func_75561_d() >= 20 && this.field_75531_f.func_75562_e() >= 20)
            {
                ChunkCoordinates chunkcoordinates = this.field_75531_f.func_75577_a();
                float f = (float)this.field_75531_f.func_75568_b();
                boolean flag = false;
                int i = 0;

                while (true)
                {
                    if (i < 10)
                    {
                        this.field_75532_g = chunkcoordinates.field_71574_a + (int)((double)(MathHelper.func_76134_b(this.field_75537_a.field_73012_v.nextFloat() * (float)Math.PI * 2.0F) * f) * 0.9D);
                        this.field_75538_h = chunkcoordinates.field_71572_b;
                        this.field_75539_i = chunkcoordinates.field_71573_c + (int)((double)(MathHelper.func_76126_a(this.field_75537_a.field_73012_v.nextFloat() * (float)Math.PI * 2.0F) * f) * 0.9D);
                        flag = false;
                        Iterator iterator1 = this.field_75537_a.field_72982_D.func_75540_b().iterator();

                        while (iterator1.hasNext())
                        {
                            Village village = (Village)iterator1.next();

                            if (village != this.field_75531_f && village.func_75570_a(this.field_75532_g, this.field_75538_h, this.field_75539_i))
                            {
                                flag = true;
                                break;
                            }
                        }

                        if (flag)
                        {
                            ++i;
                            continue;
                        }
                    }

                    if (flag)
                    {
                        return false;
                    }

                    Vec3 vec3 = this.func_75527_a(this.field_75532_g, this.field_75538_h, this.field_75539_i);

                    if (vec3 != null)
                    {
                        this.field_75534_e = 0;
                        this.field_75533_d = 20;
                        return true;
                    }

                    break;
                }
            }
        }

        return false;
    }

    private boolean func_75530_c()
    {
        Vec3 vec3 = this.func_75527_a(this.field_75532_g, this.field_75538_h, this.field_75539_i);

        if (vec3 == null)
        {
            return false;
        }
        else
        {
            EntityZombie entityzombie;

            try
            {
                entityzombie = new EntityZombie(this.field_75537_a);
                entityzombie.func_82163_bD();
                entityzombie.func_82229_g(false);
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                return false;
            }

            entityzombie.func_70012_b(vec3.field_72450_a, vec3.field_72448_b, vec3.field_72449_c, this.field_75537_a.field_73012_v.nextFloat() * 360.0F, 0.0F);
            this.field_75537_a.addEntity(entityzombie, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.VILLAGE_INVASION); // CraftBukkit
            ChunkCoordinates chunkcoordinates = this.field_75531_f.func_75577_a();
            entityzombie.func_70598_b(chunkcoordinates.field_71574_a, chunkcoordinates.field_71572_b, chunkcoordinates.field_71573_c, this.field_75531_f.func_75568_b());
            return true;
        }
    }

    private Vec3 func_75527_a(int p_75527_1_, int p_75527_2_, int p_75527_3_)
    {
        for (int l = 0; l < 10; ++l)
        {
            int i1 = p_75527_1_ + this.field_75537_a.field_73012_v.nextInt(16) - 8;
            int j1 = p_75527_2_ + this.field_75537_a.field_73012_v.nextInt(6) - 3;
            int k1 = p_75527_3_ + this.field_75537_a.field_73012_v.nextInt(16) - 8;

            if (this.field_75531_f.func_75570_a(i1, j1, k1) && SpawnerAnimals.func_77190_a(EnumCreatureType.monster, this.field_75537_a, i1, j1, k1))
            {
                return this.field_75537_a.func_82732_R().func_72345_a((double) i1, (double) j1, (double) k1);
            }
        }

        return null;
    }
}
