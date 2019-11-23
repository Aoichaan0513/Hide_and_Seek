package org.aoichaan0513.hide_and_seek.API;

public class GameManager {
    private static GameManager.GameState game = GameManager.GameState.NONE;

    public static boolean isGame(GameManager.GameState game) {
        return GameManager.game.equals(game);
    }

    public static boolean isGame() {
        return game.equals(GameManager.GameState.GAME);
    }

    public static void setGameState(GameManager.GameState game) {
        GameManager.game = game;
    }

    public static GameManager.GameState getGameState() {
        return game;
    }

    public enum GameState {
        READY,
        GAME,
        NONE;
    }
}
