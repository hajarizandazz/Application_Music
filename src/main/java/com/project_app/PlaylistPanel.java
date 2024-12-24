package main.java.com.project_app;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class PlaylistPanel extends JPanel {
    private JTable table;
    private JTextField artistField;
    private JTextField titleField;
    private JTextField playlistNameField;
    private JButton deletePlaylistButton;
    private JButton deleteSongsButton;
    private JButton createPlaylistButton;
    private JList<String> playlistList;
    private DefaultListModel<String> playlistListModel;
    private JScrollPane playlistScrollPane;
    private String currentPlaylist;
    private Member currentUser;

    public PlaylistPanel(Member currentUser) {
        this.currentUser = currentUser;
        setLayout(new BorderLayout());
        artistField = new JTextField(20);
        titleField = new JTextField(20);
        playlistNameField = new JTextField(20);
        createPlaylistButton = new JButton("Créer Playlist");
        deletePlaylistButton = new JButton("Supprimer Playlist");
        deleteSongsButton = new JButton("Supprimer Chansons");

        // Panel pour la création de playlist
        JPanel createPlaylistPanel = new JPanel(new FlowLayout());
        createPlaylistPanel.add(new JLabel("Nom de la playlist: "));
        createPlaylistPanel.add(playlistNameField);
        createPlaylistPanel.add(createPlaylistButton);
        add(createPlaylistPanel, BorderLayout.NORTH);

        // ListModel pour stocker les noms des playlists
        playlistListModel = new DefaultListModel<>();
        playlistList = new JList<>(playlistListModel);
        playlistScrollPane = new JScrollPane(playlistList);
        add(playlistScrollPane, BorderLayout.WEST);

        // Bouton pour ajouter une chanson à la playlist
        JButton enterButton = new JButton("Entrer");
        JPanel entryPanel = new JPanel(new FlowLayout());
        entryPanel.add(new JLabel("Veuillez entrer le nom de l'artiste : "));
        entryPanel.add(artistField);
        entryPanel.add(new JLabel("Veuillez entrer le titre de la musique : "));
        entryPanel.add(titleField);
        entryPanel.add(enterButton);
        entryPanel.add(deletePlaylistButton);
        entryPanel.add(deleteSongsButton );
        entryPanel.add(enterButton);
        add(entryPanel, BorderLayout.CENTER);

        // Tableau pour afficher les chansons de la playlist actuelle
        table = new JTable(new NonEditableTableModel(new Vector<>(), 0));
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.EAST);
        // Ajout d'un ActionListener pour le bouton "Supprimer Playlist"
        deletePlaylistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedPlaylist = playlistList.getSelectedValue();
                if (selectedPlaylist != null) {
                    currentUser.removePlaylist(selectedPlaylist);
                    playlistListModel.removeElement(selectedPlaylist);
                }
            }
        });
        // Ajout d'un ActionListener pour le bouton "Supprimer Chansons"
        deleteSongsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedPlaylist = playlistList.getSelectedValue();
                if (selectedPlaylist != null) {
                    List<String[]> selectedSongs = getSelectedSongs();
                    if (!selectedSongs.isEmpty()) {
                        for (String[] song : selectedSongs) {
                            currentUser.removeFavoriteSongForPlaylist(selectedPlaylist, song);
                        }
                        updateTableData(getSongsForPlaylist(selectedPlaylist));
                    }
                }
            }
        });


        // Ajout d'un ActionListener pour le bouton "Entrer"
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String artist = artistField.getText();
                String title = titleField.getText();
                if (!artist.isEmpty() && !title.isEmpty()) {
                    String csvFile = "src/main/ressources/spotify_millsongdata.csv";
                    List<String[]> filteredSongs = searchSongsInCSV(csvFile, artist, title);
                    if (!filteredSongs.isEmpty()) {
                        addSongToCurrentPlaylist(filteredSongs.get(0)); // Ajouter la chanson à la playlist actuelle
                        saveSongsForPlaylist(currentPlaylist); // Sauvegarder les chansons de la playlist actuelle
                        JOptionPane.showMessageDialog(PlaylistPanel.this, "Chanson ajoutée à la playlist.");
                    } else {
                        JOptionPane.showMessageDialog(PlaylistPanel.this, "Aucune chanson trouvée pour cet artiste et ce titre.");
                    }
                    // Réinitialiser les champs après avoir ajouté la chanson
                    artistField.setText("");
                    titleField.setText("");
                } else {
                    JOptionPane.showMessageDialog(PlaylistPanel.this, "Veuillez remplir tous les champs.");
                }
            }
        });

        // Ajout d'un ActionListener pour le bouton "Créer Playlist"
        createPlaylistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playlistName = playlistNameField.getText();
                if (!playlistName.isEmpty()) {
                    createPlaylist(playlistName);
                    playlistNameField.setText("");
                } else {
                    JOptionPane.showMessageDialog(PlaylistPanel.this, "Veuillez entrer un nom pour la playlist.");
                }
            }
        });

        // Ajout d'un ListSelectionListener pour la liste des playlists
        playlistList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedPlaylist = playlistList.getSelectedValue();
                if (selectedPlaylist != null) {
                    currentPlaylist = selectedPlaylist;
                    updateTableData(getSongsForPlaylist(selectedPlaylist));
                }
            }
        });

        // Charger et afficher les playlists de l'utilisateur
        loadPlaylists();
    }

    private List<String[]> getSelectedSongs() {
        int[] selectedRows = table.getSelectedRows();
        List<String[]> selectedSongs = new ArrayList<>();
        for (int row : selectedRows) {
            selectedSongs.add(((NonEditableTableModel) table.getModel()).getRowData(row));
        }
        return selectedSongs;
    }
    private void addSongToCurrentPlaylist(String[] song) {
        if (currentPlaylist != null) {
            List<String[]> songs = getSongsForPlaylist(currentPlaylist);
            songs.add(song);
            currentUser.addFavoriteSongForPlaylist(currentPlaylist, song);
            updateTableData(songs);
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une playlist.");
        }
    }

    private void createPlaylist(String playlistName) {
        playlistListModel.addElement(playlistName);
        currentUser.addPlaylist(playlistName);
        savePlaylists();
    }

    private void updateTableData(List<String[]> songs) {
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Artiste");
        columnNames.add("Titre");
        columnNames.add("Lien");
        columnNames.add("Paroles");

        NonEditableTableModel model = new NonEditableTableModel(columnNames, 0);

        // Ajout des chansons au modèle de tableau
        for (String[] song : songs) {
            model.addRow(song);
        }

        // Définir le modèle de tableau pour la table
        table.setModel(model);
    }

    private List<String[]> searchSongsInCSV(String csvFile, String artist, String title) {
        List<String[]> filteredSongs = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = parseCSVLine(line, br);
                if (data.length >= 4) {
                    String artiste = data[0].toLowerCase();
                    String titre = data[1].toLowerCase();
                    if (artiste.contains(artist.toLowerCase()) && titre.contains(title.toLowerCase())) {
                        filteredSongs.add(data);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filteredSongs;
    }

    private List<String[]> getSongsForPlaylist(String playlistName) {
        return currentUser.getFavoriteSongsForPlaylist(playlistName);
    }

    private void saveSongsForPlaylist(String playlistName) {
        currentUser.saveFavoriteSongs();
    }

    private void loadPlaylists() {
        List<String> playlists = currentUser.getPlaylists();
        for (String playlist : playlists) {
            playlistListModel.addElement(playlist);
        }
    }

    private void savePlaylists() {
        currentUser.savePlaylists();
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
}
