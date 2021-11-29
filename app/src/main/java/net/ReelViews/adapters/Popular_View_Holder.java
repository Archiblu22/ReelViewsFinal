package net.ReelViews.adapters;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.ReelViews.R;

public class Popular_View_Holder extends RecyclerView.ViewHolder implements
        View.OnClickListener{

    OnMovieListener onMovieListener;
    ImageView imageView22;
    RatingBar ratingBar22;

    public Popular_View_Holder(@NonNull View itemView, OnMovieListener onMovieListener) {
        super(itemView);

        this.onMovieListener = onMovieListener;
        imageView22 = itemView.findViewById(R.id.movie_img);
        ratingBar22 = itemView.findViewById(R.id.rating_bar);


        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //Display the details on later videos
    }

}
