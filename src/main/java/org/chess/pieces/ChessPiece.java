package org.chess.pieces;

import lombok.Getter;
import lombok.Setter;
import org.chess.ChessBoard;
import org.chess.utils.PieceColor;
import org.chess.utils.Position;

import java.awt.image.BufferedImage;

@Getter
public class ChessPiece {
    private final PieceColor pieceColor;
    @Setter
    private Position position;
    private final ChessPieceType pieceType;
    private final BufferedImage image;
    @Setter
    private boolean isMoved;

    private ChessPiece(PieceColor pieceColor, Position position, ChessPieceType pieceType, BufferedImage image) {
        this.pieceColor = pieceColor;
        this.position = position;
        this.pieceType = pieceType;
        this.image = image;
    }

    public static ChessPiece of(PieceColor pieceColor, Position position, ChessPieceType pieceType, BufferedImage image) {
        return new ChessPiece(pieceColor, position, pieceType, image);
    }

    public boolean isValidMove(Position newPosition, ChessBoard chessBoard) {
        if (newPosition == null || !newPosition.isValid()) {
            return false;
        }
        ChessPiece chessPieceOnNewPosition = chessBoard.getPieceAt(newPosition);

        if (this.pieceColor != chessBoard.getNextMoveSideColor()) {
            switch (this.pieceType) {
                case PAWN -> {
                    return isPawnMoveValid(this.position, newPosition, chessPieceOnNewPosition);
                }
                case BISHOP -> {
                    return isBishopMoveValid(this.position, newPosition, chessBoard);
                }
                case QUEEN -> {
                    return isQueenMoveValid(this.position, newPosition, chessBoard);
                }
                case ROOK -> {
                    return isRookMoveValid(this.position, newPosition, chessBoard);
                }
                default -> {
                    return false;
                }
            }
        }
        return false;
    }

    private boolean isPawnMoveValid(Position currentPosition, Position newPosition, ChessPiece chessPieceOnNewPosition) {
        boolean result = false;
        int secondRank = 1;
        int seventhRank = 6;
        int currentRank = currentPosition.getRank();
        int currentFile = currentPosition.getFile();
        int newRank = newPosition.getRank();
        int newFile = newPosition.getFile();
        boolean isOnDiagonal = currentFile - newFile != 0;

        switch (this.pieceColor) {
            case WHITE -> {
                if ((currentRank == secondRank && newRank <= (currentRank + 2)) || (newRank <= (currentRank + 1) && newRank > currentRank)) {
                    if (chessPieceOnNewPosition != null && isOnDiagonal && chessPieceOnNewPosition.pieceColor != this.pieceColor) {
                        result = true;
                    } else if (chessPieceOnNewPosition == null && !isOnDiagonal) {
                        result = true;
                    }
                }
            }
            case BLACK -> {
                if ((currentRank == seventhRank && newRank >= (currentRank - 2)) || (newRank >= (currentRank - 1)) && newRank < currentRank) {
                    if (chessPieceOnNewPosition != null && isOnDiagonal && chessPieceOnNewPosition.pieceColor != this.pieceColor) {
                        result = true;
                    } else if (chessPieceOnNewPosition == null && !isOnDiagonal) {
                        result = true;
                    }
                }
            }
        }
        return result;
    }

    private boolean isBishopMoveValid(Position startingPosition, Position endPosition, ChessBoard chessBoard) {
        return startingPosition.isValidDiagonal(endPosition) && chessBoard.isFriendlyPieceNotPresent(startingPosition, endPosition);
    }

    private boolean isQueenMoveValid(Position startingPosition, Position endPosition, ChessBoard chessBoard) {
        return (startingPosition.isValidDiagonal(endPosition) || startingPosition.isValidVertical(endPosition) || startingPosition.isValidHorizontal(endPosition))
                && chessBoard.isFriendlyPieceNotPresent(startingPosition, endPosition);
    }

    private boolean isRookMoveValid(Position startingPosition, Position endPosition, ChessBoard chessBoard) {
        return (startingPosition.isValidVertical(endPosition) || startingPosition.isValidHorizontal(endPosition))
                && chessBoard.isFriendlyPieceNotPresent(startingPosition, endPosition);
    }


    @Override
    public String toString() {
        return String.format("%s %s at position %s", this.pieceColor.toString()
                .toLowerCase(), this.pieceType.pieceName.toLowerCase(), this.position);
    }

    public boolean isMoved() {
        return isMoved;
    }
}
