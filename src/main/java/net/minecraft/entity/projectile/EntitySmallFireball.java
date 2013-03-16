package net.minecraft.entity.projectile;

// CraftBukkit start
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
// CraftBukkit end

public class EntitySmallFireball extends EntityFireball
{
    public EntitySmallFireball(World p_i3577_1_)
    {
        super(p_i3577_1_);
        this.func_70105_a(0.3125F, 0.3125F);
    }

    public EntitySmallFireball(World p_i3578_1_, EntityLiving p_i3578_2_, double p_i3578_3_, double p_i3578_5_, double p_i3578_7_)
    {
        super(p_i3578_1_, p_i3578_2_, p_i3578_3_, p_i3578_5_, p_i3578_7_);
        this.func_70105_a(0.3125F, 0.3125F);
    }

    public EntitySmallFireball(World p_i3579_1_, double p_i3579_2_, double p_i3579_4_, double p_i3579_6_, double p_i3579_8_, double p_i3579_10_, double p_i3579_12_)
    {
        super(p_i3579_1_, p_i3579_2_, p_i3579_4_, p_i3579_6_, p_i3579_8_, p_i3579_10_, p_i3579_12_);
        this.func_70105_a(0.3125F, 0.3125F);
    }

    protected void func_70227_a(MovingObjectPosition p_70227_1_)
    {
        if (!this.field_70170_p.field_72995_K)
        {
            if (p_70227_1_.field_72308_g != null)
            {
                if (!p_70227_1_.field_72308_g.func_70045_F() && p_70227_1_.field_72308_g.func_70097_a(DamageSource.func_76362_a(this, this.field_70235_a), 5))
                {
                    // CraftBukkit start - entity damage by entity event + combust event
                    EntityCombustByEntityEvent event = new EntityCombustByEntityEvent((org.bukkit.entity.Projectile) this.getBukkitEntity(), p_70227_1_.field_72308_g.getBukkitEntity(), 5);
                    p_70227_1_.field_72308_g.field_70170_p.getServer().getPluginManager().callEvent(event);

                    if (!event.isCancelled())
                    {
                        p_70227_1_.field_72308_g.func_70015_d(event.getDuration());
                    }

                    // CraftBukkit end
                }
            }
            else
            {
                int i = p_70227_1_.field_72311_b;
                int j = p_70227_1_.field_72312_c;
                int k = p_70227_1_.field_72309_d;

                switch (p_70227_1_.field_72310_e)
                {
                    case 0:
                        --j;
                        break;

                    case 1:
                        ++j;
                        break;

                    case 2:
                        --k;
                        break;

                    case 3:
                        ++k;
                        break;

                    case 4:
                        --i;
                        break;

                    case 5:
                        ++i;
                }

                if (this.field_70170_p.func_72799_c(i, j, k))
                {
                    // CraftBukkit start
                    org.bukkit.block.Block block = field_70170_p.getWorld().getBlockAt(i, j, k);
                    BlockIgniteEvent event = new BlockIgniteEvent(block, BlockIgniteEvent.IgniteCause.FIREBALL, null);
                    field_70170_p.getServer().getPluginManager().callEvent(event);

                    if (!event.isCancelled())
                    {
                        this.field_70170_p.func_94575_c(i, j, k, Block.field_72067_ar.field_71990_ca);
                    }

                    // CraftBukkit end
                }
            }

            this.func_70106_y();
        }
    }

    public boolean func_70067_L()
    {
        return false;
    }

    public boolean func_70097_a(DamageSource p_70097_1_, int p_70097_2_)
    {
        return false;
    }
}
