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
    public static ChessPiece selectedPiece;
    public static Position selectedPosition;

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
        drawBoard(g);
        drawPieces(g);
        if (chessBoard.getSelectedPosition() != null) {
            highlightSquare(g, chessBoard.getSelectedPosition());
        }
    }
    private class ChessBoardMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            int squareSize = chessBoard.getSquareSize();
            int file = e.getX() / squareSize;
            int rank = 7 - (e.getY() / squareSize);
            Position pos = Position.create(rank, file);
            chessBoard.selectPieceAt(pos);
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            int squareSize = chessBoard.getSquareSize();
            int file = e.getX() / squareSize;
            int rank = 7 - (e.getY() / squareSize);
            Position newPosition = Position.create(rank, file);
            chessBoard.moveSelectedPieceTo(newPosition);
            repaint();
        }
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

    /**
     *
     * @param g Graphics class instance, so function is able to graphically draw chess board
     */
    public void drawBoard(Graphics g) {
        int boardSize = Config.getBoardSize();
        int squareSize = Config.getSquareSize();
        Color lightSquareColor = Config.getLightColor();
        Color darkSquareColor = Config.getDarkColor();
        for (int rank = 0; rank < boardSize; rank++) {
            for (int file = 0; file < boardSize; file++) {
                int x = file * squareSize;
                int y = rank * squareSize;
                g.setColor((rank + file) % 2 == 0 ? lightSquareColor : darkSquareColor);
                g.fillRect(x, y, squareSize, squareSize);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, squareSize, squareSize);
                if (rank == boardSize - 1) {
                    g.drawString(Character.toString('a' + file), x + squareSize - 10, y + squareSize - 5);
                }
                if (file == 0) {
                    g.drawString(String.valueOf(boardSize - rank), x + 5, y + 15);
                }
            }
        }
    }

}
