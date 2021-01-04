package com.example.smn_aggregator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class ShowTwitterPost extends AppCompatActivity {

    public static final String TAG = "SMN_Aggregator_App_Debug";
    public static final String consumer_key = "qoaRhCOH1SZrQ190tUsp3BeVE";
    public static final String consumer_secret_key= "0voSKioruV9NDNzUp3w7vqSqHZhmgd6LK8MnIzAckBvphsWpwl";
    public static final String access_token = "1338583364631207944-7DOZADOzgmoo9b2iQ8kCeUBr50JE31";
    public static final String access_token_secret = "RJKgIm2lDSBQw4Z5aWPsJmJScWrKyBtuOWJUX2LDhvICa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_twitter_post);

        Intent intent = getIntent();
        Status status = (Status) intent.getSerializableExtra("status");

        Log.d(TAG, status.getUser().getScreenName() + " ARRIVED TO SHOW!");

        new DownloadProfileImageTask((ImageView) findViewById(R.id.profileImageView)).execute(status.getUser().get400x400ProfileImageURLHttps());
        TextView screenNameText = findViewById(R.id.screenNameText);
        TextView userNameText = findViewById(R.id.usernameText);
        TextView statusText = findViewById(R.id.statusText);
        TextView createdAtText = findViewById(R.id.createdAtText);
        Button likeButton = findViewById(R.id.likeButton);
        TextView favoritesCountText = findViewById(R.id.favoritesCountText);
        TextView retweetsCountText = findViewById(R.id.retweetsCountText);

        screenNameText.setText(status.getUser().getScreenName());
        userNameText.setText("@" + status.getUser().getName());
        statusText.setText(status.getText());
        createdAtText.setText(status.getCreatedAt().toString());
        likeButton.setBackgroundResource(R.drawable.twitterblackheart);
        favoritesCountText.setText(String.valueOf(status.getFavoriteCount()) + " Likes");
        retweetsCountText.setText(String.valueOf(status.getRetweetCount()) + " Retweets");
    }

    private class DownloadProfileImageTask extends AsyncTask<String, Void, Bitmap>{

        private ImageView imageView;

        public DownloadProfileImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String url = strings[0];
            Bitmap bitmap = null;
            try{
                InputStream in = new java.net.URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            }catch(Exception e){
                Log.d(TAG, "ERROR DOWNLOADING IMAGE!", e);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }
}