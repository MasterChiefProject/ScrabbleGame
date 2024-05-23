package test;

import java.util.HashSet;

public class CacheManager {

    public HashSet<String> cache; // hash list with all the recently used words
    public int current_size = 0; // current size of the cache
    public int max_size; // maximum size of the cache
    public CacheReplacementPolicy crp; // crp rules of the cache

    public CacheManager(int max_size, CacheReplacementPolicy new_crp) { // constructor
        this.cache = new HashSet<String>();
        this.max_size = max_size;
        this.crp = new_crp;
    }

    public boolean query(String word) { // returns true if word is recently used otherwise false
        return cache.contains(word);
    }

    public void add(String word) { // add new used word to the cache, if no space remove the least recently used
                                   // word, then add it
        if (current_size >= max_size) {
            cache.remove(crp.remove());
            crp.add(word);
            cache.add(word);
        } else {
            crp.add(word);
            cache.add(word);
            current_size++;
        }
    }

}
