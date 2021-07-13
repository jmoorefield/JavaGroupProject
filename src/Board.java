
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Control;

import javafx.scene.layout.*;

//class still needs work!
public class Board
{
    private Tile[][] DataBoard;
    private GridPane ViewBoard;

    public Board()
    {
        this.ViewBoard = new GridPane();
        this.DataBoard = new Tile[15][15];
        this.ViewBoard.setPrefSize(500,500);
        this.ViewBoard.setAlignment(Pos.CENTER);

        for(int rows = 0; rows < 15; rows++)
        {
            for(int cols = 0; cols < 15; cols++ )
            {
                Tile blankTile = new Tile("blank");
                ViewBoard.add(blankTile.getHolder(), cols, rows);
                DataBoard[rows][cols] = blankTile;
            }
        }

        for (int i = 0; i < 15; i++) {
            ViewBoard.getColumnConstraints().add(new ColumnConstraints(Control.USE_PREF_SIZE, 35, Control.USE_PREF_SIZE, Priority.ALWAYS, HPos.CENTER, true));
            ViewBoard.getRowConstraints().add(new RowConstraints(Control.USE_PREF_SIZE, 35, Control.USE_PREF_SIZE, Priority.ALWAYS, VPos.CENTER, true));
        }
        ViewBoard.setGridLinesVisible(true);
    }

    public GridPane getViewBoard() { return this.ViewBoard; }
    //will need to set up the special tiles. it probably will take some creative approach or willpower



}
