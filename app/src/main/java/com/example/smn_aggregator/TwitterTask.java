package com.example.smn_aggregator;

import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class TwitterTask extends AsyncTask<String, Void, Void> {
    private String type;
    private String text;
    private File file;

    public static final String TYPE1 = "text";
    public static final String TYPE2 = "photo";
    public static final String TAG = "SMN_Aggregator_App_Debug";

    public static final String consumer_key = "qoaRhCOH1SZrQ190tUsp3BeVE";
    public static final String consumer_secret_key= "0voSKioruV9NDNzUp3w7vqSqHZhmgd6LK8MnIzAckBvphsWpwl";
    public static final String access_token = "1338583364631207944-7DOZADOzgmoo9b2iQ8kCeUBr50JE31";
    public static final String access_token_secret = "RJKgIm2lDSBQw4Z5aWPsJmJScWrKyBtuOWJUX2LDhvICa";


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
        return null;
    }

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

    private void postTextOnlyTweet(Twitter twitter) throws TwitterException {
        twitter.updateStatus(text);
        Log.d(TAG, "TwitterTask --> postTextOnlyTweet: " + text);
    }

    private void postImageTweet(Twitter twitter) throws TwitterException, URISyntaxException {
        StatusUpdate status = new StatusUpdate(text);
        status.setMedia(file);
        twitter.updateStatus(status);
        Log.d(TAG, "TwitterTask --> postImageTweet: " + file);
    }


}
