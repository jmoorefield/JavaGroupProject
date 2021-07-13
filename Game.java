import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;



//class "runs" the game
public class Game extends Application
{
  //  public static final EnglishDictionary dict;
  //  public static final LetterBag letters;

    @Override
    public void start(Stage stage)
    {
        BorderPane window = new BorderPane();
        Board scrabble = new Board();
        window.setCenter(scrabble.getViewBoard());
        stage.setScene(new Scene(window, 800, 800));
        LetterBag letters = new LetterBag();
        // letters = new LetterBag();
        // dict = new EnglishDictionary();
        letters.test();

        // should connect to title screen class to determine number of players
        Player player1 = new Player(letters,"player1");
        letters.test();

        window.setBottom(player1.getPlayerRack().getViewRack());
        stage.show();
    }
}
