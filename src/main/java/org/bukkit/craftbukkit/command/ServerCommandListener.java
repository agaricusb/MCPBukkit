package org.bukkit.craftbukkit.command;

import java.lang.reflect.Method;


import org.bukkit.command.CommandSender;

public class ServerCommandListener implements net.minecraft.command.ICommandSender/*was:ICommandListener*/ {
    private final CommandSender commandSender;
    private final String prefix;

    public ServerCommandListener(CommandSender commandSender) {
        this.commandSender = commandSender;
        String[] parts = commandSender.getClass().getName().split("\\.");
        this.prefix = parts[parts.length - 1];
    }

    public void func_70006_a/*was:sendMessage*/(String msg) {
        this.commandSender.sendMessage(msg);
    }

    public CommandSender getSender() {
        return commandSender;
    }

    public String func_70005_c_/*was:getName*/() {
        try {
            Method getName = commandSender.getClass().getMethod("getName");

            return (String) getName.invoke(commandSender);
        } catch (Exception e) {}

        return this.prefix;
    }

    public String func_70004_a/*was:a*/(String s, Object... aobject) {
        return net.minecraft.util.StringTranslate/*was:LocaleLanguage*/.func_74808_a/*was:a*/().func_74803_a/*was:a*/(s, aobject);
    }

    public boolean func_70003_b/*was:a*/(int i, String s) {
        return true;
    }

    public net.minecraft.util.ChunkCoordinates/*was:ChunkCoordinates*/ func_82114_b/*was:b*/() {
        return new net.minecraft.util.ChunkCoordinates/*was:ChunkCoordinates*/(0, 0, 0);
    }
}
