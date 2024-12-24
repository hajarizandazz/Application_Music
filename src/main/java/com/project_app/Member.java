
package main.java.com.project_app;

import java.io.*;
import java.util.*;

public class Member {
    protected String username;
    protected String fullName;
    protected String email;
    private Set<String> friends;
    private Map<String, List<String[]>> favoriteSongs;
    private List<Notification> notifications;
    private List<NotificationBlindTest> notificationsBlindTests;
    private List<String> playlists;
    private String password;

    public Member(String username, String fullName, String email) {
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.friends = new HashSet<>();
        this.notifications = new ArrayList<>();
        this.notificationsBlindTests = new ArrayList<>();
        this.playlists = new ArrayList<>();
        this.favoriteSongs = new HashMap<>();
        loadFavoriteSongs();
        loadPlaylists();
        loadFriendsFromFile();

    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String[]> getFavoriteSongsForPlaylist(String playlistName) {
        return favoriteSongs.getOrDefault(playlistName, new ArrayList<>());
    }

    public void addFavoriteSongForPlaylist(String playlistName, String[] song) {
        favoriteSongs.computeIfAbsent(playlistName, k -> new ArrayList<>()).add(song);
        saveFavoriteSongs();
    }

    public void removeFavoriteSongForPlaylist(String playlistName, String[] song) {
        List<String[]> songs = favoriteSongs.getOrDefault(playlistName, new ArrayList<>());
        songs.remove(song);
        if (songs.isEmpty()) {
            favoriteSongs.remove(playlistName);
        }
        saveFavoriteSongs();
    }
    public void removePlaylist(String playlistName) {
        playlists.remove(playlistName);
        favoriteSongs.remove(playlistName);
        savePlaylists();
        saveFavoriteSongs();
    }

    public void removeSongsFromPlaylist(String playlistName, List<String[]> songsToRemove) {
        List<String[]> songs = favoriteSongs.get(playlistName);
        if (songs != null) {
            songs.removeAll(songsToRemove);
            if (songs.isEmpty()) {
                favoriteSongs.remove(playlistName);
            }
            saveFavoriteSongs();
        }
    }


    public void addPlaylist(String playlistName) {
        playlists.add(playlistName);
        savePlaylists();
    }

    public List<String> getPlaylists() {
        return playlists;
    }

    public void saveFavoriteSongs() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("favoriteSongs.dat"))) {
            out.writeObject(favoriteSongs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFavoriteSongs() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("favoriteSongs.dat"))) {
            favoriteSongs = (Map<String, List<String[]>>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            favoriteSongs = new HashMap<>();
        }
    }

    public void savePlaylists() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("playlists.dat"))) {
            out.writeObject(playlists);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void loadFriendsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/ressources/friends.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                friends.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

 private void saveFriendsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFriendsFilename()))) {
            for (String friend : friends) {
                writer.write(friend);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFriendsFilename() {
        return username + "_friends.txt";
    }
    public boolean addFriend(String friendUsername) {
        if (friends.add(friendUsername)) {
            saveFriendsToFile();
            return true;
        }
        return false;
    }

    public void removeFriend(String friendUsername) {
        friends.remove(friendUsername);
        saveFriendsToFile();
    }

    public Set<String> getFriends() {
        return friends;
    }

    public void addNotification(Notification notification) {
        notifications.add(notification);
    }

    public void addNotificationBlindTest(NotificationBlindTest notificationsBlindTest) {
        notificationsBlindTests.add(notificationsBlindTest);
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public List<NotificationBlindTest> getNotificationsBlindTests() {
        return notificationsBlindTests;
    }

    public void removeNotification(Notification notification) {
        notifications.remove(notification);
    }
    public void removeNotificationBlindTest( NotificationBlindTest notificationsBlindTest) {
        notificationsBlindTests.remove(notificationsBlindTest);
    }
    private void loadPlaylists() {
        try (BufferedReader reader = new BufferedReader(new FileReader(username + "_playlists.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                playlists.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}
