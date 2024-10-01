package org.chess.pieces;

import org.chess.ChessBoard;
import org.chess.utils.PieceColor;
import org.chess.utils.Position;

import java.awt.image.BufferedImage;
import java.util.Map;

public class ChessPiece {
    private final PieceColor pieceColor;
    private Position position;
    private final ChessPieceType pieceType;
    private final BufferedImage image;

    public ChessPiece(PieceColor pieceColor, Position position, ChessPieceType pieceType, BufferedImage image) {
        this.pieceColor = pieceColor;
        this.position = position;
        this.pieceType = pieceType;
        this.image = image;
    }

    public boolean isValidMove(Position newPosition, ChessBoard chessBoard) {
        Map<Position, ChessPiece> chessPieceMap = chessBoard.getChessPieceMap();
        if (newPosition == null || chessPieceMap == null) {
            return false;
        }
        boolean result = false;
        int newRank = newPosition.getRank();
        int newFile = newPosition.getFile();
        int currentRank = this.position.getRank();
        int currentFile = this.position.getFile();
        ChessPiece chessPieceOnNewPosition = chessPieceMap.get(newPosition);

        if (this.pieceColor != chessBoard.getNextMoveSideColor()) {
            switch (this.pieceType) {
                case PAWN -> result = isPawnMoveValid(currentRank, newRank, currentFile, newFile, chessPieceOnNewPosition);
                case BISHOP -> result = isBishopMoveValid();

            }
        }
        return result;
    }

    private boolean isPawnMoveValid(int currentRank, int newRank, int currentFile, int newFile, ChessPiece chessPieceOnNewPosition) {
        boolean result = false;
        int secondRank = 1;
        int seventhRank = 6;
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
                    if (chessPieceOnNewPosition != null && isOnDiagonal && chessPieceOnNewPosition.pieceColor != PieceColor.BLACK) {
                        result = true;
                    } else if (chessPieceOnNewPosition == null && !isOnDiagonal) {
                        result = true;
                    }
                }
            }
        }

        return result;
    }

    private boolean isBishopMoveValid() {
        //TODO
        return false;
    }

    public PieceColor getPieceColor() {
        return pieceColor;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getPieceName() {
        return pieceType.pieceName;
    }

    public BufferedImage getImage() {
        return image;
    }

    @Override
    public String toString() {
        return String.format("%s %s at position %s", getPieceColor().toString().toLowerCase(), getPieceName().toLowerCase(), getPosition());
    }
}
