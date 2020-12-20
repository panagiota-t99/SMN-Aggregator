package com.example.smn_aggregator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import java.io.IOException;

public class InstagramPostStory extends AppCompatActivity {

    private Button btnSelectImage;
    private Button btnPost;
    private ImageView img;
    private Uri imageUri;
    private Bitmap selectedImage;

    public static final int REQUEST_CODE = 1;
    public static final String TAG = "SMN_Aggregator_App_Debug";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram_post_story);

        imageUri = null;

        Intent intent = getIntent();
        if (intent!=null){
            btnSelectImage = findViewById(R.id.btnSelectImage);
            btnPost = findViewById(R.id.btnInstagramExecutePost);
            img = (ImageView)findViewById(R.id.imageViewGallery);

            btnSelectImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "InstagramPostStory --> onClick: going to gallery");
                    openGallery();
                }
            });

            btnPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (imageUri!=null) {
                        Log.d(TAG, "InstagramPostStory --> onClick: post action accepted " );
                        postToInstagram();
                    }
                    else
                        Toast.makeText(InstagramPostStory.this, "You have to select an image first!", Toast.LENGTH_LONG).show();
                }
            });

        }
    }



    private void postToInstagram() {
        Log.d(TAG, "InstagramPostStory --> onCreate: posting ");
        String type = "image/*";
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType(type);
        share.putExtra(Intent.EXTRA_STREAM, imageUri);
        startActivity(Intent.createChooser(share, "Share to"));
    }




    private void openGallery() {
        Log.d(TAG, "InstagramPostStory --> openGallery: in gallery");
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "InstagramPostStory --> onActivityResult: chosen photo");
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            imageUri = data.getData();
            img.setImageURI(imageUri);
            try {
                selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}