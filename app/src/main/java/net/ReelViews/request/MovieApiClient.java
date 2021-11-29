package net.ReelViews.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import net.ReelViews.AppExecutors;
import net.ReelViews.models.MovieModel;
import net.ReelViews.response.MovieSearchResponse;
import net.ReelViews.utils.Credentials;
import net.ReelViews.utils.MovieApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient{

    // Live Data for search
    private MutableLiveData<List<MovieModel>> mMovies;
    private static MovieApiClient instance;

    // Global request
    private RetrieveMoviesRunnable retrieveMoviesRunnable;

    // LIVE DATA FOR POPULAR MOVIES
    private MutableLiveData<List<MovieModel>> mMoviesPop;

    // MAKING POPULAR RUNNABLE
    private RetrieveMoviesRunnablePop retrieveMoviesRunnablePop;

    public static MovieApiClient getInstance(){
        if (instance == null){
            instance = new MovieApiClient();
        }
        return instance;
    }

    private MovieApiClient(){
        mMovies = new MutableLiveData<>();
        mMoviesPop = new MutableLiveData<>();
    }



    public LiveData<List<MovieModel>> getMovies() { return mMovies;
    }

    public LiveData<List<MovieModel>> getMoviesPop() { return mMoviesPop;
    }

    // 1 - Call Method through Classes
    public void searchMoviesApi(String query, int pageNumber) {
        if (retrieveMoviesRunnable != null){
            retrieveMoviesRunnable = null;
        }

        retrieveMoviesRunnable = new RetrieveMoviesRunnable(query, pageNumber);

        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // Cancelling retrofit call
                myHandler.cancel(true);
            }
        }, 3000, TimeUnit.MILLISECONDS);
    }

    public void searchMoviesPop(int pageNumber) {
        if (retrieveMoviesRunnablePop != null){
            retrieveMoviesRunnablePop = null;
        }

        retrieveMoviesRunnablePop = new RetrieveMoviesRunnablePop(pageNumber);

        final Future myHandler2 = AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnablePop);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // Cancelling retrofit call
                myHandler2.cancel(true);
            }
        }, 1000, TimeUnit.MILLISECONDS);
    }

        // Retrieve RestAPI by ID and Search Query
    private class RetrieveMoviesRunnable implements Runnable{

           private String query;
           private int pageNumber;
           boolean cancelRequest;

            public RetrieveMoviesRunnable(String query, int pageNumber) {
                this.query = query;
                this.pageNumber = pageNumber;
                cancelRequest = false;
            }

            @Override
            public void run() {

                try {
                    Response response = getMovies(query, pageNumber).execute();

                    if (cancelRequest){
                        return;
                    }
                    if(response.code() == 200){
                        List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response.body()).getMovies());
                        if (pageNumber == 1){
                            mMovies.postValue(list);
                        }else{
                            List<MovieModel> currentMovies = mMovies.getValue();
                            currentMovies.addAll(list);
                            mMovies.postValue(currentMovies);
                        }
                    }else{
                        String error = response.errorBody().string();
                        Log.v("Tag", "Error " +error);
                        mMovies.postValue(null);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    mMovies.postValue(null);
                }

            }

            //Search Query
            private Call<MovieSearchResponse> getMovies(String query, int pageNumber){
                return Servicey.getMovieApi().searchMovie(
                        Credentials.API_KEY,
                        query,
                        pageNumber
                );
            }

            private void cancelRequest(){
                Log.v("Tag","Cancelling Search Request");
                cancelRequest = true;
            }

        }

    private class RetrieveMoviesRunnablePop implements Runnable{


        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnablePop(int pageNumber) {
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {

            try {
                Response response2 = getPop(pageNumber).execute();

                if (cancelRequest){
                    return;
                }
                if(response2.code() == 200){
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response2.body()).getMovies());
                    if (pageNumber == 1){
                        mMoviesPop.postValue(list);
                    }else{
                        List<MovieModel> currentMovies = mMoviesPop.getValue();
                        currentMovies.addAll(list);
                        mMoviesPop.postValue(currentMovies);
                    }
                }else{
                    String error = response2.errorBody().string();
                    Log.v("Tag", "Error " +error);
                    mMoviesPop.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mMoviesPop.postValue(null);
            }

        }

        //Search Query
        private Call<MovieSearchResponse> getPop(int pageNumber){
            return Servicey.getMovieApi().getPopular(
                    Credentials.API_KEY,
                    pageNumber
            );
        }

        private void cancelRequest(){
            Log.v("Tag","Cancelling Search Request");
            cancelRequest = true;
        }

    }

}
