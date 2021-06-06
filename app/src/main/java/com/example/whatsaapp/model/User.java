package com.example.whatsaapp.model;

public class User {

    String profilepic, userName, mail , pass , userId , lastMessage;

    public User(String profilepic, String userName, String mail, String pass, String userId, String lastMessage) {
        this.profilepic = profilepic;
        this.userName = userName;
        this.mail = mail;
        this.pass = pass;
        this.userId = userId;
        this.lastMessage = lastMessage;
    }

    public User()
    {

    }
    public  User(String userName, String mail, String pass)
    {
        this.userName = userName;
        this.mail = mail;
        this.pass = pass;
    }


    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }


}
