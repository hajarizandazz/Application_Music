
package main.java.com.project_app;



import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class NotificationsPanel extends JPanel {
    private List<String[]> allSongs;

    public NotificationsPanel(Member currentUser) {
        setLayout(new BorderLayout());

        // Charger les chansons à partir du fichier CSV
        loadSongsFromCSV();

        // Obtenir les notifications de l'utilisateur actuel
        List<Notification> notifications = currentUser.getNotifications();

        // Modèle de tableau pour les notifications
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Recommandé par");
        columnNames.add("Artiste");
        columnNames.add("Titre");
        columnNames.add("Lien");
        columnNames.add("Paroles");

        NonEditableTableModel tableModel = new NonEditableTableModel(columnNames, 0);

        // Ajouter les notifications au modèle de tableau
        for (Notification notification : notifications) {
            // Récupérer les détails de la chanson depuis le fichier CSV
            String[] songDetails = getSongDetails(notification.getSong().getArtist(), notification.getSong().getTitle());
            if (songDetails != null) {
                tableModel.addRow(new Object[]{
                        notification.getSender(),
                        songDetails[0], // Artiste
                        songDetails[1], // Titre
                        songDetails[2], // Lien
                        songDetails[3]  // Paroles
                });
            } else {
                tableModel.addRow(new Object[]{
                        notification.getSender(),
                        notification.getSong().getArtist(),
                        notification.getSong().getTitle(),
                        "Détails non trouvés",
                        "Détails non trouvés"
                });
            }
        }

        // Créer et ajouter la table avec le modèle de tableau
        JTable notificationsTable = new JTable(tableModel);
        add(new JScrollPane(notificationsTable), BorderLayout.CENTER);
    }

    private void loadSongsFromCSV() {
        // Chemin du fichier CSV contenant les chansons
        String csvFile = "src/main/ressources/spotify_millsongdata.csv";

        // Initialiser la liste pour stocker toutes les chansons
        allSongs = new ArrayList<>();

        // Lecture du fichier CSV et ajout des chansons à la liste
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Ignorer la première ligne (en-têtes des colonnes)
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = parseCSVLine(line, br);
                if (data.length >= 4) {
                    allSongs.add(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] parseCSVLine(String line, BufferedReader br) throws IOException {
        List<String> result = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder currentField = new StringBuilder();

        while (line != null) {
            for (char c : line.toCharArray()) {
                switch (c) {
                    case '"':
                        inQuotes = !inQuotes;
                        break;
                    case ',':
                        if (inQuotes) {
                            currentField.append(c);
                        } else {
                            result.add(currentField.toString());
                            currentField.setLength(0);
                        }
                        break;
                    default:
                        currentField.append(c);
                }
            }

            if (!inQuotes) {
                result.add(currentField.toString());
                break;
            }
            line = br.readLine();
            if (line != null) {
                currentField.append("\n");
            }
        }

        return result.toArray(new String[0]);
    }

    private String[] getSongDetails(String artist, String title) {
        for (String[] song : allSongs) {
            if (song[0].equalsIgnoreCase(artist) && song[1].equalsIgnoreCase(title)) {
                return song;
            }
        }
        return null;
    }
}
