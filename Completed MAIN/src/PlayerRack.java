

/* COP3252
Jess Moorefield, Roderick Quiel
Group Project
25 July 2021 
*/

/* This class contains functionality for the rack of a player's current tiles. */

import java.util.*;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Control;
import javafx.scene.layout.*;

public class PlayerRack
{
    // there is a data component of the rack and a visual GUI component
    private List<Tile> DataRack;
    private GridPane ViewRack;

    public PlayerRack(LetterBag letterBag)
    {
        DataRack = new ArrayList<Tile>();
        ViewRack = new GridPane();

        // a rack contains 7 tiles
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

        // configure GUI component
        ViewRack.setAlignment(Pos.CENTER);
        ViewRack.getRowConstraints().add(new RowConstraints(Control.USE_PREF_SIZE, 45, Control.USE_PREF_SIZE, Priority.ALWAYS, VPos.CENTER, true));
        for(int y = 0; y < 7; y++)
            ViewRack.getColumnConstraints().add(new ColumnConstraints(Control.USE_PREF_SIZE, 45, Control.USE_PREF_SIZE, Priority.ALWAYS, HPos.CENTER, true));
        ViewRack.setGridLinesVisible(true);
    }

    public GridPane getViewRack() { return ViewRack; }

    // called every time a player makes a move
    // successfulMove is determined by move() in Player
    public void updateRack(boolean successfulMove, List<List<String>> playerMove)
    {
        // for every tile used in the move
        // remove it from the player's rack
        // and add a random new one
        if(successfulMove) {
            for (int x = 0; x < playerMove.size(); x++) {
                for (int y = 0; y < DataRack.size(); y++) {
                    if (DataRack.get(y).getLetter().equals(playerMove.get(x).get(0))) {
                        DataRack.remove(y);
                        break;
                    }
                }
            }
            for (int cols = 0; cols < ViewRack.getChildren().size(); cols++) {
                if (ViewRack.getChildren().get(cols) instanceof StackPane && !ViewRack.getChildren().get(cols).isVisible()) {
                    Tile tile = Game.letters.getTile();
                    DataRack.add(tile);
                    int col = ViewRack.getColumnIndex(ViewRack.getChildren().get(cols));
                    int row = ViewRack.getRowIndex(ViewRack.getChildren().get(cols));
                    ViewRack.getChildren().remove(cols);
                    ViewRack.add(tile.getHolder(), col, row);
                    cols = 0;
                }
            }
            for(int x = 0; x < ViewRack.getChildren().size(); x++)
                ViewRack.getChildren().get(x).setVisible(true);
        }

            // otherwise put the tiles back
        else
            clearRack();
    }

    // sum the points values of the tiles in the rack
    public int sumRack()
    {
    int sum = 0;
        for(Tile tile : DataRack) {
            sum += tile.getValue();
        }
    return sum;
    }

    public void clearRack()
    {
        for(int cols = 0; cols < ViewRack.getChildren().size(); cols++) {
                if(!ViewRack.getChildren().get(cols).isVisible())
                    ViewRack.getChildren().get(cols).setVisible(true);
        }
    }

    public void setViewRackMove(boolean bool)
        { ViewRack.setDisable(!bool); }


}
