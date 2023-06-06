package com.example.ecm2425coursework;

import com.google.firebase.Timestamp;

public class Save_Note {
    String Title;
    String Content;
    Timestamp timestamp;


    public Save_Note() {

    }

    // Setters and getters for information stored in each note
    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
