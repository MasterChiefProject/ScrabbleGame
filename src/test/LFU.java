package test;

import java.util.HashMap;
import java.util.Map;

public class LFU implements CacheReplacementPolicy {

    public HashMap<String, Integer> lfu_list; // least frequently used hash list

    public LFU() { // constructor
        this.lfu_list = new HashMap<String, Integer>();
    }

    @Override
    public void add(String word) { // add word to the lfu hash list
        if (lfu_list.containsKey(word)) // if word was used before then increase the count by one
            lfu_list.put(word, lfu_list.get(word) + 1);
        else
            lfu_list.put(word, 1); // else add the new word and set count to 1

    }

    @Override
    public String remove() { // remove word from the lfu hash list
        String leastUsedWord = null;
        int leastUsedCount = Integer.MAX_VALUE;

        for (Map.Entry<String, Integer> entry : lfu_list.entrySet()) { // iterate lfu hash list till we find the word
                                                                       // that
            // was most leastly used
            String word = entry.getKey();
            int wordValue = entry.getValue();
            if (wordValue < leastUsedCount) {
                leastUsedCount = wordValue;
                leastUsedWord = word;
            }
        }

        if (leastUsedWord != null)
            lfu_list.remove(leastUsedWord);

        return leastUsedWord;
    }

}
