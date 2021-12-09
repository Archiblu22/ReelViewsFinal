package net.ReelViews.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.ReelViews.R;
import net.ReelViews.models.MovieModel;

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    // Widgets
    ImageView imageView;
    RatingBar ratingBar;
    TextView textView;

    // Click Listener
    OnMovieListener onMovieListener;

    public MovieViewHolder(@NonNull View itemView, OnMovieListener onMovieListener) {
        super(itemView);

        this.onMovieListener = onMovieListener;

        textView = itemView.findViewById(R.id.movie_tittle);

        imageView = itemView.findViewById(R.id.movie_img);
        ratingBar = itemView.findViewById(R.id.rating_bar);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        onMovieListener.onMovieClick(getAdapterPosition());

    }
}
