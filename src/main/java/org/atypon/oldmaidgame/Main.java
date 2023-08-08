package org.atypon.oldmaidgame;

public class Main {
    public static void main(String[] args) throws InterruptedException {
       if (args.length < 1){
           System.out.println("Please provide number of players");
       }
        int playersNumber = Integer.parseInt(args[0]);
        Game game = new Game(playersNumber);
        game.start();

    }
}