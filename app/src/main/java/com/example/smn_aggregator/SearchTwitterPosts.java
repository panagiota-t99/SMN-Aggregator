package com.example.smn_aggregator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import twitter4j.Status;
import twitter4j.Trend;

public class SearchTwitterPosts extends AppCompatActivity {

    public static final String TAG = "SMN_Aggregator_App_Debug";
    public static final String TYPE4 = "searchPosts";

    private ArrayList<Status> statuses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_twitter_posts);

        Log.d(TAG, "ARRIVED AT SEARCH");

        Intent intent = getIntent();

        StatusesWrapper statusesWrapper = (StatusesWrapper) intent.getSerializableExtra("statuses");
        this.statuses = statusesWrapper.getStatuses();


        Log.d(TAG, "EVERYTHING WORKED!");

        Log.d(TAG, "RECEIVED " + statuses.size() + " STATUSES");

        for (Status status: statuses){
            Log.d(TAG, status.getText());
        }


    }
}