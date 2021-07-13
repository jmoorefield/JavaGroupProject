public class Player
{
    private int score;
    private final PlayerRack playerRack;
    private final String NAME;

    public Player(LetterBag letterBag, String st)
    {
        this.NAME = st;
        this.playerRack = new PlayerRack(letterBag);
        this.score = 0;
    }

    public String getName() { return NAME; }

    public PlayerRack getPlayerRack() { return playerRack; }

    /*
    // called when end of turn button is clicked, implements player move 
    public void move(Vector<Tile> playerMove) {
    String word = "";
    int points = 0;
    int specialPoints = 1;

        for(Tile t : playerMove) {
            if(t.getLetter.equals("blank")) {
                System.out.print("Please enter the letter used for the blank tile: ");
                letter = scanner.next();    // this line intentionally left not done 
            } 
            word += t.getLetter();
            points += t.getPointsValue();
            // if(tile has special value)
                specialPoints = special value;
        }

        if(Game.dict.isValidWord(word.toLowerCase())) {
            points *= specialPoints;
            score += points;
            playerRack.updateRack(true, playerMove);
        }
        else if(word.length < 2 || word.length > 15)
            playerRack.updateRack(false, playerMove);

        else
            playerRack.updateRack(false, playerMove);
    }
    */
}
