package main.java.com.project_app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ProfilePanel extends JPanel {
    private JTextField usernameField;
    private JTextField fullNameField;
    private JTextField emailField;
    private Member currentUser;

    public ProfilePanel(Member user) {
        this.currentUser = user;
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Username
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Username:"), gbc);
        usernameField = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(usernameField, gbc);

        // Full Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Nom Complet:"), gbc);
        fullNameField = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(fullNameField, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Email:"), gbc);
        emailField = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);

        // Load current user details
        loadUserDetails();

        // Save Button
        JButton saveButton = new JButton("Enregistrer");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveProfile();
            }
        });

        // Adding components to the panel
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(saveButton, gbc);

        add(formPanel, BorderLayout.CENTER);
    }

    private void loadUserDetails() {
        if (currentUser != null) {
            usernameField.setText(currentUser.getUsername());
            fullNameField.setText(currentUser.getFullName());
            emailField.setText(currentUser.getEmail());
        }
    }

    private void saveProfile() {
        if (currentUser != null) {
            String newUsername = usernameField.getText().trim();
            String newFullName = fullNameField.getText().trim();
            String newEmail = emailField.getText().trim();

            if (!newUsername.isEmpty() && !newFullName.isEmpty() && !newEmail.isEmpty()) {
                // Check if the username has changed
                if (!currentUser.getUsername().equals(newUsername)) {
                    // Rename the friends list file if it exists
                    File oldFile = new File(currentUser.getFriendsFilename());
                    currentUser.username = newUsername;
                    File newFile = new File(currentUser.getFriendsFilename());
                    if (oldFile.exists()) {
                        oldFile.renameTo(newFile);
                    }
                }

                // Update the user's details
                currentUser.setFullName(newFullName);
                currentUser.setEmail(newEmail);

                // Save the updated member details (assume MemberManager.updateMember exists)
                MemberManager.updateMember(currentUser);

                JOptionPane.showMessageDialog(this, "Profil mis à jour avec succès !");
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}