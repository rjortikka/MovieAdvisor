package com.example.movieadvisor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class IMDB extends AppCompatActivity {
    TextView IMDBName;
    TextView IMDBPlace;

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imdb);

        spinner = (Spinner) findViewById(R.id.spinner);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        IMDBName = (TextView) findViewById(R.id.name4);
        IMDBPlace = (TextView) findViewById(R.id.area4);

        //setting user information
        IMDBName.setText("ID: "+ UserInformation.getInstance().returnName());
        IMDBPlace.setText("Area: " + UserInformation.getInstance().returnPlace());

        //adding option to sidemenu
        String[] arraySpinner = new String[] {
                getString(R.string.findmovie4), getString(R.string.moc4), getString(R.string.sm4), getString(R.string.imdb4), getString(R.string.settings4), getString(R.string.so4)
        };
        Spinner s = (Spinner) findViewById(R.id.sidemenu4);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        s.setSelection(3);

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0) {
                    Intent intent = new Intent(IMDB.this, MainActivity.class);
                    startActivity(intent);
                } if (i==1) {
                    Intent intent = new Intent(IMDB.this, MainActivity2.class);
                    startActivity(intent);
                } if (i==2) {
                    Intent intent = new Intent(IMDB.this, MainActivity4.class);
                    startActivity(intent);
                } if (i==4) {
                    Intent intent = new Intent(IMDB.this, Settings.class);
                    startActivity(intent);
                }if (i==5) {
                    IdInformation.getInstance().change(3);
                    Intent intent = new Intent(IMDB.this, Start.class);
                    startActivity(intent);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    //uses getJSON and getJSONRating to find the id using the title and then finding the rating using the id
    public void readJSON(View v) {
        String json = getJSON();
        System.out.println("JSON " + json);

        ArrayList<String> list = new ArrayList<>();

        if (json != null) {
            try {
                JSONObject root = new JSONObject(json);
                JSONArray jsonArray = root.getJSONArray("results");
                for (int i=0; i<jsonArray.length(); i++) {
                    JSONObject jobject = jsonArray.getJSONObject(i);
                    System.out.println(i+1);
                    System.out.println(jobject.getString("id"));
                    System.out.println(jobject.getString("title"));
                    list.add(jobject.getString("title"));
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this , android.R.layout.simple_spinner_dropdown_item, list);
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        for (int j=0; j<jsonArray.length(); j++) {
                            JSONObject jobject = null;
                            try {
                                jobject = jsonArray.getJSONObject(j);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (i == j) {
                                try {
                                    System.out.println(jobject.getString("id"));
                                    getJSONRating(jobject.getString("id"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    System.out.println(jobject.getString("title"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    //uses the input to find the right json
    public String getJSON() {
        String response = null;
        try {
            TextView t = findViewById(R.id.movieSearch);
            String input = t.getText().toString();
            URL url = new URL("https://imdb-api.com/en/API/SearchTitle/k_i07fiofu/" + input);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            response = sb.toString();
            in.close();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    //uses id to find the rating
    public String getJSONRating(String id) throws JSONException {
        String response = null;
        try {
            URL url = new URL("https://imdb-api.com/en/API/Ratings/k_i07fiofu/" + id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            response = sb.toString();
            in.close();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(response);
        JSONObject root = new JSONObject(response);
        TextView t = findViewById(R.id.rating);
        t.setText(root.getString("imDb"));
        //System.out.println(root.getString("imDb"));
        return response;
    }
}