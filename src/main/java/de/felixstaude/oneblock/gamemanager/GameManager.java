package de.felixstaude.oneblock.gamemanager;

import de.felixstaude.oneblock.main.OneBlock_Main;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

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
        GameTimer timer = new GameTimer();
        timer.start();
        taskInGame = new BukkitRunnable(){

            @Override
            public void run() {
                countInGame += 1;
                System.out.println(countInGame);
                if(cancelInGame){
                    taskInGame.cancel();
                    countInGame = 0;
                    cancelInGame = false;
                }
                if (countInGame > 10 || countInGame == 0) {
                    //RandomItemGenerator.dropRandomItemStack();
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

/*    public static void setGameStartState(){
        setCurrentState(GameState.START_GAME);
    }
*/
    public static void startGame(){
        setCurrentState(GameState.START_GAME);
        taskStartGame = new BukkitRunnable() {
            @Override
            public void run() {
                if(!getCurrentState().equals(GameState.START_GAME)) return;
                countStartGame += 1;
                System.out.println(countStartGame);
                if(cancelStartGame){
                    taskStartGame.cancel();
                    countStartGame = 0;
                    cancelStartGame = false;
                    return;
                }
                if (countStartGame > 10 || countStartGame == 0) {
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
    }
}
