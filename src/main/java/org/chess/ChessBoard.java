package org.chess;

import lombok.Getter;
import lombok.Setter;
import org.chess.pieces.ChessPiece;
import org.chess.utils.Config;
import org.chess.utils.PieceColor;
import org.chess.utils.Position;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ChessBoard {
    private final Map<Position, ChessPiece> chessPieceMap = new HashMap<>();
    private final int boardSize = Config.getBoardSize();
    private final int squareSize = Config.getSquareSize();
    private final Color lightSquareColor = Config.getLightColor();
    private final Color darkSquareColor = Config.getDarkColor();
    private PieceColor nextMoveSideColor = PieceColor.BLACK;
    private ChessPiece selectedPiece;


    private Position selectedPosition;

    public void addPieceAt(Position position, ChessPiece piece) {
        chessPieceMap.put(position, piece);
    }

    public ChessPiece getPieceAt(Position position) {
        return chessPieceMap.get(position);
    }
    public void removePieceFrom(Position position) {
        chessPieceMap.remove(position);
    }

    public void moveSelectedPieceTo(Position newPosition) {
        if (selectedPiece != null && selectedPiece.isValidMove(newPosition, this)) {
            selectedPiece.setMoved(!newPosition.equals(selectedPosition));
            if (selectedPiece.isMoved()) {
                setPieceAt(newPosition);
                PieceColor nextMoveSideColor = selectedPiece.getPieceColor() == PieceColor.WHITE ? PieceColor.WHITE : PieceColor.BLACK;
                setNextMoveSideColor(nextMoveSideColor);
                selectedPiece = null;
                ChessBoardUI.selectedPosition = null;
            }
        }
    }


    public void selectPieceAt(Position position) {
        ChessPiece piece = this.getPieceAt(position);
        if (piece != null) {
            selectedPiece = piece;
            selectedPosition = position;
        }
    }

    public boolean isFriendlyPieceNotPresent(Position startingPosition, Position endingPosition) {

        if (startingPosition.isValidDiagonal(endingPosition)) {
            if (startingPosition.getRank() < endingPosition.getRank()) {
                int currentFile = startingPosition.getFile();
                for (int i = startingPosition.getRank(); i < endingPosition.getRank(); i++) {
                    Position position = startingPosition.getFile() < endingPosition.getFile() ? Position.of(i + 1, currentFile + 1) : Position.of(i + 1, currentFile - 1);
                    if (this.getPieceAt(position) != null && this.getPieceAt(position).getPieceColor() == selectedPiece.getPieceColor()) {
                        return false;
                    }
                    if (startingPosition.getFile() < endingPosition.getFile()) {
                        currentFile ++;
                    } else {
                        currentFile --;
                    }
                }
            } else if (startingPosition.getRank() > endingPosition.getRank()) {
                int currentFile = startingPosition.getFile();
                for (int i = startingPosition.getRank(); i > endingPosition.getRank(); i--) {
                    Position position = startingPosition.getFile() > endingPosition.getFile() ? Position.of(i - 1, currentFile - 1) : Position.of(i - 1, currentFile + 1);
                    if (this.getPieceAt(position) != null && this.getPieceAt(position).getPieceColor() == selectedPiece.getPieceColor()) {
                        return false;
                    }
                    if (startingPosition.getFile() > endingPosition.getFile()) {
                        currentFile --;
                    } else {
                        currentFile ++;
                    }
                }
            }
        }

        if (startingPosition.isValidHorizontal(endingPosition)) {
            int currentRank = startingPosition.getRank();
            if (startingPosition.getFile() < endingPosition.getFile()) {
                for (int i = startingPosition.getFile(); i < endingPosition.getFile(); i++) {
                    Position position = Position.of(currentRank, i + 1);
                    if (this.getPieceAt(position) != null && this.getPieceAt(position).getPieceColor() == selectedPiece.getPieceColor()) {
                        return false;
                    }
                }
            } else if (startingPosition.getFile() > endingPosition.getFile()) {
                for (int i = startingPosition.getFile(); i > endingPosition.getFile(); i--) {
                    Position position = Position.of(currentRank, i - 1);
                    if (this.getPieceAt(position) != null && this.getPieceAt(position).getPieceColor() == selectedPiece.getPieceColor()) {
                        return false;
                    }
                }
            }
        }

        if (startingPosition.isValidVertical(endingPosition)) {
            int currentFile = startingPosition.getFile();
            if (startingPosition.getRank() < endingPosition.getRank()) {
                for (int i = startingPosition.getRank(); i < endingPosition.getRank(); i++) {
                    Position position = Position.of(i + 1, currentFile);
                    if (this.getPieceAt(position) != null && this.getPieceAt(position).getPieceColor() == selectedPiece.getPieceColor()) {
                        return false;
                    }
                }
            } else if (startingPosition.getRank() > endingPosition.getRank()) {
                for (int i = startingPosition.getRank(); i > endingPosition.getRank(); i--) {
                    Position position = Position.of(i - 1, currentFile);
                    if (this.getPieceAt(position) != null && this.getPieceAt(position).getPieceColor() == selectedPiece.getPieceColor()) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private void setPieceAt( Position position) {
        selectedPiece.setPosition(position);
        this.removePieceFrom(selectedPosition);
        this.addPieceAt(position, selectedPiece);
    }
}
