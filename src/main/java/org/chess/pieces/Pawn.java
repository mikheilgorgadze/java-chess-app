package org.chess.pieces;

import org.chess.ChessBoard;
import org.chess.utils.PieceColor;
import org.chess.utils.Position;

import java.awt.image.BufferedImage;

public class Pawn extends AbstractChessPiece {

    public Pawn(PieceColor color, Position position, BufferedImage image, String name) {
        super(color, position, image, name);
    }

    @Override
    public boolean isValidMove(Position newPosition, ChessBoard chessBoard) {
        boolean result = false;
        int secondRank = 1;
        int seventhRank = 6;
        int currentRank = position.getRank();
        int currentFile = position.getFile();
        int newRank = newPosition.getRank();
        int newFile = newPosition.getFile();
        boolean isOnDiagonal = currentFile - newFile != 0;
        ChessPiece chessPieceOnNewPosition = chessBoard.getPieceAt(newPosition);

        switch (this.color) {
            case WHITE -> {
                if ((currentRank == secondRank && newRank <= (currentRank + 2)) || (newRank <= (currentRank + 1) && newRank > currentRank)) {
                    if (chessPieceOnNewPosition != null && isOnDiagonal && chessPieceOnNewPosition.getColor() != this.color) {
                        result = true;
                    } else if (chessPieceOnNewPosition == null && !isOnDiagonal) {
                        result = true;
                    }
                }
            }
            case BLACK -> {
                if ((currentRank == seventhRank && newRank >= (currentRank - 2)) || (newRank >= (currentRank - 1)) && newRank < currentRank) {
                    if (chessPieceOnNewPosition != null && isOnDiagonal && chessPieceOnNewPosition.getColor() != this.color) {
                        result = true;
                    } else if (chessPieceOnNewPosition == null && !isOnDiagonal) {
                        result = true;
                    }
                }
            }
        }
        return result;
    }
}
