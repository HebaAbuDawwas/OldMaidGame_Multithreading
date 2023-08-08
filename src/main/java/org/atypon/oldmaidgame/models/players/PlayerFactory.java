package org.atypon.oldmaidgame.models.players;

public class PlayerFactory {
    public static Player createPlayer(String name, Object lock, String playerType) {
        if (name == null) {
            throw new IllegalArgumentException("you should provide player name");
        }
       if(playerType.equalsIgnoreCase("AIPlayer"))
            return new AIPlayer(name, lock);
       return null;
    }
}