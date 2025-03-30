package org.chess.pieces;

import lombok.Getter;
import lombok.Setter;
import org.chess.utils.PieceColor;
import org.chess.utils.Position;

import java.awt.image.BufferedImage;

@Getter
public abstract class AbstractChessPiece implements ChessPiece{
    protected final PieceColor color;
    @Setter
    protected Position position;
    protected final BufferedImage image;
    protected final String name;
    @Setter
    protected boolean isMoved;

    public AbstractChessPiece(PieceColor color, Position position, BufferedImage image, String name) {
        this.color = color;
        this.position = position;
        this.image = image;
        this.name = name;
    }

    @Override
    public boolean isFriendlyPiece(ChessPiece piece) {
        return this.color == piece.getColor();

    }

    @Override
    public boolean isMoved() {
        return isMoved;
    }
}
