package de.felixstaude.oneblock.player.scoreboard;

import de.felixstaude.oneblock.block.BlockCounter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.*;

public class Scoreboard {
    private final ScoreboardManager instance = Bukkit.getScoreboardManager();

    public void updateScoreboard() {
        if (instance == null) return;
        org.bukkit.scoreboard.Scoreboard scoreboard = instance.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("topPlayers", "dummy", "Top 5 Players");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        List<Map.Entry<UUID, Integer>> sortedEntries;
        synchronized (BlockCounter.playerCounters) {
            sortedEntries = new ArrayList<>(BlockCounter.playerCounters.entrySet());
        }
        sortedEntries.sort(Map.Entry.<UUID, Integer>comparingByValue().reversed());

        int maxDisplayScore = sortedEntries.size();
        for (int i = 0; i < Math.min(5, sortedEntries.size()); i++) {
            Map.Entry<UUID, Integer> entry = sortedEntries.get(i);
            String playerName = Bukkit.getOfflinePlayer(entry.getKey()).getName();

            if (playerName == null) continue;

            Score score = objective.getScore(playerName + ": " + entry.getValue());
            score.setScore(maxDisplayScore - i);
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setScoreboard(scoreboard);
        }
    }

}
