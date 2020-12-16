package com.example.smn_aggregator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnFacebook;
    private Button btnInstagram;
    private Button btnTwitter;
    public static final String TAG = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnFacebook = findViewById(R.id.btnFacebookLogin);
        btnInstagram = findViewById(R.id.btnInstagramLogin);
        btnTwitter = findViewById(R.id.btnTwitterLogin);

        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnFacebook.setEnabled(false);
                continueToFunctionsActivity();
            }
        });


        btnInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnInstagram.setEnabled(false);
                continueToFunctionsActivity();
            }
        });


        btnTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnTwitter.setEnabled(false);
                continueToFunctionsActivity();
            }
        });
    }

    public boolean checkButtons(){
        if (!btnFacebook.isEnabled() && !btnInstagram.isEnabled() && !btnTwitter.isEnabled()){
            Log.d(TAG, "checkButtons: All buttons are disabled");
            return true;
        }
        return false;

    }

    public void continueToFunctionsActivity(){
        if (checkButtons()){
            Intent intent = new Intent(MainActivity.this, FunctionsActivity.class);
            Log.d(TAG, "continueToFunctionsActivity: Approved");
            startActivity(intent);
        }

    }




}