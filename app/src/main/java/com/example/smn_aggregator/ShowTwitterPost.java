package com.example.smn_aggregator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.MediaEntity;
import twitter4j.Paging;
import twitter4j.Place;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.RateLimitStatus;
import twitter4j.ResponseList;
import twitter4j.Scopes;
import twitter4j.Status;
import twitter4j.SymbolEntity;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.URLEntity;
import twitter4j.User;
import twitter4j.UserMentionEntity;
import twitter4j.conf.ConfigurationBuilder;

public class ShowTwitterPost extends AppCompatActivity {

    public static final String TAG = "SMN_Aggregator_App_Debug";
    public static final String TYPE5 = "searchReplies";

    //The post that was clicked from the user is saved here
    private Status currentStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_twitter_post);

        Intent intent = getIntent();
        currentStatus = (Status) intent.getSerializableExtra("status");
        /*
        The status received from SearchTwitterPost is shown in detail here.
        An AsyncTask is used for the user's profile image in order the application not to be delayed.
         */

        Log.d(TAG, currentStatus.getUser().getScreenName() + " ARRIVED TO SHOW!");

        new DownloadProfileImageTask((ImageView) findViewById(R.id.profileImageView)).execute(currentStatus.getUser().get400x400ProfileImageURLHttps());
        TextView screenNameText = findViewById(R.id.screenNameText);
        TextView userNameText = findViewById(R.id.usernameText);
        TextView statusText = findViewById(R.id.statusText);
        TextView createdAtText = findViewById(R.id.createdAtText);
        Button likeButton = findViewById(R.id.likeButton);
        TextView favoritesCountText = findViewById(R.id.favoritesCountText);
        TextView retweetsCountText = findViewById(R.id.retweetsCountText);

        screenNameText.setText(currentStatus.getUser().getScreenName());
        userNameText.setText("@" + currentStatus.getUser().getName());
        statusText.setText(currentStatus.getText());
        createdAtText.setText(currentStatus.getCreatedAt().toString());
        likeButton.setBackgroundResource(R.drawable.twitterblackheart);
        favoritesCountText.setText(String.valueOf(currentStatus.getFavoriteCount()) + " Likes");
        retweetsCountText.setText(String.valueOf(currentStatus.getRetweetCount()) + " Retweets");

        Button viewRepliesButton = findViewById(R.id.viewRepliesButton);

        /*
        When the seeReplies button is clicked a new twitter task is executed that searches
        all the replies given to this specific post. Then the user is redirected to
        TwitterRepliesActivity where all the replies are shown.
         */
        viewRepliesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query = new Query(currentStatus.getUser().getScreenName());
                query.setSinceId(currentStatus.getId());

                Toast.makeText(ShowTwitterPost.this, "Loading replies...", Toast.LENGTH_LONG).show();
                TwitterTask twitterTask = new TwitterTask(TYPE5, ShowTwitterPost.this, query);
                twitterTask.execute();
            }
        });
    }

    /*
    This AsyncTask is responsible for getting the post's author's profile picture
    and setting it as the image Bitmap of the imageView
     */
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