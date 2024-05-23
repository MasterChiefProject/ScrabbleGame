package test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Tile {
    public final char letter;
    public final int score;

    public Tile(char letter, int score) {
        this.letter = letter;
        this.score = score;
    }

    public char getLetter() {
        return this.letter;
    }

    public int getScore() {
        return this.score;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass() || !(obj instanceof Tile))
            return false;
        Tile other = (Tile) obj;
        return this.letter == other.letter && this.score == other.score;
    }

    @Override
    public int hashCode() {
        int result = (int) letter;
        result = 31 * result + score;
        return result;
    }

    public static class Bag {
        private static Bag bag = null;

        private static final int ALPHABET_SIZE = 26;
        private static final List<Integer> MAX_LETTER_COUNTS = Collections.unmodifiableList(
                Arrays.asList(9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1));

        private int[] letterCounts;
        private Tile[] tiles;

        private Bag() {
            letterCounts = new int[ALPHABET_SIZE];
            initializeLetterCounts();

            tiles = new Tile[ALPHABET_SIZE];
            initializeTiles();
        }

        private void initializeLetterCounts() { // total of 98 letters
            letterCounts['A' - 'A'] = 9;
            letterCounts['B' - 'A'] = 2;
            letterCounts['C' - 'A'] = 2;
            letterCounts['D' - 'A'] = 4;
            letterCounts['E' - 'A'] = 12;
            letterCounts['F' - 'A'] = 2;
            letterCounts['G' - 'A'] = 3;
            letterCounts['H' - 'A'] = 2;
            letterCounts['I' - 'A'] = 9;
            letterCounts['J' - 'A'] = 1;
            letterCounts['K' - 'A'] = 1;
            letterCounts['L' - 'A'] = 4;
            letterCounts['M' - 'A'] = 2;
            letterCounts['N' - 'A'] = 6;
            letterCounts['O' - 'A'] = 8;
            letterCounts['P' - 'A'] = 2;
            letterCounts['Q' - 'A'] = 1;
            letterCounts['R' - 'A'] = 6;
            letterCounts['S' - 'A'] = 4;
            letterCounts['T' - 'A'] = 6;
            letterCounts['U' - 'A'] = 4;
            letterCounts['V' - 'A'] = 2;
            letterCounts['W' - 'A'] = 2;
            letterCounts['X' - 'A'] = 1;
            letterCounts['Y' - 'A'] = 2;
            letterCounts['Z' - 'A'] = 1;
        }

        private void initializeTiles() { // total of 26 types of tiles
            tiles['A' - 'A'] = new Tile('A', 1);
            tiles['B' - 'A'] = new Tile('B', 3);
            tiles['C' - 'A'] = new Tile('C', 3);
            tiles['D' - 'A'] = new Tile('D', 2);
            tiles['E' - 'A'] = new Tile('E', 1);
            tiles['F' - 'A'] = new Tile('F', 4);
            tiles['G' - 'A'] = new Tile('G', 2);
            tiles['H' - 'A'] = new Tile('H', 4);
            tiles['I' - 'A'] = new Tile('I', 1);
            tiles['J' - 'A'] = new Tile('J', 8);
            tiles['K' - 'A'] = new Tile('K', 5);
            tiles['L' - 'A'] = new Tile('L', 1);
            tiles['M' - 'A'] = new Tile('M', 3);
            tiles['N' - 'A'] = new Tile('N', 1);
            tiles['O' - 'A'] = new Tile('O', 1);
            tiles['P' - 'A'] = new Tile('P', 3);
            tiles['Q' - 'A'] = new Tile('Q', 10);
            tiles['R' - 'A'] = new Tile('R', 1);
            tiles['S' - 'A'] = new Tile('S', 1);
            tiles['T' - 'A'] = new Tile('T', 1);
            tiles['U' - 'A'] = new Tile('U', 1);
            tiles['V' - 'A'] = new Tile('V', 4);
            tiles['W' - 'A'] = new Tile('W', 4);
            tiles['X' - 'A'] = new Tile('X', 8);
            tiles['Y' - 'A'] = new Tile('Y', 4);
            tiles['Z' - 'A'] = new Tile('Z', 10);
        }

        public int getTileCount(char letter) {
            int i = letter - 'A';
            if (i >= 0 && i < ALPHABET_SIZE) {
                return letterCounts[i];
            } else {
                return 0;
            }
        }

        public int getAllTilesCount() {
            int totalCount = 0;

            for (int i = 0; i < tiles.length; i++) {
                totalCount += letterCounts[i];
            }

            return totalCount;
        }

        private void addTile(char letter) {
            if (getAllTilesCount() > 97)
                return; // bag is full
            int i = letter - 'A';
            if (i >= 0 && i < ALPHABET_SIZE) {
                int count = letterCounts[i];
                if (count < MAX_LETTER_COUNTS.get(i)) {
                    letterCounts[i] = count + 1;
                }
            }
        }

        private void removeTile(char letter) {
            if (getAllTilesCount() < 1)
                return; // bag is empty
            int i = letter - 'A';
            if (i >= 0 && i < ALPHABET_SIZE) {
                int count = letterCounts[i];
                if (count > 0) {
                    letterCounts[i] = count - 1;
                } else {
                    return;
                }
            } else {
                return;
            }
        }

        public Tile getRand() {
            if (getAllTilesCount() < 1)
                return null; // bag is empty

            Random rand = new Random();
            int i = rand.nextInt(tiles.length);

            do {
                i = rand.nextInt(tiles.length);
            } while (getTileCount(tiles[i].getLetter()) < 1);

            removeTile(tiles[i].getLetter());

            return tiles[i];
        }

        public Tile getTile(char letter) {
            if (letter == '_')
                return new Tile('_', 0);
            if (getTileCount(letter) < 1)
                return null; // bag is empty

            removeTile(tiles[letter - 'A'].getLetter());

            return tiles[letter - 'A'];
        }

        public void put(Tile tile) {
            addTile(tile.getLetter());
        }

        public int size() {
            return getAllTilesCount();
        }

        public int[] getQuantities() {
            return letterCounts.clone();
        }

        public static Bag getBag() {
            if (bag == null)
                bag = new Bag();

            return bag;
        }

    }

}