package test;

import java.util.ArrayList;

public class Board {
    private static Board board = null;

    private static final int BOARD_SIZE = 15;
    public Tile[][] nodes;
    private String[][] rewards;
    private boolean isEmptyBag = false;

    private Board() {
        nodes = new Tile[BOARD_SIZE][BOARD_SIZE];
        initializeNodes();

        rewards = new String[BOARD_SIZE][BOARD_SIZE];
        initializeRewards();
    }

    private void initializeNodes() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                nodes[row][col] = null;
            }
        }
    }

    private void initializeRewards() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                rewards[row][col] = "N/A";
            }
        }

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (row % 7 == 0 && col % 7 == 0) {
                    // center
                    if (row == 7 && col == 7) {
                        rewards[row][col] = "STAR";

                        rewards[row - 1][col - 1] = "DLS";
                        rewards[row - 1][col + 1] = "DLS";
                        rewards[row + 1][col - 1] = "DLS";
                        rewards[row + 1][col + 1] = "DLS";

                        rewards[row - 2][col - 2] = "TLS";
                        rewards[row - 2][col + 2] = "TLS";
                        rewards[row + 2][col - 2] = "TLS";
                        rewards[row + 2][col + 2] = "TLS";

                        rewards[row - 3][col - 3] = "DWS";
                        rewards[row - 3][col + 3] = "DWS";
                        rewards[row + 3][col - 3] = "DWS";
                        rewards[row + 3][col + 3] = "DWS";

                        rewards[row - 4][col - 4] = "DWS";
                        rewards[row - 4][col + 4] = "DWS";
                        rewards[row + 4][col - 4] = "DWS";
                        rewards[row + 4][col + 4] = "DWS";

                        rewards[row - 5][col - 5] = "DWS";
                        rewards[row - 5][col + 5] = "DWS";
                        rewards[row + 5][col - 5] = "DWS";
                        rewards[row + 5][col + 5] = "DWS";

                        rewards[row - 6][col - 6] = "DWS";
                        rewards[row - 6][col + 6] = "DWS";
                        rewards[row + 6][col - 6] = "DWS";
                        rewards[row + 6][col + 6] = "DWS";
                    }
                    // corner edges
                    if (row == 0 && col == 0) {
                        rewards[row][col] = "TWS";

                        rewards[row + 3][col] = "DLS";
                        rewards[row][col + 3] = "DLS";
                    }
                    if (row == 14 && col == 0) {
                        rewards[row][col] = "TWS";

                        rewards[row - 3][col] = "DLS";
                        rewards[row][col + 3] = "DLS";
                    }
                    if (row == 0 && col == 14) {
                        rewards[row][col] = "TWS";

                        rewards[row + 3][col] = "DLS";
                        rewards[row][col - 3] = "DLS";
                    }
                    if (row == 14 && col == 14) {
                        rewards[row][col] = "TWS";

                        rewards[row - 3][col] = "DLS";
                        rewards[row][col - 3] = "DLS";
                    }
                    // center edges
                    if (row == 7 && col == 0) {
                        rewards[row][col] = "TWS";

                        rewards[row - 2][col + 1] = "TLS";
                        rewards[row + 2][col + 1] = "TLS";

                        rewards[row - 1][col + 2] = "DLS";
                        rewards[row + 1][col + 2] = "DLS";
                        rewards[row][col + 3] = "DLS";
                    }
                    if (row == 7 && col == 14) {
                        rewards[row][col] = "TWS";

                        rewards[row - 2][col - 1] = "TLS";
                        rewards[row + 2][col - 1] = "TLS";

                        rewards[row - 1][col - 2] = "DLS";
                        rewards[row + 1][col - 2] = "DLS";
                        rewards[row][col - 3] = "DLS";
                    }
                    if (row == 0 && col == 7) {
                        rewards[row][col] = "TWS";

                        rewards[row + 1][col - 2] = "TLS";
                        rewards[row + 1][col + 2] = "TLS";

                        rewards[row + 2][col - 1] = "DLS";
                        rewards[row + 2][col + 1] = "DLS";
                        rewards[row + 3][col] = "DLS";
                    }
                    if (row == 14 && col == 7) {
                        rewards[row][col] = "TWS";

                        rewards[row - 1][col - 2] = "TLS";
                        rewards[row - 1][col + 2] = "TLS";

                        rewards[row - 2][col - 1] = "DLS";
                        rewards[row - 2][col + 1] = "DLS";
                        rewards[row - 3][col] = "DLS";
                    }
                }

            }
        }
    }

    private boolean isNodeValid(int row, int col) {
        return (row > -1 && row < BOARD_SIZE) && (col > -1 && col < BOARD_SIZE);
    }

    private boolean isWordPlacementValid(Word word) {
        if (word.isVertical()) {
            for (int i = 0; i < word.getTiles().length; i++)
                if (!isNodeValid(word.getRow() + i, word.getCol()))
                    return false;
        } else {
            for (int i = 0; i < word.getTiles().length; i++)
                if (!isNodeValid(word.getRow(), word.getCol() + i))
                    return false;
        }
        return true;
    }

    private int nodeStatus(int row, int col) {
        if (!isNodeValid(row, col))
            return 0; // invalid node
        if (nodes[row][col] != null) // OPTIONAL: replace with instanceof Tile
            return 2; // not empty node

        return 1; // empty node
    }

    private int wordPlacementStatus(Word word) {
        if (!isWordPlacementValid(word))
            return 0; // invalid placement

        if (word.isVertical()) {
            for (int i = 0; i < word.getTiles().length; i++) {
                if (nodeStatus(word.getRow() + i, word.getCol()) == 2 && word.getTiles()[i].getLetter() != '_')
                    return 2; // not empty placement
            }

        } else {
            for (int i = 0; i < word.getTiles().length; i++) {
                if (nodeStatus(word.getRow(), word.getCol() + i) == 2 && word.getTiles()[i].getLetter() != '_')
                    return 2; // not empty placement
            }

        }
        return 1; // empty placement
    }

    public Tile[][] getTiles() {
        Tile[][] nodes_copy = new Tile[BOARD_SIZE][BOARD_SIZE];

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (nodes[row][col] != null)
                    nodes_copy[row][col] = nodes[row][col];
                else
                    nodes_copy[row][col] = null;
            }
        }
        return nodes_copy;
    }

    private void isEmptyContainer() {
        if (Tile.Bag.getBag().getAllTilesCount() < 1)
            isEmptyBag = true;
    }

    private boolean isNullNull(Word word) {
        for (int i = 0; i < word.getTiles().length; i++)
            if (word.isVertical()) {
                if (word.getTiles()[i].getLetter() == '_' && nodes[word.getRow() + i][word.getCol()] == null)
                    return true;
            } else {
                if (word.getTiles()[i].getLetter() == '_' && nodes[word.getRow()][word.getCol() + i] == null)
                    return true;
            }

        return false;
    }

    private boolean isWordLengthValid(Word word) {
        int count = 0;
        for (int i = 0; i < word.getTiles().length; i++)
            if (word.getTiles()[i].getLetter() != '_')
                count++;

        if (count > 7)
            return false;
        return true;
    }

    private boolean isEmptyWord(Word word) {
        for (int i = 0; i < word.getTiles().length; i++)
            if (word.getTiles()[i].getLetter() != '_')
                return false;
        return true;
    }

    private boolean wordHasNull(Word word) {
        for (int i = 0; i < word.getTiles().length; i++)
            if (word.getTiles()[i] == null)
                return true;
        return false;
    }

    private boolean isWordOnStar(Word word) {
        for (int i = 0; i < word.getTiles().length; i++) {
            if (word.isVertical()) {
                if (word.getTiles()[i].getLetter() != '_' && rewards[word.getRow() + i][word.getCol()] == "STAR")
                    return true;
            } else {
                if (word.getTiles()[i].getLetter() != '_' && rewards[word.getRow()][word.getCol() + i] == "STAR")
                    return true;
            }
        }
        return false;
    }

    private boolean isConnectedWord(Word word) {
        if (isWordOnStar(word))
            return true;

        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        int nullSequence = 0;

        if (word.isVertical()) {
            flag1 = nodeStatus(word.getRow() - 1, word.getCol()) == 2
                    || nodeStatus(word.getRow() + word.getTiles().length, word.getCol()) == 2;
            for (int i = 0; i < word.getTiles().length; i++) {
                if (!flag2)
                    flag2 = nodeStatus(word.getRow() + i, word.getCol() - 1) == 2
                            || nodeStatus(word.getRow() + i, word.getCol() + 1) == 2;

                if (!flag3 && word.getTiles()[i].getLetter() == '_')
                    nullSequence++;
                else
                    nullSequence = 0;

                if (nullSequence > 1)
                    flag3 = true;
            }
        } else {
            flag1 = nodeStatus(word.getRow(), word.getCol() - 1) == 2
                    || nodeStatus(word.getRow(), word.getCol() + word.getTiles().length) == 2;
            for (int i = 0; i < word.getTiles().length; i++) {
                if (!flag2)
                    flag2 = nodeStatus(word.getRow() - 1, word.getCol() + i) == 2
                            || nodeStatus(word.getRow() + 1, word.getCol() + i) == 2;

                if (!flag3 && word.getTiles()[i].getLetter() == '_')
                    nullSequence++;
                else
                    nullSequence = 0;

                if (nullSequence > 1)
                    flag3 = true;
            }
        }
        return flag1 || flag2 || flag3;
    }

    public boolean boardLegal(Word word) {
        switch (wordPlacementStatus(word)) {

            case 0: // invalid placement
                return false;
            case 1: // empty placement
                return isConnectedWord(word);

            case 2: // not empty placement
                return false;

            default:
                return false;

        }
    }

    public boolean dictionaryLegal(Word word) {
        return true;
    }

    private Word getWord(boolean vertical, int row, int col, Tile origin_tile) {
        int origin_row = row;
        int origin_col = col;

        if (!vertical) {
            while (nodeStatus(row - 1, col) == 2)
                row--;
        } else {
            while (nodeStatus(row, col - 1) == 2)
                col--;
        }

        int start_row = row;
        int start_col = col;

        ArrayList<Tile> tiles = new ArrayList<Tile>();

        if (!vertical) {
            while ((nodeStatus(row, col) == 2) || (row == origin_row && col == origin_col)) {
                if (row == origin_row && col == origin_col)
                    tiles.add(origin_tile);
                else
                    tiles.add(nodes[row][col]);
                row++;
            }
        } else {
            while (nodeStatus(row, col) == 2 || (row == origin_row && col == origin_col)) {
                if (row == origin_row && col == origin_col)
                    tiles.add(origin_tile);
                else
                    tiles.add(nodes[row][col]);
                col++;
            }
        }

        return new Word(tiles.toArray(new Tile[tiles.size()]), start_row, start_col, !vertical);
    }

    public ArrayList<Word> getWords(Word word) {
        ArrayList<Word> connected_words = new ArrayList<Word>();
        int row = word.getRow();
        int col = word.getCol();

        if (word.isVertical()) {
            while (nodeStatus(row - 1, col) == 2)
                row--;
        } else {
            while (nodeStatus(row, col - 1) == 2)
                col--;
        }

        int fixed_row = row;
        int fixed_col = col;

        ArrayList<Tile> fixed_tiles = new ArrayList<Tile>();

        if (fixed_row != word.getRow() || fixed_col != word.getCol()) {
            if (word.isVertical()) {
                while (nodeStatus(row, col) == 2) {
                    fixed_tiles.add(nodes[row][col]);
                    row++;
                }
            } else {
                while (nodeStatus(row, col) == 2) {
                    fixed_tiles.add(nodes[row][col]);
                    col++;
                }
            }
        }

        for (int i = 0; i < word.getTiles().length; i++)
            fixed_tiles.add(word.getTiles()[i]);

        int extra = 0;

        if (word.isVertical()) {
            while (isNodeValid(word.getRow() + word.getTiles().length
                    + extra,
                    word.getCol())
                    && nodes[word.getRow() + word.getTiles().length + extra][word.getCol()] != null) {
                fixed_tiles.add(nodes[word.getRow() + word.getTiles().length + extra][word.getCol()]);
                extra++;
            }
        } else {
            while (isNodeValid(word
                    .getRow(),
                    word.getCol() + word.getTiles().length
                            + extra)
                    && nodes[word.getRow()][word.getCol() + word.getTiles().length + extra] != null) {
                fixed_tiles.add(nodes[word.getRow()][word.getCol() + word.getTiles().length + extra]);
                extra++;
            }
        }

        connected_words.add(
                new Word(fixed_tiles.toArray(new Tile[fixed_tiles.size()]), fixed_row, fixed_col, word.isVertical()));

        if (word.isVertical()) {
            for (int i = 0; i < word.getTiles().length; i++) {
                if (word.getTiles()[i].getLetter() != '_') {
                    Word tWord = getWord(word.isVertical(), word.getRow() + i, word.getCol(), word.getTiles()[i]);

                    if ((tWord.getTiles() != null) && (tWord.getTiles().length > 1))
                        connected_words.add(tWord);
                }
            }
        } else {
            for (int i = 0; i < word.getTiles().length; i++) {
                if (word.getTiles()[i].getLetter() != '_') {
                    Word tWord = getWord(word.isVertical(), word.getRow(), word.getCol() + i, word.getTiles()[i]);

                    if ((tWord.getTiles() != null) && (tWord.getTiles().length > 1))
                        connected_words.add(tWord);
                }
            }
        }
        return connected_words;
    }

    private int getScore(Word word) {
        int sum = 0;
        for (int i = 0; i < word.getTiles().length; i++) {
            if (word.getTiles()[i].getLetter() != '_')
                sum += word.getTiles()[i].getScore();
            else if (word.isVertical())
                sum += nodes[word.getRow() + i][word.getCol()].getScore();
            else
                sum += nodes[word.getRow()][word.getCol() + i].getScore();
        }

        for (int i = 0; i < word.getTiles().length; i++) {
            if (word.isVertical()) {
                switch (rewards[word.getRow() + i][word.getCol()]) {
                    case "DLS":
                        sum += word.getTiles()[i].getLetter() != '_' ? word.getTiles()[i].getScore()
                                : nodes[word.getRow() + i][word.getCol()].getScore();
                        break;
                    case "TLS":
                        sum += word.getTiles()[i].getLetter() != '_' ? word.getTiles()[i].getScore() * 2
                                : nodes[word.getRow() + i][word.getCol()].getScore();
                        break;
                }
            }

            if (!word.isVertical()) {
                switch (rewards[word.getRow()][word.getCol() + i]) {
                    case "DLS":
                        sum += word.getTiles()[i].getLetter() != '_' ? word.getTiles()[i].getScore()
                                : nodes[word.getRow()][word.getCol() + i].getScore();
                        break;
                    case "TLS":
                        sum += word.getTiles()[i].getLetter() != '_' ? word.getTiles()[i].getScore() * 2
                                : nodes[word.getRow()][word.getCol() + i].getScore();
                        break;
                }
            }

        }

        for (int i = 0; i < word.getTiles().length; i++) {
            if (word.isVertical()) {
                switch (rewards[word.getRow() + i][word.getCol()]) {
                    case "STAR":
                        sum *= 2;
                        rewards[word.getRow() + i][word.getCol()] = "N/A";
                        break;
                    case "DWS":
                        sum *= 2;
                        break;
                    case "TWS":
                        sum *= 3;
                        break;
                }
            }

            if (!word.isVertical()) {
                switch (rewards[word.getRow()][word.getCol() + i]) {
                    case "STAR":
                        sum *= 2;
                        rewards[word.getRow()][word.getCol() + i] = "N/A";
                        break;
                    case "DWS":
                        sum *= 2;
                        break;
                    case "TWS":
                        sum *= 3;
                        break;
                }
            }

        }

        return sum;
    }

    private void placeWord(Word word) {
        for (int i = 0; i < word.getTiles().length; i++) {
            if (word.isVertical()) {
                if (word.getTiles()[i].getLetter() != '_')
                    nodes[word.getRow() + i][word.getCol()] = word.getTiles()[i];
            } else {
                if (word.getTiles()[i].getLetter() != '_')
                    nodes[word.getRow()][word.getCol() + i] = word.getTiles()[i];
            }
        }
    }

    public int tryPlaceWord(Word word) {
        int score = 0;

        if (!isEmptyBag) {
            isEmptyContainer();
            if (!wordHasNull(word)) {
                if (!isEmptyWord(word)) {
                    if (isWordLengthValid(word)) {
                        if (boardLegal(word)) {
                            if (!isNullNull(word)) {
                                if (dictionaryLegal(word)) {
                                    ArrayList<Word> created_words = getWords(word);
                                    for (int i = 0; i < created_words.size(); i++) {
                                        score += getScore(created_words.get(i));
                                        placeWord(created_words.get(i));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return score;
    }

    public static Board getBoard() {
        if (board == null)
            board = new Board();

        return board;
    }

}