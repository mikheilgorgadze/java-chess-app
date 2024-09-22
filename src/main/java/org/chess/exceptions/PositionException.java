package org.chess.exceptions;

public class PositionException extends RuntimeException {
    public PositionException(ChessException exception) {
        super(exception.message);
    }
}
