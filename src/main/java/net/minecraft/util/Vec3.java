package net.minecraft.util;

public class Vec3
{
    public static final Vec3Pool field_82592_a = new Vec3Pool(-1, -1);
    public final Vec3Pool field_72447_d;
    public double field_72450_a;
    public double field_72448_b;
    public double field_72449_c;
    public Vec3 next; // CraftBukkit

    public static Vec3 func_72443_a(double p_72443_0_, double p_72443_2_, double p_72443_4_)
    {
        return new Vec3(field_82592_a, p_72443_0_, p_72443_2_, p_72443_4_);
    }

    protected Vec3(Vec3Pool p_i5109_1_, double p_i5109_2_, double p_i5109_4_, double p_i5109_6_)
    {
        if (p_i5109_2_ == -0.0D)
        {
            p_i5109_2_ = 0.0D;
        }

        if (p_i5109_4_ == -0.0D)
        {
            p_i5109_4_ = 0.0D;
        }

        if (p_i5109_6_ == -0.0D)
        {
            p_i5109_6_ = 0.0D;
        }

        this.field_72450_a = p_i5109_2_;
        this.field_72448_b = p_i5109_4_;
        this.field_72449_c = p_i5109_6_;
        this.field_72447_d = p_i5109_1_;
    }

    protected Vec3 func_72439_b(double p_72439_1_, double p_72439_3_, double p_72439_5_)
    {
        this.field_72450_a = p_72439_1_;
        this.field_72448_b = p_72439_3_;
        this.field_72449_c = p_72439_5_;
        return this;
    }

    public Vec3 func_72432_b()
    {
        double d0 = (double)MathHelper.func_76133_a(this.field_72450_a * this.field_72450_a + this.field_72448_b * this.field_72448_b + this.field_72449_c * this.field_72449_c);
        return d0 < 1.0E-4D ? this.field_72447_d.func_72345_a(0.0D, 0.0D, 0.0D) : this.field_72447_d.func_72345_a(this.field_72450_a / d0, this.field_72448_b / d0, this.field_72449_c / d0);
    }

    public double func_72430_b(Vec3 p_72430_1_)
    {
        return this.field_72450_a * p_72430_1_.field_72450_a + this.field_72448_b * p_72430_1_.field_72448_b + this.field_72449_c * p_72430_1_.field_72449_c;
    }

    public Vec3 func_72441_c(double p_72441_1_, double p_72441_3_, double p_72441_5_)
    {
        return this.field_72447_d.func_72345_a(this.field_72450_a + p_72441_1_, this.field_72448_b + p_72441_3_, this.field_72449_c + p_72441_5_);
    }

    public double func_72438_d(Vec3 p_72438_1_)
    {
        double d0 = p_72438_1_.field_72450_a - this.field_72450_a;
        double d1 = p_72438_1_.field_72448_b - this.field_72448_b;
        double d2 = p_72438_1_.field_72449_c - this.field_72449_c;
        return (double)MathHelper.func_76133_a(d0 * d0 + d1 * d1 + d2 * d2);
    }

    public double func_72436_e(Vec3 p_72436_1_)
    {
        double d0 = p_72436_1_.field_72450_a - this.field_72450_a;
        double d1 = p_72436_1_.field_72448_b - this.field_72448_b;
        double d2 = p_72436_1_.field_72449_c - this.field_72449_c;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public double func_72445_d(double p_72445_1_, double p_72445_3_, double p_72445_5_)
    {
        double d3 = p_72445_1_ - this.field_72450_a;
        double d4 = p_72445_3_ - this.field_72448_b;
        double d5 = p_72445_5_ - this.field_72449_c;
        return d3 * d3 + d4 * d4 + d5 * d5;
    }

    public double func_72433_c()
    {
        return (double)MathHelper.func_76133_a(this.field_72450_a * this.field_72450_a + this.field_72448_b * this.field_72448_b + this.field_72449_c * this.field_72449_c);
    }

    public Vec3 func_72429_b(Vec3 p_72429_1_, double p_72429_2_)
    {
        double d1 = p_72429_1_.field_72450_a - this.field_72450_a;
        double d2 = p_72429_1_.field_72448_b - this.field_72448_b;
        double d3 = p_72429_1_.field_72449_c - this.field_72449_c;

        if (d1 * d1 < 1.0000000116860974E-7D)
        {
            return null;
        }
        else
        {
            double d4 = (p_72429_2_ - this.field_72450_a) / d1;
            return d4 >= 0.0D && d4 <= 1.0D ? this.field_72447_d.func_72345_a(this.field_72450_a + d1 * d4, this.field_72448_b + d2 * d4, this.field_72449_c + d3 * d4) : null;
        }
    }

    public Vec3 func_72435_c(Vec3 p_72435_1_, double p_72435_2_)
    {
        double d1 = p_72435_1_.field_72450_a - this.field_72450_a;
        double d2 = p_72435_1_.field_72448_b - this.field_72448_b;
        double d3 = p_72435_1_.field_72449_c - this.field_72449_c;

        if (d2 * d2 < 1.0000000116860974E-7D)
        {
            return null;
        }
        else
        {
            double d4 = (p_72435_2_ - this.field_72448_b) / d2;
            return d4 >= 0.0D && d4 <= 1.0D ? this.field_72447_d.func_72345_a(this.field_72450_a + d1 * d4, this.field_72448_b + d2 * d4, this.field_72449_c + d3 * d4) : null;
        }
    }

    public Vec3 func_72434_d(Vec3 p_72434_1_, double p_72434_2_)
    {
        double d1 = p_72434_1_.field_72450_a - this.field_72450_a;
        double d2 = p_72434_1_.field_72448_b - this.field_72448_b;
        double d3 = p_72434_1_.field_72449_c - this.field_72449_c;

        if (d3 * d3 < 1.0000000116860974E-7D)
        {
            return null;
        }
        else
        {
            double d4 = (p_72434_2_ - this.field_72449_c) / d3;
            return d4 >= 0.0D && d4 <= 1.0D ? this.field_72447_d.func_72345_a(this.field_72450_a + d1 * d4, this.field_72448_b + d2 * d4, this.field_72449_c + d3 * d4) : null;
        }
    }

    public String toString()
    {
        return "(" + this.field_72450_a + ", " + this.field_72448_b + ", " + this.field_72449_c + ")";
    }

    public void func_72440_a(float p_72440_1_)
    {
        float f1 = MathHelper.func_76134_b(p_72440_1_);
        float f2 = MathHelper.func_76126_a(p_72440_1_);
        double d0 = this.field_72450_a;
        double d1 = this.field_72448_b * (double)f1 + this.field_72449_c * (double)f2;
        double d2 = this.field_72449_c * (double)f1 - this.field_72448_b * (double)f2;
        this.field_72450_a = d0;
        this.field_72448_b = d1;
        this.field_72449_c = d2;
    }

    public void func_72442_b(float p_72442_1_)
    {
        float f1 = MathHelper.func_76134_b(p_72442_1_);
        float f2 = MathHelper.func_76126_a(p_72442_1_);
        double d0 = this.field_72450_a * (double)f1 + this.field_72449_c * (double)f2;
        double d1 = this.field_72448_b;
        double d2 = this.field_72449_c * (double)f1 - this.field_72450_a * (double)f2;
        this.field_72450_a = d0;
        this.field_72448_b = d1;
        this.field_72449_c = d2;
    }
    public Vec3 func_72439_b_CodeFix_Public(double a, double b, double c){
        return func_72439_b(a, b, c);
    }
}