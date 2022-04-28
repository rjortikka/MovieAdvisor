package com.example.movieadvisor;

public class IdInformation {
    //This stores information on where you are in the sidemenu

    private static IdInformation idInformation = null;
    int id = 0;
    //

    public static IdInformation getInstance() {
        if (idInformation == null) {
            idInformation = new IdInformation();

        }
        return idInformation;
    }
    private IdInformation() {
        int id;
    }

    public void change(int idChange) {
        id = idChange;
    }

    public int returnId() {
        return id;
    }
}
