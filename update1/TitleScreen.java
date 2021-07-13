public class TitleScreen
{// function to determine player order
       public void playerOrder(int numPlayers) {

        if(numPlayers < 2 || numPlayers > 4) {
            System.out.println("Invalid number of players");
            exit(1);
        }

        Random rand = new Random();
        List<Integer> letters = new ArrayList<Integer>(numPlayers);
        List<Integer> sortedLetters = new ArrayList<Integer>(numPlayers);
        int[] positions = new int[numPlayers];

        for(int i = 0; i < numPlayers; ++i)
            letters.add(rand.nextInt(27));

        for(int letter : letters)
            sortedLetters.add(letter);

        Collections.sort(sortedLetters);

        for(int k = 0, j = numPlayers-1; k < numPlayers && j >= 0; ++k, --j)
            positions[k] = letters.indexOf(sortedLetters.get(j)) + 1;

        System.out.println("Order of player positions is: ");
        for(int pos : positions) {
            System.out.println(pos);
        }
    }
}
