package net.minecraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet130UpdateSign;
public class TileEntitySign extends TileEntity
{
    public String[] field_70412_a = new String[] {"", "", "", ""};
    public int field_70410_b = -1;
    public boolean field_70411_c = true; // CraftBukkit - privite -> public

    public TileEntitySign() {}

    public void func_70310_b(NBTTagCompound p_70310_1_)
    {
        super.func_70310_b(p_70310_1_);
        p_70310_1_.func_74778_a("Text1", this.field_70412_a[0]);
        p_70310_1_.func_74778_a("Text2", this.field_70412_a[1]);
        p_70310_1_.func_74778_a("Text3", this.field_70412_a[2]);
        p_70310_1_.func_74778_a("Text4", this.field_70412_a[3]);
    }

    public void func_70307_a(NBTTagCompound p_70307_1_)
    {
        this.field_70411_c = false;
        super.func_70307_a(p_70307_1_);

        for (int i = 0; i < 4; ++i)
        {
            this.field_70412_a[i] = p_70307_1_.func_74779_i("Text" + (i + 1));

            if (this.field_70412_a[i].length() > 15)
            {
                this.field_70412_a[i] = this.field_70412_a[i].substring(0, 15);
            }
        }
    }

    public Packet func_70319_e()
    {
        String[] astring = new String[4];

        // CraftBukkit start - limit sign text to 15 chars per line
        for (int i = 0; i < 4; ++i)
        {
            astring[i] = this.field_70412_a[i];

            if (this.field_70412_a[i].length() > 15)
            {
                astring[i] = this.field_70412_a[i].substring(0, 15);
            }
        }

        // CraftBukkit end
        return new Packet130UpdateSign(this.field_70329_l, this.field_70330_m, this.field_70327_n, astring);
    }

    public boolean func_70409_a()
    {
        return this.field_70411_c;
    }
}
