package de.felixstaude.oneblock.gamemanager;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTimer extends BukkitRunnable {

    private final int durationInSeconds = 15; // 2 Stunden = 7200 Sekunden
    private int timeLeft = durationInSeconds;

    @Override
    public void run() {
        if(timeLeft <= 0) {
            cancel();
            determineWinner();
            return;
        }
        timeLeft--;
        System.out.println(timeLeft);
        
    }

    private void determineWinner() {
        WinnerDetermination winnerDetermination = new WinnerDetermination();
        winnerDetermination.determineAndAnnounceWinner();
    }

    public void start() {
        this.runTaskTimer(Bukkit.getPluginManager().getPlugin("oneblock-run"), 0L, 20L); // Jede Sekunde (20 Ticks)
    }
}
