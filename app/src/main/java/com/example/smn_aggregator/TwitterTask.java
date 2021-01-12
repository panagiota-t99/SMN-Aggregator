package com.example.smn_aggregator;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class TwitterTask extends AsyncTask<String, Void, Void> {
    private String type;
    private String text;
    private File file;
    private Query query;

    public static final String TYPE1 = "text";
    public static final String TYPE2 = "photo";
    public static final String TYPE3 = "trends";
    public static final String TYPE4 = "searchPosts";
    public static final String TAG = "SMN_Aggregator_App_Debug";
    public static final int WOEID = 23424977;

    public static final String consumer_key = BuildConfig.twitterConsumerKey;
    public static final String consumer_secret_key= BuildConfig.twitterConsumerSecret;
    public static final String access_token = BuildConfig.twitterAccessToken;
    public static final String access_token_secret = BuildConfig.twitterAccessTokenSecret;

    private Context context;

    public TwitterTask(String type, Context context){
        this.type = type;
        this.context = context;
    }

    public TwitterTask(String type, Context context, Query query){
        this.type = type;
        this.context = context;
        this.query = query;
    }

    public TwitterTask(String  PostType,String tweetText) {
        type = PostType;
        text = tweetText;
    }

    public TwitterTask(String PostType,String tweetText, File f) {
        type = PostType;
        text = tweetText;
        file = f;
    }

    @Override
    protected Void doInBackground(String... strings) {
        Twitter twitter = configureTwitter();
        if (type.equals(TYPE1)) {
            try {
                postTextOnlyTweet(twitter);
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        }
        else if (type.equals(TYPE2)) {
            try {
                postImageTweet(twitter);
            } catch (TwitterException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
        else if (type.equals(TYPE3)){
            getTwitterTrends(twitter);
        }
        else if (type.equals(TYPE4)){
            searchTwitterPosts(twitter);
        }
        return null;
    }


    //This method configures the authentication required for Twitter API
    private Twitter configureTwitter(){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(consumer_key)
                .setOAuthConsumerSecret(consumer_secret_key)
                .setOAuthAccessToken(access_token)
                .setOAuthAccessTokenSecret(access_token_secret);
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        return twitter;
    }


    //This method posts a tweet
    private void postTextOnlyTweet(Twitter twitter) throws TwitterException {
        twitter.updateStatus(text);
        Log.d(TAG, "TwitterTask --> postTextOnlyTweet: " + text);
    }


    //This method posts a photo (caption optional)
    private void postImageTweet(Twitter twitter) throws TwitterException, URISyntaxException {
        StatusUpdate status = new StatusUpdate(text);
        status.setMedia(file);
        twitter.updateStatus(status);
        Log.d(TAG, "TwitterTask --> postImageTweet: " + file);
    }

    //This method returns trending hashtags for given location woeid.
    //Current woeid 23424977 is for the United States.
    private void getTwitterTrends(Twitter twitter){
        Trends trends;
        try{
            trends = twitter.getPlaceTrends(WOEID );
            Intent intent = new Intent(context, Trendings.class);
            intent.putExtra("trends", trends);
            context.startActivity(intent);
        }catch (TwitterException e){
            e.printStackTrace();
        }
    }

    //This method searches twitter posts based on the text of the Trend that has been clicked
    private void searchTwitterPosts(Twitter twitter) {

        QueryResult queryResult = null;
        query.resultType(Query.ResultType.popular);
        try {
            queryResult = twitter.search(query);
        } catch (TwitterException twitterException) {
            twitterException.printStackTrace();
        }
        ArrayList<twitter4j.Status> statuses = new ArrayList<>();

        for (twitter4j.Status status: queryResult.getTweets()){
            statuses.add(status);
        }

        StatusesWrapper statusesWrapper = new StatusesWrapper(statuses);
        Intent intent = new Intent(context, SearchTwitterPosts.class);
        intent.putExtra("statuses", statusesWrapper);
        context.startActivity(intent);
    }
}
