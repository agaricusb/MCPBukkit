package net.minecraft.entity.passive;

// CraftBukkit start
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
// CraftBukkit end

public class EntityCow extends EntityAnimal
{
    public EntityCow(World p_i3516_1_)
    {
        super(p_i3516_1_);
        this.field_70750_az = "/mob/cow.png";
        this.func_70105_a(0.9F, 1.3F);
        this.func_70661_as().func_75491_a(true);
        this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
        this.field_70714_bg.func_75776_a(1, new EntityAIPanic(this, 0.38F));
        this.field_70714_bg.func_75776_a(2, new EntityAIMate(this, 0.2F));
        this.field_70714_bg.func_75776_a(3, new EntityAITempt(this, 0.25F, Item.field_77685_T.field_77779_bT, false));
        this.field_70714_bg.func_75776_a(4, new EntityAIFollowParent(this, 0.25F));
        this.field_70714_bg.func_75776_a(5, new EntityAIWander(this, 0.2F));
        this.field_70714_bg.func_75776_a(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.field_70714_bg.func_75776_a(7, new EntityAILookIdle(this));
    }

    public boolean func_70650_aV()
    {
        return true;
    }

    public int func_70667_aM()
    {
        return 10;
    }

    protected String func_70639_aQ()
    {
        return "mob.cow.say";
    }

    protected String func_70621_aR()
    {
        return "mob.cow.hurt";
    }

    protected String func_70673_aS()
    {
        return "mob.cow.hurt";
    }

    protected void func_70036_a(int p_70036_1_, int p_70036_2_, int p_70036_3_, int p_70036_4_)
    {
        this.func_85030_a("mob.cow.step", 0.15F, 1.0F);
    }

    protected float func_70599_aP()
    {
        return 0.4F;
    }

    protected int func_70633_aT()
    {
        return Item.field_77770_aF.field_77779_bT;
    }

    protected void func_70628_a(boolean p_70628_1_, int p_70628_2_)
    {
        // CraftBukkit start - whole method
        java.util.List<org.bukkit.inventory.ItemStack> loot = new java.util.ArrayList<org.bukkit.inventory.ItemStack>();
        int j = this.field_70146_Z.nextInt(3) + this.field_70146_Z.nextInt(1 + p_70628_2_);
        int k;

        if (j > 0)
        {
            loot.add(new org.bukkit.inventory.ItemStack(Item.field_77770_aF.field_77779_bT, j));
        }

        j = this.field_70146_Z.nextInt(3) + 1 + this.field_70146_Z.nextInt(1 + p_70628_2_);

        if (j > 0)
        {
            loot.add(new org.bukkit.inventory.ItemStack(this.func_70027_ad() ? Item.field_77734_bj.field_77779_bT : Item.field_77741_bi.field_77779_bT, j));
        }

        CraftEventFactory.callEntityDeathEvent(this, loot);
        // CraftBukkit end
    }

    public boolean func_70085_c(EntityPlayer p_70085_1_)
    {
        ItemStack itemstack = p_70085_1_.field_71071_by.func_70448_g();

        if (itemstack != null && itemstack.field_77993_c == Item.field_77788_aw.field_77779_bT)
        {
            // CraftBukkit start - got milk?
            org.bukkit.Location loc = this.getBukkitEntity().getLocation();
            org.bukkit.event.player.PlayerBucketFillEvent event = CraftEventFactory.callPlayerBucketFillEvent(p_70085_1_, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), -1, itemstack, Item.field_77771_aG);

            if (event.isCancelled())
            {
                return false;
            }

            if (--itemstack.field_77994_a <= 0)
            {
                p_70085_1_.field_71071_by.func_70299_a(p_70085_1_.field_71071_by.field_70461_c, CraftItemStack.asNMSCopy(event.getItemStack()));
            }
            else if (!p_70085_1_.field_71071_by.func_70441_a(new ItemStack(Item.field_77771_aG)))
            {
                p_70085_1_.func_71021_b(CraftItemStack.asNMSCopy(event.getItemStack()));
            }

            // CraftBukkit end
            return true;
        }
        else
        {
            return super.func_70085_c(p_70085_1_);
        }
    }

    public EntityCow func_70879_a(EntityAgeable p_70879_1_)
    {
        return new EntityCow(this.field_70170_p);
    }

    public EntityAgeable func_90011_a(EntityAgeable p_90011_1_)
    {
        return this.func_70879_a(p_90011_1_);
    }
}
