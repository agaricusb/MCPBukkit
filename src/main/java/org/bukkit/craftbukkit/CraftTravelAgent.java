package org.bukkit.craftbukkit;


import org.bukkit.Location;
import org.bukkit.TravelAgent;

public class CraftTravelAgent extends net.minecraft.world.Teleporter/*was:PortalTravelAgent*/ implements TravelAgent {

    private int searchRadius = 128;
    private int creationRadius = 16;
    private boolean canCreatePortal = true;

    public CraftTravelAgent(net.minecraft.world.WorldServer/*was:WorldServer*/ worldserver) {
        super(worldserver);
    }

    public Location findOrCreate(Location target) {
        net.minecraft.world.WorldServer/*was:WorldServer*/ worldServer = ((CraftWorld) target.getWorld()).getHandle();
        boolean before = worldServer.field_73059_b/*was:chunkProviderServer*/.field_73250_a/*was:forceChunkLoad*/;
        worldServer.field_73059_b/*was:chunkProviderServer*/.field_73250_a/*was:forceChunkLoad*/ = true;

        Location found = this.findPortal(target);
        if (found == null) {
            if (this.getCanCreatePortal() && this.createPortal(target)) {
                found = this.findPortal(target);
            } else {
                found = target; // fallback to original if unable to find or create
            }
        }

        worldServer.field_73059_b/*was:chunkProviderServer*/.field_73250_a/*was:forceChunkLoad*/ = before;
        return found;
    }

    public Location findPortal(Location location) {
        net.minecraft.world.Teleporter/*was:PortalTravelAgent*/ pta = ((CraftWorld) location.getWorld()).getHandle().func_85176_s/*was:s*/();
        net.minecraft.util.ChunkCoordinates/*was:ChunkCoordinates*/ found = pta.findPortal(location.getX(), location.getY(), location.getZ(), this.getSearchRadius());
        return found != null ? new Location(location.getWorld(), found.field_71574_a/*was:x*/, found.field_71572_b/*was:y*/, found.field_71573_c/*was:z*/, location.getYaw(), location.getPitch()) : null;
    }

    public boolean createPortal(Location location) {
        net.minecraft.world.Teleporter/*was:PortalTravelAgent*/ pta = ((CraftWorld) location.getWorld()).getHandle().func_85176_s/*was:s*/();
        return pta.createPortal(location.getX(), location.getY(), location.getZ(), this.getCreationRadius());
    }

    public TravelAgent setSearchRadius(int radius) {
        this.searchRadius = radius;
        return this;
    }

    public int getSearchRadius() {
        return this.searchRadius;
    }

    public TravelAgent setCreationRadius(int radius) {
        this.creationRadius = radius < 2 ? 0 : radius;
        return this;
    }

    public int getCreationRadius() {
        return this.creationRadius;
    }

    public boolean getCanCreatePortal() {
        return this.canCreatePortal;
    }

    public void setCanCreatePortal(boolean create) {
        this.canCreatePortal = create;
    }
}
