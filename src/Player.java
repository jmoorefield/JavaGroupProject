

//still don't know what more would need to be implemented here
public class Player
{
    private final int score;
    private final PlayerRack playerRack;
    private final String name;


    public Player(LetterBag letterBag,String st)
    {
        this.name = st;
        this.playerRack = new PlayerRack(letterBag);
        this.score = 0;
    }

    public PlayerRack getPlayerRack()
    {
        return playerRack;
    }


}
