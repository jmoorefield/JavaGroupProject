// needs: way to ask user for value of blank tile
// implement special points for tile 
// implement checking the words the move touches

import java.util.*;
public class Player
{
    private int score;
    private final PlayerRack playerRack;
    private final String name;

    public Player(LetterBag letterBag, String st)
    {
        this.name = st;
        this.playerRack = new PlayerRack(letterBag);
        this.score = 0;
    }

    public String getName() { return name; }

    public PlayerRack getPlayerRack() { return playerRack; }

    public void move(List<List<String>> playerMove) {
    String word = "";
    int points = 0;

    for(List<String> tile : playerMove) {
        if(tile.get(0).equals("blank")) {
            System.out.print("Please enter the letter used for the blank tile: ");
        //    letter = scanner.next();    // this line intentionally left not done 
           //  word += letter;
        } 

        word += tile.get(0);
        points += Integer.parseInt(tile.get(1));
        points *= Integer.parseInt(tile.get(2));
    }

        if(Game.dict.isValidWord(word.toLowerCase())) {
            score += points;
            playerRack.updateRack(true, playerMove);
            Board.updateBoard(true);
        }
        else if(word.length() < 2 || word.length() > 15) {
            playerRack.updateRack(false, playerMove);
            Board.updateBoard(false);
        }

        else {
            playerRack.updateRack(false, playerMove);
            Board.updateBoard(false);
        }
    }
}
