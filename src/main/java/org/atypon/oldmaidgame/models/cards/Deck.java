package org.atypon.oldmaidgame.models.cards;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private static Deck instance;
    private final List<Card> cards;

    private Deck() {
        this.cards = new ArrayList<>();
        initializeDeck();
        shuffle();
    }

    public static Deck getInstance() {
        if (instance == null || instance.cards.isEmpty()) {
            synchronized (Deck.class){
                if (instance == null || instance.cards.isEmpty())
                    instance = new Deck();
            }}
        return instance;
    }

    private void initializeDeck() {
        for (CardSuit suit : CardSuit.values()) {
            for (CardRank rank : CardRank.values()) {
                if (rank != CardRank.JOKER && suit != CardSuit.JOKER) cards.add(new Card(suit, rank));
            }
        }
        cards.add(new Card(CardSuit.JOKER, CardRank.JOKER));
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card deal() {
        if (cards.isEmpty()) {
            return null;
        }
        return cards.remove(cards.size() - 1);
    }

    public int size() {
        return cards.size();
    }
}
