package net.minecraft.entity.projectile;

// CraftBukkit start
import org.bukkit.entity.Ageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEggThrowEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
// CraftBukkit end

public class EntityEgg extends EntityThrowable
{
    public EntityEgg(World p_i3586_1_)
    {
        super(p_i3586_1_);
    }

    public EntityEgg(World p_i3587_1_, EntityLiving p_i3587_2_)
    {
        super(p_i3587_1_, p_i3587_2_);
    }

    public EntityEgg(World p_i3588_1_, double p_i3588_2_, double p_i3588_4_, double p_i3588_6_)
    {
        super(p_i3588_1_, p_i3588_2_, p_i3588_4_, p_i3588_6_);
    }

    protected void func_70184_a(MovingObjectPosition p_70184_1_)
    {
        if (p_70184_1_.field_72308_g != null)
        {
            p_70184_1_.field_72308_g.func_70097_a(DamageSource.func_76356_a(this, this.func_85052_h()), 0);
        }

        // CraftBukkit start
        boolean hatching = !this.field_70170_p.field_72995_K && this.field_70146_Z.nextInt(8) == 0;
        int numHatching = (this.field_70146_Z.nextInt(32) == 0) ? 4 : 1;

        if (!hatching)
        {
            numHatching = 0;
        }

        EntityType hatchingType = EntityType.CHICKEN;
        Entity shooter = this.func_85052_h();

        if (shooter instanceof EntityPlayerMP)
        {
            Player player = (shooter == null) ? null : (Player) shooter.getBukkitEntity();
            PlayerEggThrowEvent event = new PlayerEggThrowEvent(player, (org.bukkit.entity.Egg) this.getBukkitEntity(), hatching, (byte) numHatching, hatchingType);
            this.field_70170_p.getServer().getPluginManager().callEvent(event);
            hatching = event.isHatching();
            numHatching = event.getNumHatches();
            hatchingType = event.getHatchingType();
        }

        if (hatching)
        {
            for (int k = 0; k < numHatching; k++)
            {
                org.bukkit.entity.Entity entity = field_70170_p.getWorld().spawn(new org.bukkit.Location(field_70170_p.getWorld(), this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, 0.0F), hatchingType.getEntityClass(), org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.EGG);

                if (entity instanceof Ageable)
                {
                    ((Ageable) entity).setBaby();
                }
            }
        }

        // CraftBukkit end

        for (int j = 0; j < 8; ++j)
        {
            this.field_70170_p.func_72869_a("snowballpoof", this.field_70165_t, this.field_70163_u, this.field_70161_v, 0.0D, 0.0D, 0.0D);
        }

        if (!this.field_70170_p.field_72995_K)
        {
            this.func_70106_y();
        }
    }
}
