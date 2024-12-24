
package main.java.com.project_app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FriendPanel extends JPanel {
    private DefaultListModel<String> friendListModel;
    private JList<String> friendList;
    private JTextField addFriendField;
    private JTextField removeFriendField;
    private Member currentUser;

    public FriendPanel(Member currentUser) {
        this.currentUser = currentUser;
        setLayout(new BorderLayout());

        // Panel principal avec GridBagLayout pour centrer les composants
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        add(mainPanel, BorderLayout.CENTER);

        // Section pour ajouter des amis
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        JLabel addFriendTitle = new JLabel("Ajout Amis");
        addFriendTitle.setFont(new Font("Arial", Font.BOLD, 16));
        addFriendTitle.setForeground(new Color(109, 186, 231)); // Couleur du titre
        mainPanel.add(addFriendTitle, gbc);

        gbc.gridy++;
        JLabel addFriendLabel = new JLabel("Veuillez entrer le nom et prénom de votre ami :");
        mainPanel.add(addFriendLabel, gbc);

        gbc.gridy++;
        addFriendField = new JTextField(10); // Taille plus petite
        mainPanel.add(addFriendField, gbc);

        gbc.gridy++;
        JButton addButton = new JButton("Ajouter");
        addButton.setBackground(new Color(204, 192, 119)); // Couleur du bouton
        addButton.setForeground(Color.WHITE); // Couleur du texte du bouton
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addFriend();
            }
        });
        mainPanel.add(addButton, gbc);

        // Section pour retirer des amis
        gbc.gridy++;
        JLabel removeFriendTitle = new JLabel("Retirer Amis");
        removeFriendTitle.setFont(new Font("Arial", Font.BOLD, 16));
        removeFriendTitle.setForeground(new Color(109, 186, 231)); // Couleur du titre
        mainPanel.add(removeFriendTitle, gbc);

        gbc.gridy++;
        JLabel removeFriendLabel = new JLabel("Veuillez entrer le nom et prénom de l'ami à retirer :");
        mainPanel.add(removeFriendLabel, gbc);

        gbc.gridy++;
        removeFriendField = new JTextField(10); // Taille plus petite
        mainPanel.add(removeFriendField, gbc);

        gbc.gridy++;
        JButton removeButton = new JButton("Supprimer");
        removeButton.setBackground(new Color(204, 192, 119)); // Couleur du bouton
        removeButton.setForeground(Color.WHITE); // Couleur du texte du bouton
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeFriend();
            }
        });
        mainPanel.add(removeButton, gbc);

        // Liste des amis
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setBorder(new EmptyBorder(0, 20, 0, 20)); // Ajouter de l'espace autour de la liste
        listPanel.setPreferredSize(new Dimension(200, 300)); // Définir une taille préférée pour la liste
        JLabel listTitle = new JLabel("Liste Amis :");
        listTitle.setFont(new Font("Arial", Font.BOLD, 16));
        listTitle.setForeground(new Color(109, 186, 231)); // Couleur du titre
        listPanel.add(listTitle, BorderLayout.NORTH);
        friendListModel = new DefaultListModel<>();
        friendList = new JList<>(friendListModel);
        friendList.setBorder(new LineBorder(Color.BLACK)); // Ajouter une bordure noire autour de la liste
        JScrollPane friendScrollPane = new JScrollPane(friendList);
        listPanel.add(friendScrollPane, BorderLayout.CENTER);
        add(listPanel, BorderLayout.EAST);

        loadFriendsList();
    }

    private void addFriend() {
        String friendName = addFriendField.getText().trim();
        if (!friendName.isEmpty()) {
            // Vérifier si le nom de l'ami est inscrit parmi les membres
            if (!MemberManager.isUsernameAvailable(friendName)) {
                // Ajouter l'ami à la liste des amis
                if (currentUser.addFriend(friendName)) {
                    friendListModel.addElement(friendName);
                    addFriendField.setText("");
                } else {
                    // Afficher un message d'erreur si l'ami est déjà ajouté
                    JOptionPane.showMessageDialog(this, "Cet ami est déjà dans votre liste.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Afficher un message d'erreur si l'ami n'est pas inscrit
                JOptionPane.showMessageDialog(this, "Impossible d'ajouter cet ami car il n'est pas inscrit.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void removeFriend() {
        String friendName = removeFriendField.getText().trim();
        if (!friendName.isEmpty()) {
            currentUser.removeFriend(friendName);
            friendListModel.removeElement(friendName);
            removeFriendField.setText("");
        }
    }

    private void loadFriendsList() {
        for (String friend : currentUser.getFriends()) {
            friendListModel.addElement(friend);
        }
    }
}