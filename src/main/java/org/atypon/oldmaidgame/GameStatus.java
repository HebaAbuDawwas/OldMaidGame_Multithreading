package org.atypon.oldmaidgame;


import org.atypon.oldmaidgame.models.players.Player;

public class GameStatus {
    private static GameStatus instance;

    private static Game game;
    private static Player NextPlayer;

    private GameStatus() {
    }

    public static GameStatus getInstance() {
        if (instance == null) {
            synchronized (GameStatus.class) {
                if (instance == null) instance = new GameStatus();
            }
        }
        return instance;
    }

    public static void setGame(Game game) {
        GameStatus.game = game;
    }

    public int getPlayersCount() {
        return game.getPlayers().size();
    }

    public void setNextPlayer(Player nextPlayer) {
        NextPlayer = nextPlayer;
    }

    public int getCardsLeft() {
        int cardsLeft = 0;
        for (Player player : game.getPlayers())
            cardsLeft += player.getCardCount();
        return cardsLeft;
    }

    public Player getPreviousPlayer(Player player) {
        if (game.getPlayers().isEmpty()) return null;
        int currPlayerIndex = game.getPlayers().indexOf(player);
        return game.getPlayers().get((currPlayerIndex == 0) ? game.getPlayers().size() - 1 : currPlayerIndex - 1);

    }

    public Player getNextPlayer(Player player) {
        if (game.getPlayers().isEmpty()) return null;
        int currPlayerIndex = game.getPlayers().indexOf(player);
        return game.getPlayers().get((currPlayerIndex == getPlayersCount() - 1) ? 0 : currPlayerIndex + 1);

    }

    public void removePlayerFromGame(Player player) {
        game.getPlayers().remove(player);
    }

    public boolean isStillInGame(Player player) {
        return game.getPlayers().contains(player);
    }

    public boolean checkIsGameOver() {
        if (getCardsLeft() == 1 && getPlayersCount() == 1) {
            System.out.println(game.getPlayers().get(0) + " is the loser with joker ");
            game.getPlayers().remove(0);
            return true;
        }
        return false;
    }

    public boolean isNextPlayer(Player player) {
        return player == GameStatus.NextPlayer;
    }
}
