package com.example.movieadvisor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loadActivity(View v) {
        Intent intent = new Intent(MainActivity.this, Password.class);
        startActivity(intent);
    }

    public void loadActivityCreate(View v) {
        Intent intent = new Intent(MainActivity.this, Create.class);
        startActivity(intent);
    }
}