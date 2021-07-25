/* COP3252
Jess Moorefield, Roderick Quiel
Group Project
25 July 2021 
*/

/* This class runs the game and controls the buttons used by the players. */

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
    // member data for the dictionary, letterbag, players, board objects used
    public static EnglishDictionary dict;
    public static LetterBag letters;
    private Board scrabble;
    private Player[] players;

    // sets of buttons used by the players:
    // selecting the number of players
    // and actions during the game
    private Button[] playerButtons;     
    private Button[] boardButtons;

    private int[] playerOrder;          // holds the order of the players
    private int currentPlayerOrder = 0; // holds the index of the current player from playerOrder[]

    @Override
    public void start(Stage stage)
    {
        // GUI configuration
        BorderPane window = new BorderPane();
        window.setStyle("-fx-background-color: #1c839e;");
        TextPrompt textPrompt = new TextPrompt();
        GridPane textHolder = new GridPane();
        textHolder.setAlignment(Pos.CENTER);
        textHolder.add(textPrompt.getPrompt(), 0,0);

        // initialize data 
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

        // GUI configuration
        window.setCenter(scrabble.getViewBoard());
        stage.setScene(new Scene(window, 1000, 800));
        window.setRight(textHolder);

        // add buttons to GUI
        for(int i = 0; i < playerButtons.length; ++i) {
            textHolder.add(playerButtons[i],0,i+1);
        }
        
        stage.show();
        textPrompt.Welcome();

        /* BEGIN FUNCTIONS FOR WHEN BUTTONS ARE CLICKED */
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

                    // add buttons to GUI
                    for(int i = 0; i < boardButtons.length; ++i) {
                        textHolder.add(boardButtons[i],0,i+1);
                    }

                    textPrompt.getPrompt().appendText("It's Player " + playerOrder[0] + "'s Turn\n");
                    event.consume();
                }
                event.consume();
            }

            // different buttons are shown at different times in the game
            // if a player is not trying to make a move, no need to show
            // end turn, cancel move, or pass move buttons
            // once the # of players has been selected, hide those buttons too
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

        // button clicked to make a move
        boardButtons[0].setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            // show the rack of the current player
            // currentPlayerOrder stores 1,2,3, not 0,1,2
            // which is why we subtract 1
            // so we access the player that is at the current index of playerOrder[]
            players[playerOrder[currentPlayerOrder]-1].getPlayerRack().setViewRackMove(true);
            
            // show the buttons related to making a move
            for(int j = 2; j < boardButtons.length; ++j) {
                boardButtons[j].setDisable(false);
                boardButtons[j].setVisible(true);
                boardButtons[j].setManaged(true);
            }
        }
        });

        // button clicked when there are no more possible moves
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

         // button player clicks once they've finished their turn
        boardButtons[2].setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event)
            {
                // no need to show buttons related to creating the move
                boardButtons[2].setDisable(true);
                boardButtons[2].setVisible(false);
                boardButtons[2].setManaged(false);
                boardButtons[3].setDisable(true);
                boardButtons[3].setVisible(false);
                boardButtons[3].setManaged(false);

                // disable the current player's rack
                players[playerOrder[currentPlayerOrder]-1].getPlayerRack().setViewRackMove(false);

                // get the result of their move
                int result = Board.onEndOfTurn(players[playerOrder[currentPlayerOrder]-1]);

                switch(result) {

                    // if the word is valid
                    case 0:
                        textPrompt.getPrompt().appendText("Player " + playerOrder[currentPlayerOrder] + "'s score is: " + players[playerOrder[currentPlayerOrder]-1].getScore() + "\n");

                        // this logic cycles through the 0,1,2 indices to repeat the player order
                        if(currentPlayerOrder < (players.length-1))
                            currentPlayerOrder++;
                        else
                            currentPlayerOrder = 0;
                        window.setBottom(players[playerOrder[currentPlayerOrder]-1].getPlayerRack().getViewRack());
                        textPrompt.getPrompt().appendText("It's Player " + playerOrder[currentPlayerOrder] + "'s Turn\n");
                        break;

                    // if the word is invalid
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

                    // if the letterbag is empty
                    case 2:
                        textPrompt.getPrompt().appendText("Game over! The winner is: " + Board.getWinner(players));
                        Board.getWinner(players); 

                        for(int i = 0; i < boardButtons.length; ++i) {
                            boardButtons[i].setDisable(true);
                            boardButtons[i].setVisible(false);
                            boardButtons[i].setManaged(false);
                        }
                        break;

                    // if there are no tiles in the current move
                    case 3:
                        textPrompt.getPrompt().appendText("A move must include at least one letter.\n");
                        break; 

                    // if the first move on the board doesn't include the center tile
                    case 4:
                        textPrompt.getPrompt().appendText("The first move must include the center tile.\n");
                        Board.onCancel(players[playerOrder[currentPlayerOrder]-1]);
                        break;
                }
            }
        });

         // button clicked to cancel a move
        boardButtons[3].setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
               Board.onCancel(players[playerOrder[currentPlayerOrder]-1]);
            }
        });

         // button clicked to pass 
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
    /* BEGIN FUNCTIONS FOR WHEN BUTTONS ARE CLICKED */

    // determines order of players
    public void getPlayersOrder(int numPlayers, TextPrompt textprompt) {
        players = new Player[numPlayers];
        playerOrder = new int[numPlayers];

        // initialize the players 
        for(int i = 0; i < numPlayers; ++i) {
            players[i] = new Player(letters);
        }

        Random rand = new Random();
        List<Integer> letters = new ArrayList<Integer>(numPlayers);
        List<Integer> sortedLetters = new ArrayList<Integer>(numPlayers);

        // each player randomly draws a letter from the letter bag
        // here represented by numbers 0-25
        for(int i = 0; i < numPlayers; ++i) {
            int num = rand.nextInt(26);
            for (int l : letters) {
                while(num == l)
                    num = rand.nextInt(26);
            }
            letters.add(num);
        }

        // find order of the numbers
        for(int letter : letters)
            sortedLetters.add(letter);

        Collections.sort(sortedLetters);

        // establish player order based on which player drew what number
        for(int k = 0, j = numPlayers-1; k < numPlayers && j >= 0; ++k, --j)
            playerOrder[k] = letters.indexOf(sortedLetters.get(j)) + 1;

        textprompt.showPlayerOrder(playerOrder);
    }

    // main function that creates the game
    public static void main(String[] args)
    {
        launch(args);

    }
}
