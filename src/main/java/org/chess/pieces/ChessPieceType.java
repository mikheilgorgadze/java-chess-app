package org.chess.pieces;

import org.chess.utils.PieceColor;

public enum ChessPieceType {
    PAWN("pawn", 'P', 'p'),
    BISHOP("bishop", 'B', 'b'),
    KNIGHT("knight", 'N', 'n'),
    ROOK("rook", 'R', 'r'),
    KING("king", 'K', 'k'),
    QUEEN("queen", 'Q', 'q');

    public final String pieceName;
    public final char whiteFenChar;
    public final char blackFenChar;

    private ChessPieceType(String pieceName, char whiteFenChar, char blackFenChar) {
        this.pieceName = pieceName;
        this.whiteFenChar = whiteFenChar;
        this.blackFenChar = blackFenChar;
    }

    public String getPieceName() {
        return pieceName;
    }

    public char getFenChar(PieceColor color) {
        return color == PieceColor.WHITE ? whiteFenChar : blackFenChar;
    }

    public static ChessPieceType fromFenChar(char fenChar) {
        for (ChessPieceType type : values()) {
            if (Character.toLowerCase(fenChar) == Character.toLowerCase(type.blackFenChar)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid FEN character: " + fenChar);
    }
}
