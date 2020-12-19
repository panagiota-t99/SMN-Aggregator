package com.example.smn_aggregator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class PostActivity extends AppCompatActivity {

    private Button btnPostFacebook;
    private Button btnPostInstagram;
    private Button btnPostTwitter;

    public static final String TAG = "SMN_Aggregator_App_Debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        
        Intent intent = getIntent();
        if (intent!=null){

            btnPostFacebook = findViewById(R.id.btnPostFacebook);
            btnPostFacebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "PostActivity --> onClick: Facebook post");
                    Intent intent1 = new Intent(PostActivity.this, FacebookPostStory.class);
                    startActivity(intent1);
                }
            });


        }
    }
}