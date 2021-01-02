package com.example.smn_aggregator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import twitter4j.Status;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private ArrayList<Status> statuses;

    public  static  class PostViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public TextView userTextView;
        public TextView textView;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            userTextView = itemView.findViewById(R.id.userNameView);
            textView = itemView.findViewById(R.id.textView);
        }
    }

    public PostAdapter(ArrayList<Status> statuses) {
        this.statuses = statuses;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        PostViewHolder postViewHolder = new PostViewHolder(v);
        return postViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Status currentPost = statuses.get(position);

        holder.imageView.setImageResource(R.drawable.twitter_logo);
        holder.userTextView.setText(currentPost.getUser().getScreenName());
        holder.textView.setText(currentPost.getText());
    }

    @Override
    public int getItemCount() {
        return statuses.size();
    }
}
