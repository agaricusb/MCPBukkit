package net.minecraft.entity.player;

import net.minecraft.nbt.NBTTagCompound;
public class PlayerCapabilities
{
    public boolean field_75102_a = false;
    public boolean field_75100_b = false;
    public boolean field_75101_c = false;
    public boolean field_75098_d = false;
    public boolean field_75099_e = true;
    public float field_75096_f = 0.05F; // CraftBukkit private -> public
    public float field_75097_g = 0.1F; // CraftBukkit private -> public

    public PlayerCapabilities() {}

    public void func_75091_a(NBTTagCompound p_75091_1_)
    {
        NBTTagCompound nbttagcompound1 = new NBTTagCompound();
        nbttagcompound1.func_74757_a("invulnerable", this.field_75102_a);
        nbttagcompound1.func_74757_a("flying", this.field_75100_b);
        nbttagcompound1.func_74757_a("mayfly", this.field_75101_c);
        nbttagcompound1.func_74757_a("instabuild", this.field_75098_d);
        nbttagcompound1.func_74757_a("mayBuild", this.field_75099_e);
        nbttagcompound1.func_74776_a("flySpeed", this.field_75096_f);
        nbttagcompound1.func_74776_a("walkSpeed", this.field_75097_g);
        p_75091_1_.func_74782_a("abilities", nbttagcompound1);
    }

    public void func_75095_b(NBTTagCompound p_75095_1_)
    {
        if (p_75095_1_.func_74764_b("abilities"))
        {
            NBTTagCompound nbttagcompound1 = p_75095_1_.func_74775_l("abilities");
            this.field_75102_a = nbttagcompound1.func_74767_n("invulnerable");
            this.field_75100_b = nbttagcompound1.func_74767_n("flying");
            this.field_75101_c = nbttagcompound1.func_74767_n("mayfly");
            this.field_75098_d = nbttagcompound1.func_74767_n("instabuild");

            if (nbttagcompound1.func_74764_b("flySpeed"))
            {
                this.field_75096_f = nbttagcompound1.func_74760_g("flySpeed");
                this.field_75097_g = nbttagcompound1.func_74760_g("walkSpeed");
            }

            if (nbttagcompound1.func_74764_b("mayBuild"))
            {
                this.field_75099_e = nbttagcompound1.func_74767_n("mayBuild");
            }
        }
    }

    public float func_75093_a()
    {
        return this.field_75096_f;
    }

    public float func_75094_b()
    {
        return this.field_75097_g;
    }
}
