package de.felixstaude.oneblock.oneblock;

import de.felixstaude.oneblock.gamemanager.GameManager;
import de.felixstaude.oneblock.gamemanager.GameState;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLoginHandler implements Listener {
    PlayerJoinQuitHandler playerJoinQuitHandler = new PlayerJoinQuitHandler();

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event){
        // disallow a player to join the server if the game is already started
        if(GameManager.isState(GameState.IN_GAME) && !playerJoinQuitHandler.isPlayerOnList(event.getPlayer().getUniqueId())){
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "Das spiel läuft bereits");
        }
    }
}

