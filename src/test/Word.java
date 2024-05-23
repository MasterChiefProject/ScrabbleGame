package test;

import java.util.Arrays;
import java.util.Objects;

public class Word {
    private final Tile[] tiles;
    private final int row;
    private final int col;
    private final boolean vertical;

    public Word(Tile[] tiles, int row, int col, boolean vertical) {
        this.tiles = tiles;
        this.row = row;
        this.col = col;
        this.vertical = vertical;
    }

    public Tile[] getTiles() {
        return this.tiles;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public boolean isVertical() {
        return this.vertical;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass() || !(obj instanceof Word))
            return false;
        Word other = (Word) obj;

        if (this.tiles.length != other.tiles.length)
            return false;

        for (int i = 0; i < this.tiles.length; i++)
            if (!this.tiles[i].equals(other.tiles[i]))
                return false;

        return this.row == other.row && this.col == other.col && this.vertical == other.vertical;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(row, col, vertical);
        result = 31 * result + Arrays.hashCode(tiles);
        return result;
    }
}
