package org.chess.pieces;

import org.chess.ChessBoard;
import org.chess.utils.PieceColor;
import org.chess.utils.Position;

import java.awt.image.BufferedImage;

public class Queen extends AbstractChessPiece{

    public Queen(PieceColor color, Position position, BufferedImage image, String name) {
        super(color, position, image, name);
    }
    @Override
    public boolean isValidMove(Position newPosition, ChessBoard chessBoard) {
        return (position.isValidDiagonal(newPosition) || position.isValidVertical(newPosition) || position.isValidHorizontal(newPosition))
                && chessBoard.isFriendlyPieceNotPresentBetween(position, newPosition);
    }
}