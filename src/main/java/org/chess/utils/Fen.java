package org.chess.utils;

public class Fen {

    private static final String allowedFenSymbols = "rnbqkpPRNBQK";

    /**
     *
     * @param fen String of FEN notation
     * @return returns String array representation of FEN notation
     * In standard FEN notation, number of empty squares on board is represented by numeric value.
     * For my app's purposes, this numeric value is "translated" into 0s. For example, if FEN contains 8, returned array
     * will contain 8 zeroes instead.
     */
    public static String generateFullFenString(String fen) {
        StringBuilder fenFullString = new StringBuilder();
        for (int i = 0; i < fen.length(); i++) {
            char c = fen.charAt(i);
            if (Character.isDigit(c)) {
                fenFullString.append("0"
                        .repeat(Math.max(0, Integer.parseInt(Character.toString(c)))));
            } else if (isCorrectFenSymbol(c)) {
                fenFullString.append(c);
            }
        }

        return fenFullString.toString();
    }


    public static boolean isCorrectFenSymbol(char c) {
        return allowedFenSymbols.contains(Character.toString(c));
    }
}
