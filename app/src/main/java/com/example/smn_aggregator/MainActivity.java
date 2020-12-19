package com.example.smn_aggregator;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;



public class MainActivity extends AppCompatActivity {

    private LoginButton btnFacebook;
    private CallbackManager callbackManager;

    private Button btnInstagram;
    private Button btnTwitter;
    public static final String TAG = "Login";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnFacebook = (LoginButton) findViewById(R.id.btnFacebook);
        btnFacebook.setLoginBehavior(LoginBehavior.WEB_VIEW_ONLY);
        callbackManager = CallbackManager.Factory.create();
        btnFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.d(TAG, "onSuccess: proceed...");
            }
            @Override
            public void onCancel() {
                // App code
                Log.d(TAG, "onCancel: canceling... ");
            }
            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });




        btnInstagram = findViewById(R.id.btnInstagramLogin);
        btnTwitter = findViewById(R.id.btnTwitterLogin);

        /*
        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // btnFacebook.setEnabled(false);
               // continueToFunctionsActivity();

            }
        });*/


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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


}