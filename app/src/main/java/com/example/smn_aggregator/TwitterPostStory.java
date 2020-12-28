package com.example.smn_aggregator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class TwitterPostStory extends AppCompatActivity {

    private Button btnText;
    private EditText txtTweet;

    private Button btnSelectImage;
    private EditText txtTweetImage;
    private Button btnPostTweetImage;
    private Uri imageUri;
    private ImageView imageView;
    private File file;


    public static final int REQUEST_CODE = 2;
    public static final String TYPE1 = "text";
    public static final String TYPE2 = "photo";
    public static final String TAG = "SMN_Aggregator_App_Debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent!=null){
            String type = intent.getStringExtra("type");
            if (type.equals(TYPE1)){
                setContentView(R.layout.twitter_text);
                btnText = findViewById(R.id.btnPostTweetText);
                btnText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txtTweet = findViewById(R.id.txtTweetInput);
                        String txt = txtTweet.getText().toString();
                        TwitterTask task1 = new TwitterTask(TYPE1, txt);
                        task1.execute();
                    }
                });
            }
            else if (type.equals(TYPE2)){
                setContentView(R.layout.twitter_photo);
                imageUri = null;
                btnSelectImage = findViewById(R.id.btnSelectImageTwitter);
                btnPostTweetImage = findViewById(R.id.btnPostTweetPhoto);
                imageView = findViewById(R.id.TwitterImageView);

                btnSelectImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "TwitterPostStory --> onClick: going to gallery");
                        openGallery();
                    }
                });
                btnPostTweetImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (imageUri!=null) {
                            Log.d(TAG, "TwitterPostStory --> onClick: post accepted ");
                            txtTweetImage = findViewById(R.id.txtTweetPhotoInput);
                            String txt = txtTweetImage.getText().toString();
                            TwitterTask task2 = new TwitterTask(TYPE2, txt, file);
                            task2.execute();
                        }else
                            Toast.makeText(TwitterPostStory.this, "You have to select an image first!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }

    private void openGallery() {
        Log.d(TAG, "TwitterPostStory --> openGallery: in gallery");
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "TwitterPostStory --> onActivityResult: chosen photo");
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
            file = new File(getRealPathFromURI(imageUri));
            //Log.d(TAG, "onActivityResult: " + file);
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}