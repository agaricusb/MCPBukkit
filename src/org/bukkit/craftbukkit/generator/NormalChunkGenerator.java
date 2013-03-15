package org.bukkit.craftbukkit.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.generator.BlockPopulator;

public class NormalChunkGenerator extends InternalChunkGenerator {
    private final net.minecraft.world.chunk.IChunkProvider provider;

    public NormalChunkGenerator(net.minecraft.world.World world, long seed) {
        provider = world.field_73011_w.func_76555_c();
    }

    public byte[] generate(org.bukkit.World world, Random random, int x, int z) {
        throw new UnsupportedOperationException("Not supported.");
    }

    public boolean canSpawn(org.bukkit.World world, int x, int z) {
        return ((CraftWorld) world).getHandle().field_73011_w.func_76566_a(x, z);
    }

    public List<BlockPopulator> getDefaultPopulators(org.bukkit.World world) {
        return new ArrayList<BlockPopulator>();
    }

    public boolean func_73149_a(int i, int i1) {
        return provider.func_73149_a(i, i1);
    }

    public net.minecraft.world.chunk.Chunk func_73154_d(int i, int i1) {
        return provider.func_73154_d(i, i1);
    }

    public net.minecraft.world.chunk.Chunk func_73158_c(int i, int i1) {
        return provider.func_73158_c(i, i1);
    }

    public void func_73153_a(net.minecraft.world.chunk.IChunkProvider icp, int i, int i1) {
        provider.func_73153_a(icp, i, i1);
    }

    public boolean func_73151_a(boolean bln, net.minecraft.util.IProgressUpdate ipu) {
        return provider.func_73151_a(bln, ipu);
    }

    public boolean func_73156_b() {
        return provider.func_73156_b();
    }

    public boolean func_73157_c() {
        return provider.func_73157_c();
    }

    public List<?> func_73155_a(net.minecraft.entity.EnumCreatureType ect, int i, int i1, int i2) {
        return provider.func_73155_a(ect, i, i1, i2);
    }

    public net.minecraft.world.ChunkPosition func_73150_a(net.minecraft.world.World world, String string, int i, int i1, int i2) {
        return provider.func_73150_a(world, string, i, i1, i2);
    }

    public void func_82695_e(int i, int j) {
        provider.func_82695_e(i, j);
    }

    // n.m.s implementations always return 0. (The true implementation is in ChunkProviderServer)
    public int func_73152_e() {
        return 0;
    }

    public String func_73148_d() {
        return "NormalWorldGenerator";
    }
}
