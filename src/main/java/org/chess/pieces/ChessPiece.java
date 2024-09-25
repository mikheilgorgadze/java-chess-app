package org.chess.pieces;

import org.chess.utils.PieceColor;
import org.chess.utils.Position;

import java.awt.image.BufferedImage;

public class ChessPiece {
    private final PieceColor pieceColor;
    private Position position;
    private final ChessPieceType pieceType;

    public BufferedImage getImage() {
        return image;
    }

    private BufferedImage image;

    public ChessPiece(PieceColor pieceColor, Position position, ChessPieceType pieceType, BufferedImage image) {
        this.pieceColor = pieceColor;
        this.position = position;
        this.pieceType = pieceType;
        this.image = image;
    }

    public ChessPiece(PieceColor pieceColor, Position position, ChessPieceType pieceType) {
        this.pieceColor = pieceColor;
        this.pieceType = pieceType;
    }


    public ChessPiece(PieceColor pieceColor,  ChessPieceType pieceType) {
        this.pieceColor = pieceColor;
        this.pieceType = pieceType;
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


    @Override
    public String toString() {
        return String.format("%s %s at position %s", getPieceColor().toString().toLowerCase(), getPieceName().toLowerCase(), getPosition());
    }
}
