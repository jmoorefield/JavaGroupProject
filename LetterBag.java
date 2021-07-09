import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

//class still needs work!
public class LetterBag
{

    //don't know how much we would actually need to manipulate this 'letterBag' yet
    //we would probably have to change the container for the Stack<Tile> is much more manipulation is needed
    private ArrayList<Stack<Tile>> letterBag;
    //private Object[] test;

    public LetterBag()
    {
        this.letterBag = new ArrayList<>(27);
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


    //has problems. mainly java sucks when creating iterators and manipulation of such.
    //when random number is generated and selects a stack that is already empty by attempting to pop
    //the program stops. if the random number generated never gets repeated enough times then the program
    //gets to run normal. of course this is the case with only one player on the board. we maybe will have to change
    // of containers for the data manipulation.
    public Tile getTile()
    {
        Random rnd = new Random();
        int get = 1 +rnd.nextInt(27);
        // I gotta say java sucks on encapsulating multiple containers and manipulation of the data inside
        Iterator<Stack<Tile>> vectorItr = letterBag.listIterator(get);
        Iterator<Stack<Tile>> beforeItr = letterBag.listIterator(get - 1);
        while(true)
        {
            if(beforeItr.hasNext())
                return  vectorItr.next().pop();
            else
            {
                get = 1 +rnd.nextInt(27);
                vectorItr = letterBag.listIterator( get);
                beforeItr = letterBag.listIterator(get - 1);
            }
        }
    }
}
