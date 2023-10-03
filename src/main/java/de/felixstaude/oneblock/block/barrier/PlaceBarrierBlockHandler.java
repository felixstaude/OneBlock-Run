package de.felixstaude.oneblock.block.barrier;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlaceBarrierBlockHandler implements Listener {

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent event){
        if(event.getBlockAgainst().getType().equals(Material.BARRIER)){
            event.setCancelled(true);
        }
    }
}
