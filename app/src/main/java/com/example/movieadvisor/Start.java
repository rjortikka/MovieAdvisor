package com.example.movieadvisor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

public class Start extends AppCompatActivity {

    private EditText eUsername;
    private EditText ePassword;
    private Button eLogin;
    private TextView eCreateAcc;
    private CheckBox eCheckBox;

    boolean isValid = false;

    public Credentials credentials;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        eUsername = findViewById(R.id.username);
        ePassword = findViewById(R.id.password);
        eLogin = findViewById(R.id.login_btn);
        eCreateAcc = findViewById(R.id.create_account);
        eCheckBox = findViewById(R.id.checkBox);

        credentials = new Credentials();

        // check if the user credentials already exist and are saved to the file
        // if they are saved get them form the file
        sharedPreferences = getApplicationContext().getSharedPreferences("CredentialsDB", MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();

        if(sharedPreferences != null) {

            Map<String, ?> preferenceMap = sharedPreferences.getAll();

            if(preferenceMap.size() !=0){
                credentials.loadCredentials(preferenceMap);
            }

            String savedUsername = sharedPreferences.getString("LastSavedUsername", "");
            String savedPassword = sharedPreferences.getString("LastSavedPassword", "");

            // check if user has pressed the Remember me
            if(sharedPreferences.getBoolean("RememberMeCheckbox", false)) {
                eUsername.setText(savedUsername);
                ePassword.setText(savedPassword);
                eCheckBox.setChecked(true);
            }

        }

        eCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Start.this, Create.class));
            }
        });

        eLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputUsername = eUsername.getText().toString();
                String inputPassword = ePassword.getText().toString();

                if (inputUsername.isEmpty() || inputPassword.isEmpty()) {
                    Toast.makeText(Start.this, "Please enter all the details correctly!", Toast.LENGTH_SHORT).show();
                } else {

                    isValid = validate(inputUsername, inputPassword);

                    if (!isValid) {
                        Toast.makeText(Start.this, "Incorrect information entered!", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(Start.this, "Login was successful!", Toast.LENGTH_SHORT).show();

                        sharedPreferencesEditor.putBoolean("RememberMeCheckbox", eCheckBox.isChecked());
                        // save new information to the file
                        sharedPreferencesEditor.apply();

                        sharedPreferencesEditor.putString("LastSavedUsername", inputUsername);
                        sharedPreferencesEditor.putString("LastSavedPassword", inputPassword);

                        sharedPreferencesEditor.apply();

                        startActivity(new Intent(Start.this, FindMovie.class));
                        // go to the next activity

                    }
                }
            }
        });

    }
    private boolean validate(String username, String password) {
        return credentials.verifyCredentials(username, password);
    }
}