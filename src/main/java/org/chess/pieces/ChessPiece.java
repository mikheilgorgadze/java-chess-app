package org.chess.pieces;

import lombok.Getter;
import lombok.Setter;
import org.chess.ChessBoard;
import org.chess.utils.PieceColor;
import org.chess.utils.Position;

import java.awt.image.BufferedImage;
import java.util.Map;

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

    public static ChessPiece of (PieceColor pieceColor, Position position, ChessPieceType pieceType, BufferedImage image) {
        return new ChessPiece(pieceColor, position, pieceType, image);
    }

    public boolean isValidMove(Position newPosition, ChessBoard chessBoard) {
        Map<Position, ChessPiece> chessPieceMap = chessBoard.getChessPieceMap();
        if (newPosition == null || chessPieceMap == null) {
            return false;
        }
        boolean result = false;
        ChessPiece chessPieceOnNewPosition = chessPieceMap.get(newPosition);

        if (this.pieceColor != chessBoard.getNextMoveSideColor()) {
            switch (this.pieceType) {
                case PAWN -> result = isPawnMoveValid(this.position, newPosition, chessPieceOnNewPosition);
                case BISHOP -> result = isBishopMoveValid(this.position, newPosition, chessBoard);
            }
        }
        return result;
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

    private boolean isBishopMoveValid(Position currentPosition, Position newPosition, ChessBoard chessBoard) {
        boolean result = false;
        int currentRank = currentPosition.getRank();
        int currentFile = currentPosition.getFile();
        int newRank = newPosition.getRank();
        int newFile = newPosition.getFile();

        if (Math.abs(newRank - currentRank) == Math.abs(newFile - currentFile)) {
            boolean found = false;
            for (int i = 1; i <= Math.abs(newRank - currentRank); i++) {
                ChessPiece currentPiece = chessBoard.getPieceAt(Position.of(currentRank + i, currentFile + i));
                if (currentPiece != null) {
                    found = true;
                    if (currentPiece.pieceColor == this.pieceColor) {
                        return false;
                    }
                }
            }
            result = !found;
        }
        return result;
    }

    private boolean isPiecePresentOnLine(Position startingPosition, Position endingPosition) {
        return false;
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
