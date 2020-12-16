package com.example.smn_aggregator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FunctionsActivity extends AppCompatActivity {

    private Button btnTrendingHashtags;
    private Button btnPost;
    private Button btnStory;
    public static final String TAG = "Function";

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

            }
        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        btnStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}