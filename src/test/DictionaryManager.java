package test;

import java.util.HashMap;

public class DictionaryManager {
    public static DictionaryManager dManager = null;
    public HashMap<String, Dictionary> dictionary_list;

    public DictionaryManager() {
        dictionary_list = new HashMap<String, Dictionary>();
    }

    public boolean query(String... args) {
        boolean isFound = false;
        for (int i = 0; i < args.length - 1; i++) {
            if (!dictionary_list.containsKey((args[i])))
                dictionary_list.put(args[i], new Dictionary(args[i]));

            if (dictionary_list.get(args[i]).query(args[args.length - 1]))
                isFound = true;
        }

        return isFound;
    }

    public boolean challenge(String... args) {
        boolean isFound = false;
        for (int i = 0; i < args.length - 1; i++) {
            if (!dictionary_list.containsKey((args[i])))
                dictionary_list.put(args[i], new Dictionary(args[i]));

            if (dictionary_list.get(args[i]).challenge(args[args.length - 1]))
                isFound = true;
        }

        return isFound;
    }

    public int getSize() {
        return dictionary_list.size();
    }

    public static DictionaryManager get() {
        if (dManager == null)
            dManager = new DictionaryManager();

        return dManager;
    }
}
