package org.chess.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ResourceManager {
    public static BufferedImage loadImage(String name) {
        try {
            return ImageIO.read(new File(Config.getImagesPath() + name + ".png"));
        } catch (IOException io) {
            throw new RuntimeException("Failed to load image: " + name, io);
        }
    }
}
