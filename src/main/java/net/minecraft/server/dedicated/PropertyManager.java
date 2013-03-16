package net.minecraft.server.dedicated;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.logging.ILogAgent;

import joptsimple.OptionSet; // CraftBukkit

public class PropertyManager
{
    public final Properties field_73672_b = new Properties(); // CraftBukkit - private -> public
    private final ILogAgent field_73674_a;
    private final File field_73673_c;

    public PropertyManager(File p_i11028_1_, ILogAgent p_i11028_2_)
    {
        this.field_73673_c = p_i11028_1_;
        this.field_73674_a = p_i11028_2_;

        if (p_i11028_1_.exists())
        {
            FileInputStream fileinputstream = null;

            try
            {
                fileinputstream = new FileInputStream(p_i11028_1_);
                this.field_73672_b.load(fileinputstream);
            }
            catch (Exception exception)
            {
                p_i11028_2_.func_98235_b("Failed to load " + p_i11028_1_, exception);
                this.func_73666_a();
            }
            finally
            {
                if (fileinputstream != null)
                {
                    try
                    {
                        fileinputstream.close();
                    }
                    catch (IOException ioexception)
                    {
                        ;
                    }
                }
            }
        }
        else
        {
            p_i11028_2_.func_98236_b(p_i11028_1_ + " does not exist");
            this.func_73666_a();
        }
    }

    // CraftBukkit start
    private OptionSet options = null;

    public PropertyManager(final OptionSet options, ILogAgent ilogagent)
    {
        this((File) options.valueOf("config"), ilogagent);
        this.options = options;
    }

    private <T> T getOverride(String name, T value)
    {
        if ((this.options != null) && (this.options.has(name)))
        {
            return (T) this.options.valueOf(name);
        }

        return value;
    }
    // CraftBukkit end

    public void func_73666_a()
    {
        this.field_73674_a.func_98233_a("Generating new properties file");
        this.func_73668_b();
    }

    public void func_73668_b()
    {
        FileOutputStream fileoutputstream = null;

        try
        {
            // CraftBukkit start - Don't attempt writing to file if it's read only
            if (this.field_73673_c.exists() && !this.field_73673_c.canWrite())
            {
                return;
            }

            // CraftBukkit end
            fileoutputstream = new FileOutputStream(this.field_73673_c);
            this.field_73672_b.store(fileoutputstream, "Minecraft server properties");
        }
        catch (Exception exception)
        {
            this.field_73674_a.func_98235_b("Failed to save " + this.field_73673_c, exception);
            this.func_73666_a();
        }
        finally
        {
            if (fileoutputstream != null)
            {
                try
                {
                    fileoutputstream.close();
                }
                catch (IOException ioexception)
                {
                    ;
                }
            }
        }
    }

    public File func_73665_c()
    {
        return this.field_73673_c;
    }

    public String func_73671_a(String p_73671_1_, String p_73671_2_)
    {
        if (!this.field_73672_b.containsKey(p_73671_1_))
        {
            this.field_73672_b.setProperty(p_73671_1_, p_73671_2_);
            this.func_73668_b();
        }

        return this.getOverride(p_73671_1_, this.field_73672_b.getProperty(p_73671_1_, p_73671_2_)); // CraftBukkit
    }

    public int func_73669_a(String p_73669_1_, int p_73669_2_)
    {
        try
        {
            return this.getOverride(p_73669_1_, Integer.parseInt(this.func_73671_a(p_73669_1_, "" + p_73669_2_))); // CraftBukkit
        }
        catch (Exception exception)
        {
            this.field_73672_b.setProperty(p_73669_1_, "" + p_73669_2_);
            return this.getOverride(p_73669_1_, p_73669_2_); // CraftBukkit
        }
    }

    public boolean func_73670_a(String p_73670_1_, boolean p_73670_2_)
    {
        try
        {
            return this.getOverride(p_73670_1_, Boolean.parseBoolean(this.func_73671_a(p_73670_1_, "" + p_73670_2_))); // CraftBukkit
        }
        catch (Exception exception)
        {
            this.field_73672_b.setProperty(p_73670_1_, "" + p_73670_2_);
            return this.getOverride(p_73670_1_, p_73670_2_); // CraftBukkit
        }
    }

    public void func_73667_a(String p_73667_1_, Object p_73667_2_)
    {
        this.field_73672_b.setProperty(p_73667_1_, "" + p_73667_2_);
    }
}
