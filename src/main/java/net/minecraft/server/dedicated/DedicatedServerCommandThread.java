package net.minecraft.server.dedicated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import net.minecraft.server.MinecraftServer;

import static org.bukkit.craftbukkit.Main.*; // CraftBukkit

class DedicatedServerCommandThread extends Thread
{
    final DedicatedServer field_72428_a;

    DedicatedServerCommandThread(DedicatedServer p_i3380_1_)
    {
        this.field_72428_a = p_i3380_1_;
    }

    public void run()
    {
        // CraftBukkit start
        if (!useConsole)
        {
            return;
        }

        // CraftBukkit end
        jline.console.ConsoleReader bufferedreader = this.field_72428_a.reader; // CraftBukkit
        String s;

        try
        {
            // CraftBukkit start - JLine disabling compatibility
            while (!this.field_72428_a.func_71241_aa() && this.field_72428_a.func_71278_l())
            {
                if (useJline)
                {
                    s = bufferedreader.readLine(">", null);
                }
                else
                {
                    s = bufferedreader.readLine();
                }

                if (s != null)
                {
                    this.field_72428_a.func_71331_a(s, this.field_72428_a);
                }

                // CraftBukkit end
            }
        }
        catch (IOException ioexception)
        {
            // CraftBukkit
            MinecraftServer.field_71306_a.log(java.util.logging.Level.SEVERE, null, ioexception);
        }
    }
}
