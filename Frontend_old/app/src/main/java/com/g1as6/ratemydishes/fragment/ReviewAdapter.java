package com.g1as6.ratemydishes.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.g1as6.ratemydishes.R;
import com.g1as6.ratemydishes.Review;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private DisplayMetrics displayMetrics = new DisplayMetrics();
    private ArrayList<String> messages;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView user;
        private final TextView comment;
        private final RatingBar bar;
        private final ConstraintLayout layout;

        public ViewHolder(View view) {
            super(view);

            user = view.findViewById(R.id.netid);
            comment = view.findViewById(R.id.usercomment);
            bar = view.findViewById(R.id.userRating);
            layout = view.findViewById(R.id.userRatingLayout);
        }

        public TextView getUserTextView() { return user; };
        public TextView getCommentTextView() { return comment; }
        public RatingBar getRatingBar() { return bar; }
        public ConstraintLayout getConstraintLayout() { return layout; }
    }

    /**
     * Initialize the dataset of the Adapter.
     */
    public ReviewAdapter(ArrayList<String> messages) {
        this.messages = messages;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.user_review, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        try{
            JSONObject message = new JSONObject(messages.get(position));

            viewHolder.getCommentTextView().setText(message.getString("comment"));
            viewHolder.getUserTextView().setText(message.getString("netId"));
            viewHolder.getRatingBar().setStepSize((float)1.0);
            viewHolder.getRatingBar().setRating((float)message.getDouble("rating"));
        }catch(JSONException e) { e.printStackTrace(); }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() { return messages.size(); }

}