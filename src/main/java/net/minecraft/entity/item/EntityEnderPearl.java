package net.minecraft.entity.item;

// CraftBukkit start
import org.bukkit.Bukkit;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
// CraftBukkit end

public class EntityEnderPearl extends EntityThrowable
{
    public EntityEnderPearl(World p_i3589_1_)
    {
        super(p_i3589_1_);
    }

    public EntityEnderPearl(World p_i3590_1_, EntityLiving p_i3590_2_)
    {
        super(p_i3590_1_, p_i3590_2_);
    }

    protected void func_70184_a(MovingObjectPosition p_70184_1_)
    {
        if (p_70184_1_.field_72308_g != null)
        {
            p_70184_1_.field_72308_g.func_70097_a(DamageSource.func_76356_a(this, this.func_85052_h()), 0);
        }

        for (int i = 0; i < 32; ++i)
        {
            this.field_70170_p.func_72869_a("portal", this.field_70165_t, this.field_70163_u + this.field_70146_Z.nextDouble() * 2.0D, this.field_70161_v, this.field_70146_Z.nextGaussian(), 0.0D, this.field_70146_Z.nextGaussian());
        }

        if (!this.field_70170_p.field_72995_K)
        {
            if (this.func_85052_h() != null && this.func_85052_h() instanceof EntityPlayerMP)
            {
                EntityPlayerMP entityplayermp = (EntityPlayerMP)this.func_85052_h();

                if (!entityplayermp.field_71135_a.field_72576_c && entityplayermp.field_70170_p == this.field_70170_p)
                {
                    // CraftBukkit start
                    org.bukkit.craftbukkit.entity.CraftPlayer player = entityplayermp.getBukkitEntity();
                    org.bukkit.Location location = getBukkitEntity().getLocation();
                    location.setPitch(player.getLocation().getPitch());
                    location.setYaw(player.getLocation().getYaw());
                    PlayerTeleportEvent teleEvent = new PlayerTeleportEvent(player, player.getLocation(), location, PlayerTeleportEvent.TeleportCause.ENDER_PEARL);
                    Bukkit.getPluginManager().callEvent(teleEvent);

                    if (!teleEvent.isCancelled() && !entityplayermp.field_71135_a.field_72576_c)
                    {
                        entityplayermp.field_71135_a.teleport(teleEvent.getTo());
                        this.func_85052_h().field_70143_R = 0.0F;
                        EntityDamageByEntityEvent damageEvent = new EntityDamageByEntityEvent(this.getBukkitEntity(), player, EntityDamageByEntityEvent.DamageCause.FALL, 5);
                        Bukkit.getPluginManager().callEvent(damageEvent);

                        if (!damageEvent.isCancelled() && !entityplayermp.field_71135_a.field_72576_c)
                        {
                            entityplayermp.field_71145_cl = -1; // Remove spawning invulnerability
                            player.setLastDamageCause(damageEvent);
                            entityplayermp.func_70097_a(DamageSource.field_76379_h, damageEvent.getDamage());
                        }
                    }

                    // CraftBukkit end
                }
            }

            this.func_70106_y();
        }
    }
}
