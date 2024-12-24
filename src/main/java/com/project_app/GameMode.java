package main.java.com.project_app;

public enum GameMode {
    FIND_TITLE(1), // Mode pour trouver le titre de la chanson
    FIND_ARTIST(2), // Mode pour trouver l'artiste de la chanson
    FIND_TITLE_AND_ARTIST(3); // Mode pour trouver à la fois le titre et l'artiste de la chanson

    private final int value;

    GameMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static GameMode fromValue(int value) {
        for (GameMode mode : GameMode.values()) {
            if (mode.getValue() == value) {
                return mode;
            }
        }
        throw new IllegalArgumentException("Invalid GameMode value: " + value);
    }
}
