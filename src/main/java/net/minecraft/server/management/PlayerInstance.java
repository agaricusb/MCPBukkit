package net.minecraft.server.management;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet51MapChunk;
import net.minecraft.network.packet.Packet52MultiBlockChange;
import net.minecraft.network.packet.Packet53BlockChange;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ChunkCoordIntPair;

class PlayerInstance
{
    private final List field_73263_b;
    private final ChunkCoordIntPair field_73264_c;
    private short[] field_73261_d;
    private int field_73262_e;
    private int field_73260_f;
    private boolean loaded = false; // CraftBukkit

    final PlayerManager field_73265_a;

    public PlayerInstance(PlayerManager p_i3391_1_, int p_i3391_2_, int p_i3391_3_)
    {
        this.field_73265_a = p_i3391_1_;
        this.field_73263_b = new ArrayList();
        this.field_73261_d = new short[64];
        this.field_73262_e = 0;
        this.field_73264_c = new ChunkCoordIntPair(p_i3391_2_, p_i3391_3_);
        // CraftBukkit start
        p_i3391_1_.func_72688_a().field_73059_b.getChunkAt(p_i3391_2_, p_i3391_3_, new Runnable()
        {
            public void run()
            {
                PlayerInstance.this.loaded = true;
            }
        });
    }

    public void func_73255_a(final EntityPlayerMP p_73255_1_)   // CraftBukkit - added final to argument
    {
        if (this.field_73263_b.contains(p_73255_1_))
        {
            throw new IllegalStateException("Failed to add player. " + p_73255_1_ + " already is in chunk " + this.field_73264_c.field_77276_a + ", " + this.field_73264_c.field_77275_b);
        }
        else
        {
            this.field_73263_b.add(p_73255_1_);

            // CraftBukkit start
            if (this.loaded)
            {
                p_73255_1_.field_71129_f.add(this.field_73264_c);
            }
            else
            {
                // Abuse getChunkAt to add another callback
                this.field_73265_a.func_72688_a().field_73059_b.getChunkAt(this.field_73264_c.field_77276_a, this.field_73264_c.field_77275_b, new Runnable()
                {
                    public void run()
                    {
                        p_73255_1_.field_71129_f.add(PlayerInstance.this.field_73264_c);
                    }
                });
            }

            // CraftBukkit end
        }
    }

    public void func_73252_b(EntityPlayerMP p_73252_1_)
    {
        if (this.field_73263_b.contains(p_73252_1_))
        {
            p_73252_1_.field_71135_a.func_72567_b(new Packet51MapChunk(PlayerManager.func_72692_a(this.field_73265_a).func_72964_e(this.field_73264_c.field_77276_a, this.field_73264_c.field_77275_b), true, 0));
            this.field_73263_b.remove(p_73252_1_);
            p_73252_1_.field_71129_f.remove(this.field_73264_c);

            if (this.field_73263_b.isEmpty())
            {
                long i = (long)this.field_73264_c.field_77276_a + 2147483647L | (long)this.field_73264_c.field_77275_b + 2147483647L << 32;
                PlayerManager.func_72689_b(this.field_73265_a).func_76159_d(i);

                if (this.field_73262_e > 0)
                {
                    PlayerManager.c(this.field_73265_a).remove(this);
                }

                this.field_73265_a.func_72688_a().field_73059_b.func_73241_b(this.field_73264_c.field_77276_a, this.field_73264_c.field_77275_b);
            }
        }
    }

    public void func_73259_a(int p_73259_1_, int p_73259_2_, int p_73259_3_)
    {
        if (this.field_73262_e == 0)
        {
            PlayerManager.c(this.field_73265_a).add(this);
        }

        this.field_73260_f |= 1 << (p_73259_2_ >> 4);

        if (this.field_73262_e < 64)
        {
            short short1 = (short)(p_73259_1_ << 12 | p_73259_3_ << 8 | p_73259_2_);

            for (int l = 0; l < this.field_73262_e; ++l)
            {
                if (this.field_73261_d[l] == short1)
                {
                    return;
                }
            }

            this.field_73261_d[this.field_73262_e++] = short1;
        }
    }

    public void func_73256_a(Packet p_73256_1_)
    {
        for (int i = 0; i < this.field_73263_b.size(); ++i)
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)this.field_73263_b.get(i);

            if (!entityplayermp.field_71129_f.contains(this.field_73264_c))
            {
                entityplayermp.field_71135_a.func_72567_b(p_73256_1_);
            }
        }
    }

    public void func_73254_a()
    {
        if (this.field_73262_e != 0)
        {
            int i;
            int j;
            int k;

            if (this.field_73262_e == 1)
            {
                i = this.field_73264_c.field_77276_a * 16 + (this.field_73261_d[0] >> 12 & 15);
                j = this.field_73261_d[0] & 255;
                k = this.field_73264_c.field_77275_b * 16 + (this.field_73261_d[0] >> 8 & 15);
                this.func_73256_a(new Packet53BlockChange(i, j, k, PlayerManager.func_72692_a(this.field_73265_a)));

                if (PlayerManager.func_72692_a(this.field_73265_a).func_72927_d(i, j, k))
                {
                    this.func_73257_a(PlayerManager.func_72692_a(this.field_73265_a).func_72796_p(i, j, k));
                }
            }
            else
            {
                int l;

                if (this.field_73262_e == 64)
                {
                    i = this.field_73264_c.field_77276_a * 16;
                    j = this.field_73264_c.field_77275_b * 16;
                    this.func_73256_a(new Packet51MapChunk(PlayerManager.func_72692_a(this.field_73265_a).func_72964_e(this.field_73264_c.field_77276_a, this.field_73264_c.field_77275_b), (this.field_73260_f == 0xFFFF), this.field_73260_f)); // CraftBukkit - send everything (including biome) if all sections flagged

                    for (k = 0; k < 16; ++k)
                    {
                        if ((this.field_73260_f & 1 << k) != 0)
                        {
                            l = k << 4;
                            List list = PlayerManager.func_72692_a(this.field_73265_a).func_73049_a(i, l, j, i + 16, l + 16, j + 16);

                            for (int i1 = 0; i1 < list.size(); ++i1)
                            {
                                this.func_73257_a((TileEntity)list.get(i1));
                            }
                        }
                    }
                }
                else
                {
                    this.func_73256_a(new Packet52MultiBlockChange(this.field_73264_c.field_77276_a, this.field_73264_c.field_77275_b, this.field_73261_d, this.field_73262_e, PlayerManager.func_72692_a(this.field_73265_a)));

                    for (i = 0; i < this.field_73262_e; ++i)
                    {
                        j = this.field_73264_c.field_77276_a * 16 + (this.field_73261_d[i] >> 12 & 15);
                        k = this.field_73261_d[i] & 255;
                        l = this.field_73264_c.field_77275_b * 16 + (this.field_73261_d[i] >> 8 & 15);

                        if (PlayerManager.func_72692_a(this.field_73265_a).func_72927_d(j, k, l))
                        {
                            this.func_73257_a(PlayerManager.func_72692_a(this.field_73265_a).func_72796_p(j, k, l));
                        }
                    }
                }
            }

            this.field_73262_e = 0;
            this.field_73260_f = 0;
        }
    }

    private void func_73257_a(TileEntity p_73257_1_)
    {
        if (p_73257_1_ != null)
        {
            Packet packet = p_73257_1_.func_70319_e();

            if (packet != null)
            {
                this.func_73256_a(packet);
            }
        }
    }

    static ChunkCoordIntPair func_73253_a(PlayerInstance p_73253_0_)
    {
        return p_73253_0_.field_73264_c;
    }

    static List func_73258_b(PlayerInstance p_73258_0_)
    {
        return p_73258_0_.field_73263_b;
    }
}
