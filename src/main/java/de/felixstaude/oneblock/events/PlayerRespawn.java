package de.felixstaude.oneblock.events;

import de.felixstaude.oneblock.player.connection.PlayerJoinQuitHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawn implements Listener {

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event){
        Player player = event.getPlayer();
        event.setRespawnLocation(PlayerJoinQuitHandler.getBlockLocation(player.getUniqueId()).clone().add(0.5, 1, 0.5));
    }
}
