
package main.java.com.project_app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {
    private MemberManager memberManager;
    private JFrame parentFrame;

    public LoginPanel(MemberManager memberManager, JFrame parentFrame) {
        this.memberManager = memberManager;
        this.parentFrame = parentFrame;

        setLayout(new GridBagLayout());
        setBackground(new Color(245, 245, 220));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel usernameLabel = new JLabel("Nom d'utilisateur/Pseudo :");
        usernameLabel.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(usernameLabel, gbc);

        JTextField usernameField = new JTextField(15);
        gbc.gridx = 1;
        add(usernameField, gbc);

        JLabel mdpLabel = new JLabel("Mot de passe :");
        mdpLabel.setForeground(Color.BLACK); // Texte noir
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(mdpLabel, gbc);

        JPasswordField mdpField = new JPasswordField(15);
        gbc.gridx = 1;
        add(mdpField, gbc);

        // Ajouter un panneau pour les boutons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1, 10, 10));
        buttonPanel.setBackground(new Color(245, 245, 220));

        JButton loginButton = new JButton("Connexion");
        loginButton.setBackground(Color.BLACK);
        loginButton.setForeground(Color.WHITE);
        buttonPanel.add(loginButton);

        // Ajouter un bouton de retour
        JButton backButton = new JButton("← Retour");
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        buttonPanel.add(backButton);

        // Ajouter le panneau de boutons au layout principal
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                Member currentUser = memberManager.getMember(username);
                if (currentUser != null) {
                    JOptionPane.showMessageDialog(LoginPanel.this, "Connexion réussie !");
                    showHomeScreen(currentUser);
                } else {
                    JOptionPane.showMessageDialog(LoginPanel.this, "Pseudo introuvable !");
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMainScreen();
            }
        });
    }
    /**
     * Affiche l'écran d'accueil après une connexion réussie.
     *
     * @param currentUser L'utilisateur actuellement connecté.
     */
    private void showHomeScreen(Member currentUser) {
        parentFrame.getContentPane().removeAll();
        parentFrame.getContentPane().add(new HomePanel(memberManager, parentFrame, currentUser));
        parentFrame.revalidate();
        parentFrame.repaint();
    }
    /**
     * Affiche l'écran principal.
     */
    private void showMainScreen() {
        parentFrame.getContentPane().removeAll();
        parentFrame.getContentPane().add(new MainPanel(parentFrame));
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}
