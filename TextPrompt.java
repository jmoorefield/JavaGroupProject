import javafx.scene.control.TextArea;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/// *************************************************************
/// *                      Text Prompt Class                    *
/// *************************************************************
/* COP3252
Jess Moorefield, Roderick Quiel, Abigail Taylor
Group Project
29 July 2021
*/

public class TextPrompt {
    private TextArea PromptText;


    //*******************************************************************************
    // * Name: TextPrompt                                                           *
    // * Description:  initializes the Text Area for displaying information         *
    // * Last Modified by: Roderick Quiel                                           *
    // * Date:  07/26/2021                                                          *
    // ******************************************************************************
    public TextPrompt()
    {
        this.PromptText = new TextArea();
        this.PromptText.setPrefSize(300,350);
        this.PromptText.setWrapText(true);
        this.PromptText.setEditable(false);
    }

    //*******************************************************************************
    // * Name: getPrompt                                                            *
    // * Description:  returns the PromptText                                       *
    // * Last Modified by: Roderick Quiel                                           *
    // * Date:  07/26/2021                                                          *
    // ******************************************************************************
    public TextArea getPrompt()
    {
        return this.PromptText;
    }


    //*******************************************************************************
    // * Name: Welcome                                                              *
    // * Description: Prompts the welcome text for the game                         *
    // * Last Modified by: Roderick Quiel                                           *
    // * Date:  07/26/2021                                                          *
    // ******************************************************************************
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


    //*******************************************************************************
    // * Name: showPlayerOrder                                                      *
    // * Description: Prompts the players order in the game                         *
    // * Last Modified by: Roderick Quiel                                           *
    // * Date:  07/26/2021                                                          *
    // ******************************************************************************
    public void showPlayerOrder(int[] order) {
        this.PromptText.appendText("Player order is:\n");
        for(int i = 0; i < order.length; ++i) {
            this.PromptText.appendText("Player " + order[i] + "\n");
        }
    }
}

