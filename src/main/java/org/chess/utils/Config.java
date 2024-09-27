package org.chess.utils;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = Config.class.getResourceAsStream("/config/config.properties")){
            properties.load(input);
        } catch (IOException io) {
            throw new RuntimeException("Failed to load config", io);
        }
    }

    public static String getImagesPath() {
        return properties.getProperty("images.path");
    }

    public static int getBoardSize() {
        return Integer.parseInt(properties.getProperty("board.size"));
    }

    public static int getSquareSize() {
        return Integer.parseInt(properties.getProperty("square.size"));
    }

    public static int getWindowHeight() {
        return Integer.parseInt(properties.getProperty("window.height"));
    }
    public static int getWindowWidth() {
        return Integer.parseInt(properties.getProperty("window.width"));
    }

    public static String getWindowTitle() {
        return properties.getProperty("window.title");
    }
    public static String getInitialFEN() {
        return properties.getProperty("initial_fen");
    }

    public static Color getDarkColor() {
        try {
            return Color.decode(properties.getProperty("color.dark"));
        } catch (Exception e) {
            return Color.GRAY;
        }
    }


    public static Color getLightColor() {
        try {
            return Color.decode(properties.getProperty("color.light"));
        } catch (Exception e) {
            return Color.WHITE;
        }
    }


    public static Color getHighlightedColor() {
        try {
            int hex = Integer.parseInt(properties.getProperty("color.highlight"), 16);
            return new Color(hex, true);
        } catch (Exception e) {
            return new Color(60, 255, 50, 100);
        }
    }
}
