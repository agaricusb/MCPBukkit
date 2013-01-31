package net.minecraft.block;

// CraftBukkit start
import org.bukkit.craftbukkit.util.BlockStateListPopulator;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
// CraftBukkit end

public class BlockPumpkin extends BlockDirectional
{
    private boolean field_72218_a;

    protected BlockPumpkin(int p_i3982_1_, int p_i3982_2_, boolean p_i3982_3_)
    {
        super(p_i3982_1_, Material.field_76266_z);
        this.field_72059_bZ = p_i3982_2_;
        this.func_71907_b(true);
        this.field_72218_a = p_i3982_3_;
        this.func_71849_a(CreativeTabs.field_78030_b);
    }

    public int func_71858_a(int p_71858_1_, int p_71858_2_)
    {
        if (p_71858_1_ == 1)
        {
            return this.field_72059_bZ;
        }
        else if (p_71858_1_ == 0)
        {
            return this.field_72059_bZ;
        }
        else
        {
            int k = this.field_72059_bZ + 1 + 16;

            if (this.field_72218_a)
            {
                ++k;
            }

            return p_71858_2_ == 2 && p_71858_1_ == 2 ? k : (p_71858_2_ == 3 && p_71858_1_ == 5 ? k : (p_71858_2_ == 0 && p_71858_1_ == 3 ? k : (p_71858_2_ == 1 && p_71858_1_ == 4 ? k : this.field_72059_bZ + 16)));
        }
    }

    public int func_71851_a(int p_71851_1_)
    {
        return p_71851_1_ == 1 ? this.field_72059_bZ : (p_71851_1_ == 0 ? this.field_72059_bZ : (p_71851_1_ == 3 ? this.field_72059_bZ + 1 + 16 : this.field_72059_bZ + 16));
    }

    public void func_71861_g(World p_71861_1_, int p_71861_2_, int p_71861_3_, int p_71861_4_)
    {
        super.func_71861_g(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_);

        if (p_71861_1_.func_72798_a(p_71861_2_, p_71861_3_ - 1, p_71861_4_) == Block.field_72039_aU.field_71990_ca && p_71861_1_.func_72798_a(p_71861_2_, p_71861_3_ - 2, p_71861_4_) == Block.field_72039_aU.field_71990_ca)
        {
            if (!p_71861_1_.field_72995_K)
            {
                // CraftBukkit start - use BlockStateListPopulator
                BlockStateListPopulator blockList = new BlockStateListPopulator(p_71861_1_.getWorld());
                blockList.setTypeId(p_71861_2_, p_71861_3_, p_71861_4_, 0);
                blockList.setTypeId(p_71861_2_, p_71861_3_ - 1, p_71861_4_, 0);
                blockList.setTypeId(p_71861_2_, p_71861_3_ - 2, p_71861_4_, 0);
                EntitySnowman entitysnowman = new EntitySnowman(p_71861_1_);
                entitysnowman.func_70012_b((double)p_71861_2_ + 0.5D, (double)p_71861_3_ - 1.95D, (double)p_71861_4_ + 0.5D, 0.0F, 0.0F);

                if (p_71861_1_.addEntity(entitysnowman, SpawnReason.BUILD_SNOWMAN))
                {
                    blockList.updateList();
                }

                // CraftBukkit end
            }

            for (int l = 0; l < 120; ++l)
            {
                p_71861_1_.func_72869_a("snowshovel", (double)p_71861_2_ + p_71861_1_.field_73012_v.nextDouble(), (double)(p_71861_3_ - 2) + p_71861_1_.field_73012_v.nextDouble() * 2.5D, (double)p_71861_4_ + p_71861_1_.field_73012_v.nextDouble(), 0.0D, 0.0D, 0.0D);
            }
        }
        else if (p_71861_1_.func_72798_a(p_71861_2_, p_71861_3_ - 1, p_71861_4_) == Block.field_72083_ai.field_71990_ca && p_71861_1_.func_72798_a(p_71861_2_, p_71861_3_ - 2, p_71861_4_) == Block.field_72083_ai.field_71990_ca)
        {
            boolean flag = p_71861_1_.func_72798_a(p_71861_2_ - 1, p_71861_3_ - 1, p_71861_4_) == Block.field_72083_ai.field_71990_ca && p_71861_1_.func_72798_a(p_71861_2_ + 1, p_71861_3_ - 1, p_71861_4_) == Block.field_72083_ai.field_71990_ca;
            boolean flag1 = p_71861_1_.func_72798_a(p_71861_2_, p_71861_3_ - 1, p_71861_4_ - 1) == Block.field_72083_ai.field_71990_ca && p_71861_1_.func_72798_a(p_71861_2_, p_71861_3_ - 1, p_71861_4_ + 1) == Block.field_72083_ai.field_71990_ca;

            if (flag || flag1)
            {
                // CraftBukkit start - use BlockStateListPopulator
                BlockStateListPopulator blockList = new BlockStateListPopulator(p_71861_1_.getWorld());
                blockList.setTypeId(p_71861_2_, p_71861_3_, p_71861_4_, 0);
                blockList.setTypeId(p_71861_2_, p_71861_3_ - 1, p_71861_4_, 0);
                blockList.setTypeId(p_71861_2_, p_71861_3_ - 2, p_71861_4_, 0);

                if (flag)
                {
                    blockList.setTypeId(p_71861_2_ - 1, p_71861_3_ - 1, p_71861_4_, 0);
                    blockList.setTypeId(p_71861_2_ + 1, p_71861_3_ - 1, p_71861_4_, 0);
                }
                else
                {
                    blockList.setTypeId(p_71861_2_, p_71861_3_ - 1, p_71861_4_ - 1, 0);
                    blockList.setTypeId(p_71861_2_, p_71861_3_ - 1, p_71861_4_ + 1, 0);
                }

                EntityIronGolem entityirongolem = new EntityIronGolem(p_71861_1_);
                entityirongolem.func_70849_f(true);
                entityirongolem.func_70012_b((double)p_71861_2_ + 0.5D, (double)p_71861_3_ - 1.95D, (double)p_71861_4_ + 0.5D, 0.0F, 0.0F);

                if (p_71861_1_.addEntity(entityirongolem, SpawnReason.BUILD_IRONGOLEM))
                {
                    for (int i1 = 0; i1 < 120; ++i1)
                    {
                        p_71861_1_.func_72869_a("snowballpoof", (double) p_71861_2_ + p_71861_1_.field_73012_v.nextDouble(), (double)(p_71861_3_ - 2) + p_71861_1_.field_73012_v.nextDouble() * 3.9D, (double) p_71861_4_ + p_71861_1_.field_73012_v.nextDouble(), 0.0D, 0.0D, 0.0D);
                    }

                    blockList.updateList();
                }

                // CraftBukkit end
            }
        }
    }

    public boolean func_71930_b(World p_71930_1_, int p_71930_2_, int p_71930_3_, int p_71930_4_)
    {
        int l = p_71930_1_.func_72798_a(p_71930_2_, p_71930_3_, p_71930_4_);
        return (l == 0 || Block.field_71973_m[l].field_72018_cp.func_76222_j()) && p_71930_1_.func_72797_t(p_71930_2_, p_71930_3_ - 1, p_71930_4_);
    }

    public void func_71860_a(World p_71860_1_, int p_71860_2_, int p_71860_3_, int p_71860_4_, EntityLiving p_71860_5_)
    {
        int l = MathHelper.func_76128_c((double)(p_71860_5_.field_70177_z * 4.0F / 360.0F) + 2.5D) & 3;
        p_71860_1_.func_72921_c(p_71860_2_, p_71860_3_, p_71860_4_, l);
    }

    // CraftBukkit start
    public void func_71863_a(World world, int i, int j, int k, int l)
    {
        if (Block.field_71973_m[l] != null && Block.field_71973_m[l].func_71853_i())
        {
            org.bukkit.block.Block block = world.getWorld().getBlockAt(i, j, k);
            int power = block.getBlockPower();
            BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, power, power);
            world.getServer().getPluginManager().callEvent(eventRedstone);
        }
    }
    // CraftBukkit end
}
