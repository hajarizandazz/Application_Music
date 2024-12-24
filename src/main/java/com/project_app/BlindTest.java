package main.java.com.project_app;

import java.io.Serializable;

public class BlindTest implements Serializable {
    private static final long serialVersionUID = 1L; // Add a version ID for serialization
    private String lyrics;
    private String title;
    private String artist;
    private GameMode gameMode;

    // Constructor, getters, and setters
    public BlindTest(String lyrics, String title, String artist, GameMode gameMode) {
        this.lyrics = lyrics;
        this.title = title;
        this.artist = artist;
        this.gameMode = gameMode;
    }

    public String getLyrics() {
        return lyrics;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public GameMode getGameMode() {
        return gameMode;
    }
}
