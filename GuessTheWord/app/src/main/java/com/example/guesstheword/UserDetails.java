package com.example.guesstheword;

public class UserDetails {
    private String Name;
    private String Score;
    private String Level;

    public UserDetails() {
    }

    public UserDetails(String name, String score, String level) {
        Name = name;
        Score = score;
        Level = level;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }
    @Override
    public String toString() {
        return "Name: "+Name + ", " + "Score: "+ Score + ", " +"Level: "+ Level;
    }
}
