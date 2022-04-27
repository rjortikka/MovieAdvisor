package com.example.movieadvisor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MoviesOnCalendar extends AppCompatActivity {
    TextView MOCName;
    TextView MOCPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_on_calendar);

        MOCName = (TextView) findViewById(R.id.name2);
        MOCPlace = (TextView) findViewById(R.id.area2);

        //sets user information
        MOCName.setText("ID: "+ UserInformation.getInstance().returnName());
        MOCPlace.setText("Area: " + UserInformation.getInstance().returnPlace());

        //adds items to sidemenu
        String[] arraySpinner = new String[] {
                getString(R.string.findmovie2), getString(R.string.moc2), getString(R.string.sm2), getString(R.string.imdb2), getString(R.string.settings2), getString(R.string.so2)
        };
        Spinner s = (Spinner) findViewById(R.id.sidemenu2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        s.setSelection(1);

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0) {
                    Intent intent = new Intent(MoviesOnCalendar.this, SearchMovies.class);
                    startActivity(intent);
                }if (i==2) {
                    Intent intent = new Intent(MoviesOnCalendar.this, SavedMovies.class);
                    startActivity(intent);
                } if (i==3) {
                    Intent intent = new Intent(MoviesOnCalendar.this, IMDB.class);
                    startActivity(intent);
                } if (i==4) {
                    Intent intent = new Intent(MoviesOnCalendar.this, Settings.class);
                    startActivity(intent);
                }if (i==5) {
                    IdInformation.getInstance().change(1);
                    Intent intent = new Intent(MoviesOnCalendar.this, Start.class);
                    startActivity(intent);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}