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

        for(int rows = 0; rows < 15; rows++)
        {
            for(int cols = 0; cols < 15; cols++ )
            {
                Tile blankTile = new Tile(" ");
                ViewBoard.add(blankTile.getHolder(), cols, rows);
                DataBoard[rows][cols] = blankTile;
            }
        }

        for (int i = 0; i < 15; i++) {
            ViewBoard.getColumnConstraints().add(new ColumnConstraints(Control.USE_PREF_SIZE, 45, Control.USE_PREF_SIZE, Priority.ALWAYS, HPos.CENTER, true));
            ViewBoard.getRowConstraints().add(new RowConstraints(Control.USE_PREF_SIZE, 45, Control.USE_PREF_SIZE, Priority.ALWAYS, VPos.CENTER, true));
        }
        ViewBoard.setGridLinesVisible(true);
        //ViewBoard.setStyle("-fx-background-color: #18733e");
    }

    public GridPane getViewBoard() { return ViewBoard; }
    public static void setBoardStatus(boolean isEmpty) { emptyBoard = isEmpty; }

    // gets information from tile class to temporarily add a tile to the container
    public static void addTileToMove(String letter, int row, int column, String value) {
        List<String> tile =  new ArrayList<String>();
        tile.add(letter);
        tile.add(String.valueOf(row));
        tile.add(String.valueOf(column));
        tile.add(value);
        currentMove.add(tile);
    }

    // calculates special points value based on location of tile on board
    public static int specialTiles(int row, int column) { return 1;}

    // gets boolean returned from player class to decide whether to update databoard
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

        if(currentMove.size() == 0 ) {
            return 3;
        }
        else if(emptyBoard) {
            boolean flag = false;
            for(List<String> tile : currentMove) {
                if(Integer.parseInt(tile.get(1)) == 7 && Integer.parseInt(tile.get(2)) == 7)
                    flag = true;
            }
            if(!flag)
                return 4;
        }
         // the only information the player class needs for the final move is
        // letter, tile value, and special points
         // so create a new container to only send that information
        List<List<String>> finalMove = new ArrayList<List<String>>();
        boolean useETile = false;
        int startCol = Integer.parseInt(currentMove.get(0).get(2));
        int endCol = Integer.parseInt(currentMove.get(currentMove.size()-1).get(2));
        int startRow = Integer.parseInt(currentMove.get(0).get(1));
        int endRow = Integer.parseInt(currentMove.get(currentMove.size()-1).get(1));

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

        // we are going horizontally
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

        // we are going vertically
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

        for(List<String> originalTile : currentMove) {
            List<String> lessTile =  new ArrayList<String>();

            lessTile.add(originalTile.get(0));  // letter
            lessTile.add(originalTile.get(3));  // points value 

            // special tile value at row and column
            lessTile.add(String.valueOf(specialTiles(Integer.parseInt(originalTile.get(1)), Integer.parseInt(originalTile.get(2)))));
            finalMove.add(lessTile);
        }

        boolean validMove = updateBoard(player.move(finalMove, useETile));

        if(!Game.letters.isLetterBagEmpty() && validMove)
            return 0;

        else if(!validMove)
            return 1;

        return 2;
    }

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

    // did i write this correctly (?)
    public static void clearMove() {
        for(int i = 0; i < ViewBoard.getChildren().size(); i++)
        {
            for(List<String> tile : currentMove) {
                if(ViewBoard.getChildren().get(i) instanceof StackPane && ViewBoard.getChildren().get(i).isDisabled() 
                && ViewBoard.getRowIndex(ViewBoard.getChildren().get(i)) == Integer.parseInt(tile.get(1)) 
                && ViewBoard.getColumnIndex(ViewBoard.getChildren().get(i)) == Integer.parseInt(tile.get(2)))
                    ((StackPane) ViewBoard.getChildren().get(i)).getChildren().remove(0);
            }
        }
    }
}
