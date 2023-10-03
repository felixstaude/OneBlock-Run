package de.felixstaude.oneblock.main;

import de.felixstaude.oneblock.block.BlockCounter;
import de.felixstaude.oneblock.gamemanager.GameManager;
import de.felixstaude.oneblock.block.barrier.PlaceBarrierBlockHandler;
import de.felixstaude.oneblock.player.connection.PlayerJoinQuitHandler;
import de.felixstaude.oneblock.player.connection.PlayerLoginHandler;
import de.felixstaude.oneblock.world.WorldManager;
import org.bukkit.plugin.java.JavaPlugin;


public final class OneBlock_Main extends JavaPlugin {


    @Override
    public void onEnable() {
        new WorldManager(this).initializeWorld();

        getServer().getPluginManager().registerEvents(new PlayerJoinQuitHandler(), this);
        getServer().getPluginManager().registerEvents(new PlayerLoginHandler(), this);
        getServer().getPluginManager().registerEvents(new PlaceBarrierBlockHandler(), this);
        getServer().getPluginManager().registerEvents(new BlockCounter(), this);
        GameManager.setLobbyState();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
