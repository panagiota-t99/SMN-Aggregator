package com.example.smn_aggregator;

import androidx.appcompat.app.AppCompatActivity;

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

import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import java.io.IOException;

public class FacebookPostStory extends AppCompatActivity {

    private EditText hashtag;
    private Button btnSelectImage;
    private Button btnPost;
    private ShareDialog shareDialog;
    private String strHashtag;
    private ImageView img;
    private Uri imageUri;
    private Bitmap selectedImage;
    private boolean emptyHashtag;

    public static final int REQUEST_CODE = 1;
    public static final String TAG = "SMN_Aggregator_App_Debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_post_story);

        imageUri = null;
        emptyHashtag = true;

        Intent intent = getIntent();
        if (intent!=null){
            btnSelectImage = findViewById(R.id.btnSelectImage);
            btnPost = findViewById(R.id.btnFacebookExecutePost);
            img = (ImageView)findViewById(R.id.imageViewGallery);

            btnSelectImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hashtag = findViewById(R.id.txtHashtagInput);
                    strHashtag = hashtag.getText().toString();
                    if (!strHashtag.equals("")){
                        String temp = strHashtag.replaceAll("\\s+","_");
                        if (temp.charAt(0) != '#')
                            temp = '#' + temp;
                        strHashtag = temp;
                        emptyHashtag = false;
                        Log.d(TAG, "FacebookPostStory --> Hashtag: " + strHashtag);
                    }
                    Log.d(TAG, "FacebookPostStory --> onClick: going to gallery");
                    openGallery();
                }
            });

            btnPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (imageUri!=null || !emptyHashtag) {
                        Log.d(TAG, "FacebookPostStory --> onClick: post action accepted ");
                        postToFacebook();
                    }
                }
            });

        }


    }

    private void postToFacebook() {
        Log.d(TAG, "FacebookPostStory --> onCreate: posting");

        shareDialog = new ShareDialog(this);

        if (imageUri!=null && !emptyHashtag){
            if (shareDialog.canShow(SharePhotoContent.class)) {
                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(selectedImage)
                        .build();
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .setShareHashtag(new ShareHashtag.Builder()
                                .setHashtag(strHashtag).build())
                        .build();
                shareDialog.show(content, ShareDialog.Mode.WEB);
            }
            else
                Log.d(TAG, "postToFacebook: error");
        }
        else if (imageUri!=null && emptyHashtag){
            if (shareDialog.canShow(SharePhotoContent.class)) {
                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(selectedImage)
                        .build();
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();
                shareDialog.show(content, ShareDialog.Mode.WEB);
            }
            else
                Log.d(TAG, "postToFacebook: error");
        }


    }


    private void openGallery() {
        Log.d(TAG, "FacebookPostStory --> openGallery: in gallery");
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "FacebookPostStory --> onActivityResult: chosen photo");
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