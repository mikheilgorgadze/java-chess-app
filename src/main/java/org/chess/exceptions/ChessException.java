package org.chess.exceptions;

public enum ChessException {
    COlUMN_OUT_OF_BOUNDS("Illegal col"),
    ROW_OUT_OF_BOUNDS("Illegal row"),
    POSITION_OUT_OF_BOUNDS("Illegal position"),
    INVALID_PIECE_TYPE("Invalid Piece Type"),
    NOT_IMPLEMENTED("Not yet implemented"),
    ILLEGAL_FEN_SYMBOL("Illegal fen symbol provided");
    public final String message;
    ChessException(String message) {
        this.message = message;
    };
}
