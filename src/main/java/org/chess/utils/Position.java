package org.chess.utils;

import org.chess.exceptions.ChessException;
import org.chess.exceptions.PositionException;

public class Position {
    private int rank;
    private int file;
    private final int min_row_col = 0;
    private final int max_row_col = 7;

    public Position(int rank, int file) {
        if (rank < min_row_col || rank > max_row_col || file < min_row_col || file > max_row_col) {
            throw new PositionException(ChessException.POSITION_OUT_OF_BOUNDS);
        }
        this.file = file;
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        if (rank < min_row_col || rank > max_row_col) {
            throw new PositionException(ChessException.ROW_OUT_OF_BOUNDS);
        }
        this.rank = rank;
    }

    public int getFile() {
        return file;
    }

    public void setFile(int file) {
        if (file < min_row_col || file > max_row_col) {
            throw new PositionException(ChessException.COlUMN_OUT_OF_BOUNDS);
        }
        this.file = file;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return rank == position.rank && file == position.file;
    }

    @Override
    public int hashCode() {
        return 31 * rank + file;
    }
    @Override
    public String toString() {
        return Character.toString(('a' + file)) + (rank + 1);
    }
}
