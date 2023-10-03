package de.felixstaude.oneblock.player.connection;

import de.felixstaude.oneblock.gamemanager.GameManager;
import de.felixstaude.oneblock.gamemanager.GameState;
import de.felixstaude.oneblock.block.barrier.BarrierManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.LinkedHashMap;
import java.util.UUID;

public class PlayerJoinQuitHandler implements Listener {

    private final int requiredPlayers = 3;
    public static final LinkedHashMap<UUID, Location> blockLocation = new LinkedHashMap<UUID, Location>();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        UUID uuid = event.getPlayer().getUniqueId();
        if(GameManager.isState(GameState.LOBBY) || GameManager.isState(GameState.START_GAME)){
            if(!blockLocation.containsKey(uuid)){
                addNewLocation(uuid);
            }
            setBlock(uuid, true);
        }
        new BarrierManager().createBarrierBox(event.getPlayer());
        Location teleportLoc = getBlockLocation(uuid).clone().add(0.5, 1, 0.5);
        event.getPlayer().teleport(teleportLoc);

        // sets the game state
        if(Bukkit.getOnlinePlayers().size() >= requiredPlayers && GameManager.isState(GameState.LOBBY)){
            GameManager.startGame();
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        UUID uuid = event.getPlayer().getUniqueId();
        if(GameManager.isState(GameState.LOBBY) || GameManager.isState(GameState.START_GAME)){
            if(blockLocation.containsKey(uuid)){
                setBlock(uuid, false);
                new BarrierManager().removeBarrierBox(event.getPlayer());
                removeLocation(uuid);
            }
        }

        // sets the game state
        if(Bukkit.getOnlinePlayers().size() <= requiredPlayers && GameManager.isState(GameState.START_GAME)){
            GameManager.stopGameStart();
        }

    }

    // set the block under the players spawn location
    private void setBlock(UUID uuid, Boolean bool){
        Location blockLoc = getBlockLocation(uuid);

        if (blockLoc == null) {return;}

        blockLoc = blockLoc.clone();
        blockLoc.setY(blockLoc.getY() -2);

        // true = place bedrock block
        // false = break bedrock block
        if(bool){
            blockLoc.getBlock().setType(Material.BEDROCK);
        } else {
            blockLoc.getBlock().breakNaturally();
        }
    }

    // add the new spawn location of the player
    private void addNewLocation(UUID uuid) {
        if (!blockLocation.containsKey(uuid)) {
            Location freieLoc = findFreeLocation();
            blockLocation.put(uuid, freieLoc);
        }
    }

    // remove the spawn location of the player
    private void removeLocation(UUID uuid){
        blockLocation.remove(uuid);
        setBlock(uuid, false);
    }

    // get the spawn location of the player
    public static Location getBlockLocation(UUID uuid){
        return blockLocation.get(uuid);
    }

    // find a free location to set a new spawn location
    private Location findFreeLocation() {
        Location startLoc = new Location(Bukkit.getWorld("voidworld"), 0, 125, 0);
        Location currentLoc = startLoc.clone();
        int step = 10;

        while(locationExists(currentLoc)) {
            currentLoc.add(step, 0, 0);
        }
        return currentLoc;
    }

    // checks to see if a location already exists
    private boolean locationExists(Location location) {
        for (Location loc : blockLocation.values()) {
            if (loc.getBlockX() == location.getBlockX() && loc.getBlockY() == location.getBlockY() && loc.getBlockZ() == location.getBlockZ()) {
                return true;
            }
        }
        return false;
    }

    public boolean isPlayerOnList(UUID uuid){return (blockLocation.containsKey(uuid));}

}
