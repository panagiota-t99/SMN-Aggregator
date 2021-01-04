package com.example.smn_aggregator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import java.util.ArrayList;
import twitter4j.Query;
import twitter4j.Trend;
import twitter4j.Trends;

public class Trendings extends AppCompatActivity {

    public static final String TAG = "SMN_Aggregator_App_Debug";

    private Trends trends;
    private ArrayList<Trend> trendingHashtags = new ArrayList<>();
    public static final String TYPE4 = "searchPosts";

    private RecyclerView recyclerView;
    private TrendingsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trendings);

        Intent intent = getIntent();
        trends = (Trends)intent.getSerializableExtra("trends");

        Log.d(TAG, "GOT TRENDS FROM INTENT");

        for (Trend trend: trends.getTrends()){
            trendingHashtags.add(trend);
        }

        Log.d(TAG, "WE GOT HASHTAGS");
        Log.d(TAG, "SIZE = " + trendingHashtags.size());

        recyclerView = findViewById(R.id.trendingsRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new TrendingsAdapter(trendingHashtags);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnHashtagClickListener(new TrendingsAdapter.onHashtagClickListener() {
            @Override
            public void onHashtagClick(int position) {
                Log.d(TAG, "WILL SEND THE TREND " + trendingHashtags.get(position).getName() + " TO BE SEARCHED");

                Query query = new Query(trendingHashtags.get(position).getName());

                TwitterTask twitterTask = new TwitterTask(TYPE4, Trendings.this, query);
                twitterTask.execute();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.trendings_menu, menu);

        final MenuItem searchItem = menu.findItem(R.id.trendings_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}