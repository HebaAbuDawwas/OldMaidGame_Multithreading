package org.atypon.oldmaidgame.models.cards;

import org.atypon.oldmaidgame.models.cards.CardRank;
import org.atypon.oldmaidgame.models.cards.CardSuit;

public class Card {
    private final CardSuit suit;
    private final CardRank rank;

    public Card(CardSuit suit, CardRank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public CardSuit getSuit() {
        return suit;
    }

    public CardRank getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return "Card : suit=" + suit + " rank=" + rank;
    }
}

