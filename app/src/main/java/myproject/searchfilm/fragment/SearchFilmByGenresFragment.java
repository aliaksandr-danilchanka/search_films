package myproject.searchfilm.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import myproject.searchfilm.R;

/**
 * Created by Aliaksandr on 8/15/2017.
 */

public class SearchFilmByGenresFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_film_by_genres, null);
        ButterKnife.bind(this, v);
        return v;
    }
}
