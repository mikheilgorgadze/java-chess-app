package org.chess.pieces;

import org.chess.utils.PieceColor;
import org.chess.utils.Position;

public class Pawn extends ChessPiece {

    public Pawn (Position position, PieceColor pieceColor) {
        super(pieceColor, position, ChessPieceType.PAWN);
    }

    public Pawn (PieceColor pieceColor) {
        super(pieceColor, ChessPieceType.PAWN);
    }
}
