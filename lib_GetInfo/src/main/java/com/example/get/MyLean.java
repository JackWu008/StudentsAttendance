package com.example.get;

public class MyLean {
    private String type;
    private String achievement;
    private String score;
    private String term;
    private String sourse;

    public static String totalCourse = "0";
    public static String totalScore = "0";
    public static String failAmount = "0";

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MyLean(String term, String sourse, String type, String score, String achievement) {
        this.term = term;
        this.sourse = sourse;
        this.type = type;

        this.achievement = achievement;
        this.score = score;
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getSourse() {
        return sourse;
    }

    public String getTerm() {
        return term;
    }

    public String getAchievement() {
        return achievement;
    }

    public String getScore() {
        return score;
    }
}
