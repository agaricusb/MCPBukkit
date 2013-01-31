package net.minecraft.entity.monster;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

// CraftBukkit start
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.event.entity.EntityTargetEvent;
// CraftBukkit end

public class EntityPigZombie extends EntityZombie
{
    public int field_70837_d = 0; // CraftBukkit - private -> public
    private int field_70838_e = 0;

    public EntityPigZombie(World p_i3553_1_)
    {
        super(p_i3553_1_);
        this.field_70750_az = "/mob/pigzombie.png";
        this.field_70697_bw = 0.5F;
        this.field_70178_ae = true;
    }

    protected boolean func_70650_aV()
    {
        return false;
    }

    public void func_70071_h_()
    {
        this.field_70697_bw = this.field_70789_a != null ? 0.95F : 0.5F;

        if (this.field_70838_e > 0 && --this.field_70838_e == 0)
        {
            this.func_85030_a("mob.zombiepig.zpigangry", this.func_70599_aP() * 2.0F, ((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F) * 1.8F);
        }

        super.func_70071_h_();
    }

    public boolean func_70601_bi()
    {
        return this.field_70170_p.field_73013_u > 0 && this.field_70170_p.func_72855_b(this.field_70121_D) && this.field_70170_p.func_72945_a(this, this.field_70121_D).isEmpty() && !this.field_70170_p.func_72953_d(this.field_70121_D);
    }

    public void func_70014_b(NBTTagCompound p_70014_1_)
    {
        super.func_70014_b(p_70014_1_);
        p_70014_1_.func_74777_a("Anger", (short)this.field_70837_d);
    }

    public void func_70037_a(NBTTagCompound p_70037_1_)
    {
        super.func_70037_a(p_70037_1_);
        this.field_70837_d = p_70037_1_.func_74765_d("Anger");
    }

    protected Entity func_70782_k()
    {
        return this.field_70837_d == 0 ? null : super.func_70782_k();
    }

    public boolean func_70097_a(DamageSource p_70097_1_, int p_70097_2_)
    {
        if (this.func_85032_ar())
        {
            return false;
        }
        else
        {
            Entity entity = p_70097_1_.func_76346_g();

            if (entity instanceof EntityPlayer)
            {
                List list = this.field_70170_p.func_72839_b(this, this.field_70121_D.func_72314_b(32.0D, 32.0D, 32.0D));

                for (int j = 0; j < list.size(); ++j)
                {
                    Entity entity1 = (Entity)list.get(j);

                    if (entity1 instanceof EntityPigZombie)
                    {
                        EntityPigZombie entitypigzombie = (EntityPigZombie)entity1;
                        entitypigzombie.func_70835_c(entity);
                    }
                }

                this.func_70835_c(entity);
            }

            return super.func_70097_a(p_70097_1_, p_70097_2_);
        }
    }

    private void func_70835_c(Entity p_70835_1_)
    {
        // CraftBukkit start
        org.bukkit.entity.Entity bukkitTarget = p_70835_1_ == null ? null : p_70835_1_.getBukkitEntity();
        EntityTargetEvent event = new EntityTargetEvent(this.getBukkitEntity(), bukkitTarget, EntityTargetEvent.TargetReason.PIG_ZOMBIE_TARGET);
        this.field_70170_p.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled())
        {
            return;
        }

        if (event.getTarget() == null)
        {
            this.field_70789_a = null;
            return;
        }

        p_70835_1_ = ((org.bukkit.craftbukkit.entity.CraftEntity) event.getTarget()).getHandle();
        // CraftBukkit end
        this.field_70789_a = p_70835_1_;
        this.field_70837_d = 400 + this.field_70146_Z.nextInt(400);
        this.field_70838_e = this.field_70146_Z.nextInt(40);
    }

    protected String func_70639_aQ()
    {
        return "mob.zombiepig.zpig";
    }

    protected String func_70621_aR()
    {
        return "mob.zombiepig.zpighurt";
    }

    protected String func_70673_aS()
    {
        return "mob.zombiepig.zpigdeath";
    }

    protected void func_70628_a(boolean p_70628_1_, int p_70628_2_)
    {
        // CraftBukkit start
        List<org.bukkit.inventory.ItemStack> loot = new java.util.ArrayList<org.bukkit.inventory.ItemStack>();
        int j = this.field_70146_Z.nextInt(2 + p_70628_2_);

        if (j > 0)
        {
            loot.add(CraftItemStack.asNewCraftStack(Item.field_77737_bm, j));
        }

        j = this.field_70146_Z.nextInt(2 + p_70628_2_);

        if (j > 0)
        {
            loot.add(CraftItemStack.asNewCraftStack(Item.field_77733_bq, j));
        }

        // Determine rare item drops and add them to the loot
        if (this.field_70718_bc > 0)
        {
            int k = this.field_70146_Z.nextInt(200) - p_70628_2_;

            if (k < 5)
            {
                ItemStack itemstack = this.func_70600_l(k <= 0 ? 1 : 0);

                if (itemstack != null)
                {
                    loot.add(CraftItemStack.asCraftMirror(itemstack));
                }
            }
        }

        org.bukkit.craftbukkit.event.CraftEventFactory.callEntityDeathEvent(this, loot);
        // CraftBukkit end
    }

    public boolean func_70085_c(EntityPlayer p_70085_1_)
    {
        return false;
    }

    // CraftBukkit start - return rare dropped item instead of dropping it
    protected ItemStack func_70600_l(int i)
    {
        return new ItemStack(Item.field_77717_p.field_77779_bT, 1, 0);
    }

    protected int func_70633_aT()
    {
        return Item.field_77737_bm.field_77779_bT;
    }

    protected void func_82164_bB()
    {
        this.func_70062_b(0, new ItemStack(Item.field_77672_G));
    }

    public void func_82163_bD()
    {
        super.func_82163_bD();
        this.func_82229_g(false);
    }

    public int func_82193_c(Entity p_82193_1_)
    {
        ItemStack itemstack = this.func_70694_bm();
        int i = 5;

        if (itemstack != null)
        {
            i += itemstack.func_77971_a((Entity) this);
        }

        return i;
    }
}
