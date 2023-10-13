package de.felixstaude.oneblock.block;

import de.felixstaude.oneblock.block.barrier.BarrierManager;
import de.felixstaude.oneblock.gamemanager.GameManager;
import de.felixstaude.oneblock.gamemanager.GameState;
import de.felixstaude.oneblock.player.scoreboard.Scoreboard;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BlockCounter implements Listener {

    private final BarrierManager barrierManager = new BarrierManager();
    // Map to store scores, key is player's UUID, value is the score.
    public static final Map<UUID, Integer> playerCounters = Collections.synchronizedMap(new HashMap<>());
    // Map to store each player's southernmost block.
    private final Map<UUID, Block> southernmostBlocks = new HashMap<>();

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        // Ignore cancelled events and non-gameplay states and cancel the block place event
        if(event.isCancelled() || !GameManager.isState(GameState.IN_GAME)){
            if(!event.getPlayer().isOp()){
                event.setCancelled(true);
                return;
            }
            event.setCancelled(false);
            return;
        }

        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();
        Block placedBlock = event.getBlockPlaced();
        Block southernmostBlock = southernmostBlocks.get(playerId);

        // If this block is the player's first, or is more southern, update and increment the score.
        if(southernmostBlock == null || placedBlock.getZ() > southernmostBlock.getZ()) {
            southernmostBlocks.put(playerId, placedBlock);
            playerCounters.put(playerId, playerCounters.getOrDefault(playerId, 0) + 1);
            new Scoreboard().updateScoreboard();
            return;
        }

        // If block is placed to the south of the current southernmost block and on the same X coordinate, increment the score.
        if(placedBlock.getZ() == southernmostBlock.getZ() + 1 && placedBlock.getX() == southernmostBlock.getX()) {
            playerCounters.put(playerId, playerCounters.getOrDefault(playerId, 0) + 1);
        }
        new Scoreboard().updateScoreboard();
        barrierManager.extendBarrierBox(event.getPlayer());
    }

    public Integer getBlockCount(Player player) {
        return playerCounters.get(player.getUniqueId());
    }

    public Map<UUID, Integer> getBlockCountMap() {
        return playerCounters;
    }
}
