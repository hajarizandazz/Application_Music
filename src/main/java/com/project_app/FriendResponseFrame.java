package main.java.com.project_app;


import javax.swing.*;
import java.awt.*;

public class FriendResponseFrame extends JFrame {
    private JTextField titleField;
    private JTextField artistField;
    private BlindTest blindTest;
    private BlindTestPanel blindTestPanel;

    public FriendResponseFrame(NotificationBlindTest notificationBlindTest) {
        this.blindTestPanel = blindTestPanel;
        this.blindTest = notificationBlindTest.getBlindTest();
        initComponents();
        setTitle("Friend Response");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void initComponents() {
        setLayout(new GridLayout(3, 2));

        JLabel titleLabel = new JLabel("Song Title: ");
        titleField = new JTextField();
        JLabel artistLabel = new JLabel("Artist Name: ");
        artistField = new JTextField();
        JButton submitButton = new JButton("Submit");

        add(titleLabel);
        add(titleField);
        add(artistLabel);
        add(artistField);
        add(new JLabel());
        add(submitButton);

        submitButton.addActionListener(e -> {
            String title = titleField.getText().trim();
            String artist = artistField.getText().trim();

            boolean isCorrect = blindTestPanel.verifyResponse(title, artist, blindTest);

            if (isCorrect) {
                JOptionPane.showMessageDialog(this, "Correct! You've matched the song and artist.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            dispose();
        });
    }
}