package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

// CraftBukkit start
import org.bukkit.craftbukkit.util.BlockStateListPopulator;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
// CraftBukkit end

public class BlockSkull extends BlockContainer
{
    protected BlockSkull(int p_i5106_1_)
    {
        super(p_i5106_1_, Material.field_76265_p);
        this.func_71905_a(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
    }

    public int func_71857_b()
    {
        return -1;
    }

    public boolean func_71926_d()
    {
        return false;
    }

    public boolean func_71886_c()
    {
        return false;
    }

    public void func_71902_a(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_)
    {
        int l = p_71902_1_.func_72805_g(p_71902_2_, p_71902_3_, p_71902_4_) & 7;

        switch (l)
        {
            case 1:
            default:
                this.func_71905_a(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
                break;

            case 2:
                this.func_71905_a(0.25F, 0.25F, 0.5F, 0.75F, 0.75F, 1.0F);
                break;

            case 3:
                this.func_71905_a(0.25F, 0.25F, 0.0F, 0.75F, 0.75F, 0.5F);
                break;

            case 4:
                this.func_71905_a(0.5F, 0.25F, 0.25F, 1.0F, 0.75F, 0.75F);
                break;

            case 5:
                this.func_71905_a(0.0F, 0.25F, 0.25F, 0.5F, 0.75F, 0.75F);
        }
    }

    public AxisAlignedBB func_71872_e(World p_71872_1_, int p_71872_2_, int p_71872_3_, int p_71872_4_)
    {
        this.func_71902_a(p_71872_1_, p_71872_2_, p_71872_3_, p_71872_4_);
        return super.func_71872_e(p_71872_1_, p_71872_2_, p_71872_3_, p_71872_4_);
    }

    public void func_71860_a(World p_71860_1_, int p_71860_2_, int p_71860_3_, int p_71860_4_, EntityLiving p_71860_5_, ItemStack p_71860_6_)
    {
        int l = MathHelper.func_76128_c((double)(p_71860_5_.field_70177_z * 4.0F / 360.0F) + 2.5D) & 3;
        p_71860_1_.func_72921_c(p_71860_2_, p_71860_3_, p_71860_4_, l, 2);
    }

    public TileEntity func_72274_a(World p_72274_1_)
    {
        return new TileEntitySkull();
    }

    public int func_71873_h(World p_71873_1_, int p_71873_2_, int p_71873_3_, int p_71873_4_)
    {
        TileEntity tileentity = p_71873_1_.func_72796_p(p_71873_2_, p_71873_3_, p_71873_4_);
        return tileentity != null && tileentity instanceof TileEntitySkull ? ((TileEntitySkull)tileentity).func_82117_a() : super.func_71873_h(p_71873_1_, p_71873_2_, p_71873_3_, p_71873_4_);
    }

    public int func_71899_b(int p_71899_1_)
    {
        return p_71899_1_;
    }

    // CraftBukkit start - special case dropping so we can get info from the tile entity
    public void func_71914_a(World p_71914_1_, int p_71914_2_, int p_71914_3_, int p_71914_4_, int p_71914_5_, float p_71914_6_, int p_71914_7_)
    {
        if (p_71914_1_.field_73012_v.nextFloat() < p_71914_6_)
        {
            ItemStack itemstack = new ItemStack(Item.field_82799_bQ.field_77779_bT, 1, this.func_71873_h(p_71914_1_, p_71914_2_, p_71914_3_, p_71914_4_));
            TileEntitySkull tileentityskull = (TileEntitySkull) p_71914_1_.func_72796_p(p_71914_2_, p_71914_3_, p_71914_4_);

            if (tileentityskull.func_82117_a() == 3 && tileentityskull.func_82120_c() != null && tileentityskull.func_82120_c().length() > 0)
            {
                itemstack.func_77982_d(new NBTTagCompound());
                itemstack.func_77978_p().func_74778_a("SkullOwner", tileentityskull.func_82120_c());
            }

            this.func_71929_a(p_71914_1_, p_71914_2_, p_71914_3_, p_71914_4_, itemstack);
        }
    }
    // CraftBukkit end

    public void func_71846_a(World p_71846_1_, int p_71846_2_, int p_71846_3_, int p_71846_4_, int p_71846_5_, EntityPlayer p_71846_6_)
    {
        if (p_71846_6_.field_71075_bZ.field_75098_d)
        {
            p_71846_5_ |= 8;
            p_71846_1_.func_72921_c(p_71846_2_, p_71846_3_, p_71846_4_, p_71846_5_, 4);
        }

        super.func_71846_a(p_71846_1_, p_71846_2_, p_71846_3_, p_71846_4_, p_71846_5_, p_71846_6_);
    }

    public void func_71852_a(World p_71852_1_, int p_71852_2_, int p_71852_3_, int p_71852_4_, int p_71852_5_, int p_71852_6_)
    {
        if (!p_71852_1_.field_72995_K)
        {
            /* CraftBukkit start - drop item in code above, not here
            if ((i1 & 8) == 0) {
                ItemStack itemstack = new ItemStack(Item.SKULL.id, 1, this.getDropData(world, i, j, k));
                TileEntitySkull tileentityskull = (TileEntitySkull) world.getTileEntity(i, j, k);

                if (tileentityskull.getSkullType() == 3 && tileentityskull.getExtraType() != null && tileentityskull.getExtraType().length() > 0) {
                    itemstack.setTag(new NBTTagCompound());
                    itemstack.getTag().setString("SkullOwner", tileentityskull.getExtraType());
                }

                this.b(world, i, j, k, itemstack);
            }
            // CraftBukkit end */
            super.func_71852_a(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_, p_71852_5_, p_71852_6_);
        }
    }

    public int func_71885_a(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
    {
        return Item.field_82799_bQ.field_77779_bT;
    }

    public void func_82529_a(World p_82529_1_, int p_82529_2_, int p_82529_3_, int p_82529_4_, TileEntitySkull p_82529_5_)
    {
        if (p_82529_5_.func_82117_a() == 1 && p_82529_3_ >= 2 && p_82529_1_.field_73013_u > 0 && !p_82529_1_.field_72995_K)
        {
            int l = Block.field_72013_bc.field_71990_ca;
            int i1;
            EntityWither entitywither;
            int j1;

            for (i1 = -2; i1 <= 0; ++i1)
            {
                if (p_82529_1_.func_72798_a(p_82529_2_, p_82529_3_ - 1, p_82529_4_ + i1) == l && p_82529_1_.func_72798_a(p_82529_2_, p_82529_3_ - 1, p_82529_4_ + i1 + 1) == l && p_82529_1_.func_72798_a(p_82529_2_, p_82529_3_ - 2, p_82529_4_ + i1 + 1) == l && p_82529_1_.func_72798_a(p_82529_2_, p_82529_3_ - 1, p_82529_4_ + i1 + 2) == l && this.func_82528_d(p_82529_1_, p_82529_2_, p_82529_3_, p_82529_4_ + i1, 1) && this.func_82528_d(p_82529_1_, p_82529_2_, p_82529_3_, p_82529_4_ + i1 + 1, 1) && this.func_82528_d(p_82529_1_, p_82529_2_, p_82529_3_, p_82529_4_ + i1 + 2, 1))
                {
                    // CraftBukkit start - use BlockStateListPopulator
                    BlockStateListPopulator blockList = new BlockStateListPopulator(p_82529_1_.getWorld());
                    p_82529_1_.func_72921_c(p_82529_2_, p_82529_3_, p_82529_4_ + i1, 8, 2);
                    p_82529_1_.func_72921_c(p_82529_2_, p_82529_3_, p_82529_4_ + i1 + 1, 8, 2);
                    p_82529_1_.func_72921_c(p_82529_2_, p_82529_3_, p_82529_4_ + i1 + 2, 8, 2);
                    blockList.setTypeId(p_82529_2_, p_82529_3_, p_82529_4_ + i1, 0);
                    blockList.setTypeId(p_82529_2_, p_82529_3_, p_82529_4_ + i1 + 1, 0);
                    blockList.setTypeId(p_82529_2_, p_82529_3_, p_82529_4_ + i1 + 2, 0);
                    blockList.setTypeId(p_82529_2_, p_82529_3_ - 1, p_82529_4_ + i1, 0);
                    blockList.setTypeId(p_82529_2_, p_82529_3_ - 1, p_82529_4_ + i1 + 1, 0);
                    blockList.setTypeId(p_82529_2_, p_82529_3_ - 1, p_82529_4_ + i1 + 2, 0);
                    blockList.setTypeId(p_82529_2_, p_82529_3_ - 2, p_82529_4_ + i1 + 1, 0);

                    if (!p_82529_1_.field_72995_K)
                    {
                        entitywither = new EntityWither(p_82529_1_);
                        entitywither.func_70012_b((double)p_82529_2_ + 0.5D, (double)p_82529_3_ - 1.45D, (double)(p_82529_4_ + i1) + 1.5D, 90.0F, 0.0F);
                        entitywither.field_70761_aq = 90.0F;
                        entitywither.func_82206_m();

                        if (p_82529_1_.addEntity(entitywither, SpawnReason.BUILD_WITHER))
                        {
                            blockList.updateList();
                        }
                    }

                    for (j1 = 0; j1 < 120; ++j1)
                    {
                        p_82529_1_.func_72869_a("snowballpoof", (double)p_82529_2_ + p_82529_1_.field_73012_v.nextDouble(), (double)(p_82529_3_ - 2) + p_82529_1_.field_73012_v.nextDouble() * 3.9D, (double)(p_82529_4_ + i1 + 1) + p_82529_1_.field_73012_v.nextDouble(), 0.0D, 0.0D, 0.0D);
                    }

                    // CraftBukkit end
                    return;
                }
            }

            for (i1 = -2; i1 <= 0; ++i1)
            {
                if (p_82529_1_.func_72798_a(p_82529_2_ + i1, p_82529_3_ - 1, p_82529_4_) == l && p_82529_1_.func_72798_a(p_82529_2_ + i1 + 1, p_82529_3_ - 1, p_82529_4_) == l && p_82529_1_.func_72798_a(p_82529_2_ + i1 + 1, p_82529_3_ - 2, p_82529_4_) == l && p_82529_1_.func_72798_a(p_82529_2_ + i1 + 2, p_82529_3_ - 1, p_82529_4_) == l && this.func_82528_d(p_82529_1_, p_82529_2_ + i1, p_82529_3_, p_82529_4_, 1) && this.func_82528_d(p_82529_1_, p_82529_2_ + i1 + 1, p_82529_3_, p_82529_4_, 1) && this.func_82528_d(p_82529_1_, p_82529_2_ + i1 + 2, p_82529_3_, p_82529_4_, 1))
                {
                    // CraftBukkit start - use BlockStateListPopulator
                    BlockStateListPopulator blockList = new BlockStateListPopulator(p_82529_1_.getWorld());
                    p_82529_1_.func_72921_c(p_82529_2_ + i1, p_82529_3_, p_82529_4_, 8, 2);
                    p_82529_1_.func_72921_c(p_82529_2_ + i1 + 1, p_82529_3_, p_82529_4_, 8, 2);
                    p_82529_1_.func_72921_c(p_82529_2_ + i1 + 2, p_82529_3_, p_82529_4_, 8, 2);
                    blockList.setTypeId(p_82529_2_ + i1, p_82529_3_, p_82529_4_, 0);
                    blockList.setTypeId(p_82529_2_ + i1 + 1, p_82529_3_, p_82529_4_, 0);
                    blockList.setTypeId(p_82529_2_ + i1 + 2, p_82529_3_, p_82529_4_, 0);
                    blockList.setTypeId(p_82529_2_ + i1, p_82529_3_ - 1, p_82529_4_, 0);
                    blockList.setTypeId(p_82529_2_ + i1 + 1, p_82529_3_ - 1, p_82529_4_, 0);
                    blockList.setTypeId(p_82529_2_ + i1 + 2, p_82529_3_ - 1, p_82529_4_, 0);
                    blockList.setTypeId(p_82529_2_ + i1 + 1, p_82529_3_ - 2, p_82529_4_, 0);

                    if (!p_82529_1_.field_72995_K)
                    {
                        entitywither = new EntityWither(p_82529_1_);
                        entitywither.func_70012_b((double)(p_82529_2_ + i1) + 1.5D, (double)p_82529_3_ - 1.45D, (double)p_82529_4_ + 0.5D, 0.0F, 0.0F);
                        entitywither.func_82206_m();

                        if (p_82529_1_.addEntity(entitywither, SpawnReason.BUILD_WITHER))
                        {
                            blockList.updateList();
                        }
                    }

                    for (j1 = 0; j1 < 120; ++j1)
                    {
                        p_82529_1_.func_72869_a("snowballpoof", (double)(p_82529_2_ + i1 + 1) + p_82529_1_.field_73012_v.nextDouble(), (double)(p_82529_3_ - 2) + p_82529_1_.field_73012_v.nextDouble() * 3.9D, (double)p_82529_4_ + p_82529_1_.field_73012_v.nextDouble(), 0.0D, 0.0D, 0.0D);
                    }

                    // CraftBukkit end
                    return;
                }
            }
        }
    }

    private boolean func_82528_d(World p_82528_1_, int p_82528_2_, int p_82528_3_, int p_82528_4_, int p_82528_5_)
    {
        if (p_82528_1_.func_72798_a(p_82528_2_, p_82528_3_, p_82528_4_) != this.field_71990_ca)
        {
            return false;
        }
        else
        {
            TileEntity tileentity = p_82528_1_.func_72796_p(p_82528_2_, p_82528_3_, p_82528_4_);
            return tileentity != null && tileentity instanceof TileEntitySkull ? ((TileEntitySkull)tileentity).func_82117_a() == p_82528_5_ : false;
        }
    }
}
