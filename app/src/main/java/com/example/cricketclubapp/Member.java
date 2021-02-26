package com.example.cricketclubapp;

public class Member {
    String name;
    String points;
    String image;

    public Member(String name, String points, String image){
        this.name = name;
        this.points = points;
        this.image = image;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
