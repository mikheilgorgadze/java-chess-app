package org.chess.exceptions;

public enum Exception {
    COlUMN_OUT_OF_BOUNDS("Illegal col"),
    ROW_OUT_OF_BOUNDS("Illegal row"),
    POSITION_OUT_OF_BOUNDS("Illegal position");
    public final String message;
    private Exception(String message) {
        this.message = message;
    };
}
