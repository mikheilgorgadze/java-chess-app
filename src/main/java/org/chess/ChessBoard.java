package org.chess;

import org.chess.pieces.*;
import org.chess.utils.Config;
import org.chess.utils.PieceColor;
import org.chess.utils.ResourceManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class ChessBoard extends JPanel {
    private final HashMap<ChessPiece, BufferedImage> chessPieceToImage = new HashMap<>();

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

    private final String[] initialFen = generateFenArrayFromString(Config.getInitialFEN());

    // Chess board setup
    public ChessBoard() {
        loadChessPieces();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
        drawPiecesFromFEN(initialFen, g);
    }

    private void loadChessPieces() {
        for (ChessPiece piece : chessPieces) {
            String pieceName = piece.getPieceColor().name().toLowerCase() + "-" + piece.getPieceName();
            BufferedImage pieceImage = ResourceManager.loadImage(pieceName);
            chessPieceToImage.put(piece, pieceImage);
        }
    }

    private BufferedImage getChessPieceImage(ChessPiece chessPiece) {
        return chessPieceToImage.get(chessPiece);
    }
    // Draw empty chess board
    private void drawBoard(Graphics g) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                int x = col * SQUARE_SIZE;
                int y = row * SQUARE_SIZE;
                g.setColor((row + col) % 2 == 0 ? Color.WHITE : Color.GRAY);
                g.fillRect(x, y, SQUARE_SIZE, SQUARE_SIZE);
            }
        }
    }

    public void drawPiecesFromFEN(String[] fenArr, Graphics g) {
        if (fenArr == null || fenArr.length == 0) {
            return;
        }
        for (int row = 0; row < BOARD_SIZE; row++) {
            String fenString = fenArr[row];
            for (int col = 0; col < BOARD_SIZE; col++) {
                char c = fenString.toCharArray()[col];
                int x = col * SQUARE_SIZE;
                int y = row * SQUARE_SIZE;
                BufferedImage pieceImage = null;
                if (!Character.isDigit(c)) {
                    ChessPieceType chessPieceType = ChessPieceType.fromFenChar(c);
                    PieceColor pieceColor = Character.isUpperCase(c) ? PieceColor.WHITE : PieceColor.BLACK;
                    for (ChessPiece piece : chessPieces) {
                        if (piece.getPieceColor() == pieceColor && piece.getPieceName()
                                .equalsIgnoreCase(chessPieceType.pieceName)) {
                            pieceImage = getChessPieceImage(piece);
                        }
                    }
                    g.drawImage(pieceImage, x, y, SQUARE_SIZE, SQUARE_SIZE, this);
                }
            }
        }
    }

    private String[] generateFenArrayFromString(String fen) {
        String [] fenArr = fen.split("/");
        for (int i = 0; i < fenArr.length; i++) {
            String fenLine = fenArr[i];
            StringBuilder newFenLine = new StringBuilder();
            for (char c : fenLine.toCharArray()) {
                if (Character.isDigit(c)) {
                    int countOfZeros = Integer.parseInt(Character.toString(c));
                    newFenLine.append("0".repeat(Math.max(0, countOfZeros)));
                } else {
                    newFenLine.append(c);
                }
            }
            fenLine = newFenLine.toString();
            fenArr[i] = fenLine;
        }
        return fenArr;
    }

}
