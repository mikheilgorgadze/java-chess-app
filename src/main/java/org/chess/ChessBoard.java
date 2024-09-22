package org.chess;

import org.chess.exceptions.ChessException;
import org.chess.pieces.*;
import org.chess.utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class ChessBoard extends JPanel {
    private final HashMap<ChessPiece, BufferedImage> chessPieceToImage = new HashMap<>();

    private final ArrayList<ChessPiece> chessBoard = new ArrayList<>();

    private final ChessPiece[] chessPieces = {
            new Pawn(PieceColor.WHITE), new Pawn(PieceColor.BLACK),
            new Bishop(PieceColor.WHITE), new Bishop(PieceColor.BLACK),
            new Knight(PieceColor.WHITE), new Knight(PieceColor.BLACK),
            new Rook(PieceColor.WHITE), new Rook(PieceColor.BLACK),
            new Queen(PieceColor.WHITE), new Queen(PieceColor.BLACK),
            new King(PieceColor.WHITE), new King(PieceColor.BLACK),
    };
    private static final int BOARD_SIZE = Config.getBoardSize();
    private static final int SQUARE_SIZE = Config.getSquareSize();

    public ChessBoard() {
        loadChessPieces();
        addMouseListener(new ChessBoardMouseListener());
        fillChessBoardArrayFromFEN(Config.getInitialFEN());
    }

    private class ChessBoardMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            int col = e.getX() / BOARD_SIZE;
            int row = e.getY() / BOARD_SIZE;
        }
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

    }


    private void fillChessBoardArrayFromFEN(String fen) {
        if (fen == null || fen.isEmpty() ) {
            throw new IllegalArgumentException("Argument should not be null");
        }
        int pieceCount = 0;
        for (char c : fen.toCharArray()) {
            if (Fen.isCorrectFenSymbol(c)){
                int row = pieceCount / BOARD_SIZE;
                int col = pieceCount % BOARD_SIZE;
                for (ChessPiece chessPiece : chessPieces) {
                    PieceColor pieceColor = Character.isUpperCase(c) ? PieceColor.WHITE : PieceColor.BLACK;
                    if (chessPiece.getPieceName().equals(ChessPieceType.fromFenChar(c).pieceName) &&
                    chessPiece.getPieceColor().equals(pieceColor)
                    ) {
                        chessPiece.setPosition(new Position(row + 1, col + 1));
                        chessBoard.add(chessPiece);
                        pieceCount ++;
                    }
                }
            } else if (Character.isDigit(c)) {
                for (int i = 0; i < Integer.parseInt(Character.toString(c)); i++) {
                    chessBoard.add(null);
                    pieceCount ++;
                }
            } else if (c != '/') {
                throw new RuntimeException(ChessException.ILLEGAL_FEN_SYMBOL.message);
            }
        }
    }

    /**
     * Initializes chessPieceToImage HashMap. Each piece will have it's corresponding image
     */
    private void loadChessPieces() {
        for (ChessPiece piece : chessPieces) {
            String pieceName = piece.getPieceColor().name().toLowerCase() + "-" + piece.getPieceName();
            BufferedImage pieceImage = ResourceManager.loadImage(pieceName);
            chessPieceToImage.put(piece, pieceImage);
        }
    }

    /**
     *
     * @param chessPiece ChessPiece object instance
     * @return ChessPiece's corresponding image is returned from chessPieceToImage HashMap
     */
    private BufferedImage getChessPieceImage(ChessPiece chessPiece) {
        return chessPieceToImage.get(chessPiece);
    }

    /**
     *
     * @param g Graphics class instance, so function is able to graphically draw chess board
     */
    private void drawBoard(Graphics g, Color light, Color dark) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                int x = col * SQUARE_SIZE;
                int y = row * SQUARE_SIZE;
                g.setColor((row + col) % 2 == 0 ? light : dark);
                g.fillRect(x, y, SQUARE_SIZE, SQUARE_SIZE);
            }
        }
    }

    /**
     * This function draws chess pieces on board from chessBoard array
     * @param g Graphics class instance to draw chess pieces
     */
    public void drawPieces(Graphics g) {
        int pieceCount = 0;
        for (ChessPiece piece : chessBoard) {
            int row = pieceCount / BOARD_SIZE;
            int col = pieceCount % BOARD_SIZE;
            BufferedImage image = getChessPieceImage(piece);
            int x = col * SQUARE_SIZE;
            int y = row * SQUARE_SIZE;
            if (piece != null) {
                g.drawImage(image, x, y, SQUARE_SIZE, SQUARE_SIZE, this);
            }
            pieceCount ++;
        }
    }
}
