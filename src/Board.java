import java.util.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

public class Board
{
    private static Tile[][] DataBoard;
    private final GridPane ViewBoard;
    private static List<List<String>> currentMove;
    private Button EndTurn;
    private Button CancelMove;
    private EventHandler<MouseEvent> END;
    private EventHandler<MouseEvent> CANCEL;


    private BooleanProperty isEnd;

    public Board()
    {
        this.ViewBoard = new GridPane();
        DataBoard = new Tile[15][15];
        currentMove = new ArrayList<List<String>>();
        this.ViewBoard.setPrefSize(500,500);
        this.ViewBoard.setAlignment(Pos.CENTER);

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

        this.EndTurn = new Button("End Turn");
        this.CancelMove = new Button("Cancel Turn");
        buttonHandlers();
        setButtonHandlers();
        this.isEnd.set(true);
    }

    public GridPane getViewBoard() { return this.ViewBoard; }


    // gets information from tile class to temporarily add a tile to the container
    public static void addTileToMove(String letter, int row, int column, String value) {
        List<String> tile =  new ArrayList<String>();
        tile.add(letter);
        tile.add(String.valueOf(row));
        tile.add(String.valueOf(column));
        tile.add(value);
        currentMove.add(tile);
    }

    // the only information the player class needs for the final move is
    // letter, tile value, and special points
    // so create a new container to only send that information
    public List<List<String>> onEndofMove() {
        List<List<String>> finalMove = new ArrayList<List<String>>();

        for(List<String> originalTile : currentMove) {
            List<String> lessTile =  new ArrayList<String>();
            lessTile.add(originalTile.get(0));
            lessTile.add(originalTile.get(3));
            lessTile.add(String.valueOf(specialTiles(Integer.parseInt(originalTile.get(1)), Integer.parseInt(originalTile.get(2)))));
        }
        return finalMove;
    }

    public void onCancel() {
        currentMove.clear();
    }

    // calculates special points value based on location of tile on board
    public int specialTiles(int row, int column) { return 1;}

    // gets boolean returned from player class to decide whether to update databoard
    // only updates if player has a successful move
    public static void updateBoard(boolean update) {
        if(update) {
            for(List<String> tile : currentMove) {
                DataBoard[Integer.parseInt(tile.get(1))][Integer.parseInt(tile.get(2))].setLetter(tile.get(0));
            }
        }
        currentMove.clear();
    }

    private void buttonHandlers()
    {
        //add end of turn logic
        END = event ->
        {


        };

        //add cancel turn logic
        CANCEL = event ->
        {

        };
    }

    private void setButtonHandlers()
    {
        isEnd = new SimpleBooleanProperty();
        isEnd.addListener((observable, oldValue, newValue) -> {
            if(newValue)
            {
                EndTurn.addEventFilter(MouseEvent.MOUSE_CLICKED, END);
                CancelMove.addEventFilter(MouseEvent.MOUSE_CLICKED, CANCEL);
            }
            else
            {
                EndTurn.removeEventFilter(MouseEvent.MOUSE_CLICKED, END);
                CancelMove.removeEventFilter(MouseEvent.MOUSE_CLICKED, CANCEL);

            }
        });
    }
}
