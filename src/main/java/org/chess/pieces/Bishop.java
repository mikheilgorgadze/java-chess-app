package org.chess.pieces;

import org.chess.utils.PieceColor;
import org.chess.utils.Position;

public class Bishop extends ChessPiece {

    public Bishop (Position position, PieceColor pieceColor) {
        super(pieceColor, position, ChessPieceType.BISHOP);
    }
    public Bishop (PieceColor pieceColor) {
        super(pieceColor, ChessPieceType.BISHOP);
    }

}