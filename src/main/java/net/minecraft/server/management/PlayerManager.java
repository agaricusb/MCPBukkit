package net.minecraft.server.management;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.LongHashMap;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;

// CraftBukkit start
import java.util.Collections;
import java.util.Queue;
import java.util.Iterator;
import java.util.LinkedList;
// CraftBukkit end

public class PlayerManager
{
    private final WorldServer field_72701_a;
    private final List field_72699_b = new ArrayList();
    private final LongHashMap field_72700_c = new LongHashMap();
    private final Queue field_72697_d = new java.util.concurrent.ConcurrentLinkedQueue(); // CraftBukkit ArrayList -> ConcurrentLinkedQueue
    private final int field_72698_e;
    private final int[][] field_72696_f = new int[][] {{1, 0}, {0, 1}, { -1, 0}, {0, -1}};
    private boolean wasNotEmpty; // CraftBukkit

    public PlayerManager(WorldServer p_i3392_1_, int p_i3392_2_)
    {
        if (p_i3392_2_ > 15)
        {
            throw new IllegalArgumentException("Too big view radius!");
        }
        else if (p_i3392_2_ < 3)
        {
            throw new IllegalArgumentException("Too small view radius!");
        }
        else
        {
            this.field_72698_e = p_i3392_2_;
            this.field_72701_a = p_i3392_1_;
        }
    }

    public WorldServer func_72688_a()
    {
        return this.field_72701_a;
    }

    public void func_72693_b()
    {
        // CraftBukkit start - use iterator
        Iterator iterator = this.field_72697_d.iterator();

        while (iterator.hasNext())
        {
            PlayerInstance playerinstance = (PlayerInstance) iterator.next();
            playerinstance.func_73254_a();
            iterator.remove();
        }

        // CraftBukkit end

        // this.d.clear(); // CraftBukkit - removals are already covered
        if (this.field_72699_b.isEmpty())
        {
            if (!wasNotEmpty)
            {
                return;    // CraftBukkit - only do unload when we go from non-empty to empty
            }

            WorldProvider worldprovider = this.field_72701_a.field_73011_w;

            if (!worldprovider.func_76567_e())
            {
                this.field_72701_a.field_73059_b.func_73240_a();
            }

            // CraftBukkit start
            wasNotEmpty = false;
        }
        else
        {
            wasNotEmpty = true;
        }

        // CraftBukkit end
    }

    private PlayerInstance func_72690_a(int p_72690_1_, int p_72690_2_, boolean p_72690_3_)
    {
        long k = (long)p_72690_1_ + 2147483647L | (long)p_72690_2_ + 2147483647L << 32;
        PlayerInstance playerinstance = (PlayerInstance)this.field_72700_c.func_76164_a(k);

        if (playerinstance == null && p_72690_3_)
        {
            playerinstance = new PlayerInstance(this, p_72690_1_, p_72690_2_);
            this.field_72700_c.func_76163_a(k, playerinstance);
        }

        return playerinstance;
    }
    // CraftBukkit start
    public final boolean isChunkInUse(int x, int z)
    {
        PlayerInstance pi = func_72690_a(x, z, false);

        if (pi != null)
        {
            return (PlayerInstance.func_73258_b(pi).size() > 0);
        }

        return false;
    }
    // CraftBukkit end

    public void func_72687_a(int p_72687_1_, int p_72687_2_, int p_72687_3_)
    {
        int l = p_72687_1_ >> 4;
        int i1 = p_72687_3_ >> 4;
        PlayerInstance playerinstance = this.func_72690_a(l, i1, false);

        if (playerinstance != null)
        {
            playerinstance.func_73259_a(p_72687_1_ & 15, p_72687_2_, p_72687_3_ & 15);
        }
    }

    public void func_72683_a(EntityPlayerMP p_72683_1_)
    {
        int i = (int)p_72683_1_.field_70165_t >> 4;
        int j = (int)p_72683_1_.field_70161_v >> 4;
        p_72683_1_.field_71131_d = p_72683_1_.field_70165_t;
        p_72683_1_.field_71132_e = p_72683_1_.field_70161_v;
        // CraftBukkit start - load nearby chunks first
        List<ChunkCoordIntPair> chunkList = new LinkedList<ChunkCoordIntPair>();

        for (int k = i - this.field_72698_e; k <= i + this.field_72698_e; ++k)
        {
            for (int l = j - this.field_72698_e; l <= j + this.field_72698_e; ++l)
            {
                chunkList.add(new ChunkCoordIntPair(k, l));
            }
        }

        Collections.sort(chunkList, new ChunkCoordComparator(p_72683_1_));

        for (ChunkCoordIntPair pair : chunkList)
        {
            this.func_72690_a(pair.field_77276_a, pair.field_77275_b, true).func_73255_a(p_72683_1_);
        }

        // CraftBukkit end
        this.field_72699_b.add(p_72683_1_);
        this.func_72691_b(p_72683_1_);
    }

    public void func_72691_b(EntityPlayerMP p_72691_1_)
    {
        ArrayList arraylist = new ArrayList(p_72691_1_.field_71129_f);
        int i = 0;
        int j = this.field_72698_e;
        int k = (int)p_72691_1_.field_70165_t >> 4;
        int l = (int)p_72691_1_.field_70161_v >> 4;
        int i1 = 0;
        int j1 = 0;
        ChunkCoordIntPair chunkcoordintpair = PlayerInstance.func_73253_a(this.func_72690_a(k, l, true));
        p_72691_1_.field_71129_f.clear();

        if (arraylist.contains(chunkcoordintpair))
        {
            p_72691_1_.field_71129_f.add(chunkcoordintpair);
        }

        int k1;

        for (k1 = 1; k1 <= j * 2; ++k1)
        {
            for (int l1 = 0; l1 < 2; ++l1)
            {
                int[] aint = this.field_72696_f[i++ % 4];

                for (int i2 = 0; i2 < k1; ++i2)
                {
                    i1 += aint[0];
                    j1 += aint[1];
                    chunkcoordintpair = PlayerInstance.func_73253_a(this.func_72690_a(k + i1, l + j1, true));

                    if (arraylist.contains(chunkcoordintpair))
                    {
                        p_72691_1_.field_71129_f.add(chunkcoordintpair);
                    }
                }
            }
        }

        i %= 4;

        for (k1 = 0; k1 < j * 2; ++k1)
        {
            i1 += this.field_72696_f[i][0];
            j1 += this.field_72696_f[i][1];
            chunkcoordintpair = PlayerInstance.func_73253_a(this.func_72690_a(k + i1, l + j1, true));

            if (arraylist.contains(chunkcoordintpair))
            {
                p_72691_1_.field_71129_f.add(chunkcoordintpair);
            }
        }
    }

    public void func_72695_c(EntityPlayerMP p_72695_1_)
    {
        int i = (int)p_72695_1_.field_71131_d >> 4;
        int j = (int)p_72695_1_.field_71132_e >> 4;

        for (int k = i - this.field_72698_e; k <= i + this.field_72698_e; ++k)
        {
            for (int l = j - this.field_72698_e; l <= j + this.field_72698_e; ++l)
            {
                PlayerInstance playerinstance = this.func_72690_a(k, l, false);

                if (playerinstance != null)
                {
                    playerinstance.func_73252_b(p_72695_1_);
                }
            }
        }

        this.field_72699_b.remove(p_72695_1_);
    }

    private boolean func_72684_a(int p_72684_1_, int p_72684_2_, int p_72684_3_, int p_72684_4_, int p_72684_5_)
    {
        int j1 = p_72684_1_ - p_72684_3_;
        int k1 = p_72684_2_ - p_72684_4_;
        return j1 >= -p_72684_5_ && j1 <= p_72684_5_ ? k1 >= -p_72684_5_ && k1 <= p_72684_5_ : false;
    }

    public void func_72685_d(EntityPlayerMP p_72685_1_)
    {
        int i = (int)p_72685_1_.field_70165_t >> 4;
        int j = (int)p_72685_1_.field_70161_v >> 4;
        double d0 = p_72685_1_.field_71131_d - p_72685_1_.field_70165_t;
        double d1 = p_72685_1_.field_71132_e - p_72685_1_.field_70161_v;
        double d2 = d0 * d0 + d1 * d1;

        if (d2 >= 64.0D)
        {
            int k = (int)p_72685_1_.field_71131_d >> 4;
            int l = (int)p_72685_1_.field_71132_e >> 4;
            int i1 = this.field_72698_e;
            int j1 = i - k;
            int k1 = j - l;
            List<ChunkCoordIntPair> chunksToLoad = new LinkedList<ChunkCoordIntPair>(); // CraftBukkit

            if (j1 != 0 || k1 != 0)
            {
                for (int l1 = i - i1; l1 <= i + i1; ++l1)
                {
                    for (int i2 = j - i1; i2 <= j + i1; ++i2)
                    {
                        if (!this.func_72684_a(l1, i2, k, l, i1))
                        {
                            chunksToLoad.add(new ChunkCoordIntPair(l1, i2)); // CraftBukkit
                        }

                        if (!this.func_72684_a(l1 - j1, i2 - k1, i, j, i1))
                        {
                            PlayerInstance playerinstance = this.func_72690_a(l1 - j1, i2 - k1, false);

                            if (playerinstance != null)
                            {
                                playerinstance.func_73252_b(p_72685_1_);
                            }
                        }
                    }
                }

                this.func_72691_b(p_72685_1_);
                p_72685_1_.field_71131_d = p_72685_1_.field_70165_t;
                p_72685_1_.field_71132_e = p_72685_1_.field_70161_v;
                // CraftBukkit start - send nearest chunks first
                Collections.sort(chunksToLoad, new ChunkCoordComparator(p_72685_1_));

                for (ChunkCoordIntPair pair : chunksToLoad)
                {
                    this.func_72690_a(pair.field_77276_a, pair.field_77275_b, true).func_73255_a(p_72685_1_);
                }

                if (i1 > 1 || i1 < -1 || j1 > 1 || j1 < -1)
                {
                    Collections.sort(p_72685_1_.field_71129_f, new ChunkCoordComparator(p_72685_1_));
                }

                // CraftBukkit end
            }
        }
    }

    public boolean func_72694_a(EntityPlayerMP p_72694_1_, int p_72694_2_, int p_72694_3_)
    {
        PlayerInstance playerinstance = this.func_72690_a(p_72694_2_, p_72694_3_, false);
        return playerinstance == null ? false : PlayerInstance.func_73258_b(playerinstance).contains(p_72694_1_) && !p_72694_1_.field_71129_f.contains(PlayerInstance.func_73253_a(playerinstance));
    }

    public static int func_72686_a(int p_72686_0_)
    {
        return p_72686_0_ * 16 - 16;
    }

    static WorldServer func_72692_a(PlayerManager p_72692_0_)
    {
        return p_72692_0_.field_72701_a;
    }

    static LongHashMap func_72689_b(PlayerManager p_72689_0_)
    {
        return p_72689_0_.field_72700_c;
    }

    static Queue c(PlayerManager playermanager)   // CraftBukkit List -> Queue
    {
        return playermanager.field_72697_d;
    }

    // CraftBukkit start - sorter to load nearby chunks first
    private static class ChunkCoordComparator implements java.util.Comparator<ChunkCoordIntPair>
    {
        private int x;
        private int z;

        public ChunkCoordComparator(EntityPlayerMP entityplayer)
        {
            x = (int) entityplayer.field_70165_t >> 4;
            z = (int) entityplayer.field_70161_v >> 4;
        }

        public int compare(ChunkCoordIntPair a, ChunkCoordIntPair b)
        {
            if (a.equals(b))
            {
                return 0;
            }

            // Subtract current position to set center point
            int ax = a.field_77276_a - this.x;
            int az = a.field_77275_b - this.z;
            int bx = b.field_77276_a - this.x;
            int bz = b.field_77275_b - this.z;
            int result = ((ax - bx) * (ax + bx)) + ((az - bz) * (az + bz));

            if (result != 0)
            {
                return result;
            }

            if (ax < 0)
            {
                if (bx < 0)
                {
                    return bz - az;
                }
                else
                {
                    return -1;
                }
            }
            else
            {
                if (bx < 0)
                {
                    return 1;
                }
                else
                {
                    return az - bz;
                }
            }
        }
    }
    // CraftBukkit end
}
