
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class TextPrompt
{
    private TextArea PromptText;
    private Button Move;
    private Button CancelMove;
    private Button EndTurn;


    private EventHandler<KeyEvent> textEntered;
    private BooleanProperty isKeyEntered;

    public TextPrompt()
    {
        this.PromptText = new TextArea();
        this.PromptText.setPrefSize(250,250);
        this.PromptText.setWrapText(true);
        this.PromptText.setEditable(false);
        this.Move = new Button("Move");
        this.CancelMove = new Button("Cancel Move");
        this.EndTurn = new Button("End Turn");
        handle();
        setHandle();
        this.isKeyEntered.set(true);

    }

    public TextArea getPrompt()
    {
        return this.PromptText;
    }
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



    public void handle()
    {
        textEntered = event -> {
            System.out.print(event.getCode().toString());
        };
    }
    public void setHandle()
    {
        isKeyEntered = new SimpleBooleanProperty();
        isKeyEntered.addListener((observable, oldValue, newValue) -> {
        if (newValue){

        }
        else
        {

        }

    });
    }
}
