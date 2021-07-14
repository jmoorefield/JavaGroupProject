import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

//class still needs work!
public class LetterBag
{


    private final Vector<Stack<Tile>> letterBag;
    //private Object[] test;

    public LetterBag()
    {
        this.letterBag = new Vector<>(27);
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
        }
        catch (IOException | NoSuchElementException | IllegalStateException e)
        { e.printStackTrace(); }

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
                System.out.print(stackItr.next().getLetter() + " ");
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




