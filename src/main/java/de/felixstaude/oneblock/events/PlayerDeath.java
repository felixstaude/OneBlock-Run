package de.felixstaude.oneblock.events;

import de.felixstaude.oneblock.player.connection.PlayerJoinQuitHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        if(event.getEntity() instanceof Player){
            event.setKeepInventory(true);
            event.getDrops().clear();
        }
    }
}
