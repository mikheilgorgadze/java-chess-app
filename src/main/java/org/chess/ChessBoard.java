package org.chess;

import org.chess.exceptions.ChessException;
import org.chess.pieces.*;
import org.chess.utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class ChessBoard extends JPanel {
    private final Map<Position, ChessPiece> chessBoard = new HashMap<>();

    private static final int BOARD_SIZE = Config.getBoardSize();
    private static final int SQUARE_SIZE = Config.getSquareSize();

    private ChessPiece selectedPiece;
    private Position selectedPosition;

    public ChessBoard() {
        addMouseListener(new ChessBoardMouseListener());
        fillChessBoardArrayFromFEN(Config.getInitialFEN());
    }

    private class ChessBoardMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            int file = e.getX() / SQUARE_SIZE;
            int rank = 7 - (e.getY() / SQUARE_SIZE);
            Position pos = new Position(rank, file);
            try {
                selectPiece(pos);
            } catch (Exception ignore){};
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            int file = e.getX() / SQUARE_SIZE;
            int rank = 7 - (e.getY() / SQUARE_SIZE);
            setPieceAt(new Position(rank, file));
            repaint();
        }
    }


    private void selectPiece(Position position) {
        ChessPiece piece = chessBoard.get(position);
        if (piece != null) {
            selectedPiece = piece;
            selectedPosition = position;
        }
    }

    private void setPieceAt( Position position) {
        chessBoard.remove(selectedPosition);
        chessBoard.put(position, selectedPiece);
    }

    /**
     * This method draws all the chess components including board and pieces
     * @param g the <code>Graphics</code> object
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g, Config.getLightColor(), Config.getDarkColor());
        drawPieces(g);
        if (selectedPosition != null) {
            highlightSquare(g, selectedPosition);
        }
    }

    private void highlightSquare(Graphics g, Position position) {
        g.setColor(new Color(255, 255, 0, 128));
        g.fillRect(position.getFile() * SQUARE_SIZE, (7 - position.getRank()) * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
    }

    private void fillChessBoardArrayFromFEN(String fen) {
        if (fen == null || fen.isEmpty() ) {
            throw new IllegalArgumentException("Argument should not be null");
        }
        int rank = 7;
        int file = 0;
        for (char c : fen.toCharArray()) {
            if (Fen.isCorrectFenSymbol(c)){
                ChessPieceType pieceType = ChessPieceType.fromFenChar(c);
                PieceColor color = Character.isUpperCase(c) ? PieceColor.WHITE : PieceColor.BLACK;
                Position position = new Position(rank, file);
                String pieceName = color.name().toLowerCase() + "-" + pieceType.pieceName;
                BufferedImage image = ResourceManager.loadImage(pieceName);
                ChessPiece piece = new ChessPiece(color, position, pieceType, image);
                chessBoard.put(position, piece);
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
     *
     * @param g Graphics class instance, so function is able to graphically draw chess board
     */
    private void drawBoard(Graphics g, Color light, Color dark) {
        for (int rank = 0; rank < BOARD_SIZE; rank++) {
            for (int file = 0; file < BOARD_SIZE; file++) {
                int x = file * SQUARE_SIZE;
                int y = rank * SQUARE_SIZE;
                g.setColor((rank + file) % 2 == 0 ? light : dark);
                g.fillRect(x, y, SQUARE_SIZE, SQUARE_SIZE);
            }
        }
    }

    /**
     * This function draws chess pieces on board from chessBoard array
     * @param g Graphics class instance to draw chess pieces
     */
    public void drawPieces(Graphics g) {
        for (Map.Entry<Position, ChessPiece> entry : chessBoard.entrySet()) {
            Position position = entry.getKey();
            ChessPiece chessPiece = entry.getValue();
            int x = position.getFile() * SQUARE_SIZE;
            int y = (7 - position.getRank()) * SQUARE_SIZE;
            g.drawImage(chessPiece.getImage(), x, y, SQUARE_SIZE, SQUARE_SIZE, this);
        }
    }

}
