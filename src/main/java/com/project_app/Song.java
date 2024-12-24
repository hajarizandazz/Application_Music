package main.java.com.project_app;

public class Song {
    private String artist;
    private String title;
    private String link;
    private String text;

    public Song(String artist, String title, String link, String text) {
        this.artist = artist;
        this.title = title;
        this.link = link;
        this.text = text;
    }

    public String getArtist() {
        return artist;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getText() {
        return text;
    }
}
