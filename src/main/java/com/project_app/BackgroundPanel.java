
package main.java.com.project_app;

import javax.swing.*;
import java.awt.*;
/**
 * The `BackgroundPanel` class is a custom JPanel that allows setting a background image.
 * This panel can be used to display an image as the background of any GUI component that uses a JPanel.
 */
public class BackgroundPanel extends JPanel {
    protected Image backgroundImage;
    /**
     * Constructs a `BackgroundPanel` with the specified background image.
     *
     * @param filePath the path to the background image file.
     */
    public BackgroundPanel(String filePath) {
        setBackgroundImage(filePath);
    }
    /**
     * Sets the background image for this panel.
     *
     * @param filePath the path to the background image file.
     */
    public void setBackgroundImage(String filePath) {
        try {
            backgroundImage = new ImageIcon(getClass().getResource(filePath)).getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Overrides the paintComponent method of JPanel to draw the background image.
     *
     * @param g the Graphics object used for painting.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
