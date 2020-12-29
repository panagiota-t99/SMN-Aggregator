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
    private static String strHashtag;
    private ImageView img;
    private static Uri imageUri;
    private Bitmap selectedImage;
    private boolean emptyHashtag;
    private static String quote;
    private Button btnTextPost;
    private  EditText text;

    private String type;

    public static final int REQUEST_CODE = 1;
    public static final String TYPE1 = "text";
    public static final String TYPE2 = "photo";
    public static final String TAG = "SMN_Aggregator_App_Debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent != null) {
            type = intent.getStringExtra("type");
            if (type.equals(TYPE1)) {
                setContentView(R.layout.facebook_text);
                btnTextPost = findViewById(R.id.btnTextPost);
                checkTextInput();

                btnTextPost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (quote!=null) {
                            if (!quote.equals(""))
                                postQuoteToFacebook();
                        }
                        else {
                            text = findViewById(R.id.txtTextInput);
                            quote = text.getText().toString();

                            if (!quote.equals(""))
                                postQuoteToFacebook();
                            else
                                Toast.makeText(FacebookPostStory.this, "You have to enter a quote!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
            else if (type.equals(TYPE2)) {
                setContentView(R.layout.facebook_photo);
                imageUri = null;
                emptyHashtag = true;
                btnSelectImage = findViewById(R.id.btnSelectImage);
                btnPhotoPost = findViewById(R.id.btnPhotoPost);
                img = (ImageView)findViewById(R.id.FacebookImageView);
                checkSelectedPhoto();

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

    private void checkTextInput(){
        String tempTwitter = TwitterPostStory.getTxt();
        if (tempTwitter!=null){
            if (!tempTwitter.equals("")) {
                quote = tempTwitter;
                text = findViewById(R.id.txtTextInput);
                text.setText(quote);
                Log.d(TAG, "checkTextInput: " + quote);
            }
        }
    }

    private void checkSelectedPhoto(){
        Uri tempInstagram = InstagramPostStory.getImageUri();
        Uri tempTwitter = TwitterPostStory.getImageUri();
        Log.d(TAG, "checkSelectedPhoto: temp facebook uri " + tempInstagram);
        Log.d(TAG, "checkSelectedPhoto: temp twitter uri " + tempTwitter);
        if (tempInstagram!=null) {
            img.setImageURI(tempInstagram);
            imageUri = tempInstagram;
            try {
                selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (tempTwitter!=null) {
            img.setImageURI(tempTwitter);
            imageUri = tempTwitter;
            try {
                selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
                Log.d(TAG, "postToFacebook: error with hashtag");
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
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            Log.d(TAG, "FacebookPostStory --> onActivityResult: chosen photo");
            imageUri = data.getData();
            img.setImageURI(imageUri);
            try {
                selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            Intent intent = new Intent(FacebookPostStory.this, PostActivity.class);
            startActivity(intent);
        }
    }


    public static Uri getImageUri(){ return imageUri; }
    public static String getQuote() { return quote; }
    public static String getHashtag() { return strHashtag; }
}