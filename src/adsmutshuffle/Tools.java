/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adsmutshuffle;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 *
 * @author Adsmut
 */
public class Tools {
    public static ImageIcon scaleImage(File imageFile, int width, int height){
        BufferedImage bufferedImage=null;
        try{
            Image image = ImageIO.read(imageFile);
            Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            bufferedImage.getGraphics().drawImage(scaledImage, 0, 0, null);
        }catch(IOException e){
            e.printStackTrace();
        }
        return new ImageIcon(bufferedImage);
    }
    
    public static File removeBackground(String pathToFile, Color newColor, int alpha){
        File output=null;
        try {
            // Load the image
            File input = new File(pathToFile);
            BufferedImage image = ImageIO.read(input);

            // Get the image dimensions
            int width = image.getWidth();
            int height = image.getHeight();

            // Create a new image with the same dimensions
            BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

            // Loop through each pixel
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // Get the color of the current pixel
                    int rgb = image.getRGB(x, y);
                    Color color = new Color(rgb);

                    // Check if the color is white or whitish
                    int threshold = 230;
                    if (color.getRed() > threshold && color.getGreen() > threshold && color.getBlue() > threshold) {
                        // Set the alpha value to 0
                        color = new Color(newColor.getRed(), newColor.getGreen(), newColor.getBlue(), alpha);
                        newImage.setRGB(x, y, color.getRGB());
                    } else {
                        // Set the pixel value of the new image to the same value as the original image
                        newImage.setRGB(x, y, rgb);
                    }
                }
            }
            // Save the modified image
            output = new File("output.jpg");
            ImageIO.write(newImage, "jpg", output);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return output;
    }
}
