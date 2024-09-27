package org.chess;

import org.chess.utils.Config;

import java.awt.*;

public class ChessBoard {
    private final int boardSize = Config.getBoardSize();
    private final int squareSize = Config.getSquareSize();
    private final Color lightSquareColor = Config.getLightColor();
    private final Color darkSquareColor = Config.getDarkColor();

    /**
     *
     * @param g Graphics class instance, so function is able to graphically draw chess board
     */
    public void drawBoard(Graphics g) {
        for (int rank = 0; rank < boardSize; rank++) {
            for (int file = 0; file < boardSize; file++) {
                int x = file * squareSize;
                int y = rank * squareSize;
                g.setColor((rank + file) % 2 == 0 ? lightSquareColor : darkSquareColor);
                g.fillRect(x, y, squareSize, squareSize);
            }
        }
    }

    public int getSquareSize() {
        return squareSize;
    }

}
