package com.example.cricketclubapp;

public class Player {

    private String Name;
    private String Hostel;
    private String Programme;
    private int Photo;
    private boolean Sold;
    private int Amount;
    private String Team;




    public String getName() {
        return Name;
    }

    public String getHostel() {
        return Hostel;
    }

    public String getProgramme() {
        return Programme;
    }

    public int getPhoto() {
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

    public Player(String name, String hostel, String programme, int photo, boolean sold, int amount, String team) {
        Name = name;
        Hostel = hostel;
        Programme = programme;
        Photo = photo;
        Sold = sold;
        Amount = amount;
        Team = team;
    }



}
