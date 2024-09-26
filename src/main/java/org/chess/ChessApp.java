package org.chess;

import org.chess.utils.Config;

import javax.swing.*;

public class ChessApp extends JFrame {
    public ChessApp() {
        setTitle(Config.getWindowTitle());
        setSize(Config.getWindowWidth(), Config.getWindowHeight());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new ChessBoardUI());
    }
}
