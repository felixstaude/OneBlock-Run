package de.felixstaude.oneblock.gamemanager;

import de.felixstaude.oneblock.main.OneBlock_Main;
import de.felixstaude.oneblock.util.BossbarManager;
import de.felixstaude.oneblock.util.MessageManager;
import de.felixstaude.oneblock.util.RandomItemGenerator;
import de.felixstaude.oneblock.util.files.ConfigFileManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class GameManager{
    public static GameState getCurrentState(){
        return currentState;
    }
    public static boolean isState(GameState state){
        return currentState == state;
    }
    public static void setLobbyState(){
        setCurrentState(GameState.LOBBY);
    }
    private static int countStartGame;
    private static int countInGame;
    private static ConfigFileManager configFileManager = new ConfigFileManager();
    private static int dropItemTimer = configFileManager.getIntegerConfig("DropItemTime");
    private static int startGameTimer = configFileManager.getIntegerConfig("GameStartTime");
    private static int gameTimer = configFileManager.getIntegerConfig("GameTime");
    private static boolean cancelStartGame = false;
    private static boolean isGameCanceled = false;
    private static GameState currentState = GameState.LOBBY;
    private static BukkitTask taskStartGame;
    private static BukkitTask taskInGame;
    private static MessageManager messageManager = new MessageManager();
    private static void setCurrentState(GameState state){
        currentState = state;
    }

    // drop a random item every x seconds
    // start the game and after the timer ends determine the winner
    public static void setInGameState(){
        bossbarManager = new BossbarManager("Item Dropping", dropItemTimer);
        messageManager.sendMessage("Das Spiel startet!");
        messageManager.sendMessage("GameTimer: " + gameTimer);
        setCurrentState(GameState.IN_GAME);
        taskInGame = new BukkitRunnable(){
            @Override
            public void run() {
                gameTimer -= 1;
                countInGame += 1;
                if(isGameCanceled){
                    cancelGame();
                    taskInGame.cancel();
                }
                if(gameTimer <= 0){
                    bossbarManager.removeBossBar();
                    determineWinner();
                }
                if (bossbarManager.isTimerFinished()) {
                    dropItem();
                    bossbarManager.resetTimer();
                } else {
                    bossbarManager.decreaseTimer();
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

    // start the game after countdown
    private BossbarManager bossBarManager;
    public static void startGame(){
        setCurrentState(GameState.START_GAME);
        taskStartGame = new BukkitRunnable() {
            @Override
            public void run() {
                if(!getCurrentState().equals(GameState.START_GAME)) return;
                messageManager.sendMessage("Das Spiel startet in: " + (startGameTimer - countStartGame));
                countStartGame += 1;
                if(cancelStartGame){
                    taskStartGame.cancel();
                    countStartGame = 0;
                    cancelStartGame = false;
                    return;
                }
                if (countStartGame > startGameTimer || countStartGame == 0) {
                    setInGameState();
                    taskStartGame.cancel();
                    countStartGame = 0;
                    cancelStartGame = false;
                }
            }
        }.runTaskTimer(OneBlock_Main.getProvidingPlugin(OneBlock_Main.class), 0, 20);
    }

    public static void stopGameStart(){
        cancelStartGame = true;
        setLobbyState();
        messageManager.sendMessage("Der Start wurde abgebrochen! Warte auf Spieler");
    }

    private static void determineWinner() {
        if(!isState(GameState.IN_GAME)){return;}
        cancelGame();
        WinnerDetermination winnerDetermination = new WinnerDetermination();
        winnerDetermination.determineAndAnnounceWinner();
        GameManager.setAfterGameState();
    }

    private static BossbarManager bossbarManager;
    private static void dropItem(){

        if (countInGame >= dropItemTimer || countInGame == 0) {
            RandomItemGenerator.dropRandomItemStack();
            taskStartGame.cancel();
            countInGame = 0;
            isGameCanceled = false;
        }
    }

    private static void cancelGame(){
        taskInGame.cancel();
        countInGame = 0;
        isGameCanceled = false;
    }
}
