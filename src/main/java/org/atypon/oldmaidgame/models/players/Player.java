package org.atypon.oldmaidgame.models.players;

import org.atypon.oldmaidgame.models.cards.Card;

import java.util.ArrayList;
import java.util.List;

public abstract class Player implements Runnable {
    protected String name;
    protected List<Card> hand;

    public Player(String name) {
        this.name = name;
        hand = new ArrayList<>();
    }

    public abstract void addCardToHand(Card card);

    public abstract void takeTurn();

    public abstract boolean removeCard(Card card);

    public abstract Card giveRandomCard();

    public abstract int getCardCount();


    public List<Card> getHand() {
        return this.hand;
    }
}
