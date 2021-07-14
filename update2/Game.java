import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class Game extends Application
{
    public static EnglishDictionary dict;
    public static LetterBag letters;

    @Override
    public void start(Stage stage)
    {
        BorderPane window = new BorderPane();
        Board scrabble = new Board();
        window.setCenter(scrabble.getViewBoard());
        stage.setScene(new Scene(window, 800, 800));
        letters = new LetterBag();
        dict = new EnglishDictionary();

        // should connect to title screen class to determine number of players
        Player player1 = new Player(letters,"player1");

        window.setBottom(player1.getPlayerRack().getViewRack());
        stage.show();
    }
}
