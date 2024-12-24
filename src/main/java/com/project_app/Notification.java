package main.java.com.project_app;

public class Notification {
    private String sender;
    private String receiver;
    private Song song;

    public Notification(String sender, String receiver, Song song) {
        this.sender = sender;
        this.receiver = receiver;
        this.song = song;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public Song getSong() {
        return song;
    }

    @Override
    public String toString() {
        return "Recommandation de " + sender + ": " + song.getTitle() + " par " + song.getArtist();
    }
}
