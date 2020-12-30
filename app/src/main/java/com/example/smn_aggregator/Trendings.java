package com.example.smn_aggregator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Trend;
import twitter4j.Trends;

public class Trendings extends AppCompatActivity {

    public static final String TAG = "SMN_Aggregator_App_Debug";

    private Button hashtag1;
    private Button hashtag2;
    private Button hashtag3;
    private Button hashtag4;
    private Button hashtag5;
    private Button hashtag6;
    private Button hashtag7;
    private Button hashtag8;
    private Button hashtag9;
    private Button hashtag10;
    private Button hashtag11;
    private Button hashtag12;
    private Button hashtag13;
    private Button hashtag14;
    private Button hashtag15;
    private Button hashtag16;
    private Button hashtag17;
    private Button hashtag18;
    private Button hashtag19;
    private Button hashtag20;

    private Trends trends;
    private ArrayList<Trend> trendingHashtags = new ArrayList<>();
    public static final String TYPE4 = "searchPosts";

    private View.OnClickListener hashtagClickedListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            hashtagClicked(v);
        }
    };

    private void hashtagClicked(View v){
        Button buttonPressed = (Button)v;
        Log.d(TAG, buttonPressed.getText().toString() + " WAS PRESSED!");
        for (Trend trend: trendingHashtags){
            if (buttonPressed.getText().toString() == trend.getName()){
                Log.d(TAG, "WILL SEND THE TREND" + trend.getName() + "TO BE SEARCHED");

                Query query = new Query(trend.getName());

                TwitterTask twitterTask = new TwitterTask(TYPE4, Trendings.this, query);
                twitterTask.execute();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trendings);

        hashtag1 = findViewById(R.id.hashtag1);
        hashtag2 = findViewById(R.id.hashtag2);
        hashtag3 = findViewById(R.id.hashtag3);
        hashtag4 = findViewById(R.id.hashtag4);
        hashtag5 = findViewById(R.id.hashtag5);
        hashtag6 = findViewById(R.id.hashtag6);
        hashtag7 = findViewById(R.id.hashtag7);
        hashtag8 = findViewById(R.id.hashtag8);
        hashtag9 = findViewById(R.id.hashtag9);
        hashtag10 = findViewById(R.id.hashtag10);
        hashtag11 = findViewById(R.id.hashtag11);
        hashtag12 = findViewById(R.id.hashtag12);
        hashtag13 = findViewById(R.id.hashtag13);
        hashtag14 = findViewById(R.id.hashtag14);
        hashtag15 = findViewById(R.id.hashtag15);
        hashtag16 = findViewById(R.id.hashtag16);
        hashtag17 = findViewById(R.id.hashtag17);
        hashtag18 = findViewById(R.id.hashtag18);
        hashtag19 = findViewById(R.id.hashtag19);
        hashtag20 = findViewById(R.id.hashtag20);

        ArrayList<Button> hashtags = new ArrayList<>();
        hashtags.add(hashtag1);
        hashtags.add(hashtag2);
        hashtags.add(hashtag3);
        hashtags.add(hashtag4);
        hashtags.add(hashtag5);
        hashtags.add(hashtag6);
        hashtags.add(hashtag7);
        hashtags.add(hashtag8);
        hashtags.add(hashtag9);
        hashtags.add(hashtag10);
        hashtags.add(hashtag11);
        hashtags.add(hashtag12);
        hashtags.add(hashtag13);
        hashtags.add(hashtag14);
        hashtags.add(hashtag15);
        hashtags.add(hashtag16);
        hashtags.add(hashtag17);
        hashtags.add(hashtag18);
        hashtags.add(hashtag19);
        hashtags.add(hashtag20);

        for (Button btn: hashtags){btn.setVisibility(View.INVISIBLE);}

        Intent intent = getIntent();
        Log.d(TAG, "TRENDS ARRIVED");
        trends = (Trends)intent.getSerializableExtra("trends");

        Log.d(TAG, "GOT TRENDS FROM INTENT");

        for (Trend trend: trends.getTrends()){
            trendingHashtags.add(trend);
        }

        for (Trend hashtag: trendingHashtags){
            Log.d(TAG, hashtag.getName());
        }

        for (int i=0; i<20; i++){
            hashtags.get(i).setVisibility(View.VISIBLE);
            hashtags.get(i).setText(trendingHashtags.get(i).getName());
        }

        Log.d(TAG, "WE GOT HASHTAGS");
        Log.d(TAG, "Size = " + trendingHashtags.size());

        EditText hashtagSearchText = findViewById(R.id.searchText);
        Button hashtagSearchButton = findViewById(R.id.searchBtn);

        hashtagSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = hashtagSearchText.getText().toString();
                ArrayList<Trend> searchResultTrends = new ArrayList<>();
                for (Trend trend: trendingHashtags){
                    if (trend.getName().equals(text)){
                        searchResultTrends.add(trend);
                    }
                }
                for (Button btn: hashtags){btn.setVisibility(View.INVISIBLE);}
                if (searchResultTrends.size() > 0){
                    for (int i=0; i<searchResultTrends.size(); i++){
                        hashtags.get(i).setVisibility(View.VISIBLE);
                        hashtags.get(i).setText(searchResultTrends.get(i).getName());
                    }
                }else {
                    hashtags.get(0).setVisibility(View.VISIBLE);
                    hashtags.get(0).setText("No matching results found");
                }
            }
        });

        for (Button hashtagBtn: hashtags){
            hashtagBtn.setEnabled(true);
            hashtagBtn.setOnClickListener(hashtagClickedListener);
        }

    }
}