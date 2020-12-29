package com.example.smn_aggregator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FunctionsActivity extends AppCompatActivity {

    private Button btnTrendingHashtags;
    private Button btnPost;
    private Button btnStory;
    public static final String TYPE3 = "trends";
    public static final String TAG = "SMN_Aggregator_App_Debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_functions);

        btnTrendingHashtags = findViewById(R.id.btnTrendingHashtags);
        btnPost = findViewById(R.id.btnPost);
        btnStory = findViewById(R.id.btnStory);

        btnTrendingHashtags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TwitterTask twitterTask = new TwitterTask(TYPE3, FunctionsActivity.this);
                twitterTask.execute();
            }
        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "FunctionsActivity --> onClick: move to PostActivity");
                Intent intent = new Intent(FunctionsActivity.this, PostActivity.class);
                startActivity(intent);

            }
        });

        btnStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "FunctionsActivity --> onClick: move to StoryActivity");
                Intent intent = new Intent(FunctionsActivity.this, StoryActivity.class);
                startActivity(intent);
            }
        });
    }
}