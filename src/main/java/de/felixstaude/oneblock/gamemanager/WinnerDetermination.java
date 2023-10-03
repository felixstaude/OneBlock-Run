package de.felixstaude.oneblock.gamemanager;

import de.felixstaude.oneblock.block.BlockCounter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Comparator;
import java.util.Map;
import java.util.UUID;

public class WinnerDetermination {

    public void determineAndAnnounceWinner() {
        Map<UUID, Integer> scores = BlockCounter.playerCounters;

        Map.Entry<UUID, Integer> winnerEntry = scores.entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .orElse(null);

        if(winnerEntry != null) {
            Player winner = Bukkit.getPlayer(winnerEntry.getKey());
            Bukkit.broadcastMessage(winner.getName() + " hat das Spiel mit " + winnerEntry.getValue() + " Blöcken gewonnen!");
            GameManager.setAfterGameState();
        }
    }
}
