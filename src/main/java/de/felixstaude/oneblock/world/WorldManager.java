package de.felixstaude.oneblock.world;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class WorldManager {

    private final JavaPlugin plugin;
    private final String worldName = "voidworld";

    public WorldManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void initializeWorld() {
        World oldWorld = Bukkit.getWorld(worldName);

        if (oldWorld != null) {
            for (Player player : oldWorld.getPlayers()) {
                player.kickPlayer("World is resetting!");
            }

            if (Bukkit.unloadWorld(oldWorld, false)) {
                Path worldPath = oldWorld.getWorldFolder().toPath();
                try {
                    Files.walk(worldPath)
                            .sorted((p1, p2) -> p2.compareTo(p1))
                            .map(Path::toFile)
                            .forEach(File::delete);
                } catch (IOException e) {
                    plugin.getLogger().severe("Failed to delete old world: " + e.getMessage());
                }
            }
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, ()->{
            WorldCreator creator = new WorldCreator(worldName);
            creator.generator(new VoidChunkGenerator());
            creator.createWorld();
        },20L);
    }
}
