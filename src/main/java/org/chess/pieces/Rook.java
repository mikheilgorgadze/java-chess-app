package org.chess.pieces;

import org.chess.utils.PieceColor;
import org.chess.utils.Position;

public class Rook extends ChessPiece {

    public Rook (Position position, PieceColor pieceColor) {
        super(pieceColor, position, ChessPieceType.ROOK);
    }
    public Rook (PieceColor pieceColor) {
        super(pieceColor, ChessPieceType.ROOK);
    }
}