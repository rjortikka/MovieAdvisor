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
import android.widget.Button;
import android.widget.TextView;

import java.util.Collections;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity4 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView movienameforlist;
    RecyclerView listrates;
    TheatreInfo ti=TheatreInfo.getInstance();

    TextView Name4;
    TextView Area4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        Context context=MainActivity4.this;

        Name4 = (TextView) findViewById(R.id.name222);
        Area4 = (TextView) findViewById(R.id.area222);

        //sets user information
        Name4.setText("ID: "+ UserInformation.getInstance().returnName());
        Area4.setText("Area: " + UserInformation.getInstance().returnPlace());

        movienameforlist = findViewById(R.id.namelm);
        Button list = findViewById(R.id.list2);
        listrates= findViewById(R.id.listmoviespace);


        RecyclerViewAdapter3 RecyclerViewAdapter3 = new RecyclerViewAdapter3(this,ti.namestorageforrating);
        listrates.setAdapter(RecyclerViewAdapter3);
        listrates.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    // Sort saved movies in aphabetic order and reorganize recyclerview
    public void listMovies(View v){


        Collections.sort(ti.namestorageforrating, String.CASE_INSENSITIVE_ORDER);
        RecyclerViewAdapter3 RecyclerViewAdapter3 = new RecyclerViewAdapter3(this,ti.namestorageforrating);
        listrates.setAdapter(RecyclerViewAdapter3);
        listrates.setLayoutManager(new LinearLayoutManager(this));
    }
}