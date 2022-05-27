package com.example.smile_ukraine.Modals;

import java.util.Map;

public class Massage {
    private String sender;
    private String receiver;
    private String massage;
    private String time;

    public Massage(String sender, String receiver, String massage, String time) {
        this.sender = sender;
        this.receiver = receiver;
        this.massage = massage;
        this.time = time;
    }

    public Massage() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
