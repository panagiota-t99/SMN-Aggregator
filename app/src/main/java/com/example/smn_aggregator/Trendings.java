package com.example.smn_aggregator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;

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
    private Button hashtag21;
    private Button hashtag22;
    private Button hashtag23;
    private Button hashtag24;
    private Button hashtag25;
    private Button hashtag26;
    private Button hashtag27;
    private Button hashtag28;
    private Button hashtag29;
    private Button hashtag30;
    private Button hashtag31;
    private Button hashtag32;
    private Button hashtag33;
    private Button hashtag34;
    private Button hashtag35;
    private Button hashtag36;
    private Button hashtag37;
    private Button hashtag38;
    private Button hashtag39;
    private Button hashtag40;
    private Button hashtag41;
    private Button hashtag42;
    private Button hashtag43;
    private Button hashtag44;
    private Button hashtag45;
    private Button hashtag46;
    private Button hashtag47;
    private Button hashtag48;
    private Button hashtag49;
    private Button hashtag50;

    private Trends trends;
    private ArrayList<Trend> trendingHashtags = new ArrayList<>();

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
        hashtag21 = findViewById(R.id.hashtag21);
        hashtag22 = findViewById(R.id.hashtag22);
        hashtag23 = findViewById(R.id.hashtag23);
        hashtag24 = findViewById(R.id.hashtag24);
        hashtag25 = findViewById(R.id.hashtag25);
        hashtag26 = findViewById(R.id.hashtag26);
        hashtag27 = findViewById(R.id.hashtag27);
        hashtag28 = findViewById(R.id.hashtag28);
        hashtag29 = findViewById(R.id.hashtag29);
        hashtag30 = findViewById(R.id.hashtag30);
        hashtag31 = findViewById(R.id.hashtag31);
        hashtag32 = findViewById(R.id.hashtag32);
        hashtag33 = findViewById(R.id.hashtag33);
        hashtag34 = findViewById(R.id.hashtag34);
        hashtag35 = findViewById(R.id.hashtag35);
        hashtag36 = findViewById(R.id.hashtag36);
        hashtag37 = findViewById(R.id.hashtag37);
        hashtag38 = findViewById(R.id.hashtag38);
        hashtag39 = findViewById(R.id.hashtag39);
        hashtag40 = findViewById(R.id.hashtag40);
        hashtag41 = findViewById(R.id.hashtag41);
        hashtag42 = findViewById(R.id.hashtag42);
        hashtag43 = findViewById(R.id.hashtag43);
        hashtag44 = findViewById(R.id.hashtag44);
        hashtag45 = findViewById(R.id.hashtag45);
        hashtag46 = findViewById(R.id.hashtag46);
        hashtag47 = findViewById(R.id.hashtag47);
        hashtag48 = findViewById(R.id.hashtag48);
        hashtag49 = findViewById(R.id.hashtag49);
        hashtag50 = findViewById(R.id.hashtag50);

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
        hashtags.add(hashtag21);
        hashtags.add(hashtag22);
        hashtags.add(hashtag23);
        hashtags.add(hashtag24);
        hashtags.add(hashtag25);
        hashtags.add(hashtag26);
        hashtags.add(hashtag27);
        hashtags.add(hashtag28);
        hashtags.add(hashtag29);
        hashtags.add(hashtag30);
        hashtags.add(hashtag31);
        hashtags.add(hashtag32);
        hashtags.add(hashtag33);
        hashtags.add(hashtag34);
        hashtags.add(hashtag35);
        hashtags.add(hashtag36);
        hashtags.add(hashtag37);
        hashtags.add(hashtag38);
        hashtags.add(hashtag39);
        hashtags.add(hashtag40);
        hashtags.add(hashtag41);
        hashtags.add(hashtag42);
        hashtags.add(hashtag43);
        hashtags.add(hashtag44);
        hashtags.add(hashtag45);
        hashtags.add(hashtag46);
        hashtags.add(hashtag47);
        hashtags.add(hashtag48);
        hashtags.add(hashtag49);
        hashtags.add(hashtag50);

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

        for (int i=0; i<10; i++){
            hashtags.get(i).setVisibility(View.VISIBLE);
            hashtags.get(i).setText(trendingHashtags.get(i).getName());
        }

        Log.d(TAG, "WE GOT HASHTAGS");




    }
}