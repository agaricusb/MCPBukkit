package net.minecraft.entity.passive;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.EntityAIEatGrass;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

// CraftBukkit start
import org.bukkit.event.entity.SheepRegrowWoolEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
// CraftBukkit end

public class EntitySheep extends EntityAnimal
{
    private final InventoryCrafting field_90016_e = new InventoryCrafting(new ContainerSheep(this), 2, 1);
    public static final float[][] field_70898_d = new float[][] {{1.0F, 1.0F, 1.0F}, {0.85F, 0.5F, 0.2F}, {0.7F, 0.3F, 0.85F}, {0.4F, 0.6F, 0.85F}, {0.9F, 0.9F, 0.2F}, {0.5F, 0.8F, 0.1F}, {0.95F, 0.5F, 0.65F}, {0.3F, 0.3F, 0.3F}, {0.6F, 0.6F, 0.6F}, {0.3F, 0.5F, 0.6F}, {0.5F, 0.25F, 0.7F}, {0.2F, 0.3F, 0.7F}, {0.4F, 0.3F, 0.2F}, {0.4F, 0.5F, 0.2F}, {0.6F, 0.2F, 0.2F}, {0.1F, 0.1F, 0.1F}};
    private int field_70899_e;
    private EntityAIEatGrass field_70897_f = new EntityAIEatGrass(this);

    public EntitySheep(World p_i3521_1_)
    {
        super(p_i3521_1_);
        this.field_70750_az = "/mob/sheep.png";
        this.func_70105_a(0.9F, 1.3F);
        float f = 0.23F;
        this.func_70661_as().func_75491_a(true);
        this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
        this.field_70714_bg.func_75776_a(1, new EntityAIPanic(this, 0.38F));
        this.field_70714_bg.func_75776_a(2, new EntityAIMate(this, f));
        this.field_70714_bg.func_75776_a(3, new EntityAITempt(this, 0.25F, Item.field_77685_T.field_77779_bT, false));
        this.field_70714_bg.func_75776_a(4, new EntityAIFollowParent(this, 0.25F));
        this.field_70714_bg.func_75776_a(5, this.field_70897_f);
        this.field_70714_bg.func_75776_a(6, new EntityAIWander(this, f));
        this.field_70714_bg.func_75776_a(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.field_70714_bg.func_75776_a(8, new EntityAILookIdle(this));
        this.field_90016_e.func_70299_a(0, new ItemStack(Item.field_77756_aW, 1, 0));
        this.field_90016_e.func_70299_a(1, new ItemStack(Item.field_77756_aW, 1, 0));
        this.field_90016_e.resultInventory = new InventoryCraftResult(); // CraftBukkit - add result slot for event
    }

    protected boolean func_70650_aV()
    {
        return true;
    }

    protected void func_70619_bc()
    {
        this.field_70899_e = this.field_70897_f.func_75362_f();
        super.func_70619_bc();
    }

    public void func_70636_d()
    {
        if (this.field_70170_p.field_72995_K)
        {
            this.field_70899_e = Math.max(0, this.field_70899_e - 1);
        }

        super.func_70636_d();
    }

    public int func_70667_aM()
    {
        return 8;
    }

    protected void func_70088_a()
    {
        super.func_70088_a();
        this.field_70180_af.func_75682_a(16, new Byte((byte)0));
    }

    protected void func_70628_a(boolean p_70628_1_, int p_70628_2_)
    {
        // CraftBukkit start - whole method
        java.util.List<org.bukkit.inventory.ItemStack> loot = new java.util.ArrayList<org.bukkit.inventory.ItemStack>();

        if (!this.func_70892_o())
        {
            loot.add(new org.bukkit.inventory.ItemStack(org.bukkit.Material.WOOL, 1, (short) 0, (byte) this.func_70896_n()));
        }

        org.bukkit.craftbukkit.event.CraftEventFactory.callEntityDeathEvent(this, loot);
        // CraftBukkit end
    }

    protected int func_70633_aT()
    {
        return Block.field_72101_ab.field_71990_ca;
    }

    public boolean func_70085_c(EntityPlayer p_70085_1_)
    {
        ItemStack itemstack = p_70085_1_.field_71071_by.func_70448_g();

        if (itemstack != null && itemstack.field_77993_c == Item.field_77745_be.field_77779_bT && !this.func_70892_o() && !this.func_70631_g_())
        {
            if (!this.field_70170_p.field_72995_K)
            {
                // CraftBukkit start
                PlayerShearEntityEvent event = new PlayerShearEntityEvent((org.bukkit.entity.Player) p_70085_1_.getBukkitEntity(), this.getBukkitEntity());
                this.field_70170_p.getServer().getPluginManager().callEvent(event);

                if (event.isCancelled())
                {
                    return false;
                }

                // CraftBukkit end
                this.func_70893_e(true);
                int i = 1 + this.field_70146_Z.nextInt(3);

                for (int j = 0; j < i; ++j)
                {
                    EntityItem entityitem = this.func_70099_a(new ItemStack(Block.field_72101_ab.field_71990_ca, 1, this.func_70896_n()), 1.0F);
                    entityitem.field_70181_x += (double)(this.field_70146_Z.nextFloat() * 0.05F);
                    entityitem.field_70159_w += (double)((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.1F);
                    entityitem.field_70179_y += (double)((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.1F);
                }
            }

            itemstack.func_77972_a(1, p_70085_1_);
            this.func_85030_a("mob.sheep.shear", 1.0F, 1.0F);
        }

        return super.func_70085_c(p_70085_1_);
    }

    public void func_70014_b(NBTTagCompound p_70014_1_)
    {
        super.func_70014_b(p_70014_1_);
        p_70014_1_.func_74757_a("Sheared", this.func_70892_o());
        p_70014_1_.func_74774_a("Color", (byte)this.func_70896_n());
    }

    public void func_70037_a(NBTTagCompound p_70037_1_)
    {
        super.func_70037_a(p_70037_1_);
        this.func_70893_e(p_70037_1_.func_74767_n("Sheared"));
        this.func_70891_b(p_70037_1_.func_74771_c("Color"));
    }

    protected String func_70639_aQ()
    {
        return "mob.sheep.say";
    }

    protected String func_70621_aR()
    {
        return "mob.sheep.say";
    }

    protected String func_70673_aS()
    {
        return "mob.sheep.say";
    }

    protected void func_70036_a(int p_70036_1_, int p_70036_2_, int p_70036_3_, int p_70036_4_)
    {
        this.func_85030_a("mob.sheep.step", 0.15F, 1.0F);
    }

    public int func_70896_n()
    {
        return this.field_70180_af.func_75683_a(16) & 15;
    }

    public void func_70891_b(int p_70891_1_)
    {
        byte b0 = this.field_70180_af.func_75683_a(16);
        this.field_70180_af.func_75692_b(16, Byte.valueOf((byte)(b0 & 240 | p_70891_1_ & 15)));
    }

    public boolean func_70892_o()
    {
        return (this.field_70180_af.func_75683_a(16) & 16) != 0;
    }

    public void func_70893_e(boolean p_70893_1_)
    {
        byte b0 = this.field_70180_af.func_75683_a(16);

        if (p_70893_1_)
        {
            this.field_70180_af.func_75692_b(16, Byte.valueOf((byte)(b0 | 16)));
        }
        else
        {
            this.field_70180_af.func_75692_b(16, Byte.valueOf((byte)(b0 & -17)));
        }
    }

    public static int func_70895_a(Random p_70895_0_)
    {
        int i = p_70895_0_.nextInt(100);
        return i < 5 ? 15 : (i < 10 ? 7 : (i < 15 ? 8 : (i < 18 ? 12 : (p_70895_0_.nextInt(500) == 0 ? 6 : 0))));
    }

    public EntitySheep func_90015_b(EntityAgeable p_90015_1_)
    {
        EntitySheep entitysheep = (EntitySheep)p_90015_1_;
        EntitySheep entitysheep1 = new EntitySheep(this.field_70170_p);
        int i = this.func_90014_a(this, entitysheep);
        entitysheep1.func_70891_b(15 - i);
        return entitysheep1;
    }

    public void func_70615_aA()
    {
        // CraftBukkit start
        SheepRegrowWoolEvent event = new SheepRegrowWoolEvent((org.bukkit.entity.Sheep) this.getBukkitEntity());
        this.field_70170_p.getServer().getPluginManager().callEvent(event);

        if (!event.isCancelled())
        {
            this.func_70893_e(false);
        }

        // CraftBukkit end

        if (this.func_70631_g_())
        {
            int i = this.func_70874_b() + 1200;

            if (i > 0)
            {
                i = 0;
            }

            this.func_70873_a(i);
        }
    }

    public void func_82163_bD()
    {
        this.func_70891_b(func_70895_a(this.field_70170_p.field_73012_v));
    }

    private int func_90014_a(EntityAnimal p_90014_1_, EntityAnimal p_90014_2_)
    {
        int i = this.func_90013_b(p_90014_1_);
        int j = this.func_90013_b(p_90014_2_);
        this.field_90016_e.func_70301_a(0).func_77964_b(i);
        this.field_90016_e.func_70301_a(1).func_77964_b(j);
        ItemStack itemstack = CraftingManager.func_77594_a().func_82787_a(this.field_90016_e, ((EntitySheep)p_90014_1_).field_70170_p);
        int k;

        if (itemstack != null && itemstack.func_77973_b().field_77779_bT == Item.field_77756_aW.field_77779_bT)
        {
            k = itemstack.func_77960_j();
        }
        else
        {
            k = this.field_70170_p.field_73012_v.nextBoolean() ? i : j;
        }

        return k;
    }

    private int func_90013_b(EntityAnimal p_90013_1_)
    {
        return 15 - ((EntitySheep)p_90013_1_).func_70896_n();
    }

    public EntityAgeable func_90011_a(EntityAgeable p_90011_1_)
    {
        return this.func_90015_b(p_90011_1_);
    }
}
