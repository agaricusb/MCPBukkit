package net.minecraft.entity.monster;

// CraftBukkit start
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
// CraftBukkit end

public class EntitySnowman extends EntityGolem implements IRangedAttackMob
{
    public EntitySnowman(World p_i3522_1_)
    {
        super(p_i3522_1_);
        this.field_70750_az = "/mob/snowman.png";
        this.func_70105_a(0.4F, 1.8F);
        this.func_70661_as().func_75491_a(true);
        this.field_70714_bg.func_75776_a(1, new EntityAIArrowAttack(this, 0.25F, 20, 10.0F));
        this.field_70714_bg.func_75776_a(2, new EntityAIWander(this, 0.2F));
        this.field_70714_bg.func_75776_a(3, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.field_70714_bg.func_75776_a(4, new EntityAILookIdle(this));
        this.field_70715_bh.func_75776_a(1, new EntityAINearestAttackableTarget(this, EntityLiving.class, 16.0F, 0, true, false, IMob.field_82192_a));
    }

    public boolean func_70650_aV()
    {
        return true;
    }

    public int func_70667_aM()
    {
        return 4;
    }

    public void func_70636_d()
    {
        super.func_70636_d();

        if (this.func_70026_G())
        {
            // CraftBukkit start
            EntityDamageEvent event = new EntityDamageEvent(this.getBukkitEntity(), EntityDamageEvent.DamageCause.DROWNING, 1);
            this.field_70170_p.getServer().getPluginManager().callEvent(event);

            if (!event.isCancelled())
            {
                event.getEntity().setLastDamageCause(event);
                this.func_70097_a(DamageSource.field_76369_e, event.getDamage());
            }

            // CraftBukkit end
        }

        int i = MathHelper.func_76128_c(this.field_70165_t);
        int j = MathHelper.func_76128_c(this.field_70161_v);

        if (this.field_70170_p.func_72807_a(i, j).func_76743_j() > 1.0F)
        {
            // CraftBukkit start
            EntityDamageEvent event = new EntityDamageEvent(this.getBukkitEntity(), EntityDamageEvent.DamageCause.MELTING, 1);
            this.field_70170_p.getServer().getPluginManager().callEvent(event);

            if (!event.isCancelled())
            {
                event.getEntity().setLastDamageCause(event);
                this.func_70097_a(DamageSource.field_76370_b, event.getDamage());
            }

            // CraftBukkit end
        }

        for (i = 0; i < 4; ++i)
        {
            j = MathHelper.func_76128_c(this.field_70165_t + (double)((float)(i % 2 * 2 - 1) * 0.25F));
            int k = MathHelper.func_76128_c(this.field_70163_u);
            int l = MathHelper.func_76128_c(this.field_70161_v + (double)((float)(i / 2 % 2 * 2 - 1) * 0.25F));

            if (this.field_70170_p.func_72798_a(j, k, l) == 0 && this.field_70170_p.func_72807_a(j, l).func_76743_j() < 0.8F && Block.field_72037_aS.func_71930_b(this.field_70170_p, j, k, l))
            {
                // CraftBukkit start
                org.bukkit.block.BlockState blockState = this.field_70170_p.getWorld().getBlockAt(j, k, l).getState();
                blockState.setTypeId(Block.field_72037_aS.field_71990_ca);
                EntityBlockFormEvent event = new EntityBlockFormEvent(this.getBukkitEntity(), blockState.getBlock(), blockState);
                this.field_70170_p.getServer().getPluginManager().callEvent(event);

                if (!event.isCancelled())
                {
                    blockState.update(true);
                }

                // CraftBukkit end
            }
        }
    }

    protected int func_70633_aT()
    {
        return Item.field_77768_aD.field_77779_bT;
    }

    protected void func_70628_a(boolean p_70628_1_, int p_70628_2_)
    {
        // CraftBukkit start
        java.util.List<org.bukkit.inventory.ItemStack> loot = new java.util.ArrayList<org.bukkit.inventory.ItemStack>();
        int j = this.field_70146_Z.nextInt(16);

        if (j > 0)
        {
            loot.add(new org.bukkit.inventory.ItemStack(Item.field_77768_aD.field_77779_bT, j));
        }

        org.bukkit.craftbukkit.event.CraftEventFactory.callEntityDeathEvent(this, loot);
        // CraftBukkit end
    }

    public void func_82196_d(EntityLiving p_82196_1_, float p_82196_2_)
    {
        EntitySnowball entitysnowball = new EntitySnowball(this.field_70170_p, this);
        double d0 = p_82196_1_.field_70165_t - this.field_70165_t;
        double d1 = p_82196_1_.field_70163_u + (double)p_82196_1_.func_70047_e() - 1.100000023841858D - entitysnowball.field_70163_u;
        double d2 = p_82196_1_.field_70161_v - this.field_70161_v;
        float f1 = MathHelper.func_76133_a(d0 * d0 + d2 * d2) * 0.2F;
        entitysnowball.func_70186_c(d0, d1 + (double)f1, d2, 1.6F, 12.0F);
        this.func_85030_a("random.bow", 1.0F, 1.0F / (this.func_70681_au().nextFloat() * 0.4F + 0.8F));
        this.field_70170_p.func_72838_d(entitysnowball);
    }
}
