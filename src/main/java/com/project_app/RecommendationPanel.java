package main.java.com.project_app;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecommendationPanel extends JPanel {
    private JTextField receiverField;
    private JTextField artistField;
    private JTextField titleField;
    private JButton sendButton;
    private Member currentUser;
    private List<String[]> allSongs;

    public RecommendationPanel(Member currentUser) {
        this.currentUser = currentUser;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Receiver label and field
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Nom d'utilisateur du destinataire : "), gbc);

        gbc.gridx = 1;
        receiverField = new JTextField(20);
        add(receiverField, gbc);

        // Artist label and field
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Artiste : "), gbc);

        gbc.gridx = 1;
        artistField = new JTextField(20);
        add(artistField, gbc);

        // Title label and field
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Titre : "), gbc);

        gbc.gridx = 1;
        titleField = new JTextField(20);
        add(titleField, gbc);

        // Send button
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        sendButton = new JButton("Envoyer");
        add(sendButton, gbc);

        // Load songs from CSV
        loadSongsFromCSV();

        sendButton.addActionListener(e -> sendRecommendation());
    }

    private void loadSongsFromCSV() {
        // Chemin du fichier CSV contenant les chansons
        String csvFile = "src/main/ressources/spotify_millsongdata.csv";

        // Initialiser la liste pour stocker toutes les chansons
        allSongs = new ArrayList<>();

        // Lecture du fichier CSV et ajout des chansons à la liste
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1); // Garder les colonnes vides
                if (data.length >= 4) {
                    allSongs.add(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isSongInCSV(String artist, String title) {
        for (String[] song : allSongs) {
            if (song[0].equalsIgnoreCase(artist) && song[1].equalsIgnoreCase(title)) {
                return true;
            }
        }
        return false;
    }

    private void sendRecommendation() {
        String receiverUsername = receiverField.getText().trim();
        String artist = artistField.getText().trim();
        String title = titleField.getText().trim();

        if (!receiverUsername.isEmpty() && !artist.isEmpty() && !title.isEmpty()) {
            Member receiver = MemberManager.getMember(receiverUsername);
            if (receiver != null) {
                if (isSongInCSV(artist, title)) {
                    Song song = new Song(artist, title, "", "");
                    MemberManager.sendSongRecommendation(currentUser, receiver, song);
                    JOptionPane.showMessageDialog(this, "Recommandation envoyée !");
                } else {
                    JOptionPane.showMessageDialog(this, "La chanson spécifiée n'est pas disponible !");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Utilisateur non trouvé !");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.");
        }
    }
}
