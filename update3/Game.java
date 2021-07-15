import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.*;


public class Game extends Application
{
    public static EnglishDictionary dict;
    public static LetterBag letters;
    private Board scrabble;
    private Player[] players;
    private int[] playerOrder;
    private int currentPlayerOrder = 0;

    @Override
    public void start(Stage stage)
    {
        BorderPane window = new BorderPane();
        TextPrompt textPrompt = new TextPrompt();
        GridPane textHolder = new GridPane();
        textHolder.setAlignment(Pos.CENTER);

        textHolder.add(textPrompt.getPrompt(), 0,0);
        scrabble = new Board();
        letters = new LetterBag();
        dict = new EnglishDictionary();
        Button twoPlayers = new Button("2 Players");
        Button threePlayers = new Button("3 Players");
        Button fourPlayers = new Button("4 Players");
        Button move = new Button("Make Move");
        Button end = new Button("End Turn");
        Button cancel = new Button("Cancel Move");

        window.setCenter(scrabble.getViewBoard());
        stage.setScene(new Scene(window, 1000, 800));
        window.setRight(textHolder);

        textHolder.add(twoPlayers,0,1);
        textHolder.add(threePlayers,0,2);
        textHolder.add(fourPlayers,0,3);
        stage.show();
        textPrompt.Welcome();

        twoPlayers.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event)
            {
                if(!twoPlayers.isDisable())
                {
                    getPlayersOrder(2, textPrompt);
                    hidePlayerRacks();
                    window.setBottom(players[playerOrder[0]-1].getPlayerRack().getViewRack());
                    manageButtons();
                    textHolder.getChildren().remove(1);
                    textHolder.add(move,0,1);
                    textHolder.add(end,0,2);
                    textPrompt.getPrompt().appendText("It's Player " + playerOrder[0] + "'s Turn\n");
                    event.consume();
                }
                event.consume();
            }

            public void manageButtons() {
                twoPlayers.setDisable(true);
                threePlayers.setDisable(true);
                fourPlayers.setDisable(true);
                twoPlayers.setVisible(false);
                threePlayers.setVisible(false);
                fourPlayers.setVisible(false);
                twoPlayers.setManaged(false);
                threePlayers.setManaged(false);
                fourPlayers.setManaged(false);

            }

            // make every player's rack unclickable
            public void hidePlayerRacks() {
                for(Player pl : players)
                    pl.getPlayerRack().setViewRackMove(false);
            }
        });

        threePlayers.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event)
            {
                if(!threePlayers.isDisable())
                {
                    getPlayersOrder(3, textPrompt);
                    hidePlayerRacks();
                    window.setBottom(players[playerOrder[0]-1].getPlayerRack().getViewRack());
                    manageButtons();
                    textHolder.getChildren().remove(1);
                    textHolder.add(move,0,1);
                    textHolder.add(end,0,2);
                    textPrompt.getPrompt().appendText("It's Player " + playerOrder[0] + "'s Turn\n");
                    event.consume();
                }
                event.consume();
            }

            public void manageButtons() {
                twoPlayers.setDisable(true);
                threePlayers.setDisable(true);
                fourPlayers.setDisable(true);
                twoPlayers.setVisible(false);
                threePlayers.setVisible(false);
                fourPlayers.setVisible(false);
                twoPlayers.setManaged(false);
                threePlayers.setManaged(false);
                fourPlayers.setManaged(false);

            }

            public void hidePlayerRacks() {
                for(Player pl : players)
                    pl.getPlayerRack().setViewRackMove(false);
            }
        });

        fourPlayers.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event)
            {
                if(!fourPlayers.isDisable())
                {
                    getPlayersOrder(4, textPrompt);
                    hidePlayerRacks();
                    window.setBottom(players[playerOrder[0]-1].getPlayerRack().getViewRack());
                    manageButtons();
                    textHolder.getChildren().remove(1);
                    textHolder.add(move,0,1);
                    textHolder.add(end,0,2);
                    textPrompt.getPrompt().appendText("It's Player " + playerOrder[0] + "'s Turn\n");
                    event.consume();
                }
                event.consume();
            }

            public void manageButtons() {
                twoPlayers.setDisable(true);
                threePlayers.setDisable(true);
                fourPlayers.setDisable(true);
                twoPlayers.setVisible(false);
                threePlayers.setVisible(false);
                fourPlayers.setVisible(false);
                twoPlayers.setManaged(false);
                threePlayers.setManaged(false);
                fourPlayers.setManaged(false);

            }

            public void hidePlayerRacks() {
                for(Player pl : players)
                    pl.getPlayerRack().setViewRackMove(false);
            }
        });

        //this makes player able to move the tiles
       move.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            players[playerOrder[currentPlayerOrder]-1].getPlayerRack().setViewRackMove(true);
            // TODO: make cancel button visible 
        }
        });

        cancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
               Board.onCancel();
            }
            });

        end.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event)
            {
                // TODO: make cancel button invisible
                players[playerOrder[currentPlayerOrder]-1].getPlayerRack().setViewRackMove(false);
                boolean continueGame = Board.onEndOfTurn(players[playerOrder[currentPlayerOrder]-1]);

                if(continueGame) {

                textPrompt.getPrompt().appendText("Player " + playerOrder[currentPlayerOrder] + "'s score is: " + players[playerOrder[currentPlayerOrder]-1].getScore() + "\n");

                    if(currentPlayerOrder < (players.length-1))
                        currentPlayerOrder++;
                    else
                        currentPlayerOrder = 0;

                window.setBottom(players[playerOrder[currentPlayerOrder]-1].getPlayerRack().getViewRack());
                textPrompt.getPrompt().appendText("It's Player " + playerOrder[currentPlayerOrder] + "'s Turn\n");
                
                }
                
                else {
                    textPrompt.getPrompt().appendText("Game over! The winner is:");
                    Board.getWinner(players); 
                    System.exit(0);
                }
            }
        });

    }

    // determines order of players
    public void getPlayersOrder(int numPlayers, TextPrompt textprompt) {
        players = new Player[numPlayers];
        playerOrder = new int[numPlayers];

        for(int i = 0; i < numPlayers; ++i) {
            players[i] = new Player(letters, "xxx");
        }

        Random rand = new Random();
        List<Integer> letters = new ArrayList<Integer>(numPlayers);
        List<Integer> sortedLetters = new ArrayList<Integer>(numPlayers);

        for(int i = 0; i < numPlayers; ++i) {
            int num = rand.nextInt(26);
            for (int l : letters) {
                while(num == l)
                    num = rand.nextInt(26);
            }
            letters.add(num);
        }

        for(int letter : letters)
            sortedLetters.add(letter);

        Collections.sort(sortedLetters);

        for(int k = 0, j = numPlayers-1; k < numPlayers && j >= 0; ++k, --j)
            playerOrder[k] = letters.indexOf(sortedLetters.get(j)) + 1;

        textprompt.showPlayerOrder(playerOrder);
    }

    
    public static void main(String[] args)
    {
        launch(args);

    }
}
