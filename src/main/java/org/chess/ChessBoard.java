package org.chess;

import org.chess.pieces.ChessPiece;
import org.chess.utils.Config;
import org.chess.utils.PieceColor;
import org.chess.utils.Position;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ChessBoard {
    private final Map<Position, ChessPiece> chessPieceMap = new HashMap<>();
    private final int boardSize = Config.getBoardSize();
    private final int squareSize = Config.getSquareSize();
    private final Color lightSquareColor = Config.getLightColor();
    private final Color darkSquareColor = Config.getDarkColor();
    private PieceColor nextMoveSideColor = PieceColor.BLACK;

    public void addPieceAt(Position position, ChessPiece piece) {
        chessPieceMap.put(position, piece);
    }

    public ChessPiece getPieceAt(Position position) {
        return chessPieceMap.get(position);
    }
    public void removePieceFrom(Position position) {
        chessPieceMap.remove(position);
    }

    public Map<Position, ChessPiece> getChessPieceMap() {
        return chessPieceMap;
    }

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
                g.setColor(Color.BLACK);
                g.drawRect(x, y, squareSize, squareSize);
                if (rank == boardSize - 1) {
                    g.drawString(Character.toString('a' + file), x + squareSize - 10, y + squareSize - 5);
                }
                if (file == 0) {
                    g.drawString(String.valueOf(boardSize - rank), x + 5, y + 15);
                }
            }
        }
    }

    public int getSquareSize() {
        return squareSize;
    }

    public void setNextMoveSideColor(PieceColor nextMoveSideColor) {
        this.nextMoveSideColor = nextMoveSideColor;
    }

    public PieceColor getNextMoveSideColor() {
        return nextMoveSideColor;
    }
}
