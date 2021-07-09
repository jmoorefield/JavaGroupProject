//still don't know what more would need to be implemented here
public class Player
{
    private final int score;
    private final PlayerRack playerRack;
    public Player(LetterBag letterBag)
    {
        this.playerRack = new PlayerRack(letterBag);
        this.score = 0;
    }

    public PlayerRack getPlayerRack()
    {
        return playerRack;
    }

}
