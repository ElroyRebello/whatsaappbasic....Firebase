package com.example.whatsaapp.model;

public class messagesmodel {

     String message;
    String uid;
    long timestamp;

    public messagesmodel(String uid, String message, long timestamp) {
        this.uid = uid;
        this.message = message;
        this.timestamp = timestamp;
    }

    public messagesmodel(String uid, String message) {
        this.uid = uid;
        this.message = message;
    }

    public  messagesmodel()
    {

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTimestamp() {
        return (int) timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
