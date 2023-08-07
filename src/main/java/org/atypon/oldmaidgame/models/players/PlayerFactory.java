package org.atypon.oldmaidgame.models.players;

public class PlayerFactory {
    public static Player createPlayer(String name, Object lock, String playerType) {
        if (name == null) {
            throw new IllegalArgumentException("you should provide player name");
        }
        // here we can extend the code to add human player and so on
        return new AIPlayer(name, lock);
    }
}