/* COP3252
Jess Moorefield, Roderick Quiel
Group Project
25 July 2021 
*/

/* This class contains functionality for a game player. */

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

    // determine if a move is valid and calculate the score if so
    public boolean move(List<List<String>> playerMove, boolean useETile) {
    String word = "";
    int points = 0;

    // for every tile in the player's current move
    // string the letters together and add up the points
    for(List <String> tile : playerMove)
    {
        word += tile.get(0);
        points += Integer.parseInt(tile.get(1));    //This is where you can double, or triple points if they are on a colored spot
        //point += Integer.parse(tile.get(2));  //CHANGE THE VALUE OF THE TILE (DOUBLES)
    }
    //TO CHANGE THE WHOLE WORD, DO IT OUT HERE
    
    // check if the word's length is allowed
    // useETile = if the player is using a tile already on the board
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
    
        // check if the word is in the dictionary
        // if so, add the points to the player's score
        // replace the missing tiles in their rack 
        // and updatte the board
        if(Game.dict.isValidWord(word.toLowerCase())) {
            score += points;
            playerRack.updateRack(true, playerMove);
            Board.setBoardStatus(false);
            return true;
        }
    // if a move is invalid, put the tiles back in the player's rack
    playerRack.updateRack(false, playerMove);
    return false;
    }
}
 