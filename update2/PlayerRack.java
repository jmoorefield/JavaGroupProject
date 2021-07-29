import java.util.*;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Control;
import javafx.scene.layout.*;

public class PlayerRack
{
    private List<Tile> DataRack;
    private final GridPane ViewRack;

    public PlayerRack(LetterBag letterBag)
    {
        DataRack = new ArrayList<Tile>();
        ViewRack = new GridPane();

        for(int x = 0; x < 7; x++)
        {
            Tile tile = letterBag.getTile();
            DataRack.add(tile);
        }

        for(int x = 0; x < 7; x++)
        {
            Tile tile = DataRack.get(x);
            ViewRack.add(tile.getHolder(),x,0);
        }

        ViewRack.setAlignment(Pos.CENTER);
        ViewRack.getRowConstraints().add(new RowConstraints(Control.USE_PREF_SIZE, 50, Control.USE_PREF_SIZE, Priority.ALWAYS, VPos.CENTER, true));
        for(int y = 0; y < 7; y++)
            ViewRack.getColumnConstraints().add(new ColumnConstraints(Control.USE_PREF_SIZE, 50, Control.USE_PREF_SIZE, Priority.ALWAYS, HPos.CENTER, true));

        ViewRack.setGridLinesVisible(true);
    }

    public GridPane getViewRack() { return ViewRack; }

    public void updateRack(boolean successfulMove, List<List<String>> playerMove) {
        int vrCounter = 0;

        if(successfulMove) {
            for(List<String> tile : playerMove) {
                DataRack.remove(DataRack.indexOf(tile.get(0)));
                // make ViewRack image invisible
            }

        /*    while(!Game.letterBag.isLetterBagEmpty() && DataRack.size() < 7) {
                if(//ViewRack at vrCounter is invisible && vrCounter < ViewRack.gridPaneWidth()) {
                    Tile tile = letterBag.getTile();
                    DataRack.add(tile);
                    ViewRack.add(tile.getHolder(),vrCounter,0);
                    vrCounter++;
                }
            } */
        }
    } 
}