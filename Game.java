import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;



//class "runs" the game
public class Game extends Application
{

    @Override
    public void start(Stage stage)
    {

        BorderPane window = new BorderPane();
        TextPrompt textPrompt = new TextPrompt();
        GridPane textHolder = new GridPane();
        textHolder.setAlignment(Pos.CENTER);
        textHolder.add(textPrompt.getPrompt(), 0,0);
        Board scrabble = new Board();
        window.setCenter(scrabble.getViewBoard());
        stage.setScene(new Scene(window, 1000, 800));
        LetterBag letters = new LetterBag();
        Player player1 = new Player(letters,"player1");
        Player player2 = new Player(letters,"player2");

        window.setRight(textHolder);
        Button select = new Button("Two Players");
        Button move = new Button("Make Move");
        Button end = new Button("End Turn");
        textHolder.add(select,0,1);
        stage.show();
        textPrompt.Welcome();

        select.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event)
            {
                if(!select.isDisable())
                {
                    window.setBottom(player1.getPlayerRack().getViewRack());
                    select.setDisable(true);
                    textHolder.getChildren().remove(1);
                    textHolder.add(move,0,1);
                    textHolder.add(end,0,2);
                    textPrompt.getPrompt().appendText("It's  Turn\n");
                    event.consume();
                }
                event.consume();
            }
        });

        end.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event)
            {

            }
        });

    }

    public static void main(String[] args)
    {
        launch(args);

    }
}
