package org.chess.pieces;

import org.chess.utils.PieceColor;
import org.chess.utils.Position;

public class King extends ChessPiece{

    public King (Position position, PieceColor pieceColor) {
        super(pieceColor, ChessPieceType.KING);
    }
    public King (PieceColor pieceColor) {
        super(pieceColor, ChessPieceType.KING);
    }
}
