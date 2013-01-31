package net.minecraft.world.storage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.storage.IChunkLoader;

// CraftBukkit start
import java.util.UUID;

import org.bukkit.craftbukkit.entity.CraftPlayer;
// CraftBukkit end

public class SaveHandler implements ISaveHandler, IPlayerFileData
{
    private static final Logger field_75772_a = Logger.getLogger("Minecraft");
    private final File field_75770_b;
    private final File field_75771_c;
    private final File field_75768_d;
    private final long field_75769_e = System.currentTimeMillis();
    private final String field_75767_f;
    private UUID uuid = null; // CraftBukkit

    public SaveHandler(File p_i3912_1_, String p_i3912_2_, boolean p_i3912_3_)
    {
        this.field_75770_b = new File(p_i3912_1_, p_i3912_2_);
        this.field_75770_b.mkdirs();
        this.field_75771_c = new File(this.field_75770_b, "players");
        this.field_75768_d = new File(this.field_75770_b, "data");
        this.field_75768_d.mkdirs();
        this.field_75767_f = p_i3912_2_;

        if (p_i3912_3_)
        {
            this.field_75771_c.mkdirs();
        }

        this.func_75766_h();
    }

    private void func_75766_h()
    {
        try
        {
            File file1 = new File(this.field_75770_b, "session.lock");
            DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(file1));

            try
            {
                dataoutputstream.writeLong(this.field_75769_e);
            }
            finally
            {
                dataoutputstream.close();
            }
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
            throw new RuntimeException("Failed to check session lock, aborting");
        }
    }

    public File func_75765_b()   // CraftBukkit - protected to public
    {
        return this.field_75770_b;
    }

    public void func_75762_c() throws MinecraftException   // CraftBukkit - throws ExceptionWorldConflict
    {
        try
        {
            File file1 = new File(this.field_75770_b, "session.lock");
            DataInputStream datainputstream = new DataInputStream(new FileInputStream(file1));

            try
            {
                if (datainputstream.readLong() != this.field_75769_e)
                {
                    throw new MinecraftException("The save is being accessed from another location, aborting");
                }
            }
            finally
            {
                datainputstream.close();
            }
        }
        catch (IOException ioexception)
        {
            throw new MinecraftException("Failed to check session lock, aborting");
        }
    }

    public IChunkLoader func_75763_a(WorldProvider p_75763_1_)
    {
        throw new RuntimeException("Old Chunk Storage is no longer supported.");
    }

    public WorldInfo func_75757_d()
    {
        File file1 = new File(this.field_75770_b, "level.dat");
        NBTTagCompound nbttagcompound;
        NBTTagCompound nbttagcompound1;

        if (file1.exists())
        {
            try
            {
                nbttagcompound = CompressedStreamTools.func_74796_a((InputStream)(new FileInputStream(file1)));
                nbttagcompound1 = nbttagcompound.func_74775_l("Data");
                return new WorldInfo(nbttagcompound1);
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }

        file1 = new File(this.field_75770_b, "level.dat_old");

        if (file1.exists())
        {
            try
            {
                nbttagcompound = CompressedStreamTools.func_74796_a((InputStream)(new FileInputStream(file1)));
                nbttagcompound1 = nbttagcompound.func_74775_l("Data");
                return new WorldInfo(nbttagcompound1);
            }
            catch (Exception exception1)
            {
                exception1.printStackTrace();
            }
        }

        return null;
    }

    public void func_75755_a(WorldInfo p_75755_1_, NBTTagCompound p_75755_2_)
    {
        NBTTagCompound nbttagcompound1 = p_75755_1_.func_76082_a(p_75755_2_);
        NBTTagCompound nbttagcompound2 = new NBTTagCompound();
        nbttagcompound2.func_74782_a("Data", nbttagcompound1);

        try
        {
            File file1 = new File(this.field_75770_b, "level.dat_new");
            File file2 = new File(this.field_75770_b, "level.dat_old");
            File file3 = new File(this.field_75770_b, "level.dat");
            CompressedStreamTools.func_74799_a(nbttagcompound2, (OutputStream)(new FileOutputStream(file1)));

            if (file2.exists())
            {
                file2.delete();
            }

            file3.renameTo(file2);

            if (file3.exists())
            {
                file3.delete();
            }

            file1.renameTo(file3);

            if (file1.exists())
            {
                file1.delete();
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public void func_75761_a(WorldInfo p_75761_1_)
    {
        NBTTagCompound nbttagcompound = p_75761_1_.func_76066_a();
        NBTTagCompound nbttagcompound1 = new NBTTagCompound();
        nbttagcompound1.func_74782_a("Data", nbttagcompound);

        try
        {
            File file1 = new File(this.field_75770_b, "level.dat_new");
            File file2 = new File(this.field_75770_b, "level.dat_old");
            File file3 = new File(this.field_75770_b, "level.dat");
            CompressedStreamTools.func_74799_a(nbttagcompound1, (OutputStream)(new FileOutputStream(file1)));

            if (file2.exists())
            {
                file2.delete();
            }

            file3.renameTo(file2);

            if (file3.exists())
            {
                file3.delete();
            }

            file1.renameTo(file3);

            if (file1.exists())
            {
                file1.delete();
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public void func_75753_a(EntityPlayer p_75753_1_)
    {
        try
        {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            p_75753_1_.func_70109_d(nbttagcompound);
            File file1 = new File(this.field_75771_c, p_75753_1_.field_71092_bJ + ".dat.tmp");
            File file2 = new File(this.field_75771_c, p_75753_1_.field_71092_bJ + ".dat");
            CompressedStreamTools.func_74799_a(nbttagcompound, (OutputStream)(new FileOutputStream(file1)));

            if (file2.exists())
            {
                file2.delete();
            }

            file1.renameTo(file2);
        }
        catch (Exception exception)
        {
            field_75772_a.warning("Failed to save player data for " + p_75753_1_.field_71092_bJ);
        }
    }

    public void func_75752_b(EntityPlayer p_75752_1_)
    {
        NBTTagCompound nbttagcompound = this.func_75764_a(p_75752_1_.field_71092_bJ);

        if (nbttagcompound != null)
        {
            // CraftBukkit start
            if (p_75752_1_ instanceof EntityPlayerMP)
            {
                CraftPlayer player = (CraftPlayer) p_75752_1_.bukkitEntity;
                player.setFirstPlayed(new File(field_75771_c, p_75752_1_.field_71092_bJ + ".dat").lastModified());
            }

            // CraftBukkit end
            p_75752_1_.func_70020_e(nbttagcompound);
        }
    }

    public NBTTagCompound func_75764_a(String p_75764_1_)
    {
        try
        {
            File file1 = new File(this.field_75771_c, p_75764_1_ + ".dat");

            if (file1.exists())
            {
                return CompressedStreamTools.func_74796_a((InputStream)(new FileInputStream(file1)));
            }
        }
        catch (Exception exception)
        {
            field_75772_a.warning("Failed to load player data for " + p_75764_1_);
        }

        return null;
    }

    public IPlayerFileData func_75756_e()
    {
        return this;
    }

    public String[] func_75754_f()
    {
        String[] astring = this.field_75771_c.list();

        for (int i = 0; i < astring.length; ++i)
        {
            if (astring[i].endsWith(".dat"))
            {
                astring[i] = astring[i].substring(0, astring[i].length() - 4);
            }
        }

        return astring;
    }

    public void func_75759_a() {}

    public File func_75758_b(String p_75758_1_)
    {
        return new File(this.field_75768_d, p_75758_1_ + ".dat");
    }

    public String func_75760_g()
    {
        return this.field_75767_f;
    }

    // CraftBukkit start
    public UUID getUUID()
    {
        if (uuid != null)
        {
            return uuid;
        }

        try
        {
            File file1 = new File(this.field_75770_b, "uid.dat");

            if (!file1.exists())
            {
                DataOutputStream dos = new DataOutputStream(new FileOutputStream(file1));
                uuid = UUID.randomUUID();
                dos.writeLong(uuid.getMostSignificantBits());
                dos.writeLong(uuid.getLeastSignificantBits());
                dos.close();
            }
            else
            {
                DataInputStream dis = new DataInputStream(new FileInputStream(file1));
                uuid = new UUID(dis.readLong(), dis.readLong());
                dis.close();
            }

            return uuid;
        }
        catch (IOException ex)
        {
            return null;
        }
    }

    public File getPlayerDir()
    {
        return field_75771_c;
    }
    // CraftBukkit end
}
