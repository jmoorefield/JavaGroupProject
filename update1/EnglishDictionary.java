/* Class to create the reference dictionary */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;

public class EnglishDictionary {
    private Set<String> hs = new HashSet<String>();
    public EnglishDictionary() {
        createDictionary();
    }

    public void createDictionary() {
        Scanner scanner;
        try {
            scanner = new Scanner(new File("words_alpha.txt"));
            while(scanner.hasNextLine()) {
                String word = scanner.nextLine();
                hs.add(word);
            }
        scanner.close();
        }
        catch(FileNotFoundException fileNotFoundException)
        {
            System.err.println("Error opening file.");
            System.exit(1);
        }
    }

    public boolean isValidWord(String word) {
        return hs.contains(word);
    }
}
