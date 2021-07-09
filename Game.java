import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


//class "runs" the game
public class Game extends Application
{
    @Override
    public void start(Stage stage)
    {
        BorderPane window = new BorderPane();
        Board scrabble = new Board();
        window.setCenter(scrabble.getViewBoard());
        stage.setScene(new Scene(window, 800, 800));
        stage.show();
    }
}
