package org.chess.pieces;

import org.chess.exceptions.ChessException;
import org.chess.utils.PieceColor;
import org.chess.utils.Position;

import java.awt.image.BufferedImage;

public class ChessPieceFactory {
    public static ChessPiece get(PieceColor color, Position position, BufferedImage image, String name, ChessPieceType pieceType) {
        switch (pieceType) {
            case PAWN -> {
                return new Pawn(color, position, image, name);
            }
            case BISHOP -> {
                return new Bishop(color, position, image, name);
            }
            case KNIGHT -> {
                return new Knight(color, position, image, name);
            }
            case ROOK ->{
                return new Rook(color, position, image, name);
            }
            case KING -> {
                return new King(color, position, image, name);
            }
            case QUEEN -> {
                return new Queen(color, position, image, name);
            }
            default -> throw new RuntimeException(ChessException.INVALID_PIECE_TYPE.message);
        }
    }
}
