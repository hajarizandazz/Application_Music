
package main.java.com.project_app;

import javax.swing.*;

public class MainFrame extends JFrame { // Corrected class name
    public MainFrame() {
        setTitle("Your Application");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MainPanel mainPanel = new MainPanel(this);
        add(mainPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}