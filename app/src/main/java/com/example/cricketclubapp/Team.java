package com.example.cricketclubapp;

public class Team {

    private String TeamName;
    private int RemBudget;
    private int TeamSize;

    public Team(String teamName, int remBudget, int teamSize) {
        TeamName = teamName;
        RemBudget = remBudget;
        TeamSize = teamSize;
    }

    public String getTeamName() {
        return TeamName;
    }

    public int getRemBudget() {
        return RemBudget;
    }

    public int getTeamSize() {
        return TeamSize;
    }
}
