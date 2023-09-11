package de.felixstaude.oneblock.oneblock;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.LinkedHashMap;
import java.util.UUID;

public class PlayerJoinQuitHandler implements Listener {

    private final LinkedHashMap<UUID, Location> blockLocation = new LinkedHashMap<UUID, Location>();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        UUID uuid = event.getPlayer().getUniqueId();
        if(!blockLocation.containsKey(uuid)){
            addNewLocation(uuid);
        }
        setBlock(uuid, true);
        Location teleportLoc = getBlockLocation(uuid).clone().add(0.5, 1, 0.5);
        event.getPlayer().teleport(teleportLoc);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        UUID uuid = event.getPlayer().getUniqueId();
        if(blockLocation.containsKey(uuid)){
            setBlock(uuid, false);
            removeLocation(uuid);
        }
    }

    public void addNewLocation(UUID uuid) {
        if (!blockLocation.containsKey(uuid)) {
            Location freieLoc = findFreeLocation();
            System.out.println("free location for " + uuid + ": " + freieLoc); // Debug-Ausgabe
            blockLocation.put(uuid, freieLoc);
        }
        System.out.println("Updated blockLocation map: " + blockLocation);
    }

    public void removeLocation(UUID uuid){
        blockLocation.remove(uuid);
        setBlock(uuid, false);
    }

    public Location getBlockLocation(UUID uuid){
        return blockLocation.get(uuid);
    }

    private Location findFreeLocation() {
        Location startLoc = new Location(Bukkit.getWorld("world"), 0, 125, 0);
        Location currentLoc = startLoc.clone();
        int step = 10;

        while(locationExists(currentLoc)) {
            currentLoc.add(step, 0, 0);
            System.out.println("Current location after update: " + currentLoc);
        }
        return currentLoc;
    }

    // true = place block
    // false = remove block
    private void setBlock(UUID uuid, Boolean bool){
        Location blockLoc = getBlockLocation(uuid);

        if (blockLoc == null) {return;}

        blockLoc = blockLoc.clone();
        blockLoc.setY(blockLoc.getY() -2);

        if(bool){
            blockLoc.getBlock().setType(Material.BEDROCK);
        } else {
            blockLoc.getBlock().breakNaturally();
        }
    }

    private boolean locationExists(Location location) {
        for (Location loc : blockLocation.values()) {
            if (loc.getBlockX() == location.getBlockX() && loc.getBlockY() == location.getBlockY() && loc.getBlockZ() == location.getBlockZ()) {
                return true;
            }
        }
        return false;
    }

}
