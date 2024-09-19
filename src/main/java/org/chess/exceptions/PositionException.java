package org.chess.exceptions;

public class PositionException extends RuntimeException {
    public PositionException(Exception exception) {
        super(exception.message);
    }
}
