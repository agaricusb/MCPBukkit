package org.bukkit.craftbukkit.chunkio;


class QueuedChunk {
    long coords;
    net.minecraft.world.chunk.storage.AnvilChunkLoader/*was:ChunkRegionLoader*/ loader;
    net.minecraft.world.World/*was:World*/ world;
    net.minecraft.world.gen.ChunkProviderServer/*was:ChunkProviderServer*/ provider;
    net.minecraft.nbt.NBTTagCompound/*was:NBTTagCompound*/ compound;

    public QueuedChunk(long coords, net.minecraft.world.chunk.storage.AnvilChunkLoader/*was:ChunkRegionLoader*/ loader, net.minecraft.world.World/*was:World*/ world, net.minecraft.world.gen.ChunkProviderServer/*was:ChunkProviderServer*/ provider) {
        this.coords = coords;
        this.loader = loader;
        this.world = world;
        this.provider = provider;
    }

    @Override
    public int hashCode() {
        return (int) coords ^ world.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof QueuedChunk) {
            QueuedChunk other = (QueuedChunk) object;
            return coords == other.coords && world == other.world;
        }

        return false;
    }
}
