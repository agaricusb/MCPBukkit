package org.bukkit.craftbukkit.chunkio;

import org.bukkit.craftbukkit.util.AsynchronousExecutor;
import org.bukkit.craftbukkit.util.LongHash;

public class ChunkIOExecutor {
    static final int BASE_THREADS = 1;
    static final int PLAYERS_PER_THREAD = 50;

    private static final AsynchronousExecutor<QueuedChunk, net.minecraft.world.chunk.Chunk/*was:Chunk*/, Runnable, RuntimeException> instance = new AsynchronousExecutor<QueuedChunk, net.minecraft.world.chunk.Chunk/*was:Chunk*/, Runnable, RuntimeException>(new ChunkIOProvider(), BASE_THREADS);

    public static void waitForChunkLoad(net.minecraft.world.World/*was:World*/ world, int x, int z) {
        instance.get(new QueuedChunk(LongHash.toLong(x, z), null, world, null));
    }

    public static void queueChunkLoad(net.minecraft.world.World/*was:World*/ world, net.minecraft.world.chunk.storage.AnvilChunkLoader/*was:ChunkRegionLoader*/ loader, net.minecraft.world.gen.ChunkProviderServer/*was:ChunkProviderServer*/ provider, int x, int z, Runnable runnable) {
        instance.add(new QueuedChunk(LongHash.toLong(x, z), loader, world, provider), runnable);
    }

    public static void adjustPoolSize(int players) {
        int size = Math.max(BASE_THREADS, (int) Math.ceil(players / PLAYERS_PER_THREAD));
        instance.setActiveThreads(size);
    }

    public static void tick() {
        instance.finishActive();
    }
}
