package org.bukkit.craftbukkit;


import org.bukkit.Location;
import org.bukkit.TravelAgent;

public class CraftTravelAgent extends net.minecraft.world.Teleporter implements TravelAgent {

    public static TravelAgent DEFAULT = null;

    private int searchRadius = 128;
    private int creationRadius = 16;
    private boolean canCreatePortal = true;

    public CraftTravelAgent(net.minecraft.world.WorldServer worldserver) {
        super(worldserver);
        if (DEFAULT == null && worldserver.dimension == 0) {
            DEFAULT = this;
        }
    }

    public Location findOrCreate(Location target) {
        net.minecraft.world.WorldServer worldServer = ((CraftWorld) target.getWorld()).getHandle();
        boolean before = worldServer.field_73059_b.field_73250_a;
        worldServer.field_73059_b.field_73250_a = true;

        Location found = this.findPortal(target);
        if (found == null) {
            if (this.getCanCreatePortal() && this.createPortal(target)) {
                found = this.findPortal(target);
            } else {
                found = target; // fallback to original if unable to find or create
            }
        }

        worldServer.field_73059_b.field_73250_a = before;
        return found;
    }

    public Location findPortal(Location location) {
        net.minecraft.world.Teleporter pta = ((CraftWorld) location.getWorld()).getHandle().func_85176_s();
        net.minecraft.util.ChunkCoordinates found = pta.findPortal(location.getX(), location.getY(), location.getZ(), this.getSearchRadius());
        return found != null ? new Location(location.getWorld(), found.field_71574_a, found.field_71572_b, found.field_71573_c, location.getYaw(), location.getPitch()) : null;
    }

    public boolean createPortal(Location location) {
        net.minecraft.world.Teleporter pta = ((CraftWorld) location.getWorld()).getHandle().func_85176_s();
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
