package net.minecraft.util;

import java.util.ArrayList;
import java.util.List;

public class Vec3Pool
{
    private final int field_72351_a;
    private final int field_72349_b;
    // CraftBukkit start
    // private final List pool = new ArrayList();
    private Vec3 freelist = null;
    private Vec3 alloclist = null;
    private Vec3 freelisthead = null;
    private Vec3 alloclisthead = null;
    private int total_size = 0;
    // CraftBukkit end
    private int field_72347_d = 0;
    private int field_72348_e = 0;
    private int field_72346_f = 0;

    public Vec3Pool(int p_i4035_1_, int p_i4035_2_)
    {
        this.field_72351_a = p_i4035_1_;
        this.field_72349_b = p_i4035_2_;
    }

    public final Vec3 func_72345_a(double p_72345_1_, double p_72345_3_, double p_72345_5_)   // CraftBukkit - add final
    {
        if (this.field_72346_f == 0)
        {
            return Vec3.func_72443_a(p_72345_1_, p_72345_3_, p_72345_5_);    // CraftBukkit - don't pool objects indefinitely if thread doesn't adhere to contract
        }

        Vec3 vec3;

        if (this.freelist == null)   // CraftBukkit
        {
            vec3 = new Vec3(this, p_72345_1_, p_72345_3_, p_72345_5_);
            this.total_size++; // CraftBukkit
        }
        else
        {
            // CraftBukkit start
            vec3 = this.freelist;
            this.freelist = vec3.next;
            // CraftBukkit end
            vec3.func_72439_b(p_72345_1_, p_72345_3_, p_72345_5_);
        }

        // CraftBukkit start
        if (this.alloclist == null)
        {
            this.alloclisthead = vec3;
        }

        vec3.next = this.alloclist; // add to allocated list
        this.alloclist = vec3;
        // CraftBukkit end
        ++this.field_72347_d;
        return vec3;
    }

    // CraftBukkit start - offer back vector (can save LOTS of unneeded bloat) - works about 90% of the time
    public void release(Vec3 v)
    {
        if (this.alloclist == v)
        {
            this.alloclist = v.next; // Pop off alloc list

            // Push on to free list
            if (this.freelist == null)
            {
                this.freelisthead = v;
            }

            v.next = this.freelist;
            this.freelist = v;
            this.field_72347_d--;
        }
    }
    // CraftBukkit end

    public void func_72343_a()
    {
        if (this.field_72347_d > this.field_72348_e)
        {
            this.field_72348_e = this.field_72347_d;
        }

        // CraftBukkit start - intelligent cache
        // Take any allocated blocks and put them on free list
        if (this.alloclist != null)
        {
            if (this.freelist == null)
            {
                this.freelist = this.alloclist;
                this.freelisthead = this.alloclisthead;
            }
            else
            {
                this.alloclisthead.next = this.freelist;
                this.freelist = this.alloclist;
                this.freelisthead = this.alloclisthead;
            }

            this.alloclist = null;
        }

        if ((this.field_72346_f++ & 0xff) == 0)
        {
            int newSize = total_size - (total_size >> 3);

            if (newSize > this.field_72348_e)   // newSize will be 87.5%, but if we were not in that range, we clear some of the cache
            {
                for (int i = total_size; i > newSize; i--)
                {
                    freelist = freelist.next;
                }

                total_size = newSize;
            }

            this.field_72348_e = 0;
            // this.f = 0; // We do not reset to zero; it doubles for a flag
        }

        // CraftBukkit end
        this.field_72347_d = 0;
    }

    public int func_82591_c()
    {
        return this.total_size; // CraftBukkit
    }

    public int func_82590_d()
    {
        return this.field_72347_d;
    }

    private boolean func_82589_e()
    {
        return this.field_72349_b < 0 || this.field_72351_a < 0;
    }
}
