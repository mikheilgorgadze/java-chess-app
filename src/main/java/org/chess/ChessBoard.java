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

    private void setPieceAt( Position position) {
        selectedPiece.setPosition(position);
        this.removePieceFrom(selectedPosition);
        this.addPieceAt(position, selectedPiece);
    }
}
