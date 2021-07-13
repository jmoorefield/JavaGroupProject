import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Control;
import javafx.scene.layout.*;

//rack is setup. changes need to be made
public class PlayerRack
{

    private final Tile[] DataRack;
    private final GridPane ViewRack ;
    public PlayerRack(LetterBag letterBag)
    {
        DataRack = new Tile[7];
        for(int x = 0; x < 7; x++)
        {
            Tile tile = letterBag.getTile();
            DataRack[x] = tile;
        }

        ViewRack = new GridPane();
        for(int x = 0; x < 7; x++)
        {
            Tile tile = DataRack[x];
            ViewRack.add(tile.getHolder(),x,0);
        }
        ViewRack.setAlignment(Pos.CENTER);
        ViewRack.getRowConstraints().add(new RowConstraints(Control.USE_PREF_SIZE, 50, Control.USE_PREF_SIZE, Priority.ALWAYS, VPos.CENTER, true));
        for(int y = 0; y < 7; y++)
            ViewRack.getColumnConstraints().add(new ColumnConstraints(Control.USE_PREF_SIZE, 50, Control.USE_PREF_SIZE, Priority.ALWAYS, HPos.CENTER, true));
        ViewRack.setGridLinesVisible(true);

    }
    public void PlayerMove(boolean set)
    {

    }
    public GridPane getViewRack()
    {
        return ViewRack;
    }



}
