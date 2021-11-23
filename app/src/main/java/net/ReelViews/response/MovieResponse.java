package net.ReelViews.response;


// Single Movie Requests

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import net.ReelViews.models.MovieModel;

public class MovieResponse {
    // Finding Movie Object
    @SerializedName("results")
    @Expose
    private MovieModel movie;

    public MovieModel getMovie() {
        return movie;
    }

    @Override
    public String toString() {
        return "MovieResponse{" +
                "movie=" + movie +
                '}';
    }
}
