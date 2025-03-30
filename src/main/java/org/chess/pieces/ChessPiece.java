package org.chess.pieces;

import org.chess.ChessBoard;
import org.chess.utils.PieceColor;
import org.chess.utils.Position;

import java.awt.image.BufferedImage;

public interface ChessPiece {
    boolean isValidMove(Position newPosition, ChessBoard chessBoard);
    boolean isFriendlyPiece(ChessPiece piece);
    boolean isMoved();
    PieceColor getColor();
    Position getPosition();
    void setPosition(Position position);
    void setMoved(boolean isMoved);
    BufferedImage getImage();
    String getName();
}
