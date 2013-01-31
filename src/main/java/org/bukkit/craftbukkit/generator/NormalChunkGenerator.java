package org.bukkit.craftbukkit.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.generator.BlockPopulator;

public class NormalChunkGenerator extends InternalChunkGenerator {
    private final net.minecraft.world.chunk.IChunkProvider/*was:IChunkProvider*/ provider;

    public NormalChunkGenerator(net.minecraft.world.World/*was:World*/ world, long seed) {
        provider = world.field_73011_w/*was:worldProvider*/.func_76555_c/*was:getChunkProvider*/();
    }

    public byte[] generate(org.bukkit.World world, Random random, int x, int z) {
        throw new UnsupportedOperationException("Not supported.");
    }

    public boolean canSpawn(org.bukkit.World world, int x, int z) {
        return ((CraftWorld) world).getHandle().field_73011_w/*was:worldProvider*/.func_76566_a/*was:canSpawn*/(x, z);
    }

    public List<BlockPopulator> getDefaultPopulators(org.bukkit.World world) {
        return new ArrayList<BlockPopulator>();
    }

    public boolean func_73149_a/*was:isChunkLoaded*/(int i, int i1) {
        return provider.func_73149_a/*was:isChunkLoaded*/(i, i1);
    }

    public net.minecraft.world.chunk.Chunk/*was:Chunk*/ func_73154_d/*was:getOrCreateChunk*/(int i, int i1) {
        return provider.func_73154_d/*was:getOrCreateChunk*/(i, i1);
    }

    public net.minecraft.world.chunk.Chunk/*was:Chunk*/ func_73158_c/*was:getChunkAt*/(int i, int i1) {
        return provider.func_73158_c/*was:getChunkAt*/(i, i1);
    }

    public void func_73153_a/*was:getChunkAt*/(net.minecraft.world.chunk.IChunkProvider/*was:IChunkProvider*/ icp, int i, int i1) {
        provider.func_73153_a/*was:getChunkAt*/(icp, i, i1);
    }

    public boolean func_73151_a/*was:saveChunks*/(boolean bln, net.minecraft.util.IProgressUpdate/*was:IProgressUpdate*/ ipu) {
        return provider.func_73151_a/*was:saveChunks*/(bln, ipu);
    }

    public boolean func_73156_b/*was:unloadChunks*/() {
        return provider.func_73156_b/*was:unloadChunks*/();
    }

    public boolean func_73157_c/*was:canSave*/() {
        return provider.func_73157_c/*was:canSave*/();
    }

    public List<?> func_73155_a/*was:getMobsFor*/(net.minecraft.entity.EnumCreatureType/*was:EnumCreatureType*/ ect, int i, int i1, int i2) {
        return provider.func_73155_a/*was:getMobsFor*/(ect, i, i1, i2);
    }

    public net.minecraft.world.ChunkPosition/*was:ChunkPosition*/ func_73150_a/*was:findNearestMapFeature*/(net.minecraft.world.World/*was:World*/ world, String string, int i, int i1, int i2) {
        return provider.func_73150_a/*was:findNearestMapFeature*/(world, string, i, i1, i2);
    }

    public void func_82695_e/*was:recreateStructures*/(int i, int j) {
        provider.func_82695_e/*was:recreateStructures*/(i, j);
    }

    // n.m.s implementations always return 0. (The true implementation is in ChunkProviderServer)
    public int func_73152_e/*was:getLoadedChunks*/() {
        return 0;
    }

    public String func_73148_d/*was:getName*/() {
        return "NormalWorldGenerator";
    }
}
