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
    private Status currentStatus;

    public static final String consumer_key = BuildConfig.twitterConsumerKey;
    public static final String consumer_secret_key= BuildConfig.twitterConsumerSecret;
    public static final String access_token = BuildConfig.twitterAccessToken;
    public static final String access_token_secret = BuildConfig.twitterAccessTokenSecret;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView repliesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_twitter_post);

        Intent intent = getIntent();
        currentStatus = (Status) intent.getSerializableExtra("status");
        //The status received from SearchTwitterPost is shown in detail here
        //AsyncTask is used for the user's profile image in order the application not to be delayed

        Log.d(TAG, currentStatus.getUser().getScreenName() + " ARRIVED TO SHOW!");

        new DownloadProfileImageTask((ImageView) findViewById(R.id.profileImageView)).execute(currentStatus.getUser().get400x400ProfileImageURLHttps());
        TextView screenNameText = findViewById(R.id.screenNameText);
        TextView userNameText = findViewById(R.id.usernameText);
        TextView statusText = findViewById(R.id.statusText);
        TextView createdAtText = findViewById(R.id.createdAtText);
        Button likeButton = findViewById(R.id.likeButton);
        TextView favoritesCountText = findViewById(R.id.favoritesCountText);
        TextView retweetsCountText = findViewById(R.id.retweetsCountText);
        repliesTextView = findViewById(R.id.repliesTextview);

        screenNameText.setText(currentStatus.getUser().getScreenName());
        userNameText.setText("@" + currentStatus.getUser().getName());
        statusText.setText(currentStatus.getText());
        createdAtText.setText(currentStatus.getCreatedAt().toString());
        likeButton.setBackgroundResource(R.drawable.twitterblackheart);
        favoritesCountText.setText(String.valueOf(currentStatus.getFavoriteCount()) + " Likes");
        retweetsCountText.setText(String.valueOf(currentStatus.getRetweetCount()) + " Retweets");

        new DownloadRepliesTask().execute(currentStatus);

    }

    private class DownloadRepliesTask extends AsyncTask<Status, Void, ArrayList<Status>>{

        @Override
        protected ArrayList<twitter4j.Status> doInBackground(twitter4j.Status... statuses) {
            String userName = statuses[0].getUser().getScreenName();
            Long tweetID = statuses[0].getId();
            ArrayList<twitter4j.Status> repliesList = new ArrayList<>();

            try{
                Query query = new Query(userName);
                query.setSinceId(tweetID);
                QueryResult result;
                ConfigurationBuilder cb = new ConfigurationBuilder();
                cb.setDebugEnabled(true)
                        .setOAuthConsumerKey(consumer_key)
                        .setOAuthConsumerSecret(consumer_secret_key)
                        .setOAuthAccessToken(access_token)
                        .setOAuthAccessTokenSecret(access_token_secret);
                TwitterFactory tf = new TwitterFactory(cb.build());
                Twitter twitter = tf.getInstance();

                /*
                A counter was added because if we returned all the replies, twitter would give a timeout
                of 5 minutes to our app and then we couldn't get data from the API. As a result the conversation
                below the tweet doesn't make total sense.
                 */
                int i = 0;
                do{
                    result = twitter.search(query);
                    Log.d(TAG, "FOUND " + result.getTweets().size() + " REPLIES");

                    List<twitter4j.Status> replies = result.getTweets();
                    for (twitter4j.Status reply: replies){

                            //Log.d(TAG, "TWEET TEXT: " + reply.getText());
                            repliesList.add(reply);

                    }
                    i++;
                }while((query = result.nextQuery()) != null && i<=5);
            }catch (Exception e){
                Log.d(TAG, "ERROR IN GETTING REPLIES");
                return repliesList;
            }
            return repliesList;
        }



        @Override
        protected void onPostExecute(ArrayList<twitter4j.Status> statuses) {
            Log.d(TAG, "ON POST FOUND " + statuses.size() + " REPLIES");
            repliesTextView.setText(statuses.size() + " Replies");
            recyclerView = findViewById(R.id.repliesRecyclerView);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(ShowTwitterPost.this);
            adapter = new TwitterRepliesAdapter(statuses);

            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }

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