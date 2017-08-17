package myproject.searchfilm.api;

import myproject.searchfilm.model.FilmListResponse;
import myproject.searchfilm.model.InformationAboutFilm;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Aliaksandr on 8/6/2017.
 */

public interface FilmsInterface {
    @GET("search/movie")
    Call<FilmListResponse> getFilmList(@Query("api_key") String api_key, @Query("query") String query);

    @GET("movie/{movie_id}")
    Call<InformationAboutFilm> getFilmInformation(@Path("movie_id") int movie_id, @Query("api_key") String api_key);
}
