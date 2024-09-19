package org.chess.pieces;

import org.chess.utils.PieceColor;
import org.chess.utils.Position;

public class Knight extends ChessPiece {

    public Knight (Position position, PieceColor pieceColor) {
        super(pieceColor, ChessPieceType.KNIGHT);
    }

    public Knight (PieceColor pieceColor) {
        super(pieceColor, ChessPieceType.KNIGHT);
    }
}