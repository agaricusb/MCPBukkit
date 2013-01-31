package net.minecraft.entity.effect;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import org.bukkit.event.block.BlockIgniteEvent; // CraftBukkit

public class EntityLightningBolt extends EntityWeatherEffect
{
    private int field_70262_b;
    public long field_70264_a = 0L;
    private int field_70263_c;

    // CraftBukkit start
    private org.bukkit.craftbukkit.CraftWorld cworld;
    public boolean isEffect = false;

    public EntityLightningBolt(World p_i3533_1_, double p_i3533_2_, double p_i3533_4_, double p_i3533_6_)
    {
        this(p_i3533_1_, p_i3533_2_, p_i3533_4_, p_i3533_6_, false);
    }

    public EntityLightningBolt(World p_i3533_1_, double p_i3533_2_, double p_i3533_4_, double p_i3533_6_, boolean isEffect)
    {
        // CraftBukkit end
        super(p_i3533_1_);
        // CraftBukkit start
        this.isEffect = isEffect;
        this.cworld = p_i3533_1_.getWorld();
        // CraftBukkit end
        this.func_70012_b(p_i3533_2_, p_i3533_4_, p_i3533_6_, 0.0F, 0.0F);
        this.field_70262_b = 2;
        this.field_70264_a = this.field_70146_Z.nextLong();
        this.field_70263_c = this.field_70146_Z.nextInt(3) + 1;

        // CraftBukkit
        if (!isEffect && !p_i3533_1_.field_72995_K && p_i3533_1_.field_73013_u >= 2 && p_i3533_1_.func_72873_a(MathHelper.func_76128_c(p_i3533_2_), MathHelper.func_76128_c(p_i3533_4_), MathHelper.func_76128_c(p_i3533_6_), 10))
        {
            int i = MathHelper.func_76128_c(p_i3533_2_);
            int j = MathHelper.func_76128_c(p_i3533_4_);
            int k = MathHelper.func_76128_c(p_i3533_6_);

            if (p_i3533_1_.func_72798_a(i, j, k) == 0 && Block.field_72067_ar.func_71930_b(p_i3533_1_, i, j, k))
            {
                // CraftBukkit start
                BlockIgniteEvent event = new BlockIgniteEvent(this.cworld.getBlockAt(i, j, k), BlockIgniteEvent.IgniteCause.LIGHTNING, null);
                p_i3533_1_.getServer().getPluginManager().callEvent(event);

                if (!event.isCancelled())
                {
                    p_i3533_1_.func_72859_e(i, j, k, Block.field_72067_ar.field_71990_ca);
                }

                // CraftBukkit end
            }

            for (i = 0; i < 4; ++i)
            {
                j = MathHelper.func_76128_c(p_i3533_2_) + this.field_70146_Z.nextInt(3) - 1;
                k = MathHelper.func_76128_c(p_i3533_4_) + this.field_70146_Z.nextInt(3) - 1;
                int l = MathHelper.func_76128_c(p_i3533_6_) + this.field_70146_Z.nextInt(3) - 1;

                if (p_i3533_1_.func_72798_a(j, k, l) == 0 && Block.field_72067_ar.func_71930_b(p_i3533_1_, j, k, l))
                {
                    // CraftBukkit start
                    BlockIgniteEvent event = new BlockIgniteEvent(this.cworld.getBlockAt(j, k, l), BlockIgniteEvent.IgniteCause.LIGHTNING, null);
                    p_i3533_1_.getServer().getPluginManager().callEvent(event);

                    if (!event.isCancelled())
                    {
                        p_i3533_1_.func_72859_e(j, k, l, Block.field_72067_ar.field_71990_ca);
                    }

                    // CraftBukkit end
                }
            }
        }
    }

    public void func_70071_h_()
    {
        super.func_70071_h_();

        if (this.field_70262_b == 2)
        {
            this.field_70170_p.func_72908_a(this.field_70165_t, this.field_70163_u, this.field_70161_v, "ambient.weather.thunder", 10000.0F, 0.8F + this.field_70146_Z.nextFloat() * 0.2F);
            this.field_70170_p.func_72908_a(this.field_70165_t, this.field_70163_u, this.field_70161_v, "random.explode", 2.0F, 0.5F + this.field_70146_Z.nextFloat() * 0.2F);
        }

        --this.field_70262_b;

        if (this.field_70262_b < 0)
        {
            if (this.field_70263_c == 0)
            {
                this.func_70106_y();
            }
            else if (this.field_70262_b < -this.field_70146_Z.nextInt(10))
            {
                --this.field_70263_c;
                this.field_70262_b = 1;
                this.field_70264_a = this.field_70146_Z.nextLong();

                // CraftBukkit
                if (!this.isEffect && !this.field_70170_p.field_72995_K && this.field_70170_p.func_72873_a(MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c(this.field_70163_u), MathHelper.func_76128_c(this.field_70161_v), 10))
                {
                    int i = MathHelper.func_76128_c(this.field_70165_t);
                    int j = MathHelper.func_76128_c(this.field_70163_u);
                    int k = MathHelper.func_76128_c(this.field_70161_v);

                    if (this.field_70170_p.func_72798_a(i, j, k) == 0 && Block.field_72067_ar.func_71930_b(this.field_70170_p, i, j, k))
                    {
                        // CraftBukkit start
                        BlockIgniteEvent event = new BlockIgniteEvent(this.cworld.getBlockAt(i, j, k), BlockIgniteEvent.IgniteCause.LIGHTNING, null);
                        this.field_70170_p.getServer().getPluginManager().callEvent(event);

                        if (!event.isCancelled())
                        {
                            this.field_70170_p.func_72859_e(i, j, k, Block.field_72067_ar.field_71990_ca);
                        }

                        // CraftBukkit end
                    }
                }
            }
        }

        if (this.field_70262_b >= 0 && !this.isEffect)   // CraftBukkit - add !this.isEffect
        {
            if (this.field_70170_p.field_72995_K)
            {
                this.field_70170_p.field_73016_r = 2;
            }
            else
            {
                double d0 = 3.0D;
                List list = this.field_70170_p.func_72839_b(this, AxisAlignedBB.func_72332_a().func_72299_a(this.field_70165_t - d0, this.field_70163_u - d0, this.field_70161_v - d0, this.field_70165_t + d0, this.field_70163_u + 6.0D + d0, this.field_70161_v + d0));

                for (int l = 0; l < list.size(); ++l)
                {
                    Entity entity = (Entity)list.get(l);
                    entity.func_70077_a(this);
                }
            }
        }
    }

    protected void func_70088_a() {}

    protected void func_70037_a(NBTTagCompound p_70037_1_) {}

    protected void func_70014_b(NBTTagCompound p_70014_1_) {}
}
