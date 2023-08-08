package org.atypon.oldmaidgame.models.players;

import org.atypon.oldmaidgame.GameStatus;
import org.atypon.oldmaidgame.models.cards.Card;
import org.atypon.oldmaidgame.models.cards.CardSuit;

import static org.atypon.oldmaidgame.utils.GameUtils.removeAllMatchingPairsFromHand;

public class AIPlayer extends Player {
    private final Object lock;

    public AIPlayer(String name, Object lock) {
        super(name);
        this.lock = lock;
    }


    @Override
    public void run() {
        GameStatus gameStatus = GameStatus.getInstance();
        synchronized (lock) {
            while (gameStatus.getPlayersCount() > 1) {

                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if (gameStatus.isStillInGame(this) && gameStatus.isPlayerTurn(this)) {
                    this.takeTurn();
                    if (gameStatus.checkIsGameOver()) break;
                    gameStatus.setNextPlayer(gameStatus.getNextPlayer(this));
                }

                lock.notify();

            }
        }
    }


    @Override
    public void addCardToHand(Card card) {
        if (card.getSuit() == CardSuit.JOKER) hand.add(card);
        else hand.add(0, card);

    }

    @Override
    public void takeTurn() {

        GameStatus gameStatus = GameStatus.getInstance();
        Player prePlayer = gameStatus.getPreviousPlayer(this);
        Card card = prePlayer.giveRandomCard();
        this.addCardToHand(card);
        removeAllMatchingPairsFromHand(this);
        if (this.getCurrentHandSize() == 0) gameStatus.removePlayerFromGame(this);

    }

    @Override
    public void removeCardFromHand(Card card) {
        this.hand.remove(card);
    }


    @Override
    public Card giveRandomCard() {

        GameStatus gameStatus = GameStatus.getInstance();
        Card card = hand.remove(0);
        removeAllMatchingPairsFromHand(this);
        if (this.getCurrentHandSize() == 0) gameStatus.removePlayerFromGame(this);

        return card;
    }

    @Override
    public int getCurrentHandSize() {
        return this.hand.size();
    }

    @Override
    public String toString() {
        return "AIPlayer :  name = " + name;
    }


}
