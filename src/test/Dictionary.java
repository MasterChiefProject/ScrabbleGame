package test;

import java.io.BufferedReader;
import java.io.FileReader;

public class Dictionary {

    public CacheManager existsCacheManager; // least recently used cache manager
    public CacheManager notExistsCacheManager; // least frequently used cache manager

    public BloomFilter bloomFilter; // bloom filter

    public String[] fileNames; // file names

    public Dictionary(String... fileNames) { // constructor
        this.existsCacheManager = new CacheManager(400, new LRU());
        this.notExistsCacheManager = new CacheManager(100, new LFU());
        this.bloomFilter = new BloomFilter(256, "MD5", "SHA-1");
        this.fileNames = fileNames;

        for (String fileName : fileNames) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] words = line.split("\\s+");
                    for (String word : words) {
                        this.existsCacheManager.add(word);
                        this.bloomFilter.add(word);
                    }
                }
                reader.close();

            } catch (Exception e) {
                System.err.printf("Error reading file: %s , error: %s", fileName, e.getMessage());
            }
        }

    }

    public boolean query(String word) { // if word exists we'll return true, if not found then search with bloom filter
        if (existsCacheManager.query(word))
            return true;

        if (notExistsCacheManager.query(word))
            return false;

        boolean isExists = bloomFilter.contains(word);
        if (isExists)
            existsCacheManager.add(word);
        else
            notExistsCacheManager.add(word);

        return isExists;
    }

    public boolean challenge(String word) { // search for the word within the files, return true if found
        boolean isExists = IOSearcher.search(word, fileNames);

        if (isExists)
            existsCacheManager.add(word);
        else
            notExistsCacheManager.add(word);

        return isExists;
    }
}
