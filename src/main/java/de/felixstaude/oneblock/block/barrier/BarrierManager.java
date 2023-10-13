package de.felixstaude.oneblock.block.barrier;

import de.felixstaude.oneblock.block.BlockCounter;
import de.felixstaude.oneblock.player.connection.PlayerJoinQuitHandler;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import static de.felixstaude.oneblock.player.connection.PlayerJoinQuitHandler.blockLocation;

public class BarrierManager {
    // Creates a barrier box around a specified player's location with walls on the North, East, and West sides
    public void createBarrierBox(Player player) {

        // Retrieve the central block location for the player
        Location center = blockLocation.get(player.getUniqueId());
        if (center == null) return; // If no location is found, exit the method

        // Define the coordinates for the barrier
        int xCenter = center.getBlockX();
        int yMin = -63; // Starting from y = -63
        int yMax = 300; // Up to y = 300
        int zCenter = center.getBlockZ();

        int xMin = xCenter - 5; // Five blocks to the west
        int xMax = xCenter + 5; // Five blocks to the east
        int zMin = zCenter - 3; // Three blocks to the north
        int zMax = zCenter + 100; // One hundred blocks to the south, but we won't place barriers here

        // Loop through each coordinate within the defined boundaries
        for (int x = xMin; x <= xMax; x++) {
            for (int z = zMin; z <= zMax; z++) {
                for (int y = yMin; y <= yMax; y++) {
                    // Check if the current position is at the boundary (North, East, or West sides)
                    if (x == xMin || x == xMax || z == zMin) { // No barrier on the south side (zMax)
                        // Creates a new location for the barrier block
                        Location loc = new Location(center.getWorld(), x, y, z);
                        // Set the block at the new location to a barrier
                        loc.getBlock().setType(Material.BARRIER);
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
        for (int x = xMin; x <= xMax; x++) {
            for (int z = zMin; z <= zMax; z++) {
                for (int y = yMin; y <= yMax; y++) {
                    // Check if the current block is on the boundary
                    if (x == xMin || x == xMax || z == zMin || z == zMax) {
                        Location loc = new Location(center.getWorld(), x, y, z);
                        // Check if the current block is a barrier before removing it
                        if (loc.getBlock().getType() == Material.BARRIER) {
                            loc.getBlock().setType(Material.AIR); // Remove barriers at the specified locations
                        }
                    }
                }
            }
        }
    }

    // Extend the barrier box at the East and West sides for a given player
    // block counter is used to get the Z coordinate of the last placed block
    public void extendBarrierBox(Player player) {
        // Retrieve the central block location for the player
        Location centerBlockLoc = blockLocation.get(player.getUniqueId());
        if (centerBlockLoc == null) return; // If no location is found, exit the method

        // Retrieve the player's block count from the BlockCounter class
        BlockCounter blockCounter = new BlockCounter();
        int playerBlockCount = blockCounter.getBlockCount(player);

        // Create a new location, taking X and Y from centerBlockLoc and Z from the block count
        Location center = new Location(centerBlockLoc.getWorld(), centerBlockLoc.getBlockX(), centerBlockLoc.getBlockY(), playerBlockCount);

        // Define the coordinates for the barrier extension
        int zMax = playerBlockCount + 100; // Extend 100 blocks to the south
        int xMin = center.getBlockX() - 5; // Five blocks to the west
        int xMax = center.getBlockX() + 5; // Five blocks to the east
        int yMin = -63; // Starting from y = -63
        int yMax = 300; // Up to y = 300

        // Place barriers at the East and West sides
        for (int y = yMin; y <= yMax; y++) {
            // Create locations for the East and West sides
            Location locWest = new Location(center.getWorld(), xMin, y, zMax);
            Location locEast = new Location(center.getWorld(), xMax, y, zMax);

            // Set the barrier blocks at the new locations
            locWest.getBlock().setType(Material.BARRIER);
            locEast.getBlock().setType(Material.BARRIER);
        }
    }

}
