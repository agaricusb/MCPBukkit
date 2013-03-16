package net.minecraft.entity.monster;

// CraftBukkit start
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAICreeperSwell;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
// CraftBukkit end

public class EntityCreeper extends EntityMob
{
    private int field_70834_e;
    private int field_70833_d;
    private int field_82225_f = 30;
    private int field_82226_g = 3;
    private int record = -1; // CraftBukkit

    public EntityCreeper(World p_i3547_1_)
    {
        super(p_i3547_1_);
        this.field_70750_az = "/mob/creeper.png";
        this.field_70714_bg.func_75776_a(1, new EntityAISwimming(this));
        this.field_70714_bg.func_75776_a(2, new EntityAICreeperSwell(this));
        this.field_70714_bg.func_75776_a(3, new EntityAIAvoidEntity(this, EntityOcelot.class, 6.0F, 0.25F, 0.3F));
        this.field_70714_bg.func_75776_a(4, new EntityAIAttackOnCollide(this, 0.25F, false));
        this.field_70714_bg.func_75776_a(5, new EntityAIWander(this, 0.2F));
        this.field_70714_bg.func_75776_a(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.field_70714_bg.func_75776_a(6, new EntityAILookIdle(this));
        this.field_70715_bh.func_75776_a(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 16.0F, 0, true));
        this.field_70715_bh.func_75776_a(2, new EntityAIHurtByTarget(this, false));
    }

    public boolean func_70650_aV()
    {
        return true;
    }

    public int func_82143_as()
    {
        return this.func_70638_az() == null ? 3 : 3 + (this.field_70734_aK - 1);
    }

    protected void func_70069_a(float p_70069_1_)
    {
        super.func_70069_a(p_70069_1_);
        this.field_70833_d = (int)((float)this.field_70833_d + p_70069_1_ * 1.5F);

        if (this.field_70833_d > this.field_82225_f - 5)
        {
            this.field_70833_d = this.field_82225_f - 5;
        }
    }

    public int func_70667_aM()
    {
        return 20;
    }

    protected void func_70088_a()
    {
        super.func_70088_a();
        this.field_70180_af.func_75682_a(16, Byte.valueOf((byte) - 1));
        this.field_70180_af.func_75682_a(17, Byte.valueOf((byte)0));
    }

    public void func_70014_b(NBTTagCompound p_70014_1_)
    {
        super.func_70014_b(p_70014_1_);

        if (this.field_70180_af.func_75683_a(17) == 1)
        {
            p_70014_1_.func_74757_a("powered", true);
        }

        p_70014_1_.func_74777_a("Fuse", (short)this.field_82225_f);
        p_70014_1_.func_74774_a("ExplosionRadius", (byte)this.field_82226_g);
    }

    public void func_70037_a(NBTTagCompound p_70037_1_)
    {
        super.func_70037_a(p_70037_1_);
        this.field_70180_af.func_75692_b(17, Byte.valueOf((byte)(p_70037_1_.func_74767_n("powered") ? 1 : 0)));

        if (p_70037_1_.func_74764_b("Fuse"))
        {
            this.field_82225_f = p_70037_1_.func_74765_d("Fuse");
        }

        if (p_70037_1_.func_74764_b("ExplosionRadius"))
        {
            this.field_82226_g = p_70037_1_.func_74771_c("ExplosionRadius");
        }
    }

    public void func_70071_h_()
    {
        if (this.func_70089_S())
        {
            this.field_70834_e = this.field_70833_d;
            int i = this.func_70832_p();

            if (i > 0 && this.field_70833_d == 0)
            {
                this.func_85030_a("random.fuse", 1.0F, 0.5F);
            }

            this.field_70833_d += i;

            if (this.field_70833_d < 0)
            {
                this.field_70833_d = 0;
            }

            if (this.field_70833_d >= this.field_82225_f)
            {
                this.field_70833_d = this.field_82225_f;

                if (!this.field_70170_p.field_72995_K)
                {
                    boolean flag = this.field_70170_p.func_82736_K().func_82766_b("mobGriefing");
                    // CraftBukkit start
                    float radius = this.func_70830_n() ? 6.0F : 3.0F;
                    ExplosionPrimeEvent event = new ExplosionPrimeEvent(this.getBukkitEntity(), radius, false);
                    this.field_70170_p.getServer().getPluginManager().callEvent(event);

                    if (!event.isCancelled())
                    {
                        this.field_70170_p.func_72885_a(this, this.field_70165_t, this.field_70163_u, this.field_70161_v, event.getRadius(), event.getFire(), flag);
                        this.func_70106_y();
                    }
                    else
                    {
                        this.field_70833_d = 0;
                    }

                    // CraftBukkit end
                }
            }
        }

        super.func_70071_h_();
    }

    protected String func_70621_aR()
    {
        return "mob.creeper.say";
    }

    protected String func_70673_aS()
    {
        return "mob.creeper.death";
    }

    public void func_70645_a(DamageSource p_70645_1_)
    {
        // CraftBukkit start - rearranged the method (super call to end, drop to dropDeathLoot)
        if (p_70645_1_.func_76346_g() instanceof EntitySkeleton)
        {
            int i = Item.field_77819_bI.field_77779_bT + this.field_70146_Z.nextInt(Item.field_85180_cf.field_77779_bT - Item.field_77819_bI.field_77779_bT + 1);
            // this.b(i, 1); // CraftBukkit
            this.record = i;
        }

        super.func_70645_a(p_70645_1_);
        // CraftBukkit end
    }

    // CraftBukkit start - whole method
    protected void func_70628_a(boolean flag, int i)
    {
        int j = this.func_70633_aT();
        java.util.List<org.bukkit.inventory.ItemStack> loot = new java.util.ArrayList<org.bukkit.inventory.ItemStack>();

        if (j > 0)
        {
            int k = this.field_70146_Z.nextInt(3);

            if (i > 0)
            {
                k += this.field_70146_Z.nextInt(i + 1);
            }

            if (k > 0)
            {
                loot.add(new org.bukkit.inventory.ItemStack(j, k));
            }
        }

        // Drop a music disc?
        if (this.record != -1)
        {
            loot.add(new org.bukkit.inventory.ItemStack(this.record, 1));
            this.record = -1;
        }

        CraftEventFactory.callEntityDeathEvent(this, loot); // raise event even for those times when the entity does not drop loot
    }
    // CraftBukkit end

    public boolean func_70652_k(Entity p_70652_1_)
    {
        return true;
    }

    public boolean func_70830_n()
    {
        return this.field_70180_af.func_75683_a(17) == 1;
    }

    protected int func_70633_aT()
    {
        return Item.field_77677_M.field_77779_bT;
    }

    public int func_70832_p()
    {
        return this.field_70180_af.func_75683_a(16);
    }

    public void func_70829_a(int p_70829_1_)
    {
        this.field_70180_af.func_75692_b(16, Byte.valueOf((byte)p_70829_1_));
    }

    public void func_70077_a(EntityLightningBolt p_70077_1_)
    {
        super.func_70077_a(p_70077_1_);

        // CraftBukkit start
        if (CraftEventFactory.callCreeperPowerEvent(this, p_70077_1_, org.bukkit.event.entity.CreeperPowerEvent.PowerCause.LIGHTNING).isCancelled())
        {
            return;
        }

        this.setPowered(true);
    }

    public void setPowered(boolean powered)
    {
        if (!powered)
        {
            this.field_70180_af.func_75692_b(17, Byte.valueOf((byte) 0));
        }
        else
        {
            this.field_70180_af.func_75692_b(17, Byte.valueOf((byte) 1));
        }

        // CraftBukkit end
    }
}
