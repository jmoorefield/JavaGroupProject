/* COP3252
Jess Moorefield, Roderick Quiel
Group Project
25 July 2021 
*/

/* This class contains functionality for the bag of letter tiles. */

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class LetterBag
{
    private final Vector<Stack<Tile>> letterBag;
    public LetterBag()
    {
        // the letter bag is a vector of stacks, one for each letter.
        // each time a tile is used, it is pushed from its respective stack
        this.letterBag = new Vector<>(27);

        // open txt file containing all possible tiles and their point values
        try(FileInputStream file = new FileInputStream("letterBag.txt"))
        {
            Scanner input = new Scanner(file);
            while(input.hasNext())
            {
                String letter = input.next();
                int points = input.nextInt();
                int amount = input.nextInt();
                String pathname = input.next();

                // add txt file amount of tile objects into a stack
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

    // if every stack is empty, then the letterbag is empty
    public boolean isLetterBagEmpty()
    {
        for (Stack<Tile> tiles : letterBag) {
            if (!tiles.isEmpty())
                return false;
        }
       return true;
    }
   
    // get a random tile object from the bag
    public Tile getTile()
    {
        Random rnd = new Random();
        Stack<Tile> tileStack = letterBag.get(rnd.nextInt(27));
        while(true)
        {
            if(!tileStack.isEmpty())
                return tileStack.pop();
            else
                tileStack = letterBag.get(rnd.nextInt(27));
        }
    }
}




