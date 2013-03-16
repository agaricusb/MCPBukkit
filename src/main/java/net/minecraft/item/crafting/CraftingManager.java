package net.minecraft.item.crafting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import org.bukkit.craftbukkit.event.CraftEventFactory; // CraftBukkit

public class CraftingManager
{
    private static final CraftingManager field_77598_a = new CraftingManager();
    // CraftBukkit start
    public List field_77597_b = new ArrayList(); // private -> public
    public IRecipe lastRecipe;
    public org.bukkit.inventory.InventoryView lastCraftView;
    // CraftBukkit end

    public static final CraftingManager func_77594_a()
    {
        return field_77598_a;
    }

    // CraftBukkit - private -> public
    public CraftingManager()
    {
        (new RecipesTools()).func_77586_a(this);
        (new RecipesWeapons()).func_77583_a(this);
        (new RecipesIngots()).func_77590_a(this);
        (new RecipesFood()).func_77608_a(this);
        (new RecipesCrafting()).func_77589_a(this);
        (new RecipesArmor()).func_77609_a(this);
        (new RecipesDyes()).func_77607_a(this);
        this.field_77597_b.add(new RecipesArmorDyes());
        this.field_77597_b.add(new RecipesMapCloning());
        this.field_77597_b.add(new RecipesMapExtending());
        this.field_77597_b.add(new RecipeFireworks());
        this.func_92103_a(new ItemStack(Item.field_77759_aK, 3), new Object[] {"###", '#', Item.field_77758_aJ});
        this.func_77596_b(new ItemStack(Item.field_77760_aL, 1), new Object[] {Item.field_77759_aK, Item.field_77759_aK, Item.field_77759_aK, Item.field_77770_aF});
        this.func_77596_b(new ItemStack(Item.field_77821_bF, 1), new Object[] {Item.field_77760_aL, new ItemStack(Item.field_77756_aW, 1, 0), Item.field_77676_L});
        this.func_92103_a(new ItemStack(Block.field_72031_aZ, 2), new Object[] {"###", "###", '#', Item.field_77669_D});
        this.func_92103_a(new ItemStack(Block.field_82515_ce, 6, 0), new Object[] {"###", "###", '#', Block.field_71978_w});
        this.func_92103_a(new ItemStack(Block.field_82515_ce, 6, 1), new Object[] {"###", "###", '#', Block.field_72087_ao});
        this.func_92103_a(new ItemStack(Block.field_72098_bB, 6), new Object[] {"###", "###", '#', Block.field_72033_bA});
        this.func_92103_a(new ItemStack(Block.field_71993_bv, 1), new Object[] {"#W#", "#W#", '#', Item.field_77669_D, 'W', Block.field_71988_x});
        this.func_92103_a(new ItemStack(Block.field_72032_aY, 1), new Object[] {"###", "#X#", "###", '#', Block.field_71988_x, 'X', Item.field_77702_n});
        this.func_92103_a(new ItemStack(Block.field_71960_R, 1), new Object[] {"###", "#X#", "###", '#', Block.field_71988_x, 'X', Item.field_77767_aC});
        this.func_92103_a(new ItemStack(Block.field_72093_an, 1), new Object[] {"###", "XXX", "###", '#', Block.field_71988_x, 'X', Item.field_77760_aL});
        this.func_92103_a(new ItemStack(Block.field_72039_aU, 1), new Object[] {"##", "##", '#', Item.field_77768_aD});
        this.func_92103_a(new ItemStack(Block.field_72037_aS, 6), new Object[] {"###", '#', Block.field_72039_aU});
        this.func_92103_a(new ItemStack(Block.field_72041_aW, 1), new Object[] {"##", "##", '#', Item.field_77757_aI});
        this.func_92103_a(new ItemStack(Block.field_72081_al, 1), new Object[] {"##", "##", '#', Item.field_77772_aH});
        this.func_92103_a(new ItemStack(Block.field_72014_bd, 1), new Object[] {"##", "##", '#', Item.field_77751_aT});
        this.func_92103_a(new ItemStack(Block.field_94339_ct, 1), new Object[] {"##", "##", '#', Item.field_94583_ca});
        this.func_92103_a(new ItemStack(Block.field_72101_ab, 1), new Object[] {"##", "##", '#', Item.field_77683_K});
        this.func_92103_a(new ItemStack(Block.field_72091_am, 1), new Object[] {"X#X", "#X#", "X#X", 'X', Item.field_77677_M, '#', Block.field_71939_E});
        this.func_92103_a(new ItemStack(Block.field_72079_ak, 6, 3), new Object[] {"###", '#', Block.field_71978_w});
        this.func_92103_a(new ItemStack(Block.field_72079_ak, 6, 0), new Object[] {"###", '#', Block.field_71981_t});
        this.func_92103_a(new ItemStack(Block.field_72079_ak, 6, 1), new Object[] {"###", '#', Block.field_71957_Q});
        this.func_92103_a(new ItemStack(Block.field_72079_ak, 6, 4), new Object[] {"###", '#', Block.field_72081_al});
        this.func_92103_a(new ItemStack(Block.field_72079_ak, 6, 5), new Object[] {"###", '#', Block.field_72007_bm});
        this.func_92103_a(new ItemStack(Block.field_72079_ak, 6, 6), new Object[] {"###", '#', Block.field_72033_bA});
        this.func_92103_a(new ItemStack(Block.field_72079_ak, 6, 7), new Object[] {"###", '#', Block.field_94339_ct});
        this.func_92103_a(new ItemStack(Block.field_72092_bO, 6, 0), new Object[] {"###", '#', new ItemStack(Block.field_71988_x, 1, 0)});
        this.func_92103_a(new ItemStack(Block.field_72092_bO, 6, 2), new Object[] {"###", '#', new ItemStack(Block.field_71988_x, 1, 2)});
        this.func_92103_a(new ItemStack(Block.field_72092_bO, 6, 1), new Object[] {"###", '#', new ItemStack(Block.field_71988_x, 1, 1)});
        this.func_92103_a(new ItemStack(Block.field_72092_bO, 6, 3), new Object[] {"###", '#', new ItemStack(Block.field_71988_x, 1, 3)});
        this.func_92103_a(new ItemStack(Block.field_72055_aF, 3), new Object[] {"# #", "###", "# #", '#', Item.field_77669_D});
        this.func_92103_a(new ItemStack(Item.field_77790_av, 1), new Object[] {"##", "##", "##", '#', Block.field_71988_x});
        this.func_92103_a(new ItemStack(Block.field_72005_bk, 2), new Object[] {"###", "###", '#', Block.field_71988_x});
        this.func_92103_a(new ItemStack(Item.field_77766_aB, 1), new Object[] {"##", "##", "##", '#', Item.field_77703_o});
        this.func_92103_a(new ItemStack(Item.field_77792_au, 3), new Object[] {"###", "###", " X ", '#', Block.field_71988_x, 'X', Item.field_77669_D});
        this.func_92103_a(new ItemStack(Item.field_77746_aZ, 1), new Object[] {"AAA", "BEB", "CCC", 'A', Item.field_77771_aG, 'B', Item.field_77747_aY, 'C', Item.field_77685_T, 'E', Item.field_77764_aP});
        this.func_92103_a(new ItemStack(Item.field_77747_aY, 1), new Object[] {"#", '#', Item.field_77758_aJ});
        this.func_92103_a(new ItemStack(Block.field_71988_x, 4, 0), new Object[] {"#", '#', new ItemStack(Block.field_71951_J, 1, 0)});
        this.func_92103_a(new ItemStack(Block.field_71988_x, 4, 1), new Object[] {"#", '#', new ItemStack(Block.field_71951_J, 1, 1)});
        this.func_92103_a(new ItemStack(Block.field_71988_x, 4, 2), new Object[] {"#", '#', new ItemStack(Block.field_71951_J, 1, 2)});
        this.func_92103_a(new ItemStack(Block.field_71988_x, 4, 3), new Object[] {"#", '#', new ItemStack(Block.field_71951_J, 1, 3)});
        this.func_92103_a(new ItemStack(Item.field_77669_D, 4), new Object[] {"#", "#", '#', Block.field_71988_x});
        this.func_92103_a(new ItemStack(Block.field_72069_aq, 4), new Object[] {"X", "#", 'X', Item.field_77705_m, '#', Item.field_77669_D});
        this.func_92103_a(new ItemStack(Block.field_72069_aq, 4), new Object[] {"X", "#", 'X', new ItemStack(Item.field_77705_m, 1, 1), '#', Item.field_77669_D});
        this.func_92103_a(new ItemStack(Item.field_77670_E, 4), new Object[] {"# #", " # ", '#', Block.field_71988_x});
        this.func_92103_a(new ItemStack(Item.field_77729_bt, 3), new Object[] {"# #", " # ", '#', Block.field_71946_M});
        this.func_92103_a(new ItemStack(Block.field_72056_aG, 16), new Object[] {"X X", "X#X", "X X", 'X', Item.field_77703_o, '#', Item.field_77669_D});
        this.func_92103_a(new ItemStack(Block.field_71954_T, 6), new Object[] {"X X", "X#X", "XRX", 'X', Item.field_77717_p, 'R', Item.field_77767_aC, '#', Item.field_77669_D});
        this.func_92103_a(new ItemStack(Block.field_94337_cv, 6), new Object[] {"XSX", "X#X", "XSX", 'X', Item.field_77703_o, '#', Block.field_72035_aQ, 'S', Item.field_77669_D});
        this.func_92103_a(new ItemStack(Block.field_71953_U, 6), new Object[] {"X X", "X#X", "XRX", 'X', Item.field_77703_o, 'R', Item.field_77767_aC, '#', Block.field_72044_aK});
        this.func_92103_a(new ItemStack(Item.field_77773_az, 1), new Object[] {"# #", "###", '#', Item.field_77703_o});
        this.func_92103_a(new ItemStack(Item.field_77721_bz, 1), new Object[] {"# #", "# #", "###", '#', Item.field_77703_o});
        this.func_92103_a(new ItemStack(Item.field_77724_by, 1), new Object[] {" B ", "###", '#', Block.field_71978_w, 'B', Item.field_77731_bo});
        this.func_92103_a(new ItemStack(Block.field_72008_bf, 1), new Object[] {"A", "B", 'A', Block.field_72061_ba, 'B', Block.field_72069_aq});
        this.func_92103_a(new ItemStack(Item.field_77762_aN, 1), new Object[] {"A", "B", 'A', Block.field_72077_au, 'B', Item.field_77773_az});
        this.func_92103_a(new ItemStack(Item.field_77763_aO, 1), new Object[] {"A", "B", 'A', Block.field_72051_aB, 'B', Item.field_77773_az});
        this.func_92103_a(new ItemStack(Item.field_94582_cb, 1), new Object[] {"A", "B", 'A', Block.field_72091_am, 'B', Item.field_77773_az});
        this.func_92103_a(new ItemStack(Item.field_96600_cc, 1), new Object[] {"A", "B", 'A', Block.field_94340_cs, 'B', Item.field_77773_az});
        this.func_92103_a(new ItemStack(Item.field_77769_aE, 1), new Object[] {"# #", "###", '#', Block.field_71988_x});
        this.func_92103_a(new ItemStack(Item.field_77788_aw, 1), new Object[] {"# #", " # ", '#', Item.field_77703_o});
        this.func_92103_a(new ItemStack(Item.field_82796_bJ, 1), new Object[] {"# #", " # ", '#', Item.field_77772_aH});
        this.func_92103_a(new ItemStack(Item.field_77709_i, 1), new Object[] {"A ", " B", 'A', Item.field_77703_o, 'B', Item.field_77804_ap});
        this.func_92103_a(new ItemStack(Item.field_77684_U, 1), new Object[] {"###", '#', Item.field_77685_T});
        this.func_92103_a(new ItemStack(Block.field_72063_at, 4), new Object[] {"#  ", "## ", "###", '#', new ItemStack(Block.field_71988_x, 1, 0)});
        this.func_92103_a(new ItemStack(Block.field_72072_bX, 4), new Object[] {"#  ", "## ", "###", '#', new ItemStack(Block.field_71988_x, 1, 2)});
        this.func_92103_a(new ItemStack(Block.field_72074_bW, 4), new Object[] {"#  ", "## ", "###", '#', new ItemStack(Block.field_71988_x, 1, 1)});
        this.func_92103_a(new ItemStack(Block.field_72070_bY, 4), new Object[] {"#  ", "## ", "###", '#', new ItemStack(Block.field_71988_x, 1, 3)});
        this.func_92103_a(new ItemStack(Item.field_77749_aR, 1), new Object[] {"  #", " #X", "# X", '#', Item.field_77669_D, 'X', Item.field_77683_K});
        this.func_92103_a(new ItemStack(Item.field_82793_bR, 1), new Object[] {"# ", " X", '#', Item.field_77749_aR, 'X', Item.field_82797_bK}).func_92100_c();
        this.func_92103_a(new ItemStack(Block.field_72057_aH, 4), new Object[] {"#  ", "## ", "###", '#', Block.field_71978_w});
        this.func_92103_a(new ItemStack(Block.field_71992_bw, 4), new Object[] {"#  ", "## ", "###", '#', Block.field_72081_al});
        this.func_92103_a(new ItemStack(Block.field_71995_bx, 4), new Object[] {"#  ", "## ", "###", '#', Block.field_72007_bm});
        this.func_92103_a(new ItemStack(Block.field_72100_bC, 4), new Object[] {"#  ", "## ", "###", '#', Block.field_72033_bA});
        this.func_92103_a(new ItemStack(Block.field_72088_bQ, 4), new Object[] {"#  ", "## ", "###", '#', Block.field_71957_Q});
        this.func_92103_a(new ItemStack(Block.field_94338_cu, 4), new Object[] {"#  ", "## ", "###", '#', Block.field_94339_ct});
        this.func_92103_a(new ItemStack(Item.field_77780_as, 1), new Object[] {"###", "#X#", "###", '#', Item.field_77669_D, 'X', Block.field_72101_ab});
        this.func_92103_a(new ItemStack(Item.field_82802_bI, 1), new Object[] {"###", "#X#", "###", '#', Item.field_77669_D, 'X', Item.field_77770_aF});
        this.func_92103_a(new ItemStack(Item.field_77778_at, 1, 0), new Object[] {"###", "#X#", "###", '#', Item.field_77733_bq, 'X', Item.field_77706_j});
        this.func_92103_a(new ItemStack(Item.field_77778_at, 1, 1), new Object[] {"###", "#X#", "###", '#', Block.field_72105_ah, 'X', Item.field_77706_j});
        this.func_92103_a(new ItemStack(Item.field_82798_bP, 1, 0), new Object[] {"###", "#X#", "###", '#', Item.field_77733_bq, 'X', Item.field_82797_bK});
        this.func_92103_a(new ItemStack(Block.field_72043_aJ, 1), new Object[] {"X", "#", '#', Block.field_71978_w, 'X', Item.field_77669_D});
        this.func_92103_a(new ItemStack(Block.field_72064_bT, 2), new Object[] {"I", "S", "#", '#', Block.field_71988_x, 'S', Item.field_77669_D, 'I', Item.field_77703_o});
        this.func_92103_a(new ItemStack(Block.field_72035_aQ, 1), new Object[] {"X", "#", '#', Item.field_77669_D, 'X', Item.field_77767_aC});
        this.func_92103_a(new ItemStack(Item.field_77742_bb, 1), new Object[] {"#X#", "III", '#', Block.field_72035_aQ, 'X', Item.field_77767_aC, 'I', Block.field_71981_t});
        this.func_92103_a(new ItemStack(Item.field_94585_bY, 1), new Object[] {" # ", "#X#", "III", '#', Block.field_72035_aQ, 'X', Item.field_94583_ca, 'I', Block.field_71981_t});
        this.func_92103_a(new ItemStack(Item.field_77752_aS, 1), new Object[] {" # ", "#X#", " # ", '#', Item.field_77717_p, 'X', Item.field_77767_aC});
        this.func_92103_a(new ItemStack(Item.field_77750_aQ, 1), new Object[] {" # ", "#X#", " # ", '#', Item.field_77703_o, 'X', Item.field_77767_aC});
        this.func_92103_a(new ItemStack(Item.field_82801_bO, 1), new Object[] {"###", "#X#", "###", '#', Item.field_77759_aK, 'X', Item.field_77750_aQ});
        this.func_92103_a(new ItemStack(Block.field_72034_aR, 1), new Object[] {"#", '#', Block.field_71981_t});
        this.func_92103_a(new ItemStack(Block.field_82511_ci, 1), new Object[] {"#", '#', Block.field_71988_x});
        this.func_92103_a(new ItemStack(Block.field_72044_aK, 1), new Object[] {"##", '#', Block.field_71981_t});
        this.func_92103_a(new ItemStack(Block.field_72046_aM, 1), new Object[] {"##", '#', Block.field_71988_x});
        this.func_92103_a(new ItemStack(Block.field_94345_cm, 1), new Object[] {"##", '#', Item.field_77703_o});
        this.func_92103_a(new ItemStack(Block.field_94348_cl, 1), new Object[] {"##", '#', Item.field_77717_p});
        this.func_92103_a(new ItemStack(Block.field_71958_P, 1), new Object[] {"###", "#X#", "#R#", '#', Block.field_71978_w, 'X', Item.field_77707_k, 'R', Item.field_77767_aC});
        this.func_92103_a(new ItemStack(Block.field_96469_cy, 1), new Object[] {"###", "# #", "#R#", '#', Block.field_71978_w, 'R', Item.field_77767_aC});
        this.func_92103_a(new ItemStack(Block.field_71963_Z, 1), new Object[] {"TTT", "#X#", "#R#", '#', Block.field_71978_w, 'X', Item.field_77703_o, 'R', Item.field_77767_aC, 'T', Block.field_71988_x});
        this.func_92103_a(new ItemStack(Block.field_71956_V, 1), new Object[] {"S", "P", 'S', Item.field_77761_aM, 'P', Block.field_71963_Z});
        this.func_92103_a(new ItemStack(Item.field_77776_ba, 1), new Object[] {"###", "XXX", '#', Block.field_72101_ab, 'X', Block.field_71988_x});
        this.func_92103_a(new ItemStack(Block.field_72096_bE, 1), new Object[] {" B ", "D#D", "###", '#', Block.field_72089_ap, 'B', Item.field_77760_aL, 'D', Item.field_77702_n});
        this.func_92103_a(new ItemStack(Block.field_82510_ck, 1), new Object[] {"III", " i ", "iii", 'I', Block.field_72083_ai, 'i', Item.field_77703_o});
        this.func_77596_b(new ItemStack(Item.field_77748_bA, 1), new Object[] {Item.field_77730_bn, Item.field_77722_bw});
        this.func_77596_b(new ItemStack(Item.field_77811_bE, 3), new Object[] {Item.field_77677_M, Item.field_77722_bw, Item.field_77705_m});
        this.func_77596_b(new ItemStack(Item.field_77811_bE, 3), new Object[] {Item.field_77677_M, Item.field_77722_bw, new ItemStack(Item.field_77705_m, 1, 1)});
        this.func_92103_a(new ItemStack(Block.field_94344_cp), new Object[] {"GGG", "QQQ", "WWW", 'G', Block.field_71946_M, 'Q', Item.field_94583_ca, 'W', Block.field_72092_bO});
        this.func_92103_a(new ItemStack(Block.field_94340_cs), new Object[] {"I I", "ICI", " I ", 'I', Item.field_77703_o, 'C', Block.field_72077_au});
        // Collections.sort(this.recipes, new RecipeSorter(this)); // CraftBukkit - moved below
        this.sort(); // CraftBukkit - call new sort method
        System.out.println(this.field_77597_b.size() + " recipes");
    }

    // CraftBukkit start
    public void sort()
    {
        Collections.sort(this.field_77597_b, new RecipeSorter(this));
    }
    // CraftBukkit end

    // CraftBukkit - default -> public
    public ShapedRecipes func_92103_a(ItemStack p_92103_1_, Object ... p_92103_2_)
    {
        String s = "";
        int i = 0;
        int j = 0;
        int k = 0;

        if (p_92103_2_[i] instanceof String[])
        {
            String[] astring = (String[])((String[])p_92103_2_[i++]);

            for (int l = 0; l < astring.length; ++l)
            {
                String s1 = astring[l];
                ++k;
                j = s1.length();
                s = s + s1;
            }
        }
        else
        {
            while (p_92103_2_[i] instanceof String)
            {
                String s2 = (String)p_92103_2_[i++];
                ++k;
                j = s2.length();
                s = s + s2;
            }
        }

        HashMap hashmap;

        for (hashmap = new HashMap(); i < p_92103_2_.length; i += 2)
        {
            Character character = (Character)p_92103_2_[i];
            ItemStack itemstack1 = null;

            if (p_92103_2_[i + 1] instanceof Item)
            {
                itemstack1 = new ItemStack((Item)p_92103_2_[i + 1]);
            }
            else if (p_92103_2_[i + 1] instanceof Block)
            {
                itemstack1 = new ItemStack((Block)p_92103_2_[i + 1], 1, 32767);
            }
            else if (p_92103_2_[i + 1] instanceof ItemStack)
            {
                itemstack1 = (ItemStack)p_92103_2_[i + 1];
            }

            hashmap.put(character, itemstack1);
        }

        ItemStack[] aitemstack = new ItemStack[j * k];

        for (int i1 = 0; i1 < j * k; ++i1)
        {
            char c0 = s.charAt(i1);

            if (hashmap.containsKey(Character.valueOf(c0)))
            {
                aitemstack[i1] = ((ItemStack)hashmap.get(Character.valueOf(c0))).func_77946_l();
            }
            else
            {
                aitemstack[i1] = null;
            }
        }

        ShapedRecipes shapedrecipes = new ShapedRecipes(j, k, aitemstack, p_92103_1_);
        this.field_77597_b.add(shapedrecipes);
        return shapedrecipes;
    }

    // CraftBukkit - default -> public
    public void func_77596_b(ItemStack p_77596_1_, Object ... p_77596_2_)
    {
        ArrayList arraylist = new ArrayList();
        Object[] aobject1 = p_77596_2_;
        int i = p_77596_2_.length;

        for (int j = 0; j < i; ++j)
        {
            Object object = aobject1[j];

            if (object instanceof ItemStack)
            {
                arraylist.add(((ItemStack) object).func_77946_l());
            }
            else if (object instanceof Item)
            {
                arraylist.add(new ItemStack((Item) object));
            }
            else
            {
                if (!(object instanceof Block))
                {
                    throw new RuntimeException("Invalid shapeless recipy!");
                }

                arraylist.add(new ItemStack((Block) object));
            }
        }

        this.field_77597_b.add(new ShapelessRecipes(p_77596_1_, arraylist));
    }

    public ItemStack func_82787_a(InventoryCrafting p_82787_1_, World p_82787_2_)
    {
        int i = 0;
        ItemStack itemstack = null;
        ItemStack itemstack1 = null;
        int j;

        for (j = 0; j < p_82787_1_.func_70302_i_(); ++j)
        {
            ItemStack itemstack2 = p_82787_1_.func_70301_a(j);

            if (itemstack2 != null)
            {
                if (i == 0)
                {
                    itemstack = itemstack2;
                }

                if (i == 1)
                {
                    itemstack1 = itemstack2;
                }

                ++i;
            }
        }

        if (i == 2 && itemstack.field_77993_c == itemstack1.field_77993_c && itemstack.field_77994_a == 1 && itemstack1.field_77994_a == 1 && Item.field_77698_e[itemstack.field_77993_c].func_77645_m())
        {
            Item item = Item.field_77698_e[itemstack.field_77993_c];
            int k = item.func_77612_l() - itemstack.func_77952_i();
            int l = item.func_77612_l() - itemstack1.func_77952_i();
            int i1 = k + l + item.func_77612_l() * 5 / 100;
            int j1 = item.func_77612_l() - i1;

            if (j1 < 0)
            {
                j1 = 0;
            }

            // CraftBukkit start - construct a dummy repair recipe
            ItemStack result = new ItemStack(itemstack.field_77993_c, 1, j1);
            List<ItemStack> ingredients = new ArrayList<ItemStack>();
            ingredients.add(itemstack.func_77946_l());
            ingredients.add(itemstack1.func_77946_l());
            ShapelessRecipes recipe = new ShapelessRecipes(result.func_77946_l(), ingredients);
            p_82787_1_.currentRecipe = recipe;
            result = CraftEventFactory.callPreCraftEvent(p_82787_1_, result, lastCraftView, true);
            return result;
            // CraftBukkit end
        }
        else
        {
            for (j = 0; j < this.field_77597_b.size(); ++j)
            {
                IRecipe irecipe = (IRecipe)this.field_77597_b.get(j);

                if (irecipe.func_77569_a(p_82787_1_, p_82787_2_))
                {
                    // CraftBukkit start - INVENTORY_PRE_CRAFT event
                    p_82787_1_.currentRecipe = irecipe;
                    ItemStack result = irecipe.func_77572_b(p_82787_1_);
                    return CraftEventFactory.callPreCraftEvent(p_82787_1_, result, lastCraftView, false);
                    // CraftBukkit end
                }
            }

            return null;
        }
    }

    public List func_77592_b()
    {
        return this.field_77597_b;
    }
}
