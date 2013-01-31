package net.minecraft.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.io.IOException; // CraftBukkit

public class Packet2ClientProtocol extends Packet
{
    private int field_73458_a;
    private String field_73456_b;
    public String field_73457_c; // CraftBukkit private -> public
    public int field_73455_d; // CraftBukkit private -> public

    public Packet2ClientProtocol() {}

    public void func_73267_a(DataInputStream p_73267_1_) throws IOException   // CraftBukkit - throws IOException
    {
        this.field_73458_a = p_73267_1_.readByte();
        this.field_73456_b = func_73282_a(p_73267_1_, 16);
        this.field_73457_c = func_73282_a(p_73267_1_, 255);
        this.field_73455_d = p_73267_1_.readInt();
    }

    public void func_73273_a(DataOutputStream p_73273_1_) throws IOException   // CraftBukkit - throws IOException
    {
        p_73273_1_.writeByte(this.field_73458_a);
        func_73271_a(this.field_73456_b, p_73273_1_);
        func_73271_a(this.field_73457_c, p_73273_1_);
        p_73273_1_.writeInt(this.field_73455_d);
    }

    public void func_73279_a(NetHandler p_73279_1_)
    {
        p_73279_1_.func_72500_a(this);
    }

    public int func_73284_a()
    {
        return 3 + 2 * this.field_73456_b.length();
    }

    public int func_73453_d()
    {
        return this.field_73458_a;
    }

    public String func_73454_f()
    {
        return this.field_73456_b;
    }
}
