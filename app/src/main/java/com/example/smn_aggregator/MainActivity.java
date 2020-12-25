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


public class MainActivity extends AppCompatActivity {

    private LoginButton facebookLoginButton;
    private CallbackManager facebookCallbackManager;

    private TwitterLoginButton twitterLoginButton;
    public static final String TAG = "SMN_Aggregator_App_Debug";

    private Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        facebookCallbackManager = CallbackManager.Factory.create();
        Twitter.initialize(this);
        setContentView(R.layout.activity_main);

        continueButton = findViewById(R.id.continueButton);
        continueButton.setEnabled(false);

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

                /*String userName = twitterSession.getUserName();
                Intent intent = new Intent(MainActivity.this, FunctionsActivity.class);
                intent.putExtra("username", userName);
                startActivity(intent);*/

                Log.d(TAG, "Twitter Login Done!");
                twitterLoginButton.setEnabled(false);
                checkButtons();
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d(TAG, "Twitter Login Failure!");
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FunctionsActivity.class);
                Log.d(TAG, "continue to FunctionsActivity: Approved");
                startActivity(intent);
            }
        });

    }

    public void checkButtons(){
        if ((!facebookLoginButton.isEnabled()) && (!twitterLoginButton.isEnabled())){
            Log.d(TAG, "All buttons are disabled");
            Log.d(TAG, "Enabling continue button");
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
}