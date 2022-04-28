package com.example.movieadvisor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Password extends AppCompatActivity {

    int activity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
    }

    public void loadActivity(View v) {
        //uses activity to remember last activity
        activity = IdInformation.getInstance().returnId();
        if (activity == 0) {
            Intent intent = new Intent(Password.this, MainActivity.class);
            startActivity(intent);
        } else if (activity == 1) {
            Intent intent = new Intent(Password.this, MoviesOnCalendar.class);
            startActivity(intent);
        } else if (activity == 2) {
            Intent intent = new Intent(Password.this, SavedMovies.class);
            startActivity(intent);
        } else if (activity == 3) {
            Intent intent = new Intent(Password.this, IMDB.class);
            startActivity(intent);
        } else if (activity == 4) {
            Intent intent = new Intent(Password.this, Settings.class);
            startActivity(intent);
        }

    }
}