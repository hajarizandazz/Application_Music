package main.java.com.project_app;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 * The `BlindTestPanel` class represents a JPanel that allows users to create and manage blind tests.
 * It provides a user interface for creating blind tests, displaying them in a list, and inviting friends to participate.
 */
public class BlindTestPanel extends JPanel {
    private List<BlindTest> blindTests;
    private DefaultListModel<String> blindTestListModel;
    private static final String FILE_PATH = "blindTests.dat";
    private Member currentUser; // Assuming you have a Member class

    /**
     * Constructs a `BlindTestPanel` with the specified current user.
     *
     * @param currentUser the current user creating or managing blind tests.
     */
    public BlindTestPanel(Member currentUser) {
        this.currentUser = currentUser;
        blindTests = new ArrayList<>();
        blindTestListModel = new DefaultListModel<>();
        initComponents();
        loadBlindTests();
    }
    /**
     * Initializes the components of the blind test panel.
     * It sets up the user interface for creating blind tests and displaying them in a list.
     */
    private void initComponents() {
        setLayout(new BorderLayout());

        // Interface utilisateur pour la création de blind test
        JPanel createPanel = new JPanel(new GridLayout(6, 2));
        JTextField lyricsField = new JTextField();
        JTextField titleField = new JTextField();
        JTextField artistField = new JTextField();
        JComboBox<String> modeComboBox = new JComboBox<>(new String[]{"Trouver le titre exact", "Trouver l'artiste", "Trouver titre et artiste"});
        JButton createButton = new JButton("Créer Blind Test");
        JButton inviteButton = new JButton("Invite a Friend");
        createPanel.add(new JLabel("Paroles : "));
        createPanel.add(lyricsField);
        createPanel.add(new JLabel("Titre : "));
        createPanel.add(titleField);
        createPanel.add(new JLabel("Artiste : "));
        createPanel.add(artistField);
        createPanel.add(new JLabel("Mode de jeu : "));
        createPanel.add(modeComboBox);
        createPanel.add(createButton);
        createPanel.add(inviteButton);

        // JList pour afficher les blind tests créés
        JList<String> blindTestList = new JList<>(blindTestListModel);
        JScrollPane scrollPane = new JScrollPane(blindTestList);

        // Ajout des composants au panel principal
        add(createPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // ActionListener pour le bouton de création de blind test
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String lyrics = lyricsField.getText();
                String title = titleField.getText();
                String artist = artistField.getText();
                GameMode mode = GameMode.values()[modeComboBox.getSelectedIndex()];

                BlindTest blindTest = new BlindTest(lyrics, title, artist, mode);
                blindTests.add(blindTest);

                // Ajout du blind test au modèle de liste
                blindTestListModel.addElement("Titre : " + blindTest.getTitle() + ", Artiste : " + blindTest.getArtist() + ", Paroles : " + blindTest.getLyrics() + ", Mode de jeu : " + blindTest.getGameMode());

                // Sauvegarde des blind tests
                saveBlindTests();

                // Réinitialisation des champs
                lyricsField.setText("");
                titleField.setText("");
                artistField.setText("");
                modeComboBox.setSelectedIndex(0);
            }
        });

        // ActionListener pour le bouton d'invitation
        inviteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InviteFriendFrame(blindTests, currentUser);
            }
        });
    }

    private void saveBlindTests() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(blindTests);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving blind tests: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadBlindTests() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            blindTests = (List<BlindTest>) ois.readObject();
            for (BlindTest blindTest : blindTests) {
                blindTestListModel.addElement("Titre : " + blindTest.getTitle() + ", Artiste : " + blindTest.getArtist() + ", Paroles : " + blindTest.getLyrics() + ", Mode de jeu : " + blindTest.getGameMode());
            }
        } catch (FileNotFoundException e) {
            // File not found, it's OK if it doesn't exist yet
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading blind tests: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Méthode pour envoyer un blind test à un destinataire
    public void sendBlindTest(BlindTest blindTest, String recipient) {
        // Code pour envoyer le blind test à un destinataire (par email, messagerie, etc.)
        // Cette méthode peut être implémentée selon les besoins de votre application
    }

    // Méthode pour afficher un blind test
    public void displayBlindTest(BlindTest blindTest) {
        JOptionPane.showMessageDialog(this, "Titre : " + blindTest.getTitle() + "\nArtiste : " + blindTest.getArtist() + "\nParoles : " + blindTest.getLyrics() + "\nMode de jeu : " + blindTest.getGameMode());
    }


    public boolean verifyResponse(String title, String artist, BlindTest blindTest) {
        return blindTest.getTitle().equalsIgnoreCase(title) && blindTest.getArtist().equalsIgnoreCase(artist);
    }
}
