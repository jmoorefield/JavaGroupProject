/* COP3252
Jess Moorefield, Roderick Quiel
Group Project
25 July 2021 
*/

/* This class contains functionality for the text window that gives instructions. */

import javafx.scene.control.TextArea;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class TextPrompt {
    private TextArea PromptText;

    public TextPrompt()
    {
        this.PromptText = new TextArea();
        this.PromptText.setPrefSize(250,250);
        this.PromptText.setWrapText(true);
        this.PromptText.setEditable(false);
    }

    public TextArea getPrompt() { return this.PromptText; }
    
    // display the txt file welcome that explains the rules of the game
    public void Welcome()
    {
        try(FileInputStream welcome = new FileInputStream("welcome.txt"))
        {
            Scanner input = new Scanner(welcome);
            while(input.hasNext())
            {
                this.PromptText.appendText(input.nextLine()+"\n");
            }
        }
        catch (IOException | NoSuchElementException | IllegalStateException e)
        { e.printStackTrace(); }
    }

    // display the order of players
    // called from getPlayersOrder() in Game
    public void showPlayerOrder(int[] order) {
        this.PromptText.appendText("Player order is:\n");
        for(int i = 0; i < order.length; ++i) {
            this.PromptText.appendText("Player " + order[i] + "\n");
        }
    }
}