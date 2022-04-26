package com.example.movieadvisor;

public class UserInformation {
    private static UserInformation userInformation = null;
    String name = "0000001";
    String place = "unknown";

    public static UserInformation getInstance() {
        if (userInformation == null) {
            userInformation = new UserInformation();

        }
        return userInformation;
    }
    private UserInformation() {
        String name;
        String place;
    }

    public void change(String nameChange, String placeChange) {
        name =  nameChange;
        place = placeChange;
    }

    public String returnName() {
        return name;
    }

    public String returnPlace() {
        return place;
    }
}
