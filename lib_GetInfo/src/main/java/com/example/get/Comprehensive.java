package com.example.get;


public class Comprehensive {
    private String time;
    private String resone;
    private String state;
    private String score;
    private String note;
    public static String toalscore;

    public Comprehensive(String time, String reason, String state, String score, String note) {
        this.time = time;
        this.resone = reason;
        this.state = state;
        this.score = score;
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public String getResone() {
        return resone;
    }

    public String getScore() {
        return score;
    }

    public String getState() {
        return state;
    }

    public String getTime() {
        return time;
    }
}
