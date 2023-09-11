package de.felixstaude.oneblock.main;

import de.felixstaude.oneblock.oneblock.PlayerJoinQuitHandler;
import org.bukkit.plugin.java.JavaPlugin;


public final class OneBlock_Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerJoinQuitHandler(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
