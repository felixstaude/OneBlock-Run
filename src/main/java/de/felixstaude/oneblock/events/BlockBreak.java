package de.felixstaude.oneblock.events;

import de.felixstaude.oneblock.gamemanager.GameManager;
import de.felixstaude.oneblock.gamemanager.GameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        if(event.isCancelled() || !GameManager.isState(GameState.IN_GAME)) {
            if(!event.getPlayer().isOp()){
                event.setCancelled(true);
                return;
            }
            event.setCancelled(false);
            event.setDropItems(false);
        }
    }
}
