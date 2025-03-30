package org.chess.pieces;

import org.chess.ChessBoard;
import org.chess.utils.PieceColor;
import org.chess.utils.Position;

import java.awt.image.BufferedImage;

public class Knight extends AbstractChessPiece{

    public Knight(PieceColor color, Position position, BufferedImage image, String name) {
        super(color, position, image, name);
    }
    @Override
    public boolean isValidMove(Position newPosition, ChessBoard chessBoard) {
        return (Math.abs(position.getRank() - newPosition.getRank()) == 1 && Math.abs(position.getFile() - newPosition.getFile()) == 2) ||
                (Math.abs(position.getRank() - newPosition.getRank()) == 2 && Math.abs(position.getFile() - newPosition.getFile()) == 1) && !chessBoard.isFriendlyPieceOn(newPosition);
    }
}