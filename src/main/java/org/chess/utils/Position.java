package org.chess.utils;

import org.chess.exceptions.Exception;
import org.chess.exceptions.PositionException;

public class Position {
    private int row;
    private int col;
    private final int min_row_col = 1;
    private final int max_row_col = 8;

    public Position(int row, int col) {
        if (row < min_row_col || row > max_row_col || col < min_row_col || col > max_row_col) {
            throw new PositionException(Exception.POSITION_OUT_OF_BOUNDS);
        }
        this.col = col;
        this.row = row;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        if (row < min_row_col || row > max_row_col) {
            throw new PositionException(Exception.ROW_OUT_OF_BOUNDS);
        }
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        if (col < min_row_col || col > max_row_col) {
            throw new PositionException(Exception.COlUMN_OUT_OF_BOUNDS);
        }
        this.col = col;
    }

    @Override
    public String toString() {
        return Character.toString(('a' - 1 + col)) + row;
    }
}
