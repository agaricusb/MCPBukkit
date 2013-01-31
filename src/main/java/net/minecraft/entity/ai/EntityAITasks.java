package net.minecraft.entity.ai;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.profiler.Profiler;

import org.bukkit.craftbukkit.util.UnsafeList; // CraftBukkit

public class EntityAITasks
{
    // CraftBukkit start - ArrayList -> UnsafeList
    private List field_75782_a = new UnsafeList();
    private List field_75780_b = new UnsafeList();
    // CraftBukkit end
    private final Profiler field_75781_c;
    private int field_75778_d = 0;
    private int field_75779_e = 3;

    public EntityAITasks(Profiler p_i3469_1_)
    {
        this.field_75781_c = p_i3469_1_;
    }

    public void func_75776_a(int p_75776_1_, EntityAIBase p_75776_2_)
    {
        this.field_75782_a.add(new EntityAITaskEntry(this, p_75776_1_, p_75776_2_));
    }

    public void func_85156_a(EntityAIBase p_85156_1_)
    {
        Iterator iterator = this.field_75782_a.iterator();

        while (iterator.hasNext())
        {
            EntityAITaskEntry entityaitaskentry = (EntityAITaskEntry)iterator.next();
            EntityAIBase entityaibase1 = entityaitaskentry.field_75733_a;

            if (entityaibase1 == p_85156_1_)
            {
                if (this.field_75780_b.contains(entityaitaskentry))
                {
                    entityaibase1.func_75251_c();
                    this.field_75780_b.remove(entityaitaskentry);
                }

                iterator.remove();
            }
        }
    }

    public void func_75774_a()
    {
        // ArrayList arraylist = new ArrayList(); // CraftBukkit - remove usage
        Iterator iterator;
        EntityAITaskEntry entityaitaskentry;

        if (this.field_75778_d++ % this.field_75779_e == 0)
        {
            iterator = this.field_75782_a.iterator();

            while (iterator.hasNext())
            {
                entityaitaskentry = (EntityAITaskEntry)iterator.next();
                boolean flag = this.field_75780_b.contains(entityaitaskentry);

                if (flag)
                {
                    if (this.func_75775_b(entityaitaskentry) && this.func_75773_a(entityaitaskentry))
                    {
                        continue;
                    }

                    entityaitaskentry.field_75733_a.func_75251_c();
                    this.field_75780_b.remove(entityaitaskentry);
                }

                if (this.func_75775_b(entityaitaskentry) && entityaitaskentry.field_75733_a.func_75250_a())
                {
                    // CraftBukkit start - call method now instead of queueing
                    // arraylist.add(entityaitaskentry);
                    entityaitaskentry.field_75733_a.func_75249_e();
                    // CraftBukkit end
                    this.field_75780_b.add(entityaitaskentry);
                }
            }
        }
        else
        {
            iterator = this.field_75780_b.iterator();

            while (iterator.hasNext())
            {
                entityaitaskentry = (EntityAITaskEntry)iterator.next();

                if (!entityaitaskentry.field_75733_a.func_75253_b())
                {
                    entityaitaskentry.field_75733_a.func_75251_c();
                    iterator.remove();
                }
            }
        }

        this.field_75781_c.func_76320_a("goalStart");
        // CraftBukkit start - removed usage of arraylist
        /*iterator = arraylist.iterator();

        while (iterator.hasNext()) {
            entityaitaskentry = (PathfinderGoalSelectorItem) iterator.next();
            this.c.a(entityaitaskentry.a.getClass().getSimpleName());
            entityaitaskentry.a.c();
            this.c.b();
        }*/
        // CraftBukkit end
        this.field_75781_c.func_76319_b();
        this.field_75781_c.func_76320_a("goalTick");
        iterator = this.field_75780_b.iterator();

        while (iterator.hasNext())
        {
            entityaitaskentry = (EntityAITaskEntry)iterator.next();
            entityaitaskentry.field_75733_a.func_75246_d();
        }

        this.field_75781_c.func_76319_b();
    }

    private boolean func_75773_a(EntityAITaskEntry p_75773_1_)
    {
        this.field_75781_c.func_76320_a("canContinue");
        boolean flag = p_75773_1_.field_75733_a.func_75253_b();
        this.field_75781_c.func_76319_b();
        return flag;
    }

    private boolean func_75775_b(EntityAITaskEntry p_75775_1_)
    {
        this.field_75781_c.func_76320_a("canUse");
        Iterator iterator = this.field_75782_a.iterator();

        while (iterator.hasNext())
        {
            EntityAITaskEntry entityaitaskentry1 = (EntityAITaskEntry)iterator.next();

            if (entityaitaskentry1 != p_75775_1_)
            {
                if (p_75775_1_.field_75731_b >= entityaitaskentry1.field_75731_b)
                {
                    // CraftBukkit - switch order
                    if (!this.func_75777_a(p_75775_1_, entityaitaskentry1) && this.field_75780_b.contains(entityaitaskentry1))
                    {
                        this.field_75781_c.func_76319_b();
                        ((UnsafeList.Itr) iterator).valid = false; // CraftBukkit - mark iterator for reuse
                        return false;
                    }

                    // CraftBukkit - switch order
                }
                else if (!entityaitaskentry1.field_75733_a.func_75252_g() && this.field_75780_b.contains(entityaitaskentry1))
                {
                    this.field_75781_c.func_76319_b();
                    ((UnsafeList.Itr) iterator).valid = false; // CraftBukkit - mark iterator for reuse
                    return false;
                }
            }
        }

        this.field_75781_c.func_76319_b();
        return true;
    }

    private boolean func_75777_a(EntityAITaskEntry p_75777_1_, EntityAITaskEntry p_75777_2_)
    {
        return (p_75777_1_.field_75733_a.func_75247_h() & p_75777_2_.field_75733_a.func_75247_h()) == 0;
    }
}
