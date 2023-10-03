package de.felixstaude.oneblock.block.barrier;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import static de.felixstaude.oneblock.player.connection.PlayerJoinQuitHandler.blockLocation;

public class BarrierManager {
    // Creates a barrier box around a specified player's location
    public void createBarrierBox(Player player) {
        Location center = blockLocation.get(player.getUniqueId());

        if (center == null) return;

        int xCenter = center.getBlockX();
        int yMin = -63;
        int yMax = 300;
        int zCenter = center.getBlockZ();

        int xMin = xCenter - 5;
        int xMax = xCenter + 5;

        int zMin = zCenter - 3;
        int zMax = zCenter + 100;

        // Loop through each coordinate and set barriers on the boundary blocks
        for (int x = xMin; x <= xMax; x++) {
            for (int z = zMin; z <= zMax; z++) {
                for (int y = yMin; y <= yMax; y++) {
                    if (x == xMin || x == xMax || z == zMax) {
                        Location loc = new Location(center.getWorld(), x, y, z);
                        loc.getBlock().setType(Material.BARRIER); // Set barriers at the specified locations
                    }
                }
            }
        }
    }

    // Removes the barrier box around a specified player's location
    public void removeBarrierBox(Player player) {
        Location center = blockLocation.get(player.getUniqueId());

        if (center == null) return;

        int xCenter = center.getBlockX();
        int yMin = -63;
        int yMax = 300;
        int zCenter = center.getBlockZ();

        int xMin = xCenter - 5;
        int xMax = xCenter + 5;

        int zMin = zCenter - 3;
        int zMax = zCenter + 100;

        // Loop through each coordinate and remove barriers on the boundary blocks
        for (int z = zMin; z <= zMax; z++) {
            for (int y = yMin; y <= yMax; y++) {
                Location locEast = new Location(center.getWorld(), xMax, y, z);
                locEast.getBlock().setType(Material.AIR); // Remove barriers at the specified locations
            }
        }
    }

    public void extendBarrierBox(Player player) {
        Location center = blockLocation.get(player.getUniqueId());
        if (center == null) return;

        int zMax = center.getBlockZ() + 101;
        int xMin = center.getBlockX() - 5;
        int xMax = center.getBlockX() + 5;
        int yMin = -63;
        int yMax = 300;

        for (int x = xMin; x <= xMax; x++) {
            for (int y = yMin; y <= yMax; y++) {
                Location loc = new Location(center.getWorld(), x, y, zMax);
                loc.getBlock().setType(Material.BARRIER);
            }
        }
    }
}
