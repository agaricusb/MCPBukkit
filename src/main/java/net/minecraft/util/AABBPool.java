package net.minecraft.util;

import java.util.ArrayList;
import java.util.List;

public class AABBPool
{
    private final int field_72306_a;
    private final int field_72304_b;
    private final List field_72305_c = new ArrayList();
    private int field_72302_d = 0;
    private int field_72303_e = 0;
    private int field_72301_f = 0;

    public AABBPool(int p_i4030_1_, int p_i4030_2_)
    {
        this.field_72306_a = p_i4030_1_;
        this.field_72304_b = p_i4030_2_;
    }

    public AxisAlignedBB func_72299_a(double p_72299_1_, double p_72299_3_, double p_72299_5_, double p_72299_7_, double p_72299_9_, double p_72299_11_)
    {
        // CraftBukkit - don't pool objects indefinitely if thread doesn't adhere to contract
        if (this.field_72301_f == 0)
        {
            return new AxisAlignedBB(p_72299_1_, p_72299_3_, p_72299_5_, p_72299_7_, p_72299_9_, p_72299_11_);
        }

        AxisAlignedBB axisalignedbb;

        if (this.field_72302_d >= this.field_72305_c.size())
        {
            axisalignedbb = new AxisAlignedBB(p_72299_1_, p_72299_3_, p_72299_5_, p_72299_7_, p_72299_9_, p_72299_11_);
            this.field_72305_c.add(axisalignedbb);
        }
        else
        {
            axisalignedbb = (AxisAlignedBB)this.field_72305_c.get(this.field_72302_d);
            axisalignedbb.func_72324_b(p_72299_1_, p_72299_3_, p_72299_5_, p_72299_7_, p_72299_9_, p_72299_11_);
        }

        ++this.field_72302_d;
        return axisalignedbb;
    }

    public void func_72298_a()
    {
        if (this.field_72302_d > this.field_72303_e)
        {
            this.field_72303_e = this.field_72302_d;
        }

        // CraftBukkit start - intelligent cache
        if ((this.field_72301_f++ & 0xff) == 0)
        {
            int newSize = this.field_72305_c.size() - (this.field_72305_c.size() >> 3);

            // newSize will be 87.5%, but if we were not in that range, we clear some of the cache
            if (newSize > this.field_72303_e)
            {
                // Work down from size() to prevent insane array copies
                for (int i = this.field_72305_c.size() - 1; i > newSize; i--)
                {
                    this.field_72305_c.remove(i);
                }
            }

            this.field_72303_e = 0;
            // this.resizeTime = 0; // We do not reset to zero; it doubles for a flag
        }

        // CraftBukkit end
        this.field_72302_d = 0;
    }

    public int func_83013_c()
    {
        return this.field_72305_c.size();
    }

    public int func_83012_d()
    {
        return this.field_72302_d;
    }
}
