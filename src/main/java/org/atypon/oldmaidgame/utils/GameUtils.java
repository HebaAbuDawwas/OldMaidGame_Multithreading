package org.atypon.oldmaidgame.utils;


import org.atypon.oldmaidgame.models.cards.Card;
import org.atypon.oldmaidgame.models.cards.CardRank;
import org.atypon.oldmaidgame.models.cards.CardSuit;
import org.atypon.oldmaidgame.models.cards.Deck;
import org.atypon.oldmaidgame.models.players.Player;
import org.atypon.oldmaidgame.models.players.PlayerFactory;

import java.util.*;

public class GameUtils {
    public static Vector<Player> initializePlayers(int numPlayers, Object lock) {
        Vector<Player> players = new Vector<>();
        for (int i = 1; i <= numPlayers; i++) {
            players.add(PlayerFactory.createPlayer("Player "+ i ,lock, "AIPlayer"));
        }
        return players;
    }
    public static void dealCards(Deck deck, List<Player> players) {
        while (deck.size() > 0) {
            for (Player player : players) {
                Card card = deck.deal();
                if (card != null) {
                    player.addCardToHand(card);
                }
            }
        }
        for (Player player : players) {
            removeAllMatchingPairsFromHand(player);
        }
    }
    public static void removeAllMatchingPairsFromHand(Player player){
        Map<CardRank, List<Card>> rankMap = new HashMap<>();
        Map<CardSuit, List<Card>> suitMap = new HashMap<>();
        List <Card> hand = player.getHand();
        for (Card card : hand) {
            rankMap.computeIfAbsent(card.getRank(), k -> new ArrayList<>()).add(card);
            suitMap.computeIfAbsent(card.getSuit(), k -> new ArrayList<>()).add(card);
        }
        for (int i =0 ;i< hand.size()-1 ;i++) {
            Card curCard = hand.get(i);
            if(hand.get(i).getSuit() == CardSuit.JOKER) continue;
            List <Card>  matchRank = rankMap.get(curCard.getRank());
            List <Card>  matchSuit  = getMatchingSuits(suitMap, curCard);
            List<Card> sharedItems = new ArrayList<>(matchRank);
            sharedItems.retainAll(matchSuit);
            sharedItems.remove(curCard);
            if(!sharedItems.isEmpty()){
                player.removeCard(curCard);
                player.removeCard(sharedItems.get(0));
                matchSuit.remove(curCard);
                matchRank.remove(sharedItems.get(0));
                i--;
            }

        }
    }

    private static List<Card> getMatchingSuits(Map<CardSuit, List<Card>> suitMap, Card card) {
        List<Card> matchingSuits = new ArrayList<>();
        if(card.getSuit() == CardSuit.DIAMONDS || card.getSuit() == CardSuit.HEARTS){
            if(suitMap.get(CardSuit.DIAMONDS) != null && !suitMap.get(CardSuit.DIAMONDS).isEmpty())
                matchingSuits.addAll(suitMap.get(CardSuit.DIAMONDS));
            if(suitMap.get(CardSuit.HEARTS) != null && !suitMap.get(CardSuit.HEARTS).isEmpty())
                matchingSuits.addAll(suitMap.get(CardSuit.HEARTS));

        }
        else if(card.getSuit() == CardSuit.CLUBS || card.getSuit() == CardSuit.SPADES){
            if(suitMap.get(CardSuit.CLUBS) != null && !suitMap.get(CardSuit.CLUBS).isEmpty())
                matchingSuits.addAll(suitMap.get(CardSuit.CLUBS));
            if(suitMap.get(CardSuit.SPADES) != null && !suitMap.get(CardSuit.SPADES).isEmpty())
                matchingSuits.addAll(suitMap.get(CardSuit.SPADES));
        }
        return matchingSuits;
    }
}
