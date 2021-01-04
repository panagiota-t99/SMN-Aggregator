package com.example.smn_aggregator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class TwitterPostType extends AppCompatActivity {

    private Button text;
    private Button photo;
    public static final String TAG = "SMN_Aggregator_App_Debug";
    public static final String TYPE1 = "text";
    public static final String TYPE2 = "photo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_post_type);

        Intent intent = getIntent();
        if (intent!=null){
            text = findViewById(R.id.btnTwitterText);
            photo = findViewById(R.id.btnTwitterPhoto);

            text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(TwitterPostType.this,TwitterPostStory.class);
                    intent1.putExtra("type", TYPE1);
                    Log.d(TAG, "TwitterPostType --> onClick: " + TYPE1 );
                    startActivity(intent1);
                }
            });

            photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(TwitterPostType.this, TwitterPostStory.class);
                    intent1.putExtra("type", TYPE2);
                    Log.d(TAG, "TwitterPostType --> onClick: " + TYPE2 );
                    startActivity(intent1);
                }
            });
        }
    }
}