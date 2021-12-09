package net.ReelViews.adapters;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import net.ReelViews.R;
import net.ReelViews.models.MovieModel;

import org.w3c.dom.Text;

public class Popular_View_Holder extends RecyclerView.ViewHolder implements
        View.OnClickListener {

    OnMovieListener onMovieListener;
    ImageView imageView22;
    RatingBar ratingBar22;
    TextView textView;


    public Popular_View_Holder(@NonNull View itemView, OnMovieListener onMovieListener) {
        super(itemView);

        this.onMovieListener = onMovieListener;
        imageView22 = itemView.findViewById(R.id.movie_img);
        ratingBar22 = itemView.findViewById(R.id.rating_bar);
        textView = itemView.findViewById(R.id.movie_tittle);





        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //Display the details on later videos
    }

}
