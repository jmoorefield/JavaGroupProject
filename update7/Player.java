import java.util.*;
public class Player
{
    private int score;
    private final PlayerRack playerRack;

    public Player(LetterBag letterBag)
    {
        this.playerRack = new PlayerRack(letterBag);
        this.score = 0;
    }

    public PlayerRack getPlayerRack() { return playerRack; }

    public int getScore() { return score; }

    public boolean move(List<List<String>> playerMove, boolean useETile) {
    String word = "";
    int points = 0;

    for(List
    <String> tile : playerMove) {
        if(tile.get(0).equals("blank")) {
           //TODO: how to get value for blank tile
        } 

        word += tile.get(0);
        points += Integer.parseInt(tile.get(1));
        points *= Integer.parseInt(tile.get(2));
    }

    System.out.println("word is: " + word);

        if(useETile) {
            if(word.length() < 1 || word.length() > 15) {
                playerRack.updateRack(false, playerMove);
                return false;
            }
        }

        else { 
            if(word.length() < 2 || word.length() > 15) {
            playerRack.updateRack(false, playerMove);
            return false;
            }
        }
    
        if(Game.dict.isValidWord(word.toLowerCase())) {
            score += points;
            playerRack.updateRack(true, playerMove);
            Board.setBoardStatus(false);
            return true;
        }
    playerRack.updateRack(false, playerMove);
    return false;
    }
}
