/* Class to create the reference dictionary */
/* Includes a simple main program to test functionality, will be deleted later */

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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EnglishDictionary dict = new EnglishDictionary();

        System.out.println("Testing the creation of the reference dictionary");
        System.out.print("Please enter the word you would like to add to the Scrabble board: ");
        String word = scanner.next();
        boolean result = dict.isValidWord(word);
        
        if(result)
            System.out.println("Word added to board!");
        else
            System.out.println("Sorry, that is not a valid word.");
        scanner.close();
    }
}