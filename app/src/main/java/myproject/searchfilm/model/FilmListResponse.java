package myproject.searchfilm.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Aliaksandr on 8/6/2017.
 */

public class FilmListResponse {

    @SerializedName("results")
    private List<Film> films;

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }

}
