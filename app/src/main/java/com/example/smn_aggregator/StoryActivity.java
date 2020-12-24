package com.example.smn_aggregator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StoryActivity extends AppCompatActivity {

    private Button btnStoryFacebook;
    private Button btnStoryInstagram;
    private Button btnStoryTwitter;

    public static final String TAG = "SMN_Aggregator_App_Debug";
    public static final String packageName = "com.instagram.android";
    public static final String TYPE = "photo";
    private PackageManager pm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        Intent intent = getIntent();
        if (intent!=null){

            pm = getPackageManager();
            btnStoryFacebook = findViewById(R.id.btnStoryFacebook);
            btnStoryInstagram = findViewById(R.id.btnStoryInstagram);
            btnStoryTwitter = findViewById(R.id.btnStoryTwitter);

            btnStoryFacebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "StoryActivity --> onClick: Facebook story");
                    Intent intent1 = new Intent(StoryActivity.this, FacebookPostStory.class);
                    intent1.putExtra("type", TYPE);
                    startActivity(intent1);
                }
            });

            btnStoryInstagram.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isPackageInstalled(packageName,pm)){
                        Log.d(TAG, "StoryActivity --> onClick: Instagram story ");
                        Intent intent2 = new Intent(StoryActivity.this, InstagramPostStory.class);
                        startActivity(intent2);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Instagram must be installed on the device!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }



    }

    private boolean isPackageInstalled(String packageName, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }


}