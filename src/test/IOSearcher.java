package test;

import java.io.BufferedReader;
import java.io.FileReader;

public class IOSearcher {

    public static boolean search(String word, String... fileNames) { // if a word isn't found in the cache and by using
        // the bloom filter, then we'll:
        // start searching in the files (dictionaries) for
        // the occurence of the word
        for (String fileName : fileNames) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.contains(word)) {
                        reader.close();
                        return true;
                    }
                }
                reader.close();
            } catch (Exception e) {
                System.err.printf("Error reading file: %s , error: %s", fileName, e.getMessage());
            }
        }
        return false;
    }

}
