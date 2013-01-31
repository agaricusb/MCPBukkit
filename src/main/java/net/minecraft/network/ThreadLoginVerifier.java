package net.minecraft.network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLEncoder;
import net.minecraft.util.CryptManager;

// CraftBukkit start
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.util.Waitable;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;
// CraftBukkit end

class ThreadLoginVerifier extends Thread
{
    final NetLoginHandler field_72590_a;

    // CraftBukkit start
    CraftServer server;

    ThreadLoginVerifier(NetLoginHandler pendingconnection, CraftServer server)
    {
        this.server = server;
        // CraftBukkit end
        this.field_72590_a = pendingconnection;
    }

    public void run()
    {
        try
        {
            String s = (new BigInteger(CryptManager.func_75895_a(NetLoginHandler.func_72526_a(this.field_72590_a), NetLoginHandler.func_72530_b(this.field_72590_a).func_71250_E().getPublic(), NetLoginHandler.func_72525_c(this.field_72590_a)))).toString(16);
            URL url = new URL("http://session.minecraft.net/game/checkserver.jsp?user=" + URLEncoder.encode(NetLoginHandler.func_72533_d(this.field_72590_a), "UTF-8") + "&serverId=" + URLEncoder.encode(s, "UTF-8"));
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(url.openStream()));
            String s1 = bufferedreader.readLine();
            bufferedreader.close();

            if (!"YES".equals(s1))
            {
                this.field_72590_a.func_72527_a("Failed to verify username!");
                return;
            }

            // CraftBukkit start
            if (this.field_72590_a.getSocket() == null)
            {
                return;
            }

            AsyncPlayerPreLoginEvent asyncEvent = new AsyncPlayerPreLoginEvent(NetLoginHandler.func_72533_d(this.field_72590_a), this.field_72590_a.getSocket().getInetAddress());
            this.server.getPluginManager().callEvent(asyncEvent);

            if (PlayerPreLoginEvent.getHandlerList().getRegisteredListeners().length != 0)
            {
                final PlayerPreLoginEvent event = new PlayerPreLoginEvent(NetLoginHandler.func_72533_d(this.field_72590_a), this.field_72590_a.getSocket().getInetAddress());

                if (asyncEvent.getResult() != PlayerPreLoginEvent.Result.ALLOWED)
                {
                    event.disallow(asyncEvent.getResult(), asyncEvent.getKickMessage());
                }

                Waitable<PlayerPreLoginEvent.Result> waitable = new Waitable<PlayerPreLoginEvent.Result>()
                {
                    @Override
                    protected PlayerPreLoginEvent.Result evaluate()
                    {
                        ThreadLoginVerifier.this.server.getPluginManager().callEvent(event);
                        return event.getResult();
                    }
                };
                NetLoginHandler.func_72530_b(this.field_72590_a).processQueue.add(waitable);

                if (waitable.get() != PlayerPreLoginEvent.Result.ALLOWED)
                {
                    this.field_72590_a.func_72527_a(event.getKickMessage());
                    return;
                }
            }
            else
            {
                if (asyncEvent.getLoginResult() != AsyncPlayerPreLoginEvent.Result.ALLOWED)
                {
                    this.field_72590_a.func_72527_a(asyncEvent.getKickMessage());
                    return;
                }
            }

            // CraftBukkit end
            NetLoginHandler.func_72531_a(this.field_72590_a, true);
            // CraftBukkit start
        }
        catch (java.io.IOException exception)
        {
            this.field_72590_a.func_72527_a("Failed to verify username, session authentication server unavailable!");
        }
        catch (Exception exception)
        {
            this.field_72590_a.func_72527_a("Failed to verify username!");
            server.getLogger().log(java.util.logging.Level.WARNING, "Exception verifying " + NetLoginHandler.func_72533_d(this.field_72590_a), exception);
            // CraftBukkit end
        }
    }
}
