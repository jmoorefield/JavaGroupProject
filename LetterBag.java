import javafx.scene.layout.GridPane;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/// *************************************************************
/// *                      Letter Bag Class                     *
/// *************************************************************
/* COP3252
Jess Moorefield, Roderick Quiel
Group Project
25 July 2021
*/


public class LetterBag
{
    //container for the tiles
    private final Vector<Stack<Tile>> letterBag;

    //*******************************************************************************
    // * Name: LetterBag                                                            *
    // * Description: Fills the container with the amount of tiles of               *
    // *    different letters that a letter bag in a game of scrabble would have    *
    // * Last Modified by: Roderick Quiel                                           *
    // * Date:  07/26/2021                                                          *
    // ******************************************************************************
    public LetterBag()
    {
        this.letterBag = new Vector<>(27);
        //get the tile info from the "letterBag.txt" file.
        try(FileInputStream file = new FileInputStream("letterBag.txt"))
        {
            Scanner input = new Scanner(file);
            while(input.hasNext())
            {

                String letter = input.next();
                int points = input.nextInt();
                int amount = input.nextInt();
                String pathname = input.next();
                Stack<Tile> tiles = new Stack<>();
                for(int x = 0; x < amount; x++)
                {
                    FileInputStream imageFile = new FileInputStream(pathname);
                    Tile tile = new Tile(letter,points,imageFile);
                    tiles.push(tile);
                }
                this.letterBag.add(tiles);
            }
            input.close();
        }
        catch (IOException | NoSuchElementException | IllegalStateException e)
        { e.printStackTrace(); }

    }

    //*******************************************************************************
    // * Name: isLetterBagEmpty                                                     *
    // * Description: checks if the letter bag is empty                             *
    // * Last Modified by: Roderick Quiel                                           *
    // * Date:  07/26/2021                                                          *
    // ******************************************************************************
    public boolean isLetterBagEmpty()
    {
        for (Stack<Tile> tiles : letterBag) {
            if (!tiles.isEmpty())
                return false;
        }
       return true;
    }
    //*******************************************************************************
    // * Name: getTile                                                              *
    // * Description: randomly returns a tile from the letter bag                   *
    // * Last Modified by: Roderick Quiel                                           *
    // * Date:  07/26/2021                                                          *
    // ******************************************************************************
    public Tile getTile()
    {
        Random rnd = new Random();
        Stack<Tile> tileStack = letterBag.get(rnd.nextInt(26));
        while(true)
        {
            //if the letter is not available anymore we randomly select another one
            if(!tileStack.isEmpty())
                return tileStack.pop();
            else
                tileStack = letterBag.get(rnd.nextInt(26));
        }
    }
}




