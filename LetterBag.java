import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

//class still needs work!
public class LetterBag
{
    //don't know how much we would actually need to manipulate this 'letterBag' yet
    //we would probably have to change the container for the Stack<Tile> is much more manipulation is needed
    private final Vector<Stack<Tile>> letterBag;

    public LetterBag()
    {
        this.letterBag = new Vector<Stack<Tile>>(27);
        /*
        //probably needs fixing file issues
        File file = new File("/Users/hubner/Desktop/Compilations/letterBag.txt");
        //File file = new File(Paths.get("letterBag.text"))
        try(Scanner input = new Scanner(file))
        {

           while(input.hasNext())
           {
               String letter = input.next();
               int points = input.nextInt();
               int amount = input.nextInt();
               Stack<Tile> tiles = new Stack<Tile>();
               for(int x = 0; x < amount; x++)
               {
                   Tile tile = new Tile(letter,points);
                   tiles.push(tile);
               }
               this.letterBag.add(tiles);
           }
        }
        catch (IOException | NoSuchElementException |
                IllegalStateException e) {
            e.printStackTrace();
        }

         */
    }
    public boolean isLetterBagEmpty()
    {
        for (Stack<Tile> tiles : letterBag) {
            if (!tiles.isEmpty())
                return false;
        }
       return true;
    }
}
