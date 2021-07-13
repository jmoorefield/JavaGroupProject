import java.util.Vector;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Control;
import javafx.scene.layout.*;

//rack is setup. changes need to be made
// JESS: i think it would be easier to change DataRack to an ArrayList, rather than an array
// JESS: see proposed modifications below 
public class PlayerRack
{
    // private List<Tile> DataRack;
    private final Tile[] DataRack;
    private final GridPane ViewRack;

    public PlayerRack(LetterBag letterBag)
    {
     //   DataRack = new ArrayList<Tile>();
        DataRack = new Tile[7];
        for(int x = 0; x < 7; x++)
        {
            Tile tile = letterBag.getTile();
            // DataRack.add(Tile);
            DataRack[x] = tile;
        }

        ViewRack = new GridPane();
        for(int x = 0; x < 7; x++)
        {
         // Tile tile = DataRack.get(x);
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

    // this function updates DataRack and ViewRack based on the player's move
    // parameters are a flag to say if move is valid and the tiles of the move
    /*public void updateRack(boolean successfulMove, Vector<Tile> playerMove) {
        int vrCounter = 0;
        if(successfulMove) {
            for(Tile t : playerMove) {
                dataRack.remove(dataRack.indexOf(t.getLetter()));
                // remove tile from viewrack
            }

            while(!Game.letterBag.isLetterBagEmpty() && dataRack.size() < 7) {
                if(//viewRack at vrCounter is empty) {
                    Tile tile = letterBag.getTile();
                    dataRack.add(tile);
                    ViewRack.add(tile.getHolder(), vrCounter, 0);
                    vrCounter++;
                }
            }
        }
    } 
*/
    public GridPane getViewRack()
    {
        return ViewRack;
    }
}
