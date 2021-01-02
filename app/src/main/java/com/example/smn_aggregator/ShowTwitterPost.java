package com.example.smn_aggregator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import twitter4j.Status;

public class ShowTwitterPost extends AppCompatActivity {

    public static final String TAG = "SMN_Aggregator_App_Debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_twitter_post);

        Intent intent = getIntent();
        Status status = (Status) intent.getSerializableExtra("status");

        Log.d(TAG, status.getUser().getScreenName() + " ARRIVED TO SHOW!");

        TextView screenNameText = findViewById(R.id.screenNameText);
        TextView userNameText = findViewById(R.id.usernameText);
        TextView statusText = findViewById(R.id.statusText);
        TextView createdAtText = findViewById(R.id.createdAtText);
        TextView favoritesCountText = findViewById(R.id.favoritesCountText);
        TextView retweetsCountText = findViewById(R.id.retweetsCountText);

        screenNameText.setText(status.getUser().getScreenName());
        userNameText.setText("@" + status.getUser().getName());
        statusText.setText(status.getText());
        createdAtText.setText(status.getCreatedAt().toString());
        favoritesCountText.setText(String.valueOf(status.getFavoriteCount()) + " Likes");
        retweetsCountText.setText(String.valueOf(status.getRetweetCount()) + " Retweets");
    }
}