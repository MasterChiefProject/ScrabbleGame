package test;

import java.util.ArrayList;

public class LRU implements CacheReplacementPolicy {

    public ArrayList<String> lru_list; // least recently used list

    public LRU() { // constructor
        this.lru_list = new ArrayList<String>();
    }

    @Override
    public void add(String word) { // add word to the lru list
        lru_list.remove(word); // if it was used before remove it
        lru_list.add(word); // update it to be the most recently used word
    }

    @Override
    public String remove() { // remove word from the lru list
        return lru_list.remove(0); // remove least recently used word
    }

}
