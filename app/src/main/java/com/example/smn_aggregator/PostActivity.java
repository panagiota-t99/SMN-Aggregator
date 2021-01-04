package com.example.smn_aggregator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PostActivity extends AppCompatActivity {

    private Button btnPostFacebook;
    private Button btnPostInstagram;
    private Button btnPostTwitter;
    private PackageManager pm;

    public static final String TAG = "SMN_Aggregator_App_Debug";
    public static final String packageName = "com.instagram.android";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        
        Intent intent = getIntent();
        if (intent!=null){
            pm = getPackageManager();
            btnPostFacebook = findViewById(R.id.btnPostFacebook);
            btnPostInstagram = findViewById(R.id.btnPostInstagram);
            btnPostTwitter = findViewById(R.id.btnPostTwitter);

            btnPostFacebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "PostActivity --> onClick: Facebook post");
                    Intent intent1 = new Intent(PostActivity.this, FacebookPostType.class);
                    startActivity(intent1);
                }
            });

            btnPostInstagram.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isPackageInstalled(packageName,pm)){
                        Log.d(TAG, "PostActivity --> onClick: Instagram post ");
                        Intent intent2 = new Intent(PostActivity.this, InstagramPostStory.class);
                        startActivity(intent2);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Instagram must be installed on the device!", Toast.LENGTH_LONG).show();
                    }

                }
            });

            btnPostTwitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "PostActivity --> onClick: Twitter post");
                    Intent intent1 = new Intent(PostActivity.this, TwitterPostType.class);
                    startActivity(intent1);
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