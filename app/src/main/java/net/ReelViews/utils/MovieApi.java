package net.ReelViews.utils;

import net.ReelViews.models.MovieModel;
import net.ReelViews.response.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

    // Search for movies
    //https://api.themoviedb.org/3/search/movie?api_key=<<api_key>>&query=example%20text
    @GET("/3/search/movie")
    Call<MovieSearchResponse> searchMovie(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") int page
    );

    // Making Search with ID
    // https://api.themoviedb.org/3/movie/550?api_key=06f3737ddf27be998aa76d9b4a554f00
    @GET("3/movie/{movie_id}?")
    Call<MovieModel> getMovie(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key
    );
}
