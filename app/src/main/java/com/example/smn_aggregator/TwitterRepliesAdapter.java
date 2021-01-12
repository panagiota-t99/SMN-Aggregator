package com.example.smn_aggregator;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.util.ArrayList;

import twitter4j.Status;

public class TwitterRepliesAdapter extends RecyclerView.Adapter<TwitterRepliesAdapter.TwitterRepliesViewHolder> {

    public static final String TAG = "SMN_Aggregator_App_Debug";
    private ArrayList<Status> replies;

    public static class TwitterRepliesViewHolder extends RecyclerView.ViewHolder{

        public ImageView replyImageView;
        public TextView replyUsernameText;
        public TextView replyText;

        public TwitterRepliesViewHolder(@NonNull View itemView){
            super(itemView);

            replyImageView = itemView.findViewById(R.id.replyImageView);
            replyUsernameText = itemView.findViewById(R.id.replyUsernameText);
            replyText = itemView.findViewById(R.id.replyText);
        }

    }

    public TwitterRepliesAdapter(ArrayList<Status> replies){this.replies = replies;}

    @NonNull
    @Override
    public TwitterRepliesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reply_item, parent, false);
        TwitterRepliesViewHolder twitterRepliesViewHolder = new TwitterRepliesViewHolder(v);
        return twitterRepliesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TwitterRepliesViewHolder holder, int position) {
        Status currentReply = replies.get(position);

        holder.replyUsernameText.setText(currentReply.getUser().getScreenName());
        holder.replyText.setText(currentReply.getText());
        new DownloadProfileImageTask((ImageView) holder.replyImageView).execute(currentReply.getUser().get400x400ProfileImageURLHttps());
    }

    private class DownloadProfileImageTask extends AsyncTask<String, Void, Bitmap> {

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

    @Override
    public int getItemCount() {
        return replies.size();
    }
}
