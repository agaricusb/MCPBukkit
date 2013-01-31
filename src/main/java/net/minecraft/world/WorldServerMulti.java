package net.minecraft.world;

import net.minecraft.profiler.Profiler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.ISaveHandler;
public class WorldServerMulti extends WorldServer
{
    // CraftBukkit start - Changed signature
    public WorldServerMulti(MinecraftServer minecraftserver, ISaveHandler isavehandler, String s, int i, WorldSettings worldsettings, WorldServer worldserver, Profiler profiler, org.bukkit.World.Environment env, org.bukkit.generator.ChunkGenerator gen)
    {
        super(minecraftserver, isavehandler, s, i, worldsettings, profiler, env, gen);
        // CraftBukkit end
        this.field_72988_C = worldserver.field_72988_C;
        // this.worldData = new SecondaryWorldData(worldserver.getWorldData()); // CraftBukkit - use unique worlddata
    }

    // protected void a() {} // CraftBukkit - save world data!
}
