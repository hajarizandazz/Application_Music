package main.java.com.project_app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * La classe RegistrationForm représente un formulaire d'inscription permettant aux utilisateurs de créer un nouveau compte.
 */
public class RegistrationForm extends JDialog {
    private JTextField tfName;
    private JTextField tfFullName;
    private JTextField tfEmail;
    private JTextField tfAge;
    private JPasswordField pfPassword;
    private JPasswordField pfConfirmPassword;
    private JButton btnRegister;
    private JButton btnCancel;
    private JPanel registerPanel;
    private JFrame parentFrame;

    /**
     * Constructeur de la classe RegistrationForm.
     *
     * @param parent       Le cadre parent pour la boîte de dialogue.
     * @param parentFrame  Le cadre parent pour des références supplémentaires.
     */
    public RegistrationForm(Frame parent, JFrame parentFrame) {
        super(parent, "Créer un nouveau compte", true);
        this.parentFrame = parentFrame;

        setLayout(new BorderLayout());

        registerPanel = new JPanel();
        registerPanel.setLayout(new GridBagLayout());
        registerPanel.setBackground(new Color(245, 245, 220));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        tfName = new JTextField(15);
        tfFullName = new JTextField(15);
        tfEmail = new JTextField(15);
        tfAge = new JTextField(15);
        pfPassword = new JPasswordField(15);
        pfConfirmPassword = new JPasswordField(15);
        btnRegister = new JButton("S'inscrire");
        btnCancel = new JButton("Annuler");

        addField(registerPanel, gbc, "Nom d'utilisateur :", tfName, 0);
        addField(registerPanel, gbc, "Nom complet :", tfFullName, 1);
        addField(registerPanel, gbc, "Email :", tfEmail, 2);
        addField(registerPanel, gbc, "Âge :", tfAge, 3);
        addField(registerPanel, gbc, "Mot de passe :", pfPassword, 4);
        addField(registerPanel, gbc, "Confirmer le mot de passe :", pfConfirmPassword, 5);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(btnRegister);
        buttonPanel.add(btnCancel);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        btnCancel.setBackground(Color.BLACK);
        btnCancel.setForeground(Color.WHITE);
        btnRegister.setBackground(Color.BLACK);
        btnRegister.setForeground(Color.WHITE);
        registerPanel.add(buttonPanel, gbc);

        add(registerPanel, BorderLayout.CENTER);

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setSize(450, 500);
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    /**
     * Ajoute un champ au panneau de formulaire.
     *
     * @param panel     Le panneau où le champ sera ajouté.
     * @param gbc       Les contraintes de disposition pour le champ.
     * @param labelText Le texte de l'étiquette du champ.
     * @param field     Le composant de champ à ajouter.
     * @param yPos      La position verticale du champ dans la grille.
     */
    private void addField(JPanel panel, GridBagConstraints gbc, String labelText, JComponent field, int yPos) {
        JLabel label = new JLabel(labelText);
        label.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = yPos;
        gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(field, gbc);
    }

    /**
     * Enregistre un nouvel utilisateur en vérifiant les informations et en ajoutant l'utilisateur au gestionnaire de membres.
     */
    private void registerUser() {
        String userName = tfName.getText();
        String fullName = tfFullName.getText();
        String email = tfEmail.getText();
        String ageText = tfAge.getText();
        String password = String.valueOf(pfPassword.getPassword());
        String confirmPassword = String.valueOf(pfConfirmPassword.getPassword());

        if (!MemberManager.isUsernameAvailable(userName)) {
            JOptionPane.showMessageDialog(this, "Nom d'utilisateur déjà pris", "Réessayer", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (userName.isEmpty() || fullName.isEmpty() || email.isEmpty() || ageText.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs", "Réessayer", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Le mot de passe de confirmation ne correspond pas", "Réessayer", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "L'âge doit être un nombre entier valide", "Réessayer", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (age < 18) {
            JOptionPane.showMessageDialog(this, "Vous ne pouvez pas vous inscrire. Vous n'êtes pas adulte.", "Réessayer", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Member member = new AdultMember(userName, fullName, email);
        MemberManager.addMember(member);

        JOptionPane.showMessageDialog(this, "Inscription réussie !");
        dispose();
    }
}
