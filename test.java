
//***************************************************************
//*                           IGNORE                            *
//***************************************************************






import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class test extends Application
{
    @Override
    public void start(Stage stage)
    {
        BorderPane root = new BorderPane();
        GridPane board = new GridPane();
        board.setAlignment(Pos.CENTER);
        board.setPrefSize(500,500);
        int size = 15;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col ++)
            {
                StackPane yo =new StackPane();
                yo.setAlignment(Pos.CENTER);
                Tile boop = new Tile("A", 1);
                yo.setStyle("-fx-background-color: #c48964");
                yo.getChildren().add(boop.getLetter());
                DragController cont = new DragController(yo,true);
                board.add(yo, col, row);
            }
        }

        for (int i = 0; i < size; i++) {
            board.getColumnConstraints().add(new ColumnConstraints(Control.USE_PREF_SIZE, 35, Control.USE_PREF_SIZE, Priority.ALWAYS, HPos.CENTER, true));
            board.getRowConstraints().add(new RowConstraints(Control.USE_PREF_SIZE, 35, Control.USE_PREF_SIZE, Priority.ALWAYS, VPos.CENTER, true));
        }


        board.setGridLinesVisible(true);
        root.setCenter(board);
        GridPane rack = new GridPane();
        rack.setAlignment(Pos.CENTER);
        for(int x = 0; x < 7; x++){
            StackPane yo = new StackPane();
            yo.setAlignment(Pos.CENTER);
            yo.setStyle("-fx-background-color: Aqua");
            Text boop = new Text("H");
            boop.setFill(Color.BLACK);
            yo.getChildren().add(boop);
            DragController cont = new DragController(yo, true);
            rack.add(yo, x, 0);

        }
        rack.getRowConstraints().add(new RowConstraints(Control.USE_PREF_SIZE, 25, Control.USE_PREF_SIZE, Priority.ALWAYS, VPos.CENTER, true));
        for(int y = 0; y < 7; y++)
            rack.getColumnConstraints().add(new ColumnConstraints(Control.USE_PREF_SIZE, 25, Control.USE_PREF_SIZE, Priority.ALWAYS, HPos.CENTER, true));

        rack.setGridLinesVisible(true);
        root.setBottom(rack);
        stage.setScene(new Scene(root, 800, 800));
        stage.show();
    }
}
