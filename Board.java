import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Control;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

//class still needs work!
public class Board extends GridPane
{
    private Tile[][] DataBoard;
    private final GridPane ViewBoard;

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
                StackPane tileHolder = new StackPane();
                tileHolder.setAlignment(Pos.CENTER);
                tileHolder.setStyle("-fx-background-color: #cea088");
                Tile blankTile = new Tile("blank");
                blankTile.getLetter().setFill(Color.WHITE);
                tileHolder.getChildren().add(blankTile.getLetter());

                //*********** un-comment to make "Drag-able" tiles in the board **************
                //DragController makeDrag = new DragController(tileHolder, true);

                ViewBoard.add(tileHolder, cols, rows);
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

}
