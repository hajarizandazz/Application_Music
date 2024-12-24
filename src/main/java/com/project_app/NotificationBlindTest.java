package main.java.com.project_app;

import java.io.Serializable;

public class NotificationBlindTest implements Serializable {
    private String sender;
    private String message;
    private BlindTest blindTest;

    public NotificationBlindTest(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "From: " + sender + ", Message: " + message;
    }

    public BlindTest getBlindTest() {
        return blindTest;
    }
}