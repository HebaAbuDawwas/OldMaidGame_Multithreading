package org.atypon.oldmaidgame;

import org.atypon.oldmaidgame.models.cards.Deck;
import org.atypon.oldmaidgame.models.players.Player;

import java.util.Vector;

import static org.atypon.oldmaidgame.utils.GameUtils.dealCards;
import static org.atypon.oldmaidgame.utils.GameUtils.initializePlayers;

public class Game {
    private final Object lock = new Object();
    private final Vector<Player> players;
    private final Deck deck = Deck.getInstance();


    public Game(int numPlayers) {
        players = initializePlayers(numPlayers, lock);
        GameStatus.getInstance();
        GameStatus.setGame(this);
        GameStatus.getInstance().setNextPlayer(players.get(0));
    }


    public Vector<Player> getPlayers() {
        return players;
    }

    public void start() throws InterruptedException {

        dealCards(deck, players);
        playGameWithParallelPrograming();

    }

    private void playGameWithParallelPrograming() throws InterruptedException {

        while (GameStatus.getInstance().getPlayersCount() > 1) {
            for (Player player : players) {
                Thread thread = new Thread(player);
                thread.start();
            }
            Thread.sleep(1000);
            synchronized (lock) {
                lock.notify();
            }

        }


    }


}
