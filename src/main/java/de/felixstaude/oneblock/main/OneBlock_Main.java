package de.felixstaude.oneblock.main;

import de.felixstaude.oneblock.gamemanager.GameManager;
import de.felixstaude.oneblock.gamemanager.GameState;
import de.felixstaude.oneblock.oneblock.PlayerJoinQuitHandler;
import de.felixstaude.oneblock.oneblock.PlayerLoginHandler;
import org.bukkit.plugin.java.JavaPlugin;


public final class OneBlock_Main extends JavaPlugin {


    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerJoinQuitHandler(), this);
        getServer().getPluginManager().registerEvents(new PlayerLoginHandler(), this);
        GameManager.setLobbyState();

    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
