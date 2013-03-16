package net.minecraft.util;

import java.util.HashSet;
import java.util.Set;

public class IntHashMap
{
    private transient IntHashMapEntry[] field_76055_a = new IntHashMapEntry[16];
    private transient int field_76053_b;
    private int field_76054_c = 12;
    private final float field_76051_d = 0.75F;
    private transient volatile int field_76052_e;
    // private Set f = new HashSet(); // CraftBukkit - expensive and unused

    public IntHashMap() {}

    private static int func_76044_g(int p_76044_0_)
    {
        p_76044_0_ ^= p_76044_0_ >>> 20 ^ p_76044_0_ >>> 12;
        return p_76044_0_ ^ p_76044_0_ >>> 7 ^ p_76044_0_ >>> 4;
    }

    private static int func_76043_a(int p_76043_0_, int p_76043_1_)
    {
        return p_76043_0_ & p_76043_1_ - 1;
    }

    public Object func_76041_a(int p_76041_1_)
    {
        int j = func_76044_g(p_76041_1_);

        for (IntHashMapEntry inthashmapentry = this.field_76055_a[func_76043_a(j, this.field_76055_a.length)]; inthashmapentry != null; inthashmapentry = inthashmapentry.field_76034_c)
        {
            if (inthashmapentry.field_76035_a == p_76041_1_)
            {
                return inthashmapentry.field_76033_b;
            }
        }

        return null;
    }

    public boolean func_76037_b(int p_76037_1_)
    {
        return this.func_76045_c(p_76037_1_) != null;
    }

    final IntHashMapEntry func_76045_c(int p_76045_1_)
    {
        int j = func_76044_g(p_76045_1_);

        for (IntHashMapEntry inthashmapentry = this.field_76055_a[func_76043_a(j, this.field_76055_a.length)]; inthashmapentry != null; inthashmapentry = inthashmapentry.field_76034_c)
        {
            if (inthashmapentry.field_76035_a == p_76045_1_)
            {
                return inthashmapentry;
            }
        }

        return null;
    }

    public void func_76038_a(int p_76038_1_, Object p_76038_2_)
    {
        // this.f.add(Integer.valueOf(i)); // CraftBukkit
        int j = func_76044_g(p_76038_1_);
        int k = func_76043_a(j, this.field_76055_a.length);

        for (IntHashMapEntry inthashmapentry = this.field_76055_a[k]; inthashmapentry != null; inthashmapentry = inthashmapentry.field_76034_c)
        {
            if (inthashmapentry.field_76035_a == p_76038_1_)
            {
                inthashmapentry.field_76033_b = p_76038_2_;
                return;
            }
        }

        ++this.field_76052_e;
        this.func_76040_a(j, p_76038_1_, p_76038_2_, k);
    }

    private void func_76047_h(int p_76047_1_)
    {
        IntHashMapEntry[] ainthashmapentry = this.field_76055_a;
        int j = ainthashmapentry.length;

        if (j == 1073741824)
        {
            this.field_76054_c = Integer.MAX_VALUE;
        }
        else
        {
            IntHashMapEntry[] ainthashmapentry1 = new IntHashMapEntry[p_76047_1_];
            this.func_76048_a(ainthashmapentry1);
            this.field_76055_a = ainthashmapentry1;
            this.field_76054_c = (int)((float)p_76047_1_ * this.field_76051_d);
        }
    }

    private void func_76048_a(IntHashMapEntry[] p_76048_1_)
    {
        IntHashMapEntry[] ainthashmapentry1 = this.field_76055_a;
        int i = p_76048_1_.length;

        for (int j = 0; j < ainthashmapentry1.length; ++j)
        {
            IntHashMapEntry inthashmapentry = ainthashmapentry1[j];

            if (inthashmapentry != null)
            {
                ainthashmapentry1[j] = null;
                IntHashMapEntry inthashmapentry1;

                do
                {
                    inthashmapentry1 = inthashmapentry.field_76034_c;
                    int k = func_76043_a(inthashmapentry.field_76032_d, i);
                    inthashmapentry.field_76034_c = p_76048_1_[k];
                    p_76048_1_[k] = inthashmapentry;
                    inthashmapentry = inthashmapentry1;
                }
                while (inthashmapentry1 != null);
            }
        }
    }

    public Object func_76049_d(int p_76049_1_)
    {
        // this.f.remove(Integer.valueOf(i)); // CraftBukkit
        IntHashMapEntry inthashmapentry = this.func_76036_e(p_76049_1_);
        return inthashmapentry == null ? null : inthashmapentry.field_76033_b;
    }

    final IntHashMapEntry func_76036_e(int p_76036_1_)
    {
        int j = func_76044_g(p_76036_1_);
        int k = func_76043_a(j, this.field_76055_a.length);
        IntHashMapEntry inthashmapentry = this.field_76055_a[k];
        IntHashMapEntry inthashmapentry1;
        IntHashMapEntry inthashmapentry2;

        for (inthashmapentry1 = inthashmapentry; inthashmapentry1 != null; inthashmapentry1 = inthashmapentry2)
        {
            inthashmapentry2 = inthashmapentry1.field_76034_c;

            if (inthashmapentry1.field_76035_a == p_76036_1_)
            {
                ++this.field_76052_e;
                --this.field_76053_b;

                if (inthashmapentry == inthashmapentry1)
                {
                    this.field_76055_a[k] = inthashmapentry2;
                }
                else
                {
                    inthashmapentry.field_76034_c = inthashmapentry2;
                }

                return inthashmapentry1;
            }

            inthashmapentry = inthashmapentry1;
        }

        return inthashmapentry1;
    }

    public void func_76046_c()
    {
        ++this.field_76052_e;
        IntHashMapEntry[] ainthashmapentry = this.field_76055_a;

        for (int i = 0; i < ainthashmapentry.length; ++i)
        {
            ainthashmapentry[i] = null;
        }

        this.field_76053_b = 0;
    }

    private void func_76040_a(int p_76040_1_, int p_76040_2_, Object p_76040_3_, int p_76040_4_)
    {
        IntHashMapEntry inthashmapentry = this.field_76055_a[p_76040_4_];
        this.field_76055_a[p_76040_4_] = new IntHashMapEntry(p_76040_1_, p_76040_2_, p_76040_3_, inthashmapentry);

        if (this.field_76053_b++ >= this.field_76054_c)
        {
            this.func_76047_h(2 * this.field_76055_a.length);
        }
    }

    static int func_76042_f(int p_76042_0_)
    {
        return func_76044_g(p_76042_0_);
    }
}
