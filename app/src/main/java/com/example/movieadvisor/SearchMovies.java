package com.example.movieadvisor;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

@RequiresApi(api = Build.VERSION_CODES.O)
public class SearchMovies extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    EditText moviename;
    EditText latesttime;
    EditText date;
    EditText earliesttime;
    String theatresearch;
    RecyclerView movieinfo;
    String IDsearch;
    TheatreInfo ti=TheatreInfo.getInstance();

    TextView SearcMoviesName;
    TextView SearchMoviesPlace;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movies);
        Context context=SearchMovies.this;

        Button search =findViewById(R.id.search);
        Button add = findViewById(R.id.addcalendar);
        Button save = findViewById(R.id.save);
        Spinner theatre=findViewById(R.id.theatre);
        moviename=findViewById(R.id.movie);
        earliesttime=findViewById(R.id.earliesttime);
        latesttime=findViewById(R.id.latesttime);
        date=findViewById(R.id.date);
        movieinfo=findViewById(R.id.information);

        ArrayAdapter<Theatre> adapter =new ArrayAdapter<Theatre>(context, android.R.layout.simple_spinner_item, ti.theatrelist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        theatre.setAdapter(adapter);
        theatre.setOnItemSelectedListener(this);

        SearcMoviesName = (TextView) findViewById(R.id.name);
        SearchMoviesPlace = (TextView) findViewById(R.id.area);

        SearcMoviesName.setText("ID: "+ UserInformation.getInstance().returnName());
        SearchMoviesPlace.setText("Area: " + UserInformation.getInstance().returnPlace());

        String[] arraySpinner = new String[] {
                getString(R.string.findmovie1), getString(R.string.moc1), getString(R.string.sm1), getString(R.string.imdb1), getString(R.string.settings1), getString(R.string.so1)
        };
        Spinner s = (Spinner) findViewById(R.id.sidemenu);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter2);

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==1) {
                    Intent intent = new Intent(SearchMovies.this, MoviesOnCalendar.class);
                    startActivity(intent);
                } if (i==2) {
                    Intent intent = new Intent(SearchMovies.this, SavedMovies.class);
                    startActivity(intent);
                } if (i==3) {
                    Intent intent = new Intent(SearchMovies.this, IMDB.class);
                    startActivity(intent);
                } if (i==4) {
                    Intent intent = new Intent(SearchMovies.this, Settings.class);
                    startActivity(intent);
                }if (i==5) {
                    IdInformation.getInstance().change(0);
                    Intent intent = new Intent(SearchMovies.this, Start.class);
                    startActivity(intent);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        theatresearch=parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void searchMovie(View v){

        for(int i=0;i<ti.theatrelist.size();i++){
            Theatre teatteri=ti.theatrelist.get(i);
            if(teatteri.name==theatresearch){
                IDsearch=teatteri.ID;
            }
        }
        ti.findMovie(IDsearch, date.getText().toString(), earliesttime.getText().toString(),latesttime.getText().toString(), moviename.getText().toString());
        System.out.println(date.getText().toString());
        RecyclerViewAdapter RecyclerViewAdapter = new RecyclerViewAdapter(this,ti.namelist,ti.timelist, ti.placelist);
        movieinfo.setAdapter(RecyclerViewAdapter);
        movieinfo.setLayoutManager(new LinearLayoutManager(this));
    }

}