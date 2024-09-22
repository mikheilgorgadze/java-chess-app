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
    public static String[] generateFenArrayFromString(String fen) {
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


    public static boolean isCorrectFenSymbol(char c) {
        return allowedFenSymbols.contains(Character.toString(c));
    }
}
