
package main.java.com.project_app;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class SongPanel extends JPanel {
    private JTable table;
    private JTextField searchField;
    private List<String[]> allSongs;

    public SongPanel() {
        setLayout(new BorderLayout());

        // Champ de recherche
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Rechercher");
        JButton hearButton = new JButton("Entendre chanson"); // Bouton pour entendre la chanson
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Recherche : "));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(hearButton); // Ajout du bouton pour entendre la chanson
        add(searchPanel, BorderLayout.NORTH);

        // Tableau pour afficher les chansons
        table = new JTable(new NonEditableTableModel(new Vector<>(), 0));
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Charger et afficher les chansons à partir du fichier CSV
        loadAndDisplaySongsFromCSV();

        // Ajouter un écouteur de clic pour le bouton de recherche
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = searchField.getText().trim();
                filterSongs(keyword);
            }
        });


        hearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String lyrics = (String) table.getValueAt(selectedRow, 3);
                    showLyricsDialog(lyrics);
                } else {
                    JOptionPane.showMessageDialog(SongPanel.this, "Veuillez sélectionner une chanson.");
                }
            }
        });
    }

    private void showLyricsDialog(String lyrics) {
        JDialog dialog = new JDialog((Frame) null, "Paroles de la chanson", true);
        JTextArea textArea = new JTextArea(lyrics);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void loadAndDisplaySongsFromCSV() {
        String csvFile = "src/main/ressources/spotify_millsongdata.csv";
        allSongs = new ArrayList<>();
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

        updateTableData(allSongs);
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

    private void updateTableData(List<String[]> songs) {
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Artiste");
        columnNames.add("Titre");
        columnNames.add("Lien");
        columnNames.add("Paroles");

        NonEditableTableModel model = new NonEditableTableModel(columnNames, 0);

        for (String[] song : songs) {
            model.addRow(song);
        }

        table.setModel(model);
    }

    private void filterSongs(String keyword) {
        List<String[]> filteredSongs = new ArrayList<>();
        for (String[] song : allSongs) {
            String artist = song[0].toLowerCase();
            String title = song[1].toLowerCase();
            String link = song[2].toLowerCase();
            String lyrics = song[3].toLowerCase();
            if (artist.contains(keyword.toLowerCase()) ||
                    title.contains(keyword.toLowerCase()) ||
                    link.contains(keyword.toLowerCase()) ||
                    lyrics.contains(keyword.toLowerCase())) {
                filteredSongs.add(song);
            }
        }
        updateTableData(filteredSongs);
    }

    // NonEditableTableModel pour rendre les cellules non modifiables

}
