package org.chess;

import org.chess.exceptions.ChessException;
import org.chess.pieces.*;
import org.chess.utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Map;

public class ChessBoardUI extends JPanel {
    private final ChessBoard chessBoard = new ChessBoard();
    private ChessPiece selectedPiece;
    private Position selectedPosition;

    public ChessBoardUI() {
        addMouseListener(new ChessBoardMouseListener());
        fillChessPiecesMapFromFEN(Config.getInitialFEN());
    }

    /**
     * This method draws all the chess components including board and pieces
     * @param g the <code>Graphics</code> object
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        chessBoard.drawBoard(g);
        drawPieces(g);
        if (selectedPosition != null) {
            highlightSquare(g, selectedPosition);
        }
    }
    private class ChessBoardMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            int squareSize = chessBoard.getSquareSize();
            int file = e.getX() / squareSize;
            int rank = 7 - (e.getY() / squareSize);
            Position pos = Position.create(rank, file);
            selectPiece(pos);
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            int squareSize = chessBoard.getSquareSize();
            int file = e.getX() / squareSize;
            int rank = 7 - (e.getY() / squareSize);
            Position newPosition = Position.create(rank, file);
            if (selectedPiece != null && selectedPiece.isValidMove(newPosition, chessBoard)) {
                setPieceAt(newPosition);
                PieceColor nextMoveSideColor = selectedPiece.getPieceColor() == PieceColor.WHITE ? PieceColor.WHITE : PieceColor.BLACK;
                chessBoard.setNextMoveSideColor(nextMoveSideColor);
                selectedPiece = null;
                selectedPosition = null;
            }
            repaint();
        }
    }

    private void selectPiece(Position position) {
        ChessPiece piece = chessBoard.getPieceAt(position);
        if (piece != null) {
            selectedPiece = piece;
            selectedPosition = position;
        }
    }

    private void setPieceAt( Position position) {
        selectedPiece.setPosition(position);
        chessBoard.removePieceFrom(selectedPosition);
        chessBoard.addPieceAt(position, selectedPiece);
    }

    private void highlightSquare(Graphics g, Position position) {
        int squareSize = chessBoard.getSquareSize();
        g.setColor(Config.getHighlightedColor());
        g.fillRect(position.getFile() * squareSize, (7 - position.getRank()) * squareSize, squareSize, squareSize);
    }

    private void fillChessPiecesMapFromFEN(String fen) {
        if (fen == null || fen.isEmpty() ) {
            throw new IllegalArgumentException("Argument should not be null");
        }
        int rank = 7;
        int file = 0;
        for (char c : fen.toCharArray()) {
            if (Fen.isCorrectFenSymbol(c)){
                ChessPieceType pieceType = ChessPieceType.fromFenChar(c);
                PieceColor color = Character.isUpperCase(c) ? PieceColor.WHITE : PieceColor.BLACK;
                Position position = Position.create(rank, file);
                String pieceName = color.name().toLowerCase() + "-" + pieceType.pieceName;
                BufferedImage image = ResourceManager.loadImage(pieceName);
                ChessPiece piece = new ChessPiece(color, position, pieceType, image);
                chessBoard.addPieceAt(position, piece);
                file++;
            } else if (Character.isDigit(c)) {
                file += Character.getNumericValue(c);
            } else if (c == '/') {
                rank--;
                file=0;
            } else {
                throw new RuntimeException(ChessException.ILLEGAL_FEN_SYMBOL.message);
            }
        }
    }


    /**
     * This function draws chess pieces on board from chessBoard array
     * @param g Graphics class instance to draw chess pieces
     */
    public void drawPieces(Graphics g) {
        int squareSize = chessBoard.getSquareSize();
        Map<Position, ChessPiece> chessPieceMap = chessBoard.getChessPieceMap();
        for (Map.Entry<Position, ChessPiece> entry : chessPieceMap.entrySet()) {
            Position position = entry.getKey();
            ChessPiece chessPiece = entry.getValue();
            int x = position.getFile() * squareSize;
            int y = (7 - position.getRank()) * squareSize;
            if (chessPiece != null) {
                g.drawImage(chessPiece.getImage(), x, y, squareSize, squareSize, this);
            }
        }
    }

}
