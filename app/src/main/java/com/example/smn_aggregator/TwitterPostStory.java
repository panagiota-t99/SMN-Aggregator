package com.example.smn_aggregator;

import androidx.appcompat.app.AppCompatActivity;

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

public class TwitterPostStory extends AppCompatActivity {

    private Button btnText;
    private EditText txtTweet;
    private static String txt;

    private Button btnSelectImage;
    private EditText txtTweetImage;
    private static String txtImageCaption;
    private Button btnPostTweetImage;
    private static Uri imageUri;
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
                checkTextInput();

                btnText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (txt != null) {
                            if (!txt.equals("")) {
                                TwitterTask task1 = new TwitterTask(TYPE1, txt);
                                task1.execute();
                            }
                        }
                        else {
                            txtTweet = findViewById(R.id.txtTweetInput);
                            txt = txtTweet.getText().toString();

                            if (!txt.equals("")) {
                                TwitterTask task2 = new TwitterTask(TYPE1, txt);
                                task2.execute();
                                Intent intent = new Intent(TwitterPostStory.this, PostActivity.class);
                                startActivity(intent);
                            }
                            else
                                Toast.makeText(TwitterPostStory.this, "You have to enter a tweet!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
            else if (type.equals(TYPE2)){
                setContentView(R.layout.twitter_photo);
                imageUri = null;
                btnSelectImage = findViewById(R.id.btnSelectImageTwitter);
                btnPostTweetImage = findViewById(R.id.btnPostTweetPhoto);
                imageView = findViewById(R.id.TwitterImageView);
                checkSelectedPhoto();
                checkImageCaption();

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
                            if (txtImageCaption!=null){
                                if (!txtImageCaption.equals("")){
                                    TwitterTask task2 = new TwitterTask(TYPE2, txtImageCaption, file);
                                    task2.execute();
                                    Intent intent = new Intent(TwitterPostStory.this, PostActivity.class);
                                    startActivity(intent);
                                }
                            }
                            else{
                                txtTweetImage = findViewById(R.id.txtTweetPhotoInput);
                                txtImageCaption = txtTweetImage.getText().toString();
                                TwitterTask task3 = new TwitterTask(TYPE2, txtImageCaption, file);
                                task3.execute();
                                Intent intent = new Intent(TwitterPostStory.this, PostActivity.class);
                                startActivity(intent);
                            }
                        }
                        else
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

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void checkTextInput(){
        String tempFacebook = FacebookPostStory.getQuote();
        if (tempFacebook != null){
            if (!tempFacebook.equals("")) {
                txt = tempFacebook;
                txtTweet = findViewById(R.id.txtTweetInput);
                txtTweet.setText(txt);
                Log.d(TAG, "checkTextInput: " + txt);
            }
        }
    }

    private void checkImageCaption(){
        String tempFacebook = FacebookPostStory.getHashtag();
        if (tempFacebook!=null){
            if (!tempFacebook.equals("")){
                txtImageCaption = tempFacebook;
                txtTweetImage = findViewById(R.id.txtTweetPhotoInput);
                txtTweetImage.setText(txtImageCaption);
                Log.d(TAG, "checkImageCaption: " + txtImageCaption);
            }
        }
    }

    private void checkSelectedPhoto(){
        Uri tempFacebook = FacebookPostStory.getImageUri();
        Uri tempInstagram = InstagramPostStory.getImageUri();
        Log.d(TAG, "checkSelectedPhoto: temp facebook uri " + tempFacebook);
        Log.d(TAG, "checkSelectedPhoto: temp twitter uri " + tempInstagram);
        if (tempFacebook!=null) {
            imageView.setImageURI(tempFacebook);
            imageUri = tempFacebook;
            file = new File(getRealPathFromURI(imageUri));
        }
        else if (tempInstagram!=null) {
            imageView.setImageURI(tempInstagram);
            imageUri = tempInstagram;
            file = new File(getRealPathFromURI(imageUri));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            Log.d(TAG, "TwitterPostStory --> onActivityResult: chosen photo");
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
            file = new File(getRealPathFromURI(imageUri));
            Log.d(TAG, "onActivityResult: " + file);
        }
    }

    public  static Uri getImageUri(){ return imageUri; }
    public static String getTxt(){ return txt; }
}