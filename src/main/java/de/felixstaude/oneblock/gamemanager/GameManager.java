package de.felixstaude.oneblock.gamemanager;

import de.felixstaude.oneblock.main.OneBlock_Main;
import de.felixstaude.oneblock.oneblock.RandomItemGenerator;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.logging.Level;

public class GameManager{
    private static int countStartGame;
    private static int countInGame;

    private static BukkitTask taskStartGame;
    private static BukkitTask taskInGame;

    private static boolean cancelStartGame = false;
    private static boolean cancelInGame = false;

    private static GameState currentState = GameState.LOBBY;

    public static GameState getCurrentState(){
        return currentState;
    }

    private static void setCurrentState(GameState state){
        currentState = state;
    }

    public static boolean isState(GameState state){
        return currentState == state;
    }

    public static void setLobbyState(){
        setCurrentState(GameState.LOBBY);
    }

    public static void setInGameState(){
        setCurrentState(GameState.IN_GAME);
        taskInGame = new BukkitRunnable(){

            @Override
            public void run() {
                countInGame += 1;
                System.out.println(countInGame);
                if(cancelInGame){
                    taskInGame.cancel();
                    countInGame = 0;
                    cancelInGame = false;
                } else if (countInGame > 10 || countInGame == 0) {
                    System.out.println(countInGame);
                    RandomItemGenerator.dropRandomItemStack();
                    taskStartGame.cancel();
                    countInGame = 0;
                    cancelInGame = false;
                }

            }
        }.runTaskTimer(OneBlock_Main.getProvidingPlugin(OneBlock_Main.class), 0, 20);

    }

    public static void setAfterGameState(){
        setCurrentState(GameState.AFTER_GAME);
    }

    public static void setGameStartState(){
        setCurrentState(GameState.START_GAME);
    }

    public static void startGame(){
        setCurrentState(GameState.START_GAME);
        taskStartGame = new BukkitRunnable() {
            @Override
            public void run() {
                countStartGame += 1;
                if(cancelStartGame){
                    taskStartGame.cancel();
                    countStartGame = 0;
                    cancelStartGame = false;
                    return;
                } else if (countStartGame > 10 || countStartGame == 0) {
                    setInGameState();
                    Bukkit.broadcastMessage("start game");
                    taskStartGame.cancel();
                    countStartGame = 0;
                    cancelStartGame = false;
                    return;
                }
                Bukkit.broadcastMessage("Seconds passed: " + countStartGame + "!");
            }
        }.runTaskTimer(OneBlock_Main.getProvidingPlugin(OneBlock_Main.class), 0, 20);
    }

    public static void stopGameStart(){
        cancelStartGame = true;
        Bukkit.broadcastMessage("cancle bing bong");
        setLobbyState();
    }
}
