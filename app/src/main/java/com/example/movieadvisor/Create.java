package com.example.movieadvisor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Map;
import java.util.regex.Pattern;

public class Create extends AppCompatActivity {

    private EditText eRegUsername;
    private EditText eRegPassword;
    private Button eRegister;


    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 number
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^?!&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{12,}" +               //at least 12 characters
                    "$");


    public static Credentials credentials;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        eRegUsername = findViewById(R.id.reg_username);
        eRegPassword = findViewById(R.id.reg_password);
        eRegister = findViewById(R.id.register_btn);

        //remember last screen where user was
        //

        credentials = new Credentials();

        sharedPreferences = getApplicationContext().getSharedPreferences("CredentialsDB",MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();

        if(sharedPreferences != null) {

            Map<String, ?> preferenceMap = sharedPreferences.getAll();

            if(preferenceMap.size() !=0){
                credentials.loadCredentials(preferenceMap);
            }

        }


        eRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // obtain the inputs from the fields
                String registeredName = eRegUsername.getText().toString();
                String registeredPassword = eRegPassword.getText().toString();

                // Check if the username is available and unique
                if(validate(registeredName, registeredPassword)) {

                    if(credentials.checkUsername(registeredName)){
                        Toast.makeText(Create.this, "Username already taken!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        credentials.addCredentials(registeredName,registeredPassword);

                        sharedPreferencesEditor.putString(registeredName, registeredPassword);

                        sharedPreferencesEditor.putString("LastSavedUsername", registeredName);
                        sharedPreferencesEditor.putString("LastSavedPassword", registeredPassword);

                        // add changes to the file
                        sharedPreferencesEditor.apply();

                        Toast.makeText(Create.this, "Registration Successful!", Toast.LENGTH_SHORT).show();

                        // Go to Login Activity

                        startActivity(new Intent(Create.this, Start.class));


                    }
                }
            }
        });

    }
    boolean validate(String name, String password)
    {
        // if the name and password are empty
        if(name.isEmpty() || password.isEmpty())
        {
            Toast.makeText(this, "Field can not be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }

        // password does not match to the pattern
        else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            Toast.makeText(this, "Password must contain at least one digit, uppercase and lowercase letter, special character and be 12 characters long.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}