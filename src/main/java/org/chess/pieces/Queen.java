package org.chess.pieces;

import org.chess.utils.PieceColor;
import org.chess.utils.Position;

public class Queen extends ChessPiece {

    public Queen (Position position, PieceColor pieceColor) {
        super(pieceColor, position, ChessPieceType.QUEEN);
    }
    public Queen (PieceColor pieceColor) {
        super(pieceColor, ChessPieceType.QUEEN);
    }
}