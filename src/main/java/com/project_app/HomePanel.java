package main.java.com.project_app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HomePanel extends JPanel {
    private MemberManager memberManager;
    private JFrame parentFrame;
    private JPanel contentPanel;
    private Member currentUser;
    public HomePanel(MemberManager memberManager, JFrame parentFrame, Member currentUser) {
        this.memberManager = memberManager;
        this.parentFrame = parentFrame;
        this.currentUser = currentUser;
        setLayout(new BorderLayout());

        // Menu panel à gauche
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setPreferredSize(new Dimension(200, getHeight()));
        menuPanel.setBackground(new Color(220, 220, 220));

        // Ajouter des boutons pour chaque fonctionnalité
        addMenuItem(menuPanel, "Profil", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showProfilePanel();
            }
        });

        addMenuItem(menuPanel, "Notification Blind Test", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddFamilyMemberPanel();
            }
        });

        addMenuItem(menuPanel, "amis", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFriendPanel();
            }
        });
        addMenuItem(menuPanel, "Playlists", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPlaylists();
            }
        });
        addMenuItem(menuPanel, "chansons", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSongPanel(); ;
            }
        });
        addMenuItem(menuPanel, "Envoyer Recommandation", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSendRecommendationPanel();
            }
        });
        addMenuItem(menuPanel, "blind test", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCreateBlindTestPanel();
            }
        });


        addMenuItem(menuPanel, "Notifications", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showNotifications();
            }
        });


        // Boutons de déconnexion, notifications et playlists
        addMenuItem(menuPanel, "Déconnexion", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });

        // Ajouter d'autres boutons pour chaque fonctionnalité selon votre cahier des charges

        add(menuPanel, BorderLayout.WEST);

        // Espace pour afficher les différents panneaux de fonctionnalité
        contentPanel = new JPanel(); // Initialiser le contentPanel
        contentPanel.setLayout(new CardLayout());
        add(contentPanel, BorderLayout.CENTER);
    }

    private void addMenuItem(JPanel menuPanel, String itemText, ActionListener actionListener) {
        JPanel menuItemPanel = new JPanel();
        menuItemPanel.setPreferredSize(new Dimension(200, 40)); // Taille du menu
        menuItemPanel.setBackground(new Color(250, 234, 170)); // Couleur de fond du menu
        menuItemPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY)); // Bordure inférieure grise
        menuItemPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Alignement et espacements

        JLabel menuItemLabel = new JLabel(itemText);
        menuItemPanel.add(menuItemLabel);

        menuItemPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                menuItemPanel.setBackground(new Color(183, 223, 239));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                menuItemPanel.setBackground(new Color(250, 234, 170)); // Couleur de fond du menu
            }
        });

        menuItemPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                actionListener.actionPerformed(new ActionEvent(menuItemPanel, ActionEvent.ACTION_PERFORMED, null));
            }
        });

        menuPanel.add(menuItemPanel);
    }

    private void showProfilePanel() {
        ProfilePanel profilePanel = new ProfilePanel(currentUser); // Créer une instance de ProfilePanel avec l'utilisateur actuel
        contentPanel.removeAll(); // Supprimer tout contenu précédent
        contentPanel.add(profilePanel, BorderLayout.CENTER); // Ajouter le ProfilePanel à la place
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showAddFamilyMemberPanel() {
        contentPanel.removeAll(); // Supprimer tout contenu précédent
        NotificationBlindTestPanel notificationsBlindTestPanel = new  NotificationBlindTestPanel(currentUser); // Créer une instance de NotificationsPanel avec l'utilisateur actuel
        contentPanel.add(notificationsBlindTestPanel, BorderLayout.CENTER); // Ajouter le NotificationsPanel à la place
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showFriendPanel() {
        contentPanel.removeAll(); // Supprimer tout contenu précédent
        contentPanel.add(new FriendPanel(currentUser), BorderLayout.CENTER); // Ajouter le FriendPanel à la place
        contentPanel.revalidate();
        contentPanel.repaint();
    }

  private void showPlaylists(){
      contentPanel.removeAll(); // Supprimer tout contenu précédent
      PlaylistPanel playlistPanel = new PlaylistPanel(currentUser); // Créer une instance de PlaylistPanel
      contentPanel.add(playlistPanel, BorderLayout.CENTER); // Ajouter le PlaylistPanel au contentPanel
      contentPanel.revalidate();
      contentPanel.repaint();
  }
    private void showSongPanel() {
        contentPanel.removeAll(); // Supprimer tout contenu précédent
        SongPanel songPanel = new SongPanel(); // Créer une instance de SongPanel
        contentPanel.add(songPanel, BorderLayout.CENTER); // Ajouter le SongPanel au contentPanel
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showCreateBlindTestPanel() {
        contentPanel.removeAll();
        BlindTestPanel blindTestPanel = new BlindTestPanel(currentUser);
        contentPanel.add(blindTestPanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }


    private void showSendRecommendationPanel() {
        contentPanel.removeAll();
        RecommendationPanel recommendationPanel = new RecommendationPanel(currentUser);
        contentPanel.add(recommendationPanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void logout() {
        parentFrame.getContentPane().removeAll(); // Supprimer tout contenu précédent
        parentFrame.getContentPane().add(new MainPanel(parentFrame)); // Ajouter MainPanel à la place
        parentFrame.revalidate(); // Rafraîchir le contenu du parentFrame
        parentFrame.repaint();
    }
    private void showNotifications() {
        contentPanel.removeAll(); // Supprimer tout contenu précédent
        NotificationsPanel notificationsPanel = new NotificationsPanel(currentUser); // Créer une instance de NotificationsPanel avec l'utilisateur actuel
        contentPanel.add(notificationsPanel, BorderLayout.CENTER); // Ajouter le NotificationsPanel à la place
        contentPanel.revalidate();
        contentPanel.repaint();
    }


}
