import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

//class still needs work!
public class LetterBag
{

    //don't know how much we would actually need to manipulate this 'letterBag' yet
    //we would probably have to change the container for the Stack<Tile> is much more manipulation is needed
    private Vector<Stack<Tile>> letterBag;
    //private Object[] test;

    public LetterBag()
    {
        this.letterBag = new Vector<>(27);
        //WILL NOT WORK UNLESS PATH ON YOUR COMPUTER IS SET UP
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
        //test = letterBag.toArray();

    }
    public boolean isLetterBagEmpty()
    {
        for (Stack<Tile> tiles : letterBag) {
            if (!tiles.isEmpty())
                return false;
        }
       return true;
    }
    public void test()
    {
        Iterator<Stack<Tile>> vectorItr = letterBag.iterator();
        while(vectorItr.hasNext())
        {
            Iterator<Tile> stackItr = vectorItr.next().iterator();
            while(stackItr.hasNext())
            {
                System.out.print(stackItr.next().getLetter().getText() + " ");
            }
            System.out.println();
        }
    }

    //***NEW EDIT***
    //I think I fixed the issue with the manipulation of the containers of the letter back.
    //p.s. java sucks with this crap
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
