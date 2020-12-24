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
import android.widget.Toast;

import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import java.io.IOException;

public class FacebookPostStory extends AppCompatActivity {

    private EditText hashtag;
    private Button btnSelectImage;
    private Button btnPhotoPost;
    private ShareDialog shareDialog;
    private String strHashtag;
    private ImageView img;
    private Uri imageUri;
    private Bitmap selectedImage;
    private boolean emptyHashtag;
    private String quote;
    private Button btnTextPost;
    private  EditText text;

    public static final int REQUEST_CODE = 1;
    public static final String TYPE1 = "text";
    public static final String TYPE2 = "photo";
    public static final String TAG = "SMN_Aggregator_App_Debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent != null) {
            String type = intent.getStringExtra("type");
            if (type.equals(TYPE1)) {
                setContentView(R.layout.facebook_text);
                btnTextPost = findViewById(R.id.btnTextPost);
                btnTextPost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        text = findViewById(R.id.txtTextInput);
                        quote = text.getText().toString();
                        if (!quote.equals(""))
                            postQuoteToFacebook();
                        else
                            Toast.makeText(FacebookPostStory.this, "You have to enter a quote!", Toast.LENGTH_LONG).show();
                    }
                });
            }
            else if (type.equals(TYPE2)) {
                setContentView(R.layout.facebook_photo);
                imageUri = null;
                emptyHashtag = true;
                btnSelectImage = findViewById(R.id.btnSelectImage);
                btnPhotoPost = findViewById(R.id.btnPhotoPost);
                img = (ImageView)findViewById(R.id.imageViewGallery);

                btnSelectImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "FacebookPostStory --> onClick: going to gallery");
                        openGallery();
                    }
                });
                btnPhotoPost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkHashtag();
                        if (imageUri!=null) {
                            Log.d(TAG, "FacebookPostStory --> onClick: post accepted ");
                            postPhotoToFacebook();
                        }else
                            Toast.makeText(FacebookPostStory.this, "You have to select an image first!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }

    private void checkHashtag() {
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
    }

    private void openGallery() {
        Log.d(TAG, "FacebookPostStory --> openGallery: in gallery");
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, REQUEST_CODE);
    }

    private void postQuoteToFacebook() {
        Log.d(TAG, "postQuoteOnFacebook: posting " + quote);
        shareDialog = new ShareDialog(this);
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse("https://github.com/UomMobileDevelopment"))
                    .setQuote(quote)
                    .build();
            shareDialog.show(linkContent);
        }
        else
            Log.d(TAG, "postToFacebook: error");
    }

    private void postPhotoToFacebook() {
        Log.d(TAG, "FacebookPostStory --> onCreate: posting");

        shareDialog = new ShareDialog(this);

        if (!emptyHashtag){
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
        else if (emptyHashtag){
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "FacebookPostStory --> onActivityResult: chosen photo");
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
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