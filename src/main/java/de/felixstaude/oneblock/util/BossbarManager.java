package de.felixstaude.oneblock.util;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class BossbarManager {

    private BossBar bossBar;
    private int maxTimeInSeconds;
    private int timeLeftInSeconds;

    public BossbarManager(String title, int maxTimeInSeconds) {
        this.bossBar = Bukkit.createBossBar(title, BarColor.RED, BarStyle.SOLID);
        this.maxTimeInSeconds = maxTimeInSeconds;
        this.timeLeftInSeconds = maxTimeInSeconds;
        bossBar.setProgress(1.0);
        for (Player player : Bukkit.getOnlinePlayers()) {
            bossBar.addPlayer(player);
        }
    }

    public void decreaseTimer() {
        if (timeLeftInSeconds > 0) {
            timeLeftInSeconds--;
            double progress = (double) timeLeftInSeconds / maxTimeInSeconds;
            bossBar.setProgress(progress);
        }
    }

    public void resetTimer() {
        timeLeftInSeconds = maxTimeInSeconds;
        bossBar.setProgress(1.0);
    }

    public boolean isTimerFinished() {
        return timeLeftInSeconds <= 0;
    }

    public void removeBossBar() {
        bossBar.removeAll();
    }
}
