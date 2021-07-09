
//class still needs work!
import javafx.scene.text.Text;
public class Tile
{
    private final Text Letter;
    private final int value;

    public Tile(String letter, int value)
    {
        this.Letter = new Text(letter);
        this.value = value;
    }
    public Tile(String letter)
    {
        this.Letter = new Text(letter);
        this.value = 0;

    }
    //accessor function lets you manipulate the 'Text' from a Tile **careful**
    public Text getLetter(){ return this.Letter;}



}
