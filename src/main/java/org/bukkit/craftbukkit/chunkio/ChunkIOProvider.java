package org.bukkit.craftbukkit.chunkio;


import org.bukkit.Server;
import org.bukkit.craftbukkit.util.AsynchronousExecutor;
import org.bukkit.craftbukkit.util.LongHash;

import java.util.concurrent.atomic.AtomicInteger;

class ChunkIOProvider implements AsynchronousExecutor.CallBackProvider<QueuedChunk, net.minecraft.world.chunk.Chunk/*was:Chunk*/, Runnable, RuntimeException> {
    private final AtomicInteger threadNumber = new AtomicInteger(1);

    // async stuff
    public net.minecraft.world.chunk.Chunk/*was:Chunk*/ callStage1(QueuedChunk queuedChunk) throws RuntimeException {
        net.minecraft.world.chunk.storage.AnvilChunkLoader/*was:ChunkRegionLoader*/ loader = queuedChunk.loader;
        Object[] data = loader.loadChunk(queuedChunk.world, LongHash.msw(queuedChunk.coords), LongHash.lsw(queuedChunk.coords));

        if (data != null) {
            queuedChunk.compound = (net.minecraft.nbt.NBTTagCompound/*was:NBTTagCompound*/) data[1];
            return (net.minecraft.world.chunk.Chunk/*was:Chunk*/) data[0];
        }

        return null;
    }

    // sync stuff
    public void callStage2(QueuedChunk queuedChunk, net.minecraft.world.chunk.Chunk/*was:Chunk*/ chunk) throws RuntimeException {
        if(chunk == null) {
            // If the chunk loading failed just do it synchronously (may generate)
            queuedChunk.provider.func_73158_c/*was:getChunkAt*/(LongHash.msw(queuedChunk.coords), LongHash.lsw(queuedChunk.coords));
            return;
        }

        int x = LongHash.msw(queuedChunk.coords);
        int z = LongHash.lsw(queuedChunk.coords);

        // See if someone already loaded this chunk while we were working on it (API, etc)
        if (queuedChunk.provider.field_73244_f/*was:chunks*/.containsKey(queuedChunk.coords)) {
            // Make sure it isn't queued for unload, we need it
            queuedChunk.provider.field_73248_b/*was:unloadQueue*/.remove(queuedChunk.coords);
            return;
        }

        queuedChunk.loader.loadEntities(chunk, queuedChunk.compound.func_74775_l/*was:getCompound*/("Level"), queuedChunk.world);
        chunk.field_76641_n/*was:n*/ = queuedChunk.provider.field_73251_h/*was:world*/.func_82737_E/*was:getTime*/();
        queuedChunk.provider.field_73244_f/*was:chunks*/.put(queuedChunk.coords, chunk);
        chunk.func_76631_c/*was:addEntities*/();

        if (queuedChunk.provider.field_73246_d/*was:chunkProvider*/ != null) {
            queuedChunk.provider.field_73246_d/*was:chunkProvider*/.func_82695_e/*was:recreateStructures*/(x, z);
        }

        Server server = queuedChunk.provider.field_73251_h/*was:world*/.getServer();
        if (server != null) {
            server.getPluginManager().callEvent(new org.bukkit.event.world.ChunkLoadEvent(chunk.bukkitChunk, false));
        }
        
        chunk.func_76624_a/*was:a*/(queuedChunk.provider, queuedChunk.provider, x, z);
    }

    public void callStage3(QueuedChunk queuedChunk, net.minecraft.world.chunk.Chunk/*was:Chunk*/ chunk, Runnable runnable) throws RuntimeException {
        runnable.run();
    }

    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable, "Chunk I/O Executor Thread-" + threadNumber.getAndIncrement());
        thread.setDaemon(true);
        return thread;
    }
}
