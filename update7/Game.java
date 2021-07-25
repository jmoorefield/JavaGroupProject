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
    private Button[] playerButtons;
    private Button[] boardButtons;
    private int currentPlayerOrder = 0;

    @Override
    public void start(Stage stage)
    {
        BorderPane window = new BorderPane();
        window.setStyle("-fx-background-color: #1c839e;");
        TextPrompt textPrompt = new TextPrompt();
        GridPane textHolder = new GridPane();
        textHolder.setAlignment(Pos.CENTER);
        textHolder.add(textPrompt.getPrompt(), 0,0);

        // initialize variables 
        scrabble = new Board();
        letters = new LetterBag();
        dict = new EnglishDictionary();
        playerButtons = new Button[3];
        boardButtons = new Button[5];

        // create buttons
        playerButtons[0] = new Button("2 Players");
        playerButtons[1] = new Button("3 Players");
        playerButtons[2] = new Button("4 Players");

        boardButtons[0] = new Button("Make Move");
        boardButtons[1] = new Button ("No more moves possible");
        boardButtons[2] = new Button("End Turn");
        boardButtons[3] = new Button("Cancel Move");
        boardButtons[4] =  new Button("Pass");

        window.setCenter(scrabble.getViewBoard());
        stage.setScene(new Scene(window, 1000, 800));
        window.setRight(textHolder);

        for(int i = 0; i < playerButtons.length; ++i) {
            textHolder.add(playerButtons[i],0,i+1);
        }
        
        stage.show();
        textPrompt.Welcome();

        playerButtons[0].setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event)
            {
                if(!playerButtons[0].isDisable())
                {
                    getPlayersOrder(2, textPrompt);
                    hidePlayerRacks();
                    window.setBottom(players[playerOrder[0]-1].getPlayerRack().getViewRack());
                    manageButtons();
                    textHolder.getChildren().remove(1);

                    for(int i = 0; i < boardButtons.length; ++i) {
                        textHolder.add(boardButtons[i],0,i+1);
                    }

                    textPrompt.getPrompt().appendText("It's Player " + playerOrder[0] + "'s Turn\n");
                    event.consume();
                }
                event.consume();
            }

            // disable # of player buttons and cancel / noMoves
            public void manageButtons() {
                for(int i = 0; i < playerButtons.length; ++i) {
                    playerButtons[i].setDisable(true);
                    playerButtons[i].setVisible(false);
                    playerButtons[i].setManaged(false);
                }
                for(int j = 2; j < boardButtons.length; ++j) {
                    boardButtons[j].setDisable(true);
                    boardButtons[j].setVisible(false);
                    boardButtons[j].setManaged(false);
                }
            }

            // make every player's rack unclickable
            public void hidePlayerRacks() {
                for(Player pl : players)
                    pl.getPlayerRack().setViewRackMove(false);
            }
        });

        playerButtons[1].setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event)
            {
                if(!playerButtons[1].isDisable())
                {
                    getPlayersOrder(3, textPrompt);
                    hidePlayerRacks();
                    window.setBottom(players[playerOrder[0]-1].getPlayerRack().getViewRack());
                    manageButtons();
                    textHolder.getChildren().remove(1);
                   
                    for(int i = 0; i < boardButtons.length; ++i) {
                        textHolder.add(boardButtons[i],0,i+1);
                    }

                    textPrompt.getPrompt().appendText("It's Player " + playerOrder[0] + "'s Turn\n");
                    event.consume();
                }
                event.consume();
            }

            public void manageButtons() {
                for(int i = 0; i < playerButtons.length; ++i) {
                    playerButtons[i].setDisable(true);
                    playerButtons[i].setVisible(false);
                    playerButtons[i].setManaged(false);
                }
                for(int j = 2; j < boardButtons.length; ++j) {
                    boardButtons[j].setDisable(true);
                    boardButtons[j].setVisible(false);
                    boardButtons[j].setManaged(false);
                }
            }

            public void hidePlayerRacks() {
                for(Player pl : players)
                    pl.getPlayerRack().setViewRackMove(false);
            }
        });

        playerButtons[2].setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event)
            {
                if(!playerButtons[2].isDisable())
                {
                    getPlayersOrder(4, textPrompt);
                    hidePlayerRacks();
                    window.setBottom(players[playerOrder[0]-1].getPlayerRack().getViewRack());
                    manageButtons();
                    textHolder.getChildren().remove(1);
                   
                    for(int i = 0; i < boardButtons.length; ++i) {
                        textHolder.add(boardButtons[i],0,i+1);
                    }

                    textPrompt.getPrompt().appendText("It's Player " + playerOrder[0] + "'s Turn\n");
                    event.consume();
                }
                event.consume();
            }

            public void manageButtons() {
                for(int i = 0; i < playerButtons.length; ++i) {
                    playerButtons[i].setDisable(true);
                    playerButtons[i].setVisible(false);
                    playerButtons[i].setManaged(false);
                }
                for(int j = 2; j < boardButtons.length; ++j) {
                    boardButtons[j].setDisable(true);
                    boardButtons[j].setVisible(false);
                    boardButtons[j].setManaged(false);
                }
            }

            public void hidePlayerRacks() {
                for(Player pl : players)
                    pl.getPlayerRack().setViewRackMove(false);
            }
        });

        // button player clicks to make a move
        boardButtons[0].setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            players[playerOrder[currentPlayerOrder]-1].getPlayerRack().setViewRackMove(true);
            
            for(int j = 2; j < boardButtons.length; ++j) {
                boardButtons[j].setDisable(false);
                boardButtons[j].setVisible(true);
                boardButtons[j].setManaged(true);
            }
        }
        });

        // button player clicks when there are no more possible moves
        boardButtons[1].setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                textPrompt.getPrompt().appendText("Game over! The winner is Player " + Board.getWinner(players));
        
                for(int i = 0; i < boardButtons.length; ++i) {
                    boardButtons[i].setDisable(true);
                    boardButtons[i].setVisible(false);
                    boardButtons[i].setManaged(false);
                }
            }
        });

         // button player clicks once they've finished their word
        boardButtons[2].setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event)
            {
                boardButtons[2].setDisable(true);
                boardButtons[2].setVisible(false);
                boardButtons[2].setManaged(false);
                boardButtons[3].setDisable(true);
                boardButtons[3].setVisible(false);
                boardButtons[3].setManaged(false);

                players[playerOrder[currentPlayerOrder]-1].getPlayerRack().setViewRackMove(false);
                int result = Board.onEndOfTurn(players[playerOrder[currentPlayerOrder]-1]);

                switch(result) {
                    case 0:
                        textPrompt.getPrompt().appendText("Player " + playerOrder[currentPlayerOrder] + "'s score is: " + players[playerOrder[currentPlayerOrder]-1].getScore() + "\n");
                        if(currentPlayerOrder < (players.length-1))
                            currentPlayerOrder++;
                        else
                            currentPlayerOrder = 0;
                        window.setBottom(players[playerOrder[currentPlayerOrder]-1].getPlayerRack().getViewRack());
                        textPrompt.getPrompt().appendText("It's Player " + playerOrder[currentPlayerOrder] + "'s Turn\n");
                        break;

                    case 1:
                        textPrompt.getPrompt().appendText("Sorry, that's not a valid move.\n");
                        textPrompt.getPrompt().appendText("Player " + playerOrder[currentPlayerOrder] + "'s score is: " + players[playerOrder[currentPlayerOrder]-1].getScore() + "\n");
                        if(currentPlayerOrder < (players.length-1))
                            currentPlayerOrder++;
                        else
                            currentPlayerOrder = 0;
                        window.setBottom(players[playerOrder[currentPlayerOrder]-1].getPlayerRack().getViewRack());
                        textPrompt.getPrompt().appendText("It's Player " + playerOrder[currentPlayerOrder] + "'s Turn\n");
                        break;

                    case 2:
                        textPrompt.getPrompt().appendText("Game over! The winner is: " + Board.getWinner(players));
                        Board.getWinner(players); 

                        for(int i = 0; i < boardButtons.length; ++i) {
                            boardButtons[i].setDisable(true);
                            boardButtons[i].setVisible(false);
                            boardButtons[i].setManaged(false);
                        }
                        break;

                    case 3:
                        textPrompt.getPrompt().appendText("A move must include at least one letter.\n");
                        break; 

                    case 4:
                        textPrompt.getPrompt().appendText("The first move must include the center tile.\n");
                        Board.onCancel(players[playerOrder[currentPlayerOrder]-1]);
                        break;
                }
            }
        });

         // button player clicks to cancel their move
        boardButtons[3].setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
               Board.onCancel(players[playerOrder[currentPlayerOrder]-1]);
            }
        });

         // button player clicks to pass
        boardButtons[4].setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                boardButtons[2].setDisable(true);
                boardButtons[2].setVisible(false);
                boardButtons[2].setManaged(false);
                boardButtons[3].setDisable(true);
                boardButtons[3].setVisible(false);
                boardButtons[3].setManaged(false);

                players[playerOrder[currentPlayerOrder]-1].getPlayerRack().setViewRackMove(false);

                if(currentPlayerOrder < (players.length-1))
                    currentPlayerOrder++;
                else
                     currentPlayerOrder = 0;

                window.setBottom(players[playerOrder[currentPlayerOrder]-1].getPlayerRack().getViewRack());
                textPrompt.getPrompt().appendText("It's Player " + playerOrder[currentPlayerOrder] + "'s Turn\n");
            }
        });
    }

    // determines order of players
    public void getPlayersOrder(int numPlayers, TextPrompt textprompt) {
        players = new Player[numPlayers];
        playerOrder = new int[numPlayers];

        for(int i = 0; i < numPlayers; ++i) {
            players[i] = new Player(letters);
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
