package com.example.movieadvisor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class Settings extends AppCompatActivity {
    TextView SettingsName;
    TextView SettingsPlace;

    TextView name;
    TextView place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SettingsName = (TextView) findViewById(R.id.name5);
        SettingsPlace = (TextView) findViewById(R.id.area5);

        //sets user information
        SettingsName.setText("ID: "+ UserInformation.getInstance().returnName());
        SettingsPlace.setText("Area: " + UserInformation.getInstance().returnPlace());

        name = (TextView) findViewById(R.id.userName);
        place = (TextView) findViewById(R.id.userArea);

        name.setText(UserInformation.getInstance().returnName());
        place.setText(UserInformation.getInstance().returnPlace());

        //adds items to sidmenu
        String[] arraySpinner = new String[] {
                getString(R.string.findmovie5), getString(R.string.moc5), getString(R.string.sm5), getString(R.string.imdb5), getString(R.string.settings5), getString(R.string.so5)
        };
        Spinner s = (Spinner) findViewById(R.id.sidemenu5);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        s.setSelection(4);

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0) {
                    Intent intent = new Intent(Settings.this, SearchMovies.class);
                    startActivity(intent);
                } if (i==1) {
                    Intent intent = new Intent(Settings.this, MoviesOnCalendar.class);
                    startActivity(intent);
                } if (i==2) {
                    Intent intent = new Intent(Settings.this, SavedMovies.class);
                    startActivity(intent);
                } if (i==3) {
                    Intent intent = new Intent(Settings.this, IMDB.class);
                    startActivity(intent);
                }if (i==5) {
                    IdInformation.getInstance().change(4);
                    Intent intent = new Intent(Settings.this, Start.class);
                    startActivity(intent);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    //sets user information
    public void testFunction(View v) {
        String input = name.getText().toString();
        String input2 = place.getText().toString();
        UserInformation.getInstance().change(input, input2);
        name.setText(UserInformation.getInstance().returnName());
        place.setText(UserInformation.getInstance().returnPlace());

        SettingsName = (TextView) findViewById(R.id.name5);
        SettingsPlace = (TextView) findViewById(R.id.area5);

        SettingsName.setText("ID: "+ UserInformation.getInstance().returnName());
        SettingsPlace.setText("Area: " + UserInformation.getInstance().returnPlace());
    }
}