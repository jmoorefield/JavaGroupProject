/* COP3252
Jess Moorefield, Roderick Quiel
Group Project
25 July 2021 
*/

/* This class controls the GUI and data portions of the game board and constructs a player's move. */

import java.util.*;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Control;
import javafx.scene.layout.*;

public class Board
{
    private static Tile[][] DataBoard;
    private static GridPane ViewBoard;

    // each list contains Strings that hold data about each tile in the current move
    private static List<List<String>> currentMove;
    private static boolean emptyBoard;

    public Board()
    {
        ViewBoard = new GridPane();
        DataBoard = new Tile[15][15];
        currentMove = new ArrayList<List<String>>();
        ViewBoard.setPrefSize(500,500);
        ViewBoard.setAlignment(Pos.CENTER);
        emptyBoard = true;

        // populate the board with blank tiles
        for(int rows = 0; rows < 15; rows++)
        {
            for(int cols = 0; cols < 15; cols++ )
            {
                Tile blankTile = new Tile(" ");
                ViewBoard.add(blankTile.getHolder(), cols, rows);
                DataBoard[rows][cols] = blankTile;
            }
        }

        // configure GUI
        for (int i = 0; i < 15; i++) {
            ViewBoard.getColumnConstraints().add(new ColumnConstraints(Control.USE_PREF_SIZE, 45, Control.USE_PREF_SIZE, Priority.ALWAYS, HPos.CENTER, true));
            ViewBoard.getRowConstraints().add(new RowConstraints(Control.USE_PREF_SIZE, 45, Control.USE_PREF_SIZE, Priority.ALWAYS, VPos.CENTER, true));
        }
        ViewBoard.setGridLinesVisible(true);
    }

    public GridPane getViewBoard() { return ViewBoard; }
    public static void setBoardStatus(boolean isEmpty) { emptyBoard = isEmpty; }

    // called from Tile's droppedTile event
    // temporarily adds a tile to currentMove
    public static void addTileToMove(String letter, int row, int column, String value) {
        List<String> tile =  new ArrayList<String>();
        tile.add(letter);
        tile.add(String.valueOf(row));
        tile.add(String.valueOf(column));
        tile.add(value);
        currentMove.add(tile);
    }

    // called from EndOfTurn
    // gets boolean returned from Player to decide whether to update the board's data
    // only updates if player has a successful move
    public static boolean updateBoard(boolean update) {
        if(update) {
            for(List<String> tile : currentMove) {
                DataBoard[Integer.parseInt(tile.get(1))][Integer.parseInt(tile.get(2))].setLetter(tile.get(0));
                DataBoard[Integer.parseInt(tile.get(1))][Integer.parseInt(tile.get(2))].setPointsValue(Integer.parseInt(tile.get(3)));
                DataBoard[Integer.parseInt(tile.get(1))][Integer.parseInt(tile.get(2))].setPreviousMove(true);
            }
            currentMove.clear();
            return true;
        }
        
        // if move isn't valid, replace the player's move with blank tiles
        else {
            for(List<String> tile : currentMove) {
                if(!DataBoard[Integer.parseInt(tile.get(1))][Integer.parseInt(tile.get(2))].isPreviousMove()) {
                    Tile t = new Tile(" "); 
                    DataBoard[Integer.parseInt(tile.get(1))][Integer.parseInt(tile.get(2))].setLetter(" ");
                    DataBoard[Integer.parseInt(tile.get(1))][Integer.parseInt(tile.get(2))].setPointsValue(0);
                    DataBoard[Integer.parseInt(tile.get(1))][Integer.parseInt(tile.get(2))].setPreviousMove(false);
                    ViewBoard.add(t.getHolder(), Integer.parseInt(tile.get(2)), Integer.parseInt(tile.get(1)));
                }
            }
         }
        currentMove.clear();
        return false;
    }

    public static int onEndOfTurn(Player player) {
        // these two options are placed here just so the function doesn't have to waste time
        if(currentMove.size() == 0 )
            return 3;
        
        // checks if the center tile is empty or not 
        else if(emptyBoard) {
            boolean flag = false;
            for(List<String> tile : currentMove) {
                if(Integer.parseInt(tile.get(1)) == 7 && Integer.parseInt(tile.get(2)) == 7)
                    flag = true;
            }
            if(!flag)
                return 4;
        }

        // does currentMove use a tile already on the board?
        boolean useETile = false;

        // variables to look at the beginning and end of currentMove
        int startCol = Integer.parseInt(currentMove.get(0).get(2));
        int endCol = Integer.parseInt(currentMove.get(currentMove.size()-1).get(2));
        int startRow = Integer.parseInt(currentMove.get(0).get(1));
        int endRow = Integer.parseInt(currentMove.get(currentMove.size()-1).get(1));

        // if player's move is only 1 tile, are they building
        // horizontal or vertical? 
        boolean oneHorizontal = false;
        boolean oneVertical = false;

        if(currentMove.size() == 1) {
            int row = Integer.parseInt(currentMove.get(0).get(1));
            int col = Integer.parseInt(currentMove.get(0).get(2));

            if(col + 1 < 15 && DataBoard[row][col + 1].getLetter() != " ") 
                oneHorizontal = true;
            else if(col - 1 >= 0 && DataBoard[row][col - 1].getLetter() != " ")
                oneHorizontal = true;
            else if(row + 1 < 15 && DataBoard[row + 1 ][col].getLetter() != " ")
                oneVertical = true;
            else if(row - 1 >= 0 && DataBoard[row - 1][col].getLetter() != " ")
                oneVertical = true;
        }

        // currentMove is horizontal
        if(endCol - startCol != 0 || oneHorizontal) {    
                // if you can go backwards from start tile 
                if(startCol - 1 >= 0) {
                    int currentTile = startCol - 1;
                    while(currentTile >= 0) {
                    // if there is a tile one col behind the current tile, include it at beginning of move
                        if(DataBoard[startRow][currentTile].getLetter() != " ") {
                            List<String> tile =  new ArrayList<String>();
                            tile.add(DataBoard[startRow][currentTile].getLetter());
                            tile.add(String.valueOf(DataBoard[startRow][currentTile].getRow()));
                            tile.add(String.valueOf(DataBoard[startRow][currentTile].getColumn()));
                            tile.add(String.valueOf(DataBoard[startRow][currentTile].getValue()));
                            currentMove.add(0, tile);
                            useETile = true;
                            currentTile--;
                        }
                        else
                            break;
                    }
                }

                if(currentMove.size() > 1) {
                    // if we still have middle tiles 
                    if(startCol + 1 < endCol) {
                    int currentMovePos = 0;
                    int distance = Integer.parseInt(currentMove.get(currentMovePos+1).get(2)) - Integer.parseInt(currentMove.get(0).get(2));
                        // if we need to add prexisting tiles to the move
                        while(distance > 1) {
                            List<String> tile =  new ArrayList<String>();
                            tile.add(DataBoard[startRow][startCol+1].getLetter());
                            tile.add(String.valueOf(DataBoard[startRow][startCol+1].getRow()));
                            tile.add(String.valueOf(DataBoard[startRow][startCol+1].getColumn()));
                            tile.add(String.valueOf(DataBoard[startRow][startCol+1].getValue()));

                            currentMove.add(currentMovePos + 1, tile);
                            currentMovePos++;
                            startCol += 2;
                            useETile = true;

                            if(startCol + 1 < endCol)
                                distance = Integer.parseInt(currentMove.get(currentMovePos+1).get(2)) - Integer.parseInt(currentMove.get(0).get(2));
                            else 
                                break;
                        }
                    }
                } 
            
                // if you can go forwards from end tile
                if(endCol + 1 < 15) {  
                    int currentTile = endCol + 1;
                    while(currentTile < 15) {
                    // if there is a tile one col after the current tile, include it at end of move
                        if(DataBoard[startRow][currentTile].getLetter() != " ") {
                            List<String> tile =  new ArrayList<String>();
                            tile.add(DataBoard[startRow][currentTile].getLetter());
                            tile.add(String.valueOf(DataBoard[startRow][currentTile].getRow()));
                            tile.add(String.valueOf(DataBoard[startRow][currentTile].getColumn()));
                            tile.add(String.valueOf(DataBoard[startRow][currentTile].getValue()));
                            currentMove.add(currentMove.size(), tile);
                            useETile = true;
                            currentTile++;
                        }
                        else
                            break;
                    }
                }
            }   

        // currentMove is vertical
        else if(endRow - startRow != 0 || oneVertical) {    
              // if you can go backwards from start tile 
              if(startRow - 1 >= 0) {
                int currentTile = startRow - 1;
                while(currentTile >= 0) {
                // if there is a tile one row above the current tile, include it at beginning of move
                    if(DataBoard[currentTile][startCol].getLetter() != " ") {
                        List<String> tile =  new ArrayList<String>();
                        tile.add(DataBoard[currentTile][startCol].getLetter());
                        tile.add(String.valueOf(DataBoard[currentTile][startCol].getRow()));
                        tile.add(String.valueOf(DataBoard[currentTile][startCol].getColumn()));
                        tile.add(String.valueOf(DataBoard[currentTile][startCol].getValue()));
                        currentMove.add(0, tile);
                        useETile = true;
                        currentTile--;
                    }
                    else
                        break;
                
                }
            }

            if(currentMove.size() > 1) {
                // if we still have middle tiles 
                if(startRow + 1 < endRow) {
                int currentMovePos = 0;
                int distance = Integer.parseInt(currentMove.get(currentMovePos+1).get(1)) - Integer.parseInt(currentMove.get(0).get(1));
                    // if we need to add prexisting tiles to the move
                    while(distance > 1) {
                        List<String> tile =  new ArrayList<String>();
                        tile.add(DataBoard[startRow+1][startCol].getLetter());
                        tile.add(String.valueOf(DataBoard[startRow+1][startCol].getRow()));
                        tile.add(String.valueOf(DataBoard[startRow+1][startCol].getColumn()));
                        tile.add(String.valueOf(DataBoard[startRow+1][startCol].getValue()));

                        currentMove.add(currentMovePos + 1, tile);
                        currentMovePos++;
                        startRow += 2;
                        useETile = true;

                        if(startCol + 1 < endCol)
                            distance = Integer.parseInt(currentMove.get(currentMovePos+1).get(1)) - Integer.parseInt(currentMove.get(0).get(1));
                        else 
                            break;
                    }
                }
            } 
        
            // if you can go forwards from end tile
            if(endRow + 1 < 15) {  
                int currentTile = endRow + 1;
                while(currentTile < 15) {
                // if there is a tile one row below the current tile, include it at end of move
                    if(DataBoard[currentTile][startCol].getLetter() != " ") {
                        List<String> tile =  new ArrayList<String>();
                        tile.add(DataBoard[currentTile][startCol].getLetter());
                        tile.add(String.valueOf(DataBoard[currentTile][startCol].getRow()));
                        tile.add(String.valueOf(DataBoard[currentTile][startCol].getColumn()));
                        tile.add(String.valueOf(DataBoard[currentTile][startCol].getValue()));
                        currentMove.add(currentMove.size(), tile);
                        useETile = true;
                        currentTile++;
                    }
                    else
                        break;
                
                }
            } 
        }

        // the only information Player needs to construct the final move 
        // is the letter and tile value
        // so create a new container to only send that information
         List<List<String>> finalMove = new ArrayList<List<String>>();

        for(List<String> originalTile : currentMove) {
            List<String> lessTile =  new ArrayList<String>();

            lessTile.add(originalTile.get(0));  // letter
            lessTile.add(originalTile.get(3));  // points value 
            finalMove.add(lessTile);
        }

        // determine if the player's move is valid or not
        boolean validMove = updateBoard(player.move(finalMove, useETile));

        // valid, continue game
        if(!Game.letters.isLetterBagEmpty() && validMove)
            return 0;

        // invalid, continue game
        else if(!validMove)
            return 1;

        // catchall
        return 2;
    }

    // clear the data and GUI of the current tiles when a move is canceled
    // then reset the player's rack
    public static void onCancel(Player player) {
        for(List<String> tile : currentMove) {
            if(!DataBoard[Integer.parseInt(tile.get(1))][Integer.parseInt(tile.get(2))].isPreviousMove()) {
                Tile t = new Tile(" "); 
                DataBoard[Integer.parseInt(tile.get(1))][Integer.parseInt(tile.get(2))].setLetter(" ");
                DataBoard[Integer.parseInt(tile.get(1))][Integer.parseInt(tile.get(2))].setPointsValue(0);
                DataBoard[Integer.parseInt(tile.get(1))][Integer.parseInt(tile.get(2))].setPreviousMove(false);
                ViewBoard.add(t.getHolder(), Integer.parseInt(tile.get(2)), Integer.parseInt(tile.get(1)));
            }
        }
        player.getPlayerRack().clearRack();;
        currentMove.clear();
    }

    // calculate each player's final scores to determine a winner
    public static int getWinner(Player[] players) {
        int[] finalScores = new int[players.length];

        for(int i = 0; i < players.length; ++i) {
            finalScores[i] = players[i].getScore() - players[i].getPlayerRack().sumRack();
        }

        int max = finalScores[0];
        int winner = 1;
        for(int j = 0; j < finalScores.length; ++j) {
            if(finalScores[j] > max) {
                max = finalScores[j];
                winner = j + 1;
            }
        }
        return winner;
    }
}
