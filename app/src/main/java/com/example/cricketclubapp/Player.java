package com.example.cricketclubapp;

public class Player {

    private String Name;
    private String Hostel;
    private String Programme;
    private String Photo;
    private boolean Sold;
    private int Amount;
    private String Team;

    private long time;


    public String getName() {
        return Name;
    }

    public String getHostel() {
        return Hostel;
    }

    public String getProgramme() {
        return Programme;
    }

    public String getPhoto() {
        return Photo;
    }

    public boolean isSold() {
        return Sold;
    }

    public int getAmount() {
        return Amount;
    }

    public String getTeam() {
        return Team;
    }
    public long getTime() {
        return time;
    }

    public Player(String name, String hostel, String programme, String photo, boolean sold, int amount, String team,long Time) {
        Name = name;
        Hostel = hostel;
        Programme = programme;
        Photo = photo;
        Sold = sold;
        Amount = amount;
        Team = team;
        time = Time;
    }



}
