package myproject.searchfilm.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import myproject.searchfilm.R;
import myproject.searchfilm.activity.ListOfFilmsByGenreActivity;
import myproject.searchfilm.adapter.GenreAdapter;
import myproject.searchfilm.api.RestHelper;
import myproject.searchfilm.model.Genre;
import myproject.searchfilm.model.GenreListResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static myproject.searchfilm.activity.ListOfFilmsByGenreActivity.ID_GENRE_KEY;
import static myproject.searchfilm.activity.ListOfFilmsByGenreActivity.NAME_GENRE_KEY;


/**
 * Created by Aliaksandr on 8/15/2017.
 */

public class SearchFilmByGenresFragment extends Fragment {

    public static final String GENRE_KEY = "GENRE_KEY";
    private static final String API_KEY = "349f0282b34402e866888a09b5d49fb5";

    @BindView(R.id.recycler_view_for_search_by_genre)
    RecyclerView recyclerView;
    @BindView(R.id.search_by_genre_bar)
    ProgressBar searchBar;
    @BindView(R.id.view_error_for_search_by_genre)
    LinearLayout viewError;
    @BindView(R.id.view_no_results_for_search_by_genre)
    LinearLayout viewNoResults;

    private ArrayList<Genre> mGenres;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_film_by_genre, null);
        ButterKnife.bind(this, v);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        showRecyclerView();

        if (savedInstanceState != null) {
            mGenres = savedInstanceState.getParcelableArrayList(GENRE_KEY);
            if (mGenres != null) {
                initializeGenreAdapter();
            }else{
                showProgressBarView();
                loadGenre();
            }
        } else {
            showProgressBarView();
            loadGenre();
        }

        if (mGenres != null) {
            initializeGenreAdapter();
        }
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(GENRE_KEY, mGenres);
    }

    private void loadGenre() {
        RestHelper.getInterface().getGenreList(API_KEY).enqueue(new Callback<GenreListResponse>() {
            @Override
            public void onResponse(Call<GenreListResponse> call, Response<GenreListResponse> response) {
                if (mGenres == null) mGenres = new ArrayList<>();
                mGenres.clear();
                mGenres.addAll(response.body().getGenres());
                if (mGenres.isEmpty()) {
                    showNoResultsView();
                } else {
                    initializeGenreAdapter();
                    showRecyclerView();
                }
            }

            @Override
            public void onFailure(Call<GenreListResponse> call, Throwable t) {
                showErrorView();
            }
        });
    }

    private void initializeGenreAdapter() {
        GenreAdapter rvAdapter = new GenreAdapter(mGenres, new GenreAdapter.OnGenreClickListener() {
            @Override
            public void onGenreClicked(Genre genre) {
                Intent intent = new Intent(getActivity(), ListOfFilmsByGenreActivity.class);
                intent.putExtra(ID_GENRE_KEY, genre.getId());
                intent.putExtra(NAME_GENRE_KEY, genre.getName());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(rvAdapter);
    }

    private void showRecyclerView() {
        recyclerView.setVisibility(View.VISIBLE);
        searchBar.setVisibility(View.GONE);
        viewError.setVisibility(View.GONE);
        viewNoResults.setVisibility(View.GONE);
    }

    private void showProgressBarView() {
        recyclerView.setVisibility(View.GONE);
        searchBar.setVisibility(View.VISIBLE);
        viewError.setVisibility(View.GONE);
        viewNoResults.setVisibility(View.GONE);
    }

    private void showErrorView() {
        recyclerView.setVisibility(View.GONE);
        searchBar.setVisibility(View.GONE);
        viewError.setVisibility(View.VISIBLE);
        viewNoResults.setVisibility(View.GONE);
    }

    private void showNoResultsView() {
        recyclerView.setVisibility(View.GONE);
        searchBar.setVisibility(View.GONE);
        viewError.setVisibility(View.GONE);
        viewNoResults.setVisibility(View.VISIBLE);
    }
}
