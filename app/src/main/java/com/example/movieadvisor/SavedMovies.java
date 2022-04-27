package com.example.movieadvisor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class SavedMovies extends AppCompatActivity {
    TextView SavedMoviesName;
    TextView SavedMoviesPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_movies);

        SavedMoviesName = (TextView) findViewById(R.id.name3);
        SavedMoviesPlace = (TextView) findViewById(R.id.area3);

        //sets user information
        SavedMoviesName.setText("ID: "+ UserInformation.getInstance().returnName());
        SavedMoviesPlace.setText("Area: " + UserInformation.getInstance().returnPlace());

        //adds items to sidemenu
        String[] arraySpinner = new String[] {
                getString(R.string.findmovie3), getString(R.string.moc3), getString(R.string.sm3), getString(R.string.imdb3), getString(R.string.settings3), getString(R.string.so3)
        };
        Spinner s = (Spinner) findViewById(R.id.sidemenu3);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        s.setSelection(2);

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0) {
                    Intent intent = new Intent(SavedMovies.this, SearchMovies.class);
                    startActivity(intent);
                } if (i==1) {
                    Intent intent = new Intent(SavedMovies.this, MoviesOnCalendar.class);
                    startActivity(intent);
                } if (i==3) {
                    Intent intent = new Intent(SavedMovies.this, IMDB.class);
                    startActivity(intent);
                } if (i==4) {
                    Intent intent = new Intent(SavedMovies.this, Settings.class);
                    startActivity(intent);
                }if (i==5) {
                    IdInformation.getInstance().change(2);
                    Intent intent = new Intent(SavedMovies.this, Start.class);
                    startActivity(intent);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}