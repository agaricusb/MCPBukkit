package net.minecraft.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.io.IOException; // CraftBukkit

public class Packet3Chat extends Packet
{
    public static int field_73478_a = 119;
    public String field_73476_b;
    private boolean field_73477_c;

    public Packet3Chat()
    {
        this.field_73477_c = true;
    }

    public Packet3Chat(String p_i3300_1_)
    {
        this(p_i3300_1_, true);
    }

    public Packet3Chat(String p_i3301_1_, boolean p_i3301_2_)
    {
        this.field_73477_c = true;
        /* CraftBukkit start - handle this later
        if (s.length() > b) {
            s = s.substring(0, b);
        }
        // CraftBukkit end */
        this.field_73476_b = p_i3301_1_;
        this.field_73477_c = p_i3301_2_;
    }

    public void func_73267_a(DataInputStream p_73267_1_) throws IOException   // CraftBukkit
    {
        this.field_73476_b = func_73282_a(p_73267_1_, field_73478_a);
    }

    public void func_73273_a(DataOutputStream p_73273_1_) throws IOException   // CraftBukkit
    {
        func_73271_a(this.field_73476_b, p_73273_1_);
    }

    public void func_73279_a(NetHandler p_73279_1_)
    {
        p_73279_1_.func_72481_a(this);
    }

    public int func_73284_a()
    {
        return 2 + this.field_73476_b.length() * 2;
    }

    public boolean func_73475_d()
    {
        return this.field_73477_c;
    }

    public boolean func_73277_a_()
    {
        return !this.field_73476_b.startsWith("/");
    }
}
