import java.util.*;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Control;
import javafx.scene.layout.*;

import javax.swing.text.View;

public class PlayerRack
{
    private List<Tile> DataRack;
    private GridPane ViewRack;

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
        ViewRack.getRowConstraints().add(new RowConstraints(Control.USE_PREF_SIZE, 45, Control.USE_PREF_SIZE, Priority.ALWAYS, VPos.CENTER, true));
        for(int y = 0; y < 7; y++)
            ViewRack.getColumnConstraints().add(new ColumnConstraints(Control.USE_PREF_SIZE, 45, Control.USE_PREF_SIZE, Priority.ALWAYS, HPos.CENTER, true));

        ViewRack.setGridLinesVisible(true);
    }

    public GridPane getViewRack() { return ViewRack; }


    public void updateRack(boolean successfulMove, List<List<String>> playerMove)
    {
        if(successfulMove)
        {
            for(List<String> tile : playerMove)
            {
                for(Tile t : DataRack)
                {
                    if(t.getLetter() == tile.get(0))
                        DataRack.remove(t);
                }
            }
            for(int cols = 0; cols < ViewRack.getChildren().size(); cols++)
            {
                if(!ViewRack.getChildren().get(cols).isVisible())
                {
                    Tile tile = Game.letters.getTile();
                    DataRack.add(cols, tile);
                    ViewRack.add(tile.getHolder(),cols,0);
                }
            }      
        }

        else
            clearRack();
    }

    public int sumRack()
    {
    int sum = 0;
        for(Tile tile : DataRack) {
            sum += tile.getValue();
        }
    return sum;
    }

    public void clearRack() {
        for(int cols = 0; cols < ViewRack.getChildren().size(); cols++)
            {
                if(!ViewRack.getChildren().get(cols).isVisible())
                {
                    ViewRack.getChildren().get(cols).setVisible(true);
                }
            }
    }
    public void setViewRackMove(boolean bool)
    {
        ViewRack.setDisable(!bool);

    }


}
