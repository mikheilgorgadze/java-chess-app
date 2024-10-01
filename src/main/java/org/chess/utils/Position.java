package org.chess.utils;

import org.chess.exceptions.ChessException;
import org.chess.exceptions.PositionException;

import java.util.Optional;

public class Position {
    private final int rank;
    private final int file;
    private static final int min_row_col = 0;
    private static final int max_row_col = 7;
    private static final Position INVALID = new Position(-1, -1);


    public static Position create(int rank, int file) {
        return isValidPosition(rank, file) ? new Position(rank, file) : INVALID;
    }

    private Position(int rank, int file) {
        this.file = file;
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    public int getFile() {
        return file;
    }

    public boolean isValid() {
        return this != INVALID;
    }

    public static boolean isValidPosition(int rank, int file) {
        return isValidRank(rank) && isValidFile(file);
    }

    private static boolean isValidFile(int file) {
        return file >= min_row_col && file <= max_row_col;
    }

    private static boolean isValidRank(int rank) {
        return rank >= min_row_col && rank <= max_row_col;
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
