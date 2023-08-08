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
        if(numPlayers < 2) throw new IllegalArgumentException("players should be 2 or more");
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
        playGame();

    }

    private void playGame() throws InterruptedException {

        for (Player player : players) {
            Thread thread = new Thread(player);
            thread.start();
        }
        while (players.size() > 1) {
            Thread.sleep(50);
            synchronized (lock) {
                lock.notify();
            }
        }


    }


}
