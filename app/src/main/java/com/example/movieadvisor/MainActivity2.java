package com.example.movieadvisor;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView Name2;
    TextView Area2;

    TextView movieoncalendar;
    TextView datetimeoncalendar;
    TextView placeoncalendar;
    TheatreInfo ti=TheatreInfo.getInstance();
    RecyclerView movieinformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Context context = MainActivity2.this;

        Name2 = (TextView) findViewById(R.id.name333);
        Area2 = (TextView) findViewById(R.id.area333);

        //sets user information
        Name2.setText("ID: "+ UserInformation.getInstance().returnName());
        Area2.setText("Area: " + UserInformation.getInstance().returnPlace());


        movieoncalendar = findViewById(R.id.movieonc);
        datetimeoncalendar = findViewById(R.id.dateonc);
        placeoncalendar = findViewById(R.id.theatreonc);
        movieinformation=findViewById(R.id.movieinfooc);

        RecyclerViewAdapter2 RecyclerViewAdapter2 = new RecyclerViewAdapter2(this,ti.namestorage,ti.timestorage, ti.placestorage);
        movieinformation.setAdapter(RecyclerViewAdapter2);
        movieinformation.setLayoutManager(new LinearLayoutManager(this));

        System.out.println(ti.namestorage.size());

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}