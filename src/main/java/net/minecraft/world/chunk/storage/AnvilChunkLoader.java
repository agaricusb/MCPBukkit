package net.minecraft.world.chunk.storage;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.NextTickListEntry;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.storage.IThreadedFileIO;
import net.minecraft.world.storage.ThreadedFileIOBase;

public class AnvilChunkLoader implements IThreadedFileIO, IChunkLoader
{
    private List field_75828_a = new ArrayList();
    private Set field_75826_b = new HashSet();
    private Object field_75827_c = new Object();
    private final File field_75825_d;

    public AnvilChunkLoader(File p_i3779_1_)
    {
        this.field_75825_d = p_i3779_1_;
    }

    // CraftBukkit start
    public boolean chunkExists(World world, int i, int j)
    {
        ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(i, j);

        synchronized (this.field_75827_c)
        {
            if (this.field_75826_b.contains(chunkcoordintpair))
            {
                for (int k = 0; k < this.field_75828_a.size(); ++k)
                {
                    if (((AnvilChunkLoaderPending)this.field_75828_a.get(k)).field_76548_a.equals(chunkcoordintpair))
                    {
                        return true;
                    }
                }
            }
        }

        return RegionFileCache.func_76550_a(this.field_75825_d, i, j).chunkExists(i & 31, j & 31);
    }
    // CraftBukkit end

    // CraftBukkit start - add async variant, provide compatibility
    public Chunk func_75815_a(World p_75815_1_, int p_75815_2_, int p_75815_3_)
    {
        Object[] data = this.loadChunk(p_75815_1_, p_75815_2_, p_75815_3_);

        if (data != null)
        {
            Chunk chunk = (Chunk) data[0];
            NBTTagCompound nbttagcompound = (NBTTagCompound) data[1];
            this.loadEntities(chunk, nbttagcompound.func_74775_l("Level"), p_75815_1_);
            return chunk;
        }

        return null;
    }

    public Object[] loadChunk(World world, int i, int j)
    {
        // CraftBukkit end
        NBTTagCompound nbttagcompound = null;
        ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(i, j);
        Object object = this.field_75827_c;

        synchronized (this.field_75827_c)
        {
            if (this.field_75826_b.contains(chunkcoordintpair))
            {
                for (int k = 0; k < this.field_75828_a.size(); ++k)
                {
                    if (((AnvilChunkLoaderPending)this.field_75828_a.get(k)).field_76548_a.equals(chunkcoordintpair))
                    {
                        nbttagcompound = ((AnvilChunkLoaderPending)this.field_75828_a.get(k)).field_76547_b;
                        break;
                    }
                }
            }
        }

        if (nbttagcompound == null)
        {
            DataInputStream datainputstream = RegionFileCache.func_76549_c(this.field_75825_d, i, j);

            if (datainputstream == null)
            {
                return null;
            }

            nbttagcompound = CompressedStreamTools.func_74794_a((DataInput) datainputstream);
        }

        return this.a(world, i, j, nbttagcompound);
    }

    protected Object[] a(World world, int i, int j, NBTTagCompound nbttagcompound)   // CraftBukkit - return Chunk -> Object[]
    {
        if (!nbttagcompound.func_74764_b("Level"))
        {
            System.out.println("Chunk file at " + i + "," + j + " is missing level data, skipping");
            return null;
        }
        else if (!nbttagcompound.func_74775_l("Level").func_74764_b("Sections"))
        {
            System.out.println("Chunk file at " + i + "," + j + " is missing block data, skipping");
            return null;
        }
        else
        {
            Chunk chunk = this.func_75823_a(world, nbttagcompound.func_74775_l("Level"));

            if (!chunk.func_76600_a(i, j))
            {
                System.out.println("Chunk file at " + i + "," + j + " is in the wrong location; relocating. (Expected " + i + ", " + j + ", got " + chunk.field_76635_g + ", " + chunk.field_76647_h + ")");
                nbttagcompound.func_74775_l("Level").func_74768_a("xPos", i); // CraftBukkit - .getCompound("Level")
                nbttagcompound.func_74775_l("Level").func_74768_a("zPos", j); // CraftBukkit - .getCompound("Level")
                chunk = this.func_75823_a(world, nbttagcompound.func_74775_l("Level"));
            }

            // CraftBukkit start
            Object[] data = new Object[2];
            data[0] = chunk;
            data[1] = nbttagcompound;
            return data;
            // CraftBukkit end
        }
    }

    public void func_75816_a(World p_75816_1_, Chunk p_75816_2_)
    {
        // CraftBukkit start - "handle" exception
        try
        {
            p_75816_1_.func_72906_B();
        }
        catch (MinecraftException ex)
        {
            ex.printStackTrace();
        }

        // CraftBukkit end

        try
        {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();
            nbttagcompound.func_74782_a("Level", nbttagcompound1);
            this.func_75820_a(p_75816_2_, p_75816_1_, nbttagcompound1);
            this.func_75824_a(p_75816_2_.func_76632_l(), nbttagcompound);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    protected void func_75824_a(ChunkCoordIntPair p_75824_1_, NBTTagCompound p_75824_2_)
    {
        Object object = this.field_75827_c;

        synchronized (this.field_75827_c)
        {
            if (this.field_75826_b.contains(p_75824_1_))
            {
                for (int i = 0; i < this.field_75828_a.size(); ++i)
                {
                    if (((AnvilChunkLoaderPending)this.field_75828_a.get(i)).field_76548_a.equals(p_75824_1_))
                    {
                        this.field_75828_a.set(i, new AnvilChunkLoaderPending(p_75824_1_, p_75824_2_));
                        return;
                    }
                }
            }

            this.field_75828_a.add(new AnvilChunkLoaderPending(p_75824_1_, p_75824_2_));
            this.field_75826_b.add(p_75824_1_);
            ThreadedFileIOBase.field_75741_a.func_75735_a(this);
        }
    }

    public boolean func_75814_c()
    {
        AnvilChunkLoaderPending anvilchunkloaderpending = null;
        Object object = this.field_75827_c;

        synchronized (this.field_75827_c)
        {
            if (this.field_75828_a.isEmpty())
            {
                return false;
            }

            anvilchunkloaderpending = (AnvilChunkLoaderPending)this.field_75828_a.remove(0);
            this.field_75826_b.remove(anvilchunkloaderpending.field_76548_a);
        }

        if (anvilchunkloaderpending != null)
        {
            try
            {
                this.func_75821_a(anvilchunkloaderpending);
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }

        return true;
    }

    public void func_75821_a(AnvilChunkLoaderPending p_75821_1_) throws java.io.IOException   // CraftBukkit - public -> private, added throws
    {
        DataOutputStream dataoutputstream = RegionFileCache.func_76552_d(this.field_75825_d, p_75821_1_.field_76548_a.field_77276_a, p_75821_1_.field_76548_a.field_77275_b);
        CompressedStreamTools.func_74800_a(p_75821_1_.field_76547_b, (DataOutput) dataoutputstream);
        dataoutputstream.close();
    }

    public void func_75819_b(World p_75819_1_, Chunk p_75819_2_) {}

    public void func_75817_a() {}

    public void func_75818_b() {}

    private void func_75820_a(Chunk p_75820_1_, World p_75820_2_, NBTTagCompound p_75820_3_)
    {
        p_75820_3_.func_74768_a("xPos", p_75820_1_.field_76635_g);
        p_75820_3_.func_74768_a("zPos", p_75820_1_.field_76647_h);
        p_75820_3_.func_74772_a("LastUpdate", p_75820_2_.func_82737_E());
        p_75820_3_.func_74783_a("HeightMap", p_75820_1_.field_76634_f);
        p_75820_3_.func_74757_a("TerrainPopulated", p_75820_1_.field_76646_k);
        ExtendedBlockStorage[] aextendedblockstorage = p_75820_1_.func_76587_i();
        NBTTagList nbttaglist = new NBTTagList("Sections");
        boolean flag = !p_75820_2_.field_73011_w.field_76576_e;
        ExtendedBlockStorage[] aextendedblockstorage1 = aextendedblockstorage;
        int i = aextendedblockstorage.length;
        NBTTagCompound nbttagcompound1;

        for (int j = 0; j < i; ++j)
        {
            ExtendedBlockStorage extendedblockstorage = aextendedblockstorage1[j];

            if (extendedblockstorage != null)
            {
                nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.func_74774_a("Y", (byte)(extendedblockstorage.func_76662_d() >> 4 & 255));
                nbttagcompound1.func_74773_a("Blocks", extendedblockstorage.func_76658_g());

                if (extendedblockstorage.func_76660_i() != null)
                {
                    nbttagcompound1.func_74773_a("Add", extendedblockstorage.func_76660_i().field_76585_a);
                }

                nbttagcompound1.func_74773_a("Data", extendedblockstorage.func_76669_j().field_76585_a);
                nbttagcompound1.func_74773_a("BlockLight", extendedblockstorage.func_76661_k().field_76585_a);

                if (flag)
                {
                    nbttagcompound1.func_74773_a("SkyLight", extendedblockstorage.func_76671_l().field_76585_a);
                }
                else
                {
                    nbttagcompound1.func_74773_a("SkyLight", new byte[extendedblockstorage.func_76661_k().field_76585_a.length]);
                }

                nbttaglist.func_74742_a(nbttagcompound1);
            }
        }

        p_75820_3_.func_74782_a("Sections", nbttaglist);
        p_75820_3_.func_74773_a("Biomes", p_75820_1_.func_76605_m());
        p_75820_1_.field_76644_m = false;
        NBTTagList nbttaglist1 = new NBTTagList();
        Iterator iterator;

        for (i = 0; i < p_75820_1_.field_76645_j.length; ++i)
        {
            iterator = p_75820_1_.field_76645_j[i].iterator();

            while (iterator.hasNext())
            {
                Entity entity = (Entity)iterator.next();
                p_75820_1_.field_76644_m = true;
                nbttagcompound1 = new NBTTagCompound();

                if (entity.func_70039_c(nbttagcompound1))
                {
                    nbttaglist1.func_74742_a(nbttagcompound1);
                }
            }
        }

        p_75820_3_.func_74782_a("Entities", nbttaglist1);
        NBTTagList nbttaglist2 = new NBTTagList();
        iterator = p_75820_1_.field_76648_i.values().iterator();

        while (iterator.hasNext())
        {
            TileEntity tileentity = (TileEntity)iterator.next();
            nbttagcompound1 = new NBTTagCompound();
            tileentity.func_70310_b(nbttagcompound1);
            nbttaglist2.func_74742_a(nbttagcompound1);
        }

        p_75820_3_.func_74782_a("TileEntities", nbttaglist2);
        List list = p_75820_2_.func_72920_a(p_75820_1_, false);

        if (list != null)
        {
            long k = p_75820_2_.func_82737_E();
            NBTTagList nbttaglist3 = new NBTTagList();
            Iterator iterator1 = list.iterator();

            while (iterator1.hasNext())
            {
                NextTickListEntry nextticklistentry = (NextTickListEntry)iterator1.next();
                NBTTagCompound nbttagcompound2 = new NBTTagCompound();
                nbttagcompound2.func_74768_a("i", nextticklistentry.field_77179_d);
                nbttagcompound2.func_74768_a("x", nextticklistentry.field_77183_a);
                nbttagcompound2.func_74768_a("y", nextticklistentry.field_77181_b);
                nbttagcompound2.func_74768_a("z", nextticklistentry.field_77182_c);
                nbttagcompound2.func_74768_a("t", (int)(nextticklistentry.field_77180_e - k));
                nbttaglist3.func_74742_a(nbttagcompound2);
            }

            p_75820_3_.func_74782_a("TileTicks", nbttaglist3);
        }
    }

    private Chunk func_75823_a(World p_75823_1_, NBTTagCompound p_75823_2_)
    {
        int i = p_75823_2_.func_74762_e("xPos");
        int j = p_75823_2_.func_74762_e("zPos");
        Chunk chunk = new Chunk(p_75823_1_, i, j);
        chunk.field_76634_f = p_75823_2_.func_74759_k("HeightMap");
        chunk.field_76646_k = p_75823_2_.func_74767_n("TerrainPopulated");
        NBTTagList nbttaglist = p_75823_2_.func_74761_m("Sections");
        byte b0 = 16;
        ExtendedBlockStorage[] aextendedblockstorage = new ExtendedBlockStorage[b0];
        boolean flag = !p_75823_1_.field_73011_w.field_76576_e;

        for (int k = 0; k < nbttaglist.func_74745_c(); ++k)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.func_74743_b(k);
            byte b1 = nbttagcompound1.func_74771_c("Y");
            ExtendedBlockStorage extendedblockstorage = new ExtendedBlockStorage(b1 << 4, flag);
            extendedblockstorage.func_76664_a(nbttagcompound1.func_74770_j("Blocks"));

            if (nbttagcompound1.func_74764_b("Add"))
            {
                extendedblockstorage.func_76673_a(new NibbleArray(nbttagcompound1.func_74770_j("Add"), 4));
            }

            extendedblockstorage.func_76668_b(new NibbleArray(nbttagcompound1.func_74770_j("Data"), 4));
            extendedblockstorage.func_76659_c(new NibbleArray(nbttagcompound1.func_74770_j("BlockLight"), 4));

            if (flag)
            {
                extendedblockstorage.func_76666_d(new NibbleArray(nbttagcompound1.func_74770_j("SkyLight"), 4));
            }

            extendedblockstorage.func_76672_e();
            aextendedblockstorage[b1] = extendedblockstorage;
        }

        chunk.func_76602_a(aextendedblockstorage);

        if (p_75823_2_.func_74764_b("Biomes"))
        {
            chunk.func_76616_a(p_75823_2_.func_74770_j("Biomes"));
        }

        // CraftBukkit start - end this method here and split off entity loading to another method
        return chunk;
    }

    public void loadEntities(Chunk chunk, NBTTagCompound nbttagcompound, World world)
    {
        // CraftBukkit end
        NBTTagList nbttaglist1 = nbttagcompound.func_74761_m("Entities");

        if (nbttaglist1 != null)
        {
            for (int l = 0; l < nbttaglist1.func_74745_c(); ++l)
            {
                NBTTagCompound nbttagcompound2 = (NBTTagCompound)nbttaglist1.func_74743_b(l);
                Entity entity = EntityList.func_75615_a(nbttagcompound2, world);
                chunk.field_76644_m = true;

                if (entity != null)
                {
                    chunk.func_76612_a(entity);
                }
            }
        }

        NBTTagList nbttaglist2 = nbttagcompound.func_74761_m("TileEntities");

        if (nbttaglist2 != null)
        {
            for (int i1 = 0; i1 < nbttaglist2.func_74745_c(); ++i1)
            {
                NBTTagCompound nbttagcompound3 = (NBTTagCompound)nbttaglist2.func_74743_b(i1);
                TileEntity tileentity = TileEntity.func_70317_c(nbttagcompound3);

                if (tileentity != null)
                {
                    chunk.func_76620_a(tileentity);
                }
            }
        }

        if (nbttagcompound.func_74764_b("TileTicks"))
        {
            NBTTagList nbttaglist3 = nbttagcompound.func_74761_m("TileTicks");

            if (nbttaglist3 != null)
            {
                for (int j1 = 0; j1 < nbttaglist3.func_74745_c(); ++j1)
                {
                    NBTTagCompound nbttagcompound4 = (NBTTagCompound)nbttaglist3.func_74743_b(j1);
                    world.func_72892_b(nbttagcompound4.func_74762_e("x"), nbttagcompound4.func_74762_e("y"), nbttagcompound4.func_74762_e("z"), nbttagcompound4.func_74762_e("i"), nbttagcompound4.func_74762_e("t"));
                }
            }
        }

        // return chunk; // CraftBukkit
    }
}
