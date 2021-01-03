package com.example.smn_aggregator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import twitter4j.auth.RequestToken;


public class MainActivity extends AppCompatActivity {

    private LoginButton facebookLoginButton;
    private CallbackManager facebookCallbackManager;

    private TwitterLoginButton twitterLoginButton;
    public static final String TAG = "SMN_Aggregator_App_Debug";
    public static final int PERMISSION_CODE = 100;

    private boolean permission;
    private Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        facebookCallbackManager = CallbackManager.Factory.create();
        Twitter.initialize(this);
        setContentView(R.layout.activity_main);

        permission = false;
        continueButton = findViewById(R.id.continueButton);
        continueButton.setEnabled(true);

        //FACEBOOK LOGIN BUTTON
        facebookLoginButton = (LoginButton) findViewById(R.id.facebookLoginButton);
        facebookLoginButton.setLoginBehavior(LoginBehavior.WEB_VIEW_ONLY);
        facebookLoginButton.setLoginText("Log in with Facebook");

        facebookLoginButton.registerCallback(facebookCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.d(TAG, "Facebook Login Done!");
                facebookLoginButton.setEnabled(false);
                facebookLoginButton.setLogoutText("Log in with Facebook");
                checkButtons();
            }
            @Override
            public void onCancel() {
                // App code
                Log.d(TAG, "Facebook Login Cancelled!");
            }
            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.d(TAG, "Facebook Login Error!");
            }
        });


        //TWITTER LOGIN BUTTON
        twitterLoginButton = (TwitterLoginButton)findViewById(R.id.twitterLoginButton);
        twitterLoginButton.setText("Log in with Twitter");
        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession twitterSession = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken twitterAuthToken = twitterSession.getAuthToken();

                Log.d(TAG, "TWITTER LOGIN DONE!");
                twitterLoginButton.setEnabled(false);
                checkButtons();
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d(TAG, "TWITTER LOGIN FAILURE!");
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
                if (permission){
                    Intent intent = new Intent(MainActivity.this, FunctionsActivity.class);
                    Log.d(TAG, "continue to FunctionsActivity: Approved");
                    startActivity(intent);
                }
            }
        });

    }

    public void checkButtons(){
       if ((!facebookLoginButton.isEnabled()) && (!twitterLoginButton.isEnabled())){
            Log.d(TAG, "ALL BUTTONS ARE DISABLED");
            Log.d(TAG, "ENABLED CONTINUE BUTTON");
            enableContinueButton();
        }
    }

    public void enableContinueButton(){
        continueButton.setEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(TwitterAuthConfig.DEFAULT_AUTH_REQUEST_CODE == requestCode){
            twitterLoginButton.onActivityResult(requestCode, resultCode, data);
        }else{
            facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void checkPermission() {
        int hasReadContactsPermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (hasReadContactsPermission == PackageManager.PERMISSION_GRANTED){
            Log.d(TAG, "Permission is already granted!");
            permission = true;
        }
        else{
            Log.d(TAG, "Permission does not exist. Requesting now...");
            String[] permissionsToAsk = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(MainActivity.this, permissionsToAsk, PERMISSION_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case PERMISSION_CODE:{
                //if the request was denied, the arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "Permission Granted!!!");
                    permission = true;
                }
                else
                    Log.d(TAG, "Permission denied :(");
                    permission = false;
            }
        }

    }
}