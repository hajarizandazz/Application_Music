
package main.java.com.project_app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.*;

public class MainPanel extends BackgroundPanel {
    private MemberManager memberManager;

    public MainPanel(JFrame parentFrame) {
        super("/main/ressources/beige.png"); // Appel du constructeur de la classe parente avec le chemin de l'image

        // Définir la couleur de fond comme transparente
        setBackground(new Color(0, 0, 0, 0));

        memberManager = new MemberManager();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Bienvenue sur votre application musicale !");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(titleLabel, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(2, 1, 10, 10));

        JButton loginButton = new JButton("Connexion");
        loginButton.setPreferredSize(new Dimension(150, 40));

        loginButton.setBackground(Color.BLACK);
        loginButton.setForeground(Color.WHITE);
        Border roundedBorder = new LineBorder(Color.BLACK, 2, true);
        Border emptyBorder = BorderFactory.createEmptyBorder(10, 20, 10, 20);
        Border compoundBorder = BorderFactory.createCompoundBorder(roundedBorder, emptyBorder);

        loginButton.setBorder(compoundBorder);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLoginScreen(parentFrame);
            }
        });
        buttonPanel.add(loginButton);

        JButton registerButton = new JButton("Inscription");
        registerButton.setBackground(Color.BLACK);
        registerButton.setForeground(Color.WHITE);
        registerButton.setBorder(compoundBorder);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRegisterScreen(parentFrame);
            }
        });
        buttonPanel.add(registerButton);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(buttonPanel, gbc);
    }

    private void showLoginScreen(JFrame parentFrame) {
        parentFrame.getContentPane().removeAll();
        parentFrame.getContentPane().add(new LoginPanel(memberManager, parentFrame));
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    private void showRegisterScreen(JFrame parentFrame) {
        new RegistrationForm(parentFrame, parentFrame);
    }

    // Redimensionner l'image pour qu'elle remplisse toute la taille du panel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
