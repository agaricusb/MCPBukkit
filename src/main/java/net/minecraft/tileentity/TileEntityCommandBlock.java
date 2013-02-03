package net.minecraft.tileentity;

// CraftBukkit start
import java.util.ArrayList;
import java.util.Arrays;
import com.google.common.base.Joiner;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerSelector;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
// CraftBukkit end

public class TileEntityCommandBlock extends TileEntity implements ICommandSender
{
    private String field_82354_a = "";
    private final org.bukkit.command.BlockCommandSender sender;

    public TileEntityCommandBlock()
    {
        sender = new org.bukkit.craftbukkit.command.CraftBlockCommandSender(this);
    }

    public void func_82352_b(String p_82352_1_)
    {
        this.field_82354_a = p_82352_1_;
        this.func_70296_d();
    }

    public void func_82351_a(World p_82351_1_)
    {
        if (!p_82351_1_.field_72995_K)
        {
            MinecraftServer minecraftserver = MinecraftServer.func_71276_C();

            if (minecraftserver != null && minecraftserver.func_82356_Z())
            {
                // CraftBukkit start - handle command block as console
                org.bukkit.command.SimpleCommandMap commandMap = minecraftserver.server.getCommandMap();
                Joiner joiner = Joiner.on(" ");
                String command = this.field_82354_a;

                if (this.field_82354_a.startsWith("/"))
                {
                    command = this.field_82354_a.substring(1);
                }

                String[] args = command.split(" ");
                ArrayList<String[]> commands = new ArrayList<String[]>();

                // block disallowed commands
                if (args[0].equalsIgnoreCase("stop") || args[0].equalsIgnoreCase("kick") || args[0].equalsIgnoreCase("op") ||
                        args[0].equalsIgnoreCase("deop") || args[0].equalsIgnoreCase("ban") || args[0].equalsIgnoreCase("ban-ip") ||
                        args[0].equalsIgnoreCase("pardon") || args[0].equalsIgnoreCase("pardon-ip") || args[0].equalsIgnoreCase("reload"))
                {
                    return;
                }

                // make sure this is a valid command
                if (commandMap.getCommand(args[0]) == null)
                {
                    return;
                }

                // if the world has no players don't run
                if (this.field_70331_k.field_73010_i.isEmpty())
                {
                    return;
                }

                commands.add(args);
                // find positions of command block syntax, if any
                ArrayList<String[]> newCommands = new ArrayList<String[]>();

                for (int i = 0; i < args.length; i++)
                {
                    if (PlayerSelector.func_82378_b(args[i]))
                    {
                        for (int j = 0; j < commands.size(); j++)
                        {
                            newCommands.addAll(this.buildCommands(commands.get(j), i));
                        }

                        ArrayList<String[]> temp = commands;
                        commands = newCommands;
                        newCommands = temp;
                        newCommands.clear();
                    }
                }

                // now dispatch all of the commands we ended up with
                for (int i = 0; i < commands.size(); i++)
                {
                    commandMap.dispatch(sender, joiner.join(Arrays.asList(commands.get(i))));
                }

                // CraftBukkit end
            }
        }
    }

    // CraftBukkit start
    private ArrayList<String[]> buildCommands(String[] args, int pos)
    {
        ArrayList<String[]> commands = new ArrayList<String[]>();
        EntityPlayerMP[] players = PlayerSelector.func_82380_c(this, args[pos]);

        if (players != null)
        {
            for (EntityPlayerMP player : players)
            {
                if (player.field_70170_p != this.field_70331_k)
                {
                    continue;
                }

                String[] command = args.clone();
                command[pos] = player.func_70023_ak();
                commands.add(command);
            }
        }

        return commands;
    }
    // CraftBukkit end

    public String func_70005_c_()
    {
        return "@";
    }

    public void func_70006_a(String p_70006_1_) {}

    public boolean func_70003_b(int p_70003_1_, String p_70003_2_)
    {
        return p_70003_1_ <= 2;
    }

    public String func_70004_a(String p_70004_1_, Object ... p_70004_2_)
    {
        return p_70004_1_;
    }

    public void func_70310_b(NBTTagCompound p_70310_1_)
    {
        super.func_70310_b(p_70310_1_);
        p_70310_1_.func_74778_a("Command", this.field_82354_a);
    }

    public void func_70307_a(NBTTagCompound p_70307_1_)
    {
        super.func_70307_a(p_70307_1_);
        this.field_82354_a = p_70307_1_.func_74779_i("Command");
    }

    public ChunkCoordinates func_82114_b()
    {
        return new ChunkCoordinates(this.field_70329_l, this.field_70330_m, this.field_70327_n);
    }

    public Packet func_70319_e()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.func_70310_b(nbttagcompound);
        return new Packet132TileEntityData(this.field_70329_l, this.field_70330_m, this.field_70327_n, 2, nbttagcompound);
    }
}