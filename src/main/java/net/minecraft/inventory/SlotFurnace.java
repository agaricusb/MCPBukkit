package net.minecraft.inventory;

// CraftBukkit start
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.MathHelper;
// CraftBukkit end

public class SlotFurnace extends Slot
{
    private EntityPlayer field_75229_a;
    private int field_75228_b;

    public SlotFurnace(EntityPlayer p_i3608_1_, IInventory p_i3608_2_, int p_i3608_3_, int p_i3608_4_, int p_i3608_5_)
    {
        super(p_i3608_2_, p_i3608_3_, p_i3608_4_, p_i3608_5_);
        this.field_75229_a = p_i3608_1_;
    }

    public boolean func_75214_a(ItemStack p_75214_1_)
    {
        return false;
    }

    public ItemStack func_75209_a(int p_75209_1_)
    {
        if (this.func_75216_d())
        {
            this.field_75228_b += Math.min(p_75209_1_, this.func_75211_c().field_77994_a);
        }

        return super.func_75209_a(p_75209_1_);
    }

    public void func_82870_a(EntityPlayer p_82870_1_, ItemStack p_82870_2_)
    {
        this.func_75208_c(p_82870_2_);
        super.func_82870_a(p_82870_1_, p_82870_2_);
    }

    protected void func_75210_a(ItemStack p_75210_1_, int p_75210_2_)
    {
        this.field_75228_b += p_75210_2_;
        this.func_75208_c(p_75210_1_);
    }

    protected void func_75208_c(ItemStack p_75208_1_)
    {
        p_75208_1_.func_77980_a(this.field_75229_a.field_70170_p, this.field_75229_a, this.field_75228_b);

        if (!this.field_75229_a.field_70170_p.field_72995_K)
        {
            int i = this.field_75228_b;
            float f = FurnaceRecipes.func_77602_a().func_77601_c(p_75208_1_.field_77993_c);
            int j;

            if (f == 0.0F)
            {
                i = 0;
            }
            else if (f < 1.0F)
            {
                j = MathHelper.func_76141_d((float)i * f);

                if (j < MathHelper.func_76123_f((float)i * f) && (float)Math.random() < (float)i * f - (float)j)
                {
                    ++j;
                }

                i = j;
            }

            // CraftBukkit start
            Player player = (Player) field_75229_a.getBukkitEntity();
            TileEntityFurnace furnace = ((TileEntityFurnace) this.field_75224_c);
            org.bukkit.block.Block block = field_75229_a.field_70170_p.getWorld().getBlockAt(furnace.field_70329_l, furnace.field_70330_m, furnace.field_70327_n);
            FurnaceExtractEvent event = new FurnaceExtractEvent(player, block, org.bukkit.Material.getMaterial(p_75208_1_.field_77993_c), p_75208_1_.field_77994_a, i);
            field_75229_a.field_70170_p.getServer().getPluginManager().callEvent(event);
            i = event.getExpToDrop();
            // CraftBukkit end

            while (i > 0)
            {
                j = EntityXPOrb.func_70527_a(i);
                i -= j;
                this.field_75229_a.field_70170_p.func_72838_d(new EntityXPOrb(this.field_75229_a.field_70170_p, this.field_75229_a.field_70165_t, this.field_75229_a.field_70163_u + 0.5D, this.field_75229_a.field_70161_v + 0.5D, j));
            }
        }

        this.field_75228_b = 0;

        if (p_75208_1_.field_77993_c == Item.field_77703_o.field_77779_bT)
        {
            this.field_75229_a.func_71064_a((StatBase) AchievementList.field_76016_k, 1);
        }

        if (p_75208_1_.field_77993_c == Item.field_77753_aV.field_77779_bT)
        {
            this.field_75229_a.func_71064_a((StatBase) AchievementList.field_76026_p, 1);
        }
    }
}
