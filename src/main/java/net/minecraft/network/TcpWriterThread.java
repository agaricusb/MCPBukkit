package net.minecraft.network;

import java.io.IOException;

class TcpWriterThread extends Thread
{
    final TcpConnection field_74501_a;

    TcpWriterThread(TcpConnection p_i3284_1_, String p_i3284_2_)
    {
        super(p_i3284_2_);
        this.field_74501_a = p_i3284_1_;
    }

    public void run()
    {
        TcpConnection.field_74469_b.getAndIncrement();

        try
        {
            while (TcpConnection.func_74462_a(this.field_74501_a))
            {
                boolean flag;

                for (flag = false; TcpConnection.func_74451_d(this.field_74501_a); flag = true)
                {
                    ;
                }

                try
                {
                    if (flag && TcpConnection.func_74453_e(this.field_74501_a) != null)
                    {
                        TcpConnection.func_74453_e(this.field_74501_a).flush();
                    }
                }
                catch (IOException ioexception)
                {
                    if (!TcpConnection.func_74456_f(this.field_74501_a))
                    {
                        TcpConnection.func_74458_a(this.field_74501_a, (Exception) ioexception);
                    }

                    // ioexception.printStackTrace(); // CraftBukkit - Don't spam console on unexpected disconnect
                }

                try
                {
                    sleep(2L);
                }
                catch (InterruptedException interruptedexception)
                {
                    ;
                }
            }
        }
        finally
        {
            TcpConnection.field_74469_b.getAndDecrement();
        }
    }
}
