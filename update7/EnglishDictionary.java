/* COP3252
Jess Moorefield, Roderick Quiel
Group Project
25 July 2021 
*/

/* This class contains functionality for the dictionary of valid words. */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;

public class EnglishDictionary {

    // words are stored in a hashset because no duplicates are allowed
    private Set<String> hs = new HashSet<String>();
    public EnglishDictionary() {
        createDictionary();
    }

    // read in each word from the txt file words_alpha
    // to create the dictionary
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

    // a word is valid if it is in the dictionary
    public boolean isValidWord(String word) {
        return hs.contains(word);
    }
}