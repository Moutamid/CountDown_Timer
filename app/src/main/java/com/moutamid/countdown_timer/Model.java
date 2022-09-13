package com.moutamid.countdown_timer;

public class Model {
    private int id;
    private String hour;
    private String minute;
    private String second;

    public Model(int id, String hour, String minute, String second) {
        this.id = id;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }
}