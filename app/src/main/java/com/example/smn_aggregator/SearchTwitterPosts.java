package com.example.smn_aggregator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import java.util.ArrayList;

import twitter4j.Status;
import twitter4j.Trend;

public class SearchTwitterPosts extends AppCompatActivity {

    public static final String TAG = "SMN_Aggregator_App_Debug";

    private ArrayList<Status> statuses = new ArrayList<>();

    private RecyclerView recyclerView;
    private PostAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_twitter_posts);

        Intent intent = getIntent();

        StatusesWrapper statusesWrapper = (StatusesWrapper) intent.getSerializableExtra("statuses");
        this.statuses = statusesWrapper.getStatuses();

        Log.d(TAG, "RECEIVED " + statuses.size() + " STATUSES");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new PostAdapter(statuses);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        //When user clicks on a specific posts that post will be shown in detail in the ShowTwitterPost activity
        adapter.setOnPostClickListener(new PostAdapter.onPostClickListener() {
            @Override
            public void onPostClick(int position) {
                Log.d(TAG, statuses.get(position).getUser().getScreenName() + " CLICKED");
                Intent intent1 = new Intent(SearchTwitterPosts.this, ShowTwitterPost.class);
                intent1.putExtra("status", statuses.get(position));
                startActivity(intent1);
            }
        });

    }
}