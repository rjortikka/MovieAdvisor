package com.example.finnkinoharjt;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText moviename;
    EditText latesttime;
    EditText date;
    EditText earliesttime;
    String theatresearch;
    RecyclerView movieinfo;
    String IDsearch;
    SharedPreferences sp;

    TheatreInfo ti=TheatreInfo.getInstance();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context=MainActivity.this;

        Button search =findViewById(R.id.search);
        Button savedata = findViewById(R.id.savedata);
        Button add = findViewById(R.id.addcalendar);
        Button list = findViewById(R.id.listmovie);
        Spinner theatre=findViewById(R.id.theatre);
        moviename=findViewById(R.id.namemovie);
        earliesttime=findViewById(R.id.earliesttime);
        latesttime=findViewById(R.id.latesttime);
        date=findViewById(R.id.date);
        movieinfo=findViewById(R.id.information);

        //Saving data in SharedPreferences
        sp = getSharedPreferences("Moviedata", Context.MODE_PRIVATE);

        savedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sp.edit();

                editor.putString("Theatre", String.valueOf(ti.placelist));
                editor.putString("Movie name", String.valueOf(ti.namelist));
                editor.putString("Date and time", String.valueOf(ti.timelist));
                editor.commit();
                Toast.makeText(MainActivity.this, "Information Saved", Toast.LENGTH_LONG).show();

            }
        });

        ArrayAdapter<Theatre> adapter =new ArrayAdapter<Theatre>(context, android.R.layout.simple_spinner_item, ti.theatrelist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        theatre.setAdapter(adapter);
        theatre.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        theatresearch=parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    // Search movie information and list them in the recycler view
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

    // Saves the information the user wants in a list that appears on the calendar
    public void addCalendar(View c){

        for(int i=0;i<ti.theatrelist.size();i++){
            Theatre teatteri=ti.theatrelist.get(i);
            if(teatteri.name==theatresearch){
                IDsearch=teatteri.ID;
            }
        }
        ti.findMovie(IDsearch, date.getText().toString(), earliesttime.getText().toString(),latesttime.getText().toString(), moviename.getText().toString());
        System.out.println(date.getText().toString());

        for (int i = 0; i < ti.namelist.size(); i++) {
            ti.namestorage.add(ti.namelist.get(i));
            if (ti.namestorage.size()>100){
                ti.namestorage.clear();
            }
        }
        for (int i = 0; i < ti.timelist.size(); i++) {
            ti.timestorage.add(ti.timelist.get(i));
            if (ti.timestorage.size()>100) {
                ti.timestorage.clear();
            }
        }
        for (int i = 0; i < ti.placelist.size(); i++) {
            ti.placestorage.add(ti.placelist.get(i));
            if (ti.placestorage.size()>100) {
                ti.placestorage.clear();
            }
        }

        // Change the calendar view
        Intent intent = new Intent(MainActivity.this,MainActivity2.class);
        startActivity(intent);


    }

    // Saves the information the user wants in the list, and displays it in a different view
    public void ListMovies (View v){

        for(int i=0;i<ti.theatrelist.size();i++){
            Theatre teatteri=ti.theatrelist.get(i);
            if(teatteri.name==theatresearch){
                IDsearch=teatteri.ID;
            }
        }
        ti.findMovie(IDsearch, date.getText().toString(), earliesttime.getText().toString(),latesttime.getText().toString(), moviename.getText().toString());
        System.out.println(date.getText().toString());

        for (int i = 0; i < ti.namelist.size(); i++) {
            ti.namestorageforrating.add(ti.namelist.get(i));
            if (ti.namestorageforrating.size()>100){
                ti.namestorageforrating.clear();
            }
        }

        // Change the saved movies view
        Intent intent2 = new Intent(MainActivity.this,MainActivity4.class);
        startActivity(intent2);

    }
}
