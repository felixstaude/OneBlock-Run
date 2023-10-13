package de.felixstaude.oneblock.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class CreatureSpawn implements Listener {

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event){
        if(!event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.BUILD_IRONGOLEM) || !event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.BUILD_SNOWMAN)){
            event.setCancelled(true);
        }
    }
}
