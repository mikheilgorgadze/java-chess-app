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

    public ChessBoard() {
        loadChessPieces();
    }

    /**
     * This method draws all the chess components including board and pieces
     * @param g the <code>Graphics</code> object
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g, Config.getLightColor(), Config.getDarkColor());
        drawPiecesFromFEN(initialFen, g);
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
     * This function draws chess pieces on board from corresponding FEN notation
     * @param fenArr String array of FEN notation
     * @param g Graphics class instance to draw chess pieces
     */
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


    /**
     *
     * @param fen String of FEN notation
     * @return returns String array representation of FEN notation
     * In standard FEN notation, number of empty squares on board is represented by numeric value.
     * For my app's purposes, this numeric value is "translated" into 0s. For example, if FEN contains 8, returned array
     * will contain 8 zeroes instead.
     */
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
